<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>账户信息修改</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="">
${requestScope.table}
</s:form>
</div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript">
	function updateAccInfo(){
		
		fm.action = "${ctx}/utiIAccount/updateAccInfo.do";
		fm.submit();
	}

	function onClose(){
		fm.action = "${ctx}/utiIAccount/prepareAccountInfoQuery.do";
		fm.submit();
	}
</script>