<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<s:form>
<frameset  cols="250,*" frameborder="no" border="0" framespacing="0"
	rows="*">
	<frame name="menuTreeLeft" src="${ctx}/smcMenu/getMenuList.do?smcMenu.utiISvr.svrCode=<%=request.getParameter("smcMenu.utiISvr.svrCode")%>&smcMenu.utiISvr.svrName=<%=request.getParameter("smcMenu.utiISvr.svrName")%>">
	<frame name="menuTreeRight" src="">
</frameset>
</s:form>
</html>



