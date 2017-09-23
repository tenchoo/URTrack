package com.urt.web.http.shanghaiCMC;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.ShangHaiCMC.CI_CommonNotification;

@Controller
@RequestMapping("/service")
public class CommonNotificationController {
	@Autowired
	private CI_CommonNotification commonNotification;
	@RequestMapping(value="/CI_CommonNotification",method = {RequestMethod.POST})
	
	public @ResponseBody JSONObject commonNotification(@RequestBody String params){
		Logger log = Logger.getLogger(getClass());
		log.info("接收到移动传来的参数:"+params);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String,String> map = null ;
		JSONObject json = new JSONObject();
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			String Request_ID = jsonObject.getString("Request_ID");
			String Request_Date_Time = jsonObject.getString("Request_Date_Time");
			map = (Map<String, String>) jsonObject.get("Multi_Records");

			//System.out.println(Request_ID+".."+Request_Date_Time+"..."+list.get(0).get("OLD_Request_ID"));
			String OLD_Request_ID = map.get("OLD_Request_ID");
			String MSISDN = map.get("MSISDN");
			String Result_Code = map.get("Result_Code");
			String Result_Date_Time = map.get("Result_Date_Time");
			String Result_Description = map.get("Result_Description");
			if("".equals(Request_ID) || null == Request_ID){
				throw new Exception();
			}
			if("".equals(Request_Date_Time) || null == Request_Date_Time){
				throw new Exception();
			}
			if("".equals(OLD_Request_ID) || null == OLD_Request_ID){
				throw new Exception();
			}
			if("".equals(MSISDN) || null == MSISDN){
				throw new Exception();
			}
			if("".equals(Result_Code) || null == Result_Code){
				throw new Exception();
			}
			if("".equals(Result_Date_Time) || null == Result_Date_Time){
				throw new Exception();
				
			}
			if("".equals(Result_Description) || null == Result_Description){
				throw new Exception();
			}
			json.put("Result_Code", "S0000");
			json.put("Result_Description", "成功");
			json.put("Result_Date_Time", dateFormat.format(new Date()));
		}catch(Exception e){
			log.info("输入参数不合法");
			json.put("Result_Code", "B6002");
			json.put("Result_Description", "交易数据不合法");
			json.put("Result_Date_Time", dateFormat.format(new Date()));
			e.printStackTrace();
			return json;
		}
		try {
			commonNotification.commonNotification(map,params);
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		}
		return json;
	}
}
