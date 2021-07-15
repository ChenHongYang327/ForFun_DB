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

import member.bean.Publish;
import service.CityService;
import service.PublishService;

/**
 * Servlet implementation class PublishManage
 */
@WebServlet("/publishListController")
public class PublishListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		JsonObject clientReq = new Gson().fromJson(request.getReader(), JsonObject.class);
		PublishService publishService = new PublishService();
		CityService cityService = new CityService();
		try (PrintWriter pw = response.getWriter()) {
			if (clientReq.get("action").getAsString().equals("getPublishList")) {
				int memberId = clientReq.get("memberId").getAsInt();
				List<Publish> publishes = publishService.selectByOwnerId(memberId);
				List<String> cityNames = new ArrayList<String>();
				for (Publish publish : publishes) {
					cityNames.add(cityService.selectNameById(publish.getCityId()));
				}
				JsonObject resp = new JsonObject();
				resp.addProperty("publishes", new Gson().toJson(publishes));
				resp.addProperty("cityNames", new Gson().toJson(cityNames));
				pw.print(resp.toString());
//				System.out.println(resp.toString());

			} else if (clientReq.get("action").getAsString().equals("pubishDelete")) {
				int publishId = clientReq.get("publishId").getAsInt();
				JsonObject resp = new JsonObject();
				if (publishService.deleteById(publishId) == 1) {
					resp.addProperty("result", true);
				} else {
					resp.addProperty("result", false);
				}
				pw.print(resp.toString());
			} else if (clientReq.get("action").getAsString().equals("updateStatus")) {
				int publishId = clientReq.get("publishId").getAsInt();
				int status = -1;
				if (clientReq.get("status").getAsString().equals("close")) {
					status = 2;
				} 
				else if (clientReq.get("status").getAsString().equals("open")) {
					status = 3;
				}
				if (status != -1) {
					JsonObject resp = new JsonObject();
					if (publishService.updateStatus(status, publishId) == 1) {
						resp.addProperty("result", true);
					} else {
						resp.addProperty("result", false);
					}
					pw.print(resp.toString());
				}
			}
		}
	}

}
