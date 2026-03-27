<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%
  System.out.println("=====================================");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <jsp:include page="/common/meta_css.jsp" />
</head>
<body bgcolor="EFF1FE" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td > <table width="180" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="180" height="100%" class="menu">
          <IFRAME width=180 height=100% src="${ctx}/common/processUtiMenu.do?taskCode=showMenu&menuStyle=<%=request.getParameter("menuStyle")%>"></IFRAME></td>
          <td></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>