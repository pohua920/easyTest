<!--***************************************************************************
* Description: 公共处理任务主界面(包括详细信息、提交再保确认、保存、提交等。)
* Author     : luyang
* CreateDate : 2004-12-30 10:53
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<title><s:text name="title.undwrtBeforeEdit.SubmitTasks" /></title>
<%-- 提交核赔任务 --%>
<!--通用函数-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<!--通用任务处理函数-->
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
</head>
<body class=interface>
	<form name="fm" action="${ctx }/CommonSubmitTask.do">
		<s:token></s:token>
		<input type="hidden" name="submitTip">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="undwrt.SubmitTasks" />
					&nbsp;&nbsp;
					<s:text name="undwrt.SubmitSuperior" />
				</td>
				<%-- 提交核赔任务 --%>
				<%-- 提交上级 --%>
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="archive.selectTheSuperior" />
				</td>
				<%-- 选择上级 --%>
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
			<c:if test="${submitList!=null}">
				<c:forEach items="${submitList}" var="submitDto">
					<tr>
						<td class="text" style="width: 20%">
							<input type="radio" name="radSelectNode" value="0" onclick="setSelectNode('${submitDto.endNodeName}');">
							<s:text name="archive.choice" />
						</td>
						<%-- 选择 --%>
						</td>
						<td class="text" style="width: 40%">
    						${submitDto.endNodeNo-1}
							<input type="hidden" class="readonly" readonly name="NodeNo" value = "${submitDto.endNodeNo}">
						</td>
						<td class="text" style="width: 40%">
							<input type="text" class="readonly" readonly name="NodeName" value="${submitDto.endNodeName}">
						</td>
					</tr>
				</c:forEach>
			</c:if>
			&nbsp;
		</table>
		<!--隐含域-->
		<span style="display: none"> <input type="radio" name="radSelectNode" value="0"> <input name="FlowId" value="<s:property value='#parameters.FlowId'/>"> <input name="ModelNo" value="<s:property value='#parameters.ModelNo'/>"> <input name="NodeNo" value=""> <input name="NodeName" value=""> <input name="LogNo"
				value="<s:property value='#parameters.LogNo'/>"> <input name="CertiType" value="<s:property value='#parameters.BusinessType'/>"> <input name="BusinessNo" value="<s:property value='#parameters.BusinessNo'/>"> <input name="BusinessType" value="<s:property value='#parameters.BusinessType'/>"> <input name="FlowStatus" value="0"> <input name="Flag" value="1">
			<input name="OperatorCode" value="<s:property value='#parameters.OperatorCode'/>"> <input name="SingleSubmit" value=""> <input name="MultiSubmit" value=""> <input name="selectNodeNo"> <input name="selectNodeName"> <input name="ModelNo"> <!-- add by caozhigang 20090401 start  --> <!-- reason:保存提交上级的意见 --> <input name="HandleText"
				value="<s:property value='#parameters.HandleText'/>"> <!-- add by caozhigang 20090401 end  --> <input type="hidden" name="ClaimNo" value="<s:property value='#parameters.ClaimNo'/>" /> <input type="hidden" name="notion" value="<s:property value='#parameters.notion'/>" />
		</span> &nbsp;
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="text">
					<s:text name="undwrt.SubmitSuperior" />：
					<input type="text" name="SelectNode" class="readonly" readonly style="width: 80%" value="">
				</td>
				<%-- 提交上级 --%>
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
	}else{
		document.getElementsByName("ok")[0].disabled=true;
		var SelectNode = document.getElementsByName("SelectNode")[0];
		SelectNode.value='您已經是最高階，不能繼續提交，請點擊"取消"按鈕返回！';
    }
}
window.onload = selectNode;
</script>