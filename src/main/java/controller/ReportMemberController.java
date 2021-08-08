package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.bean.Member;
import member.bean.Report_page_bean;
import service.MemberService;
import service.Report_page_service;

@WebServlet("/reportMemberController")
public class ReportMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Report_page_service reportService=new Report_page_service();
		MemberService memberService=new MemberService();
		try(BufferedReader reader=request.getReader();
			PrintWriter writer=response.getWriter()) {
			JsonObject req =new Gson().fromJson(reader, JsonObject.class);
			if(req.get("action").getAsString().equals("getReportMember")) {
				JsonObject resp=new JsonObject();
				List<Report_page_bean> reports=reportService.selectReportMember();
				List<Member> members=new ArrayList<Member>();
				if(reports.size()>0) {
					for(Report_page_bean report:reports) {
							//被檢舉者
							members.add(memberService.selectById(report.getReported_id()));
					}
				}
				resp.addProperty("reports",gson.toJson(reports));
				resp.addProperty("reporteds",gson.toJson(members));
				writer.write(resp.toString());				
			}
			else if(req.get("action").getAsString().equals("selectMember")) {
				int memberId=req.get("memberId").getAsInt();
				writer.write(gson.toJson(memberService.selectById(memberId)));
			}
			else if(req.get("action").getAsString().equals("updateReport")) {
				int reportId=req.get("reportId").getAsInt();
				JsonObject resp=new JsonObject();
				if(reportService.deleteById(reportId)>0) {
					resp.addProperty("result", 1);
					writer.write(resp.toString());
					return;
				}
				resp.addProperty("result", 0);			
				writer.write(resp.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		
	}

}
