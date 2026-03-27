<!--
****************************************************************************
* DESC       ：结案登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-28
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
  <link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
  <%@ include file="/common/meta_js.jsp"%>
<script language="javascript">
  function submitForm()
  {
    fm.submit();
  }
</script>
</head>

<body class=interface onload="initPage();" >
  <form name="fm" method="post" action="${ctx}/endcase/endcaseSave.do?step=step2">
    <h3><s:text name="prompt.endcase.finalSuccessSaved" /></h3><br><%--结案登记保存成功！--%>
    <h3><s:text name="prompt.endcase.pleaseDotContinueNext" /></h3><%--要生成结案号,请点击下一步按钮！--%>
    <hr>
    <input type=hidden name="prpLendcaseClaimNo1" value="${claimNo }">
    <input type="button" name="buttonNext" value="<s:text name='button.continueNext.value' />" class="bigbutton" onclick="submitForm()">
    <input  type="hidden" name=buttonSaveType value="1">
  </form>
</body>
</html>