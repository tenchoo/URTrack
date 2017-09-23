package com.urt.web.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by Administrator on 2016/5/23.
 */
public class SHA {

    public static final String ALGORITHM = "SHA-256";

    public static String SHA256Encrypt(String orignal) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (null != md) {
            byte[] origBytes = orignal.getBytes();
            md.update(origBytes);
            byte[] digestRes = md.digest();
            String digestStr = getDigestStr(digestRes);
            return digestStr;
        }

        return null;
    }

    private static String getDigestStr(byte[] origBytes) {
        String tempStr = null;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < origBytes.length; i++) {
            // System.out.println("and by bit: " + (origBytes[i] & 0xff));
            // System.out.println("no and: " + origBytes[i]);
            // System.out.println("---------------------------------------------");
            // 这里按位与是为了把字节转整时候取其正确的整数，java中一个int是4个字节
            // 如果origBytes[i]最高位为1，则转为int时，int的前三个字节都被1填充了
            tempStr = Integer.toHexString(origBytes[i] & 0xff);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);

        }
        return stb.toString();
    }

    public static void main(String[] args) {
        System.out.println(SHA256Encrypt("AAaa11"));
    }


    /**
     * 在用的
     */
    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


}
