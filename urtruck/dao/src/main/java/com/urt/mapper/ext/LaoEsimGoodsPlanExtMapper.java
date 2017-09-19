package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoEsimGoodsPlan;


public interface LaoEsimGoodsPlanExtMapper {

	LaoEsimGoodsPlan getGoodsPlanByGoodsId(String goodsId);

	List<LaoEsimGoodsPlan> selectGoodsPlans();
}