package com.urt.web.common.util;

import java.util.List;
import java.util.Map;

import com.urt.common.util.ReflectionUtil;

public class SelectUtil {

    /**
     *
     * @param list
     * @param key
     * @param name
     * @param value
     * @param isFull
     * @return
     */
    public static String list2Options(List list, String key, String name, String value, boolean isFull) {
        StringBuffer str = new StringBuffer();
        if(isFull) {
            str.append("<option value=\"\">----请选择----</option>\n");
        }
        if(list == null || list.size() == 0) {
            return "";
        }
        for (int i = 0; i < list.size(); i++) {
            String opValue = "";
            String opText = "";
            Object obj = list.get(i);
            if(obj instanceof Map) {
                Map map = (Map)obj;
                opValue = (String)map.get(key);
                opText = (String)map.get(name);
            } else {
                opValue = (String) ReflectionUtil.getBeanFieldValue(obj, key);
                opText = (String)ReflectionUtil.getBeanFieldValue(obj, name);
            }
            str.append("<option value=\"" + opValue + "\" "+getIsSelected(value, opValue)+">" + opText + "</option>\n");
        }
        return str.toString();
    }

    public static String getIsSelected(String value, String selectValue) {
        String isSelected = "";
        if(value != null && selectValue.equals(value)) {
            isSelected = "selected=\"selected\"";
        }
        return isSelected;
    }
}
