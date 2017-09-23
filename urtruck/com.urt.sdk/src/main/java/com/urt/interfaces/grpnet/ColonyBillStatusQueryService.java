package com.urt.interfaces.grpnet;

import java.util.Map;

import com.urt.dto.grpnet.GetUserDiscntInfoReq;

public interface ColonyBillStatusQueryService {

	//集群网调用第三方接口 查询当月流量 语音 短信等使用情况
	Map<String,Object> queryColonyBillStatus(GetUserDiscntInfoReq infoReq);
}
