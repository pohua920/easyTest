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

<%
	int i = 0;
	String strClassCode = ""; //险类
	String strPolicyNo = ""; //保单号
	String strRegistNo = ""; //报案号
	String strPolicyName = ""; //被保险人名称
	String strPolicyPhonenumber = "";//被保险人电话
	String strPolicyAddress = ""; //被保险人地址
	String strCoinsFlag = ""; //联共保标志
	String strCurrency = "";
	double dbSumAmount = 0.0; //总保额
	double dbSumpremium = 0.0; //总保费
	String strComcname = "";//保单归属机构
	DateTime OperateDate = new DateTime();
	DateTime InputDate = new DateTime();
	DateTime SignDate = new DateTime();
	DateTime UnderWriteEndDate = new DateTime();
	DateTime StartDate = new DateTime();
	DateTime EndDate = new DateTime();
	String printType = (String) request.getAttribute("printType");

	String strRiskCode = ""; //险种代码
	String strRiskName = ""; //险种名称

	String[] strKindName = null; //险别名称
	String[] strItemDetailName = null; //货物名称
	double dbDeductible = 0; //免赔额

	double[] dbAmount = null; //险别保额
	String strKindNameTemp = ""; //险别名称（汇总）
	String strItemDetailNameTemp = ""; //险别名称（汇总）

	String[] strEndorseNo = null;
	String[] strEndorType = null;
	DateTime[] strEndorDate = null;
	String[] strUnderWriteName = null;

	int[] iPlanNo = null;
	String[] strPlanEndorNo = null;
	double[] dbPlanFee = null;
	double[] dbRealFee = null;
	DateTime[] strPayDate = null;

	//add by zhulei 20050915 运输路线
	String strSiteName = ""; //运输路线
	String strStartSiteName = ""; //起运地
	String strViaSiteName = ""; //中转地
	String strEndSiteName = ""; //目的地
	String strCargoName = ""; //船只名称

	Collection collection = null;
	UICodeAction uiCodeAction = new UICodeAction();

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

	String strInnerCode = uiCodeAction.translateRiskCodetoInnerCode(policyDto.getPrpCmainDto().getRiskCode());
	if ("YII".equals(strInnerCode)) {
		strCargoName = policyDto.getPrpCmainCargoDto().getBLNo();
	}

	if (registDto != null && registDto.getPrpLregistDto() != null) {
		strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();
		strClassCode = registDto.getPrpLregistDto().getClassCode();
		strRegistNo = registDto.getPrpLregistDto().getRegistNo();
	}
	if (policyDto != null && policyDto.getPrpCmainDto() != null) {
		strPolicyName = policyDto.getPrpCmainDto().getInsuredName();
		Collection prpcinsuredDtoList = new ArrayList();
		PrpCinsuredDto prpcinsuredDto = null;
		prpcinsuredDtoList = policyDto.getPrpCinsuredDtoList();
		Iterator iterator1 = prpcinsuredDtoList.iterator();
		while (iterator1.hasNext()) {
			prpcinsuredDto = (PrpCinsuredDto) iterator1.next();
			if ("1".equals(prpcinsuredDto.getInsuredFlag())) {
				break;
			}
		}
		strPolicyPhonenumber = prpcinsuredDto.getPhoneNumber();
		strPolicyAddress = policyDto.getPrpCmainDto().getAppliAddress();
		strCoinsFlag = policyDto.getPrpCmainDto().getCoinsFlag().toString();
		dbSumAmount = policyDto.getPrpCmainDto().getSumAmount();
		dbSumpremium = policyDto.getPrpCmainDto().getSumPremium();
		strComcname = new UICodeAction().findPrpDcompanyByPrimaryKey(policyDto.getPrpCmainDto().getComCode()).getComCName();
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
		strItemDetailName = new String[collection.size()];
		dbAmount = new double[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
			strKindName[i] = prpCitemKindDtoTemp.getKindName();
			strItemDetailName[i] = prpCitemKindDtoTemp.getItemDetailName();

			strKindNameTemp = strKindNameTemp + strKindName[i] + "&nbsp;&nbsp;&nbsp;&nbsp;";
			//货物名称不要重复
			if (strItemDetailNameTemp.indexOf(strItemDetailName[i]) < 0) {
				strItemDetailNameTemp = strItemDetailNameTemp + strItemDetailName[i] + "&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			dbAmount[i] = prpCitemKindDtoTemp.getAmount();
			dbDeductible = dbDeductible + prpCitemKindDtoTemp.getDeductible();
			i++;
		}
	}

	if (policyDto.getPrpCmainCargoDto() != null) {
		strStartSiteName = policyDto.getPrpCmainCargoDto().getStartSiteName(); //起运地
		strViaSiteName = policyDto.getPrpCmainCargoDto().getViaSiteName(); //中转地
		strEndSiteName = policyDto.getPrpCmainCargoDto().getEndSiteName(); //目的地
		strSiteName = "起运地：" + strStartSiteName;
		if (strViaSiteName != null && !strViaSiteName.equals("")) {
			strSiteName = strSiteName + "   中转地：" + strViaSiteName;
		}
		strSiteName = strSiteName + "   目的地：" + strEndSiteName;
	}
	///////////////////////////////////////////
	//    if (strCoinsFlag.equals("0")) {         //非共保
	//        strCoinsFlag = "非共保";
	//    } else if (strCoinsFlag.equals("1")) {  //主共保
	//        strCoinsFlag = "主共保";
	//    } else if (strCoinsFlag.equals("2")) {  //共保
	//        strCoinsFlag = "共保";
	//    }
	if (strCoinsFlag.equals("0")) { //非共保
		strCoinsFlag = "否";
	} else { //主共保、共保
		strCoinsFlag = "是";
	}

	String strMainPolicyNo = "";
	//delete by miaowenjun 20060907
	/*BLPrpCmainSub blPrpCmainSub = new BLPrpCmainSub();
	blPrpCmainSub.query("PolicyNo='"+strPolicyNo+"'");
	if(blPrpCmainSub.getSize()>0){
	  strMainPolicyNo = blPrpCmainSub.getArr(0).getMainPolicyNo();
	}*/
	strMainPolicyNo = (String) request.getAttribute("strMainPolicyNo");
%>