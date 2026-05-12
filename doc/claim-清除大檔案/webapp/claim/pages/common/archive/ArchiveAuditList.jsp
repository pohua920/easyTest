<%--
****************************************************************************
* DESC       ：实体资料调阅审核查询结果页面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-01-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLDocArchiveLogDto"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
<head>
<title><s:text name="archive.entityReadCheckQueryResultPage" /></title>
<!-- 实体资料调阅审核查询结果页 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/archive/archiveBefore.do" method="post">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td></td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class="listtitle">
				<td colspan="6">
					<b><s:text name="archive.entityReadCheckQueryResult" /></b>
				</td>
				<!-- 实体资料调阅审核查询结果 -->
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
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
				<td>
					<s:text name="certify.operate" />
				</td>
				<!-- 操作 -->
			</tr>
			<c:if test="${prpLDocArchiveLogDto.archiveList != null}">
				<c:forEach var="archiveList1" items="${prpLDocArchiveLogDto.archiveList}">
					<tr class="common">
						<td>
							<a href="/claim/archive/archiveFinishQueryList.do?claimNo=${archiveList1.id.claimNo}&editType=auditFinish">${archiveList1.id.claimNo}</a>
						</td>
						<td>
							<c:if test="${archiveList1.status != '1'}">
                                ${archiveList1.operatorName}
                                </c:if>
						</td>
						<td>
							<c:if test="${archiveList1.status != '1'}">
								<%-- ${archiveList1.operatorDate}--%>
								<rc:rcDate name="operatorDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${archiveList1.operatorDate}" />
							</c:if>
						</td>
						<td>
							<c:if test="${archiveList1.status != '1'}">
								<%-- ${archiveList1.estimateReturnDate}--%>
								<rc:rcDate name="estimateReturnDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${archiveList1.estimateReturnDate}" />
							</c:if>
						</td>
						<td>
							<s:text name="archive.readChecking" />
							<!-- 调阅审核中 -->
						</td>
						<td>
							<a href="/claim/archive/archiveFinishQueryList.do?claimNo=${archiveList1.id.claimNo}&editType=auditFinish"> <img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5"
								alt="选择处理">
							</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<%-- <td colspan="6">
                    <table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <c:set var="pageview" value="${prpLDocArchiveLogDto.turnPageDto}"></c:set>
                            <%
                                PrpLDocArchiveLogDto prpLDocArchiveLogDto = (PrpLDocArchiveLogDto) request.getAttribute("prpLDocArchiveLogDto");
                                int curPage = prpLDocArchiveLogDto.getTurnPageDto().getPageNo();
                             %>
                             <%@include file="/common/pub/TurnOverPage.jsp" %>
                        </tr>
                    </table>
                </td>--%>
			</tr>
		</table>
		<input type="hidden" name="editType" value="query">
	</form>
</body>
</html>