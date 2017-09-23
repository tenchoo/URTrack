package com.urt.interfaces.http.mno;

import java.util.List;
import java.util.Map;

import com.urt.dto.http.mno.LaoMnoProvideServerDto;

/**
 * mno 对外提供服务接口配置
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoProvideServerService {

    LaoMnoProvideServerDto selectByServerId(String serverId);

    LaoMnoProvideServerDto selectByServerName(String serverName);

    List<LaoMnoProvideServerDto> queryServerList(Map<String, String> params);

}
