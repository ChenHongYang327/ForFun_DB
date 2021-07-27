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
import dao.ChatRoomDao;
import member.bean.ChatRoom;
import member.bean.Comment;

public class ChatRoomDaolmpl implements ChatRoomDao {
	private DataSource dataSource;
	private int insertId = -1;
	
	public ChatRoomDaolmpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public List<ChatRoom> selectAll() {
		final String sql = "SELECT * FROM CHATROOM;";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) 
		{
			List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
			
			while (rs.next()) {
				ChatRoom chatRoom = new ChatRoom();
				chatRoom.setChatroomId(rs.getInt("CHATROOM_ID"));
				chatRoom.setMemberId1(rs.getInt("MEMBER_ID_1"));
				chatRoom.setMemberId2(rs.getInt("MEMBER_ID_2"));
				chatRoom.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				
				chatRoomList.add(chatRoom);
				
			}
			
			return chatRoomList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public ChatRoom selectChatRommId(int MEMBER_ID_1, int MEMBER_ID_2) {
		final String sql = "SELECT * FROM CHATROOM WHERE (MEMBER_ID_1 = ? AND MEMBER_ID_2 = ?) or (MEMBER_ID_1 = ? AND MEMBER_ID_2 = ?);";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
				stmt.setInt(1, MEMBER_ID_1);
				stmt.setInt(2, MEMBER_ID_2);
				stmt.setInt(3, MEMBER_ID_2);
				stmt.setInt(4, MEMBER_ID_1);
				ResultSet rs = stmt.executeQuery();
				ChatRoom chatRoom = new ChatRoom();
			while (rs.next()) {
				chatRoom.setChatroomId(rs.getInt("CHATROOM_ID"));
				chatRoom.setMemberId1(rs.getInt("MEMBER_ID_1"));
				chatRoom.setMemberId2(rs.getInt("MEMBER_ID_2"));
				chatRoom.setCreateTime(rs.getTimestamp("CREATE_TIME"));
			
				
			}
			System.out.println("111111111");
			return chatRoom;
			
		} catch (SQLException e) {
			System.out.println("2222222222");
			e.printStackTrace();
		}
		System.out.println("33333333333");
		return null;
	}

	@Override
	public int insert(ChatRoom chatRoom) {
		int count = 0;
		String sql = "INSERT INTO chatRoom ( MEMBER_ID_1, MEMBER_ID_2, CREATE_TIME) VALUES( ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, chatRoom.getMemberId1());
			ps.setInt(2, chatRoom.getMemberId2());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			count = ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				insertId = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getInsertId() {
		return insertId;
	}

}
