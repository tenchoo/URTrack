package com.urt.cmpp.test;

import java.net.Socket;

import com.urt.cmpp.util.MsgContainer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class Test {
	
	 
	  
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
//		Long msg_id = 1317431258989306631L;
//		String id = Long.toBinaryString(1317431258989306631L);
//		MsgUtils.formatMsgId(id);
		
//		JSONObject jo = new JSONObject();
//		jo.put("respCode", "9999");
//		jo.put("respDesc", "param is null");
//		JSONArray jsonArray = new JSONArray();     
//        jsonArray.add(0, jo); 
//        JSONObject jo1 = new JSONObject();
//        jo1.element("resultInfo", jsonArray);
//        String jsonStr = "{\"resultInfo\":[{\"respCode\":\"9999\",\"respDesc\":\"param is null\"}]}";
//        JSONObject jo1 = JSONObject.fromObject(jsonStr);
//        System.out.println(jo1);
//		JSONArray jay = jo1.getJSONArray("resultInfo");
//		JSONObject jo2 = (JSONObject) jay.get(0);
//		JSONObject jo3 = (JSONObject) jay.get(1);
//		System.out.println(jo2.get("respCode"));
//		System.out.println(jo2.get("respDesc"));
		
		Socket socket = MsgContainer.getSocketInstance();
		
		MsgContainer.sendMsg("1064855444999", "1064855444999","EngLish");
			//MsgContainer.sendMsg(msg, cusMsisdn)
			while(true){
				MsgContainer.activityTestISMG();
				Thread.sleep(10000);
			}
	}
}
