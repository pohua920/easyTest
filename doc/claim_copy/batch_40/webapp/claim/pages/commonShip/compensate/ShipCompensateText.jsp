<%--
****************************************************************************
* DESC       ：处理获取水险理算内容集的请求
* AUTHOR     ：中科软
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page import="com.sinosoft.claim.common.ConstantsCollection"%>
<%@page import="ins.framework.utils.DataUtils"%>
<%
	String contextNo = request.getParameter("contextNo");
	StringBuffer text = new StringBuffer("");
	if (DataUtils.emptyToNull(contextNo) != null) {
		String obj = ConstantsCollection.CompensateContext.get(contextNo);
		if (obj != null) {
			text.append(obj);
		}
	}
	response.getWriter().print(text.toString());
%>