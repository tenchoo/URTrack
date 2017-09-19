package com.urt.service.esim;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.esim.Es2Service;
import com.urt.service.esim.utils.KeyStoreUtils;

@Service("es2Service")
public class Es2ServiceImpl implements Es2Service {

	private static final String Httpurl = "https://lenovo.prod.ondemandconnectivity.com:12912/gsma/rsp2/es2plus/";

	// downloadOrder 预绑定
	// ConfirmOrder 确认绑定或者解绑
	// ReleaseProfile 发布profile
	// CancelOrder 解绑
	private static final Logger log = Logger.getLogger(Es2ServiceImpl.class);

	// 预绑定
	public String downloadOrder(String iccid, String eid) {

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("iccid", iccid);
		requestJSON.put("eid", eid);
		JSONObject headerJSON = new JSONObject();
		headerJSON.put("functionRequesterIdentifier", "CM2RequesterID");
		headerJSON.put("functionCallIdentifier", "");
		requestJSON.put("header", headerJSON);
		String requestParams = requestJSON.toString();

		String connectEs2 = null;
		try {
			URL url = new URL(Httpurl + "downloadOrder");
			connectEs2 = KeyStoreUtils.connectEs2(requestParams, url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connectEs2;

	}

	/**
	 * 确认接口
	 * 
	 * @param iccid
	 * @param eid
	 * @return
	 */

	public String confirmOrder(String iccid) {

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("iccid", iccid);
		requestJSON.put("releaseFlag", true);
		JSONObject headerJSON = new JSONObject();
		headerJSON.put("functionRequesterIdentifier", "CM2RequesterID");
		headerJSON.put("functionCallIdentifier", "");
		requestJSON.put("header", headerJSON);
		String requestParams = requestJSON.toString();

		String connectEs2 = null;
		try {
			URL url = new URL(Httpurl + "confirmOrder");
			connectEs2 = KeyStoreUtils.connectEs2(requestParams, url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connectEs2;

	}

	/**
	 * 发布profile
	 * 
	 * @param iccid
	 * @return
	 */
	public String releaseProfile(String iccid) {

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("iccid", iccid);
		//requestJSON.put("releaseFlag", true);
		JSONObject headerJSON = new JSONObject();
		headerJSON.put("functionRequesterIdentifier", "CM2RequesterID");
		headerJSON.put("functionCallIdentifier", "");
		requestJSON.put("header", headerJSON);
		String requestParams = requestJSON.toString();

		String connectEs2 = null;
		try {
			URL url = new URL(Httpurl + "releaseProfile");
			connectEs2 = KeyStoreUtils.connectEs2(requestParams, url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connectEs2;

	}

	// 解绑
	public String cancelOrder(String iccid, String eid, String matchingId) {

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("iccid", iccid);
		requestJSON.put("eid", eid);
		requestJSON.put("matchingId", matchingId);

		requestJSON.put("finalProfileStatusIndicator", "Available");
		JSONObject headerJSON = new JSONObject();
		headerJSON.put("functionRequesterIdentifier", "CM2RequesterID");
		headerJSON.put("functionCallIdentifier", "");
		requestJSON.put("header", headerJSON);
		String requestParams = requestJSON.toString();

		String connectEs2 = null;
		try {
			URL url = new URL(Httpurl + "cancelOrder");
			connectEs2 = KeyStoreUtils.connectEs2(requestParams, url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connectEs2;

	}

	public static void main(String[] args) {

		// String
		// matching="21451288CC3952712956517034773AFE420C4CA1980637DA000226189101FBA7";
		Es2ServiceImpl pl = new Es2ServiceImpl();
//		String downloadOrder = pl.cancelOrder("89860007180304180022", "89033023001211360000000000195129",
//				"QA91VUPHLCR37025");
		String downloadOrder = pl.downloadOrder("89354010160302000463","89033023001211360000000000195129");
		// String downloadOrder = pl.confirmOrder("89860200000000000004");
		 //String downloadOrder = pl.releaseProfile("89860200000000000004");
		System.out.println(downloadOrder);
		//JSONObject downloadOrderJSON = JSON.parseObject(downloadOrder);
		//if (checkEs2Resp(downloadOrder) && null != downloadOrderJSON.getString("iccid")) {
			// String confirmOrder = es2Service.confirmOrder(iccid);
			// JSONObject parseObject = JSON.parseObject(confirmOrder);
			// mactingId = parseObject.getString("mactingId");
			// dpAddress = parseObject.getString("dpAddress");
		//}

	}

	

}
