<%--
****************************************************************************
* DESC       ：实体资料调阅查询结果页面
* AUTHOR     ： liuwei
* CREATEDATE ： 2010-12-30
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.sinosoft.claim.dto.domain.PrpLDocArchiveDto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<html>
<head>
<title><s:text name="title.archive.entityDataReadQueryResultPage" /></title>
<!-- 实体资料调阅查询结果页 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<!-- add by zhyi fubon-2475 -->
<script src="/claim/common/js/date/WdatePicker.js"></script>
<script src="/claim/common/js/selectClassCode.js"></script>
</head>
<%
	String startDate = "";
	String endDate = "";
	if (request.getAttribute("startDate") != null) {
		startDate = (String) request.getAttribute("startDate");
	}
	if (request.getAttribute("endDate") != null) {
		endDate = (String) request.getAttribute("endDate");
	}
%>
<body onload="initPage();">
	<form name="fm" action="/claim/archiveQuery.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="archive.filedClaimQuery" />
				</td>
				<!-- 已归档赔案查询 -->
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<select class="tag" name="claimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="claimNo" class="query">
				</td>
				<td class="title">
					<s:text name="db.prpLregist.policyNo" />：
				</td>
				<td class="input">
					<select class="tag" name="policyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="policyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="archive.riskClass" />
				</td>
				<!-- 险类 -->
				<td class="input">
					<select class="tag" name="classNoSign">
						<option value="=">=</option>
						<!--  <option value="=*">=*</option> -->
					</select>
					<input type="text" name="strClassCode" class="query" value="">
					<input type="button" name="classCodeSelect" value="..." onclick="selectPublicCheckbox('selectClassCode')">
				</td>
				<td class="title">
					<s:text name="db.prpCmain.insuredName" />：
				</td>
				<!-- 被保险人名称 -->
				<td class="input">
					<select class="tag" name="insuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="insuredName" class="query">
				</td>
				<td colspan="2" class="title"></td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="currentTime" />:
				</td>
				<!-- 时间 -->
				<td colspan='2' class="input">
					<s:text name="archive.startTime" />
					<!-- 起始时间 -->
					<input class="Wdate" type="text" id="startDate" name="startDate" value="<%=startDate%>" onFocus="WdatePicker({isShowClear:false,readOnly:true})" />
					<s:text name="archive.endTime" />
					<!-- 终止时间 -->
					<input class="Wdate" type="text" id="endDate" name="endDate" value="<%=endDate%>" onFocus="WdatePicker({isShowClear:false,readOnly:true})" />
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<!-- "="符号，必须精确查询。 -->
					<s:text name="prompt.schedule.query2" />
					<!-- "=*"符号，前匹配後模糊的查询。 -->
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="center">
					<input type="submit" id="button" class='button' value="<s:text name='button.query.value' />">
				</td>
			</tr>
		</table>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td></td>
			</tr>
		</table>
		<table class="common" cellpadding="6" cellspacing="1" align="center">
			<tr class="listtitle">
				<td colspan="7">
					<b><s:text name="archive.filedClaimQueryResult" /> <!-- 已归档赔案查询结果 --></b>
				</td>
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
				<td>
					<s:text name="db.prpLregist.insuredName" />
				</td>
				<!-- 被保险人 -->
				<td>
					<s:text name="db.prpLclaim.endCaseDate" />
				</td>
				<!-- 结案日期 -->
				<td>
					<s:text name="archive.readNameApplicant" />
				</td>
				<!-- 调阅申请人姓名 -->
				<td>
					<s:text name="archive.applyReadTime" />
				</td>
				<!-- 申请调阅时间 -->
				<td>
					<s:text name="archive.expectedTimeArchive" />
				</td>
				<!-- 预计归档时间 -->
				<td>
					<s:text name="archive.stateData" />
				</td>
				<!-- 资料状态 -->
			</tr>
			<logic:notEmpty name="prpLDocArchiveDto" property="archiveList">
				<logic:iterate id="archiveList1" name="prpLDocArchiveDto" property="archiveList">
					<tr class="common">
						<td>
							<bean:write name="archiveList1" property="claimNo" />
						</td>
						<td>
							<bean:write name="archiveList1" property="insuredName" />
						</td>
						<td>
							<bean:write name="archiveList1" property="endCaseDate" />
						</td>
						<td>
							<logic:notEqual value="1" name="archiveList1" property="status">
								<bean:write name="archiveList1" property="applicantName" />
							</logic:notEqual>
						</td>
						<td>
							<logic:notEqual value="1" name="archiveList1" property="status">
								<bean:write name="archiveList1" property="applyDate" />
							</logic:notEqual>
						</td>
						<td>
							<logic:notEqual value="1" name="archiveList1" property="status">
								<bean:write name="archiveList1" property="estimateReturnDate" />
							</logic:notEqual>
						</td>
						<td>
							<logic:equal value="1" name="archiveList1" property="status">
								<s:text name="archive.archived" />
								<!-- 已归档 -->
							</logic:equal>
							<logic:equal value="2" name="archiveList1" property="status">
								<s:text name="archive.readChecking" />
								<!-- 调阅审核中 -->
							</logic:equal>
							<logic:equal value="3" name="archiveList1" property="status">
								<s:text name="archive.reading" />
								<!-- 调阅中 -->
							</logic:equal>
							<logic:equal value="4" name="archiveList1" property="status">
								<s:text name="archive.claimDealing" />
								<!-- 理赔处理中 -->
							</logic:equal>
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<td colspan="6">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLDocArchiveDto" property="turnPageDto" />
							<%
								PrpLDocArchiveDto prpDocArchiveDto = (PrpLDocArchiveDto) request.getAttribute("prpLDocArchiveDto");
								int curPage = prpDocArchiveDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="query">
	</form>
</body>
</html>