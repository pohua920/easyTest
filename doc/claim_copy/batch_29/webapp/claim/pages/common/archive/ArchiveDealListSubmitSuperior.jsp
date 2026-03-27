<!--***************************************************************************
* Description: 提交上一级审核页面
* Author     : liuwei
* CreateDate : 2011-01-07
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs_base.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
<head>
<app:css />
<title><s:text name="title.archive.submitNextAuditTask" /></title>
<!-- 提交上一级审核任务 -->
<!--通用函数-->
<script src="/claim/pages/undwrt/common/js/Common.js"></script>
</head>
<body class=interface>
	<form name="fm" action="/claim/archive/batchArchiveDeal.do">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="formtitle" colspan="3">
					<s:text name="archive.readApplyCheckedSubmit" />
				</td>
				<!-- 调阅申请审核提交上级 -->
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="archive.selectTheSuperior" />
				</td>
				<!-- 选择上级 -->
				<td>
					<s:text name="archive.level" />
				</td>
				<!-- 级别 -->
				<td>
					<s:text name="archive.levelName" />
				</td>
				<!-- 级别名称 -->
			</tr>
			<c:if test="${submitList != null}">
				<c:forEach var="submitList" items="${submitList}">
					<tr>
						<td class="text" style="width: 20%">
							<input type="radio" name="radSelectNode" value="0" checked="checked">
							<s:text name="archive.choice" />
							<!-- 选择 -->
						</td>
						<td class="text" style="width: 40%">
							<input type="text" class="readonly" readonly name="NodeNo" value="${submitList.endNodeNo}">
						</td>
						<td class="text" style="width: 40%">
							<input type="text" class="readonly" readonly name="NodeName" value="${submitList.endNodeName}">
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${submitList == null}">
				<tr>
					<td colspan="3" class="text">
						<s:text name="archive.isHighestCannotSubmit" />
					</td>
					<!-- 已是最高级，无法提交上级 -->
				</tr>
			</c:if>
			&nbsp;
		</table>
		<table class="sub">
			<tr>
				<td class=button>
					<c:if test="${submitList != null}">
						<Input class="button" name="ok" type="submit" value="<s:text name="button.determine.value"/>">
						<!-- 确定 -->
					</c:if>
					<c:if test="${submitList == null}">
						<Input class="button" name="ok" type="submit" value="<s:text name="button.determine.value"/>" disabled="disabled">
						<!-- 确定 -->
					</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<Input name="prev" class="button" type="button" value="<s:text name="prompt.cancel"/>" onclick="preWindow();">
					<!-- 取消 -->
				</td>
			</tr>
		</table>
		<!-- 隐藏域 -->
		<input type="hidden" name="editType" value="submitDeal">
		<input type="hidden" name="serialNo" value="${serialNo}">
		<input type="hidden" name="claimNo" value="${claimNo}">
		<input type="hidden" name="modelNo" value="${modelNo}">
	</form>
</body>
</html>