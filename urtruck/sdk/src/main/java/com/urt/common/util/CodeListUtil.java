package com.urt.common.util;

import java.util.List;
import java.util.Map;

public class CodeListUtil {
	/**
	 * 功能描述： 获取码表中名称放入List<map>中
	 * @author wangfu
	 * @date 2016年6月20日 上午11:29:21
	 * @param @param list 传入前台的list
	 * @param @param tolist 码表的list
	 * @param @param str 需要转译
	 * @param @param name 传入前台展示名称
	 * @param @return 
	 * @return List<Map>
	 */
	public static List<Map<String,Object>> AddMapName(List<Map<String,Object>> list,List<Map> tolist,String str,String name){
		if(list.size()<1||tolist.size()<1||"".equals(str))
			return list;
		for (int i = 0; i < list.size(); i++) {
			String custType=list.get(i).get(str).toString();
			for(int j=0;j<tolist.size();j++){
			  String custType1=	tolist.get(j).get("MENU_CODE").toString();
			  if(custType.equals(custType1)){
				  list.get(i).put(name, tolist.get(j).get("MENU_NAME").toString());
			  }
			}
		}
		return list;
	}
}
