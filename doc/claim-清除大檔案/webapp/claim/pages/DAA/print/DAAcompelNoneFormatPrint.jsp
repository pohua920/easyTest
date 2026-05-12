<%--
****************************************************************************
* DESC       ：机动车辆保险拒赔 /注销报告页面
* AUTHOR     ：zhouliu
* CREATEDATE ：2006-6-12
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="DAAcompelNoneFormatPrintIni.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>机动车交通事故责任强制保险拒赔列印</title>
<style type="text/css">
INPUT.printline {
	FONT-SIZE: 10pt;
	BORDER-TOP: #ececec 0px solid;
	WIDTH: 10px;
	COLOR: #000000;
	BORDER-BOTTOM: #400000 1px solid;
	BORDER-RIGHT-STYLE: none;
	BORDER-LEFT-STYLE: none;
	BACKGROUND-COLOR: #ffffff text-align:center
}
</style>
</head>
<body bgcolor="#FFFFFF">
	<div align="center">
		<table width="100%" border="0" align="center" style="font-family: 宋体; font-size: 10pt;">
			<tr width="100%">
				<td align=center style="font-family: 宋体; font-size: 14pt;">
					<B>
						<center>
							机动车交通事故责任强制保险拒赔通知书
							<center>
				</td>
			</tr>
		</table>
		<br> <br> <br>
		<table width="55%" border="0" align="center" style="font-family: 宋体;" align="center">
			<tr align="left">
				被保险人：
				<input type=text name="insuredName" readonly="true" class="printline" style="width: 200px; text-align: center" value="<%=strInsuredName%>">
			</tr>
			<tr>
				<td width="100%" align="left" colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<div>&nbsp;&nbsp;&nbsp;非常遗憾的通知您,根据有关法律和保险合約的规定,
				</td>
			</tr>
			<tr>
				<td width="100%" align="left" colspan="2">
					本公司保险单
					<input type=text name="policyNo" readonly="true" class="printline" style="width: 180px; text-align: center" value="<%=strPolicyNo%>">
					(号码)项下承保
				</td>
			</tr>
			<tr>
				<td width="100%" align="left" colspan="2">
					的
					<input type=text name="licenseNo" readonly="true" class="printline" style="width: 60px; text-align: center" value="<%=strLicenseNo%>">
					(牌照号码)机动车辆於
					<input type=text name="year" readonly="true" class="printline" style="width: 40px; text-align: center" value="<%=damageDate.getYear()%>">
					年
					<input type=text name="month" readonly="true" class="printline" style="width: 30px; text-align: center" value="<%=damageDate.getMonth()%>">
					月
					<input type=text name="day" readonly="true" class="printline" style="width: 35px; text-align: center" value="<%=damageDate.getDay()%>">
					日
				</td>
			</tr>
			<tr>
				<td width="100%" align="left" colspan="2">
					在
					<input type=text name="damageAddress" readonly="true" class="printline" style="width: 115px; text-align: center" value="<%=strDamageAddress%>">
					(出险地点)发生的事故损失不属於保
				</td>
			</tr>
			<tr>
				<td width="100%" align="left" colspan="2">险责任赔偿范围,对此本公司不能给予赔付,请予理解.</td>
			</tr>
			<tr>
				<td>
					<br> <br> &nbsp;&nbsp;&nbsp;&nbsp;欢迎您对本公司的工作提出意见. <br> <br> <br> &nbsp;&nbsp;&nbsp;&nbsp;此致 <br> <br> <br> <br> <br> <br>
				</td>
			</tr>
			<tr>
				<td align="left">被保险人签收：</td>
				<td align="center">保险人(签章)：</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="200" align="left">
					日期：<%=(curdate.getYear() + 1900)%>年<%=(curdate.getMonth() + 1)%>月<%=(curdate.getDay() + 11)%>日
				</td>
				<td width="200" align="center">
					日期：<%=(curdate.getYear() + 1900)%>年<%=(curdate.getMonth() + 1)%>月<%=(curdate.getDay() + 11)%>日
				</td>
			</tr>
		</table>
	</div>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			divButton.style.display = "none";
			window.print();
		}
	</script>
</body>
</html>
