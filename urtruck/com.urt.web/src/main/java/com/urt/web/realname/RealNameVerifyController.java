package com.urt.web.realname;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsRealNameVerifyDto;
import com.urt.interfaces.realNameVerify.RealNameVerifyServiece;
import com.urt.web.web.account.CallbackController;
@Controller
@RequestMapping("/realnameVerify")
public class RealNameVerifyController {
	private static Logger logger = LoggerFactory.getLogger(CallbackController.class);
	@Autowired RealNameVerifyServiece realNameVerifyServiece;
	/**
	 * 跳转至实名认证页面
	 * @return
	 */
	@RequestMapping(value="/toRealNameVerify")
	public String toRealNameVerify(HttpSession session,Model model) {
		LaoSsAccountDto currentUser=(LaoSsAccountDto)session.getAttribute("sessionUser");
		LaoSsRealNameVerifyDto dto=realNameVerifyServiece.getVerifyByAccountId(currentUser.getAcconutId());
		if(dto!=null){
			model.addAttribute("dto", dto);
			return "realNameVerify/toVerify";
		}else{
			return "realNameVerify/verify";
		}
	}
	@RequestMapping(value="/toVerify")
	public String toVerify() {
		return "realNameVerify/verify";
	}
	@ResponseBody
	@RequestMapping(value="/verify")
	public ResultMsg realnameVerify(LaoSsRealNameVerifyDto dto,HttpSession session){
		//得到当前用户
		LaoSsAccountDto currentUser=(LaoSsAccountDto)session.getAttribute("sessionUser");
		//判断当前用户是否进行过实名认证
		//进行实名认证
		ResultMsg result=new ResultMsg();
		if(!realNameVerifyServiece.checkRealName(dto.getIdnum(), currentUser.getAcconutId())){
			result.setSuccess(false);
			result.setMsg("身份证已经认证过,或今日认证次数用完了");
			return result;
		}else{
			try {
				return realNameVerifyServiece.customerVerifyServiece(dto,currentUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("》》》实名认证错误信息："+e.getMessage()+";"+e.getCause());
				result.setSuccess(false);
				result.setMsg("认证失败");
				return result;
			}	
		}
		
	}
	@RequestMapping("/review")
	public String reviewList(){
		return "realNameVerify/reviewList";
	}
	@ResponseBody
	@RequestMapping(value="list")
	public  Map<String, Object> roleList(HttpServletRequest req,HttpServletResponse resp,LaoSsRealNameVerifyDto dto) {
		  int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
		  int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
		  int pageNo = (pageStart / pageSize)+1;
		  Map<String, Object> resultMap= realNameVerifyServiece.queryPage(dto,pageNo,pageSize);
		 return resultMap;
	}
	@ResponseBody
	@RequestMapping("/approved")
	public ResultMsg approved(long id){
		ResultMsg result=new ResultMsg();
		result.setSuccess(false);
		if(realNameVerifyServiece.approved(id)){
			result.setSuccess(true);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/unapproved")
	public ResultMsg unapproved(Long id){
		ResultMsg result=new ResultMsg();
		result.setSuccess(false);
		if(realNameVerifyServiece.unapproved(id)){
			result.setSuccess(true);
		}
		return result;
	}
}
