package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Notification;
import service.AppointmentService;
import service.CommentService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.PublishService;

@WebServlet("/NotificationController")
public class NotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		NotificationService notificationService = new NotificationService();
		PublishService publishService = new PublishService();
		AppointmentService appointmentService = new AppointmentService();
		CommentService commentService = new CommentService();
		OrderService orderService = new OrderService();
		MemberService memberService = new MemberService();
		try (BufferedReader reader = request.getReader(); PrintWriter writer = response.getWriter()) {
			JsonObject req = new Gson().fromJson(reader, JsonObject.class);
//			System.out.println(req);
			// 取得通知數
			if (req.get("action").getAsString().equals("getNotificationCouunt")) {
				int memberId = req.get("memberId").getAsInt();
				List<Notification> notifications = notificationService.selectByMemberID(memberId);
				int count = 0;
				for (Notification notification : notifications) {
					// 過濾已讀的
					if (notification.getRead() == false) {
						count++;
					}
				}
				writer.print(count);
//				System.out.println(notifications.size()+"");
			}
			// 取得通知
			else if (req.get("action").getAsString().equals("getNotification")) {
				int memberId = req.get("memberId").getAsInt();
				List<Notification> notifications = notificationService.selectByMemberID(memberId);
				List<String> customersHeadShot = new ArrayList<>();
				int customerId;			
				for (Notification notification : notifications) {
					// 取得客戶頭貼URL
					// 留言
					if (notification.getCommentId() != 0) {				
						customerId = commentService.selectById(notification.getCommentId()).getMemberId();
						customersHeadShot.add(memberService.selectById(customerId).getHeadshot());
				
					}
					// 預約單
					else if (notification.getAppointmentId() != 0) {					
						// 取得客戶Id
//						System.out.println(notification.getAppointmentId() + "");
						customerId = appointmentService.selectById(notification.getAppointmentId()).getTenantId();
						customersHeadShot.add(memberService.selectById(customerId).getHeadshot());

					}
					// 訂單
					else if (notification.getOrderId() != 0) {			
//						System.out.println(notification.getOrderId() + "");
						customerId = orderService.selectByID(notification.getOrderId()).getTenantId();			
						customersHeadShot.add(memberService.selectById(customerId).getHeadshot());				
					}
					// 私訊
					else if (notification.getMessageId() != 0) {			
						// customerId=meService.selectById(notification.getAppointmentId()).getTenantId();
						// customersId.add(customerId);				
					}
				}
				//寫出回應
					JsonObject resp = new JsonObject();
					resp.addProperty("Notifications", new Gson().toJson(notifications));
					resp.addProperty("CustomersHeadShot", new Gson().toJson(customersHeadShot));
					writer.print(resp.toString());
//					System.out.println(resp.toString());
			}
			// 更改已讀狀態
			else if (req.get("action").getAsString().equals("updateReaded")) {
				int notified = req.get("memberId").getAsInt();
				JsonObject resp = new JsonObject();
				if (notificationService.updateReaded(notified) >= 0) {
					resp.addProperty("result", true);
				} else {
					resp.addProperty("result", false);
				}
				writer.print(resp.toString());
			}
		}
	}

}