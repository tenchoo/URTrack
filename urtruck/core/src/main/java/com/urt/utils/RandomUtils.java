package com.urt.utils;

import java.security.SecureRandom;

/**
 * 随机数生成工具
 * 
 * @author weiyt1
 *
 */
public final class RandomUtils {

	// 所有字符
	private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 生成定长的随机数，每位由数字或者字符组成
	 * 
	 * @param length 长度
	 * @return
	 */
	public static String generateMixStr(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}
}
