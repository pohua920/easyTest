<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ： 注销拒赔节点的工作流任务查询结果 (用于显示注销拒赔任务 查询介面的查询结果)
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/pages/workflow/task/query/QueryTop.jsp"%>
<%@ include file="/pages/workflow/task/query/SwfLogCanceQuery.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
    <tr>
        <td colspan=12 class="formtitle">${pageScope.strTitle}<s:text name="prompt.claimCancel.message"/></td>
    </tr>
    <tr>
        <td class="centertitle" style="width: 4%"><s:text name="regist.prpLregist.serialNo" /><%-- 序號 --%></td>
    <c:if test="${param.status=='0'}">
        <td class="centertitle"><s:text name="regist.prpLregist.status" /><%-- 狀態 --%></td>
    </c:if>
        <td class="centertitle"><s:text name="sendUndwrt.BusinessNumber" /><%-- 業務號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpLregist.riskCode" /><%-- 險種 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.insuredName" /><%-- 被保险人名称 --%></td>
        <td class="centertitle"><s:text name='db.prpLregist.operatorCode'/><%-- 操作人員 --%></td>
        <td class="centertitle">${pageScope.subTitleTime}<%-- 時間 --%></td>
        <td class="centertitle" style="width: 5%"><s:text name="replevy.operate" /><%--操作 --%></td>
    </tr>
<c:forEach items="${requestScope.swfLog.swfLogList}" var="tempSwfLog" varStatus="stat">
    <c:if test="${param.status=='0' && tempSwfLog.nodeStatus=='0'}">
        <c:set var="editType" value="CANCELEDIT"/>
    </c:if>
    <c:set var="flowStr" value="&swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}&status=${tempSwfLog.nodeStatus}&riskCode=${tempSwfLog.riskCode}&editType=${editType}&nodeType=${tempSwfLog.nodeType}&businessNo=${tempSwfLog.businessNo}&keyIn=${tempSwfLog.keyIn}&policyNo=${tempSwfLog.policyNo}&modelNo=${tempSwfLog.modelNo}&nodeNo=${tempSwfLog.nodeNo}&dfFlag=${tempSwfLog.dfFlag}&actorId=${tempSwfLog.actorId}&processId=${tempSwfLog.processId}"/>
    <c:choose>
        <c:when test="${tempSwfLog.nodeStatus == '4'}">
            <c:set var="flowStrStart" value="${ctx}/claimFinishQueryList.do?prpLclaimClaimNo=${tempSwfLog.businessNo}"/>
        </c:when>
        <c:otherwise>
            <c:set var="flowStrStart" value="${ctx}/claimBeforeCancel.do?ClaimNo=${tempSwfLog.businessNo}&typeFlag=${tempSwfLog.typeFlag}&flowInTime=${tempSwfLog.flowInTime}"/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${stat.index%2==0}"><tr class="listodd"></c:when>
        <c:otherwise><tr class="listeven"></c:otherwise>
    </c:choose>
        <td align="center">${stat.count}</td>
    <c:if test="${param.status=='0'}">
        <td align="center"><s:text name="guarantee.newDeal" /><%-- 新處理 --%></td>
    </c:if>
        <td align="center">
            <a href="${flowStrStart}${flowStr}" title="${tempSwfLog.titleStr}" ><c:out value="${tempSwfLog.businessNo}" /></a>
        </td>
        <td align="center"><c:out value="${tempSwfLog.policyNo}"/></td>
        <td align="center"><c:out value="${tempSwfLog.riskCodeName}"/></td>
        <td align="center"><c:out value="${tempSwfLog.insuredName}" /></td>
        <td align="center"><c:out value="${tempSwfLog.handlerName}" /></td>
        <td align="center">
            <c:choose>
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
                <c:when test="${tempSwfLog.nodeStatus == '4'}">
                    <a style = "display: none" href="${ctx}/workflow/processWorkflow.do?editType=recycle&flowID=${tempSwfLog.id.flowID}&logNo=${tempSwfLog.id.logNo}"><s:text name="workflow.withdraw" /></a>
                </c:when>
                <c:otherwise>
                    <a href="${flowStrStart}${flowStr}" ><img name=buttonDistribute src="${ctx}/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</c:forEach>
    <tr class="listtail">
        <td colspan="9" align="center">
            <%@include file="/pages/common/pub/TurnPage.jsp"%>
        </td>
    </tr>
</table>
<%@ include file="/pages/workflow/task/query/QueryBottom.jsp"%>