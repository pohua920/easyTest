<%--
****************************************************************************
* DESC       ：报案查询条件结果页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%
	//原因：向页面中增加一个打印按钮
%>
<script language="javascript">
	
<%--案件状态标志处理--%>
	function submitForm() {
		fm.submit();//提交
	}
</script>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<html:base />
</head>
<body>
	<form name="fm" action="/claim/processLinkRecord.do" method="post">
		<input type="hidden" name="pageFlag">
		<input type="hidden" name="editType" value="queryList">
		<input type="hidden" name="nodeType" value="claim">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan=4 class="formtitle">
								<s:text name="linkRecord.claimRelateQuery" />
							</td>
							<!-- 理赔联系记录查询 -->
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="ClaimNo" class="input">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaim.policyNo" />
					：
				</td>
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
					<s:text name="db.prpLclaim.registNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>