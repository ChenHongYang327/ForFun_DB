package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

@WebServlet("/adminMemberController")
public class AdminMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		JsonObject req;
		JsonObject resp=new JsonObject();
		MemberService memberService = new MemberService();
		try (BufferedReader reader = request.getReader(); PrintWriter pw = response.getWriter()) {
			req = gson.fromJson(reader, JsonObject.class);
//			System.out.println("客戶端的請求:" + req);
			if (req.get("action").getAsString().equals("getAllMember")) {
				List<Member>members=new ArrayList<Member>();
				for(Member member:memberService.selectAll()) {
					if(member.getRole()!=0) {
						members.add(member);
					}
				}	
				pw.print(gson.toJson(members));
			}
			else if(req.get("action").getAsString().equals("updateMember")) {
				Member member=gson.fromJson(req.get("member").getAsString(), Member.class);
				System.out.println("比對的電話:"+member.getPhone());
				Member selectMember=memberService.selectByPhone(member.getPhone());
				if(selectMember!=null) {
					//非同一人
					if(selectMember.getMemberId()!=member.getMemberId()) {
						resp.addProperty("pass", 2); //電話號碼已被使用
					}
					//Integer為物件使用==會比對記憶體位址需比較值須使用equal
					else if(selectMember.getRole()==member.getRole()&&selectMember.getType()==member.getType()&&selectMember.getPhone().equals(member.getPhone())) {
						resp.addProperty("pass", 3); //資料無變更
					}
					else if(memberService.adminuUpdate(member)>0) {
						resp.addProperty("pass", 0); //成功
					}
				}
				else {
					resp.addProperty("pass", 1); //更新失敗
				}
//				System.out.println("伺服器的回應:"+resp.toString());
				pw.print(resp.toString());
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
