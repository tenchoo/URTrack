package com.urt.web.common.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.dto.IccidBatchdataDto;

public class ExcelUtilsImportNew {

	Logger log = Logger.getLogger(getClass());
	
	@SuppressWarnings("resource")
	public IccidBatchdataDto getIccidBatchdataDto(InputStream inps, Boolean flag){
		IccidBatchdataDto batchdataDto = new IccidBatchdataDto();
		Workbook workbook = null;
		try {
			if(flag){
				workbook = new XSSFWorkbook(inps);
			}else{
				POIFSFileSystem fs = new POIFSFileSystem(inps);
				workbook = new HSSFWorkbook(fs);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Sheet sheet = workbook.getSheetAt(1);//第二个sheet页
		if (sheet == null) {
			System.out.println("所传Excel内容为空！");
			log.info("所传Excel内容为空！");
			return null;
		}
		try {
			Row row = sheet.getRow(2);
			Cell cell = row.getCell(1);
			if (!StringUtils.isBlank(this.getCellValue(cell))) {
				String cellStr = this.getCellValue(cell);
				BigDecimal big = new BigDecimal(cellStr);
				batchdataDto.setSumNum(big.longValue());
			}
			//==============================================
			row = sheet.getRow(2);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setEnterpriseConsignee(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(3);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setOrderFee(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(5);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setConsigneePhone(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(3);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setConsigneeIdcard(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(4);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setIndustryCategory(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(4);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setIndustrySegmentation(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(6);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setDeliveryAddress(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(7);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setRemark(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(5);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setOrderDate(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(9);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setSimType(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(9);
			cell = row.getCell(3);
			if (cell != null) {
				batchdataDto.setSimSize(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(9);
			cell = row.getCell(5);
			if (cell != null) {
				batchdataDto.setSimFee(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(10);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setIccidStart(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(10);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setIccidEnd(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(13);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setNumberStart(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(13);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setNumberEnd(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(15);
			cell = row.getCell(1);
			if (!StringUtils.isBlank(this.getCellValue(cell))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				batchdataDto.setDeliveryDate(sdf.parse(this.getCellValue(cell)));
			}
			//==============================================
			row = sheet.getRow(15);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setConsignor(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(16);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setLogisticsCompany(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(16);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setLogisticsNumber(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(17);
			cell = row.getCell(1);
			if (cell != null) {
				batchdataDto.setPullPerson(this.getCellValue(cell));
			}
			//==============================================
			row = sheet.getRow(17);
			cell = row.getCell(4);
			if (cell != null) {
				batchdataDto.setPullNumber(this.getCellValue(cell));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Excel填入信息不正确！");
			System.out.println("Excel填入信息不正确！");
			return null;
		}
		return batchdataDto;
	}
	
	 //获取单元格的值  
    private String getCellValue(Cell cell) {  
        String cellValue = "";  
        DataFormatter formatter = new DataFormatter();  
        if (cell != null) {  
            //判断单元格数据的类型，不同类型调用不同的方法  
            switch (cell.getCellType()) {  
                //数值类型  
                case Cell.CELL_TYPE_NUMERIC:  
                    //进一步判断 ，单元格格式是日期格式   
                    if (DateUtil.isCellDateFormatted(cell)) {  
                        cellValue = formatter.formatCellValue(cell);  
                    } else {
                        //数值  
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    cellValue = cell.getStringCellValue();  
                    break;  
                case Cell.CELL_TYPE_BOOLEAN:  
                    cellValue = String.valueOf(cell.getBooleanCellValue());  
                    break;  
                    //判断单元格是公式格式，需要做一种特殊处理来得到相应的值  
                case Cell.CELL_TYPE_FORMULA:{  
                    try{  
                        cellValue = String.valueOf(cell.getNumericCellValue());  
                    }catch(IllegalStateException e){  
                        cellValue = String.valueOf(cell.getRichStringCellValue());  
                    }  
                      
                }  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                    cellValue = cell.getStringCellValue();  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    cellValue = cell.getStringCellValue();  
                    break;  
                default:  
                    cellValue = cell.toString().trim();  
                    break;  
            }  
        }  
        return cellValue.trim();//去掉前后空格  
    }
  
}
