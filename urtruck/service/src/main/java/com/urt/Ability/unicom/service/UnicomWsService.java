package com.urt.Ability.unicom.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.WsResponse;


@Service("UnicomWsService")
public abstract class UnicomWsService {

	private String username;
	private String passwd;
	private String url;
	private String licenseKey;
	private String version;
	private SOAPConnectionFactory connectionFactory;
	private MessageFactory messageFactory;
	private XWSSProcessorFactory processorFactory;
	
	/**
	 * 此方法用于添加安全性。这xwss：使用UsernameToken 配置，并期望通过用户名和密码。
	 * securitypolicy.xml文件应该在类路径。
	 *
	 * @param message
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws XWSSecurityException
	 */
	abstract SOAPMessage secureMessage(SOAPMessage message, final String username, final String password) throws IOException,XWSSecurityException ;

	/**
	 * 创建请求
	 * @param args
	 * @return
	 * @throws SOAPException
	 * @throws IllegalArgumentException
	 */
	abstract SOAPMessage createRequest(Object... args) throws SOAPException,IllegalArgumentException;

	/**
	 * 解析结果
	 * @param message
	 * @return
	 */
	public abstract WsResponse parseMessage(SOAPMessage message);
	
	/**
	 * 初始化
	 * @throws SOAPException
	 * @throws MalformedURLException
	 * @throws XWSSecurityException
	 */
	abstract void init() throws SOAPException, MalformedURLException,XWSSecurityException;

	public SOAPMessage call(Object... args) throws IOException,
			XWSSecurityException, SOAPException {
		SOAPMessage request = createRequest(args);
		request = secureMessage(request, getUsername(), getPasswd());
		System.out.println("Request: ");
		request.writeTo(System.out);
		System.out.println("");
		SOAPConnection connection = getConnectionFactory().createConnection();
		SOAPMessage response = connection.call(request, new URL(getUrl()));
		System.out.println("Response: ");
		response.writeTo(System.out);
		System.out.println("");
		if (!response.getSOAPBody().hasFault()) {
			return response;
		} else {
			SOAPFault fault = response.getSOAPBody().getFault();
			System.err.println("故障信息");
			System.err.println("SOAP Fault Code :" + fault.getFaultCode());
			System.err.println("SOAP Fault String :" + fault.getFaultString());
			return null;
		}
	}
	
	void addElement(SOAPEnvelope envelope, SOAPBodyElement parentElement,
			String parentKey, String childKey,List<String> values, String prefix, String uri)
			throws SOAPException {
		Name iccids = envelope.createName(parentKey, prefix, uri);
		SOAPElement iccidsElement = parentElement.addChildElement(iccids);
		for (String string : values) {
			Name childName = envelope.createName(childKey, prefix, uri);
			SOAPElement childElement = iccidsElement.addChildElement(childName);
			childElement.setValue(string);
		}
    }
	
	void addElement(SOAPEnvelope envelope, SOAPBodyElement parentElement,
			String key, String value, String prefix, String uri)
			throws SOAPException {
		Name name = envelope.createName(key, prefix, uri);
		SOAPElement childElement = parentElement.addChildElement(name);
		childElement.setValue(value);
    }
	
	String getElementValue(SOAPEnvelope envelope,SOAPBodyElement element,String key) throws SOAPException{
        Name name = envelope.createName(key, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        try {
            SOAPBodyElement iccidElement = (SOAPBodyElement) element.getChildElements(name).next();
            return  iccidElement.getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	NodeList getElementValue(SOAPEnvelope envelope,SOAPBodyElement element,String parentkey,String childkey) throws SOAPException{
		Name parentName = envelope.createName(parentkey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	    Name chileName = envelope.createName(childkey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	    SOAPBodyElement parentElement = (SOAPBodyElement) element.getChildElements(parentName).next();
	    SOAPBodyElement childElement = (SOAPBodyElement) parentElement.getChildElements(chileName).next();
	    NodeList list = childElement.getChildNodes();
	    return list;
	}
	
//	NodeList getElementList(SOAPEnvelope envelope,SOAPBodyElement element,String parentkey,String childkey) throws SOAPException{
//		Name parentName = envelope.createName(parentkey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
//	    Name chileName = envelope.createName(childkey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
//		SOAPBodyElement parentElement = (SOAPBodyElement) element.getChildElements(parentName).next();
//		NodeList list = parentElement.getChildNodes();
//		System.out.println(list);
//		for (int i = 0; i < list.getLength(); i++) {
//			Node node = list.item(i);
//			System.out.println(node);
//			NodeList childList = node.getChildNodes();
//			for (int j = 0; j < childList.getLength(); j++) {
//				Node child = childList.item(j);
//				System.out.println(child);
//				System.out.println(child.getLocalName()+"1"+child.getTextContent());
//			}
//		}
//	    return list;
//	}
	
	NodeList getElementList(SOAPEnvelope envelope,SOAPBodyElement element,String key) throws SOAPException{
		Name parentName = envelope.createName(key, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		SOAPBodyElement parentElement = (SOAPBodyElement) element.getChildElements(parentName).next();
		NodeList list = parentElement.getChildNodes();
	    return list;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
	
	
	
