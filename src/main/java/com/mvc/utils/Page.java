package com.mvc.utils;

import java.util.List;

public class Page<T> {
	// 当前页
	private int pageNo=1;
	// 一页多少条记录
	private int pageSize = 10;
	private String orderBy;
	private String order;
	private long totalCount;
	private int totalPages;
	private boolean isHasPre = true;
	private boolean isHasNext = true;
	private List<T> result;
	private int nextPage;
	private int prePage;
	private String params;

	public int getPageNo() {
		return pageNo;
	}

	public Page<T> setPageNo(int pageNo) {
		this.pageNo = pageNo;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Page<T> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public Page<T> setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public Page<T> setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		return this;
	}


	public boolean getIsHasPre() {
		return isHasPre;
	}

	public void setIsHasPre(boolean isHasPre) {
		this.isHasPre = isHasPre;
	}

	public boolean getIsHasNext() {
		return isHasNext;
	}

	public void setIsHasNext(boolean isHasNext) {
		this.isHasNext = isHasNext;
	}

	public List<T> getResult() {
		return result;
	}

	public Page<T> setResult(List<T> result) {
		this.result = result;
		return this;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Page<T> setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public String getOrder() {
		return order;
	}

	public Page<T> setOrder(String order) {
		this.order = order;
		return this;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
