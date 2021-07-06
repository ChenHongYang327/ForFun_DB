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
import com.google.gson.GsonBuilder;
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

	private Gson gsonDate = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
	private Gson gson = new Gson();
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

		try {
			resultcode = jsonObj.get("RESULTCODE").getAsInt();
		} catch (Exception e) {
			resultcode = -1;
		}

		switch (resultcode) {
		case 1: // 拿地址 訂單id->刊登單id->地址
			
			int orderId = jsonObj.get("ORDERID").getAsInt();
			int publishId = orderService.selectPublishByID(orderId);
			Publish publish = publishService.selectById(publishId);
//			int cityId = publish.getCityId();
//			int areaId = publish.getAreaId();

			String wholeAdress = publish.getAddress();
			
			JsonObject jsonWri = new JsonObject();
			jsonWri.addProperty("ADDRESS", wholeAdress);
			jsonWri.addProperty("RESULT", 200);
			try (PrintWriter pw = response.getWriter();) {
				pw.println(jsonWri);
				System.out.println("output: " + jsonWri.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 2: // 預覽模式 , 抓值
			int agreementId = jsonObj.get("AGREEMENTID").getAsInt();
			Agreement agreement = agreementService.sellectById(agreementId);
			String agtStr = gson.toJson(agreement);

			JsonObject jsonWri2 = new JsonObject();
			jsonWri2.addProperty("AGREEMENT", agtStr);
			jsonWri2.addProperty("RESULT", 200);
			
			try (PrintWriter pw = response.getWriter();) {
				pw.println(jsonWri2);
				System.out.println("output: " + jsonWri2.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case 3: // 房東新增 & 改狀態 3

			String tmpAgmt = jsonObj.get("AGREEMENT").getAsString();
			System.out.println(tmpAgmt);
			Agreement agmtH = gson.fromJson(tmpAgmt, Agreement.class);
			// 新增
			agreementService.insertHouseOwner(agmtH);
			// 改狀態 -> 3
			// orderService.changeOrderStatus(agmtH.getOrderId(), 3);

			JsonObject jsonWri3 = new JsonObject();
			jsonWri3.addProperty("RESULT", 200);

			try (PrintWriter pw = response.getWriter();) {
				pw.println(jsonWri3);
				System.out.println("output: " + jsonWri3.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 4: // 房客存簽名path & 改狀態 4
			String imgPath_T = jsonObj.get("IMGPATH_T").getAsString();
			int agrmtId = jsonObj.get("AGREEMENTID").getAsInt();

			agreementService.updateTenant(imgPath_T, agrmtId);
			// 改狀態 -> 4
			orderService.changeOrderStatus(agrmtId, 4);

			JsonObject jsonWri4 = new JsonObject();
			jsonWri4.addProperty("RESULT", 200);

			try (PrintWriter pw = response.getWriter();) {
				pw.println(jsonWri4);
				System.out.println("output: " + jsonWri4.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			JsonObject jsonErr = new JsonObject();
			jsonErr.addProperty("RESULT", -1);
			
			try (PrintWriter pw = response.getWriter();) {
				pw.println(jsonErr);
				System.out.println("output: " + jsonErr.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		}
	}

}