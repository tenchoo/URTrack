package com.urt.web.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
* 类说明：excel工具类  poi
* @author cuichao
* @date 2016年5月17日 下午1:28:59
*/
public class ExcelUtil {
    /**
     * 表头数据key
     */
    private static final String TOTAL_CELL_INDEX = "TOTAL_CELL_INDEX";
    /**
     * 表体数据key
     */
    private static final String TOTAL_CELL_DATA = "TOTAL_CELL_DATA";


    /**
     * 获得excel的版本，获得相应的Workbook
     * 07版及其以下，包括03版及其以下 excel HSSFWorkbook;
     * 07版及其以上 excel XSSFWorkbook;
     * 支持03、07、以及以上版本
     * @param inputStreams inputStream对象集合{InputStream，InputStream}
     * @return HSSFWorkbook（07-）;XSSFWorkbook（07+）;
     */
    static Workbook parseWorkbook2(List<InputStream> inputStreams) {
        Workbook wb = null;
        InputStream xssfInpStm = inputStreams.get(0);
        try {
            if (!xssfInpStm.markSupported()) {
                xssfInpStm = new PushbackInputStream(xssfInpStm, 8);
            }
            //07版及其以下，包括03版及其以下
            if (POIFSFileSystem.hasPOIFSHeader(xssfInpStm)) {
                InputStream hssfInpStm = inputStreams.get(1);
                wb = new HSSFWorkbook(hssfInpStm);
                if (hssfInpStm != null) {
                    hssfInpStm.close();
                }
                //System.out.println("Excel版本：2003及以下");
            }
            //07版及其以上
            else if (POIXMLDocument.hasOOXMLHeader(xssfInpStm)) {
                wb = new XSSFWorkbook(OPCPackage.open(xssfInpStm));
                //System.out.println("Excel版本：2007及以上");
            } else {
                throw new IllegalArgumentException("该Excel版本目前POI解析不了！");
            }

        } catch (Exception e) {
            throw new RuntimeException("创建Workbook失败！", e);
        } finally {
            try {
                if (xssfInpStm != null) {
                    xssfInpStm.close();
                }
            } catch (Exception e) {
                throw new RuntimeException("InputStream 关闭出现异常！", e);
            }
        }
        return wb;
    }



    /**
     * 解析excel文件内容，在指定的sheet中根据列的名称查找某一列的索引
     * @param wb 需要解析的excel对象
     * @param sheetIndex sheet的索引
     * @param cellName 列名
     * @return
     */
    private static Integer findCellIndex(Workbook wb, Integer sheetIndex, String cellName){
        // 如果要查找的列名为空，则不进行查找
        if(StringUtils.isBlank(cellName)) {
            throw new RuntimeException("cellName is blank");
        }
        try{
            // 查找到指定的sheet，如果没有指定sheet的值，则默认为第一个sheet
            if(sheetIndex == null) {
                sheetIndex = 0;
            }

            Sheet sheet = wb.getSheetAt(sheetIndex);
            // 如果sheet为空，则认为是异常情况，则抛出运行时异常
            if(sheet == null) {
                throw new RuntimeException("can find sheet, please check");
            }
            // 得到sheet中有效的行的最大编号
            Integer lastRowNum = sheet.getLastRowNum();
            for(int i=0; i<=lastRowNum; i++) {
                // 得到某一行
                Row row = sheet.getRow(i);
                if(row != null) {
                    // 得到该行的某一列
                    int lastColumnNum = row.getLastCellNum();
                    // 解析得到某列的数据
                    for(int j=0; j<=lastColumnNum; j++) {
                        Cell cell = row.getCell(j);
                        if(cell != null){
                            String cellValue = cell.toString();
                            if(cellName.equals(cellValue)){
                                return j;
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 解析excel中的数据到一个集合中，集合中的每一个map为一行数据的内容
     * @param wb 需要解析的excel对象
     * @return [{"行号":{[{"列号":"值"},{"列号":"值"} ...}]}, ...]
     */
    private static List<Map<String, String>> parseExcelToValueMapList(Workbook wb) {
        // 解析excel中的数据
        Map<Integer, Map<String, Object>> excelDatas = parseExcelToMap(wb, null);

        // 遍历map，得到所有所需的数据的索引，如果有一列的数据为空，则抛出异常
        Map<String, Integer> beanPropAndCellIndexMap = Maps.newLinkedHashMap();
        // 当前表头行所在的行的索引，得到第一行的数据的集合
        List<String> firstCellValueList = (List<String>) excelDatas.get(0).get(TOTAL_CELL_DATA);
        for(String beanName : firstCellValueList){
            if(beanName == null){
                continue;
            }
            // 得到所在列的索引
            Integer dataIndex = findCellIndex(wb, null, beanName);
            if(dataIndex == null){
                throw new RuntimeException("不能找到 " + beanName + " 数据");
            }
            beanPropAndCellIndexMap.put(beanName, dataIndex);
        }


        List<Map<String, String>> excel2ValueMapList = Lists.newArrayList();
        int count = 0;
        // 将excel中的每一行的数据封装成对象
        for(Map.Entry<Integer, Map<String, Object>> entry : excelDatas.entrySet()){
            if(count == 0){
                count++;
                continue;
            }
            // 当前所在的行的索引,excel行数据
            Map<String, Object> cellDataMap = entry.getValue();
            if(cellDataMap == null) {
                continue;
            }
            // 得到一行的数据的集合
            List<String> cellValueList = (List<String>) cellDataMap.get(TOTAL_CELL_DATA);

            Map<String, String> beanValueMap = Maps.newLinkedHashMap();

            // 根据属性值和excel中的索引获取所需数据，并封装对象
            for(Map.Entry<String, Integer> entry1 : beanPropAndCellIndexMap.entrySet()) {
                //列名
//              String columnName = entry1.getKey();
                //列索引
                Integer dataIndex = entry1.getValue();
                if(dataIndex != null){
                    String propValue = cellValueList.get(dataIndex);
                    beanValueMap.put(dataIndex.toString(), propValue);
                }
            }
            excel2ValueMapList.add(beanValueMap);
        }
        return excel2ValueMapList;
    }

    /**
     * 解析Excel中的内容，获取文件中的信息(包括标题和数据)，
     * 返回的内容是以map形式(key为行号，value为一个map)
     * 此map中的key有两个，一个是  TOTAL_CELL_INDEX：对应的值为一行的列的数量,
     * 另一个是 TOTAL_CELL_DATA 值是一个arrayList,
     * @param wb  需要解析的excel对象
     * @param sheetIndex sheet所在的索引
     * @return
     */
    private static Map<Integer, Map<String, Object>> parseExcelToMap(Workbook wb, Integer sheetIndex) {
        Map<Integer, Map<String, Object>> dataMap = Maps.newLinkedHashMap();
        try {
            if (sheetIndex == null) {
                sheetIndex = 0;
            }

            Sheet sheet = wb.getSheetAt(sheetIndex);
            // 如果sheet为空，则认为是异常情况，则抛出运行时异常
            if (sheet == null) {
                throw new RuntimeException("不能找到 sheet,请重新检查文件！");
            }
            // 得到sheet中有效的行的最大编号 TODO 有bug 修改过不能正确读取
            Integer lastRowNum = sheet.getLastRowNum();
            // 标题行的最大的列数
            int titleRowMaxIndex = 0;
            for (int i = 0; i <= lastRowNum; i++) {
                // 得到某一行
                Row row = sheet.getRow(i);
                // 如果该行无有效内容，则向map放入null
                if (row == null) {
                    dataMap.put(i, null);
                } else {
                    // 得到该行的某一列，获取最后一个不为空的列是第几个。
                    int lastColumnNum = row.getLastCellNum();
                    if (i == 0) {
                        titleRowMaxIndex = lastColumnNum;
                    }
                    // 加入行的总列数，行数据内容
                    Map<String, Object> rowValueMap = new HashMap<String, Object>();
                    rowValueMap.put(TOTAL_CELL_INDEX, titleRowMaxIndex + 1);
                    dataMap.put(i, rowValueMap);
                    List<String> cellValueList = new ArrayList<String>();
                    rowValueMap.put(TOTAL_CELL_DATA, cellValueList);
                    // 解析得到某行的数据
                    for (int j = 0; j < titleRowMaxIndex; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            cellValueList.add(null);
                        } else {
                            String cellValue = getCellValue(cell);
                            cellValueList.add(cellValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataMap;
    }

    /**
     * 解析得到excel中列的值;
     * 不同类型获取不同的值;
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell){
        if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf( cell.getBooleanCellValue());
        } else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            //return String.valueOf(cell.getNumericCellValue());
            try {
            	DecimalFormat df = new DecimalFormat("###.###");
                return df.format(cell.getNumericCellValue());
			} catch (Exception e) {
				return String.valueOf(cell.getNumericCellValue());
			}
        } else {
            return String.valueOf( cell.getStringCellValue());
        }
    }

    /**
     * 将excel中的数据解析成Map集合
     * 前提：该文件的输入流，必须是excel文件输入流。
     *       在该输入流从传递前最好进行验证。
     * 要求：excel各个字段不必转换，
     * @param multipartFile multipartFile对象
     * @return [{"行号":{[{"列号":"值"},{"列号":"值"} ...}]}, ...]
     */
    public static List<Map<String, String>> parseExcelToList(
            MultipartFile multipartFile) throws IOException{
        if(multipartFile != null && !multipartFile.isEmpty() ){
            List<InputStream> inputStreams = Lists.newArrayList();
            inputStreams.add(multipartFile.getInputStream());
            Workbook wb = parseWorkbook2(inputStreams);
            List<Map<String, String>> excel2ValueMapList = parseExcelToValueMapList(wb);
            return excel2ValueMapList;
        }
        return null;
    }
    
    
    
    
    
	/**
	 * 读取Excel 返回List<Map<String, String>> 不含表头内容
	 * 
	 * @param inputStreams
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, String>> parseExcelToListNew(
			Workbook wb) throws IOException {
		List<Map<String, String>> excel2ValueMapList = parseExcelToValueMapList(wb);
		return excel2ValueMapList;
	}

	/**
	 * 读取表头
	 * 
	 * @param 
	 * @return
	 */
	public static String[] getCellData(Workbook wb) {
		Sheet sheet = wb.getSheetAt(0);
		List<String> columnHeaderList = new ArrayList<String>();
		List<List<String>> listData = new ArrayList<List<String>>();
		List<Map<String, String>> mapData = new ArrayList<Map<String, String>>();
		int numOfRows = sheet.getLastRowNum() + 1;
		int numOfCols=sheet.getRow(0).getPhysicalNumberOfCells();
		String[] str0 = new String[numOfCols];
		for (int i = 0; i < numOfRows; i++) {
			Row row2 = sheet.getRow(i);
			Map<String, String> map = new HashMap<String, String>();
			List<String> list = new ArrayList<String>();
			if (row2 != null) {
				for (int j = 0; j < row2.getLastCellNum(); j++) {
					Cell cell = row2.getCell(j);
					if (i == 0) {
						columnHeaderList.add(getCellValue(cell));
					} else {
						map.put(columnHeaderList.get(j), getCellValue(cell));
					}
					list.add(getCellValue(cell));
				}
			}
			if (i > 0) {
				mapData.add(map);
			}
			listData.add(list);
		}
		for (int i = 0; i < str0.length; i++) {
			if (listData.size() >= 1 && listData.get(0).size() >= i) {
				str0[i] = listData.get(0).get(i);
			} else {
				return null;
			}
		}
		return str0;
	}
}
