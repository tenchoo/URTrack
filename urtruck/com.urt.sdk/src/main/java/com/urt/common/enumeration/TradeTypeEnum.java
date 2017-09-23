/** 
 * Project Name:LAOAPI-SDK 
 * File Name:TradeTypeEnum.java 
 * Package Name:com.lenovo.LAOAPI.common.enumeration 
 * Date:2017年9月13日上午11:32:07 
 * Copyright (c) 2017, liuxl34@lenovo.com All Rights Reserved. 
 * 
 */
package com.urt.common.enumeration;

/**
 * ClassName: SimStatusNewEnum <br/>
 * Function: 业务类型枚举类. <br/>
 * Reason: 业务类型枚举类. <br/>
 * date: 2017年9月15日 上午11:32:07 <br/>
 * 
 * @author liuxl
 * @version
 * @since JDK 1.7
 */
public enum TradeTypeEnum {
	JIHUO(100, "卡激活"), BIANGENG(120, "产品套餐变更"), TINGJI(130, "卡停用"), KAIJI(140, "开机"),
	HUABO(150,"划拨卡"), QUJIHUO(160, "去激活卡"), JINRUKUCUN(170, "卡进入库存"), XUFEI(180, "卡续费"), SHIXIAO(190, "卡失效"),
	XIAOCHU(200, "清除/销卡"), BUHUANKA(210, "补换卡");

	int index;

	String name;

	private TradeTypeEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}
	
	public String getNameByIndex(int index) {
		for( TradeTypeEnum status : TradeTypeEnum.values()){
			if(status.getIndex() == index){
				return status.getName();
			}
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

}

