package dao;

import java.util.List;

import member.bean.Comment;

public interface CommentDao {

int insert(Comment comment);
	
	int deleteById(int COMMENT_ID);
	
	int update(Comment comment);
	
	Comment selectById(int COMMENT_ID);
	
	List<Comment> selectAll();
	
}
