package com.urt.Ability.http.mno;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.interfaces.http.mno.LaoMnoAlarmService;
import com.urt.modules.mapper.JsonMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("laoMnoAlarmService")
public class LaoMnoAlarmServiceImpl implements LaoMnoAlarmService {

    protected static final Logger logger = Logger.getLogger(LaoMnoAlarmServiceImpl.class);

    @Override
    public InterfaceHandlerContext flowAlarmRuleHandle(InterfaceHandlerContext handlerContext) {
        String requestInfo = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if (StringUtils.isEmpty(paramMap.get("ruleType"))) {
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if (paramMap.get("alarmRule") == null) {
            //Collections.isNullOrEmpty(paramMap.get("alarmRule"))
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }

        //TODO 业务处理


        return handlerContext;
    }

    @Override
    public InterfaceHandlerContext flowAlarmQuery(InterfaceHandlerContext handlerContext) {
        String requestInfo = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if (StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))) {
        }
        if (StringUtils.isEmpty(paramMap.get("ruleType"))) {

        }
        if (StringUtils.isEmpty(paramMap.get("beginDate"))) {

        }
        if (StringUtils.isEmpty(paramMap.get("endDate"))) {

        }
        if (StringUtils.isEmpty(paramMap.get("alarmLogId"))) {

        }
        if (StringUtils.isEmpty(paramMap.get("orderType"))) {

        }
        //TODO 业务处理
        if (handlerContext.isSuccess()) {
            Map<String, Object> respInfo = new HashMap<>();
            List<Map> list = new ArrayList<>();
            Map<String, String> alarmInfo;
            for (int i = 1; i <= 10; i++) {
                alarmInfo = new HashMap<>();
                alarmInfo.put("ruleType", "101");
                alarmInfo.put("iccid", "123123123123");
                alarmInfo.put("ruleName", "apn2流量预警");
                alarmInfo.put("userFlow", "700");
                alarmInfo.put("totoalFlow", "1024");
                alarmInfo.put("alarmLogId", String.valueOf(100000 + i));
                alarmInfo.put("alarmMsg", "iccid卡号为123123123123的sim卡当前流量为700M，请管理员即时处理");
                alarmInfo.put("alarmMode", "1");
                alarmInfo.put("alarmLimit", "800");
                list.add(alarmInfo);
            }
            respInfo.put("alarmInfo", list);
            handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
        }
        //返回报文
        return handlerContext;
    }

    @Override
    public void flowAlarmPush(Map<String, String> params) {

    }
}
