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
<title><s:text name="title.prepayBeforeEdit.compensateSearch" /></title>
<%--预赔搜索--%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/common/js/showpage.js">
	
</script>
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=6 class="formtitle">
				<s:text name="common.status.submited" />
				<s:text name="prepay.compensateQueryInfo" />
			</td>
		</tr>
		<%--预赔查询结果信息--%>
		<tr>
			<td class="centertitle">
				<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
			</td>
			<%--案件状态--%>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<s:text name="db.prpLprepay.preCompensateNo" />
			</td>
			<%--预赔计算书号--%>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<s:text name="check.claimNum" />
			</td>
			<%--赔案号--%>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<s:text name="db.view_larrearage.policyNo" />
			</td>
			<%--保单号--%>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<s:text name="db.prpLlawsuit.operatorCode" />
			</td>
			<%--操作员--%>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<s:text name="db.prpLclaimStatus.operatedate" />
			</td>
			<%--操作时间--%>
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
					<c:if test="${prpLprepayTemp.status=='1'}">
						<s:text name="common.status.submited" />
						<s:text name="common.status.untreated" />
					</c:if>
					<%--未处理--%>
					<c:if test="${prpLprepayTemp.status=='2'}">
						<s:text name="common.status.submited" />
						<s:text name="common.status.intreating" />
					</c:if>
					<%--正处理--%>
					<c:if test="${prpLprepayTemp.status=='3'}">
						<s:text name="common.status.submited" />
						<s:text name="common.status.treated" />
					</c:if>
					<%--已处理--%>
					<c:if test="${prpLprepayTemp.status=='4'}">
						<s:text name="common.status.submited" />
						<s:text name="common.status.submited" />
					</c:if>
					<%--已提交--%>
					<c:if test="${prpLprepayTemp.status=='5'}">
						<s:text name="common.status.submited" />
						<s:text name="common.status.revoked" />
					</c:if>
					<%--已撤消--%>
				</td>
				<td align="center">
					<a href="${ctx}/prepayFinishQueryList.do?prpLprepayPrepayNo=${prpLprepayTemp.preCompensateNo}&editType=${prpLprepay.editType}&riskCode=${prpLprepayTemp.riskCode}">${prpLprepayTemp.preCompensateNo}</a>
				</td>
				<td align="center">${prpLprepayTemp.claimNo}</td>
				<td align="center">${prpLprepayTemp.policyNo}</td>
				<td align="center">${prpLprepayTemp.handlerCode}</td>
				<td align="center">${prpLprepayTemp.operateDate}</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr class="listtail">
			<td colspan="6">
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