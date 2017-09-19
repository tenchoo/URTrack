package com.urt.web.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolInfo;

/**
 * 定时查询流量池
 * 每月定时凌晨0点调用接口同步信息
 *
 */
public class TimerQueryLaoPool {
	@Autowired
	private SI_QueryDataPoolInfo queryDataPoolInfo;
	Logger log = Logger.getLogger(getClass());
	public void execute(){
		//测试数据
		String Request_ID = "c09924c09c034b1f83ad052ae078de1412345678";
		String EID = "c09924c09c034b1f83ad052ae078de1412345678";
		String Request_Date_Time = "20140711135051";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Request_Date_Time = format.format(new Date());
		try {
			String result = queryDataPoolInfo.queryDataPoolInfo(EID);
			JSONObject json = JSON.parseObject(result);
			if("S0000".equals(json.get("Result_Code"))){
				log.info("定时查询流量池成功........");
			}else{
				log.info("调用移动接口失败======定时流量池查询失败.........");
			}
		} catch (Exception e) {
			log.info("定时查询流量失败........");
			e.printStackTrace();
		}
	}
}
