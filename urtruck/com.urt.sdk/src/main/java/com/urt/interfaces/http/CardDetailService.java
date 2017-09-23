package com.urt.interfaces.http;

import java.util.Map;

import com.urt.dto.http.CardDetail;

public interface CardDetailService {

	//查询卡总量明细（客户的卡的数量，分类）
	public CardDetail queryCardDetail(Map<String,String> reqInfo);
}
