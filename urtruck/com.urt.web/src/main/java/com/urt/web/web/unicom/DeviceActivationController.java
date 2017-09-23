package com.urt.web.web.unicom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.IccidLibDto;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.unicom.DeviceService;
import com.urt.web.common.util.ICCID;
import com.urt.web.common.util.ImportExcelUtils;
import com.urt.web.common.util.JsonConverter;
import com.urt.web.common.util.MD5;


/**
 * 类说明：设备激活
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/deviceActivation")
public class DeviceActivationController {
	@Value("${deviceid}")
	private String deviceid;
	@Autowired
	private DeviceService deviceService;
	
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/H5/activation");
		return mv;
	}
	/**
	 * 激活服务方法
	 * @param request
	 * @param response
	 * @param session
	 * @param device
	 * @throws Exception
	 */
	@RequestMapping("/activeService")
	public void activeService(HttpServletRequest request, HttpServletResponse response, HttpSession session, DeviceDto device) throws Exception{
		Map<String, Object> map =null;
		PrintWriter out= response.getWriter();
		String lpsust=(String) session.getAttribute("lpsust");
		String iccid= ICCID.replaceLastChar(request.getParameter("iccid"));
		device.setIccid(iccid);
		String secretKey= MD5.md5UTF8(MD5.md5UTF8(device.getIccid() + deviceid + lpsust));
		device.setDeviceId(deviceid);
		device.setSecretKey(secretKey);
		device.setLpsust(lpsust);
		
		session.setAttribute("device", device);
		//获取服务
		/**
		 * -1(参数不全),-2(st校验失败),-3(获取资费计划失败)，-4(系统异常),1(正常),-6(iccid已经被其他账户绑定),
			-5(签名错误)
		 */
		map= deviceService.getDeviceStatus(device);
		if(map.containsKey("isActive")){
			//看返回值 是否已激活
			Boolean b=(Boolean) map.get("isActive");
			if(!b){
				//没激活，active接口
				map= deviceService.activeInetAccess(device);
				String code=(String) map.get("retcode");
				if("1".equals(code)){
					//open接口
					map= deviceService.openInternet(device);
					/**
					 *  重新获取用户的iccid再次放入session中  因为用户激活了设备 所以会有新的iccid
					 */
					reloadIccid(session.getAttribute("lenovoid").toString(),session);
					Object jsonObject = JSONObject.toJSON(map);
					out.write(jsonObject.toString());
					return  ;
				}
				Object jsonObject = JSONObject.toJSON(map);
				out.write(jsonObject.toString());
				return;
			}else {
				String nacid=(String) map.get("nacid");
				if("apn1".equals(nacid)){
					//open接口
					map= deviceService.openInternet(device);
					/**
					 *  重新获取用户的iccid再次放入session中  因为用户激活了设备 所以会有新的iccid
					 */
					reloadIccid(session.getAttribute("lenovoid").toString(),session);
					Object jsonObject = JSONObject.toJSON(map);
					out.write(jsonObject.toString());
					return ;
				}

				if("apn2".equals(nacid)){
					map=new HashMap<String,Object>(1);
					map.put("retcode", "2");
					Object jsonObject = JSONObject.toJSON(map);
					out.write(jsonObject.toString());
					return;
				}
			}
		}
		Object jsonObject = JSONObject.toJSON(map);
		out.write(jsonObject.toString());
		return;
	}
	
	/**
	 * 激活成功
	 */
	@RequestMapping("/activeSuccess")
	public ModelAndView activeSuccess(){
		ModelAndView mv = new ModelAndView("/H5/activeSuccess");
		return mv;
	}
	/**
	 * 加载设备信息
	 * @param user
	 * @param session
	 */
	public void reloadIccid(String lenovoid,HttpSession session){
		Map<String, Object> map = deviceService.queryLenovoidStatus(lenovoid);
		String statusCode = (String) map.get("retcode");
		session.setAttribute("simCardStatus",statusCode);
		Object obj=map.get("iccidList");
		if(null!=obj){
			Object[] deviceList=JsonConverter.jsonToArray(obj);
			session.setAttribute("deviceList",deviceList);
		}
	}
	/**
	 * 查看入网协议
	 * @return
	 */
	@RequestMapping("/protocol")
	public ModelAndView protocol() {
		ModelAndView mv = new ModelAndView("/H5/protocolDetail");
		return mv;
	}
	
}
