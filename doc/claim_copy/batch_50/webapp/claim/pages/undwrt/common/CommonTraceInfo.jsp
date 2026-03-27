<!--***************************************************************************
* Description:  轨迹信息显示
* Author     :  Luyang
* CreateDate :  2005-1-18 9:59
* UpdateLog：   Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<title><s:text name="title.undwrtBeforeEdit.InformationInquiry" /></title>
<%-- 信息查询 --%>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<!--公用信息-->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<script language='javascript'>
	
</script>
</head>
<body onload="">
	<form name="fm" action="">
		<table class="common" cellpadding="5" cellspacing="1" align="center" id="Tinsure">
			<tr class=listtitle>
				<td colspan="4">
					<s:text name="guarantee.checkedAdvice" />
				</td>
				<%-- 审核意见 --%>
			</tr>
			<c:if test="${TraceInfoList!=''}">
				<!--   <logic:iterate indexId="index" id="traceInfo" name="TraceInfoList" >-->
				<c:forEach items="${TraceInfoList }" var="traceInfo" varStatus="index">
					<tr>
						<td colspan=4>
					</tr>
					<tr>
						<td class="title4">
							<s:text name="archive.levelName" />：
						</td>
						<%-- 级别名称 --%>
						<td class="input4" colspan=3>${traceInfo.nodeName}</td>
					</tr>
					<tr>
						<td class="title4">
							<s:text name="undwrt.Handlers" />：
						</td>
						<%-- 处理人员 --%>
						<td class="input4">${traceInfo.operatorName}</td>
						<td class="title4">
							<s:text name="check.proDepartment" />：
						</td>
						<%-- 处理部门 --%>
						<td class="input4">${traceInfo.deptCode}</td>
					</tr>
					<tr>
						<td class="title4">
							<s:text name="undwrt.LevelStatus" />：
						</td>
						<%-- 级别状态 --%>
						<td class="input4">${traceInfo.nodeStatusName}</td>
						<td class="title4">
							<s:text name="undwrt.Flow" />：
						</td>
						<%-- 流向 --%>
						<td class="input4">${traceInfo.flowStatusName}</td>
					</tr>
					<tr>
						<td class="title4">
							<s:text name="undwrt.SubmissionTime" />：
						</td>
						<%-- 提交时间 --%>
						<td class="input4">${traceInfo.flowInTime}</td>
						<td class="title4">
							<s:text name="undwrt.ProcessingTime" />：
						</td>
						<%-- 处理完毕时间 --%>
						<td class="input4">${traceInfo.submitTime}</td>
					</tr>
					<tr>
						<td class=title4>
							<s:text name="guarantee.checkedAdvice" />：
						</td>
						<%-- 审核意见 --%>
						<td readonly class=input4 colspan="3">
							<textarea class=common name="HandleTextMemo" rows="4" readonly> ${traceInfo.handleText}</textarea>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<table class=two>
			<tr>
				<td align="center">
					<Input class="button" name="buttonClose" type="button" alt="关闭" value="<s:text name='button.close.value'/>" onclick="closeWindow()">
				</td>
				<%-- 关 闭 --%>
			</tr>
		</table>
	</form>
</body>
</html>