package cn.com.sinosoft.common.model;

import java.io.Serializable;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDRCKRateLower;
import cn.com.sinosoft.dms.model.PrpDaccountInfo;
import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDarea;
import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.model.PrpDcodeRisk;
import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDnewCodeRisk;
import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.model.PrpDproject;
import cn.com.sinosoft.dms.model.PrpDreinsurer;
import cn.com.sinosoft.dms.model.PrpDresource;
import cn.com.sinosoft.dms.model.PrpDriskClause;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskShortRate;
import cn.com.sinosoft.dms.model.PrpDsettlementByr;
import cn.com.sinosoft.dms.model.PrpDsettlementLkr;
import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;

import com.sinosoft.dmsdriver.model.PrpDrisk;


public class InputBean implements Serializable{
	private static final long serialVersionUID = 1L;
	// 同步请求标识
	private String requestFlag;
	// 是否进行同步标识
	private boolean isSync;
	// 使用JMS配置文件
	private String usedJmsConfig;
	// 源机构
	private String sourComCode;
	// 目的机构
	private String destComCode;// 如果为ALL，则全部接受，不是则判断分公司的机构是否包含在这个字段中，包含则处理，不包含不处理
	// 同步目的地的IP
	private String svrIp;
	// 同步目的地的端口号
	private String svrPort;
	// 同步目的地的服务代码
	private String svrCode;

	// 执行同步的操作人
	private String operUserCode;
	// 执行同步的操作人归属机构
	private String operComCode;
	// 同步目的地的账户属性定义表
	// private UtiIAccAtrrDefine utiIAccAtrrDefine;
	
	// 同步prpdType
	private PrpDtype prpDtype;
	
	//同步prpdCode
	private PrpDnewCode prpDcode;
		
	// 同步机构 prpdcompany
	private PrpDcompany prpDcompany;
	
	//同步兑换率 prpdexch
	private PrpDexch prpDexch;
	
	// 同步金融机构prpdbank
	private PrpDbank prpDbank;
	
	// 同步代理人prpdagent
	private PrpDagent prpDagent;
	
	private PrpDagentAll prpDagentAll;
	
	private PrpDdealer prpDdealer;
	
	private PrpDplane prpDplane;
	
	private PrpDport prpDport;
	
	private PrpDship prpDship;
	
	//同步自留额计划
	private PrpDtreatyReten prpDtreatyReten;
	
	//同步国管局项目PICC联系人
	private PrpDsettlementLkr prpDsettlementLkr;
	
	//同步国管局项目一级预算单位
	private PrpDsettlementByr prpDsettlementByr;
	
	//同步共保体代码
	private PrpDcoins prpDcoins;
	
	//分保接收人代码
	private PrpDreinsurer prpDreinsurer;
	
	//险种代码
	private PrpDcodeRisk prpDcodeRisk;
	
	//机构代码
	private PrpDnewCodeCom prpDnewCodeCom;
	
	//专管专营代码
	private PrpDresource prpDresource;
	
	//特别约定代码
	private PrpDriskEngage prpDriskEngage;
	
	//项目代码
	private PrpDproject prpDproject;
	
	//交叉销售
	private PrpDcrossOrg prpDcrossOrg;
	
	//机构代码检验表
	private PrpDcompanyCheck prpDcompanyCheck;
	
	//核算信息表
	private List accountInfoList;
	
	//区域表
	private List areaList;
	
	//产品定义表
	private List riskList;
	
	//产品条款定义表
	private List riskClauseList;
	
	//产品条款责任表
	private List riskClauseKindList;
	
	//产品条款/责任关系表
	private List riskClauseKindRelationList;
	
	//特别约定表
	private List riskEngageList;
	
	//产品标的表
	private List riskItemList;
	
	//产品限额/免赔额表
	private List riskLimitList;
	
	//产品短期费率表
	private List riskShortRateList;
	
	//产品限额/免赔额表
	private List newCodeRiskList;
	
	//费率下限表
	private List prpdrckratelowerList;
	
	//险种表
	private List prpDclassList;
	
	//方案定义表
	private List prpDplanList;
	
	//方案条款责任表
	private List prpDplanClauseKindList;
	
	//方案限额/免赔表
	private List prpDplanLimitList;
	
	private UtiISyncLog utiISyncLog;
	
	private List<UtiISyncLog> utiISyncLogList;
	
	//prpdagentext
	private List prpDagentExtList;

	//prpdcontractmanage
	private List prpDcontractManageList;
	
	public List getPrpDagentExtList() {
		return prpDagentExtList;
	}

	public void setPrpDagentExtList(List prpDagentExtList) {
		this.prpDagentExtList = prpDagentExtList;
	}

	public List getPrpDcontractManageList() {
		return prpDcontractManageList;
	}

	public void setPrpDcontractManageList(List prpDcontractManageList) {
		this.prpDcontractManageList = prpDcontractManageList;
	}
	
	public String getRequestFlag() {
		return requestFlag;
	}

	public void setRequestFlag(String requestFlag) {
		this.requestFlag = requestFlag;
	}

	public boolean isSync() {
		return isSync;
	}

	public void setSync(boolean isSync) {
		this.isSync = isSync;
	}

	public String getUsedJmsConfig() {
		return usedJmsConfig;
	}

	public void setUsedJmsConfig(String usedJmsConfig) {
		this.usedJmsConfig = usedJmsConfig;
	}

	public String getSourComCode() {
		return sourComCode;
	}

	public void setSourComCode(String sourComCode) {
		this.sourComCode = sourComCode;
	}

	public String getDestComCode() {
		return destComCode;
	}

	public void setDestComCode(String destComCode) {
		this.destComCode = destComCode;
	}

	public String getSvrIp() {
		return svrIp;
	}

	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}

	public String getSvrPort() {
		return svrPort;
	}

	public void setSvrPort(String svrPort) {
		this.svrPort = svrPort;
	}

	public String getSvrCode() {
		return svrCode;
	}

	public void setSvrCode(String svrCode) {
		this.svrCode = svrCode;
	}

	public String getOperUserCode() {
		return operUserCode;
	}

	public void setOperUserCode(String operUserCode) {
		this.operUserCode = operUserCode;
	}

	public String getOperComCode() {
		return operComCode;
	}

	public void setOperComCode(String operComCode) {
		this.operComCode = operComCode;
	}


	public PrpDtype getPrpDtype() {
		return prpDtype;
	}

	public void setPrpDtype(PrpDtype prpDtype) {
		this.prpDtype = prpDtype;
	}


	public PrpDcompany getPrpDcompany() {
		return prpDcompany;
	}

	public void setPrpDcompany(PrpDcompany prpDcompany) {
		this.prpDcompany = prpDcompany;
	}

	public PrpDexch getPrpDexch() {
		return prpDexch;
	}

	public void setPrpDexch(PrpDexch prpDexch) {
		this.prpDexch = prpDexch;
	}

	public PrpDbank getPrpDbank() {
		return prpDbank;
	}

	public void setPrpDbank(PrpDbank prpDbank) {
		this.prpDbank = prpDbank;
	}

	public PrpDagent getPrpDagent() {
		return prpDagent;
	}

	public void setPrpDagent(PrpDagent prpDagent) {
		this.prpDagent = prpDagent;
	}

	public PrpDdealer getPrpDdealer() {
		return prpDdealer;
	}

	public void setPrpDdealer(PrpDdealer prpDdealer) {
		this.prpDdealer = prpDdealer;
	}

	public PrpDplane getPrpDplane() {
		return prpDplane;
	}

	public void setPrpDplane(PrpDplane prpDplane) {
		this.prpDplane = prpDplane;
	}

	public PrpDport getPrpDport() {
		return prpDport;
	}

	public void setPrpDport(PrpDport prpDport) {
		this.prpDport = prpDport;
	}

	public PrpDship getPrpDship() {
		return prpDship;
	}

	public void setPrpDship(PrpDship prpDship) {
		this.prpDship = prpDship;
	}

	public UtiISyncLog getUtiISyncLog() {
		return utiISyncLog;
	}

	public void setUtiISyncLog(UtiISyncLog utiISyncLog) {
		this.utiISyncLog = utiISyncLog;
	}

	public List<UtiISyncLog> getUtiISyncLogList() {
		return utiISyncLogList;
	}

	public void setUtiISyncLogList(List<UtiISyncLog> utiISyncLogList) {
		this.utiISyncLogList = utiISyncLogList;
	}

	public PrpDtreatyReten getPrpDtreatyReten() {
		return prpDtreatyReten;
	}

	public void setPrpDtreatyReten(PrpDtreatyReten prpDtreatyReten) {
		this.prpDtreatyReten = prpDtreatyReten;
	}

	public PrpDsettlementLkr getPrpDsettlementLkr() {
		return prpDsettlementLkr;
	}

	public void setPrpDsettlementLkr(PrpDsettlementLkr prpDsettlementLkr) {
		this.prpDsettlementLkr = prpDsettlementLkr;
	}

	public PrpDsettlementByr getPrpDsettlementByr() {
		return prpDsettlementByr;
	}

	public void setPrpDsettlementByr(PrpDsettlementByr prpDsettlementByr) {
		this.prpDsettlementByr = prpDsettlementByr;
	}

	public PrpDcoins getPrpDcoins() {
		return prpDcoins;
	}

	public void setPrpDcoins(PrpDcoins prpDcoins) {
		this.prpDcoins = prpDcoins;
	}

	public PrpDreinsurer getPrpDreinsurer() {
		return prpDreinsurer;
	}

	public void setPrpDreinsurer(PrpDreinsurer prpDreinsurer) {
		this.prpDreinsurer = prpDreinsurer;
	}

	public PrpDcodeRisk getPrpDcodeRisk() {
		return prpDcodeRisk;
	}

	public void setPrpDcodeRisk(PrpDcodeRisk prpDcodeRisk) {
		this.prpDcodeRisk = prpDcodeRisk;
	}

	public PrpDagentAll getPrpDagentAll() {
		return prpDagentAll;
	}

	public void setPrpDagentAll(PrpDagentAll prpDagentAll) {
		this.prpDagentAll = prpDagentAll;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public PrpDnewCodeCom getPrpDnewCodeCom() {
		return prpDnewCodeCom;
	}

	public void setPrpDnewCodeCom(PrpDnewCodeCom prpDnewCodeCom) {
		this.prpDnewCodeCom = prpDnewCodeCom;
	}

	public PrpDnewCode getPrpDcode() {
		return prpDcode;
	}

	public void setPrpDcode(PrpDnewCode prpDcode) {
		this.prpDcode = prpDcode;
	}

	public PrpDresource getPrpDresource() {
		return prpDresource;
	}

	public void setPrpDresource(PrpDresource prpDresource) {
		this.prpDresource = prpDresource;
	}

	public PrpDriskEngage getPrpDriskEngage() {
		return prpDriskEngage;
	}

	public void setPrpDriskEngage(PrpDriskEngage prpDriskEngage) {
		this.prpDriskEngage = prpDriskEngage;
	}

	public PrpDproject getPrpDproject() {
		return prpDproject;
	}

	public void setPrpDproject(PrpDproject prpDproject) {
		this.prpDproject = prpDproject;
	}

	public List getAccountInfoList() {
		return accountInfoList;
	}

	public void setAccountInfoList(List accountInfoList) {
		this.accountInfoList = accountInfoList;
	}

	public List getAreaList() {
		return areaList;
	}

	public void setAreaList(List areaList) {
		this.areaList = areaList;
	}

	public List getRiskList() {
		return riskList;
	}

	public void setRiskList(List riskList) {
		this.riskList = riskList;
	}

	public List getRiskClauseList() {
		return riskClauseList;
	}

	public void setRiskClauseList(List riskClauseList) {
		this.riskClauseList = riskClauseList;
	}

	public List getRiskClauseKindList() {
		return riskClauseKindList;
	}

	public void setRiskClauseKindList(List riskClauseKindList) {
		this.riskClauseKindList = riskClauseKindList;
	}

	public List getRiskClauseKindRelationList() {
		return riskClauseKindRelationList;
	}

	public void setRiskClauseKindRelationList(List riskClauseKindRelationList) {
		this.riskClauseKindRelationList = riskClauseKindRelationList;
	}

	public List getRiskItemList() {
		return riskItemList;
	}

	public void setRiskItemList(List riskItemList) {
		this.riskItemList = riskItemList;
	}

	public List getRiskLimitList() {
		return riskLimitList;
	}

	public void setRiskLimitList(List riskLimitList) {
		this.riskLimitList = riskLimitList;
	}

	public List getRiskShortRateList() {
		return riskShortRateList;
	}

	public void setRiskShortRateList(List riskShortRateList) {
		this.riskShortRateList = riskShortRateList;
	}

	public List getNewCodeRiskList() {
		return newCodeRiskList;
	}

	public void setNewCodeRiskList(List newCodeRiskList) {
		this.newCodeRiskList = newCodeRiskList;
	}

	public List getPrpdrckratelowerList() {
		return prpdrckratelowerList;
	}

	public void setPrpdrckratelowerList(List prpdrckratelowerList) {
		this.prpdrckratelowerList = prpdrckratelowerList;
	}

	public List getRiskEngageList() {
		return riskEngageList;
	}

	public void setRiskEngageList(List riskEngageList) {
		this.riskEngageList = riskEngageList;
	}

	public List getPrpDclassList() {
		return prpDclassList;
	}

	public void setPrpDclassList(List prpDclassList) {
		this.prpDclassList = prpDclassList;
	}

	public List getPrpDplanList() {
		return prpDplanList;
	}

	public void setPrpDplanList(List prpDplanList) {
		this.prpDplanList = prpDplanList;
	}

	public List getPrpDplanClauseKindList() {
		return prpDplanClauseKindList;
	}

	public void setPrpDplanClauseKindList(List prpDplanClauseKindList) {
		this.prpDplanClauseKindList = prpDplanClauseKindList;
	}

	public List getPrpDplanLimitList() {
		return prpDplanLimitList;
	}

	public void setPrpDplanLimitList(List prpDplanLimitList) {
		this.prpDplanLimitList = prpDplanLimitList;
	}

	public PrpDcrossOrg getPrpDcrossOrg() {
		return prpDcrossOrg;
	}

	public void setPrpDcrossOrg(PrpDcrossOrg prpDcrossOrg) {
		this.prpDcrossOrg = prpDcrossOrg;
	}

	public PrpDcompanyCheck getPrpDcompanyCheck() {
		return prpDcompanyCheck;
	}

	public void setPrpDcompanyCheck(PrpDcompanyCheck prpDcompanyCheck) {
		this.prpDcompanyCheck = prpDcompanyCheck;
	}

}
