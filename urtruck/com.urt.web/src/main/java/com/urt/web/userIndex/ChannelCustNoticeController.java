package com.urt.web.userIndex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoCustomerNoticeDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.userIndex.CorporateClientService;
import com.urt.web.common.util.ActionUtil;

@Controller
@RequestMapping("/custNotice")
public class ChannelCustNoticeController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private CorporateClientService custNoticeService;
	
	// 获取通知list
	@ResponseBody
	@RequestMapping("/custNoticeList")
	public Map<String, Object> noticeList(HttpServletRequest req,
			HttpServletResponse resp, LaoCustomerNoticeDto dto) {
		int pageStart = 1;
		long custId;
		if(!StringUtils.isBlank(req.getParameter("pageStart"))){
			pageStart = Integer.parseInt(req.getParameter("pageStart")
					.toString());// 开始显示的项
		}
		int pageSize =10;
		int pageNo = (pageStart / pageSize) + 1;
		if(!ActionUtil.ifSuperUser(req)){
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			custId=user.getCustId();
			dto.setChannelCustId(custId);
		}else{
			 custId =0L;
			 dto.setChannelCustId(custId);
		}
		Map<String, Object> resultManage = custNoticeService.queryPage(
				dto, pageNo, pageSize);
		return resultManage;
	}

	// 跳转到通知内容页面
	@ResponseBody
	@RequestMapping("/noticeContent")
	public ModelAndView noticeMore(long noticeId) {
		ModelAndView mv = new ModelAndView("userIndex/noticeContent");
		mv.addObject("noticeId",noticeId);
		return mv;
	}
	//根据id查看重要通知内容
	@ResponseBody
	@RequestMapping("/selectNoticeContent")
	public Map<String, Object> selectNoticeContent(long noticeId){
		Map<String, Object> map = new HashMap<String, Object>();
			map = custNoticeService.selectNoticeContent(noticeId);
		return map;
	}
}
