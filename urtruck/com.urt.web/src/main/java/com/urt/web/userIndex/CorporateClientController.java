package com.urt.web.userIndex;

import java.util.HashMap;
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
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.userIndex.CorporateClientService;
import com.urt.web.common.util.ActionUtil;

@Controller
@RequestMapping("/corporate")
public class CorporateClientController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private CorporateClientService corporateService;
	@Autowired
	LaoCustomerService customerService;
	
	//跳转企业客户首页
		@RequestMapping("/corporateIndex")
		public ModelAndView intoChannelIndex() {
			ModelAndView mv = new ModelAndView("/userIndex/corporateindex");
			return mv;
		}
	//获取重要通知
	@ResponseBody
	@RequestMapping("/notice")
	public List<Map<String, Object>> selectNotice(HttpServletRequest req,HttpServletResponse resp){
		List<Map<String, Object>> notices=null;
		long custId;
		if(!ActionUtil.ifSuperUser(req)){
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			 custId=user.getCustId();
		}else{
			 custId =0L;
		}
		notices = corporateService.selectNotice(custId);
		log.info("查询首页展示的通知内容"+notices);
		return notices;
	}
	// 点击查看更多重要通知
	@ResponseBody
	@RequestMapping("/noticeMore")
	public ModelAndView noticeMore() {
		ModelAndView mv = new ModelAndView("userIndex/noticeAll");
		return mv;
	}

	//本月订购产品echars
	@ResponseBody
	@RequestMapping("/corporateGoodsEcharts")
	public Map<String, Object> corporateGoodsEcharts(HttpServletRequest req) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		map = customerService.selectGoodsCorporate(custId);
		log.info("本月订购产品"+map);
		return map;
	}
	//运营商echars
	@ResponseBody
	@RequestMapping("/corporateEcharts")
	public Map<String, Object> corporateEcharts(HttpServletRequest req) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		map = customerService.selectCorporate(custId);
		return map;
	}

}
