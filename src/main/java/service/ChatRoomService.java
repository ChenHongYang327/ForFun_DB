package service;

import java.util.List;

import dao.ChatRoomDao;
import dao.impl.ChatRoomDaolmpl;
import member.bean.ChatRoom;

public class ChatRoomService {
	public ChatRoomDao dao;
	
	public ChatRoomService() {
		dao = new ChatRoomDaolmpl();
	}
	
	public List<ChatRoom> selectAll() {
		return dao.selectAll();
	}

}
