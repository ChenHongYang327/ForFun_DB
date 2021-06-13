package member.bean;

import java.sql.Timestamp;

public class Report {
	
	private Integer reportId;
	private Integer whistleblowerId;
	private Integer reportedId;
	private Integer type;
	private String message;
	private Integer erportClass;
	private Integer postId;
	private Integer chatroomId; 
	private Integer item;
	private Timestamp createTime1;
	private Timestamp deleteTime;
	
	
	
	public Report(Integer reportId, Integer whistleblowerId, Integer reportedId, Integer type, String message,
			Integer erportClass, Integer postId, Integer chatroomId, Integer item, Timestamp createTime1,
			Timestamp deleteTime) {
		super();
		this.reportId = reportId;
		this.whistleblowerId = whistleblowerId;
		this.reportedId = reportedId;
		this.type = type;
		this.message = message;
		this.erportClass = erportClass;
		this.postId = postId;
		this.chatroomId = chatroomId;
		this.item = item;
		this.createTime1 = createTime1;
		this.deleteTime = deleteTime;
	}
	
	
	
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Integer getWhistleblowerId() {
		return whistleblowerId;
	}
	public void setWhistleblowerId(Integer whistleblowerId) {
		this.whistleblowerId = whistleblowerId;
	}
	public Integer getReportedId() {
		return reportedId;
	}
	public void setReportedId(Integer reportedId) {
		this.reportedId = reportedId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getErportClass() {
		return erportClass;
	}
	public void setErportClass(Integer erportClass) {
		this.erportClass = erportClass;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(Integer chatroomId) {
		this.chatroomId = chatroomId;
	}
	public Integer getItem() {
		return item;
	}
	public void setItem(Integer item) {
		this.item = item;
	}
	public Timestamp getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(Timestamp createTime1) {
		this.createTime1 = createTime1;
	}
	public Timestamp getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}
	
	
	
}
