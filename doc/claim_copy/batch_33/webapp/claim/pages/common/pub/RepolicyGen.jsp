<%--
****************************************************************************
* DESC       ：生成分保单币种选择页面
* AUTHOR     ：liuyang
* CREATEDATE ：2004-02-05 
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html>
<head>
<app:css />
<title><s:text name="title.pubBeforeEdit.chooseCurrency" />
	<%--选择分保币种--%></title>
<script language="javascript">
	function submitForm() {
		alert("fm" + fm.currency.value);
		parent.fcoPolicyListForm.submit();
	}
</script>
</head>
<body>
	<form name="fm">
		<table class=common border="0" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td width="172" height="24" align="center" background="/claim/images/bgBarBig.gif" class="button">
					<font color="#FFFFFF" style='font-size: 12px'><b>分保币种选择</b></font>
				</td>
				<td width="328" background="/claim/images/bgBarHorizontalLine.gif">&nbsp;</td>
				<td align="right">
					<img src="/claim/images/bgClaimBlueRightNode.gif" width="10" height="24">
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="5" cellspacing="0" class=common>
			<tr>
				<td class=title2 align="center">
					<s:text name="pub.currency" />
					：
					<%--分保币种--%>
					<select name="currency" class=query>
						<option value="USD">USD</option>
						<option value="JPY">JPY</option>
						<option value="HKD">HKD</option>
						<option value="GBP">GBP</option>
						<option value="FRF">FRF</option>
						<option value="EUR">EUR</option>
						<option value="DEM">DEM</option>
						<option value="CNY">CNY</option>
					</select>
				</td>
				<td align="input2">
					<s:text name="pub.insuranceCertificateNo" />
					：
					<%--分保单号--%>
					<input type="text" class=button name="repolicyno" width="40%" value="<s:text name='button.generate.value' />">
				</td>
				<%--待生成--%>
			</tr>
			<table>
				<table class=common>
					<tr>
						<td class="title2" width="50%" align="center">
							<input type="button" class="button" onclick="submitForm();" value="<s:text name='button.generate1.value' />">
						</td>
						<%--生 成--%>
						<td class="title2" width="50%" align="center">
							<input type="reset" class="button" value="<s:text name='button.return.value' />">
						</td>
					</tr>
				</table>
				</form>
</body>
</html:html>
