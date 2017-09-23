package com.urt.interfaces.http.mno;

import com.urt.dto.http.mno.InterfaceHandlerContext;

/**
 * mno 接口校验
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoInterfaceCheckService {

    InterfaceHandlerContext checkSign(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext checkIp(InterfaceHandlerContext handlerContext);
}
