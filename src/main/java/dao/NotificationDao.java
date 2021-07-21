package dao;

import java.util.List;

import member.bean.Notification;

public interface NotificationDao {
	int insertComment(int notifiedId, int commentId);
	
	int insertAppointment(int notifiedId,int appointmenId);
	
	int insertOrder(int notifiedId, int orderId);
	
	int insertMessage(int notifiedId, int messageId);
	
	int updateComment(int notifiedId, int commentId);
	
	int deleteCommentByPost(int commentId);
		
	int updateAppointment(int notifiedId);
	
	int editAppointment(int notifiedId, int appointmenId);
	
	int updateOrder(int notifiedId, int orderId);
	
	int updateMessage(int notifiedId, int messageId);
	
	List<Notification> selectByMemberID(int notifiedId);
	
	int updateReaded(int notifiedId);
	
	int deleteOrder(int notifiedId, int orderId);
	
	int deleteComment(int notifiedId, int commentId);
	
	int deleteAppointment(int notifiedId, int appointmenId);
	
	
	
	

}
