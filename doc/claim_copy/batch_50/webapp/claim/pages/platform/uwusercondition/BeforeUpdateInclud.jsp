<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<HTML xmlns:mpc>
<HEAD>
<TITLE></TITLE>
	<jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
	<link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
	<jsp:include page="/pages/platform/behaviors/MpcStyle.jsp"/>
</HEAD>
<BODY BGCOLOR="#D7E1F6" style="scroll: no; overflow: hidden;">
	<form name="fm" action="" method="POST">
		<br>
		<table border="0" cellpadding="2" cellspacing="1" class="newcommon" width="100%">
			<tr>
				<td colspan="3" class="top"><s:text name="uwcondition.StaffPermissions"/> - <c:out value="${UtiUwUserConditionDto.userName}"/></td><%-- 人员权限 --%>
				<input name="userCode" type="hidden" value='<c:out value="${UtiUwUserConditionDto.userCode}"/>'>
				<input name="userName" type="hidden" value='<c:out value="${UtiUwUserConditionDto.userName}"/>'>
				<input name="nodeNo" type="hidden" value='<c:out value="${UtiUwUserConditionDto.nodeNo}"/>'>
			</tr>
			<tr> 
				<td class="top" width="40%"><s:text name="uwcondition.AuditDepartment"/></td><%-- 审核部门 --%>
				<td class="top" width="50%"><s:text name="regist.prpLregist.riskCodeName"/></td> <%-- 险种 --%>
				<td class="top" width="10%"><%--人员权限--%></td>
			</tr>
		<c:forEach items="${riskCodeList}" var="utiUwLevelDto" varStatus="stat">
			<tr>
				<td width="40%" class="page">
					<input name="comCode" type="text" class="codecode" readonly  value='<c:out value="${utiUwLevelDto.comCode}"/>' style="width:80px;">
					<input name="ComName" type="text" class="codename" readonly  value='<c:out value="${utiUwLevelDto.comName}"/>' style="width:140px;">
				</td>
				<td width="50%" class="page">
					<input name="riskCode" type="text" class="codecode" readonly  value='<c:out value="${utiUwLevelDto.riskCode}"/>' style="width:80px;">
					<input name="riskName" type="text" class="codename" readonly  value='<c:out value="${utiUwLevelDto.riskName}"/>' style="width:160px;">
				</td>
				<td width="10%" class="page">
					<%--<img src="/claim/platform/images/btnModifyMenu.gif" style="cursor:hand;" border="0"
					     onclick="func2(<%=x%>);">&nbsp;
					<logic:equal name="utiUwLevelDto" property="flag" value="1">
						<img src="/claim/platform/images/btnDeleteMenu.gif" style="cursor:hand;" border="0"
						   onclick="func3(<%=x%>);">
					</logic:equal>--%>
				</td>
			</tr>
		 </c:forEach>
		 <tfoot>
		    <tr>
				<td colspan="3" align="center">
		            <input type="button" class="button" value="<s:text name='button.return.value'/>" onclick="history.back(-1)"><%-- 返  回 --%>
		        </td>
		    </tr>
		 </tfoot>
		</table>
		<script language="javascript">
			function func(){
				if(fm.userCode[fm.userCode.length-1].value != ""){
					fm.startDate[fm.startDate.length-1].value = fm.utiuwlevelStartDate.value;
					fm.endDate[fm.endDate.length-1].value = fm.utiuwlevelEndDate.value;
				}
			}
			function func2(x){
				fm.action = "/claim/processUwUserCondition.do?actionType=prepareUpdate&index=" + x ;
				fm.submit();
			}
		
			function func3(x){
				var userName = fm.userName[x+1].value;
				var actionType1 = fm.actionType1.value;
				if(confirm("確實要刪除 " + userName + " 的權限嗎？")){
					fm.action = "/claim/processUwUserCondition.do?actionType=delete&index=" + x +
						          "&actionType1=" + actionType1;
					fm.submit();
				}
			}
		</script>
</BODY>
</HTML>