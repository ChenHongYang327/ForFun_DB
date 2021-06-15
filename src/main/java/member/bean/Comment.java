package member.bean;

import java.sql.Timestamp;

public class Comment {

	private Integer commentId;
	private Integer memberId;
	private Integer postId;
	private String commentMsg;
	private Boolean read;
	private Timestamp createTime;
	private Timestamp updateYime;
	private Timestamp deleteTime;

	public Comment(Integer commentId, Integer memberId, Integer postId, String commentMsg, Boolean read,
			Timestamp createTime, Timestamp updateYime, Timestamp deleteTime) {
		super();
		this.commentId = commentId;
		this.memberId = memberId;
		this.postId = postId;
		this.commentMsg = commentMsg;
		this.read = read;
		this.createTime = createTime;
		this.updateYime = updateYime;
		this.deleteTime = deleteTime;
	}

	public Comment() {

	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getCommentMsg() {
		return commentMsg;
	}

	public void setCommentMsg(String commentMsg) {
		this.commentMsg = commentMsg;
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

	public Timestamp getUpdateYime() {
		return updateYime;
	}

	public void setUpdateYime(Timestamp updateYime) {
		this.updateYime = updateYime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

}
