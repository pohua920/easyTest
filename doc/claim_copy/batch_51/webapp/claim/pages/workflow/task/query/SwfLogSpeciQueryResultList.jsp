<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ：特殊赔案节点的工作流任务查询 (用于显示待处理、正处理、已处理、退回特殊赔案任务 查询介面的查询结果)
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/pages/workflow/task/query/QueryTop.jsp"%>
<%@ include file="/pages/workflow/task/query/SwfLogSpeciQuery.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
    <tr>
        <td colspan=12 class="formtitle">${pageScope.strTitle}<s:text name="specialCase.SpecialClaims.message" /><%-- 特殊赔案訊息--%></td>
    </tr>
    <tr>
        <td class="centertitle" style="width: 4%"><s:text name="regist.prpLregist.serialNo" /><%-- 序號 --%></td>
    <c:if test="${param.status=='0'}"><%-- 未处理 --%>
        <td class="centertitle"><s:text name="regist.prpLregist.status" /><%-- 狀態 --%></td>
    </c:if>
        <td class="centertitle">
             <c:choose>
                 <c:when test="${param.status=='0'}"><s:text name="sendUndwrt.BusinessNumber" /><%-- 業務號碼--%></c:when>
                 <c:otherwise><s:text name="claim.special.fileNumber" /><%-- 特殊歸檔號 --%></c:otherwise>
             </c:choose>
        </td>
        <td class="centertitle"><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpLregist.riskCode" /><%-- 險種 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.insuredName" /><%-- 被保险人名称 --%></td>
        <td class="centertitle">
            <c:choose>
                <c:when test="${param.status=='0'}"><s:text name="workflow.taskType" /><%--立案类型 --%></c:when>
                <c:otherwise><s:text name="db.prpLclaim.caseNo" /><%--立案号码 --%></c:otherwise>
            </c:choose>
        </td>
        <td class="centertitle"><s:text name='db.prpLregist.operatorCode'/><%-- 操作人員 --%></td>
        <td class="centertitle">${pageScope.subTitleTime}<%-- 時間 --%></td>
        <td class="centertitle" style="width: 5%"><s:text name="replevy.operate" /><%--操作 --%></td>
    </tr>
<c:forEach items="${requestScope.swfLog.swfLogList}" var="tempSwfLog" varStatus="stat">
    <c:set var="flowStr" value="&swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}&status=${tempSwfLog.nodeStatus}&riskCode=${tempSwfLog.riskCode}&editType=${pageScope.editType}&nodeType=${tempSwfLog.nodeType}&businessNo=${tempSwfLog.businessNo}&keyIn=${tempSwfLog.keyIn}&policyNo=${tempSwfLog.policyNo}&modelNo=${tempSwfLog.modelNo}&nodeNo=${tempSwfLog.nodeNo}&dfFlag=${tempSwfLog.dfFlag}&actorId=${tempSwfLog.actorId}&processId=${tempSwfLog.processId}"/>
    <c:choose>
        <c:when test="${tempSwfLog.typeFlag=='3'||tempSwfLog.typeFlag=='4'||tempSwfLog.typeFlag=='6'}">
            <c:choose>
                <c:when test="${tempSwfLog.nodeStatus == '0' && tempSwfLog.riskType=='D'}">
                    <c:set var="flowStrStart" value="${ctx}/compensate/compensateBeforeEditList.do?ClaimNo=${tempSwfLog.keyIn}&caseType=${tempSwfLog.typeFlag}" />
                </c:when>
                <c:when test="${tempSwfLog.nodeStatus == '0'}">
                    <c:set var="flowStrStart" value="${ctx}/compensate/compensateBeforeEdit.do?ClaimNo=${tempSwfLog.keyIn}&caseType=${tempSwfLog.typeFlag}" />
                </c:when>
                <c:otherwise>
                    <c:set var="flowStrStart" value="${ctx}/compensate/compensateFinishQueryList.do?ClaimNo=${tempSwfLog.keyIn}&prpLcompensateCompensateNo=${tempSwfLog.keyOut}&caseType=${tempSwfLog.typeFlag}" />
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${tempSwfLog.typeFlag=='5'||tempSwfLog.typeFlag=='7'||tempSwfLog.typeFlag=='8'}">
            <c:choose>
                <c:when test="${tempSwfLog.nodeStatus == '0'}">
                    <c:set var="flowStrStart" value="${ctx}/prepayBeforeEdit.do?ClaimNo=${tempSwfLog.keyIn}&caseType=${tempSwfLog.typeFlag}" />
                </c:when>
                <c:otherwise>
                    <c:set var="flowStrStart" value="${ctx}/prepayFinishQueryList.do?ClaimNo=${tempSwfLog.keyIn}&prpLprepayPrepayNo=${tempSwfLog.keyOut}&caseType=${tempSwfLog.typeFlag}" />
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise></c:otherwise>
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
            <a href="${flowStrStart}${flowStr }" title="${tempSwfLog.titleStr}" >
                <c:choose>
                    <c:when test="${tempSwfLog.nodeStatus == '0'}">${tempSwfLog.keyIn}</c:when>
                    <c:otherwise>${tempSwfLog.keyOut}</c:otherwise>
                </c:choose>
            </a>
        </td>
        <td align="center">
            <c:forEach items="${tempSwfLog.relatePolicyList}" var="relatePolicy" varStatus="stat">
                <c:out value="${relatePolicy.id.policyNo}" />
                <c:if test="${stat.index + 1 == stat.count}"><br/></c:if>
            </c:forEach>
        </td>
        <td align="center"><c:out value="${tempSwfLog.riskCodeName}"/></td>
        <td align="center"><c:out value="${tempSwfLog.insuredName}" /></td>
        <td align="center">
            <c:choose>
                <c:when test="${tempSwfLog.nodeStatus=='0'}">
                    <c:choose><%-- 未处理显示立案类型 --%>
                        <c:when test="${tempSwfLog.typeFlag=='3'}"><s:text name="specialCase.Accommodation" /><%--通融--%></c:when>
                        <c:when test="${tempSwfLog.typeFlag=='4'}"><s:text name="specialCase.Repay" /><%--预付--%></c:when>
                        <c:when test="${tempSwfLog.typeFlag=='5'}"><s:text name="check.advance" /><%--预赔--%></c:when>
                        <c:when test="${tempSwfLog.typeFlag=='6'}"><s:text name="check.other" /><%--其它--%></c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:set var="flowStrStart" value="${ctx}/claimFinishQueryList.do?prpLclaimClaimNo=${tempSwfLog.keyIn}" />
                    <a href="${flowStrStart}${flowStr}" title="${tempSwfLog.titleStr}">${tempSwfLog.keyIn}</a>
                </c:otherwise>
            </c:choose>
        </td>
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
            <c:if test="${tempSwfLog.nodeStatus == '0'}"><%-- 未处理 --%>
                <a href="${flowStrStart}${flowStr}" ><img name=buttonDistribute src="${ctx}/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
            </c:if>
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