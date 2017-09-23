package com.urt.Ability.http.mno;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.interfaces.http.mno.LaoMnoPackageService;
import com.urt.modules.mapper.JsonMapper;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("laoMnoPackageService")
public class LaoMnoPackageServiceImpl implements LaoMnoPackageService {

    protected static final Logger logger = Logger.getLogger(LaoMnoPackageServiceImpl.class);

    @Override
    public InterfaceHandlerContext flowQuery(InterfaceHandlerContext handlerContext) {
        String requestInfo  = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("dataType"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("dataDate"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }

        //TODO 业务处理
        if(handlerContext.isSuccess()) {
            Map<String, Object> respInfo = new HashMap<>();
            respInfo.put("datePoint", paramMap.get("dataDate"));
            respInfo.put("apn1UserFlowSize", "512");
            respInfo.put("apn2UserFlowSize", "512");
            respInfo.put("userSmsUpNum", "100");
            respInfo.put("userSmsDownNum", "100");
            respInfo.put("upVoiceUser", "123456");
            respInfo.put("downVoiceUser", "123456");
            handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
        }
        //返回报文
        return handlerContext;
    }

    @Override
    public InterfaceHandlerContext productInfoQuery(InterfaceHandlerContext handlerContext) {
        String requestInfo  = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("goodsTag"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }

        //TODO 业务处理
        if(handlerContext.isSuccess()) {
            Map<String, Object> respInfo = new HashMap<>();
            List<Map> list = new ArrayList<>();
            Map<String, String> products;
            for(int i =1 ;i <=10 ; i++){
                products = new HashMap<>();
                products.put("goodsId", String.valueOf(100000+i));
                products.put("goodsName", "流量套餐"+i);
                products.put("goodsPic", "");
                products.put("goodsPrices", "18");
                products.put("goodsTag", "1");
                list.add(products);
            }
            respInfo.put("products",list);
            handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
        }
        //返回报文
        return handlerContext;
    }

    @Override
    public InterfaceHandlerContext packageInfoQuery(InterfaceHandlerContext handlerContext) {
        String requestInfo  = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("queryTag"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }

        //TODO 业务处理
        if(handlerContext.isSuccess()) {
            Map<String, Object> respInfo = new HashMap<>();
            Map<String, String> apn1Info = new HashMap<>();
            apn1Info.put("apn1TotalFlow", "1024");
            apn1Info.put("apn1SurplusFlow", "512");
            respInfo.put("apn1Info", apn1Info);
            Map<String, String> apn2Info = new HashMap<>();
            apn2Info.put("apn2TotalFlow", "1024");
            apn2Info.put("apn2SurplusFlow", "512");
            respInfo.put("apn2Info", apn2Info);
            Map<String, String> smsInfo = new HashMap<>();
            smsInfo.put("sendSmsNum", "64");
            respInfo.put("smsInfo", smsInfo);
            Map<String, String> voiceInfo = new HashMap<>();
            voiceInfo.put("voiceDuration", "220");
            respInfo.put("voiceInfo", voiceInfo);
            handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
        }
        //返回报文
        return handlerContext;
    }

    @Override
    public InterfaceHandlerContext productOrderHandle(InterfaceHandlerContext handlerContext) {
        String requestInfo  = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("goodsId"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }

        //TODO 业务处理
        if(handlerContext.isSuccess()) {
            Map<String, String> respInfo = new HashMap<>();
            respInfo.put("tradeId", ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID));
            handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
        }
        //返回报文
        return handlerContext;


    }

    @Override
    public InterfaceHandlerContext packageChangeHandle(InterfaceHandlerContext handlerContext) {

        String requestInfo  = handlerContext.getRequestInfoDecode();
        JsonMapper jsonMapper = new JsonMapper();

        Map<String, String> paramMap;
        paramMap = jsonMapper.fromJson(requestInfo, Map.class);
        if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("newGoodsId"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        if(StringUtils.isEmpty(paramMap.get("oldGoodsId"))){
            handlerContext.setSuccess(false);
            handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
        }
        //TODO 业务处理
        if(handlerContext.isSuccess()) {
            Map<String, String> respInfo = new HashMap<>();
            respInfo.put("tradeId", ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID));
            handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
        }
        //返回报文
        return handlerContext;
    }
}
