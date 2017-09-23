package com.urt.interfaces.http;

import java.util.Map;

import com.urt.dto.http.CardActiveResp;
import com.urt.dto.http.GoodsIccidDto;

public interface OrderProductService {
	public CardActiveResp orderProduct(Map<String,String> reqInfo);
	public GoodsIccidDto queryProduct(Map<String, String> reqInfo);
}
