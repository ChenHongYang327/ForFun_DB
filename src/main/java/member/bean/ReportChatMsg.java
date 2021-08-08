package member.bean;

import java.sql.Timestamp;

public class ReportChatMsg {

	private Integer report_id;
	private Integer whistleblower_id;
	private Member member; // reported_id;
	private Integer type;
	private String message;
	private Integer report_class;
	private Integer post_id;
	private Comment comment; // chatroom_id 留言
	private Integer item;
	private Timestamp createTime;
	private Timestamp deleteTime;

	public ReportChatMsg() {

	}

	public ReportChatMsg(Integer report_id, Integer whistleblower_id, Member member, Integer type, String message,
			Integer report_class, Integer post_id, Comment comment, Integer item, Timestamp createTime,
			Timestamp deleteTime) {
		super();
		this.report_id = report_id;
		this.whistleblower_id = whistleblower_id;
		this.member = member;
		this.type = type;
		this.message = message;
		this.report_class = report_class;
		this.post_id = post_id;
		this.comment = comment;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
