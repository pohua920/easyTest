<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<html>
<head>
<title>权限数据导出</title>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">权限数据导出</h2>
</div>
<s:form name="fm" method="post" enctype ="multipart/form-data">
	<table align="center" class="fix_table">
		<tr align="center">
            <td class="bgc_tt short">机构代码</td>
			<td class="long">
                <s:textarea  name="comCodes" cols="30" rows="3"
				cssClass="input_y w_p90"
				ondblclick="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0,1', 'Y','')" />
			</td>
			<td class="bgc_tt short">机构名称</td>
			<td class="long">
                <s:textarea name="comNames" cols="30" rows="3"
				cssClass="w_p90" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
			<button type="button" align="top" 
				name="submitImportButton" value="" onclick="submitImportData()"><span><em>数据导出</em></span></button>
			
<!--			<input type="button" align="top" -->
<!--				name="submitImportButton" value="数据导出" onclick="submitImportData()"-->
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
	if(fm.comCodes.value==""){
		alert("导出权限机构不可为空！");
		return false;
	}
	alert("如果数据量比较大,整个过程会很缓慢,请您耐心等待....");
	fm.submitImportButton.disabled=true;
 	fm.action="${ctx}/saaUserPower/userPowerExport.do";
    fm.submit();
    return true;
}
</script>
