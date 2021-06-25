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

import member.bean.Member;
import service.MemberService;

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/signInController")
public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		JsonObject clientReq = new Gson().fromJson(request.getReader(), JsonObject.class);
		MemberService memberService = new MemberService();
//		System.out.println("客戶端的請求:" + clientReq);
		if (clientReq.get("action").getAsString().equals("singIn")) {
			JsonObject respJson=new JsonObject(); //伺服器回覆
			int inputPhone = clientReq.get("phone").getAsInt();
			try (PrintWriter writer = response.getWriter()) {
			for (Member member : memberService.selectAll()) {
					if (inputPhone==member.getPhone()) {
						respJson.addProperty("pass", true);
						respJson.addProperty("imformation", new Gson().toJson(member));
						writer.print(respJson);
						return;
					}
				}
			respJson.addProperty("pass", false);
			writer.print(respJson);
			}
		}
		else if(clientReq.get("action").getAsString().equals("updateToken")) {
			Member member=new Gson().fromJson(clientReq.get("member").getAsString(), Member.class);
			JsonObject respJson=new JsonObject();
			if(memberService.updateToken(member)==1) {
				respJson.addProperty("status", true);
			}
			else {
				respJson.addProperty("status", false);
			}
			
		}
		else if(clientReq.get("action").getAsString().equals("clearToken")) {
			int memberid=clientReq.get("memberId").getAsInt();
			JsonObject respJson=new JsonObject();
			if(memberService.clearTokenById(memberid)==1) {
				respJson.addProperty("status", true);
			}
			else {
				respJson.addProperty("status", false);
			}
			
		}
	}

}
