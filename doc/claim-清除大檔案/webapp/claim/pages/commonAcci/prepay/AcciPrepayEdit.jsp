<!--
****************************************************************************
* DESC       ：预赔登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.prepayBeforeEdit.editPrepay" /></title>
<%--预赔登记--%>
<!-- 页面样式  -->
<%@ include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/commonAcci/prepay/js/AcciPrepayEdit.js"></script>
</head>
<s:if test="#parameters.editTypeOther[0]=='SHOWTASK'">
	<body class="interface" onload="initPage();InitDocument();initSet();readonlyAllInput();">
</s:if>
<s:elseif test="#attr.editType=='SHOW'">
	<body class="interface" onload="initPage();InitDocument();initSet();readonlyAllInput();disabledAllButton('buttonArea');">
</s:elseif>
<s:else>
	<body class=interface onload="initPage();InitDocument();initSet();">
</s:else>
<form name=fm action="${ctx}/prepaySave.do" method="post" onsubmit="return validateForm(this);">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<!-- 1.预赔主信息 -->
	<%@include file="/pages/commonAcci/prepay/AcciPrepayMainEdit.jsp"%>
	<!-- 4.预赔备注信息 -->
	<%@include file="/pages/DAA/prepay/DAAPrepayTextEdit.jsp"%>
	<!-- 5.核赔意见 -->
	<%@include file="/pages/common/pub/UndwrtTextEdit.jsp"%>
	<c:if test="${param.editTypeOther!='SHOWTASK'}">
		<!-- 保存通用按钮 -->
		<%@include file="/pages/DAA/prepay/DAAPrepaySave.jsp"%>
	</c:if>
</form>
</body>
<script src="${ctx}/common/js/Edit.js">
	
</script>
</html>
