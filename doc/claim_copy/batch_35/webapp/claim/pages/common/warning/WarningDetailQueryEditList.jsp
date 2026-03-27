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
<jsp:directive.page import="java.util.Iterator;" />
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="java.util.*"%>
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
</head>
<body onload="document.onkeydown();">
	<form name="fm" action="/claim/WarningBeforeQuery.do" method="post" onsubmit="return validateForm(this);">
		<%
  SwfLogDto swfLog = new SwfLogDto();
  swfLog = (SwfLogDto)request.getAttribute("swfLog");
%>
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">查询预警信息</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prompt.queRegist.RegistNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="swflogRegistNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="swflogRegistNo" class="query" value="<%=swfLog.getRegistNo()%>">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="swflogPolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="swflogPolicyNo" class="query" value="<%=swfLog.getPolicyNo()%>">
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
					<input type=text name="swflogFlowInTime" class="query" value="<%=swfLog.getFlowInTime()%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('fm.swflogFlowInTime', 
                  '<%=(new DateTime(DateTime.current().addDay(-7).toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>',
                  '<%=(new DateTime(DateTime.current().addDay(-7).toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">
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
					<input type="text" name="swflogSubmitTime" class="query" value="<%=swfLog.getSubmitTime()%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('fm.swflogSubmitTime',
               '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>',
               '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>环节：</td>
				<td class='input'>
					<select name="swflogNodeType" value="<%=swfLog.getNodeType()%>">
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
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="swflogHandlerCode" class="query" value="<%=swfLog.getHandlerCode()%>">
				</td>
			</tr>
			<tr>
				<td class='title'>操作状态：</td>
				<td class='input'>
					<select name="swflogNodeStatus" value="<%=swfLog.getNodeStatus()%>">
						<option value="9">所有</option>
						<option value="0">待处理</option>
						<option value="2">正在处理</option>
						<option value="3">回退处理</option>
					</select>
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' colspan="4">
					<input id="button" type=button class='button' value="查看" onClick="submitDetailForm();">
				</td>
				<td class='button' colspan="4">
					<input id="button" type=button class='button' value="导出Excel" onClick="toExcel(t2);">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="WarningDetailBeforeQuery">
		<input type="hidden" name="taskCodeC" value="lplc">
		<input type="hidden" name="pageFlag">
		<table id="t2" bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="centertitle">环节</td>
				<td class="centertitle">状态</td>
				<td class="centertitle">报案号</td>
				<td class="centertitle">保单号</td>
				<td class="centertitle">被保险人</td>
				<td class="centertitle">标的</td>
				<td class="centertitle">操作人员</td>
				<td class="centertitle">最後一次操作时间</td>
				<td class="centertitle">险种</td>
				<td class="centertitle">等待时间</td>
				<td class="centertitle">流程图</td>
			</tr>
			<logic:notEmpty name="swfLogDto" property="swfLogList">
				<%
        SwfLogDto swfLogDto = (SwfLogDto)request.getAttribute("swfLogDto");
        String[] interval = (String[])request.getAttribute("interval");
        int i = 0;
        int index = 0;
        Collection swfLogList = swfLogDto.getSwfLogList();
        Iterator iterator = swfLogList.iterator();
        while(iterator.hasNext())
        {   SwfLogDto swfLogDto1 = (SwfLogDto)iterator.next();
            String flowID = swfLogDto1.getFlowID();
            String nodeStatus = swfLogDto1.getNodeStatus();
            String nodeName = swfLogDto1.getNodeName();
            String registNo = swfLogDto1.getRegistNo();
            String policyNo = swfLogDto1.getPolicyNo();
            String insuredName = swfLogDto1.getInsuredName();
            String lossItemName = swfLogDto1.getLossItemName();
            String handlerName =  swfLogDto1.getHandlerName();
            String handleTime = swfLogDto1.getHandleTime();
            String riskCodeName = swfLogDto1.getRiskCodeName();
        %>
				<%
           if(index %2== 0)
                out.print("<tr class=listodd>");
           else
                out.print("<tr class=listeven>");
        %>
				<td align="center"><%=nodeName %></td>
				<%if(nodeStatus.equals("0"))  {%>
				<td align="center">待处理</td>
				<%}else if(nodeStatus.equals("2")) { %>
				<td align="center">正在处理</td>
				<%}else if(nodeStatus.equals("3")) { %>
				<td align="center">回退处理</td>
				<%} %>
				<td align="center">
					<a href="/claim/swfFlowBeforeQuery.do?swfLogFlowID=<%=flowID %>"><%=registNo %></a>
				</td>
				<td align="center"><%=policyNo %></td>
				<td align="center"><%=insuredName %></td>
				<td align="center"><%=lossItemName %></td>
				<td align="center"><%=handlerName %></td>
				<td align="center"><%=handleTime %></td>
				<td align="center"><%=riskCodeName %></td>
				<td align="center"><%=interval[i] %></td>
				<td align="center">
					<a href="/claim/swfFlowBeforeQuery.do?swfLogFlowID=<%=flowID %>">查看</a>
				</td>
				</tr>
				<% index++;i++;}%>
			</logic:notEmpty>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr class="listtail">
				<td colspan="7">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td class='title'>
								<bean:define id="pageview" name="swfLogDto" property="turnPageDto" />
								<%
           SwfLogDto swfLogDto = (SwfLogDto)request.getAttribute("swfLogDto");
           int curPage = swfLogDto.getTurnPageDto().getPageNo();
           String taskcode =(String)request.getParameter("taskCodeC");
         %>
								<%@include file="/common/pub/TurnOverPage.jsp"%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="taskCodeC" value="<%=taskcode%>">
	</form>
</body>
</html:html>
