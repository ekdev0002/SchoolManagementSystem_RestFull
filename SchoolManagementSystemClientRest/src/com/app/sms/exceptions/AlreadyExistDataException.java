package com.app.sms.exceptions;

@SuppressWarnings("serial")
public class AlreadyExistDataException extends ApplicationException {

	public AlreadyExistDataException(String message) {
		super(message);
	}

}
