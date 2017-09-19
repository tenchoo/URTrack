package com.urt.web.web.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.User.UserStateMmService;
import com.urt.interfaces.User.UserTotalService;
import com.urt.web.common.util.StringUtil;

/**
 * * 绫昏鏄庯細绠＄悊鍛樻暟鎹〉
 * 
 * @author qxg
 * @date 2017骞?鏈?2鏃?5:40:42
 * */
@Controller
@RequestMapping(value ="/userTotalService")
public class UserTotalController {
    Logger log = Logger.getLogger(getClass());

    @Autowired
    private UserTotalService userTotalService;
    @Autowired
    private UserStateMmService userStateService;
    
    
    
    //璺宠浆缁堢list椤甸潰
    @RequestMapping(value ="/userDataList")
    public ModelAndView userDataList() {
        ModelAndView mv = new ModelAndView("/userIndex/logindatalist");
        long count = userTotalService.getCount();
        mv.addObject("count",count);
        return mv;
    }
    
    /**
     * 鏍规嵁鏉′欢鏌ヨ鐢ㄦ埛鏁扮洰
     */
    @ResponseBody
    @RequestMapping(value ="/NeedNum")
    public List< Map<String, Long>> getNeedNum() {   
        List<Map<String, Long>> listMap = new ArrayList<Map<String, Long>>();
        listMap.add(userTotalService.queryOperDetail());
        listMap.add(userTotalService.queryStateDetail());
        return listMap;
    }
    /**
     * 鏍规嵁鏉′欢鏌ヨ鐢ㄦ埛鏁扮洰
     */
    @ResponseBody
    @RequestMapping(value ="/IndNum")
    public Map<String, Long> getIndNum() {       
        return userTotalService.queryIndustryNum();
    }
    
    /**
     * 鏍规嵁鏉′欢鏌ヨ鐢ㄦ埛鏁扮洰
     */
    @ResponseBody
    @RequestMapping(value ="/StateNum")
    public Map<String, Long> getStateNum() {   
        Map<String,Long> mapl=userTotalService.queryStateDetail();
        return mapl;
    }
    /**
     * 鏍规嵁鏉′欢鏌ヨ鐢ㄦ埛鏁扮洰
     */
    @ResponseBody
    @RequestMapping(value ="/OperNum")
    public Map<String, Long> getOperNum() {  
        return userTotalService.queryOperDetail();
    }
    
    @ResponseBody
    @RequestMapping(value="/getNumber")
    public JSONObject getNumber(String startDate,String endDate){
    	if(StringUtil.isBlank(startDate)&&StringUtil.isBlank(endDate)){
    		return userStateService.getNumber();
    	}
    	if(StringUtil.isBlank(startDate)&&!StringUtil.isBlank(endDate)){
    		startDate="2012-10-10";
    		endDate=endDate+"-01";
    	}
    	if(StringUtil.isBlank(endDate)&&!StringUtil.isBlank(startDate)){
    		endDate="2022-07-10";
    		startDate=startDate+"-01";
    	}
    	if(!StringUtil.isBlank(endDate)&&!StringUtil.isBlank(startDate)){
    		endDate=endDate+"-01";
    		startDate=startDate+"-01";
    	}
    	return userTotalService.getNumber(startDate,endDate);
    }
}
