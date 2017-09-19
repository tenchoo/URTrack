package com.urt.service.unicom;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.unicom.jsonbean.UnicomNotifyChangeBody;
import com.urt.Ability.unicom.jsonbean.UnicomNotifyChangeHead;

@Service("unicomNotifyChangeService")
@Transactional
public class UnicomNotifyChangeService {
	/**
	 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
	 * <SimDataLimit xmlns="http://api.jasperwireless.com/ws/schema">
	 * <iccid>8986061501000889172</iccid>
	 * <bodyName>Company110WLW004085</bodyName>
	 * <ratePlanName>110WLW004085_PRE-IND_500M-0S</ratePlanName>
	 * <totalIncludedUsage>524288000</totalIncludedUsage>
	 * <totalActualUsage>65935360</totalActualUsage> 
	 * </SimDataLimit>
	 * 
	 * @param xml
	 * @return 
	 */
	public UnicomNotifyChangeBody parseBody(String xml) {
		UnicomNotifyChangeBody body = null;
		if (StringUtils.isNotBlank(xml)) {
			body = new UnicomNotifyChangeBody();
			String iccid = StringUtils.substringBetween(xml, "<iccid>","</iccid>");
			
			String bodyName = StringUtils.substringBetween(xml, "<bodyName>","</bodyName>");
			String ratePlanName = StringUtils.substringBetween(xml,"<ratePlanName>", "</ratePlanName>");
			String totalIncludedUsage = StringUtils.substringBetween(xml,"<totalIncludedUsage>", "</totalIncludedUsage>");
			String totalActualUsage = StringUtils.substringBetween(xml,"<totalActualUsage>", "</totalActualUsage>");
			body.setIccid(iccid);
			body.setBodyName(bodyName);
			body.setRatePlanName(ratePlanName);
			body.setTotalIncludedUsage(totalIncludedUsage);
			body.setTotalActualUsage(totalActualUsage);
		}
		return body;
	}
	
	public UnicomNotifyChangeHead parseHead(Map<String, String> params){
		UnicomNotifyChangeHead head = new UnicomNotifyChangeHead();
		head.setEventId(params.get("eventId"));
		head.setEventType(params.get("eventType"));
		head.setTimestamp(params.get("timestamp"));
		head.setSignature(params.get("signature"));
		head.setSignature2(params.get("signature2"));

		return head;
	}

}
