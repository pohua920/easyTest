<%--
****************************************************************************
* DESC       ：立案作業查詢結果訊息
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
<title>立案作業查詢訊息</title>
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
<body >
    <form name="fm" action="${ctx}/taskQuery.do" method="post">
        <input type="hidden" name="searchType" value="ClaimTask">
        <input type="hidden" name="searchFlag" value="">
        <div align="center">
            <input name="queryButton" type="button" class="bigbutton" value="導出為Excel" onclick="queryExport();">
        </div>
        <table cellpadding="3" cellspacing="1"  class="common">
            <thead>
                <tr>
                    <td colspan="16" class="formtitle">立案作業查詢訊息</td>
                </tr>
                <tr class="tableHead">
                    <td class="centertitle">&nbsp;序號&nbsp;</td>
                    <td class="centertitle">&nbsp;處理單位&nbsp;</td>
                    <td class="centertitle">&nbsp;立案日期&nbsp;</td>
                    <td class="centertitle">&nbsp;賠案號碼&nbsp;</td>
                    <td class="centertitle">&nbsp;保單號碼&nbsp;</td>
                    <td class="centertitle">&nbsp;被保險人&nbsp;</td>
                    <td class="centertitle">&nbsp;生效日期&nbsp;</td>
                    <td class="centertitle">&nbsp;出險日期&nbsp;</td>
                    <td class="centertitle">&nbsp;出險地點&nbsp;</td>
                    <td class="centertitle">&nbsp;分項險種&nbsp;</td>
                    <td class="centertitle">&nbsp;預估金額&nbsp;</td>
                    <td class="centertitle">&nbsp;核賠金額&nbsp;</td>
                    <td class="centertitle">&nbsp;理賠員&nbsp;</td>
                    <td class="centertitle">&nbsp;業務經辦&nbsp;</td>
                    <td class="centertitle">&nbsp;業務來源&nbsp;</td>
                    <td class="centertitle">&nbsp;備註&nbsp;</td>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.resultList}" var="claimTaskDto">
                <tr class=content bgcolor='#F7F7F7'>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.serialNo}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.comCName}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.claimDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.claimNo}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.policyNo}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.insuredName}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.startDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.damageStartDate}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.damageAddress}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.kindCode}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.claimLoss}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.sumRealPay}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.handlerName}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.handler1Name}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.businessNature}"/>&nbsp;</td>
                    <td align="center">&nbsp;<c:out value="${claimTaskDto.remark}"/>&nbsp;</td>
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
                    <app:navigate objectName="page"/>
                </tr>
            </tfoot>
        </table>
    </form>
</body>
</html>