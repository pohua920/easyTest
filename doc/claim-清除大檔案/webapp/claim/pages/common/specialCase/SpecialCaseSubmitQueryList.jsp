<!--
****************************************************************************
* DESC       ：已提交报案查询条件输入页面
* AUTHOR     ：zhangpeng
* CREATEDATE ：2004-04-21
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<script src="${ctx}/common/js/showpage.js">
	
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=4 class="formtitle">
				<s:text name="title.registBeforeEdit.titleName" />
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="common.status.submited" />
				<%-- 已提交 --%>
				<s:text name="db.prpLregist.registNo" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.policyNo" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.operatorCode" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.inputDate" />
			</td>
		</tr>
		<c:if test="${prpLregist.registList!=null}">
			<c:forEach var="prpLregistTemp" value="${prpLregist.registList}" varStatus="prpLregist_status">
				<c:if test="${prpLregist_status.index%2==0}">
					<tr class="listodd">
				</c:if>
				<c:if test="${prpLregist_status.index%2!=0}">
					<tr class="listeven">
				</c:if>
				<td>
					<a href="${ctx}/registSubmitQueryList.do?prpLregistRegistNo=${prpLregistTemp.registNo}&editType=${prpLregist.editType}">${prpLregistTemp.registNo}</a>
				</td>
				<td>${prpLregistTemp.policyNo}</td>
				<td>${prpLregistTemp.operatorCode}</td>
				<td>${prpLregistTemp.inputDate}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	</tr>
	</table>
</body>
</html>