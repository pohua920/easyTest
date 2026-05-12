<%--
****************************************************************************
* DESC       ：实体资料归档调阅查询页面
* AUTHOR     ： 理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
<head>
    <title><s:text name="title.archive.readQueryEntityDataArchiving"/></title><!-- 实体资料归档调阅查询 -->
    <%-- 页面样式  --%>
    <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
    <script language='javascript'>
    function submitForm() {
    	var editType = fm.editType.value;
    	if ("extension" == editType || "retrival" == editType || "toarchive" == editType || "audit" == editType) {
    		if (trim(fm.claimNo.value) == "") {
    			alert("賠案號碼不能為空！");
    			return false;
    		}
    	}
    	fm.submit();
    }
    </script>
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/archive/archiveQuery.do" method="post">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:if test="#request.editType=='archiveBefore'">
						<s:text name="archive.undocumentedEntityDataQuery" />
						<!-- 未归档实体资料查询 -->
					</s:if>
					<s:elseif test="#request.editType=='audit'">
						<s:text name="archive.dataReadCheckedTaskQuery" />
						<!-- 资料调阅审核任务查询 -->
					</s:elseif>
					<s:elseif test="#request.editType=='extension' || #request.editType=='retrival' || #request.editType=='toarchive'">
						<s:text name="archive.inputClaimNumber" />
						<!-- 输入赔案号 -->
					</s:elseif>
				</td>
			</tr>
			<tr>
				<td class="title2" align="center">
					<s:text name="check.claimNum" />
					:
				</td>
				<!-- 赔案号： -->
				<td class="input2">
					<input type=text name="claimNo" class="common">
				</td>
			</tr>
			<tr>
				<td class="button" colspan=2 align="center">
					<s:if test="#request.editType=='extension' || #request.editType=='retrival' || #request.editType=='toarchive'">
						<input type=button class="button" class="button" value="<s:text name="button.next.value"/>" onclick="submitForm();">
						<!-- 下一步 -->
					</s:if>
					<s:else>
						<input type=button class="button" class="button" value="<s:text name="prompt.query"/>" onclick="submitForm();">
						<!-- 查询 -->
					</s:else>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="${param.editType}">
	</form>
</body>
</html>