package controller;

import java.io.BufferedReader;
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
		MemberService memberService = new MemberService();
		try (BufferedReader reader = request.getReader(); PrintWriter pw = response.getWriter()) {
			req = gson.fromJson(reader, JsonObject.class);
			System.out.println("客戶端的請求:" + req);
			if (req.get("action").getAsString().equals("getAllMember")) {
				pw.print(gson.toJson(memberService.selectAll()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
