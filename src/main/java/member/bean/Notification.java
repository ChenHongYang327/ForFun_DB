package member.bean;

import java.sql.Timestamp;

public class Notification {

	private Integer notificationId;
	private Integer commentId;
	private Integer appointmentId;
	private Integer orderId;
	private Integer messageId;
	private Boolean read;
	private Timestamp createTime;

	public Notification(Integer notificationId, Integer commentId, Integer appointmentId, Integer orderId,
			Integer messageId, Boolean read, Timestamp createTime) {
		super();
		this.notificationId = notificationId;
		this.commentId = commentId;
		this.appointmentId = appointmentId;
		this.orderId = orderId;
		this.messageId = messageId;
		this.read = read;
		this.createTime = createTime;
	}

	public Notification() {

	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
