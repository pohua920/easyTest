<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
<head>
<link href="${ctx}/style/style_all.css" type="text/css" rel="stylesheet">
</head>
<body>
<s:form name="fm" action="">
<div id="tabdemo" class="yui-navset">   
	    <ul class="yui-nav">
	    <c:set var="index" value="0" />
	    <s:iterator value="systemTasks" status="stuts">
	    <s:if test="${index+1==1}">
	    <li class="selected"><a href="#tab${index+1 }"><em><s:property value="%{systemTasks[#stuts.index].taskCName}" /></em></a></li>
	        <c:set var="index" value="${index+1 }" />
	    </s:if>
	    <s:else>
	    <li><a href="#tab${index+1 }" ><em><s:property value="%{systemTasks[#stuts.index].taskCName}" /></em></a></li>
	        <c:set var="index" value="${index+1 }" />
	    </s:else>
	    </s:iterator> 
	    </ul>               
	    <div class="yui-content">
	     <c:set var="indexi" value="0" /> 
	     <s:iterator value="systemTasks" status="stuts">
	        <div id="tab${indexi+1 }">
	        	<iframe id="taskIframe${indexi+1 }" src="${ctx}/saaUserPower/taskPowerConfigByRootCode.do?rootTaskCode=<s:property value="%{systemTasks[#stuts.index].taskCode}" />&userCode=${userCode}" frameborder="0" width="100%" height="539"></iframe>
	        	<s:property value="%{systemTasks[#stuts.index].taskCName}" />
	        </div>  
	        <c:set var="indexi" value="${indexi+1 }" /> 
	     </s:iterator>
	    </div>  
	</div>  
	<div id="taskConfigTree" style="display:none">	
	</div>
</s:form>
</body>
</html>
<script language="javascript">
var tabView = new YAHOO.widget.TabView('tabdemo');
function treeCheckBox(treeCheckBox){
  	treeCheckBox.checked=true;
  	document.getElementById("taskConfigTree").innerHTML+="<input type='checkbox' name='treeCheckBox' value="+treeCheckBox.value+" checked />";
  }
  function subMitMethod(){
	    //alert("11111111111111");
  		fm.action="${ctx}/saaUserPower/taskPowerGrant.do?userCode=${userCode}";
  		var framesNum=${indexi};
  		for(var i=1;i<=framesNum;i++){
  			//if(document.getElementById("taskIframe"+i).src!="#"){
  				document.frames["taskIframe"+i].selected();
  			//}
  		}
   		fm.submit();
   		return true;
   	}
  function checkForm(){
  	var taskList=new Array();
  	taskList=fm.treeCheckBox;
  	if(taskList.length<1){
  		alert("管理员授权需要授予该人员功能权限！");
  		return false;
  	}
  	return true;
  }
  
</script>
