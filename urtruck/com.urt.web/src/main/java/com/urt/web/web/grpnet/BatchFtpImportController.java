package com.urt.web.web.grpnet;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.grpnet.BatchFtpImportDto;
import com.urt.dto.grpnet.GetUserDiscntInfoReq;
import com.urt.dto.grpnet.GrpnetImpbillDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.grpnet.BatchFtpImportService;
import com.urt.interfaces.grpnet.ColonyBillStatusQueryService;
import com.urt.interfaces.grpnet.GrpNetExpBillService;
import com.urt.interfaces.grpnet.GrpnetImpbillService;
import com.urt.interfaces.sendMessage.SendMessageService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.FtpsFileList;

/**
 * 类说明：FTP取文件导入账单,存入数据库
 * 
 * @author Lingfei
 * @date 2016年9月18日
 */
@Controller
@RequestMapping(value = "/batchFtpImport")
public class BatchFtpImportController {

	private static Log log = LogFactory.getLog(BatchFtpImportController.class);

	@Autowired
	private BatchFtpImportService batchFtpImportService;
	@Autowired
	private GrpnetImpbillService grpnetImpbillService;
	@Autowired
	private GrpNetExpBillService grpNetExpBillService;
	@Autowired
	private BatchService batchService;
	@Autowired
	private ColonyBillStatusQueryService colonyBillQueryService;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private LaoAccountRelService laoAccountRelService;

	@RequestMapping(value = "/batchview")
	public ModelAndView grpNetBill() {
		ModelAndView mv = new ModelAndView("/grpNet/batchFtpImport");
		return mv;
	}

	/**
	 * 1.FTP读取文件 2.记录批量导入汇总表 3.发消息给kafka
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/doBatch")
	public ModelAndView doBatchFtpImport(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/grpNet/batchFtpImport");
		String retmsg = "导入文件失败！";
		try {
			Short tradeTypeCode = Short.parseShort(request.getParameter("tradeTypeCode"));
			Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
			// 查询文件存放、路径、名称
			BatchFtpImportDto bfiDto = batchFtpImportService.selectByPrimaryKey(tradeTypeCode);
			String ip = bfiDto.getHostIp();
			int port = Integer.parseInt(bfiDto.getHostPort());
			String userName = bfiDto.getUserName();
			String userPwd = bfiDto.getPasswd();
			String path = bfiDto.getFilePath();
//			String fileName = bfiDto.getFileName();
			String fileName = bfiDto.getFileName().replace("$$", cycleId+"");
													
			// 1.FTP登录,解析文件，得到List<Map<String, String>>和表头 String[]
			List<Map<String, String>> listMap = null;
			String[] str0 = null;
			FtpsFileList ffl = new FtpsFileList(ip, port, userName, userPwd,path, fileName);
			listMap = ffl.getListMap();
			str0 = ffl.getStr0();
			if (listMap == null || str0 == null) {
				retmsg = "未获取到相关文件，请确认文件是否存在，否则请手工导入文件！";
				mv.addObject("retmsg", retmsg);
				return mv;
			}
			// 同一批数据，批次号一样
			Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
			LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			String accountId = "";
			if(user != null) {
				accountId = user.getAcconutId().toString();
			}
			// 2.记录批量导入汇总表
			this.saveBatchData(listMap.size(), str0.length, batchId,tradeTypeCode,accountId);
			// 3.组装参数，发消息给kafka
			this.sendMsgKafka(listMap, str0, cycleId, batchId, tradeTypeCode,accountId);
			retmsg = "导入处理中，明细进度请查询<批量操作结果查询>！";
		} catch (Exception e) {
			e.printStackTrace();
			retmsg = "文件解析错误，请确认文件格式！";
		} finally {
			// 返回mv
			mv.addObject("retmsg", retmsg);
			return mv;
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/sendMsg")
	public Map<String, Object> upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		Short tradeTypeCode = Short.parseShort(request.getParameter("tradeTypeCode"));
		Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			if (fileName.endsWith("xlsx")) {
				InputStream is = file.getInputStream();
				List<Map<String, String>> listMap = null;
				String[] str0 = null;
				Map<String, Object> map = FtpsFileList.readFile(is, fileName);
				listMap = (List<Map<String, String>>) map.get("listMap");
				str0 = (String[]) map.get("str0");
				if (listMap != null && str0 != null) {
					// 同一批数据，批次号一样
					Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
					LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
					String accountId = "";
					if(user != null) {
						accountId = user.getAcconutId().toString();
					}
					try {
						// 2.记录批量导入汇总表
						this.saveBatchData(listMap.size(), str0.length, batchId,tradeTypeCode,accountId);
						// 3.组装参数，发消息给kafka
						this.sendMsgKafka(listMap, str0, cycleId, batchId, tradeTypeCode,accountId);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return new HashMap<>();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 存入批量汇总表
	 * 
	 * @param count
	 * @param length
	 * @param batchId
	 */
	private void saveBatchData(int count, int length, Long batchId,Short tradeTypeCode,String accountId) {
		LaoBatchDataDto dto = new LaoBatchDataDto();
		dto.setBatchId(batchId);// 批次号
		dto.setDealTag("1");// 处理状态:0-未处理，1-处理中，2-处理完成
		dto.setFailNum(0L);// 失败数量
		dto.setOperId(accountId);// 操作人员
		dto.setOperatorsId(4);// 运营商ID
		dto.setRecvTime(new Date());// 初始入表时间
		dto.setRemark("");// 备注
		dto.setResultInfo("");// 处理结果信息
		dto.setRsrvInfo1("4");// 保留字段1 :暂填运行商ID
		dto.setRsrvInfo2("");// 保留字段2
		dto.setSuccNum(0L);// 成功数量
		dto.setSumNum((long) (count) * (length - 1));// 总记录数
		dto.setTradeTypeCode(tradeTypeCode);// 业务类型编码
		dto.setUpdateTime(new Date());// 更新时间
		batchService.saveBatchData(dto);
	}

	/**
	 *  发送消息给kafka
	 *  
	 * @param listMap  解析Excel的Map集合
	 * @param str0 表头
	 * @param cycleId  账期标识
	 * @return listDto 账目集合
	 */
	private void sendMsgKafka(List<Map<String, String>> listMap, String[] str0,
			Integer cycleId, Long batchId, Short tradeTypeCode,String accountId) {
		// 查询出ItemName ItemId的集合MAP<账目名称，账目编码>
		Map<String, Integer> mapNameId = grpnetImpbillService.selectAllMapDtem();
		List<List<GrpnetImpbillDto>> listMsg = new ArrayList<List<GrpnetImpbillDto>>();
		for (int i = 0; i < listMap.size(); i++) {
			Map<String, String> map = listMap.get(i);
			String val0 = map.get("0");
			List<GrpnetImpbillDto> list = new ArrayList<GrpnetImpbillDto>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if ("0".equals(key)) {
					continue;
				}
				// 账目表参数组装
				GrpnetImpbillDto record = new GrpnetImpbillDto();
				record.setUserId(Long.parseLong(val0)); // 用户ID
				record.setSerialNumber(val0); // 用户号码
				record.setBatchId(batchId); // 批次号
				record.setDealTag("0"); // 处理标识
				record.setCycleId(cycleId); // 账期标识
				record.setRecvTime(new Date()); // 生成时间
				record.setResultInfo("OK"); // 处理结果信息
				record.setUpdateTime(new Date()); // 更新时间
				record.setUpdateStaffId(accountId); // 更新员工
				record.setFileSrc(""); // 数据来源
				record.setRsrvInfo1(tradeTypeCode + ""); // 预留字段1 传给明细表
				record.setRsrvInfo2(""); // 预留字段2
				record.setBillId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));// 账单流水号
				record.setUseCount(Long.parseLong(value)); // 使用量
				int in = Integer.parseInt(key);
				record.setItemName(str0[in]);// 账目名称
				record.setItemId(mapNameId.get(str0[in]));// 账目编码
				list.add(record);
			}
			listMsg.add(list);
		}
		// 发送消息给kafka
		grpnetImpbillService.sendUserMsg(listMsg);
	}

	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/billByNumber")
	public ModelAndView selectBillItemByCycleIdByNumber(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/H5/billByNumber");
		SimpleDateFormat sdf = new SimpleDateFormat("MM月");
		SimpleDateFormat sdfym = new SimpleDateFormat("yyyy年MM月");
		Calendar cal = Calendar.getInstance();
		Object obj = request.getSession().getAttribute("username");
		String number = "";
		log.info("----------------------selectBillItemByCycleIdByNumber进入微信集群网账单查询---------------------");
		if (obj != null) {
			number =obj.toString() ;
		} else {
			log.info("---------------------selectBillItemByCycleIdByNumber----------------------username为空");
			String openId = (String) request.getSession().getAttribute("openId");
			log.info("--------------------selectBillItemByCycleIdByNumber-----------------------openId="+openId);
			if(openId != null){
				LaoAccountRelDto laoAcco = laoAccountRelService.queryRelAccountByOpenId(openId);
				if (laoAcco != null) {
					number = laoAcco.getRelLoginname();
				}
			}
		}
		log.info("---------------------selectBillItemByCycleIdByNumber----------------------number="+number);
//		number="18519560766";// 测试数据,需要注掉
		// 查询本月账单
		GetUserDiscntInfoReq infoReq = new GetUserDiscntInfoReq();
		SimpleDateFormat sdf2 = new SimpleDateFormat("YYYYMMddHHmmss");
		infoReq.setDiscntId("A");
		infoReq.setOperator("Z000LX");
		infoReq.setOperDepart("Z00LX");
		infoReq.setPhoneNo(number);
		infoReq.setPhoneType("G");
		infoReq.setTradeId(sdf2.format(new Date()));
		Map<String, Object> map = colonyBillQueryService.queryColonyBillStatus(infoReq);
		if (map == null) {
			map = new HashMap<String, Object>();
			map.put("soundsDiscnt", "0分钟");
			map.put("messageDiscnt", "0条");
			map.put("flowDiscnt", "0KB");
		}
		for (int i = 0; i < 6; i++) {
			cal.add(Calendar.MONTH, -1);
			map.put("cycleIdM"+i, sdf.format(cal.getTime()));
			map.put("cycleIdYM"+i, sdfym.format(cal.getTime()));
		}
		mv.addObject("number", number);
		mv.addObject("map", map);
		
		return mv;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/comeWebBillByNumber")
	public ModelAndView comeSelectBillItemWeb(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/grpNet/billByNumber");
		SimpleDateFormat sdf0 = new SimpleDateFormat("YYYY年MM月");
		SimpleDateFormat sdf = new SimpleDateFormat("MM月");
		Calendar cal = Calendar.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 6; i++) {
			cal.add(Calendar.MONTH, -1);
			map.put("cycleIdM"+i, sdf.format(cal.getTime()));
			map.put("cycleIdYM"+i, sdf0.format(cal.getTime()));
		}
		mv.addObject("map", map);
		return mv;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/billByAjaxNumber")
	public Map<String, Object> selectBillByCycleIdByNumber(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> mapList = new HashMap<String,Object>();
		SimpleDateFormat sdfym = new SimpleDateFormat("YYYYMM");
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		Calendar calm = Calendar.getInstance();
		Object obj = request.getSession().getAttribute("username");
		String number = "";
		if (obj != null) {
			number =obj.toString() ;
		} else {
			log.info("-------------------selectBillByCycleIdByNumber------------------------username为空");
			String openId = (String) request.getSession().getAttribute("openId");
			log.info("---------------------selectBillByCycleIdByNumber----------------------openId="+openId);
			if(openId != null){
				LaoAccountRelDto laoAcco = laoAccountRelService.queryRelAccountByOpenId(openId);
				if (laoAcco != null) {
					number = laoAcco.getRelLoginname();
				}
			}
		}
		log.info("----------------------selectBillByCycleIdByNumber---------------------number="+number);
		int cycleId = Integer.parseInt(sdfym.format(new Date()));
		// 查询前6个月账单
		for (int i = 0; i < 6; i++) {
			calm.add(Calendar.MONTH, -1);
			cycleId = Integer.parseInt(sdfym.format(calm.getTime()));
			String cycleStr = sdfm.format(calm.getTime());
			List<Map<String, Object>> listMap = grpNetExpBillService.selectBillItemByCycleIdByNumber(cycleId, number);
			mapList.put(cycleStr, listMap);
		}
		return mapList;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/webBillByAjaxNumber")
	public Map<String, Object> webSelectBillByCycleIdByNumber(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("进入webBillByAjaxNumber");
		Map<String, Object> mapList = new HashMap<String,Object>();
		SimpleDateFormat sdfym = new SimpleDateFormat("YYYYMM");
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		Calendar calm = Calendar.getInstance();
		String number = request.getParameter("number");
		int cycleId = Integer.parseInt(sdfym.format(new Date()));
		// 查询前6个月账单
		for (int i = 0; i < 6; i++) {
			calm.add(Calendar.MONTH, -1);
			cycleId = Integer.parseInt(sdfym.format(calm.getTime()));
			String cycleStr = sdfm.format(calm.getTime());
			List<Map<String, Object>> listMap = grpNetExpBillService.selectBillItemByCycleIdByNumber(cycleId, number);
			mapList.put(cycleStr, listMap);
		}
		
		// 查询本月账单
		GetUserDiscntInfoReq infoReq = new GetUserDiscntInfoReq();
		SimpleDateFormat sdf2 = new SimpleDateFormat("YYYYMMddHHmmss");
		infoReq.setDiscntId("A");
		infoReq.setOperator("Z000LX");
		infoReq.setOperDepart("Z00LX");
		infoReq.setPhoneNo(number);
		infoReq.setPhoneType("G");
		infoReq.setTradeId(sdf2.format(new Date()));
		Map<String, Object> map = colonyBillQueryService.queryColonyBillStatus(infoReq);
		if (map == null) {
			map = new HashMap<String, Object>();
			map.put("soundsDiscnt", "0分钟");
			map.put("messageDiscnt", "0条");
			map.put("flowDiscnt", "0KB");
		}
		mapList.put("mapmonth", map);
		mapList.put("number", number);
		System.out.println("离开webBillByAjaxNumber");
		return mapList;
	}
	
	
	/**
	 * 集群网发送短信
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/colonySendMessage")
	public ModelAndView colonySendMessage(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/grpNet/grpNetBill");
		int total = userService.getCardCountByColony();
		Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		int page = (int) Math.ceil((total -1)/ 1000 );
		if (page == 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("begin", 1);
			param.put("end", total);
			List<Map<String, Object>> listMap = userService.queryUserInfoByColony(param);
			for (Map<String, Object> map : listMap) {
				map.put("batchId", batchId);
			}
			// 发送消息给kafka
			log.info("----------------------集群网发送短信发送消息给kafka---------------------");
			batchFtpImportService.sendUserMsg(listMap);
			log.info("----------------------------发送完成，listMsg:"+listMap.toString());
		} else {
			for (int i = 1; i <= page+1; i++) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("begin", (i-1)* 1000 + 1);
				param.put("end", i * 1000);
				List<Map<String, Object>> listMap = userService.queryUserInfoByColony(param);
				for (Map<String, Object> map : listMap) {
					map.put("batchId", batchId);
				}
				// 发送消息给kafka
				log.info("----------------------集群网发送短信发送消息给kafka---------------------");
				batchFtpImportService.sendUserMsg(listMap);
				log.info("----------------------------发送完成，listMsg:"+listMap.toString());
			}
		}
		LaoSsAccountDto user = (LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		String accountId = "";
		if(user != null) {
			accountId = user.getAcconutId().toString();
		}
		// 插入批量总表
		saveBatchData((long)total, batchId,accountId);
		mv.addObject("msg", "短信开始发送！");
		return mv;
	}
	
	/**
	 * 存入批量汇总表
	 * 
	 * @param count
	 * @param batchId
	 */
	private void saveBatchData(Long count, Long batchId,String accountId) {
		LaoBatchDataDto dto = new LaoBatchDataDto();
		dto.setBatchId(batchId);// 批次号
		dto.setDealTag("1");// 处理状态:0-未处理，1-处理中，2-处理完成
		dto.setFailNum(0L);// 失败数量
		dto.setOperId(accountId);// 操作人员
		dto.setOperatorsId(0);// 运营商ID
		dto.setRecvTime(new Date());// 初始入表时间
		dto.setRemark("");// 备注
		dto.setResultInfo("");// 处理结果信息
		dto.setRsrvInfo1("");// 保留字段1 :暂填运行商ID
		dto.setRsrvInfo2("");// 保留字段2
		dto.setSuccNum(0L);// 成功数量
		dto.setSumNum(count);// 总记录数
		dto.setTradeTypeCode((short)1002);// 业务类型编码
		dto.setUpdateTime(new Date());// 更新时间
		batchService.saveBatchData(dto);
	}
	
}
