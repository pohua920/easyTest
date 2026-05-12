<%--
****************************************************************************
* DESC       ：机动车辆保险拒赔通知书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page errorPage="UIErrorPage"%>
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
	//变量声明部分
	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人
	String strInsureCarFlag = ""; //是否为本保单车辆
	String strLicenseNo = ""; //牌照号码
	DateTime strDamageStartDate = new DateTime(); //出险时间
	String strDamageAddress = ""; //出险地点
	String strDealerCode = "";
	String strContext = "";
	String strMessage = "";
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	int intClaimCount = 0; //ClaimDto对象的记录数
	int intThirdPartyCount = 0;
	int index = 0;

	//得到ClaimDto对象
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	UICodeAction uiCodeAction = new UICodeAction();

	//得到prpLclaimDto对象

	prpLclaimDto = claimDto.getPrpLclaimDto();

	UIRegistAction uiRegistAction = new UIRegistAction();
	RegistDto registDto = uiRegistAction.findByPrimaryKey(prpLclaimDto.getRegistNo());
	PrpLregistDto prpLregistDto = registDto.getPrpLregistDto();

	if (prpLclaimDto != null) {
		strClaimNo = prpLclaimDto.getClaimNo();
		strDealerCode = prpLclaimDto.getDealerCode();
		if (strDealerCode == null || strDealerCode.trim().equals("")) {
			//    throw new UserException(-98,-2013,"UILDAACancelnoticeNoneFormatPrint.jsp");
			//不是据赔的案件
%>
<jsp:include page="/common/pub/UIErrorPage.jsp">
	<jsp:param name="Picture" value="F" />
	<jsp:param name="Content" value="不是拒赔案件!" />
</jsp:include>
<%
	return;
		}
		strPolicyNo = prpLclaimDto.getPolicyNo();

		strInsuredName = prpLclaimDto.getInsuredName();
		strDamageStartDate = prpLclaimDto.getDamageStartDate();
		String strDamageStartDateView = strDamageStartDate.getYear() + " 年 " + strDamageStartDate.getMonth() + " 月 " + strDamageStartDate.getDate() + " 日 ";
		strDamageAddress = prpLclaimDto.getDamageAddress();

		strCode = "";
		strName = "";
		strCode = prpLclaimDto.getMakeCom();
		//dbPrpDcompany.getInfo(strCode); 

		//得到blPrpLthirdParty对象的记录数
		if (registDto.getPrpLthirdPartyDtoList() != null) {
			intThirdPartyCount = registDto.getPrpLthirdPartyDtoList().size();
			for (index = 0; index < intThirdPartyCount; index++) {
				PrpLthirdPartyDto prpLthirdPartyDto = (PrpLthirdPartyDto) registDto.getPrpLthirdPartyDtoList().get(index);
				strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();
				if (strInsureCarFlag.equals("1")) {
					strLicenseNo = prpLthirdPartyDto.getLicenseNo();
				}
			}
		}

		strContext = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;非常遗憾地通知您，根据有关法律与保险合約的规定，本公司保险单<ins> " + strPolicyNo + "</ins> <br><br>项下承保的<ins> " + strLicenseNo + "</ins>机动车辆於<ins> " + strDamageStartDateView + "</ins>在<ins> " + strDamageAddress + "&nbsp;&nbsp;</ins><br><br>发生的事故损失不属於保险责任赔偿范围。对此本公司不能给予赔付，请予理解。";
	}
%>
<html>
<head>
<title><s:text name="title.printBeforeEdit.motorVehiclePrint" /></title>
<%-- 机动车辆保险拒赔通知书打印 --%>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td align="center" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B><s:text name="print.motorVehicleRejectCompensate" /><B> <%-- 机动车辆保险拒赔通知书 --%>
			</td>
		</tr>
		<tr>
			<td align=left style="font-family: 宋体; font-size: 10pt;">
				<br>
				<s:text name="db.prpLregist.insuredName" />
				：<%=strInsuredName%><%-- 被保险人 --%>
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td colspan="2" align="left" valign="top" height="120"><%=strContext%></td>
		</tr>
		<tr>
			<td colspan="2" align="left" height="28">
				<br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.opinion" />
			</td>
			<%-- 欢迎您对本公司的工作提出建设性的意见。 --%>
		</tr>
		<tr>
			<td colspan="2" align="left" height="28">
				<br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.advance" />
			</td>
			<%-- 此致 --%>
		</tr>
		<tr>
			<td align="left" width="50%" height="28">
				<br> <br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.insurSign" />
				：
			</td>
			<%-- 被保险人签收 --%>
			<td align="left" width="50%" height="28">
				<br> <br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.insurerSign" />
				：<%=uiCodeAction.translateComCode(prpLclaimDto.getMakeCom(), isChinese)%></td>
		</tr>
		<%-- 保险人（签章） --%>
		<tr>
			<td align="left" height="28">
				<br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.data" />
				：
				<%-- 日期 --%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.year" />
				<%-- 年 --%>
				&nbsp;&nbsp;&nbsp;
				<s:text name="print.month" />
				<%-- 月 --%>
				&nbsp;&nbsp;&nbsp;
				<s:text name="regist.prpLregist.date" />
				<%-- 日 --%>
			</td>
			<td align="left" height="28">
				<br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.data" />
				：
				<%-- 日期 --%>
				&nbsp;&nbsp;<%=DateTime.current().getYear()%><s:text name="print.year" />
				<%-- 年 --%>
				&nbsp;&nbsp;<%=DateTime.current().getMonth()%><s:text name="print.month" />
				<%-- 月 --%>
				&nbsp;&nbsp;<%=DateTime.current().getDay()%><s:text name="regist.prpLregist.date" />
				<%-- 日 --%>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<hr noshade>
			</td>
		</tr>
	</table>
	<!-- 结尾部分 -->
	<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height="20">
				<br>&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.claimsRejectedNote" />
				：
			</td>
			<%-- 拒赔案件情况备注 --%>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
