<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请选择账户</h2>
</div>
<s:form name="fm" action=""	>
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
<s:hidden name="userCode" id="userCode" value="${userCode }"></s:hidden>
<s:hidden name="svrName" id="svrName" value="${svrName}"></s:hidden>
<s:hidden name="userSort" id="userSort" value="${userSort }"></s:hidden>
	<table width="100%" class="fix_table">
	    <tr>
			<td class="bgc_tt short" colspan="3">账户</td>
			<td class="long" ><s:select name="accName" id="accName" value="${accName}" list="#request.accNames" /></td>
		</tr>
	</table>
	<table width="100%" class="fix_table">
		<tr class="top" align="center">
			<td align="center"><input type="button" value="下一步" class="button_ty" onclick="nextMethod();" /></td>
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<script language="javascript">
	function nextMethod(){
	//	var accName = document.getElementById("utiIAccount.accName").value
		fm.action = "contextRootPath/utiIUser/nextMethodB.do";
		fm.submit();
 	}
	
</script>