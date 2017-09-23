package com.urt.Ability.ShanDongCTC;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
import com.urt.Ability.ShanDongCTC.Utils.DocUtil;
import com.urt.Ability.collect.UserStateChange;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.po.IccidLib;
import com.urt.po.Trade;
import com.urt.utils.HttpClientUtil;

@Service("userStateChangeCTCCService")
public class UserStateChangeCTCC extends UserStateChange {

	Logger log = Logger.getLogger(getClass());

	/*
	 * @Autowired private CTCServiceImpl ctcService;
	 */
	
	@Autowired
	private TradeExtMapper tradeExtDAO ;
	@Override
	protected ResultMsg sendMessage(Object... args) {
		// TODO Auto-generated method stub
		log.info("**************激活或者状态变更******************");
		ResultMsg resultMsg = new ResultMsg();
		String msg = "";
		String iccid = (String) args[0];

		String stateValue = (String) args[1];
		String tradeId =  (String) args[3];
		/* String stateValue= */
		String httpUrl = "";
		boolean falg = false;
		Trade trade  = tradeExtDAO.selectByTradeId(Long.valueOf(tradeId));
		/*
		 * 通过tradeIdl来获取trade_type_code 来判断是激活还是状态变更 
		 * 20170406 wangxb20
		 */
		Short tradeTypeCode =  trade.getTradeTypeCode();	
		if(130 == tradeTypeCode || 140 == tradeTypeCode){
			/****2017年3月20日17:56:57  sunhao****/
			if(("1").equals(stateValue)){
				log.info("**************激活或者状态变更******20************");
				stateValue = "20";
				falg= true;
			}else if(("2").equals(stateValue)){
				log.info("**************激活或者状态变更********19**********");
				stateValue = "19";
				falg = true;
			}	
		}else if(100 == tradeTypeCode){
			log.info("**************激活或者状态变更********100**********");
			falg = false;
		}
			
		
		//从iccid得到电话号码
		String code="";
		if(iccid!=null && iccid.trim().length()>0 && falg){
			log.info("**************激活或者状态变更********falg**********"+falg);
			code=iccid;
			String method = "getTelephone";
			//调用des加密工具类
			DesUtils des = new DesUtils();
	        /*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {code,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			/*//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);*/
			//拼接httpUrl
			httpUrl = ConstantUtil.URL+"/query.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
			       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
			       +ConstantUtil.passWord+"="+passwordEnc+ "&"
			       +ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}
			Log.info(httpUrl);
			String str = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			iccid =  (String) JSON.parseObject(str).get("SMSG");
			log.info("**************激活或者状态变更********falg**********"+iccid);
		}
		/****end  sunhao****/
		
		if (stateValue.equals("19") || stateValue.equals("20")) {
			log.info("**************激活或者状态变更********falg**********"+iccid);
			// 停复机，激活
			String method = "disabledNumber";
			// 调用des加密工具类
			DesUtils des = new DesUtils();
			/*
			 * sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			 * 的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。
			 */
			String[] arr = { stateValue, iccid, "", ConstantUtil.userIdValue,
					ConstantUtil.passWordValue, method };
			String resultStr = des.naturalOrdering(arr);
			// 密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue,
					ConstantUtil.firstKey, ConstantUtil.secondKey,
					ConstantUtil.thirdKey);
			// sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey,
					ConstantUtil.secondKey, ConstantUtil.thirdKey);
			// 拼接httpUrl
			httpUrl = ConstantUtil.URL + "/query.do" + "?"
					+ ConstantUtil.method + "=" + method + "&"
					+ ConstantUtil.newuserId + "=" + ConstantUtil.userIdValue
					+ "&" + ConstantUtil.passWord + "=" + passwordEnc + "&"
					+ ConstantUtil.orderTypeId + "=" + stateValue + "&"
					+ ConstantUtil.accessNumber + "=" + iccid + "&"
					+ ConstantUtil.acctCd + "=" + "" + "&" + ConstantUtil.sign
					+ "=" + signEnc;
			log.info("*************************"+httpUrl);
			msg = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			log.info("**************激活或者状态变更********falg**********"+msg);
			String result = DocUtil.getResultText(DocUtil.string2Doc(msg));
			log.info("**************激活或者状态变更********falg**********"+result);
			if (result != null && result.equals("0")) {
				resultMsg.setSuccess(true);
				resultMsg.setOpeartorsDealRst("0");
			} else {
				resultMsg.setOpeartorsDealRst("1");
			}
			resultMsg.setInputMessage(httpUrl);
			resultMsg.setOutMessage(msg);
		} else {
			log.info("**********卡激活**********************");
			// 卡激活
			String method = "requestServActive";
			// 调用des加密工具类
			DesUtils des = new DesUtils();
			/*
			 * sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			 * 的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。
			 */
			String[] arr = { iccid, ConstantUtil.userIdValue,
					ConstantUtil.passWordValue, method };
			String resultStr = des.naturalOrdering(arr);
			// 密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue,
					ConstantUtil.firstKey, ConstantUtil.secondKey,
					ConstantUtil.thirdKey);
			// sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey,
					ConstantUtil.secondKey, ConstantUtil.thirdKey);
			// 拼接httpUrl
			httpUrl = ConstantUtil.URL + "/query.do" + "?"
					+ ConstantUtil.method + "=" + method + "&"
					+ ConstantUtil.newuserId + "=" + ConstantUtil.userIdValue
					+ "&" + ConstantUtil.iccid + "=" + iccid + "&"
					+ ConstantUtil.passWord + "=" + passwordEnc + "&"
					+ ConstantUtil.sign + "=" + signEnc;
			Log.info(httpUrl);
			msg = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			String result = JSON.parseObject(msg).get("RESULT").toString();
			if (result != null && result.equals("0")) {
				resultMsg.setSuccess(true);
				resultMsg.setOpeartorsDealRst("0");
			} else {
				resultMsg.setOpeartorsDealRst("1");
			}
			resultMsg.setInputMessage(httpUrl);
			resultMsg.setOutMessage(msg);
			log.info("result=" + msg);
		}
		return resultMsg;
	}

}
