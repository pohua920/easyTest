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
<body id="all_title" >
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">功能修改</h2>
</div>
<s:form name="fm" action="" namespace="/task" method="post" >
	<table class="fix_table">
	<input type="hidden" name="saaTask.id" id="saaTask.id" value="${saaTask.id }" />
	<input type="hidden" name="saaTask.creatorCode" id="saaTask.creatorCode" value="${saaTask.creatorCode }" />
	<input type="hidden" name="saaTask.createDate" id="saaTask.createDate" value="${saaTask.createDate }" />
	<input type="hidden" name="sCode" id="sCode" value="${sCode}" />
	<input type="hidden" name="saaTask.parentCode" id="saaTask.parentCode" value="${saaTask.parentCode }" />
	<input type="hidden"  id="test" value="${saaTask.taskCode }" />
		<tr>
			<td class="bgc_tt short">功能代码<font color="RED">*</font></td>
			<td class="long">
				<input name="saaTask.taskCode" id="saaTask.taskCode" value="${saaTask.taskCode }" maxlength="255" class='input_w w_30 dc-chk  dt-nzhs' >
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称简体<font color="RED">*</font></td>
			<td class="long">
				<input name="saaTask.taskCName" id="saaTask.taskCName" value="${saaTask.taskCName }" maxlength="255" class='input_w w_30 dc-chk'>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称繁体</td>
			<td class="long">
				<input name="saaTask.taskTName" id="saaTask.taskTName" value="${saaTask.taskTName }" maxlength="255" class='input_w w_30'>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称英文</td>
			<td class="long">
				<input name="saaTask.taskEName" id="saaTask.taskEName" value="${saaTask.taskEName }" maxlength="255" class='input_w w_30'>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">上级功能代码<font color="RED">*</font></td>
			<td class=" long">
<!--			    <div id="validStatusMapDiv" class="selectui-indiv">-->
<!--			        <div class="selectConfig">-->
<!--			        <div class="codeType">StaticSelect</div>-->
<!--			        </div>-->
<!--			        <c:set var="check" value="${saaTask.parentCode}"/>-->
<!--			        <ce:select name="saaTask.parentCode" id="saaTask.parentCode" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${check}" list="taskCodeMap" />-->
<!--			    </div>-->
				${tree}
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">有效标识</td>
			<td class="long">
				<ce:radio name="saaTask.validStatus" value="${saaTask.validStatus }" list="#{'1':'有效','0':'无效'}"></ce:radio>
			</td>
		</tr>
		</table>
	
</s:form></div>
</div>
	<table>
		<tr>
			<td colspan="2" align="center">
				<input type="button" name="modify" class="button_ty" align="center" value="保存" onclick="modifyMethod()"/>
			</td>
<!--			<td colspan="2" align="center">-->
<!--				<input type="button" name="cancel" class="button_ty" align="center" value="返回" onclick="window.history.back(-1)"/>-->
<!--			</td>-->
		</tr>
	</table>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script type="text/javascript">
//var parentCode_tip = new YAHOO.widget.Tooltip("parentCode_tip",{text:"请选择上级功能代码,默认为无上级!",context:"saaTask.parentCode",zIndex:300});
	function modifyMethod(){
		var test = document.getElementById("test").value; 
		var sCode = document.getElementById("sCode").value;
		var taskCode = document.getElementById("saaTask.taskCode").value;
		if(test != taskCode){
			Ims.hasChild(test,sCode,testChild);
		}else {
			checkUpperCode();
		}
		
	}

	function testChild(date){
		if(date == 'yes'){
			if(!confirm("该功能代码改变会导致其子节点无效，是否确定要更改功能代码？")){
				return false;
			}else{
				checkUpperCode();
			}
		}else {
			checkUpperCode();
		}
		
	}

	function checkUpperCode(){
		var parentCode1 = document.getElementById("saaTask.parentCode").value;
		if(parentCode1 == '0'){
			alert("根节点不能调整上级代码！");
			return false;	
		}
		var parentCode = getCheckValue();
		if(parentCode == null){
			alert("请选择上级代码！");
			return false;
		}
		var taskCode = document.getElementById("saaTask.taskCode").value;
		if(parentCode==taskCode){
			alert("不能选择自己作为上级节点!");
			return false;
		}else{
			Ims.checkUpperCode(taskCode,parentCode,execute);
		}
	}

	function execute(date){
		var parentCode = getCheckValue();
		if(date == 'no'){
			alert('不能选择自己的下级代码做上级代码！');
			return false;
		}else {
		if(YAHOO.quote.data.datacheck('fm')){
			if(checkLen()){
				fm.action="contextRootPath/utiITask/updateTask.do?parentCode="+parentCode;
				fm.submit();
			}
		}else{
			alert("界面输入有误,请核实!");
		}
		}
	}
</script>