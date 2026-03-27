package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKind;
import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngage;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngageId;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRateId;
import cn.com.sinosoft.dms.model.PrpDRationEngage;
import cn.com.sinosoft.dms.model.PrpDRationEngageId;
import cn.com.sinosoft.dms.model.PrpDclause;
import cn.com.sinosoft.dms.model.PrpDclauseReport;
import cn.com.sinosoft.dms.model.PrpDclauseReportId;
import cn.com.sinosoft.dms.model.PrpDcustomer;
import cn.com.sinosoft.dms.model.PrpDcustomerUnit;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDrationCondition;
import cn.com.sinosoft.dms.model.PrpDrationLimit;
import cn.com.sinosoft.dms.model.PrpDrationLimitId;
import cn.com.sinosoft.dms.model.PrpDrationShortrate;
import cn.com.sinosoft.dms.model.PrpDrationShortrateId;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.model.PrpDset;
import cn.com.sinosoft.dms.model.PrpDsetChannel;
import cn.com.sinosoft.dms.model.PrpDsetChannelId;
import cn.com.sinosoft.dms.model.PrpDsetRationrelation;
import cn.com.sinosoft.dms.model.PrpDsetRationrelationId;
import cn.com.sinosoft.dms.model.PrpDsetRenewal;
import cn.com.sinosoft.dms.model.PrpDsetRenewalId;
import cn.com.sinosoft.dms.model.PrpDstartPlace;
import cn.com.sinosoft.dms.model.PrpdChannelCoins;
import cn.com.sinosoft.dms.model.PrpdChannelCoinsId;
import cn.com.sinosoft.dms.model.PrpdChannelInfo;
import cn.com.sinosoft.dms.model.PrpdChannelInfoId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlan.GetPlanReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlan.GetPlanReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentCollectionConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedSetConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernateProxyConverter;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class GetPlanServiceImpl implements
		DataTransformer<GetPlanReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
	   
		DictPage dictPage = new DictPage();
		GetPlanReqPacket getPlanReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemcode = getPlanReqPacket.getHEAD().getSYSTEMCODE();
		String rationCode = getPlanReqPacket.getBODY().getRationCode();
		String riskCode = getPlanReqPacket.getBODY().getRiskCode();
		String[] comCodes=getPlanReqPacket.getBODY().getComCodes();
		String rationType=getPlanReqPacket.getBODY().getRationType();
		int pageNO = getPlanReqPacket.getHEAD().getPAGENO();
		int pageSize = getPlanReqPacket.getHEAD().getPAGESIZE();		
		System.out.println("ddd");
		if("getRationPlan".equals(rationType)){
		    Map<String, String> values = new HashMap<String, String>();
		    values.put("rationCode", rationCode);
		    values.put("riskCode", riskCode);
		    dictPage = dictionaryService.getListByPlanNo(systemcode, values);
		}else if("getSeriesList".equals(rationType)){
		    Map<String, String> values = new HashMap<String, String>();
            values.put("SeriesCode", rationCode);
            dictPage = dictionaryService.getSeriesListBySeriesNo(systemcode, values);
		}else if("getPrpdrationList".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("rationName", rationCode);
            dictPage = dictionaryService.getRationListByRationName(systemcode, values);
        }else if("getRationCodeDb".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("seriesCode", comCodes[0]);
            values.put("mainFalg", comCodes[1]);
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("rationCode", rationCode);
            
            dictPage = dictionaryService.getRationRalationListBySeriesCode(systemcode, values);
        }else if("getSetCodeDb".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("setCode", rationCode);   
            values.put("riskCode", riskCode);
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize)); 
            dictPage = dictionaryService.getSetCodeDb(systemcode, values);
        }else if("getStartPlace".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("startCode", rationCode);   
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize)); 
            values.put("riskCode", riskCode);
            values.put("flag",comCodes[0]);
            dictPage = dictionaryService.getStartPlaceInfo(systemcode, values);
        }else if("getUserInfoMC".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("userCode", rationCode);  
            values.put("customerEname", comCodes[1]);  
            values.put("lowerFee", comCodes[2]); 
            values.put("customerCname", comCodes[3]); 
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize)); 
            dictPage = dictionaryService.getUserCodeMCInfo(systemcode, values);
        }else if("saveUserInfoMC".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("userCode", comCodes[0]);  
            values.put("customerEname", comCodes[1]);  
            values.put("lowerFee", comCodes[2]); 
            values.put("customerCname", comCodes[3]); 
            dictPage = dictionaryService.saveUserCodeMCInfo(systemcode, values);
        }else if("getClauseReportNo".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("clauseCode", rationCode);
            dictPage = dictionaryService.getReportNoByClauseCode(systemcode, values);
        }else if("getEngageMaintenance".equals(rationType)){//add by yjm 特別約定附加條款查詢 20150331
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("clauseCode", comCodes[0]);
            values.put("engageCode", comCodes[1]);
            values.put("engageName", comCodes[2]);
            values.put("riskCode", comCodes[3]);
            values.put("validInd", comCodes[4]);
            dictPage = dictionaryService.getEngageMaintenance(systemcode, values);
        }else if("getClauseMaintenance".equals(rationType)){//add by yjm 條款查詢 20150331
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("kindCode", comCodes[0]);
            values.put("kindName", comCodes[1]);
            values.put("tcol1", comCodes[2]);//條款屬性
            values.put("riskCode", comCodes[3]);
            values.put("validInd", comCodes[4]);
            values.put("clauseCode", comCodes[5]);
            values.put("riskKCSerialNo", comCodes[6]);
            dictPage = dictionaryService.getClauseMaintenance(systemcode, values);
        }else if("getCopyNumber".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("clauseCode", rationCode);
            values.put("printSign", comCodes[0]);//是否列印
            values.put("riskCode", comCodes[1]);//險種
            values.put("riskName", comCodes[2]);//險種名稱
            values.put("kindCode", comCodes[3]);//條款代碼（險種代碼）
            dictPage = dictionaryService.getCopyNumber(systemcode, values);
        }else if("getCopyNumberClauseCode".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("clauseCode", comCodes[0]);
            values.put("reportno", comCodes[1]);
            dictPage = dictionaryService.getCopyNumberClauseCode(systemcode, values);
        }else if("getOccupation".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("codeCode", comCodes[0]);
            dictPage = dictionaryService.getOccupation(systemcode, values);
        }else if("getOccupationById".equals(rationType)){//add by yjm 20150714
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("codeType", comCodes[0]);
            values.put("codeCode", comCodes[1]);
            dictPage = dictionaryService.getOccupationById(systemcode, values);
        }else if("getDictRationPreium".equals(rationType)){
            Map<String, String> values = new HashMap<String, String>();
            values.put("age", comCodes[0]);//年龄
            values.put("birthday", comCodes[1]);//生日
            values.put("dutyLevel", comCodes[2]);//职业等级
            values.put("mainRelation", comCodes[3]);//身份别
            values.put("renewalFlag", comCodes[4]);//新/续保件
            values.put("sex", comCodes[5]);//性别
            values.put("riskCode", riskCode);//险种
            values.put("pageNo", String.valueOf(pageNO));
            values.put("pageSize", String.valueOf(pageSize));
            values.put("rationCode", rationCode);//方案代码
            
            dictPage = dictionaryService.getRationPreiumListByCondition(systemcode, values);
        }else if("getClauseInfo".equals(rationType)){//add by yjm 伤害险险种详细信息查询（通报用） 20150729
            Map<String, String> values = new HashMap<String, String>();
            values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
            values.put("clauseCode", comCodes[0]);
            dictPage = dictionaryService.getClauseInfo(systemcode, values);
        }else if("cleckSavePolicy".equals(rationType)){//add by lekaifeng 20160224
            Map<String, String> values = new HashMap<String, String>();
            values.put("setCode", comCodes[0]);
            values.put("businessOriginCode", comCodes[1]);
            dictPage = dictionaryService.cleckSavePolicy(values);
        }else if("findYear".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeType", comCodes[0]);
        	values.put("codeCode", comCodes[1]);
        	values.put("validStatus", comCodes[2]);
        	dictPage = dictionaryService.findYear(systemcode, values);
        }else if("insertNewYear".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeType", comCodes[0]);
        	values.put("codeCode", comCodes[1]);
        	values.put("validStatus", comCodes[2]);
        	dictionaryService.insertNewYear(systemcode, values);
        }else if("findprpDstartPlaceByQuery".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("pageNo", String.valueOf(pageNO)); 
            values.put("pageSize", String.valueOf(pageSize));
        	values.put("codeCode", comCodes[0]);
        	values.put("portName", comCodes[1]);
        	values.put("countries", comCodes[2]);
        	dictPage = dictionaryService.findprpDstartPlaceByQuery(systemcode, values);
        }else if("insertCheck".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeCode", comCodes[0]);
        	dictPage = dictionaryService.insertCheck(systemcode, values);
        }else if("insertPrpDstartPlace".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeCode", comCodes[0]);
        	values.put("portName", comCodes[1]);
        	values.put("countries", comCodes[2]);
        	values.put("validStatus", comCodes[3]);
        	dictionaryService.insertPrpDstartPlace(systemcode, values);
        }else if("deletePrpDstartPlace".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeCode", comCodes[0]);
        	dictionaryService.deletePrpDstartPlace(systemcode, values);
        }else if("searchStartPlace".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeCode", comCodes[0]);
        	dictPage = dictionaryService.searchStartPlace(systemcode, values);
        }else if("saveStartPlace".equals(rationType)){
        	Map<String, String> values = new HashMap<String, String>();
        	values.put("codeCode", comCodes[0]);
        	values.put("portName", comCodes[1]);
        	values.put("countries", comCodes[2]);
        	values.put("validStatus", comCodes[3]);
        	dictionaryService.saveStartPlace(systemcode, values);
        }else{
            System.out.println("险别代号查询"+systemcode+"--rationCode-"+rationCode+"-riskCode--"+riskCode+
                    "----comCodes--"+comCodes+"rationType--"+rationType+"pageNO---"+pageNO+"--pageSize-->"+pageSize);
		    dictPage = dictionaryService.getPlan(systemcode, rationCode, riskCode,comCodes, rationType,pageNO, pageSize);
		}		
		
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETPLAN,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		System.out.println(responsexml);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		//XStream xstream = new XStream(new DomDriver());
		final XStream xstream = new XStream() {
		    protected MapperWrapper wrapMapper(final MapperWrapper next) {
		      return new HibernateMapper(next);
		    }
		  };
		  xstream.registerConverter(new HibernateProxyConverter());
		  xstream.registerConverter(new HibernatePersistentCollectionConverter(xstream.getMapper()));
		  xstream.registerConverter(new HibernatePersistentMapConverter(xstream.getMapper()));
		  xstream.registerConverter(new HibernatePersistentSortedMapConverter(xstream.getMapper()));
		  xstream.registerConverter(new HibernatePersistentSortedSetConverter(xstream.getMapper()));

		xstream.alias("PrpDration",PrpDration.class);
		xstream.alias("PrpDrisk", PrpDrisk.class);
		xstream.alias("PrpDrationClauseKind", PrpDrationClauseKind.class);
		xstream.alias("PrpDrationClauseKindId", PrpDrationClauseKindId.class);
		xstream.alias("PrpDrationLimit", PrpDrationLimit.class);
		xstream.alias("PrpDrationLimitId", PrpDrationLimitId.class);
		xstream.alias("PrpDRationEngage", PrpDRationEngage.class);
		xstream.alias("PrpDRationEngageId", PrpDRationEngageId.class);
		xstream.alias("PrpDrationShortrate", PrpDrationShortrate.class);
		xstream.alias("PrpDstartPlace", PrpDstartPlace.class);
		xstream.alias("PrpDrationShortrateId", PrpDrationShortrateId.class);
		xstream.alias("PrpdChannelInfo", PrpdChannelInfo.class);// modify update by wpf
		xstream.alias("PrpdChannelInfoId", PrpdChannelInfoId.class);// modify update by wpf
		xstream.alias("PrpdChannelCoins", PrpdChannelCoins.class);// modify update by wpf
		xstream.alias("PrpdChannelCoinsId", PrpdChannelCoinsId.class);// modify update by wpf
		xstream.alias("PrpDclauseReportId", PrpDclauseReportId.class);//add by wangtao
		xstream.alias("PrpDclauseReport", PrpDclauseReport.class);//add by wangtao
		xstream.alias("PrpDChannelRationEngage", PrpDChannelRationEngage.class);// modify update by wpf
		xstream.alias("PrpDChannelRationEngageId", PrpDChannelRationEngageId.class);// modify update by wpf
		xstream.alias("PrpDChannelRationClauseKind", PrpDChannelRationClauseKindId.class);// modify update by wpf
		xstream.alias("PrpDChannelRationClauseKindId", PrpDChannelRationClauseKind.class);// modify update by wpf
		xstream.alias("PrpDChannelRationPeriodRate", PrpDChannelRationPeriodRate.class);// modify update by wpf
		xstream.alias("PrpDChannelRationPeriodRateId", PrpDChannelRationPeriodRateId.class);// modify update by wpf
		xstream.alias("PrpDsetRationrelation", PrpDsetRationrelation.class);// modify update by wpf
        xstream.alias("PrpDsetRationrelationId", PrpDsetRationrelationId.class);// modify update by wpf
        xstream.alias("PrpDset", PrpDset.class);// modify update by wpf
        xstream.alias("PrpDsetChannel", PrpDsetChannel.class);// modify update by wpf
        xstream.alias("PrpDsetRenewal", PrpDsetRenewal.class);// modify update by wpf

        xstream.alias("PrpDsetChannelId", PrpDsetChannelId.class);// modify update by wpf
        xstream.alias("PrpDsetRenewalId", PrpDsetRenewalId.class);// modify update by wpf
        
        xstream.alias("PrpDrationCondition", PrpDrationCondition.class);// modify update by wpf
        
        xstream.alias("PrpDcustomerUnit", PrpDcustomerUnit.class);
        xstream.alias("PrpDcustomer", PrpDcustomer.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
        //add by yjm 伤害险险种详细信息查询（通报用） 20150729 start
        xstream.alias("PrpDclause", PrpDclause.class);
        //add by yjm 伤害险险种详细信息查询（通报用） 20150729 end
		//add by yjm 20150331 特約及條款 start
        xstream.alias("PrpDriskEngage", PrpDriskEngage.class);
        xstream.alias("PrpDriskEngageId", PrpDriskEngageId.class);
        xstream.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
        xstream.alias("PrpDriskClauseKindId", PrpDriskClauseKindId.class);
        //add by yjm 20150331 特約及條款 end
		xstream.alias("DictPage", DictPage.class);
		xstream.alias("PrpDstartPlace", PrpDstartPlace.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPlanReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream(new DomDriver());
		xs.alias("GetPlanReqPacket", GetPlanReqPacket.class);
		xs.alias("GetPlanReqBody", GetPlanReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetPlanReqPacket ep = (GetPlanReqPacket) xs.fromXML(requestxml,
				new GetPlanReqPacket());
		return ep;
	}

}
