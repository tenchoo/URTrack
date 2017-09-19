package com.urt.Ability.http.server;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.i18n.Exception;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.interfaces.http.AppkeyAndIpCreate;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoUserIpManagerMapper;
import com.urt.po.LaoKeyManagement;
import com.urt.po.LaoUserIpManager;

@Service("appkeyAndIpCreate")
public class AppkeyAndIpCreateImpl implements AppkeyAndIpCreate{
	/**日志****/
	protected static final Logger logger = Logger.getLogger(AppkeyAndIpCreateImpl.class);
	@Autowired
	LaoUserIpManagerMapper laoUserIp;
	@Autowired
	LaoKeyManagementMapper laoKey;
	@Override
	public String getAppkey(Map<String, String> reqInfo) {
		logger.info("****************registerIp getAppkey********************");
		String rspInfo = null;
		String custId = reqInfo.get(Constants.CustID);
		
		if(ToolsUtil.checkStringIsNull(custId)){
			return "custId 不能为空";
		}
		/*int isExistAppKey = laoKey.doQueryAppKeyIsExist(custId);
		LaoKeyManagement lao=laoKey.selectByCustId(custId);
		logger.info("****************getAppkey********************"+isExistAppKey);
		if(isExistAppKey > 0 && null!=lao){
			rspInfo = lao.getAuthKey();
			return  rspInfo;
		}*/
		 String appkey = ToolsUtil.makeAppKey(30);
			LaoKeyManagement laokeyme = new LaoKeyManagement();
			laokeyme.setId(BigDecimal.valueOf(Long.valueOf(ToolsUtil.getId())));
			laokeyme.setCustId(custId);
			laokeyme.setUpdateDate(new Date());
			laokeyme.setAuthKey(appkey);
			int isInsertSucc = laoKey.insert(laokeyme);

			if(isInsertSucc < 0){
				rspInfo = "申请APPKEY失败";
			}else{
				rspInfo = appkey;
			}
		return rspInfo;
	}

	@Override
	public String registerIp(Map<String, String> reqInfo) {
		logger.info("****************registerIp ipApply********************");
		
		String rspInfo = null;
		String ipAddress = reqInfo.get(Constants.ipAddress);
		String changeIp = reqInfo.get("ChangeIp");
		String custId = reqInfo.get(Constants.CustID);
		int isInsertSucc;
		
		//ip地址申请更换
		if(null != changeIp){
			
			//！！！！！！！！！！！暂无此功能
			
		//ip地址申请通过
		}else{
			logger.info("****************registerIp");
			boolean isIP = ToolsUtil.checkIp(ipAddress);
			if(!isIP){
				return "IP地址格式错误";
			}
			if(ToolsUtil.checkStringIsNull(reqInfo.get(Constants.CustID))){
				return "CUSTID不能为空";
			}
			int ipNum = laoUserIp.doQueryIPIsExist(Long.parseLong(custId));
			int isExistIp = laoUserIp.doQueryIP(Long.parseLong(custId),ipAddress);
			logger.info("****************registerIp(((((("+ipNum);
			logger.info("****************registerIp(((((("+isExistIp);
			if( 5 <= ipNum){
				return "申请的IP地址个数不能超过5个";
			}else if(isExistIp > 0){
				return "此IP地址已经申请";
			}else{
				logger.info("****************registerIp");
				LaoUserIpManager laoIp = new LaoUserIpManager();
				laoIp.setCustId(Long.valueOf(custId));
				laoIp.setIpAddress(ipAddress);
				laoIp.setCreatDate(new Date());
				isInsertSucc = laoUserIp.insert(laoIp);
				logger.info("****************registerIp((+++++++"+isInsertSucc);
				if(isInsertSucc > 0){
					rspInfo = "IP注册成功";
				}else{
					rspInfo = "IP注册失败";
				}
			}	
		}		
		return rspInfo;
	}

	@Override
	public Map<String, Object> appKeyList(int start, int end,String custName) {
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);
		param.put("custName", custName);
	    List<Map<String,Object>> list=laoKey.pageQuery(param);
	    int countTotal=laoKey.totalCount(custName);
	    HashMap<String,Object> restultMap = new HashMap<String,Object>();
	    restultMap.put("data",list);
	    restultMap.put("iTotalRecords", end);// 当前页包含的记录数
	    restultMap.put("iTotalDisplayRecords", countTotal);
		return restultMap;
	}

	@Override
	public String queryAppkey(Map<String, String> reqInfo) {
		logger.info("****************registerIp getAppkey********************");
		String rspInfo = null;
		String custId = reqInfo.get(Constants.CustID);
		
		if(ToolsUtil.checkStringIsNull(custId)){
			return "custId 不能为空";
		}
		int isExistAppKey = laoKey.doQueryAppKeyIsExist(custId);
		LaoKeyManagement lao=laoKey.selectByCustId(custId);
		logger.info("****************getAppkey********************"+isExistAppKey);
		if(isExistAppKey > 0 && null!=lao){
			rspInfo = lao.getAuthKey();
			return  rspInfo;
		}
		return "0";   //0代表没查询到
	}

}
