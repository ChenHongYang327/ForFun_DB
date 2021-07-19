package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.bean.Comment;
import member.bean.Member;
import service.CommentService;
import service.MemberService;
import service.NotificationService;
import service.PostService;

@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommentService commentService = null;
	MemberService memberService = new MemberService();

	@Override
	public void init() throws ServletException {
		// 私密金鑰檔案可以儲存在專案以外
		// File file = new File("/path/to/firsebase-java-privateKey.json");
		// 私密金鑰檔案也可以儲存在專案WebContent目錄內，私密金鑰檔名要與程式所指定的檔名相同
		try (InputStream in = getServletContext().getResourceAsStream("/firebaseServerKey.json")) {
			FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(in))
					.build();
			if(NotificationController.firebaseApp==null) {
				NotificationController.firebaseApp=FirebaseApp.initializeApp(options);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		if (commentService == null) {
			commentService = new CommentService();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			int postId = jsonObject.get("postId").getAsInt();
			System.out.println("input: " + jsonIn);
			List<Comment> commentList = commentService.selectAllByPostId(postId);
			List<Member> memberList = new ArrayList<Member>();
			
			for (Comment comment : commentList) {
				Member member = memberService.selectAllHeadShotAndName(comment.getMemberId());
				memberList.add(member);
			}
			jsonObject.addProperty("commentList", new Gson().toJson(commentList));
			jsonObject.addProperty("memberList", new Gson().toJson(memberList));
			
			writeText(response, gson.toJson(jsonObject));

		} else if (action.equals("commentInsert") || action.equals("commentUpdate")) {
			String commentJson = jsonObject.get("comment").getAsString();
			System.out.println("commentJson = " + commentJson);
			Comment comment = gson.fromJson(commentJson, Comment.class);

			int count = 0;
			if (action.equals("commentInsert")) {
				count = commentService.insert(comment);
				// 新增通知功能-------
				int insertId = commentService.getInsertId();
				int notified = new PostService().selectById(comment.getPostId()).getPosterId();
				// 留言者非貼文者
				if (comment.getMemberId() != notified) {
					if (count > 0) {
						new NotificationService().insertComment(notified, insertId);
						String memberToken = new MemberService().selectById(notified).getToken();
						if (memberToken != null) {
							JsonObject notificaitonFCM = new JsonObject();
							notificaitonFCM.addProperty("title", "新通知");
							String postTitle=new PostService().selectById(comment.getPostId()).getPostTitle();
							notificaitonFCM.addProperty("body", "您的"+"「"+postTitle+"」"+"文章有一則新留言");
							sendSingleFcm(notificaitonFCM, memberToken);
						}
					}
				}
				// -----------------
			} else if (action.equals("commentUpdate")) {
				count = commentService.update(comment);

			}
			writeText(response, String.valueOf(count));

		} else if (action.equals("commentDelete")) {
			int commentId = jsonObject.get("commentId").getAsShort();
			int count = commentService.deleteById(commentId);
			writeText(response, String.valueOf(count));
			// 刪除留言並刪除通知
			int notified = new PostService().selectById(commentService.selectById(commentId).getPostId()).getPosterId();
			// 留言者非貼文者
			if (commentService.selectById(commentId).getMemberId() != notified) {
				if (count > 0) {
					System.out.println("");
					new NotificationService().updateComment(notified, commentId);
					String memberToken = new MemberService().selectById(notified).getToken();
					if (memberToken != null) {
						JsonObject notificaitonFCM = new JsonObject();
						notificaitonFCM.addProperty("title", "新通知");
						notificaitonFCM.addProperty("body", "留言已被刪除");
						sendSingleFcm(notificaitonFCM, memberToken);
					}
				}
			}

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		int postId = jsonObject.get("commentId ").getAsShort();
		if (commentService == null) {
			commentService = new CommentService();
		}

		List<Comment> comments = commentService.selectAllByPostId(postId);
		writeText(response, new Gson().toJson(comments));
	}

	// 發送單一FCM
	private void sendSingleFcm(JsonObject jsonObject, String registrationToken) {
		String title = jsonObject.get("title").getAsString();
		String body = jsonObject.get("body").getAsString();
		String data = jsonObject.get("data") == null ? "no data" : jsonObject.get("data").getAsString();
		// 主要設定訊息標題與內容，client app一定要在背景時才會自動顯示
		Notification notification = Notification.builder()
				.setTitle(title) // 設定標題
				.setBody(body) // 設定內容
				.build();
		// 發送notification message
		Message.Builder message = Message.builder();
		if(!body.equals("留言已被刪除")) {
			 message
			 	.setNotification(notification) // 設定client app在背景時會自動顯示訊息
			 	.putData("data", data); // 設定自訂資料，user點擊訊息時方可取值
		}
			message	
		 		.setToken(registrationToken); // 送訊息給指定token的裝置
		try {
			FirebaseMessaging.getInstance().send(message.build());
//					String messageId = FirebaseMessaging.getInstance().send(message);
//					System.out.println(registrationToken);
//					System.out.println("messageId: " + messageId);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
	}

}
