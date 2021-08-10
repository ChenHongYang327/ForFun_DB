package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.bean.Agreement;
import member.bean.Comment;
import member.bean.Member;
import member.bean.Order;
import member.bean.Publish;
import member.bean.ReportChatMsg;
import member.bean.Report_page_bean;
import service.AgreementService;
import service.AreaService;
import service.CityService;
import service.CommentService;
import service.MemberService;
import service.NotificationService;
import service.OrderService;
import service.PublishService;
import service.Report_page_service;

@WebServlet("/ReportChatMsg")
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private Gson gsonDate = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
	private Gson gson = new Gson();
	private Report_page_service report_page_service = new Report_page_service();
	private MemberService memberService = new MemberService();
	private CommentService commentService = new CommentService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 收取client端資料
		request.setCharacterEncoding("UTF-8");
		// 回傳前端
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		StringBuilder jsonIn = new StringBuilder();

		try (BufferedReader br = request.getReader();) {

			String line = null;
			while ((line = br.readLine()) != null) {
				jsonIn.append(line);
			}
			System.out.println("input: " + jsonIn.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拿值
		JsonObject jsonObj = gson.fromJson(jsonIn.toString(), JsonObject.class);

		int resultcode = -1; // 判斷碼
		
		try {
			resultcode = jsonObj.get("RESULTCODE").getAsInt();
		} catch (Exception e) {
			resultcode = -1;
		}

		JsonObject jsonWri = new JsonObject();
		switch (resultcode) {
		case 1: // ios 檢舉留(chatroom）拿資料用的
			
			List<ReportChatMsg> reportChatMsgs = new ArrayList<>();
			
			List<Report_page_bean> reports = new ArrayList<>();
			reports = report_page_service.selectAllChatMsg();
			for(int i = 0 ; i<reports.size() ; i++) {
				ReportChatMsg reportChatMsg = new ReportChatMsg();
				//拿值
				Member member = memberService.selectById(reports.get(i).getReported_id());
				Comment comment = commentService.selectById(reports.get(i).getChatroom_id());
				
				reportChatMsg.setReport_id(reports.get(i).getReport_id());
				reportChatMsg.setWhistleblower_id(reports.get(i).getWhistleblower_id());
				reportChatMsg.setMember(member);
				reportChatMsg.setType(reports.get(i).getType());
				reportChatMsg.setMessage(reports.get(i).getMessage());
				reportChatMsg.setReport_class(reports.get(i).getReport_class());
				reportChatMsg.setComment(comment);
				reportChatMsg.setCreateTime(reports.get(i).getCreateTime());
				
				reportChatMsgs.add(reportChatMsg);
			}

			jsonWri.addProperty("REPORTCHATMSG", gson.toJson(reportChatMsgs));
			jsonWri.addProperty("RESULT", "200");
		
			break;

		default:
			jsonWri.addProperty("RESULT", -1);
			break;
		}
		
		try (PrintWriter pw = response.getWriter();) {
			pw.println(jsonWri.toString());
			System.out.println("output: " + jsonWri.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
