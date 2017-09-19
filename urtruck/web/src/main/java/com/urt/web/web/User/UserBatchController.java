package com.urt.web.web.User;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.traffic.TimerTrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.BatchOperationUtils;

/**
 * 类说明：主界面
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/userBatchssService")
public class UserBatchController {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BatchService batchService;
	
	@Autowired
	TimerTrafficQueryService timerTrafficQueryService;
	
	/**
	 * 上传excel文件信息界面
	 */
	@RequestMapping("/uploadExcel")
	public ModelAndView uploadExcel() {
		ModelAndView mv = new ModelAndView("/user/batchService");
		return mv; 	
	}
	/**
	 * 批量录入卡信息(导入信息)
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/batchImport")
	public Map<String,Object> upload(@RequestParam(value = "file") MultipartFile file,HttpServletResponse response, HttpServletRequest request, String cardstatus, String ifMaintenance)
			throws IOException {
		BatchOperationUtils utils = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = null;
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new BatchOperationUtils();
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), true);
			} else {
				list = utils.importExcel(file.getInputStream(), false);
			}
			
			String accountId = null;
			String ifAdmin = null;
			String custId = null;
			if(list !=null && list.size() > 0){
				LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
				if(user != null) {
					accountId = user.getAcconutId().toString();
					if(user.getCustId() != null)
					custId = user.getCustId().toString();
					
					boolean right = ActionUtil.ifSuperUser(request);
					if(right){
						ifAdmin = "1";
					}
				}
				resultMap = batchService.batchImport(list, custId, cardstatus, ifMaintenance, accountId, ifAdmin);
			}else{
				resultMap.put("errorMsg", "读取excel 内容为空");
				log.error("读取excel 内容为空************************************");
			}
			
		}
		return resultMap;
	}
	
	/**
	* 功能描述：查询流量界面
	* @author sunhao
	* @date 2016年12月16日 下午2:44:53
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/queryFlow")
	public ModelAndView query() {
		ModelAndView mv = new ModelAndView("/user/batchQueryFlow");
		return mv; 	
	}
	
	@ResponseBody
	@RequestMapping("/batchQueryFlow")
	public Map<String,Object> batchQueryFlow(@RequestParam(value = "file") MultipartFile file,HttpServletResponse response, HttpServletRequest request,  String ifMaintenance)
			throws IOException {
		BatchOperationUtils utils = null;
		List<Map<String,Object>> list = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new BatchOperationUtils();
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), true);
			} else {
				list = utils.importExcel(file.getInputStream(), false);
			}
			String accountId = null;
			String ifAdmin = null;
			String custId = null;
			if(list !=null && list.size() > 0){
				LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
				if(user != null) {
					accountId = user.getAcconutId().toString();
					if(user.getCustId() != null)
					custId = user.getCustId().toString();
					
					boolean right = ActionUtil.ifSuperUser(request);
					if(right){
						ifAdmin = "1";
					}
				}
				//lao_batch_data 插入一条数据 
				LaoBatchDataDto dto=new LaoBatchDataDto();
				Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
				dto.setBatchId(id);
				dto.setRecvTime(new Date());
				dto.setSumNum((long)list.size());
				dto.setOperId(accountId);
				batchService.saveBatchData(dto);
				
				//循环将batchId 添加
				for (Map<String, Object> map : list) {
					map.put("batchId", id);
					map.put("custId", custId);
					map.put("ifAdmin", ifAdmin);
				}
				timerTrafficQueryService.sendUserMsg(list);
				resultMap.put("total", list.size());
			}else{
				resultMap.put("errorMsg", "读取excel 文件失败");
			}
		}else{
			resultMap.put("errorMsg", "没有读取到Excel文件内容");
		}
		
		return resultMap;
	}

}
