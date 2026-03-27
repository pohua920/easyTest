<%--
****************************************************************************
* DESC       ：出险後抄单打印页初始化
* AUTHOR     ： 项目组
* CREATEDATE ：2005-9-14
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

<%@page import="com.sinosoft.reins.out.bl.facade.BLCTrialFacade"%>
<%@page import="com.sinosoft.reins.out.dto.custom.CReinsTrialDto"%>
<%@page import="com.sinosoft.reins.out.dto.domain.*"%>

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

	String strRiskCode = ""; //险种代码
	String strRiskName = ""; //险种名称
	String strDamageDate = ""; //出险时间（日期）

	String[] strKindName = null; //险别名称
	double[] dbAmount = null; //险别保额

	String[] strEndorseNo = null;
	String[] strEndorType = null;
	DateTime[] strEndorDate = null;
	String[] strUnderWriteName = null;

	int[] iPlanNo = null;
	String[] strPlanEndorNo = null;
	double[] dbPlanFee = null;
	double[] dbRealFee = null;
	DateTime[] strPayDate = null;

	Collection collection = null;
	UICodeAction uiCodeAction = new UICodeAction();

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
	if (registDto != null && registDto.getPrpLregistDto() != null) {
		strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();
		strClassCode = registDto.getPrpLregistDto().getClassCode();
		strRegistNo = registDto.getPrpLregistDto().getRegistNo();
		strDamageDate = registDto.getPrpLregistDto().getDamageStartDate().toString();
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
		////System.out.println(iterator.getSize());		
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
		dbAmount = new double[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
			strKindName[i] = prpCitemKindDtoTemp.getKindName();
			dbAmount[i] = prpCitemKindDtoTemp.getAmount();
			i++;
		}
	}

	if (strCoinsFlag.equals("0")) { //非共保
		strCoinsFlag = "非共保";
	} else if (strCoinsFlag.equals("1")) { //主共保
		strCoinsFlag = "主共保";
	} else if (strCoinsFlag.equals("2")) { //共保
		strCoinsFlag = "共保";
	}
	//add by zhulei 20050914 分保信息
	String[] strReinsMode = null; //分保类别（方式）
	double[] dbShareRate = null; //分保比例
	double dbShareRate0 = 0; //法分比例
	double dbShareRate2 = 0; //合同比例
	double dbShareRate3 = 0; //临分比例
	double dbShareRate1 = 0; //自留比例

	CReinsTrialDto cReinsTrialDto = new CReinsTrialDto();
	BLCTrialFacade blCTrialFacade = new BLCTrialFacade();
	cReinsTrialDto = blCTrialFacade.getCTrialInfo(strPolicyNo, 1, strDamageDate);
	strReinsMode = new String[cReinsTrialDto.getPrpCreinsTrialDtoList().size()];
	dbShareRate = new double[cReinsTrialDto.getPrpCreinsTrialDtoList().size()];
	Iterator iterator = cReinsTrialDto.getPrpCreinsTrialDtoList().iterator();

	i = 0;
	while (iterator.hasNext()) {
		PrpCreinsTrialDtoBase prpCreinsTrialDtoBase = (PrpCreinsTrialDtoBase) iterator.next();
		strReinsMode[i] = prpCreinsTrialDtoBase.getReinsMode();
		dbShareRate[i] = prpCreinsTrialDtoBase.getShareRate();
		if (strReinsMode[i].substring(0, 1).equals("0")) {
			dbShareRate0 = dbShareRate0 + dbShareRate[i];
		} else if (strReinsMode[i].substring(0, 1).equals("1")) {
			dbShareRate1 = dbShareRate1 + dbShareRate[i];
		} else if (strReinsMode[i].substring(0, 1).equals("2")) {
			dbShareRate2 = dbShareRate2 + dbShareRate[i];
		} else if (strReinsMode[i].substring(0, 1).equals("3")) {
			dbShareRate3 = dbShareRate3 + dbShareRate[i];
		}
		////System.out.println("================strReinsMode="+strReinsMode);
		i++;
	}
%>