package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;


public interface SsUserPoExtMapper {
	/**
	 * 功能描述：根据登录用户Id查询客户Id
	 * @author wangfu
	 * @date 2016年5月23日 下午5:17:30
	 * @param @param param
	 * @param @return 
	 * @return List<Map<String,Object>>
	 */
	   Map<String, Object> selectById(Map<String, Object> param);
}