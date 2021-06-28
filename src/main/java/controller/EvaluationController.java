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
import com.google.gson.JsonObject;

import service.OrderService;
import service.PublishService;

@WebServlet("/Evaluation")
public class EvaluationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private StringBuilder jsonIn = new StringBuilder();
	private int typecode;
	private JsonObject jsonWri = new JsonObject();
	private OrderService orderService = new OrderService();
	private PublishService publishService = new PublishService();
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 收取client端資料
		request.setCharacterEncoding("UTF-8");

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

		try {
			typecode = jsonObj.get("TYPECODE").getAsInt(); //判斷碼
		} catch (Exception e) {
			typecode = -1;
		}

		if (typecode == 0) {
			// Tenant Event
			int srars_P = jsonObj.get("STARS_P").getAsInt();
			String msg_P = jsonObj.get("MSG_P").getAsString();
			int srars_H = jsonObj.get("STARS_H").getAsInt();
			String msg_H = jsonObj.get("MSG_H").getAsString();
			int orderid = jsonObj.get("ORDERID").getAsInt();
			int signInId = jsonObj.get("SIGNINID").getAsInt(); //房客ID

			int publishId = orderService.selectPublishByID(orderid); //用刊登單ID 找 房東ID
			int ownerId = publishService.selectOwnerIdByID(publishId); //房東ID
			
			// save to PERSON_EVALUATION table
			
			
			// save to ORDER table
			
			
			
			

		} else if (typecode == 1) {
			// HouseOwner Event
			int srars_P = jsonObj.get("STARS_P").getAsInt();
			String msg_P = jsonObj.get("MSG_P").getAsString();
			int orderid = jsonObj.get("ORDERID").getAsInt();
			int signInId = jsonObj.get("SIGNINID").getAsInt(); //房東ID
			int tenantId = orderService.selectTenantByID(orderid); //房客ID
	
			// save to PERSON_EVALUATION table
			
			
			
			
			
			
			
			
			

		} else {
			jsonWri.addProperty("RESULT", -1);
		}

		// 回傳前端
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (PrintWriter pw = response.getWriter();) {
			pw.println(jsonWri);
			System.out.println("output: " + jsonWri.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
