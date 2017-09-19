package com.urt.common.util;

import java.util.List;

public class PageBean<T> {

	private List<T> list;
	
	private Integer totalNums;
	
	private Integer totalPages;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotalNums() {
		return totalNums;
	}

	public void setTotalNums(Integer totalNums) {
		this.totalNums = totalNums;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	
}
