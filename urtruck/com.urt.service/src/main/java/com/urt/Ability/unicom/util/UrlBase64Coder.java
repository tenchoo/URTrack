package com.urt.Ability.unicom.util;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.UrlBase64;

public class UrlBase64Coder {
	
	public static final  String ENCODING = "UTF-8";

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encoded(String data) throws UnsupportedEncodingException {
		byte[] b = UrlBase64.encode(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String data) throws UnsupportedEncodingException {
		byte[] b = UrlBase64.decode(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

//	public static void main(String[] args) throws UnsupportedEncodingException {
//		String str = "merchantId=11118&merchantOrderId=123456&amount=100&userId=123&userName=18101021226&proName=测试&proDesc=测试DESC&sign=f9aed560ca0ee9b487306d64a7037f84&payType=zfb";
//		// 加密该字符串
//		String encodedString = UrlBase64Coder.encoded(str);
//		System.out.println(encodedString);
//		// 解密该字符串
//		String decodedString = UrlBase64Coder.decode(encodedString);
//		System.out.println(decodedString);
//	}
}
