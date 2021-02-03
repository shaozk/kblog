package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_categories")
public class Category {

  	@Id
	private String id;
  	@Column(name = "name")
	private String name;
  	@Column(name = "pinyin")
	private String pinyin;
  	@Column(name = "description")
	private String description;
  	@Column(name = "order_")
	private long order = 1;
  	@Column(name = "status")
	private String status;
  	@Column(name = "create_time")
	private Date createTime;
  	@Column(name = "update_time")
	private Date updateTime;

	public String getId() {
		return id;
	}

 	public void setId(String id) {
 		this.id = id;
 	}


	public String getName() {
		return name;
	}

 	public void setName(String name) {
 		this.name = name;
 	}


	public String getPinyin() {
		return pinyin;
	}

 	public void setPinyin(String pinyin) {
 		this.pinyin = pinyin;
 	}


	public String getDescription() {
		return description;
	}

 	public void setDescription(String description) {
 		this.description = description;
 	}


	public long getOrder() {
		return order;
	}

 	public void setOrder(long order) {
 		this.order = order;
 	}


	public String getStatus() {
		return status;
	}

 	public void setStatus(String status) {
 		this.status = status;
 	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
