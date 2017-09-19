package com.urt.Ability.unicom.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.Ability.unicom.vo.QueryProductInfoResponse;
import com.urt.Ability.unicom.vo.WsResponse;

@Service("QueryProductInfoService")
public class QueryProductInfoService extends UnicomWsService{

	@Value("${lenovo.validate.url}")
	private String url;
	
	@Override
	SOAPMessage secureMessage(SOAPMessage message, final String username,
			final String password) throws IOException, XWSSecurityException {
        return message;
	}

	@Override
	SOAPMessage createRequest(Object... args) throws SOAPException,
			IllegalArgumentException {
		String QueryBy = null;
		String ValidateCode = null;
		String QueryMethod = null;
		String BrandTypeId = null;
		String Version = null;
		String ProductNo = null;

		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		
		if (args.length > 0) {
			QueryBy = (String) args[0];
		}
		if (args.length > 1) {
			ValidateCode = (String) args[1];
		}
		if (args.length > 2) {
			QueryMethod = (String) args[2];
		}
		if (args.length > 3) {
			BrandTypeId = (String) args[3];
		}
		if (args.length > 4) {
			Version = (String) args[4];
		}
		if (args.length > 5) {
			ProductNo = (String) args[5];
		}
		
		SOAPMessage message = getMessageFactory().createMessage();
		message.getMimeHeaders().addHeader("SOAPAction",SoapConstant.DOMAIN_LENOVO + SoapConstant.SOAPACTION_QUERYPRODUCTINFO);
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name queryProductInfo = envelope.createName("QueryProductInfo", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
        SOAPBodyElement queryProductInfoElement = message.getSOAPBody().addBodyElement(queryProductInfo);
        
		Name paramQueryInput = envelope.createName("paramQueryInput", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement paramQueryElement = queryProductInfoElement.addChildElement(paramQueryInput);

		Name queryUser = envelope.createName("QueryUser", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement queryUserElement = paramQueryElement.addChildElement(queryUser);
		
		Name queryBy = envelope.createName("QueryBy", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement queryByElement = queryUserElement.addChildElement(queryBy);
		queryByElement.setValue(QueryBy);
		
		Name validateCode = envelope.createName("ValidateCode", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement validateCodeElement = queryUserElement.addChildElement(validateCode);
		validateCodeElement.setValue(ValidateCode);
		
		
		Name queryCondition = envelope.createName("QueryCondition", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement queryConditionElement = paramQueryElement.addChildElement(queryCondition);
		
		Name queryMethod = envelope.createName("QueryMethod", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement queryMethodElement = queryConditionElement.addChildElement(queryMethod);
		queryMethodElement.setValue(QueryMethod);
		
		Name brandTypeId = envelope.createName("BrandTypeId", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement brandTypeIdElement = queryConditionElement.addChildElement(brandTypeId);
		brandTypeIdElement.setValue(BrandTypeId);
		
		Name version = envelope.createName("Version", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement VersionElement = queryConditionElement.addChildElement(version);
		VersionElement.setValue(Version);
		
		Name productNo = envelope.createName("ProductNo", SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
		SOAPElement ProductNoElement = queryConditionElement.addChildElement(productNo);
		ProductNoElement.setValue(ProductNo);
		
		return message;
	}

	@Override
	public WsResponse parseMessage(SOAPMessage message) {
		QueryProductInfoResponse response = new QueryProductInfoResponse();
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name queryProductInfoResponse = envelope.createName("QueryProductInfoResponse" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPBodyElement queryProductInfoResponseElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(queryProductInfoResponse).next();
	        
	        Name queryProductInfoResult = envelope.createName("QueryProductInfoResult" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement queryProductInfoResultElement = (SOAPElement) queryProductInfoResponseElement.getChildElements(queryProductInfoResult).next();
	        
	        Name queryStatus = envelope.createName("QueryStatus" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement queryStatusElement = (SOAPElement) queryProductInfoResultElement.getChildElements(queryStatus).next();
	        
	        Name version = envelope.createName("Version" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement versionElement = (SOAPElement) queryStatusElement.getChildElements(version).next();
	        response.setVersion(versionElement.getTextContent());

	        Name resultCode = envelope.createName("ResultCode" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement resultCodeElement = (SOAPElement) queryStatusElement.getChildElements(resultCode).next();
	        response.setResultcode(resultCodeElement.getTextContent());
	        
	        Name resultNotes = envelope.createName("ResultNotes" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement resultNotesElement = (SOAPElement) queryStatusElement.getChildElements(resultNotes).next();
	        response.setResultnotes(resultNotesElement.getTextContent());
	        
	        Name queryResults = envelope.createName("QueryResults" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement queryResultsElement = (SOAPElement) queryProductInfoResultElement.getChildElements(queryResults).next();

	        Name productInfo = envelope.createName("ProductInfo" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement productInfoElement = (SOAPElement) queryResultsElement.getChildElements(productInfo).next();
	        
	        Name productBasicInfo = envelope.createName("ProductBasicInfo" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement productBasicInfoElement = (SOAPElement) productInfoElement.getChildElements(productBasicInfo).next();
	        
	        Name modelTypeName = envelope.createName("ModelTypeName" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement modelTypeNameElement = (SOAPElement) productBasicInfoElement.getChildElements(modelTypeName).next();
	        response.setModeltypename(modelTypeNameElement.getTextContent());
	        
	        Name modelType = envelope.createName("ModelType" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement modelTypeElement = (SOAPElement) productBasicInfoElement.getChildElements(modelType).next();
	        response.setModeltype(modelTypeElement.getTextContent());

	        Name catalogName = envelope.createName("CatalogName" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement catalogNameElement = (SOAPElement) productBasicInfoElement.getChildElements(catalogName).next();
	        response.setCatalogname(catalogNameElement.getTextContent());

	        Name productName = envelope.createName("ProductName" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement productNameElement = (SOAPElement) productBasicInfoElement.getChildElements(productName).next();
	        response.setProductname(productNameElement.getTextContent());

	        Name brandTypeId = envelope.createName("BrandTypeId" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement brandTypeIdElement = (SOAPElement) productBasicInfoElement.getChildElements(brandTypeId).next();
	        response.setBrandtypeid(brandTypeIdElement.getTextContent());

	        Name PrductDate = envelope.createName("PrductDate" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement prductDateElement = (SOAPElement) productBasicInfoElement.getChildElements(PrductDate).next();
	        response.setPrductdate(prductDateElement.getTextContent());

	        Name productLineId = envelope.createName("ProductLineId" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement productLineIdElement = (SOAPElement) productBasicInfoElement.getChildElements(productLineId).next();
	        response.setProductlineid(productLineIdElement.getTextContent());

	        Name packingLotNo = envelope.createName("PackingLotNo" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement packingLotNoElement = (SOAPElement) productBasicInfoElement.getChildElements(packingLotNo).next();
	        response.setPackinglotno(packingLotNoElement.getTextContent());

	        Name productSn = envelope.createName("ProductSn" , SoapConstant.PREFIX_LENOVO, SoapConstant.NAMESPACE_URI_LENOVO);
	        SOAPElement productSnElement = (SOAPElement) productBasicInfoElement.getChildElements(productSn).next();
	        response.setProductsn(productSnElement.getTextContent());

		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		
		return response;
	}

	@Override
	void init() throws SOAPException, MalformedURLException,
			XWSSecurityException {
		SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
		MessageFactory messageFactory = MessageFactory.newInstance();
		XWSSProcessorFactory processorFactory = XWSSProcessorFactory.newInstance();
		this.setConnectionFactory(connectionFactory);
		this.setMessageFactory(messageFactory);
		this.setProcessorFactory(processorFactory);
	}
	
	public static void main(String[] args) throws Exception{
		UnicomWsService client = new QueryProductInfoService();
    	client.init();
    	client.setUrl("http://dcs.mspcloud.cn/dc/service/productQueryServiceSoap");
		String queryby = "PCCarer21";
		String validatecode = "PCCarer091204";
		String querymethod = "GetProductBasicInfo";
		String brandtypeid = "GetProductBasicInfo";
		String version = "119";
		String productno = "PC05V0YS1";
    	SOAPMessage soapMessage = client.call(queryby,validatecode,querymethod,brandtypeid,version,productno);
    	QueryProductInfoResponse ws = (QueryProductInfoResponse) client.parseMessage(soapMessage);
    	System.out.println(ws);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
