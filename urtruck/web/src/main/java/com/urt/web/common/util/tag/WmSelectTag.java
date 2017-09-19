package com.urt.web.common.util.tag;

import com.urt.common.util.ReflectionUtil;
import com.urt.web.common.util.StringUtil;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WmSelectTag extends BaseTag{
	private String id;					//select的id
	private String name;			//select的name
	private String typeId;
	private String param;
	private List<?> list;				//结果集
	private String bindSql;		//SQL
	private String value;
	private String valueKey;			//目标ID
	private String textKey;			//目标值
	private String isFull;			//是否带“请选择”

	public void doTag() throws IOException {
		super.doTag();
		select();

	}
	
	/**
	 * 取select下拉框的option
	 */
	public void select() throws IOException {
		JspWriter out = super.getJspContext().getOut();
		String selectStr = "";
        //普通list
		if(list != null && valueKey != null && textKey != null && value != null && !"".equalsIgnoreCase(value) && isFull != null) {
			//从list获取，有默认值的，前台配置请选择
			selectStr = getSelectStr(list, valueKey, textKey, value, Boolean.parseBoolean(isFull));
		} else if(list != null && valueKey != null && textKey != null && value != null && !"".equalsIgnoreCase(value)) {
			//从list获取，有默认值的，默认带请选择
			selectStr = getSelectStr(list, valueKey, textKey, value, true);
		} else if(list != null && valueKey != null && textKey != null && isFull != null) {
			//从list获取，无默认值的，前台配置请选择
			selectStr = getSelectStr(list, valueKey, textKey, null, Boolean.parseBoolean(isFull));
		} else if(list != null && valueKey != null && textKey != null) {
			//从list获取，无默认值的，前台配置请选择
			selectStr = getSelectStr(list, valueKey, textKey, null, true);
		}
        //typeId
		else if(typeId != null && param!=null && value != null && !"".equalsIgnoreCase(value) && isFull != null) {
			//从配置表取值的，有默认值的，前台配置请选择
			selectStr = getSelectStrByTypeId(typeId, param,value, Boolean.parseBoolean(isFull));
		}else if (typeId != null && param!=null && value !=null && !"".equalsIgnoreCase(value)){
			//从配置表取值的，有默认值的，默认带请选择
			selectStr = getSelectStrByTypeId(typeId,param, value, true);
		} else if(typeId != null && isFull != null) {
			//从配置表取值的，无默认值的，前台配置请选择
			selectStr = getSelectStrByTypeId(typeId, param, null, Boolean.parseBoolean(isFull));
		} else if(typeId != null) {
			//从配置表取值的，无默认值的，默认带请选择
			selectStr = getSelectStrByTypeId(typeId, param, null, true);
		}
        //sql
		else if(bindSql!=null && value != null && !"".equalsIgnoreCase(value) && isFull != null){
			selectStr = getSelectStrBySql(bindSql, value, Boolean.parseBoolean(isFull));
		} else if(bindSql!=null && value != null && !"".equalsIgnoreCase(value)) {
			selectStr = getSelectStrBySql(bindSql, value, true);
		} else if(bindSql!=null && isFull != null){
			selectStr = getSelectStrBySql(bindSql, null, Boolean.parseBoolean(isFull));
		} else if(bindSql!=null){
			selectStr = getSelectStrBySql(bindSql, null, true);
		}
		out.println(selectStr);
	}


	public String getSelectStrByTypeId(String typeId, String param,String value, boolean isFull) {
        List<?> list = tagService.getOptionsByTypeId(typeId, param,null);
		return getSelectStr(list, valueKey, textKey, value, isFull);
	}

    public String getSelectStrBySql(String bindSql, String value, boolean isFull) {
        String regex = "(SELECT | FROM)";
        String sqlstr = bindSql.toUpperCase().split(regex)[1];
        String[] params = StringUtil.replaceBlank(sqlstr).split(",");
        List<?> list = tagService.getOptions(bindSql);
        return getSelectStr(list, params[0], params[1], value, isFull);
    }


    public String getSelectStr(List list, String valueKey, String textKey, String value, boolean isFull) {
		StringBuffer str = new StringBuffer();
		str.append("<select name='").append(name).append("'");
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
			str.append(">").append(opText).append("</option>\n");
		}
		return str.toString();
    }


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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getBindSql() {
		return bindSql;
	}

	public void setBindSql(String bindSql) {
		this.bindSql = bindSql;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getIsFull() {
		return isFull;
	}

	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	
}
