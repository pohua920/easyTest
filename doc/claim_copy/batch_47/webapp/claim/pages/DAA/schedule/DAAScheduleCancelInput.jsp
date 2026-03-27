<%--
****************************************************************************
* DESC       ：报案注销处理页面
* AUTHOR     ：sinosoft
* CREATEDATE ：2005-07-14
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<head>
<html locale="true"> 
 <!--立案注销/拒赔处理入口-->
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  
   <script language=javascript>
   function submitForm() {
		var context = fm.contextInnerHTML.value;
		if (context.length < 1) {
			alert("註銷原因不能为空!");
			fm.contextInnerHTML.focus();
			return false;
		}
		fm.submit();
		fm.buttonSave.disabled = true;
	}
    </script>
  </head>
<body class=interface onload="initPage()">
	<form name=fm action="/claim/flowNodeCancel.do" method="post" onsubmit="return validateForm(this);">
         <c:set var="nodeName" value="車輛定損" scope="page"/>
        <c:if test="${param.nodeType=='wound'}">
        	<c:set var="nodeName" value="人傷" scope="page"/>
        </c:if>
        <c:if test="${param.nodeType=='propc'}">
        	<c:set var="nodeName" value="財產損失" scope="page"/>
        </c:if>
		<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID }">
		<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo }">
		<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
		<input type="hidden" name="editType" value="ScheduleCancel">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="schedule.cancellProcessTask" />
				</td>
			</tr>
			<%--定损任务注销处理 --%>
			<tr>
				<td class="title" style="width: 15%; valign: bottom">
					<s:text name="db.prpLclaim.registNo" />:
				</td>
				<%--备案号码--%>
				<td class="input" style="width: 35%; valign: bottom">
					<input type="text" name="RegistNo" class="readonly" title="備案號碼" maxlength="22" readonly="true" value="${param.registNo }">
				</td>
				<td class="title" style="width: 15%; valign: bottom">
					<s:text name="db.view_loan.policyNo" />:
				</td>
				<%--保单号码 --%>
				<td class="input" style="width: 35%; valign: bottom">
					<input class="readonly" readonly value="${param.policyNo }">
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 15%; valign: bottom">
					<s:text name="schedule.typeFee" />:
				</td>
				<%--定损类型 --%>
				<td class="input" style="width: 35%; valign: bottom">
					<input class="readonly" readonly value="${nodeName }">
				</td>
				<td class="title" style="width: 15%; valign: bottom">
					<s:text name="schedule.feeMark" />:
				</td>
				<%--定损标的 --%>
				<td class="input" style="width: 35%; valign: bottom">
					<input class="readonly" readonly value="${param.lossItemName }">
				</td>
			</tr>
			<tr>
				<td class="title" colspan="6">
					<s:text name="regist.prpLregist.cancleReason" />
					<%--注销原因： --%>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="6" align="center">
					<textarea wrap="hard" rows=15 cols=80 style="width: 505px" maxlen=80 name="contextInnerHTML"></textarea>
				</td>
			</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class=button style="width: 25%" align="center">
					<!--确 定按钮-->
					<input type=button name=buttonSave class='button' value="<s:text name='button.submit.value' />" onClick="submitForm();">
				</td>
				<!--取消按钮-->
				<td class=button style="width: 25%" align="center">
					<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="history.back();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
