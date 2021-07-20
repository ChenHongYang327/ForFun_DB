package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;

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
import service.AppointmentService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.PublishService;

@WebServlet("/appointment")
public class AppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
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

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();
        
        try (
            BufferedReader reader = request.getReader();
            PrintWriter writer = response.getWriter();
        ) {
            AppointmentService appointmentService = new AppointmentService();
            OrderService orderService = new OrderService();
            
            JsonObject object = gson.fromJson(reader, JsonObject.class);
            String action = object.get("action").getAsString();
            
            if("makeAppointment".equals(action)) {
                // 建立預約
                Appointment appointment = gson.fromJson(object.get("appointment").getAsString(), Appointment.class);
                
                // 該資料存在進行update，否則insert
                int count = 0;
                if (appointmentService.selectById(appointment.getAppointmentId()) != null) {
                    appointment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    count = appointmentService.update(appointment);
                	// 新增通知功能-------
					if (count > 0) {
						int notified = appointment.getOwnerId();
						if (notified != appointment.getTenantId()) {
							new NotificationService().editAppointment(notified, appointment.getAppointmentId());
							String memberToken = new MemberService().selectById(notified).getToken();
							if (memberToken != null) {
								JsonObject notificaitonFCM = new JsonObject();
								notificaitonFCM.addProperty("title", "新通知");
								String publishTitle = new PublishService().selectById(appointment.getPublishId())
										.getTitle();
								notificaitonFCM.addProperty("body", "您的" + "「" + publishTitle + "」" + "刊登單有一筆看房預約已被修改");
								sendSingleFcm(notificaitonFCM, memberToken);
							}
						}

					}
					//------------------
                } else {
                    appointment.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    count = appointmentService.insert(appointment);
            		// 新增通知功能-------
					if (count > 0) {
						int insertId = appointmentService.getInsertId();
						int notified = appointment.getOwnerId();
						if (notified != appointment.getTenantId()) {
							new NotificationService().insertAppointment(notified, insertId);
							String memberToken = new MemberService().selectById(notified).getToken();
							if (memberToken != null) {
								JsonObject notificaitonFCM = new JsonObject();
								notificaitonFCM.addProperty("title", "新通知");
								String publishTitle = new PublishService().selectById(appointment.getPublishId())
										.getTitle();
								notificaitonFCM.addProperty("body", "您的" + "「" + publishTitle + "」" + "刊登單有一筆新的看房預約");
								sendSingleFcm(notificaitonFCM, memberToken);
							}
						}

					}
					//------------------
                    // 增加一筆訂單
                    Order order = new Order();
                    order.setPublishId(appointment.getPublishId());
                    order.setTenantId(appointment.getTenantId());
                    order.setOrderStatus(11);
                    order.setRead(false);
                    order.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    orderService.insert(order);
                }
                
                JsonObject result = new JsonObject();
                result.addProperty("result_code", count > 0 ? 1 : 0);
                writer.write(gson.toJson(result));
            } else if ("cancelAppointment".equals(action)) {
                // 取消預約 (房客)
                int appointmentId = object.get("appointmentId").getAsInt();
                
                // 取得預約資料
                Appointment appointment = appointmentService.selectById(appointmentId);
                
                // 修改訂單狀態為6
                Order order = orderService.selectByPublishIDAndTenantID(appointment.getPublishId(), appointment.getTenantId()); 
                orderService.changeOrderStatus(order.getOrderId(), 6);
                //取得被通知者
                int notified=appointmentService.selectById(appointmentId).getOwnerId();
                // 刪除預約資料
                int count = appointmentService.deleteById(appointmentId);
                //刪除通知
                new NotificationService().deleteAppointment(notified, appointmentId);
                String memberToken=new MemberService().selectById(notified).getToken();
                if(memberToken!=null) {
                	JsonObject notificaitonFCM = new JsonObject();
					notificaitonFCM.addProperty("title", "新通知");
					String publishTitle = new PublishService().selectById(appointment.getPublishId())
							.getTitle();
					notificaitonFCM.addProperty("body", "刪除");
					sendSingleFcm(notificaitonFCM, memberToken);
                }
                //---------
                JsonObject result = new JsonObject();
                result.addProperty("result_code", count > 0 ? 1 : 0);
                writer.write(gson.toJson(result));
            } else if ("confirmAppointment".equals(action)) {
                // 確認預約 (房東)
                int appointmentId = object.get("appointmentId").getAsInt();
                
                // 取得預約資料
                Appointment appointment = appointmentService.selectById(appointmentId);
                
                // 修改訂單狀態為2
                Order order = orderService.selectByPublishIDAndTenantID(appointment.getPublishId(), appointment.getTenantId()); 
                orderService.changeOrderStatus(order.getOrderId(), 2);
                //被通知者
                int notified=appointmentService.selectById(appointmentId).getOwnerId();
                // 刪除預約資料
                int count = appointmentService.deleteById(appointmentId);
                //刪除通知        
                String memberToken=new MemberService().selectById(notified).getToken();
                if(memberToken!=null) {
                	JsonObject notificaitonFCM = new JsonObject();
					notificaitonFCM.addProperty("title", "新通知");
					String publishTitle = new PublishService().selectById(appointment.getPublishId())
							.getTitle();
					notificaitonFCM.addProperty("body", "刪除");
					sendSingleFcm(notificaitonFCM, memberToken);
                }
                //---------
                JsonObject result = new JsonObject();
                result.addProperty("result_code", count > 0 ? 1 : 0);
                writer.write(gson.toJson(result));
            } else if ("getByAppointmentId".equals(action)) {
                // 根據預約ID取得資料
                int appointmentId = object.get("appointmentId").getAsInt();
                
                JsonObject result = new JsonObject();
                result.addProperty("appointment", gson.toJson(appointmentService.selectById(appointmentId)));
                writer.write(gson.toJson(result));
            } else if ("getMyAppointmentByPublishId".equals(action)) {
                // 根據使用者ID和刊登單ID取得預約ID
                int memberId = object.get("userId").getAsInt();
                int publishId = object.get("publishId").getAsInt();
                
                JsonObject result = new JsonObject();
                result.addProperty("appointmentId", appointmentService.selectAppointmentIdByTenantID(publishId, memberId));
                writer.write(gson.toJson(result));
            }
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
//							String messageId = FirebaseMessaging.getInstance().send(message.build());
//							System.out.println(registrationToken);
//							System.out.println("messageId: " + messageId);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
			}
		}



}
