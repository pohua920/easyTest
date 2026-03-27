<%@ page language="java" contentType="text/html;charset=GBK"%>
<%
	session.invalidate();
	String nextlink = "../index.jsp";
	response.sendRedirect(nextlink);
%>
