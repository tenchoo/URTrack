package com.urt.web.web.trafficQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.task.TrafficTaskService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.http.device.QueryGoodsController;
import com.urt.web.util.JDBCHandler;

/**
 * 用户流量查询
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/monthQueryByEnglish")
public class TrafficMonthQueryByEnglishController {
	private static  final Logger log=Logger.getLogger(QueryGoodsController.class);
	private static final int POINTVALUE=10000;//超过此数据，需要提醒用户两分钟后再下载
	private static final int DATATOP=200000;//下载数据最大值，查过此数据需要提醒用户选择约束条件
	private static final String STATUS_0="0";//数据量较大，第一次导出，先存入数据库，提示用户等待两分钟
	private static final String STATUS_1="1";//有相同的任务正在下载到数据库，提示用户等待两分钟
	private static final String STATUS_2="2";//数据库中有相同的任务已经完成，从数据库下载
	private static final String STATUS_3="3";//数据量较少，直接下载
	private static final String STATUS_4="4";//系统繁忙
	private static final String STATUS_5="5";//数据量过大，请选择查询条件
	@Autowired
	TrafficQueryService trafficQueryService;
	@Autowired
	TrafficTaskService  trafficTaskService;
	@Autowired
	JDBCHandler jdbcHandler;
	
	
	public JDBCHandler getJdbcHandler() {
		return jdbcHandler;
	}
	public void setJdbcHandler(JDBCHandler jdbcHandler) {
		this.jdbcHandler = jdbcHandler;
	}
	@RequestMapping(value = "/toQueryMmView")
	public ModelAndView toQuery2View(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/traffic/trafficQueryMmByEnglish");
		Map<String,String> info = trafficQueryService.getUpdateTimeInfo();
		mv.addObject("info", info);
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
		initMap.put("text", "Select");
		initMap.put("id", -1);
		list.add(initMap);
		if (!ActionUtil.ifSuperUser(request)) {
			// 企业客户登录时 只显示该客户的渠道客户名称
			String custName = "";
			for (LaoCustGroupDto dto : dtos) {
				long custId = dto.getCustId();
				if (custId == user.getCustId()) {
					custName = dto.getCustName();
				}
			}
			Map<String, Object> initMap1 = new HashMap<String, Object>();
			initMap1.put("text", custName);
			initMap1.put("id", user.getCustId());
			list.add(initMap1);
			return list;
		}

		for (LaoCustGroupDto dto : dtos) {
			// 管理员登录时
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getCustName());
			map.put("id", dto.getCustId());
			list.add(map);
		}
		return list;
	}
	/**
	 * 获取要下载的数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "selectBydynamic")
	public String selectCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		String type=request.getParameter("type");
		String month=request.getParameter("month");
		String value1=request.getParameter("value1");
		String value2 = request.getParameter("value2");
		Integer parseInt1 = Integer.parseInt(useCount1)==-1?null:Integer.parseInt(useCount1)*1024;
		Integer parseInt2 = Integer.parseInt(useCount2)==-1?null:Integer.parseInt(useCount2)*1024;
		int countAll = jdbcHandler.getDataCount(channelCustId,type,month,parseInt1,parseInt2,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2,"","");
		Map<String, String[]> propertyMap = getExcelProperty(channelCustId);
		String[] columnNames = propertyMap.get("columnNames");
		String[] keys = propertyMap.get("keys");
		if(countAll<POINTVALUE){
			return STATUS_3;
		}else if(countAll>DATATOP){
			return STATUS_5;
		}else{
			long taskId = getTaskUtil(request, type,month, channelCustId, useCount1, useCount2,value1,value2);
			String taskState=jdbcHandler.getTaskState(taskId);
			//如果数据量不小于10000，去任务表中下载，如果任务表中没有，再去10000为单位查询并下载，而且要把数据保存到任务表中
			if("0".equals(taskState)){
				log.info("数据量较大，并且数据未曾导出过，数据开始下载到数据库");
				//开始时间,数据量，状态更新
				jdbcHandler.updateTaskState(taskId);
				new ExportThread(channelCustId, type,month, parseInt1, parseInt2, columnNames, keys,taskId,value1,value2);
				return STATUS_0;
			}else if("1".equals(taskState)){
				return STATUS_1;
			}else if("2".equals(taskState)){
				log.info("数据库中有相同的任务已经完成，从数据库下载");
				return STATUS_2;
			}
		}
		return STATUS_4;
	}
	/**
	 * 导出本月流量使用详细情况表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "exportFile")
	public String exportFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		String type=request.getParameter("type");
		String month=request.getParameter("month");
		String value1=request.getParameter("value1");
		String value2 = request.getParameter("value2");
		Integer parseInt1 = Integer.parseInt(useCount1)==-1?null:Integer.parseInt(useCount1)*1024;
		Integer parseInt2 = Integer.parseInt(useCount2)==-1?null:Integer.parseInt(useCount2)*1024;
		//获取要下载的数据量
		Map<String, String[]> propertyMap = getExcelProperty(channelCustId);
		String[] columnNames = propertyMap.get("columnNames");
		String[] keys = propertyMap.get("keys");
		
		byte[] content = null ;
		ByteArrayOutputStream os;
		try {
			log.info("数据量较少，直接从数据库查询并导出，数据开始导出");
			// 满足条件的用户详细信息
			os = jdbcHandler.getResultMap(channelCustId,type,month,parseInt1,parseInt2,columnNames,keys,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2,"","");
			content = os.toByteArray();//DOTO
			exportFileUtil(response, content);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return null;
		
	}
	/**
	 * 从任务表中导出本月流量使用详细情况表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "exportFileFromDB")
	public String exportFileFromDB(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		String type=request.getParameter("type");
		String month=request.getParameter("month");
		String value1=request.getParameter("value1");
		String value2 = request.getParameter("value2");
		long taskId = getTaskUtil(request, type, month,channelCustId, useCount1, useCount2,value1,value2);
		byte[] content = jdbcHandler.getBinaryData(taskId);
		
		exportFileUtil(response, content);
		return null;
		
	}
	private Map<String, String[]> getExcelProperty(long channelCustId) {
		Map<String,String[]> map=new HashMap<String, String[]>();
		Map<String, Object> mapStaticName = trafficQueryService.selectStaticName(channelCustId);
		String staticNamea = "";
		String staticNameb = "";
		if (mapStaticName != null ) {
			staticNamea = (String) mapStaticName.get("STATIC_NAMEA");
			staticNameb = (String) mapStaticName.get("STATIC_NAMEB");
		}
		String[] columnNames = { "批次流水号", "iccid", "服务号码", "已使用流量截止日期",
				"已使用流量(MB)", "查询时间", "截止查询时间剩余流量(MB)",staticNamea,staticNameb,"终端类型","终端号","激活时间","到期时间","卡状态","imsi号" };// 列名
		String[] keys = { "batchId", "iccid", "msisdn", "dataAdded",
				"useCount", "updateTime", "dataRemaining", "staticNameA",
				"staticNameB","type" ,"imei","activeDate","endDate","cardStatus","imsi"};// map中的key
		map.put("columnNames", columnNames);
		map.put("keys", keys);
		return map;
	}
	
	private long getTaskUtil(HttpServletRequest request, String type,
			String month, long channelCustId, String useCount1, String useCount2, String value1, String value2) {
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		//查询lao_task表，判断是否有任务
		Long userId = user.getAcconutId();
		Long id = trafficTaskService.getTaskId(channelCustId,type,month,"-1".equals(useCount1)||"-2".equals(useCount1)?Integer.parseInt(useCount1):Integer.parseInt(useCount1)*1024,"-1".equals(useCount2)||"-2".equals(useCount2)?Integer.parseInt(useCount2):Integer.parseInt(useCount2)*1024,value1,value2,"","");
		if(id==null){
			//保存任务信息
			id=trafficTaskService.saveTaskInfo(userId,channelCustId,type,month,"-1".equals(useCount1)||"-2".equals(useCount1)?Integer.parseInt(useCount1):Integer.parseInt(useCount1)*1024,"-1".equals(useCount2)||"-2".equals(useCount2)?Integer.parseInt(useCount2):Integer.parseInt(useCount2)*1024,value1,value2,"","");
		}
		return id;
	}
	
	/**
     * 把excel文件流写出到客户端
     * @param response
     * @param content
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
	private void exportFileUtil(HttpServletResponse response, byte[] content)
			throws UnsupportedEncodingException, IOException {
		String fileName = "本月流量使用详细情况表";
		InputStream is = new ByteArrayInputStream(content);
		
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		// 进行转码，使其支持中文文件名
		String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//				System.out.println("文件名转码:"+codedFileName);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((codedFileName + ".xls").getBytes(), "utf-8"));
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
	}
	/**
	 * 保存excel二进制如任务表
	 * @author Administrator
	 *
	 */
	class ExportThread extends Thread {
		private long channelCustId ;
		private String type;
		private String month;
		private Integer parseInt1 ;
		private Integer parseInt2 ;
		private String[] columnNames;
		private String[] keys;
		private long taskId;
		private String value1;
		private String value2;
		public ExportThread(long channelCustId,String type,String month,Integer parseInt1,Integer parseInt2,String[] columnNames,String[] keys,long taskId, String value1, String value2) {
			this.channelCustId=channelCustId;
			this.type=type;
			this.month=month;
			this.parseInt1=parseInt1;
			this.parseInt2=parseInt2;
			this.columnNames=columnNames;
			this.keys=keys;
			this.taskId=taskId;
			this.value1=value1;
			this.value2=value2;
			start();
			log.info("监听mcu状态的程序启动成功");
		}

		public void run() {
			ByteArrayOutputStream os = jdbcHandler.getResultMap(channelCustId,type,month,parseInt1,parseInt2,columnNames,keys,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2,"","");
			byte[]content = os.toByteArray();//DOTO
			jdbcHandler.insertBinaryutil(taskId,"2", content);
		}
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//1 Locale类  本地,当地(语言-国家). 封装了字符串=> zh_cn
		
		Locale china = Locale.US;
		System.out.println(china);
		
		//2 Properties 类 => 读取properties配置文件的.
		Properties prop = new Properties();
		
		prop.load(new FileInputStream("src/main/resources/i18n/i18n.properties"));
		
		String name = prop.getProperty("name");
		System.out.println(name);//tom
//		
		ResourceBundle rb = ResourceBundle.getBundle("i18n.i18n",china );
		
		String zhName = rb.getString("name");
		System.out.println(zhName);//zhTom
		
	}
}
