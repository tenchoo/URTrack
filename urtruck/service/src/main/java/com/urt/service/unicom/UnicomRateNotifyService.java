package com.urt.service.unicom;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.urt.Ability.unicom.jsonbean.UnicomNotifyHead;
import com.urt.Ability.unicom.jsonbean.UnicomRateNotifyBody;

@Service("unicomRateNotifyService")
public class UnicomRateNotifyService {
	public UnicomRateNotifyBody parseBody(String xml) {
		UnicomRateNotifyBody body = null;
		if (StringUtils.isNotBlank(xml)) {
			body = new UnicomRateNotifyBody();
			String iccid = StringUtils.substringBetween(xml, "<iccid>","</iccid>");
			String dataUsage = StringUtils.substringBetween(xml, "<dataUsage>", "</dataUsage>");
			body.setIccid(iccid);															
			body.setDataUsage(dataUsage);
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
