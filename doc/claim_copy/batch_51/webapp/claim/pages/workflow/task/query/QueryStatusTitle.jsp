<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ： 组织查询状态、时间显示、EditType的问题
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="strTitle" value="" />
<c:set var="subTitleTime" value="" />
<c:set var="editType" value="${param.editType}"/>
<c:choose>
    <c:when test="${param.status=='0'}">
        <c:set var="strTitle" scope="page"><s:text name="common.status.untreated"/></c:set><%-- 未处理 --%>
        <c:set var="subTitleTime" scope="page"><s:text name="general.flowInTime"/></c:set><%-- 流入時間 --%>
        <c:set var="editType" scope="page" value="ADD"/>
    </c:when>
    <c:when test="${param.status=='2'}">
        <c:set var="strTitle" scope="page"><s:text name="check.dealingWith"/></c:set><%-- 正在處理 --%>
        <c:set var="subTitleTime" scope="page"><s:text name="guarantee.dealIime"/></c:set><%-- 處理時間 --%>
        <c:if test="${param.editType != 'DELETE'}">
             <c:set var="editType" scope="page" value="EDIT"/>
        </c:if>
    </c:when>
    <c:when test="${param.status=='3'}">
        <c:set var="strTitle" scope="page"><s:text name="check.dealingWith"/></c:set><%-- 正在處理 --%>
        <c:set var="subTitleTime" scope="page"><s:text name="workflow.backTime"/></c:set><%-- 退回時間 --%>
         <c:set var="editType" scope="page" value="EDIT"/>
    </c:when>
    <c:when test="${param.status=='4'}">
        <c:set var="strTitle" scope="page"><s:text name="common.status.treated"/></c:set><%-- 已處理 --%>
        <c:set var="subTitleTime" scope="page"><s:text name="workflow.flowTime"/></c:set><%-- 流出時間 --%>
         <c:set var="editType" scope="page" value="SHOW"/>
         <c:if test="${param.method == 'modify'}">
              <c:set var="editType" scope="page" value="EDIT"/>
         </c:if>
    </c:when>
    <c:when test="${param.status=='-1'}">
        <c:set var="subTitleTime" scope="page"><s:text name="claim.intoTime"/></c:set><%-- 退回時間 --%>
        <c:set var="editType" scope="page" value="CANCEL"/>
    </c:when>
    <c:otherwise>
         <c:set var="editType" scope="page" value="SHOW"/>
    </c:otherwise>
</c:choose>