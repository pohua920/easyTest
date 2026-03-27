<%--
****************************************************************************
* DESC       :添加主信息子块界面页面Head[ 实赔 ]
* AUTHOR     : 理赔组
* CREATEDATE : 2004-05-12
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function changeFinallyFlag(type) {
    var prpLcompensateSumCoinForOther = document.getElementsByName("prpLcompensateSumCoinForOther");
    var prpLcompensateSumCoinForOtherFee = document.getElementsByName("prpLcompensateSumCoinForOtherFee");
    var prpLcompensateSumCoinForOtherBak = document.getElementsByName("prpLcompensateSumCoinForOtherBak");
    var prpLcompensateSumCoinForOtherFeeBak = document.getElementsByName("prpLcompensateSumCoinForOtherFeeBak");
    if (type == 1) {
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
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
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
		var prpLclaimHospitalizedDays = fm.prpLcompensateHospitalizedDays;
	    // 取得輸入框的值
	    var input = undefined!=prpLclaimHospitalizedDays && null!=prpLclaimHospitalizedDays?prpLclaimHospitalizedDays.value:"";
	    // 移除所有非數字字符
	    prpLclaimHospitalizedDays.value = input.replace(/\D/g, '');
	}catch(e){}
}
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
</script>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="query.xianzhongName" />
						<%--险种名称--%>
					</td>
					<td class="right">${riskCName }</td>
					<td class="left">
						<s:text name="regist.prpLregist.damageTime"/><%--出險時間--%>
					</td>
					<td class="right">
						<rc:rcDate name="damageDate" value="${prpLcompensate.damageStartDate }" class="readonly"  readonly="true" wdatePicker="false" style="width:80px; "/>
						${prpLcompensate.damageStartHour }時${prpLcompensate.damageStartMinute }分
					</td>
					<td class="left">
					</td>
					<td class="right">
						<input type=button class="bigbutton" name="flowShow" value="賠案流程圖" title="賠案流程圖"  onclick="showWorkFlowerByClaimNo('${prpLcompensate.claimNo}')">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="endcase.calculationNumber" />
						<%--计算书号--%>
					</td>
					<td class="right">
						<input type=text name="prpLcompensateCompensateNo" title="計算書號" maxlength="22" class="readonly" readonly="true" value="${prpLcompensate.compensateNo}">
						<input type=hidden name="LFlag" title="理賠類型" maxlength="22" class="readonly" readonly="true" value="${prpLcompensate.lflag}">
					</td>
					<input type=hidden name="LFlag" title="理賠類型" maxlength="22" class="readonly" readonly="true" value="${prpLcompensate.lflag}">
					<td class="left">
						<s:text name="db.prpLclaim.damageAddress" />
					</td>
					<td class="left" colspan="2">
						<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 -->
						<input type=hidden name="prpLcompensateAddressCode" value="${prpLcompensate.addressCode }" />
						<input type="text" name="prpLcompensateDamageAddress" value="${prpLcompensate.damageAddress }" class="readonly" readonly="true" style="width: 90%;">
					</td>
					<td class="right">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />" onclick="backWardPolicy(fm.coreURL.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,fm.damageDate.value,fm.prpLcompensateComCode.value);">
						<%--出险时保单信息--%>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.claims" />
						<%--赔案号--%>
						<input type="hidden" name="prpLcompensateCaseNo" value="${prpLcompensate.caseNo}">
						<c:if test="${coinsFlag != null}">
							<input type="hidden" name="coinsFlag" value="${coinsFlag}">
						</c:if>
						<c:if test="${coinsFlag == null}">
							<input type="hidden" name="coinsFlag" value="0">
						</c:if>
						<input type="hidden" name="GenerateCompensateFlag" value="0">
						<input type="hidden" name="prpLcompensateClassCode" value="${prpLcompensate.classCode}">
						<input type="hidden" name="prpLcompensateRiskCode" value="${prpLcompensate.riskCode}">
						<input type="hidden" name="prpLcompensateDeductCond" value="${prpLcompensate.deductCond}">
						<input type="hidden" name="prpLcompensatePreserveDate" value="${prpLcompensate.preserveDate}">
						<input type="hidden" name="prpLcompensateCheckAgentCode" value="${prpLcompensate.checkAgentCode}">
						<input type="hidden" name="prpLcompensateCheckAgentName" value="${prpLcompensate.checkAgentName}">
						<input type="hidden" name="prpLcompensateSurveyorName" value="${prpLcompensate.surveyorName}">
						<input type="hidden" name="prpLcompensateCounterClaimerName" value="${prpLcompensate.counterClaimerName}">
						<input type="hidden" name="prpLcompensateDutyDescription" value="${prpLcompensate.dutyDescription}">
						<input type="hidden" name="prpLcompensateCurrency" value="${prpLcompensate.currency}">
						<input type="hidden" name="prpLcompensateSumLoss" value="${prpLcompensate.sumLoss}">
						<input type="hidden" name="prpLcompensateSumRest" value="${prpLcompensate.sumRest}">
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
						<input type="hidden" name="policyno" value="${prpLcompensate.policyNo }">
						<input type="hidden" name="registno" value="${prpLcompensate.registNo }">
						<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
						<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
						<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
						<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
						<input type="hidden" name="prpLcompensateStartDate" value="${prpLcompensate.startDate } 日 0 时 至 ${prpLcompensate.endDate } 日 24 时止">
						<input type="hidden" name="checkFlag" value="${checkFlag12}">
						<input type="hidden" name="prpLcompensateClauseName" value="${prpLcompensate.clauseName }">
						<input type="hidden" name="prpLcompensateLicenseNo" value="${prpLcompensate.licenseNo }">
						<input type="hidden" name="prpLcompensateCarKind" value="${prpLcompensate.carKind }">
						<input type="hidden" name="prpLcompensateLicenseColor" value="${prpLcompensate.licenseColor }">
						<input type="hidden" name="prpLcompensateBrandName" value="${prpLcompensate.brandName }">
						<input type="hidden" name="prpLcompensateEngineNo" value="${prpLcompensate.engineNo}">
						<input type="hidden" name="prpLcompensateFrameNo" value="${prpLcompensate.frameNo}">
						<input type="hidden" name="prpLcompensateDamageStartDate" value="${prpLcompensate.damageStartDate } 日 ${prpLcompensate.damageStartHour } 时 ${prpLcompensate.damageStartMinute } 分">
						<input type="hidden" name="prpLcompensateSumAmount" value="${prpLcompensate.sumAmount }">
						<input type="hidden" name="prpLcompensateSumClaim" value="${prpLcompensate.sumClaim }">
						<input type="hidden" name='payFee' value="${payFlag }">
						<input type="hidden" name='BaseCurrency1' value="${prpDexch.baseCurrency}">
						<input type="hidden" name='ExchRate1' value="${prpDexch.exchRate}">
						<input type="hidden" name="damageStartDate" value="${prpLcompensate.damageStartDate }">
						<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
						<input type="hidden" name="prpLclaimFamilyNo" value="${requestScope.familyNo}" >
						<input type="hidden" name="sumPaidAll" value="${prpLcompensate.sumPaidAll }">
						<input type="hidden" name="coreURL" value="${core_URL }">
						<!--添加标志位，用於提交表单时判断时否还有申请调查未提交。 2005-08-04-->
						<input type="hidden" name="AcciClaimFlag" value="<c:out value="com.sinosoft.acciFlag"/>">
						<%-- reason: 增加投保人的信息 --%>
						<input type="hidden" name="prpLcompensateAppliName" value="${prpLcompensate.appliName }">
						<%-- 增加 股东业务--%>
						<c:if test="${shareHolderFlag != null}">
							<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
						</c:if>
						<c:if test="${shareHolderFlag == null}">
							<input type="hidden" name="shareHolderFlag" value="0">
						</c:if>
					</td>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateClaimNo" readonly="true" value="${prpLcompensate.claimNo }">
					</td>
					<td class="left">
						<%--出險原因 --%>
						<s:text name="db.prpLclaim.damageName" />
					</td>
					<td class="right" colspan="2">
						<input type="text" name="prpLcompensateDamageCode" value="${prpLcompensate.damageCode}" style="width: 10%;" class="readonly" readonly="true">
						<input type="text" name="prpLcompensateDamageName" value="${prpLcompensate.damageName }" style="width: 85%;"  class="readonly" readonly="true">
					</td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.view_larrearage.policyNo" />
						<%--保单号--%>
					</td>
					<td class="right">
						<input type=text name="prpLcompensatePolicyNo" class="readonly" readonly="true" value="${prpLcompensate.policyNo }" style="width: 180px">
						<input type="image" name="btRelate" src="${ctx }/images/butRelate.gif" align="middle" height="17" border="0" onclick="relate(fm.prpLcompensatePolicyNo.value);return false;">
					</td>
					<c:choose>
					   <c:when test="${not empty requestScope.prpLcompensate.mutualCompensateNo || not empty mutualCompensateNoList}">
						  <td class="left">互沖計算書號碼</td>
						  <td class="right">
							  <c:choose>
							     <c:when test="${param.editType=='ADD'}">
							        <select name="prpLcompensateMutualCompensateNo" onchange="getMutualCompe(this);">
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
					      <td class="left"></td>
					      <td class="right"></td>
					   </c:otherwise>
					</c:choose>
					<td class="left"></td>
					<td class="right"></td>
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
						<s:text name="certainLoss.prpLcheck.insuredName" />
					</td>
					<%--被保险人--%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateInsuredName" readonly="true" value="${prpLcompensate.insuredName }" style="width: 50%;">
						<c:if test="${prpCinsuredBearer=='1'}"></br>(不記名保單 承保人數:${prpCinsured.insurantNumber})</c:if>
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.claimTimes" />
					</td>
					<%--赔付次数--%>
					<td class="right">
						<input type="text" name="prpLcompensateTimes" class="readonly" readonly value="${prpLcompensate.times}">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<%--已出险次数--%>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/common/regist/ExistRegist.jsp"%>
					</td>
				</tr>
				<c:if test="${coinsFlag=='1'}">
					<tr>
						<td class="left">
							<s:text name="commonAcci.compensate.whetherPaidReparat" />
						</td>
						<%--是否代付赔款--%>
						<td class="right">
							<input type="radio" onclick="changeFinallyFlag(1);" name="isPayForOther" <c:if test="${prpLcompensate.isPayForOther=='1' }">checked</c:if> value="1">
							<s:text name="certainLoss.thirdCarLoss.yes" /><%--是--%>
							<input type="radio" onclick="changeFinallyFlag(0);" name="isPayForOther" <c:if test="${prpLcompensate.isPayForOther=='0' }">checked</c:if> value="0">
							<s:text name="certainLoss.thirdCarLoss.no" /><%--否--%>
						</td>
						<td class="left" colspan="4"></td>
					</tr>
				</c:if>
				<%--重开赔案:目前是非重开赔案设为非案终计算书，重开赔案就设为案终计算书    --%>
				<c:choose>
					<c:when test="${recaseFlag=='0'||param.editType=='SHOW'}">
						<input type="hidden" name="prpLcompensateFinallyFlag" value="0">
					</c:when>
					<c:otherwise>
						<%--//重开赔案暂时都为手工结案 --%>
						<input type="hidden" name="prpLcompensateFinallyFlag" value="0">
					</c:otherwise>
				</c:choose>
				</tr>
				<tr>
					<td class="left">
						<s:text name="commonAcci.compensate.receiveCustomerTime" />
						<%--接收客户索赔申请时间--%>
					</td>
					<td class="right">
						<rc:rcDate name="startApplyPayDate" value="${prpLclaim.startApplyPayDate}" />
					</td>
					<td class="left">零結賠案不計次</td>
					<td class="right">
						<s:select name="prpLcompensateNoPaidClaim" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.prpLcompensate.noPaidClaim"></s:select>
					</td>
					<td class="left">理算文件備齊日</td>
					<td class="right">
						<rc:rcDate name="prpLcompensateFileReadyDate" format="yyyy-MM-dd HH:mm" value="${prpLcompensate.fileReadyDate }" />
						<!--mantis： CLM0119 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0119.新核心-必填欄位文件齊備日 -->
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="commonAcci.claim.involvedLitigat" />
					</td>
					<%--是否涉及诉讼--%>
					<td class="right">
						<c:choose>
							<c:when test="${prpLclaim.referLawFlag==null||prpLclaim.referLawFlag==''||prpLclaim.referLawFlag=='0'||prpLclaim.referLawFlag=='1'}">
								<s:select name="referLawFlag" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.prpLclaim.referLawFlag"></s:select>
							</c:when>
							<c:otherwise>
								<s:select disabled="true" name="referLawFlag" list="#{'1':'是','2':'是','3':'是','4':'是','5':'是','6':'是'}" listKey="key" listValue="value" value="#attr.prpLclaim.referLawFlag"></s:select>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="left">
						<s:text name="claim.possibleRec" />
					</td>
					<%--是否可能有追偿--%>
					<td class="right">
						<c:choose>
							<c:when test="${prpLclaim.replevyFlag==null||prpLclaim.replevyFlag==''||prpLclaim.replevyFlag=='0'||prpLclaim.replevyFlag=='1'}">
								<s:select name="replevyFlag" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.prpLclaim.replevyFlag"></s:select>
							</c:when>
							<c:otherwise>
								<s:select disabled="true" name="replevyFlag" list="#{'1':'是','2':'是','3':'是','4':'是','5':'是','6':'是'}" listKey="key" listValue="value" value="#attr.prpLclaim.replevyFlag"></s:select>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="left"><s:text name="title.compensateEdit.speedFlag"/>：<%-- 赔款速度  --%></td>
					<td class="right"><s:select name="prpLcompensateSpeedFlag" value="#request.prpLcompensate.speedFlag" list="#request.speedFlagList" listKey="key" listValue="value" ></s:select></td>
				</tr>
				<tr>
					<td class="left">
						追償說明：
					</td>
					<td class="right" colspan="3">
						<input name="prpLcompensateReplevyRemark" class="common" value="${prpLclaim.replevyRemark }">
					</td>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
					<c:if test="${param.riskCode == 'PA'}">
						<td class="left">本次住院天數:</td>
						<td class="right"><input name="prpLcompensateHospitalizedDays" class="common" value="${prpLcompensate.hospitalizedDays }" maxlength="3" onblur="removeNonNumeric(event)"></td>
					</c:if>
					<c:if test="${param.riskCode != 'PA'}">
						<td class="left"></td>
						<td class="right"></td>
					</c:if>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END -->
				</tr>
				<input type="hidden" name="prpLcompensateIndemnityDuty" style="width: 100px" class="readonly" readonly="true" value="${prpLcompensate.indemnityDuty }">
				<input type=hidden name="prpLcompensateIndemnityDutyName" style="width: 100px" class="readonly" readonly="true" value=" ">
				<input type="hidden" name="prpLcompensateIndemnityDutyRate" style="width: 100px" class="readonly" readonly="true" value="0">
				<tr>
					<td class="left">
						<s:text name="check.claimType" />
					</td>
					<%--案件性质--%>
					<td class="right">
						<!--原因：修改案件类型-->
						<input name="prpLcompensateClaimType" type="hidden" value="${prpLcompensate.claimType }">
						<input name="prpLcompensateClaimTypeName" type="text" class="readonly" readonly="true" value="${prpLcompensate.claimTypeName }">
						<input name="prpLcompensateCaseType" type="hidden" value="${prpLcompensate.caseType }">
						<input name="prpLcompensateCaseTypeName" type="hidden" class="readonly" readonly="true" value="${prpLcompensate.caseTypeName }">
					</td>
					<td class="left">
						<s:text name="commonAcci.compensate.costInvestigatTotal" />
					</td>
					<%--调查费用合计--%>
					<td class="right">
						<input class="readonly" type=text name="sumCheckFee" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumCheckFee}' pattern='#'/>">
					</td>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
					<c:if test="${param.riskCode == 'PA'}">
						<td class="left">本次事故累計住院天數(不含本次):</td>
						<td class="right"><input name="prpLcompensateSumHospitalizedDay" readonly="true" class="common" value="${prpLcompensate.sumHospitalizedDay }"></td>
					</c:if>
					<c:if test="${param.riskCode != 'PA'}">
						<td class="left"></td>
						<td class="right"></td>
					</c:if>
					<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END -->
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
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