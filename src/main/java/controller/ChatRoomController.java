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

import member.bean.ChatRoom;
import service.ChatRoomService;

@WebServlet("/ChatRoomController")
public class ChatRoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ChatRoomService chatRoomService = null;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();
        BufferedReader br = request.getReader();
        StringBuilder jsonin  = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
        	jsonin.append(line);
        }
        
        JsonObject jsonObject = gson.fromJson(jsonin.toString(), JsonObject.class);
        if (chatRoomService == null) {
			chatRoomService = new ChatRoomService();
		}
        String action = jsonObject.get("action").getAsString();
        if (action.equals("getAll")) {
			System.out.println("input" + jsonin);
			List<ChatRoom> chatRoomList = chatRoomService.selectAll();	
			writeText(response, gson.toJson(chatRoomList));
		}
        
        
       
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		// System.out.println("output: " + outText);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	List<ChatRoom> chatRoomList = chatRoomService.selectAll();
	writeText(response, new Gson().toJson(chatRoomList));
	
	}
}
