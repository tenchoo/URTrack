package com.urt.web.common.util.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import com.urt.common.util.ReflectionUtil;

public class ProductTypeSelectTag extends BaseTag{
	
	private String id;					
	private String name;
	private String bind;
	private String custId;
	private String value;
	private String valueKey;			//目标ID
	private String textKey;			//目标值
	private String isFull;	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueKey() {
		return valueKey;
	}
	public void setValueKey(String valueKey) {
		this.valueKey = valueKey;
	}
	public String getTextKey() {
		return textKey;
	}
	public void setTextKey(String textKey) {
		this.textKey = textKey;
	}
	public String getIsFull() {
		return isFull;
	}
	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}
	public void doTag() throws IOException {
		super.doTag();
		select();
	}
	public void select() throws IOException {
		JspWriter out = super.getJspContext().getOut();
		String selectStr = "";
		selectStr=getSelectStrByCustId(custId);
		out.println(selectStr);
	}
	public String getSelectStrByCustId(String custId) {
        List<?> list = tagService.queryProductTypeByCustId(custId);
		return getSelectStr(list, valueKey, textKey, value, true);
	}
	
	public String getSelectStr(List list, String valueKey, String textKey, String value, boolean isFull) {
			StringBuffer str = new StringBuffer();
			str.append("<select name='").append(name).append("'").append("onchange='change();'");
			//generateAttribute(sb);
			str.append(">");
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
					opValue = (String)map.get(valueKey);
					opText = (String)map.get(textKey);
				} else {
					opValue = (String) ReflectionUtil.getBeanFieldValue(obj, valueKey);
					opText = (String)ReflectionUtil.getBeanFieldValue(obj, textKey);
				}

				str.append("<option value='").append(opValue).append("'");
				if(value!=null && value.trim().length()>0) {
					if(value.equals(opValue)) {
						str.append(" selected ");
					}
				} else {
					if(i == 0) {
						str.append(" selected ");
					}
				}
				str.append(">").append(opText).append("</option>");
			}
			str.append("</select>");
			return str.toString();
	    }

}
