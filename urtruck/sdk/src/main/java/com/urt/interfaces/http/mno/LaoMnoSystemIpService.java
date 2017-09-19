package com.urt.interfaces.http.mno;

import java.util.List;

import com.urt.dto.http.mno.LaoMnoSystemIpDto;

/**
 * 外部系统IP拦截
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoSystemIpService {

    List<LaoMnoSystemIpDto> selectBySystemId(String systemId);

}
