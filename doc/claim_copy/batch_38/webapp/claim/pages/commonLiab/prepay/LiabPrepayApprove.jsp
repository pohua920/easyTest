<!--
****************************************************************************
* DESC       ：预赔登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
    <!--对title处理-->
    <title><s:text name="title.prepayBeforeEdit.editPrepay" /></title><%--预赔登记--%>
   <%@ include file="/common/meta_js.jsp"%>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
  <script src="${ctx}/pages/commonProp/prepay/js/PropPrepayEdit.js"></script>
</head>
<body  onload="initPage();InitDocument();initShow();" class="interface">
    <form name=fm action="${ctx}/prepayApprove.do" method="post" onsubmit="return validateForm(this);">
      <!-- 1.预赔主信息 -->
      <%@include file="/pages/commonLiab/prepay/LiabPrepayMainShow.jsp"%>
      <!-- 4.预赔备注信息 -->
      <%@include file="/pages/commonLiab/prepay/LiabPrepayTextShow.jsp"%>

      <!-- 保存通用按钮 -->
        <table cellpadding="0" cellspacing="0" width="80%" style="display:">
          <tr>
            <td align="center"><input type="button" name="buttonApprove" value="<s:text name='button.nuclear.value' />" class="button" onClick="approveSubmit();"></td>
          </tr>
        </table>
     </form>
</body>
<script src="${ctx}/common/js/Edit.js"> </script>
</html>
