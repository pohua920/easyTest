<%@ page contentType="text/html; charset=GBK" %>
<%@ page isErrorPage="true"%>
<%@ page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<html>
<head>
	<link href="/undwrt/css/Standard.css" rel="stylesheet" type="text/css">
  	<script language=javascript>
	  	function dealReturn()
	  	{
	  		fm.target = "fraInterface";
	  		fm.action = "/undwrt/taskCheck/commonCheckTask.do";
			fm.method="post";
			fm.submit();
	  	}
  	</script>
</head>
<body>
	  <form name="fm" action="/undwrt/CommonCheckTask.do">
<%
	  UserException usee = (UserException)session.getAttribute("userException");
	  String errorMessage = "";
	  String title        = "";
	  if(usee!=null&&!usee.equals("")){
	    errorMessage = usee.getErrorMessage();
	    title        = usee.getErrorModule();
	  }
%>
	<!--“˛∫¨”Ú-->
	<span style="display:none">
		<input name="iBusinessNo" value="<%=request.getParameter("BusinessNo")%>">
		<input name="iBusinessType" value="<%=request.getParameter("BusinessType")%>">
		<input name="iComCode" value="<%=request.getParameter("iComCode")%>">
		<input name="iContractNo" value="<%=request.getParameter("ContractNo")%>">
		<input name="iFlowID" value="<%=request.getParameter("FlowId")%>">
		<input name="iPackageID" value="<%=request.getParameter("iPackageID")%>">
		<input name="iModelNo" value="<%=request.getParameter("ModelNo")%>">
		<input name="iNodeNo" value="<%=request.getParameter("NodeNo")%>">
		<input name="iFlowStatus" value="<%=request.getParameter("iFlowStatus")%>">
		<input name="iDeptCode" value="<%=request.getParameter("iDeptCode")%>">
		<input name="iFlowInTime" value="<%=request.getParameter("iFlowInTime")%>">
		<input name="iNodeStatus" value="<%=request.getParameter("iNodeStatus")%>">
		<input name="iLogNo" value="<%=request.getParameter("LogNo")%>">
		<input name="iRiskCode" value="<%=request.getParameter("iRiskCode")%>">
		<input name="iClassCode" value="<%=request.getParameter("classCode")%>">
		<input name="iNodeName" value="<%=request.getParameter("iNodeName")%>">
	</span>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
    		<td height="20" align="center" class="menu2">
    			<img src="/undwrt/common/images/tanhao.gif"  border="0" align="absmiddle">&nbsp;&nbsp;<font size=4><b>±ß«∏,ƒ˙üoô‡åè∫ÀÕ®ﬂ^£°</b></font></td>
        </tr>
        <tr>
     		<td align="left" class="menu">
     			<font size=3><b><s:text name="undwrt.CheckAdvanceConditionErrorPage.reasonShow"/>£∫</b></font></td>
        </tr>
        <tr id="trContent">
          	<td align=left colspan="2"><br>
          		&nbsp;&nbsp;&nbsp;&nbsp;<font size=3><%=title%></font><br>    
          		&nbsp;&nbsp;&nbsp;&nbsp;<%=errorMessage%><br>
          		&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="undwrt.CheckAdvanceConditionErrorPage.clickBackAlertOrSubmit"/>
       		</td>
        </tr>
        
		<tr>
    		<td align=center colspan="2"> 
           		<br><br><br><br>
              	<input type="button" class="button" value="<s:text name='prompt.back'/>" onclick = "history.back();">
   			</td>
        </tr>
	</table>
    </form>
	<%session.setAttribute("userException","");%>
	</body>
</html>