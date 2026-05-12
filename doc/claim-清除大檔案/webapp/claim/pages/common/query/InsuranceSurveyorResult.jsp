<%--
****************************************************************************
* DESC       ：公估师查询结果界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page
	import="com.sinosoft.claim.dto.domain.PrplexternalagencyDto" />
<%@page import="com.sinosoft.claim.dto.domain.PrpLInsuranceSurveyorDto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html locale="true">
<head>
<title><s:text name="query.publicInformationQueryPage" /></title>
<%--公估师信息查询页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</script>
<script language="javascript">
	function submitForm(editType) {
		var comCode = "";
		var flag = false;

		if (fm.checkboxEdit.length == undefined) {
			if (fm.checkboxEdit.checked == true) {
				comCode = fm.checkboxEdit.value;
				flag = true;
			}
		} else {
			for (var i = 0; i < fm.checkboxEdit.length; i++) {
				if (fm.checkboxEdit[i].checked == true) {
					comCode = fm.checkboxEdit[i].value;
					flag = true;
					break;
				}
			}
		}

		if (!flag || comCode == "") {
			if ("update" == editType) {
				alert("请选择一条记录进行修改！");
			}
			return false;
		}
		fm.action = "/claim/InsuranceSurveyor.do?editType=" + editType + "&comCode=" + comCode;
		fm.target = "QueryResultFrame";
		fm.submit(); //提交
		return true;
	}
    </script>
</head>
<body onload="initPage();">
	<form name="fm" action="#" method="post" onsubmit="return validateForm(this);">
		<table width=100%>
			<tr>
				<td colspan=6 class="formtitle">
					<s:text name="query.surveyorQueryShow" />
					<%--公估师查询展示 --%>
				</td>
			</tr>
			<tr>
				<td class="formtitle"></td>
				<td class="formtitle">
					<s:text name="query.surveyorCode" />
					<%--公估师代码 --%>
				</td>
				<td class="formtitle">
					<s:text name="query.chineseName" />
					<%--中文名称 --%>
				</td>
				<td class="formtitle">
					<s:text name="query.publicTeam" />
					<%--公估机构 --%>
				</td>
				<td class="formtitle">
					<s:text name="query.assessmentType" />
					<%--公估类型 --%>
				</td>
				<td class="formtitle">
					<s:text name="query.ifUsefull" />
					<%--是否有效 --%>
				</td>
			</tr>
			<%
				int index = 0;
			%>
			<logic:iterate id="list" name="prpLInsuranceSurveyorList">
				<%
					if (index % 2 == 0)
								out.print("<tr class=listodd>");
							else
								out.print("<tr class=listeven>");
				%>
				<tr>
					<td align="center" class="page">
						<input type=radio name="checkboxEdit" value="<bean:write name="list" property="comcode"/>,<bean:write name="list" property="newcomcode"/>">
					</td>
					<td align="center" class="page">
						<a target="QueryResultFrame" href="/claim/InsuranceSurveyor.do?comCode=<bean:write name='list' property='comcode'/>&newComCode=<bean:write name='list' property='newcomcode'/>&editType=show">
							<bean:write name="list" property="comcode" />
						</a>
					</td>
					<td align="center" class="page">
						<bean:write name="list" property="comcname" />
					</td>
					<td align="center" class="page">
						<bean:write name="list" property="newComCName" />
					</td>
					<td align="center" class="page">
						<logic:equal value="A" name="list" property="comType">
							<s:text name="query.assessmentPeople" />
							<%--公估人 --%>
						</logic:equal>
						<logic:equal value="S" name="list" property="comType">
							<s:text name="db.prpLclaim.agentCode" />
							<%--代理人 --%>
						</logic:equal>
					</td>
					<td align="center" class="page">
						<logic:equal name="list" property="validstatus" value='1'>
							<s:text name="regist.prpLregist.yes" />
							<%--是--%>
						</logic:equal>
						<logic:equal name="list" property="validstatus" value='0'>
							<s:text name="regist.prpLregist.no" />
							<%--否--%>
						</logic:equal>
					</td>
				</tr>
				<%
					index++;
				--%>
			</logic:iterate>
			<logic:notEmpty name="prpLInsuranceSurveyorList">
				<tr>
					<td class='button' colspan="6" align="center">
						<input type=button class='button' value="<s:text name="button.edit.value" />" onClick="submitForm('update');">
						<%--修改 --%>
					</td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td colspan="6">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLInsuranceSurveyorDto" property="turnPageDto" />
							<%
							    PrpLInsuranceSurveyorDto prpLInsuranceSurveyorDto = (PrpLInsuranceSurveyorDto) request.getAttribute("prpLInsuranceSurveyorDto");
								int curPage = prpLInsuranceSurveyorDto.getTurnPageDto().getPageNo();
								String strEditType = prpLInsuranceSurveyorDto.getEditType();
							%>
							<input type="hidden" name="editType" value="<%=strEditType%>" />
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>