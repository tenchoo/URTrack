package com.urt.Ability.unicom;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.collect.QueryTelByIccid;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Service("queryTelByIccidCUCC")
public class QueryTelByIccidCUCC extends QueryTelByIccid{
	Logger log = Logger.getLogger(getClass());
	@Autowired
	GetTerminalDetailsService getTerminalDetailsService;
	@Autowired
	GetTerminalRatingService getTerminalRatingService;
	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		ResultMsg msg=new ResultMsg();
		SOAPMessage request = null;
		SOAPMessage response = null;
		try {
			List<String> list = new ArrayList<String>();
	    	list.add(iccid);
			request = getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
			request = getTerminalDetailsService.secureMessage(request, getTerminalDetailsService.getUsername(), getTerminalDetailsService.getPasswd());
			ByteArrayOutputStream bin=new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			System.out.println("");
			SOAPConnection connection = getTerminalDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request, new URL(getTerminalDetailsService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			response.writeTo(bout);
			GetTerminalDetailsResponse responseResult = (GetTerminalDetailsResponse) getTerminalDetailsService.parseMessage(response);
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOutMessage(bout.toString().substring(0, 2000));
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0");          //0成功1失败
				msg.setTel(responseResult.getList().get(0).getMsisdn());
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :" + fault.getFaultString());
				msg.setOutMessage(bout.toString());
				msg.setOpeartorsDealCode(fault.getFaultCode());
				msg.setOpeartorsDealRst("1"); 			//0成功1失败
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("系统异常");
			e.printStackTrace();
		}
		return msg;
	}
	@Override
	protected ResultMsg queryOperatorPlan(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		ResultMsg msg=new ResultMsg();
		SOAPMessage request = null;
		SOAPMessage response = null;
		try {
			List<String> list = new ArrayList<String>();
	    	list.add(iccid);
			request = getTerminalRatingService.createRequest(list,"lmh-test-terminal-detail");
			request = getTerminalRatingService.secureMessage(request, getTerminalRatingService.getUsername(), getTerminalRatingService.getPasswd());
			ByteArrayOutputStream bin=new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			System.out.println("");
			SOAPConnection connection = getTerminalRatingService.getConnectionFactory().createConnection();
			response = connection.call(request, new URL(getTerminalRatingService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			response.writeTo(bout);
			GetTerminalRatingResponse responseResult = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(response);
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOutMessage(bout.toString().substring(0, 2000));
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0");          //0成功1失败
				List<TerminalRatingDetail> list2 = responseResult.getList();
				List<Map<String, Object>> plans=new ArrayList<Map<String,Object>>();
				for(TerminalRatingDetail detail:list2){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("planCode", detail.getRatePlanName());
					map.put("endTime", detail.getExpirationDate());
					plans.add(map);
				}
				msg.setPlans(plans);
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :" + fault.getFaultString());
				msg.setOutMessage(bout.toString());
				msg.setOpeartorsDealCode(fault.getFaultCode());
				msg.setOpeartorsDealRst("1"); 			//0成功1失败
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("系统异常");
			e.printStackTrace();
		}
		return msg;
	}

}
