<!--
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
<%-- 预赔登记 --%>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/commonProp/prepay/js/PropPrepayEdit.js"></script>
</head>
<html locale="true">
<s:if test="#parameters.editTypeOther[0]=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();">
</s:if>
<s:elseif test="#attr.editType=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();">
</s:elseif>
<s:else>
	<body class=interface onload="initPage();initSet();">
</s:else>
<form name=fm action="${ctx}/prepaySave.do" method="post" onsubmit="return validateForm(this);">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<input type="hidden" name="registno" value="${prpLprepay.registNo}">
	<!-- 1.预赔主信息 -->
	<%@include file="/pages/commonProp/prepay/PropPrepayMainEdit.jsp"%>
	<!-- 赔付支付信息   -->
	<%@include file="/pages/common/prepay/EditPrpdpaymentaccountPrePage.jsp"%>
	<!-- 4.预赔备注信息 -->
	<%@include file="/pages/commonProp/prepay/PropPrepayTextEdit.jsp"%>
	<!-- 5.核赔意见 -->
	<%@include file="/pages/common/pub/UndwrtTextEdit.jsp"%>
	<c:if test="${param.editTypeOther!='SHOWTASK'}">
		<%@include file="/pages/commonProp/prepay/PropPrepaySave.jsp"%>
	</c:if>
</form>
<%@ include file="/common/meta_js.jsp"%>
</body>
</html>
