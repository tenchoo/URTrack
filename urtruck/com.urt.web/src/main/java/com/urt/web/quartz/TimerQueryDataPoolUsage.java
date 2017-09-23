package com.urt.web.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.ShangHaiCMC.QueryLaoPool;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolUsage;
/**
 * 定时查询流量池的流量
 * 每隔5~10分钟执行一次
 *
 */
public class TimerQueryDataPoolUsage {
	@Autowired
	private SI_QueryDataPoolUsage queryDataPoolUsage;
	@Autowired
	private QueryLaoPool queryLaoPool;
	public void execute(){
		//测试数据
		String EID = "c09924c09c034b1f83ad052ae078de1412345678";
		String Request_Date_Time = "20170718164759";
		String Request_ID = "c09924c09c034b1f83ad052ae078de1412345678";
		Logger log = Logger.getLogger(getClass());
		//获得流量池的ID
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
			Request_Date_Time = date.format(new Date());
			List<String> poolIds = queryLaoPool.queryPool();
			for (String poolId : poolIds) {
				String result = queryDataPoolUsage.queryDataPoolUsage(EID, poolId);
				JSONObject json = JSON.parseObject(result);
				if("S0000".equals(json.get("Result_Code"))){
					log.info("定时查询流量池流量开始查询........");
				}else{
					log.info("调用移动接口失败======定时查询失败.........");
					return ;
				}
			}
		} catch (Exception e) {
			log.info("定时查询失败.........");
			e.printStackTrace();
		}
		
	}
}
