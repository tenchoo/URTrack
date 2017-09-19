package com.urt.web.web.information;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.LaoCustPersonDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.dto.PasswordBean;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.authority.LaoSsRoleService;
import com.urt.interfaces.customer.LaoCustPersonService;
import com.urt.interfaces.customer.LaoCustomerService;

/**
* 类说明：系统用户controller
* @author zhangbt3
* @date 2016年6月20日 下午5:18:13
*/
@Controller
@RequestMapping("/ssUser")
public class SsUserController {

	@Autowired
	private LaoSsAccountService accountService;
	@Autowired
	private LaoSsRoleService roleService;
	@Autowired
	LaoCustomerService customerService;
	@Autowired
	LaoCustPersonService custPersonService;
	@Autowired 
	LaoAccountRelService laoAccountRelService;
	@ResponseBody
	@RequestMapping("/list")
	public LaoSsAccountDto showList(){
		LaoSsAccountDto dto = accountService.selectUserById(1L);
		return dto;
	}
	
	/**
	 * 功能描述：展示系统用户列表
	 * @author zhangbt3
	 * @date 2016年6月20日 下午7:20:20
	 * @return
	 */
	@RequestMapping("/toList")
	public ModelAndView toList(){
		ModelAndView mv = new ModelAndView("RightsManagement/sysUserList");
		return mv;
	}
	
	@RequestMapping("/toPersonalDetails")
	public ModelAndView toPersonalDetails(Long userId){
		ModelAndView mv = new ModelAndView("RightsManagement/personalDetails");
		LaoSsAccountDto ssUserDto = accountService.selectUserById(userId);
		mv.addObject("ssUserDto", ssUserDto);
		
		List<LaoSsRoleDto> roleList = roleService.selectAllSsRole();
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 功能描述：分页查询系统用户列表
	 * @author zhangbt3
	 * @date 2016年6月20日 下午7:38:20
	 * @param dto
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value="toSsUserList")
	public Map<String, Object> toSsUserList(HttpServletRequest req,HttpServletResponse response,LaoSsAccountDto dto,String custId){
		  int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
		  int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
		  int pageNo = (pageStart / pageSize)+1;
		  
		  // 查询时基于当前登录账号的客户过滤（当前客户以及下级客户）显示账号
		  if (dto.getCustId() == null) {
			  LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
			  dto.setCustId(user.getCustId());
		  }
		  
		  Map<String, Object> resultMap= accountService.selectUserByPage(dto,pageNo,pageSize);
		 return resultMap;
	}
	
	/**
	 * 功能描述：跳转到新增系统用户页面
	 * @author zhangbt3
	 * @date 2016年6月20日 下午8:21:20
	 * @return
	 */
	@RequestMapping(value="toAdd")
	public ModelAndView toAdd(HttpServletRequest req){
		ModelAndView mv = new ModelAndView("RightsManagement/addOrEditSysUser");
		mv.addObject("submitType", "add");
		//-------------------------修改-----------------------------
		List<LaoSsRoleDto> roleList = new ArrayList<LaoSsRoleDto>();
		LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
		/*boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		 if(ifSuperUser==false){
		     roleList = user.getRoles();
         }else{
             roleList = roleService.selectAllSsRole();
         }*/
		
		if (CollectionUtils.isNotEmpty(user.getRoles())){
			// 目前一个账号就一个角色
			Long priority = user.getRoles().get(0).getPriority();
			// 查询低优先级的角色信息
			roleList = roleService.selectLowPriorityRole(priority);
		}
		
		
		//-------------------------修改-----------------------------
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 功能描述：新增系统用户，返回并刷新用户列表
	 * @author zhangbt3
	 * @date 2016年6月21日 上午10:51:45
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Map<String,Object> save(LaoSsAccountDto dto,String roleIds,String custId, HttpServletRequest req){
		if(custId!=null && custId.trim().length()>0){
			dto.setCustId(Long.valueOf(custId));
		}
		else {
			// 不传客户信息时，新建的账号需要和上下文账号为同一个企业客户
			LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
			dto.setCustId(user.getCustId());
		}
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<LaoSsRoleDto> roles = dto.getRoles();
		if(roles == null){
			roles = new ArrayList<LaoSsRoleDto>();
		}
		String[] roleIdList = null;
		//如果前端传递过来的角色Ids不为空，则进行切割，并添加到SsUserDto中roles
		if(StringUtils.isNotEmpty(roleIds)){
			roleIdList = roleIds.split(",");
			for (String str : roleIdList) {
				Long roleId = Long.valueOf(str);
				LaoSsRoleDto roleDto = new LaoSsRoleDto();
				roleDto.setRoleId(roleId);
				roles.add(roleDto);
			}
		}
		dto.setRoles(roles);
		int flag = -1;
		try {
			flag = accountService.saveUser(dto);
			resultMap.put("msg", "系统用户添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/**
	 * 功能描述：登录名唯一性校验
	 * @author zhangbt3
	 * @date 2016年6月27日 下午8:26:20
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkUnique")
	public Map<String,Object> checkUnique(String loginName){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		LaoSsAccountDto userDto = new LaoSsAccountDto();
		userDto.setLoginName(loginName);
		List<LaoSsAccountDto> list = accountService.selectUsersByModel(userDto);
		if(list != null && list.size() >0){
			resultMap.put("flag", false);
		}else{
			resultMap.put("flag", true);
		}
		return resultMap;
	}
	
	/**
	 * 功能描述：跳转到更新系统用户页面
	 * @author zhangbt3
	 * @date 2016年6月21日 上午10:57:50
	 * @param userId
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "update/{accountId}", method = RequestMethod.GET)
	public ModelAndView toUpdate(@PathVariable("accountId")Long accountId,HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("RightsManagement/addOrEditSysUser");
		LaoSsAccountDto ssUserDto = accountService.selectUserById(accountId);
		mv.addObject("ssUserDto", ssUserDto);
		mv.addObject("submitType", "update");
		List<LaoSsRoleDto> roleList = new ArrayList<LaoSsRoleDto>();
        LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
        /*boolean ifSuperUser = ActionUtil.ifSuperUser(req);
         if(ifSuperUser==false){
             roleList = user.getRoles();
         }else{
             roleList = roleService.selectAllSsRole();
         }*/
         
         if (CollectionUtils.isNotEmpty(user.getRoles())){
 			// 目前一个账号就一个角色
 			Long priority = user.getRoles().get(0).getPriority();
 			// 查询低优先级的角色信息
 			roleList = roleService.selectLowPriorityRole(priority);
 		}
 		
		//List<LaoSsRoleDto> roleList = roleService.selectAllSsRole();
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 功能描述：更新系统用户，返回并刷新用户列表
	 * @author zhangbt3
	 * @date 2016年6月21日 上午11:03:11
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Map<String,Object> update(LaoSsAccountDto dto,String roleIds) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String[] roleIdList = null;
		List<LaoSsRoleDto> roles = dto.getRoles();
		if(roles == null){
			roles = new ArrayList<LaoSsRoleDto>();
		}
		//如果前端传递过来的角色Ids不为空，则进行切割，并添加到SsUserDto中roles
		if(StringUtils.isNotEmpty(roleIds)){
			roleIdList = roleIds.split(",");
			for (String str : roleIdList) {
				Long roleId = Long.valueOf(str);
				LaoSsRoleDto roleDto = new LaoSsRoleDto();
				roleDto.setRoleId(roleId);
				roles.add(roleDto);
			}
			dto.setRoles(roles);
		}
		
		int flag = accountService.updateUser(dto);
		if(flag == 1){
			resultMap.put("msg", "系统用户更新成功");
		}else{
			resultMap.put("msg", "系统用户更新失败！");
		}
		
		return resultMap;
	}
	@ResponseBody
	@RequestMapping(value = "updateAccount", method = RequestMethod.POST)
	public Map<String,Object> update(LaoSsAccountDto dto) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		int flag = accountService.updateAccount(dto);
		if(flag == 1){
			resultMap.put("msg", "系统用户更新成功");
		}else{
			resultMap.put("msg", "系统用户更新失败！");
		}
		
		return resultMap;
	}
	
	
	/**
	 * 功能描述：重置用户密码（管理员权限）
	 * @author zhangbt3
	 * @date 2016年6月24日 下午2:38:15
	 * @param userId
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("resetPassword")
	public Map<String,Object> resetPassword(Long accountId){
		//返回结果封装到Map
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			int result = accountService.resetPassword(accountId);
			if(result == 1){
				resultMap.put("flag", true);
				resultMap.put("msg", "重置密码成功");
			}else{
				resultMap.put("flag", false);
				resultMap.put("msg", "重置密码失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 功能描述：弹出密码修改框
	 * @author zhangbt3
	 * @date 2016年6月24日 下午5:08:20
	 * @return
	 */
	@RequestMapping(value="toUpdatePassword/{userId}")
	public ModelAndView toUpdatePassword(@PathVariable("userId")Long userId){
		ModelAndView mv = new ModelAndView("RightsManagement/changePassword");
		
		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 功能描述：密码修改(用户个人修改)
	 * @author zhangbt3
	 * @date 2016年6月24日 下午6:16:49
	 * @param userId
	 * @param pb
	 * @return
	 */
	@RequestMapping("/changePassword")
	public Map<String,Object> changePassword(Long userId,PasswordBean pb){
		return null;
	}
	/**
	 * 个人详情
	 * @return
	 */
	@RequestMapping(value = "toDetail")
	public ModelAndView toDetail(HttpServletRequest req,HttpServletResponse rep) {
		LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
		LaoSsAccountDto account=accountService.queryAccountById(user.getAcconutId());
		ModelAndView mv=new ModelAndView("/account/UserDetail");
		mv.addObject("account", account);
		if(user.getCustId()!=null && user.getCustId()>0){
			LaoCustomerDto customer=customerService.selectDtoById(user.getCustId());
			mv.addObject("customer", customer);
			LaoCustPersonDto custPerson=custPersonService.selectByPrimaryKey(user.getCustId());
			mv.addObject("custPerson", custPerson);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(custPerson.getPsptEndDate()!=null){
				mv.addObject("psptEndDateStr",sdf.format(custPerson.getPsptEndDate()));
			}
			if(custPerson.getBirthday()!=null){
				mv.addObject("birthdayStr",sdf.format(custPerson.getBirthday()));
			}
			
		}
		LaoAccountRelDto laoAccountRelDto=new LaoAccountRelDto();
		laoAccountRelDto.setAccountId(user.getAcconutId());
		laoAccountRelDto.setRelType("1000");
		List<LaoAccountRelDto> bindLenovo = laoAccountRelService.queryRelByRelType(laoAccountRelDto);
		laoAccountRelDto.setRelType("1001");
		List<LaoAccountRelDto> bindWx = laoAccountRelService.queryRelByRelType(laoAccountRelDto);
		laoAccountRelDto.setRelType("1002");
		List<LaoAccountRelDto> bindAlipay = laoAccountRelService.queryRelByRelType(laoAccountRelDto);
		laoAccountRelDto.setRelType("1003");
		List<LaoAccountRelDto> bindQQ = laoAccountRelService.queryRelByRelType(laoAccountRelDto);
		laoAccountRelDto.setRelType("1004");
		List<LaoAccountRelDto> bindBlog = laoAccountRelService.queryRelByRelType(laoAccountRelDto);
		mv.addObject("bindLenovo", bindLenovo);
		mv.addObject("bindWx", bindWx);
		mv.addObject("bindAlipay", bindAlipay);
		mv.addObject("bindQQ", bindQQ);
		mv.addObject("bindBlog", bindBlog);
		//绑定账号列表
		return mv;
	}
	@RequestMapping(value="toChangePwd")
	public ModelAndView toChangePwd(HttpServletRequest req,HttpServletResponse rep) {
		ModelAndView mv=new ModelAndView("/account/changePwd");
		return mv;
	}
	@ResponseBody
	@RequestMapping("changePwd")
	public ResultMsg changePwd(HttpServletRequest req,LaoSsAccountDto dto,String oldpw){
		ResultMsg msg=new ResultMsg();
		msg.setSuccess(true);
		LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
		dto.setAcconutId(user.getAcconutId());
		if(user.getPlainPassword().equals(oldpw)){
			if(accountService.changePassword(dto,oldpw)>0){
				msg.setSuccess(true);
			}else{
				msg.setSuccess(false);
			}
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	@ResponseBody
	@RequestMapping("checkPwd")
	public ResultMsg checkPwd(HttpServletRequest req,String pwd){
		ResultMsg msg=new ResultMsg();
		msg.setSuccess(true);
		LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
		if(!user.getPlainPassword().equals(pwd)){
			msg.setSuccess(false);
		}
		return msg;
	}
	
	/**
     * 功能描述：企业客户跳转到新增系统用户页面
     * @author zhangbt3
     * @date 2016年6月20日 下午8:21:20
     * @return
     */
    @RequestMapping(value="custToAdd{custId}")
    public ModelAndView custToAdd(@PathVariable("custId")Long custId,HttpServletRequest req,HttpServletResponse resp){
        ModelAndView mv = new ModelAndView("RightsManagement/addOrEditSysUser");
        mv.addObject("submitType", "add");
        //-------------------------修改-----------------------------
        List<LaoSsRoleDto> roleList = new ArrayList<LaoSsRoleDto>();
        LaoSsAccountDto user=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
       /* boolean ifSuperUser = ActionUtil.ifSuperUser(req);
         if(ifSuperUser==false){
             roleList = user.getRoles();
         }else{
             roleList = roleService.selectAllSsRole();
         }*/
        if (CollectionUtils.isNotEmpty(user.getRoles())){
			// 目前一个账号就一个角色
			Long priority = user.getRoles().get(0).getPriority();
			// 查询低优先级的角色信息
			roleList = roleService.selectLowPriorityRole(priority);
		}
        //-------------------------修改-----------------------------
        mv.addObject("roleList", roleList);
        //mv.addObject("custId", custId);
        Cookie cookie = new Cookie("postCustId",""+custId);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return mv;
    }
    
    /**
     * 激活账号
     * @param req
     * @param accountId
     * @return
     */
    @ResponseBody
	@RequestMapping("/activeAccount")
	public ResultMsg activeAccount(HttpServletRequest req,Long accountId){
		ResultMsg msg=new ResultMsg();
		msg.setSuccess(true);
		try {
			accountService.changeAccountStatus(accountId, "0");
		} catch(Exception ex) {
			msg.setSuccess(false);
		}
		return msg;
	}
    
    /**
     * 冻结账号
     * @param req
     * @param accountId
     * @return
     */
    @ResponseBody
	@RequestMapping("/freezingAccount")
	public ResultMsg freezingAccount(HttpServletRequest req,Long accountId){
		ResultMsg msg=new ResultMsg();
		msg.setSuccess(true);
		try {
			accountService.changeAccountStatus(accountId, "1");
		} catch(Exception ex) {
			msg.setSuccess(false);
		}
		return msg;
	}
}
