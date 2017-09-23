package com.urt.common.util;

import java.util.List;

/**
 * @author cuishuo
 *list判断工具类
 */
public class ValEqualUtil {
	public ValEqualUtil(){}
	
	public static <T> boolean ListValEqul(List<T> list){
      
		@SuppressWarnings("unchecked")
		T temp = (T) "";
        for (int i = 0; i < list.size() - 1; i++)
        {
            temp = list.get(i);
            for (int j = i + 1; j < list.size(); j++)
            {
                if (temp.equals(list.get(j)))
                {
                    return true;
                }
            }
        }
        return false;
	}
	
	public static <T> boolean listEqulList(List<T> list,T temp){
		
        for (int i = 0; i < list.size() - 1; i++)
        {
            temp = list.get(i);
            for (int j = i + 1; j < list.size(); j++)
            {
                if (temp.equals(list.get(j)))
                {
                    return true;
                }
            }
        }
		
		return false;
		
	}
}
