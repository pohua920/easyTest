package com.tlg.commons.util.api.soap;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.commons.codec.binary.Base64;

/**
 * SoapXmlFormatter 
 * 用途專門用於內部SoapApi(WebService)使用
 * 因內部API將要傳輸的Data的xml轉Base64帶出，故將獨立加入專用Formatter
 * 來處理Xml轉Base64的部分
 * @author bk007 蘇哲
 *
 */
public class SoapXmlFormatter {

	@SuppressWarnings("unchecked")
	public <T> T parse(Class<T> clazz, String base64Str) throws JAXBException, UnsupportedEncodingException {
		String decodeStr = new String(Base64.decodeBase64(base64Str), "UTF-8");
		return (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(new java.io.StringReader(decodeStr));
	}

	public <T> String format(Class<T> clazz, T obj) throws JAXBException {
		Marshaller marshaller = getMarshaller(clazz);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(obj, baos);
		return Base64.encodeBase64String(baos.toByteArray());
	}

	private <T> Marshaller getMarshaller(Class<T> clazz) throws JAXBException, PropertyException {
		Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 是否生成xml字串
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // 是否省略xml head訊息
		return marshaller;
	}

}
