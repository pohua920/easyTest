<%--
****************************************************************************
* DESC       ：代理人理赔维护操作界面
* AUTHOR     ： 理赔组 陈杰
* CREATEDATE ： 2013-03-08
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.query.agentClaimsMaintainQueryPage" /></title>
<%--代理人理赔维护查询页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onLoad="initPage();">
	<form name="fm" action="${ctx}/AgentMobile.do" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="query.queryAgentClaimsMaintainQueryPage" />
					<%--查询代理人理赔维护信息 --%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpDagent.agentCode" />
					:
				</td>
				<%--代理人代码 --%>
				<td class='input'>
					<input name="AgentCode" class="codecode" description="代理人名称" value="" ondblclick="code_CodeSelect(this, 'AgentCode','0,1','Y');" onchange="code_CodeSelect(this, 'AgentCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'AgentCode','0,1','Y');">
				</td>
				<td class='title'>
					<s:text name="db.prpDagent.agentName" />
					:
				</td>
				<%--代理人名称 --%>
				<td class='input'>
					<input name="AgentName" class="codename" description="代理人名称" value="" ondblclick="code_CodeSelect(this, 'AgentCode','-1,0','Y');" onchange="code_CodeSelect(this, 'AgentCode','-1,0','Y');"
						onkeyup="code_CodeSelect(this, 'AgentCode','-1,0','Y');">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="2">
					<input type="button" class='button' value="<s:text name='button.query.value' />" onclick="return query();">
				</td>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name="task.add" />" onClick="return insertMethod();">
					<%--增加 --%>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<iframe name=QueryResultFrame src='about:blank' style='Z-INDEX: 1; WIDTH: 100%; HEIGHT: 410' marginwidth='0' marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='AUTO'> </iframe>
				</td>
			</tr>
		</table>
	</form>
</body>
<script language="javascript">
	function insertMethod() {
		fm.action = "${ctx}/pages/common/query/AgentEdit.jsp?editType=insert";
		fm.target = "QueryResultFrame";
		fm.submit();
		return true;
	}

	function query() {
		fm.action = "${ctx}/pages/common/query/AgentResult.jsp?editType=select";
		fm.target = "QueryResultFrame";
		fm.submit();
		return true;
	}
</script>
</html>