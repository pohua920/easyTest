<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ：分案节点的工作流任务查询 (用于显示待处理、正处理、已处理分案任务 查询介面的查询结果)
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/pages/workflow/task/query/QueryTop.jsp"%>
<%@ include file="/pages/workflow/task/query/SwfLogSchedQuery.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
    <tr>
        <td colspan=12 class="formtitle">${pageScope.strTitle}<s:text name="check.schedulInfo" /><%-- 分案訊息--%></td>
    </tr>
    <tr>
        <td class="centertitle" style="width: 4%"><s:text name="regist.prpLregist.serialNo" /><%-- 序號 --%></td>
    <c:if test="${param.status=='0'}"><%-- 未处理  --%>
        <td class="centertitle"><s:text name="regist.prpLregist.status" /><%-- 狀態 --%></td>
    </c:if>
        <td class="centertitle"><s:text name="prpLregist.registNo" /><%-- 備案號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpLregist.riskCode" /><%-- 險種 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.insuredName" /><%-- 被保险人名称 --%></td>
        <td class="centertitle"><s:text name="workflow.dangerMark" /><%-- 出險標的 --%></td>
    <c:if test="${param.status=='0'}">
        <td class="centertitle"><s:text name="workflow.wait" /><%--等候 --%></td>
        <td class="centertitle"><s:text name="workflow.whetherCheckedAdjust" /><%--是否查勘调度 --%></td>
    </c:if>
        <td class="centertitle"><s:text name='db.prpLregist.operatorCode'/><%-- 操作人員 --%></td>
        <td class="centertitle">${pageScope.subTitleTime}<%-- 時間 --%></td>
        <td class="centertitle" style="width: 5%"><s:text name="replevy.operate" /><%--操作 --%></td>
    </tr>
<c:forEach items="${requestScope.swfLog.swfLogList}" var="tempSwfLog" varStatus="stat">
    <c:set var="flowStr" value="&swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}&status=${tempSwfLog.nodeStatus}&riskCode=${tempSwfLog.riskCode}&editType=${pageScope.editType}&nodeType=${tempSwfLog.nodeType}&businessNo=${tempSwfLog.businessNo}&keyIn=${tempSwfLog.keyIn}&policyNo=${tempSwfLog.policyNo}&modelNo=${tempSwfLog.modelNo}&nodeNo=${tempSwfLog.nodeNo}&dfFlag=${tempSwfLog.dfFlag}&actorId=${tempSwfLog.actorId}&processId=${tempSwfLog.processId}"/>
    <c:choose>
        <c:when test="${param.status=='0' && (tempSwfLog.nodeStatus == '0' || tempSwfLog.nodeStatus == '3')}"><%-- 未处理 --%>
            <c:set var="flowStrStart" value="${ctx}/schedule/scheduleBeforeEdit.do?prpLscheduleMainWFRegistNo=${tempSwfLog.registNo}&prpLscheduleMainWFSurveyNo=0&scheduleType=sched&handleDept=${tempSwfLog.handleDept}&endflag=${tempSwfLog.endFlag}&commiFlag=0" />
        </c:when>
        <c:when test="${param.status=='-1' && param.FuncName == 'cancelApply'}"><%-- 注销拒赔 --%>
            <c:set var="flowStrStart" value="${ctx}/claimBeforeCancel.do?registNo=${tempSwfLog.registNo}" />
        </c:when>
        <c:otherwise><%-- 正处理、已处理 --%>
            <c:set var="flowStrStart" value="${ctx}/schedule/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=${tempSwfLog.registNo}&prpLscheduleMainWFScheduleID=1&scheduleType=sched&handleDept=${tempSwfLog.handleDept}&endflag=${tempSwfLog.endFlag}&commiFlag=0&handlerCode=${tempSwfLog.handlerCode}" />
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${stat.index%2==0}"><tr class="listodd"></c:when>
        <c:otherwise><tr class="listeven"></c:otherwise>
    </c:choose>
        <td align="center">${stat.count}</td>
    <c:if test="${param.status=='0' || param.status=='-1'}">
        <td align="center">
            <c:choose>
                <c:when test="${tempSwfLog.nodeStatus=='3'}"><s:text name="schedule.returnDeal" /><%--回退处理 --%></c:when>
                <c:when test="${tempSwfLog.nodeStatus=='4'}"><s:text name="common.status.submited" /><%--已提交 --%></c:when>
                <c:when test="${tempSwfLog.nodeStatus=='5'}"><s:text name="workflow.notPassReturn" /><%--不通过退回 --%></c:when>
                <c:when test="${tempSwfLog.nodeStatus=='2'}"><s:text name="check.dealingWith" /></c:when>
                <c:otherwise><s:text name="guarantee.newDeal" /><%--新处理 --%></c:otherwise>
            </c:choose>
        </td>
    </c:if>
        <td align="center">
            <a href="${flowStrStart}${flowStr}" title="${tempSwfLog.titleStr}" ><c:out value="${tempSwfLog.registNo}" /></a>
        </td>
        <td align="center">
            <c:forEach items="${tempSwfLog.relatePolicyList}" var="relatePolicy" varStatus="stat">
                <c:out value="${relatePolicy.id.policyNo}" />
                <c:if test="${stat.index + 1 == stat.count}"><br/></c:if>
            </c:forEach>
        </td>
        <td align="center"><c:out value="${tempSwfLog.riskCodeName}"/></td>
        <td align="center"><c:out value="${tempSwfLog.insuredName}" /></td>
        <td align="center"><c:out value="${tempSwfLog.lossItemName}" /></td>
    <c:if test="${param.status=='0'}">
        <td align="center"><c:out value="${tempSwfLog.stopTimeDesc}" /></td>
        <td align="center"><c:out value="${tempSwfLog.handlerName}" />
            <c:choose>
                <c:when test="${tempSwfLog.typeFlag == '10'}"><s:text name="regist.prpLregist.yes" /></c:when>
                <c:otherwise><s:text name="regist.prpLregist.no" /></c:otherwise>
            </c:choose>
        </td>
    </c:if>
        <td align="center"><c:out value="${tempSwfLog.handlerName}" /></td>
        <td align="center">
            <c:choose>
                <c:when test="${tempSwfLog.nodeStatus == '2'}">
                    <rc:rcDate name="handleTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.handleTime}" /> 
                </c:when>
                <c:when test="${tempSwfLog.nodeStatus == '4'}">
                    <rc:rcDate name="submitTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.submitTime}" />
                </c:when>
                <c:otherwise>
                    <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.flowInTime}" />
                </c:otherwise>
            </c:choose>
            <input name="flowID" type="hidden" value="<c:out value='${tempSwfLog.id.flowID}'/>">
            <input name="logNo" type="hidden" value="<c:out value='${tempSwfLog.id.logNo}'/>">
            <input name="keyIN" type="hidden" value="<c:out value='${tempSwfLog.keyIn}'/>">
        </td>
        <td align="center">
            <c:choose>
                <c:when test="${param.status == '0'}"><%-- 未处理 --%>
                    <a href="${flowStrStart}${flowStr}" ><img name=buttonDistribute src="${ctx}/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </td>
    </tr>
</c:forEach>
    <tr class="listtail">
        <td colspan="12" align="center">
            <%@include file="/pages/common/pub/TurnPage.jsp"%>
        </td>
    </tr>
</table>
<%@ include file="/pages/workflow/task/query/QueryBottom.jsp"%>