package com.urt.service.removeRatePlan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.RemoveRatePlanFromQueueService;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.interfaces.removeRatePlan.RemoveRatePlanService;
@Service("removeRatePlanService")
@Transactional(propagation = Propagation.REQUIRED)
public class RemoveRatePlanServiceImpl implements RemoveRatePlanService {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private GetTerminalRatingService getTerminalRatingService;//查询流量的联通的API
	
	@Autowired
	private RemoveRatePlanFromQueueService removeRatePlanFromQueueService;//删除联通的API
	
	
	@Override
	public List<String> removeRatePlan(String iccid, String ratePlanName) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>删除资费计划开始");
		SOAPMessage soapMessage1;
		List<String> list = new ArrayList<String>();
		int kill = 0;
		try {
			soapMessage1 = getTerminalRatingService.call(iccid, "20161021113999");
			GetTerminalRatingResponse ws = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage1);
			int flag = 0;
			int end = 0;
			if (ws != null && ws.getList() != null) {
				for (int i = 0; i < ws.getList().size(); i++) {
					list.add(ws.getList().get(i).getRatePlanName());
					/*if (ws.getList().get(i).getRatePlanName().equals("110WLW004085_PRE-IND_500M_30_SP2")) {
						if(i != 0) flag = flag + 1;
						if (flag > 0) {
							log.info(">>>>>>>>>>>>>>>>>>>>110WLW004085_PRE-IND_500M_30_SP2"+ iccid);
							removeRatePlanFromQueueService.call(iccid, "lmh-test-EditTerminal", i-kill+ "","110WLW004085_PRE-IND_500M_30_SP2");
							kill = kill +1;
						}
					}else if (ws.getList().get(i).getRatePlanName().equals("110WLW004085_PRE-IND_500M-0S")) {
						if(i > 0){
							log.info(">>>>>>>>>>>>>>>>>>>>110WLW004085_PRE-IND_500M-os"+ iccid);
							removeRatePlanFromQueueService.call(iccid, "lmh-test-EditTerminal", i-kill+ "","110WLW004085_PRE-IND_500M-0S");
							kill = kill +1;
						}
					}else if (ws.getList().get(i).getRatePlanName().equals("110WLW004085_MON-FIX_5120M-0S")) {
						end = end + 1;
						//删除前面一个。
						if (end > 1) {
							log.info(">>>>>>>>>>>>>>>>>>>>110WLW004085_MON-FIX_5120M-0S"+ iccid);
							removeRatePlanFromQueueService.call(iccid, "lmh-test-EditTerminal", i-kill+ "", "110WLW004085_MON-FIX_5120M-0S");
							kill = kill +1;
						}
					}*/
				}
			}
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>删除资费计划结束："+kill);
		return list;
	}
}
