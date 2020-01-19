package com.app.sms.models;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement (name="response")
@XmlType(propOrder = {"status", "message"})
public class Response {
	
	@JsonProperty
	private String status;
	
	@JsonProperty
	private String message;

	/**
	 * @param status
	 * @param message
	 */
	public Response() {}
	
	public Response(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + "]" ;
	}
	
	public void display () {
		System.out.println(this);
	}
}
