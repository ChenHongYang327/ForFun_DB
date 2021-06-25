package member.bean;

import java.sql.Timestamp;

public class Post {

	private Integer postId;
	private String  boardId;
	private Integer posterId;
	private String postTitle;
	private String postImg;
	private String content;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp deleteTime;

	public Post () {
		
	}

	public Post(Integer postId, String boardId, Integer posterId, String postTitle, String postImg, String content,
			Timestamp createTime, Timestamp updateTime, Timestamp deleteTime) 
	{
		super();
		this.postId = postId;
		this.boardId = boardId;
		this.posterId = posterId;
		this.postTitle = postTitle;
		this.postImg = postImg;
		this.content = content;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.deleteTime = deleteTime;
	}
	
	

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

}
