<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*"%>
<html:html>
<head>
<app:css />
</head>
<%
	String strLink = request.getParameter("station");
		String strTrans = "";
		//System.out.println(strLink);

		if (strLink == null || strLink.equals("")) {
			strTrans = "";
		} else {
			if (strLink.equals("1000"))
				strTrans = ">> 理赔管理 >> 报案处理 >> 报案登记";
			if (strLink.equals("1001"))
				strTrans = ">> 理赔管理 >> 报案处理 >> 报案修改";
			if (strLink.equals("1002"))
				strTrans = ">> 理赔管理 >> 报案处理 >> 报案删除";

		}

		strTrans = "当前位置：首页" + strTrans;
%>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<!--
    <td ID="openMenuTD" name="openMenuTD" style="display:none" width="1%" height="25" nowrap>
      &nbsp;<img src="images/menuShow.gif" onClick="top.fraMenu.menuTreeTop.control();" alt="显示功能選單" style="cursor:'hand'">
    </td>
    -->
			<td nowrap>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class=common>
					<tr>
						<!--td width="43" height="25" nowrap><img src="/claim/images/bgClaimGreen.gif" width="82" height="26"></td-->
						<td background="" class="menu" align=left nowrap>
							<%=strTrans%>
						</td>
						<!--td width="33" nowrap><img src="/claim/images/bgClaimGreen.gif" width="53" height="26"></td-->
					</tr>
					<tr>
						<td width="43" height="3" nowrap></td>
						<td class="" nowrap></td>
						<td width="33" nowrap></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html:html>
