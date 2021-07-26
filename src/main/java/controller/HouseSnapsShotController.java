package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Member;
import member.bean.Order;
import member.bean.Publish;
import service.AgreementService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.PublishService;

@WebServlet("/HouseSnapsShot")
public class HouseSnapsShotController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private int resultcode = -1; // 判斷碼
	private OrderService orderService = new OrderService();
	private PublishService publishService = new PublishService();
	private MemberService memberService = new MemberService();
	private AgreementService agreementService = new AgreementService();

	@Override
	public void init() throws ServletException {
		// 私密金鑰檔案可以儲存在專案以外
		// File file = new File("/path/to/firsebase-java-privateKey.json");
		// 私密金鑰檔案也可以儲存在專案WebContent目錄內，私密金鑰檔名要與程式所指定的檔名相同
		if (NotificationController.firebaseApp == null) {
			try (InputStream in = getServletContext().getResourceAsStream("/firebaseServerKey.json")) {
				FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(in))
						.build();
				NotificationController.firebaseApp = FirebaseApp.initializeApp(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 收取client端資料
		request.setCharacterEncoding("UTF-8");
		// 回傳前端
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		StringBuilder jsonIn = new StringBuilder();
		try (BufferedReader br = request.getReader();) {
			String line = null;
			while ((line = br.readLine()) != null) {
				jsonIn.append(line);
			}
			System.out.println("input: " + jsonIn.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拿值
		JsonObject jsonObj = gson.fromJson(jsonIn.toString(), JsonObject.class);

		try {
			resultcode = jsonObj.get("RESULTCODE").getAsInt();
		} catch (Exception e) {
			resultcode = -1;
		}

		JsonObject jsonWri = new JsonObject();
		switch (resultcode) {
		case 1:
			int orderId = jsonObj.get("ORDERID").getAsInt();
			int signinId = jsonObj.get("SIGNINID").getAsInt();
			int publishId = orderService.selectPublishByID(orderId);
			Publish publish = publishService.selectById(publishId);

			// 判斷signinId 是房東OR房客，並回傳相對的值
			Member member = new Member();
			int tenantID = orderService.selectTenantByID(orderId);
			if (signinId == tenantID) {
				// signinId為房客
				int houseOwnerId = publishService.selectOwnerIdByID(publishId);
				member = memberService.selectById(houseOwnerId);

			} else {
				// signinId為房東
				member = memberService.selectById(tenantID);
			}

			jsonWri.addProperty("PUBLISH", gson.toJson(publish));
			jsonWri.addProperty("MEMBER", gson.toJson(member));
			jsonWri.addProperty("RESULT", 200);

			break;

		case 2:
			int orderId_2 = jsonObj.get("ORDERID").getAsInt();
			int orderStatus = jsonObj.get("STATUS").getAsInt();
			Boolean resault_2 = orderService.changeOrderStatus(orderId_2, orderStatus);

			if (resault_2 = true) {
				jsonWri.addProperty("RESULT", 200);
			} else {
				jsonWri.addProperty("RESULT", -1);
			}
			// 房客下定新增(order)
			if (orderStatus == 12) {
				MemberService memberService = new MemberService();
				int orderPublishId = orderService.selectPublishByID(orderId_2);
				int notifiedId = publishService.selectById(orderPublishId).getOwnerId();
				// 新增通知
				new NotificationService().insertOrder(notifiedId, orderId_2);
				String memberToken = memberService.selectById(notifiedId).getToken();
				if (memberToken != null) {
					JsonObject notificaitonFCM = new JsonObject();
					notificaitonFCM.addProperty("title", "新通知");
					String publishTitle = new PublishService().selectById(orderPublishId).getTitle();
					notificaitonFCM.addProperty("body", "您的" + "「" + publishTitle + "」" + "刊登單有一筆新訂單");
					NotificationController.sendSingleFcm(notificaitonFCM, memberToken);
				}

			} 
			//房東按下產生合約
			else if (orderStatus == 13) {
				MemberService memberService = new MemberService();
				int orderPublishId = orderService.selectPublishByID(orderId_2);
				int notifiedId = publishService.selectById(orderPublishId).getOwnerId();
				// 刪除通知
				new NotificationService().deleteOrder(notifiedId, orderId_2);
				String memberToken = memberService.selectById(notifiedId).getToken();
				if (memberToken != null) {
					NotificationController.sendSingleFcmNoNotification(memberToken);
				}

			} 
			//預約被取消
			else if(orderStatus == 6) {
				System.out.println("!@34555");
				MemberService memberService = new MemberService();
				int orderPublishId = orderService.selectPublishByID(orderId_2);
				//房東
				int notifiedId1 = publishService.selectById(orderPublishId).getOwnerId();
				//房客
				int notifiedId2 = orderService.selectTenantByID(orderId_2);	
				// 刪除通知房東
				String memberToken=null;
				if(new NotificationService().deleteOrder(notifiedId1, orderId_2)>0){
					memberToken = memberService.selectById(notifiedId1).getToken();
					if (memberToken != null) {
						NotificationController.sendSingleFcmNoNotification(memberToken);
					}
				}
				// 刪除通知房客
				if (new NotificationService().deleteOrder(notifiedId2, orderId_2)>0){
					memberToken = memberService.selectById(notifiedId2).getToken();
					if (memberToken != null) {
						NotificationController.sendSingleFcmNoNotification(memberToken);
					}
				}
			}
			break;
		case 3:
			int orderId_3 = jsonObj.get("ORDERID").getAsInt();
			int agreementId = agreementService.selectAgmtidByOrderid(orderId_3);

			if (agreementId > 0) {
				jsonWri.addProperty("RESULT", 200);
				jsonWri.addProperty("AGREEMENTID", agreementId);
			} else {
				jsonWri.addProperty("RESULT", -1);
			}
			break;

		default:
			jsonWri.addProperty("RESULT", -1);
			break;
		}

		try (PrintWriter pw = response.getWriter();) {
			pw.println(jsonWri);
			System.out.println("output: " + jsonWri.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
