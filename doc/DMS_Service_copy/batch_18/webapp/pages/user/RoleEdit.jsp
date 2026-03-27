<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title><s:text name="role.edit" />
		</title>
		<%@ include file="/common/meta.jsp"%>
		<s:head />
	</head>
	<body id="all_title">
		<div id="container">
			<div id="header">
				<h1>
					<s:text name="role.edit" />
				</h1>
			</div>
			<s:fielderror />
			<s:form action="update" method="post" namespace="/role"
				validate="true">
				<s:hidden name="role.id" value="%{role.id}" />
				<table class="fix_table" border="0">
					<s:textfield name="role.rolename" key="role.rolename"
						cssClass="input_w w_15" />
					<s:textfield name="role.privilegesFlag" key="role.privilegesFlag"
						cssClass="input_w w_15" />
				</table>
			</s:form>
		</div>
	</body>
</html>
