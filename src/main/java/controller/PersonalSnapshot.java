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

import member.bean.Member;
import member.bean.PersonEvaluation;
import service.OrderService;
import service.PersonEvaluationService;

 
@WebServlet("/personalSnapshot")
public class PersonalSnapshot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PersonEvaluationService pEvaluationService=new PersonEvaluationService();
		OrderService orderService=new OrderService();
		JsonObject clientReq = new Gson().fromJson(request.getReader(), JsonObject.class);
//		System.out.println("客戶端的請求:" + clientReq);
		//取得評論者帳號資料
		if(clientReq.get("action").getAsString().equals("getCommenter")) {
			int commenterID=clientReq.get("commenterID").getAsInt();
			Member commenter=pEvaluationService.selectMemberByCommentId(commenterID);
			String resp=new Gson().toJson(commenter,Member.class);
			try (PrintWriter writer=response.getWriter();)
			{
				writer.print(resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//取得此 受評論者 的所有評論資料
		else if(clientReq.get("action").getAsString().equals("getAllEvaluation")) {
			int commentedID=clientReq.get("commentedID").getAsInt();//受評論者
			List<PersonEvaluation> personEvaluations=pEvaluationService.selectByCommented(commentedID);//受評論者的所有資料
			List<PersonEvaluation> tenantstatus=new ArrayList<>();//房客身分
			List<PersonEvaluation> landlordstatus=new ArrayList<>();//房東身分
			//將評論資料分類資料
			for(PersonEvaluation personEvaluation:personEvaluations) {
				int ordetTenantID =orderService.selectTenantByIDForPersonEvaluation(personEvaluation.getOrderId());//取得此訂單的房客用戶ID
				if(ordetTenantID==commentedID) {
					tenantstatus.add(personEvaluation);//房客身分
				}
				else if(ordetTenantID!=commentedID){
					landlordstatus.add(personEvaluation);//房東身分
				}
			}
			try (PrintWriter writer=response.getWriter()){
				if(clientReq.get("status").getAsString().equals("tenantStatus")) {
					String resp=new Gson().toJson(tenantstatus);
//					System.out.println("伺服器的回應:" + resp);
					writer.print(resp);
				}
				else if(clientReq.get("status").getAsString().equals("landlordStatus")) {
					String resp=new Gson().toJson(landlordstatus);
//					System.out.println("伺服器的回應:" + resp);
					writer.print(resp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		}
		else if(clientReq.get("action").getAsString().equals("personalSnapshot")) {
			int memberID=clientReq.get("memberID").getAsInt();
			Member member=pEvaluationService.selectMemberByCommentId(memberID); //共用查尋會員
			String address=member.getAddress().substring(0,3);//第三個字元後不取
			member.setAddress(address);
			String resp=new Gson().toJson(member,Member.class);
			try (PrintWriter writer=response.getWriter())
			{
				writer.print(resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
