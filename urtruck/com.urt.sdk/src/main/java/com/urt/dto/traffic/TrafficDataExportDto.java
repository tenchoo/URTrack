package com.urt.dto.traffic;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class TrafficDataExportDto {

	@NumberFormat(style = Style.NUMBER)
	private Long channelCustId;

	@NumberFormat(style = Style.NUMBER)
	private Integer useCount1;

	@NumberFormat(style = Style.NUMBER)
	private Integer useCount2;

	private String type;

	private String month;

	private String value1;

	private String operatorId;

	private String goodsId;

	private String value2;

	public Long getChannelCustId() {
		return channelCustId;
	}

	public void setChannelCustId(Long channelCustId) {
		this.channelCustId = channelCustId;
	}

	public Integer getUseCount1() {
		return useCount1;
	}

	public void setUseCount1(Integer useCount1) {
		this.useCount1 = useCount1;
	}

	public Integer getUseCount2() {
		return useCount2;
	}

	public void setUseCount2(Integer useCount2) {
		this.useCount2 = useCount2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		value1 = "-1".equals(value1) ? null : value1;
		this.value1 = value1;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		value2 = "-1".equals(value2) ? null : value2;
		this.value2 = value2;
	}

}
