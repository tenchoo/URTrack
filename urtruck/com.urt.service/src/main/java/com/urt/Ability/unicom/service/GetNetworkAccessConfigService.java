package com.urt.Ability.unicom.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
import com.urt.Ability.unicom.util.NetworkAccessConfigEnum;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * 获取iccids所属通信计划
 * @author liumh8
 *
 */
@Service("getNetworkAccessConfigService")
public class GetNetworkAccessConfigService extends UnicomWsService{

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
	public SOAPMessage secureMessage(SOAPMessage message, final String username,
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

	@SuppressWarnings("unchecked")
	@Override
	public SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {
		
		List<String> iccids = null;
        String messageId = null;
        String version = null;
        String licenseKey = null;
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		
		if (args.length > 0) {
			iccids = (List<String>) args[0];
		}
		if (iccids == null || iccids.size() == 0) {
			throw new IllegalArgumentException();
		}
		
		if (args.length > 1) {
			messageId = (String) args[1];
		}
		version = getVersion();
		if (args.length > 2) {
			version = (String) args[2];
		}
		licenseKey = getLicenseKey();
		if (args.length > 3) {
			licenseKey = (String) args[3];
		}
		
		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_GETNETWORKACCESSCONFIG);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("GetNetworkAccessConfigRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", messageId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		for (String iccid : iccids) {
			addElement(envelope, parentElement, "iccid",iccid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		}
        
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		GetNetworkAccessConfigResponse response = new GetNetworkAccessConfigResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name parentName = envelope.createName("GetNetworkAccessConfigResponse", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	        SOAPBodyElement parentElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(parentName).next();
	        
	        String correlationId = getElementValue(envelope, parentElement, "correlationId");
	        response.setCorrelationId(correlationId);
	        
	        String version = getElementValue(envelope, parentElement, "version");
	        response.setVersion(version);
	        
	        String build = getElementValue(envelope, parentElement, "build");
	        response.setBuild(build);
	        
	        String timestamp = getElementValue(envelope, parentElement, "timestamp");
	        response.setTimestamp(timestamp);
	        
	        List<AccessConfig> configList = new ArrayList<AccessConfig>();
	        NodeList list = getElementList(envelope, parentElement, "nacIds");
	        if (list != null && list.getLength() > 0) {
	        	for (int i = 0; i < list.getLength(); i++) {
	    			Node node = list.item(i);
	    			NodeList childList = node.getChildNodes();
	    			AccessConfig config = new AccessConfig();
	    			for (int j = 0; j < childList.getLength(); j++) {
	    				Node child = childList.item(j);
	    				String name =child.getLocalName(); 
	    				String value = child.getTextContent();
						if ("nacId".equalsIgnoreCase(name)) {
							config.setNacId(value);
						}
						if ("iccid".equalsIgnoreCase(name)) {
							config.setIccid(value);
						}
	    			}
	    			configList.add(config);
	    		}
			}
	        
	        response.setList(configList);
	        System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
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
		//System.setProperty("javax.net.ssl.trustStore", "jssecacerts");
	}
	
	public static void main(String[] args) throws Exception{
		UnicomWsService client = new GetNetworkAccessConfigService();
    	client.init();
    	client.setUrl("https://api.10646.cn/ws/service/networkaccess");
    	client.setUsername("develop");
    	client.setPasswd("123456");
    	client.setVersion("1.0");
    	client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	List<String> list = new ArrayList<String>();
//    	list.add("8986061501000889176");
//    	list.add("8986061501000889175");
    	list.add("89860616010010314686");
//    	list.add("89860616010010260699");
    	SOAPMessage soapMessage = client.call(list,"lmh-test");
    	GetNetworkAccessConfigResponse response = (GetNetworkAccessConfigResponse) client.parseMessage(soapMessage);
    	List<AccessConfig> configList = response.getList();
    	for (AccessConfig config : configList) {
			System.out.println(config.getIccid() + "@" + config.getNacId());
			if (config.getNacId().equalsIgnoreCase(NetworkAccessConfigEnum.APN1.getNacId())) {
				System.out.println("ok");
			}
		}
    	
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
