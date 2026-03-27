<%--
****************************************************************************
* DESC       : 理赔流转讨论留言列表显示页面
* AUTHOR     : Sunhao
* CREATEDATE : 2004-07-28
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<title><s:text name="title.message.messageList" /></title>
<!-- 留言列表 -->
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard_green.css">
</head>
<body>
	<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
		<tr class=listtitle>
			<td width="5%" align="center" nowrap>
				<s:text name="db.prpDrate.serialNo" />
			</td>
			<!-- 序号 -->
			<td width="10%" align="center" nowrap>
				<div align="center">
					<s:text name="currentTime" />
				</div>
			</td>
			<!-- 时间 -->
			<td width="10%" align="center" nowrap>
				<s:text name="guarantee.nodeType" />
			</td>
			<!-- 节点类型 -->
			<td width="10%" align="center" nowrap>
				<s:text name="message.leaveMessage" />
			</td>
			<!-- 留言人 -->
			<td width="65%">
				<div align="center">
					<s:text name="message.writeMessage" />
				</div>
			</td>
			<!-- 记录信息 -->
		</tr>
		<c:forEach items="${prpLmessageList}" var="prpLmessage" varStatus="messageStatus">
			<c:if test="${messageStatus.count%2==0}">
				<tr class=listodd>
			</c:if>
			<c:if test="${messageStatus.count%2!=0}">
				<tr class=listeven>
			</c:if>
			<td>${messageStatus.count }</td>
			<td>
				<!-- ${prpLmessage.inputDate } -->
				<rc:rcDate name="prpLmessageInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLmessage.inputDate }" />
			</td>
			<td>${prpLmessage.nodeType}</td>
			<td>${prpLmessage.operatorName}</td>
			<td>
				<textarea style="overflow-x: visible;" readonly="true" cols="70" rows="3">${prpLmessage.context}</textarea>
			</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>