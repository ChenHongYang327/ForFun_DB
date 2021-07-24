package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Member;
import member.bean.Message;
import service.MemberService;
import service.MessageService;

@WebServlet("/MessageController")
public class MessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    MessageService messageService = null;   
    MemberService memberService = new MemberService();
 // client送來的token
  	private String registrationToken = "";
  	// 儲存所有client送來的token
  	private static final Set<String> registrationTokens = Collections.synchronizedSet(new HashSet<>());
     
     @Override
     public void init() throws ServletException {
     	// 私密金鑰檔案可以儲存在專案以外
     	// File file = new File("/path/to/firsebase-java-privateKey.json");
     	// 私密金鑰檔案也可以儲存在專案WebContent目錄內，私密金鑰檔名要與程式所指定的檔名相同
     	try (InputStream input = getServletContext().getResourceAsStream("/firebaseServerKey.json")){
     		FirebaseOptions options = FirebaseOptions.builder()
     				.setCredentials(GoogleCredentials.fromStream(input)).build();
     		FirebaseApp.initializeApp(options);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
     }
    
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
			int chatroomId = jsonObject.get("chatRoomId").getAsInt();
		
			
			List<Message> messageList = messageService.selectAll(chatroomId);
			jsonObject.addProperty("messageList", new Gson().toJson(messageList));
			writeText(response, gson.toJson(jsonObject));
			
		} else if (action.equals("messageInsert")) {
			String messageJson = jsonObject.get("chatRoomMessage").getAsString();
			int member1TokenId = jsonObject.get("receivedMemberId").getAsInt();
			int memberId = jsonObject.get("MemberId").getAsInt();
			//取得token
			List<Member> memberList = new ArrayList<Member>();
			Member member = memberService.selectById(member1TokenId);
			Member member2 = memberService.selectById(memberId);
			
			registrationToken = member.getToken();
			if (registrationToken != null) {
				JsonObject notificaitonFCM = new JsonObject();
				notificaitonFCM.addProperty("title", "新私訊");
				String memberName = member2.getNameL() + member2.getNameF();
				notificaitonFCM.addProperty("body", "您有來自" + "「 "+ memberName + "」" + "的一則新訊息");
				NotificationController.sendSingleFcm(notificaitonFCM, registrationToken);
			}
			
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
