package member.bean;

import java.sql.Timestamp;

public class ChatRoom {
	
	
	
	private Integer chatroomId;
	private Integer memberId1;
	private Integer memberId2;
	private Timestamp createTime;
	private Timestamp updateYime;
	private Timestamp deleteTime;
	
	
	
	public ChatRoom(Integer chatroomId, Integer memberId1, Integer memberId2, Timestamp createTime,
			Timestamp updateYime, Timestamp deleteTime) {
		super();
		this.chatroomId = chatroomId;
		this.memberId1 = memberId1;
		this.memberId2 = memberId2;
		this.createTime = createTime;
		this.updateYime = updateYime;
		this.deleteTime = deleteTime;
	}
	
	
	
	public Integer getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(Integer chatroomId) {
		this.chatroomId = chatroomId;
	}
	public Integer getMemberId1() {
		return memberId1;
	}
	public void setMemberId1(Integer memberId1) {
		this.memberId1 = memberId1;
	}
	public Integer getMemberId2() {
		return memberId2;
	}
	public void setMemberId2(Integer memberId2) {
		this.memberId2 = memberId2;
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
