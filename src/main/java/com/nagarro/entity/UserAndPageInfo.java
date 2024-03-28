package com.nagarro.entity;

public class UserAndPageInfo {

	private ValidatedUserData userData;

	private PageInfoEntity pageInfo;

	public UserAndPageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAndPageInfo(ValidatedUserData user, PageInfoEntity pageInfo) {
		super();
		this.userData = user;
		this.pageInfo = pageInfo;
	}

	public ValidatedUserData getUserData() {
		return userData;
	}

	public void setUserData(ValidatedUserData userData) {
		this.userData = userData;
	}

	public PageInfoEntity getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoEntity pageInfo) {
		this.pageInfo = pageInfo;
	}

}
