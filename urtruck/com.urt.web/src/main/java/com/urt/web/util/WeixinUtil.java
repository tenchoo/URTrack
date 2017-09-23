package com.urt.web.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.log.Log;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class WeixinUtil {

	/**
	 * 功能描述：字典排序 (按照ascll顺序）
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:28:24
	 * @param @param paraMap
	 * @param @param urlencode
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	public static String FormatBizQueryParaMap(Map<String, String> paraMap,
			boolean urlencode) throws Exception {
		String buff = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					paraMap.entrySet());
			Collections.sort(infoIds,
					new Comparator<Map.Entry<String, String>>() {
						public int compare(Map.Entry<String, String> o1,
								Map.Entry<String, String> o2) {
							return (o1.getKey()).toString().compareTo(
									o2.getKey());
						}
					});
			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, String> item = infoIds.get(i);
				if (item.getKey() != "") {
					String key = item.getKey();
					String val = item.getValue();
					if (urlencode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					buff += key + "=" + val + "&";
				}
			}
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return buff;
	}

	/**
	 * 功能描述：得到prepay_id
	 * 
	 * @author sunhao
	 * @date 2017年1月17日 下午5:37:50
	 * @param @param xml
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getPrepayId(String xml) {
		String prepay_id = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					WeixinPayConstants.orderURL).openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");
			conn.setRequestProperty("Charset", WeixinPayConstants.charset);
			OutputStream os = conn.getOutputStream();
			os.write(xml.getBytes(WeixinPayConstants.charset));
			StringBuilder xbuilder = new StringBuilder();
			os.close();
			int responseCode = conn.getResponseCode();
			InputStreamReader in = null;
			BufferedReader br = null;
			if (responseCode == 200) {
				in = new InputStreamReader(conn.getInputStream(),
						WeixinPayConstants.charset);
				br = new BufferedReader(in);
				String retData = null;
				while ((retData = br.readLine()) != null)
					xbuilder.append(retData);

				int start = xbuilder.indexOf("<prepay_id><![CDATA[")
						+ "<prepay_id><![CDATA[".length();
				int end = xbuilder.indexOf("]]></prepay_id>");
				if (end > start)
					prepay_id = xbuilder.substring(start, end);

				if(StringUtils.isBlank(prepay_id)){
					Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信预支付ID失败"+xbuilder.toString());
				}
			}
			if (in != null)
				in.close();
			if (br != null)
				br.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}
	/**
	* 功能描述：将xml转换成json
	* @author sunhao
	* @date 2017年1月18日 上午10:08:24
	* @param @param xml
	* @param @return
	* @return String
	* @throws
	 */
	public static JSONObject xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Map iterateElement(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}
	
	//通过xml 发给微信消息
 	public static String sendXml(String return_code, String return_msg) {
 		return "<xml><return_code><![CDATA[" + return_code + "]]>" + 
 				"</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
 	}

	/**
	 * 功能描述：Md5 签名
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:28:12
	 * @param @param content
	 * @param @param key
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	public static String sign(String content, String key) throws Exception {
		String signStr = "";
		signStr = content + "&key=" + key;
		return DigestUtils.md5Hex(signStr).toUpperCase();
	}

	/**
	 * 功能描述：array 转换成xml
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:27:45
	 * @param @param arr
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (StringUtils.isNotBlank(val) && IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";
			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	/**
	 * 功能描述：判断是否是数字
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:27:26
	 * @param @param str
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能描述：MD5工具
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:29:56
	 * @param @param s
	 * @param @return
	 * @return String
	 * @throws
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MD5
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5Purity(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("utf-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			plainText = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return plainText.toUpperCase();
	}

	/**
	 * 获取支付所需签名
	 * 
	 * @param ticket
	 * @param timeStamp
	 * @param card_id
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String getPayCustomSign(Map<String, String> bizObj, String key)
			throws Exception {
		String bizString = WeixinUtil.FormatBizQueryParaMap(bizObj, false);
		return sign(bizString, key);
	}

	/**
	 * 功能描述：元分转换
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:32:44
	 * @param @param amount
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getMoney(String amount) {
		if (amount == null) {
			return "";
		}
		// 金额转化为分为单位
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
					".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
					".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
					".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 功能描述：获取服务器所在的ip地址
	 * 
	 * @author sunhao
	 * @date 2017年1月12日 上午10:33:09
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		int index = ip.indexOf(",");
		if (index != -1) {
			return ip.substring(0, index);
		} else {
			return ip;
		}
	}

	/**
	 * 生成一个随机字符串
	 * 
	 * @param length
	 *            表示生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
