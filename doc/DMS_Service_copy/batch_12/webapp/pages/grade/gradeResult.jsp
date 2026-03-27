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

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		${treeScript }
    </table>
   <br><br><br><br> <br>
  <table width="100%" border="0" cellpadding="5" cellspacing="1">
    <tr>
             <td><input type="button" class="button_ty" value="增加岗位" onclick="prepareInsertGrade()" /></td>
             <td><input type="button" class="button_ty" value="配置岗位" onclick="configGrade()" /></td>
    </tr>
    <tr>
             <td><input type="button" class="button_ty" value="复制岗位" onclick="copyGrade()" /></td>
             <td><input type="button" class="button_ty" value="查看岗位" onclick="viewGrade()" /></td>
    </tr>
</table>
</s:form>
</div>
</div>
</body>
</html>


<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
  
	function prepareInsertGrade() {
		var gradeCodes = getCheckValue();
	    if(gradeCodes != null)  {
	    var gradeCode = gradeCodes.split(",");
	    }
		if (gradeCodes==null) {
			alert("请选择一个节点");
		} else if ("@"==gradeCode[0]) {
			alert("请选择岗位模板节点，岗位信息节点不能新增岗位");

		} else {
			fm.action = "${ctx}/saaGrade/prepareInsertGrade.do?editType=insert&gradeTemplId="+gradeCode[1];
			fm.submit();
		}
	}
	function configGrade() {
		var gradeCodes = getCheckValue();
	    if(gradeCodes != null)  {
	    var gradeCode = gradeCodes.split(",");
	    }
		if (gradeCodes==null) {
			alert("请选择一个节点");
		} else if ("$"==gradeCode[0]) {
			alert("请选择岗位信息节点");

		} else {
			fm.action = "${ctx}/saaGrade/prepareUpdateGrade.do?editType=update&gradeID="+gradeCode[1];
			fm.submit();
		}
	}
	function copyGrade() {
		var gradeCodes = getCheckValue();
	    if(gradeCodes != null)  {
	    var gradeCode = gradeCodes.split(",");
	    }
		if (gradeCodes==null) {
			alert("请选择一个节点");
		} else if ("$"==gradeCode[0]) {
			alert("请选择岗位信息节点");

		} else {
			fm.action = "${ctx}/saaGrade/prepareCopyGrade.do?editType=copy&gradeID="+gradeCode[1];
			fm.submit();
		}
	}
	function viewGrade() {
		var gradeCodes = getCheckValue();
	    if(gradeCodes != null)  {
	    var gradeCode = gradeCodes.split(",");
	    }
		if (gradeCodes==null) {
			alert("请选择一个节点");
		} else if ("$"==gradeCode[0]) {
			alert("请选择岗位信息节点");

		} else {
			fm.action = "${ctx}/saaGrade/viewGrade.do?editType=view&gradeID="+gradeCode[1];
			fm.submit();
		}
	}
</script>
