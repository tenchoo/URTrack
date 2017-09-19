package com.urt.Ability.shanghaiCMCC;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.interfaces.ShangHaiCMC.CI_CommonNotification;
import com.urt.mapper.ext.LaoOperatordealLogExtMapper;
import com.urt.po.LaoOperatordealLog;

/**
 * 移动回调函数
 *
 */
@Service(value="cI_CommonNotification")
public class CI_CommonNotificationImpl implements CI_CommonNotification {
	
	@Autowired
	private LaoOperatordealLogExtMapper logExtMapper;

	Logger log = Logger.getLogger(getClass());
	@Override
	public void commonNotification(Map<String,String> Multi_Records,String params) {

		try {
			String OLD_Request_ID = Multi_Records.get("OLD_Request_ID");
			
			LaoOperatordealLog laoOperatordealLog = new LaoOperatordealLog();
		
			laoOperatordealLog.setMsisdn(Multi_Records.get("MSISDN"));
			laoOperatordealLog.setOutputParameters(params);
			String Result_Code = Multi_Records.get("Result_Code");
			if("S0000".equals(Result_Code)){
				//0是成功1是失败
				laoOperatordealLog.setSuccess("0");
				
			}else{
				LaoOperatordealLog logByRequest = logExtMapper.getDealLogByRequest(OLD_Request_ID);
				Short failNum = logByRequest.getFailNum();
				laoOperatordealLog.setFailNum((short) (failNum+1));
			}
			laoOperatordealLog.setResultCode(Multi_Records.get("Result_Description"));
			logExtMapper.updateFailNumByRequestId(OLD_Request_ID);
			log.info("插入Lao_Operatordeal_log表成功.............");
			//调用业务层OrderService接口
			/*
			 * *********************************************
			 */
			//返回结果集入库lao_opeartdeal_log

		}catch(Exception e){

			e.printStackTrace();
		}
	}

}
