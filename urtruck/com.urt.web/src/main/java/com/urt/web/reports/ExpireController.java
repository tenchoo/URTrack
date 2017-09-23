package com.urt.web.reports;

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
import com.urt.dto.reports.LaoUserExpireMMDto;
import com.urt.interfaces.reports.ExpireService;
import com.urt.web.common.util.ActionUtil;

@Controller
@RequestMapping("/expire")
public class ExpireController {
	private static final Logger log = Logger.getLogger(CardCycleController.class);
	@Autowired
	private ExpireService expireService;
	//跳转页面
	@RequestMapping("/expireIndex")
	public ModelAndView intoChannelIndex() {
		ModelAndView mv = new ModelAndView("/reportsIndex/expire");
		return mv;
	}
	// 获取到期list
		@ResponseBody
		@RequestMapping("/expireList")
		public Map<String, Object> deviceList(HttpServletRequest req,
				HttpServletResponse resp, LaoUserExpireMMDto dto) {
			int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
					.toString());// 开始显示的项
			int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
					.toString());// 显示多少项
			int pageNo = (pageStart / pageSize) + 1;
			if(!ActionUtil.ifSuperUser(req)){
				LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
				dto.setChannelCustId(user.getCustId());
			}
			Map<String, Object> resultManage = expireService.queryPage(
					dto, pageNo, pageSize);
			log.info("到期list====================="+resultManage);
			return resultManage;
		}
}
