<!--***************************************************************************
* Description: 批量提交核保(包括详细信息、提交再保确认、保存、提交等。)
* Author     : luyang
* CreateDate : 2005-1-26 18:52
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- 滚动条样式定义 -->
<%@ include file="/common/CommonStyle.html"%>

<%
	String strHandType = (String)session.getAttribute("HandType");
	String strEditType = (String)session.getAttribute("EditType");
	String strHandTitle = (String)session.getAttribute("HandTitle");
	String strEditTitle = (String)session.getAttribute("EditTitle");
	
	String strSubmitSingle = "";
	String strSubmitMulti = "";
%>
<html>
  <head>
   <app:css />
    <title><%=strEditTitle%><%=strHandTitle%><s:text name="undwrt.EndorseDealContent.task"/></title>

    <!--通用函数-->
    <script src="/undwrt/common/js/Common.js"></script>

    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    
    <script src="/undwrt/hebao/js/BatchTask.js"></script>

  </head>
  <body class=interface>
  <form name="fm" action="/undwrt/CommonSubmitTask.do">
  <table class="common" cellpadding="5" cellspacing="1" align="center">
  <tr>
   <td class="formtitle" colspan="4"><s:text name="undwrt.CommonDealList.submit"/><%=strHandTitle%><s:text name="undwrt.CommonDealList.taskForward"/></td>
  </tr>
  <tr class="listtitle">
    <td><s:text name="undwrt.pages.undwrtDeal.chooseHierarchy"/></td>
	  <td><s:text name="undwrt.pages.undwrtDeal.hierarchyCode"/></td>
    <td><s:text name="undwrt.pages.undwrtDeal.hierarchyName"/></td>
    <td><s:text name="undwrt.pages.undwrtDeal.defaultPath"/></td>
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
                                        value="<bean:write name="submitList" property="defaultFlag" />" >
  	</td>
  </tr>
</logic:iterate>
</logic:notEmpty>
  &nbsp;
  <tr>
   <td class="formtitle" colspan="4"><s:text name="undwrt.pages.undwrtDeal.submit"/><%=strHandTitle%><s:text name="undwrt.CommonDealList.taskRollback"/></td>
  </tr>
  <tr class="listtitle">
    <td><s:text name="undwrt.pages.undwrtDeal.chooseHierarchy"/></td>
  	<td><s:text name="undwrt.pages.undwrtDeal.hierarchyCode"/></td>
    <td><s:text name="undwrt.pages.undwrtDeal.hierarchyName"/></td>
    <td><s:text name="undwrt.pages.undwrtDeal.defaultPath"/></td>
  </tr>
<logic:notEmpty  name="submitBackList">
<logic:iterate indexId="index" id="BackList"  name="submitBackList">
  <tr>
   <td class="text">
     <input type="radio" name="radSelectNode" value="0" onclick="setSelectNode();"><s:text name="undwrt.CommonDealList.choose"/></td>
   <td class="text">
     <input type="text" class="readonly" readonly name="NodeNo" value = "<bean:write name="BackList" property="nodeNo" />"></td>
   <td class="text">
     <input type="text" class="readonly" readonly name="NodeName" value = "<bean:write name="BackList" property="nodeName" />"> </td>
   <td class="text">
     <input type="text" class="readonly" readonly name="DefaultPath"  value="<s:text name='undwrt.HebaoTaskDealQuery.no'/>">
   </td>
  </tr>
</logic:iterate>
</logic:notEmpty>
  </table>
   <!--隐含域-->
   <span style="display:none">
    <input type="radio" name="radSelectNode" value="0">
    <input name="FlowID"        value="<%=request.getParameter("flowId")%>">
    <input name="ModelNo"       value="<%=request.getParameter("modelNo")%>">
    <input name="NodeNo"        value="">
    <input name="CertiType"     value="<%=request.getParameter("businessType")%>">
    <input name="BusinessNo"    value="<%=request.getParameter("businessNo")%>">
    <input name="BusinessType"  value="<%=request.getParameter("businessType")%>">
    <input name="FlowStatus"    value="0">
    <input name="Flag"          value="1">
    <input name="OperatorCode"  value="<%=request.getParameter("operatorCode")%>">
    <input name="SingleSubmit"  value="<%=strSubmitSingle%>">
    <input name="MultiSubmit"   value="<%=strSubmitMulti%>">
    <input name="selectNodeNo" >
    <input name="selectNodeName">
    <input name="selectModelNo">
   </span>


   <table class="common" cellpadding="5" cellspacing="1" align="center">
     <tr>
       <td class="text">
         <s:text name="undwrt.CommonDealList.submitInfo"/>：<input type="text" name="SelectNode" class="readonly" readonly style="width:80%" value=""></td>
     </tr>

    <span style="display:none">
       <input type="text" name="SelectUser" class="readonly" readonly value="">
    </span>

   </table>
   &nbsp;
   <table class=sub>
   <tr>
     <td class=button width=33%>
       <input class="button" name="ok" type="button" alt="<s:text name='prompt.ok'/>"  value="<s:text name='prompt.ok'/>" onclick="submitBatchTask();">
     </td>
     <td class=button width=33%>
       <input name="prev" class="button" type="button" alt="<s:text name='prompt.preStep'/>" value="<s:text name='prompt.preStep'/>" onclick="preWindow();">
     </td>
   </tr>
  </table>
  &nbsp;
  
  <table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr class=listtitle>
      <td><s:text name="undwrt.HebaoTaskDealQuery.contractNo"/></td>
      <td>
      		<s:text name="undwrt.HebaoTaskDealQuery.businessNo"/>
      </td>
      <td><s:text name="undwrt.HebaoTaskDealQueryResult.insuredName"/></td>
      <td><s:text name="undwrt.HebaoTaskDealQueryResult.submitTime"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.hierarchy"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.anyStatus"/></td>
      <td><s:text name="undwrt.pages.undwrtDeal.flowDirection"/></td>
    </tr>
   <logic:notEmpty  name="wfLogList"  >
   <logic:iterate indexId="index" id="wfLogList"  name="wfLogList">
      <tr class=listeven>
        <td> <bean:write name="wfLogList" property="contractNo"/></td>
        <td> <bean:write name="wfLogList" property="businessNo"/></td>
        <td> <bean:write name="wfLogList" property="insuredName"/></td>
        <td> <bean:write name="wfLogList" property="flowInTime"/></td>
        <td> <bean:write name="wfLogList" property="nodeName"/></td>
        <td> <bean:write name="wfLogList" property="nodeStatusName"/></td>
        <td> <bean:write name="wfLogList" property="flowStatusName"/></td>
        <!--隐含域-->
       	<span style="display:none" >
			    <input name="businessNo" value="<bean:write name="wfLogList" property="businessNo"/>">
			    <input name="businessType" value="<bean:write name="wfLogList" property="businessType"/>">
          <input name="contractNo" value="<bean:write name="wfLogList" property="contractNo"/>">
			    <input name="flowID"     value="<bean:write name="wfLogList" property="flowID"/>">
			    <input name="packageID"  value="<bean:write name="wfLogList" property="packageID"/>">
			    <input name="logNo"      value="<bean:write name="wfLogList" property="logNo"/>">
			    <input name="modelNo"    value="<bean:write name="wfLogList" property="modelNo"/>">
			    <input name="nodeNo"     value="<bean:write name="wfLogList" property="nodeNo"/>">
			    <input name="insuredName"  value="<bean:write name="wfLogList" property="insuredName"/>">
			    <input name="flowInTime" value="<bean:write name="wfLogList" property="flowInTime"/>">
          <input name="nodeName"     value="<bean:write name="wfLogList" property="nodeName"/>">			    
          <input name="timeLimit"     value="<bean:write name="wfLogList" property="timeLimit"/>">			    
			    <input name="nodeStatusName" value="<bean:write name="wfLogList" property="nodeStatusName"/>">
			    <input name="deptCode"   value="<bean:write name="wfLogList" property="deptCode"/>">
			    <input name="nodeStatus" value="<bean:write name="wfLogList" property="nodeStatus"/>">
			    <input name="operatorCode" value="<bean:write name="wfLogList" property="operatorCode"/>">
			    <input name="flowStatus" value="<bean:write name="wfLogList" property="flowStatus"/>">
			 	</span>        
      </tr>
   </logic:iterate>
   </logic:notEmpty>    
  </table>  
 </form>

 </body>
</html>