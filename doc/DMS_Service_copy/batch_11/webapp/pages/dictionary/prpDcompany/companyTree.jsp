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
//		function generateGrade(){//同步    2009.9.28 屏蔽同步功能 by 滑立敏。
//			if(confirm("同步数据可能需要一段时间，确定同步？")){
					
//				var url="${ctx}/dictionary/generatedPrpDcompanyGrade.do";
//				var handleSuccess = function(o){
//					if(o.responseText=="success"){
//						alert("同步成功!");
//					}else{
//						alert("同步失败!");
//					}
//				};
//				var handleFailure = function(o){
//				if(o.responseText !== undefined){
//						var msg = i18n.errors.updatefail+"!\n"+ o.status +" " + o.statusText;
//						alert(msg);
//						return true;
//					}
//				};	
//				var callback =
//				{
//				  success:handleSuccess,
//				  failure:handleFailure,
	//			  timeout:500000
	//			};
	//			var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
	//	}
	//}
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<!--
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center" class="top">
        <td><input type="button" value="同步到公司级别表" class="button_ty"
        onclick="return generateGrade()"></td>
    </tr>
  </table>
-->
<s:form name="fm" action=""	target="companyTreeRight">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<table width="100%" border="0" cellpadding="5" cellspacing="1">
		<br/>
		<div id="gradeTrees" align="left"></div>
		${treeScript}
		</table>
</s:form>
</div>
</div>
</body>
</html>