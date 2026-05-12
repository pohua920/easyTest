<%--
****************************************************************************
* DESC       : 理赔系统分页项
* AUTHOR     : 理赔组
* CREATEDATE ：2013-04-11
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
   1，引用该分页的页面，当前分页查询後台查询规则中必须request.setAttribute("page",pageResult),否则无法实现分页
****************************************************************************/
--%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<%
	ins.framework.common.Page pageRecode = (ins.framework.common.Page) request.getAttribute("page");
%>
<input type="hidden" name="rowsPerPage" value="<%=pageRecode.getPageSize()%>">
<input type="hidden" name="pageNo" value="<%=pageRecode.getCurrentPageNo()%>">
<div align="center">
	<app:navigate objectName="page" display="true" />
</div>