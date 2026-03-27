package cn.com.sinosoft.test;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;




public class TestWebService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestWebService test = new TestWebService();
//		String netUrl = "http://192.168.1.112:8080/ims/CrossSellWebService.jws";
		String netUrl = "http://11.137.76.143:8004/dms/CrossSellWebService.jws";
//		String netUrl = "http://localhost:8080/ims/CrossSellWebService.jws";
//		String funEnName = "execute";
		String funEnName = "execute";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<sysnet xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");		
		sb.append("<MESSAGE>");
//		sb.append("<OPERATORCODE>");
//		sb.append("A430100020");
//		sb.append("R-I-000085-11000000-S003-123123-20100527123430:12000010|000085|健康险|0008512|健康险测试机够|出单员10|19810987|1|11|132302198590872436|1342617|879789|softblw@yahoo.com.cn|1|在职|01,02|20100807|20100807|I|00000000|||||||20100908|");
//		sb.append("R-Q-000002-11000000-S003-123123-20100527123430:A000000000");
//		sb.append("R-I-000100-000085-S001-123123-20100527123430:5555555555|000085|中国人民健康保险股份有限公司|||销售人员55|||86210000|辽宁省分公司|||||||1|在职|1|直销|I|20090429 12:10:52|20100527 12:34:30|1|直销|20091002|20100403||20090328 15:30:33|000085,000100");
//		sb.append("R-I-000100-1430000-S001-20110534866-20110110153644:20110534866|000100|中国人寿保险有限公司|||测试1||1|1430000|湖南|11|身份证|372925198608011719||||1|在职|1|直销|I|20110110 15:36:44|20110110 15:36:44|1|个人营销|19990808||4|20110110 15:36:44|");

//		sb.append("E-000002-11000000-S001-123123-20100527123430:000002-11000000-123123|1|姓名不能为空|20100527|1|20100527");
//		sb.append("E-000002-11000000-S001-123123-20100527123430: 000002-11000000-123125|1|姓名不能为空|20100527 12:12:12|1|20100527 12:10:12");
//		sb.append("R-I-000002-43000000-S003-123123-20100825190900:00007706662|000100|中国人民人寿保险股份有限公司|1110100|北京虚拟支公司本部|Allen.chen|1980-01-01|1|14|98568214|051225556661|13698585858|uw@163.com|1|在职|null|2005-01-01|null|43000000|null|null|null|null|null|2010-08-25 19:09:00.359|null|null|");
//		sb.append("R-I-000002-43000000-S003-123123-20100826142754:a12345879|000100|人保寿险子公司|4310100|北京虚拟支公司本部|交叉销售自测1|1980-01-01|1|14|98568214|051225556661|13698585858|uw@163.com|1|在职|01,02,03,04,05,|2005-01-01|null|I|43000000|null|null|null|null|null|20100826 14:27:54|null|null");
//		sb.append("</OPERATORCODE>");
//		sb.append("E-0000002-11000000-D001-11010000-20100527123430: 000002-11010000|1|机构名称不能为空 |20100527 12:12:12|1|20100527 12:10:12");
		sb.append("R-I-000002-97010000-D001-1103551-20100830151507:97010000|000002|人保财险|1440000|湖南||||||||||1||20100830 15:34:48||");
		sb.append("</MESSAGE>");
		sb.append("</sysnet>");
//		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><sysnet  xmlns:xsi=http://www.w3.org/2001/XMLSchema-instance xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><MESSAGE>R-I-000002-11000000-D001-11010000-20100527123430: R-I-000002-11000000-D001-11010000-20100527123430: 1110001 |000100|人保寿险|1110000 |北京|1110000 |北京市分公司|1110000 |北京市分公司|1110000 |北京市分公司|1|20051118|        |1 |正常|20091207 04:12:08||</MESSAGE></sysnet>";
		
		//测试下发机构校验结果
//		message="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
//			+ "<sysnet xmlns:xsi=\"  http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"  http://www.w3.org/2001/XMLSchema\">\n"
//			+ "<MESSAGE>"
//			+ "E-0000002-11000000-D001-11010000-20100527123430: 000002-11010000|1|机构名称不能为空 |20100527 12:12:12|1|20100527 12:10:12"
//			+ "</MESSAGE>\n</sysnet>";

		//测试下发销售人员
//		message="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
//			+ "<sysnet xmlns:xsi=\" http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\" http://www.w3.org/2001/XMLSchema\">\n"
//			+ "<MESSAGE>"
//			+ "R-I-000002-11000000-S001-11010000-20100527123430: 9508812078|000002|中国人民财产保险股份有限公司|||吴兰兰|20100527|2|35080200|龙岩新罗区支|11|身份证|450987201005270908||13678964542||1|在职|1|直销|I|20090429 12:10:52|20010729 12:10:52|1|直销|20091002|20100403|2|20090328 15:30:33|000085,000100"
//			+ "</MESSAGE>\n</sysnet>";

		
		//测试下发销售人员校验结果
//		message="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
//			+ "<sysnet xmlns:xsi=\"  http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"  http://www.w3.org/2001/XMLSchema\">\n"
//			+ "<MESSAGE>"
//			+ "E-000002-11000000-S001-11010000-20100527123430: 000002-11010000|1|销售人员名称不能为空 |20100527 12:12:12|1|20100527 12:10:12"
//			+ "</MESSAGE>\n</sysnet>";

		// 1. 出单员报送（根据员工代码）
//			sb.append("R-Q-000002-11000000-S003-123123-20100527123430: A000000000");
		// 2. 出单员下发（保存出单员信息）
		        //插入、修改、删除
		// 3. 出单员唯一编码报送
				
		// 4. 销售员数据报送
				
		// 5. 销售员校验结果下发
				//ok
		// 6. 销售员数据下发
				//插入、修改、删除
				//ok	
		try {
			Object result = test.webClientCall(netUrl, funEnName, new Object[]{sb.toString()});
			System.out.println((String)result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * <p>Title: axis1的jws调用方式</p>
	 * <p>Description: </p>
	 * @param netUrl
	 * @param funEnName
	 * @param par
	 * @return
	 * @throws Exception
	 */
	public Object webClientCall(String netUrl,String funEnName,Object par[])throws Exception 
	{
		Object obj=null;
		try
		{  
	        Service service =  new  Service(); 
	        Call call = (Call) service.createCall();  
	        QName qn=new  QName(netUrl, funEnName);
	        call.setOperationName(qn);    
	        call.setTargetEndpointAddress( new  URL(netUrl));    
	        obj = call.invoke( par);    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return obj;
	}

}
