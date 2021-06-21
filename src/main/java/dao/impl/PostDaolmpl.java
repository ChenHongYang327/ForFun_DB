package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
		final String sql = "nsert into Post ( POST_ID, BOARD_ID, POSTER_ID, POST_TITLE, POST_IMG, CONTEXT, CREATE_TIME ) values (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, post.getPostId());
				ps.setString(2, post.getBoardId());
				ps.setInt(3, post.getPosterId());
				ps.setString(4, post.getPostTitle());
				ps.setString(5,post.getPostImg());
				ps.setString(6, post.getContent());
				ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				return ps.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteById(int POST_ID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Post post) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Post selectById(int POST_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
