package com.urt.Ability.unicom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.Ability.unicom.service.GetNetworkAccessConfigService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.po.ServiceStatus;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
/**
 * 联通卡状态查询
 * @author zhaoyf
 *
 */
@Service(value="userQueryCardStatusCUCC")
public class UserQueryCardStatusCUCC extends UserQueryCardStatus{
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private GetNetworkAccessConfigService getNetworkAccessConfigService;
	@Autowired
	private ServiceStatusExtMapper serviceStatusExtMapper;
	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;

	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String messageId = ZkGenerateSeq.get8IdSeq(SeqID.USER_ID);
		ResultMsg msg = new ResultMsg();
		SOAPMessage request = null;		
		SOAPMessage response = null;
		String nowApnName = null;
		GetNetworkAccessConfigResponse responseResult = null;
		GetTerminalDetailsResponse responseFirst = null;
		AccessConfig accessConfig = null;
		
		Long beginTime = System.currentTimeMillis();
		log.info("................激活前查询设备状态...................time:"
				+ beginTime);

		try {
			List<String> list = new ArrayList<String>();
			list.add(iccid);
			
			request= getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
			SOAPMessage createRequestFirst = getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
			request = getTerminalDetailsService.secureMessage(createRequestFirst, getTerminalDetailsService.getUsername(), getTerminalDetailsService.getPasswd());
			ByteArrayOutputStream binFirst=new ByteArrayOutputStream();
			request.writeTo(binFirst);
			msg.setInputMessage(binFirst.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			System.out.println("");
			
			SOAPConnection createConnection = getTerminalDetailsService.getConnectionFactory().createConnection();
			response = createConnection.call(request, new URL(getTerminalDetailsService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream boutFirst=new ByteArrayOutputStream();
			response.writeTo(boutFirst);
			responseFirst = (GetTerminalDetailsResponse) getTerminalDetailsService.parseMessage(response);
			String status = responseFirst.getList().get(0).getStatus();
			
			//wangxb20 20170606 区分月付单卡/月付灵活共享 和 预付单卡
			String accountId = responseFirst.getList().get(0).getAccountId();
			
			if(status != null){
				if("ACTIVATED_NAME".equals(status)&& "100273818".equals(accountId)){
					//调用apn接口
					request = getNetworkAccessConfigService.createRequest(list,"lmh-test-terminal-detail");
					SOAPMessage createRequest = getNetworkAccessConfigService.createRequest(list,messageId);
					request = getNetworkAccessConfigService.secureMessage(createRequest,getNetworkAccessConfigService.getUsername(),getNetworkAccessConfigService.getPasswd());
					ByteArrayOutputStream bin=new ByteArrayOutputStream();
					request.writeTo(bin);
					msg.setInputMessage(bin.toString());	
					System.out.println("Request: ");
					request.writeTo(System.out);
					System.out.println("");
					SOAPConnection connection = getNetworkAccessConfigService.getConnectionFactory().createConnection();
					response = connection.call(request, new URL(getNetworkAccessConfigService.getUrl()));
					System.out.println("Response: ");
					response.writeTo(System.out);
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					response.writeTo(bout);
					responseResult = (GetNetworkAccessConfigResponse) getNetworkAccessConfigService.parseMessage(response);
					
					if (responseResult != null) {
						String jsonStr = JSON.toJSONString(responseResult);
						List<AccessConfig> list1 = responseResult.getList();
						if (list1 != null && list1.size() > 0){
							accessConfig = list1.get(0);
						}							
						if (accessConfig == null) {
							log.info("获取资费计划异常-----------");
							msg.setStatus("error");
						} else {
							nowApnName = accessConfig.getApnNameByNacid();
							ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(nowApnName);
							if(selectByStatechangeId != null){
								msg.setStatus(selectByStatechangeId.getStateCode());
							}else{
								log.info("没有相匹配的状态");
								msg.setStatus("error");
							}			
						}
						msg.setSuccess(true);
						msg.setOutMessage(jsonStr);
						msg.setOpeartorsDealCode("success");
						msg.setOpeartorsDealRst("0");         //0成功1失败
					}else{
						msg.setOpeartorsDealCode("failed");
						msg.setOpeartorsDealRst("1"); 		//0成功1失败
						ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(status);
						msg.setStatus(selectByStatechangeId.getStateCode());
						log.info("调用apn接口出错------");						
					}					
					
				}else{
					msg.setOpeartorsDealRst("0");
					msg.setOpeartorsDealCode("success");
					ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(status);
					if(selectByStatechangeId != null){
						msg.setStatus(selectByStatechangeId.getStateCode());
					}else{
						log.info("没有相匹配的状态");
						msg.setStatus("error");
					}					
				}
			}else{
				msg.setStatus("error");
				log.info("查询状态接口调用失败--------");
			}

		} catch (IOException | XWSSecurityException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("................激活前查询设备状态.......... Total cost: "
				+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return msg;
	}
	


}
