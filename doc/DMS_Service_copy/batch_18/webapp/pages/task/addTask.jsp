<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>增加功能</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
</head>
<body>
<div id="crash_menu">
<h2 align="center">功能代码表维护</h2>
</div>
<s:form name="fm" action="addTask" method="task"
	namespace="/task">
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">功能代码</td>
			<td class="long"><s:textfield name="saaTask.taskCode"
				id="saaTask.taskCode" cssClass='input_w w_30' /></td>
			<td class="bgc_tt short">上级功能代码</td>
			<td class="long"><s:textfield name="saaTask.parentCode"
				cssClass='input_x w_30' value="${saaTask.parentCode}"
				ondblclick="code_CodeSelect(this, 'TaskCode', '0,1', 'Y','')"
				onkeyup="code_CodeSelect(this, 'TaskCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'TaskCode', '0,1', 'Y','')" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能简体名称</td>
			<td class="long"><s:textfield name="saaTask.taskCName"
				id="saaTask.taskCName" cssClass='input_w w_30' /></td>

			<td class="bgc_tt short">功能英文名称</td>
			<td class="long"><s:textfield name="saaTask.taskEName"
				id="saaTask.taskEName" cssClass='input_w w_30' /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能繁体名称</td>
			<td class="long"><s:textfield name="saaTask.taskTName"
				id="saaTask.taskTName" cssClass='input_w w_30' /></td>
			<td class="bgc_tt short"><!--创建人--></td>
			<td class="long">&lt;<!--
				<s:textfield name="saaTask.creatorCode" id="saaTask.creatorCode"
					cssClass='input_w w_30' disabled="true"/>--></td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<td><input type="submit" class="button_ty"
				value="增&nbsp;&nbsp;加" /></td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td><input type="button" class="button_ty"
				value="返&nbsp;&nbsp;回" onclick="history.back();" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/dwr/interface/dwrInvokeDataAction.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script>
function init(){
	initAllSelectUi();
}
YAHOO.util.Event.addListener(window,'load',init);
</script>

