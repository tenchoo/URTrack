package com.urt.Ability.http.mno;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.interfaces.http.mno.LaoMnoCardInfoService;
import com.urt.modules.mapper.JsonMapper;

import java.util.HashMap;
import java.util.Map;

@Service("laoMnoCardInfoService")
public class LaoMnoCardInfoServiceImpl implements LaoMnoCardInfoService {

	protected static final Logger logger = Logger.getLogger(LaoMnoCardInfoServiceImpl.class);


	@Override
	public void cardInfoPush(Map<String, String> params) {

	}

	@Override
	public void ftpDownloadPush(Map<String, String> params) {

	}

	@Override
	public InterfaceHandlerContext certificationHandle(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("userName"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("idType"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("idNumber"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("idAddress"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("idExpirdate"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("vin"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("operType"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("positivePicCard"))){

		}
		if(StringUtils.isEmpty(paramMap.get("oppositePicCard"))){

		}
		if(StringUtils.isEmpty(paramMap.get("handPicCard"))){

		}

		//TODO 业务处理

		//返回报文
		return handlerContext;
	}

	@Override
	public InterfaceHandlerContext apnStateChangeHandle(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("apnTag"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("operTag"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理

		//返回报文
		return handlerContext;
	}


	@Override
	public InterfaceHandlerContext cardStateQuery(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理
		if(handlerContext.isSuccess()) {
			Map<String, Object> respInfo = new HashMap<>();
			respInfo.put("cardState", "1");
			respInfo.put("apn1State", "0");
			respInfo.put("apn2State", "0");
			respInfo.put("deviceOnline", "0");
			handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
		}
		//返回报文
		return handlerContext;
	}

	@Override
	public InterfaceHandlerContext cardStateChangeHandle(InterfaceHandlerContext handlerContext) {

		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("stateTag"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理

		//返回报文
		return handlerContext;
	}

	@Override
	public InterfaceHandlerContext cardInfoQuery(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理
		if(handlerContext.isSuccess()) {
			Map<String, Object> respInfo = new HashMap<>();
			respInfo.put("msisdn", paramMap.get("iccid"));
			respInfo.put("iccid", paramMap.get("iccid"));
			respInfo.put("imsi", paramMap.get("iccid"));
			respInfo.put("cardState", "1");
			respInfo.put("deviceOnline", "0");
			respInfo.put("testBeginDate", "2017-5-1 00:00:00");
			respInfo.put("testEndDate", "2017-8-1 00:00:00");
			respInfo.put("activeDate", "2017-8-15 00:00:00");
			handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
		}

		//返回报文
		return handlerContext;
	}

	@Override
	public InterfaceHandlerContext cardServerOperHandle(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("serverTag"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("onOffTag"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理

		//返回报文
		return handlerContext;

	}
}
