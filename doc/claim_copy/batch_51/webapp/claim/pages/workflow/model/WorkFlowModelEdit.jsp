<%--
****************************************************************************
* DESC       ：工作流模板录入/修改页面
* AUTHOR     ：weishixin
* CREATEDATE ：2004-08-10
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<html:html locale="true">
<head>
    <!--对title处理-->
<title><s:text name="title.workflow.oaModelEditView" /> <%--工作流模板编辑界面 --%></title>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/workflow/model/js/WorkFlowModelEdit.js"></script>
</head>

<body class="interface" onload="initPage();">
    <form name=fm action="/claim/swfModelSave.do" method="post" onsubmit="return validateForm(this,'WorkFlowNode_Data');">
        <%-- 1.模板主信息 --%>
            <%@include file="/workflow/model/WorkFlowModelMainEdit.jsp" %>
        <%-- 2.模板节点信息 --%>
            <%@include file="/workflow/model/WorkFlowNodeEdit.jsp"%>
        <%-- 3.模板路径信息--%>
            <%@include file="/workflow/model/WorkFlowPathEdit.jsp"%>
        <%-- 4.模板边条件 --%>
            <%-- @include file="/workflow/model/WorkFlowConditionEdit.jsp" --%>
        <%-- 保存通用按钮 --%>

          <table cellpadding="0" cellspacing="0" width="100%" style="display:">

            <tr>
              <td class=button style="width:50%" align="center">
					<!--保存按钮--> <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return submitForm();">
              </td>

             <td class=button style="width:50%" align="center">
					<!--取消按钮--> <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
             </td>

        </tr>
      </table>
     </form>
</body>

<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->


</html:html>
