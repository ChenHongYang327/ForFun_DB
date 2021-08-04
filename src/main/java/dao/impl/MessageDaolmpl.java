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
import dao.MessageDao;
import member.bean.Message;

public class MessageDaolmpl implements MessageDao{
	DataSource dataSource;
	
	public MessageDaolmpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Message message) {
		int count = 0;
		String sql = "INSERT INTO Message (MSG_ID, CHATROOM_ID, MEMBER_ID, MSG_CHAT, CREATE_TIME) VALUES(?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, message.getMsgId());
			ps.setInt(2, message.getChatroomId());
			ps.setInt(3, message.getMemberId());
			ps.setString(4, message.getMsgChat());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public Message selectById(int MSG_ID) {
		String sql = "SELECT MEMBER_ID, CHATROOM_ID, MSG_CHAT, CREATE_TIME FROM Message WHERE MSG_ID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, MSG_ID);
			Message message = new Message();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				message.setMemberId(rs.getInt("MEMBER_ID"));
				message.setChatroomId(rs.getInt("CHATROOM_ID"));
				message.setMsgChat(rs.getString("MSG_CHAT"));
				message.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				
			}
			return message;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Message> selectAll(int CHATROOM_ID) {
		String sql = "SELECT * FROM Message WHERE CHATROOM_ID = ?;";
		List<Message> messageList = new ArrayList<Message>();
		try (Connection connection = dataSource.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, CHATROOM_ID);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Message message = new Message();
				message.setMsgId(rs.getInt("MSG_ID"));
				message.setMemberId(rs.getInt("MEMBER_ID"));
				message.setChatroomId(rs.getInt("CHATROOM_ID"));
				message.setMsgChat(rs.getString("MSG_CHAT"));
				message.setRead(rs.getBoolean("READ"));
				message.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				messageList.add(message);
				
			}
			return messageList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateRead(int MSG_ID) {
		String sql = "UPDATE FORFUN.Message SET FORFUN.Message.READ = 1,UPDATE_TIME = ? WHERE MSG_ID = ? AND FORFUN.Message.READ = 0;";
		try (Connection connection = dataSource.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, MSG_ID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}
	
	
	@Override
	public List<Message> selectByMemberId(int CHATROOM_ID, int MEMBER_ID) {
		String sql = "SELECT * FROM Message WHERE CHATROOM_ID = ? AND MEMBER_ID != ?;";
		List<Message> messageList = new ArrayList<Message>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, CHATROOM_ID);
				ps.setInt(2, MEMBER_ID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Message message = new Message();
				message.setMsgId(rs.getInt("MSG_ID"));
				message.setChatroomId(rs.getInt("CHATROOM_ID"));
				message.setMemberId(rs.getInt("MEMBER_ID"));
				message.setMsgChat(rs.getString("MSG_CHAT"));
				message.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				messageList.add(message);
			}
			return messageList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("11111111111111");
		return null;
	}
	
	
	@Override
	public List<Message> selectByMSG(int CHATROOM_ID, int MEMBER_ID) {
		String sql = "SELECT * FROM Message WHERE CHATROOM_ID = ? AND MEMBER_ID != ? AND READ = 1;";
		List<Message> messageList = new ArrayList<Message>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, CHATROOM_ID);
				ps.setInt(2, MEMBER_ID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Message message = new Message();
				message.setMsgId(rs.getInt("MSG_ID"));
				message.setChatroomId(rs.getInt("CHATROOM_ID"));
				message.setMemberId(rs.getInt("MEMBER_ID"));
				message.setMsgChat(rs.getString("MSG_CHAT"));
				message.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				messageList.add(message);
			}
			return messageList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("11111111111111");
		return null;
	}
	
}
