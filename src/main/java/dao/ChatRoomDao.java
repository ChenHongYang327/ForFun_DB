package dao;

import java.util.List;

import member.bean.ChatRoom;
import member.bean.Member;
import member.bean.Post;

public interface ChatRoomDao {
	
	List<ChatRoom> selectAll();
	
	int insert(ChatRoom chatRoom);
	
	ChatRoom selectChatRommId(int MEMBER_ID_1, int MEMBER_ID_2);
	
	public int getInsertId();	
}
