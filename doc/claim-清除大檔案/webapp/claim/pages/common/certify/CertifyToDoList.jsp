<%--
****************************************************************************
* DESC       ：单证结果
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-05
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<html locale="true">
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><s:text name="title.certifyBeforeEdit.toTasks" />
	<%--待处理任务--%></title>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx}/common/js/showpage.js">
	
</script>
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=4 class="formtitle">
				<s:text name="certify.toTasks" />
			</td>
		</tr>
		<%--待处理任务--%>
		<tr>
			<td class="centertitle">
				<s:text name="prpLregist.registNo" />
			</td>
			<%--报案号--%>
			<td class="centertitle">
				<s:text name="db.view_larrearage.policyNo" />
			</td>
			<%--保单号--%>
			<td class="centertitle">
				<s:text name="db.prpLlawsuit.operatorCode" />
			</td>
			<%--操作员--%>
			<td class="centertitle">
				<s:text name="db.prpLlawsuit.inputDate" />
			</td>
			<%--输单日期--%>
		</tr>
		<c:set var="index" value="0" scope="page" />
		<c:if test="${prpLcertifyCollect.certifyCollectList!=null}">
			<c:forEach var="prpLcertifyTemp" items="${prpLcertifyCollect.certifyCollectList}">
				<c:if test="${index%2==0}">
					<tr class="listodd">
				</c:if>
				<c:if test="${index%2!=0}">
					<tr class="listeven">
				</c:if>
				<td align="center">${prpLcertifyTemp.id.businessNo}</td>
				<td align="center">${prpLcertifyTemp.policyNo}</td>
				<td align="center">${prpLcertifyTemp.handlerCode}</td>
				<td align="center">${prpLcertifyTemp.inputDate}</td>
				</tr>
				<c:set var="index" value="${index+1}" scope="page" />
			</c:forEach>
		</c:if>
		<tr class="listtail">
			<td colspan="4">
				<s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%>
				${index }
				<s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录--%>
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html>