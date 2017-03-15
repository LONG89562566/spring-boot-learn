package com.paradise.pojo;

public class ResponseInfo {
	
	protected int status;
	
	protected String message;

	public static final int STATUS_OK = 0;
	
	public static final int STATUS_ERROR = -1;
	
	public ResponseInfo(){
		
		this.status = STATUS_OK;
		this.message = "ok";
		
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
