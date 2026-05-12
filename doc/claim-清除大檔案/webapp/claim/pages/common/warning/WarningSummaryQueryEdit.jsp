<%--
****************************************************************************
* DESC       ：录入报案前查询保单号条件果面
* AUTHOR     ： Archer
* CREATEDATE ： 2007-01-31
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@page import="com.sinosoft.claim.dto.domain.SwfLogDto"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<html:html locale="true">
<head>
<title>查询保单信息</title>
<%-- 公用函数 --%>
<script src="/claim/common/js/Common.js"></script>
<script src="/claim/common/warning/js/WarningQueryEdit.js"></script>
<script language="javascript">
	
<%--案件状态标志处理--%>
	
</script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%
	UserDto user = (UserDto) session.getAttribute("user");
		String comName = user.getComName();
		String comCode = user.getComCode();
%>
</head>
<body onload="document.onkeydown();">
	<form name="fm" action="/claim/WarningBeforeQuery.do" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">查询预警信息</td>
			</tr>
			<tr>
				<td class='title'>险种:</td>
				<td class='input'>
					<select class=tag name="swflogRiskCodeSign">
						<option value="=">=&nbsp;</option>
						<!--option value="*">*</option-->
					</select>
					<input type=text name="swflogRiskCode" class="query">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class="title4">机构代码：</td>
				<td class="input4">
					<select class="tag" name="swflogComCodeSign">
						<option value="=">=</option>
						<!--option value="*">*</option-->
					</select>
					<input type="text" name="swflogComCode" MaxLength="8" value="<%=comCode%>">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prompt.queRegist.RegistNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="swflogRegistNoSign">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="swflogRegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="swflogPolicyNoSign">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="swflogPolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>预警开始时间：</td>
				<td class='input'>
					<select class=tag name="swflogFlowInTimeSign">
						<option value=">=">&gt;=</option>
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="swflogFlowInTime" class="query" value="<%=new DateTime(DateTime.current().addDay(-7).toString(), DateTime.YEAR_TO_DAY)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('fm.swflogFlowInTime', 
                  '<%=(new DateTime(DateTime.current().addDay(-7).toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>',
                  '<%=(new DateTime(DateTime.current().addDay(-7).toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class='title'>预警结束时间：</td>
				<td class='input'>
					<select class=tag name="swflogSubmitTimeSign">
						<option value="<=">&lt;=</option>
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
					</select>
					<input type="text" name="swflogSubmitTime" class="query" value="<%=new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('fm.swflogSubmitTime',
               '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>',
               '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>环节：</td>
				<td class='input'>
					<select name="swflogNodeType">
						<option value="all">所有</option>
						<option value="regis">报案</option>
						<option value="sched">调度</option>
						<option value="schel">定损调度</option>
						<option value="verif">核损</option>
						<option value="wound">人伤定损</option>
						<option value="certa">定损</option>
						<option value="certi">单证</option>
						<option value="check">查勘</option>
						<option value="claim">立案</option>
						<option value="compe">实赔</option>
						<option value="compp">赔款计算书</option>
						<option value="backc">复勘</option>
						<option value="cance">註銷/拒赔</option>
						<option value="endca">结案</option>
						<option value="propc">财产定损</option>
						<option value="speci">特殊赔案</option>
						<option value="veric">核赔</option>
					</select>
				</td>
				<td class='title'>操作人员：</td>
				<td class='input'>
					<select class=tag name="swflogHandlerCodeSign">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="swflogHandlerCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>操作状态：</td>
				<td class='input'>
					<!--select class=tag name="swflogNodeStatusSign" >
               <option value="=">=</option>
               <option value="*">*</option-->
					</select> <select name="swflogNodeStatus">
						<option value="9">所有</option>
						<option value="0">待处理</option>
						<option value="2">正在处理</option>
						<option value="3">回退处理</option>
					</select>
					<!--input type=text name="swflogNodeStatus" class="query" -->
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' colspan="4">
					<input id="button" type=button class='button' value="查看" onClick="submitForm();">
				</td>
				<td class='button' colspan="4">
					<input id="button" type=button class='button' value="导出Excel" onClick="toExcel(t1);">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="WarningBeforeQuery">
		<input type="hidden" name="nodeType" value="Warning">
		<input type="hidden" name="comcodeName">
		<input type="hidden" name="comcodeName">
		<input type="hidden" name="startTime">
		<input type="hidden" name="endTime">
		<input type="hidden" name="pageFlag">
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<%
				SwfLogDto swfLog = (SwfLogDto) request.getAttribute("swfLog");
					String riskCode = swfLog.getRiskCode();
					String startTime = swfLog.getFlowInTime();
					String endTime = swfLog.getSubmitTime();
			%>
			<tr>
				<td class="centertitle">
					机构：<%=comName%></td>
				<td class="centertitle">
					险种：<%=riskCode%>
				</td>
				<td class="centertitle">
					时间段：<%=startTime%>&nbsp到&nbsp<%=endTime%></td>
		</table>
		<table id="t1" bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="centertitle">环节</td>
				<td class="centertitle">状态</td>
				<td class="centertitle">30分钟内</td>
				<td class="centertitle">30分-2小时内</td>
				<td class="centertitle">2-12小时内</td>
				<td class="centertitle">12-48小时内</td>
				<td class="centertitle">48小时以上</td>
				<td class="centertitle">总计</td>
			</tr>
			<%
				SwfLogDto swfLogDto = (SwfLogDto) request.getAttribute("swfLogDto");
					ArrayList arrayList = (ArrayList) swfLogDto.getSwfLogList();
					Iterator iterator = arrayList.iterator();
					while (iterator.hasNext()) {
						int i = 0;
						Object[] obj = (Object[]) iterator.next();
						String obj1 = (String) obj[1];
						if (obj1 == "0")
							obj1 = "待处理";
						if (obj1 == "2")
							obj1 = "正在处理";
						if (obj1 == "3")
							obj1 = "回退处理";
						if ((i % 2) == 0)
							out.print("<tr class=listodd>");
						else
							out.print("<tr class=listeven>");
			%>
			<tr>
				<td class="centertitle"><%=obj[0]%></td>
				<td class="centertitle"><%=obj1%></td>
				<td class="centertitle"><%=obj[2]%></td>
				<td class="centertitle"><%=obj[3]%></td>
				<td class="centertitle"><%=obj[4]%></td>
				<td class="centertitle"><%=obj[5]%></td>
				<td class="centertitle"><%=obj[6]%></td>
				<td class="centertitle"><%=obj[7]%></td>
			</tr>
			<%
				i++;
					}
			%>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr class="listtail">
				<td colspan="7">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td class='title'>
								<bean:define id="pageview" name="swfLogDto" property="turnPageDto" />
								<%
									int curPage = swfLogDto.getTurnPageDto().getPageNo();
										String taskcode = (String) request.getParameter("taskCodeC");
								%>
								<%@include file="/common/pub/TurnOverPage.jsp"%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%--    <input type="hidden" name="taskCodeC" value="<%=taskcode%>">--%>
		<input type="hidden" name="searchFlag" value="">
	</form>
</body>
</html:html>
