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
	public int insertAppointment(int notifiedId,int appointmenId){
		return notificationDao.insertAppointment(notifiedId, appointmenId);
	}
	//訂單
	public int insertOrder(int notifiedId, int orderId) {
		return notificationDao.insertOrder(notifiedId, orderId);
	}
	//私訊
	public int insertMessage(int notifiedId, int messageId) {
		return notificationDao.insertMessage(notifiedId, messageId);
	}
	//未點擊通知已讀刊登單留言
	public int updateComment(int notifiedId, int commentId) {
		return notificationDao.updateComment(notifiedId, commentId);
	}
	//文章被刪除已讀刊登單留言
	public int deleteCommentByPost(int commentId) {
		return notificationDao.deleteCommentByPost(commentId);
	}
	//未點擊通知已讀預約單
	public int updateAppointment(int notifiedId,int appointmenId) {
		return notificationDao.updateAppointment(notifiedId, appointmenId);
	}
	//預約單被修改
	public int editAppointment(int notifiedId, int appointmenId) {
		return notificationDao.editAppointment(notifiedId, appointmenId);
	}
	
	//未點擊通知已讀訂單
	public int updateOrder(int notifiedId, int orderId) {
		return notificationDao.updateOrder(notifiedId, orderId);
	}
	//未點擊通知已讀私訊
	public int updateMessage(int notifiedId, int messageId) {
		return notificationDao.updateMessage(notifiedId, messageId);
	}
	//查找還未讀的通知
	public List<Notification> selectByMemberID(int notifiedId){
		return notificationDao.selectByMemberID(notifiedId);
	}
	//更新未讀的通知已讀狀態
	public int updateReaded(int notifiedId) {
		return notificationDao.updateReaded(notifiedId);
	}
	
	public int deleteOrder(int notifiedId, int orderId) {
		return notificationDao.deleteOrder(notifiedId, orderId);
	}
	public int deleteComment(int notifiedId, int commentId) {
		return notificationDao.deleteComment(notifiedId, commentId);
	}
	public int deleteAppointment(int notifiedId,  int appointmenId) {
		return notificationDao.deleteAppointment(notifiedId, appointmenId);
	}

}
