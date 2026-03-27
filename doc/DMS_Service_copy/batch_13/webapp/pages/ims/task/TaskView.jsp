<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<title>功能管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">功能查询</h2>
</div>
<s:form name="fm" action="" namespace="/task" method="post" target="menuTreeRight">
<input type="hidden" name="sCode" id="sCode" value="${sCode}" />
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">功能代码</td>
			<td class="long">
				<input name="saaTask.taskCode" id="saaTask.taskCode" value="${saaTask.taskCode }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称简体</td>
			<td class="long">
				<input name="saaTask.taskCName" id="saaTask.taskCName" value="${saaTask.taskCName }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称繁体</td>
			<td class="long">
				<input name="saaTask.taskTName" id="saaTask.taskTName" value="${saaTask.taskTName }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称英文</td>
			<td class="long">
				<input name="saaTask.taskEName" id="saaTask.taskEName" value="${saaTask.taskEName }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">上级功能代码</td>
			<td class="long">
				<input name="saaTask.parentCode" id="saaTask.parentCode" value="${saaTask.parentCode }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">所属服务代码</td>
			<td class="long">
				<input name="saaTask.svrCode" id="saaTask.svrCode" value="${saaTask.svrCode }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">创建人</td>
			<td class="long">
				<input name="creatorName" id="creatorName" value="${creatorName }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">创建时间</td>
			<td class="long">
				<input name="saaTask.createDate" id="saaTask.createDate" value="${saaTask.createDate}" class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">更新人</td>
			<td class="long">
				<input name="updaterName" id="updaterName" value="${updaterName }"class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">创建时间</td>
			<td class="long">
				<input name="saaTask.updateDate" id="saaTask.updateDate" value="${saaTask.updateDate}" class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">有效标识</td>
			<td class="long">
				<input name="validStatus" id="validStatus" value="${validStatus }" class='input_w w_15' readonly="true">
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="button" name="ok" class="button_ty" align="center" value="确定" onclick="onClose();"/>
			</td>
		</tr>
	</table>
</s:form></div>
</div>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
	/*
	function close(){
		javascript:window.opener.location.href="${ctx}/utiITask/prepareTaskManage.do";
		window.close();
	}
	*/
	function onClose(){
		fm.action="${ctx}/utiITask/prepareFrame.do";
		fm.target="page";
		fm.submit();
	}
</script>