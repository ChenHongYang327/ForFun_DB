package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Publish;
import service.PublishService;

@WebServlet("/publishHouse")
public class PublishHouseController extends HttpServlet {
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
            
            if("getNewId".equals(action)) {
                JsonObject result = new JsonObject();
                result.addProperty("newId", publishService.getNewId());
                writer.write(gson.toJson(result));
                
            } else if ("publishHouse".equals(action)) {
                Publish publish = gson.fromJson(object.get("publish").getAsString(), Publish.class);
            
                // 該資料存在進行update，否則insert
                int count = 0;
                
                if (publishService.selectById(publish.getPublishId()) != null) {
                    publish.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    count = publishService.update(publish);
                } else {
                    publish.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    count = publishService.insert(publish);
                }
                
                JsonObject result = new JsonObject();
                result.addProperty("result_code", count > 0 ? 1 : 0);
                writer.write(gson.toJson(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
