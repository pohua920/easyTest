<%@	page contentType="text/html; charset=GB2312"	language="java"	%>
<%--
****************************************************************************
* DESC       ：定损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-10-13
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<html:html locale="true">
<head>
    <!--对title处理-->
    <title><s:text name="title.certainLossBeforeEdit.editCertainLoss" /></title><%--定损登记--%>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/commonShip/certainLoss/js/ShipCertainLossEdit.js"></script>
  <script src="/claim/commonShip/certainLoss/js/ShipCertainLossPersonEdit.js"></script>
	<META	http-equiv="Content-Type"	content="text/html;	charset=GB2312">
</head>
<%
  int indexCertainLoss = 0;
  String flag = null;
  flag = request.getParameter("flag");
%>

<%
  String editType=request.getParameter("editType");
  //System.out.println(editType);
  if (editType.equals("SHOW"))
  {
%>
<body  onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');">
<%
}else
{
%>
<body  onload="initPage();initSet();">
<%
}

%>
  <form name="fm" action="/claim/certainLossSave.do" method="post" onsubmit="return validateForm(this);">
    <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%= session.getAttribute("org.apache.struts.action.TOKEN") %>">
      <%-- 1.定损/代定损主信息 --%>
        <%@include file="/commonShip/certainLoss/ShipCertainLossMainEdit.jsp" %>
      <%-- 2.人员伤亡清单页面 --%>
        <%//@include file="/commonShip/certainLoss/ShipCertainLossPersonEdit.jsp"%>
      <%-- 3.财产损失清单页面 --%>
        <%@include file="/commonShip/certainLoss/ShipCertainLossPropEdit.jsp"%>
      <%-- 保存通用按钮 --%>
        <%@include file="/commonShip/certainLoss/ShipCertainLossSave.jsp"%>
      <!--modified by wangli add start 20050329-->
      <%--打印--%>
     <%@include file="/DAA/certainLoss/DAACertainLossPrint.jsp"%>
      <!--modified by wangli add end 20050329-->
  </form>
</body>

<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->


</html:html>
