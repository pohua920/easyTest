<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<html>
<head>
</head>
<body>
<s:form name="fm" action="">
  <s:hidden name="riskCodes" ></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		${treeScript }
    </table>
    
</s:form>
</body>
</html>
<script language="javascript">
function updateAuthRisk() {
	 var riskCode = getCheckValues();
	 fm.riskCodes.value = riskCode;
	 fm.action="${ctx}/saaUserPower/productPowerGrant.do?userCode=${userCode}";
	 fm.submit();
     return true; 
	
}
</script>
