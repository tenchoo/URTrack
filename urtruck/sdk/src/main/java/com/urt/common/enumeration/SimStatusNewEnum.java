/** 
 * Project Name:LAOAPI-SDK 
 * File Name:SimStatusNewEnum.java 
 * Package Name:com.lenovo.LAOAPI.common.enumeration 
 * Date:2017年9月13日上午11:32:07 
 * Copyright (c) 2017, liuxl34@lenovo.com All Rights Reserved. 
 * 
 */
package com.urt.common.enumeration;

/**
 * ClassName: SimStatusNewEnum <br/>
 * Function: Sim卡状态管理枚举类. <br/>
 * Reason: 对CardState枚举类进行进行覆盖，针对CMP2.0项目使用此类. <br/>
 * date: 2017年9月13日 上午11:32:07 <br/>
 * 
 * @author liuxl
 * @version
 * @since JDK 1.7
 */
public enum SimStatusNewEnum {
	JIHUOQI(1, "激活期"), TINGYONGQI(2, "停用期"), DAIJIHUOQI(3, "待激活期"), QINGCHUQI(4, "清除期"), CESHIQI(5, "测试期"), KAISHI(6,
			"开始"), KUCUN(7, "库存"), SHIXIAO(8, "失效"), YITIHUAN(9, "已替换");

	int index;

	String name;

	private SimStatusNewEnum(int index, String name) {
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
		for( SimStatusNewEnum status : SimStatusNewEnum.values()){
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

