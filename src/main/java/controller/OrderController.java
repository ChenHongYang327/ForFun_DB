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
import com.google.gson.JsonObject;

import member.bean.Publish;
import service.OrderService;
import service.PublishService;

@WebServlet("/Order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private StringBuilder jsonIn;
	private OrderService orderService = new OrderService();
	private PublishService publishService = new PublishService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 收取client端資料
		request.setCharacterEncoding("UTF-8");
		// 回傳前端
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (BufferedReader br = request.getReader();) {
			jsonIn = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				jsonIn.append(line);
			}

			System.out.println("input: " + jsonIn.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 拿值
		JsonObject jsonObject_Client = gson.fromJson(jsonIn.toString(), JsonObject.class);
		int orderID = jsonObject_Client.get("ORDER").getAsInt();
		int resultcode = jsonObject_Client.get("RESULTCODE").getAsInt();

		JsonObject jsonWri = new JsonObject();

		if (resultcode == 1) {
			// find publish id
			orderService.changeOrderStatus(orderID, resultcode);
			jsonWri.addProperty("RESULT", "SUCCESS");

		} else {
			// find publish id
			int publishId = orderService.selectPublishByID(orderID);
			// take Publish publish
			Publish publish = publishService.selectById(publishId);

			jsonWri.addProperty("MONEY", publish.getRent());
			jsonWri.addProperty("NOTEINFO", publish.getPublishInfo());
			jsonWri.addProperty("IMGPATH", publish.getTitleImg());

		}
		try (PrintWriter pw = response.getWriter();) {
			pw.println(jsonWri);
			System.out.println("output: " + jsonWri.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
