package com.urt.web.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {
	private static String algorithm="RSA";
	
	private static final String signalgorithm = "SHA256withRSA";
	
	public static PrivateKey getPrivateKey(String key) throws Exception {    
		byte[] keyBytes;
		keyBytes =Base64.decodeBase64(key);

		return RSASignatureUtils.getPrivateKey(keyBytes, algorithm);
	}
	
	public static String sign(PrivateKey privateKey,byte[] data) throws SignatureException, InvalidKeyException{
		byte[] signed= null;
		try {
			Signature signet = Signature.getInstance(signalgorithm);
			
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
}
