package com.paradise.pojo;

public class LoginUserInfo extends User{

	/**
	 * 
	 */
	protected static final long serialVersionUID = -2351651414719180612L;
	
	protected String verifyCode;
	
	protected int remember;
	
	
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public int getRemember() {
		return remember;
	}

	public void setRemember(int remember) {
		this.remember = remember;
	}


	
}
