<%--
****************************************************************************
* DESC       ：非车险理赔申请书打印页面
* AUTHOR     ：罗畅
* CREATEDATE ：2010-05-27
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.sinosoft.sysframework.common.util.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.sinosoft.claim.bl.facade.BLPrpDuserFacade"%>
<%@ page import="com.sinosoft.claim.bl.facade.BLPrpDcompanyFacade"%>
<%@ page import="com.sinosoft.claim.bl.facade.BLPrpLclaimFacade"%>
<%@ page import="com.sinosoft.claim.ui.control.viewHelper.EndorseViewHelper"%>
<%
	//变量声明部分
	String RiskCode = "";//险种代码
	String strRiskName = "";//险种名称
	String strClaimNo = "";//赔案号
	String strCompClaimNo = "";//交强险赔案号
	String strPolicyNo = "";//保单号
	String strInsuredName = "";//被保险人名称
	String strInsuredAddress = "";//被保险人地址
	String strInsuredPhoneNumber = "";//被保险人电话
	String strInsuredDate = "";//保险期限
	String strLinkerName = "";//联系人名称
	String strLinkerAddress = "";//联系人地址
	String strLinkerPhoneNumber = "";//联系人电话
	String strDamageStartDate = "";//出险日期
	String strDamageName = "";//出险原因
	String strDamageAddress = "";//出现地址
	String strEstimateLoss = "";//报损金额
	String strComAddress = "";//公司地址
	String strComPhoneNumber = "";//公司电话
	String strFaxNumber = "";//传真
	String strHandlerName = "";//经办人名称
	String strBrandName = ""; //厂牌型号
	String strLicenseNo = ""; //车牌号
	String strDamageTypeCode = "";//事故类型代码
	String strDamageTypeName = "";//事故类型说明

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	String policyNo = registDto.getPrpLregistDto().getPolicyNo();
	strClaimNo = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	strCompClaimNo = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

	PrpLRegistRPolicyDto prpLRegistRPolicyDto = registDto.getPrpLRegistRPolicyDtoOfCompel();
	if (prpLRegistRPolicyDto != null) {
		strCompClaimNo = prpLRegistRPolicyDto.getClaimNo();
	}

	RiskCode = registDto.getPrpLregistDto().getRiskCode();
	strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();
	strInsuredName = registDto.getPrpLregistDto().getInsuredName();
	strInsuredAddress = registDto.getPrpLregistDto().getInsuredAddress();
	if (policyDto.getPrpCinsuredDtoList().size() > 0) {
		Iterator it = policyDto.getPrpCinsuredDtoList().iterator();
		while (it.hasNext()) {
			PrpCinsuredDto prpCinsuredDto = (PrpCinsuredDto) it.next();
			if (prpCinsuredDto.getInsuredCode().equals(registDto.getPrpLregistDto().getInsuredCode())) {
				strInsuredPhoneNumber = prpCinsuredDto.getPhoneNumber();
				break;
			}
		}
	}

	strInsuredDate = "自 " + policyDto.getPrpCmainDto().getStartDate().getYear() + "年" + policyDto.getPrpCmainDto().getStartDate().getMonth() + "月" + policyDto.getPrpCmainDto().getStartDate().getDate() + "日 零时起" + "至 " + policyDto.getPrpCmainDto().getEndDate().getYear() + "年" + policyDto.getPrpCmainDto().getEndDate().getMonth() + "月" + policyDto.getPrpCmainDto().getEndDate().getDate()
			+ "日 二十四时止";
	strLinkerName = registDto.getPrpLregistDto().getReportorName();
	strLinkerAddress = registDto.getPrpLregistDto().getReportAddress();
	strLinkerPhoneNumber = registDto.getPrpLregistDto().getReportorPhoneNumber();
	strDamageStartDate = registDto.getPrpLregistDto().getDamageStartDate().getYear() + "年" + registDto.getPrpLregistDto().getDamageStartDate().getMonth() + "月" + registDto.getPrpLregistDto().getDamageStartDate().getDate() + "日" + registDto.getPrpLregistDto().getDamageEndHour().toString().substring(0, 2) + "时" + registDto.getPrpLregistDto().getDamageEndHour().toString().substring(3, 5)
			+ "分";
	strDamageName = registDto.getPrpLregistDto().getDamageName();
	strDamageAddress = registDto.getPrpLregistDto().getDamageAddress();
	strEstimateLoss = registDto.getPrpLregistDto().getEstiCurrency() + "  " + registDto.getPrpLregistDto().getEstimateLoss();

	BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
	UserDto userDto = (UserDto) session.getAttribute("user");
	String comcode = userDto.getComCode();
	PrpDcompanyDto prpDcompanyDto = blPrpDcompanyFacade.findByPrimaryKey(comcode);
	strComAddress = prpDcompanyDto.getAddressCName();
	strFaxNumber = prpDcompanyDto.getFaxNumber();
	strComPhoneNumber = prpDcompanyDto.getPhoneNumber();

	UICodeAction uiCodeAction = new UICodeAction();
	strRiskName = uiCodeAction.translateRiskCode(RiskCode, true);

	for (int index = 0; index < registDto.getPrpLthirdPartyDtoList().size(); index++) {
		PrpLthirdPartyDto prpLthirdPartyDto = (PrpLthirdPartyDto) registDto.getPrpLthirdPartyDtoList().get(index);
		if (index == 0)
			strClaimNo = prpLthirdPartyDto.getClaimNo();
		//取得保险车辆信息
		if (prpLthirdPartyDto.getInsureCarFlag().equals("1")) {
			strBrandName = prpLthirdPartyDto.getBrandName();
			strLicenseNo = prpLthirdPartyDto.getLicenseNo();
		}
	}

	strDamageTypeCode = registDto.getPrpLregistDto().getDamageTypeCode();
	strDamageTypeName = uiCodeAction.translateCodeCode("DamageTypeCode", strDamageTypeCode, true);
%>