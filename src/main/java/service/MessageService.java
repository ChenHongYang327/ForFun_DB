package service;

import java.util.List;

import dao.MessageDao;
import dao.impl.MessageDaolmpl;
import member.bean.ChatRoom;
import member.bean.Message;

public class MessageService {
	private MessageDao dao;
	
	public MessageService () {
		dao = new MessageDaolmpl();
	}
	
	public int insert(Message message) {
		return dao.insert(message);
	}
	 
	public Message selectById(int MSG_ID) {
		return dao.selectById(MSG_ID);
	}
	
	public List<Message> selectAll(int CHATROOM_ID) {
		return dao.selectAll(CHATROOM_ID);
	}
	
	public int updateRead(int MSG_ID) {
		return dao.updateRead(MSG_ID);
    	
    }
}
