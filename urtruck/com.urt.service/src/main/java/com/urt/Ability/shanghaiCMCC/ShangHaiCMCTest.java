package com.urt.Ability.shanghaiCMCC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.ShangHaiCMC.SI_ActivateAPN;
import com.urt.interfaces.ShangHaiCMC.SI_ActivateVoiceService;
import com.urt.interfaces.ShangHaiCMC.SI_ChangeSIMPhase;
import com.urt.interfaces.ShangHaiCMC.SI_OrderPackage;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolInfo;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolMembers;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolUsage;
/**
 * 测试上海移动端接口
 */
public class ShangHaiCMCTest {
	public static void main(String[] args) {
		//changSIMPhaseTest();
		//orderPackage();
		//activeAPN();
		//queryDatePool();
		//sI_QueryDateInfo();
		//queryLaoPoolUsage();
		//activeVoiceService();
		//SI_QueryDataPoolMembers poolMembers = new SI_QueryDataPoolMembersImpl();
		//poolMembers.queryDataPoolMembers(Request_ID, Request_Date_Time, EID, Pool_ID, Multi_Records)
		//queryTransactionResult();
		
	}
	/*private static void queryTransactionResult() {
		SI_QueryTransactionResult query = new SI_QueryTransactionResultImpl();
		String[] Old_Request_ID = {"c09924c09c034b1f83ad052ae078de14","c09924c09c034b1f83ad052ae078de14","c09924c09c034b1f83ad052ae078de14",
				"c09924c09c034b1f83ad052ae078de14","c09924c09c034b1f83ad052ae078de14","c09924c09c034b1f83ad052ae078de14",
				"c09924c09c034b1f83ad052ae078de14","c09924c09c034b1f83ad052ae078de14",
				"c09924c09c034b1f83ad052ae078de14","c09924c09c034b1f83ad052ae078de14",};
		
		String result = query.queryTransactionResult("13812341234", "c09924c09c034b1f83ad052ae078de14", Old_Request_ID, "20140711135051");
		System.out.println(result);
	}
	private static void activeVoiceService() {
		SI_ActivateVoiceService service = new SI_ActivateVoiceServiceImpl();
		String result = service.activeVoiceService("c09924c09c034b1f83ad052ae078de14", "13812341234", 
				"03", "13812341234", "01", "01", "02", "01", "01", "1024", "20140711135051");
		System.out.println(result);
	}
	private static void queryLaoPoolUsage() {
		SI_QueryDataPoolUsage si_QueryDataPoolUsageImpl = new SI_QueryDataPoolUsageImpl();
		String result = si_QueryDataPoolUsageImpl.queryDataPoolUsage("c09924c09c034b1f83ad052ae078de1412345678", "20170718164759", "c09924c09c034b1f83ad052ae078de1412345678", "12345678912345678900");
		System.out.println(result);
	}
	*//**
	 * 测试流量池查询
	 *//*
	private static void sI_QueryDateInfo() {
		//ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:META-INF/spring/applicationContext.xml");
		SI_QueryDataPoolInfo si_QueryDataPoolInfo = new SI_QueryDataPoolInfoIpml();
		String result = si_QueryDataPoolInfo.queryDataPoolInfo("reqID", "20170811135051", "10001");
			
		System.out.println(result);
	}
	*//**
	 * 流量池成员查询
	 *//*
	private static void queryDatePool() {
		List<String> Pool_ID = new ArrayList<>();
		Pool_ID.add("4444444444");
		Pool_ID.add("55555555");
		
		SI_QueryDataPoolMembers si_QueryDataPoolMembers = new SI_QueryDataPoolMembersImpl();
		String result = si_QueryDataPoolMembers.queryDataPoolMembers("c09924c09c034b1f83ad052ae078de1412345678", "20170718161259", "c09924c09c034b1f83ad052ae078de1412345678",
				Pool_ID, null);
		System.out.println(result);
	}
	*//**
	 * 更改数据业务和APN
	 *//*
	private static void activeAPN() {
		SI_ActivateAPN si_ActivateAPN = new SI_ActivateAPNImpl();
		//String result = si_ActivateAPN.activeStatusAndAPN("13073856748", "03", "c09924c09c034b1f83ad052ae078de14", "20140711135051", "01", "00");
		//System.out.println(result);
	}
	*//**
	 * 测试套餐订单管理
	 *//*
	private static void orderPackage() {
		SI_OrderPackage si_OrderPackage = new SI_OrderPackageImpl();
		String result = si_OrderPackage.orderPackage("138012345678","123", "123", 
				"1", "22", "12345678", "c09924c09c034b1f83ad052ae078de14", "20140711135051");
		System.out.println(result);
	}
	*//**
	 *测试变更 SIM 卡生命周期
	 *//*
	private static void changSIMPhaseTest() {
		测试数据
		 * {
	    "Request_ID": "c09924c09c034b1f83ad052ae078de1412345678",
	    "Request_Date_Time": "20170719112655",
	    "EID": "c09924c09c034b1f83ad052ae078de1412345678",
	    "MSISDN": "180551912561234"
		}
		SI_ChangeSIMPhase si_ChangeSIMPhase = new SI_ChangeSIMPhaseImpl();
		String result = si_ChangeSIMPhase.changeSIMPhase("c09924c09c034b1f83ad052ae078de1412345678", "20170719112655", "c09924c09c034b1f83ad052ae078de1412345678", "180551912561234");
		System.out.println(result);
	}*/
}
