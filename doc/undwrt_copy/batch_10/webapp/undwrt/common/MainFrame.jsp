<%--***************************************************************************
* Description: 显示核保核赔处理系统画面
* Author     : luyang
* CreateDate:  2004-12-22 14:49	
* UpdateLog拢潞  Name       Date            Reason/Contents
****************************************************************************--%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ page import="com.sinosoft.platform.bl.facade.BLPrpDuserFacade"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%
	PrpDuserDto prpDuserDto = (PrpDuserDto)session.getAttribute("user");
	String msgServerDomain = AppConfig.get("sysconst.MSG_SERVER_DOMAIN");
	
	BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
	PrpDuserDto user = null;
	user = blPrpDuserFacade.findByPrimaryKey(prpDuserDto.getUserCode());
	DateTime pwdExpireDate = new DateTime(user.getPasswordExpireDate());
	if(pwdExpireDate.before(DateTime.current().addDay(7)))
	{
%>
    <script language="javascript">
    alert("<s:text name='prompt.sorryYouPasswordOneWeekDeinePleaseOntimeUpdatePassword'/>");
    </script>
<%
  }
%>

<html>
	<head>
    	<title>核保系統</title>
  	</head>
  	
	<frameset rows="0,0,0,0,87,*" frameborder="no" border="1" framespacing="0" cols="*">
		<%--标题与状态区域--%>
   		<%-- <%=AppConfig.get("sysconst.MSG_JWCHAT_SERVER_ADDRESS")%>?server=<%=msgServerDomain%>&jid=<%=prpDuserDto.getUserCode() %>@<%=msgServerDomain%>/jwchat&pass=1111&btype=binding --%>
   		<frame src="#" name="jwc1" marginwidth="0" marginheight="0" >
    	<frame name="Code" src="about:blank">
    	<frame name="fraMisc"  scrolling="no" noresize src="about:blank" >
    	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
    	<frame name="fraTitle"  scrolling="no" noresize src="/undwrt/common/CommonTitle.jsp" >
    	
    	<frameset name="fraSet" cols="180,*,0" frameborder="no" border="1" framespacing="0" rows="*">
      		<%--菜单区域--%>
      		<frameset name="fraSubSet" rows="*,0" frameborder="no" border="1" framespacing="0" rows="*">
	        	<%--菜单区域--%>
	        	<frame name="fraMenu" scrolling="auto" noresize src="/undwrt/common/tre2.jsp">
	        	<%--<frame src="/undwrt/common/Tree.jsp?menuStyle=simple" name="fraMenu" scrolling="auto" noresize>--%>
	        	<%--刷新提示区域--%>
	        	<%--<frame name="fraRefresh" scrolling="no" noresize src="/undwrt/common/CommonRefresh.jsp">--%>
	      		<frame src="UntitledFrame-7">
      		</frameset>
      		<%--交互区域--%>
      		<frameset name="fraRight" rows="100%,*" frameborder="yes" border="1" framespacing="0">
      			<%--<frame id="fraInterface" name="fraInterface" scrolling="auto" src="/undwrt/common/TaskMessage.jsp" >--%>
	      		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="/undwrt/common/Welcome.html" >
		      	<%-- frame id="fraInterface" name="fraInterface" scrolling="auto" src="about:blank" --%>
		      	<%--下一步页面区域--%>
		      	<%--<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">--%>
		      	<%--代码选择区域--%>
		      	<frame name="fraCode" scrolling="yes" noresize src="/undwrt/common/UICodeGet.jsp">
      		</frameset>
      		<frame name="fraMsg" src="<%=AppConfig.get("sysconst.MSG_JWCHAT_SERVER_ADDRESS")%>?server=<%=msgServerDomain%>&jid=<%=prpDuserDto.getUserCode() %>@<%=msgServerDomain%>/jwchat&pass=1111&btype=binding" name="jwc1" marginwidth="0" marginheight="0">
		</frameset>
	</frameset>
  	<noframes>
    <body bgcolor="#ffffff"></body>
  	</noframes>
</html>
