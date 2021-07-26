package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import dao.PostDao;
import dao.impl.PostDaolmpl;
import member.bean.Comment;
import member.bean.Member;
import member.bean.Post;
import service.CommentService;
import service.MemberService;
import service.NotificationService;
import service.PostService;

@WebServlet("/DiscussionBoardController")
public class DiscussionBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PostService postService = null;   
	MemberService memberService = new MemberService();
	
	@Override
	public void init() throws ServletException {
		// 私密金鑰檔案可以儲存在專案以外
		// File file = new File("/path/to/firsebase-java-privateKey.json");
		// 私密金鑰檔案也可以儲存在專案WebContent目錄內，私密金鑰檔名要與程式所指定的檔名相同
		if (NotificationController.firebaseApp == null) {
			try (InputStream in = getServletContext().getResourceAsStream("/firebaseServerKey.json")) {
				FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(in))
						.build();
				NotificationController.firebaseApp = FirebaseApp.initializeApp(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
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
		if(postService == null) {
			postService = new PostService();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			// 將輸入資料列印出來除錯用
			String boardId = jsonObject.get("boardId").getAsString();
			System.out.println("input: " + jsonIn);
			List<Post> postList = postService.selectAll(boardId);
			List<Member> members = new ArrayList<>();
			for(Post post:postList) {
				
				//比對後端資料 拿取會員姓名跟頭貼
				Member member = memberService.selectAllHeadShotAndName(post.getPosterId());
				members.add(member);
			}
			//裝在jsonObject後設定key 送回前端	
			jsonObject.addProperty("postList", new Gson().toJson(postList));
			jsonObject.addProperty("memberList", new Gson().toJson(members));
			writeText(response, gson.toJson(jsonObject));
			
		} else if (action.equals("getImage")){
			System.out.println("input: " + jsonIn);
			PrintWriter pw = response.getWriter();
			String imagePath = jsonObject.get("imagePath").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			System.out.println("imagePath:" + imagePath.toString());
			pw.write(imagePath);
			
		}else if (action.equals("postInsert") || action.equals("postUpdate")) {
			String postJson = jsonObject.get("post").getAsString();
			System.out.println("postJson = " + postJson);
			Post post = gson.fromJson(postJson, Post.class);
			
			int count = 0;
			if (action.equals("postInsert")) {
				count = postService.insert(post);
				
			} else if (action.equals("postUpdate")) {
				count = postService.update(post);
			}
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("postDelete")){
			int postId = jsonObject.get("postId").getAsShort();
			int count = postService.deleteById(postId);
			writeText(response, String.valueOf(count));
			
			//---------刪除文章更新留言通知的狀態
			List<Comment> comments=new CommentService().selectAllByPostId(postId);
			for(Comment comment:comments) {
				new NotificationService().deleteCommentByPost(comment.getCommentId());
			}
			//刪除文章觸發更改通知數
			int notified=new PostService().selectById(postId).getPosterId();
			String memberToken = new MemberService().selectById(notified).getToken();
			if (memberToken != null) {
				NotificationController.sendSingleFcmNoNotification(memberToken);
			}
			//----------------------
		
		} else if (action.equals("getImagePath")) {
			int postId = jsonObject.get("postId").getAsShort();
			String postImagePath = postService.getImagePath(postId);
			writeText(response, gson.toJson(postImagePath));
			
		} else if(action.equals("getRentSeekList")){
			// 將輸入資料列印出來除錯用
			String boardId = jsonObject.get("boardId").getAsString();
			int memberId = jsonObject.get("memberId").getAsInt();
			List<Post> postList = postService.selectRentSeekList(boardId, memberId);
			jsonObject.addProperty("rentSeekList", new Gson().toJson(postList));
			writeText(response, gson.toJson(jsonObject)); 
		
		} else {
			writeText(response, "");
		}
		
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		// System.out.println("output: " + outText);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String boardId = jsonObject.get("boardId").getAsString();
		if (postService == null) {
			postService = new PostService();
		}
		List<Post> posts = postService.selectAll(boardId);
		writeText(response, new Gson().toJson(posts));
	}
	
}
