package service;

import java.util.List;

import dao.NotificationDao;
import dao.impl.NotificationImpl;
import member.bean.Notification;

public class NotificationService {
	private NotificationDao notificationDao;

	public NotificationService() {
		notificationDao=new NotificationImpl();
	}
	//刊登單留言
	public int insertComment(int notifiedId, int commentId) {
		return notificationDao.insertComment(notifiedId, commentId);
	}
	//預約單
	public int inserAppointment(int notifiedId,int appointmenId){
		return notificationDao.inserAppointment(notifiedId, appointmenId);
	}
	//訂單
	public int insertOreder(int notifiedId, int orederId) {
		return notificationDao.insertOreder(notifiedId, orederId);
	}
	//私訊
	public int insertMessage(int notifiedId, int messageId) {
		return notificationDao.insertMessage(notifiedId, messageId);
	}
	//已讀刊登單留言
	public int upadteComment(int notifiedId, int commentId) {
		return notificationDao.upadteComment(notifiedId, commentId);
	}
	//已讀預約單
	public int upadteAppointment(int notifiedId,int appointmenId) {
		return notificationDao.upadteAppointment(notifiedId, appointmenId);
	}
	//已讀訂單
	public int upadteOreder(int notifiedId, int orederId) {
		return notificationDao.upadteOreder(notifiedId, orederId);
	}
	//已讀私訊
	public int upadteMessage(int notifiedId, int messageId) {
		return notificationDao.upadteMessage(notifiedId, messageId);
	}
	//查找還未讀的通知
	public List<Notification> selectByMemberID(int notifiedId){
		return notificationDao.selectByMemberID(notifiedId);
	}
	//更新未讀的通知已讀狀態
	public int updateReaded(int notifiedId) {
		return notificationDao.updateReaded(notifiedId);
	}
	

}
