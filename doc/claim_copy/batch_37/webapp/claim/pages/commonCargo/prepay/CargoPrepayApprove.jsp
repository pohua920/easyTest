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
    <title><s:text name="title.prepayBeforeEdit.editPrepay"/></title> <%-- 预赔登记 --%>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
   <!--<script src="${ctx}/classC/prepay/js/CPrepayEdit.js"></script>-->
</head>

<body class=interface onload="initPage();InitDocument();initShow();" >
    <form name=fm action="${ctx}/prepayApprove.do" method="post" onsubmit="return validateForm(this);">
    <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%= session.getAttribute("org.apache.struts.action.TOKEN") %>">
      <!-- 1.预赔主信息 -->
      <%@include file="/classC/prepay/CPrepayMainShow.jsp"%>
      <!-- 4.预赔备注信息 -->
      <%@include file="/classC/prepay/CPrepayTextShow.jsp"%>

      <!-- 保存通用按钮 -->
        <table cellpadding="0" cellspacing="0" width="80%" style="display:">
          <tr>
            <td align="center"><input type="button" name="buttonApprove" value="<s:text name='button.nuclear.value'/>" class="button" onClick="approveSubmit();"></td> <%-- 复 核 --%>
          </tr>
        </table>
     </form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script src="${ctx}/common/js/Edit.js"> </script>
</html>
