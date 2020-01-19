package com.app.sms.exceptions;

public abstract class ApplicationException extends Exception {
	private String message ; 
	public ApplicationException(String message) {
		this.message = message ;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
