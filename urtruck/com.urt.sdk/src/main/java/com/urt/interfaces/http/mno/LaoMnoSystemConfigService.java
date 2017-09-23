package com.urt.interfaces.http.mno;

import com.urt.dto.http.mno.LaoMnoSystemConfigDto;

/**
 * mno外部系统配置
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoSystemConfigService {

	LaoMnoSystemConfigDto querySystemConfig(String systemId);
	
}
