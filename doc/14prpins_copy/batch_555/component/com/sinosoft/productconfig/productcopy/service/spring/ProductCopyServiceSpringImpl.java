package com.sinosoft.productconfig.productcopy.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.productconfig.common.schema.model.PrpDSimpletreaty;
import com.sinosoft.productconfig.common.schema.model.PrpDTreatyReten;
import com.sinosoft.productconfig.common.schema.model.PrpDblockClauseKind;
import com.sinosoft.productconfig.common.schema.model.PrpDcoeff;
import com.sinosoft.productconfig.common.schema.model.PrpDriskBlock;
import com.sinosoft.productconfig.common.schema.model.PrpDriskTemplate;
import com.sinosoft.productconfig.common.schema.model.UtiBackRuleConfig;
import com.sinosoft.productconfig.common.schema.model.UtiBackRuleConfigId;
import com.sinosoft.productconfig.common.schema.model.UtiDecisionTable;
import com.sinosoft.productconfig.common.schema.model.UtiFactor;
import com.sinosoft.productconfig.common.schema.model.UtiFactorRelaShip;
import com.sinosoft.productconfig.common.schema.model.UtiFormula;
import com.sinosoft.productconfig.common.schema.model.UtiJSFunc;
import com.sinosoft.productconfig.common.schema.model.UtiPrintConfig;
import com.sinosoft.productconfig.common.schema.model.UtiRiskProcessConfig;
import com.sinosoft.productconfig.common.schema.productmanage.PrpDrisk;
import com.sinosoft.productconfig.common.schema.productmanage.PrpDriskClause;
import com.sinosoft.productconfig.common.schema.vo.PeripheralAccountInfoVO;
import com.sinosoft.productconfig.common.schema.vo.PeripheralCodeVO;
import com.sinosoft.productconfig.common.schema.vo.ProductProcessVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDrationExtVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskBlockVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskClauseExtVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskClauseKindExtVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskExtVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskLimitVO;
import com.sinosoft.productconfig.common.schema.vo.PrpDriskRelatedExtSetVO;
import com.sinosoft.productconfig.common.schema.vo.UtiFormulaInfoVO;
import com.sinosoft.productconfig.common.util.ProductConstants;
import com.sinosoft.productconfig.pageconfig.risk.service.facade.RiskService;
import com.sinosoft.productconfig.peripheral.service.facade.PeripheralService;
import com.sinosoft.productconfig.productcopy.service.facade.ProductCopyService;
import com.sinosoft.prpins.common.util.BoCopyUtil;

/**
 * 產品複製實現類
 * @author Sinosoft
 */

public class ProductCopyServiceSpringImpl extends 
	GenericDaoHibernate<String,String> implements ProductCopyService {
	

	
	/**
	 * 查詢產品複製信息
	 * @param riskCodeConfigure 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @param productCopyTagFlag 產品複製標誌
	 * @return list 產品複製list對象
	 * @throws Exception
	 */
	public List<Object> findProductCopyReSetMSG(String riskCodeConfigure,String riskCodeCopy,String referenceRiskFlag,String productCopyTagFlag) throws Exception{
		HashMap<String, Object>  clauseHashMap = new HashMap<String, Object>();
		HashMap<String, Object>  clauseKindHashMap = new HashMap<String, Object>();
		List<Object> riskConfigAllDateList = new ArrayList<Object>(0);
		/**組織配置產品和復制產品的的條款（條款責任）匹配上的條款（條款責任）*/
		//條款的匹配
		StringBuffer clauseMsgSql = new StringBuffer();
		clauseMsgSql.append("select a.id.clauseCode from PrpDriskClause a where 1 = 1  ");
		clauseMsgSql.append(" and a.id.clauseCode in (  ");
		clauseMsgSql.append(" select b.id.clauseCode  from PrpDriskClause b where 1 = 1 and b.id.riskCode =  ? ) ");
		clauseMsgSql.append(" and a.id.riskCode = ? group by a.id.clauseCode");
		List<String> clauseMsgList = super.findByHql(clauseMsgSql.toString(), riskCodeConfigure,riskCodeCopy);
		/**將配置產品和被復制產品匹配上的條款組合到hashMap中*/
		for(int i = 0;i < clauseMsgList.size();i++){
			String clauseCode = clauseMsgList.get(i).trim();
			clauseHashMap.put(clauseCode, clauseCode);
		}
		//條款責任的匹配
		StringBuffer clauseKindMsgSql = new StringBuffer();
		clauseKindMsgSql.append("select a.id.clauseCode,a.kindCode from PrpDriskClauseKind a,PrpDriskClauseKind b where 1 = 1  ");
		clauseKindMsgSql.append(" and (a.id.clauseCode = b.id.clauseCode and a.kindCode = b.kindCode ) and b.id.riskCode =  ?  ");
		clauseKindMsgSql.append(" and a.id.riskCode = ? group by a.id.clauseCode,a.kindCode ");
		List<Object[]> clauseKindMsgKindList = super.findByHql(clauseKindMsgSql.toString(), riskCodeConfigure,riskCodeCopy);
		/**將配置產品和被復制產品匹配上的條款責任組合到hashMap中*/
		for(int i = 0;i < clauseKindMsgKindList.size();i++){
			Object[] prpDriskClauseKind = clauseKindMsgKindList.get(i);
			String clauseKindCode = prpDriskClauseKind[0].toString().trim()+","+prpDriskClauseKind[1].toString().trim();
			clauseKindHashMap.put(clauseKindCode,clauseKindCode);
		}
		/**產品配置中有條款、條款責任為PUB 的數據*/
		clauseHashMap.put("PUB", "PUB");
		clauseKindHashMap.put("PUB,PUB", "PUB,PUB");
		/**頁面配置信息的整理*/
		List<PrpDriskBlockVO> prpDriskBlockVOList = null;
		if("pageCopy".equals(productCopyTagFlag)){
			//1代表的是復制的是產品的信息
			if("1".equals(referenceRiskFlag)){
				//復制的是產品的信息
				prpDriskBlockVOList = this.findRiskConfigPageMSG(riskCodeCopy, clauseHashMap, clauseKindHashMap);//頁面配置信息的獲取			
			}else{
				//參考模板的時候顯示模板的信息
				prpDriskBlockVOList = this.findRiskModelConfigPageMSG(riskCodeCopy, clauseHashMap, clauseKindHashMap);
			}
			riskConfigAllDateList.add(prpDriskBlockVOList);
		}
		
		/**計算信息的整理*/
		else if("formulaCopy".equals(productCopyTagFlag)){
			List<UtiFormulaInfoVO> utiFormulaInfoVO = this.findFormulaListMSG(riskCodeCopy, clauseHashMap, clauseKindHashMap);	
			riskConfigAllDateList.add(utiFormulaInfoVO);
		}
		/**業務規則信息的整理*/
		else if("frontRuleCopy".equals(productCopyTagFlag)){
			List<UtiJSFunc> utiJSFuncList = this.findRiskRuleFrontMSG(riskCodeCopy);//前臺規則的處理
			riskConfigAllDateList.add(utiJSFuncList);
		}
		else if("backRuleCopy".equals(productCopyTagFlag)){
			List<UtiBackRuleConfig> utiBackRuleConfigList = this.findRiskConfigBackRuleMSG(riskCodeCopy,clauseHashMap,clauseKindHashMap);//后臺規則的處理
			riskConfigAllDateList.add(utiBackRuleConfigList);
		}
		else if("RiskConfigExtCopy".equals(productCopyTagFlag)){
			/**產品配置基本信息的組織，查找配置產品的產品信息*/
			PrpDrisk prpDrisk = new PrpDrisk(); 
			String prpDriskSql = "select a from PrpDrisk a where 1 = 1 and a.riskCode = ? ";
			List<PrpDrisk> prpDriskList = super.findByHql(prpDriskSql, riskCodeConfigure);//被配置產品的基本信息
			if(prpDriskList != null && !prpDriskList.isEmpty()){
				prpDrisk = prpDriskList.get(0);
			}
			if("1".equals(referenceRiskFlag) || "2".equals(referenceRiskFlag)){
				List<Object> list = this.findRiskConfigExtMSG(prpDrisk, riskCodeCopy, clauseHashMap, clauseKindHashMap,referenceRiskFlag);//擴展信息的獲取和條款責任的集合
				PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)list.get(0);//擴展信息	
				riskConfigAllDateList.add(prpDriskRelatedExtSetVO);
			}else{
				List<Object> list = this.findRiskConfigExtNotReferenceMSG(prpDrisk);//擴展信息的獲取和條款責任的集合
				PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)list.get(0);//擴展信息	
				riskConfigAllDateList.add(prpDriskRelatedExtSetVO);
			}
		}
		else if("peripheralCodeCopy".equals(productCopyTagFlag)){
			/**周邊系統數據的整理*/
			PeripheralService peripheralService = (PeripheralService) ServiceFactory.getService("peripheralService");
			List<PeripheralCodeVO> peripheralCodeVOList = peripheralService.copyPeripheralCode(riskCodeCopy);//數據字典的數據的獲取
			riskConfigAllDateList.add(peripheralCodeVOList);
		}
		else if("printConfigCopy".equals(productCopyTagFlag)){
			List<UtiPrintConfig> utiPrintConfigList = this.findPeripheralPrintMSG(riskCodeCopy);//打印參數配置的信息
			riskConfigAllDateList.add(utiPrintConfigList);
		}
		//查找系數配置的信息
		else if("coeffConfigCopy".equals(productCopyTagFlag)){
			List<PrpDcoeff> prpDcoeffList = this.findProductCoeffConfigMSG(riskCodeCopy,clauseHashMap,clauseKindHashMap);//打印參數配置的信息
			riskConfigAllDateList.add(prpDcoeffList);
		}
		//組織產品復制的真個頁面的信息
		return riskConfigAllDateList;
	}
	
/*******************************************************************************************************************************************/
/*****************************************************產品配置組織頁面全部的對象，初始化的時候數據全部加載********************************************/
/*******************************************************************************************************************************************/
	/**
	 * 產品配置全部頁面數據的組織
	 * @param riskCodeConfigure
	 * @param riskCodeCopy
	 * @param referenceRiskFlag
	 * @return List 產品配置對象list
	 */
	public List<Object> findRiskConfigAllDate(String riskCodeConfigure,String riskCodeCopy,String referenceRiskFlag) throws Exception{
		String templateCode = "";
		/**產品配置服務的獲取*/
		RiskService riskService = (RiskService) ServiceFactory.getService("riskService");
		HashMap<String, Object>  clauseHashMap = new HashMap<String, Object>();
		HashMap<String, Object>  clauseKindHashMap = new HashMap<String, Object>();
		List<Object> riskConfigAllDateList = new ArrayList<Object>(0);
		/**組織配置產品和復制產品的的條款（條款責任）匹配上的條款（條款責任）*/
		//條款的匹配
		StringBuffer clauseMsgSql = new StringBuffer();
		clauseMsgSql.append("select a.id.clauseCode from PrpDriskClause a where 1 = 1  ");
		clauseMsgSql.append(" and a.id.clauseCode in (  ");
		clauseMsgSql.append(" select b.id.clauseCode  from PrpDriskClause b where 1 = 1 and b.id.riskCode =  ? ) ");
		clauseMsgSql.append(" and a.id.riskCode = ? group by a.id.clauseCode");
		List<String> clauseMsgList = super.findByHql(clauseMsgSql.toString(), riskCodeConfigure,riskCodeCopy);
		/**將配置產品和被復制產品匹配上的條款組合到hashMap中*/
		for(int i = 0;i < clauseMsgList.size();i++){
			String clauseCode = clauseMsgList.get(i).trim();
			clauseHashMap.put(clauseCode, clauseCode);
		}
		//條款責任的匹配
		StringBuffer clauseKindMsgSql = new StringBuffer();
		clauseKindMsgSql.append("select a.id.clauseCode,a.kindCode from PrpDriskClauseKind a,PrpDriskClauseKind b where 1 = 1  ");
		clauseKindMsgSql.append(" and (a.id.clauseCode = b.id.clauseCode and a.kindCode = b.kindCode ) and b.id.riskCode =  ?  ");
		clauseKindMsgSql.append(" and a.id.riskCode = ? group by a.id.clauseCode,a.kindCode ");
		List<Object[]> clauseKindMsgKindList = super.findByHql(clauseKindMsgSql.toString(), riskCodeConfigure,riskCodeCopy);
		/**將配置產品和被復制產品匹配上的條款責任組合到hashMap中*/
		for(int i = 0;i < clauseKindMsgKindList.size();i++){
			Object[] prpDriskClauseKind = clauseKindMsgKindList.get(i);
			String clauseKindCode = prpDriskClauseKind[0].toString().trim()+","+prpDriskClauseKind[1].toString().trim();
			clauseKindHashMap.put(clauseKindCode,clauseKindCode);
		}
		/**產品配置中有條款、條款責任為PUB 的數據*/
		clauseHashMap.put("PUB", "PUB");
		clauseKindHashMap.put("PUB,PUB", "PUB,PUB");
		 /**產品配置基本信息的組織，查找配置產品的產品信息*/
		PrpDrisk prpDrisk = new PrpDrisk(); 
		String prpDriskSql = "select a from PrpDrisk a where 1 = 1 and a.riskCode = ? ";
	  	List<PrpDrisk> prpDriskList = super.findByHql(prpDriskSql, riskCodeConfigure);//被配置產品的基本信息
	  	if(prpDriskList != null && !prpDriskList.isEmpty()){
	  		prpDrisk = prpDriskList.get(0);
	  	}
	  	/**流程控制信息數據的整理*/ 
	  	List<ProductProcessVO> productProcessVOlist = new ArrayList<ProductProcessVO>(0);
		String comCodeList;
	  	productProcessVOlist = riskService.showProductProcess(riskCodeCopy);//流程控制信息的獲取
		comCodeList = riskService.getComCodeFromProcessDB(riskCodeCopy);//流程配置中所有機構的獲取
		/**頁面配置信息的整理*/
		List<PrpDriskBlockVO> prpDriskBlockVOList = null;
		//1代表的是復制的是產品的信息
		if("1".equals(referenceRiskFlag)){
			//查找參考產品下引用的模板
			String findTemplateCodeSql = "select a.prpDtemplate.templateCode from PrpDriskTemplate a where a.riskCode = ? ";
			List<String> templateCodeList = super.findByHql(findTemplateCodeSql, riskCodeCopy);
			if(!templateCodeList.isEmpty()){
				templateCode = templateCodeList.get(0);
			}
			//復制的是產品的信息
			prpDriskBlockVOList = this.findRiskConfigPageMSG(riskCodeCopy, clauseHashMap, clauseKindHashMap);//頁面配置信息的獲取			
		}else{
			templateCode = riskCodeCopy;
			//參考模板的時候顯示模板的信息
			prpDriskBlockVOList = this.findRiskModelConfigPageMSG(riskCodeCopy, clauseHashMap, clauseKindHashMap);
		}
		/**計算信息的整理*/
		List<UtiFormulaInfoVO> utiFormulaInfoVO = this.findFormulaListMSG(riskCodeCopy, clauseHashMap, clauseKindHashMap);
		//////////////////////////////////////////////////////////////////
		/**業務規則信息的整理*/
		List<UtiJSFunc> utiJSFuncList = this.findRiskRuleFrontMSG(riskCodeCopy);//前臺規則的處理
		List<UtiBackRuleConfig> utiBackRuleConfigList = this.findRiskConfigBackRuleMSG(riskCodeCopy,clauseHashMap,clauseKindHashMap);//后臺規則的處理
		List<Object> list = this.findRiskConfigExtMSG(prpDrisk, riskCodeCopy, clauseHashMap, clauseKindHashMap,referenceRiskFlag);//擴展信息的獲取和條款責任的集合
		PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)list.get(0);//擴展信息
		String clauseKindSet = (String)list.get(1);//被配置產品條款責任的集合
        /**周邊系統數據的整理*/
		PeripheralService peripheralService = (PeripheralService) ServiceFactory.getService("peripheralService");
		List<PeripheralCodeVO> peripheralCodeVOList = peripheralService.copyPeripheralCode(riskCodeCopy);//數據字典的數據的獲取
		List<PrpDSimpletreaty> prpDSimpletreatyList = peripheralService.findPrpDSimpletreatyByClassCode(prpDrisk.getClassCode());//被配置產品的簡單合約分出
		List<PrpDTreatyReten> prpDTreatyRetenList = peripheralService.findPrpDTreatyRetenByClassCode(prpDrisk.getClassCode());//自留額計劃
		PeripheralAccountInfoVO peripheralAccountInfoVO = peripheralService.findPrpDaccountInfoListByRiskCode(prpDrisk.getRiskCode());//收付財務
		List<UtiPrintConfig> utiPrintConfigList = this.findPeripheralPrintMSG(riskCodeCopy);//打印參數配置的信息
		
		//組織產品復制的真個頁面的信息
		riskConfigAllDateList.add(prpDrisk);
		riskConfigAllDateList.add(productProcessVOlist);
		riskConfigAllDateList.add(comCodeList);
		riskConfigAllDateList.add(prpDriskBlockVOList);
		riskConfigAllDateList.add(utiJSFuncList);
		riskConfigAllDateList.add(utiBackRuleConfigList);
		riskConfigAllDateList.add(prpDriskRelatedExtSetVO);
		riskConfigAllDateList.add(peripheralCodeVOList);
		riskConfigAllDateList.add(prpDSimpletreatyList);
		riskConfigAllDateList.add(prpDTreatyRetenList);
		riskConfigAllDateList.add(peripheralAccountInfoVO);
		riskConfigAllDateList.add(utiPrintConfigList);
		riskConfigAllDateList.add(clauseKindSet);
		riskConfigAllDateList.add(templateCode);
		riskConfigAllDateList.add(utiFormulaInfoVO);
		return riskConfigAllDateList;
	}
	
	/**
	 * 查找產品配置被復制產品的頁面配置信息
	 * @param riskCodeCopy 險種複製
	 * @param clauseHashMap
	 * @param clauseKindHashMap
	 * @return List PrpDriskBlockVO對象list
	 */
	public List<PrpDriskBlockVO> findRiskConfigPageMSG(String riskCodeCopy,HashMap clauseHashMap,HashMap clauseKindHashMap){
		RiskService riskService = (RiskService) ServiceFactory.getService("riskService");
		StringBuffer prpDriskBlockSql = new StringBuffer();
 	    prpDriskBlockSql.append(" select a from PrpDriskBlock a where 1 = 1 and a.id.riskTemplateCode = ? ");
 	    prpDriskBlockSql.append( " or (a.id.riskTemplateCode = (");
 	    prpDriskBlockSql.append( " select b.prpDtemplate.templateCode from PrpDriskTemplate b where 1 = 1 and b.riskCode = ? ) ");
 	    prpDriskBlockSql.append( " and a.blockConfigType = ? ) and a.validStatus = '1'");
 	    List<PrpDriskBlock> prpDriskBlockList = super.findByHql(prpDriskBlockSql.toString(), riskCodeCopy,riskCodeCopy,ProductConstants.BLOCKCONFIGTYPE_SOLID);
 	    for(PrpDriskBlock prpDriskBlock : prpDriskBlockList){
 	    	String blockClassfify = prpDriskBlock.getBlockClassify().trim();
 	    	if("ItemKind".equals(blockClassfify)){
 	    		List<PrpDblockClauseKind> prpDblockClauseKindList =  prpDriskBlock.getPrpDblockClauseKinds();
 	 	    	String blockKindPageClassify = prpDriskBlock.getBlockKindPageClassify();
 	 	    	for(PrpDblockClauseKind prpDblockClauseKind:prpDblockClauseKindList){
 	 	    		if("2".equals(blockKindPageClassify)){
 	 	    			String clauseCode =  prpDblockClauseKind.getId().getClauseCode().trim();
 	 	    			if(!clauseHashMap.containsKey(clauseCode)){
 	 	    				prpDblockClauseKind.getId().setClauseCode("");
 	 	    				prpDblockClauseKind.getId().setKindCode("");
 	 	    				prpDblockClauseKind.getId().setClauseAttribute("");
 	 	    			}
 	 	    		}else if("3".equals(blockKindPageClassify)){
 	 	    			String clauseKindCode =  prpDblockClauseKind.getId().getClauseCode().trim()+","+prpDblockClauseKind.getId().getKindCode().trim();
 	 	    			if(!clauseKindHashMap.containsKey(clauseKindCode)){
 	 	    				prpDblockClauseKind.getId().setClauseCode("");
 	 	    				prpDblockClauseKind.getId().setKindCode("");
 	 	    				prpDblockClauseKind.getId().setClauseAttribute("");
 	 	    			}
 	 	    		}
 	 	    		
 	 	    	} 	    		
 	    	}
 	    }
 	    String riskCodes = "risk," + riskCodeCopy;
 	    String flag = "";
 	    //將prpDriskBlock整理成VO對象，在前臺現實
 	    List<PrpDriskBlockVO> prpDriskBlockVOList = riskService.getPrpDriskBlockVOList(riskCodes,prpDriskBlockList,flag);
 	    return prpDriskBlockVOList;
	}
	/**
	 * 查找產品配置被復制產品的頁面配置信息
	 * @param riskCodeCopy 險種複製
	 * @param clauseHashMap
	 * @param clauseKindHashMap
	 * @return List PrpDriskBlockVO對象list
	 */
	public List<PrpDriskBlockVO> findRiskModelConfigPageMSG(String riskCodeCopy,HashMap clauseHashMap,HashMap clauseKindHashMap){
		RiskService riskService = (RiskService) ServiceFactory.getService("riskService");
		StringBuffer prpDriskBlockSql = new StringBuffer();
		prpDriskBlockSql.append(" select a from PrpDriskBlock a where 1 = 1 and a.id.riskTemplateCode = ? ");
 	    prpDriskBlockSql.append( " and a.validStatus = '1'");
 	    List<PrpDriskBlock> prpDriskBlockList = super.findByHql(prpDriskBlockSql.toString(), riskCodeCopy);
 	    for(PrpDriskBlock prpDriskBlock : prpDriskBlockList){
 	    	String blockClassfify = prpDriskBlock.getBlockClassify().trim();
 	    	if("ItemKind".equals(blockClassfify)){
 	    		List<PrpDblockClauseKind> prpDblockClauseKindList =  prpDriskBlock.getPrpDblockClauseKinds();
 	 	    	String blockKindPageClassify = prpDriskBlock.getBlockKindPageClassify();
 	 	    	for(PrpDblockClauseKind prpDblockClauseKind:prpDblockClauseKindList){
 	 	    		if("2".equals(blockKindPageClassify)){
 	 	    			String clauseCode =  prpDblockClauseKind.getId().getClauseCode().trim();
 	 	    			if(!clauseHashMap.containsKey(clauseCode)){
 	 	    				prpDblockClauseKind.getId().setClauseCode("");
 	 	    				prpDblockClauseKind.getId().setKindCode("");
 	 	    				prpDblockClauseKind.getId().setClauseAttribute("");
 	 	    			}
 	 	    		}else if("3".equals(blockKindPageClassify)){
 	 	    			String clauseKindCode =  prpDblockClauseKind.getId().getClauseCode().trim()+","+prpDblockClauseKind.getId().getKindCode().trim();
 	 	    			if(!clauseKindHashMap.containsKey(clauseKindCode)){
 	 	    				prpDblockClauseKind.getId().setClauseCode("");
 	 	    				prpDblockClauseKind.getId().setKindCode("");
 	 	    				prpDblockClauseKind.getId().setClauseAttribute("");
 	 	    			}
 	 	    		}
 	 	    		
 	 	    	} 	    		
 	    	}
 	    }
 	    String riskCodes = "riskModel," + riskCodeCopy;
 	    String flag = "";
 	    //將prpDriskBlock整理成VO對象，在前臺現實
 	    List<PrpDriskBlockVO> prpDriskBlockVOList = riskService.getPrpDriskBlockVOList(riskCodes,prpDriskBlockList,flag);
 	    return prpDriskBlockVOList;
	}
	/**
	 * 后系數配置信息的整理
	 * @param riskCode 險種
	 * @param clauseHashMap
	 * @param clauseKindHashMap
	 * @return List PrpDcoeff對象list
	 */
	public List<PrpDcoeff> findProductCoeffConfigMSG(String riskCode,HashMap clauseHashMap,HashMap clauseKindHashMap){
		List<PrpDcoeff> prpDcoeffReturnList = new ArrayList<PrpDcoeff>(0);
		//獲取產品配置的服務
		StringBuffer prpDcoeffSql = new StringBuffer();
		prpDcoeffSql.append(" select a from PrpDcoeff a where 1 = 1 and a.id.riskCode = ? ");
		prpDcoeffSql.append( " and a.validStatus = '1' ");
 	    List<PrpDcoeff> prpDcoeffList = super.findByHql(prpDcoeffSql.toString(), riskCode);
 	    for(PrpDcoeff prpDcoeff : prpDcoeffList){
 	    	String clauseCode = prpDcoeff.getId().getClauseCode().trim();
 	    	String kindCode = prpDcoeff.getKindCode().trim();
 	    	String clauseKindCode = clauseCode +"," + kindCode;
 	    	if((clauseHashMap.containsKey(clauseCode) && "PUB".equals(kindCode))
			|| clauseKindHashMap.containsKey(clauseKindCode)){
 	    		prpDcoeffReturnList.add(prpDcoeff);
 	    	}
 	    }
  	    return prpDcoeffReturnList;
	}
	/**
	 * 后臺業務規則數據的整理
	 * @param riskCode 險種
	 * @param clauseHashMap
	 * @param clauseKindHashMap
	 * @return List UtiBackRuleConfig后臺業務規則對象list
	 */
	public List<UtiBackRuleConfig> findRiskConfigBackRuleMSG(String riskCode,HashMap clauseHashMap,HashMap clauseKindHashMap){
		//獲取產品配置的服務
		StringBuffer utiJSFuncSql = new StringBuffer();
		utiJSFuncSql.append(" select a from UtiBackRuleConfig a where 1 = 1 and a.id.riskCode = ? ");
		utiJSFuncSql.append( " and a.validStatus = '1' ");
 	    List<UtiBackRuleConfig> utiBackRuleConfigList = super.findByHql(utiJSFuncSql.toString(), riskCode);
 	    for(UtiBackRuleConfig utiBackRuleConfig : utiBackRuleConfigList){
 	    	String clauseCode = utiBackRuleConfig.getId().getClauseCode().trim();
 	    	String kindCode = utiBackRuleConfig.getId().getKindCode().trim();
 	    	String clauseKindCode = clauseCode +"," + kindCode;
 	    	if((clauseHashMap.containsKey(clauseCode) && "PUB".equals(kindCode))
			|| clauseKindHashMap.containsKey(clauseKindCode)){
 	    		continue;
 	    	}
 	    	utiBackRuleConfig.getId().setClauseCode("");
	    	utiBackRuleConfig.getId().setKindCode("");
 	    }
  	    return utiBackRuleConfigList;
	}
	/**
	 * 產品擴展信息數據的整理
	 * @param prpDriskConfig
	 * @param riskCodeCopy 險種複製
	 * @param clauseHashMap
	 * @param clauseKindHashMap
	 * @return List 產品擴展信息對象list
	 */
	public List<Object> findRiskConfigExtMSG(PrpDrisk prpDriskConfig,String riskCodeCopy,HashMap clauseHashMap,HashMap clauseKindHashMap,String referenceRiskFlag){
		List<Object> list = new ArrayList<Object>(0);
		PrpDriskExtVO prpDriskExtVO = new PrpDriskExtVO();
	    List<PrpDriskClauseExtVO> prpDriskClauseExtVOList = new ArrayList<PrpDriskClauseExtVO>();
	    List<PrpDriskClauseKindExtVO> prpDriskClauseKindExtVOList = new ArrayList<PrpDriskClauseKindExtVO>();
	    List<PrpDrationExtVO> prpDrationExtVOList = new ArrayList<PrpDrationExtVO>();
	    List<PrpDriskLimitVO> prpDriskLimitVOList = new ArrayList<PrpDriskLimitVO>();
	    //被配置產品的條款責任的集合
	    String clauseKindSet = "";
	    /**組織擴展信息的集合的對象*/
	    PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = new PrpDriskRelatedExtSetVO();
	    /**查找被配置產品的條款責任*/
	    StringBuffer prpDriskClauseKindConfigSql = new StringBuffer();
	    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       begin
	    prpDriskClauseKindConfigSql.append("select a.clauseCName,b.id.clauseCode,b.kindCode,b.kindName,b.claimType,b.calculateFlag,b.id.riskKCSerialNo from PrpDriskClause a,PrpDriskClauseKind b where 1 = 1 ");
	    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       end
	    prpDriskClauseKindConfigSql.append("  and (a.id.riskCode = b.id.riskCode and a.id.clauseCode = b.id.clauseCode) and b.id.riskCode = ?");
	    List<Object[]> prpDriskClauseKindConfigList = super.findByHql(prpDriskClauseKindConfigSql.toString(),prpDriskConfig.getRiskCode());
	    /**查詢被配置產品的限額免賠信息*/
	    StringBuffer prpDriskLimitConfigSql = new StringBuffer();
	    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       begin
	    prpDriskLimitConfigSql.append("select a.id.clauseCode,a.kindCode,a.id.limitCode,a.limitCName,a.limitGroupNo,a.id.serialNo from PrpDriskLimit a where a.id.riskCode = ? ");
	    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       end
	    List<Object[]> prpDriskLimitConfigList = super.findByHql(prpDriskLimitConfigSql.toString(), prpDriskConfig.getRiskCode());
	    /**根據產品代碼查找方案的信息*/
	    //modify by liuxiaofie 20110530 del 產品引擎不同步方案信息，無需查找方案信息    begin
// 	    String prpDrationSql = "select a from PrpDration a where 1 = 1 and a.prpDrisk.riskCode = ?";
// 	    List<PrpDration> prpDrationList = super.findByHql(prpDrationSql, prpDriskConfig.getRiskCode());
 	    //modify by liuxiaofie 20110530 del 產品引擎不同步方案信息，無需查找方案信息    end
		/**查找參考產品或者模板的產品，產品條款責任，方案的信息   start*/
	    if("1".equals(referenceRiskFlag)){
			/**根據產品代碼查找產品的信息*/
			String prpDriskSql = "select a from PrpDrisk a where 1 = 1 and a.riskCode = ?";
	 	    List<PrpDrisk> prpDriskList = super.findByHql(prpDriskSql, riskCodeCopy);
			/**根據產品代碼查找產品的信息*/
	 	    StringBuffer prpDriskClauseKindCopySql = new StringBuffer();
	 	    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       begin
	 	    prpDriskClauseKindCopySql.append("select a.clauseCName,b.id.clauseCode,b.kindCode,b.kindName,b.claimType,b.calculateFlag,b.id.riskKCSerialNo from PrpDriskClause a,PrpDriskClauseKind b where 1 = 1 ");
	 	    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       end
	 	    prpDriskClauseKindCopySql.append("  and (a.id.riskCode = b.id.riskCode and a.id.clauseCode = b.id.clauseCode) and b.id.riskCode = ?");

	 	    List<Object[]> prpDriskClauseKindCopyList = super.findByHql(prpDriskClauseKindCopySql.toString(), riskCodeCopy);
	 	   /**查詢被配置產品的限額免賠信息*/
		    StringBuffer prpDriskLimitCopySql = new StringBuffer();
		    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       begin
		    prpDriskLimitCopySql.append("select a.id.clauseCode,a.kindCode,a.id.limitCode,a.limitCName,a.limitGroupNo,a.id.serialNo from PrpDriskLimit a where a.id.riskCode = ? ");
		    //modify by liuxiaofei 20110821 reason:參考產品或模板配置時，配置產品輔助標志配置無法正常保存       end
		    List<Object[]> prpDriskLimitCopyList = super.findByHql(prpDriskLimitCopySql.toString(), riskCodeCopy);
	 	    /**根據產品代碼查找方案的信息*/
		    //modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    begin
//	 	    String prpDrationCopySql = "select a from PrpDration a where 1 = 1 and a.prpDrisk.riskCode = ?";
//	 	    List<PrpDration> prpDrationCopyList = super.findByHql(prpDrationCopySql, riskCodeCopy); 	
		    //modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    end
	 	   /**查找參考產品或者模板的產品，產品條款責任，方案的信息   end*/
	 	    /**組織產品的擴展信息*/
	 	    PrpDrisk prpDrisk = prpDriskList.get(0);
			prpDriskExtVO.setRiskCode(prpDriskConfig.getRiskCode());
			prpDriskExtVO.setRiskCName(prpDriskConfig.getRiskCName());
			prpDriskExtVO.setShortRateFlag(prpDrisk.getShortRateFlag());
			prpDriskExtVO.setClassFlag(prpDrisk.getClassFlag());
			prpDriskExtVO.setRateUnit(prpDrisk.getRateUnit());
			prpDriskExtVO.setRiskFlag(prpDrisk.getRiskFlag());
			/**組織數據的規則：產品參考產品，組織的規則是找出復制產品中條款責任匹配的數據和配置產品中條款責任不匹配的數據組合在一起就是完整的數據
//			 *  產品引用模板：直接組織配置產品的信息*/
	 	    /**組織產品條款的擴展信息---被配置產品*/
	 	    List<PrpDriskClause> prpDriskClauseCopyList = prpDriskList.get(0).getPrpDriskClauses();
	 	    for(PrpDriskClause prpDriskClause : prpDriskClauseCopyList) {
	 	    	String clauseCode = prpDriskClause.getId().getClauseCode().trim();
	 	    	if(clauseHashMap.containsKey(clauseCode)){
	 	    		PrpDriskClauseExtVO prpDriskClauseExtVO = new PrpDriskClauseExtVO();
	 				prpDriskClauseExtVO.setRiskCode(prpDriskConfig.getRiskCode());
	 				prpDriskClauseExtVO.setClauseCode(prpDriskClause.getId().getClauseCode());
	 				prpDriskClauseExtVO.setClauseCName(prpDriskClause.getClauseCName());
	 				prpDriskClauseExtVO.setClauseVersion(prpDriskClause.getClauseVersion());
	 				prpDriskClauseExtVO.setSpecialType(prpDriskClause.getSpecialType());
	 				prpDriskClauseExtVO.setHealthType1(prpDriskClause.getHealthType1());
	 				prpDriskClauseExtVO.setHealthType2(prpDriskClause.getHealthType2());
	 				prpDriskClauseExtVO.setHealthType3(prpDriskClause.getHealthType3());
	 				prpDriskClauseExtVO.setClauseDescFlag(prpDriskClause.getClauseDescFlag());
	 				prpDriskClauseExtVOList.add(prpDriskClauseExtVO);	
	 	    	}
	 		    
			}
	 	   /**組織產品條款的擴展信息---被peizhi產品*/
	 	    List<PrpDriskClause> prpDriskClauseConfigList = prpDriskConfig.getPrpDriskClauses();
	 	    for(PrpDriskClause prpDriskClause : prpDriskClauseConfigList) {
	 	    	String clauseCode = prpDriskClause.getId().getClauseCode().trim();
	 	    	if(!clauseHashMap.containsKey(clauseCode)){
	 	    		PrpDriskClauseExtVO prpDriskClauseExtVO = new PrpDriskClauseExtVO();
	 				prpDriskClauseExtVO.setRiskCode(prpDriskConfig.getRiskCode());
	 				prpDriskClauseExtVO.setClauseCode(prpDriskClause.getId().getClauseCode());
	 				prpDriskClauseExtVO.setClauseCName(prpDriskClause.getClauseCName());
	 				prpDriskClauseExtVO.setClauseVersion(prpDriskClause.getClauseVersion());
	 				prpDriskClauseExtVO.setSpecialType(prpDriskClause.getSpecialType());
	 				prpDriskClauseExtVO.setHealthType1(prpDriskClause.getHealthType1());
	 				prpDriskClauseExtVO.setHealthType2(prpDriskClause.getHealthType2());
	 				prpDriskClauseExtVO.setHealthType3(prpDriskClause.getHealthType3());
	 				prpDriskClauseExtVO.setClauseDescFlag(prpDriskClause.getClauseDescFlag());
	 				prpDriskClauseExtVOList.add(prpDriskClauseExtVO);	
	 	    	}
	 		    
			}
	 	   /**產品條款責任的擴展信息-----被復制信息*/
			for(Object[] prpDriskClauseKind : prpDriskClauseKindCopyList) {
				String clauseKindCode = prpDriskClauseKind[1].toString().trim()+","+prpDriskClauseKind[2].toString().trim();
				if(clauseKindHashMap.containsKey(clauseKindCode)){
					PrpDriskClauseKindExtVO prpDriskClauseKindExtVO = new PrpDriskClauseKindExtVO();
					prpDriskClauseKindExtVO.setRiskCode(prpDriskConfig.getRiskCode());
					prpDriskClauseKindExtVO.setClauseCode(prpDriskClauseKind[1].toString());
					prpDriskClauseKindExtVO.setKindCode(prpDriskClauseKind[2].toString());
					if(prpDriskClauseKind[0] != null){
					    prpDriskClauseKindExtVO.setClauseCName(prpDriskClauseKind[0].toString());
					}
					if(prpDriskClauseKind[3] != null){
					    prpDriskClauseKindExtVO.setKindCName(prpDriskClauseKind[3].toString());
					}    
		            if(prpDriskClauseKind[4] != null){
		            	prpDriskClauseKindExtVO.setClaimType(prpDriskClauseKind[4].toString());				
					}
		            if(prpDriskClauseKind[5] != null){
		            	prpDriskClauseKindExtVO.setCalculateFlag(prpDriskClauseKind[5].toString());            	
		            }
		            //add by liuxiaofei 20110821 reason:根據產品代碼、條款代碼、責任代碼，不能唯一確定一條記錄    begin
		            if(prpDriskClauseKind[6] != null){
		            	prpDriskClauseKindExtVO.setRiskKCSerialNo(Integer.parseInt(prpDriskClauseKind[6].toString()));
		            }
		            //add by liuxiaofei 20110821 reason:根據產品代碼、條款代碼、責任代碼，不能唯一確定一條記錄    end
		            prpDriskClauseKindExtVOList.add(prpDriskClauseKindExtVO);
				}
			}
		 	   /**產品條款責任的擴展信息-----被配置信息*/
			for(Object[] prpDriskClauseKind : prpDriskClauseKindConfigList) {
				String clauseKindCode = prpDriskClauseKind[1].toString().trim()+","+prpDriskClauseKind[2].toString().trim();
				if(!clauseKindHashMap.containsKey(clauseKindCode)){
					PrpDriskClauseKindExtVO prpDriskClauseKindExtVO = new PrpDriskClauseKindExtVO();
					prpDriskClauseKindExtVO.setRiskCode(prpDriskConfig.getRiskCode());
					prpDriskClauseKindExtVO.setClauseCode(prpDriskClauseKind[1].toString());
					prpDriskClauseKindExtVO.setKindCode(prpDriskClauseKind[2].toString());
					if(prpDriskClauseKind[0] != null){
					    prpDriskClauseKindExtVO.setClauseCName(prpDriskClauseKind[0].toString());
					}
					if(prpDriskClauseKind[3] != null){
					    prpDriskClauseKindExtVO.setKindCName(prpDriskClauseKind[3].toString());
					}    
		            if(prpDriskClauseKind[4] != null){
		            	prpDriskClauseKindExtVO.setClaimType(prpDriskClauseKind[4].toString());				
					}
		            if(prpDriskClauseKind[5] != null){
		            	prpDriskClauseKindExtVO.setCalculateFlag(prpDriskClauseKind[5].toString());            	
		            }
		            //add by liuxiaofei 20110821 reason:根據產品代碼、條款代碼、責任代碼，不能唯一確定一條記錄    begin
		            if(prpDriskClauseKind[6] != null){
		            	prpDriskClauseKindExtVO.setRiskKCSerialNo(Integer.parseInt(prpDriskClauseKind[6].toString()));
		            }
		            //add by liuxiaofei 20110821 reason:根據產品代碼、條款代碼、責任代碼，不能唯一確定一條記錄    end
		            prpDriskClauseKindExtVOList.add(prpDriskClauseKindExtVO);
		            if("".equals(clauseKindSet)){
						clauseKindSet = clauseKindCode.replace(",", "~");
					}else{
						clauseKindSet = clauseKindSet +","+clauseKindCode.replace(",", "~");
					}
				}else{
					if("".equals(clauseKindSet)){
						clauseKindSet = clauseKindCode.replace(",", "~");
					}else{
						clauseKindSet = clauseKindSet +","+clauseKindCode.replace(",", "~");
					}
				}
			}
		
			  /**產品限額免賠的擴展信息-----被復制信息*/
			for(Object[] prpDriskLimitConfig : prpDriskLimitConfigList) {
				//獲取配置產品的條款和限額免賠的代碼
				String clauseCodeConfig = prpDriskLimitConfig[0].toString().trim();
				String limitCodeConfig = prpDriskLimitConfig[2].toString().trim();
				for(Object[] prpDriskLimitCopy : prpDriskLimitCopyList){
					//獲取被復制產品的條款和限額免賠的代碼
					String clauseCodeCopy = prpDriskLimitCopy[0].toString().trim();
					String limitCodeCopy = prpDriskLimitCopy[2].toString().trim();	
					//如果配置產品和復制產品的條款和限額免賠的代碼一樣，在進行下面代碼的匹配
					if(clauseCodeConfig.equals(clauseCodeCopy) && limitCodeConfig.equals(limitCodeCopy)){
					    //如果配置產品和被復制產品的限額免賠是空的,則分組號賦值給被配置的產品
						if(prpDriskLimitConfig[1]==null||StringUtils.isBlank(prpDriskLimitConfig[1].toString())){
							if(prpDriskLimitCopy[1]==null||StringUtils.isBlank(prpDriskLimitCopy[1].toString())){
								prpDriskLimitConfig[4] = prpDriskLimitCopy[4];
							}
						}else{
							////如果配置產品和復制產品的條款和限額免賠的代碼一樣,則分組號賦值給被配置的產品
							if(prpDriskLimitCopy[1] != null && (prpDriskLimitConfig[1].toString()).equals(prpDriskLimitCopy[1].toString())){
								prpDriskLimitConfig[4] = prpDriskLimitCopy[4];
							}
						}
					}
				}
			}
		 	   /**產品限額免賠的擴展信息為責任賦值責任名稱*/
			for(Object[] prpDriskLimit : prpDriskLimitConfigList) {
				PrpDriskLimitVO prpDriskLimitVO = new PrpDriskLimitVO();
				//責任代碼不為空
				if(prpDriskLimit[1] != null && StringUtils.isNotBlank(prpDriskLimit[1].toString().trim())){
					prpDriskLimitVO.setKindCode(prpDriskLimit[1].toString().trim());
					for(int i = 0;i < prpDriskClauseKindConfigList.size();i++){
						if((prpDriskLimit[1].toString().trim()).equals(prpDriskClauseKindConfigList.get(i)[2].toString().trim())){
							if(prpDriskClauseKindConfigList.get(i)[3] != null){
								prpDriskLimitVO.setKindCName(prpDriskClauseKindConfigList.get(i)[3].toString().trim());
							}
						}
					}
				}
				
				prpDriskLimitVO.setClauseCode(prpDriskLimit[0].toString().trim());
				for(int j = 0;j < prpDriskClauseConfigList.size();j++){
					String clauseCode = prpDriskClauseConfigList.get(j).getId().getClauseCode();
					if(prpDriskLimit[0].toString().trim().equals(clauseCode)){
						prpDriskLimitVO.setClauseCName(prpDriskClauseConfigList.get(j).getClauseCName());
					}
				}
				if(prpDriskLimit[2] != null){
	            	prpDriskLimitVO.setLimitCode(prpDriskLimit[2].toString().trim());				
				}
	            if(prpDriskLimit[3] != null){
	            	prpDriskLimitVO.setLimitCName(prpDriskLimit[3].toString().trim());            	
	            }
	            if(prpDriskLimit[4] != null){
	            	prpDriskLimitVO.setLimitGroupNo(prpDriskLimit[4].toString().trim());            	
	            }
	            //add by liuxiaofei 20110821 reason:根據條款代碼、責任代碼、限額代碼，不能唯一確定一條記錄    begin
	            if(prpDriskLimit[5] != null){
	            	prpDriskLimitVO.setSerialNo(Integer.parseInt(prpDriskLimit[5].toString()));            	
	            }
	            //add by liuxiaofei 20110821 reason:根據條款代碼、責任代碼、限額代碼，不能唯一確定一條記錄    end
		            prpDriskLimitVOList.add(prpDriskLimitVO);
			}
			
			//modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    begin
//			Map<String, String> prpDrationMap= new HashMap<String, String>();
//			for(int i = 0;i < prpDrationCopyList.size() ;i++){
//				if(prpDrationCopyList.get(i) == null){
//					continue;
//				}
//				String planCode = prpDrationCopyList.get(i).getPlanCode().trim();
//				if(!prpDrationMap.containsKey(planCode)){
//					prpDrationMap.put(planCode, planCode);
//				}
//			}
//	 	    /**組織產品方案的擴展信息*/
//	 	   for(PrpDration prpDration : prpDrationList) {
//	 		    PrpDrationExtVO prpDrationExtVO = new PrpDrationExtVO();
//				prpDrationExtVO.setRiskCode(prpDration.getPrpDrisk().getRiskCode());
//				prpDrationExtVO.setPlanCode(prpDration.getPlanCode().trim());
//				prpDrationExtVO.setPlanCname(prpDration.getPlanCName());
//				if(prpDrationMap.containsKey(prpDration.getPlanCode().trim())){
//					prpDrationExtVO.setUpdateFlag(prpDration.getUpdateFlag());
//					prpDrationExtVO.setUniqueFlag(prpDration.getUniqueFlag());
//					prpDrationExtVO.setMaxCount(prpDration.getMaxCount());					
//				}
//				
//				prpDrationExtVOList.add(prpDrationExtVO);
//			}
			//modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    end
	    }else{
	    	prpDriskExtVO.setRiskCode(prpDriskConfig.getRiskCode());
			prpDriskExtVO.setRiskCName(prpDriskConfig.getRiskCName());
			prpDriskExtVO.setShortRateFlag(prpDriskConfig.getShortRateFlag());
			prpDriskExtVO.setClassFlag(prpDriskConfig.getClassFlag());
			prpDriskExtVO.setRateUnit(prpDriskConfig.getRateUnit());
			prpDriskExtVO.setRiskFlag(prpDriskConfig.getRiskFlag());
			/**組織產品條款的擴展信息---被peizhi產品*/
	 	    List<PrpDriskClause> prpDriskClauseConfigList = prpDriskConfig.getPrpDriskClauses();
	 	    for(PrpDriskClause prpDriskClause : prpDriskClauseConfigList) {
	 	    	PrpDriskClauseExtVO prpDriskClauseExtVO = new PrpDriskClauseExtVO();
 				prpDriskClauseExtVO.setRiskCode(prpDriskConfig.getRiskCode());
 				prpDriskClauseExtVO.setClauseCode(prpDriskClause.getId().getClauseCode());
 				prpDriskClauseExtVO.setClauseCName(prpDriskClause.getClauseCName());
 				prpDriskClauseExtVO.setClauseVersion(prpDriskClause.getClauseVersion());
 				prpDriskClauseExtVO.setSpecialType(prpDriskClause.getSpecialType());
 				prpDriskClauseExtVO.setHealthType1(prpDriskClause.getHealthType1());
 				prpDriskClauseExtVO.setHealthType2(prpDriskClause.getHealthType2());
 				prpDriskClauseExtVO.setHealthType3(prpDriskClause.getHealthType3());
 				prpDriskClauseExtVO.setClauseDescFlag(prpDriskClause.getClauseDescFlag());
 				prpDriskClauseExtVOList.add(prpDriskClauseExtVO);
			}
	 	   /**產品條款責任的擴展信息-----被配置信息*/
			for(Object[] prpDriskClauseKind : prpDriskClauseKindConfigList) {
				String clauseKindCode = prpDriskClauseKind[1].toString().trim()+","+prpDriskClauseKind[2].toString().trim();
				PrpDriskClauseKindExtVO prpDriskClauseKindExtVO = new PrpDriskClauseKindExtVO();
				prpDriskClauseKindExtVO.setRiskCode(prpDriskConfig.getRiskCode());
				prpDriskClauseKindExtVO.setClauseCode(prpDriskClauseKind[1].toString());
				prpDriskClauseKindExtVO.setKindCode(prpDriskClauseKind[2].toString());
				if(prpDriskClauseKind[0] != null){
				    prpDriskClauseKindExtVO.setClauseCName(prpDriskClauseKind[0].toString());
				}
				if(prpDriskClauseKind[3] != null){
				    prpDriskClauseKindExtVO.setKindCName(prpDriskClauseKind[3].toString());
				}    
	            if(prpDriskClauseKind[4] != null){
	            	prpDriskClauseKindExtVO.setClaimType(prpDriskClauseKind[4].toString());				
				}
	            if(prpDriskClauseKind[5] != null){
	            	prpDriskClauseKindExtVO.setCalculateFlag(prpDriskClauseKind[5].toString());            	
	            }
	            //add by liuxiaofei 20110821 reason:根據產品代碼、條款代碼、責任代碼，不能唯一確定一條記錄    begin
	            if(prpDriskClauseKind[6] != null){
	            	prpDriskClauseKindExtVO.setRiskKCSerialNo(Integer.parseInt(prpDriskClauseKind[6].toString()));          	
	            }
	            //add by liuxiaofei 20110821 reason:根據產品代碼、條款代碼、責任代碼，不能唯一確定一條記錄    end
	            prpDriskClauseKindExtVOList.add(prpDriskClauseKindExtVO);
	            if("".equals(clauseKindSet)){
					clauseKindSet = clauseKindCode.replace(",", "~");
				}else{
					clauseKindSet = clauseKindSet +","+clauseKindCode.replace(",", "~");
				}
			}
		 	   /**產品限額免賠的擴展信息為責任賦值責任名稱*/
			for(Object[] prpDriskLimit : prpDriskLimitConfigList) {
				PrpDriskLimitVO prpDriskLimitVO = new PrpDriskLimitVO();
				//責任代碼不為空
				if(prpDriskLimit[1] != null && StringUtils.isNotBlank(prpDriskLimit[1].toString().trim())){
					for(int i = 0;i < prpDriskClauseKindConfigList.size();i++){
						if((prpDriskLimit[1].toString().trim()).equals(prpDriskClauseKindConfigList.get(i)[2].toString().trim())){
							prpDriskLimitVO.setKindCode(prpDriskLimit[1].toString().trim());
							if(prpDriskClauseKindConfigList.get(i)[3] != null){
								prpDriskLimitVO.setKindCName(prpDriskClauseKindConfigList.get(i)[3].toString().trim());
							}
						}
					}
				}
				
				prpDriskLimitVO.setClauseCode(prpDriskLimit[0].toString().trim());
				for(int j = 0;j < prpDriskClauseConfigList.size();j++){
					String clauseCode = prpDriskClauseConfigList.get(j).getId().getClauseCode();
					if(prpDriskLimit[0].toString().trim().equals(clauseCode)){
						prpDriskLimitVO.setClauseCName(prpDriskClauseConfigList.get(j).getClauseCName());
					}
				}
				if(prpDriskLimit[2] != null){
	            	prpDriskLimitVO.setLimitCode(prpDriskLimit[2].toString().trim());				
				}
	            if(prpDriskLimit[3] != null){
	            	prpDriskLimitVO.setLimitCName(prpDriskLimit[3].toString().trim());            	
	            }
	            if(prpDriskLimit[4] != null){
	            	prpDriskLimitVO.setLimitGroupNo(prpDriskLimit[4].toString().trim());            	
	            }
	            //add by liuxiaofei 20110821 reason:根據條款代碼、責任代碼、限額代碼，不能唯一確定一條記錄    begin
	            if(prpDriskLimit[5] != null){
	            	prpDriskLimitVO.setSerialNo(Integer.parseInt(prpDriskLimit[5].toString().trim()));            	
	            }
	            //add by liuxiaofei 20110821 reason:根據條款代碼、責任代碼、限額代碼，不能唯一確定一條記錄    end
		            prpDriskLimitVOList.add(prpDriskLimitVO);
			}
			/**組織產品方案的擴展信息*/
			//modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    begin
//		 	for(PrpDration prpDration : prpDrationList) {
//		 		PrpDrationExtVO prpDrationExtVO = new PrpDrationExtVO();
//				prpDrationExtVO.setRiskCode(prpDriskConfig.getRiskCode());
//				prpDrationExtVO.setPlanCode(prpDration.getPlanCode().trim());
//				prpDrationExtVO.setPlanCname(prpDration.getPlanCName());
//				prpDrationExtVO.setUpdateFlag(prpDration.getUpdateFlag());
//				prpDrationExtVO.setUniqueFlag(prpDration.getUniqueFlag());
//				prpDrationExtVO.setMaxCount(prpDration.getMaxCount());
//				
//				prpDrationExtVOList.add(prpDrationExtVO);    
//			}
			//modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    end
	    }
	    
 
 	    /**將擴展信息的集合組織到一個大對象中*/
 	  prpDriskRelatedExtSetVO.setPrpDriskExtVO(prpDriskExtVO);
 	  prpDriskRelatedExtSetVO.setPrpDriskClauseExtVOs(prpDriskClauseExtVOList);
 	  prpDriskRelatedExtSetVO.setPrpDriskClauseKindExtVOs(prpDriskClauseKindExtVOList);
 	  prpDriskRelatedExtSetVO.setPrpDrationExtVOs(prpDrationExtVOList);
 	  prpDriskRelatedExtSetVO.setPrpDriskLimitVOs(prpDriskLimitVOList);
 	  list.add(prpDriskRelatedExtSetVO);
 	  list.add(clauseKindSet);
  	  return list;
	}
	
	/********************產品前臺業務規則在復制的時候的保存的方法*****************/
	/**
	 * 前臺業務規則在復制的時候保存的方法
	 * @param utiJSFuncList 產品前臺業務規則UtiJSFunc對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void addProductFrontRuleConfig(List<UtiJSFunc> utiJSFuncList,String riskCode,String userCode){
		List<UtiJSFunc> utiJSFuncSaveList = new ArrayList<UtiJSFunc>(0);
		int serialNo = 0;
		//循環遍歷每一個前臺業務規則，去重新組織每一個對象，形成一個新的對象
        for(int i = 0;i < utiJSFuncList.size();i++){
        	if(utiJSFuncList.get(i) != null){
        		//獲取對象的事件類型，因為可能是多個時間的集合
        		String eventGather = utiJSFuncList.get(i).getEvent();
        		String[] eventList = eventGather.split(",");
        		//循環保存每一個時間
        		for(int j = 0;j < eventList.length;j++){
        			UtiJSFunc utiJSFunc = new UtiJSFunc();
                	try {
						BoCopyUtil.convert(utiJSFuncList.get(i), utiJSFunc, UtiJSFunc.class, null, null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			serialNo++;
        			utiJSFunc.getId().setSerialNo(serialNo);
        			utiJSFunc.getId().setRiskCode(riskCode);
        			utiJSFunc.setEvent(eventList[j]);
        			utiJSFunc.setCreatorCode(userCode);
        			utiJSFuncSaveList.add(utiJSFunc);
        		}
        	}
        }
        //保存前臺業務規則
	    super.saveAll(utiJSFuncSaveList);
	}
	/**
	 * 前臺業務規則在復制的時候保存的方法
	 * @param utiJSFuncList 產品前臺業務規則UtiJSFunc對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void updateProductFrontRuleConfig(List<UtiJSFunc> utiJSFuncList,String riskCode,String userCode){
		//用于更新的前臺業務規則的List
		List<UtiJSFunc> utiJSFuncUpdateList = new ArrayList<UtiJSFunc>(0);
		//用于保存的前臺的
		List<UtiJSFunc> utiJSFuncSaveList = new ArrayList<UtiJSFunc>(0);
		
		//獲取前臺拆分的的前臺業務規則的list
		List<UtiJSFunc> utiJSFuncSplitList = this.frontRuleSplitUtiJSFuncList(utiJSFuncList);
		//獲取數據庫中產品代碼下的前臺業務規則
		String utiJSFuncSql = "select a from UtiJSFunc a where 1 = 1 and a.id.riskCode = ? and a.validStatus = '1'";
		List<UtiJSFunc> utiJSFuncDateList = super.findByHql(utiJSFuncSql, riskCode);
		//手機數據中有多少數據
		int number = utiJSFuncDateList.size();
		//用于判斷是否相等的標志位
		int flag = 0;
		//查找前臺業務規則是增加還是修改操作，循環數據庫中查詢的前臺業務規則
		for(int i = 0;i < utiJSFuncDateList.size();i++){
			flag = 0;
			//int serialNo = utiJSFuncDateList.get(i).getId().getSerialNo();
			//循環頁面獲取的前臺業務規則
			for(int j = 0;j < utiJSFuncSplitList.size();j++){
				if(utiJSFuncSplitList.get(j) != null){
					//判斷頁面和數據庫中前臺業務規則是否相等（根據維度去判斷，不是主鍵）
					if(comParefrontRuleEqual(utiJSFuncDateList.get(i),utiJSFuncSplitList.get(j))){
						flag = 1;
						//如果相等房到更新的列表中
						utiJSFuncUpdateList.add(utiJSFuncSplitList.get(j));
						//將相應的頁面數據置為空，以便確認的是新增的
						utiJSFuncSplitList.set(j, null);
					}
				}
				
			}
			if(flag == 0){
				    //如果不再數據庫中說明在頁面中已刪除應置為無效，放到修改中
				    utiJSFuncDateList.get(i).setValidStatus("0");
				    utiJSFuncUpdateList.add(utiJSFuncDateList.get(i));
			}
		}
		//更新數據庫中的各個域
		for(int i = 0;i < utiJSFuncUpdateList.size();i++){
			UtiJSFunc utiJSFunc = new UtiJSFunc();
			utiJSFunc = utiJSFuncDateList.get(i);
			utiJSFunc.setFuncName(utiJSFuncUpdateList.get(i).getFuncName());
			utiJSFunc.setParameters(utiJSFuncUpdateList.get(i).getParameters());
			utiJSFunc.setFuncDesc(utiJSFuncUpdateList.get(i).getFuncDesc());
			utiJSFunc.setContent(utiJSFuncUpdateList.get(i).getContent());
			utiJSFunc.setGetElementMode(utiJSFuncUpdateList.get(i).getGetElementMode());
			utiJSFunc.setValidStatus(utiJSFuncUpdateList.get(i).getValidStatus());
			utiJSFunc.setValidDate(utiJSFuncUpdateList.get(i).getValidDate());
			utiJSFunc.setInvalidDate(utiJSFuncUpdateList.get(i).getInvalidDate());
			utiJSFunc.setRemark(utiJSFuncUpdateList.get(i).getRemark());
			utiJSFunc.setUpdaterCode(userCode);
			//utiJSFuncSaveList.add(utiJSFunc);
		}
		//頁面中的數據不為空的證明是頁面中新增數據，放到新增list中
		for(int i = 0;i < utiJSFuncSplitList.size();i++){
			if(utiJSFuncSplitList.get(i) != null){
				number++;
				UtiJSFunc utiJSFunc = new UtiJSFunc();
				utiJSFunc = utiJSFuncSplitList.get(i);
				utiJSFunc.getId().setSerialNo(number);
    			utiJSFunc.getId().setRiskCode(riskCode);
    			utiJSFunc.setCreatorCode(userCode);
				utiJSFuncSaveList.add(utiJSFunc);
			}
	    }
		super.saveAll(utiJSFuncSaveList);
		super.saveAll(utiJSFuncDateList);
	}
	/**
	 * 將前臺業務規則返回的值進行拆分
	 * @param utiJSFuncList 前臺業務規則對象list
	 * @return List UtiJSFunc對象list
	 */
	public List<UtiJSFunc> frontRuleSplitUtiJSFuncList(List<UtiJSFunc> utiJSFuncList){
		List<UtiJSFunc> utiJSFuncSplitList = new ArrayList<UtiJSFunc>(0);
		//循環遍歷前臺獲取的前臺規則的List
		for(int i = 0;i < utiJSFuncList.size();i++){
			//如果取出的對象時空，調過此層循環
			if(utiJSFuncList.get(i) == null){
				continue;
			}
			//獲取對象的事件的集合
			String eventSet = utiJSFuncList.get(i).getEvent();
			//將事件的集合進行拆分
			String[] eventList = eventSet.split(",");
			//循環遍歷事件的集合，組織對象
			for(int j = 0;j < eventList.length;j++){
				if(eventList[j] != null && eventList[j] != ""){
					UtiJSFunc utiJSFunc = new UtiJSFunc();
					try {
						BoCopyUtil.convert(utiJSFuncList.get(i), utiJSFunc, UtiJSFunc.class, null, null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					utiJSFunc.setEvent(eventList[j]);
					//將組織好的對象放到list中
					utiJSFuncSplitList.add(utiJSFunc);
				}
			}
		}
		//返回拆分好的對象
		return utiJSFuncSplitList;
	}
	/**
	 * 對比頁面獲取的前臺JS和后臺JS在維度是否相等
	 * @param utiJSFuncPage 前臺JS
	 * @param utiJSFuncDate 后臺JS
	 * @return boolean 是否相等
	 */
	public Boolean comParefrontRuleEqual(UtiJSFunc utiJSFuncPage,UtiJSFunc utiJSFuncDate){
		Boolean flag = false;
        if((utiJSFuncPage.getId().getComCode().trim()).equals(utiJSFuncDate.getId().getComCode().trim())
        		&& (utiJSFuncPage.getBizType().trim()).equals(utiJSFuncDate.getBizType().trim())
        		&& (utiJSFuncPage.getModifyMode().trim()).equals(utiJSFuncDate.getModifyMode().trim())
        		&& (utiJSFuncPage.getEvent().trim()).equals(utiJSFuncDate.getEvent().trim())
        		&& (utiJSFuncPage.getFuncLever().trim()).equals(utiJSFuncDate.getFuncLever().trim())){
        	    if ("1".equals(utiJSFuncPage.getFuncLever())){
        	    	flag = true;        	    	
        	    }else{
        	    	if((utiJSFuncPage.getControlCode().trim()).equals(utiJSFuncDate.getControlCode().trim())){
        	    		flag = true;	
        	    	}
        	    }
        }
        return flag;
	}
	
	
	/********************產品后臺業務規則在復制的時候的保存的方法*****************/
	/**
	 * 后臺業務規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 產品后臺業務規則UtiBackRuleConfig對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void addProductBackRuleConfig(List<UtiBackRuleConfig> utiBackRuleConfigList,String riskCode,String userCode){
		List<UtiBackRuleConfig> utiBackRuleConfigSaveList = new ArrayList<UtiBackRuleConfig>(0);
		//循環遍歷每一個后臺業務規則，去重新組織每一個對象，形成一個新的對象
        for(int i = 0;i < utiBackRuleConfigList.size();i++){
        	UtiBackRuleConfig utiBackRuleConfig = new UtiBackRuleConfig();
        	utiBackRuleConfig = utiBackRuleConfigList.get(i);
            if(utiBackRuleConfig == null){
            	continue;
            }
            utiBackRuleConfig.getId().setRiskCode(riskCode);
            if("".equals(utiBackRuleConfig.getId().getClauseCode())){
            	utiBackRuleConfig.getId().setClauseCode("PUB");
            }
            if("".equals(utiBackRuleConfig.getId().getKindCode())){
            	utiBackRuleConfig.getId().setKindCode("PUB");
            }
            utiBackRuleConfig.setCreatorCode(userCode);
        }
        for(int i = 0;i < utiBackRuleConfigList.size();i++){
        	if(utiBackRuleConfigList.get(i) == null ){
        		continue;
        	}
        	utiBackRuleConfigSaveList.add(utiBackRuleConfigList.get(i));
        }
        //保存后臺業務規則
	    super.saveAll(utiBackRuleConfigSaveList);
	}
	/**
	 * 后臺業務規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 產品后臺業務規則UtiBackRuleConfig對象list
	 * @param riskCode 險種代碼
	 * @param userCode 用戶代碼
	 */
	public void updateProductBackRuleConfig(List<UtiBackRuleConfig> utiBackRuleConfigList,String riskCode,String userCode){
		//用于保存的后臺的
		List<UtiBackRuleConfig> utiBackRuleConfigSaveList = new ArrayList<UtiBackRuleConfig>(0);
		
		//獲取數據庫中產品代碼下的后臺業務規則
		String utiBackRuleConfigSql = "select a from UtiBackRuleConfig a where 1 = 1 and a.id.riskCode = ? and a.validStatus = '1'";
		List<UtiBackRuleConfig> utiBackRuleConfigDateList = super.findByHql(utiBackRuleConfigSql, riskCode);
		//用于判斷是否相等的標志位
		int flag = 0;
		//查找后臺業務規則是增加還是修改操作，循環數據庫中查詢的前臺業務規則
		for(int i = 0;i < utiBackRuleConfigDateList.size();i++){
			flag = 0;
			//循環頁面獲取的后臺業務規則
			for(int j = 0;j < utiBackRuleConfigList.size();j++){
				if(utiBackRuleConfigList.get(j) != null){
					//判斷頁面和數據庫中前臺業務規則是否相等（根據維度去判斷，不是主鍵）
					if(comPareBackRuleEqualByID(utiBackRuleConfigList.get(j).getId(),utiBackRuleConfigDateList.get(i).getId())){
						flag = 1;
						utiBackRuleConfigDateList.get(i).setRuleValue(utiBackRuleConfigList.get(j).getRuleValue());
						utiBackRuleConfigDateList.get(i).setValidStatus(utiBackRuleConfigList.get(j).getValidStatus());
						utiBackRuleConfigDateList.get(i).setValidDate(utiBackRuleConfigList.get(j).getValidDate());
						utiBackRuleConfigDateList.get(i).setInvalidDate(utiBackRuleConfigList.get(j).getInvalidDate());
						utiBackRuleConfigDateList.get(i).setRemark(utiBackRuleConfigList.get(j).getRemark());
						utiBackRuleConfigDateList.get(i).setUpdaterCode(userCode);
						utiBackRuleConfigList.set(j, null);
					}
				}
				
			}
			if(flag == 0){
				    //如果不再數據庫中說明在頁面中已刪除應置為無效，放到修改中
			     	utiBackRuleConfigDateList.get(i).setValidStatus("0");
			}
		}
		//頁面中的數據不為空的證明是頁面中新增數據，放到新增list中
		for(int i = 0;i < utiBackRuleConfigList.size();i++){
			if(utiBackRuleConfigList.get(i) != null){
				UtiBackRuleConfig utiBackRuleConfig = new UtiBackRuleConfig();
				utiBackRuleConfig = utiBackRuleConfigList.get(i);
				utiBackRuleConfig.getId().setRiskCode(riskCode);
	            if("".equals(utiBackRuleConfig.getId().getClauseCode())){
	            	utiBackRuleConfig.getId().setClauseCode("PUB");
	            }
	            if("".equals(utiBackRuleConfig.getId().getKindCode())){
	            	utiBackRuleConfig.getId().setKindCode("PUB");
	            }
	            utiBackRuleConfig.setCreatorCode(userCode);
	            utiBackRuleConfigSaveList.add(utiBackRuleConfig);
			}
	    }
		utiBackRuleConfigDateList.addAll(utiBackRuleConfigSaveList);
		super.saveAll(utiBackRuleConfigDateList);
	}
	/**
	 * 根據后臺業務規則的主鍵判斷后臺業務規則是否相等
	 * @param UtiBackRuleConfigIdPage 臺業務規則的主鍵
	 * @param UtiBackRuleConfigIdDate 臺業務規則的主鍵
	 * @return boolean 是否相等
	 */
	public Boolean comPareBackRuleEqualByID(UtiBackRuleConfigId UtiBackRuleConfigIdPage,UtiBackRuleConfigId UtiBackRuleConfigIdDate){
		Boolean flag = false;
		if("".equals(UtiBackRuleConfigIdPage.getClauseCode())){
			UtiBackRuleConfigIdPage.setClauseCode("PUB");
		}
		if("".equals(UtiBackRuleConfigIdPage.getKindCode())){
			UtiBackRuleConfigIdPage.setKindCode("PUB");
		}
        if((UtiBackRuleConfigIdPage.getClauseCode().trim()).equals(UtiBackRuleConfigIdDate.getClauseCode().trim()) && 
        		(UtiBackRuleConfigIdPage.getKindCode().trim()).equals(UtiBackRuleConfigIdDate.getKindCode().trim()) &&
        		(UtiBackRuleConfigIdPage.getComCode().trim()).equals(UtiBackRuleConfigIdDate.getComCode().trim()) && 
        		(UtiBackRuleConfigIdPage.getBizType().trim()).equals(UtiBackRuleConfigIdDate.getBizType().trim()) && 
        		(UtiBackRuleConfigIdPage.getRuleCode().trim()).equals(UtiBackRuleConfigIdDate.getRuleCode().trim()) ){
        	flag = true;
        }
        return flag;
	}
	
	
	/********************產品打印參數配置規則在復制的時候的保存的方法*****************/
	/**
	 * 打印參數配置規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 打印參數配置規則UtiPrintConfig對象list
	 * @param riskCode 險種代碼
	 * @param classCode 險類代碼
	 * @param userCode 用戶代碼
	 */
	public void addProductPrintConfig(List<UtiPrintConfig> utiPrintConfigList,String riskCode,String classCode,String userCode){
		List<UtiPrintConfig> utiPrintConfigSaveList = new ArrayList<UtiPrintConfig>(0);
		//循環遍歷每一個后臺業務規則，去重新組織每一個對象，形成一個新的對象
        for(int i = 0;i < utiPrintConfigList.size();i++){
        	UtiPrintConfig utiPrintConfig = new UtiPrintConfig();
        	utiPrintConfig = utiPrintConfigList.get(i);
            if(utiPrintConfig == null){
            	continue;
            }
            utiPrintConfig.getId().setRiskCode(riskCode);
            utiPrintConfig.getId().setClassCode(classCode);
            
            utiPrintConfig.setCreatorCode(userCode);
        }
        for(int i = 0;i < utiPrintConfigList.size();i++){
        	if(utiPrintConfigList.get(i) == null ){
        		continue;
        	}
        	utiPrintConfigSaveList.add(utiPrintConfigList.get(i));
        }
        //保存后臺業務規則
	    super.saveAll(utiPrintConfigSaveList);
	}
	/**
	 * 打印參數配置規則在復制的時候保存的方法
	 * @param utiBackRuleConfigList 打印參數配置規則UtiPrintConfig對象list
	 * @param riskCode 險種代碼
	 * @param classCode 險類代碼
	 * @param userCode 用戶代碼
	 */
	public void updateProductPrintConfig(List<UtiPrintConfig> utiPrintConfigList,String riskCode,String classCode,String userCode){
		//用于保存的后臺的
		List<UtiPrintConfig> utiPrintConfigSaveList = new ArrayList<UtiPrintConfig>(0);
		
		//獲取數據庫中產品代碼下的后臺業務規則
		String utiPrintConfigSql = "select a from UtiPrintConfig a where 1 = 1 and a.id.riskCode = ? and a.validStatus = '1'";
		List<UtiPrintConfig> utiPrintConfigDateList = super.findByHql(utiPrintConfigSql, riskCode);
		//用于判斷是否相等的標志位
		int flag = 0;
		//查找后臺業務規則是增加還是修改操作，循環數據庫中查詢的前臺業務規則
		for(int i = 0;i < utiPrintConfigDateList.size();i++){
			flag = 0;
			//循環頁面獲取的后臺業務規則
			for(int j = 0;j < utiPrintConfigList.size();j++){
				if(utiPrintConfigList.get(j) != null){
					//判斷頁面和數據庫中前臺業務規則是否相等（根據維度去判斷，不是主鍵）
					if((utiPrintConfigDateList.get(i).getId().getComCode().trim()).equals(utiPrintConfigList.get(j).getId().getComCode().trim()) &&
							(utiPrintConfigDateList.get(i).getId().getPrintType().trim()).equals(utiPrintConfigList.get(j).getId().getPrintType().trim())
							 && (utiPrintConfigDateList.get(i).getId().getVisaRelation().trim()).equals(utiPrintConfigList.get(j).getId().getVisaRelation().trim())){
						flag = 1;
						utiPrintConfigDateList.get(i).setBizType(utiPrintConfigList.get(j).getBizType());
						utiPrintConfigDateList.get(i).setPrintFlag(utiPrintConfigList.get(j).getPrintFlag());
						utiPrintConfigDateList.get(i).setValidStatus(utiPrintConfigList.get(j).getValidStatus());
						utiPrintConfigDateList.get(i).setUpdaterCode(userCode);
						utiPrintConfigList.set(j, null);
					}
				}
			}
			if(flag == 0){
				    //如果不再數據庫中說明在頁面中已刪除應置為無效，放到修改中
			     	utiPrintConfigDateList.get(i).setValidStatus("0");
			}
		}
		//頁面中的數據不為空的證明是頁面中新增數據，放到新增list中
		for(int i = 0;i < utiPrintConfigList.size();i++){
			if(utiPrintConfigList.get(i) != null){
				UtiPrintConfig utiPrintConfig = new UtiPrintConfig();
				utiPrintConfig = utiPrintConfigList.get(i);
				utiPrintConfig.getId().setRiskCode(riskCode);
				utiPrintConfig.getId().setClassCode(classCode);
	            utiPrintConfig.setCreatorCode(userCode);
	            utiPrintConfigSaveList.add(utiPrintConfig);
			}
	    }
		utiPrintConfigDateList.addAll(utiPrintConfigSaveList);
		super.saveAll(utiPrintConfigDateList);
	}
	
	/**
	 * 展現業務規則-前臺業務規則的基礎信息
	 * @param riskCode 險種代碼
	 * @return List 前臺業務規則對象list
	 */
	public List<UtiJSFunc> findRiskRuleFrontMSG(String riskCode){
		List<UtiJSFunc> utiJSFuncLastList = new ArrayList<UtiJSFunc>(0);
		//獲取產品配置的服務
		StringBuffer utiJSFuncSql = new StringBuffer();
		utiJSFuncSql.append(" select a from UtiJSFunc a where 1 = 1 and a.id.riskCode = ? ");
		utiJSFuncSql.append( " and a.validStatus = '1' order by a.id.serialNo ");
 	    List<UtiJSFunc> utiJSFuncList = super.findByHql(utiJSFuncSql.toString(), riskCode);
 	    //對前臺業務規則的整合的處理
 	    Map<String, Object> utiJSFuncMap= new HashMap<String, Object>();
 	    //根據前臺業務規則的決定信息整合前臺業務規則
 	    for(int i = 0;i < utiJSFuncList.size();i++){
 	    	//組織前臺業務規則的決策信息的字符串
 	    	String decisionMSG = utiJSFuncList.get(i).getId().getComCode().trim()+","+utiJSFuncList.get(i).getBizType().trim()+","+
 	    	utiJSFuncList.get(i).getFuncLever().trim()+","+utiJSFuncList.get(i).getModifyMode().trim();
 	    	if(!"1".equals(utiJSFuncList.get(i).getFuncLever())){
 	    		decisionMSG = decisionMSG +","+utiJSFuncList.get(i).getControlCode();
 	    	}
 	    	if(utiJSFuncMap.containsKey(decisionMSG)){
 	    		String event = utiJSFuncList.get(i).getEvent();
 	    		UtiJSFunc utiJSFunc = (UtiJSFunc)utiJSFuncMap.get(decisionMSG);
 	    		utiJSFunc.setEvent(utiJSFunc.getEvent()+","+event);
 	    		utiJSFuncMap.put(decisionMSG, utiJSFunc);
 	    	}else{
 	    		utiJSFuncMap.put(decisionMSG, utiJSFuncList.get(i));
 	    	}
 	    }
 	   Set<Entry<String,Object>> utiJSFuncEntrySet = utiJSFuncMap.entrySet(); 
       for (Entry<String, Object> utiJSFuncEntry : utiJSFuncEntrySet) { 
    	   UtiJSFunc utiJSFunc = (UtiJSFunc)utiJSFuncEntry.getValue(); 
    	   utiJSFuncLastList.add(utiJSFunc);
       }
  	    return utiJSFuncLastList;
	}
	/**
	 * 展現周邊系統-打印參數配置的基礎信息
	 * @param riskCode 險種代碼
	 * @return List 打印參數配置list
	 */
	public List<UtiPrintConfig> findPeripheralPrintMSG(String riskCode){
		String hql = "select a from UtiPrintConfig a where a.id.riskCode= ?";
		List<UtiPrintConfig> utiPrintConfigList = super.findByHql(hql, riskCode);
		return utiPrintConfigList;
	}
	/**
	 * 按照機構刪除流程控制信息
	 * @param riskCode 險種代碼
	 * @param comCode 機構代碼
	 */
	public void deleteProcessConfigBycomCode(String riskCode,String comCode){
		String utiRiskProcessSql = "select a from UtiRiskProcessConfig a where 1 = 1 and a.id.riskCode = ? and a.id.comCode = ? and a.validStatus = '1'";
		List<UtiRiskProcessConfig> utiRiskProcessConfiglist = super.findByHql(utiRiskProcessSql,riskCode,comCode);
		for(UtiRiskProcessConfig utiRiskProcessConfig : utiRiskProcessConfiglist){
			if(utiRiskProcessConfig != null){
				utiRiskProcessConfig.setValidStatus("0");
			}
		}
		super.saveAll(utiRiskProcessConfiglist);
		String sql = "select a from UtiBackRuleConfig a where a.id.riskCode = ? and a.id.clauseCode = ? and a.id.kindCode = ?  and a.id.comCode = ? and a.id.bizType = ?";
		//modify by duanfa 20110726 start CB,PG 改為CB
//		List<UtiRiskProcessConfig> utiBackRuleConfigListDelete = super.findByHql(sql, riskCode,"PUB","PUB",comCode,"CB,PG");
		List<UtiRiskProcessConfig> utiBackRuleConfigListDelete = super.findByHql(sql, riskCode,"PUB","PUB",comCode,"CB");
		//modify by duanfa 20110726 end 
		if(!utiBackRuleConfigListDelete.isEmpty()){
			super.deleteAll(utiBackRuleConfigListDelete);
		}
	}
	/**
	 * 展現展現計算配置的基礎信息
	 * @param riskCodeCopy 險種複製
	 * @param clauseHashMap
	 * @param clauseKindHashMap
	 * @return List UtiFormulaInfoVO對象list
	 */
	public List<UtiFormulaInfoVO> findFormulaListMSG(String riskCodeCopy,HashMap clauseHashMap,HashMap clauseKindHashMap) {
		String hql  = "select a from UtiFormula a where a.id.riskCode = ? and a.id.formulaType='1'";
		List<UtiFormula>  formulaList=super.findByHql(hql,riskCodeCopy);
		List<UtiFormulaInfoVO>	formulaInfoVOList=new ArrayList<UtiFormulaInfoVO>();
		RiskService rs = (RiskService)ServiceFactory.getService("riskService");
		for (UtiFormula utiFormula : formulaList) {
			String clauseCode = utiFormula.getId().getClauseCode().trim();
			String kindCode = utiFormula.getId().getKindCode().trim();
			String clauseKindCode =  clauseCode+","+kindCode;
			UtiFormulaInfoVO formulaInfoVO=new UtiFormulaInfoVO();
			if((clauseHashMap.containsKey(clauseCode) && "PUB".equals(kindCode))
					|| clauseKindHashMap.containsKey(clauseKindCode)){
				if(utiFormula.getId().getClauseCode().trim().equals("PUB")){
					formulaInfoVO.setClauseCName("通用條款");
				}else{
					formulaInfoVO.setClauseCName(rs.getCodeName("clause",riskCodeCopy, "", utiFormula.getId().getClauseCode()));
				}
				if(utiFormula.getId().getKindCode().trim().equals("PUB")){
					formulaInfoVO.setKindCName("通用責任");
				}else{
					formulaInfoVO.setKindCName(rs.getCodeName("kind", riskCodeCopy,utiFormula.getId().getClauseCode(), utiFormula.getId().getKindCode()));
				}
				formulaInfoVO.setUtiFormula(utiFormula);
				formulaInfoVO.setComCName(rs.getCodeName("comcode",riskCodeCopy, "", utiFormula.getId().getComCode()));
				formulaInfoVOList.add(formulaInfoVO);
			}
		  /*else{
				utiFormula.getId().setClauseCode("");
				utiFormula.getId().setKindCode("");
			}*/
		}
		return formulaInfoVOList;
	}
	/**
	 * 產品復制進入計算配置頁面將頁面顯示的公式的因子保存成配置產品的因子
	 * @param riskCodeConfig 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @throws Exception
	 */
	public void addProductCopyFormulaFactor(String riskCodeConfig,String riskCodeCopy,String referenceRiskFlag) throws Exception{
		
		List<UtiFormulaInfoVO> formulaInfoVOList = (List<UtiFormulaInfoVO>)this.findProductCopyReSetMSG(riskCodeConfig, riskCodeCopy,referenceRiskFlag,"formulaCopy").get(0);
		List<UtiFactor> utiFactorSaveList = new ArrayList<UtiFactor>(0);
		StringBuilder factorSetList = new StringBuilder();
		//計算配置返回的對象是空的就不做處理
		if(formulaInfoVOList == null || formulaInfoVOList.isEmpty()){
			return;
		}
		for(UtiFormulaInfoVO utiFormulaInfoVO : formulaInfoVOList){
			UtiFormula utiFormula = utiFormulaInfoVO.getUtiFormula();
			//獲取的計算公式的對象是空的，就跳過此次循環
			if(utiFormula == null){
				continue;
			}
			//只有source為1 的時候才進行公式的配置，否則公式下沒有因子，不需要查找公式下的因子進行處理；
			if(!"1".equals(utiFormula.getSource())){
				continue;
			}
			String formulaContent = utiFormula.getContent();
			if(StringUtils.isBlank(factorSetList.toString())){
				factorSetList.append(emulativeFormula(formulaContent));
			}else{
				factorSetList.append(",").append(emulativeFormula(formulaContent));
			}
		}
		if(StringUtils.isNotBlank(factorSetList.toString())){
			String factorSql = "select a from UtiFactor a where 1 = 1 and a.id.riskCode = ? and a.id.factorCode in (?) and a.validStatus = '1'";//modify by liubin 20110928 bangdingbianliang
		    List<UtiFactor> utiFactorList = super.findByHql(factorSql, riskCodeCopy,factorSetList.toString());//modify by liubin 20110928 bangdingbianliang 
		    /**保存因子的相關的表*/
		    if(!utiFactorList.isEmpty()){
		    	int number = 0;
		    	addProductCopyFactorRelated(utiFactorList,riskCodeConfig,riskCodeCopy,number);
		    	for(UtiFactor utiFactor : utiFactorList){
			    	UtiFactor utiFactorNew = new UtiFactor();
			    	BoCopyUtil.convert(utiFactor, utiFactorNew, UtiFactor.class, null, null);
		            BoCopyUtil.setValueforSpecificField(utiFactorNew, "RiskCode", riskCodeConfig);
			    	utiFactorSaveList.add(utiFactorNew);
			    }
			    super.saveAll(utiFactorSaveList);
		    }
		}
	}
	/**
	 * 保存計算因子的相關的信息 
	 * @param utiFactorList 計算因子list
	 * @param riskCodeConfig 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param countNumber 數量
	 * @throws Exception
	 */
	public void addProductCopyFactorRelated(List<UtiFactor> utiFactorList,String riskCodeConfig,String riskCodeCopy,int countNumber) throws Exception{
		
		List<UtiFormula> utiFormulaSaveList = new ArrayList<UtiFormula>(0);
		List<UtiDecisionTable> utiDecisionTableSaveList = new ArrayList<UtiDecisionTable>(0);
		List<UtiFactorRelaShip> utiFactorRelaShipSaveList = new ArrayList<UtiFactorRelaShip>(0);
		List<UtiFormula> utiFormulaLastSaveList = new ArrayList<UtiFormula>(0);
		List<UtiDecisionTable> utiDecisionTableLastSaveList = new ArrayList<UtiDecisionTable>(0);
		List<UtiFactorRelaShip> utiFactorRelaShipLastSaveList = new ArrayList<UtiFactorRelaShip>(0);
		List<UtiFactor> utiFactorSaveLastList = new ArrayList<UtiFactor>(0);
		List<UtiFactor> utiFactorSaveList = new ArrayList<UtiFactor>(0);
		StringBuilder factorSetList = new StringBuilder();

		for(UtiFactor utiFactor : utiFactorList){
			
			/**保費計算死循環判斷計數器*/
			if(countNumber++ > ProductConstants.PREMIUMS_COUNTER){
				throw new BusinessException("<"+utiFactor.getFactorName()+">的關聯因子不能包含自身，請更正其關聯因子", false);
			};
			String factorControlType = utiFactor.getControlType();
			if(factorControlType.equals("1")){
				List<UtiDecisionTable> utiDecisionTableList = new ArrayList<UtiDecisionTable>(0);
				String hql = "select a from UtiDecisionTable a where a.id.riskCode= ? and  a.id.factorCode= ?";
			    utiDecisionTableList=super.findByHql(hql ,utiFactor.getId().getRiskCode().trim(),utiFactor.getId().getFactorCode().trim());
			    if(!utiDecisionTableList.isEmpty()){
			    	utiDecisionTableSaveList.addAll(utiDecisionTableList);			    	
			    }
			}else if(factorControlType.equals("2")){
				List<UtiFactorRelaShip> utiFactorRelaShipList=new ArrayList<UtiFactorRelaShip>();
				String hql = "select a from UtiFactorRelaShip a where a.id.riskCode= ? and  a.id.factorCode= ?";
				utiFactorRelaShipList=super.findByHql(hql ,utiFactor.getId().getRiskCode().trim() ,utiFactor.getId().getFactorCode().trim());
				for (int j = 0; j < utiFactorRelaShipList.size(); j++) {
					String factorRelaShipContent = utiFactorRelaShipList.get(j).getRelatedFactorCodes();
					if(StringUtils.isBlank(factorSetList.toString())){
						factorSetList.append(emulativeFormula(factorRelaShipContent));
					}else{
						factorSetList.append(",").append(emulativeFormula(factorRelaShipContent));
					}
					List<UtiDecisionTable> utiDecisionTables = new ArrayList<UtiDecisionTable>(0);	
					String hql2 = "select a from UtiDecisionTable a where a.id.riskCode = ? and  a.id.factorCode= ? and a.id.comCode =? and a.id.clauseCode = ? and a.id.kindCode = ?";
					utiDecisionTables=super.findByHql(hql2,utiFactor.getId().getRiskCode().trim(),utiFactor.getId().getFactorCode().trim(),utiFactorRelaShipList.get(j).getId().getComCode(),utiFactorRelaShipList.get(j).getId().getClauseCode(),utiFactorRelaShipList.get(j).getId().getKindCode());
					utiDecisionTableSaveList.addAll(utiDecisionTables);
				}
				utiFactorRelaShipSaveList.addAll(utiFactorRelaShipList);
				String factorSql = "select a from UtiFactor a where 1 = 1 and a.id.riskCode = ? and a.id.factorCode in (?) and a.validStatus = '1'";//modify by liubin bangdingbianliang 
			    List<UtiFactor> utiFactorRelashipList = super.findByHql(factorSql, utiFactor.getId().getRiskCode(),factorSetList.toString());//modify by liubin bangdingbianliang 
			    addProductCopyFactorRelated(utiFactorRelashipList,riskCodeConfig,riskCodeCopy,countNumber);
			}else if(factorControlType.equals("3")){
				UtiFormula utiFormula = new UtiFormula();
				String hql = "select a from UtiFormula a where a.id.riskCode= ?  and  a.id.factorCode= ?";
				utiFormula=(UtiFormula) super.findByHql(hql, utiFactor.getId().getRiskCode().trim(),utiFactor.getId().getFactorCode().trim() ).get(0);
				utiFormulaSaveList.add(utiFormula);
				String formulaContent = utiFormula.getContent();
				if(StringUtils.isBlank(factorSetList.toString())){
					factorSetList.append(emulativeFormula(formulaContent));
				}else{
					factorSetList.append(",").append(emulativeFormula(formulaContent));
				}
				String factorSql = "select a from UtiFactor a where 1 = 1 and a.id.riskCode = ? and a.id.factorCode in (?) and a.validStatus = '1'";//modify by liubin bangdingbianliang 
			    List<UtiFactor> utiFactorFormulaList = super.findByHql(factorSql, utiFactor.getId().getRiskCode(),factorSetList.toString());//modify by liubin bangdingbianliang 
			    addProductCopyFactorRelated(utiFactorFormulaList,riskCodeConfig,riskCodeCopy,countNumber);
			}
		}
		
		if(StringUtils.isNotBlank(factorSetList.toString())){
			String factorSql = "select a from UtiFactor a where 1 = 1 and a.id.riskCode = ? and a.id.factorCode in (?) and a.validStatus = '1'";//modify by liubin bangdingbianliang
		    utiFactorSaveList = super.findByHql(factorSql, riskCodeCopy,factorSetList.toString());	//modify by liubin bangdingbianliang		
		}
	    List<UtiFactor> utiFactorRemoveSameList = this.removeSameProductCopyFactor(utiFactorList, utiFactorSaveList);
	    
	    for(UtiFactor utiFactor : utiFactorRemoveSameList){
	    	UtiFactor utiFactorNew = new UtiFactor();
	    	BoCopyUtil.convert(utiFactor, utiFactorNew, UtiFactor.class, null, null);
            BoCopyUtil.setValueforSpecificField(utiFactorNew, "RiskCode", riskCodeConfig);
            utiFactorSaveLastList.add(utiFactorNew);
	    }
		
		for(UtiFormula utiFormula : utiFormulaSaveList){
			UtiFormula utiFormulaNew = new UtiFormula();
	    	BoCopyUtil.convert(utiFormula, utiFormulaNew, UtiFormula.class, null, null);
            BoCopyUtil.setValueforSpecificField(utiFormulaNew, "RiskCode", riskCodeConfig);
            utiFormulaLastSaveList.add(utiFormulaNew);
	    }
		for(UtiDecisionTable utiDecisionTableL : utiDecisionTableSaveList){
			UtiDecisionTable utiDecisionTableNew = new UtiDecisionTable();
	    	BoCopyUtil.convert(utiDecisionTableL, utiDecisionTableNew, UtiDecisionTable.class, null, null);
            BoCopyUtil.setValueforSpecificField(utiDecisionTableNew, "RiskCode", riskCodeConfig);
            utiDecisionTableLastSaveList.add(utiDecisionTableNew);
	    }
		for(UtiFactorRelaShip utiFactorRelaShip : utiFactorRelaShipSaveList){
			UtiFactorRelaShip utiFactorRelaShipNew = new UtiFactorRelaShip();
	    	BoCopyUtil.convert(utiFactorRelaShip, utiFactorRelaShipNew, UtiFactor.class, null, null);
            BoCopyUtil.setValueforSpecificField(utiFactorRelaShipNew, "RiskCode", riskCodeConfig);
            utiFactorRelaShipLastSaveList.add(utiFactorRelaShipNew);
	    }
		if(!utiFactorSaveLastList.isEmpty()){
			super.saveAll(utiFactorSaveLastList);
		}
		if(!utiFormulaLastSaveList.isEmpty()){
			super.saveAll(utiFormulaLastSaveList);
		}
		if(!utiDecisionTableLastSaveList.isEmpty()){
			super.saveAll(utiDecisionTableLastSaveList);
		}
		if(!utiFactorRelaShipLastSaveList.isEmpty()){
			super.saveAll(utiFactorRelaShipLastSaveList);
		}
		
	}
	/**
	 * 產品復制進入頁面配置的時候保存系數配置的信息
	 * @param riskCodeConfig 險種配置
	 * @param riskCodeCopy 險種複製
	 * @param referenceRiskFlag 險種標誌
	 * @throws Exception
	 */
	public void saveProductCopyCoeffConfig(String riskCodeConfig,String riskCodeCopy,String referenceRiskFlag) throws Exception{
		List<PrpDcoeff> prpDcoeffSaveList = new ArrayList<PrpDcoeff>(0);
		//查詢出來配置產品和復制產品的條款責任代碼匹配上的系數信息
		List<PrpDcoeff> prpDcoeffList = (List<PrpDcoeff>)this.findProductCopyReSetMSG(riskCodeConfig, riskCodeCopy, referenceRiskFlag, "coeffConfigCopy").get(0);
		//將復制產品的系數信息轉換成配置產品的
		for(PrpDcoeff prpDcoeff : prpDcoeffList){
			PrpDcoeff prpDcoeffNew = new PrpDcoeff();
	    	BoCopyUtil.convert(prpDcoeff, prpDcoeffNew, PrpDcoeff.class, null, null);
            BoCopyUtil.setValueforSpecificField(prpDcoeffNew, "RiskCode", riskCodeConfig);
            prpDcoeffSaveList.add(prpDcoeffNew);
	    }
		//保存系數信息
		if(!prpDcoeffSaveList.isEmpty()){
			super.saveAll(prpDcoeffSaveList);
		}
	}
	
	/**
	 * 產品復制在離開頁面的時候，如果沒有進行頁面配置的保存，則刪除配置產品的下的系數的信息
	 * @param riskCodeConfig 險種配置
	 */
	public void deleteProductCopyCoeffConfig(String riskCodeConfig){
		//查處配置產品系數的信息  
		String prpDcoeffSql = "select a from PrpDcoeff a where a.id.riskCode = ? ";
		List<PrpDcoeff> prpDcoeffList = super.findByHql(prpDcoeffSql,riskCodeConfig);
		if(!prpDcoeffList.isEmpty()){
			super.deleteAll(prpDcoeffList);
		}
	}
	/**
	 * 移除相同的計算因子
	 * @param utiFactorList 計算因子
	 * @param utiFactorSaveList 保存的計算因子
	 * @return List UtiFactor 計算因子list對象
	 */
	public List<UtiFactor> removeSameProductCopyFactor(List<UtiFactor> utiFactorList,List<UtiFactor> utiFactorSaveList){
		List<UtiFactor> utiFactorSaveLastList = new ArrayList<UtiFactor>(0);
		int flag = 0;
		//查找模塊要素數據是新增的還是要修改的
		for(int i = 0;i < utiFactorSaveList.size();i++){
			flag = 0;
			String factorCodeSave = utiFactorSaveList.get(i).getId().getFactorCode().trim();
			//判斷頁面數據是不是數據庫中的數據以便判斷執行的是新增還是修改操作
			for(int j = 0;j < utiFactorList.size();j++){
				String factorCode = utiFactorList.get(j).getId().getFactorCode().trim();
				//頁面數據在數據庫中則執行修改操作
				if(factorCode.equals(factorCodeSave)){
					flag = 1;
				}
				
			}
			if(flag == 0){
				utiFactorSaveLastList.add(utiFactorSaveList.get(i));
			}
		}
		return utiFactorSaveLastList;
	}
	
	/**
	 * 產品復制離開復制頁面計算配置沒有進行保存的情況下需要刪除原來保存的被配置產品的因子
	 * @param riskCodeConfig 險種配置
	 */
	public void deleteProductCopyFormulaFactor(String riskCodeConfig){
		//查處配置產品產品下的因子
		String factorSql = "select a from UtiFactor a where a.id.riskCode = ? ";
		//查處配置產品產品下的因子關系
		String utiFactorRelaShipSql = "select a from UtiFactorRelaShip a where a.id.riskCode = ? ";
		//查處配置產品產品下的因子的決策信息
		String utiDecisionTableSql = "select a from UtiDecisionTable a where a.id.riskCode = ? ";
		//查處配置產品產品下的因子的公式
		String utiFormulaSql = "select a from UtiFormula a where a.id.riskCode = ? ";
		List<UtiFactor> utiFactorList = super.findByHql(factorSql, riskCodeConfig);
		List<UtiFactorRelaShip> utiFactorRelaShipList = super.findByHql(utiFactorRelaShipSql, riskCodeConfig);
		List<UtiDecisionTable> utiDecisionTableList = super.findByHql(utiDecisionTableSql, riskCodeConfig);
		List<UtiFormula> utiFormulaList = super.findByHql(utiFormulaSql, riskCodeConfig);
		//獲取的因子是空的，則不做處理。
		if(!utiFactorList.isEmpty()){
			//刪除配置產品下的因子
			super.deleteAll(utiFactorList);
		}
		if(!utiFactorRelaShipList.isEmpty()){
			super.deleteAll(utiFactorRelaShipList);
		}
		if(!utiDecisionTableList.isEmpty()){
			super.deleteAll(utiDecisionTableList);
		}
		if(!utiFormulaList.isEmpty()){
			super.deleteAll(utiFormulaList);
		}
	}
	/**
	 * 產品配置完成的時候刪除產品模板的對應的關系（創新數據第一次過來的時候，默認保存的產品模板的數據
	 * @param riskCodeConfig 險種配置
	 */
	public void deletePrpDriskTemplate(String riskCodeConfig){
		//查找產品的模板的關系
		String prpDriskTemplateSql = "select a from PrpDriskTemplate a where a.flag = ? and  a.id.riskCode = ? ";
		List<PrpDriskTemplate> prpDriskTemplateList = super.findByHql(prpDriskTemplateSql,ProductConstants.PRPDRISK_FLAG_TEST, riskCodeConfig);
		//產品模板關系時空的，不做處理。
		if(prpDriskTemplateList.isEmpty()){
			return;
		}
		//刪除配置產品下的產品模板的關系
		super.deleteAll(prpDriskTemplateList);
	}
	/**
	 * 產品配置完成的時候將產品的標志位進行修改
	 * @param riskCodeConfig 險種配置
	 */
	public void updatePrpDriskFlag(String riskCodeConfig){
		//查處配置的產品
		String prpDriskSql = "select a from PrpDrisk a where a.riskCode = ? ";
		List<PrpDrisk> prpDriskList = super.findByHql(prpDriskSql,riskCodeConfig);
		//產品時空的不做處理
		if(prpDriskList.isEmpty()){
			return;
		}
		//更新產品下的標志位，標志產品配置完成
		prpDriskList.get(0).setFlag(ProductConstants.PRPDRISK_FLAG_CONFIG);
		super.update(prpDriskList.get(0));
	}
	/**
	 * 產品配置了一部分但是沒有配置完成的產品的標志位的修改
	 * @param riskCodeConfig 險種配置
	 */
	public void updatePrpDriskProcessingFlag(String riskCodeConfig){
		//查處配置的產品
		String prpDriskSql = "select a from PrpDrisk a where a.riskCode = ? ";
		List<PrpDrisk> prpDriskList = super.findByHql(prpDriskSql,riskCodeConfig);
		//產品是空的時候不做處理，或者產品已經打上了處理中的標志，那么此產品無需再打標志；
		if(prpDriskList.isEmpty() || 
				 (StringUtils.isNotBlank(prpDriskList.get(0).getFlag()) && ProductConstants.PRPDRISK_FLAG_PROCESSIMG.equals(prpDriskList.get(0).getFlag().trim()))){
			return;
		}
		//更新產品下的標志位，標志產品配置完成
		prpDriskList.get(0).setFlag(ProductConstants.PRPDRISK_FLAG_PROCESSIMG);
		super.update(prpDriskList.get(0));
	}
	/**
	 * 產品點擊完成時的操作
	 * @param  riskCodeConfig 險種配置
	 */
	public void productCopyFinishOperate(String riskCodeConfig){
		//產品配置完成的時候刪除產品模板的對應的關系（創新數據第一次過來的時候，默認保存的產品模板的數據）
		deletePrpDriskTemplate(riskCodeConfig);
		//產品配置完成的時候將產品的標志位進行修改
		updatePrpDriskFlag(riskCodeConfig);
	}
	/**
	 * 解析公式內容，獲取因子代碼
	 * @param  formulaContentT 公式內容
	 * @return String 因子代碼集合
	 */
	private  String emulativeFormula(String formulaContentT) {
		StringBuilder factorSet = new StringBuilder();
		factorSet.append("'");
		for (int i = 0; i < formulaContentT.length(); i++) {
			StringBuffer s = new StringBuffer(String.valueOf(formulaContentT.charAt(i)));
			if ("P".equals(s.toString())) {
				String factor = s.append(formulaContentT.charAt(i + 1)).append(
						formulaContentT.charAt(i + 2)).append(
						formulaContentT.charAt(i + 3)).append(
						formulaContentT.charAt(i + 4)).append(
						formulaContentT.charAt(i + 5)).toString();
				factorSet.append(factor+"','");
			}
		}
		return factorSet.substring(0, factorSet.length()-2).toString();
	}
	/**
	 * 前臺業務規則事件的onchange方法
	 * @param  frontEventSet 事件的集合
	 * @return String
	 */
	public String productCopyFrontEventChange(String frontEventSet){
		
		HashMap<String, String> eventHashMap= new HashMap<String, String>();
		//記錄錯誤的代碼
		StringBuilder errorCodeSet = new StringBuilder(255);
		//記錄事件的中文名稱
		StringBuilder eventCNameSet = new StringBuilder(255);
		//記錄事件的英文名稱
		StringBuilder eventENameSet = new StringBuilder(255);
		String[] eventList = frontEventSet.split(",");
		for(int i = 0; i < eventList.length;i++){	
			List<Object[]> eventMSGList = null;
			//獲取要查詢的事件
			String event = eventList[i].trim();
			//如果得到的值是空的，則調過此次循環
		    if(StringUtils.isBlank(event)){
				 continue;
			}
		    //如果前臺的輸入框中輸入了相同那個的事件，只處理其中的一個事件
		    if(eventHashMap.containsKey(event)){
		    	continue;
		    }else{
		    	eventHashMap.put(event, event);
		    }
		    //查詢事件的英文代碼和中文的代碼
		    String frontRuleEventSql = "select a.codeEName,a.codeCName from PrpDriskConfigCode a where 1 = 1 and a.id.codeType = 'Event' and a.codeEName = ? ";
		    eventMSGList = super.findByHql(frontRuleEventSql, event);
		    //查詢出來的對象不是空的，證明這個事件是正確的
		    if(!eventMSGList.isEmpty()){
		    	//將事件組織成頁面顯示的形式
		    	if(eventENameSet.length() <= 0){
		    		 eventENameSet.append(eventMSGList.get(0)[0].toString().trim());
		    		 eventCNameSet.append(eventMSGList.get(0)[1].toString().trim());
				}else{
					 eventENameSet.append(",").append(eventMSGList.get(0)[0].toString().trim());
		    		 eventCNameSet.append(",").append(eventMSGList.get(0)[1].toString().trim());
				}
		    //查詢出來的事件是空的，證明事件不正確	
		    }else{
		    	if(errorCodeSet.length() <= 0){
		    		errorCodeSet.append(event);
				}else{
					errorCodeSet.append(",").append(event);
				}
		    }
		}
		return eventENameSet.toString()+"|"+eventCNameSet.toString()+"|"+errorCodeSet.toString();
	}
	/**
	 * 產品復制進入流程控制頁面的時候，將復制產品的流程控制代碼復制一份，保存成配置產品的
	 * @param  riskCodeConfig 險種配置
	 * @param  riskCodeCopy 險種複製
	 * @throws Exception
	 */
	public void addProductCopyProcessConfig(String riskCodeConfig,String riskCodeCopy) throws Exception{
		
		List<UtiRiskProcessConfig> utiRiskProcessConfigSavelist = new ArrayList<UtiRiskProcessConfig>(0);
		List<UtiRiskProcessConfig> utiRiskProcessConfigDeletelist = new ArrayList<UtiRiskProcessConfig>(0);
		String utiRiskProcessSql = "select a from UtiRiskProcessConfig a where a.id.riskCode = ? and a.validStatus = '1'";
	    List<UtiRiskProcessConfig> utiRiskProcessCopylist = super.findByHql(utiRiskProcessSql,riskCodeCopy);
	    List<UtiRiskProcessConfig> utiRiskProcessConfiglist = super.findByHql(utiRiskProcessSql,riskCodeConfig);
	    //如果原來產品中已經有了一套流程控制配置則進行刪除
	    utiRiskProcessConfigDeletelist = this.compareConfigAndCopyProcess(utiRiskProcessConfiglist, utiRiskProcessCopylist);
	    for(UtiRiskProcessConfig utiRiskProcessConfig : utiRiskProcessCopylist){
	    	UtiRiskProcessConfig utiRiskProcessConfigNew = new UtiRiskProcessConfig();
	    	BoCopyUtil.convert(utiRiskProcessConfig, utiRiskProcessConfigNew, UtiRiskProcessConfig.class, null, null);
            BoCopyUtil.setValueforSpecificField(utiRiskProcessConfigNew, "RiskCode", riskCodeConfig);
            utiRiskProcessConfigSavelist.add(utiRiskProcessConfigNew);
	    }
	    super.deleteAll(utiRiskProcessConfigDeletelist);
	    super.saveAll(utiRiskProcessConfigSavelist);
	}
	
	public List<UtiRiskProcessConfig> compareConfigAndCopyProcess(List<UtiRiskProcessConfig> utiRiskProcessConfiglist,List<UtiRiskProcessConfig> utiRiskProcessCopylist){
		List<UtiRiskProcessConfig> utiRiskProcessConfigDeletelist = new ArrayList<UtiRiskProcessConfig>(0);
		int flag = 0;
		for(int i = 0;i < utiRiskProcessConfiglist.size();i++){
			flag = 0;
			String comCodeConfig = utiRiskProcessConfiglist.get(i).getId().getComCode().trim();
			String processCodeConfig = utiRiskProcessConfiglist.get(i).getId().getProcessCode().trim();
			//判斷頁面數據是不是數據庫中的數據以便判斷執行的是新增還是修改操作
			for(int j = 0;j < utiRiskProcessCopylist.size();j++){
				String comCodeCopy= utiRiskProcessCopylist.get(j).getId().getComCode().trim();
				String processCodeCopy = utiRiskProcessCopylist.get(j).getId().getProcessCode().trim();
				if(comCodeConfig.equals(comCodeCopy) && processCodeConfig.equals(processCodeCopy)){
					flag = 1;
				}
				
			}
			if(flag == 0){
				utiRiskProcessConfigDeletelist.add(utiRiskProcessConfiglist.get(i));
			}
		}
		return utiRiskProcessConfigDeletelist;
	}
	
	/**
	 * 產品配置復制的時候展現產品的基礎的信息和頁面的公用固化的信息
	 * @param riskCodeConfigure 險種配置
	 * @return List 產品的基礎的信息list對象
	 * @throws Exception
	 */
	public List<Object> findRiskConfigNotReferenceDate(String riskCodeConfigure) throws Exception{
		List<Object> riskConfigNotReferenceDateList = new ArrayList<Object>(0);
		/**產品配置服務的獲取*/
		String riskCode_reference = "PUB";
		RiskService riskService = (RiskService) ServiceFactory.getService("riskService");
		List<PrpDriskBlockVO> prpDriskBlockVOList = riskService.findNoReferenceMessage(riskCode_reference);
		 /**產品配置基本信息的組織，查找配置產品的產品信息*/
		PrpDrisk prpDrisk = new PrpDrisk(); 
		String prpDriskSql = "select a from PrpDrisk a where 1 = 1 and a.riskCode = ? ";
	  	List<PrpDrisk> prpDriskList = super.findByHql(prpDriskSql, riskCodeConfigure);//被配置產品的基本信息
	  	if(prpDriskList != null && !prpDriskList.isEmpty()){
	  		prpDrisk = prpDriskList.get(0);
	  	}
	  	/**流程控制信息的獲取*/
	  	List<ProductProcessVO> productProcessVOlist = new ArrayList<ProductProcessVO>(0);
	  	productProcessVOlist = riskService.showProductProcess(riskCodeConfigure);//流程控制信息的獲取
	  	/**產品輔助標志位的獲取*/
	  	List<Object> list = this.findRiskConfigExtNotReferenceMSG(prpDrisk);//擴展信息的獲取和條款責任的集合
		PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)list.get(0);//擴展信息
		String clauseKindSet = (String)list.get(1);//被配置產品條款責任的集合
	  	 /**周邊系統數據的整理*/
	  	PeripheralService peripheralService = (PeripheralService) ServiceFactory.getService("peripheralService");
		List<PrpDSimpletreaty> prpDSimpletreatyList = peripheralService.findPrpDSimpletreatyByClassCode(prpDrisk.getClassCode());//被配置產品的簡單合約分出
		List<PrpDTreatyReten> prpDTreatyRetenList = peripheralService.findPrpDTreatyRetenByClassCode(prpDrisk.getClassCode());//自留額計劃
		PeripheralAccountInfoVO peripheralAccountInfoVO = peripheralService.findPrpDaccountInfoListByRiskCode(prpDrisk.getRiskCode());//收付財務
		//組織產品復制的真個頁面的信息
	  	riskConfigNotReferenceDateList.add(prpDrisk);
	  	riskConfigNotReferenceDateList.add(productProcessVOlist);
	  	riskConfigNotReferenceDateList.add(prpDriskBlockVOList);
	  	//輔助標志位的信息
	  	riskConfigNotReferenceDateList.add(prpDriskRelatedExtSetVO);
	  	riskConfigNotReferenceDateList.add(clauseKindSet);
	  	//周邊系統的獲取
	  	riskConfigNotReferenceDateList.add(prpDSimpletreatyList);
	  	riskConfigNotReferenceDateList.add(prpDTreatyRetenList);
	  	riskConfigNotReferenceDateList.add(peripheralAccountInfoVO);
		return riskConfigNotReferenceDateList;
	}
	
	/**
	 * 產品擴展信息數據的整理--->產品直接配置中的操作
	 * @param prpDriskConfig 產品信息對象
	 * @return List 組織擴展信息的集合
	 */
	public List<Object> findRiskConfigExtNotReferenceMSG(PrpDrisk prpDriskConfig){
	    /**組織擴展信息的集合的對象*/
	    PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = new PrpDriskRelatedExtSetVO();
		List<Object> list = new ArrayList<Object>(0);
		PrpDriskExtVO prpDriskExtVO = new PrpDriskExtVO();
	    List<PrpDriskClauseExtVO> prpDriskClauseExtVOList = new ArrayList<PrpDriskClauseExtVO>();
	    List<PrpDriskClauseKindExtVO> prpDriskClauseKindExtVOList = new ArrayList<PrpDriskClauseKindExtVO>();
	    List<PrpDrationExtVO> prpDrationExtVOList = new ArrayList<PrpDrationExtVO>();
	    List<PrpDriskLimitVO> prpDriskLimitVOList = new ArrayList<PrpDriskLimitVO>();
	    //被配置產品的條款責任的集合
	    String clauseKindSet = "";
	    /**查找被配置產品的條款責任*/
	    StringBuffer prpDriskClauseKindConfigSql = new StringBuffer();
	    //modify by liuxiaofei 20110718 modify reason:產品配置時，需要同時帶出riskkcserialno    begin
	    //prpDriskClauseKindConfigSql.append("select a.clauseCName,b.id.clauseCode,b.kindCode,b.kindName,b.claimType,b.calculateFlag from PrpDriskClause a,PrpDriskClauseKind b where 1 = 1 ");
	    prpDriskClauseKindConfigSql.append("select a.clauseCName,b.id.clauseCode,b.kindCode,b.kindName,b.claimType,b.calculateFlag,b.id.riskKCSerialNo from PrpDriskClause a,PrpDriskClauseKind b where 1 = 1 ");
	    //modify by liuxiaofei 20110718 modify reason:產品配置時，需要同時帶出riskkcserialno    end
	    prpDriskClauseKindConfigSql.append("  and (a.id.riskCode = b.id.riskCode and a.id.clauseCode = b.id.clauseCode) and b.id.riskCode = ?");
	    List<Object[]> prpDriskClauseKindConfigList = super.findByHql(prpDriskClauseKindConfigSql.toString(),prpDriskConfig.getRiskCode());
	    /**查詢被配置產品的限額免賠信息*/
	    StringBuffer prpDriskLimitConfigSql = new StringBuffer();
	    
	    //modify by liuxiaofei 20110711 reason:根據險種代碼、條款代碼、限額代碼不一定能查出唯一一條記錄    begin
	    //prpDriskLimitConfigSql.append("select a.id.clauseCode,a.kindCode,a.id.limitCode,a.limitCName,a.limitGroupNo from PrpDriskLimit a where a.id.riskCode = ? ");
	    prpDriskLimitConfigSql.append("select a.id.clauseCode,a.kindCode,a.id.limitCode,a.limitCName,a.limitGroupNo,a.id.serialNo from PrpDriskLimit a where a.id.riskCode = ? ");
	    //modify by liuxiaofei 20110711 reason:根據險種代碼、條款代碼、限額代碼不一定能查出唯一一條記錄    end
	    
	    List<Object[]> prpDriskLimitConfigList = super.findByHql(prpDriskLimitConfigSql.toString(), prpDriskConfig.getRiskCode());
	    /**根據產品代碼查找方案的信息*/
	    //modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    begin
// 	    String prpDrationSql = "select a from PrpDration a where 1 = 1 and a.prpDrisk.riskCode = ?";
// 	    List<PrpDration> prpDrationList = super.findByHql(prpDrationSql, prpDriskConfig.getRiskCode());
 	    //modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    end
		/**查找參考產品或者模板的產品，產品條款責任，方案的信息   start*/

    	prpDriskExtVO.setRiskCode(prpDriskConfig.getRiskCode());
		prpDriskExtVO.setRiskCName(prpDriskConfig.getRiskCName());
		prpDriskExtVO.setShortRateFlag(prpDriskConfig.getShortRateFlag());
		prpDriskExtVO.setClassFlag(prpDriskConfig.getClassFlag());
		prpDriskExtVO.setRateUnit(prpDriskConfig.getRateUnit());
		prpDriskExtVO.setRiskFlag(prpDriskConfig.getRiskFlag());
		/**組織產品條款的擴展信息---被peizhi產品*/
 	    List<PrpDriskClause> prpDriskClauseConfigList = prpDriskConfig.getPrpDriskClauses();
 	    for(PrpDriskClause prpDriskClause : prpDriskClauseConfigList) {
 	    	PrpDriskClauseExtVO prpDriskClauseExtVO = new PrpDriskClauseExtVO();
			prpDriskClauseExtVO.setRiskCode(prpDriskConfig.getRiskCode());
			prpDriskClauseExtVO.setClauseCode(prpDriskClause.getId().getClauseCode());
			prpDriskClauseExtVO.setClauseCName(prpDriskClause.getClauseCName());
			prpDriskClauseExtVO.setClauseVersion(prpDriskClause.getClauseVersion());
			prpDriskClauseExtVO.setSpecialType(prpDriskClause.getSpecialType());
			prpDriskClauseExtVO.setHealthType1(prpDriskClause.getHealthType1());
			prpDriskClauseExtVO.setHealthType2(prpDriskClause.getHealthType2());
			prpDriskClauseExtVO.setHealthType3(prpDriskClause.getHealthType3());
			prpDriskClauseExtVO.setClauseDescFlag(prpDriskClause.getClauseDescFlag());
			prpDriskClauseExtVOList.add(prpDriskClauseExtVO);
		}
 	   /**產品條款責任的擴展信息-----被配置信息*/
		for(Object[] prpDriskClauseKind : prpDriskClauseKindConfigList) {
			String clauseKindCode = prpDriskClauseKind[1].toString().trim()+","+prpDriskClauseKind[2].toString().trim();
			PrpDriskClauseKindExtVO prpDriskClauseKindExtVO = new PrpDriskClauseKindExtVO();
			prpDriskClauseKindExtVO.setRiskCode(prpDriskConfig.getRiskCode());
			prpDriskClauseKindExtVO.setClauseCode(prpDriskClauseKind[1].toString());
			prpDriskClauseKindExtVO.setKindCode(prpDriskClauseKind[2].toString());
			if(prpDriskClauseKind[0] != null){
			    prpDriskClauseKindExtVO.setClauseCName(prpDriskClauseKind[0].toString());
			}
			if(prpDriskClauseKind[3] != null){
			    prpDriskClauseKindExtVO.setKindCName(prpDriskClauseKind[3].toString());
			}    
            if(prpDriskClauseKind[4] != null){
            	prpDriskClauseKindExtVO.setClaimType(prpDriskClauseKind[4].toString());				
			}
            if(prpDriskClauseKind[5] != null){
            	prpDriskClauseKindExtVO.setCalculateFlag(prpDriskClauseKind[5].toString());            	
            }
            //modify by liuxiaofei 20110718 modify reason:產品配置時，需要同時帶出riskkcserialno    begin
            if(prpDriskClauseKind[6] != null){
            	prpDriskClauseKindExtVO.setRiskKCSerialNo(Integer.parseInt(prpDriskClauseKind[6].toString()));
            }
            //modify by liuxiaofei 20110718 modify reason:產品配置時，需要同時帶出riskkcserialno    end
            prpDriskClauseKindExtVOList.add(prpDriskClauseKindExtVO);
            if("".equals(clauseKindSet)){
				clauseKindSet = clauseKindCode.replace(",", "~");
			}else{
				clauseKindSet = clauseKindSet +","+clauseKindCode.replace(",", "~");
			}
		}
	 	   /**產品限額免賠的擴展信息為責任賦值責任名稱*/
		for(Object[] prpDriskLimit : prpDriskLimitConfigList) {
			PrpDriskLimitVO prpDriskLimitVO = new PrpDriskLimitVO();
			//責任代碼不為空
			if(prpDriskLimit[1] != null && StringUtils.isNotBlank(prpDriskLimit[1].toString().trim())){
				for(int i = 0;i < prpDriskClauseKindConfigList.size();i++){
					if((prpDriskLimit[1].toString().trim()).equals(prpDriskClauseKindConfigList.get(i)[2].toString().trim())){
						prpDriskLimitVO.setKindCode(prpDriskLimit[1].toString().trim());
						if(prpDriskClauseKindConfigList.get(i)[3] != null){
							prpDriskLimitVO.setKindCName(prpDriskClauseKindConfigList.get(i)[3].toString().trim());
						}
					}
				}
			}
			
			prpDriskLimitVO.setClauseCode(prpDriskLimit[0].toString().trim());
			for(int j = 0;j < prpDriskClauseConfigList.size();j++){
				String clauseCode = prpDriskClauseConfigList.get(j).getId().getClauseCode();
				if(prpDriskLimit[0].toString().trim().equals(clauseCode)){
					prpDriskLimitVO.setClauseCName(prpDriskClauseConfigList.get(j).getClauseCName());
				}
			}
			if(prpDriskLimit[2] != null){
            	prpDriskLimitVO.setLimitCode(prpDriskLimit[2].toString().trim());				
			}
            if(prpDriskLimit[3] != null){
            	prpDriskLimitVO.setLimitCName(prpDriskLimit[3].toString().trim());            	
            }
            if(prpDriskLimit[4] != null){
            	prpDriskLimitVO.setLimitGroupNo(prpDriskLimit[4].toString().trim());            	
            }
            //modify by liuxiaofei 20110711 reason:根據險種代碼、條款代碼、限額代碼不一定能查出唯一一條記錄    begin
            if(prpDriskLimit[5] != null){
            	prpDriskLimitVO.setSerialNo(Integer.parseInt(prpDriskLimit[5].toString().trim()));
            }
            //modify by liuxiaofei 20110711 reason:根據險種代碼、條款代碼、限額代碼不一定能查出唯一一條記錄    begin
	            prpDriskLimitVOList.add(prpDriskLimitVO);
		}
		/**組織產品方案的擴展信息*/
		//modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    begin
//	 	for(PrpDration prpDration : prpDrationList) {
//	 		PrpDrationExtVO prpDrationExtVO = new PrpDrationExtVO();
//			prpDrationExtVO.setRiskCode(prpDriskConfig.getRiskCode());
//			prpDrationExtVO.setPlanCode(prpDration.getPlanCode().trim());
//			prpDrationExtVO.setPlanCname(prpDration.getPlanCName());
//			prpDrationExtVO.setUpdateFlag(prpDration.getUpdateFlag());
//			prpDrationExtVO.setUniqueFlag(prpDration.getUniqueFlag());
//			prpDrationExtVO.setMaxCount(prpDration.getMaxCount());
//			
//			prpDrationExtVOList.add(prpDrationExtVO);    
//		}
		//modify by liuxiaofie 20110526 del 產品引擎不同步方案信息，無需查找方案信息    end
    

 	    /**將擴展信息的集合組織到一個大對象中*/
 	  prpDriskRelatedExtSetVO.setPrpDriskExtVO(prpDriskExtVO);
 	  prpDriskRelatedExtSetVO.setPrpDriskClauseExtVOs(prpDriskClauseExtVOList);
 	  prpDriskRelatedExtSetVO.setPrpDriskClauseKindExtVOs(prpDriskClauseKindExtVOList);
 	  prpDriskRelatedExtSetVO.setPrpDrationExtVOs(prpDrationExtVOList);
 	  prpDriskRelatedExtSetVO.setPrpDriskLimitVOs(prpDriskLimitVOList);
 	  list.add(prpDriskRelatedExtSetVO);
 	  list.add(clauseKindSet);
  	  return list;
	}
	
}