<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>

<html:html locale="true">
<head>
    <!--对title处理-->
    <title>
    	<s:text name="title.registBeforeEdit.editRegist" />
    </title>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/commonShip/compensate/js/ShipCompensateEdit.js"></script>
</head>

<body class=interface onload="initPage();InitDocument();initSet();readonlyAllInput();disabledAllButton('tablebutton');">
    <form name=fm action="/claim/compensateSave.do" method="post" onsubmit="return validateForm(this);">
    <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%= session.getAttribute("org.apache.struts.action.TOKEN") %>">

    <%-- 1.报案主信息 --%>
        <%@include file="/commonShip/compensate/ShipCompensateMainHeadEdit.jsp" %>
    <%-- 2.特别约定 --%>
        <%@include file="/commonShip/compensate/ShipCompensateCengage.jsp"%>
    <%-- 2.赔偿限额/免赔额 --%>
        <%@include file="/commonShip/compensate/ShipCompensatePrpDlimit.jsp"%>
    <%-- 3.赔付标的信息 --%>
        <%@include file="/commonShip/compensate/ShipCompensateLlossEdit.jsp"%>
    <%-- 3.赔款费用 --%>
        <%@include file="/commonShip/compensate/ShipCompensateChargeEdit.jsp"%>
   <%-- 1.报案主信息 --%>
        <%@include file="/commonShip/compensate/ShipCompensateMainTailEdit.jsp" %>
    <%-- 4.理算报告 --%>
        <%@include file="/DAA/compensate/DAACompensateTextEdit.jsp"%>
  <%-- 保存通用按钮 --%>
        <%@include file="/DAA/compensate/DAACompensateSave.jsp"%>

     </form>
</body>

<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->


</html:html>
