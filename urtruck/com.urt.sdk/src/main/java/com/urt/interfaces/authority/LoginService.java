package com.urt.interfaces.authority;

import com.urt.dto.LaoSsAccountDto;

/**
* 类说明：系统登录service
* @author cuichao
* @date 2016年6月20日 下午7:16:18
*/
public interface LoginService {
	
	/**
	 * 功能描述：根据用户名获得用户相关信息
	 * @author cuichao
	 * @date 2016年6月20日 下午7:34:55
	 * @param @param userId
	 * @param @return 
	 * @return Map<String,Object>
	 */
	LaoSsAccountDto getUserInfo(String loginName);
}
