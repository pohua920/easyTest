<%--
****************************************************************************
* DESC       ：分页公共页面
* AUTHOR     ：理赔组
* CREATEDATE ：2005-01-25
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<html:html>
<head>
<title>分页公共页面</title>
<app:css />
<script src="/claim/pages/common/pub/TurnOverPage.js"></script>
</head>
<%
	
%>
<body>
	<table class=common>
		<tr>
			<input type="hidden" name="totalPage" value="${totalPage.pageview}">
			<c:if test="${pageview.totalPage>='0'}">
				<td width="60%" align="center">
					<div align="right">
						满足条件的记录为${totalCount.pageview}条 第${pageNo.pageview}页/共${totalPage.pageview}页
						<c:if test="${pageview.pageNo!='1'}">
							<a href="javascript:FirstPage()"><u>首页</u></a>
						</c:if>
						<c:if test="${pageview.pageNo=='1'}">
							<font color="#808080">首页</font>
						</c:if>
						<c:if test="${pageview.pageNo>'1'}">
							<a href="javascript:PrePage(${pageview.pageNo})"><u>前页</u></a>
						</c:if>
						<c:if test="${pageview.pageNo<='1'}">
							<font color="#808080">前页</font>
						</c:if>
						<%--bean:define id="curPage" name="pageview" property="pageNo" /--%>
						<c:if test="${pageview.totalPage>'<%= String.valueOf(curPage) %>'}">
							<a href="javascript:NextPage(${pageview.pageNo})"><u>後页</u></a>
						</c:if>
						<c:if test="${pageview.totalPage<='<%= String.valueOf(curPage) %>'}">
							<font color="#808080">後页</font>
						</c:if>
						<c:if test="${pageview.totalPage>'<%= String.valueOf(curPage) %>'}">
							<a href="javascript:LastPage()"><u>尾页</u></a>
						</c:if>
						<c:if test="${pageview.totalPage<='<%= String.valueOf(curPage) %>'}">
							<font color="#808080">尾页</font>
						</c:if>
						跳到
						<input type="text" name="changepage" size="2" class="common" style="width: 3%" value='${pageNo.pageview}' maxlength="10">
						页<a href="javascript:ChangePage();"><html:img page="/images/bgGo.gif" width="20" height="15" border="0" align="absmiddle" /></a>
						<input type="hidden" name="pageNo" value='${pageNo.pageview}'>
						<input type="hidden" name="condition" value="${condition.pageview}">
					</div>
				</td>
			</c:if>
		</tr>
	</table>
</body>
</html:html>