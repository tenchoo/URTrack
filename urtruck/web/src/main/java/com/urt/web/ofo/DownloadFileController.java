package com.urt.web.ofo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.urt.interfaces.ofo.BusinessService;

@Controller
public class DownloadFileController {

	
	private static final Logger  log=Logger.getLogger(DownloadFileController.class);
	
	@Autowired
	private BusinessService  service;
	
	
	
	/**
	 * ofo定时任务下载要处理的文件
	 */
	public void  downloadFile(){
		log.info("ofo的定时任务触发");
		boolean dataInsert = service.dataInsert();
		if (dataInsert) {
			//发送邮件
			service.emailNotify();
		}
	}
	
	public void ftpUpload(){
		service.ftpFileUpload();
	}
	
	
}
