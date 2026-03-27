<%--
**************************************************************************** 
* DESC       ：调度处理页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-01-22
* MODIFYLIST ：   Name       Date            Reason/Contents
                
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<head>
<!--对title处理-->
<title>
	<%-- 调度处理 --%>
	<s:text name="certainLoss.prpLregist.attemperOperate" />
</title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/pages/DAA/schedule/js/DAAScheduleEdit.js"></script>
<%-- 标签页样式 --%>
<jsp:include page="/pages/behaviors/MpcStyle.jsp" />
<script src="/claim/common/js/ajax/ajax.js"></script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
		//查勘控制，只要选择了定损调度，则必须选择查勘调度
	    var checkScheduleCheckYesNoDoc = fm.checkScheduleCheckYesNo;
	    $(":checkbox[name='checkYesNo']").click(function(){
	        if(this.checked && !checkScheduleCheckYesNoDoc.checked){
	        	checkScheduleCheckYesNoDoc.checked = true;
	        }
	    });
	})
</script>
</head>
<%--
	//reason: 防止重复提交
	session.setAttribute("oldScheduleLastAccessedTime", "");
	String editType = request.getParameter("editType");
	String nodeType = request.getParameter("nodeType"); //判断是哪个节点上的
	String flowId = request.getParameter("swfLogFlowID"); //工作流号码
	String getbackLogNo = request.getParameter("swfLogLogNo"); //改派的节点号码
	String endflag = request.getParameter("endflag");
	UserDto user = (UserDto) session.getAttribute("user");
	boolean hasSchedFlag = false; //是否有项目做过调度了
--%>
<c:set var="oldScheduleLastAccessedTime" value="" scope="session"/>
<c:set var="saveType1" value="" />
<c:choose>
	<c:when test="${editType eq 'SHOW' || editType eq 'DELETE'}">
		<body class="interface" onload="initPage();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body class=interface onload="initPage();generateCheckText();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<c:if test="${prpLscheduleMainWF.saveType == 'GETBACKEDIT'}">
	<c:set var="saveType1" value="GETBACKEDIT" />
</c:if>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx}/schedule/scheduleEditPost.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
		<input type="hidden" name="nodeType" value="sched">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name=nowURL value="<%=request.getRequestURI()%>">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' || editType == 'GETBACKEDIT'}">
			<s:token></s:token>
		</c:if>
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
			<TR>
				<td align="left">
					<input type="button" class=button name="message" value="<s:text name='button.composeMessage.value' />"
						onClick="openWinSave(fm.prpLscheduleMainWFRegistNo.value,fm.prpLscheduleMainWFPolicyNo.value,fm.prpLscheduleMainWFRiskCode.value,'sched','');">
				</td>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />"> <%--基本讯息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.调度主信息头信息 --%>
						<%@include file="/pages/DAA/schedule/DAAScheduleMainEdit.jsp"%>
						<%-- 4.报案信息补充说明 --%>
						<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
			<%--判断是否无查勘 --%>
			<c:set var="noCheck" value="1" />
			<%-- 正常调度处理--%>
			<c:choose>
				<c:when test="${saveType1 != 'GETBACKEDIT'}">
					<mpc:page ID="tabMain" TABTITLE="<s:text name="schedule.surveyProcess" />" TABTEXT="<s:text name="schedule.surveyProcess" />">  <%--查勘分案处理--%>
						<CENTER>
							<DIV name="tabMain" class="tabMain">
								<%@include file="/pages/DAA/schedule/DAAScheduleCheckItemEdit.jsp"%>
								<c:set var="noCheck" value="0"></c:set>
							</DIV>
						</CENTER>
					</mpc:page>
					<%--不管報案時選擇是否需要現場處理顯示　查勘調度　刪除--%>
					<mpc:page ID="tabMain" TABTITLE="<s:text name="schedule.feeTaskProcessing" />" TABTEXT="<s:text name="schedule.feeTaskProcessing" />"> <%--定损分案任务处理--%>
						<CENTER>
							<DIV name="tabMain" class="tabMain">
								<%-- 3.定损调度标的内容 --%>
								<%@include file="/pages/DAA/schedule/DAAScheduleItemEdit.jsp"%>
							</DIV>
						</CENTER>
					</mpc:page>
				</c:when>
				<c:otherwise>
					<%-- 改派的操作--%>
					<c:choose>
						<c:when test="${param.nodeType=='check'}">
							<%-- 查勘调度处理 --%>
							<mpc:page ID="tabMain" TABTITLE="<s:text name="schedule.surveyProcess" />" TABTEXT="<s:text name="schedule.surveyProcess" />">
								<CENTER>
									<DIV name="tabMain" class="tabMain">
										<%@include file="/pages/DAA/schedule/DAAScheduleCheckItemEdit.jsp"%>
										<c:set var="noCheck" value="0"></c:set>
									</DIV>
								</CENTER>
							</mpc:page>
						</c:when>
						<c:otherwise>
							<mpc:page ID="tabMain" TABTITLE="<s:text name="schedule.feeTaskProcessing" />" TABTEXT="<s:text name="schedule.feeTaskProcessing" />">
								<CENTER>
									<DIV name="tabMain" class="tabMain">
										<%-- 3.定损调度标的内容 --%>
										<%@include file="/pages/DAA/schedule/DAAScheduleItemEdit.jsp"%>
									</DIV>
								</CENTER>
							</mpc:page>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/schedule/DAAScheduleSave.jsp"%>
				</TD>
			</TR>
		</TABLE>
		<input type="hidden" name="prpLregistLicenseNo" value="${prpLregist.licenseNo}">
		<input type="hidden" name="newHandlerCode" value="">
		<%--用来表示是不是换新人的操作--%>
		<input type="hidden" name="nocheck" value="<c:out value="${noCheck}"/>">
		<input type="text" name=saveType value="<c:out value="${saveType1}"/>">
		<input type="hidden" name=flowId value="${param.flowId}">
		<input type="hidden" name=endflag value="${param.endflag}">
		<input type="hidden" name=getbackLogNo value="${param.getbackLogNo}">
		<input type="hidden" name=getbackNodeType value="${param.nodeType}">
		<input type="hidden" name=comcode value="${user.comCode}">
		<%--调度中心的代码user.getScheduleComCode()--%>
		<input type="hidden" name=newcomcode value="${user.comCode}">
		<%--选择调度处理单位的代码，默认是调度中心的代码--%>
		<input type="hidden" name=handlecomcode value="${user.comCode}">
		<%--当前调度员部门里的人员，估计查勘，定损人应该和调度员是一个部门的--%>
	</form>
</DIV>
</body>
</html>
