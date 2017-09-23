package com.urt.Ability.shanghaiCMCC;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolInfo;
import com.urt.mapper.LaoPoolMapper;
import com.urt.po.LaoPool;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
/**
 * 流量池查询
 */
@Service("sI_QueryDataPoolInfo")
public class SI_QueryDataPoolInfoIpml implements SI_QueryDataPoolInfo {
	@Autowired
	private LaoPoolMapper laoPoolDao;
	
	Logger log = Logger.getLogger(getClass());
	
	@Override
	public String queryDataPoolInfo(String eid) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Request_ID", "");
		jsonObject.put("Request_Date_Time", "");
		jsonObject.put("EID", eid);
		String httpUrl = ConstantUtil.URL + "/SI_QueryDataPoolInfo";
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		/*返回数据结构
		 * {"Pool_Records":[{"ResourceDescription":"企业流量","PoolID":"0000023"},{"ResourceDescription":"企业流量","PoolID":"0000023"},{"ResourceDescription":"企业流量","PoolID":"0000023"},{"ResourceDescription":"企业流量","PoolID":"0000023"},{"ResourceDescription":"企业流量","PoolID":"0000023"}],
		"Result_Date_Time":"20170815165820","Result_Code":"S0000","EID":"c09924c09c034b1f83ad052ae078de1412345678","Result_Description":"成功"}*/

		log.info("返回数据====="+result);
				try {
					JSONObject json = JSON.parseObject(result);
					List<Map<String,String>> object = (List<Map<String, String>>) json.get("Pool_Records");
					SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
					for (Map<String, String> map : object) {
						LaoPool laoPool = new LaoPool();
						String PooID = map.get("PoolID");
						laoPool.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
						String poolDesc = map.get("ResourceDescription");
						laoPool.setEid(eid);
						laoPool.seteName("蔚来汽车");
						laoPool.setPoolDesc(poolDesc);
						laoPool.setPoolId(PooID);
						laoPool.setUpdateStaffId("123456");
						laoPool.setUpdateTime(new Date());
						String format = date.format(new Date());
						int inMonth = Integer.parseInt(format);
						laoPool.setInMonth(inMonth);
						//插入数据库
						 
						laoPoolDao.insert(laoPool);
						
					}
					log.info("流量池查询插入成功......");
				} catch (Exception e) {
					log.info("流量池查询插入失败......");
					e.printStackTrace();
				}
				
				return result;
	}

}
