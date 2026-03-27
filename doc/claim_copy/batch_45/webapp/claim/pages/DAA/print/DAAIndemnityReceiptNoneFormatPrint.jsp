<%--
****************************************************************************
* DESC       ：机动车保险赔款收据
* AUTHOR     ：罗畅
* CREATEDATE ：2011-08-31
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%-- 初始化 --%>
<%@include file="DAAIndemnityReceiptNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车保险赔款收据列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script language="javascript">
	function jsPrintPage() {
		//printPage()
		//add print liudaoping 2013-04-15
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		divButton.style.display = "none"

		window.print();
	}
</script>
<style type="text/css">
<!--
.style2 {
	font-size: 10pt
}

.STYLE3 {
	font-size: 10px
}

.STYLE6 {
	font-size: 14px
}
-->
</style>
</head>
<body bgcolor="#FFFFFF" onLoad="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td height="20" align=left style="font-family: 宋体; font-size: 14pt;">
					<img src="/claim/images/claim_logo.jpg" />
				</td>
				<td height="30" align=left style="font-family: 宋体; font-size: 20pt;">
					<br>
					<br>
					<p align=left>
						<B>机动车保险赔款收据<B>
					</p>
					<br>
				</td>
			</tr>
		</table>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					被保险人：<%=strInsuredName%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					保险单号：<%=strPolicyNo%>
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					车牌号码：<%=strLicenseNo%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					赔案编号：<%=strClaimNo%>
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					出险时间：<%=strDamageDate%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					赔款计算书号：<%=strCompensateNo%>
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					赔款金额(大写)：<%=strCSumThisPaid%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					(小写)：<%=strSumThisPaid%>
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					开户银行：<%=strCustomBankName%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					银行帳号：<%=strAccountCode%>
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					收款人名称：<%=strOwnerName%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					收款人证件代码：<%=strCertifiCateCode%>
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					收款人电话：<%=strOwnerPhoneNo%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;"></td>
			</tr>
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					制单人：<%=strOperatorName%>
				</td>
				<td width="50%" align=left style="font-family: 宋体; font-size: 12pt;">
					制单日期：<%=strOperatorDate%>
				</td>
			</tr>
		</table>
		<span id="spbutton" style="WIDTH: 700px; LEFT: 40px; POSITION: absolute; TOP: 500px; font-family: 宋体; font-size: 11pt;">
			<table id='divButton' cellpadding="0" cellspacing="0" width="80%" style="display:">
				<tr>
					<td class=button align="center">
						<input class="button" type="button" name="buttonPrint" value=" 列 印 " onclick="jsPrintPage();">
					</td>
					<td class=button align="center">
						<input class="button" type="button" name="buttonClose" value=" 关 闭 " onclick="javascript:window.close();">
					</td>
				</tr>
			</table>
		</span>
</body>
</html>
