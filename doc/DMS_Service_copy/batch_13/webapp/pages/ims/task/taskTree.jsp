<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>

<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
		function doAction(url){
			fm.action = url;
			fm.submit();
		}
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	target="companyTreeRight">
<s:hidden name="sCode" id="sCode" value="${sCode}"></s:hidden>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		${treeScript }
	</table>
	<br>
	<br>
	<br>
	<br>
	<table align="center" class="fix_table">
		<tr>
			<td align="center"><input type='button' class="button_ty"
				name=buttonInsert value="增加功能" onclick="prepareAddTask()"></td>
		</tr>		
		<tr>
			<td align="center"><input type='button' class="button_ty"
				name=buttonModify value="修改功能" onclick="modifyTask()"></td>
		</tr>
		<tr>
			<td align="center"><input type='button' class="button_ty"
				name=buttonView value="查看功能" onclick="viewTask()"></td>
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
function prepareAddTask(){
var taskCode = getCheckValue();
	if(taskCode==null){
		alert("请选择一个功能点作为父功能点");
	}else{
		fm.action = "${ctx}/utiITask/prepareAddTask.do?parentCode="+taskCode;
		fm.submit();
	}
}

function modifyTask(){
	var taskCode = getCheckValue();
	if(taskCode==null){
		alert("请选择一个功能");
	}else{
		fm.action = "${ctx}/utiITask/prepareModifyTask.do?taskCode="+taskCode;
		fm.submit();
	}
}
function viewTask(){
	var taskCode = getCheckValue();
	if(taskCode==null){
		alert("请选择一个功能");
	}else{
		fm.action = "${ctx}/utiITask/viewTask.do?taskCode="+taskCode;
		fm.submit();
	}
}
</script>