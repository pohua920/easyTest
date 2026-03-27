package com.sinosoft.undwrt.common.model;

import net.vicp.vissoft.util.CodeUtils;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sinosoft.sysframework.reference.AppConfig;

/**
 * The Class PrpallCertifyTreeXml.
 */
public class PrpallCertifyTreeXml {

	/**
	 * 獲取屬性參數字符串.
	 * 
	 * @param comCode
	 *            the com code
	 * @param userCode
	 *            the user code
	 * @param businessNo
	 *            the business no
	 * @param pathPrefix
	 *            the path prefix
	 * @return 屬性參數字符串的值
	 * @throws Exception
	 *             the exception
	 */
	public String getParamString(String comCode, String userCode,
			String businessNo, String pathPrefix) throws Exception {
		String IP = AppConfig.get("sysconst.FILEMANAGERIP");
		// String IP = "http://192.168.19.16:7004";
		String typeTreeXMLCharset = "GBK";
		String pathFormat = "yyyy/MM/dd";
		// String pathPrefix = AppConfig.get("sysconst.NewCertify_pathPrefix");
		StringBuffer buffer = new StringBuffer();
		buffer.append("fileTransServiceUrl=").append(IP)
				.append("/filemanager/services/FileTrans");
		buffer.append("&fileIndexServiceUrl=").append(IP)
				.append("/filemanager/services/FileIndex");
		buffer.append("&userCode=").append(userCode);
		buffer.append("&operatorCode=").append(userCode);
		buffer.append("&comCode=").append(comCode);
		buffer.append("&bussNo=").append(businessNo);
		buffer.append("&codebase=" + IP + "/filemanager/fileupload");
		buffer.append("&property1=");
		buffer.append("&property2=");
		buffer.append("&property3=");
		buffer.append("&property4=");
		buffer.append("&property5=");
		buffer.append("&typeTreeXMLCharset=" + typeTreeXMLCharset);
		buffer.append("&pathFormat=" + pathFormat);
		buffer.append("&pathPrefix=" + pathPrefix);
		return buffer.toString();
	}
	
	public String getParamString(String url ,String comCode, String userCode,String OperatorCode,String businessNo ,String pathPrefix) throws Exception {
		//String IP = "http://192.168.18.215:7012";
		String IP = "http://"+url;
		//String IP = "http://"+SysConfig.getProperty("IMAGE_QUERY");
		String typeTreeXMLCharset = "GBK";
		String pathFormat = "yyyy/MM/dd";
		//String pathPrefix = AppConfig.get("sysconst.NewCertify_pathPrefix");
		StringBuffer buffer = new StringBuffer();
		buffer.append("fileTransServiceUrl=").append(IP).append("/filemanager/services/FileTrans");
		buffer.append("&fileIndexServiceUrl=").append(IP).append("/filemanager/services/FileIndex");
		buffer.append("&userCode=").append(userCode);
		buffer.append("&operatorCode=").append(OperatorCode);
		buffer.append("&comCode=").append(comCode);
		buffer.append("&bussNo=").append(businessNo);
		buffer.append("&codebase=" + IP + "/filemanager/fileupload");
		buffer.append("&property1=");
		buffer.append("&property2=");
		buffer.append("&property3=");
		buffer.append("&property4=");
		buffer.append("&property5=");
		buffer.append("&typeTreeXMLCharset="+typeTreeXMLCharset);
		buffer.append("&pathFormat="+pathFormat);
		buffer.append("&pathPrefix="+pathPrefix);
		return buffer.toString();
	}


	/**
	 * 单证树入口.
	 * 
	 * @param businessNo
	 *            the business no
	 * @return 屬性the sinosoft certify tree的值
	 */
	public String getCertifyTree(String businessNo) {
		// Element sysElement = DocumentHelper.createElement("SysType");
		// sysElement.addAttribute("catalog", "true");
		// sysElement.addAttribute("code", "prpall");
		// sysElement.addAttribute("name", "chengbao");
		// Element businessNoElement = sysElement.addElement("BusinessNo");
		// businessNoElement.addAttribute("catalog", "true");
		// businessNoElement.addAttribute("code", "policy");
		// businessNoElement.addAttribute("name", "danzheng");
		// Element subTypeElement = businessNoElement.addElement("SubType");
		// subTypeElement.addAttribute("catalog", "true");
		// subTypeElement.addAttribute("code", "01");
		// subTypeElement.addAttribute("name", "jibenxinxi");
		//
		// if(businessNo != null && !"".equals(businessNo)){
		// Element subTypeDetailElement =
		// subTypeElement.addElement("SubTypeDetail");
		// subTypeDetailElement.addAttribute("code", "carMessage");
		// subTypeDetailElement.addAttribute("name", "cheliangxinxi");
		//
		// }

		Element sysElement = DocumentHelper.createElement("SysType");
		sysElement.addAttribute("catalog", "true");
		sysElement.addAttribute("code", "prpall");
		sysElement.addAttribute("name", "chengbao");
		Element businessNoElement = sysElement.addElement("BusinessNo");
		businessNoElement.addAttribute("catalog", "true");
		businessNoElement.addAttribute("code", "policy");
		businessNoElement.addAttribute("name", "danzheng");
		Element subTypeElement = businessNoElement.addElement("SubType");
		subTypeElement.addAttribute("catalog", "true");
		subTypeElement.addAttribute("code", "01");
		subTypeElement.addAttribute("name", "jibenxinxi");

		// if(businessNo != null && !"".equals(businessNo)){
		Element subTypeDetailElement = subTypeElement
				.addElement("SubTypeDetail");
		subTypeDetailElement.addAttribute("code", "carMessage");
		subTypeDetailElement.addAttribute("name", "cheliangxinxi");
		//
		// }

		System.out.println("+++++CertifyTreeXml+++++++++" + sysElement.asXML());

		return CodeUtils.byteArrayToHexString(sysElement.asXML().toString()
				.getBytes(), false);
	}

	/**
	 * 添加节点.
	 * 
	 * @param businessNoElement
	 *            the business no element
	 * @param titleCode
	 *            the title code
	 * @param title
	 *            the title
	 * @param typeTreeList
	 *            the type tree list
	 * @return 屬性the sinosoft certify tree node的值
	 */
	private void getCertifyTreeNode(Element businessNoElement,
			String titleCode, String title, List typeTreeList) {
		// Element subTypeElement = businessNoElement.addElement("SubType");
		// subTypeElement.addAttribute("catalog", "true");
		// subTypeElement.addAttribute("code", titleCode);
		// subTypeElement.addAttribute("name", title);
		// for (int i = 0; i < typeTreeList.size(); i++) {
		// PrpLcertifyDirectDto newPrpLcertifyDirectDto = (PrpLcertifyDirectDto)
		// typeTreeList.get(i);
		// Element subTypeDetailElement =
		// subTypeElement.addElement("SubTypeDetail");
		// subTypeDetailElement.addAttribute("code",
		// newPrpLcertifyDirectDto.getTypeCode());
		// subTypeDetailElement.addAttribute("name",
		// newPrpLcertifyDirectDto.getTypeName());
		// }
		// }
	}
}
