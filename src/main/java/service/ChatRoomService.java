package service;

import java.util.List;

import dao.ChatRoomDao;
import dao.impl.ChatRoomDaolmpl;
import member.bean.ChatRoom;
import member.bean.Member;
import member.bean.Post;

public class ChatRoomService {
	public ChatRoomDao dao;
	
	
	public ChatRoomService() {
		dao = new ChatRoomDaolmpl();
	}
	
	public int insert(ChatRoom chatRoom) {
		return dao.insert(chatRoom);
	}
	
	public List<ChatRoom> selectAll() {
		return dao.selectAll();
	}
	
	public ChatRoom selectChatRommId(int MEMBER_ID_1, int MEMBER_ID_2) {
		
		return dao.selectChatRommId(MEMBER_ID_1, MEMBER_ID_2);
	}
	
	public int getInsertId() {
		
		return dao.getInsertId();
	}
}
