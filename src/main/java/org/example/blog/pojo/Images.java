package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_Images")
public class Images {

  	@Id
	private String id;
  	@Column(name = "user_id")
	private String userId;
  	@Column(name = "url")
	private String url;
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


	public String getUserId() {
		return userId;
	}

 	public void setUserId(String userId) {
 		this.userId = userId;
 	}


	public String getUrl() {
		return url;
	}

 	public void setUrl(String url) {
 		this.url = url;
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
