package com.urt.web.card;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.CardStatusDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.http.StopOnDto;
import com.urt.interfaces.http.CardActiveService;

@Controller
@RequestMapping(value = "/cardOperation")
public class CardOperation {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private CardActiveService cardActiveService;

	// 跳转到单卡操作页面
	@RequestMapping("/singleCardOperation")
	public ModelAndView singleCardOperation(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("card/operation");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession()
				.getAttribute("sessionUser");
		mv.addObject("custId", user.getCustId());
		return mv;
	}

	// 根据iccid查询卡状态
	@ResponseBody
	@RequestMapping("/selectState")
	public Map<String, Object> selectState(String iccid, String custid) {
		log.info("进入方法selectState");
		log.info("custid=" + custid);
		log.info("iccid=" + iccid);

		Map<String, String> reqInfo = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();
		reqInfo.put("iccid", iccid);
		reqInfo.put("custId", custid);
		reqInfo.put("ifMaintenance", "0");
		reqInfo.put("tradeTypeCode", "110");
		CardStatusDto queryCardStatus = cardActiveService
				.queryCardStatus(reqInfo);
		String cardStatus = queryCardStatus.getCardStatus();// 1在用状态 2停机状态
		log.info("cardStatus=" + cardStatus);
		if ("1".equals(cardStatus)) {
			map.put("retMsg", "1");
		} else if ("2".equals(cardStatus)) {
			map.put("retMsg", "2");
		} else {
			map.put("retMsg", "3");
		}
		return map;
	}

	// 停开机操作
	@ResponseBody
	@RequestMapping("/stopOnCard")
	public ResultMsg stopOnCard(String commend, String iccid, String custid) {
		log.info("进入方法stopOnCard");
		log.info("custid=" + custid);
		log.info("iccid=" + iccid);
		log.info("commend=" + commend);
		log.info("进行停开卡操作");
		ResultMsg msg = new ResultMsg();
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("stateCode", commend);
		hashMap.put("iccid", iccid);
		hashMap.put("custId", custid);
		log.info("hashMap=" + hashMap);
		StopOnDto stopOn = cardActiveService.stopOn(hashMap);
		log.info("stopOn.getIsSuccess()=" + stopOn.getIsSuccess());
		if ("0".equals(stopOn.getIsSuccess())) {
			msg.setSuccess(true);
			msg.setMsg("操作成功！");
		} else {
			msg.setMsg("操作失败！");
		}
		return msg;
	}
}
