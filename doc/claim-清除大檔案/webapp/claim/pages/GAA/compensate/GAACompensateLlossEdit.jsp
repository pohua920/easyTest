<%--
****************************************************************************
* DESC	   ：赔付标的信息页面
* AUTHOR	 ：理赔组
* CREATEDATE ： 2013-07-11
* MODIFYLIST ：   Name	   Date			Reason/Contents
*		  ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
var damageKind = new Array();
var damageItemKind = new Array();
var damageItemAmount = new Array();
var damageItemAmountDisplay = new Array();
<c:forEach var="prpCitemKindTemp" items="${prpCitemKindList}" varStatus="prpCitemKind_status">
	damageKind[${prpCitemKind_status.index }]  ='${prpCitemKindTemp.kindCode }';
	damageItemKind[${prpCitemKind_status.index }] ='${prpCitemKindTemp.itemCode }';
	damageItemAmountDisplay[${prpCitemKind_status.index }] ='<fmt:formatNumber value="${prpCitemKindTemp.amount }" pattern="#"/>';
	damageItemAmount[${prpCitemKind_status.index }] ='${prpCitemKindTemp.amount }';
</c:forEach>
	function viewDangerUnitCompensateLloss(field){
		for (var i=1;i<fm.prpLlossDtoSerialNo.length;i++){
	 		if(fm.prpLlossDtoDangerNo[i]==field){
	   		var count	  = i;
	   		var policyNo   = fm.policyno.value;
	   		var damageDate = fm.damageStartDate.value;
	   		//field.value="";
	   		var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&openerIndex=" + count+"&PageType=CompensateLloss";  
	 		window.open(submitStr,'查看危險單位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}  

  function judgeKindCode(Field){
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
	 var strValue = fm.prpLlossDtoKindCode[findex].value;
	 var strValueitem = fm.prpLlossDtoItemCode[findex].value;
	 //判断选择的险别是否为出险日期当时生效的险别或者标的
	 for (var j=0;j<damageKind.length;j++)
	 { 
		if(strValueitem==""||damageKind[j]==strValue&&damageItemKind[j]==strValueitem)
		{ 
			fm.prpLlossDtoAmount[findex].value = damageItemAmountDisplay[j];	 //生成保额，出险时的保额
			findFlag = 1;
			break;
		}
	 }
	 if(findFlag==0)
	 {
		 alert("<s:text name='prompt.compensate.message1'/>");<%--您选择的险别或者标的不是出险日期时的险别或者标的,请重新进行选择--%>
		 fm.prpLlossDtoKindCode[findex].value = "";
		 fm.prpLlossDtoItemCode[findex].value = "";
		 return false;
	 } 
}
</script>
<script language="javascript">

function calClaimRate(field){
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	var amount = parseFloat($prpLlossObject.find(":input[name='prpLlossDtoAmount']").val());
	var itemValue = initValue($prpLlossObject.find(":input[name='prpLlossDtoItemValue']"),0,'<%=ConstantCodes.LOCAL_CURRENCY%>');
	if(amount !=0 && itemValue !=0){
		var $claimRate = $prpLlossObject.find(":input[name='prpLlossDtoClaimRate']");
		$claimRate.val(Math.round((itemValue/amount)*100));
	}
}
/**
 *删除本条标的赔付之後的处理
 */
function afterDeletelLoss(deletObject,btnField,pageCode,csFieldName){
	calFund();
}
/***
 *包裝下險別選擇的函數，選擇險別的時候清空下標的的訊息
 */
function prpLlossKindCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	$prpLlossObject.find(":input[name='prpLlossDtoItemCode']").val("");
	$prpLlossObject.find(":input[name='prpLlossDtoLossName']").val("");
	$prpLlossObject.find(":input[name='prpLlossDtoItemKindNo']").val("");
	$prpLlossObject.find(":input[name='prpLlossDtoAmount']").val(0);
	$prpLlossObject.find(":input[name='prpLlossDtoItemValue']").val(0);
	code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition);
}
/***
 *包裝下標的選擇的函數，選擇標的時的時候，若有險別，則加上險別的條件
 */
function prpLlossItemCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	var kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val();
	if($.trim(kindCode).length!=0){
		otherCondition += "|"+kindCode;
		if("prpLlossDtoItemCode" == field.name){
			codeRelation = codeRelation.substr(0,9);
		}else if("prpLlossDtoLossName" == field.name){
			codeRelation = codeRelation.substr(0,10);//codecode 是-1，多出一位
		}
	}else{
		otherCondition += "|";
	}
	otherCondition += '|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value;
	code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition);
}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLlossDtoKindName'],:input[name='prpLlossDtoLossName'],:input[name='prpLlossDtoPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		})
	})
</script>
<table class="common" align="center">
	<c:if test="${prpDexch.baseCurrency!=null&&prpDexch.baseCurrency!=''&&prpDexch.baseCurrency!=LOCAL_CURRENCY}">
		<tr>
			<td class="title" colspan=3 style="color: red">
				<s:text name="claim.signCurrencyCase" />:
			</td>
			<%-- 此案件签单币别为 --%>
			<td colspan=4 class="title">
				<input type=text name="BaseCurrency2" class="readonly" readonly style="color: red" value="${prpDexch.baseCurrency }">
			</td>
			<td class="title" colspan=3 style="color: red">
				<s:text name="claim.currentExchangeRate" />
				:
			</td>
			<%--当前兑换率为  --%>
			<td colspan=4 class="title">
				<input type=text name="ExchRate2" class="readonly" readonly style="color: red" value="${prpDexch.exchRate }">
			</td>
		</tr>
	</c:if>
	<c:if test="${coinsFlag!=null&&(coinsFlag=='1'||coinsFlag=='2'||coinsFlag=='3')}">
		<tr style="display: none;">
			<td class="title" colspan=14 style="color: red">
				<s:text name="compensate.allLine" />
			</td>
			<%-- ***共保业务，录入损失时请录入总损失；录入费用时请录入我司分摊的费用 --%>
		</tr>
	</c:if>
</table>
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr class="common">
		<td colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="lLossImg" onclick="showPage(this,spanlLoss)">
			<s:text name="compensate.dubang.payMarkInfo" />
			<br>
			<%-- 赔付标的信息 --%>
			<span style="display: none">
				<table class="common" style="display: none" id="lLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr name="prpLlossObject">
							<td class="input" style="width: 4%">
								<div align="center">
									<input class="readonlyNo" readonly name="prpLlossDtoSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>"><%--序号--%>
								</div>
							</td>
							<td class="subformtitle" style="width: 92%">
								<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
									<!--是否超过保单中的限额标志域-->
									<!-- input type="hidden" name="prpLlossDtoOverAmount" -->
									<input type='hidden' name="prpLlossDtoLicenseNo">
									<input type='hidden' name='prpLlossDtoFamilyNo'>
									<input type='hidden' name="prpLlossDtoFeeTypeCode">
									<input type='hidden' name="prpLlossDtoFeeTypeName">
									<input type='hidden' name='prpLlossDtoFamilyName'>
									<input type='hidden' name='prpLlossDtoItemAddress'>
									<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
									<input type='hidden' name='prpLlossDtoDepreRate'>
									<input type='hidden' name='prpLlossDtoFlag'>
									<input type='hidden' name='prpLlossDtoUnit'>
									<input type="hidden" name="prpLlossDtoLossQuantity">
									<input type="hidden" name="prpLlossDtoUnitPrice">
									<input type="hidden" name="prpLlossDtoIndemnityDutyRate">
									<input type='hidden' name='prpLlossDtoCurrency1' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
									<input type='hidden' name='prpLlossDtoCurrency2' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
									<input type='hidden' name='prpLlossDtoCurrency3' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
									<input type='hidden' name='prpLlossDtoCurrency4' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.dangeSerialNum" />：
										</td>
										<%-- 危险单位序号 --%>
										<td class="input" style="width: 35%">
											<input type=text name="prpLlossDtoDangerNo" class="codecode" style="width: 72%" value="1" onClick="viewDangerUnitCompensateLloss(this);" onchange="viewDangerUnitCompensateLloss(this);"
												onblur="viewDangerUnitCompensateLloss(this);">
										</td>
										<td class="title" style="width: 15%">
											<s:text name="undwrt.Risks" />：<%-- 险别 --%>
										</td>
										<td class="input" style="width: 35%">
											<input type="input" name=prpLlossDtoKindCode class="codecode" style="width: 40px" onblur="clearPrpLloss(this);" 
												ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" >
											<input type="input" name=prpLlossDtoKindName class="codename" style="width: 110px" onblur="clearPrpLloss(this);"
												ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
												onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
											<input type='hidden' name='prpLlossItemKindNo'>
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</td>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="compensate.lossSubject" />：
										</td>
										<%-- 损失标的 --%>
										<td class="input" style="width: 85%" colspan="3">
											<input type='input' name='prpLlossDtoItemCode' class="codecode" style="width: 40px" 
												ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);"
												onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);" 
												onchange="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);" 
												onblur="checkRepeatItemCode(this);getCompensateLossHisPaid(this);" >
											<input name="prpLlossDtoLossName" class="codename" style="width: 330px" 
												ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);"
												onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);" 
												onchange="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);"
												onblur="checkRepeatItemCode(this);getCompensateLossHisPaid(this);">
											<input type='hidden' name='prpLlossDtoItemKindNo' value="">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="regist.prpLregist.sumAmount" />：
										</td>
										<%-- 保险金额 --%>
										<td class="input" style="width: 35%">
											<input type="text" name="prpLlossDtoAmount" value="0" class='readonly' readonly>
										</td>
										<td class="title" style="width: 15%">
											<s:text name="db.prpLCitemKind.value" />：
										</td>
										<%-- 保险价值 --%>
										<td class="input" style="width: 35%">
											<input name="prpLlossDtoItemValue" class="input" style="width: 180px" value="0" onchange="setClaimRate(this);">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="compensate.authorizedCurrency" />：
										</td>
										<%-- 核定币别 --%>
										<td class="input" style="width: 35%" >
											<input type="text" name="Currency" class="common" readonly="readonly" value="<%=ConstantCodes.LOCAL_CURRENCY%>" style="width: 40px">
											<input type="text" name="CurrencyName" class="readonly" readonly value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>" style="width: 80px">
										</td>
										<td class="title" style="width: 15%">累計賠付金額：</td>
										<td class="input" >
											<input type="text" name="prpLlossDtoHisPaid" class="readonly" readonly style="width: 90px" value="0">
										</td>
									</tr>
									<tr>
										<td class="title">
											<s:text name="compensate.approvedLoss" />：
										</td>
										<%-- 核定损失 --%>
										<td class="input">
											<input name="prpLlossDtoSumLoss" type="text" class="input" value="0" style="width: 180px" onchange="calRealpay(this);" >
										</td>
										<td class="title" style="width: 15%">
											<s:text name="db.prpLpersonloss.deductible" />：
										</td>
										<%-- 免赔额 --%>
										<td class="input" style="width: 35%">
											<input name="prpLlossDtoDeductible" type="text" class="input" value="0" style="width: 180px" onchange="calRealpay(this);">
										</td>
									</tr>
									<tr>
										<td class="title">
											<s:text name="db.prpLpersonloss.claimRate" />
											：
										</td>
										<%-- 赔付比例 --%>
										<td class="input">
											<input name="prpLlossDtoClaimRate" type="text" class="input" style="width: 180px" title="賠付比例" value="100" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">
											%
										</td>
										<td class="title">
											<s:text name="common.compensate.deductibleRate" />
											：
										</td>
										<%-- 自负额比率 --%>
										<td class="input">
											<input name="prpLlossDtoDeductibleRate" class="input" style="width: 180px" title="自負額比率" value="0" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">
											%
										</td>
									</tr>
									<tr>
										<td class="title">賠付幣別：</td>
										<td class="input">
											<select name="prpLlossDtoCurrency" class="input" style="width: 180px" onchange="getPrpLlossDtoExchRate(this);">
												<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
													<option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}" /> - <c:out value="${tempMap.value}" /></option>
												</c:forEach>
											</select>
										</td>
										<td class="title" style="width: 15%">匯率：</td>
										<td class="input" style="width: 35%">
											<input name="prpLlossDtoExchRate" value="1" class="input" readonly="readonly" style="width: 80px" onchange="calRealpay(this);">
										</td>
									</tr>
									<tr>
										<td class="title">剔除金額/殘值：</td>
										<td class="input">
											<input name="prpLlossDtoSumRest" type="text" class="input" value="0" style="width: 180px" onchange="calRealpay(this);">
										</td>
										<td class="title" style="width: 15%">賠償金額（NTD）：</td>
										<td class="input" style="width: 35%">
											<input name="prpLlossDtoSumRealPayNTD" style="width: 180px" value="0" readonly class="readonly" onchange="calRealpay(this);">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.compenPay" />：<%-- 赔偿金额 --%>
										</td>
										<td class="input" style="width: 35%">
											<input name="prpLlossDtoSumRealPay" style="width: 180px" readonly class="readonly" onchange="calRealpay(this);">
										</td>
										<td class="title" style="width: 15%">賠付對象讯息：</td>
										<%--賠付對象讯息 --%>
										<td class="input" style="width: 35%">
											<input name="prpLlossDtoPayObjectSerialNo" style="width: 180px" class="input" readonly onclick="setPrpObjectinfoSerialNo(this);"/>
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">保留預估：</td>
										<td class="input" style="width: 35%">
											<s:select name="prpLlossDtoReservedEstimate" list="#attr.reservedEstimateList" value="#attr.tempPrpLloss.reservedEstimate"></s:select>
										</td>
										<td class="title" style="width: 15%"></td>
										<td class="input" style="width: 35%">
										</td>
									</tr>
								</table>
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input type=button name="buttonlLossDelete" class="smallbutton" onclick="deleteRow(this,'lLoss','prpLlossDtoSerialNo');" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<span id="spanlLoss" style="display: none"> <%-- 多行输入展现域 --%>
				<table id="lLoss" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="centertitle" style="width: 4%">
								<s:text name="db.prpLreplevynew.serialNo" />
							</td>
							<%-- 序号 --%>
							<td class="centertitle" style="width: 96%" colspan=2>
								<s:text name="db.prpLregistText.context" />
							</td>
							<%-- 内容 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">
								<s:text name="prompt.schedule.addRename12" />
							</td>
							<%-- (按"+"号键增加险别信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('lLoss',this,'prpLlossDtoSerialNo');" class="smallbutton" name="buttonlLossInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="tempPrpLloss" items="${prpLloss.prpLlossList}" varStatus="prpLloss_status">
							<tr name="prpLlossObject">
								<td class="input" style="width: 4%">
									<div align="center">
										<input class="readonlyNo" readonly name="prpLlossDtoSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>" value="${tempPrpLloss.id.serialNo}"><%--序号--%>
									</div>
								</td>
								<td class="subformtitle" style="width: 92%">
									<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
										<!--是否超过保单中的限额标志域-->
										<!-- input type="hidden" name="prpLlossDtoOverAmount" -->
										<input type='hidden' name="prpLlossDtoLicenseNo" value="${tempPrpLloss.licenseNo}">
										<input type='hidden' name='prpLlossDtoFamilyNo' value="${tempPrpLloss.familyNo}">
										<input type='hidden' name="prpLlossDtoFeeTypeCode" value="${tempPrpLloss.feeTypeCode}">
										<input type='hidden' name="prpLlossDtoFeeTypeName" value="${tempPrpLloss.feeTypeName}">
										<input type='hidden' name='prpLlossDtoFamilyName' value="${tempPrpLloss.familyName}">
										<input type='hidden' name='prpLlossDtoItemAddress' value="${tempPrpLloss.itemAddress}">
										<input type='hidden' name='prpLlossDtoBuyDate' value="${tempPrpLloss.buyDate}">
										<input type='hidden' name='prpLlossDtoDepreRate' value="${tempPrpLloss.depreRate}">
										<input type='hidden' name='prpLlossDtoFlag' value="${tempPrpLloss.flag}">
										<input type='hidden' name='prpLlossDtoUnit' value="${tempPrpLloss.unit}">
										<input type="hidden" name="prpLlossDtoLossQuantity" value="${tempPrpLloss.lossQuantity}">
										<input type="hidden" name="prpLlossDtoUnitPrice" value="${tempPrpLloss.unitPrice}">
										<input type="hidden" name="prpLlossDtoIndemnityDutyRate" value="${tempPrpLloss.indemnityDutyRate}">
										<input type='hidden' name='prpLlossDtoCurrency1' value="${tempPrpLloss.currency1}">
										<input type='hidden' name='prpLlossDtoCurrency2' value="${tempPrpLloss.currency2}">
										<input type='hidden' name='prpLlossDtoCurrency3' value="${tempPrpLloss.currency3}">
										<input type='hidden' name='prpLlossDtoCurrency4' value="${tempPrpLloss.currency4}">
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="regist.prpLregist.serialNo" />
												<s:text name="claim.dangeSerialNum" />
												：
											</td>
											<%-- 危险单位序号 --%>
											<td class="input" style="width: 35%">
												<input type=text name="prpLlossDtoDangerNo" class="codecode" style="width: 72%" value="${tempPrpLloss.dangerNo }" onClick="viewDangerUnitCompensateLloss(this);"
													onchange="viewDangerUnitCompensateLloss(this);" onblur="viewDangerUnitCompensateLloss(this);">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="undwrt.Risks" />
												：
											</td>
											<%-- 险别 --%>
											<td class="input" style="width: 35%">
												<input type="input" name=prpLlossDtoKindCode value="${tempPrpLloss.kindCode}" class="codecode" style="width: 40" onblur="clearPrpLloss(this);" 
													ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
													onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" >
												<input type="input" name=prpLlossDtoKindName value="${tempPrpLloss.kindName}" class="codename" style="width: 110" onblur="clearPrpLloss(this);" 
													ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
													onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
												<input type='hidden' name='prpLlossItemKindNo'>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="compensate.lossSubject" />：<%-- 损失标的 --%>
											</td>
											<td class="input" style="width: 85%" colspan="3">
												<input type='input' name='prpLlossDtoItemCode' class="codecode" style="width: 40" value="${tempPrpLloss.itemCode}"
													ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);" 
													onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);"
													onchange="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);"
													onblur="checkRepeatItemCode(this);getCompensateLossHisPaid(this);" >
												<input name="prpLlossDtoLossName" class="codename" style="width: 330px" value="${tempPrpLloss.lossName}"
													ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);" 
													onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);"
													onchange="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);" 
													onblur="checkRepeatItemCode(this);getCompensateLossHisPaid(this);" >
												<input type='hidden' name='prpLlossDtoItemKindNo' value="${tempPrpLloss.itemKindNo}">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="regist.prpLregist.sumAmount" />：<%-- 保险金额 --%>
											</td>
											<td class="input" style="width: 35%">
												<input type="text" name="prpLlossDtoAmount" class='readonly' readonly value="<fmt:formatNumber value='${tempPrpLloss.amount}' pattern='#'/>">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLCitemKind.value" />：<%-- 保险价值 --%>
											</td>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoItemValue" class="input" style="width: 180px" value="<fmt:formatNumber value='${tempPrpLloss.itemValue}' pattern='#'/>" onchange="setClaimRate(this);">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="compensate.authorizedCurrency" />：<%-- 核定币别 --%>
											</td>
											<td class="input" style="width: 35%" >
												<input type="text" name="Currency" class="common" readonly="readonly" value="<%=ConstantCodes.LOCAL_CURRENCY%>" style="width: 40px">
												<input type="text" name="CurrencyName" class="readonly" readonly value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>" style="width: 80px">
											</td>
											<td class="title" style="width: 15%">
												累計賠付金額：
											</td>
											<td class="input" >
												<input type="text" name="prpLlossDtoHisPaid" class="readonly" readonly style="width: 90px" value="${tempPrpLloss.hisPaid}">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="compensate.approvedLoss" />：
											</td>
											<%-- 核定损失 --%>
											<td class="input">
												<input name="prpLlossDtoSumLoss" class="input" style="width: 180px" value="<fmt:formatNumber value='${tempPrpLloss.sumLoss}' pattern='#.##'/>" onchange="calRealpay(this);">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonloss.deductible" />
												：
											</td>
											<%-- 自负额 --%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoDeductible" type="text" class="input" style="width: 180px" value="<fmt:formatNumber value='${tempPrpLloss.deductible}' pattern='#.##'/>" onchange="calRealpay(this);">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="db.prpLpersonloss.claimRate" />
												：
											</td>
											<%-- 赔付比例 --%>
											<td class="input">
												<input name="prpLlossDtoClaimRate" class="input" style="width: 180px" value="<fmt:formatNumber value='${tempPrpLloss.claimRate}' pattern='#.##'/>" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">%
											</td>
											<td class="title">
												<s:text name="common.compensate.deductibleRate" />
												：
											</td>
											<%-- 免赔率 --%>
											<td class="input">
												<input name="prpLlossDtoDeductibleRate" class="input" style="width: 180px" value="<fmt:formatNumber value='${tempPrpLloss.deductiblerate}' pattern='#.##'/>" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">%
											</td>
										</tr>
										<tr>												
											<td class="title">賠付幣別：</td>
											<td class="input">
												<select name="prpLlossDtoCurrency" class="input" style="width: 180px" onchange="getPrpLlossDtoExchRate(this)">
													<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
														<option value="${tempMap.key}" <c:if test="${tempMap.key==tempPrpLloss.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}" /> - <c:out value="${tempMap.value}" /></option>
													</c:forEach>
												</select>
											</td>
											<td class="title" style="width: 15%">匯率：</td>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoExchRate" value="${tempPrpLloss.exchRate}" onchange="calRealpay(this);"  class="input" style="width: 80px" readonly="readonly"  >
											</td>
										</tr>
										<tr>
											<td class="title">剔除金額/殘值：</td>
											<td class="input">
												<input name="prpLlossDtoSumRest" class="input" style="width: 180px" value="<fmt:formatNumber value='${tempPrpLloss.sumRest}' pattern='#.##'/>" onchange="calRealpay(this);">
											</td>
											<td class="title" style="width: 15%">賠償金額（NTD）：</td>
											<%-- 赔偿金额 --%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoSumRealPayNTD" style="width: 180px" class="readonly" readonly value="<fmt:formatNumber value='${tempPrpLloss.sumRealPay*tempPrpLloss.exchRate}' pattern='#'/>" >
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.compenPay" />：<%-- 賠償金額  --%>
											</td>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoSumRealPay" style="width: 180px" class="readonly" readonly value="<fmt:formatNumber value='${tempPrpLloss.sumRealPay}' pattern='#.##'/>" onchange="calRealpay(this);">
											</td>
											<td class="title" style="width: 15%">賠付對象讯息：</td>
											<%--賠付對象讯息 --%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoPayObjectSerialNo" style="width: 180px" class="input" readonly onclick="setPrpObjectinfoSerialNo(this);" value='${tempPrpLloss.payObjectSerialNo}'/>
											</td>
										</tr>
										<tr>
											<td class="title" >保留預估：</td>
											<td class="input" >
												<s:select name="prpLlossDtoReservedEstimate" list="#attr.reservedEstimateList" value="#attr.tempPrpLloss.reservedEstimate"></s:select>
											</td>
											<td class="title" ></td>
											<td class="input" >
											</td>
										</tr>
									</table>
								</td>
								<td class="input" style='width: 4%'>
									<div align="center">
										<input type=button name="buttonlLossDelete" class="smallbutton" onclick="deleteRow(this,'lLoss','prpLlossDtoSerialNo');" value="-" style="cursor: hand">
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