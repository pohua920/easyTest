package cn.com.sinosoft.saa.service.facade;

import ins.framework.common.Page;


/**
 * ���뷭�����
 *
 */
public interface CodeService {

	
	public String CLASSCODE = "ClassCode";
	public String USERCODE = "UserCode";
	
	public String AGENTUSERCODE = "AgentUserCode";

	public String REGISTUSER = "RegistUser";

	public String COMCODE = "ComCode";
	
	public String AGENTCOMCODE = "AgentComCode";

	public String RISKCODE = "RiskCode";

	public String AGENTCODE = "AgentCode";

	public String KINDCODE = "KindCode";

	public String ITEMCODE = "ItemCode";
	
	public String INJUREDPARTSCLASS="InjuredPartsClass";

	public String CURRENCYCODE = "Currency";

	public String CUSTOMERCODE = "CustomerCode";

	public String LIMITCODE = "LimitCode";

	public String METHODTASK = "methodTask";

	public String TASKCODE = "TaskCode";

	public String PERSONNAME = "PersonName";

	public String CONFIGCODE = "ConfigCode";

	public String HOSPITALLEVEL = "HospitalLevel";

	public String EXAMFACTORY = "ExamFactory";

	public String REPAIRFACTORY = "RepairFactory";

	public String REPAIRFACTORYINVOICE = "RepairFactoryInvoice";

	public String DISASTERCODE = "DisasterCode";

	public String GROUPID = "GroupId";

	public String GRADEID = "GradeId";

	public String NODEID = "NodeId";

	public String NODEIDEXCEPTANYPAY = "NodeIdExceptAnyPay";

	public String VALIDFLAG_Y = "1";

	public String VALIDFLAG_N = "0";

	public String USERTREE = "UserTree";

	public String GROUPTREE = "GroupTree";

	public String POWERUSER = "PowerUser";
	
	public String PAYTYPE = "PayType";
	
	public String DEFLOSSAREA = "DefLossArea";
	
	public String DIRECTCLAIMITEM = "DirectClaimItem";
	
	public String DIRECTCLAIMTYPE = "DirectClaimType";
	
	public String OCCUPATION = "Occupation";
	
	public String FEETYPECODE="FeeTypeCode";
	
	public String JOBINFO = "JobInfo";
	
	public String WOUNDLEVEL = "WoundLevel";

	public String TYPECODE = "TypeCode";

	
	public String translateCode(String systemCode, String codeType, String codeCode, String codeFlag, String language);

	
	public String translateNameToCode(String systemCode, String codeType, String codeCName,String codeEName);
	
	
	public String codeTypeTranslate(String systemCode, String codeType);
	
	public Page listCodeSelect(String codeType, String riskCode,
			String language, String matches, int pageNo, int pageSize,
			String userCode, String typeParam, String extraCond)throws Exception;
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
}
