package dao;

import java.util.List;

import member.bean.Message;

public interface MessageDao {

	int insert(Message message);
	 
	Message selectById(int MSG_ID);
	
	List<Message> selectAll(int CHATROOM_ID);
	
	int updateRead(int MSG_ID);
	
	List<Message> selectByMemberId(int CHATROOM_ID, int MEMBER_ID);
	
	List<Message> selectByMSG(int CHATROOM_ID, int MEMBER_ID);
	
}
