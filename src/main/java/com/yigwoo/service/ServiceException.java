package com.yigwoo.service;

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -5142088764457190196L;
	
	private String exceptionMessage;
	
	public ServiceException(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getExceptionMessage() {
		return this.exceptionMessage;
	}
	
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
