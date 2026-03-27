<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
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
<h2 align="center">用户信息更新</h2>
</div>
<s:form name="fm" action="/" method="post">
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
<s:hidden name="userCode" id="userCode" value="${userCode }"></s:hidden>
<s:hidden name="userSort" id="userSort" value="${userSort }"></s:hidden>
	<table width="100%" class="fix_table">
	    <tr>
			<td class="bgc_tt short" >服务</td>
			<td class="long" >
				<s:select name="svrName" id="svrName" value="${svrName}" list="#request.svrNames" />
			</td>
		</tr>
	</table>
	<table width="100%" class="fix_table">
		<tr class="top" align="center">
			<td align="center"><input type="button" value="下一步" class="button_ty" onclick="nextMethod();"></td>
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
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script type="text/javascript">
	function nextMethod(){
	var svrName = document.getElementById("svrName").value;
	var userCode = document.getElementById("userCode").value;
	Ims.svrHasAccount(svrName,userCode,callBack);
	//	var svrName = document.getElementById("utiIAccount.svrName").value
/*	if(svrName.length==0){
		alert("没有对应的服务");
		fm.action = "contextRootPath/utiIUser/prepareQueryUserIdv.do";
		fm.submit();
	}else{
		fm.action = "contextRootPath/utiIUser/nextMethodA.do";
		fm.submit();
	}
*/
	}
	function callBack(data){
		if(data=="noAccount"){
			alert("没有服务对应的账户");
		}else{
			fm.action = "contextRootPath/utiIUser/nextMethodA.do";
			fm.submit();
		}
	}
	function onloadMethod(){
	}
</script>
