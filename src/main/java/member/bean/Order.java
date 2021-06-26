package member.bean;

import java.sql.Timestamp;

public class Order {

	private Integer orderId;
	private Integer publishId;
	private Integer tenantId;
	private Integer publishStar;
	private String publishComment;
	private Integer orderStatus;
	private Boolean read;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp deleteTime;

	public Order(Integer orderId, Integer publishId, Integer tenantId, Integer publishStar, String publishComment,
			Integer orderStatus, Boolean read, Timestamp createTime, Timestamp updateYime, Timestamp deleteTime) {
		super();
		this.orderId = orderId;
		this.publishId = publishId;
		this.tenantId = tenantId;
		this.publishStar = publishStar;
		this.publishComment = publishComment;
		this.orderStatus = orderStatus;
		this.read = read;
		this.createTime = createTime;
		this.updateTime = updateYime;
		this.deleteTime = deleteTime;
	}

	public Order() {

	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPublishId() {
		return publishId;
	}

	public void setPublishId(Integer publishId) {
		this.publishId = publishId;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getPublishStar() {
		return publishStar;
	}

	public void setPublishStar(Integer publishStar) {
		this.publishStar = publishStar;
	}

	public String getPublishComment() {
		return publishComment;
	}

	public void setPublishComment(String publishComment) {
		this.publishComment = publishComment;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer roderStatus) {
		this.orderStatus = orderStatus;
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
		return updateTime;
	}

	public void setUpdateYime(Timestamp updateYime) {
		this.updateTime = updateYime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

}
