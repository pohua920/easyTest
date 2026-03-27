<%--
****************************************************************************
* DESC		 ：显示立案登记的险别估损金额页面
* AUTHOR	 ：理赔组
* CREATEDATE ：2014-04-15
* MODIFYLIST ：	 Name		 Date			Reason/Contents
*			------------------------------------------------------
****************************************************************************
--%>
<%@page import="com.sinosoft.claim.dto.domain.PrpCitemKindDto"%>
<%@page import="com.sinosoft.function.insutil.dto.domain.PrpDexchDto"%>
<script language="javascript">
//显示危险单位划分信息
function viewDangerUnit(field){
	for (var i=1;i<fm.prpLclaimLossSerialNo.length;i++){
		if(fm.prpLclaimLossDangerNo[i]==field){
		var count		= i;
		var policyNo	 = fm.policyno.value;
		var damageDate = fm.prpLclaimDamageStartDate.value;
		field.value="";
		var submitStr	= "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&openerIndex=" + count+"&PageType=ClaimLoss" ;	
			window.open(submitStr,i18n.title.dangerUnitBeforeEdit.RiskUnitInformation,'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		}
	}
}
/***
 *包裝下險別選擇的函數，選擇險別的時候清空下標的的訊息
 */
function claimLossKindCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
	var $tr = $(field).closest("tr");
	$tr.find(":input[name='prpLclaimLossItemCode']").val("");
	$tr.find(":input[name='prpLclaimLossItemDetailName']").val("");
	$tr.find(":input[name='prpLclaimLossItemKindNo']").val("");
	$tr.find(":input[name='prpLclaimLossAmount']").val(0);
	code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition);
}
/***
 *包裝下標的選擇的函數，選擇標的時的時候，若有險別，則加上險別的條件
 */
function claimLossItemCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
	var $tr = $(field).closest("tr");
	var kindCode = $tr.find(":input[name='prpLclaimLossKindCode']").val();
	if($.trim(kindCode).length!=0){
		otherCondition += "|"+kindCode;
		if("prpLclaimLossItemCode" == field.name){
			codeRelation = codeRelation.substr(0,7);
		}else if("prpLclaimLossItemDetailName" == field.name){
			codeRelation = codeRelation.substr(0,8);//codecode 是-1，多出一位
		}
	}
	code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition);
}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLclaimLossFeeCategory']").each(function(){
			$(this).children('option[value="C"],option[value="O"]').remove();
		});
		$(":input[name='prpLlossDtoKindName'],:input[name='prpLlossDtoLossName'],:input[name='prpLlossDtoPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
			<s:text name="claim.amountInsurLossInfo" />
			<font color="#FF0000">*</font><br>
			<%--险别估损金额信息--%>
			<span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 5%">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onClick="viewDangerUnit(this);" onchange="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);">
								<input name="prpLclaimLossSerialNo" type="hidden">
							</td>
							<td class="input" style="width: 18%" style="align:center">
								<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 20%;" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>"
									ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
								<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 70%;" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>"
									ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="input" style="width: 18%" style="align:center">
								<input type='input' name='prpLclaimLossItemCode' class="codecode" style="width: 20%" title="<s:text name='db.prpDlimit.itemCode'/>" 
									ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);"
									onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);" 
									onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);">
								<%--标的代码--%>
								<input type='input' name="prpLclaimLossItemDetailName" class="codename" style="width: 70%" title="<s:text name='regist.prpLregist.itemName'/>"
									ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);" 
									onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);"
									onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);">
								<%--标的名称--%>
								<input name="prpLclaimLossItemKindNo" type="hidden">
							</td>
							<td class="input" align="center" style="width: 5%">
								<s:select name="prpLclaimLossFeeCategory" listKey="key" listValue="value" list="#request.lossFeeCategoryList" />
							</td>
							<td class="input" style="width: 7%" align="center"><%--保险金额 --%>
								<input type=text name="prpLclaimLossAmount" class="readonly" readonly style="text-align: right" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
							</td>
							<input type="hidden" name="prpLclaimLossItemName" value="">
							<input name="prpLclaimLossFlag" type="hidden" value="">
							<td class="input" style="width: 7%">
								<select name="prpLclaimLossLossFeeType">
									<option value="P">
										<s:text name="db.prpGradeExt.sumPaid" />
									</option>
									<%--赔款--%>
									<option value="Z">
										費用
									</option>
									<%--费用--%>
								</select>
								<input type="hidden" name="prpLclaimLossFlag" class="input" readonly="true" style="" value="1">
							</td>
							<td class="input" style="width: 8%" align="center">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="<s:text name="replevy.currency" />" value="${prpLclaim.estiCurrency}">
								<input type="hidden" name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" value="${prpLclaim.currencyName}">
							</td>
							<td class="input" style="width: 7%" align="center"><%--損失金額 --%>
								<input type=text name="prpLclaimLossKindLoss" class="input" style="text-align: right" title="<s:text name="certainLoss.thirdCarLoss.LossFee" />" value="0" onchange="calculateSumClaim(this);">
							</td>
							<td class="input" style="width: 7%" align="center"><%--自負額 --%>
								<input type="text"  name="prpLclaimLossDeductible" class="input" style="text-align: right" value="0" onchange="calculateSumClaim(this);">
							</td>
							<td class="input" style="width: 7%" align="center" style="display:">
								<input name="prpLclaimLossKindRest" class="input" style="width: 60px;text-align: right" value="0" onchange="calculateSumClaim(this);"> 
							</td>
							<td class="input" style="width: 8%" align="center">
								<input name="prpLclaimLossSumClaim" class="readonly" readonly="readonly" style="text-align: right" value="0" >
							</td>
							<td class="input" style="width: 8%" style="display:none">
								<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10" value="">
								<input type="hidden" name="prpLclaimLossRemarkFlag" class="input" style="" maxlength="100">
							</td>
							<td class="input" style='width: 3%' align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimLoss();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> 
			<span id="spanClaimLoss" style="display:" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ClaimLoss">
					<thead>
						<tr>
							<td class="centertitle" style="width: 5%">
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%--危险单位序号--%>
							<td class="centertitle"  style="width: 18%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRiskType" />
							</td>
							<%--险别--%>
							<td class="centertitle" style="width: 18%">
								<s:text name="compensate.dubang.project" />
							</td>
							<%-- 项目 --%>
							<td class="centertitle" style="width: 5%">
								<s:text name="claim.scope" />
							</td>
							<%-- 范围 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="db.prpLpersonloss.amount" />
							</td>
							<%--保險金額--%>
							<td class="centertitle"  style="width: 7%">
								<s:text name="modifySumClaim.costType" />
							</td>
							<%--费用类别--%>
							<td class="centertitle" style="width: 8%">
								<s:text name="db.prpLperson.currency" />
							</td>
							<%--币别--%>
							<%--理赔三期，增加栏位 begin --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="certainLoss.thirdCarLoss.LossFee" /><%--損失金額--%>
							</td>
							<td class="centertitle" style="width: 7%">
								<s:text name="db.prpLpersonLoss.sumRest" /><%--自負額--%>
							</td>
							<td class="centertitle" style="width: 7%;">
								<s:text name="claim.salvage" /><%--残值--%>
							</td>
							<td class="centertitle" style="width: 8%">
								<s:text name="claim.amountInsurLoss" /><%--险别估损金额--%>
							</td>
							<td class="centertitle" style="width: 3%">
								<input onclick="collectCurrency();" style="width: 35px" class="button" type="button" value="<s:text name='button.summary.value'/>"><%-- 汇总 --%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="12">
								<table id="ClaimLoss_button" style="width: 100%">
									<tr>
										<td class="title" colspan="11" style="width: 97%">
										<%-- 
											<s:text name="modifySumClaim.query1" />
										--%>
										</td>
										<%--(按"+"号键增加估损金额信息，按"-"号键删除信息)--%>
										<td class="title" align="center" style="width: 3%">
											<div align="center">
												<input type="button" value="+" class="smallbutton" onclick="insertRow('ClaimLoss')" name="buttonDriverInsert" style="cursor: hand">
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<s:if test="#request.prpDexch.baseCurrency != null && #request.prpDexch.baseCurrency != '' && #request.prpDexch.baseCurrency != #request.LOCAL_CURRENCY ">
							<tr>
								<td colspan="11">
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
								</td>
							</tr>
						</s:if>
					</tfoot>
					<tbody>
						<tr>
						<c:forEach items="${prpLclaimLoss.claimLossList }" var="prpLclaimLoss">
							<tr class=oddrow>
							<s:if test="#request.editType !='ADD' && #request.editType !='EDIT'">
								<s:set var="inputType" value="readonly" scope="page" />
								<s:set var="inputDisable" value="disabled" scope="page" />
							</s:if>
							<td class="input" style="width: 5%">
								<input name="prpLclaimLossSerialNo" type="hidden" value="${prpLclaimLoss.id.serialNo}">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLoss.dangerNo}" onClick="viewDangerUnit(this);" onchange="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);">
							</td>
							<td class="input" style="align:center;width: 18%">
								<input type=text name="prpLclaimLossKindCode" class="codecode" <s:property value="#inputType"/> style="width: 20%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>"
									<s:if test="#request.editType =='SHOW' || #request.editType =='EDIT'">
										ondblclick= "claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange= "claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup= "claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										</s:if>
									value="${prpLclaimLoss.kindCode}">
								<input type=text name="prpLclaimLossKindName" class="codecode" <s:property value="#inputType"/> style="width: 70%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>"
									<s:if test="#request.editType =='SHOW' || #request.editType =='EDIT'">
										ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup= "claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										</s:if>
									value="${prpLclaimLoss.kindName}">
							</td>
							<td class="input" style="width: 18%" style="align:center">
									<input type='input' name='prpLclaimLossItemCode' class="codecode" style="width: 20%" title="<s:text name='db.prpDlimit.itemCode'/>" value="${prpLclaimLoss.itemCode}"
										ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);"
										onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);"
										onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);"><%--标的代码--%>
									<input type='input' name="prpLclaimLossItemDetailName" class="codename" style="width: 70%" title="<s:text name='regist.prpLregist.itemName'/>" value="${prpLclaimLoss.itemDetailName}"
										ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);"
										onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);"
										onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);"><%--标的名称--%>
									<input name="prpLclaimLossItemKindNo" type="hidden" value="${prpLclaimLoss.itemKindNo}">
								</td>
								<td class="input" style="width: 5%" align="center">
										<c:set var="tempSelectedValue" value="${prpLclaimLoss.feeCategory}" />
										<s:select name="prpLclaimLossFeeCategory" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossFeeCategoryList"/>
									</td>
							<td class="input" style="width: 7%" align="center"><%--保险金额 --%>
								<input type=text name="prpLclaimLossAmount" class="readonly" readonly style="text-align: right" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaimLoss.amount}' pattern='#'/>" >
							</td>
							<input type="hidden" name="prpLclaimLossItemName" value="">
							<td class="input" style="width: 7%">
								<select name="prpLclaimLossLossFeeType">
									<option value="P" <c:if test ="${prpLclaimLoss.lossFeeType=='P'}">selected</c:if>>
										<s:text name="db.prpGradeExt.sumPaid" />
									</option>
									<%--赔款--%>
									<option value="Z" <c:if test ="${prpLclaimLoss.lossFeeType=='Z'}">selected</c:if>>
										費用
									</option>
									<%--费用--%>
								</select>
								<input type="hidden" name="prpLclaimLossFlag" class="input" readonly="true" style="width: 60%" value="1">
							</td>
							<td class="input" style="width: 8%" align="center">
								<input type=text name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="<s:text name="replevy.currency" />" value="${prpLclaimLoss.currency}">
								<input type=hidden name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="<s:text name="replevy.currency" />" value="${prpLclaimLoss.currencyName}">
							</td>
							<td class="input" style="width: 7%" align="center"><%--損失金額 --%>
								<input type=text name="prpLclaimLossKindLoss" class="input" style="text-align: right" title="<s:text name="certainLoss.thirdCarLoss.LossFee  " />" value="<fmt:formatNumber value='${prpLclaimLoss.kindLoss}' pattern='#'/>" onchange="calculateSumClaim(this);">
							</td>
							<td class="input" style="width: 7%" align="center"><%--自負額 --%>
									<input type=text name="prpLclaimLossDeductible" class="input" style="text-align: right" title="<s:text name="db.prpLpersonLoss.sumRest" />" value="<fmt:formatNumber value='${prpLclaimLoss.deductible}' pattern='#'/>" onchange="calculateSumClaim(this);">
							</td>
							<td class="input" style="width: 7%" align="center"><%--残值 --%>
								<input type=text name="prpLclaimLossKindRest" class="input" style="width: 60px;text-align: right" value="<fmt:formatNumber value='${prpLclaimLoss.kindRest}' pattern='#'/>" onchange="calculateSumClaim(this);">
							</td>
							<td class="input" style="width:8%">
								<input name="prpLclaimLossSumClaim" class="readonly" readonly="readonly" style="text-align: right" value="<fmt:formatNumber value='${prpLclaimLoss.sumClaim}' pattern='#'/>" >
							</td>
							<td class="input" style="width: 8%" style="display:none">
								<rc:rcDate name="prpLclaimLossInputDate" class="readonly" wdatePicker="false" value="${prpLclaimLoss.inputDate}" />
								<input type="hidden" name="prpLclaimLossRemarkFlag" class="input" style="" maxlength="100" value="${prpLclaimLoss.remarkFlag}">
							</td>
							<td class="input" style='width: 3%' align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimLoss();" <s:property value="#inputDisable"/> value="-" style="cursor: hand">
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