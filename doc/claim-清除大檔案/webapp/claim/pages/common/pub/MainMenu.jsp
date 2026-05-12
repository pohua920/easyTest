<%--
****************************************************************************
* DESC       ：车险理赔菜单页面、该页面包含iframe活动页面
* AUTHOR     ：????
* CREATEDATE ：2004-03-23
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%
	String userNameTemp = request.getParameter("userName");
	String visitDate = request.getParameter("visitDate");
	String subSystemTemp = request.getParameter("subSystem");
	String subSystem = new String(subSystemTemp.getBytes("ISO-8859-1"),
			"GBK");
	String userName = new String(userNameTemp.getBytes("ISO-8859-1"),
			"GBK");
%>
<html:html>
<head>
<app:css />
<script type="text/javascript" language="javascript" src="/claim/common/js/menu/sniffer.js"></script>
<script type="text/javascript" language="javascript1.2" src="/claim/common/js/menu/custom.js"></script>
<script type="text/javascript" language="javascript1.2" src="/claim/common/js/menu/style.js"></script>
<script type="text/javascript" language="javascript1.2" src="/claim/common/js/menu/menudefine.js"></script>
<script type="text/javascript" language="java
	function setTitle(title) {
		titleDiv.innerHTML = title;
     }
	  </script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@include file="/common/pub/LeftMenu.htm"%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="24" bgcolor="#ffffff"></td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="1" bgcolor="#26566D"></td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="11" bgcolor="#ffffff"></td>
		</tr>
	</table>
	<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="center" valign="bottom" bgcolor="#FFFFFF">
				<table width="96%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="172" height="33" align="center" background="/claim/images/2004-01-15_03.gif" class="button">
							<font color="#FFFFFF" style='font-size: 12px'><b><div id="titleDiv" name="titleDiv">
										<s:text name="pub.welcomeArrival" />
									</div></b></font>
						</td>
						<%--欢迎你的到来--%>
						<td background="/claim/images/2004-01-15_05.gif" valign="top" align="right">
							<img src="/claim/images/writer.gif" height="18" width="28" align="absmiddle"></img><font color="#898989" style='font-size: 11pt'>&nbsp;<%=userName%>&nbsp;&nbsp;<%=visitDate%>&nbsp;&nbsp;<%=subSystem%></font>
						</td>
						<td width="13" background="/claim/images/2004-01-15_05.gif">
							<img src="/claim/images/2004-01-15_07.gif" width="13" height="33">
						</td>
					</tr>
				</table>
				<table width="96%" height="86%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="6" background="/claim/images/images-d_24.gif"></td>
						<td height="95%" align="center">
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="1D4356">
								<tr>
									<td bgcolor="EFFAFF">
										<%@include file="/common/pub/welcome.jsp"%>
									</td>
								</tr>
							</table>
						</td>
						<td width="10" background="/claim/images/images-d_27.gif"></td>
					</tr>
				</table>
				<table width="96%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left" background="/claim/images/image_31.gif">
							<img src="/claim/images/image_29.gif" width="6" height="13">
						</td>
						<td width="766" align="center" valign="middle" background="/claim/images/image_31.gif"></td>
						<td align="right" background="/claim/images/image_31.gif">
							<img src="/claim/images/image_33.gif" width="10" height="13">
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="0D7EBB">
					<tr>
						<td align="left" height="3" bgcolor="ffffff" colspan="3"></td>
					</tr>
					<tr valign="middle">
						<td align="left" width="30%">
							<img src="/claim/images/index_15.gif" width="285" height="20">
						</td>
						<td width="60%" class="menu2" align="right">
							<s:text name="pub.reservedZhongkeSoft" />
						</td>
						<%--版权所有　中科软科技股份有限公司--%>
						<td align="right" width="10%" height="20">
							<input name="imageField" type="image" src="/claim/images/print.gif" width="14" height="14" align="middle" border="0">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html:html>
