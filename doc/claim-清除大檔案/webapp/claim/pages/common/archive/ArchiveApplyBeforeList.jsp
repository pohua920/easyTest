<%--
****************************************************************************
* DESC       ：实体资料调阅申请前查询结果页面
* AUTHOR     ： liuwei
* CREATEDATE ： 2010-12-30
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLDocArchiveDto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html>
<head>
<title><s:text name="archive.entityBeforeApplyQueryResult" /></title>
<!-- 实体资料调阅申请前查询结果页 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/archiveQuery.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="archive.entityBeforeApplyQuery" />
				</td>
				<!-- 实体资料调阅申请前查询 -->
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />:
				</td>
				<!-- 赔案号： -->
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
					<s:text name="db.prpCmain.insuredName" />:
				</td>
				<!-- 被保险人名称： -->
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
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。 --%>
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
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class="listtitle">
				<td colspan="6">
					<b><s:text name="archive.entityBeforeApplyQueryLast" /></b>
				</td>
				<!-- 实体资料调阅申请前查询结果 -->
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
				<td>
					<s:text name="prompt.queRegist.PolicyNo" />
				</td>
				<!-- 保单号 -->
				<td>
					<s:text name="db.prpCmain.insuredName" />
				</td>
				<!-- 被保险人名称 -->
				<td>
					<s:text name="db.prpLclaim.endCaseDate" />
				</td>
				<!-- 结案日期 -->
				<td>
					<s:text name="compensate.compel.paymentAmount" />
				</td>
				<!-- 赔款金额 -->
				<td>
					<s:text name="certify.operate" />
				</td>
				<!-- 操作 -->
			</tr>
			<logic:notEmpty name="prpLDocArchiveDto" property="archiveList">
				<logic:iterate id="archiveList1" name="prpLDocArchiveDto" property="archiveList">
					<tr class="common">
						<td>
							<a href="/claim/archiveFinishQueryList.do?claimNo=<bean:write name='archiveList1' property='claimNo'/>&editType=applyFinish"><bean:write name="archiveList1" property="claimNo" /></a>
						</td>
						<td>
							<bean:write name="archiveList1" property="policyNo" />
						</td>
						<td>
							<bean:write name="archiveList1" property="insuredName" />
						</td>
						<td>
							<bean:write name="archiveList1" property="endCaseDate" />
						</td>
						<td>
							<bean:write name="archiveList1" property="sumDutyPaid" />
						</td>
						<td>
							<a href="/claim/archiveFinishQueryList.do?claimNo=<bean:write name='archiveList1' property='claimNo'/>&editType=applyFinish"> <img name=buttonDistribute src="/claim/images/butDeal.gif"
								border="0" hspace="5" alt="选择处理">
							</a>
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
		<input type="hidden" name="editType" value="apply">
	</form>
</body>
</html>