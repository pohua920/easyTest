<%--
****************************************************************************
* DESC       ：承诺支付医疗费用担保函打印页面
* AUTHOR     ：zhaolu
* CREATEDATE ：2006-06-12
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gbk" %>

<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
  <%-- 初始化 --%>
<%@include file="DAAPersonDamageInvesReportNoneFormatPrintIni.jsp"%>
<%
	Calendar calendar = new GregorianCalendar();

	String strGetYear = strdamageStartDate.substring(0, 4);

	String strGetMonth = strdamageStartDate.substring(5, 7);

	String strGetDate = strdamageStartDate.substring(8, 10);

	String currYear = Integer.toString(calendar.get(Calendar.YEAR));

	String currMonth = Integer
			.toString(calendar.get(Calendar.MONTH) + 1);

	String currDay = Integer.toString(calendar
			.get(Calendar.DAY_OF_MONTH));
%>
<html>
<head>
<title>承诺支付医疗费用担保函</title>
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
		<table width="52%" border="0" align="center" cellpadding="0" cellspacing="0" style="font-family: 宋体; font-size: 10pt;">
			<tr height=30>
				<td colspan="2" height="40" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
					<span><img src="/claim/images/LOGO.jpg" /></span>
				</td>
			</tr>
			<tr height=30>
				<td colspan="2" align=center style="font-family: 宋体; font-size: 14pt;">
					<B>
						<center>
							承诺支付医疗费用担保函
							<center>
								<B> </b>
							</center>
						</center>
					</b><br>
					<br>
				</td>
			</tr>
			<tr height=20>
				<td width="48%" height="25" align=left>
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 150px; text-align: center" value="   <%=strHospital%>">
					:
				</td>
			</tr>
			<tr height=20>
				<td height="25" colspan="2" align=center>
					&nbsp;本公司
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 150px; text-align: center" value="<%=strPolicyNo%>">
					项下承保的车辆
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 90px; text-align: center" value="<%=strLicenseNo%>">
					,<br>
					<br>於
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 30px; text-align: center" value="<%=strGetYear%>">
					年
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 20px; text-align: center" value="<%=strGetMonth%>">
					月
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 20px; text-align: center" value="<%=strGetDate%>">
					日发生事故,致
					<input type=text name="prplCommission" readonly="true" class="printline" style="width: 100px; text-align: center" value="<%=strD1%>">
					受伤,在你医院抢救,<br>
					<br>&nbsp;依据<
					<道路交通事故人员创伤诊疗指南>>和国家基本医疗保险标准,在医<br>
					<br>
					疗费用赔偿限额内支付<input type=text name="prplCommission" readonly="true" class="printline" style="width: 100px; text-align: center" value="<%=strD1%>">的抢救费用.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			<tr height=20>
				<td height="25" colspan="2" align=right>
					<br>
					<br>
					<br>
					<br>
					<br>保险人盖章:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr height=20>
				<td height="25" colspan="2" align=right>
					<br>
					<br><%=currYear + "       "%>年<%="       " + currMonth + "       "%>月<%="       " + currDay + "       "%>日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp
				</td>
			</tr>
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
