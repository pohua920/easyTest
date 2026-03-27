package cn.com.sinosoft.inf.cross.webservice;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import cn.com.sinosoft.ims.util.ReadProperties;

public class CrossOrgWebServiceClient {
	private static final CrossOrgWebServiceClient	instance	= new CrossOrgWebServiceClient();

	/** 构造函数 */
	private CrossOrgWebServiceClient() {
	}

	/** 获取实例 */
	public static CrossOrgWebServiceClient getInstance() {
		return instance;
	}

	public String makeXml(String requestStr) {
		StringBuffer xml = new StringBuffer(1024);
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><sysnet xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		xml.append("<RECEIVER>CENTER</RECEIVER><MESSAGE>");
		xml.append(requestStr);
		xml.append("</MESSAGE><MSGTYPE>mainData</MSGTYPE><MSGNAME>");
		xml.append(requestStr.substring(0, requestStr.indexOf(":")));
		xml.append("</MSGNAME></sysnet>");
		return xml.toString();
	}

	public String send(String requestStr) {
		Object result = "";
		String xml = makeXml(requestStr);
		System.out.println(xml);
		String funEnName = ReadProperties.getString("crosswebservicefun");
		String netUrl = ReadProperties.getString("crosswebserviceurl");
		try {
			result = CrossOrgWebServiceClient.getInstance().webClientCall(netUrl, funEnName, new Object[] { xml });
			System.out.println((String) result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (String) result;
	}

	public Object webClientCall(String netUrl, String funEnName, Object par[]) throws Exception {
		Object obj = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			QName qn = new QName(netUrl, funEnName);
			call.setOperationName(qn);
			call.setTargetEndpointAddress(new URL(netUrl));
			obj = call.invoke(par);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
