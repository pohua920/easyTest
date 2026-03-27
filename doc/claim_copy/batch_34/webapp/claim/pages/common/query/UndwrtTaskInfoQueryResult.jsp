<%--
****************************************************************************
* DESC       ：已核賠資料查詢结果讯息
* AUTHOR     ： 理赔组
* CREATEDATE ： 2014-04-16
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="ins.framework.common.*"%>
<html>
<head>
<title>已核賠資料查詢讯息</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<style type="text/css">
    tr td {
        word-break: keep-all;/*必须*/
        overflow-x:hidden;
        text-overflow:ellipsis;
        white-space:nowrap
    } 
</style>
<script type="text/javascript">
    function queryExport(){
        var totalCount = $(":input[name='totalCount']").val();
        if(parseInt(totalCount) > 3000){
            alert("本次查詢結果記錄超過3000，請縮小查詢範圍重新導出。");
        }
        var searchType = $(":input[name='searchType']").val();
        window.location.href = "${ctx}/queryExport.do?searchType="+searchType;
    }
</script>
</head>
<body style="overflow-x: auto; overflow-y: auto">
    <form name="fm" action="${ctx}/taskQuery.do" method="post">
        <input type="hidden" name="searchType" value="UndwrtTaskInfo">
        <input type="hidden" name="searchFlag" value="">
        <div align="center">
            <input name="queryButton" type="button" class="bigbutton" value="導出為Excel" onclick="queryExport();">
        </div>
        <table cellpadding="3" cellspacing="1"  class="common">
            <thead >
                <tr>
                    <td colspan="16" class="formtitle">已核賠資料查詢讯息</td>
                </tr>
                <tr class="tableHead">
                    <td class="centertitle">&nbsp;序號&nbsp;</td>
                    <td class="centertitle">&nbsp;處理單位&nbsp;</td>
                    <td class="centertitle">&nbsp;立案日期&nbsp;</td>
                    <td class="centertitle">&nbsp;核賠通過日期&nbsp;</td>
                    <td class="centertitle">&nbsp;理算書號碼&nbsp;</td>
                    <td class="centertitle">&nbsp;保單號碼&nbsp;</td>
                    <td class="centertitle">&nbsp;被保險人&nbsp;</td>
                    <td class="centertitle">&nbsp;出險日期&nbsp;</td>
                    <td class="centertitle">&nbsp;險種&nbsp;</td>
                    <td class="centertitle">&nbsp;賠付金額&nbsp;</td>
                    <td class="centertitle">&nbsp;理賠費用&nbsp;</td>
                    <td class="centertitle">&nbsp;理賠員&nbsp;</td>
                    <td class="centertitle">&nbsp;業務經辦&nbsp;</td>
                    <td class="centertitle">&nbsp;業務來源&nbsp;</td>
                    <td class="centertitle">&nbsp;賠付日期&nbsp;</td>
                    <td class="centertitle">&nbsp;備註&nbsp;</td>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.resultList}" var="undwrtTaskInfoDto">
                <tr class=content bgcolor='#F7F7F7'>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.serialNo}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.underWriteDeptName}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.claimDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.underWriteEndDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.compensateNo}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.policyNo}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.insuredName}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.damageStartDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.riskCode}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.sumThisPaid}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.sumNoDutyFee}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.handlerName}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.handler1Name}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.businessNature}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.payDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${undwrtTaskInfoDto.remark}"/>&nbsp;</td>
                </tr>
            </c:forEach>
            <c:if test="${empty requestScope.resultList}">
                <tr class=content bgcolor='#F7F7F7'>
                    <td align="center" colspan="16">&nbsp;無記錄！&nbsp;</td>
                </tr>
            </c:if>
            </tbody>
            <tfoot>
                <tr class="listtail" align="center">
                    <%Page pageRecode = (Page) request.getAttribute("page");%>
                    <input type="hidden" name="rowsPerPage" value="<%=pageRecode.getPageSize() %>">
                    <input type="hidden" name="pageNo" value="<%=pageRecode.getCurrentPageNo() %>">
                    <input type="hidden" name="totalCount" value="<%=pageRecode.getTotalCount() %>">
                    <app:navigate objectName="page"/>
                </tr>
            </tfoot>
        </table>
    </form>
</body>
</html>