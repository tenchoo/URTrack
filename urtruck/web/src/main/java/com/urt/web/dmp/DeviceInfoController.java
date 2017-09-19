package com.urt.web.dmp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.dmp.LaoDMPCardDto;
import com.urt.interfaces.dmp.DMPService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.dmp.entity.PostJsonData;
import com.urt.web.dmp.util.AesUtil;
import com.urt.web.dmp.util.EncryptMD5Util;

/**
 * 设备信息展示
 * @author zss
 * @date 2017年03月17日
 */
@Controller
@RequestMapping("/deviceInfo")
public class DeviceInfoController {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private DMPService dmpService;
	//跳转终端list页面
	@RequestMapping("/deviceInfo")
	public ModelAndView uploadExcel() {
		ModelAndView mv = new ModelAndView("/dmp/deviceInfo");
		return mv;
	}
	//datatable获取终端list
	@ResponseBody
	@RequestMapping("/deviceList")
	public Map<String, Object> deviceList(HttpServletRequest req,HttpServletResponse resp,LaoDMPCardDto dto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		if(!ActionUtil.ifSuperUser(req)){
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			long custId=user.getCustId();
			dto.setCustid(custId);
		}
		if(dto!=null){
			if(dto.getCustid()!=null&&dto.getCustid()==-1)
				dto.setCustid(null);
		}
		log.info("dto----="+dto.toString());
		Map<String, Object> resultMap= dmpService.queryPage(dto,pageNo,pageSize);
		log.info("resultMap---------="+resultMap.toString());
		log.info("resultMap---------="+resultMap.size());
		return resultMap;
	}
	//单点定位地图
	@RequestMapping("/showMap")
	public ModelAndView showMap(String imei) {
		ModelAndView mv = new ModelAndView("/dmp/pointMap");
		String pointStr = dmpService.getPositionPoint(imei);
		mv.addObject("pointStr", pointStr);
		return mv;
	}
	//批量点地图
	@RequestMapping("/showBatchMap")
	public ModelAndView showBatchMap(String imeis) {
		ModelAndView mv = new ModelAndView("/dmp/pointsMap");
		if(imeis!=null&&!"".equals(imeis)){
			mv.addObject("imeis", imeis);
		}
		return mv;
	}
	//批量点地图
	@ResponseBody
	@RequestMapping("/getBatchMap")
	public List<String> getBatchMap(String imeis) {
		String[] imeiArr = imeis.split(",");
		List<String> points=dmpService.getPositionPoints(imeiArr);
		return points;
	}
	//轨迹地图
	@RequestMapping("/trailMap")
	public ModelAndView trailMap(String imei) {
		ModelAndView mv = new ModelAndView("/dmp/pointTrailMap");
		mv.addObject("imei", imei);
		return mv;
	}
	//轨迹地图
	@ResponseBody
	@RequestMapping("/getPointStrList")
	public List<String> getPointStrList(String imei) {
		log.info("进入方法getPointStrList");
		List<String> pointStrList=dmpService.getPointTrail(imei);
		log.info("单点的经纬度集合---："+pointStrList);
		log.info("退出方法getPointStrList");
		return pointStrList;
	}
	
	//批量点类型地图
	@RequestMapping("/showDeviceTypeMap")
	public ModelAndView showDeviceTypeMap(HttpServletRequest req,LaoDMPCardDto cardDto) {
		ModelAndView mv = new ModelAndView("/dmp/typeMap");
		if(ActionUtil.ifSuperUser(req)&&cardDto.getCustid()<=0){
			cardDto.setCustid(null);
		}else{
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			if(cardDto.getCustid()==-1)
				cardDto.setCustid(user.getCustId());
		}
		 List<String> imeiList = dmpService.getImei(cardDto);
		 String imeis = "";
		 for (int i = 0; i < imeiList.size(); i++) {
			imeis+=imeiList.get(i)+",";
		}
		 mv.addObject("imeis",imeis);
		return mv;
	}
	//跳转至详情页面
	@RequestMapping("/details")
	public ModelAndView details(String imei) {
		ModelAndView mv = new ModelAndView("/dmp/details");
		Map<String, Object> map =  dmpService.selectDeviceDetails(imei);
		mv.addObject("imei", imei);
		mv.addObject("map", map);
		return mv;
	}
	//查询设备是否在线
	@RequestMapping("/isOnline")
	@ResponseBody
	public Map<String, Object> isOnline(String imei) {
		Map<String, Object> map = dmpService.selectIsoline(imei);
		return map;
	}
	//单点定位地图
	@RequestMapping("/sendDeviceInfo")
	public void strategyTest() {
		String preData="4dNL+4VxBskIs02sXttusQNjB4Y3FNQB1VJEKZS8d/ZFpLIm+g85se8R8kQQ nlU7IFRsHMlWAZYuC6VYT/XEcrPqUVLSGtPOyvhO6+scIpJn7Psu109lqfmq 1h2R0G5+LRL6dbTCThbrHM2jgNlB1PkmQTSmxWXQGJyWoL9ad/YBGTVa2OtJ jyZ+Ij1KcTr2xjmc8yYV3LKQ/RxK2FWWHuWlXyfxx6jWOMAdHJai4h26sNlf PJ+zmNJdLZRjkLfAbsOurrh1msJoUR/nm0JC8jTaNAcacQcXBUYlQX1itW51 J1COc0T1ZJSybL4Gqcax+6QdBQHJVgpjtRGStTmikuOXIcYd9mXCMcCzLsNn xf97x9KUUHZXuJQpv8dCxY+D1JNb+zb/F93rtBfzMKZXC8KIsvvFnf2OiKOm zHjwQVb/cKqhxxfdZJGWA/wkQMxb0YK7FuUxB+2416elcuUMiZI8KNuNbCY5 GreEPs7K5nr2r1L691T57atKNOnjr1qhRXam5uDCdcqLvby6Y73a7pqmxTUT 0JKRBBz5F9sTDNvh7STwugIzgOrcwc4iwtVP/9QX/Q/fR7lTzJGqQge4PfP0 1S5dTY/Q5ai1dC0Y3mdUbQbIhLmXMRszWpySPf60bPxOq4p5G1DTIpGRaETv 75IImQdNT90ok/D9ltoYoXcALpvKCHDlAfMzlGboGwfUdqCPWvAOsuXeBd7a HXkgYyhuGXJtif1aHupCglVpjCvy0uDFMj/PFH0d8rVpSpFnGYXYluV2dp+z crTAL1sFFIzymMEiMuIUe4sGjVlDZKxMaIPwliQ5wg/CekOo4hZS5V7HuG2B aTk6j1PT3BMHjvZlBuPuIT8d5VFInxu6YiWCrXz8heIDCv+r834oZ3cr3Cc6 NAitFTxj8QeTF3RC17D+4PMzkm99d1lscsy4nkm+Z8HihwyVDDeARirDFteM sdqx3PE9ZZeX0djjQl6ciDiMxpYCqrJvNHe1S9y/V3Q0ztVg49QBejaGHOgL 87bFF4hnp/zX4Dvg9H7J2PKsA33coU8rBV1Umu0b5YhCWbkin+Uqbd6QMkYa pgVs5TVmDlzX9XwsAL8SNbSm/fwTR7IWJRXTdo+LingB054VvMZP/9Kfm1KQ KQ/sTADpWp3z27l4o6r3IAz2gs8iOMmA9sxFpdn9Uk3Fh3WP/aJd/h/fCz0+ lYmVIW6mFchIdpbTr+zn/VfF2zZfwcrPCTZMQUhKNtNl+KCYBhwyLd8+y63p bnwl+ids+yreTwVTVXZbOOebM7P7tHhQLjIsMuRcej3fKp2RQ6HxdEbWSmXB ryEQjfedwvLpuRjelFv1hU++1vBGqCtDtzkmjSrJ/AgLBVB85E+F8+MnO/tx vZWkdik=";
    	
    	System.out.println("位置信息aes解密前的数据-----："+preData);
    	//aes解密
    	String afterData = AesUtil.aesDecryPt(preData);
    	System.out.println("位置信息aes解密之后的数据-----："+afterData);
    	
    	Gson gson = new Gson();
    	PostJsonData postJsonInfo = gson.fromJson(afterData, PostJsonData.class);
    	String custId = postJsonInfo.getCustId();
    	System.out.println("位置信息由字符串转化为对象----："+postJsonInfo);
//    	
    	String posDataText=postJsonInfo.getPosDataText();
    	System.out.println("终端设备信息MD5加密前的字符串posDataText----："+posDataText);
    	String afterStrMD5 = EncryptMD5Util.sign(posDataText);
    	System.out.println("终端设备信息MD5加密后的字符串afterStrMD5----："+afterStrMD5);
    	String originalStrMD5 = postJsonInfo.getEncryptedText();
    	System.out.println("原生MD5加密字符串"+originalStrMD5);
    	if(!afterStrMD5.equals(originalStrMD5)){
    		System.out.println(afterStrMD5.equals(originalStrMD5));
    		System.out.println("验证失败");
    		return;
    	}
    	System.out.println(afterStrMD5.equals(originalStrMD5));
		System.out.println("验证通过");
        
		String randomText = postJsonInfo.getRandomText();
		System.out.println("kafka中每一条终端信息的唯一标识randomText---："+randomText); 
		
		byte deviceType = postJsonInfo.getDeviceType();
		System.out.println("kafka中每一条终端信息中的设备类型deviceType---："+deviceType);
		dmpService.dmpDataHandler(posDataText, deviceType, randomText, custId);
	}
	public static void main(String[] args) {
		String iccidStrs="89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,89860315760100858497,";
		String[] iccids = iccidStrs.split(",");
		int len = iccids.length;
		System.out.println(len);
		for(String iccid:iccids){
			System.out.println(iccid);
		}
	}
}
