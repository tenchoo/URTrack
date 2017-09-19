package com.urt.interfaces.http.mno;

import com.urt.dto.http.mno.InterfaceHandlerContext;

/**
 * 接口短信
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoSmsService {

    InterfaceHandlerContext sendSmsHandle(InterfaceHandlerContext handlerContext);

}
