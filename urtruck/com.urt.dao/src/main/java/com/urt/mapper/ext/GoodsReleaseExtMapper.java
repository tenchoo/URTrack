package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.GoodsRelease;

public interface GoodsReleaseExtMapper {
    
    public List<GoodsRelease> findAll();
    
    public List<GoodsRelease> findByChannelGroupId(Long channelGroupId);
    
    public List<GoodsRelease> findByGoodsId(Long goodsId);
    
	public List<GoodsRelease> queryPage(Page<GoodsRelease> page);  
}