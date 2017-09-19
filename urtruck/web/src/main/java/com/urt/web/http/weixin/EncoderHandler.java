package com.urt.web.http.weixin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class EncoderHandler {
	private static final Logger log = Logger.getLogger(EncoderHandler.class);
	
	
	
	public BufferedImage createQRCode(String code_url, HttpServletResponse response) {
		try {
			log.info(">>>>>>>>>>>>>>生成二维码开始<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			String  path="D:\\20170221\\LAOConnect\\LAOAPI\\LAOAPI-SpringMVC\\src\\main\\webapp\\static\\device";
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			// 内容所使用编码
			hints.put(EncodeHintType.CHARACTER_SET, "Utf-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(code_url, BarcodeFormat.QR_CODE, 200, 200, hints);
			// 生成二维码
			//File outputFile = new File(path, "QRCode.jpg");
			 ServletOutputStream outputStream = response.getOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "jpg", outputStream);
			//MatrixToImageWriter.writeToFile(bitMatrix, "jpg", outputFile);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			outputStream.close();
			return bufferedImage;

		} catch (Exception e) {
			e.printStackTrace();
			log.info(">>>>>>>>>>>>>>生成二维码异常<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
       return null;
	}
}
