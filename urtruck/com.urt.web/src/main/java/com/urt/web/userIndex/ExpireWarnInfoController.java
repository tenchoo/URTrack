package com.urt.web.userIndex;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.userIndex.ExpireWarnInfoService;

@Controller
@RequestMapping("/expire")
public class ExpireWarnInfoController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private ExpireWarnInfoService expireWarnInfoService;
	
	@ResponseBody
	@RequestMapping("/expireWarnInfo")
	public List<Map<String, Object>> almInfo(HttpServletRequest req,HttpServletResponse resp,String eventType){
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		long custId=user.getCustId();
		List<Map<String, Object>> warnInfos=null;
		if("1".equals(eventType)){
			warnInfos = expireWarnInfoService.selectExpireWarnInfo(custId);
		}else{
			warnInfos=expireWarnInfoService.selectExpireWarnInfoAll(custId);
		}
		return warnInfos;
	}
}
