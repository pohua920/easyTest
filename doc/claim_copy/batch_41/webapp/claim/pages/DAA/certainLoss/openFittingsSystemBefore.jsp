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
	//String ip					=request.getParameter("ip");
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
		<input type="hidden" class="common" name="selectCarFittings">
		<!-- <input type="hidden" class="common" name="ip" value=""> -->
		<input type="hidden" class="common" name="markColor" />
		<input type="hidden" class="common" name="engineNo" />
		<input type="hidden" class="common" name="FrameNo" />
		<input type="hidden" class="common" name="InsureVehicleName" />
		<input type="hidden" class="common" name="InsureVehicleCode" />
		<input type="hidden" class="common" name="BranchComCode" />
		<input type="hidden" class="common" name="items" />
	</form>
</body>
</html>
<script>
	fm.selectCarFittings.value = window.opener.fm.selectCarFittings.value;
	fm.markColor.value = window.opener.fm.prpLverifyLossLicenseColor.value;//车牌颜色
	fm.engineNo.value = window.opener.fm.prpLcarLossEngineNo.value;//发动机号
	fm.FrameNo.value = window.opener.fm.prpLcarLossFrameNo.value;//车架号
	fm.InsureVehicleName.value = window.opener.fm.prpLcarLossBrandName.value;//承保车型名称
	fm.InsureVehicleCode.value = window.opener.fm.prpLcarLossModelCode.value;//承保车型编码
	//fm.BranchComCode.value = window.opener.fm.prpLcarLossModelCode.value;
	fm.submit();
</script>
