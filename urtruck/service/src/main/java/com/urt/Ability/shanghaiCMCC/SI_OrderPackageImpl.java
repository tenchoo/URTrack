package com.urt.Ability.shanghaiCMCC;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_OrderPackage;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
/**
 * 套餐订单管理
 */
@Service("sI_OrderPackage")
public class SI_OrderPackageImpl implements SI_OrderPackage {
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDao;
	Logger log = Logger.getLogger(getClass());
	@Override
	public String orderPackage(String msisdn ,String original_package_code, String target_package_code, String effective_code,
			String transaction_type, String apn_id) {
			
			//json封装数据
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("MSISDN", msisdn);
				jsonObject.put("Original_Package_Code", original_package_code);
				jsonObject.put("Target_Package_Code", target_package_code);
				jsonObject.put("Effective_Code", effective_code);
				jsonObject.put("Transaction_Type", transaction_type);
				jsonObject.put("APN_ID", apn_id);
				jsonObject.put("Request_ID", "");
				jsonObject.put("Request_Date_Time", "");
				System.out.println(jsonObject.toJSONString());
			//拼接httpURl
			String httpUrl = ConstantUtil.URL + "/SI_OrderPackage";
			HttpClientUtil instance = HttpClientUtil.getInstance();
			String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());

			try {
	
				//接口调用状态信息入库
				LaoOperatordealLog laoOperatordealLog = new LaoOperatordealLog();
				laoOperatordealLog.setId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
				laoOperatordealLog.setMsisdn(msisdn);
				laoOperatordealLog.setInputParameters(jsonObject.toJSONString());
				//1订购2发短信3状态变更4查询卡状态
				if("11".equals(transaction_type)){
					laoOperatordealLog.setOperatorType("1");

				}else{
					laoOperatordealLog.setOperatorType("3");
				}
				JSONObject json = JSON.parseObject(result);
				/*设置初始值
				 * //0成功1失败
				*/
				laoOperatordealLog.setSuccess("1");
				laoOperatordealLog.setCreateDate(new Date());

				//插入数据库LAO_OPERATORDEAL_LOG
				laoOperatordealLogDao.insert(laoOperatordealLog);
				log.info("插入数据库成功.....");
			} catch (Exception e) {
				log.info("插入数据库失败.......");
				e.printStackTrace();
			}
				return result;
	}

}
