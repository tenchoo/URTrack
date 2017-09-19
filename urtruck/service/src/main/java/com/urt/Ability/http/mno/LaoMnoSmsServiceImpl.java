package com.urt.Ability.http.mno;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.interfaces.http.mno.LaoMnoSmsService;
import com.urt.modules.mapper.JsonMapper;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

import java.util.HashMap;
import java.util.Map;

@Service("laoMnoSmsService")
public class LaoMnoSmsServiceImpl implements LaoMnoSmsService {

	protected static final Logger logger = Logger.getLogger(LaoMnoSmsServiceImpl.class);

	@Override
	public InterfaceHandlerContext sendSmsHandle(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("msisdn")) || StringUtils.isEmpty(paramMap.get("iccid"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}
		if(StringUtils.isEmpty(paramMap.get("smsContent"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理
		Map<String, String> respInfo = new HashMap<>();
		respInfo.put("smsId", ZkGenerateSeq.getIdSeq(SeqID.TASK_ID));
		respInfo.put("sendSmsTag", "0");
		handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
		//返回报文
		return handlerContext;
	}
}
