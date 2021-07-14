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

import member.bean.Comment;
import service.CommentService;
import service.NotificationService;
import service.PostService;

@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommentService commentService = null;
       

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
		if (commentService == null) {
			commentService = new CommentService();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			int postId = jsonObject.get("postId").getAsInt();
			System.out.println("input: " + jsonIn);
			List<Comment> commentList = commentService.selectAllByPostId(postId);
			writeText(response, gson.toJson(commentList));
			
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
				//留言者非貼文者
				if (comment.getMemberId() != notified) {
					if (count > 0) {
						new NotificationService().insertComment(notified, insertId);
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
		int postId = jsonObject.get("commentId").getAsShort();
		if (commentService == null) {
			commentService = new CommentService();
		}
		
		List<Comment> comments = commentService.selectAllByPostId(postId);
		writeText(response, new Gson().toJson(comments));
	}
	
}
