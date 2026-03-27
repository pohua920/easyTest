<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>权限数据导入</title>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">权限数据导入</h2>
</div>
<s:form name="fm" method="POST" enctype ="multipart/form-data">

	<table align="center" class="fix_table">
		<tr align="center">
			<td class="bgc_tt short">数据导入</td>
			<td class="bgc_tt_endcase short"><s:file name="powerImportExcel" 
				title="请选择文件路径" cssClass="button_ty"></s:file>
			</td>
		</tr>
		<tr align="center">
			<td class="bgc_tt_endcase short"><a
				href="${ctx}/pages/downloadFiles/PowerModel.xls">权限模板下载</a></td>
			<td class="bgc_tt_endcase short"><a
				href="${ctx}/generate/generateRiskCodes.do">产品代码模拟器</a></td>
		</tr>
	</table>
	<table align="center" class="fix_table">
		<tr>
			<td align="center">
	<button type="button" align="top" 
				name="submitImportButton" value="" onclick="submitImportData()"><span><em>数据导入</em></span></button>
<!--			<input type="button" align="top" -->
<!--				name="submitImportButton" value="数据导入" onclick="submitImportData()"-->
<!--				class="button_ty">-->
				</td>
		</tr>
	</table>
</s:form></div>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
function submitImportData(){
	alert("如果数据量比较大,整个过程会很缓慢,请您耐心等待....");
	fm.submitImportButton.disabled=true;
 	fm.action="${ctx}/saaUserPower/userPowerImport.do";
    fm.submit();
    return true;
}
</script>
