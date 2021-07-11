package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Customer_bean;
import member.bean.Member;
import service.CUSTOMER_SERVICE_service;
import service.MemberService;

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
		}
	}

}
