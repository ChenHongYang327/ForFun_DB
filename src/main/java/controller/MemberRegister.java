package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.bean.Member;
import service.MemberService;

@WebServlet("/register")
public class MemberRegister extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		MemberService memberService=new MemberService();
		Gson gson=new Gson();
		JsonObject clientReq = gson.fromJson(request.getReader(), JsonObject.class);
//		System.out.println("客戶端的請求:" + clientReq);
		if(clientReq.get("action").getAsString().equals("register")) {
			Member member=new Member();
			member=new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(clientReq.get("member").getAsString(), Member.class);
			JsonObject respJson=new JsonObject(); //伺服器回覆
			if(member.getCitizen()==null) {
				System.out.println("申請房客");
				member.setRole(1);//房客
				member.setType(1);//帳號狀態權限
				if(memberService.insert(member)==1) {
					respJson.addProperty("status", true);
				}
				else {
					respJson.addProperty("status", false);
				}
			}
			else {
				System.out.println("申請房東");
//				member.setRole(2);//房東權限
				member.setRole(1);//房客權限
				member.setType(1);//帳號狀態權限
				if(memberService.insert(member)==1) {
					respJson.addProperty("status", true);
				}
				else {
					respJson.addProperty("status", false);
				}
			}
//			System.out.println("伺服器的回應:" + respJson);
			PrintWriter printWriter=response.getWriter();
			printWriter.print(respJson.toString()); 
			printWriter.flush();
			printWriter.close();
		}
		else if(clientReq.get("action").getAsString().equals("checkPhone")) {
			Member member=new Member();
			PrintWriter printWriter=response.getWriter();
			member=new Gson().fromJson(clientReq.get("member").getAsString(), Member.class);
//			System.out.println("客戶端的請求:" + new Gson().toJson(member));
			JsonObject respJson=new JsonObject(); //伺服器回覆
			for(int phone:memberService.selectAllPhone()) {
				if(phone==member.getPhone()){
					respJson.addProperty("pass", false);
					System.out.println("伺服器的回應:" + respJson);
					printWriter.print(respJson.toString()); 
					printWriter.flush();
					printWriter.close();
					return;
				}

			}
			respJson.addProperty("pass", true);
//			System.out.println("伺服器的回應:" + respJson);
			response.getWriter().print(respJson.toString());
			printWriter.flush();
			printWriter.close();
		} 
	}

}
