	<%--
	****************************************************************************************
	* DESC       ：理赔工作流系统首页
	* AUTHOR     ：YANGXIAOGANG
	* CREATEDATE ：2004-07-23
	* MODIFYLIST ：Name          Date            Reason/Contents
	*              ------------------------------------------------------
	*              ZHANGYING     2004-07-23      按规范整理
	*****************************************************************************************
	--%>
	<%@ page contentType="text/html; charset=GBK"%>
	<%@ include file="/common/taglibs.jsp"%>
	<html>
	<head>
	<%@include file="/common/meta_css.jsp"%>
	<%@include file="/common/meta_js.jsp"%>
	<%@include file="/common/i18njs_base.jsp"%>
	<title><s:text name="title.pubBeforeEdit.claimsWorkflowSystem"/></title><%--理赔工作流系统 --%>
	<script type="text/javascript" src="${ctx}/common/js/ValidateData.js"></script>
	</head>
	<%@ page import="com.sinosoft.platform.dto.domain.*" %>
	<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
	<%@ page import="com.sinosoft.platform.bl.facade.BLPrpDuserFacade"%>
	<%@ page import="com.sinosoft.platform.bl.facade.BLPrpDuserFacade" %>
	<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
	<%@ page import="com.sinosoft.platform.dto.domain.PrpDuserDto" %>
	<%@ page import="com.sinosoft.claim.dto.custom.UserDto" %>
	<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>

	<%
		String msgServerDomain = AppConfig.get("sysconst.MSG_SERVER_DOMAIN");
		if(session.getAttribute("user")==null){
	%>
			<script language="javascript">
				var strMsg = "系統訊息:\n\n" + "用戶名或密碼錯誤";
					alert(strMsg);
				window.history.back(-1);
			</script>
	<%}
	   
		UserDto user = (UserDto)session.getAttribute("user");
		BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
		PrpDuserDto prpDuserDto = null;
		prpDuserDto = blPrpDuserFacade.findByPrimaryKey(user.getUserCode());
		DateTime pwdExpireDate = new DateTime(prpDuserDto.getPasswordExpireDate());
		int modifyPassword = 0;
		//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 start
		/*
		if(pwdExpireDate.before(DateTime.current().addDay(30)))
		{
			modifyPassword = 1;
		*/
	%>
		  <script language="javascript">
			//alert("抱歉您的密码将在两周内失效，为保证您的正常使用请及时更新密码！");
		  </script>
	<%
		/*
		}
		*/
	  //mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 end
	%>

	<html xhtml="true" locale="true">

	  <frameset rows="87,0,0,*" frameborder="no" border="1" framespacing="0">
		<frame name="fraTitle"  scrolling="no" noresize src="${ctx}/common/Title.jsp" >
		<frame name="fraCalculate"  scrolling="yes" noresize src="about:blank">
		<frame id="fraSubmit" name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
		<frameset name="fraSet" cols="180,*,0" frameborder="no" border="0" framespacing="0" rows="*">
		  <frame id="fraMenu" name="fraMenu" scrolling="auto" noresize marginwidth=0 marginheight=0 src="${ctx}/common/Tree.jsp?menuStyle=simple">
		  <frameset name="fraRight" rows="100%,0%,0%" scrolling="yes" frameborder="auto" border="1" framespacing="0" rows="*">
			  <%
				  if(modifyPassword==1){
			  %>
				<frame id="fraInterface" name="fraInterface" scrolling="auto" src="/claim/pages/common/pub/UIUpdatePwd.jsp">
			  <%}else{%>
				<frame id="fraInterface" name="fraInterface" scrolling="auto" src="${ctx}/common/Welcome.jsp">
			  <%}%>
			<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
			<frame id="fraCode" name="fraCode" scrolling="auto" noresize src="#">
		</frameset>
		<frame name="fraMsg" src="<%=AppConfig.get("sysconst.MSG_JWCHAT_SERVER_ADDRESS")%>?server=<%=msgServerDomain%>&jid=<%=prpDuserDto.getUserCode() %>@<%=msgServerDomain%>/jwchat&pass=1111&btype=binding" name="jwc1" marginwidth="0" marginheight="0">
		</frameset>
	  </frameset>

	  <noframes>
		<body bgcolor="#c1c1c1" text="#000000">
			
		</body>
	  </noframes>
	</html>