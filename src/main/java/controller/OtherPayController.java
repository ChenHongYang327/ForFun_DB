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

import member.bean.OtherPay;
import service.OtherPayService;

@WebServlet("/OtherPay")
public class OtherPayController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private StringBuilder jsonIn;
	private OtherPayService otherPayService = new OtherPayService();
	private int resultcode, otherpayID;

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
		otherpayID = jsonObject_Client.get("OTHERPAYID").getAsInt();
		resultcode = jsonObject_Client.get("RESULTCODE").getAsInt();

		JsonObject jsonWri = new JsonObject();
		if (resultcode == 0) {
			OtherPay otherPay = otherPayService.selectById(otherpayID);

			jsonWri.addProperty("MONEY", otherPay.getOtherpayMoney());
			jsonWri.addProperty("NOTEINFO", otherPay.getOtherpayNote());
			jsonWri.addProperty("IMGPATH", otherPay.getSuggestImg());

		} else {

			// 如果前端新增成功，狀態碼要改成已付款
			otherPayService.changeOtherpayStatus(otherpayID, resultcode);
			jsonWri.addProperty("RESULT", 200);
		}

		try (PrintWriter pw = response.getWriter();) {
			pw.println(jsonWri);

			System.out.println("output: " + jsonWri.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
