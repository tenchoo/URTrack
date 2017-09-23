package com.urt.common.util;

import java.text.DecimalFormat;

/**
 * 类说明：
 * 
 * @author Baomz
 * @date 2016年6月4日 下午4:35:23
 */
public class MoneyConvert {

	public static DecimalFormat D_FORMAT = new DecimalFormat("#.##");
	public static double D_CONVERT_MONEY = 100.0;
	public static long L_CONVERT_MONEY = 100;

	/**
	 * 功能描述：元换算为li
	 * 
	 * @author Baomz
	 * @date 2016年6月4日 下午4:51:04
	 * @param @param
	 *            yuan
	 * @param @return
	 * @return long
	 */
	public static long yuan2li(double yuan) {
		//String yuan_ = D_FORMAT.format(yuan);
		return (long)(yuan * L_CONVERT_MONEY);
	}
	
	

	/**
	 * 功能描述：li换算为元
	 * 
	 * @author Baomz
	 * @date 2016年6月4日 下午4:51:40
	 * @param @param
	 *            li
	 * @param @return
	 * @return String
	 */
	public static String li2yuan(long li) {
		return D_FORMAT.format(li / D_CONVERT_MONEY);
	}
	
	/**
	 * 功能描述：li换算为元
	 * @author Baomz
	 * @date 2016年6月4日 下午5:28:50
	 * @param @param li
	 * @param @return 
	 * @return String
	 */
	public static String li2yuan(String li) {
		long li_ = Long.parseLong(li);
		return li2yuan(li_);	
	}

}
