<%--
****************************************************************************
* DESC       ：机动车辆保险结案报告书打印页面(附页)
* AUTHOR     ：zhaozhuo
* CREATEDATE ：2005-04-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 

<%
 	//变量声明部分

 	String strInsuredName = "";
 	String strDamageStartDate = ""; //出险时间
 	String strLicenseNo = ""; //保单中的号牌号码
 	String strClaimNo = ""; //赔案号
 	String strContext = ""; //赔款计算书文字
 	String strTextType = "";
 	String strComName = ""; //承保公司名称
 	String strPolicyNo = ""; //保单号
 	String strRiskCode = "";

 	int intInsuredCount = 0; //InsuredDto对象的记录数
 	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
 	int intDriverCount = 0; //DriverDto对象的记录数
 	int intCtextCount = 0; //CtextDto对象的记录数
 	int intCtextCountTmp = 0; //textarea行数
 	int intCplanCount = 0;
 	int index = 0;
 	int intLossCount = 0;
 	int intCmainCount = 0;

 	double douPlanFee = 0d;
 	double douDelinquentFee = 0d;

 	//对象定义部分
 	PrpCinsuredDto prpInsuredDto = null; //保单的InsuredDto对象                                           
 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
 	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
 	PrpLdriverDto prpLdriverDto = null; //DriverDto对象
 	PrpLctextDto prpLctextDto = null;
 	PrpLregistDto prpLregistDto = null;
 	PrpLcheckDto prpLcheckDto = null;
 	PrpCplanDto prpCplanDto = null;
 	PrpLlossDto prpLlossDto = null;
 	PrpLpersonLossDto prpLpersonLossDto = null;
 	PrpLchargeDto prpLchargeDto = null;
 	PrpCmainDto prpCmainDto = null;

 	//得到ClaimDto,RegistDto,PolicyDto,CompensateDto对象
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
 	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
 	PrpLCMainDto prpLCMainDto = (PrpLCMainDto) request.getAttribute("prpLCMainDto");

 	UICodeAction uiCodeAction = new UICodeAction();

 	//得到prpLclaimDto对象
 	prpLclaimDto = claimDto.getPrpLclaimDto();
 	prpLregistDto = registDto.getPrpLregistDto();
 	prpLcheckDto = checkDto.getPrpLcheckDto();
 	strClaimNo = prpLclaimDto.getClaimNo();
 	strComName = prpLclaimDto.getComName();
 	strPolicyNo = prpLregistDto.getPolicyNo();

 	if (compensateDto.getPrpLctextDtoDtoList() != null) {
 		//*****赔款计算文字表PrpLctext*****
 		for (index = 0; index < compensateDto.getPrpLctextDtoDtoList().size(); index++) {
 			prpLctextDto = (PrpLctextDto) compensateDto.getPrpLctextDtoDtoList().get(index);
 			strTextType = StringConvert.encode(prpLctextDto.getTextType());
 			if (strTextType.charAt(0) == '1') {
 				strContext = strContext + StringConvert.encode(prpLctextDto.getContext());
 			}
 		}
 	}

 	for (index = 0; index < strContext.length(); index++) {
 		if (strContext.substring(index, index + 1).equals("\\")) {
 			if (!(strContext.substring(index).length() < 4)) {
 				if (strContext.substring(index, index + 4).equals("\\r\\n")) {
 					intCtextCountTmp += 1;
 				}
 			}
 		}
 	}
 	int x = 0;
 	int y = 0;
 	if (!(strContext.length() < 4)) //如果strContext.length()>=4，判断strContext结尾是文字，还是回车换行
 	{
 		x = strContext.length() - 4;
 		y = strContext.length();
 		if (!strContext.substring(x, y).equals("\\r\\n")) {
 			intCtextCountTmp += 1;
 		}
 	} else
 		//如果strContext不足1行，intCtextCountTmp = 1; 
 		intCtextCountTmp = 1;

 	while (strContext.indexOf("\\r\\n") != -1)
 		strContext = strContext.substring(0, strContext.indexOf("\\r\\n")) + "<br>" + strContext.substring(strContext.indexOf("\\r\\n") + "\\r\\n".length());

 	//承保公司
 	String strCode = "";
 	String strName = "";
 	strCode = StringConvert.encode(prpLclaimDto.getComCode());
 	strName = uiCodeAction.translateComCode(strCode, true);
 	String strCaseNo = StringConvert.encode(prpLclaimDto.getCaseNo());
 	strInsuredName = StringConvert.encode(prpLclaimDto.getInsuredName());
 	if (intCtextCountTmp < 16) {
 %>

    <jsp:include page="/common/pub/UIErrorPage.jsp">
      <jsp:param name="Picture" value="F" />
      <jsp:param name="Content" value="结案报告中已经完全带出，无需列印附页!" />
    </jsp:include>


<%
	return;
	}
%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns="http://www.w3.org/TR/REC-html40">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF">
	<form name="fm">
		<p height="40" align=top style="font-family: 宋体; font-size: 14pt;">
			<B> 机动车辆保险结案报告书附页 </b>
		</p>
		<br>
		<div align="left">
			<table border="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="92%" id="AutoNumber1">
				<tr>
					<td width="33%">
						承保公司：<%=strName%></td>
					<td width="33%"></td>
					<td width="33%">
						赔案编号：<%=strClaimNo%></td>
				</tr>
				<tr>
					<td width="33%">
						被保险人：<%=strInsuredName%></td>
					<td width="33%">
						保单号：<%=strPolicyNo%></td>
					<td width="33%">
						归档编号：<%=strCaseNo%></td>
				</tr>
			</table>
		</div>
		<hr>
		<fonttext-align:center">经过（第一现场  第二现场）定损，本次事故核定损失如下：（按险种列明）</font>
			<br>
			<table border="0" cellspacing="0" style="border-collapse: collapse" width="92%">
				<tr height="210" valign=top>
					<input type="hidden" name="CtextCountTmp" value="<%=intCtextCountTmp%>">
					<td><%=strContext%></td>
				</tr>
			</table>
			<%-- include打印按钮 --%>
			<jsp:include page="/common/print/PrintButton.jsp" />
	</form>
</body>
</b>
</html>