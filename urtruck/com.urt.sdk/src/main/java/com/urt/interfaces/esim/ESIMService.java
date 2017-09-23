package com.urt.interfaces.esim;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import com.urt.dto.esim.ChangeOperDTO;
import com.urt.dto.esim.EsimAccountInfo;
import com.urt.dto.esim.LaoEsimGoodsPlanDto;






public interface ESIMService {
	/*
	 * 3.1 获取lenovoID的账户流量信息
	 * 调用联通的查询流量接口 来更新表LAO_ESIM_DETAIL 和 LAO_ESIM_FLOWINFO
	 */
	EsimAccountInfo getAccountInfo(EsimAccountInfo flowInfo);
	
	/*
	 * 加入共享组
	 * 在表LAO_ESIM_DETAIL 中插入一条记录就ok了
	 */
	boolean addGroup(String lenovoId,String imei);
	/*
	 * 产品查询
	 * 将表LAO_ESIM_GOODS_PLAN 中的数据显示出来
	 */
	List<Map<String, String>> queryGoods();
	/*
	 * 退出共享组 
	 * 调用联通的查询流量接口 来更新表LAO_ESIM_DETAIL 和 LAO_ESIM_FLOWINFO
	 */
	boolean outGroup(String lenovoId,String imei);
	/*
	 * 购买流量包产品
	 * 和切换apn接口 一样需要调用联通全球esim接口实现
	 */
	boolean buyGoods(String lenovoId,String imei,String goodsId) throws Exception, SOAPException;
	/*
	 * 切换apn
	 * 需要调用联通的全球esim接口来实现
	 */
	boolean changeApn(String lenovoId,String imei,String iccid) throws Exception;
	
	public boolean getReshFlow(String lenovoId,String imei);
		
    public String givenFlow(String givenLenovoId,String bGivenLenovoId,String operator,String flowSize);
    
    public boolean loginOut(String lenovoId,String imei);
}
