<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ taglib prefix="e3" uri="/e3/tree/E3Tree.tld" %>
<c:url var="orgIcon" value="/e3/samples/tree/Org.gif"/>
<c:url var="userIcon" value="/e3/samples/tree/User.gif"/>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<s:form name="fm" action=""	target="companyTreeRight">
<table>
	<tr align="left">
		<td align="left"><input type="button" name="generatGrade" value="重新导入级别数据" onclick="return generatGrade1();" class="button_ty"></td>
	</tr>
</table>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		<script language="javascript">
		 
			<e3:tree var="org" items="comCodList" builder="ExtLoadTree" >
			  <e3:node id="${org.comCode}" parentId="${org.uppercomcode}" name="${org.comCName}"
			           icon="${orgIcon}"
			           openIcon="${userIcon}" 
			           action="${ctx}/dictionary/prepareQueryprpDcompany.do?currentCode=${org.comCode}"  
			           subTreeURL="${ctx}/dictionary/subTree.do?currentCode=${org.comCode}"
			           cls="dynamic"
			  />
			</e3:tree>

     function generatGrade1(){
		if(confirm("重新导入需要较长时间，确定重新导入？")){
			fm.action="${ctx}/dictionary/generatedPrpDcompanyGrade.do";
			fm.submit();
		}
    }
	 </script>
	</table>
</s:form>
</div>
</div>
</body>
</html>