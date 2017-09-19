package com.urt.web.nio.index;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.User.UserTotalService;
import com.urt.web.common.util.StringUtil;

@Controller
@RequestMapping(value ="/nioIndex")
public class IndexController {
    Logger log = Logger.getLogger(getClass());
    @Autowired
    private UserTotalService userTotalService;
    
    @RequestMapping(value ="/index")
    public ModelAndView index() {
    	ModelAndView mv = new ModelAndView("/nioIndex/index");
    	return mv;
    }
    
    @RequestMapping(value ="/admin")
    public ModelAndView adminIndex() {
        ModelAndView mv = new ModelAndView("/nioIndex/admin");
        long count = userTotalService.getCount();
        mv.addObject("count",count);
        return mv;
    }
    @ResponseBody
    @RequestMapping(value="/queryDetailByNio")
    public JSONObject getNumber(String startDate,String endDate,HttpServletRequest req){
    	if(StringUtil.isBlank(startDate)){
    		startDate="2012-10-01";
    	}else{
    		startDate=startDate+"-01";
    	}
    	if(StringUtil.isBlank(endDate)){
    		endDate="2050-07-01";
    	}else{
    		endDate=endDate+"-01";
    	}
    	LaoSsAccountDto user = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
    	JSONObject json;
    	if(100==user.getRoles().get(0).getRoleId()){
    		json = userTotalService.queryDetailByNio(startDate,endDate,null);
    	}else{
    		json = userTotalService.queryDetailByNio(startDate,endDate,user.getCustId());
    	}
    	return json;
    }
    @ResponseBody
    @RequestMapping(value="/initIndexData")
    public JSONObject initIndexData(HttpServletRequest req){
    	
    	LaoSsAccountDto user = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
    	JSONObject json;
    	json = userTotalService.initIndexData();
    	return json;
    }
    @RequestMapping("/enterpriseCustomer")
	public ModelAndView intoChannelIndex() {
		ModelAndView mv = new ModelAndView("/nioIndex/enterpriseCustomer");
		return mv;
	}
}
