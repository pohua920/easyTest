<%--
****************************************************************************
* DESC       ：关联页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-05-07
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.claim.util.*"%>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLregist"%>
<html>
<head>
<STYLE>
BODY {
	SCROLLBAR-FACE-COLOR: #EFFAFF;
	SCROLLBAR-HIGHLIGHT-COLOR: #4D9AC4;
	SCROLLBAR-SHADOW-COLOR: #4D9AC4;
	SCROLLBAR-3DLIGHT-COLOR: #EFFAFF;
	SCROLLBAR-ARROW-COLOR: #EFFAFF;
	SCROLLBAR-TRACK-COLOR: #EFFAFF;
	SCROLLBAR-DARKSHADOW-COLOR: #EFFAFF;
}
</STYLE>
<title>備案關聯信息</title>
<script src="${ctx}/claim/common/js/showpage.js">
	
</script>
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form name="fm">
		<input type="hidden" name="pageFlag">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">備案關聯信息</td>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="title" width="30%">備案號:</td>
				<td class="input">
					<%
						String registNo = "";
						PrpLregist prpLregist = (PrpLregist) request.getAttribute("prpLregist");
						List registList = prpLregist.getRegistList();
						Iterator registListTemp = registList.iterator();
						while (registListTemp.hasNext()) {
							PrpLregist prpLregistTemp = (PrpLregist) registListTemp.next();
							registNo = prpLregistTemp.getRegistNo();
					%>
					<a href="${ctx}/print/claimPrint.do?printType=Regist&RegistNo=<%=registNo%>"><%=registNo%><br></a>
					<%
						}
					%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>