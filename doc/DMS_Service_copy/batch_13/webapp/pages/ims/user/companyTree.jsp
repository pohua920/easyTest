<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
		function doAction(url){
			fm.action = url;
			fm.submit();
		}
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	target="companyTreeRight">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		${treeScript }
</s:form>
</div>
</div>
</body>
</html>