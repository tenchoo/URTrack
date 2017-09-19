package com.urt.service.unicom;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.mapper.IccidDonateStepMapper;
import com.urt.po.IccidDonateStep;

@Service("iccidDonateStepService")
@Transactional
public class IccidDonateStepService {
	
	@Autowired
	private IccidDonateStepMapper iccidDonateStepDAO;
	
	/**
	 * 添加步骤信息
	 * @param stepId
	 * @param userId
	 * @param iccid
	 * @param plan
	 * @param totalFlowSize
	 * @param totalStep
	 * @param currentStep
	 * @param remainStep
	 * @return
	 * @throws Exception
	 */
	public boolean saveIccidDonateStep(String stepId,String userId,String iccid,String plan,String totalFlowSize,Integer totalStep,Integer currentStep,Integer remainStep)throws Exception{
		IccidDonateStep iccidDonateStep=new IccidDonateStep();
		iccidDonateStep.setStepid(stepId);
		iccidDonateStep.setUserid(userId);
		iccidDonateStep.setIccid(iccid);
		iccidDonateStep.setPlan(plan);
		iccidDonateStep.setTotalflowsize(totalFlowSize);
		iccidDonateStep.setTotalstep(totalStep);
		iccidDonateStep.setCurrentstep(currentStep);//赠送流量第一步
		iccidDonateStep.setRemainstep(remainStep);
		iccidDonateStep.setCreatetime(new Date());
		iccidDonateStepDAO.insert(iccidDonateStep);
		return true;
	}
	
	/**
	 * 赠送成功更新剩余步骤
	 * @param stepId
	 * @throws Exception
	 */
	public void updateSuccess(String iccid,String userId)throws Exception{
		IccidDonateStep iccidDonateStepByStepId = this.getIccidDonateStepByIccidAndUserId(iccid, userId);
		iccidDonateStepByStepId.setRemainstep(iccidDonateStepByStepId.getTotalstep()-iccidDonateStepByStepId.getCurrentstep());
		iccidDonateStepByStepId.setUpdatetime(new Date());
		iccidDonateStepDAO.update(iccidDonateStepByStepId);
	}
	
	/**
	 * 赠送失败更新剩余步骤
	 * @param stepId
	 * @throws Exception
	 */
	public void updateFail(String iccid,String userId)throws Exception{
		IccidDonateStep iccidDonateStepByStepId = this.getIccidDonateStepByIccidAndUserId(iccid, userId);
		iccidDonateStepByStepId.setRemainstep(iccidDonateStepByStepId.getTotalstep()-iccidDonateStepByStepId.getCurrentstep()+1);
		iccidDonateStepByStepId.setUpdatetime(new Date());
		iccidDonateStepDAO.update(iccidDonateStepByStepId);	
	}
	
	/**
	 * 更新当前步骤
	 * @param iccid
	 * @param userId
	 * @throws Exception
	 */
	public void updateStept(String iccid,String userId)throws Exception{
		IccidDonateStep iccidDonateStepByIccidAndUserId = this.getIccidDonateStepByIccidAndUserId(iccid, userId);
		iccidDonateStepByIccidAndUserId.setCurrentstep(iccidDonateStepByIccidAndUserId.getCurrentstep()+1);
		iccidDonateStepDAO.update(iccidDonateStepByIccidAndUserId);
	}
	
	/**
	 * 
	 * @param iccid
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IccidDonateStep getIccidDonateStepByIccidAndUserId(String iccid,String userId)throws Exception{
		String hql = " from IccidDonateStep where iccid=? and userId=?";
		return iccidDonateStepDAO.doQueryFirst(iccid,userId);
	}
	 
}
