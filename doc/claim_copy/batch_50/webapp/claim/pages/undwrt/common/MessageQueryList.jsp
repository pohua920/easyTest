<%--
****************************************************************************
* DESC       : 留言列表显示页面
* AUTHOR     : liuyang
* CREATEDATE : 2005-02-03
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<title><s:text name="title.message.messageList"/></title><%-- 留言列表 --%>
</head>
<body>
<table class="common" cellpadding="5" cellspacing="1" align="center">
  <tr class=listtitle>
    <td width="10%"><s:text name="regist.prpLregist.serialNo"/></td><%-- 序号 --%>
    <td width="25%"><s:text name="currentTime"/></td><%-- 时间 --%>
    <td width="20%"><s:text name="message.leaveMessage"/></td><%-- 留言人 --%>
    <td width="45%"><s:text name="undwrt.DiscussionMessages"/></td> <%-- 讨论留言 --%>
  </tr>
  <c:if test="${WfMessageList!=null}">
  	<c:forEach items="${WfMessageList}" var="wfMessage" varStatus="wfMessage_status">
	  <tr <c:if test="${wfMessage_status.count%2==0 }">class="listodd"</c:if><c:if test="${wfMessage_status.count%2!=0 }">class="listeven"</c:if>>
	      <td>${wfMessage_status.count }</td>
	      <td>${wfMessage.operateTime}</td>
	      <td>${wfMessage.operatorName}</td>
	      <td><textarea readonly="true" cols="50" rows="3">${wfMessage.context}</textarea></td>
	  </tr>
  </c:forEach>
  </c:if>
</table>
<form name="fm" method="post" action="${ctx }/messageRemarkQueryInfo.do?actionType=save">
<table class="common" cellpadding="5" cellspacing="1" align="center">
  <input name="messageId" type="hidden" value="${WfMessageDto.messageID }">
	<tr >
	  <td class="title2"><s:text name="sendUndwrt.BusinessNumber"/>：</td><%-- 业务号 --%>
    <td class="input2" ><input name="businessNo" class="readonly" readonly="true" value="${WfMessageDto.businessNo}"></td>
  </tr>
  <tr>
    <td class="title2" ><s:text name="db.prpLlawsuit.inputDate"/>：</td><%-- 输单日期 --%>
	  <td class="input2" ><input name="operateTime" class="readonly" readonly="true" value="${WfMessageDto.operateTime}"></td>
	</tr>
	<tr >
	  <td class="title2"><s:text name="db.prpLarrearageNew.operatorCode"/>：</td><%-- 操作员代码 --%>
    <td class="input2"><input name="operatorCode" class="readonly" readonly="true" value="${WfMessageDto.operatorCode}"></td>
  </tr>
  <tr>
     <td class="title2"><s:text name="guarantee.operateName"/>：</td><%-- 操作员名称 --%>
	  <td class="input2"><input name="operatorName" class="readonly" readonly="true" value="${WfMessageDto.operatorName}"></td>
	</tr>    
	<tr>
	    <td class="title2" colspan="2"><div align="center" class="style1"><s:text name="title.prepayBeforeEdit.editPrepay"/><s:text name="undwrt.DiscussionMessages"/></div></td><%-- 讨论留言 --%>
	</tr>
	<tr >
    <td colspan="2"  class="title2" align="center"><textarea class=common name="Context" cols="50" rows="5"></textarea></td>
  </tr>
</table>
<table width="100%">
    <tr>
    <td align="center">
    <input type="button" class=button name="Submit" value="<s:text name='button.save.value'/>" onclick="return saveMessage();"></td><%-- 保 存 --%>
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
     alert("请输入“讨论留言”！");
     return;
   }
   fm.target = "_self";
   fm.submit();
}
</script>