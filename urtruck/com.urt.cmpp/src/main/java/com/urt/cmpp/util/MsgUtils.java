package com.urt.cmpp.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;


/**
 * 短信接口辅助工具类
 */
public class MsgUtils {
	private static int sequenceId=10000;//序列编号
	/**
	 * 序列 自增
	 */
	public static int getSequence(){
		sequenceId = Integer.valueOf(ZkGenerateSeq.get8IdSeq(SeqID.SEQUENCE_ID));
//		++sequenceId;
//		if(sequenceId>255){
//			sequenceId=0;
//		}
		return sequenceId;
	}
	/**
	 * 时间戳的明文,由客户端产生,格式为MMDDHHMMSS，即月日时分秒，10位数字的整型，右对齐 。
	 */
	public static String  getTimestamp(){
		DateFormat format=new SimpleDateFormat("MMddhhmmss");
		return format.format(new Date());
	}
	/**
	 * 用于鉴别源地址。其值通过单向MD5 hash计算得出，表示如下：
	 * AuthenticatorSource =
	 * MD5（Source_Addr+9 字节的0 +shared secret+timestamp）
	 * Shared secret 由中国移动与源地址实体事先商定，timestamp格式为：MMDDHHMMSS，即月日时分秒，10位。
	 * @return
	 */
	public static byte[] getAuthenticatorSource(String spId,String secret){
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			byte[] data=(spId+"\0\0\0\0\0\0\0\0\0"+secret+MsgUtils.getTimestamp()).getBytes();
			return md5.digest(data);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("SP链接到ISMG拼接AuthenticatorSource失败："+e.getMessage());
			return null;
		}
	}
	/**
	 * 向流中写入指定字节长度的字符串，不足时补0
	 * @param dous:要写入的流对象
	 * @param s:要写入的字符串
	 * @param len:写入长度,不足补0
	 */
	public static void writeString(DataOutputStream dous,String s,int len){
		
		try {
			byte[] data=s.getBytes("gb2312");
			if(data.length>len){
				System.out.println("向流中写入的字符串超长！要写"+len+" 字符串是:"+s);
			}
			int srcLen=data.length;
			dous.write(data);
			while(srcLen<len){
				dous.write('\0');
				srcLen++;
			}
		} catch (IOException e) {
			System.out.println("向流中写入指定字节长度的字符串失败："+e.getMessage());
		}
	}
	
	/**
	 * 从流中读取指定长度的字节，转成字符串返回
	 * @param ins:要读取的流对象
	 * @param len:要读取的字符串长度
	 * @return:读取到的字符串
	 */
	public static String readString(java.io.DataInputStream ins,int len){
		byte[] b=new byte[len];
		try {
			ins.read(b);
			String s=new String(b);
			s=s.trim();
			return s;
		} catch (IOException e) {
			return "";
		}
	}
	/**
	 * 截取字节
	 * @param msg
	 * @param start
	 * @param end
	 * @return
	 */
	public static byte[] getMsgBytes(byte[] msg,int start,int end){
		byte[] msgByte=new byte[end-start];
		int j=0;
		for(int i=start;i<end;i++){
			msgByte[j]=msg[i];
			j++;
		}		
		return msgByte;
	}
	/**  
	 * UCS2解码  
	 *   
	 * @param src  
	 *            UCS2 源串  
	 * @return 解码后的UTF-16BE字符串  
	 */  
	public static String DecodeUCS2(String src) {   
	    byte[] bytes = new byte[src.length()/2];   
	    for (int i = 0; i < src.length(); i += 2) {   
	        bytes[i/2]=(byte)(Integer.parseInt(src.substring(i, i + 2), 16));   
	    }   
	    String reValue = "";   
	    try {   
	        reValue = new String(bytes, "UTF-16BE");   
	    } catch (UnsupportedEncodingException e) {   
	    	reValue="";
	    }   
	    return reValue;   
	  
	}   
	  
	/**  
	 * UCS2编码  
	 *   
	 * @param src  
	 *            UTF-16BE编码的源串  
	 * @return 编码后的UCS2串  
	 */  
	public static String EncodeUCS2(String src) {   
	    byte[] bytes;   
	    try {   
	        bytes = src.getBytes("UTF-16BE");   
	    } catch (UnsupportedEncodingException e) {   
	    	bytes=new byte[0]; 
	    }   
	    StringBuffer reValue = new StringBuffer();   
	    StringBuffer tem = new StringBuffer();   
	    for (int i = 0; i < bytes.length; i++) {   
	        tem.delete(0, tem.length());   
	        tem.append(Integer.toHexString(bytes[i] & 0xFF));   
	        if(tem.length()==1){   
	            tem.insert(0,'0');   
	        }   
	        reValue.append(tem);   
	    }   
	    return reValue.toString().toUpperCase();   
	}   
	public static String getAscii(String str) {  
	    String tmp;  
	    StringBuffer sb = new StringBuffer(1000);  
	    char c;  
	    int i, j;  
	    sb.setLength(0);  
	    for (i = 0; i < str.length(); i++) {  
	        c = str.charAt(i);  
	        if (c > 255) {  
	            sb.append("//u");  
	            j = (c >>> 8);  
	            tmp = Integer.toHexString(j);  
	            if (tmp.length() == 1)  
	                sb.append("0");  
	            sb.append(tmp);  
	            j = (c & 0xFF);  
	            tmp = Integer.toHexString(j);  
	            if (tmp.length() == 1)  
	                sb.append("0");  
	            sb.append(tmp);  
	        } else {  
	            sb.append(c);  
	        }  
	  
	    }  
	    return (new String(sb));  
	}
	
	public static int binaryToTen(String s){
        int x = 0;
        for(char c: s.toCharArray())
             x = x * 2 + (c == '1' ? 1 : 0);
        return x;
	}
	
	public static Date formatMsgId(Long postMsgId){
		String msgId = Long.toBinaryString(postMsgId);
		while(msgId.length()<64){
			msgId = "0"+msgId; 
		}
		System.out.println(msgId);
		int month = binaryToTen(msgId.substring(0, 4));
		int day = binaryToTen(msgId.substring(4, 9));
		int hour = binaryToTen(msgId.substring(9, 14));
		int min = binaryToTen(msgId.substring(14, 20));
		int sec = binaryToTen(msgId.substring(20, 26));
		Calendar cal = Calendar.getInstance();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		cal.set(year, month, day, hour, min, sec);
		cal.add(Calendar.MONTH,-1);
		System.out.println(cal.getTime());
		return cal.getTime();
	}
	
	public static void main(String[] args) {
	}
}
