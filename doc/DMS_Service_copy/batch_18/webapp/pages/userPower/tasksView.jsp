<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%--
<%@include file="/common/meta_css.jsp"%>
 --%>
 <%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<html>
<head>
<link href="${ctx}/style/style_all.css" type="text/css" rel="stylesheet">
</head>
<body>
<s:form name="fm" action="">
<table>
	<tr align="left"><td align="left">
	<button type="button" name="openCloseAll" value="" onclick="openAndClose();"><span><em>展开</em></span></button>
	
<!--	<input type="button" name="openCloseAll" value="展开" onclick="openAndClose();" class="button_ty">-->
	</td>
	</tr>
</table>
	<div id="taskConfigTree" align="left">	
	</div>
</s:form>
</body>
</html>
<script language="javascript">
d = new dTree('d');
d.add('0','-1','功能列表(按住SHIFT可同时选择下级)','','功能列表','','','','',false,false,true);
<s:iterator value="saaAuthTaskVOList" status="stuts">	
  	d.add('<s:property value="%{saaAuthTaskVOList[#stuts.index].taskCode}" />','<s:property value="%{saaAuthTaskVOList[#stuts.index].taskParentCode}" />','<s:property value="%{saaAuthTaskVOList[#stuts.index].taskCName}" />','','','','','','',true,'<s:property value="%{saaAuthTaskVOList[#stuts.index].checked}" />',true,'<s:property value="%{saaAuthTaskVOList[#stuts.index].hasPower}"/>');
</s:iterator>	
	document.getElementById("taskConfigTree").innerHTML = d;  
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
    	listCheckBox=fm.treeCheckBox;
    	for(var i=0;i<listCheckBox.length;i++){
    		if(listCheckBox[i].checked){
    			parent.treeCheckBox(listCheckBox[i]);
    		}
    	}
    }
</script>
