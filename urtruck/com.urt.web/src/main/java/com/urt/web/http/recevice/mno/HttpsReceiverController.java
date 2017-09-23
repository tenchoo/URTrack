package com.urt.web.http.recevice.mno;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.common.util.DateUtil;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.dto.http.mno.LaoMnoProvideServerDto;
import com.urt.dto.http.mno.LaoMnoServerConfigDto;
import com.urt.dto.http.mno.LaoMnoSystemConfigDto;
import com.urt.dto.http.mno.MnoInterfaceResponseDto;
import com.urt.interfaces.http.mno.LaoMnoAccessLogService;
import com.urt.interfaces.http.mno.LaoMnoAlarmService;
import com.urt.interfaces.http.mno.LaoMnoCardInfoService;
import com.urt.interfaces.http.mno.LaoMnoInterfaceCheckService;
import com.urt.interfaces.http.mno.LaoMnoPackageService;
import com.urt.interfaces.http.mno.LaoMnoProvideServerService;
import com.urt.interfaces.http.mno.LaoMnoServerConfigService;
import com.urt.interfaces.http.mno.LaoMnoServerTaskService;
import com.urt.interfaces.http.mno.LaoMnoSmsService;
import com.urt.interfaces.http.mno.LaoMnoSystemConfigService;
import com.urt.interfaces.http.mno.LaoMnoSystemIpService;
import com.urt.modules.mapper.JsonMapper;
import com.urt.web.common.util.StringUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;


/**
 * @author Tian Peng4
 * @date 2017/8/17
 * <p>
 * 为TSP提供的统一接口平台，通讯使用https方式
 */
@Controller
@RequestMapping("/httpsOpenServer")
public class HttpsReceiverController {
    /**
     * 日志
     ****/
    protected static final Logger logger = Logger.getLogger(HttpsReceiverController.class);

    private final static String DEFAULT_CHARSET = "UTF-8";

    @Autowired
    LaoMnoSystemConfigService laoMnoSystemConfigService;

    @Autowired
    LaoMnoServerConfigService laoMnoServerConfigService;

    @Autowired
    LaoMnoSystemIpService laoMnoSystemIpService;

    @Autowired
    LaoMnoAccessLogService laoMnoAccessLogService;

    @Autowired
    LaoMnoInterfaceCheckService laoMnoInterfaceCheckService;

    @Autowired
    LaoMnoProvideServerService laoMnoProvideServerService;

    //手动映射处理类
    @Autowired
    LaoMnoCardInfoService laoMnoCardInfoService;
    @Autowired
    LaoMnoPackageService laoMnoPackageService;
    @Autowired
    LaoMnoSmsService laoMnoSmsService;
    @Autowired
    LaoMnoServerTaskService laoMnoServerTaskService;
    @Autowired
    LaoMnoAlarmService laoMnoAlarmService;


    /**
     * doPut 对Post方法的处理实现.
     * <p>
     * 入参格式
     * {
     * "systemId": "",
     * "serverName": "",
     * "requestId": "",
     * "sign": "",
     * "requesTime": "",
     * "requestInfo": "urlEncode()"
     * }
     * <p>
     * 返回报文格式
     * {
     * "respCode": "",
     * "respDesc": "",
     * "requestId": "",
     * "respInfo": "urlEncode()"
     * }
     */
    @RequestMapping(value = "serviceProvide", method = RequestMethod.POST)
    public void httpServerProvide(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        logger.info("***********************MNO INTERFACE BEGIN************************");
        InterfaceHandlerContext handlerContext = new InterfaceHandlerContext();
        try {
            initParams(handlerContext, request);
            //校验系统是否存在
            LaoMnoSystemConfigDto laoMnoSystemConfigDto = null;
            if (handlerContext.isSuccess()) {
                /*test*/
                laoMnoSystemConfigDto = new LaoMnoSystemConfigDto();
                laoMnoSystemConfigDto.setAppKey("FIToIQ3haY8HHT3l");
                laoMnoSystemConfigDto.setIpLimit("0");
                laoMnoSystemConfigDto.setSignLimit("1");
//                laoMnoSystemConfigDto = laoMnoSystemConfigService.querySystemConfig(handlerContext.getSystemId());
                if (laoMnoSystemConfigDto == null) {
                    handlerContext.setSuccess(false);
                    handlerContext.setInterfaceCode(MnoInterfaceCode.SYSTEMID_INVALID);
                }else{
                    handlerContext.setAppKey(laoMnoSystemConfigDto.getAppKey());
                }

            }
            //校验IP是否限制
            if (handlerContext.isSuccess() && laoMnoSystemConfigDto.getIpLimit().equals("1")) {
//                handlerContext = laoMnoInterfaceCheckService.checkIp(handlerContext);
            }


            //校验服务方法是否存在
//            LaoMnoProvideServerDto provideServerDto = null;
//            if (handlerContext.isSuccess()) {
//                provideServerDto = laoMnoProvideServerService.selectByServerName(handlerContext.getServerName());
//                if (provideServerDto == null || "0".equals(provideServerDto.getServerOpen())) {
//                    handlerContext.setSuccess(false);
//                    handlerContext.setInterfaceCode(MnoInterfaceCode.SERVER_INVALID);
//                }
//            }

            //TODO 校验通讯类型 https http webservice 等


            //判断系统是否有访问方法的权限
//            LaoMnoServerConfigDto serverConfigDto = null;
//            if (handlerContext.isSuccess()) {
//                serverConfigDto = laoMnoServerConfigService.selectBySystemIdAndServerId(handlerContext.getSystemId(), provideServerDto.getServerId());
//                if (serverConfigDto == null || "0".equals(serverConfigDto.getIsOpen())) {
//                    handlerContext.setSuccess(false);
//                    handlerContext.setInterfaceCode(MnoInterfaceCode.SERVER_INVALID);
//                }
//            }


            //TODO 校验vists次数


            //TODO 校验签名
            if (handlerContext.isSuccess() && laoMnoSystemConfigDto.getSignLimit().equals("1")) {
                handlerContext = laoMnoInterfaceCheckService.checkSign(handlerContext);
            }

            //校验业务参数是否正确
            if (handlerContext.isSuccess()) {
                try {
                    String decodeInfo = URLDecoder.decode(handlerContext.getRequestInfo(), DEFAULT_CHARSET);
                    handlerContext.setRequestInfoDecode(decodeInfo);
                } catch (Exception e1) {
                    logger.error("RequestInfo解码错误", e1);
                    handlerContext.setSuccess(false);
                    handlerContext.setInterfaceCode(MnoInterfaceCode.REQUESTINFO_FORMAT_ERROR);
                }
            }

            //执行业务处理
            if (handlerContext.isSuccess()) {
                //业务处理
//                InterfaceHandlerContext reflectionReturn = null;
//                reflectionReturn = (InterfaceHandlerContext) reflection(provideServerDto.getHandlerClass(), provideServerDto.getHandlerMethod(), handlerContext);
//                if (null == reflectionReturn) {
//                    handlerContext.setSuccess(false);
//                    handlerContext.setInterfaceCode(MnoInterfaceCode.SYSTEM_ERROR);
//                    logger.error("反射调用方法返回为空");
//                }
                if(handlerContext.getServerName().equals("Lao.base.apnStateChange.handle")){
                    handlerContext = laoMnoCardInfoService.apnStateChangeHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.packageInfo.query")){
                    handlerContext =  laoMnoPackageService.packageInfoQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.cardState.query")){
                    handlerContext =  laoMnoCardInfoService.cardStateQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.cardStateChange.handle")){
                    handlerContext =  laoMnoCardInfoService.cardStateChangeHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.productInfo.query")){
                    handlerContext = laoMnoPackageService.productInfoQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.productOrder.handle")){
                    handlerContext =   laoMnoPackageService.productOrderHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.cardInfo.query")){
                    handlerContext =   laoMnoCardInfoService.cardInfoQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.packageChange.handle")){
                    handlerContext =   laoMnoPackageService.packageChangeHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.cardServerOper.handle")){
                    handlerContext =  laoMnoCardInfoService.cardServerOperHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.sendSms.handle")){
                    handlerContext =   laoMnoSmsService.sendSmsHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.certification.handle")){
                    handlerContext =   laoMnoCardInfoService.certificationHandle(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.flow.query")){
                    handlerContext =   laoMnoPackageService.flowQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.operationResult.query")){
                    handlerContext =   laoMnoServerTaskService.operationResultQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.flowAlarm.query")){
                    handlerContext =    laoMnoAlarmService.flowAlarmQuery(handlerContext);
                }else if(handlerContext.getServerName().equals("Lao.base.flowAlarmRule.handle")){
                    handlerContext =      laoMnoAlarmService.flowAlarmRuleHandle(handlerContext);
                }else{
                    handlerContext.setSuccess(false);
                    handlerContext.setInterfaceCode(MnoInterfaceCode.SERVER_INVALID);
                }
            }
        } catch (Exception e) {
            logger.error("接口调用异常", e);
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.SYSTEM_ERROR);
        }
        //返回接口报文
        MnoInterfaceResponseDto mnoInterfaceResponseDto = new MnoInterfaceResponseDto();
        mnoInterfaceResponseDto.setRequestId(handlerContext.getRequestId());
        mnoInterfaceResponseDto.setRespCode(handlerContext.getInterfaceCode().getCode());
        mnoInterfaceResponseDto.setRespDesc(handlerContext.getInterfaceCode().getExplain());
        if(!StringUtil.isEmpty(handlerContext.getRespInfo())){
            mnoInterfaceResponseDto.setRespInfo(URLEncoder.encode(handlerContext.getRespInfo(),DEFAULT_CHARSET));
        }else{
            mnoInterfaceResponseDto.setRespInfo("");
        }
        JsonMapper jsonMapper = new JsonMapper();
        String resultStr = jsonMapper.toJson(mnoInterfaceResponseDto);
        //TODO 记录日志

        logger.debug(resultStr);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(resultStr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 反射
     *
     * @param className
     * @param handlerMethod
     * @param paramObj
     * @date 2012-11-8
     * @author sunyue
     */
    public static Object reflection(String className, String handlerMethod, Object paramObj) {
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(handlerMethod, paramObj.getClass());
            return m.invoke(c.newInstance(), paramObj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 初始化
     * 参数校验
     *
     * @return
     */
    public static void initParams(InterfaceHandlerContext handlerContext, HttpServletRequest request) {
        handlerContext.setStartTime(new Date());
        String systemId = request.getParameter("systemId");
        String serverName = request.getParameter("serverName");
        String requestId = request.getParameter("requestId");
        String sign = request.getParameter("sign");
        String requestTime = request.getParameter("requestTime");
        String requestInfo = request.getParameter("requestInfo");
        String ip = getIp(request);
        handlerContext.setRequestIp(ip);
        if (StringUtil.isBlank(systemId)) {
            handlerContext.setInterfaceCode(MnoInterfaceCode.SYSTEMID_IS_NULL);
        } else {
            handlerContext.setSystemId(systemId);
        }
        if (StringUtil.isBlank(serverName)) {
            handlerContext.setInterfaceCode(MnoInterfaceCode.SERVERNAME_IS_NULL);
        } else {
            handlerContext.setServerName(serverName);
        }
        if (StringUtil.isBlank(requestId)) {
            handlerContext.setInterfaceCode(MnoInterfaceCode.REQUESTID_IS_NULL);
        } else {
            handlerContext.setRequestId(requestId);
        }
        if (StringUtil.isBlank(sign)) {
            handlerContext.setInterfaceCode(MnoInterfaceCode.SIGN_IS_NULL);
        } else {
            handlerContext.setSign(sign);
        }
        if (StringUtil.isBlank(requestTime)) {
            handlerContext.setInterfaceCode(MnoInterfaceCode.REQUESTTIME_IS_NULL);
        } else {
            try {
                handlerContext.setRequestTime(requestTime);
                handlerContext.setRequestTimeParse(DateUtils.parseDate(requestTime, DateUtil.FORMAT_DATE_SECOND));
            } catch (ParseException e) {
                //参数解析错误
                handlerContext.setInterfaceCode(MnoInterfaceCode.REQUESTTIME_FORMAT_ERROR);
            }
        }
        if (StringUtil.isBlank(requestInfo)) {
            handlerContext.setInterfaceCode(MnoInterfaceCode.REQUESTINFO_IS_NULL);
        } else {
            handlerContext.setRequestInfo(requestInfo);
        }
        if (!MnoInterfaceCode.SUCCESS.equals(handlerContext.getInterfaceCode())) {
            handlerContext.setSuccess(false);
        }
    }

    /**
     * @param request
     * @return 获取到真正的客户端ip
     */

    private static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值,第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


}
