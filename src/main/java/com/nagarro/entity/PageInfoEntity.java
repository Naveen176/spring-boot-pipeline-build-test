package com.nagarro.entity;

public class PageInfoEntity {
	long total;
	boolean hasNextPage;
	boolean hasPreviousPage;
	
	public PageInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PageInfoEntity(long total, boolean hasNextPage, boolean hasPreviousPage) {
		super();
		this.total = total;
		this.hasNextPage = hasNextPage;
		this.hasPreviousPage = hasPreviousPage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
}
