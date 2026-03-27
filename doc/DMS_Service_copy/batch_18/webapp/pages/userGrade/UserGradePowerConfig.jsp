<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<frameset cols="250,*" frameborder="no" border="0" framespacing="0" rows="*">
	<frame name="companyTreeLeft" src="${ctx }/saaUserGrade/initUserGradeList.do?userCode=<%=request.getParameter("userCode")%>">
	<frame name="companyTreeRight" src="">	
</frameset>
</html>
