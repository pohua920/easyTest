<!--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
    <!--对title处理-->
    <title><s:text name="title.endcaseBeforeEdit.editEndcase" /></title><%--结案登记--%>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
  <script src="${ctx }/pages/commonLiab/endcase/js/LiabEndcaseEdit.js"></script>
  <%@ include file="/common/meta_js.jsp"%>
  <script type="text/javascript">
  function showNotBackCount(){
    var NotBackCount = document.getElementsByName("prpNotBackCount");
    if(NotBackCount.length>0 && NotBackCount[0].value!="0"){
      alert(i18n.endcase.caseWith+NotBackCount[0].value+i18n.endcase.caseWith.message);
    }
  }
  </script>
</head>
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();">
</s:if>
<s:elseif test="#parameters.editType[0]=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();">
</s:else>
		<form name=fm action="${ctx }/endcase/endcaseSave.do?step=step1" method="post" onsubmit="return validateForm(this);">
			<c:if test="${editType == 'ADD' || editType == 'EDIT' }">
				<s:token></s:token>
			</c:if>
			<!-- 1.结案主信息 -->
			<%@include file="/pages/commonLiab/endcase/LiabEndcaseMainEdit.jsp" %>
			<!-- 4.结案文本信息 -->
			<%@include file="/pages/commonLiab/endcase/LiabEndcaseTextEdit.jsp"%>
			<!-- 保存通用按钮 -->
			<c:if test="${param.editTypeOther!='SHOWTASK'}">
				<%@include file="/pages/commonLiab/endcase/LiabEndcaseSave.jsp"%>
			</c:if>
		</form>
</body>
<html>
