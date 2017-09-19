package com.urt.Ability.ShanDongCTC.CTCService;

import org.eclipse.jetty.util.log.Log;

import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
import com.urt.utils.HttpClientUtil;

public class CTCServiceImpl{

		//查询手机号码服务接口--通过19位iccid或15位imsi查询对应的10649手机号
		public String getTelephone(String iccid,String imsi) {
			String code="";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=imsi;
			}
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
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
			       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
			       +ConstantUtil.passWord+"="+passwordEnc+ "&"
			       +ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.imsi+"="+imsi;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
		
		//定位接口  --查询单个10649号码实时定位信息
		public String getLocationByPhone(String tel, String userId, String passWord) {
			String method = "getLocationByPhone";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {method,tel,userId,passWord};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(passWord, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL +"/app/location.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
				   +ConstantUtil.tel+"="+tel+ "&"
			       +ConstantUtil.userId+"="+userId+ "&"
				   +ConstantUtil.passWord+"="+passwordDec+ "&"
			       +ConstantUtil.sign+"="+signDec;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
		//活卡激活接口---通过tel号或iccid号激活
		public String requestServActive(String iccid,String tel) {
			String code="";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			String method = "requestServActive";
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
			//拼接httpUrl
			
			/*String[] arr1 = {iccid,ConstantUtil.userIdValue,signEnc,ConstantUtil.passWordValue};
			String resultStr1 = des.naturalOrdering(arr1);
			String password = des.strEnc(resultStr1, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);*/
			String httpUrl = ConstantUtil.URL +"/servactive.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
			       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
				   +ConstantUtil.passWord+"="+passwordEnc+ "&"
			       +ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl) ;
		}
	
		//查询卡状态接口---查询单个10649号码或19位的iccid号码的卡状态
		public String queryCardStatus(String iccid,String tel) {
			String code="";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			String method = "queryCardStatus";
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
				   +ConstantUtil.method+"="+method+ "&"
			       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
				   +ConstantUtil.passWord+"="+passwordEnc+ "&"
			       +ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
		//流量查询---查询单个10649号码或iccid号码的当天实时流量使用情况
		public String queryTrafficOfToday(String iccid,String tel) {
			String code="";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			String method = "queryTrafficOfToday";
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
		//---查询当月流量使用情况
		public String queryTraffic(String iccid,String tel) {
			String code="";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			String method = "queryTraffic";
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
		//查询时间段流量使用情况
		public String queryTrafficByDate(String iccid,String tel,String startDate,String endDate) {
			String code="";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			String method = "queryTrafficByDate";
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&"
					+ConstantUtil.startDate+"="+startDate+ "&"
					+ConstantUtil.endDate+"="+endDate+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
	
		//三码充值接口---通过15位imsi号或19位iccid号或10649号码对手机充值
		public String pay(String order_number, String iccid, String sub_bank_id,
				String pay_money, String callbackURL, String callURL,
				String userId, String passWord, String Params) {
			String method = "pay";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {iccid,userId,passWord};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(userId, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/app/pay.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
				   +ConstantUtil.userId+"="+userId+ "&"
			       +ConstantUtil.passWord+"="+passwordDec+ "&"
				   +ConstantUtil.order_number+"="+order_number+ "&"
			       +ConstantUtil.iccid+"="+iccid+ "&"
				   +ConstantUtil.sub_bank_id+"="+sub_bank_id+ "&"
			       +ConstantUtil.pay_money+"="+pay_money+ "&"
				   +ConstantUtil.sign+"="+signDec+ "&"
			       +ConstantUtil.callbackURL+"="+callbackURL+ "?"
				   +ConstantUtil.Params+"="+Params+ "&"
			       +ConstantUtil.callURL+"="+callURL+ "?"
				   +ConstantUtil.Params+"="+Params;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
		//套餐查询---查询单个10649号码或iccid号码的套餐使用情况可查询单个套餐、多个套餐及跨月套餐
		public String queryPakage(String iccid, String userId, String passWord,
				String monthDate) {
			String method = "queryPakage";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {iccid,userId,passWord};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(userId, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey,ConstantUtil.secondKey , ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
			        +ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+userId+ "&"
			        +ConstantUtil.passWord+"="+passwordDec+ "&"
					+ConstantUtil.iccid+"="+iccid+ "&"
			        +ConstantUtil.sign+"="+signDec+ "&"
					+ConstantUtil.monthDate+"="+monthDate;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
		//套餐订购叠加接口---设置单个10649号码的套餐
		public String orderFlow(String iccid, String flowValue, String userId,
				String passWord) {
			String method = "orderFlow";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {iccid,userId,passWord};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(userId, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/app/serviceAccept.do"+ "?"
			        +"&"+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+userId+ "&"
			        +ConstantUtil.passWord+"="+passwordDec+ "&"
					+ConstantUtil.iccid+"="+iccid+ "&"
			        +ConstantUtil.flowValue+"="+flowValue+ "&"
					+ConstantUtil.sign+"="+signDec;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
		//根据指定ICCID（智能号码）或者MDN（ 接入号码），退订所需套餐。
		public String unsubScribe(String iccid, String flowValue, String userId,
				String passWord) {
			String method = "unsubScribe";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {iccid,userId,passWord};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(userId, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/app/serviceAccept.do"+"?&"
			         +ConstantUtil.method+"="+method+ "&"
					 +ConstantUtil.userId+"="+userId+ "&"
			         +ConstantUtil.passWord+"="+passwordDec+ "&"
					 +ConstantUtil.iccid+"="+iccid+ "&"
			         +ConstantUtil.flowValue+"="+flowValue+ "&"
					 +ConstantUtil.sign+"="+signDec;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
		//提供停机保号，复机功能。本接口通过http方式通过get或者 post请求方式进行调用。
		public String disabledNumber(String orderTypeId, String accessNumber,
				String acctCd, String userId, String passWord) {
			String method = "disabledNumber";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {accessNumber,userId,passWord,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(userId, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+"?"
			       +ConstantUtil.method+"="+method+ "&"
				   +ConstantUtil.userId+"="+userId+ "&"
			       +ConstantUtil.passWord+"="+passwordDec+ "&"
				   +ConstantUtil.orderTypeId+"="+orderTypeId+ "&"
			       +ConstantUtil.accessNumber+"="+accessNumber+ "&"
				   +ConstantUtil.acctCd+"="+acctCd+ "&"
			       +ConstantUtil.sign+"="+signDec;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}
	
		//余额接口---查询单个10649号码或iccid号码的实时余额信息
		/*public String queryBalance(String iccid, String userId, String passWord) {
			String method = "queryBalance";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。
			String[] arr = {iccid,userId,passWord};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(userId, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//密码解密结果 
			String passwordDec = des.strDec(passwordEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign解密结果
			String signDec = des.strDec(signEnc, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
				   +ConstantUtil.userId+"="+userId+ "&"
			       +ConstantUtil.iccid+"="+iccid+ "&"
				   +ConstantUtil.passWord+"="+passwordDec+ "&"
			       +ConstantUtil.sign+"="+signDec;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		}*/
	
		
		//定位接口
		public String getLocatoinByPhone(String tel) {
			
			String method = "getLocatoinByPhone";
			//调用des加密工具类
			DesUtils des = new DesUtils();
	        /*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {tel,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
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
			String httpUrl = ConstantUtil.URL+"/app/location.do"+ "?"
			       +ConstantUtil.method+"="+method+ "&"
			       +ConstantUtil.tel+"="+tel+ "&"
			       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
			       +ConstantUtil.passWord+"="+passwordEnc+ "&"
			       +ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			String num= HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			String signDec = des.strDec(num,ConstantUtil.firstKey,ConstantUtil.secondKey,ConstantUtil.thirdKey);
			return signDec;
		}
		//短信订单查询接口
		public String querySmsDetail(String accNbr,String startDate,String endDate) {
			
			String method = "querySmsDetail";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {accNbr,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
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
					+ConstantUtil.start_time+"="+startDate+ "&"
					+ConstantUtil.end_time+"="+endDate+ "&"
					+ConstantUtil.accNbr+"="+accNbr+ "&"
					+"user_id="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			String num= HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			String signDec = des.strDec(num,ConstantUtil.firstKey,ConstantUtil.secondKey,ConstantUtil.thirdKey);
			return signDec;
		}
		//获取用户流量池
		public String getPoolList(String userId) {
			
			String method = "getPoolList";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {userId,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+userId+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//卡激活
		public String requestServActive(String iccid) {
			String method = "requestServActive";
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.newuserId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.iccid+"="+iccid+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		
		//流量预警设置
		public String flowAlarmSet(String tel,String value) {
			String method = "flowAlarmSet";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {tel,value,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+"user_id="+ConstantUtil.userIdValue+ "&"
					+"attr_value="+value+ "&"
					+ConstantUtil.accNbr+"="+tel+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		
		//欠费查询
		public String queryOwe(String accNbr) {
			String method = "queryOwe";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {accNbr,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.newuserId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.accNbr+"="+accNbr+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//套餐流量查询
		public String queryPakage(String iccid,String tel) {
			String method = "queryPakage";
			String code = "";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//套餐订购
		public String orderFlow(String iccid,String tel,String flowValue) {
			String method = "orderFlow";
			String code = "";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {code,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method,flowValue};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/app/serviceAccept.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.flowValue+"="+flowValue+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.accessNumber+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//套餐退订
		public String unsubScribe(String iccid,String tel,String flowValue) {
			String method = "unsubScribe";
			String code = "";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {code,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method,flowValue};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/app/serviceAccept.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.flowValue+"="+flowValue+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.accessNumber+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//添加、修改、取消卡达量断网功能
		public String offNetAction(String access_number,String action,String quota,String type) {
			String method = "offNetAction";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {access_number,action,quota,type,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+"access_number="+access_number+ "&"
					+"action="+action+ "&"
					+"quota="+quota+ "&"
					+"type="+type+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//停复机功能
		public String disabledNumber(String accessNumber,String orderTypeId,String acctCd) {
			String method = "disabledNumber";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {orderTypeId,accessNumber,acctCd,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.newuserId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.orderTypeId+"="+orderTypeId+ "&"
					+ConstantUtil.accessNumber+"="+accessNumber+ "&"
					+ConstantUtil.acctCd+"="+acctCd+ "&"
					+ConstantUtil.sign+"="+signEnc;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//余额查询
		public String queryBalance(String iccid,String tel) {
			String method = "queryBalance";
			String code = "";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//语音详单查询
		public String queryVoice(String iccid,String tel,String startDate,String endDate) {
			String method = "queryVoice";
			String code = "";
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
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
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.startDate+"="+startDate+ "&"
					+ConstantUtil.endDate+"="+endDate+ "&"
					+ConstantUtil.newuserId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&";
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+="access_number="+tel;
			}
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		
		//语音详单查询
		public String valueAddedDetailQuery(String tel,String startDate,String endDate) {
			String method = "valueAddedDetailQuery";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {tel,startDate,endDate,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+"start_date="+startDate+ "&"
					+"end_date="+endDate+ "&"
					+ConstantUtil.newuserId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&"
					+ConstantUtil.accNbr+"="+tel;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		//账单查询
		public String getCustBill(String tel,String ym) {
			String method = "getCustBill";
			//调用des加密工具类
			DesUtils des = new DesUtils();
			/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
			String[] arr = {tel,ym,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
			String resultStr = des.naturalOrdering(arr);
			//密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
			//拼接httpUrl
			String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
					+ConstantUtil.method+"="+method+ "&"
					+ConstantUtil.newuserId+"="+ConstantUtil.userIdValue+ "&"
					+ConstantUtil.passWord+"="+passwordEnc+ "&"
					+ConstantUtil.sign+"="+signEnc+ "&"
					+ConstantUtil.ym+"="+ym+ "&"
					+ConstantUtil.accNbr+"="+tel;
			Log.info(httpUrl);
			return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			
		}
		public static void main(String[] args) {
			CTCServiceImpl imp=new CTCServiceImpl();
			//8986031641201936634H
			//查询号码
			//String str=imp.getTelephone("8986031641201936634",null);
			
			//定位
			//String str=imp.getLocatoinByPhone("1064930097875");
			
			//短信订单查询 提示该号码和用户没有绑定！
			//String str=imp.querySmsDetail("1064930097875","20151001","20160505");
			
			//获取用户的流量池列表 暂无信息 流量池成员接口，流量单个成员查询，流量池成员新增。。。无法完成调试
			//String str=imp.getPoolList("LenovoConnectSDCM");
			
			//卡激活 待验证
			//String str=imp.requestServActive("8986031641201936634");
			
			//卡状态 验证返回的结果与文档不一致
			//String str=imp.queryCardStatus("8986031641201936638",null);
			//String str=imp.queryCardStatus(null,"1064930097875");
			
			//查询当天流量
			//String str=imp.queryTrafficOfToday(null,"1064930097875");
			//String str=imp.queryTrafficOfToday("8986031641201936638",null);
			
			//查询当月流量使用情况
			//String str=imp.queryTraffic(null,"1064930097875");
			//String str=imp.queryTraffic("8986031641201936638",null);
			
			//查询时间段流量使用情况
			String str=imp.queryTrafficByDate("8986031645202979951",null,"20170619","20170621");
			//String str=imp.queryTrafficByDate(null,"1064930097875","20151001","20160505");
			
			//设置用户流量预警
			//String str=imp.flowAlarmSet("1064930097875","40");
			
			//用户欠费查询
			//String str=imp.queryOwe("1064930097875");
			
			//用户套餐查询
			//String str=imp.queryPakage(null,"1064930097875");
		    //String str=imp.queryPakage("8986031641201936638",null);
			
			//订购套餐
			//String str=imp.orderFlow(null,"1064930097875","1");
			//String str=imp.orderFlow("8986031641201936638",null,"2007");
			
			//套餐退订
			//String str=imp.unsubScribe(null,"1064930097875","1");
			//String str=imp.orderFlow("8986031641201936638",null,"1");
			
			//添加、修改、取消卡达量断网功能
			//String str=imp.offNetAction("1064930097875","1","1024","2"); 该账号和用户未绑定
			
			//停复机 19 停机 20 复机
			//String str=imp.disabledNumber("1064930097875","20","");  //该号码和用户没有绑定！
			
			//查询余额
			//String str=imp.queryBalance(null,"1064930097875");
			
			//语音详单查询
			//String str=imp.queryVoice(null,"1064930097875","20151010","20160601");  <root/>
			
			//增值详单查询
			//String str=imp.valueAddedDetailQuery("1064930097875","20151010","20160601");
			
			//账单查询
			//String str=imp.getCustBill("1064930097875","20151010");
			
			//账单查询
			//String str=imp.getCustBill("1064930097875","20151010");
			System.out.println(">>>>>>>>>>>>>>>>>"+str);
			//解析json
			//System.out.println(JSON.parseObject(str).get("RESULT"));
			//解析xml		
			/*try {
				Document doc=DocUtil.string2Doc(str);
				Element rootElement = doc.getRootElement();
				System.out.println(rootElement.getChild("RESULT").getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
}
