package com.urt.web.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExpUtil {
    /** 
    * 1.创建 workbook 2007版及以上excel
    * @return 
    */  
    public XSSFWorkbook getXSSFWorkbook(){  
       return new XSSFWorkbook();  
    }  
    
    /** 
    * 2.创建 sheet 
    * @param xssfWorkbook 
    * @param sheetName sheet 名称 
    * @return 
    */  
    public XSSFSheet getXSSFSheet(XSSFWorkbook xssfWorkbook, String sheetName){  
       return xssfWorkbook.createSheet(sheetName);  
      
    }
    /** 
    * 3.写入表头信息 
    * @param xssfWorkbook 
    * @param xssfSheet 
    * @param headInfoList List<Map<String, Object>> 
    *              key: title         列标题 
    *                   columnWidth   列宽 
    *                   dataKey       列对应的 dataList item key 
    */  
    public void writeHeader(XSSFWorkbook xssfWorkbook,XSSFSheet xssfSheet ,
    		Integer idx, List<Map<String, Object>> headInfoList){  
       XSSFCellStyle headStyle = xssfWorkbook.createCellStyle();  
       
       XSSFFont headFont = xssfWorkbook.createFont();  
       headFont.setFontHeightInPoints((short)12);//设置字体大小
       //headFont.setBoldweight(font.BOLDWEIGHT_BOLD);  
       headStyle.setFont(headFont);  
       
       //设置边框
       /*headStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
       headStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
       headStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
       headStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
*/       
       headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);  //左右居中
       headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //上下居中
    
       XSSFRow r = xssfSheet.createRow(idx);  
       r.setHeight((short) 380);  
       XSSFCell c = null;  
       Map<String, Object> headInfo = null;  
       //处理excel表头  
       for(int i=0, len = headInfoList.size(); i < len; i++){  
           headInfo = headInfoList.get(i);  
           c = r.createCell(i);  
           c.setCellValue(headInfo.get("title").toString());  
           c.setCellStyle(headStyle);  
           if(headInfo.containsKey("columnWidth")){
               xssfSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));  
           }
       }
    }
    
    /** 
    * 3.写入表头信息，可自由组合表头
    * @param xssfWorkbook 
    * @param xssfSheet 
    * @param headInfoList List<Map<String, Object>> 
    *              key: title         列标题 
    *                   columnWidth   列宽 
    *                   dataKey       列对应的 dataList item key 
    */  
    
    public void writeMultiHeader(XSSFWorkbook xssfWorkbook,XSSFSheet xssfSheet, Integer rowNum,
    		List<Map<String, Object>> headInfoList,
    		List<String> headPattern){  
        XSSFCellStyle headStyle = xssfWorkbook.createCellStyle();  
        
        XSSFFont headFont = xssfWorkbook.createFont();  
        headFont.setFontHeightInPoints((short)12);//设置字体大小
        //headFont.setBoldweight(font.BOLDWEIGHT_BOLD);  
        headStyle.setFont(headFont);
        
        //设置边框
        /*headStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        headStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        headStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        headStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
*/        
        headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);  //左右居中
        headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //上下居中
     
        XSSFRow r = xssfSheet.createRow(rowNum);  
        r.setHeight((short) 380);  
        XSSFCell c = null;  
        Map<String, Object> headInfo = null;
        String[] temp = null;
        
        //处理excel表头  
        for(int i=0, len = headInfoList.size(); i < len; i++){  
            headInfo = headInfoList.get(i); 
            temp = headPattern.get(i).split(",");
            
            c = r.createCell(Integer.parseInt(temp[2]));  
            c.setCellValue(headInfo.get("title").toString());  
            c.setCellStyle(headStyle);  
            if(headInfo.containsKey("columnWidth")){
                xssfSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));  
            }
        }
        
        //根据设置的表头布局参数，合并表头
        for(int j = 0, jLen = headPattern.size(); j < jLen; j++)
        {
			temp = headPattern.get(j).split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);

			xssfSheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol)); 	   		   
        }
        
     }
    
    /** 
    * 4.写入内容部分 
    * @param xssfWorkbook 
    * @param xssfSheet 
    * @param startIndex 从1开始，多次调用需要加上前一次的dataList.size() 
    * @param headInfoList List<Map<String, Object>> 
    *              key: title         列标题 
    *                   columnWidth   列宽 
    *                   dataKey       列对应的 dataList item key 
    * @param dataList 
    */  
    public void writeContent(XSSFWorkbook xssfWorkbook,XSSFSheet xssfSheet ,int startIndex,  
                                    List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList){  
       Map<String, Object> headInfo = null;  
       XSSFRow r = null;  
       XSSFCell c = null;  
       //处理数据  
       Map<String, Object> dataItem = null;  
       Object v = null;  
       for (int i=0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++,rownum++){  
           r = xssfSheet.createRow(rownum);  
           r.setHeightInPoints(16);  
           dataItem = dataList.get(i);  
           for(int j=0, jlen = headInfoList.size(); j < jlen; j++){  
               headInfo = headInfoList.get(j);  
               c = r.createCell(j);
               
               if(dataItem.containsKey(headInfo.get("dataKey").toString()))
                   v = dataItem.get(headInfo.get("dataKey").toString());
               else
            	   v = "0";
    
               if (v instanceof String) {  
                   c.setCellValue((String)v);  
               }else if (v instanceof Boolean) {  
                   c.setCellValue((Boolean)v);  
               }else if (v instanceof Calendar) {  
                    c.setCellValue((Calendar)v);  
                }else if (v instanceof Double) {  
                    c.setCellValue((Double)v);  
                }else if (v instanceof Integer  
                        || v instanceof Long  
                        || v instanceof Short  
                        || v instanceof Float) {  
                    c.setCellValue(Double.parseDouble(v.toString()));  
                }else if (v instanceof XSSFRichTextString) {  
                    c.setCellValue((XSSFRichTextString)v);  
                }else {  
                    c.setCellValue(v.toString());  
                }  
            }  
        }  
    }  
    
    public void write2FilePath(XSSFWorkbook xssfWorkbook, String filePath) throws IOException{  
        FileOutputStream fileOut = null;  
        try{  
            fileOut = new FileOutputStream(filePath);  
            xssfWorkbook.write(fileOut);  
        }finally{  
            if(fileOut != null){  
                fileOut.close();  
            }  
        }  
    }  
    
    
    /** 
     * 导出excel 
     * @param sheetName   sheet名称 
     * @param filePath   文件存储路径， 如：f:/a.xls 
     * @param headInfoList List<Map<String, Object>> 
     *                           key: title         列标题 
     *                                columnWidth   列宽 
     *                                dataKey       列对应的 dataList item key 
     * @param dataList  List<Map<String, Object>> 导出的数据 
     * @throws java.io.IOException 
     * 
     */  
    public static void exportExcel2FilePath(String sheetName, String filePath,  
                                   List<Map<String, Object>> headInfoList,  
                                   List<Map<String, Object>> dataList,
                                   ServletOutputStream outputStream) throws IOException {  
    	ExcelExpUtil poiUtil = new ExcelExpUtil();  
        //1.创建 Workbook
        XSSFWorkbook xssfWorkbook = poiUtil.getXSSFWorkbook();
        //2.创建 Sheet  
        XSSFSheet xssfSheet = poiUtil.getXSSFSheet(xssfWorkbook, sheetName);  
        //3.写入 head  
        poiUtil.writeHeader(xssfWorkbook, xssfSheet, 0, headInfoList);  
        //4.写入内容  
        poiUtil.writeContent(xssfWorkbook, xssfSheet, 1, headInfoList, dataList);  
        //5.保存文件到filePath中  
        //poiUtil.write2FilePath(xssfWorkbook, filePath);  
        
        try  
        {  
        	xssfWorkbook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }
    }
    
    public static void exportExcelByMultiHeads(String sheetName, String filePath,  
            List<List<Map<String, Object>>> headInfoListList,
            List<List<String>> headPatternList,
            List<Map<String, Object>> headInfoStandard,
            List<Map<String, Object>> dataList,
            ServletOutputStream outputStream) throws IOException {  
        ExcelExpUtil poiUtil = new ExcelExpUtil();  
        //1.创建 Workbook
		XSSFWorkbook xssfWorkbook = poiUtil.getXSSFWorkbook();  
		//2.创建 Sheet  
		XSSFSheet xssfSheet = poiUtil.getXSSFSheet(xssfWorkbook, sheetName);
		
		//有合并方式的表格头信息，一行
		List<Map<String, Object>> headInfoList = null;
		
		//表格头信息每个数据所占的行列值，对应一行中的每个数据的开始行号，结束行号，开始列号，结束列号
		List<String> headPattern = null;
		
		int i = 0;
		for(;i < headInfoListList.size(); i++)
		{
			headInfoList = new ArrayList<Map<String, Object>>();
			headInfoList = headInfoListList.get(i);
			headPattern = new ArrayList<String>();
			headPattern = headPatternList.get(i);
			
			//3.写入 head  
			poiUtil.writeMultiHeader(xssfWorkbook, xssfSheet, i, headInfoList, headPattern); 
		}
		
		//写入标准表头
		poiUtil.writeHeader(xssfWorkbook, xssfSheet, i, headInfoStandard);

		//4.写入内容  
		poiUtil.writeContent(xssfWorkbook, xssfSheet, i+1, headInfoStandard, dataList);  
		//5.保存文件到filePath中  
		//poiUtil.write2FilePath(xssfWorkbook, filePath);  
		
        try  
        {  
        	xssfWorkbook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }
	}

}
