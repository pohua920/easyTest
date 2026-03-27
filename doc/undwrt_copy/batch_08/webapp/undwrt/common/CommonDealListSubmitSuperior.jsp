<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     :
* CreateDate :
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<%
	String strHandType = (String)session.getAttribute("handType");
	String strEditType = (String)session.getAttribute("editType");
	String strHandTitle = (String)session.getAttribute("handTitle");
	String strEditTitle = (String)session.getAttribute("editTitle");
%>
<html>
	<head>
		<title><%=strEditTitle%><%=strHandTitle%><s:text name='undwrt.CommonDealList.task'/></title>
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
  	<form name="fm" action="/undwrt/commonDealSubmit/commonSubmitTask.do">
		<input type="hidden" name="submitTip">
		<br>
  		<table class="common" cellpadding="5" cellspacing="1" align="center">
  			<tr>
   				<td class="formtitle" colspan="4">
   					<s:text name='undwrt.pages.undwrtDeal.submit'/><%=strHandTitle%><s:text name='undwrt.CommonDealList.task'/>&nbsp;&nbsp;<s:text name='undwrt.pages.undwrtDeal.submitSuperior'/>
   				</td>
  			</tr>
		  	<tr class="listtitle">
				<td><s:text name='undwrt.pages.undwrtDeal.chooseUpLevel'/></td>
				<td><s:text name='undwrt.pages.undwrtDeal.rank'/></td>
		    	<td><s:text name='undwrt.pages.undwrtDeal.gradeName'/></td>
		  	</tr>
		  	<s:iterator value="#session.submitList">
  			<tr>
			    <td class="text" style="width:20%">
					<input type="radio" name="radSelectNode" value="0" 
						   onclick="setSelectNode('<s:property value="endNodeName"/>');"><s:text name='undwrt.CommonDealList.choose'/>
						   
				</td>
			    <td class="text" style="width:40%">
					<input type="text" class="readonly" readonly name="NodeNo"
			               value = "<s:property value="endNodeNo" />">
				</td>
			    <td class="text" style="width:40%">
					<input type="text" class="readonly" readonly name="NodeName"
			               value = "<s:property value="endNodeName" />">
				</td>
  			</tr>
			</s:iterator>
  		</table>
  		
   		<!--隐含域-->
		<span style="display:none">
			<input type="hidden" name="EditType" value="<s:property value="editType"/>">
            <input type="hidden" name="HandType" value="<s:property value="handType"/>">
    		<input type="radio" name="radSelectNode" value="0">
    		<input name="FlowId" 		value="<%=request.getParameter("FlowId")%>">
    		<input name="ModelNo" 		value="<%=request.getParameter("ModelNo")%>">
      		<input name="NodeNo"        value="">
			<input name="NodeName"      value="">
		    <input name="LogNo"         value="<%=request.getParameter("LogNo")%>">
		    <input name="CertiType"     value="<%=request.getParameter("BusinessType")%>">
		    <input name="BusinessNo"    value="<%=request.getParameter("BusinessNo")%>">
		    <input name="BusinessType"  value="<%=request.getParameter("BusinessType")%>">
		    <input name="FlowStatus"    value="0">
		    <input name="Flag"          value="1">
		    <input name="OperatorCode"  value="<%=request.getParameter("OperatorCode")%>">
		    <input name="SingleSubmit"  value="">
		    <input name="MultiSubmit"   value="">
		    <input name="selectNodeNo">
		    <input name="selectNodeName">
		    <input name="ModelNo">
		    <input name="GradeCode"		value="<%=request.getParameter("GradeCode")%>">
		    <input name="GradeValue"  	value="<%=request.getParameter("GradeValue")%>">
		    <input name="MaxUsableRate"	value="<%=request.getParameter("MaxUsableRate")%>">
		    <input name="DisRate"     	value="">
		    <input name="ClassCode"   	value="<%=request.getParameter("hiClassCode")%>">
		    <input name="RiskCode"     	value="<%=request.getParameter("riskCode")%>">
		    <input name="HistoryBusiness"	value="<%=request.getParameter("HistoryBusiness")%>">
		    
		    <input name="iBusinessNo" 	value="<%=request.getParameter("BusinessNo")%>">
			<input name="iBusinessType" value="<%=request.getParameter("BusinessType")%>">
			<input name="iComCode" 		value="<%=request.getParameter("iComCode")%>">
		 	<input name="iContractNo" 	value="<%=request.getParameter("ContractNo")%>">
			<input name="iFlowID" 		value="<%=request.getParameter("FlowId")%>">
			<input name="iPackageID" 	value="<%=request.getParameter("iPackageID")%>">
			<input name="iModelNo" 		value="<%=request.getParameter("ModelNo")%>">
			<input name="iNodeNo" 		value="<%=request.getParameter("NodeNo")%>">
			<input name="iFlowStatus" 	value="<%=request.getParameter("iFlowStatus")%>">
			<input name="iDeptCode" 	value="<%=request.getParameter("iDeptCode")%>">
			<input name="iFlowInTime" 	value="<%=request.getParameter("iFlowInTime")%>">
			<input name="iNodeStatus" 	value="<%=request.getParameter("iNodeStatus")%>">
			<input name="iLogNo" 		value="<%=request.getParameter("LogNo")%>">
			<input name="iRiskCode" 	value="<%=request.getParameter("iRiskCode")%>">
			<input name="iClassCode" 	value="<%=request.getParameter("classCode")%>">
			<input name="iNodeName" 	value="<%=request.getParameter("iNodeName")%>">
   		</span>
   		<br>
   		<table class="common" cellpadding="5" cellspacing="1" align="center">
     		<tr>
       			<td class="text">
         			<s:text name='undwrt.pages.undwrtDeal.submitSuperior'/>：<input type="text" name="SelectNode" class="readonly" readonly style="width:80%" value="">
         		</td>
     		</tr>
    		<span style="display:none">
       			<input type="text" name="SelectUser" class="readonly" readonly value="">
       			<input type="hidden" name="submitPage" value="1" description="<s:text name='prompt.messages.confirmPageIsSubmitPage'/>">       
    		</span>
   		</table>
		<br>
	   	<table class="sub">
   			<tr>
     			<td class=button>
       				<Input class="button" name="ok" type="button" value="<s:text name='prompt.ok'/>" 
       					onclick="submitTask();">
	   				&nbsp;&nbsp;&nbsp;&nbsp;
	   				<Input name="prev" class="button" type="button" value="<s:text name='prompt.cancel'/>" 
	   					onclick="dealReturn();">
     			</td>
   			</tr>
  		</table>
 	</form>
 	</body>
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
</html>