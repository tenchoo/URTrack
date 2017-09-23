package com.urt.web.web.cmpp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSmsInfoDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.cmpp.CmppService;
import com.urt.interfaces.cmpp.CmppZjService;
import com.urt.interfaces.cmpp.LaoCmppService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.StringUtil;

/**
 * 类说明：短信发送
 * 
 */
@Controller
@RequestMapping(value = "/cmpp")
public class CmppController {
	@Autowired
	private CmppService cmppServiceImpl;
	@Autowired
	private CmppZjService cmppZjServiceImpl;
	@Autowired
	private LaoCmppService laoCmppService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/show")
	public ModelAndView manualSettlement(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/CMPP/cmpp");
		return mv;
	}

	/**
	 * 导入excel
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/sendMsg")
	@ResponseBody
	public String upload(@RequestParam(value = "file") MultipartFile file, String templeId,
			HttpServletRequest request) throws Exception {
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		boolean supperRes = ActionUtil.ifSuperUser(request);
		List<Map<String, Object>> array = new ArrayList<>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		int sendSuccess = 0;
		Date date = new Date();
		String tokenMes = "";
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = readExcel(file);
				if(list == null){
					return "error";
				}
				if(list.size() == 0){
					return "null";
				}
				for (HashMap<String, Object> localMap : list) {
					// TODO send Msg
					String custId = "";
					if (user.getCustId() != null) {
						custId = user.getCustId().toString();
					}
					String iccid = localMap.get("iccid").toString().trim();
					String content = localMap.get("content").toString().trim();
					IccidLibDto iccidLib = userService.selectByIccid(iccid);
					if (iccidLib == null) {
						continue;
					}
					if (!supperRes) {
						if (iccidLib != null && ("2".equals(iccidLib.getOperators())||"5".equals(iccidLib.getOperators()))
								&& !iccidLib.getCustid().toString().equals(user.getCustId().toString())) {
							continue;
						}
						if (iccidLib != null && !("2".equals(iccidLib.getOperators())||"5".equals(iccidLib.getOperators()))) {
							continue;
						}
						
					}else{
						if (iccidLib != null && !("2".equals(iccidLib.getOperators())||"5".equals(iccidLib.getOperators()))) 
						{
							continue;
						}
					}

					Long msgId = laoCmppService.saveMsg(content,iccid, custId.toString(), user.getAcconutId());
//					LaoUserDto userInfo = userService.getLaoUserDtoByIccid(localMap.get("iccid").toString());
//					if (userInfo == null) {
//						System.out.println("iccid not regisited");
//						return null;
//					}
//					String number = userInfo.getMsisdn();
					boolean res = false;
					String lang = request.getParameter("lang");
					if(StringUtil.isBlank(lang)){
						lang ="EngList";
					}
					
					// TODO 判断运营商是浙江移动还是东莞移动
					if ("5".equals(iccidLib.getOperators())) {
						res = cmppZjServiceImpl.sendCmpp3Msg(localMap.get("content").toString(), iccidLib.getMsisdn(), lang);
					}else {
						res = cmppServiceImpl.sendCmpp3Msg(localMap.get("content").toString(), iccidLib.getMsisdn(), lang);
					}
					if (res) {
						laoCmppService.updateMsgSuccess(msgId);
						sendSuccess++;
					}
				}
			}	
		}else{
			return "null";
		}
		return "发送成功"+sendSuccess+"条，发送失败"+(list.size()-sendSuccess)+"条";// resultMap;
	}

	// 单卡发送
	@RequestMapping("/sigeSendSms")
	@ResponseBody
	public String sigeSendSms(HttpServletRequest req, HttpServletResponse resp, String content, String iccid, String lang) {
		Boolean res = false;
		String reult = "";
		try {
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			String custId = "";
			if (user.getCustId() != null) {
				custId = user.getCustId().toString();
			}
			Long msgId = laoCmppService.saveMsg(content, iccid, custId.toString(), user.getAcconutId());
			IccidLibDto iccidLib = userService.selectByIccid(iccid);
			res = false;
			// TODO 判断运营商是浙江移动还是东莞移动
			if ("5".equals(iccidLib.getOperators())) {
				res = cmppZjServiceImpl.sendCmpp3Msg(content, iccidLib.getMsisdn(), lang);
			}else {
				res = cmppServiceImpl.sendCmpp3Msg(content, iccidLib.getMsisdn(), lang);
			}
			if (res) {
				laoCmppService.updateMsgSuccess(msgId);
				reult = "1";
			}
		} catch (Exception e) {
			reult = "0";
			e.printStackTrace();

		}
		return reult;
	}

	// public Map<String, Object> showGrid(){
	//
	// }

	private List<HashMap<String, Object>> readExcel(MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<HashMap<String, Object>> list = null;
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			XSSFRow xssfRow0 = xssfSheet.getRow(0);
			if(xssfRow0 == null){
				break;
			}
			if(xssfRow0.getCell(0).getStringCellValue().equals("iccid")&&xssfRow0.getCell(1).getStringCellValue().equals("smsContent")){
				list = new ArrayList<HashMap<String, Object>>();
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						HashMap<String, Object> paraMap = new HashMap<String, Object>();
						String iccid = xssfRow.getCell(0).getStringCellValue();
						System.out.println(iccid);
						String content = xssfRow.getCell(1).getStringCellValue();
						System.out.println(content);
						paraMap.put("iccid", iccid);
						paraMap.put("content", content);
						list.add(paraMap);
					}
				}
			}
		}
		is.close();
		return list;
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList")
	public Map<String, Object> queryList(HttpServletRequest req, HttpServletResponse resp) {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		boolean supperRes = ActionUtil.ifSuperUser(req);
		if (supperRes) {
			paraMap.put("custId", null);
		} else {
			paraMap.put("custId", user.getCustId());
		}
		String iccid = req.getParameter("iccid");
		if (null == iccid || "".equals(iccid)) {
			paraMap.put("iccid", null);
		} else {
			paraMap.put("iccid", iccid);
		}
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		String parameter = req.getParameter("send");
		int start = pageStart + 1;
		int end = pageSize + pageStart;
		String startTimeRes = req.getParameter("start").toString();
		String endTimeRes = req.getParameter("end").toString();
		if (!startTimeRes.equals("")) {
			Date startTime = new Date(startTimeRes);
			paraMap.put("startTime", startTime);
		}
		if (!endTimeRes.equals("")) {
			Date endTime = new Date(endTimeRes);
			endTime.setHours(23);
			endTime.setMinutes(59);
			endTime.setSeconds(59);
			paraMap.put("endTime", endTime);
		}
		paraMap.put("start", start);
		paraMap.put("end", end);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		List<LaoSmsInfoDto> smsInfos = null;
		if ("1".equals(parameter)) {
			String condition = req.getParameter("condition");
			if(StringUtil.isBlank(condition)){
				paraMap.put("condition", null);
			}else{
				paraMap.put("condition", condition);
			}
			smsInfos = laoCmppService.querySendInfo(paraMap);
			count = laoCmppService.countSendInfo(paraMap);
		} else {
			// 已接收的
			smsInfos = laoCmppService.queryAcceptSms(paraMap);
			count = laoCmppService.countAcceptSms(paraMap);
		}

		resultMap.put("data", smsInfos);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", count);// 总记录数
		return resultMap;
	}

	@RequestMapping(value = "/checkedICCID")
	@ResponseBody
	public String checkedICCID(HttpServletRequest req, HttpServletResponse resp) {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String iccid = req.getParameter("iccid");
		String tokenMes = null;
		if (null == iccid || "".equals(iccid)) {
			tokenMes = "iccid非法";
			return tokenMes;
		}
		try {
			//iccid = "898606".equals(iccid.substring(0, 6)) ? ICCID.replaceLastChar(iccid) : iccid;
			IccidLibDto iccidLib = userService.selectByIccid(iccid);
			boolean supperRes = ActionUtil.ifSuperUser(req);
			if (!supperRes) {
				if (iccidLib != null && "2".equals(iccidLib.getOperators())
						&& iccidLib.getCustid().toString().equals(user.getCustId().toString())) {
					tokenMes = "1";
					return tokenMes;
				}
				if (iccidLib != null && "5".equals(iccidLib.getOperators())
						&& iccidLib.getCustid().toString().equals(user.getCustId().toString())) {
					tokenMes = "1";
					return tokenMes;
				}
				if (iccidLib != null && "2".equals(iccidLib.getOperators())
						&& !iccidLib.getCustid().toString().equals(user.getCustId().toString())) {
					tokenMes = "此卡不属于你登录的客户下";
					return tokenMes;
				}
				if (iccidLib != null && !"2".equals(iccidLib.getOperators()) && "1".equals(iccidLib.getOperators())) {
					tokenMes = "输入ICCID为联通卡，暂不支持短信发送功能";
					return tokenMes;
				}
				if (iccidLib != null && !"2".equals(iccidLib.getOperators()) && "3".equals(iccidLib.getOperators())) {
					tokenMes = "输入ICCID为电信卡，暂不支持短信发送功能";
					return tokenMes;
				}
			}
			if (iccidLib != null && "2".equals(iccidLib.getOperators())) {
				tokenMes = "1";
				return tokenMes;
			}
			if (iccidLib != null && "5".equals(iccidLib.getOperators())) {
				tokenMes = "1";
				return tokenMes;
			}
			if (iccidLib != null && !"2".equals(iccidLib.getOperators()) && "1".equals(iccidLib.getOperators())) {
				tokenMes = "输入ICCID为联通卡，暂不支持短信发送功能";
				return tokenMes;
			}
			if (iccidLib != null && !"2".equals(iccidLib.getOperators()) && "3".equals(iccidLib.getOperators())) {
				tokenMes = "输入ICCID为电信卡，暂不支持短信发送功能";
				return tokenMes;
			}
			if (iccidLib==null) {
				tokenMes = "iccid不存在";
				return tokenMes;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tokenMes = "iccid非法";
		return tokenMes;
	}
}
