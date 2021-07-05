package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import service.PublishService;

@WebServlet("/getPublishData")
public class PublishController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();
        
        try (
            BufferedReader reader = request.getReader();
            PrintWriter writer = response.getWriter();
        ) {
            PublishService publishService = new PublishService();
            
            JsonObject object = gson.fromJson(reader, JsonObject.class);
            String action = object.get("action").getAsString();
            
            if("getAll".equals(action)) {
                // 取得全部刊登資料
                JsonObject result = new JsonObject();
                result.addProperty("publishList", gson.toJson(publishService.selectAll()));
                writer.write(gson.toJson(result));
            } else if ("getBySearch".equals(action)) {
                // 根據條件取得刊登資料
                Type paramMap = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> map = gson.fromJson(object.get("searchParam").getAsString(), paramMap);
                
                JsonObject result = new JsonObject();
                result.addProperty("publishList", gson.toJson(publishService.selectAllByParam(map)));
                writer.write(gson.toJson(result));
            } else if ("getByPublishId".equals(action)) {
                // 根據刊登ID取得資料
                int publishId = object.get("publishId").getAsInt();
                
                JsonObject result = new JsonObject();
                result.addProperty("publish", gson.toJson(publishService.selectById(publishId)));
                writer.write(gson.toJson(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
