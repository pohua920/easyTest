<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<title>審批任務提交上級</title>
<script type="text/javascript">
  function submitTask(){
	  fm.submit();
  }
</script>
<body class=interface>
	<form name="fm" action="${ctx }/audit/submitTask.do">
	    <input type="hidden" name = "auditType" value="${param.auditType}">
	    <input type="hidden" name = "editType" value="superior">
	    <input type="hidden" name = "swfLogFlowID" value="${param.swfLogFlowID}">
	    <input type="hidden" name = "swfLogLogNo" value="${param.swfLogLogNo}">
	    <input type="hidden" name = "businessNo" value="${param.businessNo}">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="formtitle" colspan="4">
					審批任務&nbsp;&nbsp;提交上級
				</td>
			</tr>
			<tr class="listtitle">
				<td>選擇上級</td>
				<td>級別</td>
				<td>級別名稱</td>
			</tr>
			<c:forEach items="${requestScope.nextNodeList}" var="tempSwfConfig" varStatus="stat">
				<tr class="common">
					<td  style="width: 20%" align="center">
						<input type="radio" name="nextActorId" value="${tempSwfConfig.id.actorId}" <c:if test="${stat.count==1}">checked="checked"</c:if>><s:text name="archive.choice" /><%-- 选择 --%>
					</td>
					<td  style="width: 40%" align="center"><c:out value="${tempSwfConfig.nodeNo}"/></td>
					<td  style="width: 40%" align="center"><c:out value="${tempSwfConfig.nodeName}"/></td>
				</tr>
			</c:forEach>
			<c:if test="${empty requestScope.nextNodeList}">
			    <tr class="common"><td align="center" colspan="3">當前已經是最高級，請點擊“取消”按鈕返回處理。</td></tr>
			</c:if>
		</table>
		&nbsp;
		<table class="common" cellpadding="5" cellspacing="1" align="center" <c:if test="${empty requestScope.nextNodeList}">style="display: none"</c:if> >
			<tr>
				<td class="formtitle" colspan="4">
					审批意见
				</td>
			</tr>
			<tr>
				<td class="left" style="width: 20%">处理意见：</td>
				<td class="right" style="width: 80%">
				    <select name="swfNotionFlag" >
					    <option value="3" selected="selected">提交上級</option>
				    </select>
				</td>
			</tr>
			<tr>
			    <td class="left" style="width: 20%">内容：</td>
				<td class="right" style="width: 80%">
				   <input type="text" class="input" name="swfNotionHandleText" class="input" style="width: 80%" value="">
				</td>
			</tr>
		</table>
		&nbsp;
		<table class="sub">
			<tr>
				<td class=button>
				    <Input class="button" name="ok" type="button" value="<s:text name='button.determine.value'/>" onclick="submitTask();" <c:if test="${empty requestScope.nextNodeList}">disabled="disabled"</c:if>>
					<%-- 确定 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<Input name="prev" class="button" type="button" value="<s:text name='button.cancel.value'/>" onclick="history.back();">
					<%-- 取消 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
