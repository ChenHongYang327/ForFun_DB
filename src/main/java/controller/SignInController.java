package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
			JsonObject respJson = new JsonObject(); // 伺服器回覆
			int inputPhone = clientReq.get("phone").getAsInt();
			try (PrintWriter writer = response.getWriter()) {
				for (Member member : memberService.selectAll()) {
					if (inputPhone == member.getPhone()) {
						if (member.getRole() != 0 && member.getType() != 0) {
							respJson.addProperty("pass", 0);
							respJson.addProperty("imformation", new Gson().toJson(member));
							writer.print(respJson);
						} else if (member.getType() == 0) {
							respJson.addProperty("pass", 1);
							writer.print(respJson);
						}
						return;
					}
				}
				respJson.addProperty("pass", 2);
				writer.print(respJson);

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		} else if (clientReq.get("action").getAsString().equals("rootSingIn")) {
			JsonObject respJson = new JsonObject(); // 伺服器回覆
			int inputPhone = clientReq.get("phone").getAsInt();
			try (PrintWriter writer = response.getWriter()) {
				for (Member member : memberService.selectAll()) {
					if (inputPhone == member.getPhone()) {
						if (member.getRole() == 0) {
							respJson.addProperty("pass", 0);
							writer.print(respJson);
						}
						// 非管理者權限
						else if (member.getRole() == 1 || member.getRole() == 2) {
							respJson.addProperty("pass", 1);
							writer.print(respJson);
						}
						return;
					}
				}
				// 非已註冊會員
				respJson.addProperty("pass", 2);
				writer.print(respJson);

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		} else if (clientReq.get("action").getAsString().equals("checkType")) {
			int memberId = clientReq.get("memberId").getAsInt();
			try (PrintWriter writer = response.getWriter()) {
				if (memberService.selectById(memberId).getType() == 0) {
					memberService.clearTokenById(memberId);
					writer.print(0);
				} else {
					writer.print(1);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		} else if (clientReq.get("action").getAsString().equals("updateToken")) {
			Member member = new Gson().fromJson(clientReq.get("member").getAsString(), Member.class);
			JsonObject respJson = new JsonObject();
			if (memberService.updateToken(member) == 1) {
				respJson.addProperty("status", true);
			} else {
				respJson.addProperty("status", false);
			}

		} else if (clientReq.get("action").getAsString().equals("clearToken")) {
			int memberid = clientReq.get("memberId").getAsInt();
			JsonObject respJson = new JsonObject();
			if (memberService.clearTokenById(memberid) == 1) {
				respJson.addProperty("status", true);
			} else {
				respJson.addProperty("status", false);
			}

		} else if (clientReq.get("action").getAsString().equals("checkRole")) {
			String phone = clientReq.get("phone").getAsString();
			List<Member> members = memberService.selectAll();
			int role = -1;
			for (Member member : members) {
				if (member.getPhone() == Integer.parseInt(phone)) {
					role = member.getRole();
					break;
				}
			}
			try (PrintWriter writer = response.getWriter()) {
				writer.print(role);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (clientReq.get("action").getAsString().equals("updateInfo")) {
			int memberId = clientReq.get("memberId").getAsInt();
			Member member = memberService.selectById(memberId);
			try (PrintWriter writer = response.getWriter()) {
				writer.print(new Gson().toJson(member));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
