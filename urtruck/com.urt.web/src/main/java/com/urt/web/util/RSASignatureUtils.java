/*
 * 版权所有 ( c ) 联想集团有限公司 2005-2010 保留所有权利。
 * 项目	: Lenovo LeShare Service
 * 文件名	: RSASignature.java
 * 版本	: 1.0
 * 描述	:
 * 作者	: zhangjh
 * 日期	: Dec 30, 2011 
 * 修改历史：
 * 【时间】		【修改者】	【修改内容】
 */


package com.urt.web.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @ClassName	: RSASignatureUtils
 * @author		: 张军宏(jhzhanga@isoftstone.com)
 * @version		: 
 * @Date		: Dec 30, 2011
 *
 * @Description	: RSA签名工具
 * @see			: 
 */

public class RSASignatureUtils {
	/**
	 *  签名算法：MD5withRSA
	 */
	private static final String algorithm = "MD5withRSA";

	/**
	 * @Title:	sign
	 * @Description	生成签名数据，并对签名数据做encodeBase64URLSafeString编码
	 * @param privateKey 用户私钥对象
	 * @param data	待签名数据
	 * @return
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public static String sign(PrivateKey privateKey,byte[] data) throws SignatureException, InvalidKeyException{
		byte[] signed= null;
		try {
			Signature signet = Signature.getInstance(algorithm);
			
			signet.initSign(privateKey);
			signet.update(data);
			signed = signet.sign();
		} catch (NoSuchAlgorithmException e) {
			throw new SignatureException("NoSuchAlgorithmException",e.getCause());
		} catch (InvalidKeyException e) {
			throw e;
		} catch (SignatureException e) {
			throw e;
		}
		
		return Base64.encodeBase64URLSafeString(signed);
	}
	/**
	 * @Title:	verify
	 * @Description	确认签名数据
	 * @param publicKey 用户公钥
	 * @param data	明文
	 * @param signedStr	待验证的签名数据
	 * @return 如果签名数据正确，返回true;否则返回false;
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public static boolean verify(PublicKey publicKey,byte[] data,String signedStr) throws SignatureException, InvalidKeyException{
		boolean isVerified = false;
		try {
			Signature signetCheck = Signature.getInstance(algorithm);
			
			signetCheck.initVerify(publicKey);
			signetCheck.update(data);
			
			byte[] signed = Base64.decodeBase64(signedStr);
			isVerified = signetCheck.verify(signed);
		} catch (NoSuchAlgorithmException e) {
			throw new SignatureException("NoSuchAlgorithmException",e.getCause());
		} catch (InvalidKeyException e) {
			throw e;
		}catch (SignatureException e) {
			throw e;
		}
		
		return isVerified;
	}
	
	/**
	 * @Title:	getPublicKey
	 * @Description 获取公钥
	 */
	public static PublicKey getPublicKey(byte[] keyBytes,String algorithm) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	/**
	 * @Title:	getPrivateKey
	 * @Description	获取私钥
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes,String algorithm) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
}

