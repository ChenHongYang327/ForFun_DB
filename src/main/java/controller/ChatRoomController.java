package controller;

import java.io.BufferedReader;
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

import io.grpc.netty.shaded.io.netty.buffer.UnpooledByteBufAllocator;
import member.bean.ChatRoom;
import member.bean.Member;
import member.bean.Post;
import service.ChatRoomService;
import service.MemberService;
import service.PostService;

@WebServlet("/ChatRoomController")
public class ChatRoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ChatRoomService chatRoomService = null;
	MemberService memberService = new MemberService();

       

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
         if (action.equals("selectChatRoomId")) {
			//檢查有無聊天室
			int count = 0;
			int chatRoomMemberId1 = jsonObject.get("receivedMemberId").getAsInt();
			int chatRoomMemberId2 = jsonObject.get("sendMemberId").getAsInt();
			ChatRoom chatRoom = chatRoomService.selectChatRoomId(chatRoomMemberId1, chatRoomMemberId2);
			//如果有聊天室回傳聊天室ID	
			if (chatRoom.getChatroomId() != null ) {
				writeText(response, String.valueOf(chatRoom.getChatroomId()));
				System.out.println("getChatroomId:" + chatRoom.getChatroomId());
			//如果無聊天室 新增後回傳聊天室ID		
			} else {
				ChatRoom chatRoom2 = new ChatRoom();
				chatRoom2.setMemberId1(chatRoomMemberId1);
				chatRoom2.setMemberId2(chatRoomMemberId2);
				count = chatRoomService.insert(chatRoom2);
				int chatRoomId = chatRoomService.getInsertId();
				System.out.println("else chatRoomId:" + chatRoomId);
				writeText(response, String.valueOf(chatRoomId));
			}
			
		} else if (action.equals("getAll")) {
        	int chatRoomMemberId1 = jsonObject.get("receivedMemberId").getAsInt();
			List<ChatRoom> chatRoomList = chatRoomService.selectAll(chatRoomMemberId1);
			
			jsonObject.addProperty("equalMember", new Gson().toJson(chatRoomList));
			//裝在jsonObject後設定key 送回前端	
			writeText(response, gson.toJson(jsonObject));
			
		} if (action.equals("getMemberId")) {
			int memberId2 = jsonObject.get("MemberId2").getAsInt();
			Member member = memberService.selectById(memberId2);
			writeText(response, gson.toJson(member));
			
		} else if (action.equals("chatRoomDelete")) {
			int chatRoomId = jsonObject.get("chatRoomId").getAsShort();
			int count = chatRoomService.deleteById(chatRoomId);
			writeText(response, String.valueOf(count));
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
		int chatRoomMemberId1 = jsonObject.get("receivedMemberId").getAsInt();
		
		List<ChatRoom> chatRoomList = chatRoomService.selectAll(chatRoomMemberId1);
//		List<ChatRoom> chatRoomList2 = chatRoomService.selectAll2(chatRoomMemberId1);	
		
		//裝在jsonObject後設定key 送回前端	
		jsonObject.addProperty("equalMember1", new Gson().toJson(chatRoomList));
//		jsonObject.addProperty("equalMember2", new Gson().toJson(chatRoomList2));
		writeText(response, gson.toJson(chatRoomList));
	
	}
}
