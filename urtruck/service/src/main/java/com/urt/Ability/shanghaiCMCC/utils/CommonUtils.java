package com.urt.Ability.shanghaiCMCC.utils;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 上海移动工具类
 *
 */

public class CommonUtils {
	/*@Autowired
	private AccessSeqMapper seqMapper;
	
	
	public  String getRequestId(){
		Long seq = seqMapper.querySeq();
		String eid = ConstantUtil.EID;
		String dateTime = getFormate(new Date());
		
		return null;
	}*/
	
	public static String getFormate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateFormat = format.format(date);
		return dateFormat;
	}
}
