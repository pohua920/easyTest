<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<body>
<s:form name="fm" action="">
	<div id="div"
		style="MARGIN: 2pt; OVERFLOW: scroll; width: 100%; height: 700px;">
	
	<table class="fix_table">
		<tr>
			<td align="center">
			<h2>岗位选择</h2>
			</td>
		</tr>
	<c:set var="index" value="0" />
		<s:iterator value="userGrades" status="stuts">
		<s:if test="%{userGrades[#stuts.index].checked == true }">
				<td>
				<div style="display: none">${index+1 }</div>
				<a href="${ctx }/saaUserGrade/viewAgentUserGrade.do?saaGradeID=<s:property
					value='%{userGrades[#stuts.index].gradeCode}' />&userCode=<%=request.getParameter("userCode")%>"
					target="companyTreeRight"><s:property
					value='${index+1}' />-<s:property
					value="%{userGrades[#stuts.index].gradeName}" /></a></td>
				<s:hidden name="gradeCode" value="%{userGrades[#stuts.index].id}" />
			</tr>
			<c:set var="index" value="${index+1 }" />
		</s:if>		
		</s:iterator>
	</table>
	</div>
</s:form>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
