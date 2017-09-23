package com.urt.web.common.util.tag;

import javax.servlet.jsp.JspWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WmTranslateTag extends BaseTag{
	private String typeId;
	/*private String bindSql;*/	//SQL
	private String value;
	private String custId;
	
	
	public void doTag() throws IOException {
		super.doTag();
		translate();
	}
	
	/**
	 * 翻译名称
	 */
	public void translate() throws IOException {
        JspWriter out = super.getJspContext().getOut();
		String resultStr = "";
		//普通list
		List<Map> list = tagService.getOptionsByTypeId(typeId, value,custId);
		resultStr=list.get(0).get("STATIC_NAME").toString();
		/*if(typeId != null && value != null && !"".equalsIgnoreCase(value) ) {
			resultStr = translateByTypeId(typeId, value);
		}else if(bindSql!=null && value != null && !"".equalsIgnoreCase(value)) {
			resultStr =  translateBySql(bindSql, value);
		}*/

		out.println(resultStr);
	}
	
	/**
	 * 去码表翻译名称
	 * @param typeId
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String translateByTypeId(String typeId, String value) {
		String exp = "";
		try {
			exp = tagService.translateByTypeId(typeId, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exp;
	}
	
	/**
	 * 根据id去指定表翻译名称
	 * @param bindSql   SQL
	 * @param translateValue    要求翻译的值
	 * @return
	 * @throws Exception
	 */
	public String translateBySql(String bindSql, String translateValue){
		String exp = "";
		try {
			exp = tagService.translateBySql(bindSql, translateValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exp;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/*public String getBindSql() {
		return bindSql;
	}

	public void setBindSql(String bindSql) {
		this.bindSql = bindSql;
	}*/

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
}
