<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>功能管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">功能增加</h2>
</div>
<s:form name="fm" action="" namespace="/utiITask" method="post" >
<s:hidden name="parentCode" id="parentCode" value="${parentCode}"></s:hidden>
<s:hidden name="sCode" id="sCode" value="${sCode}" />
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">功能代码<font color="RED">*</font></td>
			<td class="long">
			    <input type="text" name="saaTask.taskCode" id="saaTask.taskCode"  maxlength="255" class="input_w w_30 dc-chk  dt-nzhs" onblur="onChecked();" value="" />
			    	<nobr id="taskMsg"></nobr>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称简体<font color="RED">*</font></td>
			<td class="long">
				<input type="text" name="saaTask.taskCName" id="saaTask.taskCName"  maxlength="255" class="input_w w_30 dc-chk" value="" />  
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称繁体</td>
			<td class="long">
			    <input name="saaTask.taskTName" id="saaTask.taskTName"  maxlength="255" class="input_w w_30" value="" />
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">功能名称英文</td>
			<td class="long">
				<input type="text" name="saaTask.taskEName" id="saaTask.taskEName"  maxlength="255" class="input_w w_30 " value="" />
			</td>
		</tr>
		<tr>
<!--		<td class="bgc_tt short">上级功能代码<font color="RED">*</font></td>-->
<!--			<td class=" long">-->
<!--			    <div id="validStatusMapDiv" class="selectui-indiv">-->
<!--			        <div class="selectConfig">-->
<!--			        <div class="codeType">StaticSelect</div>-->
<!--			        </div>-->
<!--			        <c:set var="chked" value="0"/>-->
<!--			        <ce:select name="saaTask.parentCode" id="saaTask.parentCode" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${chked}" list="taskCodeMap" />-->
<!--			    -->
<!--			    </div>-->
<!--			</td>-->
		</tr>
		<tr>
			<td class="bgc_tt short">有效性</td>
			<td class="long">
		   		<div id="validStatusDiv" class="selectui-indiv">
		        <div class="selectConfig">
		        <div class="codeType">StaticSelect</div>
		        </div>
		        	<c:set var="checked" value="1" />
					<ce:radio name="saaTask.validStatus" value="${checked}" list="#{'1':'有效','0':'无效'}" ></ce:radio>
				</div>
			</td>
		</tr>
		</table>

	
</s:form></div>
</div>
	<table>
		<tr  align="right">
			<td >
				<input type="button" class="button_ty"  value="保存" onclick="addTaskMethod();"/>
			</td>
		</tr>
	</table>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script type="text/javascript">
	var parentCode_tip = new YAHOO.widget.Tooltip("parentCode_tip",{text:"请选择上级功能代码,默认为无上级!",context:"saaTask.parentCode",zIndex:300});	
	var tCode = "yes";
	function validate(){
		this.taskMsg = "";
	}
		
	function callBackTask(data){
	    if(!data){   
	     DWRUtil.setValue("taskMsg","该功能代码已经存在!");   
	     tCode = "no";    
	    }
	    else{
	     DWRUtil.setValue("taskMsg",null); 
	     tCode = "yes";
		}
	}
	function onChecked(){
		var taskCode = document.getElementById("saaTask.taskCode").value;
		Ims.isTaskCodeExist(taskCode,callBackTask);
	}
	
	function addTaskMethod(){
		if(YAHOO.quote.data.datacheck('fm')){
			if(tCode=="yes"){
				if(checkLen()){
					fm.action="${ctx}/utiITask/addTask.do";
					fm.submit();
				}
			}else{
				alert("界面输入有误，请核实！");
			}
		}else{
			alert("界面输入有误，请核实！");
		}
	}
</script>