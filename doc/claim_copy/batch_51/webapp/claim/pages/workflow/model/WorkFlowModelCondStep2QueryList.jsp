<%--
****************************************************************************
* DESC       : 工作流模板条件Step2列表
* AUTHOR     ：weishixin
* CREATEDATE ：2004-08-22
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
<title><s:text name="title.workflow.oaModelWayList" />
	<%--工作流模板路径列表 --%></title>
<script src="/claim/common/js/showpage.js">
</script>
  <html:base/>
</head>

<body  >

  <table  class="common" cellpadding="5" cellspacing="1" >
		<tr>
			<td colspan=8 class="formtitle"><s:text name="title.workflow.oaModelWayList" />
				<%--工作流模板路径列表 --%></td>
		</tr>
     <tr>
			<td class="centertitle"><s:text name="workflow.wayNumber" />
				<%--路径号 --%></td>
			<td class="centertitle"><s:text name="workflow.wayName" />
				<%--路径名称 --%></td>
			<td class="centertitle"><s:text name="workflow.startNode" />
				<%--起始节点 --%></td>
			<td class="centertitle"><s:text name="workflow.startNodeName" />
				<%--起始节点名称 --%></td>
			<td class="centertitle"><s:text name="workflow.endNode" />
				<%--终止节点 --%></td>
			<td class="centertitle"><s:text name="workflow.endNodeName" />
				<%--终止节点名称 --%></td>
			<td class="centertitle"><s:text name="db.prpDIdentifier.identifierOrder" />
				<%--优先级 --%></td>
			<td class="centertitle"><s:text name="workflow.shortWay" />
				<%--缺省路径 --%></td>
     </tr>
		<%
			int index = 0;
		%>
     <logic:notEmpty  name="swfPathDto"  property="pathList">
     <logic:iterate id="pathList1"  name="swfPathDto"  property="pathList">
<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");
%>
				<tr class=common>
					<td align="center"><a href="/claim/swfConditionBeforeEdit.do?editType=EDIT&modelNo=<bean:write name='pathList1' property='modelNo'/>&pathNo=<bean:write name='pathList1' property='pathNo'/>&pathName=<bean:write name='pathList1' property='pathName'/>"><bean:write name='pathList1' property='pathNo' /></a></td>
        <td align="center"><bean:write name="pathList1" property="pathName"/></td>
        <td align="center"><bean:write name="pathList1" property="startNodeNo"/></td>
        <td align="center"><bean:write name="pathList1" property="startNodeName"/></td>
        <td align="center"><bean:write name="pathList1" property="endNodeNo"/></td>
        <td align="center"><bean:write name="pathList1" property="endNodeName"/></td>
        <td align="center"><bean:write name="pathList1" property="priority"/></td>
        <td align="center"><bean:write name="pathList1" property="defaultFlag"/></td>
      </tr>
				<%
					index++;
				%>
      </logic:iterate>
      </logic:notEmpty>
      <tr class="listtail">
			<td colspan="8"><s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%><%=index--%><s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录 --%></td>
      </tr>
  </table>
    </table>
    </tr>
  </table>


</body>
</html:html>