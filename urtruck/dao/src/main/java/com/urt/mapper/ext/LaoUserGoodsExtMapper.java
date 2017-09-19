package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.Goods;
import com.urt.po.LaoUserGoods;

public interface LaoUserGoodsExtMapper {
	 List<Goods> getGoodsListByIccid(String iccid);
	 
	 LaoUserGoods getUserGoodsByIccid(String iccid);
	 
	 List<Map<String, Object>> getIccidAndGoodsId();
	 
	 List<Map<String, Object>> getIccidAndGoodsIdByDay();
	 
     List<Long> getGoodsListByUserId(Long userId);
	 
	 List<Long> getGoodsList(Long userId);
	 
	 int updateByUserIdAndGoodsId(List<Map<String, Object>> obj);
	 
	 int updateByUseDate(List<Map<String, Object>> obj);
}