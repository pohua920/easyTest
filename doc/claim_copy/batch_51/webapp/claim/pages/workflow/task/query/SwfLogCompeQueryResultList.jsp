<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ： 理算节点节点的工作流任务查询 (用于显示待处理、正处理、已处理、核赔退回、注销拒赔  理算任务 查询介面的查询结果)
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/pages/workflow/task/query/QueryTop.jsp"%>
<%@ include file="/pages/workflow/task/query/SwfLogCompeQuery.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
    <tr>
        <td colspan=12 class="formtitle">${pageScope.strTitle}<s:text name="compensate.adjustmentInformation" /></td>
    </tr>
    <tr>
        <td class="centertitle" style="width: 4%"><s:text name="regist.prpLregist.serialNo" /><%-- 序號 --%></td>
        <td class="centertitle"><s:text name="regist.prpLregist.status" /><%-- 狀態 --%></td>
        <td class="centertitle"><s:text name="prpLclaim.claimNo" /><%-- 立案號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%></td>
        <td class="centertitle"><s:text name="db.prpLregist.riskCode" /><%-- 險種 --%></td>
        <td class="centertitle"><s:text name="db.prpCmain.insuredName" /><%-- 被保险人名称 --%></td>
        <td class="centertitle"><s:text name='db.prpLregist.operatorCode'/><%-- 操作人員 --%></td>
        <td class="centertitle">${pageScope.subTitleTime}<%-- 時間 --%></td>
        <td class="centertitle" style="width: 5%"><s:text name="replevy.operate" /><%--操作 --%></td>
    </tr>
<c:forEach items="${requestScope.swfLog.swfLogList}" var="tempSwfLog" varStatus="stat">
    <c:set var="flowStr" value="&swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}&status=${tempSwfLog.nodeStatus}&riskCode=${tempSwfLog.riskCode}&editType=${pageScope.editType}&nodeType=${tempSwfLog.nodeType}&businessNo=${tempSwfLog.businessNo}&keyIn=${tempSwfLog.keyIn}&policyNo=${tempSwfLog.policyNo}&modelNo=${tempSwfLog.modelNo}&nodeNo=${tempSwfLog.nodeNo}&dfFlag=${tempSwfLog.dfFlag}&actorId=${tempSwfLog.actorId}&processId=${tempSwfLog.processId}&chargeType=${param.chargeType}"/>
    <c:choose>
        <c:when test="${param.status=='0'}"><%-- 未处理 --%>
            <c:set var="flowStrStart" value="${ctx}/compensate/compensateBeforeEdit.do?ClaimNo=${tempSwfLog.keyIn}&caseType=${tempSwfLog.typeFlag}&compeCount=${tempSwfLog.compeCount}" />
        </c:when>
        <c:when test="${param.status=='-1' && param.FuncName == 'cancelApply'}"><%-- 注销拒赔 --%>
            <c:set var="flowStrStart" value="${ctx}/claimBeforeCancel.do?ClaimNo=${tempSwfLog.keyIn}&registNo==${tempSwfLog.registNo}" />
        </c:when>
        <c:otherwise><%-- 正处理、已处理、回退处理 --%>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${stat.index%2==0}"><tr class="listodd"></c:when>
        <c:otherwise><tr class="listeven"></c:otherwise>
    </c:choose>
        <td align="center">${stat.count}</td>
        <td align="center">
            <c:choose>
                <c:when test="${tempSwfLog.compeFlag=='1'}"><s:text name="workflow.undwrt.notPass" /><%--核赔未通过 --%></c:when>
                <c:when test="${tempSwfLog.compeFlag=='2'}"><s:text name="button.NuclearThrough.value" /><%--核赔通过 --%></c:when>
                <c:when test="${tempSwfLog.compeFlag=='0'}"><s:text name="workflow.compensate.notComp" /><%--未出计算书--%></c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </td>
        <td align="center">
            <a href="${flowStrStart}${flowStr}" title="${tempSwfLog.titleStr}" onclick="return compeCount('<c:out value='${tempSwfLog.compeCount}'/>');"><c:out value="${tempSwfLog.businessNo}" /></a>
        </td>
        <td align="center">
            <c:forEach items="${tempSwfLog.relatePolicyList}" var="relatePolicy" varStatus="stat">
                <c:out value="${relatePolicy.id.policyNo}" />
                <c:if test="${stat.index + 1 == stat.count}"><br/></c:if>
            </c:forEach>
        </td>
        <td align="center"><c:out value="${tempSwfLog.riskCodeName}"/></td>
        <td align="center" width="10%"><c:out value="${tempSwfLog.insuredName}" /></td>
        <td align="center"><c:out value="${tempSwfLog.handlerName}" /></td>
        <td align="center">
            <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.flowInTime}" />
            <input name="flowID" type="hidden" value="<c:out value='${tempSwfLog.id.flowID}'/>">
            <input name="logNo" type="hidden" value="<c:out value='${tempSwfLog.id.logNo}'/>">
            <input name="keyIN" type="hidden" value="<c:out value='${tempSwfLog.keyIn}'/>">
        </td>
        <td align="center">
            <c:choose>
                <c:when test="${param.status == '0'}"><%-- 未处理 --%>
                    <a href="${flowStrStart}${flowStr}" onclick="return compeCount('<c:out value='${tempSwfLog.compeCount}'/>')"><img name=buttonDistribute src="${ctx}/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
                    <c:if test="${tempSwfLog.compeFlag=='0' && tempSwfLog.nodeStatus == '0'}"><%-- 任务状态0 ，未出计算书 --%>
                        <c:if test="${tempSwfLog.riskType == 'D'}"><%-- 车险为理算通过可以退回定损 --%>
                            <c:choose>
                            	<c:when test="${tempSwfLog.simpleFlag == true}"><%-- 車險簡易賠案撤銷 --%>
                            		<c:set var="flowStrCancelSimple" value="${ctx}/compensate/compensateCancelSimpleCase.do?claimNo=${tempSwfLog.businessNo}&registNo=${tempSwfLog.registNo}&swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}" />
	                            	<a href="${flowStrCancelSimple}${flowStr}" onClick="return ifCancle('${tempSwfLog.businessNo}')"><img name=buttonDistribute src="${ctx}/images/butBack.gif" border="0" hspace="0" alt="撤銷簡易賠案"></a>
                            	</c:when>
                            	<c:otherwise>
                            		<c:set var="flowStrBackStart" value="${ctx}/compensate/compensateBackBeforeQuery.do?claimNo=${tempSwfLog.businessNo}&registNo=${tempSwfLog.registNo}" />
                            		<a href="${flowStrBackStart}${flowStr}" onClick="return otherFlag('${tempSwfLog.otherFlag}')"><img name=buttonDistribute src="${ctx}/images/butBack.gif" border="0" hspace="0" alt="<s:text name='schedule.returnDeal'/>"></a>
                            	</c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${tempSwfLog.riskType != 'E' && tempSwfLog.simpleFlag == false}"><%-- 非意健险，可以退回单证 --%>
                            <c:set var="flowStrBackCerti" value="${ctx}/compensate/compensateBackCerti.do?swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}" />
                            <a href="${flowStrBackCerti}" onClick="return ifSubmit('${tempSwfLog.registNo}')">
                                <img name=buttonDistribute src="${ctx}/images/butBack.gif" border="0" hspace="0" alt="<s:text name='prompt.workFlow.checkQuickCase3'/>">
                            </a>
                        </c:if>
                    </c:if>
                </c:when>
                <c:when test="${param.status == '-1' && param.FuncName == 'cancelApply' && tempSwfLog.compeFlag=='0'}"><%-- 申请注销拒赔 --%>
                    <a href="${flowStrStart}${flowStr}" ><img name=buttonDistribute src="${ctx}/images/butCancel.gif" border="0" hspace="5" alt="<s:text name='claim.cancelReject'/>"></a>
                </c:when>
                <c:otherwise></c:otherwise>
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
<script language="javascript">
//有计算书还没核赔通过就不能在出计算书
function compeCount(count) {
    if (count > 0 && fm.editType.value != 'CANCEL') {
        alert("<s:text name='prompt.workFlow.compeCount'/>"); <%--此案件还有未核赔通过或未提交的计算书，不得再出计算书。--%>
        return false;
    }
    return true;
}
function ifSubmit(registNo) { <%--请确认要把报案为+registNo + 回退到单证环节--%>
    var message = "<s:text name='prompt.workFlow.checkQuickCase'/>'" + registNo + "'<s:text name='prompt.workFlow.checkQuickCase3'/>？";
    if (window.confirm(message) == false) {
        return false;
    }
    return true;
}

function ifCancle(claimNo) { <%--请确认要把报案为+registNo + 回退到单证环节--%>
	var message = "請確認要把立案號碼為'" + claimNo + "'的任務撤銷簡易賠案處理嗎？";
	if (window.confirm(message) == false) {
		return false;
	}
	return true;
}
</script>