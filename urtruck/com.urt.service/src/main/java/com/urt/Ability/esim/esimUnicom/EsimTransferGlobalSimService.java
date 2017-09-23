package com.urt.Ability.esim.esimUnicom;

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
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.GetSessionInfoResponse;
import com.urt.Ability.unicom.vo.SendCancelLocationResponse;
import com.urt.Ability.unicom.vo.TargetSimSubscriptionInfo;
import com.urt.Ability.unicom.vo.Terminal;
import com.urt.Ability.unicom.vo.TransferGlobalSimResponse;
import com.urt.Ability.unicom.vo.WsResponse;

/**
 * @author haosun1 通过更改设备IMSI 将SIM 卡从一个运营商转 移到另一个运营商
 * 
 */
@Service("esimTransferGlobalSimService")
public class EsimTransferGlobalSimService extends UnicomWsService {

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
		UnicomWsService client = new EsimTransferGlobalSimService();
		client.init();
		client.setUrl("https://api.10646.cn/ws/service/termina");
		client.setUsername("fuhp3AA");
		client.setPasswd("abc123456");
		client.setLicenseKey("5e546edf-2016-400b-89a1-e1e630048140");
		client.setVersion("1.0");

		SOAPMessage soapMessage = client.call("lmh-test-TransferGlobalSim","89860116770002311868","CU Guangdong","CU Test alliance","REVERT","");
		TransferGlobalSimResponse response = (TransferGlobalSimResponse) client
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
		String targetPartnerName = null;
		String globalAllianceName = null;
		String globalSimTransferType = null;  // enumeration	STANDARD	REVERT
		String callbackNotificationURL = null;

		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		if (args.length > 0) {
			messageId = (String) args[0];
		}
		
		if (args.length > 1) {
			primaryIccid = (String) args[1];
		}
		if (args.length > 2) {
			targetPartnerName = (String) args[2];
		}
		if (args.length > 3) {
			globalAllianceName = (String) args[3];
		}
		if (args.length >4) {
			globalSimTransferType = (String) args[4];
		}
		if (args.length >5) {
			callbackNotificationURL = (String) args[5];
		}
		version = getVersion();
		licenseKey = getLicenseKey();

		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader(
				"SOAPAction",
				SoapConstant.DOMAIN
						+ SoapConstant.SOAPACTION_TRANSFERGLOBALSIM);
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		Name name = envelope.createName("TransferGlobalSimRequest",
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
		addElement(envelope, parentElement, "targetPartnerName", targetPartnerName,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "globalAllianceName", globalAllianceName,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "globalSimTransferType", globalSimTransferType,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "callbackNotificationURL", callbackNotificationURL,
				SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);

		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		TransferGlobalSimResponse response = new TransferGlobalSimResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			Name parentName = envelope.createName("TransferGlobalSimResponse",
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
			String globalSimTransferId = getElementValue(envelope, parentElement, "globalSimTransferId");
			response.setGlobalSimTransferId(globalSimTransferId);
			String primaryIccid = getElementValue(envelope, parentElement, "primaryIccid");
			response.setPrimaryIccid(primaryIccid);
			
			List<TargetSimSubscriptionInfo> terminals = new ArrayList<TargetSimSubscriptionInfo>();
			NodeList list = getElementList(envelope, parentElement, "targetSimSubscriptionInfo");
			if (list != null && list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					NodeList childList = node.getChildNodes();
					for (int j = 0; j < childList.getLength(); j++) {
						Node child = childList.item(j);
						String name = child.getLocalName();
						String value = child.getTextContent();
						TargetSimSubscriptionInfo terminal = new TargetSimSubscriptionInfo();
						if ("targetIccid".equalsIgnoreCase(name)) {
							terminal.setTargetIccid(value);
						}
						if ("targetMsisdn".equalsIgnoreCase(name)) {
							terminal.setTargetMsisdn(value);
						}
						if ("targetImsi".equalsIgnoreCase(name)) {
							terminal.setTargetImsi(value);
						}
						terminals.add(terminal);
					}
				}
			}
			response.setTargetSimSubscriptionInfo(terminals);

		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	@PostConstruct
	public void init() throws SOAPException, MalformedURLException,
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


}
