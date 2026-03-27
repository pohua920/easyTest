<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	</head>
	<script type="text/javascript">
	function closePages(type){
		var newUrl="";
		var oldUrl="";
		var rUrl = "";
		if(type != "" && type != null){
			if(type=="new"){
				newUrl = document.getElementById("newUrl").value;
				rUrl = contextRootPath+newUrl;
			}else{
				oldUrl = document.getElementById("oldUrl").value;
				rUrl = contextRootPath+oldUrl;
			}
		}
		document.getElementById("fmAction").action=rUrl;
		fm.submit();
	}
	</script>
	<body>
		<br>
		<br>
		<form method="post" name="fm" id="fmAction">
			<input type="hidden" id="operate" value="${operate}" />
			<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
				<tr>
					<td class=formtitle colspan="2">
						<s:text name="prompt.system.title"/><%--系统提示 --%>
					</td>
				</tr>
				<tr>
					<td class="common">
						<img src="/claim/images/bgClaimSuccess.gif" />
					</td>
					<td class="common">
						<c:forEach items="${actionMessages}" var="msg">
							<span><c:out value="${pageScope.msg}"/></span><br>
						</c:forEach>
						<c:if test="${not empty user.userMessage}">
							${user.userMessage}
						</c:if>
					 	<%
						 UserDto user = (UserDto) session.getAttribute("user");
						 user.setUserMessage("");
						%>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="common" align="center">
						<c:if test="${editType=='store'}">
							<input type="hidden" id="oldUrl" value="${oldUrl}" />
							<input type="button" class="btn1" value="<s:text name="common.returnModify"/>"
								onClick="closePages('old');"><%--返回修改 --%>
							<input type="hidden" id="newUrl" value="${newUrl}" />
							<c:if test="${op=='plan'}">
								<input type="button" class="btn1" value="<s:text name="common.writeNewPlan"/>"
									onClick="closePages('new');"><%--写新计划 --%>
							</c:if>
							<c:if test="${op=='contain'}">
								<input type="button" class="btn1" value="<s:text name="common.writeNewSummary"/>"
									onClick="closePages('new');"><%--写新总结 --%>
							</c:if>
						</c:if>
						<c:if test="${editType=='save'}">
							<input type="hidden" id="newUrl" value="${newUrl}" />
							<c:if test="${op=='plan'}">
								<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回 --%>"
									onClick="closePages('new');">
							</c:if>
							<c:if test="${op=='contain'}">
								<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回 --%>"
									onClick="closePages('new');">
							</c:if>
						</c:if>
						<c:if test="${operate=='addUser'}">
							<input type="hidden" id="newUrl" value="${newUrl}" />
							<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回 --%>"
								onClick="closePages('new');">
						</c:if>
						<c:if test="${operate=='query'}">
							<input type="hidden" id="newUrl" value="${newUrl}" />
							<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回 --%>"
								onClick="javascript:parent.submitDlg.hide();javascript:parent.executeQuery(1,10);">
						</c:if>
						<c:if test="${editType=='opSave'}">
							<input type="hidden" id="newUrl" value="${newUrl}" />
							<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回 --%>"
								onClick="closePages('new');">
						</c:if>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>