package com.urt.Ability.esim.esimUnicom;

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
import com.urt.Ability.unicom.vo.EditNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * 切换通信计划
 * @author liumh8
 *
 */
@Service("esimEditNetworkAccessConfigService")
public class EditNetworkAccessConfigService extends UnicomWsService{
   
	private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;
    private XWSSProcessorFactory processorFactory;    
    @Override
    @PostConstruct
	public void init() throws SOAPException, MalformedURLException,
			XWSSecurityException {
		connectionFactory = SOAPConnectionFactory.newInstance();
		messageFactory = MessageFactory.newInstance();
		processorFactory = XWSSProcessorFactory.newInstance();
		//System.setProperty("javax.net.ssl.trustStore", "jssecacerts");
	}
	
	public SOAPConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(SOAPConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public MessageFactory getMessageFactory() {
		return messageFactory;
	}

	public void setMessageFactory(MessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}

	public XWSSProcessorFactory getProcessorFactory() {
		return processorFactory;
	}

	public void setProcessorFactory(XWSSProcessorFactory processorFactory) {
		this.processorFactory = processorFactory;
	}

	@Override
	SOAPMessage secureMessage(SOAPMessage message, final String username,
			final String password) throws IOException, XWSSecurityException {
		CallbackHandler callbackHandler = new CallbackHandler() {
            public void handle(Callback[] callbacks)throws UnsupportedCallbackException {
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
            System.out.println(System.getProperty("SecurityPolicy.xml"));
            policyStream = getClass().getResourceAsStream("SecurityPolicy.xml");
            processor = processorFactory.createProcessorForSecurityConfiguration(policyStream,callbackHandler);
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

	/**
	 * 
	 * @param iccid	设备标示
	 * @param uid	用户标识
	 * @param version	版本号
	 * @param nacId	通信计划
	 * @return
	 * @throws SOAPException
	 */
	@Override
	SOAPMessage createRequest(Object... args) throws SOAPException,IllegalArgumentException {
		String iccid = null;
		String uid = null;
		String version = null;
		String nacId = null;
		String licenseKey = null;
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		
		iccid = (String) args[0];
		uid = (String) args[1];
		nacId = (String) args[2];
		version = getVersion();
		licenseKey = getLicenseKey();
		SOAPMessage message = messageFactory.createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_EDITNETWORKACCESSCONFIG);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("EditNetworkAccessConfigRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", uid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "iccid", iccid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "nacId", nacId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        
        return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		try {
			EditNetworkAccessConfigResponse response = new EditNetworkAccessConfigResponse();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name terminalResponseName = envelope.createName("EditNetworkAccessConfigResponse", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	        SOAPBodyElement parentElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(terminalResponseName).next();
	        
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
	        String effectiveDate = getElementValue(envelope, parentElement, "effectiveDate");
	        response.setEffectiveDate(effectiveDate);

	        return response; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
		UnicomWsService client = new EditNetworkAccessConfigService();
    	client.init();
    	client.setUrl("https://api.10646.cn/ws/service/networkaccess");
    	client.setUsername("fuhp3AA");
    	client.setPasswd("abc123456");
    	client.setLicenseKey("5e546edf-2016-400b-89a1-e1e630048140");
    	client.setVersion("1.0");
    	SOAPMessage soapMessage = client.call("89860116770002311835","lmh-test-EditNetworkAccessConfig","99318");
    	EditNetworkAccessConfigResponse response = (EditNetworkAccessConfigResponse) client.parseMessage(soapMessage);
    	System.out.println(response);
	}

}
