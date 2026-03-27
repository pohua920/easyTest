<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
	<head>
		<title><s:text name="role.show"/></title>
		<%@ include file="/common/meta.jsp"%>
	</head>
	<body  id="all_title">
	 <div id="container">
		<div id="header">
			<h1><s:text name="role.show"/></h1>
		</div>
		
		<s:form>
			<table class="fix_table" border="0">
				<s:textfield name="role.id" key="prompt.id" disabled="true" cssClass="input_w w_15" />
				<s:textfield name="role.rolename" key="role.rolename" cssClass="input_w w_15" />
				<s:textfield name="role.privilegesFlag" key="role.privilegesFlag" cssClass="input_w w_15" />
			</table>
		</s:form>
		</div>
	</body>
</html>