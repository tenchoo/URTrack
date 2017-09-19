package com.urt.web.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {
	// key='8da530b3bdcb4f53bd86d15f5027a325'
	public static String aesEncrypt(String str, String key) throws Exception {
		if (str == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,
				new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
		return new BASE64Encoder().encode(bytes);
	}

	public static String aesDecrypt(String str, String key) throws Exception {
		if (str == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE,
				new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = new BASE64Decoder().decodeBuffer(str);
		bytes = cipher.doFinal(bytes);
		return new String(bytes, "utf-8");
	}
	public static void main(String[] args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+aesEncrypt("wechat", "e2da530b3bdcb4f5"));
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+aesDecrypt("ClYyknavws9Z4vX/9XRI2KP5tbvRk/Bb3vq+njAiHOBHZpJOh4RADcl6MoMaN/ZaZaNM03nrRdfiihbTud7bbGCeiD9ZPqDreqC7ukCeOAZE+kt+ivgZFAjxgrlMXMe9vcctpkwbO/T2+TE0CCDowQ==", "e2da530b3bdcb4f5"));
	}
}
