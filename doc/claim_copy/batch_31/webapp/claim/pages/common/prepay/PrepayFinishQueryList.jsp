<!--
****************************************************************************
* DESC       ：预赔查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-11
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<script src="${ctx}/common/js/showpage.js">
	
</script>
</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=4 class="formtitle">
				<s:text name="prepay.infoQuery" />
			</td>
		</tr>
		<%--查询预赔信息--%>
		<tr>
			<td class="centertitle">
				<s:text name="certainLoss.prePayNo" />
				：
			</td>
			<%--预赔号--%>
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
				<td>
					<a href="${ctx}/prepayFinishQueryList.do?prpLprepayPrepayNo=${prpLprepayTemp.preCompensateNo}&editType=${prpLprepay.editType}"> ${prpLprepayTemp.preCompensateNo}</a>
				</td>
				<td>${prpLprepayTemp.policyNo}</td>
				<td>${prpLprepayTemp.operatorCode}</td>
				<td>${prpLprepayTemp.inputDate}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	</tr>
	</table>
</body>
</html>