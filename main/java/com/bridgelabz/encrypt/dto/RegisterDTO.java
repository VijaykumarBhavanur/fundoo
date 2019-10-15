package com.bridgelabz.encrypt.dto;

public class RegisterDTO
{
	private String emailId;
	private String name;
	private String mobile;
	private String resetToken;
	private String password;
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "RegisterDTO [emailId=" + emailId + ", name=" + name + ", mobile=" + mobile + ", resetToken="
				+ resetToken + ", password=" + password + "]";
	}
	
}
