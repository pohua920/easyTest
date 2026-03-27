<%@page import="java.util.Date"%>
<%
	out.print(net.sf.json.JSONObject.fromObject(new Date()));
%>