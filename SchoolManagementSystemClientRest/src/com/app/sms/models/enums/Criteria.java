package com.app.sms.models.enums;

public enum Criteria {
	Contains ("CONTAINS"),
	Equal("using ="),
	Like("using LIKE");
	
	private String criteria ;

	/**
	 * @param criteria
	 */
	private Criteria(String criteria) {
		this.criteria = criteria;
	}
	
	public String criteria() {
		return criteria;
	}
}
