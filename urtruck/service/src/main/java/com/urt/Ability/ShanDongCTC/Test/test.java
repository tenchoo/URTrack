package com.urt.Ability.ShanDongCTC.Test;
/*package com.lenovo.LAOAPI.Ability.ShanDongCTC.Test;

import com.alibaba.dubbo.common.json.ParseException;
import com.lenovo.LAOAPI.Ability.ShanDongCTC.CTCService.CTCServiceImpl;
import com.lenovo.LAOAPI.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.lenovo.LAOAPI.Ability.ShanDongCTC.Utils.DesUtils;

public class test {
	
	//main方法
		public static void main(String[] args) throws ParseException {
			CTCServiceImpl service = new CTCServiceImpl();
			DesUtils des = new DesUtils();
			String telephone = service.getTelephone("44C62BE54884C12592B5CFE57120DEFF");
			String locationByPhone = service.getLocationByPhone("1064910609242", "ch2015", "44C62BE54884C12592B5CFE57120DEFF");
			String requestServActive = service.requestServActive("13716481382", "sdzcqcfwyxgs", "x17d5DH3J");
			String queryCardStatus = service.queryCardStatus("13716481382", "sdzcqcfwyxgs", "x17d5DH3J");
			String queryTrafficOfToday = service.queryTrafficOfToday("13716481382", "sdzcqcfwyxgs", "x17d5DH3J");
			String queryTraffic = service.queryTraffic("13716481382", "sdzcqcfwyxgs", "x17d5DH3J");
			String queryTrafficByDate = service.queryTrafficByDate("13716481382", "sdzcqcfwyxgs", "x17d5DH3J", "20150201", "20150210");
			String pay = service.pay("102520141111111111123456", "8986031540025047173", "ALIPAY", "0.01", "http://callbackURL", "http://callbackURL", "admin", "13AF32801E008AEF7BAE37BE950FF542", "ORDERSEQ");
			String queryPakage = service.queryPakage("8986031540025047173", ConstantUtil.userId, ConstantUtil.passWord, "20150501");
			String orderFlow = service.orderFlow("8986031340025008722", "1", "admin", "13AF32801E008AEF7BAE37BE950FF542");
			String unsubScribe = service.unsubScribe("8986031340025008722", "1", "admin", "13AF32801E008AEF7BAE37BE950FF542");
			String disabledNumber = service.disabledNumber("19", "106490987233", "-100", "admin", "13AF32801E008AEF7BAE37BE950FF542");
			String queryBalance = service.queryBalance("8986031540025047173", "ch2015", "44C62BE54884C12592B5CFE57120DEFF");
			String queryVoice = service.queryVoice("8986031530025000009", "ch2015", "44C62BE54884C12592B5CFE57120DEFF", "20150201", "20150701");
			
			//解析telphone
			System.out.println(telephone);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			TeleResp teleResp = JSON.parse(telephone, TeleResp.class);
			System.out.println(teleResp.getRESULT());
			System.out.println(teleResp.getSMSG());
			//解析locationByPhone
			System.out.println(locationByPhone);
			System.out.println("((((((((((((((((((((((((((((((((((((((((((((((((((((");
			LocationResp locationResp = JSON.parse(locationByPhone, LocationResp.class);
			System.out.println(locationResp.getMSID_TYPE());
			//System.out.println(queryVoice);
			System.out.println(orderFlow);
			
			
		}
}
*/