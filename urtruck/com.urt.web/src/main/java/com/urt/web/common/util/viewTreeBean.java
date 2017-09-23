package com.urt.web.common.util;

import java.util.List;

/**
 * 类说明：
 * @author zhaoxy9
 * @date 2016年6月22日 下午7:49:40
 */
public class viewTreeBean {

	private Long id;
	private String name;
	private boolean check;
	private List<viewTreeBean> viewTreeBeans;
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<viewTreeBean> getViewTreeBeans() {
		return viewTreeBeans;
	}
	public void setViewTreeBeans(List<viewTreeBean> viewTreeBeans) {
		this.viewTreeBeans = viewTreeBeans;
	}
	
}
