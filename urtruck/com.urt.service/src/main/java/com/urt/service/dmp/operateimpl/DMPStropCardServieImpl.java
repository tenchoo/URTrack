package com.urt.service.dmp.operateimpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.urt.cache.DMPCacheUtil;
import com.urt.dto.CardStatusDto;
import com.urt.dto.http.StopOnDto;
import com.urt.interfaces.dmp.DMPOperateService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.po.LaoDMPCardPo;
import com.urt.service.dmp.entity.CardData;
import com.urt.service.dmp.strategyimpl.DMPRangeAreaServieImpl;

@Service("stopCardServie")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPStropCardServieImpl implements DMPOperateService {
	Logger log = Logger.getLogger(DMPRangeAreaServieImpl.class);
	@Autowired
	private DMPCacheUtil dmpCacheUtil;
	@Autowired
	private CardActiveService cardActiveService;
	@Override
	public boolean OperateExecute(String imei,String custId) {
		log.info("进入停卡的接口");
		log.info("imei="+imei);
		log.info("custId="+custId);
		Gson gson=new Gson();
		boolean b=false;
		try{
			String iccid="";
			String cartDataStr = dmpCacheUtil.getValue("cardStr_" + imei);
			log.info("从redis中获取的卡信息cartDataStr="+cartDataStr);
			if(!StringUtils.isBlank(cartDataStr)){
				CardData cardData=gson.fromJson(cartDataStr, CardData.class);
				iccid=cardData.getIccid();
				log.info("iccid="+iccid);
			}
			
			Map<String, String> reqInfo = new HashMap<String, String>();
			reqInfo.put("iccid", iccid);
			reqInfo.put("custId", custId);
			reqInfo.put("ifMaintenance", "0");
			reqInfo.put("tradeTypeCode", "110");
			CardStatusDto queryCardStatus = cardActiveService.queryCardStatus(reqInfo);
			String cardStatus = queryCardStatus.getCardStatus();//1在用状态  2停机状态
			log.info("cardStatus="+cardStatus);
			if("2".equals(cardStatus)){
				log.info("sim卡处于停卡状态");
				b=true;
			}else{
				Map<String, String> hashMap = new HashMap<>();
				hashMap.put("iccid", iccid);
				hashMap.put("stateCode", "0");//0停卡 1开卡
				hashMap.put("custId", custId);
				StopOnDto stopOn = cardActiveService.stopOn(hashMap);
				if ("0".equals(stopOn.getIsSuccess())) {
					b=true;
				} 
			}
		}catch(Exception e){
			e.printStackTrace();
			b=false;
		}
		 if(b)
		    	log.info("停卡成功");
		    else
		    	log.info("停卡失败");
	    return b;
	}

	
}
