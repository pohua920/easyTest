<%--
****************************************************************************
* DESC       ：生成理算报告页面
* AUTHOR     ：理赔组 chenjie
* CREATEDATE ：2013-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body class="interface">
	<%
		Object message = request.getAttribute("compensateMessage");//理算计算书生成时校验信息
		if (message != null) {
	%>
	<textarea id="message" style="display: none"><%=String.valueOf(message.toString().replaceAll("<br>","\r\n"))%></textarea>
		<script language="javascript">
		    alert(document.getElementById("message").innerHTML);
		</script>
	<%
		}
	%>
	<%
		StringBuilder text = new StringBuilder("");
		PrpLctext prpLctext = (PrpLctext) request.getAttribute("prpLctext");
		if (prpLctext != null) {
			for (PrpLctext temp : prpLctext.getPrpLctextList()) {
				text.append(temp.getContext()).append("\\n");
			}
	%>
		<script language="javascript">
		  parent.fraInterface.fm.prpLctextContextInnerHTML.value = "<%= text.toString() %>";
		</script>
	<%
		}
	%>
	<%
		PrpLctext compelPrpLctext = (PrpLctext) request
				.getAttribute("compelPrpLctext");
		if (compelPrpLctext != null) {
			for (PrpLctext temp : compelPrpLctext.getPrpLctextList()) {
				text.append(temp.getContext()).append("\\n");
			}
	%>
		<script language="javascript">
		   parent.fraInterface.fm.compelPrpLctextContextInnerHTML.value = "<%=text.toString()%>";
		</script>
	<%
		}
	%>
</body>
</html>
