package com.urt.Ability.DongguanCMC;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.google.gson.Gson;
import com.urt.interfaces.DongguanCMC.MemberMethod;
import com.urt.utils.HttpClientUtil;

@Service("memberMethod")
public class MemberMethodImpl implements MemberMethod {
	Logger log = Logger.getLogger(getClass());

	public static void main(String[] args) throws JSONException {
		MemberMethodImpl m = new MemberMethodImpl();
//		 System.out.println(m.additionplanChange("1064888362834","99910100001025"));
//		 System.out.println(m.planChange("1064856085750", "I00010100067","I00010100061"));
//		System.out.println(m.simstateChangeOpen("1064856085750")); 
		 
		String str=m.dataplanleftQuery("1064888362811");
		System.out.println(str);
		/*JSONObject jsonObject=new JSONObject(str);
		Object object2 = jsonObject.get("PackageVos");
		JSONArray array=new JSONArray(object2.toString());
		for(int i=0;i<array.length();i++){
			Object object = array.get(i);
			jsonObject=new JSONObject(object.toString());
			jsonObject=new JSONObject(jsonObject.getString("packageInfoVo"));
			String code=jsonObject.get("packageCode").toString();
			String startTime=jsonObject.get("beginTime").toString();
			String endTime=jsonObject.get("endTime").toString();
			System.out.println(">>>>>>>>>>"+code+";"+startTime+";"+endTime);
		}*/
	}

	// 通过集团编号查询客户基本信息的API
	public String infoQuery(String msisdn) {
		String method = "iot.member.info.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&" + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 成员剩余流量信息实时查询API
	public String dataplanleftQuery(String msisdn) {
		String method = "iot.member.dataplanleft.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 成员日流量信息查询API
	// dateStr 月份 String 6 YES 格式:yyyyMM
	public String dailydatausageQuery(String msisdn, String dateStr) {
		String method = "iot.member.dailydatausage.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		paramMap.put(ConstantsUntil.dateStr, dateStr);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.dateStr + "=" + dateStr + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员日流量信息查询API
	// startDateStr 开始月份 String 6 YES 格式:yyyyMM
	// endDateStr 结束月份 String 6 YES 格式:yyyyMM

	public String monthlydatausageQuery(String msisdn, String startDateStr,
			String endDateStr) {
		String method = "iot.member.monthlydatausage.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		paramMap.put(ConstantsUntil.startDateStr, startDateStr);
		paramMap.put(ConstantsUntil.endDateStr, endDateStr);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.startDateStr + "=" + startDateStr + "&"
				+ ConstantsUntil.endDateStr + "=" + endDateStr + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员月费用详单查询
	public String monthlybilldetailQuery(String msisdn, String dateStr) {
		String method = "iot.member.monthlybilldetail.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		paramMap.put(ConstantsUntil.dateStr, dateStr);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.dateStr + "=" + dateStr + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员月费用查询
	public String monthlybillQuery(String msisdn, String startDateStr,
			String endDateStr) {
		String method = "iot.member.monthlybill.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		paramMap.put(ConstantsUntil.startDateStr, startDateStr);
		paramMap.put(ConstantsUntil.endDateStr, endDateStr);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.startDateStr + "=" + startDateStr + "&"
				+ ConstantsUntil.endDateStr + "=" + endDateStr + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员告警信息查询API
	public String alarmQuery(String msisdn, String dateStr) {

		String method = "iot.member.alarm.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		paramMap.put(ConstantsUntil.dateStr, dateStr);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.dateStr + "=" + dateStr + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员号码流量信息实时查询API
	public String datausageQuery(String msisdn) {
		String method = "iot.member.datausage.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员余额实时查询
	public String balanceQuery(String msisdn) {

		String method = "iot.member.balance.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 成员套餐已订购信息实时查询
	public String packageQuery(String msisdn) {
		String method = "iot.member.package.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 成员业务状态实时状态查询
	public String statusQuery(String msisdn) {
		String method = "iot.member.status.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 终端停开机状态实时查询
	public String terminalstatusQuery(String msisdn) {

		String method = "iot.member.terminalstatus.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	public String simstateChangeOpen(String msisdn) {
		return simstateChange(msisdn, "12");
	}

	public String simstateChangeClose(String msisdn) {
		return simstateChange(msisdn, "11");
	}

	// 号码停开机API
	public String simstateChange(String msisdn, String ncode) {

		String method = "iot.member.simstate.change";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ncode, ncode);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.ncode + "=" + ncode + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// GPRS停开API
	public String dataservicestateChange(String msisdn, String optType) {

		String method = "iot.member.dataservicestate.change";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.optType, optType);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.optType + "=" + optType + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// Apn切换API
	public String apnChange(String msisdn, String prdOrdNum1, String prdOrdNum2) {

		String method = "iot.member.apn.change";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.prdOrdNum1, prdOrdNum1);
		paramMap.put(ConstantsUntil.prdOrdNum2, prdOrdNum2);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.prdOrdNum1 + "=" + prdOrdNum1 + "&"
				+ ConstantsUntil.prdOrdNum2 + "=" + prdOrdNum2 + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 套餐修改
	public String planChange(String msisdn, String newPackage, String oldPackage) {

		String method = "iot.member.plan.change";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.newPackage, newPackage);
		paramMap.put(ConstantsUntil.oldPackage, oldPackage);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.newPackage + "=" + newPackage + "&"
				+ ConstantsUntil.oldPackage + "=" + oldPackage + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		System.out.println(">>>>>>>>>>>>>>>>>>>"+httpUrl);
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 套餐叠加
	public String additionplanChange(String msisdn, String newPackage) {

		String method = "iot.member.additionplan.change";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.newPackage, newPackage);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.newPackage + "=" + newPackage + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

}
