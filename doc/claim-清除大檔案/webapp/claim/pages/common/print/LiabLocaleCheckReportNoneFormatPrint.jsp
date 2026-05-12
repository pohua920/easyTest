<%--
****************************************************************************
* DESC       ：机动车辆现场查勘记录打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="LiabLocaleCheckReportNoneFormatPrintini.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td colspan="2" height="40" align="left" style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/claim_logo.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 16pt;">
				<p align="center">
					<b><span id="riskName"></span></b>
				</p>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2" id="tdRegistNo">报案编号：</td>
		</tr>
	</table>
	<table border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr align="center">
			<td width="15%" height="25">被保险人</td>
			<td width="35%" id="tdInsuredName">&nbsp;</td>
			<td width="15%">保险单号</td>
			<td width="30%" id="tdPolicyNo">&nbsp;</td>
		</tr>
		<tr align="center">
			<td width="15%" height="25">批单号</td>
			<td width="30%" id="tdEndorseNo">&nbsp;</td>
			<td width="15%">保险期限</td>
			<td width="30%" id="tdDate">&nbsp;</td>
		</tr>
		<tr align="center">
			<td width="15%" height="25">出险地点</td>
			<td width="30%" id="tdDamageAddress">&nbsp;</td>
			<td width="15%">出险原因</td>
			<td width="30%" id="tdDamageClause">&nbsp;</td>
		</tr>
		<tr align="center">
			<td width="15%" height="25">出险标的</td>
			<td width="30%" id="tdLossName">&nbsp;</td>
			<td width="15%">报损金额</td>
			<td width="30%" id="tdEstimateLoss">&nbsp;</td>
		</tr>
		<tr align="center">
			<td width="15%" height="25">报案时间</td>
			<td width="30%" id="tdReportDate">&nbsp;</td>
			<td width="15%">出险时间</td>
			<td width="30%" id="tdDamageStartDate">&nbsp;</td>
		</tr>
		<tr align="center">
			<td width="15%" height="25">联系人</td>
			<td width="30%" id="tdLinkerName">&nbsp;</td>
			<td width="15%">联系方式</td>
			<td width="35%" id="tdPhoneNumber">&nbsp;</td>
		</tr>
		<tr height="300">
			<td colspan="4" align="center">
				<table width="100%" height="100%">
					<tr>
						<td colspan="2" valign="top" height="520">
							<p>请填写本案出险原因、经过以及损失处理情况；估计全案损失並填写查勘人意见。</p>
							<%=strContext%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="left">
				<br> 查勘时间：<%=strCheckDate%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 查勘地点：<%=strCheckSite%>
				<br>
				<br> <br> 查&nbsp;勘&nbsp;人：<%=strChecker1%>
				<%--
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  当事人签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
--%>
				<br>
				<br>
				<br> 主管意见： <br>
				<br>
				<p id="sysDate" style="text-align: right"></p>
			</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
