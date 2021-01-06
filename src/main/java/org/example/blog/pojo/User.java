package org.example.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_User")
public class User {

  	@Id
	private String id;
  	@Column(name = "user_name")
	private String userName;
  	@Column(name = "password")
	private String password;
  	@Column(name = "roles")
	private String roles;
  	@Column(name = "avatar")
	private String avatar;
  	@Column(name = "email")
	private String email;
  	@Column(name = "sign")
	private String sign;
  	@Column(name = "state")
	private String state;
  	@Column(name = "reg_ip")
	private String regIp;
  	@Column(name = "login_ip")
	private String loginIp;
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


	public String getUserName() {
		return userName;
	}

 	public void setUserName(String userName) {
 		this.userName = userName;
 	}


	public String getPassword() {
		return password;
	}

 	public void setPassword(String password) {
 		this.password = password;
 	}


	public String getRoles() {
		return roles;
	}

 	public void setRoles(String roles) {
 		this.roles = roles;
 	}


	public String getAvatar() {
		return avatar;
	}

 	public void setAvatar(String avatar) {
 		this.avatar = avatar;
 	}


	public String getEmail() {
		return email;
	}

 	public void setEmail(String email) {
 		this.email = email;
 	}


	public String getSign() {
		return sign;
	}

 	public void setSign(String sign) {
 		this.sign = sign;
 	}


	public String getState() {
		return state;
	}

 	public void setState(String state) {
 		this.state = state;
 	}


	public String getRegIp() {
		return regIp;
	}

 	public void setRegIp(String regIp) {
 		this.regIp = regIp;
 	}


	public String getLoginIp() {
		return loginIp;
	}

 	public void setLoginIp(String loginIp) {
 		this.loginIp = loginIp;
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
