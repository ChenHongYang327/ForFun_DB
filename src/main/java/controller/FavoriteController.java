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

import member.bean.Favorite;
import member.bean.Publish;
import service.CityService;
import service.FavoriteService;
import service.PublishService;

/**
 * Servlet implementation class FavoriteController
 */
@WebServlet("/favoriteController")
public class FavoriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		FavoriteService favoriteService = new FavoriteService();
		PublishService publishService = new PublishService();
		CityService cityService = new CityService();
		JsonObject clientreq = new Gson().fromJson(request.getReader(), JsonObject.class);
		if (clientreq.get("action").getAsString().equals("getFavorite")) {
			int memberId = clientreq.get("memberId").getAsInt();
			List<Favorite> favorites = favoriteService.selectByMemberId(memberId);
			List<Publish> publishs = new ArrayList<>();
			List<String> cityNames = new ArrayList<>();
			for (Favorite favorite : favorites) {
				publishs.add(publishService.selectById(favorite.getPublishId()));
			}
			for (Publish publish : publishs) {
				cityNames.add(cityService.selectNameById(publish.getCityId()));
			}
			try (PrintWriter writer = response.getWriter()) {
				JsonObject resp = new JsonObject();
				resp.addProperty("publishList", new Gson().toJson(publishs));
				resp.addProperty("cityName", new Gson().toJson(cityNames));
				resp.addProperty("favoriteId", new Gson().toJson(favorites));
				writer.print(resp.toString());
//				System.out.println("伺服器的回應:" + resp.toString());
			}

		} else if (clientreq.get("action").getAsString().equals("remove")) {
			int favoriteId = clientreq.get("removeId").getAsInt();
			JsonObject resp = new JsonObject();
			try (PrintWriter writer = response.getWriter()) {
				if (favoriteService.deleteById(favoriteId) == 1) {
					resp.addProperty("pass", true);
				} else {
					resp.addProperty("pass", false);
				}
				writer.print(resp.toString());
			}
		}

	}

}
