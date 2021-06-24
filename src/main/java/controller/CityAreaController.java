package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import service.AreaService;
import service.CityService;

@WebServlet("/getCityAreaData")
public class CityAreaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();
        CityService cityService = new CityService();
        AreaService areaService = new AreaService();
        
        try (
            PrintWriter writer = response.getWriter();
        ) {
            JsonObject object = new JsonObject();
            object.addProperty("city", gson.toJson(cityService.selectAll()));
            object.addProperty("area", gson.toJson(areaService.selectAll()));
            
            writer.write(gson.toJson(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
