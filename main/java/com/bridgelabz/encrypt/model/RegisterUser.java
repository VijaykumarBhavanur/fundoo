package com.bridgelabz.encrypt.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Register")
public class RegisterUser 
{
	
	@Id
	private String id;
	private String emailId;
	private String name;
	private String mobile;
	private String password;
	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public String toString() {
		return "RegisterUser [emailId=" + emailId + ", name=" + name + ", mobile=" + mobile + ", password=" + password
				+ "]";
	}
}
