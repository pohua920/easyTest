<%--
****************************************************************************
* DESC       : 工作流模板列表
* AUTHOR     ：weishixin
* CREATEDATE ：2004-08-10
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<html:html locale="true">
<head>
    <app:css />

<title><s:text name="title.workflow.oaModelList" />
	<%--工作流模板列表 --%></title>
<script src="/claim/common/js/showpage.js">
</script>
  <html:base/>
</head>

<body  >

  <table  class="common" cellpadding="5" cellspacing="1" >
		<tr>
			<td colspan=7 class="formtitle"><s:text name="workflow.oaList" />
				<%--工作流列表 --%></td>
		</tr>
     <tr>
			<td class="centertitle"><s:text name="db.prpGnode.modelNo" />
				<%--模板号 --%></td>
			<td class="centertitle"><s:text name="workflow.modelName" />
				<%--模板名称 --%></td>
			<td class="centertitle"><s:text name="workflow.modelType" />
				<%--模板类型 --%></td>
			<td class="centertitle"><s:text name="query.founder" />
				<%--创建人 --%></td>
			<td class="centertitle"><s:text name="query.bulidTime" />
				<%--创建时间 --%></td>
			<td class="centertitle"><s:text name="workflow.modelState" />
				<%--模板状态 --%></td>
			<td class="centertitle"><s:text name="workflow.viewConfiguration" />
				<%--可视化配置 --%></td>
     </tr>
		<%
			int index = 0;
		%>
     <logic:notEmpty  name="swfModelMainDto"  property="modelMainList">
     <logic:iterate id="modelMainList1"  name="swfModelMainDto"  property="modelMainList">
<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");
%>
				<tr class=common>
					<td align="center"><a href="/claim/swfModelBeforeEdit.do?editType=EDIT&modelNo=<bean:write name='modelMainList1' property='modelNo'/>"><bean:write name='modelMainList1' property='modelNo' /></a></td>
        <td align="center"><bean:write name="modelMainList1" property="modelName"/></td>
        <td align="center"><bean:write name="modelMainList1" property="modelType"/></td>
        <td align="center"><bean:write name="modelMainList1" property="authorCode"/></td>
        <td align="center"><bean:write name="modelMainList1" property="modifyDate"/></td>

					<td align="center"><logic:equal name="modelMainList1" property="modelStatus" value="0">
							<s:text name="workflow.notUsed" />
							<%--未使用 --%>
						</logic:equal> <logic:equal name="modelMainList1" property="modelStatus" value="1">
							<s:text name="workflow.using" />
							<%--正在使用 --%>
						</logic:equal> <logic:equal name="modelMainList1" property="modelStatus" value="2">
							<s:text name="workflow.cannotUsed" />
							<%--已停用 --%>
						</logic:equal> <logic:equal name="modelMainList1" property="modelStatus" value="3">
							<s:text name="workflow.notUsedRegister" />
							<%--未使用作废 --%>
						</logic:equal> <logic:equal name="modelMainList1" property="modelStatus" value="4">
							<s:text name="common.status.cancled" />
							<%--已注销 --%>
						</logic:equal></td>
					<td align="center"><a href="/claim/swfModelBeforeEdit.do?editType=view&modelNo=<bean:write name='modelMainList1' property='modelNo'/>"><img src='/claim/images/btnGo.gif' align='middle' style='cursor: hand' border='0' alt='转到' /></a></td>
      </tr>
				<%
					index++;
				%>
      </logic:iterate>
      </logic:notEmpty>
      <tr class="listtail">
			<td colspan="7"><s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%><%=index--%><s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录 --%></td>
      </tr>
  </table>
    </table>
    </tr>
  </table>


</body>
</html:html>
