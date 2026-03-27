
<%
	String path = request.getContextPath();
%>
<script language='javascript'>
  //alert("系统已超时,请重新登录");
  window.top.location.href='<%=path%>/index.jsp';
</script>
