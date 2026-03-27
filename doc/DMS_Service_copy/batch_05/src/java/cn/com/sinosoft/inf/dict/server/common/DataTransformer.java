package cn.com.sinosoft.inf.dict.server.common;


/**服务器端
 * 2009.7.29 by ain
 * */

public interface DataTransformer<Request, Response> {

	/** xml To requestSchema */
	public Request xmlToSchema(String requestxml) throws Exception;

	/** responseSchema To Xml */
	public String schemaToXml(Response responsePacket) throws Exception;

	/**服务器接收的xml通过业务处理转换成发送到客户端的xml*/
	public String execute(String requestxml) throws Exception;
}
