package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import com.urt.common.util.Page;
import com.urt.po.LaoDMPLogPo;


public interface LaoDMPLogPoExtMapper {
	List<HashMap<String,Object>> queryPage(Page<LaoDMPLogPo> page);
}