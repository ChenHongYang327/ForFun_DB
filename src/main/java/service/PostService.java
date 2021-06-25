package service;

import java.util.List;

import dao.PostDao;
import dao.impl.PostDaolmpl;
import member.bean.Post;

public class PostService {
	PostDao postDao;
	
	public PostService () {
		
		postDao = new PostDaolmpl();
	}

	
	public int insert(Post post) {
		
		return postDao.insert(post);
	}
	
	public int deleteById(int POST_ID) {
		
		return postDao.deleteById(POST_ID);
	}
	
	public int update(Post post) {
		
		return postDao.update(post);	
	}
	
	public Post selectById(int POST_ID) {
		
		return postDao.selectById(POST_ID);
	}
	
	public List<Post> selectAll() {
		
		return postDao.selectAll();
	}
	
}
