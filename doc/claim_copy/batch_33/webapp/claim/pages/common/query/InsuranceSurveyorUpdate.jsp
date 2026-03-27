<%--
****************************************************************************
* DESC       ：公估师修改界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.query.publicEditView" /></title>
<%--公估师修改界面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
	function submitForm(editType) {
		if (fm.ComCName.value == "") {
			alert("请输入中文名称！");
			return false;
		}
		fm.action = "${ctx}/externalAgency/insuranceSurveyor.do?editType="
				+ editType;
		fm.submit();//提交
	}
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="#" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" align="center" colspan="4">
					<s:text name="query.editPublic" />
					<%--修改公估师 --%>
				</td>
			</tr>
			<tr>
				<td class="title" width="50%" colspan="2">
					<input type=hidden name="ComCode" value="${prpLInsuranceSurveyor.id.comCode}">
				</td>
				<td class="title" width="50%" colspan="2">
					<input type=hidden name="NewComCode" value="${prpLInsuranceSurveyor.id.newcomcode}">
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.publicTeam" />
					<%--公估机构 --%>
					：
				</td>
				<td class="input" style="width: 35%">${prpLInsuranceSurveyor.newComCName}</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.chineseName" />
					<%--中文名称 --%>
					：
				</td>
				<td class="input" style="width: 35%">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type=text name="ComCName" class="input" value="${prpLInsuranceSurveyor.comcname}" maxlength="100" style="width: 220px">
					<img src="/claim/images/bgMarkMustInput.jpg" complete="complete" />
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.englishName" />
					<%--英文名称 --%>
					：
				</td>
				<td class="input" style="width: 35%">
					<input type=text name="ComEName" class="input" value="${prpLInsuranceSurveyor.comename}" style="width: 220px" maxlength="40">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="db.prpLregist.phoneNumber" />
					<%--联系电话 --%>
					：
				</td>
				<td class="input" style="width: 35%">
					<input type=text name="Telephone" class="input" value="${prpLInsuranceSurveyor.telephone}" style="width: 220px" maxlength="40">
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">E-mail：</td>
				<td class="input" style="width: 35%">
					<input type=text name="EMail" class="input" value="${prpLInsuranceSurveyor.email}" maxlength="25" style="width: 220px">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="db.prpLregistText.flag" />
					<%--标志位 --%>
					：
				</td>
				<td class="input" style="width: 35%" colspan="3">
					<c:if test="${prpLInsuranceSurveyor.validStatus=='1'}">
						<input type=radio name="Validstatus" value="1" checked>
						<s:text name="query.flagTrue" />
						<%--有效 --%>
						<input type=radio name="Validstatus" value="0">
						<s:text name="query.flagFalse" />
						<%--无效 --%>
					</c:if>
					<c:if test="${prpLInsuranceSurveyor.validStatus=='0'}">
						<input type=radio name="Validstatus" value="1">
						<s:text name="query.flagTrue" />
						<%--有效 --%>
						<input type=radio name="Validstatus" value="0" checked>
						<s:text name="query.flagFalse" />
						<%--无效 --%>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class=button style="width: 40%" colspan="4" align="center">
					<input type="button" name=buttonSave class='button' value="<s:text name="button.save.value" />" onClick="submitForm('updateSave');">
					<%--保存 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>