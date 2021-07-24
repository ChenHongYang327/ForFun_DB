package dao;

import java.util.List;

import member.bean.Message;

public interface MessageDao {

	int insert(Message message);
	 
	Message selectById(int MSG_ID);
	
	List<Message> selectAll(int CHATROOM_ID);
}
