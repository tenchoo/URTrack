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
import com.urt.Ability.unicom.vo.EditTerminalRatingResponse;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * 修改用户续约模式
 * @author liumh8
 *
 */
@Service("editTerminalRatingService")
public class EditTerminalRatingService extends UnicomWsService{

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
	public
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
	public
	SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {
		String messageId = null;
        String version = null;
        String licenseKey = null;
        String iccid = null;
        String renewalMode = null;
        String renewalRatePlan = null;
        
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
			renewalRatePlan = (String) args[2];
		}
		if (args.length > 3) {
			renewalMode = (String) args[3];
		} else {
			renewalMode = "N";
		}
		
		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_EDITTERMINALRATING);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("EditTerminalRatingRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", messageId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "iccid",iccid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "renewalMode",renewalMode, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "renewalRatePlan",renewalRatePlan, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		try {
			EditTerminalRatingResponse response = new EditTerminalRatingResponse();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name parentName = envelope.createName("EditTerminalRatingResponse", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	        SOAPBodyElement parentElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(parentName).next();
	        String correlationId = getElementValue(envelope, parentElement, "correlationId");
	        response.setCorrelationId(correlationId);
	        String version = getElementValue(envelope, parentElement, "version");
	        response.setVersion(version);
	        String build = getElementValue(envelope, parentElement, "build");
	        response.setBuild(build);
	        String timestamp = getElementValue(envelope, parentElement, "timestamp");
	        response.setTimestamp(timestamp);
	        String iccid = getElementValue(envelope, parentElement, "iccid");
	        response.setIccid(iccid);
//	        String effectiveDate = getElementValue(envelope, parentElement, "effectiveDate");
//	        response.setEffectiveDate(effectiveDate);
	        String status = getElementValue(envelope, parentElement, "status");
	        response.setStatus(status);

	        return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) throws Exception{
		UnicomWsService client = new EditTerminalRatingService();
		client.init();
    	client.setUrl("https://api.10646.cn/ws/service/termina");
    	client.setUsername("develop");
    	client.setPasswd("123456");
    	client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	client.setVersion("1.0");
    	/*
    	 * 110WLW004085_MON-FIX_5120M-0S 月付
    	 * 110WLW004085_PRE-IND_500M-0S	预付
    	 * 
    	 */
    	SOAPMessage soapMessage = client.call("89860616010010314686","lmh-test-EditTerminal","110WLW004085_PRE-IND_5M");
//    	SOAPMessage soapMessage = client.call("89860616010010313845","lmh-test-EditTerminal","110WLW004085_MON-FIX_5120M-0S");

//    	client.call("89860616010010313845","lmh-test-EditTerminal","110WLW004085_PRE-IND_5M");
    	EditTerminalRatingResponse response = (EditTerminalRatingResponse) client.parseMessage(soapMessage);
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
