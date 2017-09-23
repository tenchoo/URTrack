package com.urt.interfaces.esim;


import com.urt.dto.LaoEsimGoodsDto;

public interface EsimUserService {

	String insertUser(String orderStatus,String username, LaoEsimGoodsDto dto, String eid, String iccid,String goodsStatus,String mactingId,String dpaddress);
	public void upDataUser(String userId ,String goodsCycle);
	Long insertTrade(String username, String eid, String iccid, String countryId, Long goodsId, String tradeTypeCode);
	void insertBunding(Long userId, String eid);
	void inSertUserGoods(String goodsCycle, String goodsId, Long userId,String goodsStatus);
	 void insertUserGiven(Long userId,String goodsId,String username,String usernameTarge,String eid);
	public void updateTrade(String statusCode,String tradeId);
	void updateUserBunding(Long userId, String eid);
}
