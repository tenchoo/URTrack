package com.urt.Ability.http.mno;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.urt.common.enumeration.MnoInterfaceCode;
import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.dto.http.mno.LaoMnoServerTaskDto;
import com.urt.interfaces.http.mno.LaoMnoServerTaskService;
import com.urt.modules.mapper.JsonMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("laoMnoServerTaskService")
public class LaoMnoServerTaskServiceImpl implements LaoMnoServerTaskService {

	protected static final Logger logger = Logger.getLogger(LaoMnoServerTaskServiceImpl.class);

	@Override
	public int insert(LaoMnoServerTaskDto serverTaskDto) {
		return 0;
	}

	@Override
	public int updateStatus(String status, String code, String desc) {
		return 0;
	}

	@Override
	public List<LaoMnoServerTaskDto> queryTaskList(Map<String, String> param) {
		return null;
	}

	@Override
	public LaoMnoServerTaskDto queryTask(Map<String, String> param) {
		return null;
	}

	@Override
	public InterfaceHandlerContext operationResultQuery(InterfaceHandlerContext handlerContext) {
		String requestInfo  = handlerContext.getRequestInfoDecode();
		JsonMapper jsonMapper = new JsonMapper();

		Map<String, String> paramMap;
		paramMap = jsonMapper.fromJson(requestInfo, Map.class);
		if(StringUtils.isEmpty(paramMap.get("srcRequestId"))){
			handlerContext.setSuccess(false);
			handlerContext.setInterfaceCode(MnoInterfaceCode.PARAM_INVALID);
		}

		//TODO 业务处理
		Map<String, String> respInfo = new HashMap<>();
		respInfo.put("operationCode", MnoInterfaceCode.SUCCESS.getCode());
		respInfo.put("operationDesc", MnoInterfaceCode.SUCCESS.getExplain());
		handlerContext.setRespInfo(jsonMapper.toJson(respInfo));
		//返回报文
		return handlerContext;
	}

	@Override
	public void operationResultPush(Map<String, String> params) {

	}
}
