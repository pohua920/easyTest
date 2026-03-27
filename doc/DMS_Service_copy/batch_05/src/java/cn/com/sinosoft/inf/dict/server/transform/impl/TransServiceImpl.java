package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.dms.model.IPServiceConfig;
import cn.com.sinosoft.dms.model.IPServiceConfigId;
import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDclauseKind;
import cn.com.sinosoft.dms.model.PrpDclauseKindId;
import cn.com.sinosoft.dms.model.PrpDcode;
import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.model.PrpDcoinsId;
import cn.com.sinosoft.dms.model.PrpDkind;
import cn.com.sinosoft.dms.model.PrpDkindCar;
import cn.com.sinosoft.dms.model.PrpDkindCarId;
import cn.com.sinosoft.dms.model.PrpDkindId;
import cn.com.sinosoft.dms.model.PrpDkindProduct;
import cn.com.sinosoft.dms.model.PrpDkindProductId;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.model.PrpDreinsurer;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpDriskClauseId;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskItemId;
import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskLimitId;
import cn.com.sinosoft.dms.model.PrpDsettlementByr;
import cn.com.sinosoft.dms.model.PrpDsettlementLkr;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.model.PrpDtreatyRetenId;
import cn.com.sinosoft.dms.model.PrpYDDagent;
import cn.com.sinosoft.dms.model.PrpdRegulation;
import cn.com.sinosoft.dms.vo.PrpDregulationVo;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestBodySchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.PrpDcodeInfo;

import com.sinosoft.dmsdriver.model.PrpDkindReport;
import com.sinosoft.dmsdriver.model.PrpDkindReportId;
import com.thoughtworks.xstream.XStream;

public class TransServiceImpl implements
		DataTransformer<RequestPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = new DictPage();
		PageResPacket pageResPacket = new PageResPacket();
		RequestPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String requestType = requestPacket.getHEAD().getREQUEST_TYPE();
		Map values = requestPacket.getBODY().getValues();
		/***************继续分发，根据不同的requestType发往不同的持久方法获取数据****start**/
		if (ServiceInfoConst.GETPRPDTREATYRETEN.equals(requestType)) {
			dictPage = dictionaryService.getPrpDtreatyReten(systemCode, values);
			 List<PrpDtreatyReten> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.PrpDtreatyReten> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.PrpDtreatyReten prpDtreatyReten = new  cn.com.sinosoft.dms.vo.PrpDtreatyReten();
					 BeanUtilsEx.copyProperties(prpDtreatyReten, list.get(i));
					 voList.add(prpDtreatyReten);
				 }
			 }
			 dictPage.setData(voList);
		}else if(ServiceInfoConst.GETRISKBYCONDITION.equals(requestType)){
			dictPage = dictionaryService.getPrpDriskByCondition(systemCode, values);
			 List<PrpDrisk> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.PrpDrisk> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.PrpDrisk prpDrisk = new  cn.com.sinosoft.dms.vo.PrpDrisk();
					 BeanUtilsEx.copyProperties(prpDrisk, list.get(i));
					 voList.add(prpDrisk);
				 }
			 }
			 dictPage.setData(voList);
		}else if(ServiceInfoConst.GETSERVICEINFOBYCODE.equals(requestType)){
			dictPage = dictionaryService.getServiceInfoByCode(systemCode, values);
			 List<IPServiceConfig> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.IPServiceConfig> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.IPServiceConfig iPServiceConfig = new  cn.com.sinosoft.dms.vo.IPServiceConfig();
					 BeanUtilsEx.copyProperties(iPServiceConfig, list.get(i));
					 voList.add(iPServiceConfig);
				 }
			 }
			 dictPage.setData(voList);
		}
		else if(ServiceInfoConst.GETSERVICEINFOBYCODES.equals(requestType)){
			dictPage = dictionaryService.getServiceInfoByEnvironmentCode(systemCode,values);
			 List<IPServiceConfig> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.IPServiceConfig> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.IPServiceConfig iPServiceConfig = new  cn.com.sinosoft.dms.vo.IPServiceConfig();
					 BeanUtilsEx.copyProperties(iPServiceConfig, list.get(i));
					 voList.add(iPServiceConfig);
				 }
			 }
			 dictPage.setData(voList);
		}
		else if(ServiceInfoConst.GETURLBYCODE.equals(requestType)){
			dictPage = dictionaryService.getUrlByCode(systemCode, values);
		}else if(ServiceInfoConst.GETAGENT.equals(requestType)){
			dictPage = dictionaryService.getAgent(systemCode, values);
			boolean classFlag = true;
			if(dictPage.getData().size()>0){
				if("PrpDagent".equals(dictPage.getData().get(0).getClass().getSimpleName())){
					classFlag = true;
				}else{
					classFlag = false;
				}
			}
			if(classFlag){
				List<PrpDagent> list = dictPage.getData();
				 List<cn.com.sinosoft.dms.vo.PrpDagent> voList = new ArrayList();
				 if(list!=null&&list.size()>0){
					 for (int i = 0;i<list.size();i++){
						 cn.com.sinosoft.dms.vo.PrpDagent prpDagent = new  cn.com.sinosoft.dms.vo.PrpDagent();
						 BeanUtilsEx.copyProperties(prpDagent, list.get(i));
						 voList.add(prpDagent);
					 }
				 }
				 dictPage.setData(voList);
			}
			if(!classFlag){
				 List<PrpYDDagent> list = dictPage.getData();
				 List<cn.com.sinosoft.dms.vo.PrpYDDagent> voList = new ArrayList();
				 if(list!=null&&list.size()>0){
					 for (int i = 0;i<list.size();i++){
						 cn.com.sinosoft.dms.vo.PrpYDDagent PrpYDDagent = new  cn.com.sinosoft.dms.vo.PrpYDDagent();
						 BeanUtilsEx.copyProperties(PrpYDDagent, list.get(i));
						 voList.add(PrpYDDagent);
					 }
				 }
				 dictPage.setData(voList);
			}
		} else if (ServiceInfoConst.GETAGENTBYCODE.equals(requestType)) {
			String agentCode = (String)values.get("agentCode");
			int pageNo = requestPacket.getHEAD().getPAGENO();
			int pageSize = requestPacket.getHEAD().getPAGESIZE();
			dictPage = dictionaryService.getAgent(systemCode, agentCode,pageNo,pageSize);
			List<PrpDagent> list = dictPage.getData();
			List<cn.com.sinosoft.dms.vo.PrpDagent> voList = new ArrayList();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					cn.com.sinosoft.dms.vo.PrpDagent prpDagent = new cn.com.sinosoft.dms.vo.PrpDagent();
					BeanUtilsEx.copyProperties(prpDagent, list.get(i));
					voList.add(prpDagent);
				}
			}
			dictPage.setData(voList);
		}
		else if(ServiceInfoConst.GETRISKCLAUSE.equals(requestType)){
			String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
			values.put("validStatus", validStatus);
			dictPage = dictionaryService.getRiskClause(systemCode, values);
			 List<PrpDagent> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.PrpDriskClause> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.PrpDriskClause prpDriskClause = new  cn.com.sinosoft.dms.vo.PrpDriskClause();
					 BeanUtilsEx.copyProperties(prpDriskClause, list.get(i));
					 voList.add(prpDriskClause);
				 }
			 }
			 dictPage.setData(voList);
		}//add by xuli 20130623 
		else if(ServiceInfoConst.GETPRPDKINDREPORT.equals(requestType)){
		    dictPage = dictionaryService.getPrpDkindReport(systemCode, values);
	           List<cn.com.sinosoft.dms.model.PrpDkindReport> list = dictPage.getData();
	             List<cn.com.sinosoft.dms.model.PrpDkindReport> voList = new ArrayList<cn.com.sinosoft.dms.model.PrpDkindReport>();
	             if(list!=null&&list.size()>0){
	                 for (int i = 0;i<list.size();i++){
	                	 cn.com.sinosoft.dms.model.PrpDkindReport prpDkindReport = new cn.com.sinosoft.dms.model.PrpDkindReport();
	                     BeanUtilsEx.copyProperties(prpDkindReport, list.get(i));
	                     voList.add(prpDkindReport);
	                 }
	             }
	       dictPage.setData(voList);     
		}
		//add by linzhongxia 
		else if(ServiceInfoConst.GETPRPDKINDPRODUCT.equals(requestType)){
		    dictPage = dictionaryService.getPrpDkindProduct(systemCode, values);
	           List<PrpDkindProduct> list = dictPage.getData();
	             List<PrpDkindProduct> voList = new ArrayList<PrpDkindProduct>();
	             if(list!=null&&list.size()>0){
	                 for (int i = 0;i<list.size();i++){
	                     PrpDkindProduct prpDkindProduct = new  PrpDkindProduct();
	                     BeanUtilsEx.copyProperties(prpDkindProduct, list.get(i));
	                     voList.add(prpDkindProduct);
	                 }
	             }
	       dictPage.setData(voList);     
		}
		//add by zhongjiang 
		else if(ServiceInfoConst.ALLOWCARKIND.equals(requestType)){
		    dictPage = dictionaryService.getAllowcarKind(systemCode,values);
	           List<PrpDkindCar> list = dictPage.getData();
	             List<PrpDkindCar> voList = new ArrayList<PrpDkindCar>();
	             if(list!=null&&list.size()>0){
	                 for (int i = 0;i<list.size();i++){
	                	 PrpDkindCar prpDkindCar = new  PrpDkindCar();
	                     BeanUtilsEx.copyProperties(prpDkindCar, list.get(i));
	                     voList.add(prpDkindCar);
	                 }
	             }
	       dictPage.setData(voList);     
		}
		//MODIFY BEGIN-ADD-chenyi-20110513-reason:查询社保地方政策资料
		else if(ServiceInfoConst.GETINFOMATION.equals(requestType)){
			//String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
			//values.put("validStatus", validStatus);
			
			 dictPage = dictionaryService.getInfomation(systemCode, values);
			 List<PrpdRegulation> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.PrpDregulationVo> voList = new ArrayList<cn.com.sinosoft.dms.vo.PrpDregulationVo>();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.PrpDregulationVo prpDregulationVo = new  cn.com.sinosoft.dms.vo.PrpDregulationVo();
					 BeanUtilsEx.copyProperties(prpDregulationVo, list.get(i));
					 voList.add(prpDregulationVo);
				 }
			 }
			 dictPage.setData(voList);
		}
	    //MODIFY END-ADD-chenyi-20110513-reason:查询社保地方政策资料
		else if(ServiceInfoConst.GETRISKITEM.equals(requestType)){
			dictPage = dictionaryService.getRiskItem(systemCode, values);
			 List<PrpDriskItem> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.model.PrpDriskItem> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.model.PrpDriskItem prpDriskItem = new cn.com.sinosoft.dms.model.PrpDriskItem();
					 BeanUtilsEx.copyProperties(prpDriskItem, list.get(i));
					 voList.add(prpDriskItem);
				 }
			 }
			 dictPage.setData(voList);
		}
		else if(ServiceInfoConst.GETRISKILIMIT.equals(requestType)){
			dictPage = dictionaryService.getRiskLimit(systemCode, values);
			 List<PrpDriskLimit> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.model.PrpDriskLimit> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.model.PrpDriskLimit prpDriskLimit = new cn.com.sinosoft.dms.model.PrpDriskLimit();
					 BeanUtilsEx.copyProperties(prpDriskLimit, list.get(i));
					 voList.add(prpDriskLimit);
				 }
			 }
			 dictPage.setData(voList);
		}
		else if(ServiceInfoConst.GETREVERRISKIENGAGE.equals(requestType)){
			dictPage = dictionaryService.getReverseRiskEngage(systemCode, values);
			 List<PrpDriskEngage> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.model.PrpDriskEngage> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.model.PrpDriskEngage prpDriskEngage = new cn.com.sinosoft.dms.model.PrpDriskEngage();
					 BeanUtilsEx.copyProperties(prpDriskEngage, list.get(i));
					 voList.add(prpDriskEngage);
				 }
			 }
			 dictPage.setData(voList);
		}
		//added by yuyiqiang 20130226 begin 条款、险别关系查询
		else if(ServiceInfoConst.GETPRPDCLAUSEKIND.equals(requestType)){
			String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
//			String reverseType = requestPacket.getHEAD().;
//			values.put("validStatus", validStatus);
			values.put("validStatus", validStatus);
//			dictPage = dictionaryService.getPrpDclauseKind(systemCode, values);
			dictPage = dictionaryService.getPrpDclauseKind(systemCode, values);
			 List<PrpDclauseKind> list = dictPage.getData();
			 List<PrpDclauseKind> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 PrpDclauseKind prpDclauseKind = new PrpDclauseKind();
					 BeanUtilsEx.copyProperties(prpDclauseKind, list.get(i));
					 voList.add(prpDclauseKind);
				 }
			 }
			 dictPage.setData(voList);
		}//added by yuyiqiang 20130226 end
		else if(ServiceInfoConst.GETRISKCLAUSEKIND.equals(requestType)){
			String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
			values.put("validStatus", validStatus);
			dictPage = dictionaryService.getRiskClauseKind(systemCode, values);
			 List<PrpDagent> list = dictPage.getData();
			 List<PrpDriskClauseKind> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 PrpDriskClauseKind prpDriskClauseKind = new PrpDriskClauseKind();
					 BeanUtilsEx.copyProperties(prpDriskClauseKind, list.get(i));
					 voList.add(prpDriskClauseKind);
				 }
			 }
			 dictPage.setData(voList);
		}
		//modify begin by renshuo 2011-5-18 reason:增加二级责任查询
		else if(ServiceInfoConst.GETRISKCLAUSEKINDSUB.equals(requestType)){
			String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
			values.put("validStatus", validStatus);
			dictPage = dictionaryService.getRiskClauseKindSub(systemCode, values);
			 List<PrpDagent> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.PrpDriskClauseKind> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.PrpDriskClauseKind prpDriskClauseKind = new  cn.com.sinosoft.dms.vo.PrpDriskClauseKind();
					 BeanUtilsEx.copyProperties(prpDriskClauseKind, list.get(i));
					 voList.add(prpDriskClauseKind);
				 }
			 }
			 dictPage.setData(voList);
		}
		//modify end by renshuo 2011-5-18 reason:增加二级责任查询
		//modify begin by renshuo 2011-07-12 reason:增加条款责任互斥条件查询
		else if(ServiceInfoConst.GETRISKCLAUSEKINDRELATION.equals(requestType)){
			dictPage = dictionaryService.getRiskClauseKindRelation(systemCode, values);
			 List<PrpDagent> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation prpDriskClauseKindRelation = new cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation();
					 BeanUtilsEx.copyProperties(prpDriskClauseKindRelation, list.get(i));
					 voList.add(prpDriskClauseKindRelation);
				 }
			 }
			 dictPage.setData(voList);
		}
		//modify end by renshuo 2011-07-12 reason:增加条款责任互斥条件查询
		//旧险别表
		else if(ServiceInfoConst.PRPDKIND.equals(requestType)){
			dictPage = dictionaryService.getPrpDkind(systemCode, values);
		}
		//added by wanglianzhou 20130414 客户 个人 单位 关联方 信息校验 begin
		else if(ServiceInfoConst.GETPRPDCUSTOMERIDV.equals(requestType)){
			dictPage = dictionaryService.getPrpDcustomerIdv(systemCode, values);
		}
		//add by fengyang 20140520 查询船舶校验信息
		else if(ServiceInfoConst.GETPRDITEMSHIP.equals(requestType)){
			dictPage = dictionaryService.getPrDitemShip(systemCode, values);
		}
		//add by fengyang 20140520 查询船舶校验信息
	   else if(ServiceInfoConst.GETPRDITEMPLANE.equals(requestType)){
			dictPage = dictionaryService.getPrDplane(systemCode, values);
		}
		//added by wanglianzhou 20130423 新增客户
		else if(ServiceInfoConst.SAVEPRPDCUSTOMERIDV.equals(requestType)){
			System.out.println("-------------進入個人保存方法1--------------");
			dictPage = dictionaryService.savePrpDcustomerIdv(systemCode, values);
		}
		//add by fengyang 20140520  保存新增船舶信息
	    else if(ServiceInfoConst.SAVEPRPDITEMSHIP.equals(requestType)){
			dictPage = dictionaryService.savePrpDitemShip(systemCode, values);
		}
		//add by fengyang 20140526  保存新增飞机信息
	    else if(ServiceInfoConst.SAVEPRPDPLANE.equals(requestType)){
			dictPage = dictionaryService.savePrpDplane(systemCode, values);
		}
		//add by yjm  保存條款  20150331 start
	    else if(ServiceInfoConst.SAVECLAUSEMAINTENANCE.equals(requestType)){
			dictPage = dictionaryService.saveClauseMaintenance(systemCode, values);
		}
		//add by  yjm  保存條款  20150331 end  
		//add by yjm  保存特约  20150331 start
	    else if(ServiceInfoConst.SAVEENGAGEMAINTENANCE.equals(requestType)){
			dictPage = dictionaryService.saveEngageMaintenance(systemCode, values);
		}
		//add by  yjm  保存特约  20150331 end  
		//add by mjx  保存文案号  20150225
	    else if(ServiceInfoConst.SAVECOPYNUMBER.equals(requestType)){
			dictPage = dictionaryService.saveCopyNumber(systemCode, values);
		}
		//add by  mjx  保存文案号  20150225 end      
		//add by mjx  保存职业类别  20150302
	    else if(ServiceInfoConst.SAVEORUPDATEOCCUPATION.equals(requestType)){
			dictPage = dictionaryService.saveOrUpdateOccupation(systemCode, values);
		}
		//add by  mjx  保存职业类别  20150302 end     
		else if(ServiceInfoConst.GETPRPDCUSTOMERUNIT.equals(requestType)){
			dictPage = dictionaryService.getPrpDcustomerUnit(systemCode, values);
		}
		else if(ServiceInfoConst.SAVEPRPDCUSTOMERUNIT.equals(requestType)){
			System.out.println("-----------進入單位保存方法1------------");
			dictPage = dictionaryService.savePrpDcustomerUnit(systemCode, values);
		}
		else if(ServiceInfoConst.GETPRPDCUSTOMERFXQ.equals(requestType)){
			System.out.println("--------進入查詢FXQ方法1-----------");
			dictPage=dictionaryService.getPrpDcustomerFXQ(systemCode, values);
		}
		//added by wanglianzhou 20130414 客户 个人 单位 关联方 信息校验 end
		//旧限额免赔额		
		else if(ServiceInfoConst.PRPDLIMIT.equals(requestType)){
			dictPage = dictionaryService.getPrpDlimit(systemCode, values);
		}
		else if(ServiceInfoConst.GETACCOUNTINFO.equals(requestType)){
			dictPage = dictionaryService.getAccountInfo(systemCode, values);
			 List<PrpDagent> list = dictPage.getData();
			 List<cn.com.sinosoft.dms.vo.PrpDaccountInfo> voList = new ArrayList();
			 if(list!=null&&list.size()>0){
				 for (int i = 0;i<list.size();i++){
					 cn.com.sinosoft.dms.vo.PrpDaccountInfo prpDaccountInfo = new  cn.com.sinosoft.dms.vo.PrpDaccountInfo();
					 BeanUtilsEx.copyProperties(prpDaccountInfo, list.get(i));
					 voList.add(prpDaccountInfo);
				 }
			 }
			 dictPage.setData(voList);
		}
		//分保接受人
		else if(ServiceInfoConst.GETREINSURER.equals(requestType)){
			dictPage = dictionaryService.getReinsurer(systemCode, values);
		}
		//共保人
		else if(ServiceInfoConst.GETCOINS.equals(requestType)){
			dictPage = dictionaryService.getCoins(systemCode, values);
		}
		//限额免赔额
		else if(ServiceInfoConst.PRPDRISKLIMIT.equals(requestType)){
			dictPage = dictionaryService.getPrpDriskLimit(systemCode, values);
		}
		//
		else if(ServiceInfoConst.PRPDSETTLEMEMTBYR.equals(requestType)){
			dictPage  = dictionaryService.getPrpDsettlementByr(systemCode, values);
		}
		//
		else if(ServiceInfoConst.PRPDSETTLEMENTLKR.equals(requestType)){
			dictPage  = dictionaryService.getPrpDsettlementLkr(systemCode, values);
		}
		else if(ServiceInfoConst.GETPRPDOLDCODE.equals(requestType)){
			PrpDcode prpDcode  = dictionaryService.getPrpDoldCode(systemCode, values);
			PrpDcodeInfo codeInfo = new PrpDcodeInfo();
			codeInfo.setId(new PrpDnewCodeId());
			codeInfo.getId().setCodeType(prpDcode.getId().getCodeType());
			codeInfo.getId().setCodeCode(prpDcode.getId().getCodeCode());
			codeInfo.setCodeCName(prpDcode.getCodeCName());
			codeInfo.setCodeEName(prpDcode.getCodeEName());
			codeInfo.setNewCodeCode(prpDcode.getNewCodeCode());
			codeInfo.setValidStatus(prpDcode.getValidStatus());
			codeInfo.setFlag(prpDcode.getFlag());
			dictPage.getData().add(codeInfo);
		}
		// 增加新接口，就要增加分发，通过if else 分开
		/*****************继续分发，根据不同的requestType发往不同的持久方法获取数据****end***/
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS,
				requestType, ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		//---------------------------------------------------------
		xstream.alias("PrpDtreatyReten", cn.com.sinosoft.dms.vo.PrpDtreatyReten.class);
		xstream.alias("PrpDtreatyRetenId", PrpDtreatyRetenId.class);
		xstream.alias("PrpDrisk", cn.com.sinosoft.dms.vo.PrpDrisk.class);
		xstream.alias("IPServiceConfig", cn.com.sinosoft.dms.vo.IPServiceConfig.class);
		xstream.alias("IPServiceConfigId", IPServiceConfigId.class);
		xstream.alias("PrpDagent", cn.com.sinosoft.dms.vo.PrpDagent.class);
		xstream.alias("PrpYDDagent", cn.com.sinosoft.dms.vo.PrpYDDagent.class);
		//and by xuli 20130623
		xstream.alias("PrpDkindReport", cn.com.sinosoft.dms.model.PrpDkindReport.class);
		xstream.alias("PrpDkindReportId", cn.com.sinosoft.dms.model.PrpDkindReportId.class);
		//and by xuli 20130623
		xstream.alias("PrpDriskClause", cn.com.sinosoft.dms.vo.PrpDriskClause.class);
		xstream.alias("PrpDriskClauseId", PrpDriskClauseId.class);
		//added by yuyiqiang 20132025 begin 对应prpdkind类转换
		xstream.alias("PrpDkind", cn.com.sinosoft.dms.model.PrpDkind.class);
		xstream.alias("PrpDkindId", PrpDkindId.class);
		//added by yuyiqiang 20130225 end
		//added by wanglianzhou 20130418 start 
		xstream.alias("PrpDcustomer", cn.com.sinosoft.dms.model.PrpDcustomer.class);
		xstream.alias("PrpDcustomerUnit",com.sinosoft.dmsdriver.model.PrpDcustomerUnit.class);
		xstream.alias("PrpDcustomerIdv", com.sinosoft.dmsdriver.model.PrpDcustomerIdv.class);
		xstream.alias("PrpDcustomerFine", cn.com.sinosoft.dms.model.PrpDcustomerFine.class);
		xstream.alias("PrpDcustomerFXQ", cn.com.sinosoft.dms.model.PrpDcustomerFXQ.class);
		xstream.alias("PrpDcustomerFineId", cn.com.sinosoft.dms.model.PrpDcustomerFineId.class);
		xstream.alias("PrpDcustomerRelation", cn.com.sinosoft.dms.model.PrpDcustomerRelation.class);
		// added by wanglianzhou 20130418 end
		xstream.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
		xstream.alias("PrpDriskClauseKindId", PrpDriskClauseKindId.class);
		xstream.alias("PrpDkindProduct", PrpDkindProduct.class);
		xstream.alias("PrpDkindProductId", PrpDkindProductId.class);
		
		xstream.alias("PrpDkindCar", PrpDkindCar.class);
		xstream.alias("PrpDkindCarId", PrpDkindCarId.class);
		
		xstream.alias("PrpDclauseKind", PrpDclauseKind.class);
		xstream.alias("PrpDclauseKindId", PrpDclauseKindId.class);
		
		xstream.alias("PrpDcodeInfo", PrpDcodeInfo.class);
		xstream.alias("PrpDCodeId", PrpDnewCodeId.class);
		xstream.alias("PrpDaccountInfo", cn.com.sinosoft.dms.vo.PrpDaccountInfo.class);
		xstream.alias("PrpDreinsurer", PrpDreinsurer.class);
		xstream.alias("PrpDcoins", PrpDcoins.class);
		xstream.alias("PrpDcoinsId", PrpDcoinsId.class);
		xstream.alias("PrpDriskLimitId",PrpDriskLimitId.class);
		xstream.alias("PrpDriskLimit",PrpDriskLimit.class);
		xstream.alias("PrpDsettlementLkr",PrpDsettlementLkr.class);
		xstream.alias("PrpDsettlementByr",PrpDsettlementByr.class);
		xstream.alias("PrpDriskItem", PrpDriskItem.class);
		xstream.alias("PrpDriskItemId", PrpDriskItemId.class);
		xstream.alias("PrpDriskLimit", PrpDriskLimit.class);
		xstream.alias("PrpDriskLimitId", PrpDriskLimitId.class);
		xstream.alias("PrpDriskEngage",PrpDriskEngage.class);
		xstream.alias("PrpDriskEngageId", PrpDriskEngageId.class);
		//MODIFY BEGIN-ADD-chenyi-20110514-reason:添加返回政策资料解析类型
		xstream.alias("PrpDregulationVo", PrpDregulationVo.class);
		//MODIFY END-ADD-chenyi-20110514-reason:添加返回政策资料解析类型
		//MODIFY BEGIN-ADD-renshuo-20110514-reason:添加二级责任查询
		xstream.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
		//MODIFY END-ADD-renshuo-20110514-reason:添加二级责任查询
		//modify add begin by renshuo 2011-07-12 reason:增加责任条款互斥依赖
		xstream.alias("PrpDriskClauseKindRelationId", cn.com.sinosoft.dms.model.PrpDriskClauseKindRelationId.class);
		xstream.alias("PrpDriskClauseKindRelation", cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation.class);
		xstream.alias("PrpDrationClauseKindId", cn.com.sinosoft.dms.model.PrpDrationClauseKindId.class);
        xstream.alias("PrpDrationClauseKind", cn.com.sinosoft.dms.model.PrpDrationClauseKind.class);
		//modify end begin by renshuo 2011-07-12 reason:增加责任条款互斥依赖
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public RequestPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("RequestPacket", RequestPacket.class);
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		xs.alias("RequestBodySchema", RequestBodySchema.class);
		RequestPacket ep = (RequestPacket) xs.fromXML(requestxml,
				new RequestPacket());
		return ep;
	}
}
