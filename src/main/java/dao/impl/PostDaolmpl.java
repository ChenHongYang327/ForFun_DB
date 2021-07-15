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
import dao.PostDao;
import member.bean.Post;

public class PostDaolmpl implements PostDao {
	DataSource dataSource;

	public PostDaolmpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Post post) {
		int count = 0;
		String sql = "INSERT INTO Post (POST_ID, BOARD_ID, POSTER_ID, POST_TITLE, POST_IMG, POST_CONTEXT, CREATE_TIME) VALUES(?, ?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, post.getPostId());
			ps.setString(2, post.getBoardId());
			ps.setInt(3, post.getPosterId());
			ps.setString(4, post.getPostTitle());
			ps.setString(5, post.getPostImg());
			ps.setString(6, post.getPostContext());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			count = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	@Override
	public int deleteById(int POST_ID) {
		int count = 0;
		String sql = "UPDATE Post SET DELETE_TIME = ? WHERE POST_ID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, POST_ID);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int update(Post post) {
		int count = 0;
		String sql = "UPDATE post SET POST_TITLE = ?, POST_CONTEXT = ?,POST_IMG = ?, UPDATE_TIME = ? WHERE POST_ID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, post.getPostTitle());
			ps.setString(2, post.getPostContext());
			ps.setString(3, post.getPostImg());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, post.getPostId());
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Post selectById(int POST_ID) {
		String sql = "SELECT BOARD_ID, POSTER_ID, POST_TITLE, POST_IMG, POST_CONTEXT, CREATE_TIME FROM Post WHERE POST_ID = ?;";
		Post post = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, POST_ID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String boardId = rs.getString(1);
				int posterId = rs.getInt(2);
				String postTitle = rs.getString(3);
				String postImg = rs.getString(4);
				String postContext = rs.getString(5);
				Timestamp postCreatTime = rs.getTimestamp(6);
				post = new Post(POST_ID, boardId, posterId, postTitle, postImg, postContext, postCreatTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	public List<Post> selectAll(String BOARD_ID) {
		String sql = "SELECT POST_ID, POSTER_ID, POST_TITLE, POST_IMG, POST_CONTEXT, CREATE_TIME  FROM Post WHERE BOARD_ID = ? AND DELETE_TIME IS NULL;";
		List<Post> postList = new ArrayList<Post>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, BOARD_ID);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int postId = rs.getInt(1);
				int posterId = rs.getInt(2);
				String postTitle = rs.getString(3);
				String postImg = rs.getString(4);
				String postContext = rs.getString(5);
				Timestamp postCreatTime = rs.getTimestamp(6);
				Post post = new Post(BOARD_ID, postId, posterId, postTitle, postImg, postContext, postCreatTime);
				postList.add(post);

			}
			return postList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return postList;
	}

	@Override
	public String getImagePath(int POST_ID) {
		String sql = "SELECT POST_IMG FROM Post WHERE POST_ID = ?;";
		String imagePath = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, POST_ID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				imagePath = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagePath;
	}

	@Override
	public String getBoardId(int POST_ID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
