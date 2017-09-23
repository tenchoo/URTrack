package com.urt.Ability.JiangSuCMC;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class TestE {

	
	protected static final Logger logger = Logger.getLogger(TestE.class);
	private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(30000)
            .setConnectTimeout(30000)
            .setConnectionRequestTimeout(30000)
            .build();
	public static void main(String[] args) throws UnsupportedOperationException {
		
		
		/*Date date1 = new Date();
		SimpleDateFormat df1=new SimpleDateFormat("yyyyMMddHHmmsssss");
		String randomId1 = df1.format(date1);
		Map<String, String> paramValues1  = new HashMap<String,String>();
		paramValues1.put("custId", "3071341381105330");//3071311390025064  3071359090428354
		paramValues1.put("serverName", "Lao.base.cardState.query"); //Lao.base.porudct.query  
		paramValues1.put("iccid", "89860617020000054787");//stateCode
		paramValues1.put("randomId", randomId1+"123");
		//paramValues1.put("goodsId", "824");

		List<String> ignoreParamNames1 = new ArrayList<String>();
		ignoreParamNames1.add("appKey");
		String md51 = TestE.md5Sign(paramValues1, ignoreParamNames1, "829vnlV992QCLU16O068hs727HB7K8");
		System.out.println("md5:"+md51);
		String httpUrl1 = "http://gla.lenovo.com//httpOpenServer/serviceProvide" + "?" 
				+ "serverName"+"="+"Lao.base.cardState.query" + "&" 
				+ "iccid"+"="+"89860617020000054787"+"&" //1064887825795   1064829750194 
				+ "custId"+"="+"3071341381105330"+"&" 
				//+ "goodsId"+"="+"824"+"&"
				+ "randomId"+"="+randomId1+"123"+"&"
				+ "sign"+"="+md51;
		
			TestE t1 = new TestE();
			System.out.println(httpUrl1);	
			String responseContent1 = t1.sendHttpsGe(httpUrl1);
			System.out.println("%%%%%%%%%%%%%%%%%%"+responseContent1);*/	
		
		
		
		/*Date date1 = new Date();
		SimpleDateFormat df1=new SimpleDateFormat("yyyyMMddHHmmsssss");
		String randomId1 = df1.format(date1);
		Map<String, String> paramValues1  = new HashMap<String,String>();
		paramValues1.put("custId", "3071341381105330");//3071311390025064  3071359090428354
		paramValues1.put("serverName", "Lao.base.flowOrder.change"); //Lao.base.porudct.query  
		paramValues1.put("iccid", "89860617020000054787");//stateCode
		paramValues1.put("randomId", randomId1+"123");
		paramValues1.put("goodsId", "824");

		List<String> ignoreParamNames1 = new ArrayList<String>();
		ignoreParamNames1.add("appKey");
		String md51 = TestE.md5Sign(paramValues1, ignoreParamNames1, "829vnlV992QCLU16O068hs727HB7K8");
		System.out.println("md5:"+md51);
		String httpUrl1 = "http://gla.lenovo.com//httpOpenServer/serviceProvide" + "?" 
				+ "serverName"+"="+"Lao.base.flowOrder.change" + "&" 
				+ "iccid"+"="+"89860617020000054787"+"&" //1064887825795   1064829750194 
				+ "custId"+"="+"3071341381105330"+"&" 
				+ "goodsId"+"="+"824"+"&"
				+ "randomId"+"="+randomId1+"123"+"&"
				+ "sign"+"="+md51;
		
			TestE t1 = new TestE();
			System.out.println(httpUrl1);	
			String responseContent1 = t1.sendHttpsGe(httpUrl1);
			System.out.println("%%%%%%%%%%%%%%%%%%"+responseContent1);	*/

		
		/**
		 * ���ŷ���
		 */
		//&randomId=60820170619110652963&iccid=8986061702003173391N&serverName=Lao.base.surplusFlow.query&sign=a191f7b3cd8ebe25735cd35975463b5d
		Date date1 = new Date();
		SimpleDateFormat df1=new SimpleDateFormat("yyyyMMddHHmmsssss");
		String randomId1 = df1.format(date1);
		Map<String, String> paramValues1  = new HashMap<String,String>();
		paramValues1.put("custId", "3071809471147095");//3071311390025064  3071359090428354
		paramValues1.put("serverName", "Lao.base.cardDetail.query"); //Lao.base.porudct.query  
		paramValues1.put("iccid", "898607B6191790019011");//stateCode
		paramValues1.put("randomId", "12320170228170923459");
		//paramValues1.put("channelCustId", "1");
		//paramValues1.put("stateCode", "1");
		//paramValues1.put("dayDate", "2017-05");
//		paramValues1.put("startDate", "2000211500");
//		paramValues1.put("endDate", "20161230");

		List<String> ignoreParamNames1 = new ArrayList<String>();
		ignoreParamNames1.add("appKey");
		String md51 = TestE.md5Sign(paramValues1, ignoreParamNames1, "RWLURH3376YE9Y08oz96z145Z2Q1H4");
		System.out.println("md5:"+md51);
		String httpUrl1 = "http://gla.lenovo.com//httpOpenServer/serviceProvide?" 
				+ "serverName"+"="+"Lao.base.cardDetail.query" + "&" 
				+ "iccid"+"="+"898607B6191790019011"+"&" //1064887825795   1064829750194 
				+ "custId"+"="+"3071809471147095"+"&" 
				//+ "stateCode"+"="+"1"+"&"
				//+ "dayDate"+"="+"2017-05"+"&"
				+ "randomId"+"="+"12320170228170923459"+"&"
//				+ "startDate"+"="+"2000211500"+"&"
//				+ "endDate"+"="+"20161230"+"&"
				//+ "channelCustId"+"="+"1"+"&"
				+ "sign"+"="+md51;
		
//		paramValues1.put("custId", "3071030187339469");//3071311390025064  3071359090428354
//		paramValues1.put("serverName", "Lao.base.surplusFlowQueryUnicom.query"); //Lao.base.porudct.query  
//		paramValues1.put("iccid", "89860617020000032890");//stateCode
//		paramValues1.put("randomId", randomId1+"123");
//		//paramValues1.put("stateCode", "1");
//		//paramValues1.put("dayDate", "2017-05");
//		
//		List<String> ignoreParamNames1 = new ArrayList<String>();
//		ignoreParamNames1.add("appKey");
//		String md51 = TestE.md5Sign(paramValues1, ignoreParamNames1, "udR492587v3h598OS267T9qY03sQ2L");
//		System.out.println("md5:"+md51);
//		String httpUrl1 = "http://gla.lenovomm.com/httpOpenServer/serviceProvide" + "?" 
//				+ "serverName"+"="+"Lao.base.surplusFlowQueryUnicom.query" + "&" 
//				+ "iccid"+"="+"89860617020000032890"+"&" //1064887825795   1064829750194 
//				+ "custId"+"="+"3071030187339469"+"&" 
//				//+ "stateCode"+"="+"1"+"&"
//				//+ "dayDate"+"="+"2017-05"+"&"
//				+ "randomId"+"="+randomId1+"123"+"&"
//				+ "sign"+"="+md51;
		
			TestE t1 = new TestE();
			System.out.println(httpUrl1);	
			String responseContent1 = t1.sendHttpsGe(httpUrl1);
			System.out.println("%%%%%%%%%%%%%%%%%%"+responseContent1);
		
		
		/**
		 * ��״̬����
		 */
		/*Date date1 = new Date();
		SimpleDateFormat df1=new SimpleDateFormat("yyyyMMddHHmmsssss");
		String randomId1 = df1.format(date1);
		Map<String, String> paramValues1  = new HashMap<String,String>();
		paramValues1.put("custId", "3071359090428354");//3071311390025064  3071359090428354
		paramValues1.put("serverName", "Lao.base.cardState.query"); //Lao.base.porudct.query  
		paramValues1.put("iccid", "898602B5191651028393");//stateCode
		paramValues1.put("randomId", randomId1+"123");
		

		List<String> ignoreParamNames1 = new ArrayList<String>();
		ignoreParamNames1.add("appKey");
		String md51 = TestE.md5Sign(paramValues1, ignoreParamNames1, "829vnlV992QCLU16O068hs727HB7K8");
		System.out.println("md5:"+md51);
		String httpUrl1 = "http://gla.lenovomm.com//httpOpenServer/serviceProvide" + "?" 
				+ "serverName"+"="+"Lao.base.cardState.query" + "&" 
				+ "iccid"+"="+"898602B5191651028393"+"&" //1064887825795   1064829750194 
				+ "custId"+"="+"3071359090428354"+"&" 
				+ "randomId"+"="+randomId1+"123"+"&"
				+ "sign"+"="+md51;
		
			TestE t1 = new TestE();
			System.out.println(httpUrl1);	
			String responseContent1 = t1.sendHttpsGe(httpUrl1);
			System.out.println("%%%%%%%%%%%%%%%%%%"+responseContent1);	*/
		
		
			/**
			 * ��������ѯ
			 */
		/*
					Date date = new Date();
					SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmsssss");
					String randomId = df.format(date);
					Map<String, String> paramValues  = new HashMap<String,String>();
					paramValues.put("custId", "3071359090428354");//3071311390025064  3071359090428354
					paramValues.put("serverName", "Lao.base.monthFlow.query"); //Lao.base.porudct.query  
					paramValues.put("iccid", "898602B1191650107968");//stateCode
					paramValues.put("dayDate", "2017-04");
					paramValues.put("randomId", randomId+"123");
					

					List<String> ignoreParamNames = new ArrayList<String>();
					ignoreParamNames.add("appKey");
					String md5 = TestE.md5Sign(paramValues, ignoreParamNames, "829vnlV992QCLU16O068hs727HB7K8");
					System.out.println("md5:"+md5);
					String httpUrl = "http://gla.lenovo.com//httpOpenServer/serviceProvide" + "?" 
							+ "serverName"+"="+"Lao.base.monthFlow.query" + "&" 
							+ "iccid"+"="+"898602B1191650107968"+"&" //1064887825795   1064829750194 
							+ "custId"+"="+"3071359090428354"+"&" 
							+ "randomId"+"="+randomId+"123"+"&"
							+ "dayDate"+"="+"2017-04"+"&"
							+ "sign"+"="+md5;
							
					TestE t = new TestE();
					System.out.println(httpUrl);	
			   String responseContent = t.sendHttpsGe(httpUrl);
			    System.out.println("%%%%%%%%%%%%%%%%%%"+responseContent);	*/	

	}	
	
	
	
	 public static String md5Sign(Map<String, String> paramValues, List<String> ignoreParamNames,String secret) {
	        StringBuilder sb = new StringBuilder();
			List<String> paramNames = new ArrayList<String>(paramValues.size());
			paramNames.addAll(paramValues.keySet());
			if(ignoreParamNames != null && ignoreParamNames.size() > 0){
			    for (String ignoreParamName : ignoreParamNames) {
			        paramNames.remove(ignoreParamName);
			    }
			}
			Collections.sort(paramNames);
			sb.append(secret);
			for (String paramName : paramNames) {
			    sb.append(paramName).append(paramValues.get(paramName));
			}
			sb.append(secret);
			logger.info("scort:***************"+sb.toString());
	/*		String str="";
			try {
				str = URLEncoder.encode(sb.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return degistByMD5(sb.toString());
	    }
		
		public static String degistByMD5(String str){
			StringBuffer buf = null;
			try{
			  MessageDigest md = MessageDigest.getInstance("MD5");  
	          md.update(str.getBytes());  
	          byte b[] = md.digest();  
	          int i;  
	          buf = new StringBuffer("");  
	          for (int offset = 0; offset < b.length; offset++) {  
	              i = b[offset];  
	              if (i < 0)  
	                  i += 256;  
	              if (i < 16)  
	                  buf.append("0");  
	              buf.append(Integer.toHexString(i));  
	          }  
	         
	      } catch (NoSuchAlgorithmException e) {  
	      	 e.printStackTrace();
	      }
			 return buf.toString();  
		}
	
	
	
	public String sendHttpsGe(String httpUrl) {
		HttpGet httpGet = new HttpGet(httpUrl);// ����get����
		return sendHttpGet(httpGet);
	}
	
	/**
	 * ����Get����
	 * @param httpPost
	 * @return
	 */
	private String sendHttpGet(HttpGet httpGet) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// ����Ĭ�ϵ�httpClientʵ��.
			httpClient = HttpClients.createDefault();
			httpGet.setConfig(requestConfig);
			// ִ������
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �ر�����,�ͷ���Դ
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	

}
