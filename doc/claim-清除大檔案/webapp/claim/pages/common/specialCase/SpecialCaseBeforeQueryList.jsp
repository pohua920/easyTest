<!--
****************************************************************************
* DESC       ：立案查询结果显示页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->

<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
	<head>
		<title><s:text name="title.claimBeforeEdit.queryClaim" />
		</title>
		<script src="${ctx}/common/js/showpage.js"> </script>
		<script language="javascript">
  <!--案件状态标志处理-->
  <!--
    function submitForm()
    {
      fm.submit();//提交
    }
  //-->
  </script>
	</head>
<body>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=5 class="formtitle">
				<s:text name="title.claimBeforeEdit.titleName" />
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="db.prpLclaimStatus.status" />
				<%-- 案件状态 --%>
			</td>
			<td class="centertitle">
				<s:text name="db.prpLclaim.claimNo" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.operatorCode" />
				<%-- 操作员 --%>
			</td>
			<td class="centertitle">
				<s:text name="db.prpLclaimStatus.operatedate" />
				<%--操作时间  --%>
			</td>
			<td class="centertitle">
				<s:text name="specialCase.Operations" />
				<%--操作(预付/预赔/通融)  --%>
			</td>
		</tr>
		<s:set var="swfLog_count" value="0" scope="page" />
		<c:if test="${swfLogDto.swfLogList!=null}">
			<s:set var="swfLog_count" value="#attr.swfLogDto.swfLogList.size()" scope="page" />
			<c:forEach var="swfLogTemp" value="${swfLogDto.swfLogList}" varStatus="swfLogTemp_status">
				<c:if test="${swfLogTemp_status.index%2==0}">
					<tr class="listodd">
				</c:if>
				<c:if test="${swfLogTemp_status.index%2!=0}">
					<tr class="listeven">
				</c:if>
				<td align="center">
					<c:if test="${swfLogTemp.nodeStatus=='1'}">
						<s:text name="specialCase.ToProcessed" />
					</c:if>
					<%--待处理  --%>
					<c:if test="${swfLogTemp.nodeStatus=='2'}">
						<s:text name="common.status.intreating" />
					</c:if>
					<%--正处理  --%>
					<c:if test="${swfLogTemp.nodeStatus=='3'}">
						<s:text name="common.status.treated" />
					</c:if>
					<%--已处理  --%>
					<c:if test="${swfLogTemp.nodeStatus=='4'}">
						<s:text name="common.status.submited" />
					</c:if>
					<%--已提交  --%>
					<c:if test="${swfLogTemp.nodeStatus=='5'}">
						<s:text name="common.status.revoked" />
					</c:if>
					<%--已撤消  --%>
				</td>
				<td align="center">
					<a href="${ctx}/claimFinishQueryList.do?prpLclaimClaimNo=${swfLogTemp.businessNo}&editType=SHOW&riskCode=${swfLogTemp.riskCode}">${swfLogTemp.businessNo}</a>
				</td>
				<td align="center">${swfLogTemp.handlerCode}</td>
				<td align="center">${swfLogTemp.handleTime}</td>
				<td align="center">
					<a
						href="${ctx}/common/compensate/CompensateBeforeEdit.jsp?ClaimNo=${swfLogTemp.businessNo}&editType=ADD&caseType=4&swfLogFlowID=${swfLogTemp.flowID}&swfLogLogNo=${swfLogTemp.logNo}&riskCode=${swfLogTemp.riskCode}&status=${swfLogTemp.nodeStatus}&nodeType=${swfLogTemp.nodeType}&businessNo=${swfLogTemp.businessNo}"><s:text
							name="specialCase.Repay" /></a>
					<%-- 预付 --%>
					<a href="${ctx}/prepayBeforeEdit.do?ClaimNo=${swfLogTemp.businessNo}&editType=ADD&caseType=5&swfLogFlowID=${swfLogTemp.flowID}&swfLogLogNo=${swfLogTemp.logNo}">预赔</a> <a
						href="${ctx}/common/compensate/CompensateBeforeEdit.jsp?ClaimNo=${swfLogTemp.businessNo}&editType=ADD&caseType=3&swfLogFlowID=${swfLogTemp.flowID}&swfLogLogNo=${swfLogTemp.logNo}&riskCode=${swfLogTemp.riskCode}&status=${swfLogTemp.nodeStatus}&nodeType=${swfLogTemp.nodeType}&businessNo=${swfLogTemp.businessNo}"><s:text
							name="specialCase.Accommodation" /></a>
					<%-- 通融 --%>
				</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr class="listtail">
			<td colspan="5">
				<s:text name="certainLoss.totalInquiries" />
				${swfLog_count }
				<s:text name="certainLoss.meetRecord" />
				<%-- 共查询出 --%>
				<%-- 条满足条件的记录 --%>
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html>