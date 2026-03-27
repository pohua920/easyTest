<%--
****************************************************************************
* DESC       ：路径增加编辑界面
* AUTHOR     ： weishixin
* CREATEDATE ： 2004-8-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"  %>

<html>
<head>
    <!--对title处理-->
<title><s:text name="title.workflow.wayAddEdit" /> <%--路径增加编辑 --%></title>
  <app:css />
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  
  <script src="/claim/workflow/model/js/WorkFlowPathAddEdit.js"></script>

 </head>
<body class="interface" onload="loadForm();">
  <form name=fm action="" method="post" >

    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
			<tr>
				<td class="formtitle" colspan=4><s:text name="workflow.oaModelWayEdit" />
					<%--工作流模板路径编辑 --%></td>
			</tr>
      <tr>

				<td class="title"><s:text name="workflow.wayNumber" />
					<%--路径号 --%>：</td>
				<td class="input"><input type=text name="swfPathPathNo" class="readonly" readonly="true" value=""></td>
				<td class="title" style="valign: bottom"><s:text name="workflow.wayName" />
					<%--路径名称 --%>：</td>
				<td class="input" style="valign: middle"><input type=text name="swfPathPathName" class="common" value=""></td>
      </tr>
      <tr>
				<td class="title"><s:text name="workflow.startNode" />
					<%--起始节点 --%>：</td>
				<td class="input"><select name="swfPathStartNodeNo" class="three" onchange="resetStartNodeName();">
				</select></td>
				<td class="title"><s:text name="workflow.startNodeName" />
					<%--起始节点名称 --%>：</td>
				<td class="input"><input type=text name="swfPathStartNodeName" class="common" value=""></td>
      </tr>
      <tr>
				<td class="title"><s:text name="workflow.endNode" />
					<%--终止节点 --%>：</td>
				<td class="input"><select name="swfPathEndNodeNo" class="three" onchange="resetEndNodeName();">
				</select></td>
				<td class="title"><s:text name="workflow.endNodeName" />
					<%--终止节点名称 --%>：</td>
				<td class="input"><input type=text name="swfPathEndNodeName" class="common" value=""></td>
      </tr>
      <tr>
				<td class="title"><s:text name="db.prpDIdentifier.identifierOrder" />
					<%--优先级 --%>：</td>
				<td class="input"><input type=text name="swfPathPriority" class="common" value="0"></td>
				<td class="title"><s:text name="workflow.shortWay" />
					<%--缺省路径 --%>：</td>
				<td class="input"><select name="swfPathDefaultFlag" size="1" class="three">
						<option value="0" SELECTED>
							<s:text name="regist.prpLregist.no" />
							<%--否 --%>
						<option value="1">
							<s:text name="regist.prpLregist.yes" />
							<%--是 --%>
						<option value="3">
							<s:text name="workflow.canSelect" />
							<%--供选择 --%>
				</select></td>
      </tr>

    </table>
      <table cellpadding="10" cellspacing="0" width="100%">

            <tr>
              <td class=button style="width:50%" align="center">
					<!--保存按钮--> <input type="button" name=buttonSave class='button' value=" <s:text name="prompt.ok"/> " onclick="return saveModelForm();">
				<%--确 定 --%>
              </td>

             <td class=button style="width:50%" align="center">
					<!--取消按钮--> <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="window.close();">
             </td>

        </tr>
      </table>
         <input type=hidden name="WorkFlowPathPageID" value="WorkFlowPath">
         <input type=hidden name="WorkFlowPathRecordOrder" value="">
  </form>
</body>
</html>