package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.PostDao;
import dao.impl.PostDaolmpl;
import member.bean.Post;
import service.PostService;

public class DiscussionBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PostDao postDao = null;
       

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PostService postService = new PostService();
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if(postDao == null) {
			 postDao = new PostDaolmpl();
		}
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("postInsert") || action.equals("postUpdate")) {
			String postJson = jsonObject.get("post").getAsString();
			
			System.out.println("postJson = " + postJson);
			Post post = gson.fromJson(postJson, Post.class);
			
			int count = 0;
			if (action.equals("postInsert")) {
				count = postDao.insert(post);
			} else if (action.equals("postUpdate")) {
				count = postDao.update(post);
			}
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (postDao == null) {
			postDao = new PostDaolmpl();
		}
		List<Post> posts = postDao.selectAll();
		writeText(response, new Gson().toJson(posts));
	}
}
