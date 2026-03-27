<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]（非车险）
* AUTHOR     ： lixiang
* CREATEDATE ： 2004-10-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
<script type="text/javascript">
function isNumberKey(evt) {
	removeNonNumeric(evt);
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    // 允許 Backspace、Tab、Delete、Arrow 鍵
    if (charCode == 8 || charCode == 9 || charCode == 46 || (charCode >= 37 && charCode <= 40)) {
        return true;
    }
    // 僅允許數字
    if (charCode < 48 || charCode > 57) {
        return false;
    }
    return true;
}
function removeNonNumeric(event) {
	try{
		var prpLclaimHospitalizedDays = fm.prpLclaimHospitalizedDays;
	    // 取得輸入框的值
	    var input = undefined!=prpLclaimHospitalizedDays && null!=prpLclaimHospitalizedDays?prpLclaimHospitalizedDays.value:"";
	    // 移除所有非數字字符
	    prpLclaimHospitalizedDays.value = input.replace(/\D/g, '');
	}catch(e){}
}
</script>
<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END -->
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td width="30%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<c:if test="${prpLclaim.caseType=='1'}">
							<s:text name="commonAcci.claim.rejectClaim" />
							<%--（已拒赔）--%>
						</c:if>
						<c:if test="${prpLclaim.caseType=='0'}">
							<s:text name="commonAcci.claim.cancelled" />
							<%--（已注销）--%>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<input type="hidden" name="termFlag" value="${prpLclaim.termFlag}">
			<%-- 以familyName+kindCode作为name名称添加保额隐藏域 begin --%>
			<c:forEach var="prpCitemKind" items="${damageKindList}">
			<span name="spanPayoutTime" style="display: none;">
				<input type='hidden' name='${prpCitemKind.familyName}${prpCitemKind.kindCode}${prpCitemKind.itemCode}' value='${prpCitemKind.amount}'>
				<input type="hidden" name="${prpCitemKind.kindCode}_${prpCitemKind.id.itemKindNo}" value="${prpCitemKind.amount}" />
				<input type="hidden" name="${prpCitemKind.kindCode}" value="${prpCitemKind.familyNo}" />
				<input type="hidden" name="payoutTime" value="${prpCitemKind.coverageratio}" />
			</span>
			</c:forEach>
			<%-- 以familyName+kindCode作为name名称添加保额隐藏域 end --%>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="query.xianzhongName" />
					</td>
					<%--险种名称--%>
					<td class="right">${requestScope.riskCName}</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<input type="hidden" name="prpLclaimRiskCode" value="${prpLclaim.riskCode}">
					<input type="hidden" name="prpLclaimOperatorCode" value="${prpLclaim.operatorCode}">
					<input type="hidden" name="prpLclaimMakeCom" value="${prpLclaim.makeCom}">
					<input type="hidden" name="prpLclaimEngineNo">
					<input type="hidden" name="prpLclaimFrameNo">
					<input type="hidden" name="prpLclaimRunDistance">
					<%-- <input type="hidden" name="prpLclaimLossName" value="<bean:write name='prpLclaimDto' property='lossName' />">--%>
					<input type="hidden" name="prpLclaimSumDefLoss" value="${prpLclaim.sumDefLoss}">
					<input type="hidden" name="prpLclaimTypeForDriver" value="claim">
					<input type="hidden" name="coreURL" value="${sysconst_Core_URL}">
					<input type="hidden" name="prpLclaimPolicyType" value="${prpLclaim.policyType}">
					<input type="hidden" name="prpLclaimEscapeFlag" value="${prpLclaim.escapeFlag}">
					<input type="hidden" name="prpLclaimClassCode" value="${prpLclaim.classCode}">
					<input type="hidden" name="prpLclaimInputDate" value="${prpLclaim.inputDate}">
					<input type="hidden" name="prpLclaimDamageEndDate" value="${prpLclaim.damageEndDate}">
					<input type="hidden" name="prpLclaimDamageEndHour" value="${prpLclaim.damageEndHour}">
					<input type="hidden" name="prpLclaimDamageEndMinute" value="${prpLclaim.damageEndMinute}">
					<input type="hidden" name="prpLclaimClauseType" readonly="true" style="width: 30px" value="${prpLclaim.clauseType}">
					<input type="hidden" name="prpLclaimClauseName" readonly="true" style="width: 300px" value="${prpLclaim.clauseName}">
					<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START -->
					<!-- TA下面會顯示處理 這裡如果顯示會影響連 動[預計給付金額]機制 -->
					<c:if test="${prpLclaim.riskCode != 'TA'}">
						<input type="hidden" name="prpLclaimAddressCode" title="事故地代碼" class="input" style="width: 80px" value="${prpLclaim.addressCode}">
					</c:if>
					<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END -->
					<input type="hidden" name="prpLclaimDamageAddressType" title="事故地" class="codecode" style="width: 90px" value="${prpLclaim.damageAddressType}">
					<input type="hidden" name="prpLclaimDamageAreaCode" class="codecode" style="width: 15%" title="事故網域" value="${prpLclaim.damageAreaCode}">
					<input type="hidden" name="riskcode" value="${prpLclaim.riskCode}">
					<input type="hidden" name="policyno" value="${prpLclaim.policyNo}">
					<input type="hidden" name="registno" value="${prpLclaim.registNo}">
					<input type="hidden" name="prpLclaimLanguage" title="語種" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.language}">
					<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
					<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
					<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
					<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
					<input type="hidden" name="prpLclaimOthFlag" value="${prpLclaim.othFlag}">
					<input type="hidden" name="underWriteEndDate" value="${prpLclaim.underWriteEndDate}">
					<input type="hidden" name="damageStartDate" value="${prpLclaim.damageStartDate}">
					<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
					<input type="hidden" name="familyno" value="${requestScope.familyno}" >
					<input type="hidden" name="AcciClaimFlag" value="${com_sinosoft_acciFlag}">
					<input type="hidden" name='payFee' value="${payFlag}">
					<input type="hidden" name='BaseCurrency1' value="${prpDexch.baseCurrency}">
					<input type="hidden" name='ExchRate1' value="${prpDexch.exchRate}">
					<input type="hidden" name="reportDamageMessage" value="${reportDamageMessage }"><%-- 出险延期天数提示 --%>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
					<input type="hidden" name="PAF456_SUMLOSS" value="${requestScope.PAF456_SUMLOSS}">
					<input type="hidden" name="PAF7_AMOUNT" value="${requestScope.PAF7_AMOUNT}">
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END -->
					<!--联共保和股东信息-->
					<c:if test="${not empty coinsFlag}">
						<input type="hidden" name="coinsFlag" value="${coinsFlag}">
					</c:if>
					<c:if test="${empty coinsFlag}">
						<input type="hidden" name="coinsFlag" value="0">
					</c:if>
					<c:if test="${not empty shareHolderFlag}">
						<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
					</c:if>
					<c:if test="${empty shareHolderFlag}">
						<input type="hidden" name="shareHolderFlag" value="0">
					</c:if>
					<c:if test="${not empty tempReinsFlag}">
						<input type="hidden" name="tempReinsFlag" value="${tempReinsFlag}">
					</c:if>
					<c:if test="${empty tempReinsFlag}">
						<input type="hidden" name="tempReinsFlag" value="0">
					</c:if>
					<c:if test="${not empty registDate}">
						<input type="hidden" name="prpLclaimReportDate1" value="${registDate}">
					</c:if>
					<c:if test="${empty registDate}">
						<input type="hidden" name="prpLclaimReportDate1" value="0">
					</c:if>
					<%--立案天数 --%>
					<c:if test="${not empty claim_days}">
						<input type="hidden" name='claim_days' value="${claim_days}">
					</c:if>
					<c:if test="${empty claim_days}">
						<input type="hidden" name='claim_days' value="1">
					</c:if>
					<c:if test="${not empty standardDays}">
						<input type="hidden" name='standardDays' value="${standardDays}">
					</c:if>
					<c:if test="${empty standardDays}">
						<input type="hidden" name='standardDays' value="100">
					</c:if>
					<td class="left">
						<s:text name="db.prpLclaim.registNo" />
					</td>
					<%--备案号码--%>
					<td class="right">
						<input type=text name="prpLclaimRegistNo" title="備案號碼" class="readonly" readonly="true" style="width: 170px" value="${prpLclaim.registNo}">
						<input type="hidden" name="damageDate" value="${prpLclaim.damageStartDate}">
					</td>
					<td class="left">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />" onclick="backWardPolicy(fm.coreURL.value,fm.prpLclaimPolicyNoForRelate.value,fm.prpLclaimRiskCode.value,fm.damageDate.value,fm.prpLclaimComCode.value);">
						<%--出险时保单信息--%>
					</td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left" style="width: 15%">
						<s:text name="db.prpLclaim.claimNo" />
					</td>
					<td class="right" style="width: 36%">
						<input type=text name="prpLclaimClaimNo" title="立案號碼" maxlength="22" class="readonly" readonly="true" value="${prpLclaim.claimNo}">
					</td>
					<!--赔案号改为立案号 -->
					<td class="left" style="width: 14%; valign: bottom">
						<s:text name="db.prpLcompensate.caseNo" />
					</td>
					<%--结案号--%>
					<td class="right" style="width: 35%; valign: middle">
						<input type=text name="prpLclaimCaseNo" title="結案號碼" class="readonly" readonly="true" maxlength="22" style="width: 140px" value="${prpLclaim.caseNo}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="commonAcci.claim.accidentTimes" />
					</td>
					<%--已发生事故次数--%>
					<td class="right">
						<%-- 事故信息画面 --%>
						<%@include file="/pages/common/regist/ExistRegist.jsp"%>
					</td>
					<td class="left">
						<div style='display: none'>
							<s:text name="db.prpLclaim.lflag" />
						</div>
					</td>
					<td class="right">
						<div style='display: none'>
							<s:select name="lflag" list="#attr.claimFlagList" listKey="key" listValue="value" value="#attr.prpLclaim.lflag"></s:select>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.policyNo" />
					</td>
					<td class="right">
						<input type=text name="prpLclaimPolicyNo" class="readonly" readonly="true" style="width: 160px" value="${prpLclaim.policyNo}">
						<input type=hidden name="prpLregistPolicyNo" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.policyNo}">
						<%--
			Reson:在意健险类别时和另一个JSP页面（common/claim/ClaimProposer.jsp）中定义的name 为“prpLclaimPolicyNo”的变量发生了冲突，导致在点关联按钮时无法获取"prpLclaimPolicyNo"变量;
			但又不能简单的删掉其中任何一个，故在下面重新定义一个新的变量，用以在进行关联操作时使用
		  --%>
						<input type=hidden name="prpLclaimPolicyNoForRelate" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.policyNo}">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLclaimPolicyNoForRelate.value);return false;">
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.businessNature" />
					</td>
					<%--业务来源--%>
					<td class="right">
						<input type="hidden" name="prpLclaimBusinessNature" value="${prpLclaim.businessNature}">
						<input type=text name="prpLclaimBusinessNatureName" title="業務來源" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.businessNatureName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.language" />
					</td>
					<%--语种--%>
					<td class="right">
						<c:if test="${prpLclaim.language=='C'}">
							<s:text name="commonAcci.claim.chinese" />
							<%--中文--%>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.insuredName" />
					</td>
					<td class="right">
						<c:if test="${prpLclaim.customerType=='1'}">
							<!---<a href='/claim/processPrpDcustomerIdv.do?actionType=prepareUpdate&prpCmainPolicyNo=<bean:write name='prpLclaimDto' property='policyNo' filter='true' />&prpDcustomerIdvCustomerCode=<bean:write name='prpLclaimDto' property='insuredCode' filter='true' />' >--->
						</c:if>
						<c:if test="${prpLclaim.customerType=='2'}">
							<!--- <a href='/claim/processPrpDcustomerUnit.do?actionType=prepareUpdate&prpCmainPolicyNo=<bean:write name='prpLclaimDto' property='policyNo' filter='true' />&prpDcustomerUnitCustomerCode=<bean:write name='prpLclaimDto' property='insuredCode' filter='true' />'>--->
						</c:if>
						<input type=hidden name="prpLregistInsuredCode" title="被保險人代碼" class="readonly" readonly="true" value="${prpLclaim.insuredCode}">
						<input type=hidden name="prpLclaimInsuredName" title="被保險人" class="readonly" readonly="true" value="${prpLclaim.insuredName}">
						<input type=hidden name="policyNo" class="readonly" readonly="true" value="${prpLclaim.policyNo}">
						<input type=text name="prpLclaimInsuredName" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.insuredName}">
						</a>
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />
					</td>
					<%--保险期间--%>
					<td class="right" colspan='3'>
						<rc:rcDate name="prpLclaimStartDate" title="起保日期" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.startDate}" />
						${startHour}
						<rc:rcDate name="prpLclaimEndDate" title="終保日期" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.endDate}" />
						${endHour}
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="db.prpLperson.currency" />
					</td>
					<%--币别--%>
					<td class="right">
						<%--input class="readonly" readonly name="prpLclaimCurrencyName" value="人民币"--%>
						<input class="readonly" name="claimCurrency" value="${prpLclaim.estiCurrency}-${strCurrencyName}">
						<input class="readonly" type=hidden name="prpLclaimCurrency" value="${prpLclaim.estiCurrency}">
						<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.sumAmount" />
					</td>
					<%--保险金额--%>
					<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber value="${prpLclaim.sumAmount}" pattern="#"/>">
						<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="${prpLclaim.sumPremium}">
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.damageTime" />
					</td>
					<%--事故时间--%>
					<td class="right">
						<rc:rcDate name="prpLclaimDamageStartDate" title="事故時間" class="readonly" readonly="readonly" style="width: 80px" value="${prpLclaim.damageStartDate}" />日
						<input type=text name="prpLclaimDamageStartHour" title="事故小時" class="readonly" readonly maxlength="2" style="width: 20px" value="${prpLclaim.damageStartHour}">時
						<input type=text name="prpLclaimDamageStartMinute" title="事故分鐘" class="readonly" readonly maxlength="2" style="width: 20px" value="${prpLclaim.damageStartMinute}">分
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.damageName" />
					</td>
					<td class="right">
						<input type=text class="codecode" name="prpLclaimDamageCode" style="width: 15%" title="事故原因" value="${prpLclaim.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" onchange="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);">
						<input type=text class="codecode" name="prpLclaimDamageName" title="事故原因" style="width: 63%" value="${prpLclaim.damageName}" ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageTypeName" />:
					</td>
					<td class="right">
						<input type=text name="prpLclaimDamageTypeCode" class="codecode" style="width: 15%" title="事故原因" value="${prpLclaim.damageTypeCode}" ondblclick="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');" onchange="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');">
						<input type=text name="prpLclaimDamageTypeName" class="codecode" title="事故原因" style="width: 63%" value="${prpLclaim.damageTypeName}" ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');" onchange="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="certify.dateReceipt"/>
					</td>
					<td class="right">
						<rc:rcDate name="prpLclaimReceiptDate" title="收件日期" style="width:187px" value="${prpLclaim.receiptDate}" format="yyyy-MM-dd HH:mm" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageAddress" />
					</td>
					<%--事故地点--%>
					<td class="right" colspan="5">
						<select name="countryFlag" style="width: 100px;display: none" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
							<option value="0">
								<s:text name="commonAcci.claim.domestic" />
							</option>
							<%--国内--%>
							<option value="1" selected="selected">
								<s:text name="commonAcci.claim.abroad" />
							</option>
							<%--国外--%>
						</select>
						<!-- mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 START-->
						<c:choose>
						    <c:when test="${prpLclaim.riskCode == 'TA'}">
						    	<input type=text class="codecode" name="prpLclaimAddressCode" style="width:50px" value="${prpLclaim.addressCode}" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','0,1','Y','Y');conutryCodeTrigger(this);" onkeyup="code_CodeSelect(this, 'CountryCode_CTN','0,1','Y','Y');" onchange="code_CodeSelect(this, 'CountryCode_CTN','0,1','Y','Y');"/>
								<input type=text class="input" name="prpLclaimDamageAddress" title="事故地點" style="width:450px" value="${prpLclaim.damageAddress}" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" />
								
								<input type=text class="codecode" name="provinceCode" style="display: none" />
								<input type=text class="codecode" name="provinceName" title="選擇省" style="width: 120px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onchange="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" />
								<input type=text class="codecode" name="cityCode" style="display: none" />
								<input type=text class="codecode" name="cityName" title="選擇市" style="width: 120px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onkeyup="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onchange="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" />
								<!-- input type=text name="prpLclaimDamageAddress" title="事故地點" class="input" style="display: none" value="${prpLclaim.damageAddress}" onclick="showProvinceCity(this,'countryCName','cityName');"-->
						    </c:when>
						    <c:otherwise>
								<input type=text class="codecode" name="countryCode" style="display: none" />
								<input type=text class="codecode" name="countryCName" title="選擇地域名" style="width:120px" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" />
								<input type=text class="codecode" name="provinceCode" style="display: none" />
								<input type=text class="codecode" name="provinceName" title="選擇省" style="width: 120px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onchange="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" />
								<input type=text class="codecode" name="cityCode" style="display: none" />
								<input type=text class="codecode" name="cityName" title="選擇市" style="width: 120px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onkeyup="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onchange="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" />
								<input type=text name="prpLclaimDamageAddress" title="事故地點" class="input" style="width: 350px" value="${prpLclaim.damageAddress}" onclick="showProvinceCity(this,'countryCName','cityName');">
						    </c:otherwise>
						</c:choose>
						
						<!-- mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 END-->
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class="common" align="center" width="100%"  style="display: none">
	<tr>
		<td class="left" colspan="4" width="100%">
			<table class="common" align="center" width="100%">
				<tr>
					<td class="right" >
						<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="RegistPolicyRiskImg" onclick="showPage(this,RegistPolicyInfo)">
						<s:text name="commonAcci.claim.insuredAccidentInfo" />
						<%--事故被保险人信息--%>
						<br>
						<table class="common" align="center" id="RegistPolicyInfo">
							<thead>
								<tr>
									<td class="prompttitle" style="width: 20%; display: none">
										<s:text name="commonAcci.claim.accidentCode" />
									</td>
									<%--事故者代码--%>
									<td class="prompttitle" style="width: 20%">
										<s:text name="claim.name" />
									</td>
									<%--姓名--%>
									<td class="prompttitle" style="width: 10%">
										<s:text name="db.prpLperson.personSex" />
									</td>
									<%--性别--%>
									<td class="prompttitle" style="width: 10%">
										<s:text name="db.prpLperson.personAge" />
									</td>
									<%--年龄--%>
									<td class="prompttitle" style="width: 25%">
										<s:text name="db.prpLregist.identifyNumber" />
									</td>
									<%--身份证号--%>
									<td class="prompttitle" style="width: 5%">
										<s:text name="commonAcci.claim.beneficiaryInfo" />
									</td>
									<%--受益人信息--%>
									<td class="prompttitle" style="width: 10%">
										<s:text name="commonAcci.claim.insuranceBenefInfo" />
									</td>
									<%--保益信息--%>
								</tr>
							</thead>
							<tbody>
								<tr class=listodd>
									<s:if test="#request.insuredNameFlag=='Ture'">
										<td class="input" align=center style="width: 20%; display: none">
											<input type="text" name="prpLacciPersonAcciCode" value="${prpLclaim.acciCode}" class="input" title="事故者代碼">
											<input type="hidden" name="clickCount">
										</td>
										<td class="input" align=center style="width: 20%">
											<input type=text name="prpLacciPersonAcciName" title="事故者姓名" value="${prpLclaim.acciName}" class="input" title="事故者姓名">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</td>
									</s:if>
									<s:else>
										<td class="input" align=center style="width: 20%; display: none">
											<input type="hidden" name="clickCount" value="1">
											<input type="text" name="prpLacciPersonAcciCode" value="${prpLclaim.acciCode}" title="事故者代碼" class="codecode" style="width: 120px" title="幣別" ondblclick="showAcciName(this);" onkeyup="showAcciName(this);" onchange="showAcciName(this);">
										</td>
										<td class="input" align=center style="width: 20%">
											<input type=text name="prpLacciPersonAcciName" title="事故者姓名" value="${prpLclaim.acciName}" class="codecode" style="width: 120px" title="事故者姓名" ondblclick="showAcciName(this);" onkeyup="showAcciName(this);" onchange="showAcciName(this);">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</td>
									</s:else>
									<td class="input" align=center style="width: 10%">
										<select name="prpLacciPersonSex" title="性别" class="input" style="width: 50px">
											<option value="1" <c:if test="${prpLclaim.sex=='1'}">selected</c:if>>
												<s:text name="certainLoss.male" />
											</option>
											<%--男--%>
											<option value="2" <c:if test="${prpLclaim.sex=='2'}">selected</c:if>>
												<s:text name="certainLoss.female" />
											</option>
											<%--女--%>
										</select>
									</td>
									<td class="input" align=center style="width: 10%">
										<input type=text name="prpLacciPersonAge" title="年齡" class="input" style="width: 80px" value="${prpLclaim.age}">
									</td>
									<td class="input" align=center style="width: 25%">
										<input type=text name="prpLacciPersonIdentifyNumber" title="身份證號" class="input" style="width: 180px" value="${prpLclaim.identifyNumber}">
										<%-- 隐藏被保险人序号 --%>
										<input type=hidden name="prpLacciPersonFamilyNo" value="${prpLclaim.familyNo}" class="input" />
									</td>
									<td class="input" align=center style="width: 5%">
										<%@include file="/pages/common/regist/Beneficiary.jsp"%>
									</td>
									<td class="input" align=center style="width: 10%">
										<%@include file="/pages/common/regist/Benerisk.jsp"%>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class='left'>
						<s:text name="claim.possibleRec" />
					</td>
					<%--是否可能有追偿--%>
					<td class='right'>
						<select name="replevyFlag">
							<option value="0" <c:if test="${prpLclaim.replevyFlag=='0'}">selected</c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1" <c:if test="${prpLclaim.replevyFlag=='1'}">selected</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--是--%>
						</select>
					</td>
					<td class='left'>
						<s:text name="claim.otherClaimsInterm" />
					</td>
					<%--是否有其他理赔中介机构--%>
					<td class='right'>
						<select name="thirdComFlag">
							<option value="0" <c:if test="${prpLclaim.thirdComFlag=='0'}">selected</c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1" <c:if test="${prpLclaim.thirdComFlag=='1'}">selected</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--是--%>
						</select>
					</td>
					<td class="left">
						<s:text name="claim.recoverAge" />
					</td>
					<%--追偿时效--%>
					<td class="right">
						<rc:rcDate name="ReplevyLimitDate" class="query" value="${prpLclaim.replevyLimitDate}" title="结束日期" onkeypress="return pressFullDate(event);" style="width: 120" />
					</td>
				</tr>
				<tr>
					<td class="left">
						追償說明：
						<%-- 是否可能有追偿 --%>
					</td>
					<td class="right" colspan="3">
						<input name="prpLclaimReplevyRemark" class="common" value="${prpLclaim.replevyRemark }">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class='left'>
						<s:text name="commonAcci.claim.involvedLitigat" />
					</td>
					<%--是否涉及诉讼--%>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 -->
					<td class='right' colspan="${prpLclaim.riskCode == 'PA'?1:5}">
						<select name="referLawFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
					</td>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
					<c:if test="${prpLclaim.riskCode == 'PA'}">
						<td class='left'>
							本次住院天數
						</td>
						<%--本次住院天數--%>
						<td class='right' colspan="1">
							<input type="text" name="prpLclaimHospitalizedDays" value="${prpLclaim.hospitalizedDays}" class="input" title="本次住院天數"
								 maxlength="3" onblur="removeNonNumeric(event)">
						</td>
						<td class='left'>
							&nbsp;
						</td>
						<td class='right' colspan="1">
							&nbsp;
						</td>
					</c:if>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END -->
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>