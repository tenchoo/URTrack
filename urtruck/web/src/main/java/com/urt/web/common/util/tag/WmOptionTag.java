package com.urt.web.common.util.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.urt.common.util.ReflectionUtil;
import com.urt.interfaces.authority.TagService;
import com.urt.web.common.util.StringUtil;

public class WmOptionTag extends BaseTag{
	private String typeId;
	private List<?> list;			//结果集
	private String bindSql;	//SQL
	private String value;
	private String valueKey;			//目标ID
	private String textKey;				//目标值
	private String isFull;			//是否带“请选择”

	private  TagService tagService;
	
	public void doTag() throws IOException {
	//	super.doTag();
		option();

	}
	
	/**
	 * 取select下拉框的option
	 */
	public void option() throws IOException {
		JspWriter out = super.getJspContext().getOut();
		String option = "";
        //typeId
		if(typeId != null && value != null && !"".equalsIgnoreCase(value) && isFull != null) {
			//从配置表取值的，有默认值的，前台配置请选择
			option = getOptionsByTypeId(typeId, value, Boolean.parseBoolean(isFull));
		}else if (typeId != null && value !=null && !"".equalsIgnoreCase(value)){
			//从配置表取值的，有默认值的，默认带请选择
			option = getOptionsByTypeId(typeId, value, true);
		} else if(typeId != null && isFull != null) {
			//从配置表取值的，无默认值的，前台配置请选择
			option = getOptionsByTypeId(typeId, null, Boolean.parseBoolean(isFull));
		} else if(typeId != null) {
			//从配置表取值的，无默认值的，默认带请选择
			option = getOptionsByTypeId(typeId, null, true);
		}
		out.println(option);
	}


	public String getOptionsByTypeId(String typeId, String value, boolean isFull) {
		 List<Map> list = setNewsService().getOptionsByTypeId(typeId, null,null);
		return getOptions(list, "MENU_CODE", "MENU_NAME", value, isFull);
	}

    public String getOptionsBySql(String bindSql, String value, boolean isFull) {
        String regex = "(SELECT | FROM)";
        String sqlstr = bindSql.toUpperCase().split(regex)[1];
        String[] params = StringUtil.replaceBlank(sqlstr).split(",");
        List<?> list = tagService.getOptions(bindSql);
        return getOptions(list, params[0], params[1], value, isFull);
    }

    public String getOptionsBySqlAndCondition(String bindSql, String sqlCondition, String value,  boolean isFull) {
        String regex = "(SELECT | FROM)";
        String sqlstr = bindSql.toUpperCase().split(regex)[1];
        String[] params = StringUtil.replaceBlank(sqlstr).split(",");
        List<?> list = tagService.getOptionsBySql(bindSql, sqlCondition);
        return getOptions(list, params[0], params[1], value, isFull);
    }


    public static String getOptions(List list, String valueKey, String textKey, String value, boolean isFull) {
        return list2Options(list, valueKey, textKey, value, isFull);
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
	public static String list2Options(List list, String valueKey, String textKey, String value, boolean isFull) {
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
				opValue = (String)map.get(valueKey);
				opText = (String)map.get(textKey);
			} else {
				opValue = (String) ReflectionUtil.getBeanFieldValue(obj, valueKey);
				opText = (String)ReflectionUtil.getBeanFieldValue(obj, textKey);
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getBindSql() {
		return bindSql;
	}

	public void setBindSql(String bindSql) {
		this.bindSql = bindSql;
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


	
    public TagService setNewsService() {
        PageContext pageContext = (PageContext) this.getJspContext();
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        return (TagService) wac.getBean("tagService");
    }
}
