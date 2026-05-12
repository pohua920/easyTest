<%--
****************************************************************************
* DESC       ：出险後抄单打印历史赔付信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintHistoryIni.jsp"%>
<html>
<head>
</head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="6">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
	</table>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<%
			if ("CopyPrintNew".equals(printType)) {
		%>
		<tr>
			<td colspan="3">
				<b>歷史賠付紀錄：</b>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<table width="100%" align="center" style="font-family: 宋体; font-size: 11pt;">
					<tr>
						<td align="center">赔案号</td>
						<td align="center">出险时间</td>
						<%-- <td align="center">未决金额 </td>--%>
						<td align="center">赔付金额</td>
						<td align="center">结案日期</td>
						<%-- <td align="center">理算人</td>--%>
						<td align="center">核赔人</td>
						<%-- <td align="center">赔付次数</td>--%>
					</tr>
					<%
						for (int j = 0; j < strClaimNo.length; j++) {
					%>
					<tr>
						<td align="center"><%=strClaimNo[j]%></td>
						<td align="center"><%=strDamangeDate[j]%></td>
						<%-- <td align="center"><%= new DecimalFormat("0.00").format(dbSumClaim[j]-dbSumPaid[j]) %></td>--%>
						<td align="center"><%=dbSumPaid[j]%></td>
						<td align="center"><%=arrEndCaseDate[j]%></td>
						<%-- <td align="center"><%= arrHandlerName[j]%> </td>--%>
						<td align="center"><%=arrUnderWriteName[j]%></td>
						<%-- <td align="center">- </td>--%>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td colspan="6">
				<b>歷史賠付紀錄：（已決）</b>
			</td>
		</tr>
		<tr>
			<td align="center" style="width: 20%">保险单号</td>
			<td align="center" style="width: 20%">赔案号</td>
			<td align="center" style="width: 12%">出险时间</td>
			<td align="center" style="width: 10%">出险原因</td>
			<td align="center" style="width: 10%">赔付金额</td>
			<td align="center" style="width: 15%">结案日期</td>
		</tr>
		<%
			int m = 0;
				double dbSumSumPaid = 0; //金额合计
				for (int j = 0; j < strClaimNo.length; j++) {
					if (strEndCaseDate[j] != null && !strEndCaseDate[j].equals("")) {
						dbSumSumPaid = dbSumSumPaid + strSumPaid[j];
						m = m + 1;
						//System.out.println("......sadfsd.....strEndCaseDate[j]=="+strEndCaseDate[j]+"--");
		%>
		<tr>
			<td align="center"><%=strPolicyNo%></td>
			<td align="center"><%=strClaimNo[j]%></td>
			<td align="center"><%=strDamangeDate[j]%></td>
			<td align="center"><%=strDamageName[j]%></td>
			<td align="center"><%=strSumPaid[j]%></td>
			<td align="center"><%=strEndCaseDate[j]%></td>
		</tr>
		<%
			}
				}
		%>
		<tr>
			<td colspan="2">
			<td colspan="2">
				合计出险：<%=m%>次
			</td>
			<td colspan="2">
				金额合计：<%=dbSumSumPaid%></td>
		</tr>
		<tr>
			<td colspan="6">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<b>歷史賠付紀錄：（未決）</b>
			</td>
		</tr>
		<tr>
			<td align="center">保险单号</td>
			<td align="center">赔案号</td>
			<td align="center">出险时间</td>
			<td align="center">出险原因</td>
			<td align="center">估损金额</td>
			<td align="center"></td>
		</tr>
		<%
			dbSumSumPaid = 0;
				m = 0;
				for (int j = 0; j < strClaimNo.length; j++) {
					//System.out.println("...........strEndCaseDate[j]=>>>="+strEndCaseDate[j]+"--");
					if (strEndCaseDate[j] == null || strEndCaseDate[j].equals("")) {
						m = m + 1;
						dbSumSumPaid = dbSumSumPaid + strSumPaid[j];
		%>
		<tr>
			<td align="center"><%=strPolicyNo%></td>
			<td align="center"><%=strClaimNo[j]%></td>
			<td align="center"><%=strDamangeDate[j]%></td>
			<td align="center"><%=strDamageName[j]%></td>
			<td align="center"><%=strSumPaid[j]%></td>
			<td align="center"></td>
		</tr>
		<%
			}
				}
		%>
		<tr>
			<td colspan="2">
			<td colspan="2">
				合计出险：<%=m%>次
			</td>
			<td colspan="2">
				金额合计：<%=dbSumSumPaid%></td>
		</tr>
	</table>
	<%
		}
	%>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr height="5px">
			<td colspan="6">
				<hr align="center" width="100%" height="1px" size="2" />
			</td>
		</tr>
		<tr>
			<td style="width: 45%" colspan="2">&nbsp;</td>
			<td style="width: 25%" colspan="2" align="left">
				经办人：<%=strUserName%></td>
			<td style="width: 30%" align="left" colspan="2">
				日期：<%=mDateTime.toString()%></td>
		</tr>
	</table>
</body>
</html>