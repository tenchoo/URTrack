package com.urt.web.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FtpsFileList {

	private List<Map<String, String>> listMap;
	private String[] str0;
	private String host;
	private int port;
	private String username;
	private String password;
	private String dir;
	private String path;
	private String fileName;

	public FtpsFileList(String host, int port, String username,
			String password, String path,String fileName) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.path = path;
		this.fileName = fileName;
		this.dir = path+"/"+fileName;
		listFileNames();
	}

	@SuppressWarnings("unchecked")
	private void listFileNames() {
		ChannelSftp sftp = null;
		Channel channel = null;
		Session sshSession = null;
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			int timeout = 30000;  
			sshSession.setTimeout(timeout); // 设置timeout时间 
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			InputStream inps = null;
			try {
				inps = sftp.get(dir);
			} catch (Exception e) {
				throw new Exception("Cannot find this file!");
			}
			Map<String,Object> map = readFile(inps, dir);
			listMap = (List<Map<String, String>>) map.get("listMap");
			str0 = (String[]) map.get("str0");
			// 每次读取完，将读取的文件改名 标记为已读文件。
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
			String strdate = df.format(new Date());
			rename(path, fileName, fileName+strdate, sftp);
		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			closeChannel(sftp);
			closeChannel(channel);
			closeSession(sshSession);
		}
	}

	public static Map<String,Object> readFile(InputStream inps, String fileName) {
		List<Map<String, String>> listMap = null;
		String[] str0 = null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<InputStream> list = null;
		InputStream inps1 = null;
		List<Map<String, String>> listMap1 = null;
        String st0 = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		try {
			if (st0.equals(".xlsx")) {
				list = new ArrayList<InputStream>();
				list.add(inps);
				Workbook workbook = ExcelUtil.parseWorkbook2(list);
				listMap = ExcelUtil.parseExcelToListNew(workbook);
				str0 = ExcelUtil.getCellData(workbook);
			}else if(st0.equals(".zip")){
				ZipInputStream zin = new ZipInputStream(inps);
				ZipEntry ze = null;
				listMap = new ArrayList<Map<String, String>>();
				while ((ze = zin.getNextEntry()) != null) {
					if (ze.isDirectory()) {
					} else {  
						long size = ze.getSize();  
						if (size > 0) {  
							inps1 = IOUtils.toBufferedInputStream(zin);  
							list = new ArrayList<InputStream>();
							list.add(inps1);
							Workbook workbook = ExcelUtil.parseWorkbook2(list);
							listMap1 = ExcelUtil.parseExcelToListNew(workbook);
							listMap.addAll(listMap1);
							str0 = ExcelUtil.getCellData(workbook);
						}
					}
				}
			}
			map.put("listMap", listMap);
			map.put("str0", str0);
		} catch (IOException e) {
			return null;
		}
		return map;
	}

	public static void closeChannel(Channel channel) {
		if (channel != null) {
			if (channel.isConnected()) {
				channel.disconnect();
			}
		}
	}

	public static void closeSession(Session session) {
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory 要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 * @throws Exception
	 */
	public static void delete(String directory, String deleteFile,
			ChannelSftp sftp) throws Exception {
		sftp.cd(directory);
		sftp.rm(deleteFile);
	}

	/**
	 * 更改文件名
	 * 
	 * @param directory 文件所在目录
	 * @param oldFileNm 原文件名
	 * @param newFileNm 新文件名
	 * 
	 * @throws Exception
	 */
	public void rename(String directory, String oldFileNm,
			String newFileNm, ChannelSftp sftp) throws Exception {
		sftp.cd(directory);
		sftp.rename(oldFileNm, newFileNm);
	}

	public List<Map<String, String>> getListMap() {
		return listMap;
	}

	public void setListMap(List<Map<String, String>> listMap) {
		this.listMap = listMap;
	}

	public String[] getStr0() {
		return str0;
	}

	public void setStr0(String[] str0) {
		this.str0 = str0;
	}

}
