package com.urt.web.common.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.urt.dto.IccidLibDto;

/**
 * 利用开源组件POI2.5动态导改EXCEL文档
 * 
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类
 */
public class ImportExcelUtils<T> {
	private static final Log logger = LogFactory.getLog(ImportExcelUtils.class);
	private POIFSFileSystem fs;
	private Workbook wb;
	private Sheet sheet;
	private Row row;
	private XSSFWorkbook xb;

	public List<IccidLibDto> importExcel(InputStream file, IccidLibDto dto, Boolean flag) {
		return readExcelContent(file,dto ,flag);
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param path  excel文件路径
	 * @return 
	 */
	public List<IccidLibDto> readExcelContent(InputStream file, IccidLibDto dto,  Boolean flag) {
		logger.debug("The excel read has begun. ");
		List<IccidLibDto> content = new ArrayList<IccidLibDto>();
		try {
			if(flag){
				wb = new XSSFWorkbook(file);
			}else{
				fs = new POIFSFileSystem(file);
				wb = new HSSFWorkbook(fs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sheet =  wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			IccidLibDto t = new IccidLibDto();
			int j = 0;
			while (j <= colNum) {
				if(j == 0){
					if(!StringUtil.isBlank(getStringCellValue(row.getCell(j))))
					t.setIccid(getStringCellValue(row.getCell(j)).trim());
				}else if(j == 1){
					t.setMsisdn(getStringCellValue(row.getCell(j)).trim());
				}
				j++;
			}
			t.setCustid(dto.getCustid());
			t.setValue1(dto.getValue1());
			t.setValue2(dto.getValue2());
			t.setDevicetype(dto.getValue1());
			t.setCardtype(dto.getValue2());
			t.setOperators(dto.getOperators());
			t.setInitproduct(dto.getInitproduct());
			t.setAttribute1("type");
			t.setAttribute2("version");
			t.setCardstatus(dto.getCardstatus());
			t.setIfMaintenance(dto.getIfMaintenance());
			
			t.setPrivatekey("no data");
			t.setCtype("no data");
			content.add(t);
		}
		logger.debug("The excel read has succeed.");
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

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell Excel单元格
	 * @return
	 */
	private String getDateCellValue(Cell cell) {
		String result = "";
		if (cell != null) {
			try {
				int cellType = cell.getCellType();
				if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
					Date date = cell.getDateCellValue();
					result = GregorianCalendar.YEAR + "-" + (GregorianCalendar.MONTH + 1) + "-" + GregorianCalendar.DAY_OF_MONTH;
				} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
					String date = getStringCellValue(cell);
					result = date.replaceAll("[年月]", "-").replace("日", "").trim();
				} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
					result = "";
				}
			} catch (Exception e) {
				logger.equals("日期格式不正确!");
				e.printStackTrace();
			}
		}
		return result;
	}
    public Map<String, List<String>> readInfoContent(InputStream file,  Boolean flag) {
        logger.debug("The excel read has begun. ");
        //List<Map<String, Object>> tMap = new ArrayList<Map<String, Object>>();
        Map<String, List<String>> tMap= new HashMap<String, List<String>>();
        List<String> listIccid =new ArrayList<String>();
        List<String> listMsison =new ArrayList<String>();
        List<String> listPsptId =new ArrayList<String>();
        List<String> listPspCode =new ArrayList<String>();
        try {
            if(flag){
                wb = new XSSFWorkbook(file);
            }else{
                fs = new POIFSFileSystem(file);
                wb = new HSSFWorkbook(fs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sheet =  wb.getSheetAt(0);
        // å¾—åˆ°æ€»è¡Œæ•°
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        // æ­£æ–‡å†…å®¹åº”è¯¥ä»Žç¬¬äºŒè¡Œå¼€å§‹,ç¬¬ä¸€è¡Œä¸ºè¡¨å¤´çš„æ ‡é¢˜
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            
            if((StringUtil.isBlank(getStringCellValue(row.getCell(0))) && StringUtil.isBlank(getStringCellValue(row.getCell(1))))
                    ||StringUtil.isBlank(getStringCellValue(row.getCell(2))) || StringUtil.isBlank(getStringCellValue(row.getCell(3))) ){
                logger.info("readInfoContent The excel read has empty cell.");
                return null;
            }else{
                listIccid.add(getStringCellValue(row.getCell(0)));
                listMsison.add(getStringCellValue(row.getCell(1)));
                listPsptId.add(getStringCellValue(row.getCell(2)));
                listPspCode.add(getStringCellValue(row.getCell(3)));
                
            }
        }
        tMap.put("psptId", listPsptId);
        tMap.put("psptTypeCode",listPspCode);
        tMap.put("iccId", listIccid);
        tMap.put("msisonId", listMsison);
        logger.debug("The excel read has succeed.");
        return tMap;
    }
	public static void main(String[] args) {
		ImportExcelUtils<IccidLibDto> utils = new ImportExcelUtils<IccidLibDto>();
//		List<IccidLibDto> list = utils.importExcel("D:/exex.xlsx");
//		System.out.println(list.size());
	}
	
}
