<%--
****************************************************************************
* DESC       ：调度查勘内容
* AUTHOR     ：
* CREATEDATE ：2004-08-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<html:html>
<head>
<app:css />
<STYLE>
BODY {
	SCROLLBAR-FACE-COLOR: #EFFAFF;
	SCROLLBAR-HIGHLIGHT-COLOR: #4D9AC4;
	SCROLLBAR-SHADOW-COLOR: #4D9AC4;
	SCROLLBAR-3DLIGHT-COLOR: #EFFAFF;
	SCROLLBAR-ARROW-COLOR: #EFFAFF;
	SCROLLBAR-TRACK-COLOR: #EFFAFF;
	SCROLLBAR-DARKSHADOW-COLOR: #EFFAFF;
}
</STYLE>
<title><s:text name="title.scheduleBeforeEdit.schedulTaskList" />
	<%--调度取回任务清单 --%></title>
<script src="/claim/common/js/showpage.js">
	
</script>
</script>
<html:base />
</head>
<body>
	<form name="fm">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan="8" class="formtitle">
					<s:text name="schedule.schedulTaskList" />
				</td>
				<%--定损调度任务清单 --%>
			</tr>
			<tr>
				<td class="centertitle" style="width: 6%">
					<s:text name="regist.prpLregist.serialNo" />
				</td>
				<%--序号 --%>
				<td class="centertitle" style="width: 18%">
					<s:text name="schedule.reportRegistrateNo" />
				</td>
				<%--报案登记号 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="schedule.typeFee" />
				</td>
				<%--定损类型 --%>
				<td class="centertitle" style="width: 28%">
					<s:text name="schedule.schedulObjectName" />
				</td>
				<%--调度对象名称 --%>
				<td class="centertitle" style="width: 20%">
					<s:text name="certainLoss.prpLscheduleMainWF.attemperDate" />
				</td>
				<%--调度时间 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="check.schedulOpera" />
				</td>
				<%--调度操作员 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="schedule.surveyPersonn" />
				</td>
				<%--查勘/定损人员 --%>
			</tr>
			<%
				int index = 0;
			%>
			<c:if test="${swfLog.swfLogList != null}">
				<c:forEach var="prpLcheckTaskList" items="${swfLog.swfLogList}">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center">
						<%=index + 1%>
					</td>
					<%
						String scheduleType = "sched";//调度类型
					%>
					<td>
						<%
							//GETBACKEDIT 这个变量绝对重要，不可以随便修改，关系到保存
						%>
						<c:if test="${swfLog.nodeType == 'certa'}">
							<%
								scheduleType = "schel";
							%>
						</c:if>
						<a
							href="/claim/DAA/schedule/DAAScheduleCancelInput.jsp?registNo=<bean:write name='prpLcheckTaskList' property='keyIn'/>&nodeType=<bean:write name='prpLcheckTaskList' property='nodeType'/>&lossItemName=<bean:write name='prpLcheckTaskList' property='lossItemName'/>&swfLogFlowID=<bean:write name='prpLcheckTaskList' property='flowID'/>&swfLogLogNo=<bean:write name='prpLcheckTaskList' property='logNo'/>&policyNo=<bean:write name='prpLcheckTaskList' property='policyNo'/>">
							<bean:write name="prpLcheckTaskList" property="keyIn" />
						</a>
					</td>
					<td>
						<c:if test="${prpLcheckTaskList.nodeType=='certa'}">
							<s:text name="schedule.fee" />
							<%--车辆定损 --%>
						</c:if>
						<c:if test="${prpLcheckTaskList.nodeType=='wound'}">
							<s:text name="regist.prpLregist.personLossFlag" />
							<%--人伤 --%>
						</c:if>
						<c:if test="${prpLcheckTaskList.nodeType=='propc'}">
							<s:text name="compensate.dubang.damageProperty" />
							<%--财产损失 --%>
						</c:if>
					</td>
					<td>${prpLcheckTaskList.lossItemName}</td>
					<td>${prpLcheckTaskList.flowInTime}</td>
					<%
						// 是否被调度使用
					%>
					<td>${prpLcheckTaskList.beforeHandlerName}</td>
					<%
						// 预约查勘(定损)
					%>
					<td>${prpLcheckTaskList.handlerName}</td>
					</tr>
					<%
						index++;
					%>
				</c:forEach>
			</c:if>
			<tr class="listtail">
				<td colspan="15">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="swfLogDto" property="turnPageDto" />
							<c:set></c:set>
							<%
								SwfLogDto swfLogDto = (SwfLogDto) request.getAttribute("swfLogDto");
									int curPage = swfLogDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		<input type="hidden" name="editType" value="CANCELBEFOREQUERY">
		<table class="common" cellpadding="4" cellspacing="20">
			<tr>
			</tr>
			<tr>
			</tr>
			<table>
			</table>
			</form>
</body>
</html:html>