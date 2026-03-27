<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : luyang
* CreateDate : 2004-12-30 10:53
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
  <head>
    <title><s:property value="handTitle"/><s:text name='undwrt.pages.undwrtDeal.task'/></title>
	<jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <!--通用函数-->
    <script src="/undwrt/common/js/Common.js"></script>

    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    <script language=javascript>
	  	function dealReturn()
	  	{
	  		var oldAction = fm.action;
	  		fm.target = "fraInterface";
	  		fm.action="/undwrt/taskCheck/commonCheckTask.do";
			  fm.method="post";
			  fm.submit();
			  fm.action = oldAction;
	  	}
	  </script>

  </head>
  <body class=interface>
  <form name="fm" action="/undwrt/CommonSubmitTask.do">
		<input type="hidden" name="submitTip">
  <table class="common" cellpadding="5" cellspacing="1" align="center">
<!--
  <tr>
   <td class="formtitle" colspan="3">提交handtitle任务</td>
  </tr>
  <tr class="listtitle">
	<td>选择上级</td>
	<td>上级节点号</td>
    <td>上级名称</td>
    <td>默认路径</td>
  </tr>
<logic:notEmpty  name="submitList">
<logic:iterate indexId="index" id="submitList"  name="submitList">
  <tr>
    <td class="text" style="width:20%">
		<input type="radio" name="radSelectNode" value="0" 
			   onclick="setSelectNode('<bean:write name="submitList" property="endNodeName"/>');">选择
	</td>
    <td class="text" style="width:40%">
		<input type="text" class="readonly" readonly name="NodeNo"
               value = "<bean:write name="submitList" property="endNodeNo" />">
	</td>
    <td class="text" style="width:40%">
		<input type="text" class="readonly" readonly name="NodeName"
               value = "<bean:write name="submitList" property="endNodeName" />">
	</td>
    <td class="text" style="width:30%"><input type="text" class="readonly" readonly name="DefaultPath"
                                        value="<logic:equal name="submitList" property="defaultFlag" value="0">否</logic:equal><logic:equal name="submitList" property="defaultFlag" value="1">是</logic:equal>" >
  	</td>
  </tr>
</logic:iterate>
</logic:notEmpty>
-->
  <tr>
   <td class="formtitle" colspan="3"><s:text name='undwrt.BatchTaskSubmitList.submit'/><s:property value="handTitle"/><s:text name='undwrt.pages.undwrtDeal.task'/>&nbsp;&nbsp;<s:text name='undwrt.pages.undwrtDeal.issuedModify'/></td>
  </tr>
  <tr class="listtitle">
    <td><s:text name='undwrt.BatchTaskSubmitList.chooseDownLevel'/></td>
  	<td><s:text name='undwrt.HebaoTaskDealQueryResult.level'/></td>
    <td><s:text name='undwrt.pages.undwrtDeal.gradeName'/></td>
    <!--<td>默认路径</td>-->
  </tr>
<s:if test="colBackList!= null">
<s:iterator value="colBackList" status="statu" id="colBackList">
<tr>
   <td class="text">
     <input type="radio" name="radSelectNode" value="0" 
			onclick="setSelectNode('<s:property value="#colBackList.nodeName"/>');"><s:text name='undwrt.CommonDealList.choose'/></td>
   <td class="text">
     <input type="hidden" value="<s:property value="#colBackList.logNo"/>"> 
     <input type="text" class="readonly" readonly name="NodeNo" value = "<s:property value="#colBackList.nodeNo"/>"></td>
   <td class="text">
     <input type="text" class="readonly" readonly name="NodeName" value = "<s:property value="#colBackList.nodeName"/>"> </td>
  </tr>
</s:iterator>
</s:if>
  </table>
   <!--隐含域-->
   <span style="display:none">
     <input type="text" name="EditType" value="<s:property value="editType"/>">
     <input type="text" name="HandType" value="<s:property value="handType"/>">
    <input type="radio" name="radSelectNode" value="0">
    <input name="FlowId"       value='<s:property value='FlowId'/>'>
    <input name="ModelNo"       value='<s:property value='ModelNo'/>'>
    <input name="NodeNo"        value="">
	  <input name="NodeName"    value="">
    <input name="LogNo"        value='<s:property value='LogNo'/>'>
    <input name="CertiType"    value='<s:property value='CertiType'/>'> 
    <input name="BusinessNo"    value='<s:property value='BusinessNo'/>'>
    <input name="BusinessType"  value='<s:property value='BusinessType'/>'>
    <input name="FlowStatus"    value="1">
    <input name="Flag"          value="1">
    <input name="OperatorCode"  value='<s:property value='OperatorCode'/>'>
    <input name="SingleSubmit"   value="">
    <input name="MultiSubmit"    value="">
    <input name="selectNodeNo"   value='<s:property value='selectNodeNo'/>'>
    <input name="selectNodeName"  value="">
    <input name="ModelNo">
    <input name="GradeCode"        value='<s:property value='GradeCode'/>'> 
    <input name="GradeValue"         value='<s:property value='GradeValue'/>'>
    <input name="MaxUsableRate"        value='<s:property value='MaxUsableRate'/>'> 
    <input name="DisRate"         value='<s:property value='DisRate'/>'>
     <!--  <input name="ClassCode"         value='<s:property value='hiClassCode'/>'>
   <input name="RiskCode"        value='<s:property value='riskCode'/>'>  -->
    <input name="HistoryBusiness"        value='<s:property value='HistoryBusiness'/>'> 
    
 		<input name="iBusinessType" value='<s:property value='BusinessType'/>'>
 		<input name="iBusinessNo"    value='<s:property value='BusinessNo'/>'>
 		<input name="riskCode" value='<s:property value='iRiskCode'/>'>
 		<input name="iFlowID" value='<s:property value='FlowId'/>'>
		<input name="iComCode" value='<s:property value='iComCode'/>'>
		<input name="comCode" value='<s:property value='iComCode'/>'>
 		<input name="iContractNo" value='<s:property value='ContractNo'/>'>
 		<input name="iModelNo" value='<s:property value='ModelNo'/>'>
 		<input name="iNodeNo" value='<s:property value='NodeNo'/>'>
 		<input name="iLogNo" value='<s:property value='LogNo'/>'>
 		<input name="iRiskCode" value='<s:property value='iRiskCode'/>'>
		<input name="iPackageID" value='<s:property value='iPackageID'/>'>
		<input name="iFlowStatus" value='<s:property value='iFlowStatus'/>'>
		<input name="iDeptCode" value='<s:property value='iDeptCode'/>'>
		<input name="iFlowInTime" value='<s:property value='iFlowInTime'/>'>
		<input name="iNodeStatus" value='<s:property value='iNodeStatus'/>'>
		
		<input name="RiskCode" value='<s:property value='iRiskCode'/>'>
		<input name="ClassCode" value="<%=request.getParameter("hiClassCode")%>">
		<input name="iNodeName" value='<s:property value='iNodeName'/>'>
   </span>
   &nbsp;
   <table class="common" cellpadding="5" cellspacing="1" align="center">
     <tr>
       <td class="text">
         <s:text name='prompt.messages.downSendDownLevel'/>：<input type="text" name="SelectNode" class="readonly" readonly style="width:80%" value=""></td>
     </tr>
    <span style="display:none">
       <input type="text" name="SelectUser" class="readonly" readonly value="">
       <input type="hidden" name="submitPage" value="1" description="<s:text name='prompt.messages.confirmPageIsSubmitPage'/>">       
    </span>
   </table>
   &nbsp;
   <table class="sub">
   <tr>
<!--
<%
  if(AppConfig.get("sysconst.SUBMITSINGER")!=null && AppConfig.get("sysconst.SUBMITSINGER").equals("1"))
  {   
%>
     <td class=button width=34%>
       <Input name="people" class="button" type="button" alt="指定人员" value="指定人员" onclick="selectPeople();">
     </td>    
<%
  }
%>
-->
     <td class=button>
       <Input class="button" name="ok" type="button" value="<s:text name='prompt.ok'/>" onclick="submitTask();">
	   &nbsp;&nbsp;&nbsp;&nbsp;
	   <Input name="prev" class="button" type="button" value="<s:text name='prompt.cancel'/>" onclick="dealReturn();">
     </td>
   </tr>
  </table>
 </form>
 </body>
</html>
<script language="javascript">
	function selectNode()
	{
		if(fm.radSelectNode.length > 0)
		{
			fm.radSelectNode.item(0).checked = true;
			fm.SelectNode.value = fm.NodeName[0].value;
		}
	}
	window.onload = selectNode;
</script>