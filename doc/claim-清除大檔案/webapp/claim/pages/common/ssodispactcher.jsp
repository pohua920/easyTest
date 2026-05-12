
<%
	String ssoUrl = java.net.URLEncoder.encode(
			request.getHeader("ssoUrl"), "GBK");
%>
<script language='javascript'>	
  window.location.href='<%=request.getContextPath()%>/SsoLoginDispatcherServlet?random=<%=request.getSession().getId()%>'
  	+'&ssoUrl=<%=ssoUrl%>
	&redirect=true';
</script>
