<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>print Info</title>
  </head>
  <%
  	String info = (String)request.getAttribute("info");
  %>
    <SCRIPT>
    	alert('<%=info%>');
	    window.close();
    </SCRIPT>
    
</html>
