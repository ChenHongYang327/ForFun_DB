package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.PostDao;
import dao.impl.PostDaolmpl;
import member.bean.Post;
import service.PostService;

@WebServlet("/DiscussionBoardController")
public class DiscussionBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PostService postService = null;   

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
			writeText(response, gson.toJson(postList));
			
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
		} else if (action.equals("getImagePath")) {
			int postId = jsonObject.get("postId").getAsShort();
			String postImagePath = postService.getImagePath(postId);
			writeText(response, gson.toJson(postImagePath));
		}  else {
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
