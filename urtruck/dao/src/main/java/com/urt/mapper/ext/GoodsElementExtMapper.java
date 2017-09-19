package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.GoodsElement;

public interface GoodsElementExtMapper {
    
    public List<GoodsElement> findGoodsElementByGoodsId(Long goodsId);
    
    public int inserts(List<GoodsElement> list);
}