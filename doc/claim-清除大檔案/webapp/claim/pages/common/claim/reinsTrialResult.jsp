<%--
****************************************************************************
* DESC       : 分摊试算结果
* AUTHOR     : 
* CREATEDATE : 2013-02-22
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claiminterface.reins.dto.custom.ReinsRepayCalResult"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.Double"%>
<%@ page import="java.text.DecimalFormat"%>
<html>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/meta_js.jsp"%>
	<head>
		<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
		<title><s:text name="title.claimBeforeEdit.shareTrialResult" /></title><%--分摊试算结果--%>
		<script src="${ctx}/common/js/showpage.js"></script>
	</head>
	<body class="interface">
		<table border="0" align="center" cellpadding="3" cellspacing="1" class="common">
			<%
				Collection reinsRepayCalResultCollection = (Collection) request.getAttribute("reinsRepayCalResultCollection");
				String dangernoflag = "";
				boolean isReins = false;
				if (reinsRepayCalResultCollection != null) {
					for (Iterator i = reinsRepayCalResultCollection.iterator(); i.hasNext();) {

						ReinsRepayCalResult reinsRepayCalResult = (ReinsRepayCalResult) i.next();
						String dangerno = String.valueOf(reinsRepayCalResult.getDangerNo());
						String modeName = reinsRepayCalResult.getReinsModeName();
						DecimalFormat idecimalFormat = new DecimalFormat("0.000000");
						String shareRate = String.valueOf(Double.parseDouble(idecimalFormat.format(reinsRepayCalResult.getShareRate())));
						java.text.NumberFormat numberFormatnf = java.text.NumberFormat.getInstance();
						numberFormatnf.setGroupingUsed(false);
						String sumPaid2 = String.valueOf(numberFormatnf.format(reinsRepayCalResult.getSumPaid()));

						if (!isReins) {
							if (modeName != null && "临分".equals(modeName.trim())) {
								isReins = true;
							}
						}
						String sumpaid = "";

						int b1 = sumPaid2.indexOf(".");
						int c1 = sumPaid2.length();
						if (b1 != -1) {
							if (b1 + 3 <= c1) {
								sumpaid = sumPaid2.substring(0, b1 + 3);
							} else {
								sumpaid = sumPaid2.substring(0, c1);
								sumpaid = sumpaid + "0";
							}
						} else {
							sumpaid = sumPaid2 + "";
						}
						if (!dangernoflag.equals(dangerno)) {
			%>
			<tr class="title">
				<td colspan=3 class="subformtitle" align=center>
					<br><font size="3"><b><s:text name="claim.dangeSerialNum" /><%=dangerno%></b></font><br><%--危险单位序号--%>
				</td>
			</tr>
			<tr class="title">
				<td class="subformtitle"><s:text name="claim.whichWay" /></td><%--分保方式--%>
				<td class="subformtitle"><s:text name="claim.percentage" /></td><%--比例％--%>
				<td class="subformtitle"><c:out value="${requestScope.reinsTrial}" /><s:text name="db.prpLassureDetail.amount" /></td><%--金额--%>
			</tr>
			<%
				dangernoflag = dangerno;
						}
			%>
			<tr class="input">
				<td class="input"><%=modeName%></td>
				<td class="input" align="center"><%=shareRate%>&nbsp;&nbsp;</td>
				<td class="input" align="right"><%=sumpaid%>&nbsp;&nbsp;</td>
			</tr>
			<%
				}
				}
			%>
			<tr class="title">
				<td colspan=3 align=center>
					<br><br><input type="button" class="button" value="<s:text name='button.close.value' />" onclick="window.close()"><br>
				</td>
			</tr>
		</table>
	</body>
	<script language="javascript">
<%if (isReins) {%>
        alert("該業務涉及臨時分出，請盡快通知公司相關險種核保人，進行臨時賠案通知及攤賠處理！");  
<%}%>
</script>
</html>