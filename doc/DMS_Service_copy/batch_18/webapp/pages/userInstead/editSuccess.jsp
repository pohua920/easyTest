<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%> 
<%@include file="/common/meta_css.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<script type="text/javascript">
//parent.window.opener.location.reload();
</script>
<body>
  <table class=common align=center>
  <br>
    <tr>
      <td align="center"><img src='${pageContext.request.contextPath}/pages/image/success.gif'/></td>
      <td class="common"><s:property value="%{businessNo}"/>操作成功!</td>
    </tr>
    <tr>
      <td align="center" colspan="2">
      <button type="button"  value="" onclick="onClose();"><span><em>确   定</em></span></button>
<!--        <input type="button" class="button_ty" value="确   定" onclick="onClose();"> -->
      </td>
    </tr>
  </table>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
function onClose(){
    if(parent.submitDlg!=undefined){
        parent.submitDlg.hide();
    }else {
        window.close();
    }
}
</script>