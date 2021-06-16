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


/**
 * Servlet implementation class meberCenterPersonalInformation
 */
@WebServlet("/meberCenterPersonalInformation")
public class meberCenterPersonalInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		MemberDao memberDao=new MemberDaoImpl();
		Gson gson=new Gson();
		JsonObject clientReq = gson.fromJson(request.getReader(), JsonObject.class);
		System.out.println("客戶端的請求:" + clientReq);
		if(clientReq.get("action").getAsString().equals("getMember")) {
			int member_id=clientReq.get("member_id").getAsInt();
			Member member=new Member();
			member=memberDao.selectById(member_id);
			String respJson = gson.toJson(member);
			System.out.println("伺服器的回應:" + respJson);
			response.getWriter().print(respJson); 
		}
		else if(clientReq.get("action").getAsString().equals("updateMember")){
			String member = clientReq.get("member").getAsString();
			Member updatemMember = new Gson().fromJson(member, Member.class);
			if(memberDao.update(updatemMember)==1) {
				response.getWriter().write("true");
			} else {
				response.getWriter().write("false");
			}
			
		}
	}

}
