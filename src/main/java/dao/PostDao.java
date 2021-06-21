package dao;

import java.util.List;

import member.bean.Post;

public interface PostDao {
	
	int insert(Post post);
	
	int deleteById(int POST_ID);
	
	int update(Post post);
	
	Post selectById(int POST_ID);
	
	List<Post> selectAll();
	

}
