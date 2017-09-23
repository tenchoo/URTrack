package com.urt.web.common.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIExcelUtil {
	public static final String FILE_EXTENSION_XLS = "xls";
	public static final String FILE_EXTENSION_XLSX = "xlsx";
	Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * @param Map
	 *            <String,String> maps 属性表，成员属性age为KEY，中文名称为VALUE
	 * @param List
	 *            <T> list 需要导出的数据列表对象
	 *            
	 * @return true 导出成功 false 导出失败
	 */
	public <T> boolean excelExport(Map<String, String> maps, List<T> list, String filename, HttpServletResponse response) {

		try {
			Workbook wb = null;
			String type = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
			if (type.equals(FILE_EXTENSION_XLS)) {
				wb = new HSSFWorkbook();
			}
			if (type.equals(FILE_EXTENSION_XLSX)) {
				wb = new XSSFWorkbook();
			}
			StringBuilder fullPath = new StringBuilder("download").append(File.separator);
			String fullName = createExportPath(fullPath, filename);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>导出文件"+fullName);
			
			CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet = wb.createSheet("sheet1");
			Set<String> sets = maps.keySet();
			Row row = sheet.createRow(0);
			int i = 0;
			// 定义表头
			for (Iterator<String> it = sets.iterator(); it.hasNext();) {
				String key = it.next();
				Cell cell = row.createCell(i++);
			    cell.setCellValue(createHelper.createRichTextString(maps.get(key)));
			}
			// 填充表单内容
			System.out.println("--------------------100%");
			for (int j = 0; j < list.size(); j++) {
				T p = list.get(j);
				Class classType = p.getClass();
				int index = 0;
				Row row1 = sheet.createRow(j+1);
				for (Iterator<String> it = sets.iterator(); it.hasNext();) {
					String key = it.next();
					String firstLetter = key.substring(0, 1).toUpperCase();
					// 获得和属性对应的getXXX()方法的名字
					String getMethodName = "get" + firstLetter+ key.substring(1);
					// 获得和属性对应的getXXX()方法
					Method getMethod = classType.getMethod(getMethodName,new Class[] {});
					// 调用原对象的getXXX()方法
					Object value = getMethod.invoke(p, new Object[] {});
					Cell cell = row1.createCell(index++);
					if(value != null && value != "")
				    cell.setCellValue(value.toString());
				}
			}
			File file = new File(fullName);
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			
			InputStream ins =  new FileInputStream(fullName);
			response.setContentType("application/unknown");
			response.setHeader("Content-Disposition","attachment; filename=" + new String(fullName.getBytes("gb2312"), "ISO8859-1"));
			OutputStream os = response.getOutputStream();
			
			byte[] bytes = new byte[1024];
			int len = 0;
			while ((len = ins.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
			ins.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 创建导出路径
	 * @param fullPath  路径名
	 */
	private String createExportPath(StringBuilder fullPath,String filename){
		File dir = new File(fullPath.toString());
		dir.mkdir();
		String excelFileName = fullPath.append(filename).toString();
		File excelFile = new File(excelFileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
		return excelFileName;
	}
}
