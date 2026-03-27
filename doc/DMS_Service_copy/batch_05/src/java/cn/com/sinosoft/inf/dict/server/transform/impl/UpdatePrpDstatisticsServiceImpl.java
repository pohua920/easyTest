package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDstatistics;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.updateprpdstatistics.UpdatePrpDstatisticsReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.updateprpdstatistics.UpdatePrpDstatisticsReqPacket;

import com.thoughtworks.xstream.XStream;

public class UpdatePrpDstatisticsServiceImpl implements
		DataTransformer<UpdatePrpDstatisticsReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();		//返回报文对象
		ResponseHeadSchema head;
		try {
			UpdatePrpDstatisticsReqPacket requestPacket = xmlToSchema(requestxml);		//请求报文转对象
			DictionaryService dictionaryService = (DictionaryService) ServiceFactory
					.getService("dictionaryService");// 获得数据字典接口服务实现类
			String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
			
			PrpDstatistics prpDstatistics = requestPacket.getBODY().getPrpDstatistics();
			if (prpDstatistics.getMakeCom() == null
					|| prpDstatistics.getOpCode() == null
					|| prpDstatistics.getKsdm() == null) {
				head = MessageUtil.setHeadMessage(
						ServiceInfoConst.ERRORCODE_FAIL,
						"MakeCom、OpCode、Ksdm、StatisticsYM不能为空",				//异常信息
						ServiceInfoConst.UPDATEPRPDSTATISTICS,
						ServiceInfoConst.RESPONSECODE_FAIL);
			} else {
				dictionaryService.updatePrpDstatistics(systemCode,
						prpDstatistics);
				head = MessageUtil.setHeadMessage(
						ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.UPDATEPRPDSTATISTICS,
						ServiceInfoConst.RESPONSECODE_SUCCESS);
			}
		} catch (Exception e) {
			head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_FAIL,
					e.getMessage(), ServiceInfoConst.UPDATEPRPDSTATISTICS,
					ServiceInfoConst.RESPONSECODE_FAIL);
			e.printStackTrace();
		}
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(null);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	/*
	 * 请求报文转对象
	 */
	public UpdatePrpDstatisticsReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("请求报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("UpdatePrpDstatisticsReqPacket",UpdatePrpDstatisticsReqPacket.class);
		xs.alias("RequestHeadPacket",RequestHeadPacket.class);
		xs.alias("UpdatePrpDstatisticsReqBody",UpdatePrpDstatisticsReqBody.class);
		UpdatePrpDstatisticsReqPacket ep = (UpdatePrpDstatisticsReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	/*
	 * 结果对象转报文
	 */
	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		/***********************************************/
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}
