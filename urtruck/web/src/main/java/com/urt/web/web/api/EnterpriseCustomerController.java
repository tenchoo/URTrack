package com.urt.web.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.web.common.util.RequestUtil;


@Controller
@RequestMapping(value = "/EnterpriseCustomer")
public class EnterpriseCustomerController {

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "account/login";
	}
	   
	
	/**
	 * 增加工期配置信息
	 */
	@RequestMapping(value ="add")
	@ResponseBody
	public int addPeriod(HttpServletRequest request){
		String name=RequestUtil.getParam(request, "name", "");
		String password=RequestUtil.getParam(request,"password", "");
		
		if(name.equals(""))
		  name="";
	    if(password.equals(""))
	    	password="";
	  /*  Map<String, Object> map = new HashMap<>();
	    IMap params=new IMap(request);
	    TmUser tmUser = (TmUser) request.getSession().getAttribute("tmUser");
	    params.put("createBy", tmUser.getUserId());//获取当前的创建用户

	    //获取当前插入下限的值
	    int workloadLower =  Integer.parseInt(params.getString("workloadLower"));
	    //获取当前插入上限的值
	    int workloadUpper1 =  Integer.parseInt(params.getString("workloadUpper"));


	    //判断本次插入下限是否大于上次插入的上限工日
	    List<TmRuleConstructPeriod> tmRuleConstructPeriod= constructPeriodService.selectConstructPeriodList(new IMap());
	    if(tmRuleConstructPeriod.size()==0){
	        if ( workloadUpper1 > workloadLower) {
	            int m = constructPeriodService.addConstructPeriod(params);
	            if (m > 0) {
	                map.put("flag", "1");
	                map.put("msg", "增加成功");
	            }
	        } else {
	            map.put("flag", "0");
	            map.put("msg", "请输入正确的工程周期上限和下限！");
	        }
	    }else {
	        int[] ints = new int[tmRuleConstructPeriod.size()];
	        for (int i = 0; i < tmRuleConstructPeriod.size(); i++) {
	            int workloadUpper = Integer.parseInt(tmRuleConstructPeriod.get(i).getWorkloadUpper().toString());
	            ints[i] = workloadUpper;
	        }
	    //获取列表中workloadUpper的最大值
	        int max = ints[0];
	        for (int j = 0; j < ints.length - 1; j++) {
	            if (max < ints[j + 1])
	                max = ints[j + 1];
	        }

	        if (workloadLower >= max && workloadUpper1 > workloadLower) {
	            int m = constructPeriodService.addConstructPeriod(params);
	            if (m > 0) {
	                map.put("flag", "1");
	                map.put("msg", "增加成功");
	            }
	        } else {
	            map.put("flag", "0");
	            map.put("msg", "请输入正确的工程周期上限和下限！");
	        }
	    }*/
	    return 0;
	}
}
