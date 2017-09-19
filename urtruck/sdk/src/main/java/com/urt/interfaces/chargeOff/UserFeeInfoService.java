package com.urt.interfaces.chargeOff;


public interface UserFeeInfoService {
	int insert(String fee, String recvFee, String payTag, Long tradeId);
}
