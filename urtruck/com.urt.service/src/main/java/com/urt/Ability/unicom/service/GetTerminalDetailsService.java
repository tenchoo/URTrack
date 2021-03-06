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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.TerminalDetail;
import com.urt.Ability.unicom.vo.TerminalDetailRating;
import com.urt.Ability.unicom.vo.WsResponse;


/**
 * 
 * @author liumh8
 *
 */
@Service("getTerminalDetailsService")
public class GetTerminalDetailsService extends UnicomWsService {
	
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

    /**
     * 主程序。用法：terminalclient <用户名> <密码>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	UnicomWsService client = new GetTerminalDetailsService();
    	client.init();
    	client.setUrl("https://api.10646.cn/ws/service/terminal");
    	client.setUsername("ITRandD");
    	client.setPasswd("o.s.wnYct23GrzqL");
    	client.setLicenseKey("2c800619-0416-47d6-858e-584c3e6b6c6a");
    	client.setVersion("1.0");
    	List<String> list = new ArrayList<String>();
    	list.add("89860615010014084443");
    	SOAPMessage soapMessage = client.call(list,"20170802105903");
    	GetTerminalDetailsResponse response = (GetTerminalDetailsResponse) client.parseMessage(soapMessage);
    	System.out.println(response);
    	JSONObject jsonObject =(JSONObject) com.alibaba.dubbo.common.json.JSON.parse(response.toString());
	    JSONArray array = jsonObject.getArray("list");
        for (int i = 0; i < array.length(); i++) {
        	 JSONObject jsonObj = (JSONObject) array.get(i);
        	 JSONArray jsonArray2 = jsonObj.getArray("rating");
        	 JSONObject resultJSon = (JSONObject) jsonArray2.get(0);
        	 String primaryDataRemaining = resultJSon.getString("primaryDataRemaining"); //当前包已用流量
        	 String termStartDate = resultJSon.getString("termStartDate"); //当前包的激活时间
        	 String termEndDate = resultJSon.getString("termEndDate");  //当前包套餐截止时间
        	 String totalPrimaryIncludedData = resultJSon.getString("totalPrimaryIncludedData");  //当前包总流量
        	 String renewalRatePlan = resultJSon.getString("renewalRatePlan");  //当前包套餐档次
        	 System.out.println("primaryDataRemaining"+primaryDataRemaining);
        	 System.out.println("termStartDate========"+termStartDate);
        	 System.out.println("termEndDate==========="+termEndDate);
        	 System.out.println("totalPrimaryIncludedData"+totalPrimaryIncludedData);
        	 System.out.println("renewalRatePlan"+renewalRatePlan);
        	 
		}
    	
    	System.out.println(response);
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
//        	 URL url = getClass().getResource("");
//        	 String path = "";
//             if (url != null) {
//                 path = url.getFile();
//                 System.out.println(path);
//                 if (StringUtils.contains(path, "/classes/")) {
//                     path = StringUtils.substringBefore(path,"/classes/") + "/classes/";
//                 }
//                 System.out.println(path);
//             } 
//			File file = new File(path + "SecurityPolicy.xml");
//            System.out.println(getClass().getResourceAsStream("SecurityPolicy.xml"));
            policyStream = getClass().getResourceAsStream("SecurityPolicy.xml");
//            policyStream = new FileInputStream(file);
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
	public
	SOAPMessage createRequest(Object... args) throws SOAPException, IllegalArgumentException {
		List<String> iccidList = null;
		String messageId = null;
        String version = null;
        String licenseKey = null;
		
		if (args == null) {
			throw new IllegalArgumentException();
		}
        version = getVersion();
		licenseKey = getLicenseKey();
		
		if (args.length > 0) {
			iccidList = ((List<String>) args[0]);
		}
		if (args.length > 1) {
			messageId = (String) args[1];
		}
		
		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_GETTERMINALDETAILS);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("GetTerminalDetailsRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", messageId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement,"iccids", "iccid",iccidList, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		try {
			GetTerminalDetailsResponse response = new GetTerminalDetailsResponse();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name terminalResponseName = envelope.createName("GetTerminalDetailsResponse", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	        SOAPBodyElement parentElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(terminalResponseName).next();
	        String correlationId = getElementValue(envelope, parentElement, "correlationId");
	        response.setCorrelationId(correlationId);
	        String version = getElementValue(envelope, parentElement, "version");
	        response.setVersion(version);
	        String build = getElementValue(envelope, parentElement, "build");
	        response.setBuild(build);
	        String timestamp = getElementValue(envelope, parentElement, "timestamp");
	        response.setTimestamp(timestamp);
//	        NodeList list = getElementValue(envelope, parentElement, "terminals", "terminal");
//	        Node n = null;
//	        for (int i = 0; i < list.getLength(); i++) {
//	            n = list.item(i);
//	            String textContent = n.getTextContent();
//	            if ("status".equalsIgnoreCase(n.getLocalName())){
//	            	response.setStatus(textContent);
//	            }
//	            if ("ratePlan".equalsIgnoreCase(n.getLocalName())){
//	            	response.setRatePlan(textContent);
//	            }
//	            if ("iccid".equalsIgnoreCase(n.getLocalName())){
//	            	response.setIccid(textContent);
//	            }
//	            if ("imsi".equalsIgnoreCase(n.getLocalName())){
//	            	response.setImsi(textContent);
//	            }
//	        }

	        List<TerminalDetail> configList = new ArrayList<TerminalDetail>();
	        NodeList list = getElementList(envelope, parentElement, "terminals");
	        if (list != null && list.getLength() > 0) {
	        	for (int i = 0; i < list.getLength(); i++) {
	    			Node node = list.item(i);
	    			NodeList childList = node.getChildNodes();
	    			TerminalDetail detail = new TerminalDetail();
	    			List<TerminalDetailRating> ratingList = new ArrayList<>();
		            TerminalDetailRating rating = new TerminalDetailRating();
	    			for (int j = 0; j < childList.getLength(); j++) {
	    				Node child = childList.item(j);
	    				String name =child.getLocalName(); 
	    				String value = child.getTextContent();
						if ("iccid".equalsIgnoreCase(name)) {
							detail.setIccid(value);
						}
						if ("suspended".equalsIgnoreCase(name)) {
							detail.setSuspended(value);
						}
						if ("ratePlan".equalsIgnoreCase(name)) {
							detail.setRatePlan(value);
						}
						if ("status".equalsIgnoreCase(name)) {
							detail.setStatus(value);
						}
						if ("monthToDateUsage".equalsIgnoreCase(name)) {
							detail.setMonthToDateUsage(value);
						}
						if ("overageLimitReached".equalsIgnoreCase(name)) {
							detail.setOverageLimitReached(value);
						}
						if ("dateActivated".equalsIgnoreCase(name)) {
							detail.setDateActivated(value);
						}
						if ("dateAdded".equalsIgnoreCase(name)) {
							detail.setDateAdded(value);
						}
						if ("dateModified".equalsIgnoreCase(name)) {
							detail.setDateModified(value);
						}
						if ("dateShipped".equalsIgnoreCase(name)) {
							detail.setDateShipped(value);
						}
						if ("monthToDateDataUsage".equalsIgnoreCase(name)) {
							detail.setMonthToDateDataUsage(value);
						}
						if ("monthToDateSMSUsage".equalsIgnoreCase(name)) {
							detail.setMonthToDateSMSUsage(value);
						}
						if ("monthToDateVoiceUsage".equalsIgnoreCase(name)) {
							detail.setMonthToDateVoiceUsage(value);
						}
						if ("accountId".equalsIgnoreCase(name)) {
							detail.setAccountId(value);
						}
						if ("version".equalsIgnoreCase(name)) {
							detail.setVersion(value);
						}
						if ("imei".equalsIgnoreCase(name)) {
							detail.setImei(value);
						}
						if ("imsi".equalsIgnoreCase(name)) {
							detail.setImsi(value);
						}
						if ("status".equalsIgnoreCase(name)) {
							detail.setStatus(value);
						}
						if ("overageLimitOverride".equalsIgnoreCase(name)) {
							detail.setOverageLimitOverride(value);
						}
			            if ("rating".equalsIgnoreCase(name)) {
			                NodeList childList2 = child.getChildNodes();
			               
			                for (int k = 0; k < childList2.getLength(); k++) {
			                  Node child2 = childList2.item(k);
			                  String name2 = child2.getLocalName();
			                  String value2 = child2.getTextContent();
			                  if ("renewalPolicy".equalsIgnoreCase(name2)) {
			                    detail.setRenewalPolicy(value2);
			                  }
			                  if ("termStartDate".equalsIgnoreCase(name2)) {
			                	  rating.setTermStartDate(value2);
			                  }
			                  if ("termEndDate".equalsIgnoreCase(name2)) {
			                	  rating.setTermEndDate(value2);
			                  }
			                  if ("totalPrimaryIncludedData".equalsIgnoreCase(name2)) {
			                	  rating.setTotalPrimaryIncludedData(value2);
			                  }
			                  if ("primaryDataRemaining".equalsIgnoreCase(name2)) {
			                	  rating.setPrimaryDataRemaining(value2);
			                  }
			                  if ("renewalRatePlan".equalsIgnoreCase(name2)) {
			                	  rating.setRenewalRatePlan(value2);
			                  }
			               }
			                ratingList.add(rating);
			                detail.setRating(ratingList);
			                
			            }
	    			}
					NamedNodeMap attrs = node.getAttributes();
					if (attrs != null && attrs.getLength() > 0 ) {
						Node attr = attrs.getNamedItem("msisdn");
						detail.setMsisdn(attr.getNodeValue());
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
