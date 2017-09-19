package com.urt.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


public class ReflectionUtil {
    public static boolean isShow = true;	//是否启用

    /**
     * 获得一个对象的属性值
     * @param obj 目标对象
     */
    public static void getFieldValue(Object obj){
        if (isShow) getFieldValue(obj, isShow);
    }



    /**
     * 获得一个对象的属性值
     * @createTime:2012-7-4
     * @param obj 目标对象
     */
    private static void getFieldValue(Object obj, boolean isShow){
        Class cls = obj.getClass();
        Method[] methods = cls.getDeclaredMethods();//获得类的方法集合
        StringBuffer buffer = new StringBuffer();
        try {
            for (Method method : methods) {
                if(method!=null && method.getName().startsWith("get")){
                    Object value = method.invoke(obj, null);
                    value = method.getName().substring(3)+":"+value+",";
                    buffer.append(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(cls.toString()+"["+buffer.toString()+"]");

    }

    /**
     * 通过反射获取javaBean中字段值
     * @param bean
     * @param fieldName
     * @return
     */
    public static Object getBeanFieldValue(Object bean, String fieldName) {
        Field[] fields = bean.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Object obj = null;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            if (field.getName().equals(fieldName)) {
                try {
                    obj = field.get(bean);
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }
            }
        }
        return obj;
    }
	/**
	 *
	 * @param list
	 * @param valueKey
	 * @param textKey
	 * @param value
	 * @param isFull
	 * @return
	 */
	public static String toMap(List list, String valueKey, String textKey) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String opValue = "";
			String opText = "";
			Object obj = list.get(i);
			if(obj instanceof Map) {
				Map map = (Map)obj;
				opValue = (String)map.get(valueKey);
				opText = (String)map.get(textKey);
			} else {
				opValue = (String) ReflectionUtil.getBeanFieldValue(obj, valueKey);
				opText = (String)ReflectionUtil.getBeanFieldValue(obj, textKey);
			}
		}
		return str.toString();
	}
}
