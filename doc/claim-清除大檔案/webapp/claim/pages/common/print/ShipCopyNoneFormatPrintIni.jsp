<%--

****************************************************************************

* DESC       ：出险後抄单打印页初始化

* AUTHOR     ：wangwei

* CREATEDATE ：2005-5-28

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
	String strPolicyNo = ""; //保单号

	String strRegistNo = ""; //报案号

	String strPolicyName = ""; //被保险人名称

	String strPolicyAddress = ""; //被保险人地址

	String strCoinsFlag = ""; //联共保标志

	String strLossName = "";

	String strSumAmount = "";

	String strClaimSite = "";

	String strDeductibleRate = "";

	String strStartDate = "";

	String strInputDate = "";

	String strOperateDate = "";

	String strEndDate = "";

	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");

	if (registDto != null && registDto.getPrpLregistDto() != null) {

		strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();

		strRegistNo = registDto.getPrpLregistDto().getRegistNo();

		strLossName = registDto.getPrpLregistDto().getLossName();

	}

	if (policyDto != null && policyDto.getPrpCmainDto() != null) {

		strPolicyName = policyDto.getPrpCmainDto().getArbitBoardName();

		strPolicyAddress = policyDto.getPrpCmainDto().getAppliAddress();

		strCoinsFlag = policyDto.getPrpCmainDto().getCoinsFlag().toString();

		strSumAmount = "" + policyDto.getPrpCmainDto().getSumAmount();

		strStartDate = policyDto.getPrpCmainDto().getStartDate().toString();

		strInputDate = policyDto.getPrpCmainDto().getInputDate().toString();

		strOperateDate = policyDto.getPrpCmainDto().getOperateDate().toString();

		strEndDate = "从" + policyDto.getPrpCmainDto().getStartDate() + "到" + policyDto.getPrpCmainDto().getEndDate();

	}

	if (strCoinsFlag.equals("0")) { //非共保

		strCoinsFlag = "非共保";

	} else if (strCoinsFlag.equals("1")) { //主共保

		strCoinsFlag = "主共保";

	} else if (strCoinsFlag.equals("2")) { //共保

		strCoinsFlag = "共保";

	}
%>