package com.urt.interfaces.http;

import java.util.Map;

import com.urt.dto.CardStatusDto;
import com.urt.dto.http.CardActiveResp;
import com.urt.dto.http.StopOnDto;

public interface CardActiveService {

	public CardActiveResp cardActive(Map<String,String> reqInfo);
	public CardStatusDto queryCardStatus(Map<String,String> reqInfo);
	public StopOnDto stopOn (Map<String, String> reqInfo);
}
