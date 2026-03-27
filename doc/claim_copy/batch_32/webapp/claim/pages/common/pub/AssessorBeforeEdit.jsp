<%--
****************************************************************************
* DESC       ：公估师评估前选择公估机构和公估师页面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<%
	String claimNo = request.getParameter("claimNo");
%>
<head>
<title><s:text name="title.pubBeforeEdit.assessDivisionMasterPage" /></title>
<%--公估师评估前选择公估机构和公估师页面--%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language='javascript'>
	function clearComCode() {
		fm.ComCode.value = "";
		fm.ComCName.value = "";
	}
	function submitForm() {
		fm.buttonNext.disabled = "true";
		if (fm.NewComCode.value == "") {
			alert("请选择公估机构！");
			fm.buttonNext.disabled = "";
			return false;
		}
		if (fm.ComCode.value == "") {
			alert("请选择公估师！");
			fm.buttonNext.disabled = "";
			return false;
		}
		fm.submit();
	}
</script>
</head>
<html>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form name="fm" method="post" action="/claim/AssessorScore.do" onsubmit="return validateForm(this);">
		<input type="hidden" name="claimNo" value="<%=claimNo%>" />
		<input type="hidden" name="editType" value="queryResult" />
		<table width="50%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="common">
			<thead>
				<tr>
					<td class="formtitle" id="TitleName" colspan="2">
						<s:text name="pub.assessTeacher" />
						<%--公估师评估--%>
					</td>
				</tr>
			</thead>
			<tr>
				<td class='common' style="width: 20%" align="right">
					<s:text name="certainLoss.claims" />
					：
					<%--赔案号--%>
				</td>
				<td class='input'>
					<%=claimNo%>
				</td>
			</tr>
			<tr>
				<td class='common' style="width: 20%" align="right">
					<s:text name="pub.assessAgencies" />
					：
					<%--公估机构--%>
				</td>
				<td class='input'>
					<input type="hidden" name="NewComCode" class="codecode" title="公估機構代碼" value="" style="width: 19%" ondblclick="clearComCode();code_CodeSelect(this, 'getExternalAgency','0,1','Y');"
						onchange="clearComCode();code_CodeSelect(this, 'getExternalAgency','0,1','Y');" onkeyup="clearComCode();code_CodeSelect(this, 'getExternalAgency','0,1','Y');">
					<input type=text name="NewComCName" class="codename" title="公估機構名稱" value="" style="width: 40%" ondblclick="clearComCode();code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');"
						onchange="clearComCode();code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');" onkeyup="clearComCode();code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');">
					<img src="/claim/images/bgMarkMustInput.jpg" complete="complete" />
				</td>
			</tr>
			<tr>
				<td class='common' style="width: 20%" align="right">
					<s:text name="pub.assessDivision" />
					：
					<%--公估师--%>
				</td>
				<td class='input'>
					<input type="hidden" name="ComCode" class="codecode" title="公估師代碼" value="" style="width: 19%" ondblclick="code_CodeSelect(this, 'getInsuranceSurveyor','0,1','Y');"
						onchange="code_CodeSelect(this, 'getInsuranceSurveyor','0,1','Y');" onkeyup="code_CodeSelect(this, 'getExternalAgency','0,1','Y');">
					<input type=text name="ComCName" class="codename" title="公估師名稱" value="" style="width: 40%" ondblclick="code_CodeSelect(this, 'getInsuranceSurveyor','-1,0','Y','N');"
						onchange="code_CodeSelect(this, 'getInsuranceSurveyor','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'getInsuranceSurveyor','-1,0','Y','N');">
					<img src="/claim/images/bgMarkMustInput.jpg" complete="complete" />
				</td>
			</tr>
			<tr>
				<td class="common" align="center" style="width: 100%" colspan="2">
					<input name="buttonNext" type=button value="<s:text name='button.next.value' />" class='button' onclick="submitForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
