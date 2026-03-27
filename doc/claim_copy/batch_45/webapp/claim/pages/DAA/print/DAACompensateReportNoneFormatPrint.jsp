<%--
****************************************************************************
* DESC       ：机动车辆保险赔款计算书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：22004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="DAACompensateReportNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险理算报告书</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#FFFFFF">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="60"></td>
			</tr>
			<tr>
				<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 16pt;">
					<p align="center">
						<B><img src="/claim/images/LOGO.jpg" /><B>
					</p>
					<p align="center">
						<B>机动车辆保险理算报告书<B>
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="3" height="30"></td>
			</tr>
			<tr>
				<td width="40%" align=left id="tdInsureCompany" style="font-family: 宋体; font-size: 9pt;">
					承保公司：<%=strCompany%>
				</td>
				<td width="30%" align=left id="tdInsuredName" style="font-family: 宋体; font-size: 9pt;">
					被保险人：<%=strInsuredName%>
				</td>
				<td width="40%" align=left id="tdPolicyNo" style="font-family: 宋体; font-size: 9pt;">
					保险单号：<%=strPolicyNo%>
				</td>
			</tr>
			<tr>
				<td width="40%" align=left id="tdClaimNo" style="font-family: 宋体; font-size: 9pt;">
					立案编号：<%=strClaimNo%>
				</td>
				<td width="30%" align=left id="tdRegistNo" style="font-family: 宋体; font-size: 9pt;">
					报案编号：<%=strRegistNo%>
				</td>
				<td width="40%" align=left id="tdCompensateNo" style="font-family: 宋体; font-size: 9pt;">
					赔款计算书号：<%=strCompensateNo%>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<hr noshade>
				</td>
			</tr>
		</table>
		<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:12pt;">
			<tr>
				<td colspan="3" align="left" valign="top" height="60"font-size:12pt;><%=strFirstDuanluo%></td>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="top" height="60"font-size:12pt;><%=strSecondDuanluo%></td>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="center" height="30"font-size:12pt;>本车承保情况如下：</td>
			</tr>
			<tr>
				<td colspan="1" align="left" valign="top" height="24"font-size:12pt;>
					保险期间：<%=strInsuredTerm%>
				</td>
				<td colspan="1" align="left" valign="top" height="24"font-size:12pt;>
					车辆使用性质：<%=strName%></td>
				<td colspan="1" align="left" valign="top" height="24"font-size:12pt;>
					车架号：<%=strFrameNo%></td>
			</tr>
			<tr>
				<td colspan="1" align="left" valign="top" height="24"font-size:12pt;>
					新车购置价：<%=dblPurchasePrice%>元
				</td>
				<td colspan="1" align="left" valign="top" height="24"font-size:12pt;>
					车辆行驶網域：<%=strRunAreaName%></td>
				<td colspan="1" align="left" valign="top" height="24"font-size:12pt;>
					已使用年限：<%=strUseYears%>年
				</td>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="top" height="35"font-size:12pt;><%=strThirdDuanluo%></td>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="center" height="40"font-size:12pt;><%=strFiveDuanluo%></td>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="top" height="35"font-size:12pt;>
					<table>
						<%=strKindNameOut%></td>
					</table>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="center" height="40"font-size:12pt;>理算结果如下：（按险别列明）</td>
			</tr>
			<tr>
				<td colspan="3" align="left" valign="top" height="35"font-size:12pt;>
					<table>
						<%=strKindNameOut1%></td>
					</table>
			</tr>
			<tr>
				<td colspan="3" align="left" height="28">
					<br>&nbsp;&nbsp;&nbsp;&nbsp;赔款总计：<%=strCSumPaidOut%></td>
			</tr>
			<tr>
				<td colspan="2" align="center" height="28">
					<br> <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报告人：
				</td>
				<td colspan="1" align="right" height="28">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="1" align="center" height="28">&nbsp;</td>
				<td colspan="2" align="center" height="28">
					<br> <br>年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
				</td>
			</tr>
		</table>
	</form>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
