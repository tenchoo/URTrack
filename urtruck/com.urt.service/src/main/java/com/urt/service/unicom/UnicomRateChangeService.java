package com.urt.service.unicom;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.unicom.jsonbean.UnicomNotifyHead;
import com.urt.Ability.unicom.jsonbean.UnicomRateChangeBody;

@Service("unicomRateChangeService")
@Transactional
public class UnicomRateChangeService {
	public UnicomRateChangeBody parseBody(String xml) {
		UnicomRateChangeBody body = null;
		if (StringUtils.isNotBlank(xml)) {
			body = new UnicomRateChangeBody();
			String iccid = StringUtils.substringBetween(xml, "<iccid>","</iccid>");
			String oldRatePlanName = StringUtils.substringBetween(xml, "<oldRatePlanName>","</oldRatePlanName>");
			String newRatePlanName = StringUtils.substringBetween(xml,"<newRatePlanName>", "</newRatePlanName>");
			String dataChanged = StringUtils.substringBetween(xml,"<dataChanged>", "</dataChanged>");
			body.setIccid(iccid);
			body.setOldRatePlanName(oldRatePlanName);
			body.setNewRatePlanName(newRatePlanName);
			body.setDataChanged(dataChanged);
		}
		return body;
	}
	
	public UnicomNotifyHead parseHead(Map<String, String> params){
		UnicomNotifyHead head = new UnicomNotifyHead();
		head.setEventId(params.get("eventId"));
		head.setEventType(params.get("eventType"));
		head.setTimestamp(params.get("timestamp"));
		head.setSignature(params.get("signature"));
		head.setSignature2(params.get("signature2"));

		return head;
	}
}
