package com.urt.Ability.unicom.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.GetTerminalUsageDataDetailsResponse;
import com.urt.Ability.unicom.vo.UsageDetail;
import com.urt.Ability.unicom.vo.WsResponse;
/**
 * 资费详情
 * @author zhaoyf
 *
 */
@Service("GetTerminalUsageDataDetailsService")
public class GetTerminalUsageDataDetailsService extends UnicomWsService{
	
	@Value("${unicom.licenseKey}")
	private String licenseKey;
	@Value("${unicom.url.networkaccess}")
	private String url;
	@Value("${unicom.username}")
	private String username;
	@Value("${unicom.passwd}")
	private String passwd;
	@Value("${unicom.version}")
	private String version;

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
		//System.setProperty("javax.net.ssl.trustStore", "jssecacerts");
	}
	@Override
	public SOAPMessage secureMessage(SOAPMessage message, final String username,
			final String password) throws IOException, XWSSecurityException {
		CallbackHandler callbackHandler = new CallbackHandler() {
            public void handle(Callback[] callbacks)
                    throws UnsupportedCallbackException {
                for (int i = 0; i < callbacks.length; i++) {
                    if (callbacks[i] instanceof UsernameCallback) {
                        UsernameCallback callback = (UsernameCallback) callbacks[i];
                        if (StringUtils.isNotBlank(username)) {
                        	callback.setUsername(username);
						}
                    } else if (callbacks[i] instanceof PasswordCallback) {
                        PasswordCallback callback = (PasswordCallback) callbacks[i];
                        if (StringUtils.isNotBlank(password)) {
                        	callback.setPassword(password);
						}
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
	public SOAPMessage createRequest(Object... args) throws SOAPException, IllegalArgumentException {
		String iccid = null;
		String messageId = null;
        String version = null;
        String licenseKey = null;
        String cycleStartDate = null;
        String pageNumber = null;
        
        
        version = getVersion();
		licenseKey = getLicenseKey();
//		cycleStartDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		if (args == null) {
			throw new IllegalArgumentException();
		}
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		if (args.length > 1) {
			messageId = (String) args[1];
		}
		if (args.length > 2) {
			pageNumber = (String) args[2];
		}
		if (args.length > 3) {
			cycleStartDate = (String) args[3];
		}


		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_GETTERMINALUSAGEDATA);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("GetTerminalUsageDataDetailsRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", messageId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "iccid",iccid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "cycleStartDate",cycleStartDate, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "pageNumber",pageNumber, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		GetTerminalUsageDataDetailsResponse response = new GetTerminalUsageDataDetailsResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name UsageResponseName = envelope.createName("GetTerminalUsageDataDetailsResponse", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	        SOAPBodyElement parentElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(UsageResponseName).next();
	        
	        String correlationId = getElementValue(envelope, parentElement, "correlationId");
	        response.setCorrelationId(correlationId);
	        
	        String version = getElementValue(envelope, parentElement, "version");
	        response.setVersion(version);
	        
	        String build = getElementValue(envelope, parentElement, "build");
	        response.setBuild(build);
	        
	        String timestamp = getElementValue(envelope, parentElement, "timestamp");
	        response.setTimestamp(timestamp);
	        
	        String totalPages = getElementValue(envelope, parentElement, "totalPages");
	        response.setTotalPages(totalPages);
	        
	        
	        List<UsageDetail> configList = new ArrayList<UsageDetail>();
	        NodeList list = getElementList(envelope, parentElement, "usageDetails");
	        if (list != null && list.getLength() > 0) {
	        	for (int i = 0; i < list.getLength(); i++) {
	    			Node node = list.item(i);
	    			NodeList childList = node.getChildNodes();
	    			UsageDetail detail = new UsageDetail();
	    			for (int j = 0; j < childList.getLength(); j++) {
	    				Node child = childList.item(j);
	    				String name =child.getLocalName(); 
	    				String value = child.getTextContent();
	    				if("iccid".equalsIgnoreCase(name)){
	    					detail.setIccid(value);
	    				}
						if ("cycleStartDate".equalsIgnoreCase(name)) {
							detail.setCycleStartDate(value);
						}
						if ("terminalId".equalsIgnoreCase(name)) {
							detail.setTerminalId(value);
						}
						if ("endConsumerId".equalsIgnoreCase(name)) {
							detail.setEndConsumerId(value);
						}
						if ("customer".equalsIgnoreCase(name)) {
							detail.setCustomer(value);
						}
						if ("billable".equalsIgnoreCase(name)) {
							detail.setBillable(value);
						}
						if ("zone".equalsIgnoreCase(name)) {
							detail.setZone(value);
						}
						if ("sessionStartTime".equalsIgnoreCase(name)) {
							detail.setSessionStartTime(value);
						}
						if ("duration".equalsIgnoreCase(name)) {
							detail.setDuration(value);
						}
						if ("dataVolume".equalsIgnoreCase(name)) {
							detail.setDataVolume(value);
						}
						if ("countryCode".equalsIgnoreCase(name)) {
							detail.setCountryCode(value);
						}
						if ("serviceType".equalsIgnoreCase(name)) {
							detail.setServiceType(value);
						}
						
	    			}
	    			configList.add(detail);
	    		}
			}

	        response.setList(configList);
	        return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) throws Exception{
    	UnicomWsService client = new GetTerminalUsageDataDetailsService();
    	client.init();
    	client.setUrl("https://api.10646.cn/ws/service/terminal");
    	client.setUsername("develop");
    	client.setPasswd("123456");
    	client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	client.setVersion("1.0");
    	
    	SOAPMessage soapMessage = client.call("89860616010024409811","11111111111","0","2016-09-01");
    	GetTerminalUsageDataDetailsResponse response =(GetTerminalUsageDataDetailsResponse) client.parseMessage(soapMessage);
    	System.out.println(response);
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



}
