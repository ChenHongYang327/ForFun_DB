package member.bean;

import java.sql.Timestamp;

public class Person_Evaluation {

	private Integer personEvaluationId;
	private Integer orderId;
	private Integer commented;
	private Integer commentedBy;
	private Integer personStar;
	private String personComment;
	private Timestamp createTime;
	private Timestamp updateYime;
	private Timestamp deleteTime;

	public Person_Evaluation(Integer personEvaluationId, Integer orderId, Integer commented, Integer commentedBy,
			Integer personStar, String personComment, Timestamp createTime, Timestamp updateYime,
			Timestamp deleteTime) {
		super();
		this.personEvaluationId = personEvaluationId;
		this.orderId = orderId;
		this.commented = commented;
		this.commentedBy = commentedBy;
		this.personStar = personStar;
		this.personComment = personComment;
		this.createTime = createTime;
		this.updateYime = updateYime;
		this.deleteTime = deleteTime;
	}

	public Person_Evaluation() {

	}

	public Integer getPersonEvaluationId() {
		return personEvaluationId;
	}

	public void setPersonEvaluationId(Integer personEvaluationId) {
		this.personEvaluationId = personEvaluationId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCommented() {
		return commented;
	}

	public void setCommented(Integer commented) {
		this.commented = commented;
	}

	public Integer getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(Integer commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Integer getPersonStar() {
		return personStar;
	}

	public void setPersonStar(Integer personStar) {
		this.personStar = personStar;
	}

	public String getPersonComment() {
		return personComment;
	}

	public void setPersonComment(String personComment) {
		this.personComment = personComment;
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
