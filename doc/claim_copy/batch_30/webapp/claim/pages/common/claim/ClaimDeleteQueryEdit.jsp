<%--
****************************************************************************
* DESC       ：立案撤消查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
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
<!--
	
<%--案件撤消处理--%>
	function submitForm() {
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {

			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}
		}
		fm.caseFlag.value = ref;

		fm.submit();//提交
	}
//-->
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/ClaimDeleteQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.claimBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />：
				</td>
				<td class='input'>
					<select class=tag>
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLclaimClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaim.policyNo" />：
				</td>
				<td class='input'>
					<select class=tag>
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLclaimPolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />：
				</td>
				<td class='input'>
					<select class=tag>
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLthirdPartyLicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="prpLClaimStatusOperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="prpLClaimStatusOperateDate" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />:
				</td>
				<%--案件状态--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag">
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<input type="checkbox" name="status" value="3">
					<s:text name="common.status.submited" />
					<%--已完成--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SHOW">
	</form>
</body>
</html:html>
