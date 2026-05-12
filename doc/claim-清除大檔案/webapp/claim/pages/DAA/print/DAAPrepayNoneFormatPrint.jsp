<%--
****************************************************************************
* DESC       ：机动车辆保险预付赔款审批表列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16 
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAAPrepayNoneFormatPrintIni.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	if (configCode.equals("RISKCODE_DAZ")) {
%>
<title>机动车交通事故责任交强险垫付赔款审批表列印</title>
<%
	} else {
%>
<title>机动车辆保险预付赔款审批表列印</title>
<%
	}
%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td align="center" colspan="2" height="40" style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<%
					if (configCode.equals("RISKCODE_DAZ")) {
				%>
				<B>机动车交通事故责任交强险垫付赔款审批表</B>
				<%
					} else {
				%>
				<B>机动车辆保险预付赔款审批表</B>
				<%
					}
				%>
			</td>
		</tr>
		<tr>
			<td align=left id="tdCompany" width="50%" style="font-family: 宋体; font-size: 10pt;">填报单位（签章）：</td>
			<td align=right width="50%" style="font-family: 宋体; font-size: 10pt;">
				<%
					if (configCode.equals("RISKCODE_DAZ")) {
				%>
				交强险立案号：
				<%
					} else {
				%>
				商业立案号：
				<%
					}
				%>
				<%=strClaimNo%>
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td align="center" colspan="1" height="28" width="10%">被保险人</td>
			<td height="28" colspan="4" width="40%" id="tdInsuredName">&nbsp;</td>
			<td align="center" colspan="1" height="28" width="10%">
				<%
					if (configCode.equals("RISKCODE_DAZ")) {
				%>
				交强险保单号：
				<%
					} else {
				%>
				商业险保单号：
				<%
					}
				%>
			</td>
			<td height="28" colspan="3" width="30%" id="tdPolicyNo">&nbsp;</td>
		</tr>
		<tr>
			<td align="center" colspan="1" height="28" width="10%">厂牌型号</td>
			<td height="28" colspan="2" width="20%"><%=brandName%>&nbsp;
			</td>
			<td align="center" colspan="1" height="25" width="10%">号牌号码</td>
			<td height="28" colspan="2" width="20%"><%=licenseNo%>&nbsp;
			</td>
			<%
				if (configCode.equals("RISKCODE_DAZ")) {
			%>
			<td align="center" colspan="1" height="28" width="10%">赔款性质</td>
			<td height="28" colspan="2" width="30%"><%=castType%>&nbsp;
			</td>
			<%
				} else {
			%>
			<td align="center" colspan="1" height="28" width="10%">保险金额</td>
			<td height="28" colspan="2" width="30%"><%=sumAmount%>&nbsp;
			</td>
			<%
				}
			%>
		</tr>
		<tr>
			<td align="center" colspan="1" height="28">出险时间</td>
			<td height="28" colspan="2"><%=strDamageStartDate%>&nbsp;
			</td>
			<%
				if (configCode.equals("RISKCODE_DAZ")) {
			%>
			<td align="center" colspan="2" height="28">出险地点</td>
			<td height="28" colspan="4"><%=damageAddress%>&nbsp;
			</td>
			<%
				} else {
			%>
			<td align="center" colspan="1" height="28">出险险种</td>
			<td height="28" colspan="2"><%=riskName%>&nbsp;
			</td>
			<td align="center" colspan="1" height="28">出险地点</td>
			<td height="28" colspan="2"><%=damageAddress%>&nbsp;
			</td>
			<%
				}
			%>
		</tr>
		<tr>
			<%
				if (configCode.equals("RISKCODE_DAZ")) {
			%>
			<td align="center" height="28">保险期间</td>
			<td height="28" colspan="8"><%=strInsuredDate%></td>
			<%
				} else {
			%>
			<td align="center" height="28">保险期间</td>
			<td height="28" colspan="4"><%=strInsuredDate%></td>
			<td align="center" height="28">估损金额</td>
			<td height="28" colspan="3"><%=estimateLoss%>&nbsp;
			</td>
			<%
				}
			%>
		</tr>
		<%
			if (configCode.equals("RISKCODE_DAZ")) {
		%>
		<tr>
			<td align="left" height="28" colSpan=3 id="tdSumPrePaid">&nbsp;通知预付、垫付的交警单位名称</td>
			<td height="28" colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" height="28" colSpan=3 id="tdSumPrePaid">&nbsp;与本公司结算的医疗单位名称</td>
			<td height="28" colspan="6">&nbsp;</td>
		</tr>
		<%
			}
		%>
		<tr>
			<td align="left" height="28" colSpan=9>
				&nbsp;<%=strCSumPrePaid%></td>
		</tr>
		<tr>
			<td height="160" valign=top colSpan=9>
				&nbsp;事故概况：<%=registReason%></td>
		</tr>
		<tr>
			<td height="160" valign="top" colSpan=9>
				<table width="100%" height="100%" border=0 style="font-family: 宋体; font-size: 10pt;">
					<tr height="70%">
						<td valign="top" colspan="5">
							&nbsp;预付、垫付原因：<%=prepayReason%></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="160" valign="top" colSpan=9>
				<table width="100%" height="100%" border=0 style="font-family: 宋体; font-size: 10pt;">
					<tr height="70%">
						<td valign="top" colspan="5">&nbsp;三级机构意见：</td>
					</tr>
					<tr height="20%">
						<td width="60%">&nbsp;</td>
						<td align="left" width="40%">经（副）理：</td>
					</tr>
					<tr height="10%">
						<td>&nbsp</td>
						<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="160" valign="top" colSpan=9>
				<table width="100%" height="100%" border=0 style="font-family: 宋体; font-size: 10pt;">
					<tr height="70%">
						<td valign="top" colspan="5">&nbsp;二级机构意见：</td>
					</tr>
					<tr height="20%">
						<td width="60%">&nbsp;</td>
						<td align="left" width="40%">经（副）理或被授权人：</td>
					</tr>
					<tr height="10%">
						<td>&nbsp</td>
						<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- 结尾部分 -->
	<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td width="50%" height="20" valign="bottom" colspan=5>
				填报人：<%=strUserName%></td>
			<td width="50%" height="20" valign="bottom" colspan=4 align="right">
				填报时间：<%=strInputDate%></td>
		</tr>
	</table>
	<%-- include列印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
