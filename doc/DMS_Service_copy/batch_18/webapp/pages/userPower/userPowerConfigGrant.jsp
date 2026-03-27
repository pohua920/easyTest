<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<frameset  rows="90,*" frameborder="no" border="0" framespacing="0"
	cols="*">
	<frame name="content" src="${ctx}/saaUserPower/showUserInfo.do?userCode=<%=request.getAttribute("userCode")%>">
	<frame name="content" src="${ctx}/saaUserPower/userPowerAllConfig.do?userCode=<%=request.getAttribute("userCode")%>">
</frameset>
</html>

