package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.ChatRoomDao;
import member.bean.ChatRoom;

public class ChatRoomDaolmpl implements ChatRoomDao {
	private DataSource dataSource;
	
	public ChatRoomDaolmpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public List<ChatRoom> selectAll() {
		final String sql = "SELECT CHATROOM_ID, MEMBER_ID_1, CREATE_TIME FROM CHATROOM;";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) 
		{
			List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
			
			while (rs.next()) {
				ChatRoom chatRoom = new ChatRoom();
				chatRoom.setChatroomId(rs.getInt("CHATROOM_ID"));
				chatRoom.setMemberId1(rs.getInt("MEMBER_ID_1"));
				chatRoom.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				
				chatRoomList.add(chatRoom);
				
			}
			
			return chatRoomList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
