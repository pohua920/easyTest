<%--
****************************************************************************
* DESC       ：公估机构查询界面
* AUTHOR     ： weizeyu
* CREATEDATE ： 2009-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="com.sinosoft.claim.dto.domain.PrplexternalagencyDto" />
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrplexternalagencyDto"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<html>
<head>
<title>公估信息查询页面</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
	function submitForm(editType) {
		if ("delete" == editType || "update" == editType) {
			var flag = false;
			if (fm.checkboxEdit.length == undefined) {
				if (fm.checkboxEdit.checked == true) {
					flag = true;
				}
			} else {
				for ( var i = 0; i < fm.checkboxEdit.length; i++) {
					if (fm.checkboxEdit[i].checked == true) {
						flag = true;
						break;
					}
				}
			}

			if (!flag) {
				if ("update" == editType) {
					alert("请选择一条数据进行修改！");
				} else {
					alert("请选择一条数据进行删除！");
				}
				return false;
			}
		}
		fm.action = "/claim/externalAgency/externalagency.do?editType="
				+ editType;
		fm.submit();//提交
	}
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/externalAgency/externalagency.do?" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">查询公估机构信息</td>
			</tr>
			<tr>
				<td class='title'>公估机构代码:</td>
				<td class='input'>
					<select class=tag name="ComCodeSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ComCode" class="query" value="<%=(String) request.getAttribute("comcode")%>">
				</td>
				<td class='title'>公估类型:</td>
				<td class='input'>
					<input type=radio name="ComType" value="A">
					A-公估人
					<!-- 
		<input type=radio name="ComType" value="L">L-律师
		 -->
					<input type=radio name="ComType" value="S">
					S-代理人
				</td>
			</tr>
			<tr>
				<td class='title'>操作时间:</td>
				<td class='input'>
					<select class=tag name="CreateTimeSign">
						<option value="=">=&nbsp;</option>
					</select>
					<input type=text name="CreateTime" class="query">
					<img style='' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.CreateTime', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class="title">
					是否有效:
					<input type=radio name="Validstatus" value='1'>
					是
					<input type=radio name="Validstatus" value='0'>
					否
				</td>
			</tr>
			<tr>
				<td class='title'>中文名称:</td>
				<td class='input'>
					<select class=tag name="ComCNameSign">
						<option value="=">=</option>
					</select>
					<input type=text name="ComCName" class="query">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm('queryResult');">
				</td>
				<td class='button' colspan="2">
					<input type=button class='button' value="增加" onClick="submitForm('add');">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="2">
					"=" 符号，必须精确查询 <br> "=*"符号，前匹配後模糊的查询。
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td colspan=7 class="formtitle">公估机构查询展示</td>
			</tr>
			<tr>
				<td class="formtitle"></td>
				<td class="formtitle">公估代码</td>
				<td class="formtitle">中文名称</td>
				<td class="formtitle">公估类型</td>
				<td class="formtitle">法人</td>
				<td class="formtitle">是否有效</td>
			</tr>
			<%
				int index = 0;
			%>
			<c:forEach var="prpLexternalagency1" items="${prplexternalagencyList}">
				<%
					if (index % 2 == 0)
							out.print("<tr class=listodd>");
						else
							out.print("<tr class=listeven>");
				%>
				<tr>
					<td align="center" class="page">
						<input type=radio name="checkboxEdit" value="${prpLexternalagency1.comcode}">
					</td>
					<td align="center" class="page">
						<a target="fraInterface" href="/claim/externalAgency/externalagency.do?ComCode=${prpLexternalagency1.comcode}&editType=show&ComType=${prpLexternalagency1.comtype}">
							${prpLexternalagency1.comcode} </a>
					</td>
					<td align="center" class="page">${prpLexternalagency1.comcname}</td>
					<td align="center" class="page">${prpLexternalagency1.comtype}</td>
					<td align="center" class="page">${prpLexternalagency1.juridicalperson}</td>
					<td align="center" class="page">
						<c:if test="${prpLexternalagency1.validstatus=='1'}">是</c:if>
						<c:if test="${prpLexternalagency1.validstatus=='0'}">否</c:if>
					</td>
				</tr>
				<%
					index++;
				%>
			</c:forEach>
			<tr>
				<td class='button' colspan="3" align="center">
					<input type=button class='button' value="<s:text name='button.delete.value' />" onClick="submitForm('delete');">
				</td>
				<td class='button' colspan="3" align="center">
					<input type=button class='button' value="修改" onClick="submitForm('update');">
				</td>
			</tr>
			<tr>
				<td colspan="7">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<c:set var="pageview" value="${prplexternalagency.turnPageDto}" />
							<%
								PrplexternalagencyDto prplexternalagencyDto = (PrplexternalagencyDto) request.getAttribute("prplexternalagencyDto");
								int curPage = prplexternalagencyDto.getTurnPageDto().getPageNo();
								String strEditType = prplexternalagencyDto.getEditType();
							%>
							<input type="hidden" name="editType" value="<%=strEditType%>" />
							<%@include file="/pages/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>