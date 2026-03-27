<%--
****************************************************************************
* DESC       ：预付通融赔款审批单打印初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-09
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
<%@page import="java.util.Iterator"%>

<%
	//变量声明部分
	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
	String strClaimNo = (String) request.getAttribute("ClaimNo"); //赔案号
	String strRegistNo = ""; //报案号
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人姓名
	String strDamageStartDate = ""; //出险时间
	String strInsuredDate = ""; //保险期间
	String strDamageName = ""; //出险原因
	String strSumAmount = ""; //保险金额
	String strSumClaim = ""; //估损金额
	String strRiskCode = ""; //承保险别代码
	String strRiskName = ""; //承保险别名称
	String strCaseType = ""; //案件性质
	String strSumThisPaid = ""; //预付金额
	double dblSumThisPaid = 0.0; //预付金额 
	double dblSumAmount = 0.0; //保险金额

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分

	PrpCmainDto prpCmainDto = null; //保单的CmainDto对象
	PrpCinsuredDto prpInsuredDto = null; //保单的InsuredDto对象
	PrpCitemCarDto prpItemcarDto = null; //保单的ItemcarDto对象
	PrpLregistDto prpLregistDto = null; //RegistDto对象
	PrpLregistTextDto prpLregistTextDto = null; //RegistTextDto对象
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLdriverDto prpLdriverDto = null; //DriverDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLclaimLossDto prpLclaimLossDto = null; //ClaimLossDto对象
	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象

	int intClaimCount = 0; //ClaimDto对象的记录数
	int intInsuredCount = 0; //InsuredDto对象的记录数
	int intItemCarCount = 0; //ItemCarDto对象的记录数
	int intDriverCount = 0; //DriverDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
	int intRegistTextCount = 0; //RegistTextDto对象的记录数
	int intClaimLoss = 0; //PrpLclaimLossDto对象的记录数

	int index = 0;
	//得到PolicyDto,ClaimDto,RegistDto,CompensateDto对象
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");

	UICodeAction uiCodeAction = new UICodeAction();

	//得到保单号
	strPolicyNo = claimDto.getPrpLclaimDto().getPolicyNo();

	//得到prpMainDto对象
	prpCmainDto = policyDto.getPrpCmainDto();

	//得到PrpCinsured对象的记录数  
	if (policyDto.getPrpCinsuredDtoList() != null) {
		intInsuredCount = policyDto.getPrpCinsuredDtoList().size();
	}
	//得到prpItemcarDto对象
	if (policyDto.getPrpCitemCarDtoList() != null) {
		intItemCarCount = policyDto.getPrpCitemCarDtoList().size();
	}
	//得到PrpLthirdParty对象的记录数
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
	}

	//得到PrpLdriver对象的记录数
	if (claimDto.getPrpLdriverDtoList() != null) {
		intDriverCount = claimDto.getPrpLdriverDtoList().size();
	}
	//得到PrpLclaimLoss对象的记录数
	if (claimDto.getPrpLclaimLossDtoList() != null) {
		intClaimLoss = claimDto.getPrpLclaimLossDtoList().size();
	}

	//得到prpLclaimDto对象
	prpLclaimDto = claimDto.getPrpLclaimDto();

	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	//得到prpLcompensateDto对象
	prpLcompensateDto = compensateDto.getPrpLcompensateDto();

	//得到blPrpLregistText对象的记录数
	if (registDto.getPrpLregistTextDtoList() != null) {
		intRegistTextCount = registDto.getPrpLregistTextDtoList().size();
	}

	strRiskCode = prpLclaimDto.getRiskCode();
	strRiskName = uiCodeAction.translateRiskCode(strRiskCode, isChinese);

	double dblSumClaim = 0.0; //估损金额
	if (intClaimLoss > 0) {
		for (index = 0; index < intClaimLoss; index++) {
			prpLclaimLossDto = (PrpLclaimLossDto) claimDto.getPrpLclaimLossDtoList().get(index);
			dblSumClaim += prpLclaimLossDto.getSumClaim();
		}
	}
	strSumClaim = new DecimalFormat("#,##0.00").format(dblSumClaim) + "元";
	strInsuredName = StringConvert.encode(prpLclaimDto.getInsuredName());
	String strDateTemp = "";
	if (prpLclaimDto.getDamageStartHour() != null) {
		if (prpLclaimDto.getDamageStartHour().length() < 2) {
			strDateTemp = prpLclaimDto.getDamageStartHour();
		} else {
			strDateTemp = prpLclaimDto.getDamageStartHour().substring(0, 2);
		}
	}
	strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + " 年 " + prpLclaimDto.getDamageStartDate().getMonth() + " 月 " + prpLclaimDto.getDamageStartDate().getDate() + " 日 " + strDateTemp + " 时";
	if (prpCmainDto != null) {
		strInsuredDate = "自 " + prpCmainDto.getStartDate().getYear() + "年" + prpCmainDto.getStartDate().getMonth() + "月" + prpCmainDto.getStartDate().getDate() + "日" + prpCmainDto.getStartHour() + "时起" + "至 " + prpCmainDto.getEndDate().getYear() + "年" + prpCmainDto.getEndDate().getMonth() + "月" + prpCmainDto.getEndDate().getDate() + "日" + prpCmainDto.getEndHour() + "时止";
	}
	if (prpLregistDto != null) {
		strDamageName = StringConvert.encode(prpLregistDto.getDamageName());
	}

	if (prpLcompensateDto != null) {
		strCaseType = prpLcompensateDto.getCaseType();
		if ("3".equals(strCaseType) || "4".equals(strCaseType) || "6".equals(strCaseType)) {
			dblSumThisPaid = prpLcompensateDto.getSumThisPaid();
			if (dblSumThisPaid > 0) {
				strSumThisPaid = new DecimalFormat("#,##0.00").format(dblSumThisPaid) + "元";
			}
		}
	}

	dblSumAmount = prpLclaimDto.getSumAmount();
	strSumAmount = new DecimalFormat("#,##0.00").format(dblSumAmount) + "元";
%>