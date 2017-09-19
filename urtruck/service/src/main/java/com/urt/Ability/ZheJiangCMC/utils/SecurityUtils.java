package com.urt.Ability.ZheJiangCMC.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import com.alibaba.fastjson.JSONObject;

public class SecurityUtils {
	public static final String ENCODE = "UTF-8";
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

	public static byte[] initAES256Key() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

		keyGenerator.init(256);

		SecretKey secretKey = keyGenerator.generateKey();

		return secretKey.getEncoded();
	}

	public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		keyGenerator.init(256);

		SecretKey secretKey = keyGenerator.generateKey();

		return secretKey.getEncoded();
	}

	public static byte[] encodeHmacSHA256(byte[] data, byte[] key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");

		Mac mac = Mac.getInstance(secretKey.getAlgorithm());

		mac.init(secretKey);

		return mac.doFinal(data);
	}

	public static String encodeHmacSHA256HexUpper(String data, byte[] key)
			throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
		return AESUtil.bytesToHexString(encodeHmacSHA256(data.getBytes("UTF-8"), key)).toUpperCase(Locale.US);
	}

	public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
		cipher.init(1, new SecretKeySpec(key, "AES"));
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
		cipher.init(2, new SecretKeySpec(key, "AES"));
		return cipher.doFinal(data);
	}

	public static String encodeHexUpper(byte[] data) throws UnsupportedEncodingException {
		return AESUtil.bytesToHexString(data).toUpperCase(Locale.US);
	}

	public static byte[] decodeHexUpper(String str) throws UnsupportedEncodingException {
		return Hex.decode(str.toLowerCase(Locale.US));
	}

	public static String decodeHexUpper(String str, String charsetName) throws UnsupportedEncodingException {
		return new String(Hex.decode(str.toLowerCase(Locale.US)), charsetName);
	}

	public static String encodeAES256HexUpper(String data, byte[] key)
			throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		return encodeHexUpper(encrypt(data.getBytes("UTF-8"), key));
	}

	public static String decodeAES256HexUpper(String data, byte[] key)
			throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, UnsupportedEncodingException {
		return new String(decrypt(Hex.decode(data.toLowerCase(Locale.US)), key), "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap();
		map.put("method", "ABILITY_10000629");
		map.put("format", "json");
		map.put("appId", "501140");
		map.put("messageId", "1");
		/*
		 * { "GROUP_ID": "57174301006", "BILL_ID": "1064815109582", "REGION_ID":
		 * "574", }
		 */
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", "57174301006");
		jsonObject.put("BILL_ID", "1064815109582");
		jsonObject.put("REGION_ID", "574");
		String content = "{" + "\"" + "GROUP_ID" + "\"" + ":" + "\"" + "57174301006" + "\"" + "}";
		map.put("content", jsonObject.toJSONString());
		String[] paramArr = (String[]) map.keySet().toArray(new String[map.size()]);

		Arrays.sort(paramArr);
		StringBuilder keyBuf = new StringBuilder();
		StringBuilder buf = new StringBuilder();
		buf.append("c4d3da6483feb876f0d317150d8bf118");
		for (String param : paramArr) {
			if (!"sign".equals(param)) {
				String value = (String) map.get(param.trim());
				if ((value != null) && (value.trim().length() > 0)) {
					keyBuf.append(param).append("|");
					buf.append(param).append(value.trim());
				}
			}
		}
		String string = buf.append("c4d3da6483feb876f0d317150d8bf118").toString();
		System.out.println("待加密串" + string);
		System.out.println(
				"密文" + encodeHmacSHA256HexUpper(buf.toString(), decodeHexUpper("c4d3da6483feb876f0d317150d8bf118")));
	}

	public static String getSign(Map<String, Object> map, JSONObject content,String key) {
		String sign = null;
	    map.put("content", content.toJSONString());
		String[] paramArr = (String[]) map.keySet().toArray(new String[map.size()]);
		Arrays.sort(paramArr);
		StringBuilder keyBuf = new StringBuilder();
		StringBuilder buf = new StringBuilder();
		buf.append(key);
		for (String param : paramArr) {
			if (!"sign".equals(param)) {
				String value = (String) map.get(param.trim());
				if ((value != null) && (value.trim().length() > 0)) {
					keyBuf.append(param).append("|");
					buf.append(param).append(value.trim());
				}
			}
		}
		buf.append(key);
		System.out.println("待加密串" + buf.toString());
		try {
			sign = encodeHmacSHA256HexUpper(buf.toString(), decodeHexUpper(key));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sign;
	}

}
