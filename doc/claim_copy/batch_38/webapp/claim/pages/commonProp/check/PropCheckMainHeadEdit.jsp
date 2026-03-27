<%--
****************************************************************************
* DESC       :添加主信息子块界面页面
* AUTHOR     : 理赔组
* CREATEDATE : 2004-06-03 
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%--//add by wangli 20050601 reason:系统内从prpdcompany带出，系统外则从客户资料表prpdcustomer_unit中取值--%>
<script language="javascript">
	function selectHandleCodeByUnitType(field, eventType, coordinate, queryType) {
		var unitType = fm.unitType.value;
		var riskcode = fm.riskCode.value;
		if (eventType == "dbclick" || eventType == "keyup") {
			if (unitType == 1) {
				code_CodeSelect(field, 'prpdcompany', coordinate, 'Y',
						queryType,riskcode);
			} else {
				code_CodeSelect(field, 'prpdCustomerUnit', coordinate, 'Y',
						queryType);
			}
		} else if (eventType == "change") {
			if (unitType == 1) {
				code_CodeChange(field, 'prpdcompany', coordinate, 'Y',
						queryType,riskcode);
			} else {
				code_CodeChange(field, 'prpdCustomerUnit', coordinate, 'Y',
						queryType);
			}
		}
	}

	function clearHandleUnitCode() {
		fm.prpLcheckHandleUnitCode.value = "";
		fm.prpLcheckHandleUnitName.value = "";
		if (fm.unitType.value == "1") {
			fm.freightHeresyCheck.style.display = "none";
		} else {
			fm.freightHeresyCheck.style.display = "";
		}
	}
</script>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<%-- 保单号 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckPolicyNo" />
						<input type="hidden" name="referKind">
						<input type="hidden" name="prpLcheckRiskCode" value="${prpLcheck.riskCode }">
						<input type="hidden" name="prpLcheckFlag" value="${prpLcheck.flag }">
						<input type="hidden" name="prpLcheckReferSerialNo" value="${prpLcheck.id.referSerialNo }">
						<input type="hidden" name="prpLcheckInsureCarFlag" value="${prpLcheck.insureCarFlag }">
						<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID}">
						<input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo}">
						<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
						<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
						<input type="hidden" name="riskCode" value="${prpLcheck.riskCode}">
						<input type="hidden" name="riskcode" value="${prpLcheck.riskCode}"><%-- 有地方用小写的 --%>
						<input type="hidden" name="registno" value="${prpLcheck.id.registNo }">
						<input type="hidden" name="policyno" value="${prpLcheck.policyNo }">
						<input type="hidden" name="damageStartDate" value="${prpLcheck.damageStartDate}">
						<input type="hidden" name="damageStartHour" value="${prpLcheck.damageStartHour}">
						<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
						<input type=hidden name="prpLregistComCode" title="<s:text name='Ownership.institution'/>" class="ReadOnly" ReadOnly style="width: 120px" value="${prpLregist.comCode }" /><%-- 归属机构 --%>
					</td>
					<td class="right">
						<input type="text" name="prpLcheckPolicyNo" style="width: 90%" class="readonly" readonly="true" value="${prpLcheck.policyNo }">
						<br>
					</td>
					<td class="left">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" border="0" onclick="relate(fm.prpLcheckPolicyNo.value);return false;">
					</td>
					<td class="right">
						<input type="hidden" name="damageDate" value="${prpLcheck.damageStartDate}">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>"
							<%-- 出险时保单信息 --%>
				onclick="backWardPolicy(fm.coreURL.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,fm.damageDate.value);">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<%-- 报案号 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckRegistNo" />
					</td>
					<td class="right">
						<input type=text name="prpLcheckRegistNo" class="readonly" readonly="true" value="${prpLcheck.id.registNo}">
					</td>
					<td class="left">
						<%-- 赔案号 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckClaimNo" />
					</td>
					<td class="right">
						<input type="text" name="prpLcheckClaimNo" class="readonly" readonly="true" value="${prpLcheck.claimNo}">
					</td>
					<td class="left">同險號碼:
					</td>
					<td class="right">
						<input type=text name="prpCaddressSameAddressNo" class="readonly" readonly value="${prpLcheck.sameAddressNo}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<%--险种名称 --%>
						<s:text name="certainLoss.prpLcheck.riskCName" />
					</td>
					<td class="right">${riskCName}</td>
					<td class="left">
						<%--已出险次数 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/commonProp/regist/PropExistRegist.jsp"%>
					</td>
					<td class="left">
						<%--被保险人 --%>
						<s:text name="certainLoss.prpLcheck.insuredName" />
					</td>
					<td class="right">
						<input type="text" name="prpLregistLinkerName" class="readonly" readonly="true" value="${prpLcheck.insuredName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<%--出险时间 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
					</td>
					<td class="right">
						<rc:rcDate name="prpLcheckDamageStartDate" class="readonly" readonly="true" style="width:80" value="${prpLcheck.damageStartDate}"/>${prpLcheck.damageStartHour} <s:text name ="regist.prpLregist.hour"/>${prpLcheck.damageStartMinute} <s:text name ="regist.prpLregist.minute"/>
					</td>
					<td class="left">
						<%--出险原因 --%>
						<s:text name="prpLcheck.damageCode" />
					</td>
					<td class="right" colspan="3">
						<input name="prpLcheckDamageCode" class="codecode" style="width: 17%" maxlength=3 description="<s:text name='db.prpLregist.damageCode'/>" value="${prpLcheck.damageCode}"
							ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);"
							onchange="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);"><%-- 出险原因 --%>
						<input name="prpLcheckDamageName" class="codename" style="width: 23%" maxlength=20 description="<s:text name='db.prpLregist.damageCode'/>" value="${prpLcheck.damageName}"
							ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);"
							onchange="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);"><%-- 出险原因 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<%--出险地点 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<td class="right" colspan="5">
						<select name="countryFlag" style="width: 100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
							<option value="0">
								<s:text name="commonAcci.claim.domestic" />
							</option>
							<%--国内  --%>
							<option value="1">
								<s:text name="commonAcci.claim.abroad" />
							</option>
							<%-- 国外 --%>
						</select>
						<input type=text class="codecode" name="countryCode" style="display:none"/>
        <input type=text class="codecode" name="countryCName" style="display:none" title="<s:text name='common.select.country'/>" style="width:120px"
        	ondblclick = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
            onkeyup = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
            onchange = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"/>
        <input type=hidden class="codecode" name="provinceCode" style="display:none"/>
        <input type=hidden class="codecode" name="provinceName" title="<s:text name='common.select.province'/>" style="width:120px"
        	ondblclick = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
            onkeyup = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
            onchange = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"/>
        <input type=text class="codecode" name="prpLcheckAddressCode" style="width: 40px;" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLcheck.addressCode}"
			ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"
			onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" > 
		<input type=text class="codecode" name="prpLcheckAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLcheck.addressName}"
			ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"
			onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');">
      		<input type="text" name="prpLcheckDamageAddress" onchange="changeLDamageAddress()" style="width:50%" class="input" value="${prpLcheck.damageAddress}"
      		onclick = "showProvinceCity(this,'countryCName','prpLcheckAddressName');" selectValue="${prpLcheck.addressName}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<%--查勘日期 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckDate" />
					</td>
					<td class="right">
						<rc:rcDate name="prpLcheckCheckDate" style="width:100" class="input" value="${prpLcheck.checkDate}"/>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<%--查勘地点 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckSite" />
					</td>
					<td class="right">
						<input type="text" name="prpLcheckCheckSite" class="input" value="${prpLcheck.checkSite}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left" style="valign: bottom">
						<%--查勘性质 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckKind" />
					</td>
					<td class="right" style="valign: middle">
						<s:select list="#request.checkNatures" listKey="newCodeCode" listValue="codeCName" name="checkNature" value="#request.prpLcheck.checkNature"></s:select>
					</td>
				</tr>
				<tr>
					<td class="left">
						<%--查勘类型 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckType" />
					</td>
					<td class="right">
						<s:select list="#request.checkTypeList" name="checkType" listKey="key" listValue="value" value="#request.prpLcheck.checkType" />
					</td>
					<td class="left">
						<%--查勘人 1 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker1" />
					</td>
					<td class="right">
						<input name='prpLcheckChecker1' class='input' maxlength=20 description="<s:text name='certainLoss.person1'/>" value="${prpLcheck.checker1}"><%-- 查勘人1 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left" style="valign: bottom">
						<%--查勘人 2--%>
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker2" />
					</td>
					<td class="right" style="valign: middle">
						<input name='prpLcheckChecker2' class='input' maxlength=20 description="<s:text name='certainLoss.person2'/>" value="${prpLcheck.checker2}"><%-- 查勘人2 --%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<!--modify by qinyongli 2005-07-22 start 联共保、临分和股东信息-->
				<s:if test="#coinsFlag!=null">
					<input type="hidden" name="coinsFlag" value="${coinsFlag}">
				</s:if>
				<s:if test="#coinsFlag==null">
					<input type="hidden" name="coinsFlag" value="0">
				</s:if>
				<s:if test="#shareHolderFlag!=null">
					<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
				</s:if>
				<s:if test="#shareHolderFlag==null">
					<input type="hidden" name="shareHolderFlag" value="0">
				</s:if>
				<s:if test="#tempReinsFlag!=null">
					<input type="hidden" name="tempReinsFlag" value="${tempReinsFlag}">
				</s:if>
				<s:if test="#tempReinsFlag==null">
					<input type="hidden" name="tempReinsFlag" value="0">
				</s:if>
				<tr>
					<td class="left">
						<%--报损金额 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckEstimateLoss2" />
						${prpLcheck.currency}
					</td>
					<td class="right">
						<input name='prpLcheckEstimateLoss2' class="readonly" readonly="true" maxlength=20  value="<fmt:formatNumber value="${prpLcheck.registEstimateLoss}" pattern="#"/>">
					</td>
					<td class="left">
						<%--预估金额 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckEstimateLoss" />
						${prpLcheck.currency}
					</td>
					<td class="right">
						<input name='prpLcheckEstimateLoss' class='input' maxlength=20  value="<fmt:formatNumber value="${prpLcheck.estimateLoss}"pattern="#"/>" readonly="readonly">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left" style="valign: bottom">
						<%--预估费用 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckEstimateFee" />
						${prpLcheck.currency}
					</td>
					<td class="right" style="valign: middle">
						<input name='prpLcheckEstimateFee' class='input' maxlength=20 value="<fmt:formatNumber value="${prpLcheck.estimateFee}"pattern="#"/>" readonly="readonly">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class='left' id='tdTitleCheckUnitName' style="width:12%">
						<%--查勘处理单位 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckCom" />
					</td>
					<td class="left" style="width:55%" >
						<select name="unitType" onchange="clearHandleUnitCode();">
							<option value="1" <c:if test="${prpLcheck.unitType=='1'}"> selected</c:if>>
								<s:text name="check.inSystem" />
							</option>
							<%-- 系统内 --%>
							<option value="0" <c:if test="${prpLcheck.unitType=='0'}"> selected</c:if>>
								<s:text name="check.outSystem" />
							</option>
							<%-- 系统外 --%>
						</select>
						<c:if test="${not empty prpLcheck.handleUnitCode}">
							<input name="prpLcheckHandleUnitCode" class="codecode" style="width:100px" maxlength=20 description="<s:text name='certainLoss.prpLscheduleMainWF.Unitcode'/>" value="${prpLcheck.handleUnitCode}" ondblclick="selectHandleCodeByUnitType(this,'dbclick','0,1','Y');"
								onkeyup="selectHandleCodeByUnitType(this,'keyup','0,1','Y');">
							<input name="prpLcheckHandleUnitName" class="input" style="width:200px" maxlength=60 description="<s:text name='certainLoss.prpLscheduleMainWF.Unit'/>" value="${prpLcheck.handleUnit}" readonly><%-- 查勘处理单位 --%>
						</c:if>
						<c:if test="${empty prpLcheck.handleUnitCode}">
							<input name="prpLcheckHandleUnitCode" class="codecode" style="width:100px"  maxlength=20 description="<s:text name='certainLoss.prpLscheduleMainWF.Unitcode'/>" value="${prpLcheck.handleUnitCode}" ondblclick="selectHandleCodeByUnitType(this,'dbclick','0,1','Y');"
								onkeyup="selectHandleCodeByUnitType(this,'keyup','0,1','Y');">
							<input name="prpLcheckHandleUnitName" class="input" style="width:200px" maxlength=60 description="<s:text name='certainLoss.prpLscheduleMainWF.Unit'/>" value="${ prpLcheck.handleUnit}" readonly><%-- 查勘处理单位 --%>
						</c:if>
					</td>
					<td class="left" style="width:33%">
						<c:if test="${prpLcheck.unitType!='0'}">
							<input type=button class='button' value="<s:text name='button.attorney.value'/>" name="freightHeresyCheck" style="display: none" onclick="heresyCheck()" />
							<%-- 委托书 --%>
						</c:if>
						<c:if test="${prpLcheck.unitType=='0'}">
							<input type=button class='button' value="<s:text name='button.attorney.value'/>" name="freightHeresyCheck" onclick="heresyCheck()" />
							<%-- 委托书 --%>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
