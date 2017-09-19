package com.urt.web.dmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.dmp.LaoDMPGroupDto;
import com.urt.dto.dmp.LaoDMPOperationDto;
import com.urt.dto.dmp.LaoDMPStrategyDto;
import com.urt.dto.dmp.LaoDMPStrategyEditDto;
import com.urt.interfaces.dmp.DMPStrategyEditService;
@Controller
@RequestMapping("/tactical")
public class DMPtacticalController {
	
	@Autowired
	private DMPStrategyEditService dmpStrategyEditService;
	
	@RequestMapping("/addScheme")
	public ModelAndView addScheme(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("/dmp/addScheme");
		List<LaoDMPStrategyDto> strategyDtos=dmpStrategyEditService.getStrategys();
		List<LaoDMPOperationDto> operationDtos=dmpStrategyEditService.getOperations();
		mv.addObject("strategyDtos", strategyDtos);
		mv.addObject("operationDtos", operationDtos);
		return mv;
	}
	@RequestMapping("/selectOperatons")
	public ModelAndView selectOperaton(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("/dmp/selectOperatons");
		List<LaoDMPOperationDto> operationDtos=dmpStrategyEditService.getOperations();
		mv.addObject("operationDtos", operationDtos);
		return mv;
	}
	@RequestMapping("/editOperations")
	public ModelAndView selectOperaton(HttpServletRequest req,HttpServletResponse resp,String schemeId,String strategyId) {
		ModelAndView mv = new ModelAndView("/dmp/selectOperatons");
		List<LaoDMPOperationDto> operationDtos=dmpStrategyEditService.getOperations();
		mv.addObject("operationDtos", operationDtos);
		mv.addObject("schemeId", schemeId);
		mv.addObject("strategyId", strategyId);
		return mv;
	}
	@ResponseBody
	@RequestMapping("/getOperationByStrategy")
	public List<LaoDMPOperationDto> getOperationByStrategy(HttpServletRequest req,HttpServletResponse resp,String schemeId,String strategyId) {
		List<LaoDMPOperationDto> operationDtos=dmpStrategyEditService.getOperations(Long.parseLong(schemeId),Long.parseLong(strategyId));
		return operationDtos;
	}
	@RequestMapping("/schemeDetail/{id}")
	public ModelAndView schemeDetail(HttpServletRequest req,HttpServletResponse resp,@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/dmp/toDetail");
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		LaoDMPStrategyEditDto schemeDto=dmpStrategyEditService.getSchemeDetail(id,custId);
		mv.addObject("schemeDto", schemeDto);
		if("2".equals(schemeDto.getTargittype())){
			List<LaoDMPGroupDto> groupDtos=dmpStrategyEditService.getGroupsByScheme(id);
			mv.addObject("groupDtos", groupDtos);
		}
		List<LaoDMPStrategyDto> strategyDtos=dmpStrategyEditService.getStrategis(id);
		if(strategyDtos!=null&&strategyDtos.size()>0){
//			mv.addObject("strategyDtos", strategyDtos);
			Map<String,List<LaoDMPOperationDto>> map=new HashMap<String, List<LaoDMPOperationDto>>();
			for(LaoDMPStrategyDto strategyDto:strategyDtos){
				Long strategyId=strategyDto.getId();
				List<LaoDMPOperationDto> operationDtos=dmpStrategyEditService.getOperations(id,strategyId);
				if(operationDtos!=null&&operationDtos.size()>0){
					map.put(strategyDto.getStrategyName(), operationDtos);
				}
			}
			mv.addObject("operationMap", map);
		}
		return mv;
	}
	@ResponseBody
	@RequestMapping("/getGroupsOfScheme")
	public List<LaoDMPGroupDto> getSelectGroups(HttpServletRequest req,HttpServletResponse resp,LaoDMPStrategyEditDto schemeDto) {
		Long schemeId = schemeDto.getId();
		List<LaoDMPGroupDto> groupDtos=dmpStrategyEditService.getGroupsByScheme(schemeId);
		return groupDtos;
	}
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param id  schemeId
	 * @return
	 */
	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(HttpServletRequest req,HttpServletResponse resp,@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/dmp/schemeEdit");
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		LaoDMPStrategyEditDto schemeDto=dmpStrategyEditService.getSchemeDetail(id,custId);
		mv.addObject("schemeDto", schemeDto);
		List<LaoDMPStrategyDto> strategyDtos=dmpStrategyEditService.getStrategys();
		mv.addObject("strategyDtos", strategyDtos);
		return mv;
	}
	@ResponseBody
	@RequestMapping("/getStrategyOperations")
	public List<LaoDMPStrategyDto> getStrategyOperations(HttpServletRequest req,HttpServletResponse resp,LaoDMPStrategyEditDto schemeDto) {
		Long id = schemeDto.getId();
		List<LaoDMPStrategyDto> strategyDtos=dmpStrategyEditService.getStrategis(id);
		return strategyDtos;
	}
	@ResponseBody
	@RequestMapping("/saveScheme")
	public boolean saveScheme(HttpServletRequest req,HttpServletResponse resp,LaoDMPStrategyEditDto schemeDto) {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		schemeDto.setChannelcustId(custId);
		return dmpStrategyEditService.saveScheme(schemeDto);
	}
	@ResponseBody
	@RequestMapping("/getGroups")
	public List<LaoDMPGroupDto> getGroups(HttpServletRequest req,HttpServletResponse resp) {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		List<LaoDMPGroupDto> groupList=dmpStrategyEditService.getGroupsByCustId(custId);
		return groupList;
	}
	//跳转终端策略管理页面
	@RequestMapping("/tacticalManage")
	public ModelAndView tactical(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("/dmp/tacticalManage");
		return mv;
	}
	@ResponseBody
	@RequestMapping("/startUsing")
	public ResultMsg startUsing(Long id) {
		ResultMsg msg = new ResultMsg();
		if (dmpStrategyEditService.startUsing(id) > 0) {
			msg.setSuccess(true);
			msg.setMsg("启用成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("启用失败！");
		}
		return msg;
	}
	@ResponseBody
	@RequestMapping("/blockUp")
	public ResultMsg blockUp(HttpServletRequest req,HttpServletResponse resp, Long id) {
		ResultMsg msg = new ResultMsg();
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		if (dmpStrategyEditService.stopScheme(custId,id)) {
			msg.setSuccess(true);
			msg.setMsg("停用成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("停用失败！");
		}
		return msg;
	}
	/**
	 * 删除用户策略
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del")
	public ResultMsg del(HttpServletRequest req,HttpServletResponse resp, Long id) {
		ResultMsg msg = new ResultMsg();
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		if (dmpStrategyEditService.delStrategyRelative(custId,id)) {
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("删除失败！");
		}
		return msg;
	}
	/**
	 * 获取用户策略列表
	 * 
	 * @param req
	 * @param resp
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList")
	public Map<String, Object> queryList(HttpServletRequest req,
			HttpServletResponse resp, LaoDMPStrategyEditDto schemeDto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
				.toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
				.toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
//		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
//		if (ifSuperUser == false) {
//			if (user.getCustId() != null && user.getCustId() > 0) {
//				dto.setCustId(user.getCustId());
//			} else {
//				dto.setCustId(-1l);
//			}
//		}
		Long custId = user.getCustId();
		schemeDto.setChannelcustId(custId);
		Map<String, Object> resultMap = dmpStrategyEditService.queryPage(schemeDto, pageNo,pageSize);
		return resultMap;
	}
}
