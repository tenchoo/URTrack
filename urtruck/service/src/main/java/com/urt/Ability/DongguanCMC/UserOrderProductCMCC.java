package com.urt.Ability.DongguanCMC;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.collect.UserOrderProduct;
import com.urt.Ability.unicom.service.EditTerminalRatingService;
import com.urt.Ability.unicom.service.EditTerminalService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.QueueTerminalRatePlanService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.OperatorPlan;
import com.urt.utils.HttpClientUtil;

/**
 * 移动产品订购实现
 * @author sunhao
 *
 */
@Service("userOrderProductCMCC")
public class UserOrderProductCMCC extends UserOrderProduct<SOAPMessage> {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;

	@Autowired
	private EditTerminalService editTerminalService;

	@Autowired
	private EditTerminalRatingService editTerminalRatingService;

	@Autowired
	private QueueTerminalRatePlanService queueTerminalRatePlanService;
	
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDAO;// 用户套餐
	
	@Autowired
	private GetTerminalRatingService getTerminalRatingService;
	
	@Autowired
	private LaoUserOperatorPlanMapper laoUserOperatorPlanDAO;
	
	@Override
	protected OrderProductResultMsg sendMessage(Object... args) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>移动订购产品");
		LaoUserOperatorPlan laoUserOperatorPlan = null;
		OrderProductResultMsg resultMsg = new OrderProductResultMsg();
		OperatorPlan plan = null;
		String iccid = null;
		String httpUrl = null;
		String oldPackage = null;
		if (args == null || args.length == 0 || args.length<3)
			throw new IllegalArgumentException();
		iccid = (String) args[0];
		plan = (OperatorPlan) args[1];
		String newPackage = plan.getOperatorsPid();
		laoUserOperatorPlan = (LaoUserOperatorPlan) args[2];
		
		
		if(plan.getPlanType().equals("0")){//0 月套餐，1流量包，2共享流量池
//			boolean flag = false;
			//查询当前移动用户 最新 月套餐计划
			LaoUserOperatorPlan laoUserOldPlan = laoUserOperatorPlanExtDAO.queryCurrentMonthPlan(laoUserOperatorPlan.getUserId());
			if(laoUserOldPlan !=null){
				oldPackage = laoUserOldPlan.getOperatorsPid();
			}else{
				log.info("查询当前移动用户 最新 月套餐计划  为空****************************");
//				flag = true;
			}
			//如果是包月 使用套餐修改接口  新的套餐下一个月生效，当前的lao_user_operator_paln 当前有效的且是包月  将结束时间设置成当月的月底
			String method = "iot.member.plan.change";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.newPackage, newPackage);
			paramMap.put(ConstantsUntil.oldPackage, oldPackage);
			paramMap.put(ConstantsUntil.msisdn, iccid);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
					+ "&" + ConstantsUntil.msisdn + "=" + iccid + "&"
					+ ConstantsUntil.newPackage + "=" + newPackage + "&"
					+ ConstantsUntil.oldPackage + "=" + oldPackage + "&"
					+ ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
			
			/*if(!flag){
				//获取当前月的最后一天
				Calendar cale = Calendar.getInstance();  
				cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+1);
				cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
				laoUserOldPlan.setEndDate(cale.getTime());		//老的套餐计划设置月底失效
				laoUserOperatorPlanDAO.updateByPrimaryKeySelective(laoUserOldPlan);
				
				cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+1);
				cale.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为下月第一天 
				laoUserOperatorPlan.setStartDate(cale.getTime());//新的套餐计划设置下月生效
				log.info("新的套餐计划设置下月"+new SimpleDateFormat("yyyyMMdd").format(laoUserOperatorPlan.getStartDate()));
			}*/
			
		}else if(plan.getPlanType().equals("1")){//0 月套餐，1流量包，2共享流量池
			//如果是流量包 使用套餐叠加接口 新的套餐立即生效
			String method = "iot.member.additionplan.change";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.newPackage, newPackage);
			paramMap.put(ConstantsUntil.msisdn, iccid);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
					+ "&" + ConstantsUntil.msisdn + "=" + iccid + "&"
					+ ConstantsUntil.newPackage + "=" + newPackage + "&"
					+ ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
		}
		
		//记录入参
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>移动订购url"+httpUrl);
		resultMsg.setInputMessage(httpUrl);
		//真正调用移动的业务
		String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		JSONObject json = JSON.parseObject(reuslt);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>移动订购结果"+reuslt);
		resultMsg.setOutMessage(reuslt);
		resultMsg.setOpeartorsDealCode(json.getString("errorMessage") == null? null:json.getString("errorMessage").substring(0, 25));
		if(json.containsKey("resultCode")){
			if(json.getString("resultCode").equals("1")){
				resultMsg.setOpeartorsDealRst("1");			//0成功1失败
			}else if(json.getString("resultCode").equals("0")){
				resultMsg.setSuccess(true);
				resultMsg.setOpeartorsDealRst("0");			//0成功1失败
			}
		}
		resultMsg.setUserOperatorPlan(laoUserOperatorPlan);
	    return resultMsg;
	}
	public static void main(String[] args) {
		//获取当前月的最后一天
		Calendar cale = Calendar.getInstance();  
		cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+1);
		cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
		
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(cale.getTime()));
		cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+1);
		cale.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为下月第一天 
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(cale.getTime()));
		
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(new Date("Tue Nov 01 17:48:19 CST 2016")));
	}
}
