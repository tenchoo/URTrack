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
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.userIndex.CorporateClientService;

@Controller
@RequestMapping("/custIndex")
public class ChannelCustIndexController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private CorporateClientService corporateService;
	//跳转渠道客户首页
	@RequestMapping("/channelCustIndex")
	public ModelAndView intoChannelIndex() {
		ModelAndView mv = new ModelAndView("/userIndex/channelcustindex");
		return mv;
	}
	@ResponseBody
	@RequestMapping("/initNotice")
	public List<Map<String,Object>> initNotice(HttpServletRequest req,HttpServletResponse resp,String eventType) {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		long custId=user.getCustId();
		List<Map<String, Object>> notices=null;
		if("1".equals(eventType)){
			notices = corporateService.selectNotice(custId);
		}else{
			notices=corporateService.selectNoticeAll(custId);
		}
		return notices;
	}
}
