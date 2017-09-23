package com.urt.web.quartz;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolMembers;

/**
 * 定时查询流量池成员
 * 每月凌晨0点调用移动接口同步信息
 *
 */
public class TimerQueryLaoPoolMember {
	@Autowired
	private SI_QueryDataPoolMembers queryDataPoolMembers;
	Logger log = Logger.getLogger(getClass());
	public void execute(){
		//queryDataPoolMembers.queryDataPoolMembers(Request_ID, Request_Date_Time, EID, Pool_ID, Multi_Records)
	}
}
