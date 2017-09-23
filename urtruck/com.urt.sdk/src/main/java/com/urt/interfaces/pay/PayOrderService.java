package com.urt.interfaces.pay;

import com.alibaba.fastjson.JSONObject;

public interface PayOrderService {
  
	public  JSONObject createOrder(JSONObject paramJson);
}
