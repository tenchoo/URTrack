package com.urt.interfaces.http.mno;

import java.util.Map;

import com.urt.dto.http.mno.InterfaceHandlerContext;

public interface LaoMnoCardInfoService {

    void cardInfoPush(Map<String, String> params);

    void ftpDownloadPush(Map<String, String> params);

    InterfaceHandlerContext certificationHandle(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext apnStateChangeHandle(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext cardStateQuery(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext cardStateChangeHandle(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext cardInfoQuery(InterfaceHandlerContext handlerContext);

    InterfaceHandlerContext cardServerOperHandle(InterfaceHandlerContext handlerContext);


}
