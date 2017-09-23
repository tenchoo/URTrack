package com.urt.Ability.ShanDongCTC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.jetty.util.log.Log;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
import com.urt.Ability.collect.QueryTelByIccid;
import com.urt.utils.HttpClientUtil;
@Service("queryTelByIccidCTCC")
public class QueryTelByIccidCTCC extends QueryTelByIccid{
	Logger log = Logger.getLogger(getClass());
	@Override
	protected ResultMsg sendMessage(Object... args) {
		// TODO Auto-generated method stub
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String method = "getTelephone";
		//调用des加密工具类
		DesUtils des = new DesUtils();
        /*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
		的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
		String[] arr = {iccid,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
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
		String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
		       +ConstantUtil.method+"="+method+ "&"
		       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
		       +ConstantUtil.passWord+"="+passwordEnc+ "&"
		       +ConstantUtil.sign+"="+signEnc+ "&";
		httpUrl+=ConstantUtil.iccid+"="+iccid;
		Log.info(httpUrl);
		ResultMsg msg=new ResultMsg();
		String result= HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		Gson gson=new Gson();
		@SuppressWarnings("unchecked")
		Map<String, Object> map=gson.fromJson(result, HashMap.class);
		if("0".equals(map.get("RESULT").toString())){
			msg.setSuccess(true);
			msg.setTel(map.get("SMSG").toString());
			msg.setOpeartorsDealCode("0");	
		}else{
			msg.setSuccess(false);
			msg.setOpeartorsDealCode("1");	
		}
		msg.setInputMessage(httpUrl);
		msg.setOutMessage(result);
		return msg;
	}
	@Override
	protected ResultMsg queryOperatorPlan(Object... args) {
		// TODO Auto-generated method stub
		
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String method = "queryPakage";
		DesUtils des = new DesUtils();
		String[] arr = {iccid,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
		String resultStr = des.naturalOrdering(arr);
		String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
		String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
		String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
				+ConstantUtil.method+"="+method+ "&"
				+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
				+ConstantUtil.passWord+"="+passwordEnc+ "&"
				+ConstantUtil.sign+"="+signEnc+ "&";
		httpUrl+=ConstantUtil.iccid+"="+iccid;
		HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		ResultMsg msg=new ResultMsg();
		String result= HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		Gson gson=new Gson();
		Document document;
		List<Map<String, Object>> maps=new ArrayList<Map<String,Object>>();
		try {
			document = DocumentHelper.parseText(result);
			System.out.println(document.getRootElement()) ;
			List<Element> e = document.getRootElement().element("CurrAcuRsp").elements();
			System.out.println(e.size());
			for(Element ee : e){
				if(ee.getName().equals("CumulRspList")){
					List<Element> a  = ee.elements();
					for(Element aa :a){
						Map<String, Object> map=new HashMap<String, Object>();
						if("OFFER_NAME".equals(aa.getName()))
						System.out.println(aa.getText());
						map.put("planCode",aa.getText());
						maps.add(map);
					}
					
				}
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(maps!=null && maps.size()>0){
			msg.setPlans(maps);
		}
		Map<String, Object> map=gson.fromJson(result, HashMap.class);
		if("0".equals(map.get("RESULT").toString())){
			msg.setSuccess(true);
			msg.setTel(map.get("SMSG").toString());
			msg.setOpeartorsDealCode("0");	
		}else{
			msg.setSuccess(false);
			msg.setOpeartorsDealCode("1");	
		}
		msg.setInputMessage(httpUrl);
		msg.setOutMessage(result);
		return msg;
	}

}
