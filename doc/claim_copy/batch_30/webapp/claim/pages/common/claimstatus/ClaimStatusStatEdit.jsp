<%--
****************************************************************************
* DESC       ：查勘查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-07
* MODIFYLIST ：   Name Sunhao      Date  2004-08-24          Reason/Contents
           1. 增加车牌号，案件状态，操作时间查询条件
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.*" %>

<%
  String riskType= request.getParameter("type");
  //System.out.println("----------------------riskType-----"+ riskType );
%>
<script>
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
</script>
<!--

//-->
</script>
<html>
<head>
  <title><s:text name="title.claimBeforeEdit.titleName" /></title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>

<body  onload="initPage();document.onkeydown();">
<form name="fm" action="/claim/claimStatusStat.do"  method="post" onsubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
    <tr><td colspan="4" class="formtitle"><s:text name="claimstatus.nodeStatuQuery" /></td></tr><%--节点状态时间段查询--%>
      <tr>
        <td class='title' ><s:text name="manage.startTime" />：</td><%--开始时间--%>
        <td class='input' >
          <input type=text name="statStartDate" class="query" > <img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle" onclick="TogglePopupCalendarWindow('document.fm.statStartDate', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()-15 %>', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()+2 %>')">
        </td>
        <td class='title' ><s:text name="manage.endTime" />：</td><%--结束时间--%>
        <td class='input' >
          <input type=text name="statEndDate" class="query" > <img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle" onclick="TogglePopupCalendarWindow('document.fm.statEndDate', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()-15 %>', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()+2 %>')">
        </td>
        </tr>
        <tr>
        <td class='title' align=center colspan="4">
          <input type=submit class='button' id="button" value="<s:text name='button.query.value' />">
        </td>
      </tr>
    </table>
    <input type="hidden" name="editType" value="EDIT">
    <input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
     <input type="hidden" name="riskType" value="<%= riskType%>">

    <%
        //原因：向下一个文件增加一个意健险信息
    %>
         <input type="hidden" name="type" value="<%= request.getParameter("type")%>"/>
  </form>
  
</body>
</html>