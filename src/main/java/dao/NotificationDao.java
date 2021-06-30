package dao;

import java.util.List;

import member.bean.Notification;

public interface NotificationDao {
	int insertComment(int notifiedId, int commentId);
	
	int inserAppointment(int notifiedId,int appointmenId);
	
	int insertOreder(int notifiedId, int orederId);
	
	int insertMessage(int notifiedId, int messageId);
	
	int upadteComment(int notifiedId, int commentId);
	
	int upadteAppointment(int notifiedId,int appointmenId);
	
	int upadteOreder(int notifiedId, int orederId);
	
	int upadteMessage(int notifiedId, int messageId);
	
	List<Notification> selectByMemberID(int notifiedId);
	
	int updateReaded(int notifiedId);
	
	

}
