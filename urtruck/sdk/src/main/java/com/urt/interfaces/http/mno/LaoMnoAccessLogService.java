package com.urt.interfaces.http.mno;

import java.util.List;
import java.util.Map;

import com.urt.dto.http.mno.LaoMnoAccessLogDto;

/**
 * mno接口 访问日志服务
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoAccessLogService {

    int insert(LaoMnoAccessLogDto accessLogDto);

    List<LaoMnoAccessLogDto> queryAccessLog(Map<String, String> param);
}
