package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.CommentDao;
import member.bean.Comment;
import member.bean.Post;

public class CommentDaolmpl implements CommentDao {
	DataSource dataSource;

	public CommentDaolmpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Comment comment) {
		int count = 0;
		String sql = "INSERT INTO Comment (COMMENT_ID, MEMBER_ID, POST_ID, COMMENT_MSG, CREATE_TIME, READ) VALUES(?, ?, ?, ?, ?, ? );";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, comment.getCommentId());
			ps.setInt(2, comment.getMemberId());
			ps.setInt(3, comment.getPostId());
			ps.setString(4, comment.getCommentMsg());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setBoolean(6, false);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteById(int COMMENT_ID) {
		int count = 0;
		String sql = "DELETE FROM Comment WHERE COMMENT_ID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, COMMENT_ID);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int update(Comment comment) {
		int count = 0;
		String sql = "UPDATE Comment SET COMMENT_MSG = ?, UPDATE_TIME = ? WHERE COMMENT_ID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, comment.getCommentMsg());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public Comment selectById(int COMMENT_ID) {
		String sql = "SELECT MEMBER_ID, POST_ID, COMMENT_MSG, CREATE_TIME  WHERE COMMENT_ID = ?;";
		Comment comment = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, COMMENT_ID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int memberId = rs.getInt(1);
				int postId = rs.getInt(2);
				String commentMsg = rs.getString(3);
				Timestamp commentCreatTime = rs.getTimestamp(4);
				comment = new Comment(COMMENT_ID, memberId, postId, commentMsg, commentCreatTime);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comment;

	}

	@Override
	public List<Comment> selectAll() {
		String sql = "SELECT COMMENT_ID, MEMBER_ID, POST_ID, COMMENT_MSG, CREATE_TIME FROM Comment;";
		List<Comment> commentList = new ArrayList<Comment>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int commentId = rs.getInt(1);
				int memberId = rs.getInt(2);
				int postId = rs.getInt(3);
				String commentMsg = rs.getString(4);
				Timestamp commentCreatTime = rs.getTimestamp(5);
				Comment comment = new Comment(commentId, memberId, postId, commentMsg, commentCreatTime);
				commentList.add(comment);

			}
			return commentList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return commentList;
	}

}
