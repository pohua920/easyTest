<%@ page contentType="text/html; charset=GBK"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="com.sinosoft.claimciplatform.dto.custom.ReturnInfo"%>
<%@ page import="com.sinosoft.claimciplatform.dto.custom.ComparsionDetailDto"%>
<%@ page import="com.sinosoft.claimciplatform.dto.custom.Iconstants"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title><s:text name="title.comparisonBeforeEdit.platfDetailView" /></title>
<%-- 平台返回明细查看 --%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<%
		ReturnInfo returninfo = (ReturnInfo) request.getAttribute("returnInfo");
		if (returninfo != null && Iconstants.ReturnType.SUCCESS.equals(returninfo.getReturnType())) {
	%>
	<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
		<tr>
			<td colspan=14 class="formtitle">
				<s:text name="comparison.platfDetailView" />
			</td>
		</tr>
		<%-- 平台返回明细查看 --%>
		<tr>
			<td class='title'>
				<s:text name="comparison.sequeNum" />
			</td>
			<%-- 序列编号 --%>
			<td class='title'>
				<s:text name="db.prpDfieldExt.codeCode" />
			</td>
			<%-- 业务代码 --%>
			<td class='title'>
				<s:text name="db.prpCmain.policyNo" />
			</td>
			<%-- 保单号码 --%>
			<td class='title'>
				<s:text name="db.prpLregist.registNo" />
			</td>
			<%-- 报案号 --%>
			<td class='title'>
				<s:text name="db.prpLclaimStatus.operatedate" />
			</td>
			<%-- 操作时间 --%>
		</tr>
		<%
			List list = returninfo.getComparsionDetailList();
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						ComparsionDetailDto comparsionDetailDto = (ComparsionDetailDto) list.get(i);
		%>
		<tr>
			<td class='input'><%=comparsionDetailDto.getSerialNo()%></td>
			<td class='input'><%=comparsionDetailDto.getBusinessCode()%></td>
			<td class='input'><%=comparsionDetailDto.getPolicyNo()%></td>
			<td class='input'><%=comparsionDetailDto.getRegistNo()%></td>
			<td class='input'><%=comparsionDetailDto.getOperationTime()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
		} else {
			out.println(new String(returninfo.getErrorMessage().getBytes(), "GBK"));
		}
	%>
</body>
</html>
