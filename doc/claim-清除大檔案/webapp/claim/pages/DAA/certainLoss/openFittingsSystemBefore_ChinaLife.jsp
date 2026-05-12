<%--
*************************************************************
*定损时选择标准配件中转页面
*防止URL中参数过长出错
*此页面不显示
*************************************************************
--%>
<%@	page contentType="text/html; charset=GBK" language="java"%>
<%
	String queryType = request.getParameter("queryType");
	String registNo = request.getParameter("registNo");
	String policyNo = request.getParameter("policyNo");
	String insurant = request.getParameter("insurant");
	String lossItemCode = request.getParameter("lossItemCode");
	String licenseNo = request.getParameter("licenseNo");
	String repairfactorytype = request.getParameter("registNo");
	String showpriceflag = request.getParameter("showpriceflag");
	String systemAreaCode = request.getParameter("systemAreaCode");
	String localAreaCode = request.getParameter("localAreaCode");
	String vehCode = request.getParameter("vehCode");
	String vehName = request.getParameter("vehName");
	String requestType = request.getParameter("requestType");
	String callType = request.getParameter("callType");
%>
<html>
<body>
	<form name="fm" action="/claim/openFittingsSystem.do" method="post">
		<input type="hidden" class="common" name="queryType" value="<%=queryType%>">
		<input type="hidden" class="common" name="registNo" value="<%=registNo%>">
		<input type="hidden" class="common" name="policyNo" value="<%=policyNo%>">
		<input type="hidden" class="common" name="insurant" value="<%=insurant%>">
		<input type="hidden" class="common" name="lossItemCode" value="<%=lossItemCode%>">
		<input type="hidden" class="common" name="licenseNo" value="<%=licenseNo%>">
		<input type="hidden" class="common" name="repairfactorytype" value="<%=repairfactorytype%>">
		<input type="hidden" class="common" name="showpriceflag" value="<%=showpriceflag%>">
		<input type="hidden" class="common" name="systemAreaCode" value="<%=systemAreaCode%>">
		<input type="hidden" class="common" name="localAreaCode" value="<%=localAreaCode%>">
		<input type="hidden" class="common" name="vehCode" value="<%=vehCode%>">
		<input type="hidden" class="common" name="vehName" value="<%=vehName%>">
		<input type="hidden" class="common" name="requestType" value="<%=requestType%>">
		<input type="hidden" class="common" name="callType" value="<%=callType%>">
		<input type="hidden" class="common" name="selectCarFittings">
	</form>
</body>
</html>
<script>
	fm.selectCarFittings.value = window.opener.fm.selectCarFittings.value;
	fm.submit();
</script>
