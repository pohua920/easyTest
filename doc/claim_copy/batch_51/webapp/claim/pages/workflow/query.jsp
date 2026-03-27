<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%

%>
<title>JBPM工作流任務回滾作業查詢</title>
</head>
<body class="body_12" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form action="${ctx}/pages/workflow/rollback/list.jsp" method="post">
		<div align="center">
			流程業務號碼（businessId） : 
			<input type="text" name="businessId" style="width: 250px" value="<c:out value='${param.businessId}'></c:out>"/>&nbsp;&nbsp;
			<input type="submit" value="查詢">
		</div>
	</form>
</body>
</html>