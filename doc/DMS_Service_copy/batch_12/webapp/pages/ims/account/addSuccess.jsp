<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%> 
<%@include file="/common/meta_css.jsp"%>
</head>
<script type="text/javascript">
//parent.window.opener.location.reload();
</script>
<body>
  <s:form name="fm" action="" target="accountTreeRight">
  <s:hidden name="userCode" value="${userCode}"> </s:hidden>
  <table class="common" align="center">
    <tr>
      <td align="center">
        <img src='${pageContext.request.contextPath}/pages/image/success.gif'/>
      </td>
      <td class="common"><s:property value="%{businessNo}"/>增加成功!</td>
    </tr>
    <tr>
      <td align="center" colspan="2">
        <input type="button" class="button_ty" value="返   回" onclick="onClose();"> 
      
      </td>
    </tr>
  </table>
  </s:form>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
function onClose(){
	fm.action="${ctx}/utiIAccount/userAndAccountQuery.do";
	fm.target="utiIUserQueryRight";
    fm.submit();
}
</script>