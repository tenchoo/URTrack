package com.urt.Ability.shanghaiCMCC;
/*package com.lenovo.LAOAPI.Ability.shanghaiCMCC;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lenovo.LAOAPI.Ability.DongguanCMC.ConstantsUntil;
import com.lenovo.LAOAPI.Ability.collect.UserOrderProduct;
import com.lenovo.LAOAPI.Ability.unicom.service.EditTerminalRatingService;
import com.lenovo.LAOAPI.Ability.unicom.service.EditTerminalService;
import com.lenovo.LAOAPI.Ability.unicom.service.GetTerminalRatingService;
import com.lenovo.LAOAPI.Ability.unicom.service.QueueTerminalRatePlanService;
import com.lenovo.LAOAPI.mapper.LaoOperatordealLogMapper;
import com.lenovo.LAOAPI.mapper.LaoUserOperatorPlanMapper;
import com.lenovo.LAOAPI.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.lenovo.LAOAPI.po.LaoUserOperatorPlan;
import com.lenovo.LAOAPI.po.OperatorPlan;
import com.lenovo.LAOAPI.utils.HttpClientUtil;

@Service("userOrderProductCMCCSH")
public class UserOrderProductCMCCSH extends UserOrderProduct<SOAPMessage>{

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
			
			//调用接口
			
		}else if(plan.getPlanType().equals("1")){//0 月套餐，1流量包，2共享流量池
			//调用接口
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
*/