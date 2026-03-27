<%--
****************************************************************************************
* DESC       ：单证系统首页
* AUTHOR     ：YANGXIAOGANG
* CREATEDATE ：2004-07-23
* MODIFYLIST ：Name          Date            Reason/Contents
*              ------------------------------------------------------
*              ZHANGYING     2004-07-23      按规范整理
*****************************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp" %>

<%@ page import="com.sinosoft.platform.dto.domain.*" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ page import="com.sinosoft.platform.bl.facade.BLPrpDuserFacade"%>
<%@ page import="com.sinosoft.platform.bl.facade.BLPrpDuserFacade" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<%@ page import="com.sinosoft.platform.dto.domain.PrpDuserDto" %>

<%
    if(session.getAttribute("user")==null){
%>
        <script language="javascript">
        	var strMsg = "<s:text name='prompt.systemInfo'/>:\n\n" + "<s:text name='prompt.userNameOrPasswordFail'/>";
  				alert(strMsg);
        	window.history.back(-1);
        </script>
<%
        //request.getRequestDispatcher("/").forward(request, response);
        //return;
    }
    PrpDuserDto user = (PrpDuserDto)session.getAttribute("user");
%>

<%
    /* DHCH 注释 
    if(session.getAttribute("user")==null){
        request.getRequestDispatcher("/").forward(request, response);
        return;
    }
    PrpDuserDto user1 = (PrpDuserDto)session.getAttribute("user");
    */
    BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
    PrpDuserDto prpDuserDto = null;
    prpDuserDto = blPrpDuserFacade.findByPrimaryKey(user.getUserCode());
    DateTime pwdExpireDate = new DateTime(prpDuserDto.getPasswordExpireDate());
      System.out.println("pwdExpireDate=======" + pwdExpireDate);
    int modifyPassword = 0;
    if(pwdExpireDate.before(DateTime.current().addDay(14)))
    {
        modifyPassword = 1;

%>
      <script language="javascript">
      	//alert("抱歉您的密码将在两周内失效，为保证您的正常使用请及时更新密码！");
      	alert("<s:text name='prompt.youPasswordGoodDate'/><%=pwdExpireDate%><s:text name='prompt.protectYouUsePleaseOntimeUpdatePassword'/>！");
      </script>
<%
    }
%>

<html:html xhtml="true" locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title><s:text name='prompt.oneBillManagerSystem'/></title>
    <script language="javascript">
        var intPageWidth=screen.availWidth;
        var intPageHeight=screen.availHeight;
        window.resizeTo(intPageWidth,intPageHeight);
        window.focus();
    </script>
  </head>
  <frameset rows="0,89,*" frameborder="no" border="1" framespacing="0" cols="*">
      <!--标题与状态区域-->
      <frame name="fraSubmit" scrolling="yes" noresize src="about:blank">
      <frame name="fraTitle"  scrolling="no"   noresize src="${ctx}/common/Title.jsp" >
      <frameset name="fraSet" cols="180,*" frameborder="no" border="1" framespacing="0" rows="*">
		  <!-- 菜单区域 -->
          <!--<frame src="common/Tree.jsp?menuStyle=simple" name="fraMenu" scrolling="no">-->
          <frame src="${ctx}/common/Tree.jsp?menuStyle=simple" name="fraMenu" scrolling="auto" noresize>
           
          	 <frameset name="fraRight" rows="100%,0%,*" frameborder="no" border="1" framespacing="0" cols="*">
              <!--交互区域-->
		  <%
              if(modifyPassword==1){
          %>
            <frame id="fraInterface" name="fraInterface" src="${ctx}/common/UpdatePwd.jsp" frameborder=0 scrolling="auto">
          <%}else{%>
            <frame id="fraInterface" name="fraInterface" src="${ctx}/common/Welcome.html" frameborder=0 scrolling="auto">
          <%}%>
              <!--下一步页面区域-->
              <frame name="fraNext" name="fraNext" scrolling="auto" noresize src="about:blank">
              <!--代码选择区域-->
              <frame name="fraCode" id="fraCode" scrolling="auto" frameborder=0 noresize src="${ctx}/common/pub/InputCode.jsp">
          </frameset>
      </frameset>
  </frameset>
  <noframes></noframes>
</html:html>
