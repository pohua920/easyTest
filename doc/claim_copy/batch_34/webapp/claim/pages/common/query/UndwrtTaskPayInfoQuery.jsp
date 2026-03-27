<%--
****************************************************************************
* DESC       ：立案作業查詢
* AUTHOR     ： 理赔组
* CREATEDATE ： 2014-04-16
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.*" %>
<html>
<head>
<title>已核賠賠付查詢</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
    function submitForm(field) {
        fm.submit();//提交
    }
</script>
</head>
<body >
    <form name="fm" action="${ctx}/taskQuery.do" method="post" target="new">
        <input type="hidden" name="searchType" value="UndwrtTaskPayInfo">
        <input type="hidden" name="searchFlag" value="1">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
            <tr>
                <td colspan="4" class="formtitle">已核賠賠付查詢</td>
            </tr>
            <tr>
                <td class="title" align="right">核決單位：</td>
                <td class="input" >&nbsp;&nbsp;
                    <input type="text" name="UnderWriteDeptCode" class="codecode" title="核決單位代碼" style="width:60px"
                        ondblclick="code_CodeSelect(this, 'UnderWriteDeptCode','0,1','Y');" 
                        onchange="code_CodeChange(this, 'UnderWriteDeptCode','0,1','Y');" 
                        onkeyup="code_CodeSelect(this, 'UnderWriteDeptCode','0,1','Y');">
                    <input type="text" name="UnderWriteDeptName" class="codename" title="核決單位名稱" style="width:207px" 
                        ondblclick="code_CodeSelect(this, 'UnderWriteDeptCode','-1,0','Y','N');" 
                        onchange="code_CodeChange(this, 'UnderWriteDeptCode','-1,0','Y','N');" 
                        onkeyup="code_CodeSelect(this, 'UnderWriteDeptCode','-1,0','Y','N');">
                </td>
            </tr>
            <tr>
                <td class="title" align="right">核賠日期：</td>
                <td class="input" >&nbsp;&nbsp;
                    <rc:rcDate name="UnderWriteDateStart" style="width:120px" value="${UnderWriteDateStart}" />
                    &nbsp;<s:text name="prompt.to" />&nbsp;
                    <rc:rcDate name="UnderWriteDateEnd" style="width:120px" value="${UnderWriteDateEnd}" />
                </td>
            </tr>
            <tr>
                <td class="title" align="right">險種：</td>
                <td class="input" >&nbsp;&nbsp;
                    <select name="RiskType" style="width: 272px">
                        <option value="">全險種</option>
                        <c:forEach items="${riskTypes }" var="riskTypeTemp">
                        	<option value="${riskTypeTemp.key }">${riskTypeTemp.value }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <table width=100%>
            <tr>
                <td class='button' colspan="4">
                    <input id="button" type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm(this);">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>