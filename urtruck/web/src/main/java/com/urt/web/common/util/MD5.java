package com.urt.web.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Ordering;

public class MD5 {
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f' };
	
	
	/**
	 * 根据content,key,按预定算法计算hash??
	 * 
	 * @param text
	 * @param key
	 * @return 加密结果
	 */
	public static String hash(String text, String key) {
		
		if (text == null) {
			throw new IllegalArgumentException("text can't be null");
		}
		if (key == null) {
			throw new IllegalArgumentException("key can't be null");
		}

		// 1.令S=MD5(key);将text末尾????6字节的整数??(n),将补0后的text??6字节分组
		// 为c(1),c(2),...c(n);令b(1),b(2),...b(n)为中间变??令最终结果为hash.
		String S = md5(key);
		byte[] textData = text.getBytes();
		int len = textData.length;
		int n = (len + 15) / 16;
		byte[] tempData = new byte[n * 16];
		for (int i = len; i < n * 16; i++) {
			tempData[i] = 0;
		}
		System.arraycopy(textData, 0, tempData, 0, len);
		textData = tempData;
		String[] c = new String[n];
		for (int i = 0; i < n; i++) {
			c[i] = new String(textData, 16 * i, 16);
		}
		// end c
		String[] b = new String[n];
		String hash;

		// 2.计算b(i)
		// b(1)=MD5(S+c(1))
		// b(2)=MD5(b(1)+c(2))
		// ...
		// b(n)=MD5(b(n-1)+c(n))
		String temp = S;
		String target = "";
		for (int i = 0; i < n; i++) {
			b[i] = md5(temp + c[i]);
			temp = b[i];
			target += b[i];
		}

		// 3.hash=MD5(b(1)+b(2)+...+b(n))
		hash = md5(target);
		return hash;
	}
	/**
	 * Converts an array of bytes into an array of characters representing the
	 * hexidecimal values of each byte in order. The returned array will be
	 * double the length of the passed array, as it takes two characters to
	 * represent any given byte.
	 * 
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @return A char[] containing hexidecimal characters
	 */
	private static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}

	private static MessageDigest getMD5Digest() {
		try {
			MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
			md5MessageDigest.reset();
			return md5MessageDigest;
		} catch (NoSuchAlgorithmException nsaex) {
			throw new RuntimeException("Could not access MD5 algorithm, fatal error");
		}
	}
	
	/**
	 * 计算content的md5摘要.
	 * 
	 * @param content
	 * @return md5结果
	 */
	public static String md5(String content) {
		try {
			byte[] data = getMD5Digest().digest(content.getBytes());
			char[] chars = encodeHex(data);
			return new String(chars);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static String md5UTF8(String content) {
		/*原来的

		try {
			byte[] data = getMD5Digest().digest(content.getBytes("UTF-8"));
			char[] chars = encodeHex(data);
			return new String(chars);
		} catch (Exception ex) {
			return null;
		}*/
		try {
			return SHA.sha256(content);
		}catch (Exception ex) {
			return null;
		}
	}
	/**
	 * 计算content的md5摘要.
	 * 
	 * @param content
	 * @return md5结果
	 */
	public static String md5GBK(String content) {
		try {
			byte[] data = getMD5Digest().digest(content.getBytes("GBK"));
			char[] chars = encodeHex(data);
			return new String(chars);
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * 手机助手的md5算法
	 * 
	 * @param str
	 * @return md5结果
	 */
	public static String baifubaoMD5(String str) {  
        MessageDigest md5 = null;  
        try {  
            md5 = MessageDigest.getInstance("MD5"); 
        } catch(Exception e) {  
            e.printStackTrace();
            return "";  
        }
        char[] charArray = str.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
          
        for(int i = 0; i < charArray.length; i++) {  
            byteArray[i] = (byte)charArray[i];  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for( int i = 0; i < md5Bytes.length; i++) {  
            int val = ((int)md5Bytes[i])&0xff;  
            if(val < 16) {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }
	public static String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
			while (value.length() < 32) {
				value = "0" + value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public static String md5AndSort(Map paramsMap, String key) {
		Set<String> keySet = paramsMap.keySet();
		Ordering<String> ordering = Ordering.natural();
		List<String> list = ordering.sortedCopy(keySet);
		StringBuffer stringBuffer = new StringBuffer();
		for(String mapKey : list) {
			if(paramsMap.get(mapKey) == null )
				continue;
			stringBuffer.append(mapKey).append("=").append(paramsMap.get(mapKey)).append("&");
		}
		stringBuffer.append("key=").append(key);
		System.out.println(stringBuffer.toString());
		return md5UTF8(stringBuffer.toString());
	}
	
	public static String md5AndSortAndEncode(Map paramsMap, String key) {
		Set<String> keySet = paramsMap.keySet();
		Ordering<String> ordering = Ordering.natural();
		List<String> list = ordering.sortedCopy(keySet);
		StringBuffer stringBuffer = new StringBuffer();
		for(String mapKey : list) {
			if(paramsMap.get(mapKey) == null)
				continue;
			try {
				stringBuffer.append(mapKey).append("=").append(URLEncoder.encode(paramsMap.get(mapKey).toString(), "utf-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		stringBuffer.append("key=").append(key);
		System.out.println(stringBuffer.toString());
		return md5UTF8(stringBuffer.toString());
	}
	
	public static String md5UTF8ForEhoo(Map paramsMap, String md5Key) {
		Set<String> keySet = paramsMap.keySet();
		Ordering<String> ordering = Ordering.natural();
		List<String> list = ordering.sortedCopy(keySet);
		StringBuffer stringBuffer = new StringBuffer();
		for(String mapKey : list) {
			stringBuffer.append(mapKey).append("=").append(paramsMap.get(mapKey)).append("&");
		}
		String paramsStr = stringBuffer.substring(0, stringBuffer.length() - 1) + md5Key;
		System.out.println("sourceStr: " + paramsStr);
		return md5UTF8(md5UTF8(paramsStr)+md5Key);
	}
	
	public static String md5AndSortForUnionPay(Map paramsMap, String key) {
		Set<String> keySet = paramsMap.keySet();
		Ordering<String> ordering = Ordering.natural();
		List<String> list = ordering.sortedCopy(keySet);
		StringBuffer stringBuffer = new StringBuffer();
		for(String mapKey : list) {
			stringBuffer.append(mapKey).append("=").append(paramsMap.get(mapKey)).append("&");
		}
		stringBuffer.append(md5UTF8(key));
		return md5UTF8(stringBuffer.toString());
	}

	public static String md5AndSortForAlipay(Map paramsMap, String key) {
		Set<String> keySet = paramsMap.keySet();
		Ordering<String> ordering = Ordering.natural();
		List<String> list = ordering.sortedCopy(keySet);
		StringBuffer stringBuffer = new StringBuffer();
		for(String mapKey : list) {
			stringBuffer.append(mapKey).append("=").append(paramsMap.get(mapKey)).append("&");
		}
		String params = stringBuffer.substring(0, stringBuffer.lastIndexOf("&"));
		params = params + key;
		System.out.println("params: " + params);
		return md5UTF8(params);
	}

}
