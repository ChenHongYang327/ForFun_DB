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

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Comment;
import member.bean.Member;
import member.bean.Notification;
import member.bean.Post;
import member.bean.Publish;
import service.AppointmentService;
import service.CommentService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.PostService;
import service.PublishService;

@WebServlet("/NotificationController")
public class NotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static FirebaseApp firebaseApp;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		NotificationService notificationService = new NotificationService();
		PublishService publishService = new PublishService();
		AppointmentService appointmentService = new AppointmentService();
		CommentService commentService = new CommentService();
		PostService postService = new PostService();
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
				List<Notification> notifications =new ArrayList<>() ;
				List<String> customersHeadShot = new ArrayList<>();
				List<Integer>appointmentOwnerId=new ArrayList<>();
				int customerId;
				//過濾通知
				for (Notification notification : notificationService.selectByMemberID(memberId)) {
					// 取得客戶頭貼URL
					// 留言
					if (notification.getCommentId() != 0) {
						Comment comment = commentService.selectById(notification.getCommentId());
						if (comment.getDeleteTime() == null) {
							notifications.add(notification);
							customerId = comment.getMemberId();
							customersHeadShot.add(memberService.selectById(customerId).getHeadshot());
							appointmentOwnerId.add(-1);
						}
					}
					// 預約單
					else if (notification.getAppointmentId() != 0) {
						// 取得客戶Id
//						System.out.println(notification.getAppointmentId() + "");
						notifications.add(notification);
//						System.out.println(notification.getAppointmentId()+"");
						customerId = appointmentService.notificationselectById(notification.getAppointmentId()).getTenantId();
						customersHeadShot.add(memberService.selectById(customerId).getHeadshot());
						appointmentOwnerId.add(appointmentService.notificationselectById(notification.getAppointmentId()).getOwnerId());

					}
					// 訂單
					else if (notification.getOrderId() != 0) {
//						System.out.println(notification.getOrderId() + "");
						notifications.add(notification);
						customerId = orderService.selectByID(notification.getOrderId()).getTenantId();
						customersHeadShot.add(memberService.selectById(customerId).getHeadshot());
						appointmentOwnerId.add(-1);
					}
					// 私訊
					else if (notification.getMessageId() != 0) {
						// customerId=meService.selectById(notification.getAppointmentId()).getTenantId();
						// customersId.add(customerId);
						
					}
				}
				// 寫出回應
				JsonObject resp = new JsonObject();
				resp.addProperty("Notifications", new Gson().toJson(notifications));
				resp.addProperty("CustomersHeadShot", new Gson().toJson(customersHeadShot));
				resp.addProperty("appointmentOwnerId", new Gson().toJson(appointmentOwnerId));
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
			} else if (req.get("action").getAsString().equals("getPostId")) {
				int commentId = req.get("commentId").getAsInt();
				int postId = commentService.selectById(commentId).getPostId();
				Post post = postService.selectById(postId);
				Member member=memberService.selectById(post.getPostId());
				String name=member.getNameL()+member.getNameF();
				String headshot=member.getHeadshot();
				JsonObject resp= new JsonObject();
				resp.addProperty("post", new Gson().toJson(post));
				resp.addProperty("name", name);
				resp.addProperty("headshot", headshot);
				writer.print(resp.toString());
			}
			else if (req.get("action").getAsString().equals("getPostTitle")) {
				int commentId = req.get("commentId").getAsInt();
				int postId = commentService.selectById(commentId).getPostId();
				Post post = postService.selectById(postId);
				writer.print(post.getPostTitle());
			}

			else if (req.get("action").getAsString().equals("getPublishTitle")) {
				int appointmentId = req.get("appointmentId").getAsInt();
				int publishId = appointmentService.notificationselectById(appointmentId).getPublishId();
				Publish publish =publishService.selectById(publishId);
				writer.print(publish.getTitle());
			}
		}
	}
	
	// 發送單一FCM
		public static void sendSingleFcm(JsonObject jsonObject, String registrationToken) {
			String title = jsonObject.get("title").getAsString();
			String body = jsonObject.get("body").getAsString();
			String data = jsonObject.get("data") == null ? "no data" : jsonObject.get("data").getAsString();
			// 主要設定訊息標題與內容，client app一定要在背景時才會自動顯示
			com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder()
					.setTitle(title) // 設定標題
					.setBody(body) // 設定內容
					.build();
			// 發送notification message
			Message.Builder message = Message.builder();
			message
				 	.setNotification(notification) // 設定client app在背景時會自動顯示訊息
				 	.putData("data", data)// 設定自訂資料，user點擊訊息時方可取值
			 		.setToken(registrationToken); // 送訊息給指定token的裝置
			try {
				FirebaseMessaging.getInstance().send(message.build());
//						String messageId = FirebaseMessaging.getInstance().send(message);
//						System.out.println(registrationToken);
//						System.out.println("messageId: " + messageId);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
			}
		}
		
		// 發送單一FCM(不含通知)
		public static void sendSingleFcmNoNotification (String registrationToken) {	
					// 發送notification message
					Message.Builder message = Message.builder();
					message	.setToken(registrationToken); // 送訊息給指定token的裝置
					try {
						FirebaseMessaging.getInstance().send(message.build());
//								String messageId = FirebaseMessaging.getInstance().send(message);
//								System.out.println(registrationToken);
//								System.out.println("messageId: " + messageId);
					} catch (FirebaseMessagingException e) {
						e.printStackTrace();
					}
				}

}
