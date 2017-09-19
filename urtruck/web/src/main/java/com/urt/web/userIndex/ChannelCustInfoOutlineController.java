package com.urt.web.userIndex;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.userIndex.ChannelCustInfoOutlineService;

@Controller
@RequestMapping("/info")
public class ChannelCustInfoOutlineController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private ChannelCustInfoOutlineService outlineService;
	
	@ResponseBody
	@RequestMapping("/infoOutline")
	public Map<String,Object> initNotice(HttpServletRequest req,HttpServletResponse resp) {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		long custId=user.getCustId();
		Map<String, Object> infoMap = outlineService.getInfoOutline(custId);
		return infoMap;
	}
}
