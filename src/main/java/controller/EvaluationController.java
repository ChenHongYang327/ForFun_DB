package controller;

import java.io.BufferedReader;
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

import member.bean.Order;
import member.bean.PersonEvaluation;
import service.OrderService;
import service.PersonEvaluationService;
import service.PublishService;

@WebServlet("/Evaluation")
public class EvaluationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	private OrderService orderService = new OrderService();
	private PublishService publishService = new PublishService();
	private PersonEvaluationService personEvaluationService = new PersonEvaluationService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonObject jsonWri = new JsonObject();
		StringBuilder jsonIn = new StringBuilder();
		int typecode = -1;
		
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
			typecode = jsonObj.get("TYPECODE").getAsInt(); // 判斷碼
		} catch (Exception e) {
			typecode = -1;
		}
		

		switch (typecode) {
		case 0:
			// Tenant Event
			int strars_P = jsonObj.get("STARS_P").getAsInt();
			String msg_P = jsonObj.get("MSG_P").getAsString();
			int strars_H = jsonObj.get("STARS_H").getAsInt();
			String msg_H = jsonObj.get("MSG_H").getAsString();
			int orderid = jsonObj.get("ORDERID").getAsInt();
			int signInId = jsonObj.get("SIGNINID").getAsInt(); // 房客ID

			int publishId = orderService.selectPublishByID(orderid); // 用刊登單ID 找 房東ID
			int ownerId = publishService.selectOwnerIdByID(publishId); // 房東ID

			// save to PERSON_EVALUATION table
			PersonEvaluation personEvaluation = new PersonEvaluation();
			personEvaluation.setOrderId(orderid);
			personEvaluation.setCommented(ownerId);
			personEvaluation.setCommentedBy(signInId);
			personEvaluation.setPersonStar(strars_P);
			personEvaluation.setPersonComment(msg_P);

			int sqlResultP = personEvaluationService.insert(personEvaluation);

			// save to ORDER table
			Order order = new Order();
			order.setPublishStar(strars_H);
			order.setPublishComment(msg_H);

			int sqlResultH = orderService.insertEvaluation(order, orderid);

			if (sqlResultP <= 0 && sqlResultH <= 0) {
				jsonWri.addProperty("RESULT", -1);
			} else {
				jsonWri.addProperty("RESULT", 200);
			}
			break;
			
		case 1:
			// HouseOwner Event
			int strars_P_ = jsonObj.get("STARS_P").getAsInt();
			String msg_P_ = jsonObj.get("MSG_P").getAsString();
			int orderid_ = jsonObj.get("ORDERID").getAsInt();
			int signInId_ = jsonObj.get("SIGNINID").getAsInt(); // 房東ID
			int tenantId_ = orderService.selectTenantByID(orderid_); // 房客ID

			// save to PERSON_EVALUATION table
			PersonEvaluation personEvaluation_ = new PersonEvaluation();
			personEvaluation_.setOrderId(orderid_);
			personEvaluation_.setCommented(tenantId_);
			personEvaluation_.setCommentedBy(signInId_);
			personEvaluation_.setPersonStar(strars_P_);
			personEvaluation_.setPersonComment(msg_P_);

			int sqlResult = personEvaluationService.insert(personEvaluation_);

			if (sqlResult <= 0) {
				jsonWri.addProperty("RESULT", -1);
			} else {
				jsonWri.addProperty("RESULT", 200);
			}
			break;
			
		case 2:
			int signinId_2 = jsonObj.get("SIGNINID").getAsInt();
			int orderId_2 = jsonObj.get("ORDERID").getAsInt();

			// 用評價人來判斷是否有評價過!但有個BUG 房客如果只評價房子的話，會無限新增！
			boolean isExist = personEvaluationService.isEvaluationExist(signinId_2,orderId_2);
			if (isExist) {
				jsonWri.addProperty("EXIST", true);
			} else {
				jsonWri.addProperty("EXIST", false);
			}
			jsonWri.addProperty("RESULT", 200);
			break;

		default:
			jsonWri.addProperty("RESULT", -1);
			break;
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
