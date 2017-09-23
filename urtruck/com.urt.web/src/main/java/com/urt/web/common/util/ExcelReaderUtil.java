package com.urt.web.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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
public class ExcelReaderUtil {
	private static final Log logger = LogFactory.getLog(ExcelReaderUtil.class);
	private POIFSFileSystem fs;
	private Workbook  wb;

	public List<Map<String,String>> importExcel(InputStream file, Boolean flag) {
		return readExcelContent(file,flag);
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param path  excel文件路径
	 * @return 
	 */
	public List<Map<String,String>> readExcelContent(InputStream file,  Boolean flag) {
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>The excel read has begun. ");
		List<Map<String, String>> content = new ArrayList<Map<String,String>>();
		try {
			if(flag){
				wb = new XSSFWorkbook(file);
			}else{
				fs = new POIFSFileSystem(file);
				wb = new HSSFWorkbook(fs);
			}
			// 得到总行数
		int numberOfSheets = wb.getNumberOfSheets();
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sheet:"+numberOfSheets);
		for (int index = 0;index < wb.getNumberOfSheets(); index++) {
			Sheet sheet =  wb.getSheetAt(index);
			int rowNum = sheet.getPhysicalNumberOfRows();
			// 正文内容应该从第二行开始,第一行为表头的标题
			if(rowNum > 0){
				Row row = sheet.getRow(0);
				int colNum = row.getPhysicalNumberOfCells();
				for (int i = 1; i < rowNum; i++) {
					int j = 0;
					row = sheet.getRow(i);
					Map<String, String> resultMap = new HashMap<String, String>();
					while (j < colNum) {
						if(j == 0){
							if(!StringUtil.isBlank(getStringCellValue(row.getCell(j))))
								resultMap.put("ICCID", getStringCellValue(row.getCell(j)).trim());
						}else if(j == 1){
							if(!StringUtil.isBlank(getStringCellValue(row.getCell(j)))){
								resultMap.put("MSISDN", getStringCellValue(row.getCell(j)).trim());
							}
						}else if(j == 2){
							if(!StringUtil.isBlank(getStringCellValue(row.getCell(j)))){
								resultMap.put("FEE", getStringCellValue(row.getCell(j)).trim());
							}
						}else if(j == 3){
							if(!StringUtil.isBlank(getStringCellValue(row.getCell(j)))){
								resultMap.put("REAL_FEE", getStringCellValue(row.getCell(j)).trim());
							}
						}else if(j == 4){
							resultMap.put("OPERATORS_PID", getStringCellValue(row.getCell(j)).trim());
						}
						
						j++;
					}
					content.add(resultMap);
					System.out.println("---"+index+"---Sheet表"+i+"行处理完毕---");
				}
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
		ExcelReaderUtil utils = new ExcelReaderUtil();
//		List<String> list = utils.importExcel("D:/exex.xlsx");
//		System.out.println(list.size());
	}
	
}
