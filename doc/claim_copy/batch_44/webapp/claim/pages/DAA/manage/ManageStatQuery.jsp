<%--
****************************************************************************
* DESC       ：1.1.	接报案总体工作情况统计
* AUTHOR     ：lijiyuan
* CREATEDATE ：2005-03-24
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%
	//得到本周周一与周日的日期
	String strDateColumn = "submittime";
	String strTitle = "";
	String strQReportType = request.getParameter("ReportType");
	if (strQReportType.equals("report_regist")) {
		strTitle = "備案總體工作情況查詢信息";
	}
	if (strQReportType.equals("report_registf")) {
		strDateColumn = "ReportDate";
		strTitle = "第壹現場案件數統計";
	}
	if (strQReportType.equals("report_check")) {
		strTitle = "查勘工作總體質量情況統計";
	}
	if (strQReportType.equals("report_bcheck")) {
		strDateColumn = "BackVisitTime";
		strTitle = "查勘回訪統計";
	}
	if (strQReportType.equals("report_cerloss_c")) {
		strTitle = "定損工作總體質量情況";
	}
	if (strQReportType.equals("report_certi")) {
		strTitle = "單證收集人員工作質量統計";
	}
	if (strQReportType.equals("report_certip")) {
		strTitle = "單證收集人員工作明細統計";
	}
	if (strQReportType.equals("report_compp")) {
		strTitle = "理算人員工作質量統計";
	}
	if (strQReportType.equals("report_comppp")) {
		strTitle = "理算人員工作明細統計";
	}
	if (strQReportType.equals("report_veric")) {
		strTitle = "核賠人員工作質量統計";
	}
	if (strQReportType.equals("report_vericp")) {
		strTitle = "核賠人員工作明細統計";
	}
	if (strQReportType.equals("report_bvisit")) {
		strTitle = "查勘工作服務質量回訪統計";
		strDateColumn = "BackVisitTime";
	}
	if (strQReportType.equals("report_bvisitp")) {
		strDateColumn = "BackVisitTime";
		strTitle = "查勘人員工作服務質量回訪統計";
	}
	if (strQReportType.equals("report_bvisitlist")) {
		strDateColumn = "BackVisitTime";
		strTitle = "查勘工作服務質量回訪明細";
	}
	//System.out.println("***********="+strQReportType);

	//Date date = new Date();
	//String strMonday = ""; //date.getMondayOFWeek();
	//String strSunday = ""; //date.getSundayOFWeek();

	String strSunday = DateTime.current().toString();
	String strMonday = new DateTime(DateTime.current().addMonth(-1), DateTime.YEAR_TO_DAY).toString();
%>
<html:html locale="true">
<head>
<title><s:text name="title.manageBeforeEdit.reportStatistics" /></title>
<!--接报案总体工作情况统计-->
<%-- 公用函数 --%>
<script src="/claim/common/Common.js"></script>
<script src="/claim/workflow/flow/js/WorkFlowFlowBeforeQuery.js"></script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/ManageReport.do?SelectStyle=S" method="post" onsubmit="return validateForm(this);">
		<table class=common cellpadding="5" cellspacing="1">
			<tr class=listtitle>
				<td colspan=4 class="formtitle"><%=strTitle%></td>
				</td>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLclaim.comCode" />:
				</td>
				<td class='input' style="width: 25%">
					<select name="ComCodeSign" class=query style="width: 20%">
						<option value="=">=</option>
					</select>
					<input type=text name="ComCode" class="input" style="width: 80%">
				</td>
				<%
					if (strQReportType.equals("report_certi") || strQReportType.equals("report_compp") || strQReportType.equals("report_veric")) {
				%>
				<td class='title' style="width: 10%">
					<s:text name="manage.timeFrames" />:
				</td>
				<!--处理时限-->
				<td class='input' style="width: 25%">
					<select name="DealTimeNoSign" class=query style="width: 20%">
						<option value="=">=</option>
					</select>
					<input type=text name="DealTime" class="input" style="width: 80%">
				</td>
				<%
					} else if (strQReportType.equals("report_certip") || strQReportType.equals("report_comppp") || strQReportType.equals("report_vericp")) {
				%>
				<td class='title' style="width: 10%">
					<s:text name="manage.staff" />:
				</td>
				<!--岗位人员-->
				<td class='input' style="width: 25%">
					<select name="HandlerCodeNoSign" class=query style="width: 20%">
						<option value="=">=</option>
					</select>
					<input type=text name="HandlerCode" class="input" style="width: 80%">
				</td>
				<%
					} else {
				%>
				<td class='title' style="width: 10%"></td>
				<td class='input' style="width: 25%"></td>
				<%
					}
				%>
				<td class='button' style="width: 20%" rowspan="2">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 10%">
					<s:text name="manage.startTime" />:
				</td>
				<!--开始时间-->
				<td class='input' style="width: 25%">
					<input type=text name="DateStart" class="input" style="width: 80%" value=<%=strMonday%>>
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.statStartDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class='title' style="width: 10%">
					<s:text name="manage.endTime" />:
				</td>
				<!--结束时间-->
				<td class='input' style="width: 25%">
					<input type=text name="DateEnd" class="input" style="width: 80%" value=<%=strSunday%>>
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.statEndDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
		</table>
		<input type="hidden" name="ReportType" value="<%=strQReportType%>">
		<input type="hidden" name="DateType" value="<%=strDateColumn%>">
		<input type="hidden" name="ExcelFlag" value="0">
		<input type="hidden" name="PageFlag" value="1">
		<input type="hidden" name="QueryStyle" value="6">
	</form>
</body>
<%@include file="/daa/manage/ReportConfig.jsp"%>
<%@include file="/daa/manage/ManageStatQueryList.jsp"%>
</html:html>
