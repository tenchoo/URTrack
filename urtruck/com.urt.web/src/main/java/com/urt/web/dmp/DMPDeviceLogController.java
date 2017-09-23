package com.urt.web.dmp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.dmp.LaoDMPLogDto;
import com.urt.interfaces.dmp.DMPLogService;

@Controller
@RequestMapping("/deviceLog")
public class DMPDeviceLogController {
	@Autowired
	private DMPLogService dmpLogService;
	//datatable获取日志list
		@ResponseBody
		@RequestMapping("/logList")
		public Map<String, Object> logList(HttpServletRequest req,HttpServletResponse resp,String imei,LaoDMPLogDto logDto) {
			int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
			int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
			int pageNo = (pageStart / pageSize)+1;
			if(imei!=null){
				logDto.setImei(imei);
			}
			Map<String, Object> resultMap= dmpLogService.queryPage(logDto,pageNo,pageSize);
			return resultMap;
		}
}
