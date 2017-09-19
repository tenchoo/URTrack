package com.urt.interfaces.http.mno;

import com.urt.dto.http.mno.InterfaceHandlerContext;

/**
 * mno 套餐 相关服务
 *
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoPackageService {


    InterfaceHandlerContext flowQuery(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext productInfoQuery(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext packageInfoQuery(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext productOrderHandle(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext packageChangeHandle(InterfaceHandlerContext handlerContext);

}
