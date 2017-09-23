package com.urt.web.alarm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoAlmElemValueDto;
import com.urt.dto.LaoAlmRuleDto;
import com.urt.dto.LaoAlmRuleElemDto;
import com.urt.dto.LaoAlmRuleLogDto;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.UtilDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.interfaces.alarm.AlarmService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.web.batchQuery.ExcelUtil;

@Controller
@RequestMapping(value = "/alarm")
public class AlarmController {

	@Autowired
	private AlarmService alarmService;
	/**
	 * 跳转告警规则页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ruleList")
	public String ruleList() {
		return "alarm/ruleList";
	}
	/**
	 * 跳转告警记录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendMsgLog")
	public String sendMsgLog() {
		return "alarm/sendMsgLog";
	}

	/**
	 * 获取告警规则列表
	 * 
	 * @param req
	 * @param resp
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList")
	public Map<String, Object> queryList(HttpServletRequest req,
			HttpServletResponse resp, LaoAlmRuleDto dto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
				.toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
				.toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute(
				"sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		if (ifSuperUser == false) {
			if (user.getCustId() != null && user.getCustId() > 0) {
				dto.setCustId(user.getCustId());
			} else {
				dto.setCustId(-1l);
			}
		}
		Map<String, Object> resultMap = alarmService.queryPage(dto, pageNo,
				pageSize);
		return resultMap;
	}
	/**
	 * 获取告警日志列表
	 * 
	 * @param req
	 * @param resp
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryLogList")
	public Map<String, Object> queryLogList(HttpServletRequest req,
			HttpServletResponse resp,LaoAlmRuleLogDto almRuleLogDto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
				.toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
				.toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		if (ifSuperUser == false) {
			almRuleLogDto.setChannelCustId(currentUser.getCustId());
		}
		return alarmService.queryLogPage(almRuleLogDto, pageNo,pageSize);
	}
	@RequestMapping("/createRule")
	public ModelAndView createCustomer() {
		ModelAndView mv = new ModelAndView("alarm/add");
		return mv;
	}

	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("alarm/edit");
		LaoAlmRuleDto dto = alarmService.getRuleById(id);
		List<LaoAlmElemValueDto> ruleElemVaues = alarmService
				.getRuleElemVaue(id);
		mv.addObject("ruleDto", dto);
		Long pid = alarmService.getParentAlmRuleType(dto.getRuleTypeId())
				.getRulePId();
		mv.addObject("pid", pid);
		mv.addObject("ruleElemValueDtos", ruleElemVaues);
		return mv;
	}

	@RequestMapping("/toDetail/{id}")
	public ModelAndView toDetail(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("alarm/detail");
		LaoAlmRuleDto dto = alarmService.getRuleById(id);
		List<LaoAlmElemValueDto> ruleElemVaues = alarmService
				.getRuleElemVaue(id);
		mv.addObject("ruleDto", dto);
		Long pid = alarmService.getParentAlmRuleType(dto.getRuleTypeId())
				.getRulePId();
		mv.addObject("pid", pid);
		mv.addObject("ruleElemValueDtos", ruleElemVaues);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getLevel1")
	public List<Map<String, Object>> getLevel1() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<Map<String, Object>> maps = alarmService.getLevel1();
		for (Map<String, Object> level : maps) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", level.get("NAME"));
			map.put("id", level.get("ID"));
			list.add(map);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/getLevel2")
	public List<Map<String, Object>> getLevel2(Integer pid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<Map<String, Object>> maps = alarmService.getLevel2(pid);
		for (Map<String, Object> level : maps) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", level.get("NAME"));
			map.put("id", level.get("ID"));
			list.add(map);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/getElementsByRuleType")
	public JSONObject getElements(Long ruleId, HttpServletRequest req) {
		LaoAlmRuleElemDto dto = new LaoAlmRuleElemDto();
		dto.setRuleTypeId(ruleId);
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute(
				"sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		if (ifSuperUser == false) {
			if (user.getCustId() != null && user.getCustId() > 0) {
				dto.setChannelCustId(user.getCustId());
			} else {
				dto.setChannelCustId(null);
			}
		}
		List<Map<String, Object>> elements = alarmService
				.getElementsByRuleType(dto);
		JSONObject condition = null;
		Boolean conditionFlag = false;
		List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
		JSONObject execute = null;
		Boolean executeFlag = false;
		List<Map<String, Object>> executeList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < elements.size(); i++) {
			Map<String, Object> map = elements.get(i);
			/*
			 * if("1".equals(map.get("ELEMENT_TYPE").toString())){
			 * if(conditionFlag){ conditionList.add(map);
			 * if(i==elements.size()-1){
			 * condition.put(map.get("ELEM_GROUP").toString(), conditionList);
			 * }else{
			 * if(Integer.valueOf(map.get("ELEMENT_TYPE").toString())!=Integer
			 * .valueOf(elements.get(i-1).get("ELEMENT_TYPE").toString())){
			 * condition.put(map.get("ELEM_GROUP").toString(), conditionList);
			 * conditionList=new ArrayList<Map<String,Object>>(); }else{
			 * if(Integer
			 * .valueOf(map.get("ELEM_GROUP").toString())!=Integer.valueOf
			 * (elements.get(i-1).get("ELEM_GROUP").toString())){
			 * condition.put(map.get("ELEM_GROUP").toString(), conditionList);
			 * conditionList=new ArrayList<Map<String,Object>>(); } } }
			 * 
			 * }else{ condition=new JSONObject(); conditionList=new
			 * ArrayList<Map<String,Object>>(); conditionList.add(map);
			 * if(i==elements.size()-1){
			 * condition.put(map.get("ELEM_GROUP").toString(), conditionList);
			 * }else{
			 * if(Integer.valueOf(map.get("ELEMENT_TYPE").toString())!=Integer
			 * .valueOf(elements.get(i+1).get("ELEMENT_TYPE").toString())){
			 * condition.put(map.get("ELEM_GROUP").toString(), conditionList);
			 * conditionList=new ArrayList<Map<String,Object>>(); }else{
			 * if(Integer
			 * .valueOf(map.get("ELEM_GROUP").toString())!=Integer.valueOf
			 * (elements.get(i+1).get("ELEM_GROUP").toString())){
			 * condition.put(map.get("ELEM_GROUP").toString(), conditionList);
			 * conditionList=new ArrayList<Map<String,Object>>(); } } } }
			 * conditionFlag=true; }else
			 * if("2".equals(map.get("ELEMENT_TYPE").toString())){
			 * if(executeFlag){ executeList.add(map); if(i==elements.size()-1){
			 * execute.put(map.get("ELEM_GROUP").toString(), executeList);
			 * }else{
			 * if(Integer.valueOf(map.get("ELEMENT_TYPE").toString())!=Integer
			 * .valueOf(elements.get(i-1).get("ELEMENT_TYPE").toString())){
			 * execute.put(map.get("ELEM_GROUP").toString(), executeList);
			 * executeList=new ArrayList<Map<String,Object>>(); }else{
			 * if(Integer
			 * .valueOf(map.get("ELEM_GROUP").toString())!=Integer.valueOf
			 * (elements.get(i-1).get("ELEM_GROUP").toString())){
			 * execute.put(map.get("ELEM_GROUP").toString(), executeList);
			 * executeList=new ArrayList<Map<String,Object>>(); } } }
			 * 
			 * }else{ execute=new JSONObject(); executeList=new
			 * ArrayList<Map<String,Object>>(); executeList.add(map);
			 * if(i==elements.size()-1){
			 * execute.put(map.get("ELEM_GROUP").toString(), executeList);
			 * }else{
			 * if(Integer.valueOf(map.get("ELEMENT_TYPE").toString())!=Integer
			 * .valueOf(elements.get(i+1).get("ELEMENT_TYPE").toString())){
			 * execute.put(map.get("ELEM_GROUP").toString(), executeList);
			 * executeList=new ArrayList<Map<String,Object>>(); }else{
			 * if(Integer
			 * .valueOf(map.get("ELEM_GROUP").toString())!=Integer.valueOf
			 * (elements.get(i+1).get("ELEM_GROUP").toString())){
			 * execute.put(map.get("ELEM_GROUP").toString(), executeList);
			 * executeList=new ArrayList<Map<String,Object>>(); } } } }
			 * executeFlag=true;
			 * 
			 * }else{ //预留 }
			 */
			if ("1".equals(map.get("ELEMENT_TYPE").toString())) {
				conditionList.add(map);
			} else if ("2".equals(map.get("ELEMENT_TYPE").toString())) {
				executeList.add(map);
			} else {

			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("condition", conditionList);
		jsonObject.put("execute", executeList);
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping(value = "/getElemValue")
	public JSONObject getElemValue(Long ruleId) {
		JSONObject jsonObject = new JSONObject();
		List<LaoAlmElemValueDto> ruleElemVaues = alarmService
				.getRuleElemVaue(ruleId);
		for (LaoAlmElemValueDto dto : ruleElemVaues) {
			jsonObject.put(dto.getElementId().toString(), dto.getElemValue());
		}
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public ResultMsg save(HttpServletRequest req, UtilDto utilDto,
			LaoAlmRuleDto dto) {
		Long ruleId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
		dto.setCustId(currentUser.getCustId());
		dto.setRecvOperId(currentUser.getAcconutId().toString());
		dto.setRecvTime(new Date());
		dto.setUpdOperId(currentUser.getAcconutId().toString());
		dto.setUpdTime(new Date());
		dto.setRuleId(ruleId);
		dto.setValidTag("1");
		ResultMsg msg = new ResultMsg();
		try {
			Integer result = alarmService.saveRule(dto);
			if (result > 0) {
				msg.setSuccess(true);
				for (String eid : utilDto.getElementIds()) {
					LaoAlmElemValueDto elemValue = new LaoAlmElemValueDto();
					String[] paramValues = req.getParameterValues(eid);
					elemValue.setElementId(Long.valueOf(eid));
					String paramValue="";
					for(int i=0;i<paramValues.length;i++){
						paramValue+=paramValues[i]+",";
					}
					paramValue=paramValue.substring(0, paramValue.length()-1);
					elemValue.setElemValue(paramValue);
					elemValue.setRecvOperId(currentUser.getAcconutId()
							.toString());
					elemValue.setRecvTime(new Date());
					elemValue.setRuleId(ruleId);
					elemValue.setRuleTypeId(dto.getRuleTypeId());
					elemValue.setUpdateOperId(currentUser.getAcconutId()
							.toString());
					elemValue.setUpdateTime(new Date());
					alarmService.saveRuleElemVaue(elemValue);
				}
			} else {
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			msg.setSuccess(false);
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultMsg update(HttpServletRequest req, UtilDto utilDto,
			LaoAlmRuleDto dto) {
		ResultMsg msg = new ResultMsg();
		LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
		dto.setUpdOperId(currentUser.getAcconutId().toString());
		dto.setUpdTime(new Date());
		try {
			Integer result = alarmService.updateRule(dto);
			if (result > 0) {
				alarmService.delAllElemValueByRuleId(dto.getRuleId());
				msg.setSuccess(true);
				for (String eid : utilDto.getElementIds()) {
					LaoAlmElemValueDto elemValue = new LaoAlmElemValueDto();
					String[] paramValues = req.getParameterValues(eid);
					elemValue.setElementId(Long.valueOf(eid));
					String paramValue="";
					for(int i=0;i<paramValues.length;i++){
						paramValue+=paramValues[i]+",";
					}
					paramValue=paramValue.substring(0, paramValue.length()-1);
					elemValue.setElemValue(paramValue);
					elemValue.setRecvOperId(currentUser.getAcconutId()
							.toString());
					elemValue.setRecvTime(new Date());
					elemValue.setRuleId(dto.getRuleId());
					elemValue.setRuleTypeId(dto.getRuleTypeId());
					elemValue.setUpdateOperId(currentUser.getAcconutId()
							.toString());
					elemValue.setUpdateTime(new Date());
					alarmService.saveRuleElemVaue(elemValue);
				}
			} else {
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			msg.setSuccess(false);
		}

		return msg;
	}

	@ResponseBody
	@RequestMapping("/del")
	public ResultMsg del(Long id) {
		ResultMsg msg = new ResultMsg();
		if (alarmService.delRule(id) > 0) {
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("删除失败！");
		}
		return msg;
	}
	@ResponseBody
	@RequestMapping("/startUsing")
	public ResultMsg startUsing(Long id) {
		ResultMsg msg = new ResultMsg();
		if (alarmService.updateRuleStatus(id,"1") > 0) {
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
	public ResultMsg blockUp(Long id) {
		ResultMsg msg = new ResultMsg();
		if (alarmService.updateRuleStatus(id,"0") > 0) {
			msg.setSuccess(true);
			msg.setMsg("停用成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("停用失败！");
		}
		return msg;
	}

	@RequestMapping(value = "/toSendMsg")
	public String toSendMsg() {
		return "alarm/sendMsg";
	}

	@ResponseBody
	@RequestMapping(value = "/sendMsg")
	public String sendMsg(HttpServletRequest req) {
		LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		if (ifSuperUser == false) {
			return alarmService.sendMsgByCust(currentUser);
		}else{
			return alarmService.sendMsg(currentUser);
		}
		
	}
	@RequestMapping(value = "/download")
	public String download(HttpServletRequest req,HttpServletResponse response) {
		String almId=req.getParameter("almId");
		String fileName = "卡详情信息";
		try{
			List<Map<String,Object>> cardInfos=alarmService.queryCardInfo(almId);
			String[] columnNames = { "ICCID", "运营商", "企业客户", "已使用流量(MB)", "剩余流量(MB)"};// 列名
			String[] keys = { "ICCID", "OPERATORNAME", "CUSTNAME", "USECOUNT", "REMAINDATA"};// map中的key
			List<Map<String, Object>> list = createExcelRecord(cardInfos);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 进行转码，使其支持中文文件名
			String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			// System.out.println("文件名转码:"+codedFileName);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((codedFileName + ".xls").getBytes(), "utf-8"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (final IOException e) {
				throw e;
			} finally {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			return null;
  }
	private List<Map<String, Object>> createExcelRecord(List<Map<String,Object>> cardInfos) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		for (int j = 0; j < cardInfos.size(); j++) {
			Map<String, Object> cardInfo = cardInfos.get(j);
			String strUseCount = "";
			String strDataRemain = "";
			// 单位转化为MB
			DecimalFormat df = new DecimalFormat("######0.00");
			if (cardInfo.get("USECOUNT") != null) {
				BigDecimal usecount=(BigDecimal) cardInfo.get("USECOUNT");
				double dUseCount = usecount.setScale(BigDecimal.ROUND_HALF_UP).doubleValue();
				strUseCount = df.format(dUseCount / 1024);
			}
			if (cardInfo.get("REMAINDATA") != null) {
				BigDecimal remainData=(BigDecimal) cardInfo.get("REMAINDATA");
				double dDataRemain = remainData.setScale(BigDecimal.ROUND_HALF_UP).doubleValue();
				strDataRemain = df.format(dDataRemain / 1024);
			}
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("ICCID", cardInfo.get("ICCID"));
			mapValue.put("OPERATORNAME", cardInfo.get("OPERATORNAME"));
			mapValue.put("CUSTNAME", cardInfo.get("CUSTNAME"));
			mapValue.put("USECOUNT", strUseCount);
			mapValue.put("REMAINDATA", strDataRemain);
			listmap.add(mapValue);
		}
		return listmap;
	}
}
