<%@ page pageEncoding="GBK" %>
<%--
****************************************************************************
* DESC       ：出险後抄单打印页初始化
* AUTHOR     ： 项目组
* CREATEDATE ：2005-9-15
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.sysframework.common.util.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDkind"%>

<%
	int i = 0;
	String strClassCode = ""; //险类
	String strPolicyNo = ""; //保单号
	String strRegistNo = ""; //报案号
	String strPolicyName = ""; //被保险人名称
	String strPolicyAddress = ""; //被保险人地址
	String strCoinsFlag = ""; //联共保标志
	String strCurrency = "";
	double dbSumAmount = 0.0;
	DateTime OperateDate = new DateTime();
	DateTime InputDate = new DateTime();
	DateTime SignDate = new DateTime();
	DateTime UnderWriteEndDate = new DateTime();
	DateTime StartDate = new DateTime();
	DateTime EndDate = new DateTime();
	String printType = (String) request.getAttribute("printType");
	String strRiskCode = ""; //险种代码
	String strRiskName = ""; //险种名称
	StringBuffer strEngage1 = new StringBuffer();

	String[] strKindName = null; //险别名称
	String[] strItemCode = null; //标的项目
	String[] strItemdetailName = null;//标的名称
	double[] dbAmount = null; //险别保额
	String[] strItemKindFlag = null; //主险、附加险标志
	double[] dbDeductible = null; //免赔额
	//add by zhulei 20050916 区分主险、附加险、利损险、利损附加险
	String[] strKindCode = null; //险别代码
	String[] strCalculateFlag = null; //险别标志：区分主险、附加险、利损险、利损附加险
	String strSQL = "";
	BLPrpDkind blPrpDkind = new BLPrpDkind();

	String[] strEndorseNo = null;
	String[] strEndorType = null;
	DateTime[] strEndorDate = null;
	String[] strUnderWriteName = null;

	int[] iPlanNo = null;
	String[] strPlanEndorNo = null;
	double[] dbPlanFee = null;
	double[] dbRealFee = null;
	DateTime[] strPayDate = null;

	//add by zhulei 20050915 特别约定信息
	String[] arrEngage = null;
	String[] arrClause = null;
	String strEngage = "";
	Collection collection = null;
	UICodeAction uiCodeAction = new UICodeAction();

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
	if (registDto != null && registDto.getPrpLregistDto() != null) {
		strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();
		strRiskCode = registDto.getPrpLregistDto().getRiskCode();
		strClassCode = registDto.getPrpLregistDto().getClassCode();
		strRegistNo = registDto.getPrpLregistDto().getRegistNo();
	}
	if (policyDto != null && policyDto.getPrpCmainDto() != null) {
		strPolicyName = policyDto.getPrpCmainDto().getAppliName();
		strPolicyAddress = policyDto.getPrpCmainDto().getAppliAddress();
		strCoinsFlag = policyDto.getPrpCmainDto().getCoinsFlag().toString();
		dbSumAmount = policyDto.getPrpCmainDto().getSumAmount();
		strCurrency = policyDto.getPrpCmainDto().getCurrency();
		OperateDate = policyDto.getPrpCmainDto().getOperateDate();
		InputDate = policyDto.getPrpCmainDto().getInputDate();
		SignDate = policyDto.getPrpCmainDto().getSignDate();
		UnderWriteEndDate = policyDto.getPrpCmainDto().getUnderWriteEndDate();
		StartDate = policyDto.getPrpCmainDto().getStartDate();
		EndDate = policyDto.getPrpCmainDto().getEndDate();
		strRiskName = uiCodeAction.translateRiskCode(policyDto.getPrpCmainDto().getRiskCode(), true);
	}
	if (endorseDto.getPrpPheadDtoList() != null) {
		collection = endorseDto.getPrpPheadDtoList();
		strEndorseNo = new String[collection.size()];
		strEndorType = new String[collection.size()];
		strEndorDate = new DateTime[collection.size()];
		strUnderWriteName = new String[collection.size()];
		i = 0;
		Iterator iterator = endorseDto.getPrpPheadDtoList().iterator();

		while (iterator.hasNext()) {
			PrpPheadDto prpPheadDtoTemp = (PrpPheadDto) iterator.next();
			strEndorseNo[i] = prpPheadDtoTemp.getEndorseNo();
			strEndorType[i] = uiCodeAction.translateCodeCode("EndorType", prpPheadDtoTemp.getEndorType(), true);
			strEndorDate[i] = prpPheadDtoTemp.getEndorDate();
			strUnderWriteName[i] = prpPheadDtoTemp.getUnderWriteName();
			i++;
		}
	}
	if (policyDto.getPrpCplanDtoList() != null) {
		Iterator iterator = policyDto.getPrpCplanDtoList().iterator();
		collection = policyDto.getPrpCplanDtoList();
		iPlanNo = new int[collection.size()];
		strPlanEndorNo = new String[collection.size()];
		dbPlanFee = new double[collection.size()];
		dbRealFee = new double[collection.size()];
		strPayDate = new DateTime[collection.size()];
		i = 0;
		while (iterator.hasNext()) {
			PrpCplanDto prpCplanDtoTemp = (PrpCplanDto) iterator.next();

			iPlanNo[i] = prpCplanDtoTemp.getPayNo();
			if (prpCplanDtoTemp.getPayNo() == 0)
				iPlanNo[i] = 1;
			strPlanEndorNo[i] = prpCplanDtoTemp.getEndorseNo();
			dbPlanFee[i] = prpCplanDtoTemp.getPlanFee();
			dbRealFee[i] = prpCplanDtoTemp.getPlanFee() - prpCplanDtoTemp.getDelinquentFee();
			strPayDate[i] = prpCplanDtoTemp.getPlanDate();
			i++;
		}
	}
	if (policyDto.getPrpCitemKindDtoList() != null) {

		collection = policyDto.getPrpCitemKindDtoList();
		strKindName = new String[collection.size()];
		strItemCode = new String[collection.size()];
		strItemdetailName = new String[collection.size()];
		dbAmount = new double[collection.size()];
		strItemKindFlag = new String[collection.size()];
		dbDeductible = new double[collection.size()];
		strKindCode = new String[collection.size()];
		strCalculateFlag = new String[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
			strKindName[i] = prpCitemKindDtoTemp.getKindName();
			strItemCode[i] = prpCitemKindDtoTemp.getItemCode();
			strItemdetailName[i] = prpCitemKindDtoTemp.getItemDetailName();
			dbAmount[i] = prpCitemKindDtoTemp.getAmount();
			strItemKindFlag[i] = prpCitemKindDtoTemp.getFlag();
			dbDeductible[i] = prpCitemKindDtoTemp.getDeductible();
			strKindCode[i] = prpCitemKindDtoTemp.getKindCode();
			//add by zhulei 20050916 由KindCode从PrpDcode查出CalculateFlag，以便显示的时候确定是主险、附加险、利损险、利损附加险
			strSQL = "RiskCode='" + strRiskCode + "' AND KindCode='" + strKindCode[i] + "'";

			blPrpDkind = new BLPrpDkind();
			blPrpDkind.query(strSQL);
			if (blPrpDkind != null && blPrpDkind.getSize() > 0) {
				strCalculateFlag[i] = blPrpDkind.getArr(0).getCalculateFlag();
			} else {
				strCalculateFlag[i] = null;
			}
			i++;
		}
	}

	//add by zhulei 20050915 特别约定
	if (policyDto.getPrpCengageDtoList() != null) {
		collection = policyDto.getPrpCengageDtoList();
		arrEngage = new String[collection.size()];
		arrClause = new String[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCengageDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCengageDto prpCengageDto = (PrpCengageDto) iterator.next();
			arrClause[i] = prpCengageDto.getClauseCode();
			if (arrClause[i].substring(0, 1).equals("T")) {
				arrEngage[i] = prpCengageDto.getClauses().trim();
				strEngage = strEngage + arrEngage[i];
			}
			i++;
		}
		String[] rules = StringUtils.split(strEngage, 100);
		int j = 0;
		while (j < rules.length) {
			strEngage1.append("        ").append(rules[j]).append("\t").append("\n");
			j++;
		}
	}

	if (strCoinsFlag.equals("0")) { //非共保
		strCoinsFlag = "非共保";
	} else if (strCoinsFlag.equals("1")) { //主共保
		strCoinsFlag = "主共保";
	} else if (strCoinsFlag.equals("2")) { //共保
		strCoinsFlag = "共保";
	}
%>