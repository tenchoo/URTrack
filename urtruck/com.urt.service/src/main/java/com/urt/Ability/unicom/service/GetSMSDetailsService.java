package com.urt.Ability.unicom.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
import com.urt.Ability.unicom.vo.GetModifiedSMSResponse;
import com.urt.Ability.unicom.vo.GetSMSDetailsResponse;
import com.urt.Ability.unicom.vo.SmsMessage;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * @author haosun1 返回有关一条或多条短信的详细信息。
 * 
 */
@Service("getSMSDetailsService")
public class GetSMSDetailsService extends UnicomWsService {
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
			processor = getProcessorFactory()
					.createProcessorForSecurityConfiguration(policyStream,
							callbackHandler);
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

	public static void main(String[] args) throws SOAPException,
			XWSSecurityException, IOException {
		UnicomWsService client = new GetSMSDetailsService();
		client.init();
		client.setUrl("https://api.10646.cn/ws/service/networkaccess");
		client.setUsername("develop");
		client.setPasswd("123456");
		client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
		client.setVersion("1.0");

		List<String> list = new ArrayList<String>();
		list.add("60809618");
		SOAPMessage soapMessage = client.call("lmh-test-TransferGlobalSim",list);
		GetSMSDetailsResponse response = (GetSMSDetailsResponse) client
				.parseMessage(soapMessage);
		System.out.println(response);
	}

	@SuppressWarnings("unchecked")
	@Override
	public
	SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {

		String messageId = null;
		String version = null;
		String licenseKey = null;
		
		List<String> smsMsgIds = null;

		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		if (args.length > 0) {
			messageId = (String) args[0];
		}
		if(args.length > 1){
			smsMsgIds = (List<String>) args[1];
		}
		version = getVersion();
		licenseKey = getLicenseKey();

		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",
				SoapConstant.DOMAIN + SoapConstant.SOAPACTION_GETSMSDETAILS);
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		Name name = envelope.createName("GetSMSDetailsRequest",
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(
				name);
		addElement(envelope, parentElement, "messageId", messageId,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "version", version,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "licenseKey", licenseKey,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "smsMsgIds","smsMsgId", smsMsgIds,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);

		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		GetSMSDetailsResponse response = new GetSMSDetailsResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			Name parentName = envelope.createName("GetSMSDetailsResponse",
					SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
			SOAPBodyElement parentElement = (SOAPBodyElement) message
					.getSOAPBody().getChildElements(parentName).next();
			String correlationId = getElementValue(envelope, parentElement,
					"correlationId");
			response.setCorrelationId(correlationId);
			String version = getElementValue(envelope, parentElement, "version");
			response.setVersion(version);
			String build = getElementValue(envelope, parentElement, "build");
			response.setBuild(build);
			String timestamp = getElementValue(envelope, parentElement,
					"timestamp");
			response.setTimestamp(timestamp);

			List<SmsMessage> smsMessages = new ArrayList<SmsMessage>();
			NodeList list = getElementList(envelope, parentElement, "smsMessages");
			if (list != null && list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					NodeList childList = node.getChildNodes();
					SmsMessage smsMessage = new SmsMessage();
					for (int j = 0; j < childList.getLength(); j++) {
						Node child = childList.item(j);
						String name = child.getLocalName();
						String value = child.getTextContent();
						
						if ("smsMsgId".equalsIgnoreCase(name)) {
							smsMessage.setSmsMsgId(value);
						}
						if ("status".equalsIgnoreCase(name)) {
							smsMessage.setStatus(value);
						}
						if ("messageText".equalsIgnoreCase(name)) {
							smsMessage.setMessageText(value);
						}
						if ("senderLogin".equalsIgnoreCase(name)) {
							smsMessage.setSenderLogin(value);
						}
						if ("sentToIccid".equalsIgnoreCase(name)) {
							smsMessage.setSentToIccid(value);
						}
						if ("sentFrom".equalsIgnoreCase(name)) {
							smsMessage.setSentFrom(value);
						}
						if ("smsMsgAttemptStatus".equalsIgnoreCase(name)) {
							smsMessage.setSmsMsgAttemptStatus(value);
						}

						if ("msgType".equalsIgnoreCase(name)) {
							smsMessage.setMsgType(value);
						}
						if ("dateSent".equalsIgnoreCase(name)) {
							smsMessage.setDateSent(value);
						}
						if ("dateReceived".equalsIgnoreCase(name)) {
							smsMessage.setDateReceived(value);
						}
						if ("dateAdded".equalsIgnoreCase(name)) {
							smsMessage.setDateAdded(value);
						}
						if ("dateModified".equalsIgnoreCase(name)) {
							smsMessage.setDateModified(value);
						}
						
					}
					smsMessages.add(smsMessage);
				}
			}
			response.setSmsMessages(smsMessages);

		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	@PostConstruct
	void init() throws SOAPException, MalformedURLException,
			XWSSecurityException {
		SOAPConnectionFactory connectionFactory = SOAPConnectionFactory
				.newInstance();
		MessageFactory messageFactory = MessageFactory.newInstance();
		XWSSProcessorFactory processorFactory = XWSSProcessorFactory
				.newInstance();
		this.setConnectionFactory(connectionFactory);
		this.setMessageFactory(messageFactory);
		this.setProcessorFactory(processorFactory);
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
