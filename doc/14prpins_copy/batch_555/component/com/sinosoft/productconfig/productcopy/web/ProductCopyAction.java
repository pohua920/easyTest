package com.sinosoft.productconfig.productcopy.web;

import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.productconfig.common.schema.model.PrpDSimpletreaty;
import com.sinosoft.productconfig.common.schema.model.PrpDTreatyReten;
import com.sinosoft.productconfig.common.schema.model.PrpDriskBlock;
import com.sinosoft.productconfig.common.schema.model.UtiBackRuleConfig;
import com.sinosoft.productconfig.common.schema.model.UtiFormula;
import com.sinosoft.productconfig.common.schema.model.UtiJSFunc;
import com.sinosoft.productconfig.common.schema.model.UtiPrintConfig;
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
import com.sinosoft.productconfig.productcopy.service.facade.ProductCopyService;
import com.sinosoft.prpins.policy.schema.vo.UserInfo;

/**
 * 非車產品配置系統的產品復制功能ACTION,里面包括了要素相關的操作
 * @author Sinosoft
 */
public class ProductCopyAction extends Struts2Action{
	
	/**********************產品復制變量定義開始****************/
	/** 產品復制接口定義 */
	private ProductCopyService productCopyService;
	/** 產品配置基本信息頁面封裝的信息 */
	private PrpDrisk prpDrisk;//產品信息
	private List<PrpDriskClause> prpDriskClauselist = new ArrayList<PrpDriskClause>();//產品條款信息
	/**產品配置流程信息頁面封裝的信息*/
	private List<ProductProcessVO> productProcessVOlist = new ArrayList<ProductProcessVO>(0);//頁面所有的流程控制，及選中的流程的信息
	private String comCodeList;//流程控制中單個產品所有的機構的集合
	/**產品配置頁面信息頁面封裝的信息*/
	private List<PrpDriskBlockVO> prpDriskBlockVOList = new ArrayList<PrpDriskBlockVO>(0);//產品配置頁面信息對應的VO
	private String templateCode;//產品復制的時候，引用的模板代碼
	private String testRiskOrTemplate;//配置產品時判斷是引用的模板還是產品（pageframe）
	/**產品配置計算信息頁面封裝的信息*/
    private List<UtiFormulaInfoVO> utiFormulaInfoVOList = new ArrayList<UtiFormulaInfoVO>(0);
	/**產品配置業務規則信息頁面封裝的信息*/
	private List<UtiJSFunc> utiJSFuncList = new ArrayList<UtiJSFunc>(0);
	private List<UtiBackRuleConfig> utiBackRuleConfigList = new ArrayList<UtiBackRuleConfig>(0);
	private PrpDriskExtVO prpDriskExtVO;//產品擴展信息的VO
    private List<PrpDriskClauseExtVO> prpDriskClauseExtVOList = new ArrayList<PrpDriskClauseExtVO>();//產品條款擴展信息的VO
    private List<PrpDriskClauseKindExtVO> prpDriskClauseKindExtVOList = new ArrayList<PrpDriskClauseKindExtVO>();//產品條款責任擴展信息的VO
    private List<PrpDrationExtVO> prpDrationExtVOList = new ArrayList<PrpDrationExtVO>();//產品下的方案擴展信息的VO
    private List<PrpDriskLimitVO> prpDriskLimitVOList = new ArrayList<PrpDriskLimitVO>();//產品下的限額免賠擴展信息的VO
    /**產品配置周邊系統信息頁面封裝的信息*/
    private List<PeripheralCodeVO> peripheralCodeVOList;//周邊喜用數據字典對應的信息
	private List<PrpDSimpletreaty> prpDSimpletreatyList;//簡單合約分出對應的信息
	private int prpDSimpletreatyListLength;//簡單合約分出對應信息的數量
	private List<PrpDTreatyReten> prpDTreatyRetenList;//自留額計劃信息集合
	private int prpDTreatyRetenListLength;//自留額計劃信息的數量
	private PeripheralAccountInfoVO peripheralAccountInfoVO; //收付財務信息的VO
	private List<UtiPrintConfig> utiPrintConfigList = new ArrayList<UtiPrintConfig>(0);//打印參數配置的信息
	/** 流程控制中的流程代碼的集合*/
	private String processCodes;
	/**產品條款責任代碼的集合（用于在前臺判斷條款責任是否已經全部配置）*/
	private String productClauseKindSet;
	/**產品參考產品或者模板的代碼及名稱*/
	private String referenceRiskOrTemplateCode;
	private String referenceRiskOrTemplateCName;
	/** 用于在前臺判斷是更新還是保存 */
	private String pageEditType;
	/**********************產品復制變量定義結束****************/
	/**
	 * 產品復制頁面測試信息
	 * @return 返回一個字符串
	 */
	public String productCopyTest() {
        logger.debug("產品復制頁面測試信息");
        return SUCCESS;
    }
	

	/**
	 * 產品復制第3步--基礎信息的復制；展現頁面的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferenceRiskPageMSG(){
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		List<PrpDriskBlockVO> prpDriskBlockVOs = new ArrayList<PrpDriskBlockVO>(0);
		
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				prpDriskBlockVOs = (List<PrpDriskBlockVO>)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"pageCopy").get(0);				
			}else{
				/**產品配置服務的獲取*/
				String riskCode_reference = "PUB";
				RiskService riskService = (RiskService) ServiceFactory.getService("riskService");
				prpDriskBlockVOs = riskService.findNoReferenceMessage(riskCode_reference);
			}
            //產品配置異步調用的時候，時間類型的數據在反射的時候會出現格式問題，所以這里進行時間的格式化
			for(PrpDriskBlockVO vo:prpDriskBlockVOs){
				for(PrpDriskBlock block: vo.getPrpDriskBlocks()){
					if(block.getInvalidDate()!= null){
						block.setInvalidDate( new java.util.Date(block.getInvalidDate().getTime()) );
					}
					if(block.getValidDate()!= null){
						block.setValidDate( new java.util.Date(block.getValidDate().getTime()) );
					}
					//在向前臺寫數據的時候將主表的內容設置空的對象，防止造成死的循環
					block.setPrpDblock(null);
				}
			}
			
	    	this.writeJSONData(prpDriskBlockVOs,"blockClassify","prpDriskBlocks");
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	/**
	 * 產品復制第4步--基礎信息的復制；展現計算配置的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferenceRiskFormulaMSG(){
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		List<UtiFormulaInfoVO> utiFormulaInfoVOs = new ArrayList<UtiFormulaInfoVO>(0);
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				utiFormulaInfoVOs = (List<UtiFormulaInfoVO>)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"formulaCopy").get(0);				
			}
			
			//產品配置異步調用的時候，時間類型的數據在反射的時候會出現格式問題，所以這里進行時間的格式化
			for(UtiFormulaInfoVO vo:utiFormulaInfoVOs){
				UtiFormula utiFormula = vo.getUtiFormula();
				if(utiFormula.getInvalidDate()!= null){
					utiFormula.setInvalidDate( new java.util.Date(utiFormula.getInvalidDate().getTime()) );
				}
				if(utiFormula.getValidDate()!= null){
					utiFormula.setValidDate( new java.util.Date(utiFormula.getValidDate().getTime()) );
				}
			}
			
	    	this.writeJSONData(utiFormulaInfoVOs,"comCName","clauseCName","kindCName","utiFormula");
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	/**
	 * 產品復制第5步--業務規則信息的復制；展現業務規則-前臺業務規則的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferenceRiskRuleFrontMSG(){
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		List<UtiJSFunc> utiJSFuncs = new ArrayList<UtiJSFunc>(0);
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				utiJSFuncs = (List<UtiJSFunc>)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"frontRuleCopy").get(0);
			}
	    	this.writeJSONData(utiJSFuncs,"id.comCode","funcName","bizType","parameters","funcDesc","event",
	    			"content","funcLever","controlCode","modifyMode","getElementMode","validStatus","validDate","invalidDate","remark");
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	
	/**
	 * 產品復制第5步--業務規則信息的復制；展現業務規則-后臺業務規則的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferenceRiskRuleBackMSG(){
		List<UtiBackRuleConfig> UtiBackRuleConfigs = new ArrayList<UtiBackRuleConfig>(0);
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				UtiBackRuleConfigs = (List<UtiBackRuleConfig>)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"backRuleCopy").get(0);
			}
	    	this.writeJSONData(UtiBackRuleConfigs,"id.ruleCode","id.clauseCode","id.kindCode","id.comCode"
	    			,"id.bizType","ruleValue","validStatus","validDate","invalidDate","remark");
	    	 
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	
	/**
	 * 產品復制第5步--業務規則信息的復制；展現業務規則-產品復制標志的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferenceRiskRuleExtMSG(){
		/**組織擴展信息的集合的對象的list*/
		List<PrpDriskRelatedExtSetVO> prpDriskRelatedExtSetVOList = new ArrayList<PrpDriskRelatedExtSetVO>();
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"RiskConfigExtCopy").get(0);
				//參考模板獲取模板信息顯示到界面
				prpDriskRelatedExtSetVOList.add(prpDriskRelatedExtSetVO);
			//直接配置的時候的產品輔助標志位的重置	
			}else{
				PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"RiskConfigExtCopy").get(0);
				prpDriskRelatedExtSetVOList.add(prpDriskRelatedExtSetVO);	
			}
	    	this.writeJSONData(prpDriskRelatedExtSetVOList,"prpDriskExtVO","prpDriskClauseExtVOs","prpDriskClauseKindExtVOs","prpDrationExtVOs","prpDriskLimitVOs");
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	
	/**
	 * 產品復制第6步--周邊系統信息的復制；展現周邊系統-數據字典的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferencePeripheralCodeMSG(){
		/**組織擴展信息的集合的對象的list*/
		List<PeripheralCodeVO> peripheralCodeVOList = new ArrayList<PeripheralCodeVO>(0);
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				peripheralCodeVOList = (List<PeripheralCodeVO>)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"peripheralCodeCopy").get(0);
			}
	    	this.writeJSONData(peripheralCodeVOList,"codeType","codeTypeDesc","peripheralCodeSelectList","peripheralCodeCodeList");
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	
	/**
	 * 產品復制第6步--周邊系統信息的復制；展現周邊系統-打印參數配置的基礎信息；
	 * @return 返回一個字符串
	 */
	public String findReferencePeripheralPrintMSG(){
		/**組織擴展信息的集合的對象的list*/
		List<UtiPrintConfig> utiPrintConfigs = new ArrayList<UtiPrintConfig>(0);
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		try {
			if("risk".equals(this.getTestRiskOrTemplate()) || "riskTemplate".equals(this.getTestRiskOrTemplate())){
				//參考模板獲取模板信息顯示到界面
				utiPrintConfigs = (List<UtiPrintConfig>)productCopyService.findProductCopyReSetMSG(riskCodeConfig,riskCodeCopy,referenceRiskFlag,"printConfigCopy").get(0);
			}
	    	this.writeJSONData(utiPrintConfigs,"id.comCode","id.printType","printFlag","printName","bizType","validStatus","visaRelation");
	    	                                    
        } catch (Exception e) {
        	e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}
	/**
	 * 流程控制增刪改的處理方法；
	 * @return 返回一個字符串
	 */
	public String productCopyProcessSave(){
		UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();	
		logger.debug("保存 流程控制信息");
		String comCode = this.getRequest().getParameter("comCode");
		String riskCode = prpDrisk.getRiskCode();
		String processEditType = this.getRequest().getParameter("processEditType");
		//獲取選中的流程配置信息的流程配置代碼
		String processCodeList = this.getProcessCodes();
		if("add".equals(processEditType) || "update".equals(processEditType)){
			RiskService riskService = (RiskService) ServiceFactory.getService("riskService");
			//流程配置的保存操作
			riskService.productProcessSave(userCode,riskCode,comCode,processCodeList);
		}else{
			productCopyService.deleteProcessConfigBycomCode(riskCode, comCode);
		}
		return NONE;
	}
/*******************************************************************************************************************************************/
/*****************************************************產品配置組織頁面全部的對象，初始化的時候數據全部加載（上面是異步）********************************/
/*******************************************************************************************************************************************/
	/**
	 * 產品配置全部頁面數據的組織；
	 * @return 返回一個字符串
	 * @throws Exception
	 */
	public String findRiskConfigAllDate() throws Exception{
		
		List<Object> riskConfigAllDateList = new ArrayList<Object>(0); 
		/*String riskCodeConfigure = "CAC";
		String riskCodeCopy = "CBA";*/
		//去取配置的類型，配置的產品代碼，復制的產品/模板代碼
		String riskCodeConfig = getRequest().getParameter("riskCodeConfig");//配置產品的產品代碼
    	String riskCodeReference = getRequest().getParameter("riskCodeReference");//參考產品的產品代碼
        String riskOrTemplateCName = getRequest().getParameter("riskOrTemplateCName");
    	String referenceRiskOrTemplate = getRequest().getParameter("referenceRiskOrTemplate");//參考或者模板的標志：1，參考產品；2，參考模板；
    	//將頁面置成增加的頁面
    	this.setPageEditType(ProductConstants.PRODUCT_ADD);
    	//設置產品時參考產品還是引用模板的標志位
		if("1".equals(referenceRiskOrTemplate) || "2".equals(referenceRiskOrTemplate)){
			if("1".equals(referenceRiskOrTemplate)){
	        	this.setTestRiskOrTemplate("risk");
	        //2參考模板
	    	}else if("2".equals(referenceRiskOrTemplate)) {
	    		//設置為參考模板
	        	this.setTestRiskOrTemplate("riskTemplate");
	        //直接配置的操作	
	    	}
			//用于前臺顯示參考的產品或者模板的代碼及名稱
			this.setReferenceRiskOrTemplateCode(riskCodeReference);
			this.setReferenceRiskOrTemplateCName(riskOrTemplateCName);
			//參考產品或者模板的標志
			riskConfigAllDateList = productCopyService.findRiskConfigAllDate(riskCodeConfig, riskCodeReference,referenceRiskOrTemplate);
			prpDrisk = (PrpDrisk)riskConfigAllDateList.get(0);
			prpDriskClauselist = prpDrisk.getPrpDriskClauses();
			productProcessVOlist = (List<ProductProcessVO>)riskConfigAllDateList.get(1);
			comCodeList = (String)riskConfigAllDateList.get(2);
			prpDriskBlockVOList = (List<PrpDriskBlockVO>)riskConfigAllDateList.get(3);
			utiJSFuncList = (List<UtiJSFunc>)riskConfigAllDateList.get(4);
			utiBackRuleConfigList = (List<UtiBackRuleConfig>)riskConfigAllDateList.get(5);
			PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)riskConfigAllDateList.get(6);
			prpDriskExtVO = prpDriskRelatedExtSetVO.getPrpDriskExtVO();
			prpDriskClauseExtVOList = prpDriskRelatedExtSetVO.getPrpDriskClauseExtVOs();
			prpDriskClauseKindExtVOList = prpDriskRelatedExtSetVO.getPrpDriskClauseKindExtVOs();
			prpDrationExtVOList = prpDriskRelatedExtSetVO.getPrpDrationExtVOs();
			prpDriskLimitVOList = prpDriskRelatedExtSetVO.getPrpDriskLimitVOs();
			
			peripheralCodeVOList = (List<PeripheralCodeVO>)riskConfigAllDateList.get(7);
			prpDSimpletreatyList = (List<PrpDSimpletreaty>)riskConfigAllDateList.get(8);
			this.setPrpDSimpletreatyListLength(prpDSimpletreatyList.size());
			prpDTreatyRetenList = (List<PrpDTreatyReten>)riskConfigAllDateList.get(9);
			this.setPrpDTreatyRetenListLength(prpDTreatyRetenList.size());
			peripheralAccountInfoVO = (PeripheralAccountInfoVO)riskConfigAllDateList.get(10);
			utiPrintConfigList = (List<UtiPrintConfig>)riskConfigAllDateList.get(11);
			this.setProductClauseKindSet((String)riskConfigAllDateList.get(12));
			this.setTemplateCode((String)riskConfigAllDateList.get(13));
			utiFormulaInfoVOList = (List<UtiFormulaInfoVO>)riskConfigAllDateList.get(14);
		//直接配置模塊的操作	
		}else{
    		this.setTestRiskOrTemplate("notReference");
    		riskConfigAllDateList = productCopyService.findRiskConfigNotReferenceDate(riskCodeConfig);
    		prpDrisk = (PrpDrisk)riskConfigAllDateList.get(0);
    		prpDriskClauselist = prpDrisk.getPrpDriskClauses();
    		productProcessVOlist = (List<ProductProcessVO>)riskConfigAllDateList.get(1);
    		prpDriskBlockVOList = (List<PrpDriskBlockVO>)riskConfigAllDateList.get(2);
    		PrpDriskRelatedExtSetVO prpDriskRelatedExtSetVO = (PrpDriskRelatedExtSetVO)riskConfigAllDateList.get(3);
			prpDriskExtVO = prpDriskRelatedExtSetVO.getPrpDriskExtVO();
			prpDriskClauseExtVOList = prpDriskRelatedExtSetVO.getPrpDriskClauseExtVOs();
			prpDriskClauseKindExtVOList = prpDriskRelatedExtSetVO.getPrpDriskClauseKindExtVOs();
			prpDrationExtVOList = prpDriskRelatedExtSetVO.getPrpDrationExtVOs();
			prpDriskLimitVOList = prpDriskRelatedExtSetVO.getPrpDriskLimitVOs();
			this.setProductClauseKindSet((String)riskConfigAllDateList.get(4));
    		//產品輔助標志位的獲取在這里
    		prpDSimpletreatyList = (List<PrpDSimpletreaty>)riskConfigAllDateList.get(5);
			this.setPrpDSimpletreatyListLength(prpDSimpletreatyList.size());
			prpDTreatyRetenList = (List<PrpDTreatyReten>)riskConfigAllDateList.get(6);
			this.setPrpDTreatyRetenListLength(prpDTreatyRetenList.size());
			peripheralAccountInfoVO = (PeripheralAccountInfoVO)riskConfigAllDateList.get(7);
    	}
		return SUCCESS;
	}
	
	/********************產品前臺業務規則在復制的時候的保存的方法*****************/
	/**
	 * 前臺業務規則在復制的時候保存的方法
	 * @return 返回一個字符串
	 */
    public String addProductFrontRuleConfig(){
    	logger.debug("準備增加頁面要素信息");
    	UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();
		//獲取當前配置的產品進行保存
		String riskCode  = prpDrisk.getRiskCode();
		
		//保存前臺業務規則
		productCopyService.addProductFrontRuleConfig(utiJSFuncList, riskCode, userCode);
    	return NONE;
    }
    
    /**
     * 前臺業務規則在復制的時候更新的方法
     * @return 返回一個字符串
     */
    public String updateProductFrontRuleConfig(){
    	logger.debug("準備增加頁面要素信息");
    	UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();
		//獲取當前配置的產品進行保存
		String riskCode  = prpDrisk.getRiskCode();
		//保存前臺業務規則
		productCopyService.updateProductFrontRuleConfig(utiJSFuncList, riskCode, userCode);
    	return NONE;
    }
	/********************產品前臺業務規則在復制的時候的保存的方法*****************/
    /**
     * 前臺業務規則在復制的時候保存的方法
     * @return 返回一個字符串
     */
    public String addProductBackRuleConfig(){
    	logger.debug("準備增加頁面要素信息");
    	UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();
		//獲取當前配置的產品進行保存
		String riskCode  = prpDrisk.getRiskCode();
		
		//保存前臺業務規則
		productCopyService.addProductBackRuleConfig(utiBackRuleConfigList, riskCode, userCode);
    	return NONE;
    }
    
    /**
     * 前臺業務規則在復制的時候更新的方法
     * @return 返回一個字符串
     */
    public String updateProductBackRuleConfig(){
    	logger.debug("準備增加頁面要素信息");
    	UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();
		//獲取當前配置的產品進行保存
		String riskCode  = prpDrisk.getRiskCode();
		//保存前臺業務規則
		productCopyService.updateProductBackRuleConfig(utiBackRuleConfigList, riskCode, userCode);
    	return NONE;
    }
    
	/********************產品打印參數配置在復制的時候的保存的方法*****************/
    /**
     * 打印參數配置在復制的時候保存的方法
     * @return 返回一個字符串
     */
    public String addProductPrintConfig(){
    	logger.debug("準備增加頁面要素信息");
    	UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();
		//獲取當前配置的產品進行保存
		String riskCode  = prpDrisk.getRiskCode();
		String classCode = prpDrisk.getClassCode();
		//保存前臺業務規則
		productCopyService.addProductPrintConfig(utiPrintConfigList, riskCode, classCode,userCode);
    	return NONE;
    }
    
    /**
     * 打印參數配置在復制的時候更新的方法
     * @return 返回一個字符串
     */
    public String updateProductPrintConfig(){
    	logger.debug("準備增加頁面要素信息");
    	UserInfo userInfo = (UserInfo) this.getSession().getAttribute("userInfo"); 
		String userCode = userInfo.getUserCode();
		//獲取當前配置的產品進行保存
		String riskCode  = prpDrisk.getRiskCode();
		String classCode = prpDrisk.getClassCode();
		//保存前臺業務規則
		productCopyService.updateProductPrintConfig(utiPrintConfigList,riskCode,classCode,userCode);
    	return NONE;
    }
    /**
     * 產品復制進入計算配置頁面將頁面顯示的公式的因子保存成配置產品的因子
     * @return 返回一個字符串
     * @throws Exception
     */
	public String addProductCopyFormulaFactor() throws Exception{
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		productCopyService.addProductCopyFormulaFactor( riskCodeConfig, riskCodeCopy, referenceRiskFlag);
		return NONE;
	}
	/**
	 * 產品復制進入頁面配置的時候保存系數配置的信息
	 * @return 返回一個字符串
	 * @throws Exception
	 */
	public String saveProductCopyCoeffConfig() throws Exception{
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		String referenceRiskFlag = "";
		if("risk".equals(this.getTestRiskOrTemplate())){
			referenceRiskFlag = "1";
		}else{
			referenceRiskFlag = "2";
		}
		productCopyService.saveProductCopyCoeffConfig(riskCodeConfig, riskCodeCopy, referenceRiskFlag);
		return NONE;
	}
	/**
	 * 產品復制進入流程控制頁面的時候，將復制產品的流程控制代碼復制一份，保存成配置產品的
	 * @return 返回一個字符串
	 * @throws Exception
	 */
	public String addProductCopyProcessConfig() throws Exception{
		String riskCodeConfig = this.getRequest().getParameter("riskCodeConfig");
		String riskCodeCopy = this.getRequest().getParameter("riskCodeCopy");
		productCopyService.addProductCopyProcessConfig(riskCodeConfig, riskCodeCopy);
		return NONE;
	}
	/**
	 *  產品復制離開復制頁面計算配置沒有進行保存的情況下需要刪除原來保存的被配置產品的因子
	 * @return 返回一個字符串
	 */
	public String deleteProductCopyFormulaFactor(){
		String riskCodeConfig = this.getRequest().getParameter("riskCode");
		productCopyService.deleteProductCopyFormulaFactor(riskCodeConfig);
		return NONE;
	}
	/**
	 * 產品復制離開復制頁面頁面配置沒有進行保存的情況下需要刪除原來保存的系數的相關的信息
	 * @return 返回一個字符串
	 */
	public String deleteProductCopyCoeffConfig(){
		String riskCodeConfig = this.getRequest().getParameter("riskCode");
		productCopyService.deleteProductCopyCoeffConfig(riskCodeConfig);
		return NONE;
	}
	/**
	 * 產品配置完成的時候刪除產品模板的對應的關系（創新數據第一次過來的時候，默認保存的產品模板的數據）  
	 * @return 返回一個字符串
	 */
	public String productCopyFinishOperate(){
		String riskCodeConfig = this.getRequest().getParameter("riskCode");
		productCopyService.productCopyFinishOperate(riskCodeConfig);
		return NONE;
	}
	/**
	 *  前臺業務規則事件的onchange方法
	 * @return 返回一個字符串
	 */
	public String productCopyFrontEventChange(){
		
		try {
			String frontEventSet = this.getRequest().getParameter("frontEventSet");
			//根據產品代碼查詢該產品下的流程配置信息
			String frontEventSetMSG = productCopyService.productCopyFrontEventChange(frontEventSet);
            this.writeJSONMsg(frontEventSetMSG);
        } catch (Exception e) {
            this.writeJSONMsg(e.getMessage());
        }
		return NONE;
	}


	/**
	 * 變量productCopyService的getter方法
	 * @return productCopyService
	 */
	
	public ProductCopyService getProductCopyService() {
		return productCopyService;
	}


	/**
	 * 變量productCopyService的setter方法
	 * @param productCopyService 參數 productCopyService
	 */
	public void setProductCopyService(ProductCopyService productCopyService) {
		this.productCopyService = productCopyService;
	}


	/**
	 * 變量prpDrisk的getter方法
	 * @return prpDrisk
	 */
	
	public PrpDrisk getPrpDrisk() {
		return prpDrisk;
	}


	/**
	 * 變量prpDrisk的setter方法
	 * @param prpDrisk 參數 prpDrisk
	 */
	public void setPrpDrisk(PrpDrisk prpDrisk) {
		this.prpDrisk = prpDrisk;
	}


	/**
	 * 變量prpDriskClauselist的getter方法
	 * @return prpDriskClauselist
	 */
	
	public List<PrpDriskClause> getPrpDriskClauselist() {
		return prpDriskClauselist;
	}


	/**
	 * 變量prpDriskClauselist的setter方法
	 * @param prpDriskClauselist 參數 prpDriskClauselist
	 */
	public void setPrpDriskClauselist(List<PrpDriskClause> prpDriskClauselist) {
		this.prpDriskClauselist = prpDriskClauselist;
	}


	/**
	 * 變量productProcessVOlist的getter方法
	 * @return productProcessVOlist
	 */
	
	public List<ProductProcessVO> getProductProcessVOlist() {
		return productProcessVOlist;
	}


	/**
	 * 變量productProcessVOlist的setter方法
	 * @param productProcessVOlist 參數 productProcessVOlist
	 */
	public void setProductProcessVOlist(List<ProductProcessVO> productProcessVOlist) {
		this.productProcessVOlist = productProcessVOlist;
	}


	/**
	 * 變量comCodeList的getter方法
	 * @return comCodeList
	 */
	
	public String getComCodeList() {
		return comCodeList;
	}


	/**
	 * 變量comCodeList的setter方法
	 * @param comCodeList 參數 comCodeList
	 */
	public void setComCodeList(String comCodeList) {
		this.comCodeList = comCodeList;
	}


	/**
	 * 變量prpDriskBlockVOList的getter方法
	 * @return prpDriskBlockVOList
	 */
	
	public List<PrpDriskBlockVO> getPrpDriskBlockVOList() {
		return prpDriskBlockVOList;
	}


	/**
	 * 變量prpDriskBlockVOList的setter方法
	 * @param prpDriskBlockVOList 參數 prpDriskBlockVOList
	 */
	public void setPrpDriskBlockVOList(List<PrpDriskBlockVO> prpDriskBlockVOList) {
		this.prpDriskBlockVOList = prpDriskBlockVOList;
	}


	/**
	 * 變量templateCode的getter方法
	 * @return templateCode
	 */
	
	public String getTemplateCode() {
		return templateCode;
	}


	/**
	 * 變量templateCode的setter方法
	 * @param templateCode 參數 templateCode
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}


	/**
	 * 變量testRiskOrTemplate的getter方法
	 * @return testRiskOrTemplate
	 */
	
	public String getTestRiskOrTemplate() {
		return testRiskOrTemplate;
	}


	/**
	 * 變量testRiskOrTemplate的setter方法
	 * @param testRiskOrTemplate 參數 testRiskOrTemplate
	 */
	public void setTestRiskOrTemplate(String testRiskOrTemplate) {
		this.testRiskOrTemplate = testRiskOrTemplate;
	}


	/**
	 * 變量utiFormulaInfoVOList的getter方法
	 * @return utiFormulaInfoVOList
	 */
	
	public List<UtiFormulaInfoVO> getUtiFormulaInfoVOList() {
		return utiFormulaInfoVOList;
	}


	/**
	 * 變量utiFormulaInfoVOList的setter方法
	 * @param utiFormulaInfoVOList 參數 utiFormulaInfoVOList
	 */
	public void setUtiFormulaInfoVOList(List<UtiFormulaInfoVO> utiFormulaInfoVOList) {
		this.utiFormulaInfoVOList = utiFormulaInfoVOList;
	}


	/**
	 * 變量utiJSFuncList的getter方法
	 * @return utiJSFuncList
	 */
	
	public List<UtiJSFunc> getUtiJSFuncList() {
		return utiJSFuncList;
	}


	/**
	 * 變量utiJSFuncList的setter方法
	 * @param utiJSFuncList 參數 utiJSFuncList
	 */
	public void setUtiJSFuncList(List<UtiJSFunc> utiJSFuncList) {
		this.utiJSFuncList = utiJSFuncList;
	}


	/**
	 * 變量utiBackRuleConfigList的getter方法
	 * @return utiBackRuleConfigList
	 */
	
	public List<UtiBackRuleConfig> getUtiBackRuleConfigList() {
		return utiBackRuleConfigList;
	}


	/**
	 * 變量utiBackRuleConfigList的setter方法
	 * @param utiBackRuleConfigList 參數 utiBackRuleConfigList
	 */
	public void setUtiBackRuleConfigList(
			List<UtiBackRuleConfig> utiBackRuleConfigList) {
		this.utiBackRuleConfigList = utiBackRuleConfigList;
	}


	/**
	 * 變量prpDriskExtVO的getter方法
	 * @return prpDriskExtVO
	 */
	
	public PrpDriskExtVO getPrpDriskExtVO() {
		return prpDriskExtVO;
	}


	/**
	 * 變量prpDriskExtVO的setter方法
	 * @param prpDriskExtVO 參數 prpDriskExtVO
	 */
	public void setPrpDriskExtVO(PrpDriskExtVO prpDriskExtVO) {
		this.prpDriskExtVO = prpDriskExtVO;
	}


	/**
	 * 變量prpDriskClauseExtVOList的getter方法
	 * @return prpDriskClauseExtVOList
	 */
	
	public List<PrpDriskClauseExtVO> getPrpDriskClauseExtVOList() {
		return prpDriskClauseExtVOList;
	}


	/**
	 * 變量prpDriskClauseExtVOList的setter方法
	 * @param prpDriskClauseExtVOList 參數 prpDriskClauseExtVOList
	 */
	public void setPrpDriskClauseExtVOList(
			List<PrpDriskClauseExtVO> prpDriskClauseExtVOList) {
		this.prpDriskClauseExtVOList = prpDriskClauseExtVOList;
	}


	/**
	 * 變量prpDriskClauseKindExtVOList的getter方法
	 * @return prpDriskClauseKindExtVOList
	 */
	
	public List<PrpDriskClauseKindExtVO> getPrpDriskClauseKindExtVOList() {
		return prpDriskClauseKindExtVOList;
	}


	/**
	 * 變量prpDriskClauseKindExtVOList的setter方法
	 * @param prpDriskClauseKindExtVOList 參數 prpDriskClauseKindExtVOList
	 */
	public void setPrpDriskClauseKindExtVOList(
			List<PrpDriskClauseKindExtVO> prpDriskClauseKindExtVOList) {
		this.prpDriskClauseKindExtVOList = prpDriskClauseKindExtVOList;
	}


	/**
	 * 變量prpDrationExtVOList的getter方法
	 * @return prpDrationExtVOList
	 */
	
	public List<PrpDrationExtVO> getPrpDrationExtVOList() {
		return prpDrationExtVOList;
	}


	/**
	 * 變量prpDrationExtVOList的setter方法
	 * @param prpDrationExtVOList 參數 prpDrationExtVOList
	 */
	public void setPrpDrationExtVOList(List<PrpDrationExtVO> prpDrationExtVOList) {
		this.prpDrationExtVOList = prpDrationExtVOList;
	}


	/**
	 * 變量prpDriskLimitVOList的getter方法
	 * @return prpDriskLimitVOList
	 */
	
	public List<PrpDriskLimitVO> getPrpDriskLimitVOList() {
		return prpDriskLimitVOList;
	}


	/**
	 * 變量prpDriskLimitVOList的setter方法
	 * @param prpDriskLimitVOList 參數 prpDriskLimitVOList
	 */
	public void setPrpDriskLimitVOList(List<PrpDriskLimitVO> prpDriskLimitVOList) {
		this.prpDriskLimitVOList = prpDriskLimitVOList;
	}


	/**
	 * 變量peripheralCodeVOList的getter方法
	 * @return peripheralCodeVOList
	 */
	
	public List<PeripheralCodeVO> getPeripheralCodeVOList() {
		return peripheralCodeVOList;
	}


	/**
	 * 變量peripheralCodeVOList的setter方法
	 * @param peripheralCodeVOList 參數 peripheralCodeVOList
	 */
	public void setPeripheralCodeVOList(List<PeripheralCodeVO> peripheralCodeVOList) {
		this.peripheralCodeVOList = peripheralCodeVOList;
	}


	/**
	 * 變量prpDSimpletreatyList的getter方法
	 * @return prpDSimpletreatyList
	 */
	
	public List<PrpDSimpletreaty> getPrpDSimpletreatyList() {
		return prpDSimpletreatyList;
	}


	/**
	 * 變量prpDSimpletreatyList的setter方法
	 * @param prpDSimpletreatyList 參數 prpDSimpletreatyList
	 */
	public void setPrpDSimpletreatyList(List<PrpDSimpletreaty> prpDSimpletreatyList) {
		this.prpDSimpletreatyList = prpDSimpletreatyList;
	}


	/**
	 * 變量prpDSimpletreatyListLength的getter方法
	 * @return prpDSimpletreatyListLength
	 */
	
	public int getPrpDSimpletreatyListLength() {
		return prpDSimpletreatyListLength;
	}


	/**
	 * 變量prpDSimpletreatyListLength的setter方法
	 * @param prpDSimpletreatyListLength 參數 prpDSimpletreatyListLength
	 */
	public void setPrpDSimpletreatyListLength(int prpDSimpletreatyListLength) {
		this.prpDSimpletreatyListLength = prpDSimpletreatyListLength;
	}


	/**
	 * 變量prpDTreatyRetenList的getter方法
	 * @return prpDTreatyRetenList
	 */
	
	public List<PrpDTreatyReten> getPrpDTreatyRetenList() {
		return prpDTreatyRetenList;
	}


	/**
	 * 變量prpDTreatyRetenList的setter方法
	 * @param prpDTreatyRetenList 參數 prpDTreatyRetenList
	 */
	public void setPrpDTreatyRetenList(List<PrpDTreatyReten> prpDTreatyRetenList) {
		this.prpDTreatyRetenList = prpDTreatyRetenList;
	}


	/**
	 * 變量prpDTreatyRetenListLength的getter方法
	 * @return prpDTreatyRetenListLength
	 */
	
	public int getPrpDTreatyRetenListLength() {
		return prpDTreatyRetenListLength;
	}


	/**
	 * 變量prpDTreatyRetenListLength的setter方法
	 * @param prpDTreatyRetenListLength 參數 prpDTreatyRetenListLength
	 */
	public void setPrpDTreatyRetenListLength(int prpDTreatyRetenListLength) {
		this.prpDTreatyRetenListLength = prpDTreatyRetenListLength;
	}


	/**
	 * 變量peripheralAccountInfoVO的getter方法
	 * @return peripheralAccountInfoVO
	 */
	
	public PeripheralAccountInfoVO getPeripheralAccountInfoVO() {
		return peripheralAccountInfoVO;
	}


	/**
	 * 變量peripheralAccountInfoVO的setter方法
	 * @param peripheralAccountInfoVO 參數 peripheralAccountInfoVO
	 */
	public void setPeripheralAccountInfoVO(
			PeripheralAccountInfoVO peripheralAccountInfoVO) {
		this.peripheralAccountInfoVO = peripheralAccountInfoVO;
	}


	/**
	 * 變量utiPrintConfigList的getter方法
	 * @return utiPrintConfigList
	 */
	
	public List<UtiPrintConfig> getUtiPrintConfigList() {
		return utiPrintConfigList;
	}


	/**
	 * 變量utiPrintConfigList的setter方法
	 * @param utiPrintConfigList 參數 utiPrintConfigList
	 */
	public void setUtiPrintConfigList(List<UtiPrintConfig> utiPrintConfigList) {
		this.utiPrintConfigList = utiPrintConfigList;
	}


	/**
	 * 變量processCodes的getter方法
	 * @return processCodes
	 */
	
	public String getProcessCodes() {
		return processCodes;
	}


	/**
	 * 變量processCodes的setter方法
	 * @param processCodes 參數 processCodes
	 */
	public void setProcessCodes(String processCodes) {
		this.processCodes = processCodes;
	}


	/**
	 * 變量productClauseKindSet的getter方法
	 * @return productClauseKindSet
	 */
	
	public String getProductClauseKindSet() {
		return productClauseKindSet;
	}


	/**
	 * 變量productClauseKindSet的setter方法
	 * @param productClauseKindSet 參數 productClauseKindSet
	 */
	public void setProductClauseKindSet(String productClauseKindSet) {
		this.productClauseKindSet = productClauseKindSet;
	}


	/**
	 * 變量referenceRiskOrTemplateCode的getter方法
	 * @return referenceRiskOrTemplateCode
	 */
	
	public String getReferenceRiskOrTemplateCode() {
		return referenceRiskOrTemplateCode;
	}


	/**
	 * 變量referenceRiskOrTemplateCode的setter方法
	 * @param referenceRiskOrTemplateCode 參數 referenceRiskOrTemplateCode
	 */
	public void setReferenceRiskOrTemplateCode(String referenceRiskOrTemplateCode) {
		this.referenceRiskOrTemplateCode = referenceRiskOrTemplateCode;
	}


	/**
	 * 變量referenceRiskOrTemplateCName的getter方法
	 * @return referenceRiskOrTemplateCName
	 */
	
	public String getReferenceRiskOrTemplateCName() {
		return referenceRiskOrTemplateCName;
	}


	/**
	 * 變量referenceRiskOrTemplateCName的setter方法
	 * @param referenceRiskOrTemplateCName 參數 referenceRiskOrTemplateCName
	 */
	public void setReferenceRiskOrTemplateCName(String referenceRiskOrTemplateCName) {
		this.referenceRiskOrTemplateCName = referenceRiskOrTemplateCName;
	}


	/**
	 * 變量pageEditType的getter方法
	 * @return pageEditType
	 */
	
	public String getPageEditType() {
		return pageEditType;
	}


	/**
	 * 變量pageEditType的setter方法
	 * @param pageEditType 參數 pageEditType
	 */
	public void setPageEditType(String pageEditType) {
		this.pageEditType = pageEditType;
	}
	

}