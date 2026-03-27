package com.sinosoft.app.webservice.util;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
public class JaxbUtil {
	public static JAXBContext context = null;
	public static StringWriter writer = null;
	public static StringReader reader = null;
	/**
	 * xml和bean互转 
	 * @param xml
	 * @param obj
	 * @return
	 */
	public static Object xml2Bean(String xml, Class clazz) {
		
		if(clazz == null)
			return null;
		
		StringReader sr = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller um =context.createUnmarshaller();
			sr = new StringReader(xml);
			return um.unmarshal(sr);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(sr!=null) 
				sr.close();
			sr = null;
		}
	} 
	
	
	/**
	 * xml和bean互转 
	 * @param obj
	 * @return
	 */
	public static String bean2Xml(Object obj) {
		if(obj == null)
			return "";
		
		StringWriter sw = null;
		StringBuffer strBuffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
	        sw = new StringWriter();
	        m.marshal(obj,sw);
	        return strBuffer.append(sw.toString()).toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return "";
		} finally {
			if(sw!=null)
				try {
					sw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			sw = null;
		}
	}
			
}
