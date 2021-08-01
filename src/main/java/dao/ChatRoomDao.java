package dao;

import java.util.List;

import member.bean.ChatRoom;
import member.bean.Member;
import member.bean.Post;

public interface ChatRoomDao {
	
	List<ChatRoom> selectAll(int MEMBER_ID_1);
	
	
	public int insert(ChatRoom chatRoom);
	
	ChatRoom selectChatRoomId(int MEMBER_ID_1, int MEMBER_ID_2);
	
	public int getInsertId();	
	
	public int deleteById(int CHATROOM_ID);
	
	public ChatRoom selectById(int CHATROOM_ID);
}
