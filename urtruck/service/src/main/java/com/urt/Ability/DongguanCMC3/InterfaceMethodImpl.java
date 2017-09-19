package com.urt.Ability.DongguanCMC3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.urt.interfaces.DongguanCMC3.InterfaceMethod;
import com.urt.mapper.ext.LaoDownloadfileConfigExtMapper;
import com.urt.po.LaoDownloadfileConfig;
import com.urt.utils.HttpClientUtil;



/**
 * 东莞移动3.0版本接口实现
 * 
 *
 */
@Service("interfaceMethod")
public class InterfaceMethodImpl implements InterfaceMethod{
     
	private static final Logger log=Logger.getLogger(InterfaceMethodImpl.class);
	
	@Value("${Ftp_downloadName}")
	private String downloadName;
	
	@Autowired
	private  LaoDownloadfileConfigExtMapper laoDownload;
	
	// 集团信息查询
	public String groupQuery(String groupCode) {
		String method = "triopi.group.info.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "groupCode" + "=" + groupCode;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}
	//单个集团流量池成员信息查询
	public String groupFlowmemberSingle(String msisdn) {
		String method = "triopi.group.flowmember.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
	}
	//批量集团流量池成员信息查询
	public String groupFlowmemberBatch(String msisdns) {
		String method = "triopi.group.flowmember.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
	}
	
	//单个号码业务生命周期状态查询
	public String lifecycle(String msisdn) {
		String method = "triopi.member.lifecycle.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}
	//单个号码激活时间查询
	public String activateTimeSingle(String msisdn) {
		String method = "triopi.member.actitime.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}
	//批量号码激活时间查询
	public String activateTimeBatch(String msisdn) {
		String method = "triopi.member.actitime.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}

	// 单个号码信息查询
	public String oneMsisdnQuery(String msisdn) {
		String method = "triopi.member.info.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}
    //批量号码信息查询
	public String bathMsisdnQuery(String msisdns){
		String method = "triopi.member.info.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
	}
	//单个码号信息查询
	public String msisdnSingleQuery(String msisdn) {
		String method = "triopi.member.iccid.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}
	//批量码号信息查询
	public String msisdnBathQuery(String msisdns){
		String method = "triopi.member.iccid.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		
		return DESUtils.decrypt(result, ConstantsUtils3.secret);
	}
	// 单个号码功能状态查询
	public String oneMsisdnFunctionQuery(String msisdn) {
		String method = "triopi.member.functstatus.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
	}
	//批量号码业务生命周期状态查询
	public String lifecycleBatch(String msisdns) {
		String method = "triopi.member.lifecycle.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		
		return DESUtils.decrypt(result, ConstantsUtils3.secret);
	}
	//批量号码功能状态查询
	public String functstatusBatch(String msisdns) {
		String method = "triopi.member.functstatus.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		
		return DESUtils.decrypt(result, ConstantsUtils3.secret);
	}
	
	
	
	
	//单个号码已用总流量实时查询
	public String useTotalFlowQuery(String msisdn){
		String method = "triopi.member.uesdflow.realtime.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
	}
	
	//单个号码套餐流量实时查询
	public String dailyFlow(String msisdn){
		String method="triopi.member.dailyflow.realtime.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
		
	}
	//批量号码日使用流量查询
	public String dayFlowsQuery(String msisdns,String date){
		
		String method="triopi.member.dailyflow.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("date", date);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns+ "&" + "date" + "=" + date;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
		
		
	}
	//单个号码流量校验查询
	public  String flowverify(String msisdn){
		String method="triopi.member.flowverify.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
	}
    //批量号码历史月流量查询
	public  String monthflow(String msisdns,String month){
		String method="triopi.member.monthflow.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("month", month);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns+ "&" + "month" + "=" + month;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
	}
	// 号码停开机 1 停机 2 开机
	public String stopOpenMsisdn(String msisdn, String optType) {
		String method = "triopi.business.member.switch";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		paramMap.put("optType", optType);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn + "&" + ConstantsUtils3.optType
				+ "=" + optType;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return businessOrderNoQuery(DESUtils.decrypt(result, ConstantsUtils3.secret));

	}

	// GPRS 停开 1 停GPRS 2 开GPRS
	public String stopOpenGPRS(String msisdn, String optType) {
		String method = "triopi.business.gprs.switch";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		paramMap.put("optType", optType);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn + "&" + ConstantsUtils3.optType
				+ "=" + optType;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return businessOrderNoQuery(decrypt);

	}

	// 号码套餐修改办理 opttype=1
	public String planChange(String msisdn, String optType, String newPackageId) {
		String method = "triopi.business.memberpkg.handle";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		paramMap.put("optType", optType);
		paramMap.put("packageId", newPackageId);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn + "&" + ConstantsUtils3.optType
				+ "=" + optType + "&" + "packageId" + "=" + newPackageId;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return businessOrderNoQuery(DESUtils.decrypt(result, ConstantsUtils3.secret));
	}

	// 号码流量叠加办理
	public String addPlanChange(String msisdn, String packageId) {
		String method = "triopi.business.memberpkg.add";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		paramMap.put("packageId", packageId);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn + "&" + "packageId" + "="
				+ packageId;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);

		return businessOrderNoQuery(decrypt);
	}
	 //短信重置
	public String smsRest(String msisdn){
		String method = "triopi.sms.status.reset";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);

		return decrypt;
		
	}
	//单个终端GPRS在线状态实时查询      1 在线  2 不在线
	public String gprsonlineSingle(String msisdn) {
		String method = "triopi.member.gprsonline.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}
	

	// 订单号处理情况查询
	public static String businessOrderNoQuery(String decrypt) {
//		JSONObject fromObject = JSONObject.fromObject(decrypt);
//		String resultCode = fromObject.getString("code");
//		if (null != resultCode && "0".equals(resultCode)) {
//			String data = fromObject.getString("data");
//			if (null==data && "".equals(data)) {
//				return decrypt;
//			}
//			JSONObject fromObject2 = JSONObject.fromObject(data);
//			// 获取订单编号
//			String orderNo = fromObject2.getString("orderNo");
			String method = "triopi.business.orderno.query";
			Map<String, String> paramMap = SignUtils.genComMap();
			paramMap.put(ConstantsUtils3.method, method);
			paramMap.put("orderNo", decrypt);
			String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
			String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret
					+ "&" + ConstantsUtils3.method + "=" + method + "&" + "orderNo" + "=" +decrypt;
			System.out.println(httpUrl);
			String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

			return DESUtils.decrypt(result, ConstantsUtils3.secret);
//		}
//		return decrypt;
	}
	//终端开关机状态实时查询
	public String onoff(String msisdn){
		String method = "triopi.member.onoff.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);

		return decrypt;
	}
	//终端GPRS在线数查询
	public String gprsonlineCount(String groupCode) {
		String method = "triopi.member.gprsonline.count.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "groupCode" + "=" + groupCode;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return DESUtils.decrypt(result, ConstantsUtils3.secret);
		
	}
	//终端GPRS在线率查询
	public String gprsonlinePercent(String groupCode) {
		String method = "triopi.member.gprsonline.percent.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "groupCode" + "=" + groupCode;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return DESUtils.decrypt(result, ConstantsUtils3.secret);

	}

	// 业务办理历史查询
	public String busHandHistoryQuery(String msisdn) {
		String method = "triopi.business.orderhistory.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;

	}
	//集团流量池信息查询
	public String groupFlowQuery(String groupCode) {
		String method = "triopi.group.flow.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "groupCode" + "=" + groupCode;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;

	}
	
    /**
     * 集团历史月账单查询
     * @param groupCode
     * @param month yyyyMM，只能取最近六个月（不包含本月）
     * @return
     */
	public String monthfeeQuery(String groupCode,String month) {
		String method = "triopi.group.monthfee.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		paramMap.put("month", month);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "groupCode" + "=" + groupCode+ "&" + "month" + "=" + month;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;

	}
	/**
	 * 号码历史月费用查询
	 * @param msisdn
	 * @param month yyyyMM，只能取最近六个月（不包含本月）
	 * @return
	 */
	public String memberMonthFee(String msisdn,String month) {
		String method = "triopi.member.monthfee.single.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		paramMap.put("month", month);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn+ "&" + "month" + "=" + month;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	/**
	 * 批量号码历史月费用查询
	 * @param msisdn
	 * @param month  yyyyMM，只能取最近六个月（不包含本月）
	 * @return
	 */
	public String memberMonthFeeBatch(String msisdns,String month) {
		String method = "triopi.member.monthfee.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("month", month);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" + "=" + msisdns+ "&" + "month" + "=" + month;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	/**
	 * 号码个人余额实时查询
	 * @param msisdn
	 * @return
	 */
	public String memberBalance(String msisdn) {
		String method = "triopi.member.balance.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdn", msisdn);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdn" + "=" + msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	//批量号码套餐修改办理 optType=1 1修改
	public String memberpkgBatch(String msisdns,String optType,String packageId) {
		String method = "triopi.business.memberpkg.batch.handle";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("optType", optType);
		paramMap.put("packageId", packageId);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method + "&" + "msisdns" +
				"=" + msisdns+ "&" + "packageId" + "=" + packageId+ "&" + "optType" + "=" + optType;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	//批量号码流量叠加办理
	public String memberpkgAdd(String msisdns,String packageId) {
		String method = "triopi.business.memberpkg.batch.add";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("packageId", packageId);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method +
				"&" + "msisdns" + "=" + msisdns+"&" + "packageId" + "=" + packageId;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	//批量号码开停
	public String memberBatch(String msisdns,String optType) {
		String method = "triopi.business.member.batch.switch";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("optType", optType);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method +
				"&" + "msisdns" + "=" + msisdns+"&" + "optType" + "=" + optType;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	
	//批量GPRS开停
	public String gprsBatch(String msisdns,String optType) {
		String method = "triopi.business.gprs.batch.switch";
		Map<String, String> paramMap = SignUtils.genComMap();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("msisdns", msisdns);
		paramMap.put("optType", optType);
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		String httpUrl = ConstantsUtils3.URL + SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method +
				"&" + "msisdns" + "=" + msisdns+"&" + "optType" + "=" + optType;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	//批量号码月流量查询
	public String batchMothFlowQuery(String groupCode) {
		String method = "triopi.member.dailyflow.realtime.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
	    String	transIDVal=ConstantsUtils3.dataString();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		paramMap.put(ConstantsUtils3.transID, transIDVal);
		System.out.println(paramMap.toString());
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		System.out.println(secret);
		String httpUrl = ConstantsUtils3.URL+ SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method +"&" +ConstantsUtils3.transID+"="+transIDVal
				+"&" + "groupCode" + "=" + groupCode;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	//批量号码历史月流量查询
	public String batchMothFlow(String groupCode,String month) {
		String method = "triopi.member.monthflow.batch.query";
		Map<String, String> paramMap = SignUtils.genComMap();
		String	transIDVal=ConstantsUtils3.dataString();
		paramMap.put(ConstantsUtils3.method, method);
		paramMap.put("groupCode", groupCode);
		paramMap.put("month", month);
		paramMap.put(ConstantsUtils3.transID, transIDVal);
		System.out.println(paramMap.toString());
		String secret = SignUtils.sign(paramMap, ConstantsUtils3.secret);
		System.out.println(secret);
		String httpUrl = ConstantsUtils3.URL+ SignUtils.genComUtr() + "&" + ConstantsUtils3.sign + "=" + secret + "&"
				+ ConstantsUtils3.method + "=" + method +"&" +ConstantsUtils3.transID+"="+transIDVal
				+"&" + "groupCode" + "=" + groupCode+"&" + "month" + "=" + month;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		String decrypt = DESUtils.decrypt(result, ConstantsUtils3.secret);
		return decrypt;
		
	}
	public void  ftpFileDownload(){
		Date date = new Date();//获取当前时间    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		/*Calendar calendar = Calendar.getInstance();    
		 calendar.setTime(date);    
		 calendar.add(Calendar.DAY_OF_MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		 Date time = calendar.getTime();*/
		StringBuffer sb = new StringBuffer();
		List<LaoDownloadfileConfig> list = laoDownload.selectListFile();
		if (null!=list && list.size()>0) {
			for (LaoDownloadfileConfig fileConfig : list) {
				sb.append(fileConfig.getFileprefixName());
				if (null!=fileConfig.getFilesuffixDate()) {
					Date filesuffixDate = fileConfig.getFilesuffixDate();
					sb.append(sdf.format(filesuffixDate));
				}else{
					sb.append(sdf.format(date));
				}
				sb.append(",");
			}
			String destFileName = sb.toString().substring(0,sb.toString().length()-1);
			System.out.println(destFileName);
			//String destFileName="FL_MEMBER_LIFECYCLE_2001219805_"+format+","+"FL_MEMBER_ICCID_2001219805_"+format;
			 try {
				 log.info("文件保存路径>>>>>>>>>>>>>>>>>>>>>>"+downloadName);
				 long statTime = System.currentTimeMillis();
				 String downloadFile = FtpUtils.downloadFile(destFileName,downloadName);
				 long endTime = System.currentTimeMillis();
				 log.info("文件下载的时长>>>>>>>>>>>>>>>>>>>>>>"+(endTime-statTime)/1000);
				 log.info("文件下载返回结果>>>>>>>>>>>>>>>>>>>"+downloadFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

	public static void main(String[] args) {
		InterfaceMethodImpl mm = new InterfaceMethodImpl();
		//String batchMothFlow = mm.batchMothFlowQuery("2001219805");
//		String batchMothFlow = mm.batchMothFlow("2001219805","201706");
//		System.out.println(batchMothFlow);
		//System.out.println(mm.batchMothFlowQuery("2001219805"));
		// 集团信息查询
		// String result = mm.infoQuery("2001219805");
		// 单个号码信息查询
		// String result = mm.OneMsisdnQuery("1064887825775");
		// 单个号码功能状态查询
		// String result = mm.OneMsisdnFunctionQuery("1064887825775");
		// String result = mm.StopOpenMsisdn("1064887825775", "2");
		// String result =
		// mm.BusinessOrderNoQuery("OI-NUMSWITCH-2017042813511107150524");
		// 套餐修改
		// String result = mm.planChange("1064887825775","1","I00010100067");
		//String result = mm.addPlanChange("1064887825775", "I00010100067");
        //
		//String result = mm.busHandHistoryQuery("1064887825775");
		//使用总量查询
		//String result = mm.useTotalFlowQuery("1064887825775");
		//String result = mm.dailyFlow("1064887825775");
		//短信重置
		//String result = mm.onoff("1064887825775");
		//String msisdn="1064887825775";
		//String msisdns="1064887825775,1064888362778";
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//String  date=sdf.format(new Date());
		//String groupCode="2001219805";
		//String dayFlowsQuery = mm.stopOpenMsisdn(msisdn,"1");
		//String orderNo="OI-NUMSWITCH-2017050217054247153597";
		String result = mm.stopOpenGPRS("1064715029206","2");
		//String result = mm.businessOrderNoQuery("OI-GPRSSWITCH-2017050310170953787268");
		//System.out.println(dayFlowsQuery);
		//String result = mm.useTotalFlowQuery(msisdn);
		//HashMap<String,String> map = new HashMap<>();
		Date date = new Date();//获取当前时间    
		/*Calendar calendar = Calendar.getInstance();    
		 calendar.setTime(date);    
		 calendar.add(Calendar.DAY_OF_MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		 Date time = calendar.getTime();*/
		//String format = sdf.format(date);
		//FL_MEMBER_MONTH_FEE_2001219805_20170527
		//FL_MEMBER_ICCID_2001219805_20170527
		System.out.println(result);
		//mm.ftpFileDownload();
	}

}
