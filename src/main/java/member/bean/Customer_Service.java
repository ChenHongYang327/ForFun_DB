package member.bean;

import java.sql.Timestamp;

public class Customer_Service {
	
	private Integer customerId;
	private Integer type;
	private Integer memberId;
	private String nickName;
	private String mail;
	private Integer phone;
	private String mag;
	private Timestamp createTime1;
	private Timestamp deleteTime;
	
	
	public Customer_Service(Integer customerId, Integer type, Integer memberId, String nickName, String mail,
			Integer phone, String mag, Timestamp createTime, Timestamp deleteTime) {
		super();
		this.customerId = customerId;
		this.type = type;
		this.memberId = memberId;
		this.nickName = nickName;
		this.mail = mail;
		this.phone = phone;
		this.mag = mag;
		this.createTime1 = createTime;
		this.deleteTime = deleteTime;
	}	
		
		
		
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public String getMag() {
		return mag;
	}
	public void setMag(String mag) {
		this.mag = mag;
	}
	public Timestamp getCreateTime() {
		return createTime1;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime1 = createTime;
	}
	public Timestamp getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}
	
}
