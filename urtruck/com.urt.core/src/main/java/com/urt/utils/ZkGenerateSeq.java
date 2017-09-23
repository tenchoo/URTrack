package com.urt.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 类说明：生成16位唯一索引 3位ID标识+6位日期yymmdd+7位系列号
 * 
 * @author Administrator
 * @date 2016年5月26日 下午5:30:45
 */
public class ZkGenerateSeq {

	public static ZkClient client;
	// 所有的seq默认为7位
	public static final int minseq = 1000000;
	public static final int min8seq = 10000000;

	// public static final int min8seq=10000;
	static {
//		client = new ZkClient("172.25.99.12:2181,10.0.20.110:2181,10.0.20.111:2181", 300000, 100000);
		client = new ZkClient("127.0.0.1:2181", 300000, 100000);
	}

	/**
	 * 功能描述：3位ID标识+6位日期yymmdd+7位系列号
	 * 
	 * @author fuhp
	 * @date 2016年5月19日 下午8:12:31
	 * @return void
	 */
	public static String getIdSeq(SeqID seqID) {

		String path = "/ZkSeq/" + seqID.getIdName();
		if(!client.exists("/ZkSeq")){ 
			
			client.create("/ZkSeq", new byte[0], CreateMode.PERSISTENT);
		}
		if (!client.exists(path)) {
			client.create(path, new byte[0], CreateMode.PERSISTENT);
		}
		Stat stat = client.writeDataReturnStat(path, new byte[0], -1);

		int versionSeq = stat.getVersion();
		String strSeq = (new Integer(versionSeq)).toString();
		while ( 7 > strSeq.length()) {
			strSeq = "0" + strSeq;
		}
		if(strSeq.length()>7){
			strSeq=strSeq.substring(strSeq.length()-7,strSeq.length());
		}
		
		return seqID.getIdSeq() + getFormatDate() + strSeq;
	}	
	/**
	 * 功能描述：6位系列号
	 * 
	 * @author fuhp
	 * @date 2016年5月19日 下午8:12:31
	 * @return void
	 */
	public static String get6IdSeq(SeqID seqID) {

		String path = "/ZkSeq/" + seqID.getIdName();
		if (!client.exists(path)) {
			client.create(path, new byte[0], CreateMode.PERSISTENT);
		}
		Stat stat = client.writeDataReturnStat(path, new byte[0], -1);

		int versionSeq = stat.getVersion();
		String strSeq = (new Integer(versionSeq)).toString();
		while ( 6 > strSeq.length()) {
			strSeq = "0" + strSeq;
		}
		
		return strSeq;
	}



	/**
	 * 功能描述：8位系列号
	 * 
	 * @author fuhp
	 * @date 2016年5月19日 下午8:12:31
	 * @return void
	 */
	public static String get8IdSeq(SeqID seqID) {

		String path = "/ZkSeq/" + seqID.getIdName();
		if (!client.exists(path)) {
			client.create(path, new byte[0], CreateMode.PERSISTENT);
		}
		Stat stat = client.writeDataReturnStat(path, new byte[0], -1);
		int versionSeq = stat.getVersion();
		String strSeq = (new Integer(versionSeq)).toString();
		while ( 8 > strSeq.length()) {
			strSeq = "0" + strSeq;
		}
	
		return strSeq;
	}

	/**
	 * 功能描述：返回yyMMdd格式的时间字符
	 * 
	 * @author fuhp
	 * @date 2016年5月19日 下午8:12:31
	 * @return void
	 */
	public static String getFormatDate() {

		SimpleDateFormat  dateFm = new SimpleDateFormat("HHmmss"); // 格式化当前系统日期
		String dateTime = dateFm.format(new java.util.Date());
		return dateTime;
	}

	/**
	 * 功能描述：返回format格式的时间字符yy，yyyymmdd....
	 * 
	 * @author fuhp
	 * @date 2016年5月19日 下午8:12:31
	 * @return void
	 */
	public static String getFormatDate(String format) {

		SimpleDateFormat dateFm = new SimpleDateFormat("yyMMdd"); // 格式化当前系统日期
		String dateTime = dateFm.format(new java.util.Date());
		return dateTime;
	}

	public static void main(String[] args) {

		final ExecutorService service = Executors.newFixedThreadPool(20);

		for (int i = 0; i < 2000; i++) {
			// TestZkGenerateSeq a=new ;
			service.execute(new TestZkGenerateSeq());
		}

		if (!service.isShutdown()) {
			try {
				service.shutdown();
				if (!service.awaitTermination(10, TimeUnit.SECONDS)) {
					service.shutdownNow();
				}
			} catch (InterruptedException e) {
				service.shutdownNow();
				System.out.println(e.getMessage());
			}
		}
	}

	public static class TestZkGenerateSeq implements Runnable {

		public TestZkGenerateSeq() {

		}

		@Override
		public void run() {

			System.out.println(getIdSeq(SeqID.GOODSELEMENT_ID));
		}

	}

}
