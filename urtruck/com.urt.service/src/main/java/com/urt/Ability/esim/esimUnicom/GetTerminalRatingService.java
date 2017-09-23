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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.Ability.unicom.vo.WsResponse;

@Service("esimGetTerminalRatingService")
public class GetTerminalRatingService extends UnicomWsService{

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
	public SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {
        String iccid = null;
        String messageId = null;
        String version = null;
        String licenseKey = null;
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		
		if (args.length > 0) {
			iccid = (String) args[0];
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
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN + SoapConstant.SOAPACTION_GETTERMINALRATING);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName("GetTerminalRatingRequest", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        SOAPBodyElement parentElement = message.getSOAPBody().addBodyElement(name);
        addElement(envelope, parentElement, "messageId", messageId, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "version", version, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
        addElement(envelope, parentElement, "licenseKey", licenseKey, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		addElement(envelope, parentElement, "iccid",iccid, SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		GetTerminalRatingResponse response = new GetTerminalRatingResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name terminalResponseName = envelope.createName("GetTerminalRatingResponse", SoapConstant.PREFIX, SoapConstant.NAMESPACE_URI);
	        SOAPBodyElement parentElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(terminalResponseName).next();
	        
	        String iccid = getElementValue(envelope, parentElement, "iccid");
	        response.setIccid(iccid);

	        String correlationId = getElementValue(envelope, parentElement, "correlationId");
	        response.setCorrelationId(correlationId);
	        
	        String version = getElementValue(envelope, parentElement, "version");
	        response.setVersion(version);
	        
	        String build = getElementValue(envelope, parentElement, "build");
	        response.setBuild(build);
	        
	        String timestamp = getElementValue(envelope, parentElement, "timestamp");
	        response.setTimestamp(timestamp);
	        
//	        NodeList list = getElementValue(envelope, parentElement, "terminalRatings", "terminalRating");
//	        Node n = null;
//	        for (int i = 0; i < list.getLength(); i++) {
//	            n = list.item(i);
//	            if ("ratePlanName".equalsIgnoreCase(n.getLocalName())){
//	            	response.setRatePlanName(n.getTextContent());
//	            }
//	            if ("queuePosition".equalsIgnoreCase(n.getLocalName())){
//	            	response.setQueuePosition(n.getTextContent());
//	            }
//	            if ("expirationDate".equalsIgnoreCase(n.getLocalName())){
//	            	response.setExpirationDate(n.getTextContent());
//	            }
//	            if ("termLength".equalsIgnoreCase(n.getLocalName())){
//	            	response.setTermLength(n.getTextContent());
//	            }
//	            if ("dataRemaining".equalsIgnoreCase(n.getLocalName())){
//	            	response.setDataRemaining(n.getTextContent());
//	            }
//	        }

	        List<TerminalRatingDetail> detailList = new ArrayList<TerminalRatingDetail>();
	        NodeList list = getElementList(envelope, parentElement, "terminalRatings");
	        if (list != null && list.getLength() > 0) {
	        	for (int i = 0; i < list.getLength(); i++) {
	    			Node node = list.item(i);
	    			NodeList childList = node.getChildNodes();
	    			TerminalRatingDetail detail = new TerminalRatingDetail();
	    			String monfixRatePlanName = "";
	    			for (int j = 0; j < childList.getLength(); j++) {
	    				Node child = childList.item(j);
	    				String name =child.getLocalName(); 
	    				String value = child.getTextContent();
						if ("ratePlanName".equalsIgnoreCase(name)) {
							//如果是包月计划
							if("110WLW004085_MON-FIX_5120M-0S".equals(value)){
								monfixRatePlanName = "110WLW004085_MON-FIX_5120M-0S";
							}
							detail.setRatePlanName(value);
						}
						if ("queuePosition".equalsIgnoreCase(name)) {
							detail.setQueuePosition(value);
						}
						if ("expirationDate".equalsIgnoreCase(name)) {
							detail.setExpirationDate(value);
						}
						if ("termLength".equalsIgnoreCase(name)) {
							detail.setTermLength(value);
						}
						if ("dataRemaining".equalsIgnoreCase(name)) {
							//如果是默认包月套餐，流量设置为0
							if("110WLW004085_MON-FIX_5120M-0S".equals(monfixRatePlanName)){
								detail.setDataRemaining("0");
							}else{
								detail.setDataRemaining(value);
							}
						}
	    			}
	    				detailList.add(detail);
	    		}
			}
	        response.setList(detailList);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return response;
	}

	@Override
	@PostConstruct
	public void init() throws SOAPException, MalformedURLException,
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
		UnicomWsService client = new GetTerminalRatingService();
    	client.init();
//    	client.setUrl("https://api.10646.cn/ws/service/terminal");
    	client.setUrl("https://api.10646.cn/ws/service/terminal");
    	client.setUsername("develop");
    	client.setPasswd("123456");
    	client.setVersion("1.0");
    	client.setLicenseKey("f7d35f99-d21c-4e04-811d-5efef0a6c7b2");//2c800619-0416-47d6-858e-584c3e6b6c6a
    
    //	for(int i = 0; i<100;i++){       8986061501000889177         89860616010010260715
    		SOAPMessage soapMessage = client.call("89860616010024409811","20161021113999");
    		GetTerminalRatingResponse ws = (GetTerminalRatingResponse) client.parseMessage(soapMessage);
    		
    		String str=ws.toString();
    		JSONObject jsonObject=new JSONObject(str);
    		Object object2 = jsonObject.get("list");
    		JSONArray array=new JSONArray(object2.toString());
    		for(int i=0;i<array.length();i++){
    			Object object = array.get(i);
    			jsonObject=new JSONObject(object.toString());
    			String code=jsonObject.get("ratePlanName").toString();
    			String endTime=jsonObject.getString("expirationDate");
    			System.out.println(">>>>>>>>>>"+code+";"+endTime);
    		}
    		System.out.println(ws.toString());
    //	}

	}

}
