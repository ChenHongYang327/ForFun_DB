package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Customer_bean;
import member.bean.Member;
import member.bean.Report_page_bean;
import service.CUSTOMER_SERVICE_service;
import service.MemberService;
import service.Report_page_service;

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/REPORT_Servlet")
public class Report_page_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		JsonObject clientReq = new Gson().fromJson(request.getReader(), JsonObject.class);
		System.out.println("Input" + clientReq.toString());
		
		JsonObject respJson = new JsonObject(); // 伺服器回覆
		Report_page_service report_page_Service = new Report_page_service(); 
		Report_page_bean report_page_bean = new Report_page_bean();
		
		String requestCode = clientReq.get("action").getAsString();
		switch (requestCode) {
		case "Post":
			Integer whistleBlowerIdPost = clientReq.get("WHISTLEBLOWER_ID").getAsInt();	
			Integer reportedIdPost = clientReq.get("REPORTED_ID").getAsInt();		
			String detailedStatusPost = clientReq.get("MESSAGE").getAsString();	
			Integer spinnerPost = clientReq.get("REPORT_CLASS").getAsInt();	
			Integer postIdPost = clientReq.get("POST_ID").getAsInt();
			Integer itemPost = clientReq.get("ITEM").getAsInt();	
			

			report_page_bean.setWhistleblower_id(whistleBlowerIdPost);
			report_page_bean.setReported_id(reportedIdPost);
			report_page_bean.setMessage(detailedStatusPost);
			report_page_bean.setReport_class(spinnerPost);
			report_page_bean.setPost_id(postIdPost);
			report_page_bean.setItem(itemPost);
			
			if (report_page_Service.insertPost(report_page_bean) >= 0) {
	
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
			break;
			
			
			
		case "Charoom":
			Integer whistleBlowerIdCharoom = clientReq.get("WHISTLEBLOWER_ID").getAsInt();	
			Integer reportedIdCharoom = clientReq.get("REPORTED_ID").getAsInt();		
			String detailedStatusCharoom = clientReq.get("MESSAGE").getAsString();	
			Integer spinnerCharoom = clientReq.get("REPORT_CLASS").getAsInt();	
			Integer chatroomIdCharoom = clientReq.get("CHATROOM_ID").getAsInt();
			
			report_page_bean.setWhistleblower_id(whistleBlowerIdCharoom);
			report_page_bean.setReported_id(reportedIdCharoom);
			report_page_bean.setMessage(detailedStatusCharoom);
			report_page_bean.setReport_class(spinnerCharoom);
			report_page_bean.setChatroom_id(chatroomIdCharoom);
			
			if (report_page_Service.insertCharoom(report_page_bean) >= 0) {
				
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
			
			break;
			
			

		case "User":
			
			Integer whistleBlowerIdUser = clientReq.get("WHISTLEBLOWER_ID").getAsInt();	
			Integer reportedIdUser = clientReq.get("REPORTED_ID").getAsInt();		
			String detailedStatusUser = clientReq.get("MESSAGE").getAsString();	
			Integer spinnerUser = clientReq.get("REPORT_CLASS").getAsInt();	
			Integer itemUser = clientReq.get("ITEM").getAsInt();
			
			report_page_bean.setWhistleblower_id(whistleBlowerIdUser);
			report_page_bean.setReported_id(reportedIdUser);
			report_page_bean.setMessage(detailedStatusUser);
			report_page_bean.setReport_class(spinnerUser);
			report_page_bean.setItem(itemUser);
			
			if (report_page_Service.insertUser(report_page_bean) >= 0) {
				
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
			
			break;
			
			
			
		default:
			break;
		}


	}

}
