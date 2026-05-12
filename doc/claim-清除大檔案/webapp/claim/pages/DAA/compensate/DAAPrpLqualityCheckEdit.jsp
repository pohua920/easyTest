<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：显示质量评审内容信息
* AUTHOR     ：理赔项目组
* CREATEDATE ：2013-02-18
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1" style="display: none">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="QualityCheckImg" onclick="showPage(this,QualityCheck)">
			<s:text name="certify.workQuality" />
			<%-- 工作质量审核信息 --%>
			<br>
			<table class="common" align="center" id="QualityCheck" style="display:">
				<tbody>
					<!-- 设置零时变量，intTemp记录条数信息，forEach有if判断，不能使用forEach的status -->
					<c:set var="intTemp" value="0" scope="page" />
					<c:forEach items="${requestScope.qualityCheckList}" var="prpDCode">
						<c:set var="intTemp" value="${pageScope.intTemp+1}" scope="page" />
						<c:choose>
							<c:when test="${intTemp%2==0}">
								<c:set var="trClass" value="listodd" />
							</c:when>
							<c:otherwise>
								<c:set var="trClass" value="listeven" />
							</c:otherwise>
						</c:choose>
						<tr class="${pageScope.trClass}">
							<td align="left" style="width: 50%" colspan=3>
								${pageScope.intTemp}、
								<c:out value="${pageScope.prpDCode.codeCName}" />
								<input type="radio" name="VisitBackQue${pageScope.intTemp }" value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
								<%-- 是 --%>
								<input type="radio" name="VisitBackQue${pageScope.intTemp }" value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
								<%-- 否 --%>
								<input type="radio" name="VisitBackQue${pageScope.intTemp }" value="2">
								<s:text name="certainLoss.thirdCarLoss.uncertainty" />
								<%-- 不确定 --%>
								<input type="hidden" name="txtQuestionCode${pageScope.intTemp }" value="${pageScope.prpDCode.id.codeCode}">
								<input type="hidden" name="txtQuestionName${pageScope.intTemp }" value="${pageScope.prpDCode.codeCName}">
							</td>
							<td>
								<input type="text" name="txtQuestionRemark${intTemp}" class="common" maxlength="255">
							</td>
						</tr>
					</c:forEach>
					<input type="hidden" name="txtRecordNum" value="${intTemp}">
					<input type="hidden" name="qualityCheckType" value="compe">
				</tbody>
			</table>
		</td>
	</tr>
</table>
