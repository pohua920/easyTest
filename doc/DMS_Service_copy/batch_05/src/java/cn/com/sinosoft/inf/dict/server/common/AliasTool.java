package cn.com.sinosoft.inf.dict.server.common;

import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getRiskEngage.GetRiskEngageReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getRiskEngage.GetRiskEngageReqPacket;

import com.sinosoft.dmsdriver.model.PrpDrisk;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class AliasTool {
	private static final AliasTool instance = new AliasTool();
	/** 获取实例 */
	public static AliasTool getInstance() {
		return instance;
	}
	public XStream alias(){
		XStream xs = new XStream();
		/**返回报文公用对象*/
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("GetRiskEngageReqPacket",GetRiskEngageReqPacket.class);
		xs.alias("GetRiskEngageReqBody",GetRiskEngageReqBody.class);
		xs.alias("PrpDriskEngage", PrpDriskEngage.class);
		xs.alias("PrpDriskEngageId", PrpDriskEngageId.class);
		return xs;
	}
}
