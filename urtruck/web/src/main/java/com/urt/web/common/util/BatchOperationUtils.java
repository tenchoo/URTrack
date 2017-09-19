package com.urt.web.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 利用开源组件POI2.5动态导改EXCEL文档
 * 
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类
 */
public class BatchOperationUtils {
	private static final Log logger = LogFactory.getLog(BatchOperationUtils.class);
	private POIFSFileSystem fs;
	private Workbook wb;
	private Sheet sheet;
	private Row row;

	public List<Map<String,Object>> importExcel(InputStream file, Boolean flag) {
		return readExcelContent(file,flag);
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param path  excel文件路径
	 * @return 
	 */
	public List<Map<String,Object>> readExcelContent(InputStream file,  Boolean flag) {
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>The excel read has begun. ");
		List<Map<String, Object>> content = null;
		try {
			if(flag){
				wb = new XSSFWorkbook(file);
			}else{
				fs = new POIFSFileSystem(file);
				wb = new HSSFWorkbook(fs);
			}
			
			// 得到总行数
			sheet =  wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			row = sheet.getRow(0);
			int colNum = row.getPhysicalNumberOfCells();
			// 正文内容应该从第二行开始,第一行为表头的标题
			if(rowNum > 0){
				content = new ArrayList<Map<String,Object>>();
				for (int i = 1; i <= rowNum; i++) {
					int j = 0;
					row = sheet.getRow(i);
					Map<String, Object> resultMap = new HashMap<String, Object>();
					while (j <= colNum) {
						if(j == 0){
							if(!StringUtil.isBlank(getStringCellValue(row.getCell(j))))
								resultMap.put("iccid", getStringCellValue(row.getCell(j)).trim());
						}else if(j == 1){
							if(!StringUtil.isBlank(getStringCellValue(row.getCell(j)))){
								resultMap.put("goodsId", getStringCellValue(row.getCell(j)).trim());
								resultMap.put("plan", getStringCellValue(row.getCell(j)).trim());
							}
						}
						j++;
					}
					content.add(resultMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(">>>>>>>>>>>>>>>>>>The excel read has succeed.");
		return content;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell    Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(Cell cell) {
		String strCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
			}
		}
		return strCell;
	}

	public static void main(String[] args) {
		BatchOperationUtils utils = new BatchOperationUtils();
//		List<String> list = utils.importExcel("D:/exex.xlsx");
//		System.out.println(list.size());
	}
	
}
