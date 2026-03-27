<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title></title>
</head>
<body>
<s:form action="./submitTask.do">
	<s:hidden name="taskid" value="%{taskid}"></s:hidden>
	<s:hidden name="tokenName" value="%{tokenName}"></s:hidden>
	<s:radio list="transitionNames" name="transitionName"></s:radio>
	<s:textarea name="handletext"></s:textarea>
	<s:submit value="提交" cssClass="button_ty"></s:submit>
</s:form>
</body>
</html>