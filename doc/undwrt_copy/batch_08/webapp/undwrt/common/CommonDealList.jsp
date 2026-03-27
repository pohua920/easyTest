<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : luyang
* CreateDate : 2004-12-30 10:53
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>

<%
	String strHandType = (String)session.getAttribute("HandType");
	String strEditType = (String)session.getAttribute("EditType");
	String strHandTitle = (String)session.getAttribute("HandTitle");
	String strEditTitle = (String)session.getAttribute("EditTitle");
	
%>
<html>
  <head>

   <app:css />
    <title><%=strEditTitle%><%=strHandTitle%><s:text name="undwrt.CommonDealList.task"/></title>

    <!--通用函数-->
    <script src="/undwrt/common/js/Common.js"></script>

    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>

  </head>
  <body class=interface>
  <form name="fm" action="/undwrt/CommonSubmitTask.do">
  <table class="common" cellpadding="5" cellspacing="1" align="center">
  <tr>
   <td class="formtitle" colspan="4"><s:text name="undwrt.CommonDealList.submit"/><%=strHandTitle%><s:text name="undwrt.CommonDealList.taskForward"/></td>
  </tr>
  <tr class="listtitle">
    <td><s:text name="undwrt.CommonDealList.chooseNode"/></td>
	  <td><s:text name="undwrt.CommonDealList.nodeNo"/></td>
    <td><s:text name="undwrt.CommonDealList.nodeName"/></td>
    <td><s:text name="undwrt.CommonDealList.defaultPath"/></td>
  </tr>
<logic:notEmpty  name="submitList"  >
<logic:iterate indexId="index" id="submitList"  name="submitList">
  <tr>
    <td class="text" style="width:10%"><input type="radio" name="radSelectNode" value="0" onclick="setSelectNode();"><s:text name="undwrt.CommonDealList.choose"/></td>
    <td class="text" style="width:30%"><input type="text" class="readonly" readonly name="NodeNo"
                                        value = "<bean:write name="submitList" property="endNodeNo" />"></td>
    <td class="text"style="width:30%"><input type="text" class="readonly" readonly name="NodeName"
                                        value = "<bean:write name="submitList" property="endNodeName" />"> </td>
    <td class="text" style="width:30%"><input type="text" class="readonly" readonly name="DefaultPath"
                                        value="<logic:equal name="submitList" property="defaultFlag" value="0"><s:text name='undwrt.no'/></logic:equal><logic:equal name="submitList" property="defaultFlag" value="1">是</logic:equal>" >
  	</td>
  </tr>
</logic:iterate>
</logic:notEmpty>
  &nbsp;
  <tr>
   <td class="formtitle" colspan="4"><s:text name="undwrt.CommonDealList.submit1"/><%=strHandTitle%><s:text name="undwrt.CommonDealList.taskRollback"/></td>
  </tr>
  <tr class="listtitle">
    <td><s:text name="undwrt.CommonDealList.chooseNode1"/></td>
  	<td><s:text name="undwrt.CommonDealList.nodeNo1"/></td>
    <td><s:text name="undwrt.CommonDealList.nodeName1"/></td>
    <td><s:text name="undwrt.CommonDealList.defaultPath1"/></td>
  </tr>
<logic:notEmpty  name="submitBackList">
<logic:iterate indexId="index" id="BackList"  name="submitBackList">
  <tr>
   <td class="text">
     <input type="radio" name="radSelectNode" value="0" onclick="setSelectNode();"><s:text name="undwrt.CommonDealList.choose1"/></td>
   <td class="text">
     <input type="hidden" value="<bean:write name="BackList" property="logNo"/>"> 
     <input type="text" class="readonly" readonly name="NodeNo" value = "<bean:write name="BackList" property="nodeNo" />"></td>
   <td class="text">
     <input type="text" class="readonly" readonly name="NodeName" value = "<bean:write name="BackList" property="nodeName" />"> </td>
   <td class="text">
     <input type="text" class="readonly" readonly name="DefaultPath"  value="<s:text name='undwrt.no'/>">
   </td>
  </tr>
</logic:iterate>
</logic:notEmpty>
  </table>
   <!--隐含域-->
   <span style="display:none">
    <input type="radio" name="radSelectNode" value="0">
    <input name="FlowID"        value="<%=request.getParameter("FlowId")%>">
    <input name="ModelNo"       value="<%=request.getParameter("ModelNo")%>">
    <input name="NodeNo"        value="">
    <input name="LogNo"         value="<%=request.getParameter("LogNo")%>">
    <input name="CertiType"     value="<%=request.getParameter("BusinessType")%>">
    <input name="BusinessNo"    value="<%=request.getParameter("BusinessNo")%>">
    <input name="BusinessType"  value="<%=request.getParameter("BusinessType")%>">
    <input name="FlowStatus"    value="0">
    <input name="Flag"          value="1">
    <input name="OperatorCode"  value="<%=request.getParameter("OperatorCode")%>">
    <input name="SingleSubmit"  value="">
    <input name="MultiSubmit"   value="">
    <input name="selectNodeNo" >
    <input name="selectNodeName">
    <input name="ModelNo">
   </span>


   <table class="common" cellpadding="5" cellspacing="1" align="center">
     <tr>
       <td class="text">
         <s:text name="undwrt.CommonDealList.submitInfo"/>：<input type="text" name="SelectNode" class="readonly" readonly style="width:80%" value=""></td>
     </tr>

    <span style="display:none">
       <input type="text" name="SelectUser" class="readonly" readonly value="">
       <input type="hidden" name="submitPage" value="1" description="<s:text name='prompt.messages.confirmPageIsSubmitPage'/>">       
    </span>

   </table>
   <table class=sub>
   <tr>
<%   
  if(AppConfig.get("sysconst.SUBMITSINGER")!=null && AppConfig.get("sysconst.SUBMITSINGER").equals("1"))
  {   
%>
<!-- modify by zhulei 20050704 "指定人员"功能隐藏 -->
     <td class=button width=34% style="display:none">
       <Input name="people" class="button" type="button" alt="<s:text name='prompt.messages.appointedPerson'/>" value="<s:text name='prompt.messages.appointedPerson'/>" onclick="selectPeople();">
     </td>    
<%
  }
%>  
     <td class=button width=33%>
       <Input class="button" name="ok" type="button" alt="<s:text name='prompt.ok'/>"  value="<s:text name='prompt.ok'/>" onclick="submitTask();">
     </td>
     <td class=button width=33%>
       <Input name="prev" class="button" type="button" alt="<s:text name='prompt.cancel'/>" value="<s:text name='prompt.cancel'/>" onclick="backQuery();">
     </td>
   </tr>
  </table>

 </form>

 </body>
</html>