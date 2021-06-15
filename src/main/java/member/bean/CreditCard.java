package member.bean;

import java.sql.Timestamp;

public class CreditCard {
	
	
	private Integer creditcardId;
	private Integer memberId;
	private Integer cardNumber;
	private Integer expYear;
	private Integer expMonth;
	private String name;
	private String add;
	private Boolean cardDefault;
	
	
	
	public CreditCard(Integer creditcardId, Integer memberId, Integer cardNumber, Integer expYear, Integer expMonth,
			String name, String add, Boolean cardDefault, Timestamp createTime, Timestamp deleteTime) {
		super();
		this.creditcardId = creditcardId;
		this.memberId = memberId;
		this.cardNumber = cardNumber;
		this.expYear = expYear;
		this.expMonth = expMonth;
		this.name = name;
		this.add = add;
		this.cardDefault = cardDefault;
		this.createTime = createTime;
		this.deleteTime = deleteTime;
	}
	
	
	
	public Integer getCreditcardId() {
		return creditcardId;
	}
	public void setCreditcardId(Integer creditcardId) {
		this.creditcardId = creditcardId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Integer getExpYear() {
		return expYear;
	}
	public void setExpYear(Integer expYear) {
		this.expYear = expYear;
	}
	public Integer getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(Integer expMonth) {
		this.expMonth = expMonth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public Boolean getCardDefault() {
		return cardDefault;
	}
	public void setCardDefault(Boolean cardDefault) {
		this.cardDefault = cardDefault;
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
	private Timestamp createTime;
	private Timestamp deleteTime;
	
	
	
}
