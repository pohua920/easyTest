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
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">用户分类</h2>
</div>
<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
<s:hidden name="userType" id="userType" value="${userType}"></s:hidden>
	<table width="100%" class="fix_table">
							
		    <tr>
				<td class="bgc_tt short">用户分类</td>
				<td class="long""><s:select name="userSort" value="${userSort}"
					list="#@java.util.HashMap@{'01':'个人用户','02':'企业用户'}"  />
			</tr>
		 
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<td><input type="button" value="下一步" class="button_ty"
				onclick="return nextMethod()"></td>
            
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<script language="javascript">
	var tabView = new YAHOO.widget.TabView('tabdemo');
	var tabFlag = new Array();
	tabFlag.push("taskIframe1");


    function nextMethod(){
			fm.action="${ctx}/utiIUser/prepareInsertUser.do";
			fm.submit();
			return true;
	}
</script>