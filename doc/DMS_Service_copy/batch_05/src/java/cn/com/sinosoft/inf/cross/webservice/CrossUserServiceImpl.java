package cn.com.sinosoft.inf.cross.webservice;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import cn.com.sinosoft.ims.util.ReadProperties;

public class CrossUserServiceImpl {
	private static final CrossUserServiceImpl	instance	= new CrossUserServiceImpl();

	/** 构造函数 */
	private CrossUserServiceImpl() {
	}

	/** 获取实例 */
	public static CrossUserServiceImpl getInstance() {
		return instance;
	}

	public String forward(String requestStr) {
		Object result = "";
		System.out.println("转发的报文："+requestStr);
		String funEnName = ReadProperties.getString("crossuserforwardfun");
		String netUrl = ReadProperties.getString("crossuserforwardurl");
		System.out.println("转发地址" + netUrl);
		System.out.println("转发函数" + funEnName);
//		funEnName = "execute";
//		netUrl = "http://11.137.110.128:8080/DataExchangePlatform/ServiceProxy.jws";
//		netUrl = "http://11.287.1.20:8080/DataExchangePlatform/ServiceProxy.jws";
//		netUrl = "http://10.128.1.20:8080/MainData/MainDataServiceProxy.jws";
//		netUrl = "http://10.128.112.115:8080/portal/ServiceProxy.jws";
//		netUrl = "http://192.168.1.110:8080/ims/CrossSellWebService.jws";
//		netUrl = "http://11.137.76.44:8003/ims/CrossSellWebService.jws";
		try {
			result = CrossUserServiceImpl.getInstance().webClientCall(netUrl, funEnName, new Object[] { requestStr });
			System.out.println("回复的报文："+(String) result);
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
