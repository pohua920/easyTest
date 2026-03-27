<%--
****************************************************************************
* DESC       ：财产险险代查勘委托书打印页面
* AUTHOR     ：zhuly
* CREATEDATE ：2005-11-15
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
<%@include file="PropLocaleHeresyCheckSubmitNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>财产险代查勘委托书列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name=fm action="/claim/localeHeresyCheck.do" method="post">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height="40">
				<td colspan="2" height="40" align="left" style="font-family: 宋体; font-size: 12pt;">
					<img src="/claim/images/copyprintlogo.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
					<p align="center">
						<b><%=strRiskName%>公估公司委托书</b>
						<input type="hidden" name="riskName" value="<%=strRiskName%>" />
					</p>
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2" style="font-family: 宋体; font-size: 10pt;">
					赔案号：<%=strClaimNo%><input type="hidden" name="claimNo" value="<%=strClaimNo%>" />
				</td>
			</tr>
		</table>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr>
				<td colspan="7" height="35">
					致<%=handleUnitName%><input type="hidden" name="handleUnitName" value="<%=handleUnitName%>" />
					：
				</td>
			</tr>
			<tr>
				<td width="38" height="25">&nbsp;</td>
				<td colspan="6">
					<p>
						本公司承保的<%=strRiskName%>保单已出险，拟委托贵公司针对下列出险标的展开公估事宜。保单承保说明及委托事项如下：
					</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="6">
					<p>A.承保及出险信息：</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td width="9">&nbsp;</td>
				<td colspan="2" width="120px">
					被保险人名称：
					<input type="hidden" name="insuredName" value="<%=StringConvert.encode(prpCmainDto.getInsuredName())%>" />
				</td>
				<td id="tdInsuredName">&nbsp;</td>
				<td>
					保单号码：
					<input type="hidden" name="policyNo" value="<%=strPolicyNo%>" />
				</td>
				<td id="tdPolicyNo"></td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan="2">
					出险地址：
					<input type="hidden" name="damageAddress" value="<%=StringConvert.encode(prpLregistDto.getDamageAddress())%>" />
				</td>
				<td colspan="2" id="tdDamageAddress">&nbsp;</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td width="9">&nbsp;</td>
				<td colspan="2">受损标的：</td>
				<td width="190" id="tdLossName">
					<input type="text" name="lossName" style="border-width: 0; width: 150px; border-bottom: 1 solid black" />
				</td>
				<td>
					出险时间：
					<input type="hidden" name="damageStartDate" value="<%=strDamageStartDate%>" />
				</td>
				<td id="tdDamageStartDate">&nbsp;</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan="2">
					联 系 人：
					<input type="hidden" name="linkerName" value="<%=strLinkerName%>" />
				</td>
				<td id="tdLinkerName">&nbsp;</td>
				<td>
					保险期限：
					<input type="hidden" name="date" value="<%=strDate%>" />
				</td>
				<td id="tdDate">&nbsp;</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan="2">
					联络电话：
					<input type="hidden" name="phoneNumber" value="<%=strPhoneNumber%>" />
				</td>
				<td id="tdPhoneNumber">&nbsp;</td>
				<td>
					保险金额：
					<input type="hidden" name="sumAmount" value="<%=prpCmainDto.getCurrency()%> <%=strSumAmount%>" />
				</td>
				<td id="tdSumAmount">&nbsp;</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="6">
					<p>B. 委托事项（需要委托的，请打 √ ）：</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="0">
				</td>
				<td colspan="4">
					<p>现场查勘：包括事故原因调查、损失金额的核定、理算，出具相关查勘报告；</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="1">
				</td>
				<td colspan="4">
					<p>安排对保险标的损失检验和确定损失金额；</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="2">
				</td>
				<td colspan="4">
					<p>针对事故损失的原因及责任进行检验、鉴定；</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="3">
				</td>
				<td colspan="4">安排保险标的在当地进行修复、定价並收集相关的维修资料与收據；</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="4">
				</td>
				<td colspan="4">协助被保险人对受损物资进行清点、施救；</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="5">
				</td>
				<td colspan="4">
					<p>损馀物资的残值处理；</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="6">
				</td>
				<td colspan="4">
					<p>追偿；</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>&nbsp;</td>
				<td>
					&nbsp;
					<input type="checkbox" name="chooseflag" value="7">
				</td>
				<td>其他：</td>
				<td colspan="3">
					<input type="text" name="others" style="border-width: 0; width: 200px; border-bottom: 1 solid black" />
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="6">
					<p>
						C． 如果估损金额超过
						<input type="text" name="paidLimit" style="border-width: 0; width: 60px; border-bottom: 1 solid black" />
						万元，请书面通知本公司，得到答复後再作处理。
					</p>
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="6">
					D. 本案有关叁考文件共
					<input type="text" name="pageCount" style="border-width: 0; width: 60px; border-bottom: 1 solid black" />
					页，请查收。
				</td>
			</tr>
			<tr>
				<td colspan="7" height="60" align="right" style="font-size: 12pt;">
					财产保险有限公司
					<input type="text" name="comName" style="font-size: 12pt; border-width: 0; width: 60px; border-bottom: 1 solid black" />
					分公司
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="6">（同意接受委托，请附上公估师姓名及联络方式後，公司用章後回传至本公司，谢谢！）</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="3">公估师姓名：</td>
				<td></td>
				<td align="right"></td>
				<td></td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="3">联络电话：</td>
				<td></td>
				<td align="right"></td>
				<td></td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="6">
					<hr size=1 color="black">
				</td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="3">保险公司联系人：</td>
				<td><%=strUserName%></td>
				<td align="right">被保险人/代理人签章：</td>
				<td></td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="3">电话：</td>
				<td><%=phoneNumber%><input type="hidden" name="comPhoneNumber" value="<%=phoneNumber%>" />
				</td>
				<td align="right"></td>
				<td></td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td colspan="3">传&nbsp;&nbsp;真：</td>
				<td><%=faxNumber%><input type="hidden" name="comFaxNumber" value="<%=faxNumber%>" />
				</td>
				<td align="right">联系人姓名：</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="7" height="30">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="7" height="25" align="right"><%=year%>年&nbsp;<%=month%>月&nbsp;<%=day%>日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div align="center" id="saveButton" style="display:">
			<p>
			<table cellpadding="0" cellspacing="0" width="80%" style="display:">
				<tr>
					<td class=button style="width: 33%" align="center">
						<input class="button" type="button" name="toCheck" value="下载委托书" onclick="fm.submit()">
					</td>
				</tr>
			</table>
			</p>
		</div>
		<!-- 按钮部分 -->
		<%-- include打印按钮 --%>
		<jsp:include page="/common/print/PrintButton.jsp" />
		<script language='javascript'>
			function printPage() {
				//add print liudaoping 2013-04-15
				//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
				return false;
				saveButton.style.display = "none";
				divButton.style.display = "none";
				window.print();
			}
		</script>
	</form>
</body>
</html>
