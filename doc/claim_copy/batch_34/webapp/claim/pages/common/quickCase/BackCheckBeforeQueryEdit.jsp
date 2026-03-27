<%--
****************************************************************************
* DESC       ：简易赔案查询输入界面
* AUTHOR     ： claim
* CREATEDATE ： 2007-06-22
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<title><s:text name="title.quickCaseBeforeEdit.simpleClaimInfoQuery" /></title>
<%--简易赔案处理信息查询页面--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
  <%--案件状态标志处理--%>
<!--
	function submitForm() {
		var ref = "";
		for (i = 0; i < fm.checkFlag.length; i++) {
			if (fm.checkFlag[i].checked == true) {
				ref = ref + fm.checkFlag[i].value + ",";
			}
		}
		fm.caseFlag.value = ref;
		fm.submit();//提交
	}
//-->
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/backCheckQueryEdit.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="quickCase.simpleClaimInfoQuery" />
				</td>
			</tr>
			<%--查询简易赔案信息--%>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimApprov.registNo" />
					：
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />
					：
				</td>
				<%--保单号--%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />
					:
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="quickCase.simpleStatu" />
					:
				</td>
				<%--简易赔案状态--%>
				<td class='input' colspan="3">
					<input type=checkbox name="checkFlag" value='0'>
					<s:text name="common.status.untreated" />
					<%--未处理--%>
					<input type=checkbox name="checkFlag" value='1'>
					<s:text name="common.status.submitedReview" />
					<%--复查通过--%>
					<input type=checkbox name="checkFlag" value='2'>
					<s:text name="common.status.unsubmitedReview" />
					<%--复查未通过--%>
					<input type=checkbox name="checkFlag" value='3'>
					<s:text name="common.status.intreatingReview" />
					<%--复堪处理--%>
				</td>
			</tr>
			<tr>
				<input name="caseFlag" type="hidden">
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>