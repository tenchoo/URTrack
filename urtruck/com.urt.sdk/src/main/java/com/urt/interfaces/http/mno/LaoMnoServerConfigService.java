package com.urt.interfaces.http.mno;

import com.urt.dto.http.mno.LaoMnoServerConfigDto;

/**
 * 外部系统 接口方法 相关查询
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoServerConfigService {


    LaoMnoServerConfigDto selectBySystemIdAndServerId(String systemId, Long serverId);

}
