<%@ page contentType="text/html;charset=GBK" isErrorPage="true" %>
<%@page import="java.util.Enumeration"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="ins.framework.exception.*"%>
<%@page import="ins.framework.cache.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>清除緩存界面</title>
</head>
<!--mantis：CLM0276，處理人員：DP0713，需求單編號：新核心-修正正在處理立案任務的[提交]按鈕問題 -->
<body>
	<form name="fm" method="post">
		<table width="1004" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td align="center">清除缓存界面...<!-- 清除缓存界面 --></td></tr>
		</table>
	</form>
</body>
</html>


<script type="text/javascript">
  	window.onload=function setfocus(){
     	<%
		CacheManager.clearAllCacheManager();
		%>
		//alert(i18n.wx.clear.clearCacheFinished+"....");//缓存清理完毕，请继续操作
	}
</script>
