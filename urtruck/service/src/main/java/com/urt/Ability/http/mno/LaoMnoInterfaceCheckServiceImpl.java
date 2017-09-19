package com.urt.Ability.http.mno;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.dto.http.mno.MnoInterfaceRequestDto;
import com.urt.interfaces.http.mno.LaoMnoInterfaceCheckService;
import com.urt.mapper.mno.LaoMnoSystemIpMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.mno.LaoMnoSystemIp;
import com.urt.po.mno.LaoMnoSystemIpExample;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("laoMnoInterfaceCheckService")
public class LaoMnoInterfaceCheckServiceImpl implements LaoMnoInterfaceCheckService {

    protected static final Logger logger = Logger.getLogger(LaoMnoInterfaceCheckServiceImpl.class);

    @Autowired
    LaoMnoSystemIpMapper laoMnoSystemIpMapper;


    @Override
    public InterfaceHandlerContext checkSign(InterfaceHandlerContext handlerContext) {
        Map<String, String> m = new HashMap<String, String>();
        logger.info("*******************MD5校验*****************");
        if (StringUtils.isEmpty(handlerContext.getAppKey())) {
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.APPKEY_INVALID);
        }

        if (handlerContext.isSuccess()) {
            MnoInterfaceRequestDto mnoInterfaceRequestDto = new MnoInterfaceRequestDto();
            BeanMapper.copy(handlerContext, mnoInterfaceRequestDto);
            Map<String, Object> requestParams = transBean2Map(mnoInterfaceRequestDto);
            String path = "/httpsOpenServer/serviceProvide";
            String method = "POST";
            try {
                String md5 = signURLAndRequestParams(path, requestParams, handlerContext.getAppKey(), method);
                if (!handlerContext.getSign().equals(md5)) {
                    handlerContext.setSuccess(false);
                    handlerContext.setInterfaceCode(MnoInterfaceCode.SIGN_INVALID);
                }
            } catch (Exception e) {
                logger.error("签名校验异常", e);
                handlerContext.setSuccess(false);
                handlerContext.setInterfaceCode(MnoInterfaceCode.SIGN_INVALID);
            }
        }
        return handlerContext;
    }

    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Map<String, Object> map = new HashMap<>();
        map.put("systemId", "100001");
        map.put("serverName", "Lao.base.flowAlarmRule.handle");
        map.put("requestId", "10000120170821175711000000000015");
        map.put("requestInfo", "%7b%22ruleType%22%3a%22101%22%2c%22alarmRule%22%3a%5b%7b%22alarmMode%22%3a%221%22%2c%22alarmLimit%22%3a%22200-400-800%22%2c%22msisdn%22%3a%221121231231%22%7d%2c%7b%22alarmMode%22%3a%221%22%2c%22alarmLimit%22%3a%22200-400-800%22%2c%22msisdn%22%3a%22234234234%22%7d%5d%7d");
        map.put("requestTime", "2017-8-22 00:00:00");
        String path = "/httpsOpenServer/serviceProvide";
        String method = "POST";
        String md5 = signURLAndRequestParams(path, map, "FIToIQ3haY8HHT3l", method);
        System.out.println(md5);
    }

    public static String signURLAndRequestParams(String url, Map<String, Object> requestParams, String appSecret, String method) throws NoSuchAlgorithmException {

        StringBuilder sb = new StringBuilder();

        Object[] keys = requestParams.keySet().toArray();

        Arrays.sort(keys);

        for (Object key : keys) {

            String value = (String) requestParams.get(key);

            if (!StringUtils.isEmpty(value)) {

                sb.append(key).append("=").append(value).append("&");

            }

        }

        String sign = "";

        if (sb.length() > 0) {

            sign = sign + sb.substring(0, sb.length() - 1);

        }

        sign = method + url + "?" + sign + appSecret;

        logger.debug("sign:" + sign);

        return degistByMD5(sign);

    }

    public static String degistByMD5(String str) {

        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    @Override
    public InterfaceHandlerContext checkIp(InterfaceHandlerContext handlerContext) {
        if (!StringUtils.isEmpty(handlerContext.getSystemId()) && !StringUtils.isEmpty(handlerContext.getRequestIp())) {
            LaoMnoSystemIpExample example = new LaoMnoSystemIpExample();
            example.createCriteria().andSystemIdEqualTo(handlerContext.getSystemId()).andIpAddressEqualTo(handlerContext.getRequestIp());
            List<LaoMnoSystemIp> list = laoMnoSystemIpMapper.selectByExample(example);
            if (list.size() > 0) {
                handlerContext.setSuccess(true);
                return handlerContext;
            }
        }
        handlerContext.setSuccess(false);
        handlerContext.setInterfaceCode(MnoInterfaceCode.IP_NOT_APPLIED);
        return handlerContext;
    }
}
