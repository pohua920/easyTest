<%--
****************************************************************************
* DESC       ：出险後抄单打印附加及特约信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintSubIni.jsp"%>
<html>
<head>
</head>
<body>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="2">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2" valign="top">
				<b>附加险：</b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center" style="font-family: 宋体; font-size: 11pt;">
					<tr>
						<!-- 
       	  <!%if("CopyPrintNew".equals(printType)){ %>
       	   -->
						<td align="center" width="25%">名称</td>
						<td align="center" width="25%">标的项目</td>
						<td align="center" width="25%">标的名称</td>
						<td align="center" width="25%">保额/限额</td>
						<!-- 
          <!%}else{ %>
            <td width="5%"></td>
            <td align="center" width="65%" > 险别名称</td>
            <td align="center" width="15%" > 保额 </td>
            <td align="center" width="15%" > 免赔额</td>
          <!%} %>
          -->
					</tr>
					<tr>
						<td colspan="4">
							<hr align="center" width="90%" size="1" />
						</td>
					</tr>
					<%
						for (i = 0; i < strKindName.length; i++) {
							if ("".equals(strItemKindFlag[i]) == false) {
								if (strItemKindFlag[i].substring(1, 2).equals("2")) {
					%>
					<tr>
						<!-- 
             <!%if("CopyPrintNew".equals(printType)){ %>
              -->
						<td align="center"><%=strKindName[i]%></td>
						<td align="center"><%=strItemCode[i]%></td>
						<td align="center"><%=strItemdetailName[i]%></td>
						<td align="center"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
						<!-- 
             <!%}else{ %>
              <td> </td>
              <td align="left"><%=strKindName[i]%></td>
              <td align="center"><%=new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
              <td align="center"><%=dbDeductible[i]%></td>
             <!%} %>
             -->
					</tr>
					<%
						}
							}
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<%
				if ("CopyPrintNew".equals(printType)) {
			%>
		
		<tr>
			<td>
				<b>特别约定：</b>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<pre><%=strEngage%> </pre>
			</td>
		</tr>
		<%
			}
		%>
		</tr>
	</table>
</body>
</html>