<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<%@page import="java.util.*" %>
<%
   // Calendar date = Calendar.getInstance();
   // date.add(Calendar.MONTH,-3);//事故日期控制在3个月内的
   // pageContext.setAttribute("damageStartDate", date.getTime());
   // pageContext.setAttribute("damageEndDate", Calendar.getInstance().getTime());
%>
<html>
<head>
    <title><s:text name="title.wfLogBeforeEdit.nodeFlag" /></title>
    <%@include file="/common/meta_css.jsp"%>
    <%@include file="/common/i18njs.jsp"%>
    <%@include file="/common/meta_js.jsp"%>
    <script src="/claim/pages/workflow/task/js/WfLogStatusList.js"> </script>
    <script language="javascript">
        function submitForm(field){
        // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
            field.disabled = true;
            fm.searchFlag.value = "true";
            fm.submit();//提交
        }
        $(document).ready(function(){
	    	$(document).keydown(function (e) {
			    var e = e?e:window.event;
			    var code = e.keyCode?e.keyCode:e.which;
			    if (code == 13) {
			        $("#button").click();
			    }
			})
	    });
        function queryUrgentCase(){ 
            var linkURL = "/claim/wfLogQuery.do?editType=urgentCase&nodeType=urgentCase";<%--紧急案件清单--%>
            var newWindow = window.open(linkURL,"<s:text name='title.compensate.emergencyCaseListing'/>","width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
        }
    </script>
</head>
<body>
    <form name="fm" action="/claim/wfLogQuery.do"  method="post" >
        <%@include file="/pages/workflow/task/query/QueryStatusTitle.jsp"%>
        <c:choose>
            <c:when test="${param.nodeType == 'regis'}">
                <%@include file="/pages/workflow/task/query/SwfLogRegisQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'sched'}">
                <%@include file="/pages/workflow/task/query/SwfLogSchedQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'claim'}">
                <%@include file="/pages/workflow/task/query/SwfLogClaimQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'check'}">
                <%@include file="/pages/workflow/task/query/SwfLogCheckQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'certa' || param.nodeType == 'propc' || param.nodeType == 'wound'}">
                <%@include file="/pages/workflow/task/query/SwfLogCertaQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'verif' || param.nodeType == 'propv' || param.nodeType == 'veriw'}">
                <%@include file="/pages/workflow/task/query/SwfLogVerifQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'certi'}">
                <%@include file="/pages/workflow/task/query/SwfLogCertiQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'compe' || param.nodeType == 'compp'}">
                <%@include file="/pages/workflow/task/query/SwfLogCompeQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'endca'}">
                <%@include file="/pages/workflow/task/query/SwfLogEndcaQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'speci'}">
                <%@include file="/pages/workflow/task/query/SwfLogSpeciQuery.jsp"%>
            </c:when>
            <c:when test="${param.nodeType == 'cance'}">
                <%@include file="/pages/workflow/task/query/SwfLogCanceQuery.jsp"%>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
        <%-- 为了查勘登记所使用的输入域，此处输入的name名称必须与查勘登记录入的名称相同，否则UIfacade会有问题--%>
        <input type="hidden" name="recordCount" class="common" value="">
        <input type="hidden" name="swfLogFlowID" class="common" value="">
        <input type="hidden" name="swfLogLogNo" class="common" value="">
        <input type="hidden" name="bussinessNo" class="common" value="">
        <input type="hidden" name="nodeType" class="common" value="${param.nodeType}">
        <input type="hidden" name="status" class="common" value="${param.status}">
        <input type="hidden" name="userLastAction" class="common" value="">
        <input type="hidden" name="flag" value="${param.flag}">
        <input type="hidden" name="editType" value="${param.editType}">
        <input type="hidden" name="FuncName" value="${param.FuncName}">
        <input type="hidden" name="searchFlag" value="">
        <input type="hidden" name="searchField" value="${param.searchField}">
        <input type="hidden" name="searchLabel" value="${param.searchLabel}">
        <input type="hidden" name="method" value="${param.method}">
        <input type="hidden" name="type" value="<c:out value='${param.type}'/>" />
    </form>
</body>
<script language="javascript">
   fm.queryButton.disabled = false;
</script>
</html>