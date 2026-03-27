<%--
****************************************************************************
* DESC       ：報送保發查詢
* AUTHOR     ： 理赔组
* CREATEDATE ： 2017-05-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.*" %>
<html>
<head>
<title>已核賠資料查詢</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
function queryExport(field){
    
    var searchType = $(":input[name='searchType']").val();
    window.location.href = "${ctx}/queryExport.do?searchType="+searchType+"&exportType="+field;
}
</script>
</head>
<body >
    <form name="fm" action="${ctx}" method="post" target="new">
        <input type="hidden" name="searchType" value="SendToTiiQuery">
        <input type="hidden" name="searchFlag" value="1">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
            <tr>
                <td colspan="4" class="formtitle">報送保發資料查詢</td>
            </tr>
        </table>
        <table width=100%>
            <tr align="center">
            	<td class="title">受害人醫療收據明細資料:</td>
                <td class="title" align="left">
                    <input id="detail" type=button class='bigbutton' value="匯出為TXT" onClick="queryExport(this.id);">
                </td>
            </tr>
            <tr align="center">
            	<td class="title" >受害人醫療收據匯總資料:</td>
            	<td class="title" align="left">
                    <input id="collect" type=button class='bigbutton' value="匯出為Word" onClick="queryExport(this.id);">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>