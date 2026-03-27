<%--
****************************************************************************
* DESC       ：关联页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-09-02
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
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
<title><s:text name="title.endcaseBeforeEdit.associatedInformation" /></title>
<!--计算书关联信息-->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
	
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
							<td width="161" class="formtitle">
								<s:text name="endcase.associatedInformation" />
							</td>
							<!--计算书关联信息-->
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
				<td class="title" width="30%">
					<s:text name="endcase.calculationNumber" />:
				</td>
				<!--计算书号-->
				<td class="input">
					<c:forEach var="prpLcompensate" items="${prpLcompensateList}">
						<a href="/claim/print/claimPrint.do?printType=${printType}&CompensateNo=${prpLcompensate.compensateNo}">${prpLcompensate.compensateNo}<br></a>
					</c:forEach>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>