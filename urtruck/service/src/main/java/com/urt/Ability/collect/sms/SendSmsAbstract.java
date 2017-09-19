package com.urt.Ability.collect.sms;

import java.util.Map;

public abstract class SendSmsAbstract {
	
	public abstract String sendSMS(Map<String,Object> smsContent);
	public abstract String getSendSmsReprot(String reqMsidn);
}
