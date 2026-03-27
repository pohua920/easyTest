<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<frameset  cols="330,*" frameborder="no" border="0" framespacing="0"
	rows="*">
	<frame name="prpDtypeList" src="${ctx}/dictionary/prepareQueryPrpDtype.do">
	<frame name="prpDcodeRight" src="${ctx}/pages/dictionary/prpDtype/mainInitPage.jsp">
</frameset>
</html>
