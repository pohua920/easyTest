<%--
****************************************************************************
* DESC       ：录入报案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
 <%-- 标签页样式 --%>
  <jsp:include page="/behaviors/MpcStyle.jsp" />
	<%-- 页面样式	--%>
	<%@include file="/common/meta_js.jsp"%>
<script>
 //add by liping 20070109 start 按钮响应回车
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
    // add by liping 20070109 end
</script>
<html locale="true">
<head>
  <title><s:text name="title.appCheck.applyEntrySurvey"/></title><!-- 录入调查申请 -->
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  
</head>

<body  onload="initPage();document.onkeydown();">
<form name="fm" action="/claim/specailCaseQuery.do"  method="post" onsubmit="return validateForm(this);">

  <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
  <tr><td colspan=2 class="formtitle"><s:text name="appCheck.inputReportNumber"/></td></tr><!-- 输入报案号 -->
    <tr>
      <td class="title2"  align="center"><s:text name="prompt.queRegist.RegistNo"/>:</td><!-- 报案号： -->
      <td class="input2">
        <input type=text name="appRegistNo" class="common">
      </td>
      </tr>
      <tr>
      <td class="button"   colspan=2 align="center">
        <input type=submit id="button" class="button" class="button" value="<s:text name="button.next.value"/>"><!-- 下一步 -->
      </td>
    </tr>
  </table>
  <input type="hidden" name="editType" value="ApplySchedule">
</form>
</body>
</html>



