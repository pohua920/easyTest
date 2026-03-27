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
  <form name="fm" action="" target="menuTreeRight">
  <s:hidden name="smcMenu.utiISvr.svrCode" value="${smcMenu.utiISvr.svrCode}"/> 
<s:hidden name="smcMenu.utiISvr.svrName" value="${smcMenu.utiISvr.svrName}"/>
  <table class="common" align="center">
    <tr>
      <td align="center">
        <img src='${ctx}/pages/image/success.gif'/>
      </td>
      <td class="common"><s:property value="%{businessNo}"/>操作成功!</td>
    </tr>
    <tr>
      <td align="center" colspan="2">
        <input type="button" class="button_ty" value="返   回" onclick="onClose();"> 
      
      </td>
    </tr>
  </table>
  </form>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
function onClose(){
	fm.action="${ctx}/smcMenu/prepareFrame.do";
	 fm.target="page";
	fm.submit();
}
</script>