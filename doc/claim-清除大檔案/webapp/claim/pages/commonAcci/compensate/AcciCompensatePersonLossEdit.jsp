<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2004-06-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function getKindCodeForAcci(field, codeType, codeRelation, isClear, isQueryCode){
		var policyno = fm.policyno.value;
		var damageDate = fm.damageStartDate.value;
		var damageHour = fm.damageStartHour.value;
		var $personLossTr = $(field).closest("tr[name='prpLpersonLossTr']");
		var $familyNo = $personLossTr.find(":input[name='prpLpersonLossFamilyNo']");
		if($familyNo.length > 0){
			var familyNo = $familyNo.val();
			if(familyNo.length == 0 || parseInt(familyNo)==0){
				familyNo = fm.prpLclaimFamilyNo.value;
			}
			code_CodeSelect(field,codeType,codeRelation,isClear,isQueryCode,policyno+'|'+damageDate+'|'+damageHour+'|'+familyNo);
		}
	}
</script>
<%--险种信息 --%>
<c:forEach var="prpCitemKindTemp" items="${prpCitemKindList}" varStatus="prpCitemKind_status">
<span style="display: none" name="span_prpCitemKind">
	<input type="hidden" name="itemKindNo" value="${prpCitemKindTemp.id.itemKindNo}">
	<input type="hidden" name="familyno" value="${prpCitemKindTemp.familyNo }">
	<input type="hidden" name="hisKind"  value="${prpCitemKindTemp.kindCode }">
	<input type="hidden" name="hisItem" value="${prpCitemKindTemp.itemCode}">
	<input type="hidden" name="hisPaid" value="${prpCitemKindTemp.hisPaid}">
	<input type="hidden" name="amount" value="<fmt:formatNumber pattern='#' value='${prpCitemKindTemp.amount}'/>"><%-- 保额 --%>
	<input type="hidden" name="dayAmount" value="${prpCitemKindTemp.dayAmount}"><%-- 日额 --%>
	<input type="hidden" name="coverageratio" value="${prpCitemKindTemp.coverageratio}"> <%-- 赔付倍数 --%>
	<input type="hidden" name="currAmount" value="${prpCitemKindTemp.unitAmount }">
	<input type="hidden" name="commodityCode" value="${prpCitemKindTemp.commodityCode }"> <%-- 商品代号 --%>
	<input type="hidden" name="contractingScope" value="${prpCitemKindTemp.contractingScope }"> <%-- 承保范围 --%>
</span>
</c:forEach>
<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START -->
<!-- TA海突倍率 -->
<c:forEach var="prpDpolicyRulesTemp" items="${prpDpolicyRulesList}" varStatus="prpDpolicyRules_status">
<span style="display: none" name="span_prpDpolicyRules">
	<input type="hidden" name="init_data_rulCode" value="${prpDpolicyRulesTemp.id.codeCode}">
	<input type="hidden" name="init_data_rulKind"  value="${prpDpolicyRulesTemp.kindCode }">
	<input type="hidden" name="init_data_rulMultiplier" value="${prpDpolicyRulesTemp.multiplier}">
</span>
</c:forEach>
<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END -->
<%--被保险人信息 --%>
<c:if test="${prpLcompensate.riskCode!='TE'}">
	<c:forEach var="prpCinsured" items="${prpCinsuredList}" varStatus="prpCinsured_status">
		<span style="display: none" name="span_prpCinsured">
			<input type="hidden" name="prpCinsuredInsuredFlag" value="${prpCinsured.insuredFlag }"/><%--1-被保险人，2-投保人 --%>
			<input type="hidden" name="prpCinsuredInsuredCode" value="${prpCinsured.insuredCode }"/>
			<input type="hidden" name="prpCinsuredInsuredName" value="${prpCinsured.insuredName }"/>
			<input type="hidden" name="prpCinsuredSex" value="${prpCinsured.prpCinsuredNature.sex }"/>
			<input type="hidden" name="prpCinsuredAge" value="${prpCinsured.prpCinsuredNature.age }"/>
			<input type="hidden" name="prpCinsuredSerialNo" value="${prpCinsured.id.serialNo }"/>
			<input type="hidden" name="prpCinsuredIdentifyNumber" value="${prpCinsured.identifyNumber }"/>
		</span>
	</c:forEach>
</c:if>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLpersonLossFractureSite'] , :input[name='prpLpersonLossFractureDegree']").each(function(){
			$(this).bind("change" , function(e){
				$(this).prop("title" , $(this).children("option:selected").text());
			});
			$(this).triggerHandler("change");
		});
		if($.browser.msie && $.browser.version < 9){
			$(":input[name='prpLpersonLossFractureSite']").seltoul( 320, 185);
			$(":input[name='prpLpersonLossFractureDegree']").seltoul(110, 70);
		}
		

		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
		var $prpLpersonLossKindCode = $("input[name='prpLpersonLossKindCode']");
		$prpLpersonLossKindCode.each(function(){
			if($(this).val()=="TR47"){
				$(this).blur();
				//var amount = getAmount(field);
				//$tr.find("input[name='prpLpersonLossAmount']").val(amount);
			}
		});
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
	})
</script>
<span style="display: none">
	<table class="common" style="display: none" id="PersonFeeLoss_Data" name="PersonFeeLoss" cellspacing="1" cellpadding="0">
		<tbody>
			<tr name="prpLpersonFeeLossTr">
				<td style="width: 100%" colspan="15" >
					<table style="width: 100%;" cellspacing="1" cellpadding="0" class="common">
						<thead>
							<col style="width: 15%;">
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 6%;">
							<col style="width: 10%;">
							<col style="width: 5%;">
							<col style="width: 10%;">
							<col style="width: 6%;">
							<col style="width: 6%;">
							<col style="width: 5%;">
							<col style="width: 6%;">
							<col style="width: 9%;">
							<col style="width: 4%;">
							<col style="width: 3%;">
						</thead>
						<tr class="inputsubsub" name="prpLpersonFeeLossPaymentTr">
							<td class="inputsubsub" >
								<input type="hidden" name="prpLpersonLossPersonNo"  value="0">
								<input type="text" class="codecode" style="width: 25%;" name="prpLpersonLossKindCode"
									value="" maxlength=20 description="险别"
									ondblclick="getKindCodeForAcci(this,'policyItemKindCodeForAcci','0,1,2,3','Y','Y' );"
									onkeyup="getKindCodeForAcci(this,'policyItemKindCodeForAcci','0,1,2,3','Y','Y' );"
									onchange="getKindCodeForAcci(this,'policyItemKindCodeForAcci','0,1,2,3','Y','Y' );"
									onblur="setPaymentType_24_30(this);"
									>
								<input type="text" name="prpLpersonLossKindName" class="codename" style="width: 65%;" value=""
									ondblclick="getKindCodeForAcci(this,'policyItemKindCodeForAcci','-1,0,1,2','Y','N' );"
									onkeyup="getKindCodeForAcci(this,'policyItemKindCodeForAcci','-1,0,1,2','Y','N' );"
									onchange="getKindCodeForAcci(this,'policyItemKindCodeForAcci','-1,0,1,2','Y','N' );"
									onblur="setPaymentType_24_30(this);"
									>
								<input type="hidden" name="prpLpersonLossContractingScope" value="">
								<input type="hidden" name="prpLpersonLossItemKindNo" value="">
							</td>
							<td class="inputsubsub" >
								<input name="prpLpersonLossPaymentType" type="text" value="" onkeyup="queryPaymentType(this,'paymentType');" onfocus="setCacheValue(this);queryPaymentType(this,'paymentType');"  onblur="isPaymentType(this,'paymentType');" class="input"  style="width: 95%;">
							</td>
							<td class="inputsubsub" >
								<input name="prpLpersonLossPaymentType1" type="text" value="" onkeyup="queryPaymentType(this,'paymentType1');" onfocus="setCacheValue(this);queryPaymentType(this,'paymentType1');"  onblur="isPaymentType(this,'paymentType1')" class="input"  style="width: 95%;">
							</td>
							<td class="inputsubsub" >
								<input name="prpLpersonLossPaymentType2" type="text" value="" onkeyup="queryPaymentType(this,'paymentType2');" onfocus="setCacheValue(this);queryPaymentType(this,'paymentType2');"  onblur="isPaymentType(this,'paymentType2')"  class="input"  style="width: 95%;">
							</td>
							<td class="inputsubsub" >
								<%--残疾给付比例 --%>
								<input name="prpLpersonLossPaymentRate" type="hidden" value="0" class="common"  style="width: 95%;">
								<input name="prpLpersonLossPaymentContent" type="hidden" value="" class="input"  style="width: 95%;">
								<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 -->
								<input type="text" name="prpLpersonLossAmount" value="<fmt:formatNumber pattern='#' value='${prpLcompensate.sumAmount }'/>" readonly="readonly" class="readonly" style="width: 95%;">
							</td>
							<td class="inputsubsub">
								<select	name="prpLpersonLossFractureSite" onchange="sumHospitalDays(this);" style="width: 95%;" >
									<option value="" title=""></option>
									<c:forEach items="${fractureSiteList}" var="fractureSiteTemp">
										<option value="${fractureSiteTemp.id.fractureCode }" title="${fractureSiteTemp.fractureRate}">${fractureSiteTemp.fractureName}</option>
									</c:forEach>
								</select>
							</td>
							<td class="inputsubsub" >
								<input name="prpLpersonLossNotHospitalDays" type="text" value="" class="input" onchange="sumRealPay(this);" style="width: 95%;">
							</td>
							<td class="inputsubsub" >
								<select	name="prpLpersonLossFractureDegree" onchange="sumRealPay(this);" style="width: 95%;">
									<option value="" title=""></option>
									<c:forEach items="${fractureDegreeList}" var="fractureDegreeTemp">
										<option value="${fractureDegreeTemp.id.fractureCode }" title="${fractureDegreeTemp.fractureRate}">${fractureDegreeTemp.fractureName}</option>
									</c:forEach>
								</select>
							</td>
							<td class="inputsubsub" >
								<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核  (理賠)-->
								<input type="text" name="prpLpersonLossSumRealPay" onchange="checkBeyondSumAmount();countSumRealPay(this);" value="" class="input" style="width: 95%;">
								<input type="hidden" name="prpLpersonLossSumLoss" value="" >
								<input type="hidden" name="prpLpersonLossSumRest" value="" >
								<input type="hidden" name="prpLpersonLossDeductible"  value="" >
								<input type="hidden" name="prpLpersonLossClaimRate"   value="" >
								<input type="hidden" name="prpLpersonLossUnitAmount" value="">
								<input type="hidden" name="prpLpersonLossLossQuantity" value="">
								<input type="hidden" name="prpLpersonLossIndemnityDutyRate" value="">
								<input type="hidden" name="prpLpersonLossDeductibleRate" value="">
								<input type="hidden" name="prpLpersonLossLiabCode" value="">
								<input type="hidden" name="prpLpersonLossLiabName" value="">
								<input type="hidden" name="prpLpersonLossJobCode" value="">
								<input type="hidden" name="prpLpersonLossJobName" value="">
								<input type="hidden" name="prpLpersonLossItemAddress" value="">
								<input type="hidden" name="prpLpersonLossUnit" value="">
								<input type="hidden" name="prpLpersonLossCurrency2" value="${LOCAL_CURRENCY }">
								<input type="hidden" name="prpLpersonLossCurrency1" value="${LOCAL_CURRENCY }">
								<input type="hidden" name="prpLpersonLossItemValue" value="">
								<input type="hidden" name="prpLpersonLossCurrency4" value="${LOCAL_CURRENCY }">
								<input type="hidden" name="prpLpersonLossFlag" value="">
								<input type="hidden" name="prpLpersonLossCurrency3" value="${LOCAL_CURRENCY }">
							</td>
							<td class="inputsubsub" >
								<select name="prpLpersonLossCurrency" class="input" style="width: 50px" onchange="getPrpLpersonLossExchRate(this);">
									<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
										<option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}" /></option>
									</c:forEach>
								</select>
							</td>
							<td class="inputsubsub" >
								<input type="text" name="prpLpersonLossExchRate" value="1" class="input" onchange="setObjectInfoExchRate(this);" style="width: 95%;">
							</td>
							<td class="inputsubsub" >
								<input type="text" name="prpLpersonLossSumRealPayNTD"  value="" class="input" style="width: 95%;" readonly>
							</td>
							<td class="inputsubsub" > 
								<input type="text" class='input' name="prpLpersonLossPayObjectSerialNo" readonly="readonly"
									onclick="setPrpObjectinfoSerialNo(this);" description="賠付對象訊息" style="width: 95%;" >
							</td>
							<td class="inputsubsub" > 
								<s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" ></s:select>
							</td>
							<td class="inputsubsub" >
								<div align="center">
									<input type=button class="smallbutton" name="buttonPersonFeeLossDelete" onclick="deletePrpLpersonFeeLossObject(this);countSumRealPay();" value="-" readonly style="cursor: hand">
								</div>
							</td>
						</tr>
						<tr name="prpLpersonFeeLossDeathTr">
							<td class="common">
								死亡日期
							</td>
							<td colspan="3" class="inputsubsub">
								<rc:rcDate name="prpLpersonLossDeathDate" style="width:80%;" class="common"/>
							</td>
							<td class="common">
								死亡地點
							</td>
							<td class="common" colspan="2">
								<input type="text" name="prpLpersonLossDeathAddressCode" type="hidden" value="" class="common">
								<input type="text" name="prpLpersonLossDeathAddressName" title="選擇地域名" class="codename"  onkeyup="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" value=""/>
							</td>
							<td colspan="2" class="common">
								檢察署名稱
							</td>
							<td colspan="6" class="inputsubsub" >
								<s:select name="prpLpersonLossProsecutorsOffice" list="#request.prosecutorsOfficeList" listKey="key" listValue="value" class="common" style="width: 100%;"></s:select>
							</td>
						</tr>
						<tr name="prpLpersonFeeLossDeathPlaceTr">
							<td class="common">
								死亡場所
							</td>
							<td colspan="3" class="inputsubsub">
								<s:select name="prpLpersonLossDeathPlace" list="#request.deathPlaceList" listKey="key" listValue="value" class="common" style="width: 100%;"></s:select>
							</td>
							<td class="common">
								死亡方式
							</td>
							<td class="inputsubsub" colspan="2">
								<s:select name="prpLpersonLossDeathManner" list="#request.deathMannerList" listKey="key" listValue="value" class="common" style="width: 100%;"></s:select>
							</td>
							<td class="common" colspan="2">
								檢察官姓名
							</td>
							<td colspan="5" class="inputsubsub">
								<input  name="prpLpersonLossProsecutor" type="text" value="" class="common" style="width: 60%;">
							</td>
							<td class="inputsubsub"></td>
						</tr>
						<tr name="prpLpersonFeeLossDeathCertificateTr">
							<td class="common" colspan="5" align="right" >
								證明書開立日期
							</td>
							<td class="inputsubsub" colspan="2">
								<rc:rcDate name="prpLpersonLossDeathCertificateDate" style="width:90%;"/>
							</td>
							<td class="common" colspan="2">
								法醫師／檢驗名
							</td>
							<td class="inputsubsub" colspan="5">
								<input name="prpLpersonLossCourtDoctor" type="text" value="" class="common" style="width:50%;">
							</td>
							<td class="inputsubsub"></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<%--被保险人赔付信息--%>
<span style="display: none">
	<table class="common" style="display: none" id="PersonLoss_Data" name="PersonLoss" cellspacing="1" cellpadding="0">
		<tbody>
			<tr name="prpLpersonLossTr">
				<td class="input" style="width: 4%">
					<div align="center">
						<input type="text" class="readonly" readonly name="personLossPersonNo" description="序號" value="">
					</div>
				</td>
				<td class="subformtitle" style="width: 92%">
					<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
						<tbody>
							<tr>
								<td class="title" style="width: 15%;">
									<s:text name="claim.dangeSerialNum" />
								</td>
								<%--危险单位序号--%>
								<td class="input" style="width: 5%;">
									<input type=text name="prpLpersonLossDangerNo" class="codecode" value="1" style="width: 63%"
										onClick="viewDangerUnitPersonLoss(this);" onchange="viewDangerUnitPersonLoss(this);" onkeyup="viewDangerUnitPersonLoss(this);">
										<input type="hidden" name="prpLpersonLossFamilyNo" value="${prpLclaim.familyNo}">
										<input type="hidden" name="prpLpersonLossFamilyName" value="${prpLclaim.insuredName }">
										<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
										<input type="hidden" name="prpLpersonLossAmountHit" value="${prpLcompensate.sumAmount }">
										<input type="hidden" name="prpLpersonLossPaf4SumLossHit" value="${prpLcompensate.paf4SumLoss }">
										<input type="hidden" name="PAF456_SUMLOSS" value="${requestScope.PAF456_SUMLOSS}">
										<input type="hidden" name="PAF5_SUMLOSS" value="${requestScope.PAF5_SUMLOSS}">
										<input type="hidden" name="PAF6_SUMLOSS" value="${requestScope.PAF6_SUMLOSS}">
										<input type="hidden" name="PAF5_AMOUNT" value="${requestScope.PAF5_AMOUNT}">
										<input type="hidden" name="PAF6_AMOUNT" value="${requestScope.PAF6_AMOUNT}">
										<input type="hidden" name="PAF7_AMOUNT" value="${requestScope.PAF7_AMOUNT}">
										
										<%-- 隐藏域 begin 暫時留下 用在可能需要整個PRPLPERSONLOSS帶出 --%>
										<c:forEach var="prpCitemKind" items="${damageKindList}">
										<span name="spanPayoutTime" style="display: none;">
											<input type='hidden' name='${prpCitemKind.familyName}${prpCitemKind.kindCode}${prpCitemKind.itemCode}' value='${prpCitemKind.amount}'>
											<input type="hidden" name="${prpCitemKind.kindCode}_${prpCitemKind.id.itemKindNo}" value="${prpCitemKind.amount}" />
											<input type="hidden" name="${prpCitemKind.kindCode}" value="${prpCitemKind.familyNo}" />
											<input type="hidden" name="payoutTime" value="${prpCitemKind.coverageratio}" />
										</span>
										</c:forEach>
										<%-- 以familyName+kindCode作为name名称添加保额隐藏域 end --%>
										<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END -->
								</td>
								<td class="title" style="width: 10%;">
									人員姓名
								</td>
								<%--姓名--%>
								<td class="input" style="width: 10%;">
									<input type="text"  style="width: 100%;" name="prpLpersonLossPersonName" value="" maxlength=120 description="人員姓名"
									 <c:if test="${prpLcompensate.riskCode=='TE' }">
									 	class="codename"  ondblclick="code_CodeSelect(this,'PrpCinsuredAcci','0,1,2,3','Y','N',fm.prpLcompensatePolicyNo.value);" 
									 </c:if>
									 class='input'
									 />
								</td>
								<td class="title" style="width: 5%">
									<s:text name="db.prpLperson.personSex" />
								</td>
								<%--性别--%>
								<td class="input" style="width: 10%">
									<select name="prpLpersonLossSex" description="性別" onchange="checkPersonIdentifyNumber(this);" style="width: 90%">
										<option value="1">
											<s:text name="certainLoss.male" />
										</option>
										<%-- 男 --%>
										<option value="2">
											<s:text name="certainLoss.female" />
										</option>
										<%-- 女 --%>
									</select>
								</td>
								<td class="title" style="width: 5%;">
									<s:text name="db.prpLperson.personAge" />
								</td>
								<%--年龄--%>
								<td class="input" style="width: 10%;">
									<input type="text" name="prpLpersonLossAge" style="width: 100%;" value="" maxlength="3" description="年齡" class="input">
								</td>
								<td class="title" style="width: 10%">
									<s:text name="db.prpCinsured.identifyNumber" />
								</td>
								<%--身份证号--%>
								<td class="input">
									<input type="text" name="prpLpersonLossIdentifyNumber" value="" onchange="checkPersonIdentifyNumber(this);" class='input' style="width: 100%;"  maxlength=20 description="身份證字號">
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 15%;">
									<s:text name="commonAcci.compensate.biggestPayout" />
								</td>
								<%--最大赔付额--%>
								<td class="input" colspan="2" style="width: 15%;">
									<input type="text" name="prpLpersonLossMaxPaid" value="0" class='readonly'  style="width: 100%;" readonly  maxlength=20 description="最大賠付額">
								</td>
								<td class="title"  style="width: 10%;">
									<s:text name="commonAcci.compensate.historicalPayout" />
								</td>
								<%--历史赔付额--%>
								<td class="input" colspan="2" style="width: 15%;">
									<input type="text" name="prpLpersonLossHisPaid" value="0"  style="width: 100%;" class='readonly' readonly maxlength="3" description="歷史賠付額">
								</td>
								<td class="title" colspan="2" style="width: 15%;">
									本次賠付合計
								</td>
								<td class="input" colspan="2">
									<input type="text" name="prpLpersonLossSumRealPay1" value=""  style="width: 100%;" class='readonly' readonly maxlength="3" description="歷史賠付額">
								</td>
							</tr>
							<tr>
								<td colspan="10" style="width: 100%">
									<table id="PersonHospital" name="PersonHospital" style="width: 100%;" cellspacing="1" cellpadding="0">
										<tbody>
											<tr name="prpLpersonHospitalTr" >
												<td style="width: 100%" class="subformtitle">
													<table style="width: 100%;" cellspacing="0" cellpadding="0">
														<tr>
															<td class="title" style="width: 15%;">
																就診醫院
															</td>
															<td class="input" colspan="6" style="width: 45%;">
																<input type="text" name="prpLpersonHospitalHospitalCode"  value="" onkeyup="getHospital(this,'codeCode','0,1')" onfocus="setCacheValue(this);" onblur="isHospital(this,'codeCode');"  style="width: 45%;" class='input' maxlength=20 description="醫院代號"/>
																<input type="text" name="prpLpersonHospitalHospitalName"  value="" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 45%;" class='input' maxlength="3" description="醫院名稱"/>
															</td>
															<td class="title" style="width: 10%;">
																出入院日期
															</td>
															<td class="input"  colspan="2">
																<input type="hidden" name="hospitalPersonNo" value="-1">
																<rc:rcDate name="prpLpersonHospitalInHospDate" value="" title="入院日期" style="width: 35%;"/>
																<rc:rcDate name="prpLpersonHospitalOutHospDate" value="" title="出院日期" style="width: 35%;"/>
																<input type="button" name="buttonHospitalInsert" class="smallbutton" value="+" onclick="insertPersonHospitalObject(this);"  readonly style="cursor: hand;width: 8%;">
																<input type="button" name="buttonHospitalDelete" class="smallbutton" value="-" onclick="deletePersonHospitalObject(this);" readonly style="cursor: hand;width: 8%;">
															</td>
														</tr>
														<tr>
															<td class="title" style="width: 15%;">
																醫師姓名
															</td>
															<td class="input"  style="width: 15%;">
																<input type="text"  name="prpLpersonHospitalDoctor" value="" class="input"  style="width: 100%;"  description="醫師姓名">
															</td>
															<td class="title" style="width: 10%;">
																診斷科別
															</td>
															<td class="input"  colspan="4" style="width: 20%;">
																<input type="text"  name="prpLpersonHospitalDiagnosisDivision"  value="" style="width: 100%;" class="input"  description="診斷科別">
															</td>
															<td class="title" style="width: 10%;">
																診斷名稱
															</td>
															<td class="input" colspan="2">
																<input type="text"  name="prpLpersonHospitalDiagnosisName" value="" style="width: 80%;" class="input"  description="診斷名稱">
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 15%;">
									警員姓名
								</td>
								<td class="input" colspan="2" style="width: 15%;">
									<input type="text" name="prpLpersonLossPoliceName"  value="" class="input"  style="width: 100%;" maxlength=20 description="警員姓名">
								</td>
								<td class="title" style="width: 10%;">
									警方單位
								</td>
								<td class="input"  colspan="3" style="width: 20%;">
									<input type="text" name="prpLpersonLossPoliceUnits" value="" style="width: 100%;" class="input" maxlength="3" description="警方單位">
								</td>
								<td class="title" style="width: 10%;">
								</td>
								<td class="input" colspan="2">
								</td>
							</tr>
							<tr>
								<td colspan="10" style="width: 100%">
									<span id="spanPersonFeeLoss"> <%-- 多行输入展现域 --%>
										<table id="PersonFeeLoss" name="PersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0">
											<thead>
												<tr>
													<td class="subformtitle" colspan="15">
														賠付訊息
													</td>
												</tr>
												<tr>
													<td class="centertitle" style="width: 15%;">
														險別
													</td>
													<td class="centertitle" style="width: 5%;">
														給付類別
													</td>
													<td class="centertitle" style="width: 5%;">
														給付內容1
													</td>
													<td class="centertitle" style="width: 5%;">
														給付內容2
													</td>
													<td class="centertitle" style="width: 6%;">
														保險金額
													</td>
													<td class="centertitle" style="width: 10%;">
														骨折部位
													</td>
													<td class="centertitle" style="width: 5%;">
														未住院日數
													</td>
													<td class="centertitle" style="width: 10%;">
														骨折程度
													</td>
													<td class="centertitle" style="width: 6%;">
														賠付金額
													</td>
													<td class="centertitle" style="width: 6%;">
														賠償幣別
													</td>
													<td class="centertitle" style="width: 5%;">
														匯率
													</td>
													<td class="centertitle" style="width: 6%;">
														賠償金額（NTD）
													</td>
													<td class="centertitle" style="width: 9%;">
														賠付對象訊息
													</td>
													<td class="centertitle" style="width: 4%;">
														保留預估
													</td>
													<td class="centertitle" style="width: 3%;">
														&nbsp;
													</td>
												</tr>
											</thead>
											<tfoot>
												<tr>
													<td class="titlesubsub" colspan="14" style="width: 93%"></td>
													<td class="title" align="right" style="width: 4%">
														<div align="center">
															<input type="button" class="smallbutton" value="+" onclick="insertPrpLpersonFeeLossObject(this);"
																name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
														</div>
													</td>
												</tr>
											</tfoot>
											<tbody>
											</tbody>
										</table>
									</span>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td class="input" style="width: 4%">
					<div align="center">
						<input type=button class="smallbutton" name="buttonPersonDelete" onclick="deletePrpLpersonLossObject(this);countSumRealPay();" value="-" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr class="common">
		<td colspan="4" align="left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="PersonImg" onclick="showPage(this,spanPerson)">
			<s:text name="commonAcci.compensate.insuredPayInfo" />
			<br>
			<span id="spanPerson" style="display: none">
				<table id="PersonLoss" name="PersonLoss" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="subformtitle" style="width: 4%">
								<s:text name="db.prpLmedicine.serialNo" />
							</td>
							<%--序号--%>
							<td class="subformtitle" style="width: 96%" colspan=2>
								<s:text name="db.prpLregistText.context" />
							</td>
							<%--内容--%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">
								<s:text name="prompt.schedule.addRename12" />
							</td>
							<%--(按"+"号键增加险别信息，按"-"号键删除险别信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" class="smallbutton" value="+" onclick="insertPrpLpersonLossObject();" name="buttonPersonInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:set var="personNo" value="1" scope="page"/>
						<c:forEach var="prpLpersonLossTemp" items="${prpLpersonLoss.prpLpersonLossList}" varStatus="prpLpersonLoss_status">
							<c:if test="${prpLpersonLossTemp.personNo==personNo}">
								<tr name="prpLpersonLossTr">
									<td class="input" style="width: 4%">
										<div align="center">
											<input type="text" class="readonly" readonly name="personLossPersonNo" description="序號" value="${prpLpersonLossTemp.personNo }">
										</div>
									</td>
									<td class="subformtitle" style="width: 92%">
										<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
											<tbody>
												<tr>
													<td class="title" style="width: 15%;">
														<s:text name="claim.dangeSerialNum" />
													</td>
													<%--危险单位序号--%>
													<td class="input" style="width: 5%;">
														<input type=text name="prpLpersonLossDangerNo" class="codecode" value="${prpLpersonLossTemp.dangerNo }" style="width: 63%"
															onClick="viewDangerUnitPersonLoss(this);" onchange="viewDangerUnitPersonLoss(this);" onkeyup="viewDangerUnitPersonLoss(this);">
															<input type="hidden" name="prpLpersonLossFamilyNo" value="${prpLpersonLossTemp.familyNo}">
															<input type="hidden" name="prpLpersonLossFamilyName" value="${prpLpersonLossTemp.familyName}">
													</td>
													<td class="title" style="width: 10%;">
														人員姓名
													</td>
													<td class="input" style="width: 10%;">
														<input type="text" style="width: 100%;" name="prpLpersonLossPersonName" value="${prpLpersonLossTemp.personName}" maxlength=120 description="人員姓名"
														 <c:if test="${prpLcompensate.riskCode=='TE' }">
														 	class="codename"  ondblclick="code_CodeSelect(this,'PrpCinsuredAcci','0,1,2,3','Y','N',fm.prpLcompensatePolicyNo.value);" 
														 </c:if>
														 class='input'
														>
													</td>
													<td class="title" style="width: 5%">
														<s:text name="db.prpLperson.personSex" />
													</td>
													<%--性别--%>
													<td class="input" style="width: 10%">
														<select name="prpLpersonLossSex" description="性別" onchange="checkPersonIdentifyNumber(this);">
															<option value="1" <c:if test="${fn:trim(prpLpersonLossTemp.sex)=='1'}"><c:out value="selected"/></c:if>>
																<s:text name="certainLoss.male" />
															</option>
															<%-- 男 --%>
															<option value="2" <c:if test="${fn:trim(prpLpersonLossTemp.sex)=='2'}"><c:out value="selected"/></c:if>>
																<s:text name="certainLoss.female" />
															</option>
															<%-- 女 --%>
														</select>
													</td>
														<td class="title" style="width: 5%;">
														<s:text name="db.prpLperson.personAge" />
													</td>
													<%--年龄--%>
													<td class="input" style="width: 10%;">
														<input type="text" name="prpLpersonLossAge" style="width: 100%;" value="${prpLpersonLossTemp.age}" maxlength="3" description="年齡" class="input">
													</td>
													<td class="title" style="width: 10%">
														<s:text name="db.prpCinsured.identifyNumber" />
													</td>
													<%--身份证号--%>
													<td class="input">
														<input type="text" name="prpLpersonLossIdentifyNumber" value="${prpLpersonLossTemp.identifyNumber }" onchange="checkPersonIdentifyNumber(this);" class='input' style="width: 100%;"  maxlength=20 description="身份證字號">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="commonAcci.compensate.biggestPayout" />
													</td>
													<%--最大赔付额--%>
													<td class="input"  colspan="2" style="width: 15%">
														<input type="text" name="prpLpersonLossMaxPaid" value="<fmt:formatNumber value='${prpLpersonLossTemp.maxpaid }' pattern='#'/>" class='readonly'  style="width: 100%;" readonly  maxlength=20 description="最大賠付額">
													</td>
													<td class="title" style="width: 10%">
														<s:text name="commonAcci.compensate.historicalPayout" />
													</td>
													<%--历史赔付额--%>
													<td class="input"  colspan="2" style="width: 15%">
														<input type="text" name="prpLpersonLossHisPaid" value="<fmt:formatNumber value='${prpLpersonLossTemp.hispaid }' pattern='#'/>"  style="width: 100%;" class='readonly' readonly maxlength="3" description="歷史賠付額">
													</td>
													<td class="title" colspan="2" style="width: 15%;" >
														本次賠付合計
													</td>
													<td class="input" colspan="2">
														<input type="text" name="prpLpersonLossSumRealPay1" value="<fmt:formatNumber value='${prpLpersonLossTemp.sumRealPay1 }' pattern='#'/>"  style="width: 100%;" class='readonly' readonly maxlength="3" description="歷史賠付額">
													</td>
												</tr>
												<tr>
													<td colspan="10" style="width: 100%">
														<table id="PersonHospital" name="PersonHospital" style="width: 100%" cellspacing="1" cellpadding="0">
															<tbody>
																<c:forEach var="prpLpersonHospitalTemp" items="${prpLpersonLossTemp.prpLpersonHospitalList}">
																	<tr name="prpLpersonHospitalTr">
																		<td style="width: 100%" class="subformtitle">
																			<table style="width: 100%" cellspacing="0" cellpadding="0">
																				<tr>
																					<td class="title" style="width: 15%;">
																						就診醫院
																					</td>
																					<td class="input" colspan="6" style="width: 45%;">
																						<input type="text" name="prpLpersonHospitalHospitalCode"  value="${prpLpersonHospitalTemp.hospitalCode }" onkeyup="getHospital(this,'codeCode','0,1')" onfocus="setCacheValue(this);" onblur="isHospital(this,'codeCode');" cacheValue="${prpLpersonHospitalTemp.hospitalCode }" style="width: 45%;" class='input' maxlength=20 description="醫院代號"/>
																						<input type="text" name="prpLpersonHospitalHospitalName"  value="${prpLpersonHospitalTemp.hospitalName }" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 45%;" class='input' maxlength="3" description="醫院名稱"/>
																					</td>
																					<td class="title" style="width: 10%;">
																						出入院日期
																					</td>
																					<td class="input"  colspan="2">
																						<input type="hidden" name="hospitalPersonNo" value="${prpLpersonHospitalTemp.personNo }">
																						<rc:rcDate name="prpLpersonHospitalInHospDate" value="${prpLpersonHospitalTemp.inHospDate }" title="入院日期" style="width: 35%;"/>
																						<rc:rcDate name="prpLpersonHospitalOutHospDate" value="${prpLpersonHospitalTemp.outHospDate }" title="出院日期" style="width: 35%;"/>
																						<input type="button" name="buttonHospitalInsert" class="smallbutton" value="+" onclick="insertPersonHospitalObject(this);"  readonly style="cursor: hand;width: 8%;">
																						<input type="button" name="buttonHospitalDelete" class="smallbutton" value="-" onclick="deletePersonHospitalObject(this);" readonly style="cursor: hand;width: 8%;">
																					</td>
																				</tr>
																				<tr>
																					<td class="title" style="width: 15%;">
																						醫師姓名
																					</td>
																					<td class="input" style="width: 15%;">
																						<input type="text" name="prpLpersonHospitalDoctor" value="${prpLpersonHospitalTemp.doctor }" class="input"  style="width: 100%;"  description="醫師姓名">
																					</td>
																					<td class="title" style="width: 10%;">
																						診斷科別
																					</td>
																					<td class="input" style="width: 20%;" colspan="4" style="width: 20%;">
																						<input type="text" name="prpLpersonHospitalDiagnosisDivision"  value="${prpLpersonHospitalTemp.diagnosisDivision}" style="width: 100%;" class="input"   description="診斷科別">
																					</td>
																					<td class="title" style="width: 10%;">
																						診斷名稱
																					</td>
																					<td class="input" colspan="2">
																						<input type="text" name="prpLpersonHospitalDiagnosisName" value="${prpLpersonHospitalTemp.diagnosisName }" style="width: 80%;" class="input"  description="診斷名稱">
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%;">
														警員姓名
													</td>
													<td class="input" colspan="2" style="width: 15%;">
														<input type="text" name="prpLpersonLossPoliceName"  value="${prpLpersonLossTemp.policeName }" class="input"  style="width: 100%;" maxlength=20 description="警員姓名">
													</td>
													<td class="title" style="width: 10%;">
														警方單位
													</td>
													<td class="input"  colspan="3" style="width: 20%;">
														<input type="text" name="prpLpersonLossPoliceUnits" value="${prpLpersonLossTemp.policeUnits }" style="width: 100%;" class="input" maxlength="3" description="警方單位">
													</td>
													<td class="title" style="width: 10%;">
													</td>
													<td class="input" colspan="2">
													</td>
												</tr>
												<tr>
													<td colspan="10" style="width: 100%">
														<span id="spanPersonFeeLoss"> <%-- 多行输入展现域 --%>
															<table id="PersonFeeLoss" name="PersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0">
																<thead>
																	<tr>
																		<td class="subformtitle" colspan="15">
																			賠付訊息
																		</td>
																	</tr>
																	<tr>
																		<td class="centertitle" style="width: 15%;">
																			險別
																		</td>
																		<td class="centertitle" style="width: 5%;">
																			給付類別
																		</td>
																		<td class="centertitle" style="width: 5%;">
																			給付內容1
																		</td>
																		<td class="centertitle" style="width: 5%;">
																			給付內容2
																		</td>
																		<td class="centertitle" style="width: 6%;">
																			保險金額
																		</td>
																		<td class="centertitle" style="width: 10%;">
																			骨折部位
																		</td>
																		<td class="centertitle" style="width: 5%;">
																			未住院日數
																		</td>
																		<td class="centertitle" style="width: 10%;">
																			骨折程度
																		</td>
																		<td class="centertitle" style="width: 6%;">
																			賠付金額
																		</td>
																		<td class="centertitle" style="width: 6%;">
																			賠償幣別
																		</td>
																		<td class="centertitle" style="width: 5%;">
																			匯率
																		</td>
																		<td class="centertitle" style="width: 6%;">
																			賠償金額（NTD）
																		</td>
																		<td class="centertitle" style="width: 9%;">
																			賠付對象訊息
																		</td>
																		<td class="centertitle" style="width: 4%;">
																			保留預估
																		</td>
																		<td class="centertitle" style="width: 3%;">
																			&nbsp;
																		</td>
																	</tr>
																</thead>
																<tfoot>
																	<tr>
																		<td class="titlesubsub" colspan="14" style="width: 93%"></td>
																		<td class="title" align="right" style="width: 4%">
																			<div align="center">
																				<input type="button" class="smallbutton" value="+" onclick="insertPrpLpersonFeeLossObject(this);" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
																			</div>
																		</td>
																	</tr>
																</tfoot>
																<tbody>
																	<c:forEach var="prpLpersonLoss2Temp" items="${prpLpersonLoss.prpLpersonLossList}" varStatus="prpLpersonLoss2_status">
																		<c:if test="${prpLpersonLoss2Temp.personNo==prpLpersonLossTemp.personNo}">
																			<tr name="prpLpersonFeeLossTr">
																				<td style="width: 100%" colspan="15" >
																					<table style="width: 100%;" cellspacing="1" cellpadding="0" class="common">
																						<thead>
																							<col style="width: 15%;">
																							<col style="width: 5%;">
																							<col style="width: 5%;">
																							<col style="width: 5%;">
																							<col style="width: 6%;">
																							<col style="width: 10%;">
																							<col style="width: 5%;">
																							<col style="width: 10%;">
																							<col style="width: 6%;">
																							<col style="width: 6%;">
																							<col style="width: 5%;">
																							<col style="width: 6%;">
																							<col style="width: 9%;">
																							<col style="width: 4%;">
																							<col style="width: 3%;">
																						</thead>
																						<tr class="inputsubsub" name="prpLpersonFeeLossPaymentTr">
																							<td class="inputsubsub">
																								<input type="hidden" name="prpLpersonLossPersonNo"  value="${prpLpersonLoss2Temp.personNo }">
																								<c:set var="policypersonfamily1" value="${prpLpersonLoss2Temp.policyNo}|${prpLpersonLoss2Temp.familyNo}" scope="page" />
																								<input type="text" class="codecode" style="width: 25%;" name="prpLpersonLossKindCode"
																									value="${prpLpersonLoss2Temp.kindCode }" maxlength=20 description="险别"
																									ondblclick="getKindCodeForAcci(this,'policyItemKindCodeForAcci','0,1,2,3','Y','Y' );"
																									onkeyup="getKindCodeForAcci(this,'policyItemKindCodeForAcci','0,1,2,3','Y','Y' );"
																									onchange="getKindCodeForAcci(this,'policyItemKindCodeForAcci','0,1,2,3','Y','Y' );"
																									onblur="setPaymentType_24_30(this);"
																									>
																								<input type="text" name="prpLpersonLossKindName" class="codename" style="width: 65%;" value="${prpLpersonLoss2Temp.kindName }"
																									ondblclick="getKindCodeForAcci(this,'policyItemKindCodeForAcci','-1,0,1,2','Y','N' );"
																									onkeyup="getKindCodeForAcci(this,'policyItemKindCodeForAcci','-1,0,1,2','Y','N' );"
																									onchange="getKindCodeForAcci(this,'policyItemKindCodeForAcci','-1,0,1,2','Y','N' );"
																									onblur="setPaymentType_24_30(this);"
																									>
																								<input type="hidden" name="prpLpersonLossContractingScope" value="${prpLpersonLoss2Temp.contractingScope}">
																								<input type="hidden" name="prpLpersonLossItemKindNo" value="${prpLpersonLoss2Temp.itemKindNo }">
																							</td>
																							<td class="inputsubsub">
																								<input  name="prpLpersonLossPaymentType" type="text" value="${prpLpersonLoss2Temp.paymentType }" onkeyup="queryPaymentType(this,'paymentType');" onfocus="setCacheValue(this);queryPaymentType(this,'paymentType');" onblur="isPaymentType(this,'paymentType');" cacheValue="${prpLpersonLoss2Temp.paymentType }" class="input" style="width: 95%;">
																							</td>
																							<td class="inputsubsub">
																								<input name="prpLpersonLossPaymentType1" type="text" value="${prpLpersonLoss2Temp.paymentType1 }" onkeyup="queryPaymentType(this,'paymentType1');" onfocus="setCacheValue(this);queryPaymentType(this,'paymentType1');" onblur="isPaymentType(this,'paymentType1')" cacheValue="${prpLpersonLoss2Temp.paymentType1 }" class="input" style="width: 95%;">
																							</td>
																							<td class="inputsubsub">
																								<input name="prpLpersonLossPaymentType2" type="text" value="${prpLpersonLoss2Temp.paymentType2 }" onkeyup="queryPaymentType(this,'paymentType2');" onfocus="setCacheValue(this);queryPaymentType(this,'paymentType2');" onblur="isPaymentType(this,'paymentType2')"  cacheValue="${prpLpersonLoss2Temp.paymentType2 }" class="input" style="width: 95%;">
																							</td>
																							<td class="inputsubsub">
																								<%--残疾给付比例 --%>
																								<input name="prpLpersonLossPaymentRate" type="hidden" value="${prpLpersonLoss2Temp.paymentRate }" class="common"  style="width: 95%;">
																								<input  name="prpLpersonLossPaymentContent" type="hidden" value="${prpLpersonLoss2Temp.paymentContent }" cacheValue="${prpLpersonLoss2Temp.paymentContent }" class="input" style="width: 95%;">
																								<input type="text" name="prpLpersonLossAmount" value="<fmt:formatNumber pattern='#' value='${prpLpersonLoss2Temp.amount }'/>" class="readonly" readonly="readonly" style="width: 95%;">
																							</td>
																							<td class="inputsubsub">
																								<select name="prpLpersonLossFractureSite" onchange="sumHospitalDays(this);" style="width: 95%;" >
																									<option value="" title=""></option>
																									<c:forEach items="${fractureSiteList}" var="fractureSiteTemp">
																										<option value="${fractureSiteTemp.id.fractureCode }" title="${fractureSiteTemp.fractureRate}" <c:if test="${fractureSiteTemp.id.fractureCode==prpLpersonLoss2Temp.fractureSite }">selected="selected"</c:if>>${fractureSiteTemp.fractureName}</option>
																									</c:forEach>
																								</select>
																							</td>
																							<td class="inputsubsub">
																								<input name="prpLpersonLossNotHospitalDays" type="text" value="${prpLpersonLoss2Temp.notHospitalDays }" onchange="sumRealPay(this);" class="input" style="width: 95%;">
																							</td>
																							<td class="inputsubsub">
																								<select	name="prpLpersonLossFractureDegree" onchange="sumRealPay(this);" style="width: 95%;">
																									<option value="" title=""></option>
																									<c:forEach items="${fractureDegreeList}" var="fractureDegreeTemp">
																										<option value="${fractureDegreeTemp.id.fractureCode }" title="${fractureDegreeTemp.fractureRate}" <c:if test="${fractureDegreeTemp.id.fractureCode==prpLpersonLoss2Temp.fractureDegree }">selected="selected"</c:if>>${fractureDegreeTemp.fractureName}</option>
																									</c:forEach>
																								</select>
																							</td>
																							<td class="inputsubsub">
																								<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核  (理賠)-->
																								<input type="text" name="prpLpersonLossSumRealPay" onchange="checkBeyondSumAmount();countSumRealPay(this);" class="input"  value="<fmt:formatNumber value='${prpLpersonLoss2Temp.sumRealPay}' pattern='#.##'/>" style="width: 95%;">
																								<input type="hidden" name="prpLpersonLossSumLoss" value="<fmt:formatNumber value='${prpLpersonLoss2Temp.sumLoss}' pattern='#'/>" >
																								<input type="hidden" name="prpLpersonLossSumRest" value="<fmt:formatNumber value='${prpLpersonLoss2Temp.sumRest}' pattern='#'/>" >
																								<input type="hidden" name="prpLpersonLossDeductible"  value="<fmt:formatNumber value='${prpLpersonLoss2Temp.deductible}' pattern='#'/>" >
																								<input type="hidden" name="prpLpersonLossClaimRate"   value="${prpLpersonLoss2Temp.claimRate}" >
																								<input type="hidden" name="prpLpersonLossUnitAmount" value="${prpLpersonLoss2Temp.unitAmount}">
																								<input type="hidden" name="prpLpersonLossLossQuantity" value="${prpLpersonLoss2Temp.lossQuantity}">
																								<input type="hidden" name="prpLpersonLossIndemnityDutyRate" value="${prpLpersonLoss2Temp.indemnityDutyRate}">
																								<input type="hidden" name="prpLpersonLossDeductibleRate" value="${prpLpersonLoss2Temp.deductiblerate}">
																								<input type="hidden" name="prpLpersonLossLiabCode" value="${prpLpersonLoss2Temp.liabCode}">
																								<input type="hidden" name="prpLpersonLossLiabName" value="${prpLpersonLoss2Temp.liabName}">
																								<input type="hidden" name="prpLpersonLossJobCode" value="${prpLpersonLoss2Temp.jobCode}">
																								<input type="hidden" name="prpLpersonLossJobName" value="${prpLpersonLoss2Temp.jobName}">
																								<input type="hidden" name="prpLpersonLossItemAddress" value="${prpLpersonLoss2Temp.itemAddress}">
																								<input type="hidden" name="prpLpersonLossUnit" value="${prpLpersonLoss2Temp.unit}">
																								<input type="hidden" name="prpLpersonLossCurrency2" value="${prpLpersonLoss2Temp.currency2}">
																								<input type="hidden" name="prpLpersonLossCurrency1" value="${prpLpersonLoss2Temp.currency1}">
																								<input type="hidden" name="prpLpersonLossItemValue" value="${prpLpersonLoss2Temp.itemValue}">
																								<input type="hidden" name="prpLpersonLossCurrency4" value="${prpLpersonLoss2Temp.currency4}">
																								<input type="hidden" name="prpLpersonLossFlag" value="${prpLpersonLoss2Temp.flag}">
																								<input type="hidden" name="prpLpersonLossCurrency3" value="${prpLpersonLoss2Temp.currency3}">
																							</td>
																							<td class="inputsubsub" >
																								<select name="prpLpersonLossCurrency" class="input" style="width: 50px" onchange="getPrpLpersonLossExchRate(this);">
																									<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
																										<option value="${tempMap.key}" <c:if test="${tempMap.key==prpLpersonLoss2Temp.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}" /></option>
																									</c:forEach>
																								</select>
																							</td>
																							<td class="inputsubsub" >
																								<input type="text" name="prpLpersonLossExchRate" value="${prpLpersonLoss2Temp.exchRate }" class="input" onchange="setObjectInfoExchRate(this);">
																							</td>
																							<td class="inputsubsub" >
																								<input type="text" name="prpLpersonLossSumRealPayNTD"  value="<fmt:formatNumber value='${prpLpersonLoss2Temp.sumRealPay*prpLpersonLoss2Temp.exchRate}' pattern='#'/>" class="input" style="width: 95%;" readonly="readonly">
																							</td>
																							<td class="inputsubsub"> 
																								<input type="text" name="prpLpersonLossPayObjectSerialNo" value="${prpLpersonLoss2Temp.payObjectSerialNo}"
																									onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly" description="賠付對象訊息"  class='input' style="width: 95%;"  >
																							</td>
																							<td class="input" style="width: 4%" align="ceter">
																								<s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" value="#attr.prpLpersonLoss2Temp.reservedEstimate"></s:select>
																							</td>
																							<td class="inputsubsub">
																								<div align="center">
																									<input type=button class="smallbutton" name="buttonPersonFeeLossDelete" onclick="deletePrpLpersonFeeLossObject(this);countSumRealPay();" value="-" readonly style="cursor: hand">
																								</div>
																							</td>
																						</tr>
																						<tr name="prpLpersonFeeLossDeathTr">
																							<td class="common">
																								死亡日期
																							</td>
																							<td colspan="3" class="inputsubsub">
																								<rc:rcDate name="prpLpersonLossDeathDate" value="${prpLpersonLoss2Temp.deathDate}" style="width:80%;" class="common"/>
																							</td>
																							<td class="common">
																								死亡地點
																							</td>
																							<td class="common" colspan="2">
																								<input type="text" name="prpLpersonLossDeathAddressCode" value="${prpLpersonLoss2Temp.deathAddressCode}" type="hidden"  class="common">
																								<input type="text" name="prpLpersonLossDeathAddressName" value="${prpLpersonLoss2Temp.deathAddressName}" title="選擇地域名" class="codename"  onkeyup="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');"/>
																							</td>
																							<td colspan="2" class="common">
																								檢察署名稱
																							</td>
																							<td colspan="5" class="inputsubsub">
																								<s:select name="prpLpersonLossProsecutorsOffice" list="#request.prosecutorsOfficeList" value="#attr.prpLpersonLoss2Temp.prosecutorsOffice" listKey="key" listValue="value" class="common" style="width: 100%;"></s:select>
																							</td>
																							<td class="inputsubsub"></td>
																						</tr>
																						<tr name="prpLpersonFeeLossDeathPlaceTr">
																							<td class="common">
																								死亡場所
																							</td>
																							<td colspan="3" class="inputsubsub">
																								<s:select name="prpLpersonLossDeathPlace" list="#request.deathPlaceList" value="#attr.prpLpersonLoss2Temp.deathPlace" listKey="key" listValue="value" class="common" style="width: 100%;"></s:select>
																							</td>
																							<td class="common">
																								死亡方式
																							</td>
																							<td class="inputsubsub"  colspan="2">
																								<s:select name="prpLpersonLossDeathManner" list="#request.deathMannerList"  value="#attr.prpLpersonLoss2Temp.deathManner" listKey="key" listValue="value" class="common" style="width: 100%;"></s:select>
																							</td>
																							<td class="common" colspan="2">
																								檢察官姓名
																							</td>
																							<td colspan="5" class="inputsubsub">
																								<input name="prpLpersonLossProsecutor" type="text" value="${prpLpersonLoss2Temp.prosecutor }" class="common" style="width: 60%;">
																							</td>
																							<td class="inputsubsub"></td>
																						</tr>
																						<tr name="prpLpersonFeeLossDeathCertificateTr">
																							<td class="common" colspan="5" align="right" >
																								證明書開立日期
																							</td>
																							<td class="inputsubsub" colspan="2">
																								<rc:rcDate name="prpLpersonLossDeathCertificateDate" value="${prpLpersonLoss2Temp.deathCertificateDate }" style="width:90%;"/>
																							</td>
																							<td class="common" colspan="2">
																								法醫師／檢驗名
																							</td>
																							<td class="inputsubsub" colspan="5">
																								<input name="prpLpersonLossCourtDoctor" type="text" value="${prpLpersonLoss2Temp.courtDoctor }" class="common" style="width: 50%;">
																							</td>
																							<td class="common"></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</c:if>
																	</c:forEach>
																</tbody>
															</table>
														</span>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td class="input" style="width: 4%">
										<div align="center">
											<input type=button class="smallbutton" name="buttonPersonDelete" onclick="deletePrpLpersonLossObject(this);countSumRealPay();" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<c:set var="personNo" value="${personNo+1}" scope="page"/>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
</span>
<div id="prpLPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
	<ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList==null||#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()==0">
			<li>
				沒有賠款給付對象訊息，請錄入賠款給付對象。
			</li>
		</s:if>
		<s:else>
			<c:forEach var="prpLpayObjectInfoTemp" items="${prpLpayObjectInfo.prpLpayObjectInfoList}">
				<li>
					<input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="${prpLpayObjectInfoTemp.id.serialNo}" />
					賠付對象${prpLpayObjectInfoTemp.id.serialNo} 賠付金額:
					<input type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width: 100px" />
				</li>
			</c:forEach>
		</s:else>
	</ul>
	<ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'prpLPayObjectinfo')" value="<s:text name='button.close.value' />" />
		</li>
	</ul>
</div>
<div  id="hospitalList" style="background-color:FFFFFF;display: none;cursor:hand;position: absolute;width: 400px;" align="left"></div>