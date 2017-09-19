package com.urt.web.common.util;

import javax.servlet.http.HttpServletRequest;

public class RequesSession {
	private Long staffId;
	
	public static Long getStaffId(HttpServletRequest q){
		//String id=q.getSession().getAttribute("staffId").toString();
		//暂时默认一个 后期修改 wangfu
		String id="1";
		return  Long.parseLong(id);
	}

/*	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}*/
	
	
	
	
	
	
}
