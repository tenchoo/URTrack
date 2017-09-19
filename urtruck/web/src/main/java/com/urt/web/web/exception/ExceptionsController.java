package com.urt.web.web.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.exception.LaoBusiexcpLogDto;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.exception.BusiExceptionService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;

@Controller
@RequestMapping("/exceptionService")
public class ExceptionsController {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private BusiExceptionService busiExceptionService;
	
	@Autowired
	private BatchService batchService;
	
	/**
	* 功能描述：展示手动处理异常界面
	* @author sunhao
	* @date 2016年12月7日 上午10:22:31
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/showExceptions")
	public ModelAndView showExceptions() {
		ModelAndView mv = new ModelAndView("/exception/showExceptions");
		return mv; 	
	}
	
	/**
	* 功能描述： 查询异常
	* @author sunhao
	* @date 2016年12月16日 上午10:24:22
	* @param @param request
	* @param @param dto
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryExceptions")
	public Map<String, Object> queryExceptionss(LaoBusiexcpLogDto dto , HttpServletRequest request){
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		return busiExceptionService.getHandedExceptions(dto,pageNo,pageSize);
	}
	
	@ResponseBody
	@RequestMapping("/solveExceptions")
	public Map<String,Object> solveExceptions(HttpServletRequest request,
			@RequestParam(value="boxIds[]",required=false) List<String> excpIdList){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 判断上传文件，如果不为空，将之转换成对象
		String accountId = null;
		String ifAdmin = null;
		String custId = null;
		if (excpIdList != null && excpIdList.size() > 0) {
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			if (user != null) {
				accountId = user.getAcconutId().toString();
				if (user.getCustId() != null)
					custId = user.getCustId().toString();

				boolean right = ActionUtil.ifSuperUser(request);
				if (right) {
					ifAdmin = "1";
				}
			}
			// lao_batch_data 插入一条数据
			LaoBatchDataDto dto = new LaoBatchDataDto();
			Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			dto.setBatchId(id);
			dto.setRecvTime(new Date());
			dto.setSumNum((long) excpIdList.size());
			dto.setOperId(accountId);
			batchService.saveBatchData(dto);
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			// 循环将batchId 添加
			for (String excpId : excpIdList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("batchId", id);
				map.put("custId", custId);
				map.put("ifAdmin", ifAdmin);
				map.put("excpId", excpId);
				list.add(map);
			}
			
			busiExceptionService.sendUserMsg(list);
			resultMap.put("total", list.size());
		} else {
			resultMap.put("errorMsg", "读取excel 文件失败");
		}

		return resultMap;
	}
}
