<%--
****************************************************************************
* DESC       : 添加主信息子块界面页面Head[ 实赔 ]
* AUTHOR     : 中科软
* MODIFYLIST : Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<c:forEach items="${amountMap}" var="amountMapTemp">
    <input type="hidden" name="kindCode" value="${amountMapTemp.key}">
    <input type="hidden" name="kindAmount" value="${amountMapTemp.value}">
</c:forEach>
<script language="javascript">
    function changePrpLcompensateFinallyFlag(){
        var t = document.getElementById("prpLcompensateFinallyFlag");
        var v = t.options[t.selectedIndex].value;
        if ("0" == v) {
            fm.replevyFlag.disabled = false;
            fm.referLawFlag.disabled = false;
            Lltext.style.display = "";
        } else if ("1" == v) {
            fm.replevyFlag.disabled = false;
            fm.referLawFlag.disabled = true;
            Lltext.style.display = "";
        } else {
            fm.replevyFlag.disabled = true;
            fm.referLawFlag.disabled = true;
            Lltext.style.display = "";
        }
    }
    function changePrpLcompensateFinallyFlag1(){
        fm.replevyFlag.disabled = false;
        fm.referLawFlag.disabled = true;
        Lltext.style.display = "";
    }
    function changeFinallyFlagAndLltextContent(){
        var isPayForOtherList = document.getElementsByName("isPayForOther");
        //reason:理算报告用来保存"後续理算内容"
        var strtemp = "";
        var t = document.getElementById("prpLcompensateFinallyFlag");
        var v = t.options[t.selectedIndex].value;
        if ("0" == v) {
            alert("<s:text name='prompt.certify.message1'/>\n");//案件核赔通过后，不会自动结案，请手工结案！
            fm.replevyFlag.disabled = false;
            fm.referLawFlag.disabled = false;
            Lltext.style.display = "";
        } else if ("1" == v) {
            if (isPayForOtherList.length > 0 && isPayForOtherList[0].checked == true) {
                //alert("<s:text name='prompt.certify.message2'/>");//选择代付赔款，不准许选择为结案！
                //fm.prpLcompensateFinallyFlag.options[0].selected = true;
                fm.replevyFlag.disabled = false;
                fm.referLawFlag.disabled = false;
                Lltext.style.display = "";
                return;
            }
            alert("<s:text name='prompt.certify.message3'/>\n");//案件核赔通过后，将自动结案！
            fm.replevyFlag.disabled = false;
            fm.referLawFlag.disabled = true;
            Lltext.style.display = "";
        } else {
            alert("<s:text name='prompt.certify.message3'/>\n");//案件核赔通过后，将自动结案！
            fm.replevyFlag.disabled = true;
            fm.referLawFlag.disabled = true;
            Lltext.style.display = "";
        }
    }
    function changeFinallyFlag(type){
        var prpLcompensateFinallyFlag = document.getElementById("prpLcompensateFinallyFlag");
        var prpLcompensateSumCoinForOther = document.getElementsByName("prpLcompensateSumCoinForOther");
        var prpLcompensateSumCoinForOtherFee = document.getElementsByName("prpLcompensateSumCoinForOtherFee");
        var prpLcompensateSumCoinForOtherBak = document.getElementsByName("prpLcompensateSumCoinForOtherBak");
        var prpLcompensateSumCoinForOtherFeeBak = document.getElementsByName("prpLcompensateSumCoinForOtherFeeBak");
        if (type == 1) {
            //if (prpLcompensateFinallyFlag.length > 1) {
               // fm.prpLcompensateFinallyFlag.options[0].selected = true;
            //}
            if (prpLcompensateSumCoinForOther.length > 0 && prpLcompensateSumCoinForOtherBak.length > 0) {
                prpLcompensateSumCoinForOther[0].value = prpLcompensateSumCoinForOtherBak[0].value;
            }
            if (prpLcompensateSumCoinForOtherFee.length > 0 && prpLcompensateSumCoinForOtherFeeBak.length > 0) {
                prpLcompensateSumCoinForOtherFee[0].value = prpLcompensateSumCoinForOtherFeeBak[0].value;
            }
        } else if (type == 0) {
            if (prpLcompensateSumCoinForOther.length > 0 && prpLcompensateSumCoinForOtherBak.length > 0) {
                prpLcompensateSumCoinForOther[0].value = '0.0';
            }
            if (prpLcompensateSumCoinForOtherFee.length > 0 && prpLcompensateSumCoinForOtherFeeBak.length > 0) {
                prpLcompensateSumCoinForOtherFee[0].value = '0.0';
            }
        }
    }
</script>
<input type="hidden" name="configCode" value="${requestScope.configCode}">
<input type="hidden" name="prpLcompensateCaseNo" value="${prpLcompensate.caseNo}">
<input type="hidden" name="prpLcompensateClassCode" value="${prpLcompensate.classCode}">
<input type="hidden" name="prpLcompensateRiskCode" value="${prpLcompensate.riskCode}">
<input type="hidden" name="prpLcompensateDeductCond" value="${prpLcompensate.deductCond}">
<input type="hidden" name="prpLcompensatePreserveDate" value="${prpLcompensate.preserveDate}">
<input type="hidden" name="prpLcompensateCheckAgentCode" value="${prpLcompensate.checkAgentCode}">
<input type="hidden" name="prpLcompensateCheckAgentName" value="${prpLcompensate.checkAgentName}">
<input type="hidden" name="prpLcompensateSurveyorName" value="${prpLcompensate.surveyorName}">
<input type="hidden" name="prpLcompensateDutyDescription" value="${prpLcompensate.dutyDescription}">
<input type="hidden" name="prpLcompensateCurrency" value="${prpLcompensate.currency}">
<input type="hidden" name="prpLcompensateSumLoss" value="${prpLcompensate.sumLoss}">
<input type="hidden" name="prpLcompensateReceiverName" value="${prpLcompensate.receiverName}">
<input type="hidden" name="prpLcompensateBank" value="${prpLcompensate.bank}">
<input type="hidden" name="prpLcompensateAccount" value="${prpLcompensate.account}">
<input type="hidden" name="prpLcompensateMakeCom" value="${prpLcompensate.makeCom}">
<input type="hidden" name="prpLcompensateComCode" value="${prpLcompensate.comCode}">
<input type="hidden" name="prpLcompensateHandlerCode" value="${prpLcompensate.handlerCode}">
<input type="hidden" name="prpLcompensateHandler1Code" value="${prpLcompensate.handler1Code}">
<input type="hidden" name="prpLcompensateApproverCode" value="${prpLcompensate.approverCode}">
<input type="hidden" name="prpLcompensateUnderWriteCode" value="${prpLcompensate.underWriteCode}">
<input type="hidden" name="prpLcompensateUnderWriteName" value="${prpLcompensate.underWriteName}">
<input type="hidden" name="prpLcompensateOperatorCode" value="${prpLcompensate.operatorCode}">
<input type="hidden" name="prpLcompensateInputDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.inputDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
<input type="hidden" name="prpLcompensateUnderWriteEndDate" value="${prpLcompensate.underWriteEndDate}">
<input type="hidden" name="prpLcompensateUnderWriteFlag" value="${prpLcompensate.underWriteFlag}">
<input type="hidden" name="prpLcompensateFlag" value="${prpLcompensate.flag}">
<input type="hidden" name="riskcode" value="${prpLcompensate.riskCode}">
<input type="hidden" name="policyno" value="${prpLcompensate.policyNo}">
<input type="hidden" name="registno" value="${prpLcompensate.registNo}">
<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
<input type="hidden" name="status" value="${param.status}">
<input type="hidden" name="clauseType" value="">
<input type="hidden" name="GenerateCompensateFlag" value="0">
<input type="hidden" name='payFee' value="${payFlag}">
<input type="hidden" name='BaseCurrency1' value="${prpDexch.baseCurrency}">
<input type="hidden" name='ExchRate1' value="${prpDexch.exchRate}">
<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
<input type="hidden" name="coreURL" value="${coreURL}">
<input type="hidden" name="prpLcompensateCaseType" value="">
<input type="hidden" name="prpLcompensateStartDate" value="${prpLcompensate.startDate }<s:text name='endcase.dayStart'/> ${prpLcompensate.endDate}<s:text name='endcase.dayEnd'/>">
<input type="hidden" name="prpLcompensateClauseType" value="${prpLcompensate.clauseType}">
<input type="hidden" name="prpLcompensateClauseName" value="${prpLcompensate.clauseName}">
<input type="hidden" name="prpLcompensateLicenseNo" value="${prpLcompensate.licenseNo}">
<input type="hidden" name="prpLcompensateCarKind" value="${prpLcompensate.carKind}">
<input type="hidden" name="prpLcompensateLicenseColor" value="${prpLcompensate.licenseColor}">
<input type="hidden" name="prpLcompensateBrandName" value="${prpLcompensate.brandName}">
<input type="hidden" name="prpLcompensateEngineNo" value="${prpLcompensate.engineNo}">
<input type="hidden" name="prpLcompensateFrameNo" value="${prpLcompensate.frameNo}">
<input type="hidden" name="prpLcompensateDamageStartDate"
    value="${prpLcompensate.damageStartDate} <s:text name='regist.prpLregist.date'/> ${prpLcompensate.damageStartHour} <s:text name='regist.prpLregist.hour'/> ${prpLcompensate.damageStartMinute} <s:text name='regist.prpLregist.minute'/>">
<input type="hidden" name="prpLcompensateDamageAddress" value="${prpLcompensate.damageAddress}">
<input type="hidden" name="prpLcompensateSumClaim" value="${prpLcompensate.sumClaim}">
<input type="hidden" name="damageStartDate" value="${prpLcompensate.damageStartDate}">
<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
<input type="hidden" name="sumPaidAll" value="${prpLcompensate.sumPaidAll}">
<!--增加 股东业务信息-->
<c:choose>
    <c:when test="${not empty requestScope.shareHolderFlag}"><input type="hidden" name=shareHolderFlag value="${requestScope.shareHolderFlag}"></c:when>
    <c:otherwise><input type="hidden" name="shareHolderFlag" value="0"></c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty requestScope.coinsFlag}"><input type="hidden" name="coinsFlag" value="${requestScope.coinsFlag}"></c:when>
    <c:otherwise><input type="hidden" name="coinsFlag" value="0"></c:otherwise>
</c:choose>
<table class="subtable" cellpadding="0" cellspacing="1">
    <tr>
        <td>
            <table class="common" cellpadding="1" cellspacing="1">
                <tr>
                    <td class="left">
                        <s:text name="query.xianzhongName" />：<%-- 险种名称 --%>
                    </td>
                    <td class="right">${requestScope.riskCName}</td>
                    <c:choose>
                        <c:when test="${not empty requestScope.prpLcompensate.mutualCompensateNo || not empty mutualCompensateNoList}">
                            <td class="left">互沖計算書號碼：</td>
                            <td class="right">
                                <c:choose>
                                    <c:when test="${param.editType=='ADD'}">
                                        <select name="prpLcompensateMutualCompensateNo" onchange="getMutualCompe(this);" style="width: 180px">
                                            <c:if test="${empty param.prpLcompensateMutualCompensateNo}">
                                                <option value="" selected="selected"></option>
                                            </c:if>
                                            <c:forEach items="${requestScope.mutualCompensateNoList}" var="mutualCompensateNo">
                                                <option value="${mutualCompensateNo}" <c:if test="${mutualCompensateNo==param.prpLcompensateMutualCompensateNo}">selected="selected"</c:if>>${mutualCompensateNo}</option>
                                            </c:forEach>
                                        </select>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" name="prpLcompensateMutualCompensateNo" class="readonly" readonly value="<c:out value='${requestScope.prpLcompensate.mutualCompensateNo}'/>">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="left">&nbsp;</td>
                            <td class="right">&nbsp;</td>
                        </c:otherwise>
                    </c:choose>
                    <td class="left">賠付速別：</td>
                    <td class="right">
                        <s:select name="prpLcompensateSpeedFlag" value="#request.prpLcompensate.speedFlag" list="#request.speedFlagList" listKey="key" listValue="value" cssStyle="width: 120px"></s:select>
                        <input type=hidden name="LFlag" title="<s:text name='db.prpLcompensate.lflag'/>" maxlength="22" class="readonly" readonly="true" value="${prpLcompensate.lflag}">
                        <%-- 理赔类型 --%>
                    </td>
                </tr>
                <tr>
                    <td class="left">
                        <s:text name="compensate.computeBookNum" />：
                        <%-- 计算书号 --%>
                    </td>
                    <td class="right">
                        <input type="text" style="width: 180px" name="prpLcompensateCompensateNo" title="<s:text name='compensate.computeBookNum' />" maxlength="22" class="readonly" readonly="true" value="${prpLcompensate.compensateNo}">
                        &nbsp;&nbsp;
                    </td>
                    <td class="left" colspan="4">
                        <input type=button class="bigbutton" name="flowShow" value="<s:text name='button.flowChart.value'/>" title="<s:text name='button.flowChart.value'/>" onclick="showWorkFlowerByClaimNo('${prpLcompensate.claimNo}')">
                    </td>
                </tr>
                <tr>
                    <td class="left">
                        <s:text name="certainLoss.claims" />：
                        <%-- 赔案号 --%>
                    </td>
                    <td class="right">
                        <input class="readonly" type="text" name="prpLcompensateClaimNo" readonly="true" value="${prpLcompensate.claimNo}" style="width: 180px">
                        <input type="hidden" name="damageDate" value="${prpLcompensate.damageStartDate}">
                    </td>
                    <td class="left" colspan="2">
                        <input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>"
                            onclick="backWardPolicy(fm.coreURL.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,fm.damageDate.value,fm.prpLcompensateComCode.value);">
                    </td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<br/>
<c:if test="${prpLcompensate.riskCode=='CC'}">
	<%-- add by zhuyongwei reason:增加 银行卡相关信息  欄位 begin --%>
	<table  class=subtable cellpadding="0" cellspacing="1" >
		<tr>
			<td>
				<table  class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left"><s:text name="db.prpLclaim_credit.issuingBank" />:</td><%-- 發卡銀行 --%>
						<td class="right">
							<select name="prpLclaimCreditBankCode" id="prpLclaimCreditBankCode" disabled="disabled" >
								<option value=""></option>
								<c:forEach items="${requestScope.creditBankList}" var="prpDcode">
									<option value="${prpDcode.id.codeCode}">${prpDcode.id.codeCode}.${prpDcode.codeCName} </option>
								</c:forEach>
							</select>
							<script type="text/javascript">
								$("#prpLclaimCreditBankCode").val("${prpLclaimCredit.bankCode}");
							</script>
						</td>
						<td class="left"><s:text name="db.prpLclaim_credit.creditCardType" />:</td><%--卡別--%>
						<td class="right" >
							<select name="prpLclaimCreditCardCode" id="prpLclaimCreditCardCode" disabled="disabled" style="width: 250px">
								<option value=""></option>
								<c:forEach items="${requestScope.creditTypeList}" var="prpDcode">
									<option value="${prpDcode.id.codeCode}">${prpDcode.id.codeCode}-${prpDcode.codeCName}</option>
								</c:forEach>
							</select>
							<script type="text/javascript">
								$("#prpLclaimCreditCardCode").val("${prpLclaimCredit.cardCode}");
							</script>
						</td>
						<td class="left">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLclaim_credit.creditCardNo" />:</td><%--信用卡號碼--%>
						<td class="right">
							<input type=text name="prpLclaimCreditCardNo" class="readonly" readonly value="${prpLclaimCredit.cardNo}">
						</td>
						<td class="left">到期年月：</td>
						<td class="right">
							<input type="text" name="prpLclaimCreditValidDate" title="到期日" class="readonly" readonly style="width:120px" value="${prpLclaimCredit.validDateYear}年${prpLclaimCredit.validDateMonth}月" />
						</td>
						<td class="left">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLclaim_credit.cardholderName" />:</td><%--持卡人姓名--%>
						<td class="right" >
							<input type=text name="prpLclaimCreditHolderName" class="readonly" readonly value="${prpLclaimCredit.holderName}">
						</td>
						<td class="left"><s:text name="db.prpLclaim_credit.cardholderIDNo"/>:</td><%--持卡人身份證字號--%>
						<td class="right">
							<input type=text name="prpLclaimCreditHolderIdentifyNumber" class="readonly" readonly  value="${prpLclaimCredit.holderIdentifyNumber}">
						</td>
						<td class="left">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLclaim_credit.cardholderTel" />:</td><%--持卡人電話--%>
						<td class="right">
							<input type=text name="prpLclaimCreditHolderTel" class="readonly" readonly value="${prpLclaimCredit.holderTel}">
						</td>
						<td class="left"><s:text name="db.prpLclaim_credit.cardholderPhone" />:</td><%--持卡人手機--%>
						<td class="right">
							<input type=text name="prpLclaimCreditHolderPhone" class="readonly" readonly value="${prpLclaimCredit.holderPhone}">
						</td>
						<td class="left">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLclaim_credit.relationship" />:</td><%-- 與被保險人關係 --%>
						<td class="right">
							<select name="prpLclaimCreditHolderRelationShip" style="width: 200px" id="prpLclaimCreditHolderRelationShip" disabled="disabled" >
								<option value="1" > 1.本人 </option>
								<option value="2" > 2.本人及配偶 </option>
								<option value="3" > 3.本人及配偶及子女 </option>
								<option value="4" > 4.配偶 </option>
								<option value="5" > 5.配偶及子女 </option>
							</select>
							<script type="text/javascript">
								$("#prpLclaimCreditHolderRelationShip").val("${prpLclaimCredit.holderRelationShip}");
							</script>
						</td>
						<td class="left"><s:text name="db.prpLclaim_credit.cardholderAddress" />:</td><%--持卡人居住地址--%>
						<td class="right" >
							<input type=text name="prpLclaimCreditHolderAddress" class="readonly" readonly value="${prpLclaimCredit.holderAddress}">
						</td>
						<td class="left">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if> 
<br>
<table class="subtable" cellpadding="0" cellspacing="1" >
    <tr>
        <td>
            <table class="common" cellpadding="1" cellspacing="1">
                <tr>
                    <td class="left" ><s:text name="db.view_larrearage.policyNo" />：<%-- 保單號碼  --%></td>
                    <td class="right" >
                        <input type="text" name="prpLcompensatePolicyNo" class="readonly" readonly="true" value="${prpLcompensate.policyNo}" style="width: 120px" >
                        <input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" onclick="relate(fm.prpLcompensatePolicyNo.value);return false;">
                    </td>
                    <td class="left" >
                        <s:text name="certainLoss.prpLcheck.insuredName" />：<%-- 被保险人 --%>
                    </td>
                    <td class="right" >
                        <input class="readonly" type="text" name="prpLcompensateInsuredName" readonly="true" value="${prpLcompensate.insuredName}">
                    </td>
                    <td class="left" ></td>
                    <td class="right" ></td>
                </tr>
                <tr>
                    <td class="left" >業務來源：</td>
                    <td class="right" >
                        <input class="readonly" readonly="readonly" type="text" style="width: 120px" name="businessNatureName" title="業務來源" value="${prpLclaim.businessNatureName}">
                    </td>
                    <td class="left" >幣別：</td>
                    <td class="right" ><%=ConstantCodes.LOCAL_CURRENCY%>&nbsp;<%=ConstantCodes.LOCAL_CURRENCYNAME%></td>
                    <td class="left" >保險金額：</td>
                    <td class="right" >
                        <input type="text" readonly="readonly" class="readonly" name="prpLcompensateSumAmount" value="<fmt:formatNumber value='${prpLcompensate.sumAmount}' pattern='#'/>">
                    </td>
                </tr>
                <tr>
                    <td class="left" >保險期間：</td>
                    <td class="right" colspan="5" align="left">
                        <rc:rcDate name="displayStartDate" value="${prpLclaim.startDate}" class="readonly" wdatePicker="false" style="width: 80px"/>
                        ${prpLclaim.startHour}時起至
                        <rc:rcDate name="displayEndDate" value="${prpLclaim.endDate}" class="readonly" wdatePicker="false" style="width: 80px"/>
                        ${prpLclaim.endHour}時止
                    </td>
                </tr>
                <tr>
                    <td class="left" >出險時間：</td>
                    <td class="right" >
                        <rc:rcDate name="displayDamageStartDate" value="${prpLcompensate.damageStartDate}" class="readonly" wdatePicker="false" style="width: 80px"/>
                        ${prpLcompensate.damageStartHour}時&nbsp;${prpLcompensate.damageStartMinute}分
                    </td>
                    <td class="left" >出險地點：</td>
                    <td class="right" >${prpLclaim.damageAddress}</td>
                    <td class="left" >出險原因：</td>
                    <td class="right">
                        <input type=text class="codecode" name="prpLcompensateDamageCode" style="width: 30px" title="出险原因" value="${prpLclaim.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
                            onchange="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" readonly="readonly">
                        <input type=text class="codecode" name="prpLcompensateDamageName" title="<s:text name="db.prpLregist.damageCode" />" style="width: 150px" value="${prpLclaim.damageName}"
                            ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onchange="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
                            onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" readonly="readonly">
                        <img src="${ctx}/images/bgDoubleClick1.gif" align="absmiddle">
                    </td>
                </tr>
                <tr>
                    <td class="left" >
                        <s:text name="db.prpLCMain.claimTimes" />：<%-- 赔付次数 --%>
                    </td>
                    <td class="right" >
                        <input type="text" name="prpLcompensateTimes" class="readonly" readonly value="${prpLcompensate.times}">
                    </td>
                    <td class="left" >
                        <s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" /><%--已出险次数--%>
                    </td>
                    <td class="right" ><%@include file="/pages/commonLiab/regist/LiabExistRegist.jsp"%></td>
                    <td class="left" ></td>
                    <td class="right" ></td>
                </tr>
            <c:if test="${requestScope.coinsFlag == '1'}">
                <tr>
                    <td class="left" >
                        <s:text name="commonAcci.compensate.whetherPaidReparat" />：<%-- 是否代付赔款 --%>
                    </td>
                    <td class="right" >
                        <input type="radio" name="isPayForOther" onclick="changeFinallyFlag(1);" <c:if test="${prpLcompensate.isPayForOther == '1'}">checked="checked"</c:if> value="1">
                        <s:text name="certainLoss.thirdCarLoss.yes" /><%-- 是 --%>
                        <input type="radio" name="isPayForOther" onclick="changeFinallyFlag(0);" <c:if test="${prpLcompensate.isPayForOther == '0'}">checked="checked"</c:if> value="0">
                        <s:text name="certainLoss.thirdCarLoss.no" /><%--否  --%>
                    </td>
                    <td class="left" ></td>
                    <td class="right" ></td>
                    <td class="left" ></td>
                    <td class="right" ></td>
                </tr>
            </c:if>
                <tr>
            <c:choose>
                <c:when test="${requestScope.recaseFlag=='0' || param.editType == 'SHOW'}">
                    <td class="left" >
                        <s:text name="compensate.closedType" />：<%--  结案类型--%>
                    </td>
                    <td class="right" >
                        <select id="prpLcompensateFinallyFlag" name="prpLcompensateFinallyFlag" style="width: 110px;" onchange="changeFinallyFlagAndLltextContent();">
                            <option value="0" <c:if test="${prpLcompensate.finallyFlag == '0'}">selected</c:if>><s:text name="分次賠付"/></option>
                            <option value="1" <c:if test="${prpLcompensate.finallyFlag == '1'}">selected</c:if>><s:text name="結案" /></option>
                            <option value="2" <c:if test="${prpLcompensate.finallyFlag == '2'}">selected</c:if>><s:text name="拒賠" /></option>
                            <option value="3" <c:if test="${prpLcompensate.finallyFlag == '3'}">selected</c:if>><s:text name="免賠" /></option>
                        </select>
                    </td>
                </c:when>
                <c:otherwise>
                    <td class="left" >
                        <s:text name="compensate.claimsType" />：<%-- 重开赔案结案类型 --%>
                    </td>
                    <td class="right" >
                        &nbsp;&nbsp;結案<input type="hidden" name="prpLcompensateFinallyFlag" value="1">
                    </td>
                </c:otherwise>
            </c:choose>
                    <td class="left" ></td>
                    <td class="right" ></td>
                    <td class="left" ></td>
                    <td class="right" ></td>
                </tr>
                <tr>
                    <td class="left" >立案日期：</td>
                    <td class="right" >
                        <rc:rcDate name="displayClaimDate" value="${prpLclaim.claimDate}" class="readonly" wdatePicker="false" style="width: 80px"/>
                    </td>
                    <td class="left" >收件日期：</td>
                    <td class="right" >
                        <rc:rcDate name="displayReceiptDate" value="${prpLclaim.receiptDate}" class="readonly" wdatePicker="false" style="width: 80px"/>
                    </td>
                    <td class="left" ></td>
                    <td class="right" ></td>
                </tr>
                <tr>
                    <td class="left" >
                        <s:text name="commonAcci.claim.involvedLitigat" />：<%--是否涉及诉讼--%>
                    </td>
                    <td class="right" >
                <c:choose>
                    <c:when test="${empty requestScope.prpLclaim.referLawFlag || requestScope.prpLclaim.referLawFlag =='0' || requestScope.prpLclaim.referLawFlag =='1'}">
                        <select name="referLawFlag" <c:if test="${requestScope.recaseFlag != '0' && param.editType !='SHOW'}">disabled="true"</c:if> >
                            <option value="0" <c:if test="${requestScope.prpLclaim.referLawFlag=='0'}">selected="selected"</c:if> >
                                <s:text name="certainLoss.thirdCarLoss.no" /><%-- 否 --%>
                            </option>
                            <option value="1" <c:if test="${requestScope.prpLclaim.referLawFlag=='1'}">selected="selected"</c:if> >
                                <s:text name="certainLoss.thirdCarLoss.yes" /><%--是  --%>
                            </option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="referLawFlag" disabled="true">
                            <option value="${requestScope.prpLclaim.referLawFlag}"><s:text name="certainLoss.thirdCarLoss.yes" /><%--是  --%></option>
                        </select>
                    </c:otherwise>
                </c:choose>
                    </td>
                    <td class="left" >
                        <s:text name="claim.possibleRec" />：<%--是否可能有追偿--%>
                    </td>
                    <td class="right" >
                <c:choose>
                    <c:when test="${empty requestScope.prpLclaim.replevyFlag || requestScope.prpLclaim.replevyFlag =='0' || requestScope.prpLclaim.replevyFlag =='1'}">
                        <select name="replevyFlag" >
                            <option value="0" <c:if test="${requestScope.prpLclaim.replevyFlag=='0'}">selected="selected"</c:if> >
                                <s:text name="certainLoss.thirdCarLoss.no" /><%-- 否 --%>
                            </option>
                            <option value="1" <c:if test="${requestScope.prpLclaim.replevyFlag=='1'}">selected="selected"</c:if> >
                                <s:text name="certainLoss.thirdCarLoss.yes" /><%--是  --%>
                            </option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="replevyFlag" disabled="true">
                            <option value="${requestScope.prpLclaim.replevyFlag}"><s:text name="certainLoss.thirdCarLoss.yes" /><%--是  --%></option>
                        </select>
                    </c:otherwise>
                </c:choose>
                    </td>
                    <td class="left">是否有殘餘物：</td>
					<td class="right">
						<s:select name="prpLcompensateRemnants" list="#{'0':'否','1':'是'}" value="#request.prpLcompensate.remnants" listKey="key" listValue="value" />
					</td>
                </tr>
                <tr>
					<td class="left">
						追償說明：
					</td>
					<td class="right" colspan="3">
						<input name="prpLcompensateReplevyRemark" class="common" value="${prpLclaim.replevyRemark }">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
                <input type="hidden" name="prpLcompensateIndemnityDuty" class="readonly" readonly="true" value="${prpLcompensate.indemnityDuty}">
                <input type=hidden name="prpLcompensateIndemnityDutyName" class="readonly" readonly="true" value=" ">
                <input type="hidden" name="prpLcompensateIndemnityDutyRate" class="readonly" readonly="true" value="0">
            </table>
        </td>
    </tr>
</table>
<script language="javascript">
/***
 * 互沖計算書切換時，獲取要互沖的計算書的訊息
 * @param field
 */
function getMutualCompe(field){
    if($.trim($(field).val())!=""){
        var url = "${ctx}/compensate/beforeCompeMutualImpulse.do?ClaimNo=${param.ClaimNo}&caseType=${param.caseType}&swfLogFlowID=${param.swfLogFlowID}&swfLogLogNo=${param.swfLogLogNo}&status=0&riskCode=${param.riskCode}&editType=ADD&nodeType=compe&businessNo=${param.businessNo}&keyIn=${param.keyIn}&policyNo=${param.policyNo}&modelNo=${param.modelNo}&nodeNo=${param.nodeNo}&dfFlag=${param.dfFlag}&actorId=${param.actorId}&processId=${param.processId}&compeCount=${param.compeCount}";
        url += "&prpLcompensateMutualCompensateNo="+$(field).val();
        window.location.href = url;
    }
}
</script>