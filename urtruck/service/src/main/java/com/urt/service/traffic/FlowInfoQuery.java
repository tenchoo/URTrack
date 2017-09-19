package com.urt.service.traffic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.DongguanCMC.MemberMethodImpl;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.dto.device.CMCFlow;
import com.urt.dto.device.FlowQueryCmc;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("flowInfoQuery")
public class FlowInfoQuery {
	private static final Logger log = Logger.getLogger(FlowInfoQuery.class);
	/*{"resultCode":0,"errorMessage":"调用成功！","mobileNo":"1064856081967",
		"totalinfoList":[{"totalinfo":{"resourcesCount":"0","usedresCount":"17","exceedusedCount":"0","ieavingsCount":"0"}}]}*/  
	@Autowired
	MemberMethodImpl memberMethodImpl;
	
	public static void main(String[] args) {
		//getFlowInfoQuery("");
	}
	
	public  FlowQueryCmc getFlowInfoQuery(String msisdn){
		FlowQueryCmc flow = null;
		List<CMCFlow> cmcFLowList = new ArrayList<CMCFlow>();
		String resultInfo = memberMethodImpl.datausageQuery(msisdn);
		//resultCode  0 成功
		//errorMessage
		//mobileNo
		//totalinfoList  集合
		//totalinfo      对象
		//resourcesCount  套餐总量
		//usedresCount    已使用总量
		//exceedusedCount  超出套餐部分使用量
		//ieavingsCount     剩余累计量
		//String json = "{\"resultCode\":0,\"errorMessage\":\"调用成功！\",\"mobileNo\":\"1064856081967\","
		//		+ "\"totalinfoList\":[{\"totalinfo\":{\"resourcesCount\":\"0\",\"usedresCount\":\"17\",\"exceedusedCount\":\"0\",\"ieavingsCount\":\"0\"}}]}";
		if(!ToolsUtil.checkStringIsNull(resultInfo)){
			flow = new FlowQueryCmc();
			JSONObject fromObject = JSONObject.fromObject(resultInfo);
			String  resultCode= fromObject.getString("resultCode");
			String  errorMessage= fromObject.getString("errorMessage");
			String  mobileNo= fromObject.getString("mobileNo");
			flow.setRespCode(resultCode);;
			flow.setRespDesc(errorMessage);
			flow.setMsidn(mobileNo);		
			JSONArray rspobject = fromObject.getJSONArray("totalinfoList");
			try {
				if (rspobject!=null) {
					for(int a=0;a<rspobject.size();a++){
						CMCFlow cmcFlow = new CMCFlow();
						JSONObject res = rspobject.getJSONObject(a).getJSONObject("totalinfo");
						cmcFlow.setResourcesCount(res.get("resourcesCount").toString());
						cmcFlow.setExceedusedCount(res.get("exceedusedCount").toString());
						cmcFlow.setIeavingsCount(res.get("ieavingsCount").toString());
						cmcFlow.setUsedresCount(res.get("usedresCount").toString());
						cmcFLowList.add(cmcFlow);
					}
				}else{
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<无查询到流量信息-------------");
				}
			} catch (Exception e) {
				log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<无查询到流量信息-------------");
				e.printStackTrace();
			}
			flow.setFlowInfo(cmcFLowList);
		}
		return flow;
	}
}
