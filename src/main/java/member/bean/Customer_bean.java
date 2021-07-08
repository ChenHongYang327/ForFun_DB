package member.bean;

import java.sql.Timestamp;

public class Customer_bean {

	private Integer customerId;
	private Integer type;
	private Integer memberId;
	private String nickName;
	private String mail;
	private String phone;
	private String mag;
	private Timestamp createTime;
	private Timestamp deleteTime;

	public Customer_bean(Integer customerId, Integer type, Integer memberId, String nickName, String mail,
			String phone, String mag, Timestamp createTime, Timestamp deleteTime) {
		super();
		this.customerId = customerId;
		this.type = type;
		this.memberId = memberId;
		this.nickName = nickName;
		this.mail = mail;
		this.phone = phone;
		this.mag = mag;
		this.createTime = createTime;
		this.deleteTime = deleteTime;
	}

	public Customer_bean() {

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMag() {
		return mag;
	}

	public void setMag(String mag) {
		this.mag = mag;
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
