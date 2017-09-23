package com.urt.interfaces.http.mno;

import java.util.Map;

import com.urt.dto.http.mno.InterfaceHandlerContext;

/**
 * mno 告警信息 查询
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoAlarmService {

    InterfaceHandlerContext flowAlarmRuleHandle(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext flowAlarmQuery(InterfaceHandlerContext handlerContext);

    void flowAlarmPush(Map<String,String> params);
}
