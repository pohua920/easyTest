<%--
****************************************************************************
* DESC       ：车险理赔框架页面
* AUTHOR     ：weishixin
* CREATEDATE ：2004-03-02 
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.sinosoft.platform.bl.facade.BLPrpDuserFacade" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<%@ page import="com.sinosoft.platform.dto.domain.PrpDuserDto" %>
<%
	if (session.getAttribute("prpDuserDto") == null) {
		request.getRequestDispatcher("/").forward(request, response);
		return;
	}
	PrpDuserDto user = (PrpDuserDto) session.getAttribute("prpDuserDto");
	BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
	PrpDuserDto prpDuserDto = null;
	prpDuserDto = blPrpDuserFacade.findByPrimaryKey(user.getUserCode());
	DateTime pwdExpireDate = new DateTime(prpDuserDto.getPasswordExpireDate());
	int modifyPassword = 0;
	if (pwdExpireDate.before(DateTime.current().addDay(14))) {
		modifyPassword = 1;
%>
      <script language="javascript">
//      	alert("抱歉您的密码将在两周内失效，为保证您的正常使用请及时更新密码！");
      	alert("您的密码有效期到<%=pwdExpireDate%>，为保证您的正常使用请及时更新密码！");
      </script>
<%
	}
%>
<html:html xhtml="true" locale="true">
<head>
<title><s:text name="title.pubBeforeEdit.claimsWorkflowSystem" /></title>
<%--理赔工作流系统--%>
<script language="javascript">
        <!--
            if (self!=top){top.location=self.location;}
            var intPageWidth=screen.availWidth;
            var intPageHeight=screen.availHeight;
            window.resizeTo(intPageWidth,intPageHeight);
            window.focus();
        // -->
      </script>
<html:base />
</head>
<frameset rows="87,0,0,*" frameborder="no" border="1" framespacing="0">
	<frame name="fraTitle" scrolling="no" noresize src="/claim/common/pub/Title.jsp">
	<frame name="fraCalculate" scrolling="yes" noresize src="about:blank">
	<frame id="fraSubmit" name="fraSubmit" scrolling="yes" noresize src="about:blank">
	<frameset name="fraSet" cols="180,*,0" frameborder="no" border="0" framespacing="0" rows="*">
		<!---<frame id="fraMenu" name="fraMenu" scrolling="auto" noresize marginwidth=0 marginheight=0 src="/claim/common/pub/LeftMenu.jsp">--->
		<frame id="fraMenu" name="fraMenu" scrolling="auto" noresize marginwidth=0 marginheight=0 src="/claim/common/pub/tree.jsp">
		<frameset name="fraRight" rows="100%,0%,0%" scrolling="yes" frameborder="auto" border="1" framespacing="0" rows="*">
			<!--reason:Unix要求大小写一致-->
			<%
          	if (modifyPassword == 1) {
          %>
			<frame id="fraInterface" name="fraInterface" scrolling="auto" src="/claim/common/pub/UIUpdatePwd.jsp">
			<%
          	} else {
          %>
			<frame id="fraInterface" name="fraInterface" scrolling="auto" src="/claim/common/pub/Welcome.jsp">
			<%
          	}
          %>
			<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
			<frame id="fraCode" name="fraCode" scrolling="auto" noresize src="/claim/common/pub/InputCode.jsp">
		</frameset>
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#c1c1c1" text="#000000">
	</body>
</noframes>
</html:html>