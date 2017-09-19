package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.DItem;

public interface DItemExtMapper {
	// 根据账目名称查询
    DItem selectByItemName(String itemName);
	// 查询全部数据
    List<DItem> selectAll();
    
    //查询第一级别的账目项
    List<DItem> selDItemByPItemId();
}
