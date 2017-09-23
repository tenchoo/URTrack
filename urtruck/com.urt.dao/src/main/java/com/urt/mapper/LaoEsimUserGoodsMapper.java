package com.urt.mapper;

import com.urt.po.LaoEsimUserGoods;

public interface LaoEsimUserGoodsMapper {
    int insert(LaoEsimUserGoods record);

    int insertSelective(LaoEsimUserGoods record);

	int updateUserGoods(LaoEsimUserGoods goods);
}