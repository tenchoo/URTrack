package com.urt.web.web.laoiccidassignbath;

import java.util.HashMap;
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

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoIccidAssignBatchDto;
import com.urt.dto.LaoIccidAssignLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.laoIccidassignbatch.LaoIccidAssignBatchService;
import com.urt.web.common.util.ActionUtil;

@Controller
@RequestMapping("/iccidassignbatch")
public class LaoIccidAssignBatchController {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	@Autowired
	private LaoIccidAssignBatchService laoIccidAssignBatchService;
	@Autowired
	private BatchService batchService;

	/**
	 * 分页查询批量划拨总表记录
	 */
	@ResponseBody
	@RequestMapping("/get")
	public Map<String, Object> getIccidInfo(HttpServletRequest req,HttpServletResponse resp,LaoIccidAssignBatchDto dto)throws Exception{
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
        Map<String, Object> resultMap= laoIccidAssignBatchService.selectByPage(dto, pageNo, pageSize);
		return resultMap;
	}
	/**
	 * 分页查询批量划拨主页面
	 */
	@RequestMapping("/assignList")
	public ModelAndView assignList(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		ModelAndView model = new ModelAndView("assignbatch/assigncardlist");
		return model;
	}
	/**
	 * 划拨页面
	 */
	@RequestMapping("/assignCard")
	public ModelAndView assignCard(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		ModelAndView model = new ModelAndView("assignbatch/assigncard");
		return model;
	}
	/**
	 * 批量划拨页面
	 */
	@RequestMapping("/assignCardUpload")
	public ModelAndView assignCardUpload(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		ModelAndView model = new ModelAndView("assignbatch/assigncardupload");
		return model;
	}
	/**
	 * 卡划拨
	 */
	@ResponseBody
	@RequestMapping("/insertAssignCard")
	public int insertAssignCard(HttpServletRequest req,HttpServletResponse resp, LaoIccidAssignBatchDto dto)throws Exception{
		int i = laoIccidAssignBatchService.insertAssignCard(dto);
		return i;
		
	}
	/**
	 * 划拨信息
	 */
	@RequestMapping("/queryOneDetail")
	public ModelAndView queryOneDetail(HttpServletResponse response, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("assignbatch/queryOneDetail");
		Long batchId = Long.parseLong(request.getParameter("batchId"));
		LaoIccidAssignBatchDto dto = laoIccidAssignBatchService.selectByBatchId(batchId);
		Map<String, String> map = laoIccidAssignBatchService.selectOneDetailByBatchId(batchId+"");
		if (map != null) {
			mv.addObject("map", map);
		}
		mv.addObject("batchId", batchId);
		mv.addObject("dto", dto);
		return mv;
	}
	//划拨明细
	@RequestMapping("/queryDetail")
	public ModelAndView queryDetail(HttpServletResponse response, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("assignbatch/queryDetail");
		String batchId = request.getParameter("batchId");
		mv.addObject("batchId", batchId);
	    return mv;
	}
	//卡基本信息页面
	@RequestMapping("/basicCard")
	public ModelAndView basicCard(HttpServletResponse response, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("assignbatch/BasicCardInformation");
		String batchId = request.getParameter("batchId");
		mv.addObject("batchId", batchId);
	    return mv;
	}
		//SIM卡、号信息
		@RequestMapping("/SIMCard")
		public ModelAndView SIMCard(HttpServletResponse response, HttpServletRequest request){
			ModelAndView mv = new ModelAndView("assignbatch/SIMCardInformation");
			String batchId = request.getParameter("batchId");
			mv.addObject("batchId", batchId);
			return mv;
		}
		//操作划拨的卡
		@ResponseBody
		@RequestMapping("/toAssignCard")
		public int toAssignCard(HttpServletResponse response, HttpServletRequest request, LaoIccidAssignLibDto dto){
			return laoIccidAssignBatchService.toAssignCard(dto);
			
		}
		
		/**
		 * 分页查询批量录入明细表记录lao_iccid_assign_lib
		 */
		@ResponseBody
		@RequestMapping("/getDetailIccidInfo")
		public Map<String, Object> getDetailIccidInfo(HttpServletRequest req,HttpServletResponse resp,LaoIccidAssignLibDto dto)throws Exception{
			int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
			int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
			int pageNo = (pageStart / pageSize) + 1;
			Map<String, Object> resultMap= laoIccidAssignBatchService.selectDetailByPage(dto, pageNo, pageSize);
			return resultMap;
		}
}
