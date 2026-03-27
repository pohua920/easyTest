package cn.com.sinosoft.inf;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.inf.cross.webservice.CrossOrgServiceImpl;
import cn.com.sinosoft.inf.cross.webservice.CrossUserServiceImpl;

public class CrossSellAdepter {
	public static final String CROSS_USER = "S003";
	public static final String CROSS_SELLER = "S001";
	public static final String CROSS_COMPANY = "D001";
	/** 文本：（空） */
	public static final String TEXT_EMPTY = "";
	
	private static final CrossSellAdepter instance = new CrossSellAdepter();

	
	/** 私有构造方法 */
	private CrossSellAdepter() {
	}

	public static CrossSellAdepter getInstance() {
		return instance;
	}

	public String handle(String requestStr) {
		String requestMessage = getTagValue(requestStr,"<MESSAGE>");
		String result = "";
		//截取头信息	abcd:xxxxyyyy
		String requestHead = getHead(requestMessage);
		//根据头信息区分接口
		String businessType = getBusinessType(requestHead);
		//分别调用各自的实现方法，入参是报文体
		if (CROSS_COMPANY.equals(businessType)) {
			CrossOrgServiceImpl crossOrgServiceImpl = (CrossOrgServiceImpl)ServiceFactory.getService("crossOrgService");
			result = crossOrgServiceImpl.execute(requestMessage);
			//返回处理结果
			return makeResXml(result);
		}
		else {
			result = CrossUserServiceImpl.getInstance().forward(requestStr);
			//用户系统服务返回的报文不用处理
			return result;
		}
	}
	
	private String getHead(String requestStr) {
		return requestStr.substring(0, requestStr.indexOf(":"));
	}
	
	private String getBusinessType(String requestHead){
		String[] heads = requestHead.split("-");
		if("E".equals(heads[0])) {
			return heads[3];
		} else {
			return heads[4];
		}
	}
	
	public String makeResXml(String result) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<SYSNET xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		sb.append("<MESSAGE>");
		sb.append("TRUE".equals(result)?"数据处理成功":"数据处理失败");
		sb.append("</MESSAGE><RESULT>");
		sb.append(result);
		sb.append("</RESULT></SYSNET>");
		return sb.toString();
	}
	/** 通过字符串截取，获得标签的值 */
	private String getTagValue(String requestMessage, String tag) {
		int beginIndex = requestMessage.indexOf(tag);// 得到开始标记<tag>中的"<"的起始位置
		int endIndex = -1;

		String tagValue = "";
		if (beginIndex >= 0) {
			String endTag = "</" + tag.substring(1);
			endIndex = requestMessage.indexOf(endTag); // 得到结束标记</tag>中的"<"的起始位置
			tagValue = requestMessage.substring(beginIndex + tag.length(),
					endIndex);
			tagValue = tagValue.replaceAll("\r", TEXT_EMPTY);
			tagValue = tagValue.replaceAll("\n", TEXT_EMPTY);
			tagValue = tagValue.replaceAll("\t", TEXT_EMPTY);
			tagValue = tagValue.trim();
		}
		return tagValue;
	}
	public static void main(String []  args) {
		String requestStr = "a-b-c-d-e-f-g:xxxx|yyyy";
		String requestHead = requestStr.substring(0, requestStr.indexOf(":"));
		System.out.println(requestHead);
		String businessType = CrossSellAdepter.getInstance().getBusinessType(requestHead);
		System.out.println(businessType);
	}
}
