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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Order;
import member.bean.OtherPay;
import member.bean.Publish;
import service.AppointmentService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.OtherPayService;
import service.PublishService;

@WebServlet("/OrderConfirm")
public class OrderConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private int resultcode = -1; // 判斷碼
	private OrderService orderService = new OrderService();
	private PublishService publishService = new PublishService();
	private AppointmentService appointmentService = new AppointmentService();
	private OtherPayService otherPayService = new OtherPayService();
	
	@Override
	public void init() throws ServletException {
		// 私密金鑰檔案可以儲存在專案以外
		// File file = new File("/path/to/firsebase-java-privateKey.json");
		// 私密金鑰檔案也可以儲存在專案WebContent目錄內，私密金鑰檔名要與程式所指定的檔名相同
		try (InputStream in = getServletContext().getResourceAsStream("/firebaseServerKey.json")) {
			FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(in))
					.build();
			if(NotificationController.firebaseApp==null) {
				NotificationController.firebaseApp=FirebaseApp.initializeApp(options);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		case 1: //房客流程 拿訂單物件
			int statusCode = jsonObj.get("STATUS").getAsInt();
			int signinId = jsonObj.get("SIGNINID").getAsInt(); //房客ＩＤ
			List<Order> orders = orderService.selectAllBySatus(statusCode,signinId);
			
			jsonWri.addProperty("ORDERLIST", gson.toJson(orders));
			jsonWri.addProperty("RESULT", 200);
			
			break;
			
		case 2: //房客流程 拿刊登單物件
			int publishId = jsonObj.get("PUBLISHID").getAsInt();
			Publish publish = publishService.selectById(publishId);
			String publStr = gson.toJson(publish);
			
			jsonWri.addProperty("PUBLISH", publStr);
			jsonWri.addProperty("RESULT", 200);
			
			break;
		case 3: //房客流程 reserve專用 take AppointmentId
			int publishId_3 = jsonObj.get("PUBLISHID").getAsInt();
			int signinId_3 = jsonObj.get("SIGNINID").getAsInt();
			
			int apmtId_3 = appointmentService.selectAppointmentIdByTenantID(publishId_3, signinId_3);
			
			jsonWri.addProperty("APPOINTMENTID", apmtId_3);
			jsonWri.addProperty("RESULT", 200);
			
			break;
		case 4: //房東流程 reserve專用 take AppointmentId
			int publishId_4 = jsonObj.get("PUBLISHID").getAsInt();
			int signinId_4 = jsonObj.get("SIGNINID").getAsInt();
			
			int apmtId_4 = appointmentService.selectAppointmentIdByOwnerID(publishId_4, signinId_4);
			
			jsonWri.addProperty("APPOINTMENTID", apmtId_4);
			jsonWri.addProperty("RESULT", 200);
			
			break;
			
		case 5: //房東流程 拿訂單物件
			int statusCode_5 = jsonObj.get("STATUS").getAsInt();
			int signinId_5 = jsonObj.get("SIGNINID").getAsInt(); //房東ＩＤ
			List<Order> orders_5 = orderService.selectAllByOwnerandSatus(statusCode_5, signinId_5);
			
			jsonWri.addProperty("ORDERLIST", gson.toJson(orders_5));
			jsonWri.addProperty("RESULT", 200);
			
			//通知功能
			int updateCount=new NotificationService().updateAppointment(signinId_5);
			if(updateCount>0) {
				String memberToken=new MemberService().selectById(signinId_5).getToken();
				if(memberToken!=null) {
					JsonObject notificaitonFCM = new JsonObject();
					notificaitonFCM.addProperty("title", "新通知");
					notificaitonFCM.addProperty("body", "刪除");
					sendSingleFcm(notificaitonFCM, memberToken);
				}
			}
			//---------
			
			break;
			
		case 6: //房客流程 拿 otherpay List
			int statusCode_6 = jsonObj.get("STATUS").getAsInt();
			int signinId_6 = jsonObj.get("SIGNINID").getAsInt(); //房客ＩＤ
			
			// signId->orderId->agreementId->otherpayId->otherpayList
			List<OtherPay> otherPays = otherPayService.selectByTenntId(signinId_6, statusCode_6);
			
			jsonWri.addProperty("OTHERPAYLIST", gson.toJson(otherPays));
			jsonWri.addProperty("RESULT", 200);
			
			break;
			
		case 7: //otherpay流程 拿order 物件 By otherpayId
			int otherpayId_7 = jsonObj.get("OTHERPAYID").getAsInt(); //房客ＩＤ
			Order order = orderService.selectByotherpayID(otherpayId_7);
			
			jsonWri.addProperty("ORDER", gson.toJson(order));
			jsonWri.addProperty("RESULT", 200);
			
			break;
			
		case 8: //房東流程 拿 otherpay List
			int statusCode_8 = jsonObj.get("STATUS").getAsInt();
			int signinId_8 = jsonObj.get("SIGNINID").getAsInt(); //房東ＩＤ
			
			// signId->orderId->agreementId->otherpayId->otherpayList
			List<OtherPay> oList = otherPayService.selectByOwnerId(signinId_8, statusCode_8);
			
			jsonWri.addProperty("OTHERPAYLIST", gson.toJson(oList));
			jsonWri.addProperty("RESULT", 200);
			
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
	// 發送單一FCM
			private void sendSingleFcm(JsonObject jsonObject, String registrationToken) {
				String title = jsonObject.get("title").getAsString();
				String body = jsonObject.get("body").getAsString();
				String data = jsonObject.get("data") == null ? "no data" : jsonObject.get("data").getAsString();
				// 主要設定訊息標題與內容，client app一定要在背景時才會自動顯示
				Notification notification = Notification.builder().setTitle(title) // 設定標題
						.setBody(body) // 設定內容
						.build();
				// 發送notification message
				Message.Builder message = Message.builder();
				if (!body.equals("刪除")) {
					message.setNotification(notification) // 設定client app在背景時會自動顯示訊息
							.putData("data", data); // 設定自訂資料，user點擊訊息時方可取值
				}
				message.setToken(registrationToken); // 送訊息給指定token的裝置
				try {
					FirebaseMessaging.getInstance().send(message.build());
//								String messageId = FirebaseMessaging.getInstance().send(message.build());
//								System.out.println(registrationToken);
//								System.out.println("messageId: " + messageId);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
				}
			}

}
