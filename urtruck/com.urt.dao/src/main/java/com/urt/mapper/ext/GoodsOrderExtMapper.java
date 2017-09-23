package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.Goods;
import com.urt.po.LaoUser;

public interface GoodsOrderExtMapper {

	List<LaoUser> queryLaoUser();
	List<LaoUser> queryLaoUserCon(Map<String, Object> param);
	List<Goods> queryLaoGoods(Map<String, Object> param);
	List<Goods> queryLaoTestGoods(Map<String, Object> param);
	List<LaoUser> queryLaoUserByCustId(Long custId);
	List<Goods> queryLaoGoodsTSP(Map<String, Object> param);
}
