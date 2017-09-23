package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.Goods;

public interface GoodsExtMapper {

    
    public List<Goods> findAll();
    
    public List<Goods> queryGoodsByPage(Page<Goods> page);
    
//    public String getGoodsPricesByGoodsId(String goodsId);
    
    public List<Goods> selGoodsByName(@Param("goodsName")String goodsName);

	public List<Goods> selectGoodsByOperator(@Param("operatorsId")int operatorsId);
	
	public String queryLaoGoodsName(String initproduct);

}