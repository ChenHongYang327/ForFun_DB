package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.MemberDao;
import dao.MemberDaoImpl;
import member.bean.Member;

@WebServlet("/register")
public class memberRegister extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		MemberDao memberDao=new MemberDaoImpl();
		Gson gson=new Gson();
		JsonObject clientReq = gson.fromJson(request.getReader(), JsonObject.class);
		System.out.println("客戶端的請求:" + clientReq);
		if(clientReq.get("action").getAsString().equals("register")) {
			Member member=new Member();
			member=new Gson().fromJson(clientReq.get("member").getAsString(), Member.class);
			JsonObject respJson=new JsonObject(); //伺服器回覆
			if(member.getCitizen()==null) {
				System.out.println("申請房客");
				member.setRole(1);//房客
				member.setType(1);//帳號權限
				if(memberDao.insert(member)==1) {
					respJson.addProperty("status", true);
				}
				else {
					respJson.addProperty("status", false);
				}
			}
			else {
				System.out.println("申請房東");
				member.setRole(2);
				member.setType(1);
				if(memberDao.insert(member)==1) {
					respJson.addProperty("status", true);
				}
				else {
					respJson.addProperty("status", false);
				}
			}
			System.out.println("伺服器的回應:" + respJson);
			response.getWriter().print(respJson.toString()); 
		}
		else if(clientReq.get("action").getAsString().equals("checkPhone")) {
			Member member=new Member();
			member=new Gson().fromJson(clientReq.get("member").getAsString(), Member.class);
			System.out.println("客戶端的請求:" + new Gson().toJson(member));
			JsonObject respJson=new JsonObject(); //伺服器回覆
			for(int phone:memberDao.selectPhone()) {
				if(phone==member.getPhone()){
					respJson.addProperty("pass", false);
					response.getWriter().print(respJson.toString()); 
					System.out.println("伺服器的回應:" + respJson);
					return;
				}

			}
			respJson.addProperty("pass", true);
			response.getWriter().print(respJson.toString());
			System.out.println("伺服器的回應:" + respJson);
		} 
	}

}
