<%--
****************************************************************************
* DESC       ：第三者车辆信息页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-04-01
* MODIFYLIST ：   Name       Date            Reason/Contents  
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<head>
<!--对title处理-->
<title><s:text name="certainLoss.thirdCarLoss.prpLcheckDamageCar" /></title>
<%--涉案车辆 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/pages/DAA/regist/js/DAAThirdCarLossEdit.js"></script>
<script src="/claim/pages/DAA/schedule/js/DAAScheduleThirdParty.js"></script>
</head>
<body class="interface"
	onload="initPage();readonlyAllTableInput('readonlyThirdPartyTable');readonlyAllTableInput('readonlyThirdPropTable');readonlyAllTableInput('readonlyPersonTraceTable');check_person();">
	<%-- //<body class="interface" > --%>
	<%-- 1.界面信息 --%>
	<%@include file="/pages/common/schedule/ScheduleAddCertainLossTaskMainEdit.jsp"%>
	<form name=fm action="${ctx}/schedule/scheduleAddCertainLossSave.do" method="post" onsubmit="return validateForm(this,'ThirdParty_Data');">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<input type="hidden" name="businessNo" value="<%=request.getAttribute("businessNo")%>">
		<input type="hidden" name="prpLcheckRiskCode" value="<%=request.getParameter("riskCode")%>">
		<!--隐藏险种代码-->
		<input type="hidden" name="swfLogFlowID" value="<%=request.getParameter("swfLogFlowID")%>">
		<input type="hidden" name="swfLogLogNo" value="<%=request.getParameter("swfLogLogNo")%>">
		<input type="hidden" name="policyNo" value="<%=request.getParameter("policyNo")%>">
		<input type="hidden" name="editType" value="EDIT">
		<!-- 此新增定损调度任务的双代标志:1:出险地方调度任务; 2:承保方调度任务; 其他:正常任务 -->
		<input type="hidden" name="commiFlag" value="<%=request.getAttribute("commiFlag")%>">
		<%-- 涉案车辆 --%>
		<%@include file="/pages/DAA/schedule/DAAScheduleThirdPartyEdit.jsp"%>
		<%@include file="/pages/DAA/schedule/DAAScheduleThirdPropEdit.jsp"%>
		<%@include file="/pages/DAA/schedule/DAASchedulePersonTraceEdit.jsp"%>
		<table id=save align="center">
			<tr>
				<td class=button style="width: 100%" align="center">
					<!--保存按钮-->
					<input type="submit" name=buttonSave class='button' value="儲存" onclick="return saveThirdParty(this);">
				</td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
