package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import member.bean.Agreement;
import member.bean.Appointment;
import member.bean.Order;
import member.bean.Publish;
import service.AgreementService;
import service.AppointmentService;
import service.CityService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.OtherPayService;
import service.PublishService;

/**
 * Servlet implementation class PublishManage
 */
@WebServlet("/publishListController")
public class PublishListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		JsonObject clientReq = new Gson().fromJson(request.getReader(), JsonObject.class);
		PublishService publishService = new PublishService();
		CityService cityService = new CityService();
		NotificationService notificationService = new NotificationService();
		AppointmentService appointmentService = new AppointmentService();
		OrderService orderService = new OrderService();
		try (PrintWriter pw = response.getWriter()) {
			if (clientReq.get("action").getAsString().equals("getPublishList")) {
				int memberId = clientReq.get("memberId").getAsInt();
				List<Publish> publishes = publishService.selectByOwnerId(memberId);
				List<String> cityNames = new ArrayList<String>();
				for (Publish publish : publishes) {
					cityNames.add(cityService.selectNameById(publish.getCityId()));
				}
				JsonObject resp = new JsonObject();
				resp.addProperty("publishes", new Gson().toJson(publishes));
				resp.addProperty("cityNames", new Gson().toJson(cityNames));
				pw.print(resp.toString());
//				System.out.println(resp.toString());
				// 刊登單刪除
			} else if (clientReq.get("action").getAsString().equals("pubishDelete")) {
				// 被刪除的刊登單Id
				int publishId = clientReq.get("publishId").getAsInt();
				int notified = publishService.selectById(publishId).getOwnerId();
				JsonObject resp = new JsonObject();
				if (publishService.deleteById(publishId) == 1) {
					// 刪除預約單的通知及預約單
					List<Appointment> appointments = new AppointmentService().selectAppointmentByPublishId(publishId);
					if (appointments.size() > 0&& appointments!=null) {
						int count1 = 0;
						for (Appointment appointment : appointments) {
							// 刪除預約
							appointmentService.deleteById(appointment.getAppointmentId());// 加Delete_Time
							// 房東
							count1 = notificationService.deleteAppointment(appointment.getOwnerId(),
									appointment.getAppointmentId());

							// 刪除及即時更新通知房客
							if (notificationService.deleteAppointment(appointment.getTenantId(),
									appointment.getAppointmentId()) > 0) {
								String memberToken2 = new MemberService().selectById(appointment.getTenantId())
										.getToken();
								if (memberToken2 != null) {
									NotificationController.sendSingleFcmNoNotification(memberToken2);
								}
							}
						}
						// 如果房東通知有刪除
						if (count1 > 0) {
							// 房東僅需觸發一次
							String memberToken1 = new MemberService().selectById(notified).getToken();
							if (memberToken1 != null) {
								NotificationController.sendSingleFcmNoNotification(memberToken1);

							}
						}
					}

					// 刪除訂單的通知及訂單
					List<Order> orders = orderService.selectAllByPublishID(publishId);
					List<Agreement> agreements=new ArrayList<Agreement>();
					AgreementService agreementService=new AgreementService();
					if (orders.size() > 0 && orders!=null) {
						int count1 = 0;
						for (Order order : orders) {
							//取得跟此訂單有關的所有合約(刪除其他付款用)
							if(agreementService.selectByOrderId(order.getOrderId())!=null) {
								agreements.add(agreementService.selectByOrderId(order.getOrderId()));
							}
							// 刪除合約
							agreementService.deleteByOrderId(order.getOrderId());
							// 刪除訂單
							orderService.deleteByPublishId(order.getPublishId());// 加Delete_Time
							// 刪除訂單通知
							count1 = notificationService.deleteOrder(notified, order.getOrderId());
							// 刪除及即時更新通知房客
							if (notificationService.deleteOrder(order.getTenantId(), order.getOrderId()) > 0) {
								String memberToken2 = new MemberService().selectById(order.getTenantId()).getToken();
								if (memberToken2 != null) {
									NotificationController.sendSingleFcmNoNotification(memberToken2);
								}
							}
						}
						//如果房東通知有刪除
						if (count1 > 0) {
							// 房東僅需觸發一次
							String memberToken1 = new MemberService().selectById(notified).getToken();
							if (memberToken1 != null) {
								NotificationController.sendSingleFcmNoNotification(memberToken1);
							}
						}
					}
					//刪除其他付款
					if(agreements.size()>0 && agreements!=null) {
						System.out.println(agreements.size()+"");
						OtherPayService otherPayService=new OtherPayService();
//						int count=0;//測試用
						for(Agreement agreement:agreements) {
							otherPayService.deleteByAgreementId(agreement.getAgreementId());
//							count+=otherPayService.deleteByAgreementId(agreement.getAgreementId()); //測試用
							
						}
//						System.out.println(count+""); //測試用
					}
					resp.addProperty("result", true);
				
					//刪除其他付款
				} else {
					resp.addProperty("result", false);
				}
				pw.print(resp.toString());
			} else if (clientReq.get("action").getAsString().equals("updateStatus")) {
				int publishId = clientReq.get("publishId").getAsInt();
				int status = -1;
				if (clientReq.get("status").getAsString().equals("close")) {
					status = 2;
				} else if (clientReq.get("status").getAsString().equals("open")) {
					status = 3;
				}
				if (status != -1) {
					JsonObject resp = new JsonObject();
					if (publishService.updateStatus(status, publishId) == 1) {
						resp.addProperty("result", true);
					} else {
						resp.addProperty("result", false);
					}
					pw.print(resp.toString());
				}
			}
		}
	}

}
