<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : luyang
* CreateDate : 2004-12-30 10:53
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<title>${EditTitle }${HandTitle }<s:text name="title.undwrtBeforeEdit.Task" /></title>
<%-- 任务 --%>
<!--通用函数-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<!--通用任务处理函数-->
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
</head>
<body class=interface>
	<form name="fm" action="${ctx }/CommonSubmitTask.do">
		<%/** 防止重複提交，為審核頁面的提交上級動作，處理的是同一任務，所以token需一致  */%>
		<s:token></s:token>
		<input type="hidden" name="submitTip">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="formtitle" colspan="3">
					<s:text name="undwrt.Submit" />
					${HandTitle }
					<s:text name="task" />
				</td>
				&nbsp;&nbsp;
				<s:text name="undwrt.IssuedModified" />
				</td>
				<%-- 提交 --%>
				<%-- 任务 --%>
				<%-- 下发修改 --%>
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="undwrt.SelectLower" />
				</td>
				<%-- 选择下级 --%>
				<td>
					<s:text name="archive.level" />
				</td>
				<%-- 级别 --%>
				<td>
					<s:text name="archive.levelName" />
				</td>
				<%-- 级别名称 --%>
				<!--<td>默认路径</td>-->
			</tr>
			<c:if test="${submitBackList!=null}">
				<c:forEach items="${submitBackList}" var="BackList">
					<tr>
						<td class="text">
							<input type="radio" name="radSelectNode" value="0" onclick="setSelectNode('${BackList.nodeName}');">
							<s:text name="archive.choice" />
						</td>
						<%-- 选择 --%>
						<td class="text">
     						<input type="hidden" class="readonly" readonly name="NodeNo" value = "${BackList.nodeNo}">
     						${BackList.nodeNo-1}
     					</td>
						<td class="text">
							<input type="text" class="readonly" readonly name="NodeName" value="${BackList.nodeName}">
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<!--隐含域-->
		<span style="display: none"> <input type="radio" name="radSelectNode" value="0"> <input name="FlowId" value="<s:property value='#parameters.FlowId'/>"> <input name="ModelNo" value="<s:property value='#parameters.ModelNo'/>"> <input name="NodeNo" value=""> <input name="NodeName" value=""> <input name="LogNo"
				value="<s:property value='#parameters.LogNo'/>"> <input name="CertiType" value="<s:property value='#parameters.BusinessType'/>"> <input name="BusinessNo" value="<s:property value='#parameters.BusinessNo'/>"> <input name="BusinessType" value="<s:property value='#parameters.BusinessType'/>"> <input name="FlowStatus" value="1"> <input name="Flag" value="1">
			<input name="OperatorCode" value="<s:property value='#parameters.OperatorCode'/>"> <input name="SingleSubmit" value=""> <input name="MultiSubmit" value=""> <input name="selectNodeNo"> <input name="selectNodeName"> <input name="ModelNo"> <!-- add by caozhigang 20090401 start  --> <!-- reason:保存下发修改的意见 --> <input name="HandleText"
				value="<s:property value='#parameters.HandleText'/>"> <!-- add by caozhigang 20090401 end  --> <input type="hidden" name="ClaimNo" value="<s:property value='#parameters.ClaimNo'/>" /> <input type="hidden" name="notion" value="<s:property value='#parameters.notion'/>" />
		</span> &nbsp;
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="text">
					<s:text name="undwrt.IssueSubordinate" />
					：
					<input type="text" name="SelectNode" class="readonly" readonly style="width: 80%" value="">
				</td>
				<%-- 下发下级 --%>
			</tr>
			<span style="display: none"> <input type="text" name="SelectUser" class="readonly" readonly value=""> <input type="hidden" name="submitPage" value="1" description="确定该页面为提交页面">
			</span>
		</table>
		&nbsp;
		<table class="sub">
			<tr>
				<td class=button>
					<Input class="button" name="ok" type="button" value="<s:text name='button.determine.value'/>" onclick="submitTask();">
					<%-- 确定 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<Input name="prev" class="button" type="button" value="<s:text name='button.cancel.value'/>" onclick="preWindow();">
					<%-- 取消 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script language="javascript">
function selectNode() {
    if (fm.radSelectNode.length > 0) {
        fm.radSelectNode.item(0).checked = true;
        fm.SelectNode.value = fm.NodeName[0].value;
    }
}
window.onload = selectNode;
</script>