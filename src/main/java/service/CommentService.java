package service;

import java.util.List;

import dao.CommentDao;
import dao.impl.CommentDaolmpl;
import member.bean.Comment;

public class CommentService {
	CommentDao commentDao;
	
	public CommentService () {
		commentDao = new CommentDaolmpl();
	}
	
public int insert(Comment comment) {
		
		return commentDao.insert(comment);
	}
	
	public int deleteById(int COMMENT_ID) {
		
		return commentDao.deleteById(COMMENT_ID);
	}
	
	public int update(Comment comment) {
		
		return commentDao.update(comment);	
	}
	
	public Comment selectById(int COMMENT_ID) {
		
		return commentDao.selectById(COMMENT_ID);
	}
	
	public List<Comment> selectAllByPostId(int postId) {
		
		return commentDao.selectAllByPostId(postId);
	}
	
	//取得insert的Id失敗會得到-1
    public int getInsertId() {
    	return commentDao.getInsertId();
    }

}
