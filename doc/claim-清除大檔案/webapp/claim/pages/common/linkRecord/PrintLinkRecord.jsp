<%--
****************************************************************************
* DESC       ：货运险赔案终结报告书　
* AUTHOR     ：dongchengliang
* CREATEDATE ：2005-6-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%--@include file="PropCompensateNoticeNoneFormatPrintIni.jsp"--%>
<html>
<head>
<title><s:text name="title.linkRecord.claimRelateRecord" /></title>
<!-- 理赔联系记录 -->
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 18pt;">
				<B><s:text name="print.propertyIns" /> <B>
						<!-- 财产保险有限公司 -->
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B><s:text name="title.linkRecord.claimRelateRecord" /> <B>
						<!-- 理赔联系记录 -->
			</td>
		</tr>
		<tr>
			<td height="40" align=top align=left style="text-align: left; font-family: 宋体; font-size: 10pt;">
				<s:text name="linkRecord.unit" />
				:
				<bean:write name='linkRecordDto' property='comName' filter='true' />
			</td>
			<!-- 单位 -->
			<td height="40" align=top align=center style="text-align: right; font-family: 宋体; font-size: 10pt;">
				<s:text name="print.claimNo" />
				:
				<bean:write name='linkRecordDto' property='claimNo' filter='true' />
			</td>
			<!-- 赔案编号 -->
		</tr>
	</table>
	<table border=1 class=common align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr class=common>
			<td width="13%">
				<s:text name="linkRecord.jiebaoanDate" />
			</td>
			<!-- 接报案日期 -->
			<td width="20%">
				<bean:write name='linkRecordDto' property='reportDate' filter='true' />
			</td>
			<td width="13%">
				<s:text name="linkRecord.clientRelateDate" />
			</td>
			<!-- 与客户联系日期 -->
			<td width="20%">
				<bean:write name='linkRecordDto' property='linkCustomDate' />
			</td>
			<td width="13%">
				<s:text name="linkRecord.copyDate" />
			</td>
			<!-- 抄单日期 -->
			<td width="21%">
				<bean:write name='linkRecordDto' property='copyPolicyDate' filter='true' />
			</td>
		</tr>
		<tr class=common>
			<td width="13%">
				<s:text name="linkRecord.siteSurveyDate" />
			</td>
			<!-- 现场查勘日期 -->
			<td width="20%">
				<bean:write name='linkRecordDto' property='checkDate' filter='true' />
			</td>
			<td width="13%">
				<s:text name="linkRecord.documentDate" />
			</td>
			<!-- 单证收齐日期 -->
			<td width="20%">
				<bean:write name='linkRecordDto' property='finishDocDate' />
			</td>
			<td width="13%">
				<s:text name="db.prpLclaim.endCaseDate" />
			</td>
			<!-- 结案日期 -->
			<td width="21%">
				<bean:write name='linkRecordDto' property='endCaseDate' filter='true' />
			</td>
		</tr>
	</table>
	<table border=1 class=common align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr class=common>
			<td width="10%">
				<s:text name="print.month" />
			</td>
			<!-- 月 -->
			<td width="10%">
				<s:text name="regist.prpLregist.date" />
			</td>
			<!-- 日 -->
			<td width="80%">
				<s:text name="linkRecord.mainContent" />
			</td>
			<!-- 主要内容摘要 -->
		</tr>
		<logic:notEmpty name='linkRecordDto' property='prpLregistExtList'>
			<logic:iterate id='prpLregistExtList' name='linkRecordDto' property='prpLregistExtList'>
				<tr class=common>
					<td width="10%">
						<bean:write name='prpLregistExtList' property='inputDate' />
					</td>
					<td width="10%">
						<bean:write name='prpLregistExtList' property='inputDate' />
					</td>
					<td width="80%">
						<bean:write name='prpLregistExtList' property='context' />
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
		<!--include打印按钮-->
		<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
