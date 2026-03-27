<%--
****************************************************************************
* DESC       ：查询机构内非车查勘、立案、理算、结案权限需调派人员界面
* AUTHOR     ：罗畅
* CREATEDATE ： 2010-06-08
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------

****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<title>需调派人员选择</title>
<%-- 公用函数 --%>
<script src="/claim/common/js/Common.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<script language="javascript">
	function submitForm() {
		var choosed = false;
		for (i = 0; i < fm.all("chooseflag").length; i++) {
			if (fm.chooseflag[i].checked) {
				choosed = true;
				if ((fm.nodeName[i].value == "查勘" || fm.nodeName[i].value == "立案")
						&& fm.ProxyToPersonCheckClaim.value != "") {
					fm.chooseflag[i].value = i + " "
							+ fm.ProxyToPersonCheckClaim.value;
				} else if ((fm.nodeName[i].value == "单证" || fm.nodeName[i].value == "单证收集")
						&& fm.ProxyToPersonCerti.value != "") {
					fm.chooseflag[i].value = i + " "
							+ fm.ProxyToPersonCerti.value;
				} else if ((fm.nodeName[i].value == "理算" || fm.nodeName[i].value == "计算书")
						&& fm.ProxyToPersonCompensate.value != "") {
					fm.chooseflag[i].value = i + " "
							+ fm.ProxyToPersonCompensate.value;
				} else if (fm.nodeName[i].value == "结案"
						&& fm.ProxyToPersonEndca.value != "") {
					fm.chooseflag[i].value = i + " "
							+ fm.ProxyToPersonEndca.value;
				} else {
					if (fm.nodeName[i].value == "计算书") {
						var nodeName = "理算";
					} else if (fm.nodeName[i].value == "查勘"
							|| fm.nodeName[i].value == "立案") {
						var nodeName = "查勘或立案";
					} else {
						var nodeName = fm.nodeName[i].value;
					}
					alert("您选择了需要调派的" + nodeName + "任務，请选择" + nodeName
							+ "任務调派给哪位人员！");
					return false;
				}
			}
		}
		if (!choosed) {
			alert("请选择需要调派的赔案！");
			return false;
		}
		fm.submit();//提交
	}

	function selectAllOne() {
		for (i = 0; i < fm.all("chooseflag").length; i++) {
			fm.chooseflag[i].checked = true;
		}
	}

	function selectNoOne() {
		for (i = 0; i < fm.all("chooseflag").length; i++) {
			fm.chooseflag[i].checked = false;
		}
	}
</script>
<body>
	<form name="fm" action="/claim/Proxy.do?actionType=ToPerson" method="post">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="8" class="formtitle">在处理赔案列表</td>
			</tr>
			<tr class=common>
				<td class="centertitle"></td>
				<td class="centertitle">报案号</td>
				<td class="centertitle">被保险人名称</td>
				<td class="centertitle">出险时间</td>
				<td class="centertitle">报案时间</td>
				<td class="centertitle">估损金额</td>
				<td class="centertitle">当前节点</td>
				<td class="centertitle">节点进入时间</td>
			</tr>
			<logic:notEmpty name="ProxyViewClaimDtoList">
				<logic:iterate id="ProxyViewClaimDto" name="ProxyViewClaimDtoList">
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="查勘">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
				</logic:iterate>
				<logic:iterate id="ProxyViewClaimDto" name="ProxyViewClaimDtoList">
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="立案">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
				</logic:iterate>
				<logic:iterate id="ProxyViewClaimDto" name="ProxyViewClaimDtoList">
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="单证">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="单证收集">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName1" value="单证" />
								<input class="readonly" type=hidden readonly="readonly" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
				</logic:iterate>
				<logic:iterate id="ProxyViewClaimDto" name="ProxyViewClaimDtoList">
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="理算">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="计算书">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName1" value="理算" />
								<input class="readonly" type=hidden readonly="readonly" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
				</logic:iterate>
				<logic:iterate id="ProxyViewClaimDto" name="ProxyViewClaimDtoList">
					<logic:equal name='ProxyViewClaimDto' property='nodeName' value="结案">
						<tr class=common align="center">
							<td>
								<input type="checkbox" name="chooseflag">
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 130px" name="registNo" value="<bean:write name='ProxyViewClaimDto' property='registNo'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='insuredName' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='damageStartDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='reportDate' />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='sumClaim' />
							</td>
							<td>
								<input class="readonly" readonly="readonly" style="width: 40px" name="nodeName" value="<bean:write name='ProxyViewClaimDto' property='nodeName'/>" />
							</td>
							<td>
								<bean:write name='ProxyViewClaimDto' property='flowinTime' />
							</td>
						</tr>
					</logic:equal>
				</logic:iterate>
			</logic:notEmpty>
			<tr class=common align="left">
				<td colspan="8" align="left">
					&nbsp;&nbsp;
					<input type="radio" name="selectedAll" onclick="selectAllOne();">
					全部选择 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="selectedAll" onclick="selectNoOne();">
					全部取消
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="2" class="formtitle">调派人员选择</td>
			</tr>
			<tr>
				<td width='45%' align="right">查勘、立案任務调派给：</td>
				<td width='55%'>
					<select class=tag name="ProxyToPersonCheckClaim">
						<option value="" selected>请选择</option>
						<logic:notEmpty name="CheckClaimUserDtoList">
							<logic:iterate id="PrpDuserDto" name="CheckClaimUserDtoList">
								<option value="<bean:write name='PrpDuserDto' property='userCode'/>">
									<bean:write name='PrpDuserDto' property='userName' />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</td>
			</tr>
			<tr>
				<td width='45%' align="right">单证任務调派给：</td>
				<td width='55%'>
					<select class=tag name="ProxyToPersonCerti">
						<option value="" selected>请选择</option>
						<logic:notEmpty name="CertiUserDtoList">
							<logic:iterate id="PrpDuserDto" name="CertiUserDtoList">
								<option value="<bean:write name='PrpDuserDto' property='userCode'/>">
									<bean:write name='PrpDuserDto' property='userName' />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</td>
			</tr>
			<tr>
				<td width='45%' align="right">理算任務调派给：</td>
				<td width='55%'>
					<select class=tag name="ProxyToPersonCompensate">
						<option value="" selected>请选择</option>
						<logic:notEmpty name="CompensateUserDtoList">
							<logic:iterate id="PrpDuserDto" name="CompensateUserDtoList">
								<option value="<bean:write name='PrpDuserDto' property='userCode'/>">
									<bean:write name='PrpDuserDto' property='userName' />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</td>
			</tr>
			<tr>
				<td width='45%' align="right">结案任務调派给：</td>
				<td width='55%'>
					<select class=tag name="ProxyToPersonEndca">
						<option value="" selected>请选择</option>
						<logic:notEmpty name="EndcaUserDtoList">
							<logic:iterate id="PrpDuserDto" name="EndcaUserDtoList">
								<option value="<bean:write name='PrpDuserDto' property='userCode'/>">
									<bean:write name='PrpDuserDto' property='userName' />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' align="center" colspan="2">
					<input id="text" type=hidden name="ProxyFromPerson" value="<bean:write name='ProxyFromPerson'/>">
					<input id="button" type=button class='bigbutton' value="调派赔案" onClick="submitForm();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
