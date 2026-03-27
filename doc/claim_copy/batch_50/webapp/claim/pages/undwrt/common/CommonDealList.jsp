<!--***************************************************************************
* Description: 公共处理任務主界面(包括详细信息、提交再保确认、保存、提交等。)
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
<title>${EditTitle}${HandTitle }<s:text name="title.undwrtBeforeEdit.Task" /></title>
<%-- 任務 --%>
<!--通用函数-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<!--通用任務处理函数-->
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
</head>
<body class=interface>
	<form name="fm" action="${ctx }/pages/undwrt/CommonSubmitTask.do">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="undwrt.Submit" />
					${HandTitle }
					<s:text name="undwrt.Tasks" />
				</td>
				<%--提交 --%>
				<%--任務(向前) --%>
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="undwrt.SelectNode" />
				</td>
				<%--选择节点 --%>
				<td>
					<s:text name="db.prpGnode.nodeNo" />
				</td>
				<%--节点号 --%>
				<td>
					<s:text name="db.prpGnode.nodeName" />
				</td>
				<%--节点名称--%>
				<td>
					<s:text name="undwrt.DefaultPath" />
				</td>
				<%--默认路径 --%>
			</tr>
			<c:if test="${submitList!=null}">
				<c:forEach items="${submitList}" var="submitDto">
					<tr>
						<td class="text" style="width: 10%">
							<input type="radio" name="radSelectNode" value="0" onclick="setSelectNode();">
							<s:text name="archive.choice" />
						</td>
						<%-- 选择 --%>
						<td class="text" style="width: 30%">
							<input type="text" class="readonly" readonly name="NodeNo" value="${submitDto.endNodeNo}">
						</td>
						<td class="text" style="width: 30%">
							<input type="text" class="readonly" readonly name="NodeName" value="${submitDto.endNodeName}">
						</td>
						<td class="text" style="width: 30%">
							<input type="text" class="readonly" readonly name="DefaultPath" value='<c:if test="${submitDto.defaultFlag=='0'}"><s:text name="certainLoss.thirdCarLoss.no"/></c:if><c:if test="${submitDto.defaultFlag=='1'}"><s:text name="certainLoss.thirdCarLoss.yes"/></c:if>'>
							<%-- 否 --%>
							<%-- 是 --%>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			&nbsp;
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="title.prepayBeforeEdit.editPrepay" />
					<s:text name="undwrt.Submit" />
					${HandTitle }
					<s:text name="undwrt.Rollback" />
				</td>
				<%-- 任務(回退) --%>
				<%-- 提交 --%>
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="undwrt.SelectNode" />
				</td>
				<%--选择节点 --%>
				<td>
					<s:text name="db.prpGnode.nodeNo" />
				</td>
				<%--节点号 --%>
				<td>
					<s:text name="db.prpGnode.nodeName" />
				</td>
				<%--节点名称--%>
				<td>
					<s:text name="undwrt.DefaultPath" />
				</td>
				<%--默认路径 --%>
			</tr>
			<c:if test="${submitBackList!=null}">
				<c:forEach items="${submitBackList}" var="BackList">
					<tr>
						<td class="text">
							<input type="radio" name="radSelectNode" value="0" onclick="setSelectNode();">
							<s:text name="archive.choice" />
						</td>
						<%-- 选择 --%>
						<td class="text">
							<input type="hidden" value="${BackList.logNo}">
							<input type="text" class="readonly" readonly name="NodeNo" value="${BackList.nodeNo}">
						</td>
						<td class="text">
							<input type="text" class="readonly" readonly name="NodeName" value="${BackList.nodeName}">
						</td>
						<td class="text">
							<input type="text" class="readonly" readonly name="DefaultPath" value="<s:text name='certainLoss.thirdCarLoss.no'/>">
							<%-- 否 --%>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<!--隐含域-->
		<span style="display: none"> <input type="radio" name="radSelectNode" value="0"> <input name="FlowID" value="<s:property value='#parameters.FlowId'/>"> <input name="ModelNo" value="<s:property value='#parameters.ModelNo'/>"> <input name="NodeNo" value=""> <input name="LogNo" value="<s:property value='#parameters.LogNo'/>"> <input name="CertiType"
				value="<s:property value='#parameters.BusinessType'/>"> <input name="BusinessNo" value="<s:property value='#parameters.BusinessNo'/>"> <input name="BusinessType" value="<s:property value='#parameters.BusinessType'/>"> <input name="FlowStatus" value="0"> <input name="Flag" value="1"> <input name="OperatorCode"
				value="<s:property value='#parameters.OperatorCode'/>"> <input name="SingleSubmit" value=""> <input name="MultiSubmit" value=""> <input name="selectNodeNo"> <input name="selectNodeName"> <input name="ModelNo">
		</span>
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="text">
					<s:text name="undwrt.SubmitInformation" />：
					<input type="text" name="SelectNode" class="readonly" readonly style="width: 80%" value="">
				</td>
				<%-- 提交信息 --%>
			</tr>
			<span style="display: none"> <input type="text" name="SelectUser" class="readonly" readonly value=""> <input type="hidden" name="submitPage" value="1" description="确定该页面为提交页面">
			</span>
		</table>
		<table class=sub>
			<tr>
				<c:if test="${undwrt_continuetask!=null&&undwrt_continuetask=='1'}">
					<!-- "指定人员"功能隐藏 -->
					<td class=button width=34% style="display: none">
						<Input name="people" class="button" type="button" alt="指定人员" value="<s:text name='button.Officer.value'/>" onclick="selectPeople();">
						<%-- 指定人员 --%>
					</td>
				</c:if>
				<td class=button width=33%>
					<Input class="button" name="ok" type="button" alt="确定" value="<s:text name='button.determine.value'/>" onclick="submitTask();">
					<%-- 确 定 --%>
				</td>
				<td class=button width=33%>
					<Input name="prev" class="button" type="button" alt="取消" value="<s:text name='button.cancel.value'/>" onclick="backQuery();">
					<%-- 取 消 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>