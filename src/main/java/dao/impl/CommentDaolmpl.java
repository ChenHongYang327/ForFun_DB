package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private int insertId=-1;

	public CommentDaolmpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Comment comment) {
		int count = 0;
		String sql = "INSERT INTO Comment (COMMENT_ID, MEMBER_ID, POST_ID, COMMENT_MSG, CREATE_TIME) VALUES(?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, comment.getCommentId());
			ps.setInt(2, comment.getMemberId());
			ps.setInt(3, comment.getPostId());
			ps.setString(4, comment.getCommentMsg());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			count = ps.executeUpdate();
			 if (count  == 1) {
					ResultSet rs = ps.getGeneratedKeys();
					while (rs.next()) {
						insertId = rs.getInt(1);
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteById(int COMMENT_ID) {
		int count = 0;
//		String sql = "DELETE FROM Comment WHERE COMMENT_ID = ?;";
		String sql = "UPDATE Comment SET DELETE_TIME = ? WHERE COMMENT_ID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, COMMENT_ID);
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
			ps.setInt(3, comment.getCommentId());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public Comment selectById(int COMMENT_ID) {
		String sql = "SELECT * FROM Comment WHERE COMMENT_ID = ?;";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, COMMENT_ID);
			Comment comment = new Comment();	
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comment.setCommentId(rs.getInt("COMMENT_ID"));
				comment.setMemberId(rs.getInt("MEMBER_ID"));
				comment.setPostId(rs.getInt("POST_ID"));
				comment.setCommentMsg(rs.getString("COMMENT_MSG"));
				comment.setRead(rs.getBoolean("READ"));
				comment.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				comment.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				comment.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				
			}
			return comment;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Comment> selectAllByPostId(int POST_ID) {
		String sql = "SELECT MEMBER_ID, COMMENT_ID, COMMENT_MSG, CREATE_TIME FROM Comment WHERE POST_ID = ? AND DELETE_TIME IS NULL;";
		List<Comment> commentList = new ArrayList<Comment>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, POST_ID);
				
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setMemberId(rs.getInt("MEMBER_ID"));
				comment.setCommentId(rs.getInt("COMMENT_ID"));
				comment.setCommentMsg(rs.getString("COMMENT_MSG"));
				comment.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				commentList.add(comment);

			}
			return commentList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getInsertId() {
		return insertId;
	}

}
