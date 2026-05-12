<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-03 
* MODIFYLIST ：   Name       Date            Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
****************************************************************************
--%> 
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
function showRegistInfo() {
	var win;
	var registShowDo = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + fm.prpLcheckRegistNo.value + "&editType=SHOW&riskCode=" + fm.prpLcheckRiskCode.value
	var newWindow = window.open(registShowDo, "DAARegistEdit", 'width=640,height=300,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	newWindow.focus();
}

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relatePolicy() {
	var policyNo = fm.prpLcheckPolicyNo.value;
	var linkURL = "http://192.168.60.12:8101/ddccallweb/DAA/tbcbpg/UIPrPoEnDAAShow.jsp?" + "BIZTYPE=POLICY" + "&BizNo=" + policyNo + "&SHOWTYPE=SHOW";
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否 则返回false
 */

function relateRegist() {
	var registNo = fm.prpLcheckRegistNo.value;
	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW&riskCode=" + fm.riskcode.value;
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}


/**
 *@description 设置画面的初始值
 *@param       无
 *@return      通过返回true,否则返回false
 */

function loadCheckExt() { <%
		ArrayList checkExtArray = null;
	if (((PrpLcheckExt) request.getAttribute("prpLcheckExt")).getPrpLcheckExtList() != null) {
		checkExtArray = (ArrayList)((PrpLcheckExt) request.getAttribute("prpLcheckExt")).getPrpLcheckExtList();
		int intValue = -1;
		int indexCheckExt = 0;
		for (indexCheckExt = 0; indexCheckExt < checkExtArray.size(); indexCheckExt++) {
			PrpLcheckExt prpLcheckExtDto1 = new PrpLcheckExt();
			prpLcheckExtDto1 = (PrpLcheckExt) checkExtArray.get(indexCheckExt);

			intValue = -1;
			if (prpLcheckExtDto1.getColumnValue().trim().equals("1"))
				intValue = 0;
			else if (prpLcheckExtDto1.getColumnValue().trim().equals("0"))
				intValue = 1;
			//查勘扩展信息用颜色间隔区分，增加一个不确定选项
			else if (prpLcheckExtDto1.getColumnValue().trim().equals("2"))
				intValue = 2;
			if (intValue > -1) { %>
					fm. <%= prpLcheckExtDto1.getColumnName() %> ['<%=intValue%>'].checked = true; <%
			}
		}
	} %>
}
</script>
<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
	<tr>
		<td class="title" colspan="4">
			查勘登记
			<input type="hidden" name="referKind" value="${prpLcheck.referKind}">
			<input type="hidden" name="prpLcheckRiskCode" value="${prpLcheck.riskCode}">
			<input type="hidden" name="prpLcheckCheckUnitName" value="${prpLcheck.checkUnitName}">
			<input type="hidden" name="prpLcheckFlag" value="${prpLcheck.flag}">
			<input type="hidden" name="prpLcheckReferSerialNo" value="${prpLcheck.referSerialNo}">
			<input type="hidden" name="prpLcheckInsureCarFlag" value="${prpLcheck.insureCarFlag}">
			<input type="hidden" name="swfLogFlowID" class="common" value="${swfLogFlowID}">
			<input type="hidden" name="swfLogLogNo" class="common" value="${swfLogLogNo}">
			<input type="hidden" name="checkNature" value="${prpLcheck.checkNature}">
			<input type="hidden" name="claimType" value="${prpLcheck.claimType}">
			<input type="hidden" name="damageAddressType" value="${prpLcheck.damageAddressType}">
			<input type="hidden" name="prpLcheckPolicyNo" class="readonly" readonly="true" style="width: 140px" value="${prpLcheck.policyNo}">
			<input type="hidden" name="prpLcheckClaimNo" class="readonly" readonly="true" value="${prpLcheck.claimNo}">
			<input type="hidden" name="prpLcheckRegistNo" class="readonly" readonly="true" style="width: 140px" value="${prpLcheck.registNo}">
			<input type="hidden" name="prpLcheckCheckDate" class="input" style="width: 140px" value="${prpLcheck.checkDate}">
			<input type="hidden" name="prpLcheckCheckSite" class="input" style="width: 85%" value="${prpLcheck.checkSite}">
			<input type="hidden" name="prpLcheckDamageStartDate" class="readonly" readonly="true" maxlength="10"
				value="${prpLcheck.damageStartDate} 日 ${prpLcheck.damageStartHour} 時 ${prpLcheck.damageStartMinute} 分">
			<input type="hidden" name="prpLcheckDamageAddress" class="readonly" readonly="true" style="width: 140px" value="${prpLcheck.damageAddress}">
			<input type="hidden" name='prpLcheckChecker1' class='input' maxlength=20 style="width: 140px" description="查勘人1" value="${prpLcheck.checker1}">
			<input type="hidden" name='prpLcheckChecker2' class='input' maxlength=20 style="width: 140px" description="查勘人2" value="${prpLcheck.checker2}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.firstSiteFlag" />
			：
		</td>
		<!--是否第一现场-->
		<td class="input">
			<html:radio name="prpLcheckDto" property="firstSiteFlag" value="0" />
			<s:text name="certainLoss.prpLscheduleMainWF.no" />
			<!--否-->
			<html:radio name="prpLcheckDto" property="firstSiteFlag" value="1" />
			<s:text name="certainLoss.prpLscheduleMainWF.yes" />
			<!--是-->
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckCheckType" />
			：
		</td>
		<!--查勘类型-->
		<td class="input">
			<html:select name="prpLcheckDto" property="checkType">
				<html:option value="L">
					<s:text name="check.mentHereunde" />
				</html:option>
				<!--查勘-->
				<html:option value="D">
					<s:text name="check.generSurvey" />
				</html:option>
				<!--代查勘-->
				<html:option value="B">
					<s:text name="check.bySurvey" />
				</html:option>
				<!--被查勘-->
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageCase" />
			：
		</td>
		<!--出险原因-->
		<td class="input">
			<input name="prpLcheckDamageCode" class="codecode" style="width: 40px" maxlength=3 description="出险原因" value="${prpLcheck.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode');"
				onchange="code_CodeChange(this,'DamageCode');" onkeyup="code_CodeSelect(this, 'DamageCode');">
			<input name="prpLcheckDamageName" class="codename" style="width: 110px" maxlength=20 description="出险原因" value="${prpLcheck.damageName}"
				ondblclick="code_CodeSelect(this, 'DamageCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DamageCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'DamageCode','-1','name','none','post');">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" style="valign: bottom">
			<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCase" />
			：
		</td>
		<!--事故原因-->
		<td class="input" style="valign: middle">
			<input name="prpLcheckDamageTypeCode" class="codecode" style="width: 40px" maxlength=3 description="事故原因" value="${prpLcheck.damageTypeCode}" ondblclick="code_CodeSelect(this, 'DamageTypeCode');"
				onchange="code_CodeChange(this,'DamageTypeCode');" onkeyup="code_CodeSelect(this, 'DamageTypeCode');">
			<input name="prpLcheckDamageTypeName" class="codename" style="width: 110px" maxlength=20 description="事故原因" value="${prpLcheck.damageTypeName}"
				ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DamageTypeCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1','name','none','post');">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="valign: middle">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageArea" />
			：
		</td>
		<!--出险区域-->
		<td class="input" style="valign: middle">
			<input name="prpLcheckDamageAreaCode" class="codecode" style="width: 40px" description="出险網域" value="${prpLcheck.damageAreaCode}" ondblclick="code_CodeSelect(this, 'DamageAreaCode');"
				onchange="code_CodeChange(this,'DamageAreaCode');" onkeyup="code_CodeSelect(this, 'DamageAreaCode');">
			<input name="prpLcheckDamageAreaName" class="codename" style="width: 110px" description="出险網域" value="${prpLcheck.damageAreaName}"
				ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DamageAreaCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1','name','none','post');">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" style="valign: middle">
			<s:text name="certainLoss.prpLcheck.lossItemName" />
			：
		</td>
		<!--车牌号码-->
		<td class="input" style="valign: middle">
			<input type="text" name="lossItemName" class="readonly" readonly="true" style="width: 90%" value="<%=request.getParameter("lossItemName")%>">
			<input type="hidden" name="lossItemCode" class="readonly" readonly="true" value="<%=request.getParameter("lossItemCode")%>">
		</td>
	<tr>
		<td class="title">
			<s:text name="certainLoss.accidentLiability" />
			：
		</td>
		<!-事故赔偿责任--->
		<td class="input">
			<s:select onchange="changeIndemnityDuty();" list="#request.indemnityDutys" name="indemnityDuty" listKey="id.codeCode" listValue="codeCName" value="#request.prpLcheck.indemnityDuty"></s:select>
		</td>
		<td class="title" style="valign: bottom">
			<s:text name="db.prpLcheck.claimFlag" />
			：
		</td>
		<!--是否属於保险责任-->
		<td class="input" style="valign: middle">
			<html:radio name="prpLcheckDto" property="claimFlag" value="1" />
			<s:text name="regist.prpLregist.yes" />
			<!--是-->
			<html:radio name="prpLcheckDto" property="claimFlag" value="2" />
			<s:text name="check.no" />
			<!--不是-->
			<html:radio name="prpLcheckDto" property="claimFlag" value="3" />
			<s:text name="check.unableDet" />
			<!--无法确定-->
		</td>
	</tr>
	<%--modify by wangli add start 20050407--%>
	<tr>
		<td class="title">
			<s:text name="check.proDepartment" />
			：
		</td>
		<!--处理部门-->
		<td class="input" style="width: 85%" colspan="3">
			<input type="input" name="prpLcheckHandleUnit" class="codecode" style="width: 40%" description="处理部门代码" value="${prpLcheck.handleUnit}" ondblclick="code_CodeSelect(this, 'ComCode');"
				onchange="code_CodeChange(this,'ComCode');" onkeyup="code_CodeSelect(this, 'ComCode');">
			<input name='prpLcheckHandleUnitName' class='codename' maxlength=60 style="width: 45%" description="处理部门" value="${prpLcheck.handleUnitName}"
				ondblclick="code_CodeSelect(this, 'ComCode','-1','name','none','post');" onchange="code_CodeChange(this, 'ComCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'ComCode','-1','name','none','post');">
			<img src="/claim/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.linkerName" />
			:
		</td>
		<td class="input">
			<input type=text name="prpLregistLinkerName" title="聯系人" class="input" value="${prpLregist.linkerName}" />
		</td>
		<td class="title">
			<s:text name="db.prpLregist.phoneNumber" />
			:
		</td>
		<td class="input">
			<input type=text name="prpLregistPhoneNumber" title="聯系電話" class="input" value="${prpLregist.phoneNumber}">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLregistInsuredAddress" />
			:
		</td>
		<!--联系人地址-->
		<td class="input">
			<input type=text name="prpLregistInsuredAddress" class="input" value="${prpLregist.insuredAddress}">
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLregistDamageAreaPostCode" />
			:
		</td>
		<!--联系人邮编-->
		<td class="input">
			<input type=text name="prpLregistDamageAreaPostCode" class="input" maxlength=6 description="出险地邮政编码" value="${prpLregist.damageAreaPostCode}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckRemark" />
			：
		</td>
		<!--备注-->
		<td class="input" colspan="3">
			<input name='prpLcheckRemark' class='input' maxlength=80 value="${prpLcheck.remark}">
		</td>
	</tr>
</table>
