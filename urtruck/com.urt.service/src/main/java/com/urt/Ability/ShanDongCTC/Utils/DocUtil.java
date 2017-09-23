package com.urt.Ability.ShanDongCTC.Utils;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class DocUtil {
	public static Document strToDoc(String str) throws Exception {
		/*StringReader stringReader  =  new StringReader(str);

		InputSource  inputSource  =  new  InputSource(stringReader);
		DocumentBuilderFactory domfac=DocumentBuilderFactory.newInstance();
		 DocumentBuilder documentBuilder;
		try {
			documentBuilder = domfac.newDocumentBuilder();
			Document  document  =  documentBuilder.parse(inputSource );
			 return document;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}   */
		 
		StringReader sr = new StringReader(str);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc = (Document) builder.parse(is);
		return doc;
	}
	public static Document string2Doc(String str) {
		
		java.io.Reader in = new StringReader(str);     
        try {
			Document doc = (Document) (new SAXBuilder()).build(in);
			return doc;
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
		return null;
	}
	public static String getResultText(Document doc) {
		Element rootElement = doc.getRootElement();
		return rootElement.getChild("result").getText();
		
	}
}
