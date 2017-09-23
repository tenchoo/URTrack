package com.urt.service.unicom;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.mapper.IccidLogMapper;
import com.urt.po.IccidLog;

@Service("iccidLogService")
public class IccidLogService {
	
	@Autowired
	private IccidLogMapper iccidLogDAO;
	
	
	public boolean saveIccidLog(Integer action,Integer actionStatus,String iccid,String stepId,String targetFlowsize,String userId)throws Exception{
		IccidLog iccidLog = new IccidLog();
		iccidLog.setAction(action);
		iccidLog.setActionstatus(actionStatus);
		iccidLog.setCreatetime(new Date());
		iccidLog.setIccid(iccid);
		iccidLog.setStepid(stepId);
		iccidLog.setTargetflowsize(targetFlowsize);
		iccidLog.setUserid(userId);
		iccidLogDAO.insert(iccidLog);
		
		return true;
	}
	
}
