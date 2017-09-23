package com.urt.web.web.trafficQuery;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.common.enumeration.ConstantEnum;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.web.batchQuery.ExcelUtil;
@Controller
@RequestMapping(value = "/trafficRecord")
public class TrafficRecordController {
	Logger log = Logger.getLogger(getClass());

	@Autowired
	TrafficQueryService trafficQueryService;
	@Autowired
	TradeService tradeService;
	// 跳转到订购记录查询页面
		@RequestMapping("/recordList")
		public ModelAndView singleCardOperation(HttpServletRequest request) {
			ModelAndView mv = new ModelAndView("traffic/trafficRecord");
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession()
					.getAttribute("sessionUser");
			mv.addObject("custId", user.getCustId());
			return mv;
		}
		/**
		 * 进入查询页面 加载渠道客户名称
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/getChannelCust")
		public List<Map<String, Object>> getGroupList(HttpServletRequest request) {
			Map<String, Object> initMap = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			List<LaoCustGroupDto> dtos = trafficQueryService.selectAll();
			
			if (!ActionUtil.ifSuperUser(request)) {
				// 企业客户登录时 只显示该客户的渠道客户名称,没登陆的时候，显示联想客户的
				String custName = "";
				Long custId = (user.getCustId() == null || user.getCustId().equals("")) == true
						? ConstantEnum.CUSTID.getCode() : user.getCustId();
				Map<String, Object> initMap1 = new HashMap<String, Object>();
				for (LaoCustGroupDto dto : dtos) {
					if (custId.equals(dto.getCustId())) {
						custName = dto.getCustName();
						break;
					}
				}

				initMap1.put("text", custName);
				initMap1.put("id", user.getCustId());
				list.add(initMap1);
				return list;
			}else{
				initMap.put("text", "请选择");
				initMap.put("id", "");
				list.add(initMap);
				for (LaoCustGroupDto dto : dtos) {
					// 管理员登录时
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("text", dto.getCustName());
					map.put("id", dto.getCustId());
					list.add(map);
				}
				return list;
			}

		}
		//获取list页面
		@ResponseBody
		@RequestMapping("/getRecord")
		public Map<String, Object>getRecord(HttpServletRequest req,HttpServletResponse resp,TradeDto dto,String monthId){
			int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
			int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
			int pageNo = (pageStart / pageSize)+1;
			if(!ActionUtil.ifSuperUser(req)){
				LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
				long custId=user.getCustId();
				dto.setCustId(custId);
			
			}
			if(monthId==null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				monthId = sdf.format(new Date());
			}
			log.info("dto----="+dto.toString());
			Map<String, Object> resultMap= tradeService.queryPage(dto,pageNo,pageSize,monthId);
			log.info("resultMap---------="+resultMap.toString());
			log.info("resultMap---------="+resultMap.size());
			return resultMap;
		}
		
		@RequestMapping(value = "exportExcel")
		public String download(HttpServletRequest request, HttpServletResponse response,String monthId,Long custId) throws IOException {
			String fileName = monthId+"月订购记录查询表";
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			Map<String,Object> mapValue = new HashMap<String, Object>();
			List<Map<String, Object>> resultMap= tradeService.selectRecord(monthId, custId);
			mapValue.put("sheetName", "sheet1");
			mapList.add(mapValue);
			for (Map<String, Object> map : resultMap) {
				mapList.add(map);
			}
			try {
				String[] columnNames = { "客户", "号卡(ICCID)", "订购产品", "运营商", "数量", "缴费金额", "订购时间",
						};// 列名
				String[] keys = { "CUSTNAME", "ICCID", "GOODSNAME", "OPERATORSNAME", "COUNT", "FEE", "ORDERDATE"};// map中的key
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try {
					ExcelUtil.createWorkBook(mapList, keys, columnNames).write(os);
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
				//System.out.println("文件名转码:"+codedFileName);
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
}
