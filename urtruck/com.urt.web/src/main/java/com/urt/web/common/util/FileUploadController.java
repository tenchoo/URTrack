package com.urt.web.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {
	//上传图片
	@RequestMapping(value = "/uploadPic")
	public void uploadPic(MultipartFile handpic,MultipartFile frontpic,MultipartFile backpic,HttpServletResponse response)throws Exception{
		//扩展名
		String ext ="";
		byte[] betys={};
		if(handpic!=null){
			ext=FilenameUtils.getExtension(handpic.getOriginalFilename());	
			betys=handpic.getBytes();
		}
		if(frontpic!=null){
			ext=FilenameUtils.getExtension(frontpic.getOriginalFilename());	
			betys=frontpic.getBytes();
		}
		if(backpic!=null){
			ext=FilenameUtils.getExtension(backpic.getOriginalFilename());
			betys=backpic.getBytes();
		}
		/*//图片名称生成策略
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");*/
		//图片生成策略     		
 		String time = getDateByFormat("HHmmss");
 		//生成三位随机数
 		int i=(int)(Math.random()*900)+100;
		//图片名称一部分
		/*String format = df.format(new Date());
		
		//随机三位数
		Random r = new Random();
		// n 1000   0-999   99
		for(int i=0 ; i<3 ;i++){
			format += r.nextInt(10);
		}*/
		
		//实例化一个Jersey
		Client client = new Client();
		//保存数据库
		String path = "upload/" + i + "." + ext;
		
		//另一台服务器的请求路径是?
		String url = SoapConstant.IMAGE_URL  + path;
		//设置请求路径
		WebResource resource = client.resource(url);
		resource.put(String.class, betys);
		//返回二个路径
		JSONObject jo = new JSONObject();
		jo.put("url", url);
		jo.put("path",path);
		
		ResponseUtils.renderJson(response, jo.toString());
	}
	private String getDateByFormat(String format){
    	Calendar date=Calendar.getInstance();
 		SimpleDateFormat sdf=new SimpleDateFormat(format);
 		return sdf.format(date.getTime());
    }

    private void makeDirByDate(String path){
 		File file=new File(path);
 		if(!file.exists()){
 			file.mkdir();
 		}
    }
	
	
	
	
	

}
