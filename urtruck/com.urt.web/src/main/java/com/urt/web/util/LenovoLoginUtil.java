package com.urt.web.util;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.urt.web.SendPostReqUtil;

public class LenovoLoginUtil {
	public static String login(String realm, String userName, String password, String localIp){
		String privateKeyStr = LenovoLoginConstants.privateKeyStr;
		String tokenUrl = LenovoLoginConstants.tokenUrl;
		HttpPost httpPostOfToken = new HttpPost(tokenUrl);// 创建httpPost  
		String assertion = null;
		try {
			JSONObject headerObj = new JSONObject();
			headerObj.put("alg", "RS256"); //写死
			headerObj.put("typ", "JWT");//写死
			String header = headerObj.toString();
			String token_header = Base64.encodeBase64String(header.getBytes("utf-8"));
			JSONObject payloadObj = new JSONObject();
			payloadObj.put("iss", realm+"@realm.passport.lenovo.com");
			payloadObj.put("scope", "passport.lenovo.com/proxyapi");
			payloadObj.put("aud", "https://passport.lenovo.com/interserver/accounts/1.4/realm/token");
			//以下可以自己写过期时间
			payloadObj.put("exp", System.currentTimeMillis()/1000+60*60+"");
			payloadObj.put("iat", System.currentTimeMillis()/1000+"");
			String payload = payloadObj.toString();
			String token_payload = Base64.encodeBase64String(payload.getBytes("utf-8"));
			PrivateKey privateKey = RSAUtil.getPrivateKey((privateKeyStr));
			String signature = RSAUtil.sign(privateKey, (token_header+"."+token_payload).getBytes("utf-8"));
			assertion  = token_header+"."+token_payload + "." + signature;
			System.out.println(assertion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer"));
		nameValuePairs.add(new BasicNameValuePair("assertion", assertion));
		try {
			httpPostOfToken.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String token = SendPostReqUtil.sendHttpPost(httpPostOfToken);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+token);
		JSONObject tokenJson = JSONObject.fromObject(token);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+tokenJson.getString("access_token"));
		
		String authUrlStr = LenovoLoginConstants.loginUrl+userName;
		HttpPost httpPost = new HttpPost(authUrlStr);// 创建httpPost  
		// 创建参数队列  
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("realm", realm));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("source", realm));
		nameValuePairs.add(new BasicNameValuePair("lang", "zh-CN"));
		nameValuePairs.add(new BasicNameValuePair("clientip", localIp));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			httpPost.setHeader("Authorization", "Bearer {"+tokenJson.getString("access_token")+"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsonStr = SendPostReqUtil.sendHttpPost(httpPost);
		return jsonStr;
	}
	
}
