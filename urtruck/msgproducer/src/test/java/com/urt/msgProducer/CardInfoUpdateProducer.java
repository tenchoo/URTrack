package com.urt.msgProducer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.msgProducter.trade.CardInfoProducer;
import com.urt.msgProducter.trade.CardStatusTestProducer;


public class CardInfoUpdateProducer{
	@Autowired 
	private CardInfoProducer cardInfoProducer;
	public void sendMessage(List<Map<String, Object>> iccids) {
		
		for(Map<String, Object> iccid:iccids){
			Gson gson=new Gson();
			String jsonStr=gson.toJson(iccid);
			/*cardInfoProducer.sendTask(jsonStr);*/
		}
	} 
}
