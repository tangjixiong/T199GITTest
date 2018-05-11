package com.t199.entity;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
	private Integer id;
	@NotEmpty(message="用户编码不能为空")
	private String userCode;
	@NotEmpty(message="用户名称不能为空")
	private String userName;
	@NotEmpty(message="密码不能为空")
	@Length(min=6,max=10,message="用户密码长度为6-10位")
	private String userPassword;
	private Integer gender;
	@Past(message="必须是一个过去的时间")
	/*@DateTimeFormat(pattern="yyyy-MM-dd")//处理字符串转日期
*/	@JSONField(format="yyyy-MM-dd")//日期转JSON
	private Date birthday;
	private String phone;
	private String address;
	private Integer userRole;
	private Integer createdBy;
	private Date creationDate;
	private Integer modifyBy;
	private Date modifyDate;
	private String idPicPath;
	private String workPicPath;
	private Integer blance;
	private String userRoleName;
	private Role role;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getIdPicPath() {
		return idPicPath;
	}
	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	public String getWorkPicPath() {
		return workPicPath;
	}
	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}
	public Integer getBlance() {
		return blance;
	}
	public void setBlance(Integer blance) {
		this.blance = blance;
	}
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

}
