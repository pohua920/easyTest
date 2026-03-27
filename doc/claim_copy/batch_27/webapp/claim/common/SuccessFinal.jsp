<html>
<head>
<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%> 
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
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
			// rUrl=contextRootPath+"/depart/prepareUpdate.do?plDepartNo=PDE00000000000002328";
		}
	}
	//fm.aciton = "";
	document.getElementById("fmAction").action=rUrl;
	//alert(fm.aciton);
	fm.submit();
	
}
</script>
<body>
<br>
<br>
<form  method="post" name="fm" id="fmAction">
<input type="hidden" id="operate" value="${param.operate}"/>

<table class=common align=center>
	<tr>
		<td width="567" height="38" align="center">&nbsp;</td>
	</tr>
	<tr>
		<td width="567" height="81" align="center" valign="middle">
			<img src='${pageContext.request.contextPath}/pages/common/archive/images/success.gif' />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">${param.message}</td>
	</tr>
	<tr>
		<td height="32" colspan="2" align="center">&nbsp;</td>
	</tr>
	<tr colspan="3"  align="center">
			<td >
			<c:if test="${param.editType=='store'}">
				<input type="hidden" id="oldUrl" value="${param.oldUrl}"/>
				<input type="button" class="btn1" value="<s:text name="common.returnModify"/>" onClick="closePages('old');"><%--返回修改 --%>
				<input type="hidden" id="newUrl" value="${param.newUrl}"/>
					<c:if test="${param.op=='plan'}">
						<input type="button" class="btn1" value="<s:text name="common.writeNewPlan"/>" onClick="closePages('new');"><%--写新计划 --%>
					</c:if>
					<c:if test="${param.op=='contain'}">
						<input type="button" class="btn1" value="<s:text name="common.writeNewSummary"/>" onClick="closePages('new');"><%--写新总结 --%>
					</c:if>
			</c:if>
			<c:if test="${param.editType=='save'}">
				<input type="hidden" id="newUrl" value="${param.newUrl}"/>
					<c:if test="${param.op=='plan'}">
						<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回--%>" onClick="closePages('new');">
					--</c:if>
					<c:if test="${param.op=='contain'}">
						<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回--%>" onClick="closePages('new');">
					</c:if>
			</c:if>
			<c:if test="${param.operate=='addUser'}">
				<input type="hidden" id="newUrl" value="${param.newUrl}"/>
				<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回--%>" onClick="closePages('new');">
			</c:if>
			<c:if test="${param.operate=='query'}">
				<input type="hidden" id="newUrl" value="${param.newUrl}"/>
				<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回--%>" onClick="javascript:parent.submitDlg.hide();javascript:parent.executeQuery(1,10);">
			</c:if>
			<c:if test="${param.editType=='opSave'}">
				<input type="hidden" id="newUrl" value="${param.newUrl}"/>
				<input type="button" class="btn1" value="<s:text name="button.return.value"/><%--返 回--%>" onClick="closePages('new');">
			</c:if>
			</td>
			</td>
	</tr>
</table>
</form>
</body>
</html>

