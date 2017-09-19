package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.common.util.Page;
import com.urt.po.LaoUserIpManager;


public interface LaoUserIpManagerExtMapper {
      
	public  List<LaoUserIpManager>  getLaoUserIpByCustId(Long custId);

	public List<Map<String, Object>> queryPage(Page<LaoUserIpManager> page);
	  
}
