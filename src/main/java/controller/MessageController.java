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

import member.bean.Message;
import service.MessageService;

@WebServlet("/MessageController")
public class MessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    MessageService messageService = null;   

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (messageService == null) {
			messageService = new MessageService();
			
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			int memberId = jsonObject.get("MEMBER_ID").getAsInt();
			List<Message> messageList = messageService.selectAll(memberId);
			writeText(response, gson.toJson(messageList));
			
		} else if (action.equals("messageInsert")) {
			String messageJson = jsonObject.get("chatRoomMessage").getAsString();
			
			System.out.println("messageJson = " + messageJson);
			Message message = gson.fromJson(messageJson, Message.class);
			
			int count = 0;
			if (action.equals("messageInsert")) {
				count = messageService.insert(message);
			}
			writeText(response, String.valueOf(count));
		} else {
			writeText(response, "");
		}
	} 
	
	
	
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		int memberId = jsonObject.get("MEMBER_ID").getAsInt();
		if (messageService == null) {
			messageService = new MessageService();
			
		}
		
		List<Message> messageList = messageService.selectAll(memberId);
		writeText(response, new Gson().toJson(messageList));
	}
	
}
