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
<%@page import="java.util.*"%>
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
	String strPolicyPhonenumber = ""; //联系电话
	String strPolicyAddress = ""; //被保险人地址
	String strCoinsFlag = ""; //联共保标志
	String strCurrency = "";
	double dbSumAmount = 0.0; //总保额
	double dbSumpremium = 0.0; //总保费
	String strComcname = "";//保单归属机构
	String strSumAmount = ""; //总保额格式显示
	double dbDeductible = 0; //免赔额
	DateTime OperateDate = new DateTime();
	DateTime InputDate = new DateTime();
	DateTime SignDate = new DateTime();
	DateTime UnderWriteEndDate = new DateTime();
	DateTime StartDate = new DateTime();
	DateTime EndDate = new DateTime();
	DateTime loanStartDate = new DateTime();
	DateTime loanEndDate = new DateTime();
	String printType = (String) request.getAttribute("printType");
	String[] strAddressName = null; //保险住房详细地址
	String[] strStructure = null; //保险住房结构
	double[] dblBuildArear = null; //保险住房建筑面积
	String strLoanUsage = ""; //房屋类型
	String strLoanBank = ""; //贷款银行,受益人
	String strWarrantorCode = ""; //贷款审批表编号
	String strLoanNo2 = ""; //房屋购置价
	double dblLoanAmount = 0.0d; //贷款金额

	String strRiskCode = ""; //险种代码
	String strRiskName = ""; //险种名称

	String[] strKindCode = null; //险别代码
	String[] strKindName = null; //险别名称
	String[] strKindFlag = null; //险别标志
	String[] strItemDetailName = null; //标的名称
	int[] strKindAddress = null; //险别地址
	String[] strCalculateFlag = null; //险别标志：区分主险、附加险、利损险、利损附加险
	double[] dbAmount = null; //险别保额
	String[] strAmount = null; //险别保额格式显示
	double dbAmount100 = 0; //利损险总保额
	String strAmount100 = "0.00"; //利损险总保额显示格式
	BLPrpDkind blPrpDkind = new BLPrpDkind();
	String strSQL = "";

	String[] strEndorseNo = null;
	String[] strEndorType = null;
	DateTime[] strEndorDate = null;
	String[] strUnderWriteName = null;

	int[] iPlanNo = null;
	String[] strPlanEndorNo = null;
	double[] dbPlanFee = null;
	double[] dbRealFee = null;
	DateTime[] strPayDate = null;

	String[] arrClauseCode = null; //特约及附加代码
	String[] arrEngage = null;
	String strEngage = "";

	Collection collection = null;
	UICodeAction uiCodeAction = new UICodeAction();

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

	if (policyDto != null && policyDto.getPrpCaddressDtoList() != null) {
		strAddressName = new String[policyDto.getPrpCaddressDtoList().size()];
		for (int index = 0; index < policyDto.getPrpCaddressDtoList().size(); index++) {
			PrpCaddressDto prpCaddressDto = (PrpCaddressDto) ((ArrayList) policyDto.getPrpCaddressDtoList()).get(index);
			strAddressName[index] = prpCaddressDto.getAddressName();
		}
	}

	if (policyDto != null && policyDto.getPrpCitemHouseDtoList() != null) {
		for (int index = 0; index < policyDto.getPrpCitemHouseDtoList().size(); index++) {
			strStructure = new String[policyDto.getPrpCitemHouseDtoList().size()];
			dblBuildArear = new double[policyDto.getPrpCitemHouseDtoList().size()];
			PrpCitemHouseDto prpCitemHouseDto = (PrpCitemHouseDto) ((ArrayList) policyDto.getPrpCitemHouseDtoList()).get(index);
			if ("0".equals(prpCitemHouseDto.getStructure())) {
				strStructure[index] = "钢筋混凝土";
			} else if ("1".equals(prpCitemHouseDto.getStructure())) {
				strStructure[index] = "混合型";
			} else if ("9".equals(prpCitemHouseDto.getStructure())) {
				strStructure[index] = "砖木型";
			}
			dblBuildArear[index] = prpCitemHouseDto.getBuildArea();
		}
	}

	if (policyDto != null && policyDto.getPrpCmainLoanDto() != null) {
		if ("0".equals(policyDto.getPrpCmainLoanDto().getLoanUsage())) {
			strLoanUsage = "现房";
		} else {
			strLoanUsage = "期房";
		}
		strLoanBank = policyDto.getPrpCmainLoanDto().getLoanBankName();
		strWarrantorCode = policyDto.getPrpCmainLoanDto().getWarrantorCode();
		strLoanNo2 = policyDto.getPrpCmainLoanDto().getLoanNo2();
		dblLoanAmount = policyDto.getPrpCmainLoanDto().getLoanAmount();
		loanStartDate = policyDto.getPrpCmainLoanDto().getLoanStartDate();
		loanEndDate = policyDto.getPrpCmainLoanDto().getLoanEndDate();
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
		strSumAmount = new DecimalFormat("0.00").format(policyDto.getPrpCmainDto().getSumAmount());

		strCurrency = policyDto.getPrpCmainDto().getCurrency();
		OperateDate = policyDto.getPrpCmainDto().getOperateDate();
		InputDate = policyDto.getPrpCmainDto().getInputDate();
		SignDate = policyDto.getPrpCmainDto().getSignDate();
		UnderWriteEndDate = policyDto.getPrpCmainDto().getUnderWriteEndDate();
		StartDate = policyDto.getPrpCmainDto().getStartDate();
		EndDate = policyDto.getPrpCmainDto().getEndDate();
		strRiskCode = policyDto.getPrpCmainDto().getRiskCode();
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
		strKindCode = new String[collection.size()];
		strKindName = new String[collection.size()];
		strKindFlag = new String[collection.size()];
		strItemDetailName = new String[collection.size()];
		strKindAddress = new int[collection.size()];
		dbAmount = new double[collection.size()];
		strAmount = new String[collection.size()];
		strCalculateFlag = new String[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCitemKindDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCitemKindDto prpCitemKindDtoTemp = (PrpCitemKindDto) iterator.next();
			strKindCode[i] = prpCitemKindDtoTemp.getKindCode();
			strKindName[i] = prpCitemKindDtoTemp.getKindName();

			strKindFlag[i] = prpCitemKindDtoTemp.getFlag();

			strItemDetailName[i] = prpCitemKindDtoTemp.getItemDetailName();

			strKindAddress[i] = prpCitemKindDtoTemp.getAddressNo();

			dbAmount[i] = prpCitemKindDtoTemp.getAmount();

			strAmount[i] = new DecimalFormat("0.00").format(prpCitemKindDtoTemp.getAmount());

			dbDeductible = dbDeductible + prpCitemKindDtoTemp.getDeductible();

			strSQL = "RiskCode='" + strRiskCode + "' AND KindCode='" + strKindCode[i] + "'";

			blPrpDkind = new BLPrpDkind();

			blPrpDkind.query(strSQL);
			if (blPrpDkind != null && blPrpDkind.getSize() > 0) {
				strCalculateFlag[i] = blPrpDkind.getArr(0).getCalculateFlag();
			} else {
				strCalculateFlag[i] = null;
			}
			if ("100".equals(strKindCode[i])) {
				dbAmount100 = dbAmount100 + dbAmount[i];
			}

			i++;

		}

		strAmount100 = new DecimalFormat("0.00").format(dbAmount100);
	}

	if (strCoinsFlag.equals("0")) {
		strCoinsFlag = "否";
	} else if (strCoinsFlag.equals("1")) {
		strCoinsFlag = "是";
	}
	//    if (strCoinsFlag.equals("0")) {         //非共保
	//        strCoinsFlag = "非共保";
	//    } else if (strCoinsFlag.equals("1")) {  //主共保
	//        strCoinsFlag = "主共保";
	//    } else if (strCoinsFlag.equals("2")) {  //共保
	//        strCoinsFlag = "共保";
	//    }

	if (policyDto.getPrpCengageDtoList() != null) {
		collection = policyDto.getPrpCengageDtoList();
		arrEngage = new String[collection.size()];
		arrClauseCode = new String[collection.size()];
		i = 0;
		Iterator iterator = policyDto.getPrpCengageDtoList().iterator();
		while (iterator.hasNext()) {
			PrpCengageDto prpCengageDto = (PrpCengageDto) iterator.next();
			arrEngage[i] = prpCengageDto.getClauses();
			arrClauseCode[i] = prpCengageDto.getClauseCode();
			//特约不重复显示（注意要区分附加险,arrClauseCode[i]的第一位是T）
			if (arrClauseCode[i].substring(0, 1).equals("T")) {
				if (strEngage.indexOf(arrEngage[i]) < 0) {
					strEngage = strEngage + arrEngage[i] + "<br>";
				}
			}
			i++;
		}
	}

	int strGrossProfitMonths = 0; //毛利润－月
	int strWageMonths = 0; //毛利润－月
	/*BLPrpCitemProp blPrpCitemProp = new BLPrpCitemProp();
	blPrpCitemProp.query("PolicyNo='"+ strPolicyNo +"'");
	if(blPrpCitemProp.getSize()>0){
		strGrossProfitMonths = blPrpCitemProp.getArr(0).getGrossProfitMonths();
		strWageMonths = blPrpCitemProp.getArr(0).getWageMonths();
	}*/
	if (policyDto != null && policyDto.getPrpCitemPropDtoList() != null && policyDto.getPrpCitemPropDtoList().size() > 0) {
		strGrossProfitMonths = ((PrpCitemPropDto) policyDto.getPrpCitemPropDtoList().get(0)).getGrossProfitMonths();
		strWageMonths = ((PrpCitemPropDto) policyDto.getPrpCitemPropDtoList().get(0)).getWageMonths();
	}
%>