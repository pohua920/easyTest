package com.ctbcins.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;

/**
 *mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
public class WebserviceObjConvert {
	
	
	public static Object convertBase64StrToObj(String str, Class obj) throws JAXBException, UnsupportedEncodingException{
		byte[] decryptedByteArray = Base64.decodeBase64(str.getBytes());
//		System.out.println(">>>>>>>IN base64 string = " + str);
		String tmp = new String(decryptedByteArray, "UTF-8");
		java.io.StringReader reader = new java.io.StringReader(tmp);
		JAXBContext context = JAXBContext.newInstance(obj);
		Unmarshaller objUnmarshaller = context.createUnmarshaller();
		return objUnmarshaller.unmarshal(reader);
	}
	
	public static String convertObjToBase64Str(Class classObj, Object obj) throws JAXBException{

		Marshaller marshaller = JAXBContext.newInstance(classObj).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING,"GBK");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //是否生成xml字串
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); //是否省略xml head訊息
	       
		ByteArrayOutputStream objByteArrayOutputStream = new ByteArrayOutputStream();
		marshaller.marshal(obj, objByteArrayOutputStream);
		byte[] vbResultXML = objByteArrayOutputStream.toByteArray();
		byte[] vbResultXML2 = null;
		try{
			vbResultXML2 = new String(vbResultXML,"GBK").getBytes("UTF-8");
//			System.out.println(new String(vbResultXML2,"UTF-8"));
		}catch(Exception e){}
		
		String encryptedtext = new String(Base64.encodeBase64(vbResultXML2));
//		System.out.println(">>>>>>>OUT base64 String  = " + encryptedtext.toString());
		return encryptedtext;
	}
}
