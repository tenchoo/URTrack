package com.urt.web.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class JDBCHandlerByReport {
	private static Logger logger = LoggerFactory.getLogger(JDBCHandlerByReport.class);

	@Autowired
	ComboPooledDataSource dataSource;

	public ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Map<String,Object>> getResultMap(String sql,int indate,String[] keys,String condCols) {
		logger.info("进入方法 -------------------------------getResultMap");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		logger.info("-------------------------------------------------------创建数据库连接");
		try {
			con = dataSource.getConnection();
			logger.info("jdbcHandler---------" + sql);
			pre = con.prepareStatement(sql);// 实例化预编译语句
			if(condCols!=null){
				String [] condCol  =  condCols.split(",");
				for (int i = 0; i < condCol.length; i++) {
					pre.setObject(i+1, indate);
				}
			}
			//select a.iccid,b.cust_name,a.use_count  from lao_traffic_mm a,lao_customer b
			//where a.data_cycle > to_date(?,'yyyy-MM-dd')
			//and a.data_cycle < to_date(?,'yyyy-MM-dd')
			//and a.channel_cust_id = b.cust_id
			//group by a.iccid,b.cust_name,a.use_count
			logger.info("执行查询前 ----------------------------------");
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			logger.info("执行查询---------------------------" + result);
			while(result.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				for (int i = 0; i < keys.length; i++) {
					map.put(keys[i], result.getObject(keys[i]));
				}
				list.add(map);
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
		return list;

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
						double value2 = result.getBigDecimal("dataRemaining")
								.setScale(BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						cell.setCellValue(df.format(value2 / 1024));
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
