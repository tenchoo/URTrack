package com.urt.web.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
@Controller
@RequestMapping("/fileUpload")
public class FileUpload{
	
	/**
	 * 上传文件到服务器上面，并返回文件的绝对路径+文件名以及前段传来的parameter的name和value。
	 * 其中文件保存在uploadFilePlace下面
	 * @param request
	 * @param response
	 * @return 存放parameter的hash map，其中文件路径在hashMap中的key是"fileName"
	 * @author yuanzj
	 */
	 @ResponseBody
	 @RequestMapping(value="/uploadImg")
	 public Map<String,String> uploadFile(@RequestParam(value = "file")MultipartFile file,HttpServletRequest request, HttpServletResponse response){
		String filePathAndName= null;
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		response.setContentType("text/plain");
		
		//第一个是暂时的缓存文件名
		//第二个是存放文件的地方
		String tempFilePath = "tempImg";
		String tempFilePath2 = "img";
		//获取绝对路径
		tempFilePath = request.getSession().getServletContext().getRealPath("/")+"\\fileUpload\\"+tempFilePath; 
		tempFilePath2 = request.getSession().getServletContext().getRealPath("/")+"\\fileUpload\\"+tempFilePath2;
		//可以修改为
		/*tempFilePath="d:/temp1";

		tempFilePath2 ="d:/temp2";*/
		
		System.out.println("path = "+tempFilePath);

		try{
			//创建一个基于硬盘的FileItem工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置向硬盘写数据时所用的缓冲区的大小，此处为4k
			factory.setSizeThreshold(4*1024);
			//判断是否有没有这个文件目录
			File tempFile = new File(tempFilePath);
			if(!tempFile.exists() && !tempFile.isDirectory()){
				tempFile.mkdir();
			}
			CommonsMultipartFile cf= (CommonsMultipartFile)file; 
		    DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
		    /*File f = fi.getStoreLocation();*/
		    fi.write(tempFile);
			//设置暂时缓存目录
			/*factory.setRepository(tempFile);
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置文件最大不能超过1G
			upload.setSizeMax(1024*1024*1024);
			
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
			
			while(iter.hasNext()){
				FileItem item = iter.next();
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString();
					parameters.put(name, value);
//					System.out.println("name = "+name+"; value = "+value);
				}else{
					//获取文件名
					String fileName = item.getName();
					int index = fileName.lastIndexOf("\\");
					fileName = fileName.substring(index+1, fileName.length());
//					System.out.println("filePath = "+fileName);
					long fileSize = item.getSize();
					
					if("".equals(fileName) && fileSize==0){
						return null;
					}
					
					//判断并创建文件夹tempFilePath2
					File temp = new File(tempFilePath2);
					if(!temp.exists()&&!temp.isDirectory()){
						temp.mkdir();
					}
					
					filePathAndName = tempFilePath2+File.separator+fileName;
//					System.out.println("filePath and name = "+filePathAndName);
					File tempFile2 = new File(filePathAndName);
					
					//将文件路径放到map中
					parameters.put("fileName", filePathAndName);
					
					//将上传的文件放到tempFilePath2目录下面
					try {
						item.write(tempFile2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}*/
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return parameters;
	}

}


