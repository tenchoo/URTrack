package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoEsimGoods;

public interface LaoEsimGoodsExtMapper {
    
	List<LaoEsimGoods> queryGoods();

	LaoEsimGoods findGoodByGoodsId(Long goodsId);

	LaoEsimGoods findGoodsByUsername(String username);

}
