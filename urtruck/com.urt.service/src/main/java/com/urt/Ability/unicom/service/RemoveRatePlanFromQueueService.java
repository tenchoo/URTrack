package com.urt.Ability.unicom.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.annotation.PostConstruct;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * 删除用户资费计划
 * @author liumh8
 *
 */
@Service("RemoveRatePlanFromQueueService")
public class RemoveRatePlanFromQueueService extends UnicomWsService{

	@Value("${unicom.licenseKey}")
	private String licenseKey;
	@Value("${unicom.url.terminal}")
	private String url;
	@Value("${unicom.username}")
	private String username;
	@Value("${unicom.passwd}")
	private String passwd;
	@Value("${unicom.version}")
	private String version;
	
	@Override
	SOAPMessage secureMessage(SOAPMessage message, final String username,
			final String password) throws IOException, XWSSecurityException {
		CallbackHandler callbackHandler = new CallbackHandler() {
            public void handle(Callback[] callbacks)
                    throws UnsupportedCallbackException {
                for (int i = 0; i < callbacks.length; i++) {
                    if (callbacks[i] instanceof UsernameCallback) {
                        UsernameCallback callback = (UsernameCallback) callbacks[i];
                        callback.setUsername(username);
                    } else if (callbacks[i] instanceof PasswordCallback) {
                        PasswordCallback callback = (PasswordCallback) callbacks[i];
                        callback.setPassword(password);
                    } else {
                        throw new UnsupportedCallbackException(callbacks[i]);
                    }
                }
            }
        };
        InputStream policyStream = null;
        XWSSProcessor processor = null;
        try {
            policyStream = getClass().getResourceAsStream("SecurityPolicy.xml");
            processor = getProcessorFactory().createProcessorForSecurityConfiguration(policyStream,callbackHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (policyStream != null) {
                policyStream.close();
            }
        }
        ProcessingContext context = processor.createProcessingContext(message);
        return processor.secureOutboundMessage(context);
	}

	@Override
	SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {
		String messageId = null;
        String version = null;
        String licenseKey = null;
        String iccid = null;
        String queuePosition = null;
        String ratePlan = null;
        
        version = getVersion();
		licenseKey = getLicenseKey();
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		if (args.length > 1) {
			messageId = (String) args[1];
		}
		if (args.length > 2) {
			queuePosition = (String) args[2];
		}
		if (args.length > 3) {
			ratePlan = (String) args[3];
		} 
		
		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_REMOVERATEPLANFROMQUEUE);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("RemoveRatePlanFromQueueRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", messageId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "iccid",iccid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "queuePosition",queuePosition, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "ratePlan",ratePlan, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PostConstruct
	void init() throws SOAPException, MalformedURLException,
			XWSSecurityException {
		SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
		MessageFactory messageFactory = MessageFactory.newInstance();
		XWSSProcessorFactory processorFactory = XWSSProcessorFactory.newInstance();
		this.setConnectionFactory(connectionFactory);
		this.setMessageFactory(messageFactory);
		this.setProcessorFactory(processorFactory);
//		System.setProperty("javax.net.ssl.trustStore", "jssecacerts");
	}
	
	
	
	

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static void main(String[] args) throws Exception {
		
	/*	//清除队列1
		UnicomWsService client2 = new RemoveRatePlanFromQueueService();
		client2.init();
    	client2.setUrl("https://api.10646.cn/ws/service/termina");
    	client2.setUsername("zhaoyf8");
    	client2.setPasswd("zhaoyf4158460");
    	client2.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	client2.setVersion("1.0");
		
		SOAPMessage soapMessage2 = client2.call("89860616010024405058","lmh-test-EditTerminal","2","110WLW004085_PRE-IND_500M-0S");
		System.out.println("iccic==="+"89860616010024405058"+"队列1已经清除");*/
		
		//删除流量
		UnicomWsService client = new RemoveRatePlanFromQueueService();
		client.init();
    	client.setUrl("https://api.10646.cn/ws/service/termina");
    	client.setUsername("ITRandD");
    	client.setPasswd("o.s.wnYct23GrzqL");
    	client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	client.setVersion("1.0");
    	/*
    	 * 110WLW004085_MON-FIX_5120M-0S 月付
    	 * 110WLW004085_PRE-IND_500M-0S	预付
    	 * 
    	 */
    	
    	//查询流量
    	UnicomWsService query = new GetTerminalRatingService();
    	query.init();
//    	client.setUrl("https://api.10646.cn/ws/service/terminal");
    	query.setUrl("https://api.10646.cn/ws/service/terminal");
    	query.setUsername("ITRandD");
    	query.setPasswd("o.s.wnYct23GrzqL");
    	query.setVersion("1.0");
    	query.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	
    	
    	//充值送流量
		UnicomWsService chong = new EditTerminalRatingService();
		chong.init();
		chong.setUrl("https://api.10646.cn/ws/service/termina");
		chong.setUsername("ITRandD");
		chong.setPasswd("o.s.wnYct23GrzqL");
		chong.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
		chong.setVersion("1.0");
		
		//队尾添加默认包月流量
		UnicomWsService endChong = new QueueTerminalRatePlanService();
		endChong.init();
		endChong.setUrl("https://api.10646.cn/ws/service/termina");
		endChong.setUsername("ITRandD");
		endChong.setPasswd("o.s.wnYct23GrzqL");
		endChong.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
		endChong.setVersion("1.0");

 
		String[] list = new String[] { "89860617020000292486",	
				};
		for (String iccid : list) {
			SOAPMessage soapMessage1 = query.call(iccid, "20161021113999");
			GetTerminalRatingResponse ws = (GetTerminalRatingResponse) query
					.parseMessage(soapMessage1);
			if (ws != null && ws.getList() != null) {
				if (ws.getList().size() > 3) {
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>110WLW004085_MON-FIX_5120M-0S");
					SOAPMessage soapMessage2 = client.call(iccid,"lmh-test-EditTerminal","2","110WLW004085_MON-FIX_5120M-0S");
					
				}/*else if(ws.getList().size() == 3){
        				boolean big = true;
        				for (int i = 0; i < ws.getList().size(); i++) {
        					if(ws.getList().get(i).getRatePlanName().equals("110WLW004085_MON-FIX_5120M-0S")){
        						big = false;
        						break;
        					}
						}
        				if(big){
        					SOAPMessage soapMessage = chong.call(iccid,"lmh-test-EditTerminal","110WLW004085_MON-FIX_5120M-0S");
            				chong.parseMessage(soapMessage);
            	    	    System.out.println(iccid+">>>>>>>>>>>>>"+"110WLW004085_MON-FIX_5120M-0S");
        				}else{
        					SOAPMessage soapMessage = chong.call(iccid,"lmh-test-EditTerminal",ss);
            				chong.parseMessage(soapMessage);
            	    	    System.out.println(iccid+">>>>>>>>>>>>>"+ss);
        				}
        				
        			}
        			else if(ws.getList().size() == 2){
        				boolean big = false;
        				for (int i = 0; i < ws.getList().size(); i++) {
        					if(ws.getList().get(i).getRatePlanName().equals("110WLW004085_MON-FIX_5120M-0S")){
        						big = true;
        						break;
        					}
						}
        				SOAPMessage soapMessage2 = chong.call(iccid,"lmh-test-EditTerminal",ss);
        				chong.parseMessage(soapMessage2);
        				if(big){
        					SOAPMessage soapMessage = chong.call(iccid,"lmh-test-EditTerminal",ss);
            				chong.parseMessage(soapMessage);
        				}else{
        					SOAPMessage soapMessage = chong.call(iccid,"lmh-test-EditTerminal","110WLW004085_MON-FIX_5120M-0S");
            				chong.parseMessage(soapMessage);
        				}
        				
        			}
        			else if(ws.getList().size() == 1){
        				SOAPMessage soapMessage2 = chong.call(iccid,"lmh-test-EditTerminal",ss);
        				chong.parseMessage(soapMessage2);
        				SOAPMessage soapMessage = chong.call(iccid,"lmh-test-EditTerminal",ss);
        				chong.parseMessage(soapMessage);
        				
        				SOAPMessage end = endChong.call(iccid,"lmh-test-EditTerminal","110WLW004085_MON-FIX_5120M-0S");
        		    	client.parseMessage(end);
        			}*/
        		}
        		
        		
			}
    //	}
    	
    
  
    	
	}
}
