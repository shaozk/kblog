package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_daily_view_count")
public class DailyViewCount {

  	@Id
	private String id;
  	@Column(name = "view_count")
	private long viewCount;
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


	public long getViewCount() {
		return viewCount;
	}

 	public void setViewCount(long viewCount) {
 		this.viewCount = viewCount;
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
