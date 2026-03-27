<!--
****************************************************************************
* DESC       ：预赔条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<title><s:text name="title.prepayBeforeEdit.compensateSearch" /></title>
<%--预赔搜索--%>
<script src="${ctx}/common/js/showpage.js">
	
</script>
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=5 class="formtitle">
				<s:text name="prepay.compensateQueryInfo" />
				<%--预赔查询结果信息--%>
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="certainLoss.prePayNo" />
				<%--预赔号--%>
			</td>
			<td class="centertitle">
				<s:text name="certainLoss.claims" />
				<%--赔案号--%>
			</td>
			<td class="centertitle">
				<s:text name="db.view_larrearage.policyNo" />
				<%--保单号--%>
			</td>
			<td class="centertitle">
				<s:text name="db.prpLlawsuit.operatorCode" />
				<%--操作员--%>
			</td>
			<td class="centertitle">
				<s:text name="check.enterTime" />
				<%--输入时间--%>
			</td>
		</tr>
		<s:set var="prpLprepay_count" value="0" scope="page" />
		<c:if test="${prpLprepay.claimList!=null}">
			<s:set var="prpLprepay_count" value="#attr.prpLprepay.claimList.size()" scope="page" />
			<c:forEach var="prpLprepayTemp" items="${prpLprepay.claimList}" varStatus="prpLprepay_status">
				<c:if test="${prpLprepay_status.index%2==0}">
					<tr class="listodd">
				</c:if>
				<c:if test="${prpLprepay_status.index%2!=0}">
					<tr class="listeven">
				</c:if>
				<td align="center">
					<a href="${ctx}/prepayFinishQueryList.do?prpLprepayPrepayNo=${prpLprepayTemp.preCompensateNo}&editType=Approve&riskCode=${prpLprepayTemp.riskCode}">${prpLprepayTemp.preCompensateNo}</a>
				</td>
				<td align="center">${prpLprepayTemp.claimNo}</td>
				<td align="center">${prpLprepayTemp.policyNo}</td>
				<td align="center">${prpLprepayTemp.operatorCode}</td>
				<td align="center">${prpLprepayTemp.inputDate}</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr class="listtail">
			<td colspan="5">
				<s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%>
				${prpLprepay_count }
				<s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录--%>
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html>