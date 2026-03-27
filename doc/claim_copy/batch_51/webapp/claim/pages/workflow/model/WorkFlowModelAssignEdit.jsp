 <%--
****************************************************************************
* DESC       ：工作流模板分配界面
* AUTHOR     ：weishixin
* CREATEDATE ：2004-08-25
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
<title><s:text name="title.workflow.oaModelPartView" /> <%--工作流模板分配界面 --%></title>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/workflow/model/js/WorkFlowModelAssignEdit.js"></script>
</head>

<body class="interface" onload="initPage();">
    <form name=fm action="/claim/swfModelUseSave.do" method="post">


    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
      <tr>
				<td class="title" colspan="4"><s:text name="workflow.oaModelPart" />
					<%--工作流模板分配 --%></td>
      </tr>
      <tr>
				<td class="title"><s:text name="workflow.template" />
					<%--模板 --%>：</td>

				<td class="input"><html:select name="swfModelUseDto" property="modelNo" size="10" styleClass="three">
         <html:options collection="modelMainList" property="modelNo"   labelProperty="modelName"/>
					</html:select></td>


				<td class="title" style="valign: bottom"><s:text name="db.prpDdbs.riskCode" />
					<%--险种 --%>：</td>
				<td class="input" style="valign: middle"><html:select name="swfModelUseDto" property="riskCode" size="10" multiple="true" styleClass="three">
             <html:options collection="riskList" property="riskCode"   labelProperty="riskCName"/>
					</html:select></td>
        </tr>
        <tr>
				<td class="title" style="valign: bottom"><s:text name="title.workflow.oaFlowQueryResultShow" />
					<%--机构 --%>：</td>
				<td class="input" style="valign: middle"><html:select name="swfModelUseDto" property="comCode" size="10" multiple="true" styleClass="three">
		    <html:options collection="companyList" property="comCode"   labelProperty="comCName"/>
					</html:select></td>
        <td class=title colspan="2"></td>
      </tr>

    </table>
		<input type=hidden name=swfModelUseModelStatus value='1'> <input type=hidden name="editType" value="<bean:write name='swfModelUseDto' property='editType' filter='true' />">

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
