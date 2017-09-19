package com.urt.Ability.DongguanCMC3;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

//int port 为FTP的端口号   默认为21  

public class FtpUtils {

	private static Logger logger = Logger.getLogger(FtpUtils.class);

	private static final String FTPHost = "120.197.89.244";
	private static final String FTPUserName = "lenovodd";
	private static final String FTPPassword = "lenovodd123";
	private static final int FTPPort = 10021;
	private static final String ftpPath = "/home/lenovodd";

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
	 * @return
	 * @throws Exception
	 */

	public static String downloadFile(String fileNames, String localFile) {
		OutputStream out = null;
		FTPClient ftpClient =getFTPClient();
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			FTPFile[] listFiles = ftpClient.listFiles();
			System.out.println("多少个"+listFiles.length);
			int s=0;
			for(FTPFile file:listFiles){ 
				s++;
				logger.info("序号"+s+"名称>>>>>"+file.getName());  
	            }  
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			return "没找到文件";
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
			e.printStackTrace();
			return "配置文件读取失败，请联系管理员.";
		}
		try {
			String[] fileName1 = fileNames.split(",");
			String fileName="";
			for (int i = 0; i < fileName1.length; i++) {
				fileName=fileName1[i];
				FTPFile[] fileInfoArray = ftpClient.listFiles(fileName);
				int length = fileInfoArray.length;
				if (0== length) {
					continue;
				}
				// 下载文件
				out = new BufferedOutputStream(new FileOutputStream(localFile + "/" + fileName));
				boolean retrieveFile = ftpClient.retrieveFile(fileName, out);
				if (!retrieveFile) {
					return "文件下载异常,请检查FTP服务和文件路径";
				}
				out.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ftpClient.disconnect();
				if (null!=out) {
					out.close();
				}else{
					return "Ftp服务器没有要下载的文件";
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "断开FTP异常";
			}

		}
		return "success";
	}
	/*public void List(String pathName,String ext) throws IOException{  
        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
            String directory = pathName;  
            //更换目录到当前目录  
            this.ftp.changeWorkingDirectory(directory);  
            FTPFile[] files = this.ftp.listFiles();  
            for(FTPFile file:files){  
                if(file.isFile()){  
                    if(file.getName().endsWith(ext)){  
                        arFiles.add(directory+file.getName());  
                    }  
                }else if(file.isDirectory()){  
                    List(directory+file.getName()+"/",ext);  
                }  
            }  
        }  
    }  */
}