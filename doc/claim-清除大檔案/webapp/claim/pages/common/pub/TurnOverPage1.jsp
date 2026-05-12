<%--
****************************************************************************
* DESC       ：分页公共页面(紧急案件清单专用，有写死的内容，其他功能慎用)
* AUTHOR     ：曹志刚
* CREATEDATE ：2009-10-29
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/visa-app.tld" prefix="app"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:html>
<head>
<title><s:text name="title.pubBeforeEdit.pagingPublicPage" /></title>
<%--分页公共页面--%>
<app:css />
<script src="/claim/common/pub/TurnOverPage.js"></script>
<script language='javascript'>
	function currPage(page) {
		document.forms[0].pageNo.value = page;
		fm.action = "/claim/wfLogQuery.do?editType=" + fm.editType.value
				+ "&nodeType=" + fm.editType.value;
		fm.submit();
	}
</script>
</head>
<%
	
%>
<body>
	<table class=common>
		<tr>
			<input type="hidden" name="totalPage" value="<bean:write property='totalPage' name='pageview'/>">
			<logic:greaterEqual name="pageview" property="totalPage" value="0">
				<td width="60%" align="center">
					<div align="right">
						<s:text name="pub.conditionsRecord" />
						<%--满足条件的记录为--%>
						<bean:write property="totalCount" name="pageview" />
						<s:text name="manage.article" />
						<%--条--%>
						<s:text name="manage.subsection" />
						<%--第--%>
						<
						<bean:write property="pageNo" name="pageview" />
						<s:text name="manage.pageTotal" />
						<%--页/共--%>
						<bean:write property="totalPage" name="pageview" />
						<s:text name="navigator.page" />
						<%--页　--%>
						<logic:notEqual name="pageview" property="pageNo" value="1">
							<a href="javascript:FirstPage()"><u><s:text name="navigator.first" />
									<%--首页--%></u></a>
						</logic:notEqual>
						<logic:equal name="pageview" property="pageNo" value="1">
							<font color="#808080"><s:text name="navigator.first" />
								<%--首页--%></font>
						</logic:equal>
						<logic:greaterThan name="pageview" property="pageNo" value="1">
							<a href="javascript:PrePage(<bean:write name="pageview" property="pageNo"/>)"><u><s:text name="navigator.previousPage" />
									<%--前页--%></u></a>
						</logic:greaterThan>
						<logic:lessEqual name="pageview" property="pageNo" value="1">
							<font color="#808080"><s:text name="navigator.previousPage" />
								<%--前页--%></font>
						</logic:lessEqual>
						<%--bean:define id="curPage" name="pageview" property="pageNo" /--%>
						<logic:greaterThan name="pageview" property="totalPage" value='<%=String.valueOf(curPage)%>'>
							<a href="javascript:NextPage(<bean:write name="pageview" property="pageNo"/>)"><u><s:text name="navigator.afterPage" />
									<%--後页--%></u></a>
						</logic:greaterThan>
						<logic:lessEqual name="pageview" property="totalPage" value='<%=String.valueOf(curPage)%>'>
							<font color="#808080"><s:text name="navigator.afterPage" />
								<%--後页--%></font>
						</logic:lessEqual>
						<logic:greaterThan name="pageview" property="totalPage" value='<%=String.valueOf(curPage)%>'>
							<a href="javascript:LastPage()"><u><s:text name="navigator.last" />
									<%--尾页--%></u></a>
						</logic:greaterThan>
						<logic:lessEqual name="pageview" property="totalPage" value='<%=String.valueOf(curPage)%>'>
							<font color="#808080"><s:text name="navigator.last" />
								<%--尾页--%></font>
						</logic:lessEqual>
						<s:text name="manage.skip" />
						<%--跳到--%>
						<input type="text" name="changepage" size="2" class="common" style="width: 3%" value='<bean:write  property="pageNo" name="pageview"/>' maxlength="10">
						页<a href="javascript:ChangePage();"><html:img page="/images/bgGo.gif" width="20" height="15" border="0" align="absmiddle" /></a>
						<input type="hidden" name="pageNo" value='<bean:write  property="pageNo" name="pageview"/>'>
						<input type="hidden" name="condition" value="<bean:write  property="condition" name="pageview"/>">
						<input type="hidden" name="editType" value="<%=editType%>">
					</div>
				</td>
			</logic:greaterEqual>
		</tr>
	</table>
</body>
</html:html>