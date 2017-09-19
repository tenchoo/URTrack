package com.urt.web.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.interfaces.apiMsg.ApiMsgLogService;

public class BatchMonthPackages {
	@Autowired
	private ApiMsgLogService apiMsgLogService;
	public void execute() {
		//获取需要所有同步的iccid，goodsId信息
		List<Map<String, Object>> maps = apiMsgLogService.getIccidAndGoodsIdByDay();
		//往kafka丢数据
		List<Map<String, Object>> mapList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:maps){
			map.put("iccid", map.get("ICCID"));
			mapList.add(map);
		}
		apiMsgLogService.sendButtomlessInfo(mapList);
	}
}
