package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoUserExpireMMPo;

public interface LaoUserExpireMMPoExtMapper {

	List<HashMap<String, Object>> queryPage(Page<LaoUserExpireMMPo> page);
  
}