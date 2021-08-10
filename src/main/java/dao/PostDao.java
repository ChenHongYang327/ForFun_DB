package dao;

import java.util.List;

import member.bean.Post;

public interface PostDao {
	
	int insert(Post post);
	
	int deleteById(int POST_ID);
	
	int update(Post post);
	
	Post selectById(int POST_ID);
	
	List<Post> selectAll(String BOARD_ID);
	
	String getImagePath(int POST_ID);

	String getBoardId(int POST_ID);
	
	List<Post> selectRentSeekList(String BOARD_ID, int POSTER_ID);
	
	Post selectAllPost(int POST_ID);

}
