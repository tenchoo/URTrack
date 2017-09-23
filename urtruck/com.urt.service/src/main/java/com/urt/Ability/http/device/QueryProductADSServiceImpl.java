package com.urt.Ability.http.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.unicom.util.HttpClientUtil;
import com.urt.interfaces.http.device.QueryProductADSService;
import com.urt.mapper.LaoGoodsOperativeMapper;
import com.urt.po.LaoGoodsOperative;


@Service("queryProductADSService")
public class QueryProductADSServiceImpl implements QueryProductADSService {
    
	private static final Logger log=Logger.getLogger(QueryProductADSServiceImpl.class);
	
	@Resource
	private LaoGoodsOperativeMapper laoGoodsOperativeMapper;

	@Override
	public List<Map<String,Object>> queryProductADS() {
		log.info("enter the method queryProductADS");
		List<Map<String,Object>> info=new ArrayList<Map<String,Object>>();
		List<LaoGoodsOperative> list = laoGoodsOperativeMapper.selectAll();
		if (null!=list && list.size()>0) {
			for (LaoGoodsOperative laoGoodsOperative : list) {
				Map<String,Object> innputMap=new HashMap<String, Object>();
				innputMap.put("pictureUrl", laoGoodsOperative.getPictureurl());
				innputMap.put("pictureLinkUrl", laoGoodsOperative.getPicturelinkurl());
				innputMap.put("displayTag", laoGoodsOperative.getDisplaytag());
				info.add(innputMap);
			}
			log.info("exit the method queryProductADS"+info.toString());
			return info;
		}
		log.info("exit the method queryProductADS 为空了");
		return null;
		
	}
	
	
	public static void main(String[] args){
        JSONObject respJSON=new JSONObject();
		respJSON.put("payAmount", "1");
        respJSON.put("privateAttach", "http%3A%2F%2Fvbtest.lenovomm.cn%2Fcommon%2Fapplay_order.xhtml%3Fc%3DpayWeb");
		respJSON.put("tradeNo", "1201702070557183640727993123");
		respJSON.put("sign_type", "MD5");
		respJSON.put("orderTime", "20170207175743");
		respJSON.put("orderStatus", "1");
		respJSON.put("sign", "efdbdab711c0a885ef85426256492fef");
		respJSON.put("_input_charset", "utf-8");
		respJSON.put("userId", "10070840727");
		respJSON.put("payType", "8");
		respJSON.put("userName", "18515001231");
		respJSON.put("bankOrderid", "2017020721001004140285999617");
		respJSON.put("merchantOrderId", "1011739290000818");
		HttpClientUtil httpClient=HttpClientUtil.getInstance();
		String resp = httpClient.sendHttpPostOfJson("http://h5mobiletest.lenovomm.com/back/payBack", respJSON.toString());
		System.out.println(resp);
	}
     
}
