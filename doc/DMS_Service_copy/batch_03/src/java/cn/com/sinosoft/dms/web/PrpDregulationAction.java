package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.model.PrpdBasicMedical;
import cn.com.sinosoft.dms.model.PrpdInjuryDefine;
import cn.com.sinosoft.dms.model.PrpdInjuryDuty;
import cn.com.sinosoft.dms.model.PrpdInjuryRate;
import cn.com.sinosoft.dms.model.PrpdRegulation;
import cn.com.sinosoft.dms.model.PrpdbpmMain;
import cn.com.sinosoft.dms.service.facade.PrpDbpmMainService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDregulationService;

public class PrpDregulationAction extends Struts2Action{
	
	private static final long serialVersionUID = 1L;
	private String comments;
	private String regulationCodes;
	private String regulationCode;
	private String regulationType;
	private PrpdRegulation prpdRegulation;
	private String ParentCode;
	private PrpDregulationService prpDregulationService;
	private PrpDcodeService prpDcodeService;
	//add by duanfa2011015
	private PrpDbpmMainService prpDbpmMainService;
	private String proinvceResult;
	private String cityResult;
	private String countyResult;
	private List<PrpdInjuryDefine> prpdInjuryDefines = new ArrayList<PrpdInjuryDefine>(0);
	private List<PrpdInjuryDuty> prpdInjuryDuties = new  ArrayList<PrpdInjuryDuty>(0); 
	private List<PrpdInjuryRate> prpdInjuryRates = new ArrayList<PrpdInjuryRate>(0);
	private List<PrpdBasicMedical> prpdBasicMedicals = new  ArrayList<PrpdBasicMedical>(0);
	private List<String> strings = new ArrayList<String>(0);
	private String editType;
	//选择省或市后查询下一级的下拉框
	public void getAreaCode(){
		try {
			List<PrpDnewCode> provinceCodes = prpDcodeService.getSubCode("AreaCode",ParentCode);
			StringBuffer AreaCode = new StringBuffer("");
			for(PrpDnewCode code:provinceCodes){
				AreaCode.append("<option value='");
				AreaCode.append(code.getNewCodeCode());
				AreaCode.append("'>");
				AreaCode.append(code.getCodeCName());
				AreaCode.append("</option>");
			}
			try {
				HttpServletResponse response = this.getResponse();
				response.setContentType("text/html; charset=utf-8"); 
				PrintWriter out = response.getWriter();
				out.write(AreaCode.toString());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//初始化选择省的下拉框
	public String prepareQueryPrpDregulation(){
		List<PrpDnewCode> provinceCodes = prpDcodeService.getSubCode("AreaCode", "000000");
		StringBuffer proinvceSB = new StringBuffer("<option value=''>选择省</option>");
		for(PrpDnewCode code:provinceCodes){
			proinvceSB.append("<option value='");
			proinvceSB.append(code.getNewCodeCode());
			proinvceSB.append("'>");
			proinvceSB.append(code.getCodeCName());
			proinvceSB.append("</option>");
		}
		proinvceResult = proinvceSB.toString();
		return SUCCESS;
	}
	//初始化选择省的下拉框,跳转到审核页面//add by duanfa 2011-06-16
	public String prepareCheckPrpDregulation(){
		List<PrpDnewCode> provinceCodes = prpDcodeService.getSubCode("AreaCode", "000000");
		StringBuffer proinvceSB = new StringBuffer("<option value=''>选择省</option>");
		for(PrpDnewCode code:provinceCodes){
			proinvceSB.append("<option value='");
			proinvceSB.append(code.getNewCodeCode());
			proinvceSB.append("'>");
			proinvceSB.append(code.getCodeCName());
			proinvceSB.append("</option>");
		}
		proinvceResult = proinvceSB.toString();
		return SUCCESS;
	}
	//审核通过//add by duanfa 2011-06-16
	public String checkPassRegulation(){
		if(regulationCode!=null&&!regulationCode.equals("")){
			String[] regulationCodes = regulationCode.split(",");
			prpDregulationService.checkPassAll(regulationCodes, comments);
		}
		return SUCCESS;
	}
	//审核退回//add by duanfa 2011-06-16
	public String checkRejectRegulation(){
		if(regulationCode!=null&&!regulationCode.equals("")){
			String[] regulationCodes = regulationCode.split(",");
			prpDregulationService.checkRejectAll(regulationCodes, comments);
		}
		return SUCCESS;
	}
	public String prepareInsertPrpDregulation(){
		List<PrpDnewCode> provinceCodes = prpDcodeService.getSubCode("AreaCode", "000000");
		StringBuffer proinvceSB = new StringBuffer("<option value=''>选择省</option>");
		for(PrpDnewCode code:provinceCodes){
			proinvceSB.append("<option value='");
			proinvceSB.append(code.getNewCodeCode());
			proinvceSB.append("'>");
			proinvceSB.append(code.getCodeCName());
			proinvceSB.append("</option>");
		}
		proinvceResult = proinvceSB.toString();
		if(prpdRegulation.getRegulationType().equals("I")){
			return "I";
		}else{
			return "B";
		}
	}
	public String queryPrpDregulation(){
		try {
		    Page page = prpDregulationService.PrpDregulationList(prpdRegulation,this.pageNo,this.pageSize);
		    List<PrpdRegulation> regulations = page.getResult();
			//将省市县的code全部转化为中文，做显示用
			for(PrpdRegulation regulation :regulations){
					PrpDnewCodeId prpDnewCodeId = new PrpDnewCodeId();
					prpDnewCodeId.setCodeType("AreaCode");
					
					prpDnewCodeId.setCodeCode(regulation.getProviceCode());
					PrpDnewCode prpDnewCode = prpDcodeService.findByPrimaryKey(prpDnewCodeId);
					if(prpDnewCode!=null){
						regulation.setProviceCode(prpDnewCode.getCodeCName());
					}
					prpDnewCodeId.setCodeCode(regulation.getCityCode());
					prpDnewCode = prpDcodeService.findByPrimaryKey(prpDnewCodeId);
					if(prpDnewCode!=null){
						regulation.setCityCode(prpDcodeService.findByPrimaryKey(prpDnewCodeId).getCodeCName());
					}
					prpDnewCodeId.setCodeCode(regulation.getCountyCode());
					prpDnewCode = prpDcodeService.findByPrimaryKey(prpDnewCodeId);
					if(prpDnewCode!=null){
						regulation.setCountyCode(prpDcodeService.findByPrimaryKey(prpDnewCodeId).getCodeCName());
					}
			}
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    //modify by duanfa 20110921 start 添加影像路径
		    //this.writeJSONData(page,"regulationCode","fileName","auditFlag","fileCode","proviceCode","countyCode","cityCode","validDate","validStatus");
		    this.writeJSONData(page,"regulationCode","fileName","auditFlag","fileCode","proviceCode","countyCode","cityCode","validDate","validStatus","imagePath");
		    //modify by duanfa 20110921 end
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		return null;
		
	}
	public String queryCheckPrpDregulation(){
		try {
			Page page = prpDregulationService.checkPrpDregulationList(prpdRegulation,this.pageNo,this.pageSize);
			List<PrpdRegulation> regulations = page.getResult();
			//将省市县的code全部转化为中文，做显示用
			for(PrpdRegulation regulation :regulations){
				PrpDnewCodeId prpDnewCodeId = new PrpDnewCodeId();
				prpDnewCodeId.setCodeType("AreaCode");
				
				prpDnewCodeId.setCodeCode(regulation.getProviceCode());
				PrpDnewCode prpDnewCode = prpDcodeService.findByPrimaryKey(prpDnewCodeId);
				if(prpDnewCode!=null){
					regulation.setProviceCode(prpDnewCode.getCodeCName());
				}
				prpDnewCodeId.setCodeCode(regulation.getCityCode());
				prpDnewCode = prpDcodeService.findByPrimaryKey(prpDnewCodeId);
				if(prpDnewCode!=null){
					regulation.setCityCode(prpDcodeService.findByPrimaryKey(prpDnewCodeId).getCodeCName());
				}
				prpDnewCodeId.setCodeCode(regulation.getCountyCode());
				prpDnewCode = prpDcodeService.findByPrimaryKey(prpDnewCodeId);
				if(prpDnewCode!=null){
					regulation.setCountyCode(prpDcodeService.findByPrimaryKey(prpDnewCodeId).getCodeCName());
				}
				//modfiy by duanfa20110815 start
				PrpdbpmMain bpmMain = prpDbpmMainService.findByPropertyName("taskId", regulation.getRegulationCode());
				regulation.setAuditFlag(bpmMain.getBusinessState());
			}
			logger.debug("【查询结果数：" + page.getTotalCount() + "】");
			//modfiy by duanfa20110921
			this.writeJSONData(page,"regulationCode","fileName","fileCode","proviceCode","countyCode","cityCode","validDate","auditFlag","validStatus","imagePath");
			logger.debug("【writeJSONData over】");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
		
	}
	
	public String insertPrpDregulation() {
		
		String usercode = (String)getSession().getAttribute("UserCode");
		if(editType.equals("insert")){
			prpDregulationService.insertPrpdRegulation(usercode,prpdRegulation,prpdInjuryDefines,prpdInjuryRates,prpdInjuryDuties,prpdBasicMedicals);
		}else{
			//prpDregulationService.deletePrpdRegulation(prpdRegulation.getRegulationCode());
			prpDregulationService.updatePrpdRegulation(usercode,prpdRegulation,prpdInjuryDefines,prpdInjuryRates,prpdInjuryDuties,prpdBasicMedicals);
		}
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	public String checkPrpDregulation() throws Exception{
		prpdRegulation = prpDregulationService.findByPrimaryKey(prpdRegulation.getRegulationCode());
		 
		 List<PrpDnewCode> provinceCodes = prpDcodeService.getSubCode("AreaCode", "000000");
			StringBuffer proinvceSB = new StringBuffer("<option value=''>请选择</option>");
			for(PrpDnewCode code:provinceCodes){
				proinvceSB.append("<option value='");
				proinvceSB.append(code.getNewCodeCode());
				if(code.getNewCodeCode().equals(prpdRegulation.getProviceCode())){
					proinvceSB.append("' selected='selected");
				}
				proinvceSB.append("'>");
				proinvceSB.append(code.getCodeCName());
				proinvceSB.append("</option>");
			}
			proinvceResult = proinvceSB.toString();
			
			List<PrpDnewCode> cityCodes = prpDcodeService.getSubCode("AreaCode",prpdRegulation.getProviceCode());
			StringBuffer citySB = new StringBuffer("");
			for(PrpDnewCode code:cityCodes){
				citySB.append("<option value='");
				citySB.append(code.getNewCodeCode());
				if(code.getNewCodeCode().equals(prpdRegulation.getCityCode())){
					citySB.append("' selected='selected");
				}
				citySB.append("'>");
				citySB.append(code.getCodeCName());
				citySB.append("</option>");
			}
			cityResult = citySB.toString();
			
			List<PrpDnewCode> countyCodes = prpDcodeService.getSubCode("AreaCode",prpdRegulation.getCityCode());
			StringBuffer countySB = new StringBuffer("");
			for(PrpDnewCode code:countyCodes){
				countySB.append("<option value='");
				countySB.append(code.getNewCodeCode());
				if(code.getNewCodeCode().equals(prpdRegulation.getCountyCode())){
					countySB.append("' selected='selected");
				}
				countySB.append("'>");
				countySB.append(code.getCodeCName());
				countySB.append("</option>");
			}
			countyResult = countySB.toString();
	        if(prpdRegulation.getRegulationType().equals("I")){
				return "I";
			}else{
				return "B";
			}
	}
	 public String prepareUpdatePrpDregulation() throws Exception{
		 prpdRegulation = prpDregulationService.findByPrimaryKey(prpdRegulation.getRegulationCode());
		 
		 List<PrpDnewCode> provinceCodes = prpDcodeService.getSubCode("AreaCode", "000000");
			StringBuffer proinvceSB = new StringBuffer("<option value=''>请选择</option>");
			for(PrpDnewCode code:provinceCodes){
				proinvceSB.append("<option value='");
				proinvceSB.append(code.getNewCodeCode());
				if(code.getNewCodeCode().equals(prpdRegulation.getProviceCode())){
					proinvceSB.append("' selected='selected");
				}
				proinvceSB.append("'>");
				proinvceSB.append(code.getCodeCName());
				proinvceSB.append("</option>");
			}
			proinvceResult = proinvceSB.toString();
			
			List<PrpDnewCode> cityCodes = prpDcodeService.getSubCode("AreaCode",prpdRegulation.getProviceCode());
			StringBuffer citySB = new StringBuffer("");
			for(PrpDnewCode code:cityCodes){
				citySB.append("<option value='");
				citySB.append(code.getNewCodeCode());
				if(code.getNewCodeCode().equals(prpdRegulation.getCityCode())){
					citySB.append("' selected='selected");
				}
				citySB.append("'>");
				citySB.append(code.getCodeCName());
				citySB.append("</option>");
			}
			cityResult = citySB.toString();
			
			List<PrpDnewCode> countyCodes = prpDcodeService.getSubCode("AreaCode",prpdRegulation.getCityCode());
			StringBuffer countySB = new StringBuffer("");
			for(PrpDnewCode code:countyCodes){
				countySB.append("<option value='");
				countySB.append(code.getNewCodeCode());
				if(code.getNewCodeCode().equals(prpdRegulation.getCountyCode())){
					countySB.append("' selected='selected");
				}
				countySB.append("'>");
				countySB.append(code.getCodeCName());
				countySB.append("</option>");
			}
			countyResult = countySB.toString();
	        if(prpdRegulation.getRegulationType().equals("I")){
				return "I";
			}else{
				return "B";
			}
	  }
	public void changeRegulationStatus(){
		//modify by duanfa20110915
		String usercode = (String)getSession().getAttribute("UserCode");
		prpDregulationService.changeRegulationStatus(usercode,regulationCode);
	}
	public String getRegulationType() {
		return regulationType;
	}
	public void setRegulationType(String regulationType) {
		this.regulationType = regulationType;
	}
	public PrpdRegulation getPrpdRegulation() {
		return prpdRegulation;
	}
	public void setPrpdRegulation(PrpdRegulation prpdRegulation) {
		this.prpdRegulation = prpdRegulation;
	}
	public void setPrpDregulationService(PrpDregulationService prpDregulationService) {
		this.prpDregulationService = prpDregulationService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProinvceResult() {
		return proinvceResult;
	}
	public void setProinvceResult(String proinvceResult) {
		this.proinvceResult = proinvceResult;
	}
	public void setParentCode(String parentCode) {
		ParentCode = parentCode;
	}
	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}
	public void setPrpdInjuryDuties(List<PrpdInjuryDuty> prpdInjuryDuties) {
		this.prpdInjuryDuties = prpdInjuryDuties;
	}
	public void setPrpdBasicMedicals(List<PrpdBasicMedical> prpdBasicMedicals) {
		this.prpdBasicMedicals = prpdBasicMedicals;
	}
	public String getParentCode() {
		return ParentCode;
	}
	public PrpDregulationService getPrpDregulationService() {
		return prpDregulationService;
	}
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}
	public List<PrpdInjuryDuty> getPrpdInjuryDuties() {
		return prpdInjuryDuties;
	}
	public List<PrpdBasicMedical> getPrpdBasicMedicals() {
		return prpdBasicMedicals;
	}
	public List<String> getStrings() {
		return strings;
	}
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
	public List<PrpdInjuryDefine> getPrpdInjuryDefines() {
		return prpdInjuryDefines;
	}
	public void setPrpdInjuryDefines(List<PrpdInjuryDefine> prpdInjuryDefines) {
		this.prpdInjuryDefines = prpdInjuryDefines;
	}
	public String getCityResult() {
		return cityResult;
	}
	public void setCityResult(String cityResult) {
		this.cityResult = cityResult;
	}
	public String getCountyResult() {
		return countyResult;
	}
	public void setCountyResult(String countyResult) {
		this.countyResult = countyResult;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getRegulationCode() {
		return regulationCode;
	}
	public void setRegulationCode(String regulationCode) {
		this.regulationCode = regulationCode;
	}
	public List<PrpdInjuryRate> getPrpdInjuryRates() {
		return prpdInjuryRates;
	}
	public void setPrpdInjuryRates(List<PrpdInjuryRate> prpdInjuryRates) {
		this.prpdInjuryRates = prpdInjuryRates;
	}
	public String getRegulationCodes() {
		return regulationCodes;
	}
	public void setRegulationCodes(String regulationCodes) {
		this.regulationCodes = regulationCodes;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public PrpDbpmMainService getPrpDbpmMainService() {
		return prpDbpmMainService;
	}
	public void setPrpDbpmMainService(PrpDbpmMainService prpDbpmMainService) {
		this.prpDbpmMainService = prpDbpmMainService;
	}

}
