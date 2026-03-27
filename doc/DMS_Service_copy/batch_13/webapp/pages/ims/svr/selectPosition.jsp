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
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请选择集中方式</h2>
</div>
<s:form name="fm" action="/" method="post">
	<table width="100%" class="fix_table">
	    <tr>
			<td class="bgc_tt short" >集中方式</td>
			<td class="long" >
				<ce:select name="position" id="position" cssClass="input_y w_30" value="" list="#{'1':'全集中','2':'省集中'}" />
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
		fm.action = "${ctx}/utiISvr/prepareSvrInsert.do";
		fm.submit();
	}

</script>
