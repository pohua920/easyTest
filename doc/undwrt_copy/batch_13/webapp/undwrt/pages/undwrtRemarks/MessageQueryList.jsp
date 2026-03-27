<%--
****************************************************************************
* DESC       : 留言列表显示页面
* AUTHOR     : liuyang
* CREATEDATE : 2005-02-03
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<!-- 滚动条样式定义 -->
<%@ include file="/pages/undwrtDeal/CommonStyle.html"%>
<html locale="true">
<head>
<link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
<title><s:text name="undwrt.MessageQueryList.leaveMsgList"/></title>
<script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<table class="common" cellpadding="5" cellspacing="1" align="center">
  <tr class=listtitle>
    <td width="10%"><s:text name="undwrt.MessageQueryList.serialNo"/></td>
    <td width="25%"><s:text name="undwrt.MessageQueryList.time"/></td>
    <td width="20%"><s:text name="undwrt.MessageQueryList.leavePerson"/></td>
    <td width="45%"><s:text name="undwrt.MessageQueryList.talkLeaveMsg"/></td>
  </tr>
  <%int index=1;%>
  <s:if test="#request.WfMessageList!=null">
   <s:iterator id="wfMessage" status="statu" value="#request.WfMessageList">
  <tr class=common>
<%
          if(index %2== 0)
          {
               out.print("<tr class=listodd>");
          }
          else
          {
               out.print("<tr class=listeven>");
          } 
%>
      <td><%=index%></td>
      <td><rc:rcDate name = "#wfMessage.operateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
      <td><s:property value="#wfMessage.operatorName"/></td>
      <td><textarea readonly="true" cols="50" rows="3"><s:property value="#wfMessage.context"/></textarea></td>
  </tr>
<%index++;%>
  </s:iterator>
  </s:if>
</table>
<form name="fm" method="post" action="/undwrt/common/messageRemarkQueryInfo.do?actionType=save">
<table class="common" cellpadding="5" cellspacing="1" align="center">
  <input name="messageId" type="hidden" value="<s:property value="#request.WfMessageDto.messageId"/>">
	<tr >
	  <td class="title2"><s:text name="undwrt.MessageQueryList.businessNo"/>：</td>
    <td class="input2" ><input name="businessNo" class="readonly" readonly="true" value="<s:property value="#request.WfMessageDto.businessNo"/>"></td>
  </tr>
  <tr>
    <td class="title2" ><s:text name="undwrt.MessageQueryList.outBillDate"/>：</td>
	  <td class="input2" ><input name="operateTime" class="readonly" readonly="true" value="<rc:rcDate name = "#request.WfMessageDto.operateTime" format="yyyy-MM-dd HH:mm:ss"/>"></td>
	</tr>
	<tr >
	  <td class="title2"><s:text name="undwrt.MessageQueryList.operatorCode"/>：</td>
    <td class="input2"><input name="operatorCode" class="readonly" readonly="true" value="<s:property value="#request.WfMessageDto.operatorCode"/>"></td>
  </tr>
  <tr>
     <td class="title2"><s:text name="undwrt.MessageQueryList.operatorName"/>：</td>
	  <td class="input2"><input name="operatorName" class="readonly" readonly="true" value="<s:property value="#request.WfMessageDto.operatorName"/>"></td>
	</tr>    
	<tr>
	    <td class="title2" colspan="2"><div align="center" class="style1"><s:text name="undwrt.MessageQueryList.talkLeaveMsg1"/></div></td>
	</tr>
	<tr >
    <td colspan="2"  class="title2" align="center"><textarea class=common name="Context" cols="50" rows="5"></textarea></td>
  </tr>
</table>
<table width="100%">
    <tr>
    <td align="center">
    <input type="button" class=button name="Submit" value="<s:text name='undwrt.save'/>" onclick="return saveMessage();"></td>
  </tr>
</table>
</form>
</body>
</html>
<script language="javascript">
function saveMessage()
{
   if(fm.Context.value=="")
   {
     alert("<s:text name='undwrt.MessageQueryList.pleaseInTalkMessage'/>");
     return;
   } 
   if(fm.Context.value.length>23)
	{
	   alert("<s:text name='undwrt.MessageQueryList.messageTooLong'/>");
	   return;   
	}
   fm.target = "_self";
   fm.submit();
}
</script>