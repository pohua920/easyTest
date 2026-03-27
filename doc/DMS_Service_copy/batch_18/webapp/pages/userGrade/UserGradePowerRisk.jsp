<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
	
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="riskCodes" ></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		${treeScript }
    </table>
    <table width="100%" class="fix_table" border="0">
	  <tr>
		<td align=center>
			<input type="button" value="确定" class="button_ty"
			onclick="return updatePermitRisk()">
		</td>
	  </tr>
    </table>
</s:form>
</div>
</div>
</body>
</html>
<script language="javascript">

function updatePermitRisk() {
	
		if(checkLen()){
			var riskCode = getCheckValues();
			if(riskCode == null){
				alert('请选择允许产品！');
				return false;
			}else {
				fm.action = '${ctx }/saaUserGrade/updateUserPowerOnRisk.do?userCode=<%=request.getParameter("userCode")%>&saaGradeID=<%=request.getParameter("saaGradeID")%>';
				fm.riskCodes.value = riskCode;
				fm.submit();
			}
		}
	
}
  
</script>