package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.laoUserOrderMmPo;


public interface laoUserOrderMmPoExtMapper {
	//财务报表分页展示
	List<HashMap<String, Object>> queryPage(Page<laoUserOrderMmPo> page);
	//查询要导出的数据
	List<Map<String, Object>> selectFinancia(@Param("indate")int indate);
}