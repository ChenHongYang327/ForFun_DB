package member.bean;

import java.sql.Timestamp;

public class Report_page_bean {

	private Integer report_id;
	private Integer whistleblower_id;
	private Integer reported_id;
	private Integer type;
	private String message;
	private Integer report_class;
	private Integer post_id;
	private Integer chatroom_id;
	private Integer item;
	private Timestamp createTime;
	private Timestamp deleteTime;
	
	public Report_page_bean() {}
	
	
	
	public Report_page_bean(Integer report_id, Integer whistleblower_id, Integer reported_id, Integer type,
			String message, Integer report_class, Integer post_id, Integer chatroom_id, Integer item,
			Timestamp createTime, Timestamp deleteTime) {
		super();
		this.report_id = report_id;
		this.whistleblower_id = whistleblower_id;
		this.reported_id = reported_id;
		this.type = type;
		this.message = message;
		this.report_class = report_class;
		this.post_id = post_id;
		this.chatroom_id = chatroom_id;
		this.item = item;
		this.createTime = createTime;
		this.deleteTime = deleteTime;
	}




	public Integer getReport_id() {
		return report_id;
	}

	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}

	public Integer getWhistleblower_id() {
		return whistleblower_id;
	}

	public void setWhistleblower_id(Integer whistleblower_id) {
		this.whistleblower_id = whistleblower_id;
	}

	public Integer getReported_id() {
		return reported_id;
	}

	public void setReported_id(Integer reported_id) {
		this.reported_id = reported_id;
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

	public Integer getReport_class() {
		return report_class;
	}

	public void setReport_class(Integer report_class) {
		this.report_class = report_class;
	}

	public Integer getPost_id() {
		return post_id;
	}

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	public Integer getChatroom_id() {
		return chatroom_id;
	}

	public void setChatroom_id(Integer chatroom_id) {
		this.chatroom_id = chatroom_id;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}
	
	

	

	
	
	

	

}
