package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_Labels")
public class Labels {

  	@Id
	private String id;
  	@Column(name = "name")
	private String name;
  	@Column(name = "count")
	private long count;
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


	public String getName() {
		return name;
	}

 	public void setName(String name) {
 		this.name = name;
 	}


	public long getCount() {
		return count;
	}

 	public void setCount(long count) {
 		this.count = count;
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
