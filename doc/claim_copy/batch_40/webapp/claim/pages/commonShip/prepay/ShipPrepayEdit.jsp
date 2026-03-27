<!--
****************************************************************************
* DESC       ：预赔登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
  <!--对title处理-->
  <title><s:text name="title.prepayBeforeEdit.editPrepay" /></title><%--预赔登记--%>
  <%@ include file="/common/meta_js.jsp"%>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
  <script src="${ctx}/pages/commonShip/prepay/js/ShipPrepayEdit.js"></script>
</head>
<s:if test="#parameters.editTypeOther[0]=='SHOWTASK'">
<body  onload="initPage();InitDocument();initSet();readonlyAllInput();">
</s:if>
<s:elseif test="#attr.editType=='SHOW'">
<body  onload="initPage();InitDocument();initSet();readonlyAllInput();disabledAllButton('buttonArea');">
</s:elseif>
<s:else>
<body class=interface onload="initPage();InitDocument();initSet();" >
</s:else>
  <form name=fm action="${ctx}/prepaySave.do" method="post" onsubmit="return validateForm(this);">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<input type="hidden" name="registno" value="${prpLprepay.registNo}">
    <!-- 1.预赔主信息 -->
    <%@include file="/pages/commonShip/prepay/ShipPrepayMainEdit.jsp" %>
    <!-- 赔付支付信息   -->
    <%@include file="/pages/common/prepay/EditPrpdpaymentaccountPrePage.jsp"%>
    <!-- 4.预赔备注信息 -->
    <%@include file="/pages/commonShip/prepay/ShipPrepayTextEdit.jsp"%>
    <!-- 5.核赔意见 -->
    <%@include file="/pages/common/pub/UndwrtTextEdit.jsp" %>
  	<c:if test="${parame.editTypeOther!='SHOWTASK'}">
    <!-- 保存通用按钮 -->
    <%@include file="/pages/commonShip/prepay/ShipPrepaySave2.jsp"%>
    </c:if>
  </form>
</body>
<script src="${ctx}/pages/common/js/Edit.js"> </script>
</html>
