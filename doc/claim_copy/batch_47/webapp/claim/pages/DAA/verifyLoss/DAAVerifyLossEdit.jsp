<%--
****************************************************************************
* DESC       ：核损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.verifyLossBeforeEdit.editVerifyLoss" /></title>
<%--核损登记 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="/claim/DAA/verifyLoss/js/DAAVerifyLossEdit.js"></script>
<script src="/claim/DAA/certainLoss/js/DAAVerifyLossPersonEdit.js"></script>
<script src="/claim/DAA/certainLoss/js/DAAVerifyLossRepairComponentEdit.js"></script>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<META http-equiv="Content-Type" content="text/html;	charset=GB2312">
</head>
<%
	String editType = request.getParameter("editType");
	//System.out.println(editType);
	if (editType.equals("SHOW")) {
%>
<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	<%
		} else {
	%>

<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
	<%
		}
	%>
	<form name="fm" action="/claim/verifyLossSave.do" method="post" onsubmit="return validateForm(this);">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<DIV id="mainLayer" style="position: absolute; top: 30px; left: 5px; height: 480px; z-index: 1;">
			<mpc:container ID="oMPC" style="width:830px;height:480px;">
				<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.PreCompensationRegistMain" />" TABTEXT="<s:text name="regist.prpLregist.PreCompensationRegistMain" />">
					<%--预赔基本信息 --%>
					<CENTER>
						<DIV style="width: 830px; height: 480px; background-color: #F7F7F7; overflow: scroll;"></DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
		</DIV>
		<%-- 1.核损/代核损主信息 --%>
		<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossMainEdit.jsp"%>
		<%-- 保存通用按钮 --%>
		<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossSave.jsp"%>
		<%-- 打印定损清单、损失确认书 及检验定损报告 --%>
		<%
			//@include file="/DAA/certainLoss/DAACertainLossPrint.jsp"
		%>
	</form>
</body>
</html>
