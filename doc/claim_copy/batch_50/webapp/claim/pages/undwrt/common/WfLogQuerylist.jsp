<!--***************************************************************************
* Description: 处理任务列表(包括核保、核赔、核损)
* Author     : luyang
* CreateDate : 2004-12-27 14:37
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<title>${HandTitle }<s:text name="title.undwrtBeforeEdit.TaskqueryResults" /></title>
<%--任务查询结果 --%>
<script language="javascript">
window.history.forward(1); 
</script>
<!--通用函数-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<!--通用任务处理函数-->
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
</head>
<body>
	<form name="fm" action="${ctx }/CommonCheckTask.do" target="fraInterface" method="post">
		<c:if test="${HandType=='11'}">
  &nbsp;&nbsp;<font color="#D82626"><s:text name="prompt.undwrt.Iformation" /></font>
			<%-- 点击协议号进行批量核保！ --%>
		</c:if>
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class=common>
				<td colspan="10"><s:if test="#attr.EditType=='query'">
						<s:text name="undwrt.Query" />${HandTitle }<s:text name="task" />
						<%-- 查询 --%>
						<%-- 任务 --%>
					</s:if> <s:elseif test="#attr.EditTyp=='submit'">
						<s:text name="undwrt.TaskSubmit" />
						<%-- 任务提交 --%>
					</s:elseif> <s:else>
	    ${HandTitle }<s:text name="undwrt.TaskQuery" />
						<%-- 任务查询 --%>
					</s:else></td>
			</tr>
			<tr class=listtitle>
				<td><s:text name="sendUndwrt.BusinessNumber" /></td>
				<%-- 业务号 --%>
				<td><s:text name="db.prpCOpenCoverBal.policyno" /></td>
				<%-- 协议号 --%>
				<td><s:text name="db.prpCmain.insuredName" /></td>
				<%-- 被保险人名称 --%>
				<td><s:text name="db.prpLsalvation.licenseNo " /></td>
				<%-- 号牌号码 --%>
				<td><s:text name="undwrt.SubmissionTime" /></td>
				<%-- 提交时间 --%>
				<td><s:text name="archive.level" /></td>
				<%-- 级别 --%>
				<td><s:text name="prpLdocCollect.riskCode" /></td>
				<%-- 险种代码 --%>
				<td><s:text name="db.prpCOpenCoverBal.comCode" /></td>
				<%-- 机构代码 --%>
				<td><s:text name="undwrt.ReservationAgreement" /></td>
				<%-- 预约协议号 --%>
				<td><s:text name="undwrt.TaskStatus" /></td>
				<%-- 任务状态 --%>
				<td style="display: none"><s:text name="undwrt.Flow" /></td>
				<%-- 流向 --%>
			</tr>
			<c:if test="${WflogListForm!=null}">
				<c:forEach items="${WflogListForm}" var="WflogList" varStatus="WflogList_status">
					<tr class=common>
						<td><a class="check" href="#" onclick="checkTask(${WflogList_status.index })">${WflogList.businessNo}</a></td>
						<td>${WflogList.insuredName}</td>
						<td>${WflogList.licenseNo}</td>
						<td>${WflogList.flowInTime}</td>
						<td>${WflogList.nodeName}</td>
						<td>${WflogList.riskCode}</td>
						<td>${WflogList.comCode}</td>
						<td>${WflogList.relateContractNo}</td>
						<td>${WflogList.nodeStatusName}</td>
						<td style="display: none">${WflogList.flowStatusName}</td>
						<!--隐含域-->
						<span style="display: none"> <input name="BusinessNo" value="${WflogList.businessNo}"> <input name="BusinessType" value="${WflogList.businessType}"> <input name="ContractNo" value="${WflogList.contractNo}"> <input name="FlowID" value="${WflogList.flowID}"> <input name="PackageID" value="${WflogList.packageID}"> <input name="LogNo"
							value="${WflogList.logNo}"> <input name="ModelNo" value="${WflogList.modelNo}"> <input name="NodeNo" value="${WflogList.nodeNo}"> <input name="FlowStatus" value="${WflogList.flowStatus}"> <input name="DeptCode" value="${WflogList.deptCode}"> <input name="FlowInTime" value="${WflogList.flowInTime}"> <input name="NodeStatus"
							value="${WflogList.nodeStatus}"> <input name="RiskCode" value="${WflogList.riskCode}"> <input name="ClassCode" value="${WflogList.classCode}">
						</span>
					</tr>
				</c:forEach>
			</c:if>
			<!--控制数组有效的隐含域-->
			<span style="display: none"> <input name="iBusinessNo"> <input name="iBusinessType"> <input name="iContractNo"> <input name="iFlowID"> <input name="iPackageID"> <input name="iModelNo"> <input name="iNodeNo"> <input name="iFlowStatus"> <input name="iDeptCode"> <input name="iFlowInTime"> <input
				name="iNodeStatus"> <input name="iLogNo"> <input name="iRiskCode"> <input name="iClassCode">
			</span>
			<!--隐含域,-->
			<span style="display: none"> <input name="BusinessNo"> <input name="BusinessType"> <input name="ContractNo"> <input name="FlowID"> <input name="PackageID"> <input name="LogNo"> <input name="ModelNo"> <input name="NodeNo"> <input name="FlowStatus"> <input name="DeptCode">
				<input name="FlowInTime"> <input name="NodeStatus"> <input name="RiskCode"> <input name="ClassCode"> <input name="EditType" value="${EditType }"> <input name="HandType" value="${HandType }">
			</span>
		</table>
		<table class=menu align="center">
			<tr>
				<td align=right height=27px><s:text name="manage.total" /> ${RowsCount }<s:text name="manage.article" /> <s:text name="manage.subsection" /> ${PageNo } <s:text name="manage.pageTotal" /> ${PageCount }<s:text name="navigator.page " /> <%-- 共 --%>
					<%-- 条 --%> <%-- 第 --%> <%-- 页/共 --%> <%-- 页 --%> <input type="hidden" name="PageCount" value="${RowsCount }"> <input type="hidden" name="pageNo" value="${PageNum }"> <input type="hidden" name="pageSize" value="15"> <input type="hidden" name="conditions" value="${Conditions }"> <c:if test="${PageNum>1}">
						<a name="FirstPage" class=common alt="首页" href="#" onclick="gotoPage('First');"><s:text name="navigator.first" /></a>
						<%--首页  --%>
						<a name="PreviousPage" class=common alt="上一页" href="#" onclick="gotoPage('Previous');"><s:text name="manage.previousPage" /></a>
						<%-- 上一页 --%>
					</c:if> <c:if test="${PageNum>PageCount}">
						<a name="NextPage" class=common alt="下一页" href="#" onclick="gotoPage('Next');"><s:text name="manage.nextPage" /></a>
						<%-- 下一页 --%>
						<a name="FinalPage" class=common alt="最後一页" href="#" onclick="gotoPage('Final');"><s:text name="undwrt.LastPage" /></a>
						<%-- 最後一页 --%>
					</c:if> <c:if test="${PageCount>1}">
						<s:text name="navigator.goToDi" />
						<%--转到第 --%>
						<input type="text" class=common style="width: 3%" name="Personal" size="2" value="1">
						<%--页--%>
						<s:text name="navigator.page" />
						<input name="image" onclick="return gotoPage('Personal')" type="image" src="/undwrt/common/images/buttonGo.gif" align="middle">
					</c:if></td>
			</tr>
		</table>
		&nbsp;
		<table class=two>
			<tr>
				<td align=center><c:if test="${EditType!=null&&EditType=='submit'}">
						<c:set var="EditType" value="deal" scope="page" />
					</c:if> <input class=button type="button" value="<s:text name='button.return.value'/>" onclick="javascript:window.location.href='${ctx }/pages/undwrt/common/WfLogQueryEntrance.jsp?HandType=${HandType }&EditType=${EditType }'"> <%-- 返 回 --%></td>
			</tr>
		</table>
		<c:remove var="PageCount" scope="session" />
		<c:remove var="RowsCount" scope="session" />
		<c:remove var="PageNum" scope="session" />
		<c:remove var="Conditions" scope="session" />
	</form>
</body>
<script language="javascript">
function gotoPage(strMethod) {
    if (strMethod == "First") {
        fm.pageNo.value = 1;
    } else if (strMethod == "Previous") {
        fm.pageNo.value = parseInt(fm.pageNo.value) - 1;
    } else if (strMethod == "Next") {
        fm.pageNo.value = parseInt(fm.pageNo.value) + 1;
    } else if (strMethod == "Final") {
        fm.pageNo.value = fm.PageCount.value;
    } else if (strMethod == "Personal") {
        if (parseInt(fm.Personal.value) < 1 || parseInt(fm.Personal.value) > parseInt(fm.PageCount.value)) {
            alert("没有这一页，请重试！");
            fm.Personal.focus();
            return false;
        } else {
            fm.pageNo.value = fm.Personal.value;
        }
    }
    fm.action = "/undwrt/WfLogQuery.do";
    fm.submit();
}
</script>
</html>