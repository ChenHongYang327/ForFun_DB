package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Agreement;
import member.bean.Publish;
import service.AgreementService;
import service.AreaService;
import service.CityService;
import service.OrderService;
import service.PublishService;

@WebServlet("/Agreement")
public class AgreementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private StringBuilder jsonIn = new StringBuilder();
	private JsonObject jsonWri = new JsonObject();
	private int resultcode = -1; // 判斷碼
	private OrderService orderService = new OrderService();
	private PublishService publishService = new PublishService();
	private CityService cityService = new CityService();
	private AreaService areaService = new AreaService();
	private AgreementService agreementService = new AgreementService();


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
			resultcode = jsonObj.get("TYPECODE").getAsInt();
		} catch (Exception e) {
			resultcode = -1;
		}

		switch (resultcode) {
		case 1: // 拿地址 訂單id->刊登單id->地址
			int orderId = jsonObj.get("ORDER").getAsInt();
			int publishId = orderService.selectPublishByID(orderId);
			Publish publish = publishService.selectById(publishId);
			int cityId = publish.getCityId();
			int areaId = publish.getAreaId();
			
			String wholeAdress = areaService.selectNameById(areaId) + 
					cityService.selectNameById(cityId) + publish.getAddress();
			
			jsonWri.addProperty("ADDRESS", wholeAdress);
			jsonWri.addProperty("RESULT", 200);

			break;

		case 2: // 預覽模式 , 抓值
			int agreementId = jsonObj.get("AGREEMENTID").getAsInt();
			Agreement agreement = agreementService.sellectById(agreementId);
			
			jsonWri.addProperty("AGREEMENT", agreement.toString());
			jsonObj.addProperty("RESULT", 200);
			break;

		case 3: // 房東新增 & 改狀態

			break;

		case 4: // 房客存簽名path & 改狀態

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
