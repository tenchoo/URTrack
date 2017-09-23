package com.urt.service.ofo.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.urt.po.LaoFtpfileCollect;
import com.urt.po.LaoFtpfileInfo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public class FtpUtils {

	private static Logger logger = Logger.getLogger(FtpUtils.class);

	private static final String FTPHost = "10.0.20.110";
	private static final String FTPUserName = "lenovobill";
	private static final String FTPPassword = "EeFHY6TE5qn4YGyeBHt_";
	private static final int FTPPort = 21;
	private static final String ftpPath = "/data/file/ofo/input";

	private static final String updateFilePath = "/data/file/ofo/output";

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient() {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(FTPHost, FTPPort);// 连接FTP服务器
			ftpClient.login(FTPUserName, FTPPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 * @param localFile
	 * @return Map<String, Object>
	 * @throws Exception
	 */

	public static Map<String, Object> readFile(String fileNames) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<LaoFtpfileInfo> infoList = new ArrayList<>();
		List<LaoFtpfileCollect> collectList = new ArrayList<>();
		OutputStream out = null;
		FTPClient ftpClient = getFTPClient();

		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			//ftpClient.completePendingCommand();
			
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			resultMap.put("result", "没找到文件");
			return resultMap;
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
			e.printStackTrace();
			resultMap.put("result", "文件读取失败");
			return resultMap;
		}
		try {
			String[] fileName1 = fileNames.split(",");
			for (int i = 0; i < fileName1.length; i++) {
				String fileName = fileName1[i];
				System.out.println("文件名称"+fileName);
				FTPFile[] fileInfoArray = ftpClient.listFiles(fileName.trim());
				int length = fileInfoArray.length;
				System.out.println("文件是否存在"+length);
				if (0 == length) {
					continue;
				}
				InputStream ins = ftpClient.retrieveFileStream(fileName);
				BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
				String inLine = reader.readLine();
				// 先读取第一行，判断是否为空，
				logger.info("文件名：" + fileName + "读取到的第一行的值--" + inLine);
				if (inLine == null) {
					// 此文件为空
					resultMap.put(fileName, "F100");
					continue;
				}
				int fileTotalCount = Integer.valueOf(inLine.trim());
				// 定义一个判断是文件中是否包含订购
				String tokenFileName = null;
				int count = 0; // 记录读取的行数

				int countOrdechang1 = 0; // 订购数
				int countOrdechang2 = 0; // 变更数量
				int cardStopCount = 0; // 停机数量
				int cardStartCount = 0; // 开机数量

				if (fileName.contains("productOrderOrChange")) {
					tokenFileName = "100";
				} else {
					tokenFileName = "119";
				}
				Date date = new Date();
				while (inLine != null) {
					inLine = reader.readLine();
					if (inLine == null) {

					} else {
						count++;
						String[] split = inLine.split(",");
						LaoFtpfileInfo info = new LaoFtpfileInfo();
						String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						info.setId(Long.valueOf(idSeq));
						info.setStatus(Short.valueOf("1")); // 刚写入的数据
						info.setProcessmode(Short.valueOf("2")); // 手工处理
						info.setCreatedate(date);
						info.setFileName(fileName);
						info.setIccid(split[0]);
						if ("119".equals(tokenFileName)) {
							// 从停开机文件中读取
							if ("01".equals(split[1])) {
								cardStopCount++;
								// 停机
								info.setTradetypecode("130");
							} else {
								// 开机
								cardStartCount++;
								info.setTradetypecode("140");
							}
						} else {
							// 从订购文件中读取

							if ("01".equals(split[1])) {
								// 订购
								countOrdechang1++;
								info.setTradetypecode("120");
							} else {
								// 变更
								countOrdechang2++;
								info.setTradetypecode("180");
							}
							info.setGoodsreleaseid(Integer.valueOf(split[2]));
						}
						infoList.add(info);
					}

				}

				// 如果此文件中
				if ("100".equals(tokenFileName)) {

					if (countOrdechang1 == 0 || countOrdechang2 == 0) {

						LaoFtpfileCollect ftpfileCollect = new LaoFtpfileCollect();
						ftpfileCollect.setCreateDate(date);
						ftpfileCollect.setFileName(fileName);
						ftpfileCollect.setSuccessnum(0);
						ftpfileCollect.setFailnum(0);
						String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						ftpfileCollect.setId(Long.valueOf(idSeq));
						ftpfileCollect.setPendingnum(count); // 待处理数
						ftpfileCollect.setCardtotal(count); // 读取到的总数
						if (countOrdechang1 == 0) {
							ftpfileCollect.setTradetypecode("120");
						} else {
							ftpfileCollect.setTradetypecode("180");
						}
						collectList.add(ftpfileCollect);
					} else {

						LaoFtpfileCollect ftpfileCollect = new LaoFtpfileCollect();
						ftpfileCollect.setCreateDate(date);
						ftpfileCollect.setFileName(fileName);
						ftpfileCollect.setSuccessnum(0);
						ftpfileCollect.setFailnum(0);
						String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						ftpfileCollect.setId(Long.valueOf(idSeq));
						ftpfileCollect.setPendingnum(countOrdechang1); // 待处理数
						ftpfileCollect.setCardtotal(countOrdechang1); // 读取到的总数
						ftpfileCollect.setTradetypecode("120");

						LaoFtpfileCollect ftpfileCollect1 = new LaoFtpfileCollect();
						ftpfileCollect1.setCreateDate(date);
						ftpfileCollect1.setFileName(fileName);
						ftpfileCollect1.setSuccessnum(0);
						ftpfileCollect1.setFailnum(0);
						String idSeq1 = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						ftpfileCollect1.setId(Long.valueOf(idSeq1));
						ftpfileCollect1.setPendingnum(countOrdechang2); // 待处理数
						ftpfileCollect1.setCardtotal(countOrdechang2); // 读取到的总数
						ftpfileCollect1.setTradetypecode("180");

						collectList.add(ftpfileCollect);
						collectList.add(ftpfileCollect1);

					}

				} else {

					if (cardStopCount == 0 || cardStartCount == 0) {

						LaoFtpfileCollect ftpfileCollect = new LaoFtpfileCollect();
						ftpfileCollect.setCreateDate(date);
						ftpfileCollect.setFileName(fileName);
						ftpfileCollect.setSuccessnum(0);
						ftpfileCollect.setFailnum(0);
						String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						ftpfileCollect.setId(Long.valueOf(idSeq));
						ftpfileCollect.setPendingnum(count); // 待处理数
						ftpfileCollect.setCardtotal(count); // 读取到的总数
						if (countOrdechang1 == 0) {
							ftpfileCollect.setTradetypecode("130");
						} else {
							ftpfileCollect.setTradetypecode("140");
						}

						collectList.add(ftpfileCollect);

					} else {

						LaoFtpfileCollect ftpfileCollect = new LaoFtpfileCollect();
						ftpfileCollect.setCreateDate(date);
						ftpfileCollect.setFileName(fileName);
						ftpfileCollect.setSuccessnum(0);
						ftpfileCollect.setFailnum(0);
						String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						ftpfileCollect.setId(Long.valueOf(idSeq));
						ftpfileCollect.setPendingnum(cardStopCount); // 待处理数
						ftpfileCollect.setCardtotal(cardStopCount); // 读取到的总数
						ftpfileCollect.setTradetypecode("130");

						LaoFtpfileCollect ftpfileCollect1 = new LaoFtpfileCollect();
						ftpfileCollect1.setCreateDate(date);
						ftpfileCollect1.setSuccessnum(0);
						ftpfileCollect1.setFailnum(0);
						ftpfileCollect1.setFileName(fileName);
						String idSeq1 = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
						ftpfileCollect1.setId(Long.valueOf(idSeq1));
						ftpfileCollect1.setPendingnum(cardStartCount); // 待处理数
						ftpfileCollect1.setCardtotal(cardStartCount); // 读取到的总数
						ftpfileCollect1.setTradetypecode("140");

						collectList.add(ftpfileCollect);
						collectList.add(ftpfileCollect1);
					}

				}

				logger.info("此文件头标记总数===" + fileTotalCount);
				logger.info("统计的此文件的总数===" + count);
				if (count > fileTotalCount || count < fileTotalCount) {
					resultMap.put(fileName, "F002");
				}
				ins.close();
				boolean completePendingCommand = ftpClient.completePendingCommand(); 
				System.out.println(completePendingCommand);
				resultMap.put("result", "success");
			}
			resultMap.put("infoFile", infoList);
			resultMap.put("collectFile", collectList);
//			ftpClient.getReply();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMap.put("result", "文件异常");
			logger.info("Ftp----文件内容格式不正确");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "文件异常");
			logger.info("Ftp----文件内容格式不正确");
		} finally {
			try {
				ftpClient.disconnect();
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				resultMap.put("result", "断开异常");
				logger.info("Ftp----断开异常");
			}

		}
		logger.info("ftp读取结果------"+resultMap.toString());
		return resultMap;
	}

	@SuppressWarnings("static-access")
	public static boolean uploadFile(String filename, InputStream input) {
		Boolean success = true;
		FTPClient ftp = null;
		try {

			ftp = getFTPClient();
			ftp.changeWorkingDirectory(updateFilePath);
			ftp.setFileTransferMode(ftp.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			boolean storeFile = ftp.storeFile(filename, input);
			System.out.println("写入" + storeFile);
			input.close();
			ftp.logout();
			success = storeFile;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	
	public static void main(String[] args) {
		//productOrderOrChange_9001_20170814.REQ  cardStopOrOpen_9001_20170814.REQ
		String files="cardStopOrOpen_9001_20170814.REQ"+","+"productOrderOrChange_9001_20170814.REQ";
		Map<String, Object> readFile = readFile(files);
	    System.out.println(readFile.toString());
	
	}
	public static boolean uploadFile(String url, int port, String username, String password, String path,
			String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			boolean storeFile = ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
			success = storeFile;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
}
