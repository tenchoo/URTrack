package com.urt.web.web.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.urt.interfaces.device.RedisService;
import com.urt.web.common.util.NotifyOperationUtils;

/**
 * 类说明：主界面
 * 
 * @author zss
 * @date 2017年03月17日
 */
@Controller
@RequestMapping("/userNotify")
public class NotifyController {
	Logger log = Logger.getLogger(getClass());

	@Autowired
	private RedisService redisClientService;
	/**
	 * 上传excel文件信息界面
	 */
	@RequestMapping("/notifyUpload")
	public ModelAndView uploadExcel() {
		ModelAndView mv = new ModelAndView("/user/notifyUpload");
		return mv;
	}
	/**
	 * 导入的通知信息
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/notifyImport")
	public String upload(@RequestParam(value = "file") MultipartFile file,HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		NotifyOperationUtils utils = null;
		String result="";
		List<Map<String,Object>> list = null;
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new NotifyOperationUtils();
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), true);
			} else {
				list = utils.importExcel(file.getInputStream(), false);
			}
			
			if(list !=null && list.size() > 0){
				System.out.println(list);
				result="success";
				for(Map<String,Object> map:list){
					String notify = JSONObject.toJSONString(map);
					System.out.println(notify);
					redisClientService.set(map.get("ICCID")+"-"+map.get("TYPE"), notify);
				}
			}else{
				result="fail";
				log.error("读取excel 内容为空************************************");
			}
			
		}else{
			result="empty";
		}
		return result;
	}

}
