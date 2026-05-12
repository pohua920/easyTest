<%--
****************************************************************************
* DESC       ：赔款计算书打印页面
* AUTHOR     ： 项目组
* CREATEDATE ：2005-9-21
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%@include file="FreightCompensateNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>赔款计算书清单列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<%
		//货运险计算书
		if (strClassCode.equals("09") || strClassCode.equals("10")) {
	%>
	<!-- 标题部分 -->
	<form name="fm">
		<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height="40">
				<td colspan="2" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
					<img src="/claim/images/LOGO.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align=center style="font-size: 14pt">
					<B><%=strRiskName%>赔款计算书 <B>
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td id="tdCompensateNo" align="right" style="font-size: 10pt">
					<font size="2">计算书号 ：</font>
				</td>
			</tr>
		</table>
		<table class="size11" border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111">
			<tr>
				<td style="font-size: 10pt" width="15%" height="25">被保险人</td>
				<td style="font-size: 10pt" width="30%" id="tdInsuredName" colspan="2">&nbsp;</td>
				<td style="font-size: 10pt" width="15%">立 案 号</td>
				<td style="font-size: 10pt" width="30%" id="tdClaimNo" colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="25">保险单号码</td>
				<td style="font-size: 10pt" id="tdPolicyNo" colspan="2">&nbsp;</td>
				<td style="font-size: 10pt">保险期限</td>
				<td style="font-size: 10pt" colspan="2"><%=strInsuredDateTime%></td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="25">保险金额</td>
				<td style="font-size: 10pt" id="tdSumAmount" colspan="2"><%=prpLclaimDto.getCurrency()%><%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%></td>
				<td style="font-size: 10pt">起运日期</td>
				<td style="font-size: 10pt" colspan="2"><%=StartDate%></td>
				<td style="font-size: 10pt" style="display:none">险 别</td>
				<td style="font-size: 10pt" id="tdKindName" style="display:none">&nbsp;</td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="25">批单号码</td>
				<td style="font-size: 10pt" colspan="2">&nbsp;</td>
				<td style="font-size: 10pt">出险地点</td>
				<td style="font-size: 10pt" colspan="2" id="tdDamageAddress"><%=prpLclaimDto.getDamageAddress()%></td>
			</tr>
			<tr style="display:" height="25">
				<td style="font-size: 10pt">运输路线</td>
				<td style="font-size: 10pt" colspan="2" id="tdLindline">&nbsp;</td>
				<td style="font-size: 10pt" width="20%">保险标的</td>
				<td style="font-size: 10pt" width="30%" id="tdCount" colspan="2"><%=strCount%></td>
			</tr>
			<tr style="display:" height="25">
				<td style="font-size: 10pt">出险日期</td>
				<td style="font-size: 10pt" colspan="2"><%=strDamageStartDate%>&nbsp;
				</td>
				<td style="font-size: 10pt" width="20%">货物名称</td>
				<td style="font-size: 10pt" width="30%" colspan="2">
			</tr>
			<tr style="display:" height="25">
				<td style="font-size: 10pt">出险原因</td>
				<td style="font-size: 10pt" colspan="2" id="tdDamageTypeName">&nbsp;</td>
				<td style="font-size: 10pt" width="20%">货物数量</td>
				<td style="font-size: 10pt" width="30%" colspan="2">
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">出险原因</td>
				<td style="font-size: 10pt" colspan="2" id="tdDamageTypeName">&nbsp;</td>
				<td style="font-size: 10pt" rowspan="2">理赔代理人</td>
				<td style="font-size: 10pt" rowspan="2" id="tdCheckAgentName">&nbsp;</td>
				<td style="font-size: 10pt" id="tdSumPrePaid"></td>
				<td style="font-size: 10pt" id="tdSumThisPaid"></td>
				<td style="font-size: 10pt" id="tdConveyance"></td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="450px" align="center">
					<p>
						赔<br>款<br>计<br>算<br>方<br>式
					</p>
				</td>
				<td style="font-size: 10pt" colspan="5" id="tdContext" valign="top">
					<pre>
        <%=strContext%>
        </pre>
				</td>
			</tr>
			<tr>
				<td style="font-size: 10pt" colspan="2" height="25">金额合计（大写）人民币</td>
				<td style="font-size: 10pt" id="tdCSumPaid" colspan="4"></td>
				<td style="font-size: 10pt" id="tdSumPaidCNY" style="display:none">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">赔款核定</td>
				<td style="font-size: 10pt" width="93">金额</td>
				<td style="font-size: 10pt" width="94">币种</td>
				<td style="font-size: 10pt" colspan="3" align="center">付 款 说 明</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">保品损失</td>
				<td style="font-size: 10pt" id="tdSumRealpay"><%=strSumRealPayShow%></td>
				<td style="font-size: 10pt" id="tdCurrency4"><%=strCurrency4Show%></td>
				<td style="font-size: 10pt" colspan="3" rowspan="8">
					<textarea rows="10" cols="150" class=readonly readonly White style="overflow: hidden; width: 100%" style="font-size:10pt"><%=strContext2%></textarea>
				</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">第三者赔付</td>
				<td style="font-size: 10pt" id="tdChargeAmount1">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY1">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">施救整理费用</td>
				<td style="font-size: 10pt" id="tdChargeAmount2">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY2">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">代 理 费</td>
				<td style="font-size: 10pt" id="tdChargeAmount3">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY3">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">检 验 费</td>
				<td style="font-size: 10pt" id="tdChargeAmount4">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY4">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">查 勘 费</td>
				<td style="font-size: 10pt" id="tdChargeAmount5">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY5">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">其 它</td>
				<td style="font-size: 10pt" id="tdChargeAmount6">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY6">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">合 计</td>
				<td style="font-size: 10pt"></td>
				<td style="font-size: 10pt"><%=strCurrency1%></td>
			</tr>
			<tr>
				<td rowspan="3" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;编制人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td rowspan="3" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;分公司核赔人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td align="center" colspan="4" height="23">&nbsp;赔款支出</td>
			</tr>
			<tr height="23">
				<td align="left" width="11%">&nbsp;保品损失</td>
				<td align="left" id="tdSumRealpay"><%=strSumRealPayShow%>&nbsp;<%
					//=strCurrency4Show
				%>
				</td>
			</tr>
			<tr height="23">
				<td align="left">&nbsp;查勘费用</td>
				<td align="left" id="tdChargeAmount5">&nbsp;</td>
			</tr>
			<tr>
				<td rowspan="5" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;分公司首席核赔人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td rowspan="5" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="50">
							<td width="33%" align="left" valign="top">&nbsp;总公司核赔人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td align="left" height="23">&nbsp;代理费用</td>
				<td align="left" id="tdChargeAmount3">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">&nbsp;鉴定费用</td>
				<td align="left" id="tdChargeAmount4">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">施救整理费用</td>
				<td align="left" id="tdChargeAmount2">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">其它费用</td>
				<td align="left" id="tdChargeAmount6">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">总&nbsp;&nbsp;&nbsp;计</td>
				<td align="left" id="tdSumPaid"><%=strCurrency1%></td>
			</tr>
		</table>
		<table border=0 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;" style="display:none">
			<tr>
				<td style="font-size: 10pt">理算：_______</td>
				<td style="font-size: 10pt">经办：_______</td>
				<td style="font-size: 10pt">核赔：_______</td>
				<td style="font-size: 10pt">核赔：_______</td>
			</tr>
			<tr>
				
				<td style="font-size: 10pt">____年____月____日</td>
				<td style="font-size: 10pt">____年____月____日</td>
				<td style="font-size: 10pt">____年____月____日</td>
				<td style="font-size: 10pt">____年____月____日</td>
			</tr>
		</table>
	</form>
	<%
		} else {
			//非货运险计算书
	%>
	<!--以下为非货运险计算书-->
	<!-- 标题部分 -->
	<form name="fm">
		<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height="40">
				<td colspan="2" height="40" align="center" style="font-family: 宋体; font-size: 14pt;">
					<img src="/claim/images/LOGO.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align=center style="font-size: 14pt">
					<B><%=strRiskName%>赔款计算书 <B>
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td id="tdCompensateNo" align="right" style="font-size: 10pt">
					<font size="2">计算书号 ：</font>
				</td>
			</tr>
		</table>
		<table class="size11" border=1 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111">
			<tr>
				<td style="font-size: 10pt" width="15%" height="25">被保险人</td>
				<td style="font-size: 10pt" width="30%" id="tdInsuredName" colspan="2">&nbsp;</td>
				<td style="font-size: 10pt" width="15%">立 案 号</td>
				<td style="font-size: 10pt" width="30%" id="tdClaimNo" colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="25">保险单号码</td>
				<td style="font-size: 10pt" id="tdPolicyNo" colspan="2">&nbsp;</td>
				<td style="font-size: 10pt">保险期限</td>
				<td style="font-size: 10pt" colspan="2"><%=strInsuredDateTime%></td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="25">保险金额</td>
				<td style="font-size: 10pt" id="tdSumAmount" colspan="2"><%=prpLclaimDto.getCurrency()%><%=new DecimalFormat("#,##0.00").format(prpLclaimDto.getSumAmount())%></td>
				<td style="font-size: 10pt">起运日期</td>
				<td style="font-size: 10pt" colspan="2"><%=StartDate%></td>
				<td style="font-size: 10pt" style="display:none">险 别</td>
				<td style="font-size: 10pt" id="tdKindName" style="display:none">&nbsp;</td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="25">批单号码</td>
				<td style="font-size: 10pt" colspan="2">&nbsp;</td>
				<td style="font-size: 10pt">出险地点</td>
				<td style="font-size: 10pt" colspan="2" id="tdDamageAddress"><%=prpLclaimDto.getDamageAddress()%></td>
			</tr>
			<tr style="display:" height="25">
				<td style="font-size: 10pt">运输路线</td>
				<td style="font-size: 10pt" colspan="2" id="tdLindline">&nbsp;</td>
				<td style="font-size: 10pt" width="20%">保险标的</td>
				<td style="font-size: 10pt" width="30%" id="tdCount" colspan="2"><%=strCount%></td>
			</tr>
			<tr style="display:" height="25">
				<td style="font-size: 10pt">出险日期</td>
				<td style="font-size: 10pt" colspan="2"><%=strDamageStartDate%>&nbsp;
				</td>
				<td style="font-size: 10pt" width="20%">货物名称</td>
				<td style="font-size: 10pt" width="30%" colspan="2">
			</tr>
			<tr style="display:" height="25">
				<td style="font-size: 10pt">出险原因</td>
				<td style="font-size: 10pt" colspan="2" id="tdDamageTypeName">&nbsp;</td>
				<td style="font-size: 10pt" width="20%">货物数量</td>
				<td style="font-size: 10pt" width="30%" colspan="2">
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">出险原因</td>
				<td style="font-size: 10pt" colspan="2" id="tdDamageTypeName">&nbsp;</td>
				<td style="font-size: 10pt" rowspan="2">理赔代理人</td>
				<td style="font-size: 10pt" rowspan="2" id="tdCheckAgentName">&nbsp;</td>
				<td style="font-size: 10pt" id="tdSumPrePaid"></td>
				<td style="font-size: 10pt" id="tdSumThisPaid"></td>
				<td style="font-size: 10pt" id="tdConveyance"></td>
			</tr>
			<tr>
				<td style="font-size: 10pt" height="450px" align="center">
					<p>
						赔<br>款<br>计<br>算<br>方<br>式
					</p>
				</td>
				<td style="font-size: 10pt" colspan="5" id="tdContext" valign="top">
					<pre>
        <%=strContext%>
        </pre>
				</td>
			</tr>
			<tr>
				<td style="font-size: 10pt" colspan="2" height="25">金额合计（大写）人民币</td>
				<td style="font-size: 10pt" id="tdCSumPaid" colspan="4"></td>
				<td style="font-size: 10pt" id="tdSumPaidCNY" style="display:none">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">赔款核定</td>
				<td style="font-size: 10pt" width="93">金额</td>
				<td style="font-size: 10pt" width="94">币种</td>
				<td style="font-size: 10pt" colspan="3" align="center">付 款 说 明</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">保品损失</td>
				<td style="font-size: 10pt" id="tdSumRealpay"><%=strSumRealPayShow%></td>
				<td style="font-size: 10pt" id="tdCurrency4"><%=strCurrency4Show%></td>
				<td style="font-size: 10pt" colspan="3" rowspan="8">
					<textarea rows="10" cols="150" class=readonly readonly White style="overflow: hidden; width: 100%" style="font-size:10pt"><%=strContext2%></textarea>
				</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">第三者赔付</td>
				<td style="font-size: 10pt" id="tdChargeAmount1">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY1">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">施救整理费用</td>
				<td style="font-size: 10pt" id="tdChargeAmount2">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY2">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">代 理 费</td>
				<td style="font-size: 10pt" id="tdChargeAmount3">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY3">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">检 验 费</td>
				<td style="font-size: 10pt" id="tdChargeAmount4">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY4">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">查 勘 费</td>
				<td style="font-size: 10pt" id="tdChargeAmount5">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY5">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">其 它</td>
				<td style="font-size: 10pt" id="tdChargeAmount6">&nbsp;</td>
				<td style="font-size: 10pt" id="tdChargeAmountCNY6">&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td style="font-size: 10pt">合 计</td>
				<td style="font-size: 10pt"></td>
				<td style="font-size: 10pt"><%=strCurrency1%></td>
			</tr>
			<tr>
				<td rowspan="3" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="55">
							<td width="33%" align="left" valign="top">&nbsp;编制人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td rowspan="3" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="55">
							<td width="33%" align="left" valign="top">&nbsp;分公司核赔人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td align="center" colspan="4" height="23">&nbsp;赔款支出</td>
			</tr>
			<tr height="23">
				<td align="left" width="11%">&nbsp;保品损失</td>
				<td align="left" id="tdSumRealpay"><%=strSumRealPayShow%>&nbsp;<%
					//=strCurrency4Show
				%>
				</td>
			</tr>
			<tr height="23">
				<td align="left">&nbsp;查勘费用</td>
				<td align="left" id="tdChargeAmount5">&nbsp;</td>
			</tr>
			<tr>
				<td rowspan="5" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="90">
							<td width="33%" align="left" valign="top">&nbsp;分公司首席核赔人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td rowspan="5" colspan="2">
					<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
						<tr height="90">
							<td width="33%" align="left" valign="top">&nbsp;总公司核赔人意见：</td>
						</tr>
						<tr height="23">
							<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
				<td align="left" height="23">&nbsp;代理费用</td>
				<td align="left" id="tdChargeAmount3">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">&nbsp;鉴定费用</td>
				<td align="left" id="tdChargeAmount4">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">施救整理费用</td>
				<td align="left" id="tdChargeAmount2">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">其它费用</td>
				<td align="left" id="tdChargeAmount6">&nbsp;</td>
			</tr>
			<tr height="23">
				<td align="left">总&nbsp;&nbsp;&nbsp;计</td>
				<td align="left" id="tdSumPaid"><%=strCurrency1%></td>
			</tr>
		</table>
		<table border=0 width="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;" style="display:none">
			<tr>
				<td style="font-size: 10pt">理算：_______</td>
				<td style="font-size: 10pt">经办：_______</td>
				<td style="font-size: 10pt">核赔：_______</td>
				<td style="font-size: 10pt">核赔：_______</td>
			</tr>
			<tr>
				
				<td style="font-size: 10pt">____年____月____日</td>
				<td style="font-size: 10pt">____年____月____日</td>
				<td style="font-size: 10pt">____年____月____日</td>
				<td style="font-size: 10pt">____年____月____日</td>
			</tr>
		</table>
	</form>
	<%
		}
	%>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>