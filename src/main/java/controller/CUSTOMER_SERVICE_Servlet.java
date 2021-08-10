package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Customer_bean;
import service.CUSTOMER_SERVICE_service;

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/CUSTOMER_SERVICE_Servlet")
public class CUSTOMER_SERVICE_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		
		JsonObject clientReq = new Gson().fromJson(request.getReader(), JsonObject.class);

		CUSTOMER_SERVICE_service customer_SERVICE_service = new CUSTOMER_SERVICE_service();
//		System.out.println("客戶端的請求:" + clientReq);
		if (clientReq.get("action").getAsString().equals("CUSTOMER_SERVICE")) {
			JsonObject respJson = new JsonObject(); // 伺服器回覆
			String username = clientReq.get("username").getAsString();
			String mail = clientReq.get("mail").getAsString();
			String phone = clientReq.get("phone").getAsString();
			String message = clientReq.get("message").getAsString();
			Customer_bean customer_Service = new Customer_bean();
			customer_Service.setNickName(username);
			customer_Service.setMail(mail);
			customer_Service.setPhone(phone);
			customer_Service.setMag(message);
			if (customer_SERVICE_service.insert(customer_Service) == 1) {
				respJson.addProperty("status", true);
			} else {
				respJson.addProperty("status", false);
			}

			try (PrintWriter writer = response.getWriter()) {
				writer.print(respJson);
				System.out.println("ouption: " + respJson);

			} catch (Exception e) {
				System.out.println("exception: " + e.toString());
			}
		} else if (clientReq.get("action").getAsString().equals("getAll")) {
		    // 取得全部客服資料
            JsonObject result = new JsonObject();
            result.addProperty("customerList", gson.toJson(customer_SERVICE_service.selectAll()));
            try (PrintWriter writer = response.getWriter()) {
                writer.write(gson.toJson(result));
            } catch (Exception e) {
                System.out.println("exception: " + e.toString());
            }
		} else if (clientReq.get("action").getAsString().equals("sendMail")) {
		    int customerId = clientReq.get("customerServiseId").getAsInt();
		    String customerReply = clientReq.get("customerReply").getAsString();
		    if (customerReply.trim().length() == 0) {
		        customerReply = "感謝您的回覆，我們會盡快幫您處理，請耐心等候\n謝謝。";
		    }
		    
		    Customer_bean customer = customer_SERVICE_service.selectById(customerId);
		    if (customer != null) {
		     // 寄信
	            Properties props = System.getProperties();
	            props.setProperty("mail.transport.protocol", "smtp");     
	            props.setProperty("mail.host", "smtp.gmail.com");
	            props.put("mail.smtp.auth", "true");
	            props.put("mail.smtp.port", "465");
//	            props.put("mail.debug", "true");
	            props.put("mail.smtp.socketFactory.port", "465");  
	            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	            props.put("mail.smtp.socketFactory.fallback", "false");
	            
	            Session session = Session.getDefaultInstance(props, new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {  
	                    return new PasswordAuthentication("heeroyuy31329@gmail.com", "oomqoqrzxxbhcysu");
	                }
	            });
	            
	            try {
	                MimeMessage message = new MimeMessage(session);
	                message.setFrom(new InternetAddress("heeroyuy31329@gmail.com"));
	                message.setRecipient(Message.RecipientType.TO, new InternetAddress(customer.getMail()));
	                message.setSubject(customer.getNickName() + " 感謝您聯繫客服");
	                message.setContent(customerReply, "text/plain;charset=UTF-8");
	                
	                Transport.send(message);
	                
	                // 信件發送完後刪除該資料
	                JsonObject result = new JsonObject();
	                result.addProperty("result_code", customer_SERVICE_service.deleteById(customerId) > 0 ? 1 : 0);
	                
	                try (PrintWriter writer = response.getWriter()) {
	                    writer.write(gson.toJson(result));
	                } catch (Exception e) {
	                    System.out.println("exception: " + e.toString());
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
		    }
		}
	}
}
