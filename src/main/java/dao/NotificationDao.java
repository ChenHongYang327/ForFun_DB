package dao;

import java.util.List;

import member.bean.Notification;

public interface NotificationDao {
	int insertComment(int notifiedId, int commentId);
	
	int inserAppointment(int notifiedId,int appointmenId);
	
	int insertOreder(int notifiedId, int orederId);
	
	int insertMessage(int notifiedId, int messageId);
	
	int updateComment(int notifiedId, int commentId);
	
	int updateCommentByPost(int commentId);
		
	int updateAppointment(int notifiedId,int appointmenId);
	
	int updateOreder(int notifiedId, int orederId);
	
	int updateMessage(int notifiedId, int messageId);
	
	List<Notification> selectByMemberID(int notifiedId);
	
	int updateReaded(int notifiedId);
	
	

}
