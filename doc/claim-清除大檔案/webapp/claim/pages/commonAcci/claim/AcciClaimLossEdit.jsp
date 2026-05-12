<%--
****************************************************************************
* DESC       ：显示立案登记的险别估损金额页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-24
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<script language="javascript"> 
//显示危险单位划分信息

function viewDangerUnit(field) {
    for (var i = 1; i < fm.prpLclaimLossSerialNo.length; i++) {
        if (fm.prpLclaimLossDangerNo[i] == field) {
            var count = i;
            var policyNo = fm.policyno.value;
            var damageDate = fm.prpLclaimDamageStartDate.value;
            field.value = "";
            var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=ClaimLoss";
            window.open(submitStr, '查看危险单位信息', 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
        }
    }
}

var damageKind = new Array(); 
<c:forEach var="damageKindList" items="${damageKindList}" varStatus="damageKindListIndex">
damageKind[${damageKindListIndex.index}]   ="${prpCitemKind.kindCode}";
</c:forEach>

function judgeKindCode(Field)
{
 var findFlag = 0;
 var fieldname=Field.name;
 var i = 0;
 var findex=0;
 for(i=1;i<fm.all(fieldname).length;i++)
 {
    if( fm.all(fieldname)[i] == Field )
    {
       findex=i;
       break;
    }
 } 
 var strValue = fm.prpLclaimLossKindCode[findex].value;
 //判断选择的险别是否为出险日期当时生效的险别
for (var j = 0; j < damageKind.length; j++) {
    if (damageKind[j] == strValue) {
        findFlag = 1;
        break;
    }
}
if (findFlag == 0) {
    alert("您选择的险别不是出险日期时的险别,请重新进行选择");
    fm.prpLclaimLossKindCode[findex].value = "";
    return false;
}
}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLclaimLossKindName'],:input[name='prpLclaimLossItemName']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
			<s:text name="claim.amountInsurLossInfo" />
			<font color="#FF0000">*</font> <br>
			<%--险别估损金额信息--%>
			<span style="display: none">
						<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START -->
						<%--险种信息 --%>
						<c:forEach var="prpCitemKindTemp" items="${prpCitemKindList}" varStatus="prpCitemKind_status">
						<span style="display: none" name="span_prpCitemKind">
							<input type="hidden" name="init_data_itemKindNo" value="${prpCitemKindTemp.id.itemKindNo}">
							<input type="hidden" name="init_data_familyno" value="${prpCitemKindTemp.familyNo }">
							<input type="hidden" name="init_data_hisKind"  value="${prpCitemKindTemp.kindCode }">
							<input type="hidden" name="init_data_hisItem" value="${prpCitemKindTemp.itemCode}">
							<input type="hidden" name="init_data_hisPaid" value="${prpCitemKindTemp.hisPaid}">
							<input type="hidden" name="init_data_amount" value="<fmt:formatNumber pattern='#' value='${prpCitemKindTemp.amount}'/>"><%-- 保额 --%>
							<input type="hidden" name="init_data_dayAmount" value="${prpCitemKindTemp.dayAmount}"><%-- 日额 --%>
							<input type="hidden" name="init_data_coverageratio" value="${prpCitemKindTemp.coverageratio}"> <%-- 赔付倍数 --%>
							<input type="hidden" name="init_data_currAmount" value="${prpCitemKindTemp.unitAmount }">
							<input type="hidden" name="init_data_commodityCode" value="${prpCitemKindTemp.commodityCode }"> <%-- 商品代号 --%>
							<input type="hidden" name="init_data_contractingScope" value="${prpCitemKindTemp.contractingScope }"> <%-- 承保范围 --%>
						</span>
						</c:forEach>
						<!-- TA海突倍率 -->
						<c:forEach var="prpDpolicyRulesTemp" items="${prpDpolicyRulesList}" varStatus="prpDpolicyRules_status">
						<span style="display: none" name="span_prpDpolicyRules">
							<input type="hidden" name="init_data_rulCode" value="${prpDpolicyRulesTemp.id.codeCode}">
							<input type="hidden" name="init_data_rulKind"  value="${prpDpolicyRulesTemp.kindCode }">
							<input type="hidden" name="init_data_rulMultiplier" value="${prpDpolicyRulesTemp.multiplier}">
						</span>
						</c:forEach>
						<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END -->
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 5%">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onClick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
							</td>
							<td class="input" style="width: 22%" style="align:center">
								<%--mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 --%>
								<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 30%" title="險別" ondblclick="code_CodeSelect(this,'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);sumRealPay(this);" onkeyup="code_CodeSelect(this,'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" onchange="code_CodeSelect(this,'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);sumRealPay(this);">
								<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 65%" title="險別" ondblclick="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" onkeyup="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" onchange="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);">
								<input name="prpLclaimLossItemKindNo" type="hidden">
								<input name="prpLclaimLossSerialNo" type="hidden">
								<input type="hidden" name="prpLclaimLossAmount" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
							</td>
							<td class="input" style="width: 12%;display:none" align="center">
								<input type="hidden" name="prpLclaimLossItemCode" value="" class="codecode" style="width: 30%" title="保險責任" ondblclick="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);" onchange="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);" onkeyup="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);">
								<input type=hidden name="prpLclaimLossItemName" class="codecode" style="width: 60%" title="保險責任" value="" ondblclick="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);" onchange="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);" onkeyup="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);">
							</td>
							<input type="hidden" name="prpLclaimLossItemCode" value="" class="codecode" style="width: 30%" title="保險責任" ondblclick="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);" onchange="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);" onkeyup="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);">
							<input type=hidden name="prpLclaimLossItemName" class="codecode" style="width: 60%" title="保險責任" value="" ondblclick="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);" onchange="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);" onkeyup="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);">
							<%--mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START--%>
							<c:if test="${prpLclaim.riskCode == 'TA'}">
								<td class="input" style="width: 10%" align="center">
									<!-- CLM0274_1_保險金額 輸入 -->
									<input type=text name="prpLclaimLossAmount2" readonly value="" class="readonly" style="width: 60%;text-align: right" title="保險金額 ">
								</td>
							</c:if>
							<%--mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END--%>
							<td class="input" style="width: 10%" align="center">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" readonly value="${prpLclaim.estiCurrency}" style="width: 30%" title="幣別" >
								<input type=text name="prpLclaimLossCurrencyName" readonly class="readonly" style="width: 60%" title="幣別" value="${strCurrencyName}" >
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLclaimLossSumClaim" class=common style="text-align: right" onchange="checkBeyondSumAmount();collectClaimLoss();">
							</td>
							<td class="input" style="width: 8%">
								<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" />
							</td>
							<td class="input" style="width: 8%; display: none">
								<input name="prpLclaimLossKindRest" class=common style="width: 50px">
							</td>
							<td class="input" style="width: 9%" style="display:none">
								<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10" value="${claimDateTime}">
							</td>
							<td class="input" style="width: 20%">
								<input name="prpLclaimLossRemarkFlag" class="readonly" style="width: 150px" maxlength="100" readonly="readonly">
								<input name="prpLclaimLossFlag" type="hidden">
								<input name="prpLclaimLossFeeCategory" type="hidden" value="">
							</td>
							<td class="input" style='width: 4%' colspan="2" align="center">
								<div>
									<input type="button" class="smallbutton" name="buttonClaimLossDelete" onclick="deleteRow(this,'ClaimLoss');collectClaimLoss();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanClaimLoss" style="display:" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ClaimLoss">
					<thead>
						<tr>
							<td class="centertitle" style="width: 5%">
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%--危险单位序号--%>
							<td class="centertitle" style="width: 22%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRiskType" />
							</td>
							<%--险别--%>
							<td class="centertitle" style="width: 12%;display: none">
								<s:text name="commonAcci.claim.responsibility" />
							</td>
							<%--责任--%>
							<%--mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START--%>
							<c:if test="${prpLclaim.riskCode == 'TA'}">
								<td class="centertitle" style="width: 10%">
									保險金額
								</td>
							</c:if>
							<%--mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END--%>
							<td class="centertitle" style="width: 10%" >
								<s:text name="db.prpLperson.currency" />
							</td>
							<%--币别--%>
							<td class="centertitle" style="width: 10%">
								<s:text name="commonAcci.claim.expectPayAmount" />
							</td>
							<%--预计给付金额--%>
							<td class="centertitle" style="width: 8%">
								<s:text name="commonAcci.claim.category" />
							</td>
							<%--类别--%>
							<td class="centertitle" style="width: 8%; display: none">
								<s:text name="claim.salvage" />
							</td>
							<%--残值--%>
							<td class="centertitle" style="width: 9%" style="display:none">
								<s:text name="modifySumClaim.inputDate" />
							</td>
							<%--输入日期--%>
							<td class="centertitle" style="width: 20%">
								<s:text name="claim.adjustReason" />
							</td>
							<%--调整原因--%>
							<td class="centertitle" style="width: 4%">
								<input onclick="collectCurrency();" style="width: 35px" type="button" class="button" value="<s:text name='button.summary.value' />">
								<%--汇总--%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<!--查看页面，按钮要灰掉（如果这里要修改，请注意对应的ClaimEdit需要去掉disabledAllButton(ClaimLoss_button)方法）-->
						<tr>
							<td colspan="9">
								<table id="ClaimLoss_button" style="width: 100%">
									<tr>
										<td type="button" class="button" colspan=8 style="width: 96%;text-align:left;">
											<s:text name="prompt.certify.addRemove" />
										</td>
										<%--(按"+"号键增加信息，按"-"号键删除信息)--%>
										<td class="title" align="center" style="width: 4%">
											<div align="center">
												<input type="button" class="smallbutton" value="+" onclick="insertRow('ClaimLoss');" name="buttonDriverInsert" style="cursor: hand">
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="9">
								<c:if test="${prpDexch.baseCurrency!=null&&prpDexch.baseCurrency!=''&&prpDexch.baseCurrency!='NTD'}">
									<table>
										<tr>
											<td class="title" style="color: red">
												<s:text name="claim.signCurrencyCase" />:
											</td>
											<%--此案件签单币别为--%>
											<td>
												<input type=text name="BaseCurrency2" class="readonly" readonly style="color: red" value="${prpDexch.baseCurrency}">
											</td>
										</tr>
										<tr>
											<td class="title" style="color: red">
												<s:text name="claim.currentExchangeRate" />:
											</td>
											<%--当前兑换率为--%>
											<td>
												<input type=text name="ExchRate2" class="readonly" readonly style="color: red" value="${prpDexch.exchRate}">
											</td>
										</tr>
									</table>
								</c:if>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="prpLclaimLoss" items="${claimDto.prpLclaimLossList}">
							<tr>
								<td class="input" style="width: 5%">
									<input type=text name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLoss.dangerNo}" onClick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
								</td>
								<td class="input" style="width: 22%" style="align:center">
									<!-- mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核 -->
									<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START -->
									<input type=text name="prpLclaimLossKindCode" value="${prpLclaimLoss.kindCode}" class="codecode" style="width: 30%" title="險別" ondblclick="code_CodeSelect(this,'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);sumRealPay(this);" onchange="code_CodeSelect(this,'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);"
										onkeyup="code_CodeSelect(this,'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);">
									<!-- mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核 -->	
									<input type=text name="prpLclaimLossKindName" value="${prpLclaimLoss.kindName}" class="codecode" style="width: 65%" title="險別" ondblclick="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" onchange="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);"
										onkeyup="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" >
									<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END -->
									<input name="prpLclaimLossItemKindNo" type="hidden" value="${prpLclaimLoss.itemKindNo}">
									<input name="prpLclaimLossSerialNo" type="hidden" value="${prpLclaimLoss.id.serialNo}">
									<input type="hidden" name="prpLclaimLossAmount" value="<fmt:formatNumber value='${prpLclaimLoss.amount}' pattern='#'/>">
								</td>
								<td class="input" style="width: 12%;display: none" align="center">
								</td>
								<input type="hidden" name="prpLclaimLossItemCode" value="${prpLclaimLoss.itemCode}" class="codecode" style="width: 30%" title="保險責任" ondblclick="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);" onchange="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);"
										onkeyup="before_code_CodeSelect(this, 'policyItemCode','0,1','Y','Y',fm.policyno.value);">
								<input type=hidden name="prpLclaimLossItemName" class="codecode" style="width: 60%" title="保險責任" value="${prpLclaimLoss.itemKindName}" ondblclick="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);" onchange="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);"
										onkeyup="before_code_CodeSelect(this, 'policyItemCode','-1,0','Y','N',fm.policyno.value);">
								<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START -->
								<c:if test="${prpLclaim.riskCode == 'TA'}">
									<td class="input" style="width: 10%" align="center">
										<!-- CLM0274_1_保險金額 輸入 -->
										<input type=text name="prpLclaimLossAmount2" readonly value="<fmt:formatNumber value="${prpLclaimLoss.amount}" pattern="#"/>" class="readonly" style="width: 60%;text-align: right" title="保險金額 ">
									</td>
								</c:if>
								<!-- mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END -->
								<td class="input" style="width: 10%" align="center">
									<input type="text" name="prpLclaimLossCurrency" readonly value="${prpLclaimLoss.currency}" class="readonly" style="width: 30%" title="幣別" >
									<input type=text name="prpLclaimLossCurrencyName" readonly value="${prpLclaimLoss.currencyName}" class="readonly" style="width: 60%" title="幣別" >
								</td>
								<td class="input" style="width: 10%">
									<!-- mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核 -->
									<input name="prpLclaimLossSumClaim" class=common style="text-align: right" value="<fmt:formatNumber value="${prpLclaimLoss.sumClaim}" pattern="#"/>" 
										onchange="checkBeyondSumAmount();collectClaimLoss();">
								</td>
								<td class="input" style="width: 8%">
									<c:set var="tempSelectedValue" value="${prpLclaimLoss.lossFeeType}" />
									<s:select name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" />
								</td>
								<td class="input" style="width: 8%; display: none">
									<input name="prpLclaimLossKindRest" class=common style="width: 50px" value="<fmt:formatNumber value="${prpLclaimLoss.kindRest}" pattern="#"/>">
								</td>
								<td class="input" style="width: 9%" style="display:none">
									<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10" value="${prpLclaimLoss.inputDate}">
								</td>
								<td class="input" style="width: 20%">
									<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100" style="width: 150px" value="${prpLclaimLoss.remarkFlag}">
									<input name="prpLclaimLossFlag" type="hidden" value="${prpLclaimLoss.flag}">
									<input name="prpLclaimLossFeeCategory" type="hidden" value="${prpLclaimLoss.feeCategory}">
								</td>
								<td class="input" style='width: 4%' colspan="2" align="center">
									<div>
										<input type="button" class="smallbutton" name="buttonClaimLossDelete" onclick="deleteRow(this,'ClaimLoss');collectClaimLoss();" <c:if test="${param.editType!='ADD'&&param.editType!='EDIT'}">disabled</c:if> value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>