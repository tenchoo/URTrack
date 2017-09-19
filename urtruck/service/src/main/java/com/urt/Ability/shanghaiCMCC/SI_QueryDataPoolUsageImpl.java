package com.urt.Ability.shanghaiCMCC;
import java.math.BigDecimal;
/**
 * 流量池流量查询
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolUsage;
import com.urt.mapper.LaoPoolUseInfoMapper;
import com.urt.po.LaoPoolUseInfo;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Service("sI_QueryDataPoolUsage")
public class SI_QueryDataPoolUsageImpl implements SI_QueryDataPoolUsage{
	@Autowired
	private LaoPoolUseInfoMapper laoPoolUseInfoDao;
	Logger log = Logger.getLogger(getClass());
	@Override
	public String queryDataPoolUsage(String eid, String poolId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Request_ID", "");
		jsonObject.put("Request_Date_Time", "");
		jsonObject.put("EID", eid);
		jsonObject.put("PoolId", poolId);
		String httpUrl = ConstantUtil.URL + "/SI_QueryDataPoolUsage";
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		//将返回结果插入表中
		log.info("返回数据："+result);
		try {
			LaoPoolUseInfo laoPoolUseInfo = new LaoPoolUseInfo();
			JSONObject json = JSON.parseObject(result);
			//封装参数
			laoPoolUseInfo.setId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
			laoPoolUseInfo.setEid(json.getString("EID"));
			
			SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
			laoPoolUseInfo.setInMonth(Integer.parseInt(date.format(new Date())));
			laoPoolUseInfo.setPoolId(json.getString("PoolId"));
			String remainingVolume = json.getString("RemainDataVolume");
			laoPoolUseInfo.setRemainingVolume(changeType(remainingVolume));
			laoPoolUseInfo.setTotalVolume(changeType(json.getString("TotalDataVolume")));
			laoPoolUseInfo.setUsedVolume(changeType(json.getString("UsedDataVolume")));
			laoPoolUseInfo.setUpdateStaffId(ConstantUtil.updateStaffId);
			laoPoolUseInfo.setUpdateTime(new Date());
			laoPoolUseInfoDao.insert(laoPoolUseInfo);
			log.info("插入数据成功......");
		} catch (Exception e) {
			log.info("插入数据失败......");
			e.printStackTrace();
		}
		return result;
	}
	public BigDecimal changeType(String param){
		String substring = param.substring(0, param.length()-1);
		long value = Long.parseLong(substring);
		if(param.contains("G")){
			value = value * 1024;
		}
		BigDecimal result = BigDecimal.valueOf(value);
		return result;
	}
}
