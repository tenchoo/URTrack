package com.urt.web.util;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.urt.dto.task.LaoTrafficMMTaskDto;
import com.urt.dto.traffic.TrafficDataExportDto;
import com.urt.web.common.util.StringUtil;

public class JDBCHandler {
	private static Logger logger = LoggerFactory.getLogger(JDBCHandler.class);

	@Autowired
	ComboPooledDataSource dataSource;

	public ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * queryResultCountSQL 方法重构
	 */
	public ByteArrayOutputStream getResultMap(
			String[] columnNames, String[] keys,String paramNum) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			con = dataSource.getConnection();
			String sql = queryResultMapSQL(paramNum);
			logger.info("jdbcHandler---------" + sql);
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			logger.info("result---rows" + result.getRow());
			createWorkBook(result, keys, columnNames).write(os);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return os;

	}
	
	public ByteArrayOutputStream getResultMap(long channelCustId, String type, String month, Integer parseInt1, Integer parseInt2, String[] columnNames, String[] keys, String value1, String value2, String operatorId, String goodsId){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
	    Connection con = null;// 创建一个数据库连接
	    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
	    try
	    { 
	    	con = dataSource.getConnection();
	        String sql = queryResultMapSQL(channelCustId,type, month,parseInt1, parseInt2,value1,value2,operatorId,goodsId);
	        logger.info("jdbcHandler---------"+sql);
	        pre = con.prepareStatement(sql);// 实例化预编译语句
	        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
	        logger.info("result---rows"+result.getRow());
	        createWorkBook(result, keys, columnNames).write(os);
	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
	            // 注意关闭的顺序，最后使用的最先关闭
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if(con!=null)
	            	con.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
		return os;
	   
	}

	public byte[] getBinaryData(long taskId) {
		byte[] binaryData = null;
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			con = dataSource.getConnection();
			String sql = " select BINARY_DATA from lao_trafficmm_task where ID =?";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setLong(1, taskId);
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				binaryData = result.getBytes("BINARY_DATA");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return binaryData;
	}

	public void updateTaskState(long taskId) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		try {
			con = dataSource.getConnection();
			String sql = "update lao_trafficmm_task set TASK_STATE=1,START_TIME=sysdate where ID =?";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setLong(1, taskId);
			pre.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getTaskState(long taskId) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		String taskStatu = "";
		try {
			con = dataSource.getConnection();
			String sql = " select TASK_STATE from lao_trafficmm_task where ID =? and to_char(create_time,'yyyymmdd')=to_char(sysdate,'yyyymmdd')";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setLong(1, taskId);
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				taskStatu = result.getString("TASK_STATE");
				logger.info("月流量查询下载任务状态：" + taskStatu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return taskStatu;
	}

	public LaoTrafficMMTaskDto getLaoTaskDto(long taskId) {
		LaoTrafficMMTaskDto laoTask = new LaoTrafficMMTaskDto();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			con = dataSource.getConnection();
			String sql = " select CHANNEL_CUST_ID, DATA1, DATA2, OPER_ID, TASK_STATE, START_TIME, END_TIME, REMARK,TYPE, CREATE_TIME, COUNT,BINARY_DATA from lao_trafficmm_task where ID =?";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setLong(1, taskId);
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				laoTask.setId(taskId);
				laoTask.setChannelCustId(result.getLong("CHANNEL_CUST_ID"));
				laoTask.setData1(result.getBigDecimal("DATA1"));
				laoTask.setData2(result.getBigDecimal("DATA2"));
				laoTask.setOperId(result.getLong("OPER_ID"));
				laoTask.setTaskState(result.getString("TASK_STATE"));
				laoTask.setStartTime(result.getDate("START_TIME"));
				laoTask.setEndTime(result.getDate("END_TIME"));
				laoTask.setRemark(result.getString("REMARK"));
				laoTask.setType(result.getString("TYPE"));
				laoTask.setCreateTime(result.getDate("CREATE_TIME"));
				laoTask.setCount(result.getInt("COUNT"));
				laoTask.setBinaryData(result.getBytes("BINARY_DATA"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return laoTask;
	}
	/**
	 * queryResultCountSQL 方法重构
	 * @return
	 */
	// 根据条件查询结果集
	public String queryResultMapSQL(String paramNum) {
		// String dataCycle = new SimpleDateFormat("yyyyMM").format(new Date());

		StringBuffer buffer = new StringBuffer();
		String sql = "select "
				+ " a.iccid iccid,"
				+ " a.msisdn msisdn,"
				+ " lc.cust_name custname,"
				+ " l.operators_name dataAdded,"
				+ " c.static_name staticNameA,"
				+ " d.static_name staticNameB,"
				+ " j.static_name cardStatus"
				+ " from Lao_User a,"
				+ " lao_operators l,"
				+ " lao_ss_static c,"
				+ " lao_ss_static d,"
				+ " lao_service_status f,"
				+ " lao_customer lc,"
				+ " lao_ss_static j "
				+ "  where (a.channel_cust_id = c.cust_id or c.cust_id=-1) "
				+ " and (a.channel_cust_id = d.cust_id   or c.cust_id=-1) "
				+ " and a.value1 = c.static_code "
				+ " and a.value2 = d.static_code "
				+ " and a.state_code = f.state_code "
				+ " and f.outsidestate=j.static_code "
				+ " and j.tab_name='LAO_SERVICE_STATUS' "
				+ " and j.col_name='OUTSIDESTATE' "
				+ " and a.operators_id=l.operators_id "
				+ " and a.channel_cust_id = lc.cust_id";
				

		buffer.append(sql);
		if (paramNum != null && !"".equals(paramNum)) {
			buffer.append(" and (a.iccid = " + paramNum + " or a.msisdn = " + paramNum + ")");
			
		}
		/*
		if (value2 != null && !"".equals(value2)) {
			buffer.append(" and a.value2 = " + value2);
		}
		if (operatorId != null && operatorId != "") {
			buffer.append(" and b.operators_id =to_number(" + operatorId + ")");
		}
		if (goodsId != null && goodsId != "") {
			buffer.append(" b.iccid in(select iccid from lao_trade lt where to_char(lt.accept_date,'yyyyMM')=b.data_cycle and lt.goods_id=to_number("
					+ goodsId + ")");
		}
		if (parseInt1 == null && parseInt2 == null)
			return buffer.toString();
		if ("1".equals(type)) {
			if (parseInt1 == -2) {
				buffer.append(" and b.USE_COUNT  >= " + 5120 * 1024);
			} else {
				buffer.append(" and b.USE_COUNT  >= " + parseInt1
						+ " and b.USE_COUNT  <=" + parseInt2);
			}
		} else {
			if (parseInt1 == -2) {
				buffer.append(" and b.DATA_REMAINING  >= " + 5120 * 1024);
			} else {
				buffer.append(" and b.DATA_REMAINING  >= " + parseInt1
						+ " and b.USE_COUNT  <=" + parseInt2);
			}
		}
*/
		return buffer.toString();
	}
	
	
	//根据条件查询结果集
		public String queryResultMapSQL(long channelCustId, String type, String month, Integer parseInt1, Integer parseInt2, String value1, String value2, String operatorId, String goodsId){
//			String dataCycle = new SimpleDateFormat("yyyyMM").format(new Date());
			
			StringBuffer buffer=new StringBuffer();
			String sql="select "
					        + "b.batch_id batchId,"
					 		+ "b.iccid iccid,"
					 		+ "b.msisdn msisdn,"
					 		+ "l.operators_name dataAdded,"
					 		+ "b.use_count useCount,"
					 		+ "to_char(b.update_time,'yyyy-MM-dd HH:mm:SS') updateTime,"
					 		+ "b.data_remaining dataRemaining, "
					 		+ "c.static_name staticNameA,"
					 		+ "d.static_name staticNameB, "
					 		+ "(select ug.start_use_date from lao_user_goods ug,lao_goods g where ug.user_id=a.user_id and ug.goods_id=g.goods_id and g.goods_type='0' and rownum <2) activeDate,"
					 		+ "j.static_name cardStatus,"
					 		+ "g.device_id imei,"
					 		+ "h.device_id imsi "
		        		+ "from Lao_User a,"
		        		+ "LAO_TRAFFIC_MM b,"
		        		+ "lao_operators l,"
		        		+ "lao_ss_static c,"
		        		+ "lao_ss_static d,"
		        		+ "lao_service_status f,"
		        		+ "(select * from lao_device_rel k where k.id_type='IMEI' and k.valid_tag='1') g,"
		        		+ "(select * from lao_device_rel k where k.id_type='IMSI' and k.valid_tag='1') h,"
		        		+ "lao_ss_static j "
		        		+ "where (a.channel_cust_id = c.cust_id or c.cust_id=-1) "
		        		+ "and (a.channel_cust_id = d.cust_id   or c.cust_id=-1) "
		        		+ "and a.value1 = c.static_code "
		        		+ "and a.value2 = d.static_code "
		        		+ "and a.user_id = b.user_id "
		        		+ "and a.channel_cust_id=b.channel_cust_id "
		        		+ "and a.state_code = f.state_code "
		        		+ "and a.iccid=g.iccid(+) "
		        		+ "and a.iccid=h.iccid(+) "
		        		+ "and f.outsidestate=j.static_code "
		        		+ "and j.tab_name='LAO_SERVICE_STATUS' "
		        		+ "and j.col_name='OUTSIDESTATE'	 "
		        		+ "and b.operators_id=l.operators_id "
		        		+ "and b.channel_cust_id="+channelCustId
		        		+ " and b.DATA_CYCLE =  '"+month+"'";
		        		
			 buffer.append(sql);
			 if(value1!=null&&!"".equals(value1)){
				 buffer.append(" and a.value1 = "+value1) ;
			 }
			 if(value2!=null&&!"".equals(value2)){
				 buffer.append(" and a.value2 = "+value2) ;
			 }
			 if(operatorId!=null&&operatorId!=""){
				 buffer.append(" and b.operators_id =to_number("+operatorId+")") ;
			 }
			 if(goodsId!=null&&goodsId!=""){
				 buffer.append(" b.iccid in(select iccid from lao_trade lt where to_char(lt.accept_date,'yyyyMM')=b.data_cycle and lt.goods_id=to_number("+goodsId+")") ;
			 }
			 if(parseInt1==null&&parseInt2==null)
				 return buffer.toString();
			 if("1".equals(type)){
				 if(parseInt1==-2){
					 buffer.append(" and b.USE_COUNT  >= "+5120*1024) ;
				 }else{
					 buffer.append(" and b.USE_COUNT  >= "+parseInt1+" and b.USE_COUNT  <="+parseInt2);
				 }
			 }else{
				 if(parseInt1==-2){
					 buffer.append(" and b.DATA_REMAINING  >= "+5120*1024) ;
				 }else{
					 buffer.append(" and b.DATA_REMAINING  >= "+parseInt1+" and b.USE_COUNT  <="+parseInt2);
				 }
			 }
			 
			 return buffer.toString();
		}

	/**
	 * queryResultCountSQL 方法重构
	 * 
	 * @param exportDataDto
	 * @return
	 */
	public String queryResultCountSQL(TrafficDataExportDto exportDataDto) {
		StringBuffer buffer = new StringBuffer();
		String sql = "select count(1) from (select "
				+ " a.iccid iccid,"
				+ " a.msisdn msisdn,"
				+ " lc.cust_name custname,"
				+ " l.operators_name dataAdded,"
				+ " c.static_name staticNameA,"
				+ " d.static_name staticNameB,"
				+ " j.static_name cardStatus"
				+ " from Lao_User a,"
				+ " lao_operators l,"
				+ " lao_ss_static c,"
				+ " lao_ss_static d,"
				+ " lao_service_status f,"
				+ " lao_customer lc,"
				+ " lao_ss_static j "
				+ "  where (a.channel_cust_id = c.cust_id or c.cust_id=-1) "
				+ " and (a.channel_cust_id = d.cust_id   or c.cust_id=-1) "
				+ " and a.value1 = c.static_code "
				+ " and a.value2 = d.static_code "
				+ " and a.state_code = f.state_code "
				+ " and f.outsidestate=j.static_code "
				+ " and j.tab_name='LAO_SERVICE_STATUS' "
				+ " and j.col_name='OUTSIDESTATE' "
				+ " and a.operators_id=l.operators_id "
				+ " and a.channel_cust_id = lc.cust_id)";

		buffer.append(sql);
		/*if (!StringUtil.isBlank(exportDataDto.getValue1())) {
			buffer.append(" and lu.value1 = " + exportDataDto.getValue1());
		}
		if (!StringUtil.isBlank(exportDataDto.getValue2())) {
			buffer.append(" and lu.value2 = " + exportDataDto.getValue2());
		}
		if (!StringUtil.isBlank(exportDataDto.getOperatorId())) {
			buffer.append(" and ltm.operators_id =to_number("
					+ exportDataDto.getOperatorId() + ")");
		}
		if (!StringUtil.isBlank(exportDataDto.getGoodsId())) {
			buffer.append(" ltm.iccid in(select iccid from lao_trade lt where to_char(lt.accept_date,'yyyyMM')=ltm.data_cycle and lt.goods_id=to_number("
					+ exportDataDto.getGoodsId() + ")");
		}
		if (exportDataDto.getUseCount1() != null
				&& exportDataDto.getUseCount2() != null) {
			if ("1".equals(exportDataDto.getType())) {
				if (exportDataDto.getUseCount1() == -2) {
					buffer.append("and ltm.USE_COUNT  >= " + 5120 * 1024);
				} else {
					buffer.append("and ltm.USE_COUNT  >= "
							+ exportDataDto.getUseCount1()
							+ " and ltm.USE_COUNT  <="
							+ exportDataDto.getUseCount2());
				}
			} else {
				if (exportDataDto.getUseCount1() == -2) {
					buffer.append("and ltm.DATA_REMAINING  >= " + 5120 * 1024);
				} else {
					buffer.append("and ltm.DATA_REMAINING  >= "
							+ exportDataDto.getUseCount1()
							+ " and ltm.USE_COUNT  <="
							+ exportDataDto.getUseCount2());
				}
			}
		}*/
		return buffer.toString();
	}

	// 根据条件查询要下载数据的数量
	public String queryResultCountSQL(long channelCustId, String type,
			String month, Integer parseInt1, Integer parseInt2, String value1,
			String value2, String operatorId, String goodsId) {
		StringBuffer buffer = new StringBuffer();
		String sql = "select count(1) sum "
				+ "from lao_traffic_mm ltm,lao_user lu  "
				+ "where ltm.user_id=lu.user_id "
				+ "and ltm.channel_cust_id in(SELECT CUST_ID FROM lao_customer START WITH CUST_ID="
				+ channelCustId + " CONNECT BY PRIOR cust_id=dev_cust) "
				+ "and ltm.data_cycle=" + month;

		buffer.append(sql);
		if (value1 != null) {
			buffer.append(" and lu.value1 = " + value1);
		}
		if (value2 != null && value2 != "") {
			buffer.append(" and lu.value2 = " + value2);
		}
		if (operatorId != null && operatorId != "") {
			buffer.append(" and ltm.operators_id =to_number(" + operatorId
					+ ")");
		}
		if (goodsId != null && goodsId != "") {
			buffer.append(" ltm.iccid in(select iccid from lao_trade lt where to_char(lt.accept_date,'yyyyMM')=ltm.data_cycle and lt.goods_id=to_number("
					+ goodsId + ")");
		}
		if (parseInt1 != null && parseInt2 != null) {
			if ("1".equals(type)) {
				if (parseInt1 == -2) {
					buffer.append("and ltm.USE_COUNT  >= " + 5120 * 1024);
				} else {
					buffer.append("and ltm.USE_COUNT  >= " + parseInt1
							+ " and ltm.USE_COUNT  <=" + parseInt2);
				}
			} else {
				if (parseInt1 == -2) {
					buffer.append("and ltm.DATA_REMAINING  >= " + 5120 * 1024);
				} else {
					buffer.append("and ltm.DATA_REMAINING  >= " + parseInt1
							+ " and ltm.USE_COUNT  <=" + parseInt2);
				}
			}
		}
		return buffer.toString();
	}

	public int insertBinaryutil(long taskId, String taskState, byte[] content) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		int row = 0;
		try {
			logger.info("数据库获得连接前《《《《《《《《《《《《《《《《《《");
			con = dataSource.getConnection();
			logger.info("数据库获得连接后》》》》》》》》》》》》》》》》》》》");

			String sql = "update lao_trafficmm_task set task_state=? , end_time=sysdate , binary_data=? where id=?";

			logger.info(sql);
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, taskState);
			pre.setBytes(2, content);
			pre.setLong(3, taskId);
			row = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return row;
	}

	public int getDataCount(long channelCustId, String type, String month,
			Integer parseInt1, Integer parseInt2, String value1, String value2,
			String operatorId, String goodsId) {
		int countAll = 0;
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			logger.info("数据库获得连接前《《《《《《《《《《《《《《《《《《");
			con = dataSource.getConnection();
			logger.info("数据库获得连接后》》》》》》》》》》》》》》》》》》》");

			logger.info("value1:=====" + value1 + "=========value2:======"
					+ value2);
			String sql = queryResultCountSQL(channelCustId, type, month,
					parseInt1, parseInt2, value1, value2, operatorId, goodsId);
			logger.info("getCountAll---------->>>:" + sql + "<<<<<<<<<");
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				countAll = result.getInt("sum");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return countAll;
	}

	/**
	 * getDataCount 方法重构
	 * 
	 * @param channelCustId
	 * @param type
	 * @param month
	 * @param parseInt1
	 * @param parseInt2
	 * @param value1
	 * @param value2
	 * @param operatorId
	 * @param goodsId
	 * @return
	 */
	public int getDataCount(TrafficDataExportDto exportDto) {
		int countAll = 0;
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			logger.info("数据库获得连接前《《《《《《《《《《《《《《《《《《");
			con = dataSource.getConnection();
			logger.info("数据库获得连接后》》》》》》》》》》》》》》》》》》》");

			logger.info("value1:=====" + exportDto.getValue1()
					+ "=========value2:======" + exportDto.getValue2());
			String sql = queryResultCountSQL(exportDto);
			logger.info("getCountAll---------->>>:" + sql + "<<<<<<<<<");
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				countAll = result.getInt("sum");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return countAll;
	}

	/**
	 * jdbc 创建excel文档， [@param](http://my.oschina.net/u/2303379) list 数据
	 * 
	 * @param keys
	 *            list中map的key数组集合
	 * @param columnNames
	 *            excel的列名
	 * @throws SQLException
	 * */
	public Workbook createWorkBook(ResultSet result, String[] keys,
			String columnNames[]) throws SQLException {
		DecimalFormat df = new DecimalFormat("######0.00");
		// 创建excel工作簿
		// Workbook wb = new HSSFWorkbook();
		Workbook wb = new XSSFWorkbook();
		logger.info("jdbchandler  creste>>>>>>>>>>>>>>417");
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet("sheet1");
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		Row row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// Font f3=wb.createFont();
		// f3.setFontHeightInPoints((short) 10);
		// f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		int i = 1;
		int a = 1;
		try {
			while (result.next()) {
				a++;
				logger.info("already create rows" + a);
				// for(int k=0;k<50;k++){
				// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
				// 创建一行，在页sheet上
				Row row1 = sheet.createRow(i);
				// 在row行上创建一个方格
				for (short j = 0; j < keys.length; j++) {
					Cell cell = row1.createCell(j);
					String key = keys[j];
					if ("useCount".equals(key)) {
						double value1 = result.getBigDecimal("useCount")
								.setScale(BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						cell.setCellValue(df.format(value1 / 1024));
					} else if ("dataRemaining".equals(key)) {
						BigDecimal remainFlow = result
								.getBigDecimal("dataRemaining");
						if (remainFlow != null && remainFlow.intValue() >= 0) {
							double value2 = result
									.getBigDecimal("dataRemaining")
									.setScale(BigDecimal.ROUND_HALF_UP)
									.doubleValue();
							cell.setCellValue(df.format(value2 / 1024));
						} else {
							cell.setCellValue("--");
						}
					} else {
						String value = result.getString(key);
						cell.setCellValue(value);
					}
					cell.setCellStyle(cs2);
				}

				i++;
				// }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wb;
	}

}
