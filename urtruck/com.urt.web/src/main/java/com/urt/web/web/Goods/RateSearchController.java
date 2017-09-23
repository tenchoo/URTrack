package com.urt.web.web.Goods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.BatchOperationUtils;

/**
 * 类说明：主界面
 * 
 * @author zhaoyf
 */
@Controller
@RequestMapping("/rateSearch")
public class RateSearchController {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private TrafficQueryService trafficQueryService;
	
	private final List<TrafficQueryNowDto> resulList = new ArrayList<TrafficQueryNowDto>();
	
	/**
	 * 上传excel文件信息界面
	 */
	@RequestMapping("/toRateSearch")
	public ModelAndView uploadExcel() {
		ModelAndView mv = new ModelAndView("/Goods/rateSearch");
		return mv; 	
	}
	/**
	 * 批量查询剩余流量(导入信息)
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/batchImport")
	public String upload(@RequestParam(value = "file") MultipartFile file,HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		BatchOperationUtils utils = null;
		List<Map<String,Object>> list = null;
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new BatchOperationUtils();
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), true);
			} else {
				list = utils.importExcel(file.getInputStream(), false);
			}
		resulList.clear();	
		if(list !=null && list.size() > 0){
			for (Map<String, Object> map : list) {
				for (Object value : map.values()) {  					  
				    String iccid = (String)value;
				    TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
				    resulList.add(doNowTrafficQuery);
				}  
			}
		}	

		}
		return "redirect:/rateSearch/showRate";
	}
	
	@RequestMapping("/showRate")
	public ModelAndView showRate() throws Exception{
		ModelAndView mv = new ModelAndView("/Goods/showRate");
		mv.addObject("resulList", resulList);	
		return mv;
	}

}
