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
 * Servlet implementation class meberCenterPersonalInformation
 */
@WebServlet("/memberCenterPersonalInformation")
public class MemberCenterPersonalInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		MemberService memberService=new MemberService();
		Gson gson=new Gson();
		JsonObject clientReq = gson.fromJson(request.getReader(), JsonObject.class);
//		System.out.println("客戶端的請求:" + clientReq);
		if(clientReq.get("action").getAsString().equals("getMember")) {
			int member_id=clientReq.get("member_id").getAsInt();
			Member member=new Member();
			member=memberService.selectById(member_id);
			String respJson = gson.toJson(member);
//			System.out.println("伺服器的回應:" + respJson);
			try(PrintWriter writer=response.getWriter()) {
				writer.print(respJson); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(clientReq.get("action").getAsString().equals("updateMember")){
			String member = clientReq.get("member").getAsString();
			Member updatemMember = new Gson().fromJson(member, Member.class);
			try(PrintWriter writer=response.getWriter()) {
				if(memberService.update(updatemMember)==1) {
					writer.write("true");
				} else {
					writer.write("false");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
