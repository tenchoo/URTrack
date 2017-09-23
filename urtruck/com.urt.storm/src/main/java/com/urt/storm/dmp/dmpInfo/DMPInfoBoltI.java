package com.urt.storm.dmp.dmpInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.urt.interfaces.dmp.DMPService;
import com.urt.storm.dmp.entity.CardData;
import com.urt.storm.dmp.entity.DeviceData;
import com.urt.storm.dmp.entity.PositionData;
import com.urt.storm.dmp.entity.PostData;
import com.urt.storm.dmp.entity.PostJsonData;
import com.urt.storm.util.AesUtil;
import com.urt.storm.util.EncryptMD5Util;

public class DMPInfoBoltI extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(DMPInfoBoltI.class);
	
	private DMPService dmpService;
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.dmpService = applicationContext.getBean(DMPService.class);
	}

	@Override
	public void execute(Tuple input) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入PositionInfoBoltI的execute函数"+input.getValue(0));
		
		String preDataListStr = (String) input.getValue(0);
		
//		String preData = (String) input.getValue(0);
		preDataListStr=preDataListStr.substring(1, preDataListStr.length()-1);
		
		String[] preDataList = preDataListStr.split(",%,");
		System.out.println("信息用'%'切割以后的字符串数组-----："+preDataList);
		for(String preData:preDataList){
			System.out.println("消息aes解密前的数据-----："+preData);
			if ("".equals(preData)||preData==null) {
				collector.ack(input);
				return;
			}
			
			//aes解密
			String afterData = AesUtil.aesDecryPt(preData);
			System.out.println("位置信息aes解密之后的数据-----："+afterData);
			
			Gson gson = new Gson();
			PostJsonData postJsonInfo = gson.fromJson(afterData, PostJsonData.class);
			System.out.println("位置信息由字符串转化为对象----："+postJsonInfo);
			if(postJsonInfo==null){
				collector.ack(input);
				return;
			}
			
			String posDataText=postJsonInfo.getPosDataText();
			System.out.println("终端设备信息MD5加密前的字符串posDataText----："+posDataText);
			if ("".equals(posDataText)||posDataText==null) {
				collector.ack(input);
				return;
			}
			
			String afterStrMD5 = EncryptMD5Util.sign(posDataText);
			System.out.println("终端设备信息MD5加密后的字符串afterStrMD5----："+afterStrMD5);
			String originalStrMD5 = postJsonInfo.getEncryptedText();
			System.out.println("原生MD5加密字符串"+originalStrMD5);
			if("".equals(originalStrMD5)||originalStrMD5==null){
				collector.ack(input);
				return;
			}
			
			if(!afterStrMD5.equals(originalStrMD5)){
				System.out.println(afterStrMD5.equals(originalStrMD5));
				System.out.println("验证失败");
				collector.ack(input);
				return;
			}
			System.out.println(afterStrMD5.equals(originalStrMD5));
			System.out.println("验证通过");
			
			String randomText = postJsonInfo.getRandomText();
			System.out.println("kafka中每一条终端信息的唯一标识randomText---："+randomText);
			if ("".equals(randomText)||randomText==null) {
				System.out.println("kafka中每一条终端信息的唯一标识randomText为空");
				collector.ack(input);
				return;
			}
			byte deviceType = postJsonInfo.getDeviceType();
			System.out.println("kafka中每一条终端信息中的设备类型deviceType---："+deviceType);
			String custId = postJsonInfo.getCustId();
			System.out.println("kafka中每一条终端信息中的设备所属企业用户的custId---："+custId);
			
//		PostData postData = gson.fromJson(posDataText, PostData.class);
//		System.out.println("设备信息，位置信息，卡信息的封装对象"+postData);
			boolean ok=dmpService.dmpDataHandler(posDataText,deviceType,randomText,custId);
			if(!ok){
				System.out.println("dmp终端数据处理失败dmpDataHandler");
				collector.ack(input);
			}
		}
		
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}
	/**
	 * MD5加密
	 * @param str
	 * @return
	 */
	public static String degistByMD5(String str){
		StringBuffer buf = null;
		try{
		  MessageDigest md = MessageDigest.getInstance("MD5");  
          md.update(str.getBytes());  
          byte b[] = md.digest();  
          int i;  
          buf = new StringBuffer("");  
          for (int offset = 0; offset < b.length; offset++) {  
              i = b[offset];  
              if (i < 0)  
                  i += 256;  
              if (i < 16)  
                  buf.append("0");  
              buf.append(Integer.toHexString(i));  
          }  
         
      } catch (NoSuchAlgorithmException e) {  
      	 e.printStackTrace();
      }
		 return buf.toString();  
	}
	 public static void main(String[] args){
	    	
	    	String preData="1D3CdGEwx0IcQesdqqbP/hovErCvtQHgASkfNKwes4PNeeHzKHSh3+dft9vY Wn44P4G64X58x/k3/IxBvdVbOe3Upki/9aMNNQcZryMo5H3jFtHOFlAIqeFu IZo+A++JtDFaLmi5PsVqyCRmh+cSlpVQZtnZN138yQ9kiAhsk+2UXSThIOIb mN1wUQs4iyPOpnjxk6XP0n9U+3/DcV8XwfYy/nGMtNwrt2peLq6oxvi4gdWg UP80wdN3Fqjy+WQ/gf8hTKgU2wioxDt56qRl/KT+/Y2KDaD5ejZC+2GSZEzx tm77BvH5K4eRqeQmjgwYZTvAedV9TDhzslj65G4/qd6XgwMQJj5AFwvCbKLB mH1vnBn6akaA2TU0BB36PrKc74OdAbadPlc4pjpm8PGgYBv5CVm+ObYzzSLB CxrABvfJ0+z8VEUO882zBKEMwqfFZcPRecO/2pouAykze9/VGOBkMj4Dfmaw echl1hScx1U9BFpU9wAwbAFGeTMLaNzMWkQopNQXtHd7kvknLceDeJ5qqdgh C8ZwCgrw23e4SJ93vDtVPNeVvbdOquDbye4yQ0MDCJ1e5ZrZwnCsnqzpel2Q zthF/TUjCn8uIi11IjpBhdJCN2kglCnm4Tig78h/8NJ/hPiY1MU0dv6ajMjO TRTnAtTjcSUmUs0CdDDnWqIyJKzCI+kMe/jHbH+czbvFrJoP7QOCp4MtTgo0 gKq21p4Od2QU6TgnHiymVWpgiLmpkvFiI/sBrfdySQZdZQ6hE1iaoGw/h2q/ kE1MzORk1J0YD1bWgUeTopsB61n4TTQQHwMexKbi7iCr5oS4UaVXxVuvBISC cKKizTJ5abvb+Us1Urcjh3Y5TX2ZIecc6LZjROLPvrMFmrOPLKfEPQ3l8u9p IBG3PYQUmUiXfpqIgHVqEtN7sBl1UlYy8QK57a4jYIZ24uja1PITKynY5VYb 0jzhyjGq9/vk3u0rQQ5d4qO7+Pg5YQPPyFwVDaK5ZXIRsAACSfAnRCPf7n1L SwKoPg7WPBL9GNavmR7QC2rpN5r7psPbi58VLyEH5gLNEqVINL3lLV5HKmwv da/tFUt+";
	    	
	    	System.out.println("位置信息aes解密前的数据-----："+preData);
	    	//aes解密
	    	String afterData = AesUtil.aesDecryPt(preData);
	    	System.out.println("位置信息aes解密之后的数据-----："+afterData);
	    	
	    	Gson gson = new Gson();
	    	PostJsonData postJsonInfo = gson.fromJson(afterData, PostJsonData.class);
	    	String custId = postJsonInfo.getCustId();
	    	System.out.println("位置信息由字符串转化为对象----："+postJsonInfo);
//	    	
	    	String posDataText=postJsonInfo.getPosDataText();
	    	System.out.println("终端设备信息MD5加密前的字符串posDataText----："+posDataText);
	    	String afterStrMD5 = EncryptMD5Util.sign(posDataText);
	    	System.out.println("终端设备信息MD5加密后的字符串afterStrMD5----："+afterStrMD5);
	    	String originalStrMD5 = postJsonInfo.getEncryptedText();
	    	System.out.println("原生MD5加密字符串"+originalStrMD5);
	    	if(!afterStrMD5.equals(originalStrMD5)){
	    		System.out.println(afterStrMD5.equals(originalStrMD5));
	    		System.out.println("验证失败");
	    		return;
	    	}
	    	System.out.println(afterStrMD5.equals(originalStrMD5));
			System.out.println("验证通过");
	        
			String randomText = postJsonInfo.getRandomText();
			System.out.println("kafka中每一条终端信息的唯一标识randomText---："+randomText); 
			
			byte deviceType = postJsonInfo.getDeviceType();
			System.out.println("kafka中每一条终端信息中的设备类型deviceType---："+deviceType);
			
			PostData postData = gson.fromJson(posDataText, PostData.class);
			System.out.println("设备信息，位置信息，卡信息的封装对象"+postData);
			PositionData postionData=postData.getPositionData();
			System.out.println("位置信息--:"+postionData);
			DeviceData deviceData = postData.getDeviceData();
			System.out.println("设备信息--:"+deviceData);
			CardData cardData = postData.getCardData();
			System.out.println("卡信息的封装对象--:"+cardData);
			
	    }
	    
	
}
