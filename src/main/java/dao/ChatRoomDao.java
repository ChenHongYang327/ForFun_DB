package dao;

import java.util.List;

import member.bean.ChatRoom;

public interface ChatRoomDao {
	
	List<ChatRoom> selectAll();
	
}
