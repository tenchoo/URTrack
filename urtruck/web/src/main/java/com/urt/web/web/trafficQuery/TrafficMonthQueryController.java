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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.traffic.TrafficDataExportDto;
import com.urt.interfaces.User.UserService;
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
@RequestMapping("/month")
public class TrafficMonthQueryController {
	private static  final Logger log=Logger.getLogger(QueryGoodsController.class);
	private static final int POINTVALUE=10000;//超过此数据，需要提醒用户两分钟后再下载
	private static final int DATATOP=300000;//下载数据最大值，查过此数据需要提醒用户选择约束条件
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
	@Autowired
	UserService userService;
	
	
	
	public JDBCHandler getJdbcHandler() {
		return jdbcHandler;
	}
	public void setJdbcHandler(JDBCHandler jdbcHandler) {
		this.jdbcHandler = jdbcHandler;
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
	public String selectCount(HttpServletRequest request, HttpServletResponse response, TrafficDataExportDto exportDto) throws IOException {
		int countAll = jdbcHandler.getDataCount(exportDto);
//		int countAll=trafficQueryService.getCountAll(channelCustId,type,parseInt1,parseInt2,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2);
		log.info("数据库中需要下载的条数-----------"+countAll+"条------------------------");
		Map<String, String[]> propertyMap = getExcelProperty();
		String[] columnNames = propertyMap.get("columnNames");
		String[] keys = propertyMap.get("keys");
		if(countAll<POINTVALUE){
			log.info("数据量小于10000，直接下载");
			return STATUS_3;
		}else if(countAll>DATATOP){
			log.info("数据量大于200000，选择查询条件");
			return STATUS_5;
		}else{
			long taskId = getTaskUtil(request,exportDto);
			String taskState=jdbcHandler.getTaskState(taskId);
			//如果数据量不小于10000，去任务表中下载，如果任务表中没有，再去10000为单位查询并下载，而且要把数据保存到任务表中
			if("0".equals(taskState)){
				log.info("数据量较大，并且数据未曾导出过，数据开始下载到数据库");
				//开始时间,数据量，状态更新
				jdbcHandler.updateTaskState(taskId);
				new ExportThread(exportDto, columnNames, keys,taskId);
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
	 * 先连表查询，把查出的结构导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "exportFile")
	public String exportFile(HttpServletRequest request,
			HttpServletResponse response,String paramNum) throws IOException {
		/*long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		String type=request.getParameter("type");
		String month=request.getParameter("month");
		String value1=request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String operatorId = request.getParameter("operatorId");
		String goodsId = request.getParameter("goodsId");
		Integer parseInt1 = Integer.parseInt(useCount1)==-1?null:Integer.parseInt(useCount1)*1024;
		Integer parseInt2 = Integer.parseInt(useCount2)==-1?null:Integer.parseInt(useCount2)*1024;*/
		//获取要下载的数据量
		Map<String, String[]> propertyMap = getExcelProperty();
		String[] columnNames = propertyMap.get("columnNames");
		String[] keys = propertyMap.get("keys");
		
		byte[] content = null ;
		ByteArrayOutputStream os;
		try {
			log.info("数据量较少，直接从数据库查询并导出，数据开始导出-----135");
			// 满足条件的用户详细信息
			os = jdbcHandler.getResultMap(columnNames,keys,paramNum);
			log.info("success-----141");
			content = os.toByteArray();//DOTO
			log.info("success-----143");
			exportFileUtil(response, content);
		} catch (Exception e) {
			log.info("146----"+e.getMessage());
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
		String operatorId=request.getParameter("operatorId");
		String goodsId=request.getParameter("goodsId");
		long taskId = getTaskUtil(request, type,month, channelCustId, useCount1, useCount2,value1,value2,operatorId,goodsId);
		byte[] content = jdbcHandler.getBinaryData(taskId);
		
		exportFileUtil(response, content);
		return null;
		
	}
	/**
	 * 查询运营商下的商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "selectGoodsByOperator")
	public List<GoodsDto> selectGoodsByOperator(HttpServletRequest request,
			HttpServletResponse response,String operatorId) throws IOException {
		List<GoodsDto> goods=trafficQueryService.selectGoodsByOperator(operatorId);
		return goods;
		
	}
	private Map<String, String[]> getExcelProperty() {
		Map<String,String[]> map=new HashMap<String, String[]>();
/*		Map<String, Object> mapStaticName = trafficQueryService.selectStaticName(channelCustId);
		String staticNamea = "";
		String staticNameb = "";
		if (mapStaticName != null ) {
			staticNamea = (String) mapStaticName.get("STATIC_NAMEA");
			staticNameb = (String) mapStaticName.get("STATIC_NAMEB");
		}*/
		String[] columnNames = { "iccid", "服务号码", "运营商",
				"终端类型", "客户名称", "型号","卡状态" };// 列名
		String[] keys = { "iccid", "msisdn", "custname",
				"dataAdded", "staticNameA", "staticNameB", "cardStatus"};// map中的key
		map.put("columnNames", columnNames);
		map.put("keys", keys);
		return map;
	}
	
	private long getTaskUtil(HttpServletRequest request, String type,
			String month, long channelCustId, String useCount1, String useCount2, String value1, String value2, String operatorId, String goodsId) {
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		//查询lao_task表，判断是否有任务
		Long userId = user.getAcconutId();
		Long id = trafficTaskService.getTaskId(channelCustId,type,month,"-1".equals(useCount1)||"-2".equals(useCount1)?Integer.parseInt(useCount1):Integer.parseInt(useCount1)*1024,"-1".equals(useCount2)||"-2".equals(useCount2)?Integer.parseInt(useCount2):Integer.parseInt(useCount2)*1024,value1,value2,operatorId,goodsId);
		if(id==null){
			//保存任务信息
			log.info("----插入任务信息------");
			id=trafficTaskService.saveTaskInfo(userId,channelCustId,type,month,"-1".equals(useCount1)||"-2".equals(useCount1)?Integer.parseInt(useCount1):Integer.parseInt(useCount1)*1024,"-1".equals(useCount2)||"-2".equals(useCount2)?Integer.parseInt(useCount2):Integer.parseInt(useCount2)*1024,value1,value2,operatorId,goodsId);
		}
		return id;
	}
	
	/**
	 * getTaskUtil 方法重构
	 * @param request
	 * @param type
	 * @param month
	 * @param channelCustId
	 * @param useCount1
	 * @param useCount2
	 * @param value1
	 * @param value2
	 * @param operatorId
	 * @param goodsId
	 * @return
	 */
	private long getTaskUtil(HttpServletRequest request,TrafficDataExportDto exportDto) {
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		//查询lao_task表，判断是否有任务
		Long userId = user.getAcconutId();
		Long id = trafficTaskService.getTaskId(exportDto.getChannelCustId(),exportDto.getType(),exportDto.getMonth(),"-1".equals(exportDto.getUseCount1())||"-2".equals(exportDto.getUseCount1())?exportDto.getUseCount1():exportDto.getUseCount1()*1024,"-1".equals(exportDto.getUseCount2())||"-2".equals(exportDto.getUseCount2())?exportDto.getUseCount2():exportDto.getUseCount2()*1024,exportDto.getValue1(),exportDto.getValue2(),exportDto.getOperatorId(),exportDto.getGoodsId());
		if(id==null){
			//保存任务信息
			log.info("----插入任务信息------");
			id=trafficTaskService.saveTaskInfo(userId,exportDto.getChannelCustId(),exportDto.getType(),exportDto.getMonth(),"-1".equals(exportDto.getUseCount1())||"-2".equals(exportDto.getUseCount1())?exportDto.getUseCount1():exportDto.getUseCount1()*1024,"-1".equals(exportDto.getUseCount2())||"-2".equals(exportDto.getUseCount2())?exportDto.getUseCount2():exportDto.getUseCount2()*1024,exportDto.getValue1(),exportDto.getValue2(),exportDto.getOperatorId(),exportDto.getGoodsId());
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
		log.info("success-----217");
		String fileName = "本月流量使用详细情况表";
		log.info("success-----218");
		InputStream is = new ByteArrayInputStream(content);
		log.info("success-----219");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		// 进行转码，使其支持中文文件名
		String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		log.info("success-----227");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((codedFileName + ".xls").getBytes(), "utf-8"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			log.info("success-----234");
			bis = new BufferedInputStream(is);
			log.info("success-----235");
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			log.info("success-----240");
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			log.info(buff.length+"");
		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			log.info("finally");
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
		private String operatorId;
		private String goodsId;
		public ExportThread(TrafficDataExportDto exportDto,String[] columnNames,String[] keys,long taskId) {
			this.channelCustId=channelCustId;
			this.month=month;
			this.type=type;
			this.parseInt1=parseInt1;
			this.parseInt2=parseInt2;
			this.columnNames=columnNames;
			this.keys=keys;
			this.taskId=taskId;
			this.value1=value1;
			this.value2=value2;
			this.operatorId=operatorId;
			this.goodsId=goodsId;
			start();
			log.info("监听mcu状态的程序启动成功");
		}

	/*	public void run() {
			ByteArrayOutputStream os = jdbcHandler.getResultMap(channelCustId,type,month,parseInt1,parseInt2,columnNames,keys,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2,operatorId,goodsId);
			byte[]content = os.toByteArray();//DOTO
			jdbcHandler.insertBinaryutil(taskId,"2", content);
		}*/
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
	//SIM卡管理展示页面
	@ResponseBody
	@RequestMapping(value = "/queryList")
	public Map<String, Object> queryList(HttpServletRequest req,
			HttpServletResponse resp, String paramNum) {
		Long custId = null;
		if(!ActionUtil.ifSuperUser(req)){
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			  custId=user.getCustId();
		}
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
				.toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
				.toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("paramNum", paramNum);
		map.put("custId", custId);
		
		Map<String, Object> resultMap = trafficQueryService.queryPage(map,pageNo,
				pageSize);
		return resultMap;
	}
     //SIM管理销卡
	@ResponseBody
	@RequestMapping(value = "/piniccid")
	public Map<String, Object> pinCardByIccid(HttpServletRequest req,
			HttpServletResponse resp,@RequestParam(value="iccidList[]")List<String> iccidList){
			Map<String, Object> resultMap = userService.updateByIccids(iccidList);
			return resultMap;
	}
	//跳转到详情页面
	@ResponseBody
	@RequestMapping("/trafficDetails")
	public ModelAndView noticeMore(String iccid) {
		ModelAndView mv = new ModelAndView("traffic/trafficQueryMmDetails");
		Map<String, Object> resultMap = userService.selectSimDetails(iccid);
		mv.addObject("map",resultMap);
		return mv;
	}
}
