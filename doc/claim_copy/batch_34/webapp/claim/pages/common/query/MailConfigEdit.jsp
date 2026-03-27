<%--
****************************************************************************
* DESC       ：邮箱配制维护
* AUTHOR     ： zhyi fubon-2525 20110928
* CREATEDATE ： 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.fubon.mail.schema.MailConfigSchema"%>
<html locale="true">
<head>
<title><s:text name="title.query.emailConfigurationMaintenanceEntryPage" /></title>
<%--邮箱配制维护录入页面 --%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script language="javascript">
	//邮箱校验
	function checkEmail(Field) {
		var strValue = Field.value;
		var patrn = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!patrn.exec(strValue)) {
			alert('提示\n\n请输入有效的E_mail！');
			Field.value = "";
			return false;
		}
	}

	function submitFormToSave() {
		var email = fm.email.value;
		if (email == "") {
			alert("邮箱地址不能为空！");
			fm.email.focus();
			return false;
		}
		if (fm.comCode.value == "" || fm.comCname.value == "") {
			alert("机构信息不能为空");
			fm.comCode.focus();
			return false;
		}
		fm.submit();
	}
</script>
</head>
<%
	MailConfigSchema mailConfigSchema = null;
	String comCode = "";
	String comCname = "";
	String userCode = "";
	String userName = "";
	String email = "";
	String validStatus = "";
	String node = "";
	String remark = "";
	String mailConfig_sequence = "";
	if ("update".equals(request.getParameter("editType"))) {
		if (request.getAttribute("mailConfigSchema") != null) {
			mailConfigSchema = (MailConfigSchema) request.getAttribute("mailConfigSchema");
			comCode = mailConfigSchema.getComCode();
			comCname = mailConfigSchema.getComCName();
			if (mailConfigSchema.getUserCode() != null) {
				userCode = mailConfigSchema.getUserCode();
				userName = mailConfigSchema.getUserName();
			}
			email = mailConfigSchema.getEmail();
			validStatus = mailConfigSchema.getValidStatus();
			mailConfig_sequence = mailConfigSchema.getMailConfig_sequence();
			node = mailConfigSchema.getNode();
			if (mailConfigSchema.getRemark() != null) {
				remark = mailConfigSchema.getRemark();
			}
		}
	}
%>
<body>
	<form name="fm" action="/claim/mailConfig.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<%
				if ("insert".equals(request.getParameter("editType"))) {
			%>
			<tr>
				<td class="formtitle" colspan="6">
					<s:text name="query.addEmailAddress" />
					<%--增加邮箱地址 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 11%">
					<s:text name="query.organization" />
					<%--组织机构 --%>
					：
				</td>
				<td class="input">
					<input type=text class="codecode" name="comCode" num=-1 style="width: 20%" title="具體單位" ondblclick="dbclickComCodeByProvinceCode1(this,'dbclick','0,1','Y','50','Check');"
						onkeyup="dbclickComCodeByProvinceCode1(this,'keyup','0,1','Y','50','Check');" onchange="dbclickComCodeByProvinceCode1(this,'change','0,1','Y','50','Check');">
					<input type=text class="codecode" name="comCname" title="具體單位" style="width: 50%" ondblclick="dbclickComCodeByProvinceCode1(this,'dbclick','-1,0','N','50');"
						onkeyup="dbclickComCodeByProvinceCode1(this,'keyup','-1,0','N','50');" onchange="dbclickComCodeByProvinceCode1(this,'change','-1,0','N','50');">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class="title" style="width: 11%">
					<s:text name="check.personnel" />
					<%--人员 --%>
					：
				</td>
				<td class="input" colspan=3>
					<input type=text name="userCode" class="codecode" style="width: 20%" maxlength="10" title="操作員" ondblclick="dbclickCheckPerson1(this,'dbclick','0,1','Y');"
						onkeyup="dbclickCheckPerson1(this,'keyup','0,1','Y');">
					<input type=text name="userName" class="codecode" style="width: 50%" title="操作員" ondblclick="dbclickCheckPerson1(this,'dbclick','-1,0','N');"
						onkeyup="dbclickCheckPerson1(this,'keyup','-1,0','N');">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="query.emailAddress" />
					：
					<%--邮箱地址 --%>
				</td>
				<td class='input'>
					<input type="text" name="email" value="" class="input" style="width: 72%" onblur="checkEmail(this)">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td style="width: 5%">
					<s:text name="query.sendNode" />
					<%--发送节点 --%>
					：
				</td>
				<td style="width: 10%">
					<select name="node">
						<option value="all" selected>
							--
							<s:text name="query.all" />
							<%----全部-- --%>
						</option>
						<option value="regis">
							<s:text name="check.report" />
							<%--报案 --%>
						</option>
						<option value="claim">
							<s:text name="check.record" />
							<%--立案 --%>
						</option>
						<option value="sched">
							<s:text name="schedule.scheduling" />
							<%--调度 --%>
						</option>
						<option value="check">
							<s:text name="check.mentHereunde" />
							<%--查勘 --%>
						</option>
						<option value="certa">
							<s:text name="compensate.fee" />
							<%--定损 --%>
						</option>
						<option value="verif">
							<s:text name="query.hesun" />
							<%--核损 --%>
						</option>
						<option value="certi">
							<s:text name="query.documents" />
							<%--单证 --%>
							-->
						</option>
						<option value="compe">
							<s:text name="query.adjustments" />
							<%--理算 --%>
						</option>
						<option value="compp">
							<s:text name="check.calculation" />
							<%--计算书 --%>
						</option>
						<option value="veric">
							<s:text name="query.hepei" />
							<%--核赔 --%>
						</option>
						<option value="endca">
							<s:text name="claim.endCase" />
							<%--结案 --%>
						</option>
						<option value="other">
							<s:text name="check.other" />
							<%--其它 --%>
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<s:text name="query.ifUsefull" />
					<%--是否有效 --%>
					：
				</td>
				<td>
					<s:text name="query.flagTrue" />
					<%--有效 --%>
					<input type="radio" name="validStatus" value="1" checked="checked" />
					<s:text name="query.flagFalse" />
					<%--无效 --%>
					<input type="radio" name="validStatus" value="0" />
				</td>
			</tr>
			<tr>
				<td>
					<s:text name="db.prpDcompany.remark" />
					<%--备注 --%>
					：
				</td>
				<td colspan="3">
					<input type="input" class="input" name="remark" />
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name="button.save.value" />" onClick="submitFormToSave();">
					<%--保存 --%>
					<input type="hidden" name="editType" value="insert">
				</td>
			</tr>
			<%
				} else {
			%>
			<tr>
				<td class="formtitle" colspan="6">
					<s:text name="query.modifyEmailAddress" />
					<%--修改邮箱地址 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 11%">
					<s:text name="query.organization" />
					<%--组织机构 --%>
					：
				</td>
				<td class="input">
					<input type=text class="codecode" name="comCode" num=-1 style="width: 20%" title="具體單位" value="<%=comCode%>" ondblclick="dbclickComCodeByProvinceCode1(this,'dbclick','0,1','Y','50','Check');"
						onkeyup="dbclickComCodeByProvinceCode1(this,'keyup','0,1','Y','50','Check');" onchange="dbclickComCodeByProvinceCode1(this,'change','0,1','Y','50','Check');">
					<input type=text class="codecode" name="comCname" title="具體單位" style="width: 50%" value="<%=comCname%>" ondblclick="dbclickComCodeByProvinceCode1(this,'dbclick','-1,0','N','50');"
						onkeyup="dbclickComCodeByProvinceCode1(this,'keyup','-1,0','N','50');" onchange="dbclickComCodeByProvinceCode1(this,'change','-1,0','N','50');">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class="title" style="width: 11%">
					<s:text name="check.personnel" />
					<%--人员 --%>
					：
				</td>
				<td class="input" colspan=3>
					<input type=text name="userCode" class="codecode" style="width: 20%" maxlength="10" title="操作員" value="<%=userCode%>" ondblclick="dbclickCheckPerson1(this,'dbclick','0,1','Y');"
						onkeyup="dbclickCheckPerson1(this,'keyup','0,1','Y');">
					<input type=text name="userName" class="codecode" style="width: 50%" title="操作員" value="<%=userName%>" ondblclick="dbclickCheckPerson1(this,'dbclick','-1,0','N');"
						onkeyup="dbclickCheckPerson1(this,'keyup','-1,0','N');">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="query.emailAddress" />
					<%--邮箱地址 --%>
					：
				</td>
				<td class='input'>
					<input type="text" name="email" value="<%=email%>" class="input" style="width: 72%" onkeypress="return pressNumber(event);" onblur="return checkMail(this);">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td style="width: 5%">
					<s:text name="query.sendNode" />
					<%--发送节点 --%>
					：
				</td>
				<td style="width: 10%">
					<select name="node">
						<option value="all" <%=(node.equals("all") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%----全部-- --%>
						</option>
						<option value="regis" <%=(node.equals("regis") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--报案 --%>
						</option>
						<option value="claim" <%=(node.equals("claim") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--立案 --%>
						</option>
						<option value="sched" <%=(node.equals("sched") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--调度 --%>
						</option>
						<option value="check" <%=(node.equals("check") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--查勘 --%>
						</option>
						<option value="certa" <%=(node.equals("certa") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--定损 --%>
						</option>
						<option value="verif" <%=(node.equals("verif") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--核损 --%>
						</option>
						<option value="certi" <%=(node.equals("certi") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--单证 --%>
							-->
						</option>
						<option value="compe" <%=(node.equals("compe") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--理算 --%>
						</option>
						<option value="compp" <%=(node.equals("compp") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--计算书 --%>
						</option>
						<option value="veric" <%=(node.equals("veric") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--核赔 --%>
						</option>
						<option value="endca" <%=(node.equals("endca") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--结案 --%>
						</option>
						<option value="other" <%=(node.equals("other") ? "selected" : "")%>>
							<s:text name="button.save.value" />
							<%--其它 --%>
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<s:text name="query.ifUsefull" />
					<%--是否有效 --%>
					：
				</td>
				<td>
					<%
						if (validStatus.equals("1")) {
					%>
					<s:text name="query.flagTrue" />
					<%--有效 --%>
					<input type="radio" name="validStatus" checked="checked" value="1">
					<s:text name="query.flagFalse" />
					<%--无效 --%>
					<input type="radio" name="validStatus" value="0" />
					<%
						} else {
					%>
					<s:text name="query.flagTrue" />
					<%--有效 --%>
					<input type="radio" name="validStatus" value="1">
					<s:text name="query.flagFalse" />
					<%--无效 --%>
					<input type="radio" name="validStatus" checked="checked" value="0" />
					<%
						}
					%>
				</td>
			</tr>
			<tr>
				<td>
					<s:text name="db.prpDcompany.remark" />
					<%--备注 --%>
					：
				</td>
				<td colspan="3">
					<input type="input" class="input" name="remark" value="<%=remark%>" />
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name="button.save.value" />" onClick="return submitFormToSave();">
					<%--保存 --%>
					<input type="hidden" name="editType" value="updateSave">
					<input type="hidden" name="mailConfig_sequence" value="<%=mailConfig_sequence%>">
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</form>
</body>
</html>