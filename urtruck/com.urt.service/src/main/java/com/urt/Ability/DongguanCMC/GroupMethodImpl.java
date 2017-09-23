package com.urt.Ability.DongguanCMC;

import java.util.Map;

import org.eclipse.jetty.util.log.Log;
import org.springframework.stereotype.Service;

import com.urt.interfaces.DongguanCMC.GroupMethod;
import com.urt.utils.HttpClientUtil;

@Service("groupMethod")
public class GroupMethodImpl implements GroupMethod {
	public static void main(String[] args) {
		GroupMethodImpl s = new GroupMethodImpl();
		System.out.println(s.infoQuery("2001219805"));
	}

	// 集团信息查询API
	public String infoQuery(String ecCode) {
		String method = "iot.group.info.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);

		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 集团成员批量停机告警清单查询API
	public String dailyalarmQuery(String ecCode, String dateStr, Integer pageNo) {
		String method = "iot.group.dailyalarm.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.dateStr, dateStr);
		paramMap.put(ConstantsUntil.pageNo, pageNo.toString());
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.dateStr + "=" + dateStr + "&"
				+ ConstantsUntil.pageNo + "=" + pageNo + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 集团成员批量流量告警清单查询
	public String dailydatausagealarmQuery(String ecCode, String dateStr,
			Integer pageNo) {
		String method = "iot.group.dailydatausagealarm.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.dateStr, dateStr);
		paramMap.put(ConstantsUntil.pageNo, pageNo.toString());
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.dateStr + "=" + dateStr + "&"
				+ ConstantsUntil.pageNo + "=" + pageNo + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 集团成员批量日流量查询
	public String dailydatausageQuery(String ecCode, String dateStr,
			Integer pageNo) {

		String method = "iot.group.dailydatausage.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.dateStr, dateStr);
		paramMap.put(ConstantsUntil.pageNo, pageNo.toString());
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.dateStr + "=" + dateStr + "&"
				+ ConstantsUntil.pageNo + "=" + pageNo + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 集团成员批量月流量查询
	public String monthlydatausageQuery(String ecCode, String month,
			Integer pageNo) {

		String method = "iot.group.monthlydatausage.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.month, month);
		paramMap.put(ConstantsUntil.pageNo, pageNo.toString());
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.month + "=" + month + "&"
				+ ConstantsUntil.pageNo + "=" + pageNo + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 集团成员批量费用查询
	public String memberbillQuery(String ecCode, String month, Integer pageNo) {

		String method = "iot.group.memberbill.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.month, month);
		paramMap.put(ConstantsUntil.pageNo, pageNo.toString());
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.month + "=" + month + "&"
				+ ConstantsUntil.pageNo + "=" + pageNo + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 集团总费用查询（历史）
	public String billQuery(String ecCode, String month) {
		String method = "iot.group.bill.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.month, month);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.month + "=" + month + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 集团成员信息查询
	public String memberInfoQuery(String ecCode, String pageNo) {
		String method = "iot.group.memberInfo.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.pageNo, pageNo.toString());
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.pageNo + "=" + pageNo + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 通过集团编号和月份查询集团的购卡总数
	public String membercountQuery(String ecCode, String month) {
		String method = "iot.group.membercount.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.month, month);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.month + "=" + month + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 集团产品信息查询
	public String productInfoQuery(String ecCode) {
		String method = "iot.group.productInfo.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// paramMap.put(ConstantsUntil.sign, secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	// 集团余额信息实时查询
	public String balanceQuery(String ecCode, String productCode) {
		String method = "iot.group.balance.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ecCode, ecCode);
		paramMap.put(ConstantsUntil.productCode, productCode);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.ecCode + "=" + ecCode + "&"
				+ ConstantsUntil.productCode + productCode + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	@Override
	public String test(String ecCode) {
		// TODO Auto-generated method stub
		return ecCode;
	}

}
