<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	target="companyTreeRight">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
	<s:if test="${editType=='view' }">
		<script language="javascript">
		d = new dTree('d');
		d.add('0','-1','功能列表(功能名称后两选项框分别表示内网，外网权限)按住SHIFT可同时选择下级','','功能列表','','','','',false,false,true);
	   <s:iterator value="gradeTasks" status="stuts">
  			d.add('<s:property value="%{gradeTasks[#stuts.index].taskCode}" />','<s:property value="%{gradeTasks[#stuts.index].taskParentCode}" />','<s:property value="%{gradeTasks[#stuts.index].taskCName}" />','','','','','','',true,'<s:property value="%{gradeTasks[#stuts.index].value}" />',true,true,true,'<s:property value="%{gradeTasks[#stuts.index].intranetValue}" />',true,'<s:property value="%{gradeTasks[#stuts.index].internetValue}" />');
  		</s:iterator>	
	document.getElementById("gradeTrees").innerHTML = d;  
    function openAndClose(){
      if(fom.openCloseAll.value=="展开"){
        fom.openCloseAll.value = "合并";
        d.openAll();
      }else{
        fom.openCloseAll.value = "展开";
        d.closeAll();
      }
    }
	 </script>
	</s:if>
	<s:else>
	<script language="javascript">
		d = new dTree('d');
		d.add('0','-1','功能列表(功能名称后两选项框分别表示内网，外网权限)按住SHIFT可同时选择下级','','功能列表','','','','',false,false,true);
	   <s:iterator value="gradeTasks" status="stuts">
  			d.add('<s:property value="%{gradeTasks[#stuts.index].taskCode}" />','<s:property value="%{gradeTasks[#stuts.index].taskParentCode}" />','<s:property value="%{gradeTasks[#stuts.index].taskCName}" />','','','','','','',true,'<s:property value="%{gradeTasks[#stuts.index].value}" />',true,'<s:property value="%{gradeTasks[#stuts.index].hasPower}" />',true,'<s:property value="%{gradeTasks[#stuts.index].intranetValue}" />',true,'<s:property value="%{gradeTasks[#stuts.index].internetValue}" />');		
  		</s:iterator>	
	document.getElementById("gradeTrees").innerHTML = d; 
    function openAndClose(){
      if(fm.openCloseAll.value=="展开"){
        fm.openCloseAll.value = "合并";
        d.openAll();
      }else{
        fm.openCloseAll.value = "展开";
        d.closeAll();
      }
    }
    function selected(){
    	var listCheckBox=new Array();
    	var listintranetCheckBox=new Array();
    	var listinternetCheckBox=new Array();
    	listCheckBox=fm.treeCheckBox;
    	listintranetCheckBox=fm.intranetCheckBox;
    	listinternetCheckBox=fm.internetCheckBox;
    	for(var i=0;i<listCheckBox.length;i++){
    		if(listCheckBox[i].checked){
    			parent.treeCheckBox(listCheckBox[i]);
    		}
    		if(listintranetCheckBox[i]!=null&&listintranetCheckBox[i].checked){
    			parent.intranetCheckBox(listintranetCheckBox[i]);
    		}
    		if(listinternetCheckBox[i]!=null&&listinternetCheckBox[i].checked){
    			parent.internetCheckBox(listinternetCheckBox[i]);
    		}
    		
    	}
    }
	 </script>
	 </s:else>
</s:form>
</div>
</div>
</body>
</html>
