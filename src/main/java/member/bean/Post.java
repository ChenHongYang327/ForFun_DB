package member.bean;

import java.sql.Timestamp;

public class Post {
	
	
	
	private Integer postId;
	private Integer boardId;
	private Integer posterId;
	private String postTitle;
	private String postImg;
	private String content;
	private Timestamp createTime;
	private Timestamp updateYime;
	private Timestamp deleteTime;
	
	
	
	public Post(Integer postId, Integer boardId, Integer posterId, String postTitle, String postImg, String content,
			Timestamp createTime, Timestamp updateYime, Timestamp deleteTime) {
		super();
		this.postId = postId;
		this.boardId = boardId;
		this.posterId = posterId;
		this.postTitle = postTitle;
		this.postImg = postImg;
		this.content = content;
		this.createTime = createTime;
		this.updateYime = updateYime;
		this.deleteTime = deleteTime;
	}
	
	
	
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public Integer getPosterId() {
		return posterId;
	}
	public void setPosterId(Integer posterId) {
		this.posterId = posterId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostImg() {
		return postImg;
	}
	public void setPostImg(String postImg) {
		this.postImg = postImg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
