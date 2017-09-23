package com.urt.Ability.ShanDongCTC;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.ShanDongCTC.CTCService.CTCServiceImpl;
import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
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
@Service("userOrderCTCCService")
public class UserOrderProductCTCC extends UserOrderProduct<Object>{

	/*@Autowired
	private CTCServiceImpl ctcService;*/
	
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
		LaoUserOperatorPlan laoUserOperatorPlan = null;
		OrderProductResultMsg resultMsg = new OrderProductResultMsg();
		OperatorPlan plan = null;
		String iccid = null;
		String httpUrl = null;
//		String oldPackage = null;
		if (args == null || args.length == 0 || args.length<3)
			throw new IllegalArgumentException();
		iccid = (String) args[0];
		plan = (OperatorPlan) args[1];
//		String newPackage = plan.getOperatorsPid();
//		laoUserOperatorPlan = (LaoUserOperatorPlan) args[2];
		
		
		if(plan.getPlanType().equals("0")){//0 月套餐，1流量包，2共享流量池
			/*boolean flag = false;
			//查询当前移动用户 最新 月套餐计划
			LaoUserOperatorPlan laoUserOldPlan = laoUserOperatorPlanExtDAO.queryCurrentMonthPlan(laoUserOperatorPlan.getUserId());
			if(laoUserOldPlan !=null){
				oldPackage = laoUserOldPlan.getOperatorsPid();
			}else{
				log.info("查询当前移动用户 最新 月套餐计划  为空****************************");
				flag = true;
			}*/
			//如果是包月 使用套餐修改接口  新的套餐下一个月生效，当前的lao_user_operator_paln 当前有效的且是包月  将结束时间设置成当月的月底
			String method = "orderFlow";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {iccid,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method,plan.getOperatorsPid()};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			httpUrl = ConstantUtil.URL+"/app/serviceAccept.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.flowValue+"="+plan.getOperatorsPid()+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}
			Log.info(httpUrl);
			
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
				System.out.println("新的套餐计划设置下月"+new SimpleDateFormat("yyyyMMdd").format(laoUserOperatorPlan.getStartDate()));
			}*/
			
		}else if(plan.getPlanType().equals("1")){//0 月套餐，1流量包，2共享流量池
			//如果是流量包 使用套餐叠加接口 新的套餐立即生效
			String method = "orderFlow";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {iccid,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method,plan.getOperatorsPid()};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			httpUrl = ConstantUtil.URL+"/app/serviceAccept.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.flowValue+"="+plan.getOperatorsPid()+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}
			Log.info(httpUrl);
			
		}
		
		//记录入参
		resultMsg.setInputMessage(httpUrl);
		//真正调用移动的业务
		String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		JSONObject json = JSON.parseObject(reuslt);
		
		resultMsg.setOutMessage(reuslt);
		/*resultMsg.setOpeartorsDealCode(json.getString("errorMessage"));*/
		/*if(json.containsKey("resultCode")){
			if(json.getString("resultCode").equals("1")){
				resultMsg.setOpeartorsDealRst("1");			//0成功1失败
			}else if(json.getString("resultCode").equals("0")){
				resultMsg.setSuccess(true);
				resultMsg.setOpeartorsDealRst("0");			//0成功1失败
			}
		}*/
		if("success".equals(json.getString("info"))){
			resultMsg.setSuccess(true);
			resultMsg.setOpeartorsDealRst("0");	
		}else{
			resultMsg.setOpeartorsDealRst("1");
		}
		resultMsg.setUserOperatorPlan(laoUserOperatorPlan);
	    return resultMsg;
	}
}
