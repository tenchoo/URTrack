package com.urt.Ability.unicom.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSONObject;

public class HttpsUtil {

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post鏂瑰紡璇锋眰鏈嶅姟鍣�https鍗忚)
	 * 
	 * @param url
	 *            璇锋眰鍦板潃
	 * @param content
	 *            鍙傛暟
	 * @param charset
	 *            缂栫爜
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static String doPost(String url, String content, String charset)
			throws Exception {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
				new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 鍒锋柊銆佸叧闂�
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toString();
		}
		return null;
	}

/*	public static void main(String[] args) {
		String httpsUrl = "https://202.96.18.121:20000/openplatform/rest/1.0/allPreferentialQuery";
//		String httpsUrl1 = "https://202.96.18.121:20000/openplatform/rest/1.0/billStatusQuery";
//		String json = "{\"appKey\":\"20001000\",\"timeStamp\":\"20161129163800\",\"GetUserDiscntInfoReq\": {\"TradeId\": \"2016102711135499\",\"PhoneNo\": \"13021172187\",\"PhoneType\": \"G\",\"OperDepart\": \"Z000KF\",\"Operator\": \"BJKFIVR\",\"DiscntId\": \"A\"}}";
//		String str = "";//20001012
//		try {
//			str = doPost(httpsUrl, message,"UTF-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(str);
		GetUserDiscntInfoReq infoReq = new GetUserDiscntInfoReq();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		infoReq.setDiscntId("A");
		infoReq.setOperator("Z000LX");
		infoReq.setOperDepart("Z00LX");
		infoReq.setPhoneNo("18519560766");//18519560766銆�8519552018銆�8519552038銆�8519556055
		infoReq.setPhoneType("G");
		infoReq.setTradeId(sdf.format(new Date()));
		StringBuilder strBuil = new StringBuilder();
		strBuil
				.append("DiscntId=").append(infoReq.getDiscntId())
				.append("&OperDepart=").append(infoReq.getOperDepart())
				.append("&Operator=").append(infoReq.getOperator())
				.append("&PhoneNo=").append(infoReq.getPhoneNo())
				.append("&PhoneType=").append(infoReq.getPhoneType())
				.append("&TradeId=").append(infoReq.getTradeId())
				.append("&appKey=").append("10001014")
				.append("&timeStamp=").append(sdf.format(new Date()))
				;
		String text = strBuil.toString();
		System.out.println("绛惧悕鍓峵ext:"+text);
		String md5Str = "";
		try {
//			String textEncoder = URLEncoder.encode(text, "UTF-8");
//			System.out.println("textEncoder:"+textEncoder);
//			md5Str = MD5.sign(textEncoder, "", "UTF-8");
//			System.out.println("md5Str:"+md5Str);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKBqPF3aYBSBJE7IuEQx4qwxpJvWH7Z9f/YUqKPEC87dw0vOKug0eWCWhfdwe7dRtkKXPn4t4B4WIreMYBJz4ibUeFn3vqcY4+2FrpPEJMQ1ahDypRBOC+5YrPGcVXSzUG7tJBT7bKLBPxu3WraAzX8edYK8PAnc630ipdkvfMbTAgMBAAECgYBOTKycjvv45jRHtFelCch/jlevrSubktSD0/+guA+fcMVj2gU9hGd+itpnNeNdTqgtBs/9HP1ZEirt9rnqJ+BJl+FChcHeHno7VIiyu/TNUpyvHmEHqK/ZBHuzCx2DorBOyAycf9PSpeYbyvXW0KoBUpbRrF6nYJ0mhIKYZa96YQJBANBzVn/XHYUXAStOEMTdSDf71ErmpvB9oKSgiGgAooKZNUbOwZtz0alwwt+tmTzjunuaT8GszmuRxc6jgOl0q4MCQQDFAdE2AbUX8nUrgTFbG0RT2CGpVcygNvjMqs4g65IkhR2/0aTfE7npR63hEP+aYmK71vatL7B7GgFTBh9zqAZxAkEAwGalIYyhr7WDG8CLk9oXja0Azz3EOtgKDcoq/awQU/EsDrZKV7z4eageBy2J8nzUJMBS+5YQVZOQe+HI3DMXFwJAXsZJB1o67JJPm+ZDADlV3aAUUYpdLpZDivKAx4WMgNUpQqZwUoEC5x78n1G2JWc/aG45rYdLt20DRhWj+9fRgQJBALVvz0lNoKSB00V7v4KYUGEKTo1EGq+G2+/ajSbpT/uYuCfjr14T0Knj/yzVVDAIAf9oJg/aesZZQGCWNggrGTg=";	
		String sign = "";
		try {
			text = StringUtil.sortOrginReqStr(text);
			System.out.println("text:"+text);
			System.out.println("杞爜鍓峜ontent锛� + text);
			String enQryStr = URLEncoder.encode(text, "UTF-8");

			System.out.println("杞爜鍚巈nQryStr锛� + enQryStr);
			md5Str = MD5.ToMD5(enQryStr);
			System.out.println("md5Str::::" + md5Str);
			sign = RSAUtils.sign(md5Str.getBytes(), privateKey);
			sign = URLEncoder.encode(sign, "UTF-8");// 鏈湴娴嬭瘯鍙互涓嶇敤encode
			
//			sign = Rsa.sign(md5Str, privateKey, "UTF-8");
//			System.out.println(sign);
//			sign = URLEncoder.encode(sign, "UTF-8");
			System.out.println(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("sign:"+sign);
		text = text + "&sign=" +sign ;
		System.out.println("绛惧悕鍚巘ext:"+text);
		String retJson = "";
		String t ="";
		try {
			System.out.println("鍙戦�璇锋眰---------------");
			long t1 = System.currentTimeMillis();
			String url = httpsUrl+"?sign="+sign;
//			System.out.println("url:"+httpsUrl+"?sign="+sign);
			retJson = HttpsUtil.doPost(httpsUrl,text, "UTF-8");
			long t2 = System.currentTimeMillis();
			t = (t2-t1)+"";
			System.out.println("鐢ㄦ椂锛�+t+"姣");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("杩斿洖鎶ユ枃retJson:"+ retJson);
//		retJson = retJson.replace("[\"\",", "[");
		Gson gson = new Gson();
		BillStatusQueryOutBean outBean = gson.fromJson(retJson,new TypeToken<BillStatusQueryOutBean>() {}.getType());
		if (outBean != null) {
			if (outBean.getGetUserDiscntInfoRsp() != null) {
				System.out.println("tradeId:"+outBean.getGetUserDiscntInfoRsp().getTradeId());
			}
		}
	}*/
	public static void main(String[] args) throws Exception {
		String url="https://219.142.224.173:3000/emm/api/user/login";
		JSONObject json=new JSONObject();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("loginName",  "ceshi");
		map.put("password", "12345678");
		json.put("data",map);
		json.put("companyCode", "Lenovo");
		String jsonStr=json.toString();
		System.out.println("jsonStr="+jsonStr);
		String result=doPost(url,json.toString(),"UTF-8");
		System.out.println(result);
//		Integer.valueOf(100);
//		Integer.valueOf("100");
	}
}