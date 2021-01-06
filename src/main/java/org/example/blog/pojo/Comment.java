package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_Comment")
public class Comment {

  	@Id
	private String id;
  	@Column(name = "parent_content")
	private String parentContent;
  	@Column(name = "article_id")
	private String articleId;
  	@Column(name = "content")
	private String content;
  	@Column(name = "user_id")
	private String userId;
  	@Column(name = "user_avatar")
	private String userAvatar;
  	@Column(name = "user_name")
	private String userName;
  	@Column(name = "state")
	private String state;
  	@Column(name = "create_time")
	private java.sql.Timestamp createTime;
  	@Column(name = "update_time")
	private java.sql.Timestamp updateTime;


	public String getId() {
		return id;
	}

 	public void setId(String id) {
 		this.id = id;
 	}


	public String getParentContent() {
		return parentContent;
	}

 	public void setParentContent(String parentContent) {
 		this.parentContent = parentContent;
 	}


	public String getArticleId() {
		return articleId;
	}

 	public void setArticleId(String articleId) {
 		this.articleId = articleId;
 	}


	public String getContent() {
		return content;
	}

 	public void setContent(String content) {
 		this.content = content;
 	}


	public String getUserId() {
		return userId;
	}

 	public void setUserId(String userId) {
 		this.userId = userId;
 	}


	public String getUserAvatar() {
		return userAvatar;
	}

 	public void setUserAvatar(String userAvatar) {
 		this.userAvatar = userAvatar;
 	}


	public String getUserName() {
		return userName;
	}

 	public void setUserName(String userName) {
 		this.userName = userName;
 	}


	public String getState() {
		return state;
	}

 	public void setState(String state) {
 		this.state = state;
 	}


	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

 	public void setCreateTime(java.sql.Timestamp createTime) {
 		this.createTime = createTime;
 	}


	public java.sql.Timestamp getUpdateTime() {
		return updateTime;
	}

 	public void setUpdateTime(java.sql.Timestamp updateTime) {
 		this.updateTime = updateTime;
 	}

}
