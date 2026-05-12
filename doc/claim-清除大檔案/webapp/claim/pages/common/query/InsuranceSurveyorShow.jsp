<%--
****************************************************************************
* DESC       ：公估师查询详细信息界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-16
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
<title><s:text name="title.query.publicDetailInformationQueryPage" /></title>
<%--公估师查询详细信息界面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="#" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" align="center" colspan="4">
					<s:text name="query.publicDetailInformation" />
					<%--公估师详细信息 --%>
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.publicTeam" />
					<%--公估机构 --%>
					：
				</td>
				<td class="readonly" style="width: 35%">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type=text name="ComCName" class="readonly" readonly maxlength="100" style="width: 220px" value="${prpLInsuranceSurveyor.newComCName}">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.chineseName" />
					<%--中文名称 --%>
					:
				</td>
				<td class="readonly" style="width: 35%">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type=text name="ComCName" class="readonly" readonly maxlength="100" style="width: 220px" value="${prpLInsuranceSurveyor.comcname}">
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.englishName" />
					<%--英文名称 --%>
					：
				</td>
				<td class="readonly" style="width: 35%">
					<input type=text name="ComEName" class="readonly" readonly style="width: 220px" maxlength="40" value="${prpLInsuranceSurveyor.comename}">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="db.prpLregist.phoneNumber" />
					<%--联系电话 --%>
					:
				</td>
				<td class="readonly" style="width: 35%">
					<input type=text name="Telephone" class="readonly" readonly maxlength="25" style="width: 220px" value="${prpLInsuranceSurveyor.telephone}">
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">E-mail：</td>
				<td class="readonly" style="width: 35%">
					<input type=text name="EMail" class="readonly" readonly value="${prpLInsuranceSurveyor.email}">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="db.prpLregistText.flag" />
					<%--标志位 --%>
					：
				</td>
				<td class="readonly" style="width: 35%" colspan="3">
					<c:if test="${prpLInsuranceSurveyor.validStatus=='1'}">
						<input type=radio name="Validstatus" value="1" checked disabled="disabled">
						<s:text name="query.flagTrue" />
						<%--有效 --%>
						<input type=radio name="Validstatus" value="0" disabled="disabled">
						<s:text name="query.flagFalse" />
						<%--无效 --%>
					</c:if>
					<c:if test="${prpLInsuranceSurveyor.validStatus=='0'}">
						<input type=radio name="Validstatus" value="1" disabled="disabled">
						<s:text name="query.flagTrue" />
						<%--有效 --%>
						<input type=radio name="Validstatus" value="0" checked disabled="disabled">
						<s:text name="query.flagFalse" />
						<%--无效 --%>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class=button style="width: 33%" colspan="4">
					<input type=button name=buttonBack class='button' value="<s:text name="button.return.value" />" onclick="return history.back();">
					<%--返回 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>