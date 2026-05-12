<%--
****************************************************************************
* DESC       ： 回访查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
	
<%--案件状态标志处理--%>
<!--
	function submitForm() {
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {
			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}
		}
		fm.statusCollect.value = ref;
		fm.submit();//提交
	}
//-->
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/BackVisitQuery.do" method="post" onsubmit="return validateForm(this);">
		<table width="90%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="backVisit.queryBackVisitInformation" />
				</td>
			</tr>
			<!-- 查询回访信息 -->
			<tr>
				<td class='title'>
					<s:text name="prompt.queRegist.RegistNo" />:
				</td>
				<!-- 报案号： -->
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="prompt.queRegist.PolicyNo" />：
				</td>
				<!-- 保单号 -->
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<!-- 操作时间： -->
				<td class='input' colspan="3">
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />
				</td>
				<!-- 案件状态： -->
				<td colspan="3" class='input'>
					<input type="hidden" name="statusCollect" value="">
					<input type="checkbox" name="status" value="1">
					<s:text name="common.status.untreated" />
					<!-- 未处理 -->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<!-- 正处理 -->
					<input type="checkbox" name="status" value="3">
					<s:text name="common.status.treated" />
					<!-- 已处理 -->
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<!-- 已提交 -->
					<input type="checkbox" name="status" value="5">
					<s:text name="common.status.revoked" />
					<!-- 已撤消 -->
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' rowspan="3">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SHOW">
		<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
	</form>
</body>
</html:html>