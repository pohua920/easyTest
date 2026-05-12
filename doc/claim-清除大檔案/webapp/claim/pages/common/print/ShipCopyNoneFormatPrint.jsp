<%--

****************************************************************************

* DESC       ：出险後抄单打印页面

* AUTHOR     ： wangwei

* CREATEDATE ： 2005-05-27

* MODIFYLIST ：   Name       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="ShipCopyNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>财产保险公司保单抄件</title>
</head>
<body>
	<p align="center">财产保险公司</p>
	<p align="center">****险保单抄件</p>
	<hr align="center" width="90%" size="4" />
	<table align="center" width="90%">
		<tr>
			<td colspan="2">
				报案号：<%=strRegistNo%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险单号：<%=strPolicyNo%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			被保险人名称：<%=strPolicyName%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			被保险人地址：<%=strPolicyAddress%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险金额：<%=strSumAmount%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险标的：<%=strLossName%></td>
		</tr>
		<tr></tr>
		<td colspan="2">保险标的地址：</td>
		</tr>
		<tr></tr>
		<td colspan="2">免赔说明：</td>
		</tr>
		<tr></tr>
		<td>
			投保日期:<%=strStartDate%></td>
		<td>
			輸入日期：<%=strInputDate%></td>
		</tr>
		<tr></tr>
		<td>
			签单日期：<%=strOperateDate%></td>
		<td>
			出单日期：<%=strInputDate%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			保险期间：<%=strEndDate%></td>
		</tr>
		<tr></tr>
		<td colspan="2">
			是否涉及再保、联保、共保：<%=strCoinsFlag%></td>
		</tr>
		<tr></tr>
		<td colspan="2">特别约定：</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr align="center" width="90%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2">附加险：</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="center">名称</td>
						<td align="center">保额/限额</td>
						<td align="center">每次赔偿限额</td>
						<td align="center">说明</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr align="center" width="90%" style="direction: inherit" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr align="center" width="90%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2">批改情况：</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="center">批单号</td>
						<td align="center">批改原因</td>
						<td align="center">批改时间</td>
						<td align="center">核保人</td>
					</tr>
					<tr>
						<td colspan="4">
							<hr width="90%" align="center" style="direction: inherit" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr align="center" width="90%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2">保费到帳情况：</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="center">付款期数</td>
						<td align="center">批单号</td>
						<td align="center">应收</td>
						<td align="center">实收</td>
						<td align="center">到帳日期</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr align="center" width="90%" size="2" />
			</td>
		</tr>
		<tr>
			<td colspan="2">歷史賠付紀錄：</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="90%" align="center">
					<tr>
						<td align="center">归档号</td>
						<td align="center">出险时间</td>
						<td align="center">未决金额</td>
						<td align="center">赔付金额</td>
						<td align="center">结案日期</td>
						<td align="center">核赔人</td>
						<td align="center">赔付次数</td>
					</tr>
					<tr>
						<td colspan="7">
							<hr width="90%" align="center" style="direction: inherit" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr align="center" width="90%" size="4" />
			</td>
		</tr>
		<tr>
			<td>经办人：</td>
			<td>日期：</td>
		</tr>
	</table>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
