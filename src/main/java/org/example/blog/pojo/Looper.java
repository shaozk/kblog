package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_Looper")
public class Looper {

  	@Id
	private String id;
  	@Column(name = "title")
	private String title;
  	@Column(name = "order")
	private long order;
  	@Column(name = "state")
	private String state;
  	@Column(name = "target_url")
	private String targetUrl;
  	@Column(name = "image_url")
	private String imageUrl;
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


	public String getTitle() {
		return title;
	}

 	public void setTitle(String title) {
 		this.title = title;
 	}


	public long getOrder() {
		return order;
	}

 	public void setOrder(long order) {
 		this.order = order;
 	}


	public String getState() {
		return state;
	}

 	public void setState(String state) {
 		this.state = state;
 	}


	public String getTargetUrl() {
		return targetUrl;
	}

 	public void setTargetUrl(String targetUrl) {
 		this.targetUrl = targetUrl;
 	}


	public String getImageUrl() {
		return imageUrl;
	}

 	public void setImageUrl(String imageUrl) {
 		this.imageUrl = imageUrl;
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
