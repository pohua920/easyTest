<%--
****************************************************************************
* DESC       ：回访信息录入/修改页面
* AUTHOR     ：中科软
* CREATEDATE ：2005-03-24
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

<html:html locale="true">
<head>
    <!--对title处理-->
    <title>
      <s:text name="title.backVisitBeforeEdit.returnInformationInput"/><%-- 回访信息录入 --%>
    </title>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/DAA/backVisit/js/DAABackVisitEdit.js"></script>

  <!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->


  <SCRIPT LANGUAGE="JavaScript">

  /**
   *@description 初始化回访问询信息
   *@param       无
   *@return      通过返回true,否则返回false
   */

  function initSet() {

  	<%
  		BackVisitDto backVisitDto = (BackVisitDto) request.getAttribute("backVisitDto");


  	List prpLbackVisitQueDtoList = (ArrayList) backVisitDto.getPrpLbackVisitQueDtoList();
  	if (prpLbackVisitQueDtoList != null && prpLbackVisitQueDtoList.size() > 0) {
  		for (int i = 0; i < prpLbackVisitQueDtoList.size(); i++) {
  			PrpLbackVisitQueDto prpLbackVisitQueDto = (PrpLbackVisitQueDto) prpLbackVisitQueDtoList.get(i);
  			if (prpLbackVisitQueDto.getQuestionResult().equals("0")) { %>

  				fm.VisitBackQue <%= i + 1 %> [1].checked = true; <%
  			} else if (prpLbackVisitQueDto.getQuestionResult().equals("1")) { %>
  					fm.VisitBackQue <%= i + 1 %> [0].checked = true; <%
  			} else if (prpLbackVisitQueDto.getQuestionResult().equals("2")) { %>
  					fm.VisitBackQue <%= i + 1 %> [2].checked = true; <%
  			} %>
  				fm.txtQuestionRemark <%= i + 1 %> .value = '<%= prpLbackVisitQueDto.getQuestionRemark() %>'; <%
  		}
  	} %>
  	return true;
  }
  </SCRIPT>

</head>

<%
  String editType=request.getParameter("editType");
  if(editType.equals("SHOW")||editType.equals("DELETE"))
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
	<form name=fm action="/claim/backVisitSave.do" method="post">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%= session.getAttribute("org.apache.struts.action.TOKEN") %>">
		<%-- 1.回访主信息 --%>
		<%@include file="/DAA/backVisit/DAABackVisitMainEdit.jsp"%>
		<%-- 1.回访问询信息 --%>
		<%@include file="/DAA/backVisit/DAABackVisitQueEdit.jsp"%>
		<%-- 2.备注信息 --%>
		<%@include file="/DAA/backVisit/DAABackVisitTextEdit.jsp"%>
		<%-- 3.保存通用按钮 --%>
		<%@include file="/DAA/backVisit/DAABackVisitSave.jsp"%>
	</form>
</body>

</html:html>