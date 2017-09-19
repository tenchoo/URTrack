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
import com.urt.Ability.unicom.vo.CancelGlobalSimTransferResponse;
import com.urt.Ability.unicom.vo.GetSessionInfoResponse;
import com.urt.Ability.unicom.vo.SendCancelLocationResponse;
import com.urt.Ability.unicom.vo.TransferGlobalSimResponse;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * @author haosun1 为给定设备取消全球SIM 卡转移请求
 * 
 */
@Service("cancelGlobalSimTransferService")
public class CancelGlobalSimTransferService extends UnicomWsService {
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
		UnicomWsService client = new CancelGlobalSimTransferService();
		client.init();
		client.setUrl("https://api.10646.cn/ws/service/termina");
		client.setUsername("develop");
		client.setPasswd("123456");
		client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
		client.setVersion("1.0");

		SOAPMessage soapMessage = client.call("lmh-test-TransferGlobalSim","89860616010010314686");
		CancelGlobalSimTransferResponse response = (CancelGlobalSimTransferResponse) client
				.parseMessage(soapMessage);
		System.out.println(response);
	}

	@Override
	SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {

		String messageId = null;
		String version = null;
		String licenseKey = null;
		
		String  primaryIccid = null;
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		if (args.length > 0) {
			messageId = (String) args[0];
		}
		
		if (args.length > 1) {
			primaryIccid = (String) args[1];
		}
		version = getVersion();
		licenseKey = getLicenseKey();

		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader(
				"SOAPAction",
				SoapConstant.DOMAIN
						+ SoapConstant.SOAPACTION_CANCELGLOBALSIMTRANSFER);
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		Name name = envelope.createName("CancelGlobalSimTransferRequest",
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(
				name);
		addElement(envelope, parentElement, "messageId", messageId,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "version", version,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "licenseKey", licenseKey,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "primaryIccid", primaryIccid,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);

		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		CancelGlobalSimTransferResponse response = new CancelGlobalSimTransferResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			Name parentName = envelope.createName("CancelGlobalSimTransferResponse",
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
			String primaryIccid = getElementValue(envelope, parentElement, "primaryIccid");
			response.setPrimaryIccid(primaryIccid);
			String status = getElementValue(envelope, parentElement, "status");
			response.setStatus(status);

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
