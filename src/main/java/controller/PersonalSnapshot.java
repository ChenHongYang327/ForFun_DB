package controller;

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
import com.google.gson.JsonObject;

import dao.PersonEvaluationDao;
import dao.impl.PersonEvaluationDaoImpl;
import member.bean.Member;
import member.bean.PersonEvaluation;
import service.PersonEvaluationService;

 
@WebServlet("/personalSnapshot")
public class PersonalSnapshot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PersonEvaluationService pEvaluationService=new PersonEvaluationService();
		Gson gson=new Gson();
		JsonObject clientReq = gson.fromJson(request.getReader(), JsonObject.class);
		System.out.println("客戶端的請求:" + clientReq);
		//取得評論者帳號資料
		if(clientReq.get("action").getAsString().equals("getCommenter")) {
			int commenterID=clientReq.get("commenterID").getAsInt();
			Member commenter=pEvaluationService.selectMemberByCommentId(commenterID);
			String resp=new Gson().toJson(commenter,Member.class);
			PrintWriter writer=response.getWriter();
			writer.print(resp);
			writer.flush();
		
		}
		//取得此受評論者的所有評論資料
		else if(clientReq.get("action").getAsString().equals("TenantStatusEvaluation")) {
			int commentedID=clientReq.get("commentedID").getAsInt();//受評論者
			List<PersonEvaluation> personEvaluations=(List<PersonEvaluation>) pEvaluationService.selectMemberByCommentId(commentedID);;
			String resp = new Gson().toJson(personEvaluations); 
			PrintWriter writer=response.getWriter();
			writer.print(resp);
			writer.flush();
			
		}
	}

}
