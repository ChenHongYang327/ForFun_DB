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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Appointment;
import member.bean.Order;
import member.bean.Publish;
import service.AppointmentService;
import service.CityService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
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
		try (InputStream in = getServletContext().getResourceAsStream("/firebaseServerKey.json")) {
			FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(in))
					.build();
			if (NotificationController.firebaseApp == null) {
				NotificationController.firebaseApp = FirebaseApp.initializeApp(options);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				int notified=publishService.selectById(publishId).getOwnerId();
				JsonObject resp = new JsonObject();
				if (publishService.deleteById(publishId) == 1) {
					// 刪除預約單的通知及預約單
					List<Appointment> appointments = new AppointmentService().selectAppointmentByPublishId(publishId);
					if (appointments.size() > 0) {
						for (Appointment appointment : appointments) {
							appointmentService.deleteById(appointment.getAppointmentId());// 加Delete_Time
							notificationService.deleteAppointment(notified,appointment.getAppointmentId());
						}
					}
					// 刪除訂單的通知及訂單
					List<Order> orders = orderService.selectAllByPublishID(publishId);
					if (orders.size() > 0) {
						for (Order order : orders) {
							orderService.deleteById(order.getOrderId());// 加Delete_Time
							notificationService.deleteOrder(notified, order.getOrderId()); 
						}
					}
					// 觸發通知
					String memberToken = new MemberService().selectById(appointments.get(0).getOwnerId()).getToken();
					if (memberToken != null) {	
						NotificationController.sendSingleFcmNoNotification(memberToken);
					}
					resp.addProperty("result", true);
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
