<%-- 
****************************************************************************
* DESC       ：赔付标的信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
	function viewDangerUnitCompensateLloss(field){
		for (var i=1;i<fm.prpLlossDtoSerialNo.length;i++){
	 		if(fm.prpLlossDtoDangerNo[i]==field){
	   		var count      = i;
	   		var policyNo   = fm.policyno.value;
	   		var damageDate = fm.damageStartDate.value;
	   		var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&openerIndex=" + count+"&PageType=CompensateLloss";  
     		//field.value="";
     		<%-- 查看危险单位讯息 --%>
     		window.open(submitStr,i18n.title.compensateEidt.dangerUnitInformation,'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}     
var damageKind = new Array();
var damageItemKind = new Array();
var damageItemAmount = new Array();
var damageItemAmountDisplay = new Array();
<c:forEach var="prpCitemKindTemp" items="${prpCitemKindList}" varStatus="prpCitemKind_status">
	damageKind[${prpCitemKind_status.index }]  ='${prpCitemKindTemp.kindCode }';
	damageItemKind[${prpCitemKind_status.index }] ='${prpCitemKindTemp.itemCode }';
	damageItemAmount[${prpCitemKind_status.index }] ='<fmt:formatNumber value="${prpCitemKindTemp.amount }" pattern="#.###"/>';
	damageItemAmountDisplay[${prpCitemKind_status.index }] ='${prpCitemKindTemp.amount }';
</c:forEach>
  function changeCurrency(field){
    var currency = field.value;
    var currencyName = fm.prpLlossDtoCurrency4Name[1].value;
    fm.MergeCurrency.value = currency;
    fm.MergeCurrencyName.value = currencyName;
    if(currency == ""){
    }
  }
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
     var strValue = fm.prpLlossDtoKindCode[findex].value;
     var strValueitem = fm.prpLlossDtoItemCode[findex].value;
     //判断选择的险别是否为出险日期当时生效的险别或者标的
     for (var j=0;j<damageKind.length;j++)
     { 
        if(damageKind[j]==strValue&&damageItemKind[j]==strValueitem)
        { 
            findFlag = 1;
            break;
        }
     }
     if (strValueitem=="")
     {
     	findFlag = 1;
     }
     if(findFlag==0)
     {
         alert(i18n.prompt.compensate.message1);<%--您选择的险别或者标的不是出险日期时的险别或者标的,请重新进行选择--%>
         fm.prpLlossDtoKindCode[findex].value = "";
         fm.prpLlossDtoItemCode[findex].value = "";
         return false;
     } 
}
</script>
<script language="javascript">
  /*
    删除本条WarnRegion之後的处理（可选方法）
  */
  function afterDeletelLoss(field) {
	  calFund(field);
  }

</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center">
	<c:if
		test="${prpDexch.baseCurrency!=null&&prpDexch.baseCurrency!=''&&prpDexch.baseCurrency!=LOCAL_CURRENCY}">
		<tr>
			<td class="title" colspan=3 style="color: red">
				<s:text name="claim.signCurrencyCase" />
				:
			</td>
			<%-- 此案件签单币别为 --%>
			<td colspan=4>
				<input type=text name="BaseCurrency2" class="readonly" readonly
					style="color: red" value="${prpDexch.baseCurrency}">
			</td>
			<td class="title" colspan=3 style="color: red">
				<s:text name="claim.currentExchangeRate" />
				:
			</td>
			<%--当前兑换率为  --%>
			<td colspan=4>
				<input type=text name="ExchRate2" class="readonly" readonly
					style="color: red" value="${prpDexch.exchRate}">
			</td>
		</tr>
	</c:if>
	<c:if
		test="${coinsFlag!=null&&(coinsFlag=='1'||coinsFlag=='2'||coinsFlag=='3')}">
		<tr>
			<td class="title" colspan=14 style="color: red">
				<s:text name="compensate.allLine" />
			</td>
			<%-- ***共保业务，录入损失时请录入总损失；录入费用时请录入我司分摊的费用 --%>
		</tr>
	</c:if>
</table>
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif"
				name="lLossImg" onclick="showPage(this,spanlLoss)">
			<s:text name="compensate.dubang.payMarkInfo" />
			<br>
			<%-- 赔付标的信息 --%>
			<span style="display: none">
				<table class="common" style="display: none" id="lLoss_Data"
					cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 7%">
								<input type=text name="prpLlossDtoDangerNo" class="codecode"
									value="1" onClick="viewDangerUnitCompensateLloss(this);"
									onkeyup="viewDangerUnitCompensateLloss(this);"
									onchange="viewDangerUnitCompensateLloss(this);"><%-- 危险单位号 --%>
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input class="readonlyNo" readonly name="prpLlossDtoSerialNo"
										description="<s:text name='regist.prpLregist.serialNo'/>"><%--序号--%>
									<!--是否超过保单中的限额标志域-->
									<!-- input type="hidden" name="prpLlossDtoOverAmount" -->
									<input type='hidden' name="prpLlossDtoLicenseNo">
									<input type='hidden' name='prpLlossDtoFamilyNo'>
									<input type='hidden' name="prpLlossDtoFeeTypeCode">
									<input type='hidden' name="prpLlossDtoFeeTypeName">
									<input type='hidden' name='prpLlossDtoFamilyName'>
									<input type='hidden' name='prpLlossDtoItemAddress'>
									<input type='hidden' name='prpLlossDtoBuyDate'
										value="2004/12/12">
									<input type='hidden' name='prpLlossDtoDepreRate'>
									<input type='hidden' name='prpLlossDtoCurrency1'
										value="${prpLcompensate.currency}">
									<input type='hidden' name='prpLlossDtoCurrency2'
										value="${prpLcompensate.currency}">
									<input type='hidden' name='prpLlossDtoCurrency3'
										value="${prpLcompensate.currency}">
									<input type='hidden' name='prpLlossDtoFlag'>
									<input type='hidden' name='prpLlossDtoUnit'>
									<input type="hidden" name="prpLlossDtoLossQuantity">
									<input type="hidden" name="prpLlossDtoUnitPrice">
									<input type="hidden" name="prpLlossDtoIndemnityDutyRate">
								</div>
							</td>
							<td class="input" style="width: 15%">
								<%-- 险别 --%>
								<input type="hidden" name=prpLlossDtoKindCode class="codecode"
									style="width: 40"
									ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onblur="checkRepeatItemCode(this);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
								<input type="input" name=prpLlossDtoKindName class="codename"
									style="width: 110"
									ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onblur="checkRepeatItemCode(this);">
								<input type='hidden' name='prpLlossItemKindNo'>
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>

							<td class="input" style="width: 8%">
								<%-- 损失标的 --%>
								<input type="hidden" name="prpLlossDtoItemCode" class="codecode"
									style="width: 30%"
									ondblclick="code_CodeSelect(this, 'PolicyItemKindCode','0,1','Y','Y',fm.policyno.value);"
									onkeyup="code_CodeSelect(this, 'PolicyItemKindCode','0,1','Y','Y',fm.policyno.value);"
									onblur="checkRepeatItemCode(this);judgeKindCode(this);">
								<input name="prpLlossDtoLossName" class="codename"
									style="width: 65"
									ondblclick="code_CodeSelect(this, 'PolicyItemKindCode','-1,0','Y','N',fm.policyno.value);"
									onkeyup="code_CodeSelect(this, 'PolicyItemKindCode','-1,0','Y','N',fm.policyno.value);"
									onblur="checkRepeatItemCode(this);judgeKindCode(this);">
								<input type='hidden' name='prpLlossDtoItemKindNo'>
							</td>
							<td class="input" style="width: 10%">
								<input type="hidden" name="prpLlossDtoAmountDisplay"
									value='<fmt:formatNumber value="${prpLcompensate.sumAmount}" pattern="#"/>'
									class='readonly' readonly>
								<input type="text" name="prpLlossDtoAmount"
									value='<fmt:formatNumber value="${prpLcompensate.sumAmount}" pattern="#"/>'
									class='readonly' readonly>
							</td>
							<td class="input" style="width: 4%">
								<%-- 币别 --%>
								<input name="prpLlossDtoCurrency4" class="readonly" readonly
									value="${prpLcompensate.currency}">
								<input name="prpLlossDtoCurrency4Name" type="hidden"
									value="${prpLcompensate.currencyName}">
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLlossDtoItemValue" class="common"
									onblur="checkLength(this)">
							</td>

							<td class="input" style="width: 7%">
								<input name="prpLlossDtoSumLoss" class="common"
									onblur="calRealpay(this);checkRepeatItemCode(this);calLoss();checkBeyondQuota(this);">
							</td>

							<td class="input" style="width: 7%">
								<input name="prpLlossDtoDeductible" class="common" value="0.00"
									onblur="calRealpay(this);calLoss();checkBeyondQuota(this);">
							</td>

							<td class="input" style="width: 8%">
								<input name="prpLlossDtoClaimRate" class="common" value="100"
									onblur="calRealpay(this);calLoss();checkBeyondQuota(this);">
							</td>

							<td class="input" style="width: 5%">
								<input name="prpLlossDtoSumRest" class="common" value="0.00"
									onblur="calRealpay(this);calLoss();checkBeyondQuota(this);">
							</td>

							<td class="input" style="width: 7%">
								<input name="prpLlossDtoDeductibleRate" class="common" value="0"
									onblur="calRealpay(this);calLoss();">
							</td>

							<td class="input" style="width: 8%">
								<input name="prpLlossDtoSumRealPay" readonly class="common"
									onblur="calRealpay(this);calLoss();checkBeyondQuota(this);"
									onchange="setChangelossChargeFlag();checkBeyondQuota(this);">
							</td>
							<%--賠付對象讯息 --%>
							<td class="input" style="width: 8%">
								<input name="prpLlossDtoPayObjectSerialNo" class="common"
									onclick="setPrpObjectinfoSerialNo(this);" />
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input type=button name="buttonlLossDelete" class="smallbutton"
										onclick="deleteRow(this,'lLoss','prpLlossDtoSerialNo')" value="-"
										style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanlLoss" style="display: none"> <%-- 多行输入展现域 --%>
				<table id="lLoss" class="common" align="center" cellspacing="1"
					cellpadding="3">
					<thead>
						<tr>
							<td class="title" style="width: 7%" align="center">
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%-- 危险单位序号 --%>
							<td class="title" style="width: 4%" align="center">
								<s:text name="db.prpLreplevynew.serialNo" />
							</td>
							<%-- 序号 --%>
							<td class="title" style="width: 15%" align="center">
								<s:text name="undwrt.Risks" />
							</td>
							<%-- 险别 --%>
							<td class="title" style="width: 10%" align="center">
								<s:text name="compensate.lossSubject" />
							</td>
							<%-- 损失标的 --%>
							<td class="title" style="width: 7%" align="center">
								<s:text name="regist.prpLregist.sumAmount" />
							</td>
							<%--保险金额  --%>
							<td class="title" style="width: 4%" align="center">
								<s:text name="regist.prpLregist.currency" />
							</td>
							<%-- 币别 --%>
							<td class="title" style="width: 7%" align="center">
								<s:text name="db.prpCcargoDetail.sumValue" />
							</td>
							<%--货物价值  --%>
							<td class="title" style="width: 7%" align="center">
								<s:text name="compensate.approvedLoss" />
							</td>
							<%-- 核定损失 --%>
							<td class="title" style="width: 7%" align="center">
								<s:text name="db.prpLpersonloss.deductible" />
							</td>
							<%-- 免赔额 --%>
							<td class="title" style="width: 8%" align="center">
								<s:text name="commonAcci.compensate.compensatPercentage" />
							</td>
							<%-- 赔偿比例% --%>
							<td class="title" style="width: 5%" align="center">
								<s:text name="claim.salvage" />
							</td>
							<%-- 残值 --%>
							<td class="title" style="width: 7%" align="center">
								<s:text name="compensate.franchise" />
							</td>
							<%--免赔率%  --%>
							<td class="title" style="width: 8%" align="center">
								<s:text name="claim.compenPay" />
							</td>
							<%-- 赔偿金额 --%>
							<td class="title" style="width: 8%" align="center">賠付對象讯息</td>
							<%-- 賠付對象讯息 --%>
							<td class="title" style="width: 4%" align="center">
								<s:text name="certify.operate" />
							</td>
							<%--操作  --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=14 style="width: 96%">
								<s:text name="prompt.schedule.addRename12" />
							</td>
							<%-- (按"+"号键增加险别信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class="smallbutton"
										onclick="insertRow('lLoss',this,'prpLlossDtoSerialNo')" name="buttonlLossInsert"
										style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="prpLlossTemp" items="${prpLloss.prpLlossList}"
							varStatus="prpLloss_status">
							<tr>
								<td class="input" style="width: 7%">
									<input type=text name="prpLlossDtoDangerNo" class="codecode"
										value="${prpLlossTemp.dangerNo}"
										onClick="viewDangerUnitCompensateLloss(this);"
										onkeyup="viewDangerUnitCompensateLloss(this);"
										onchange="viewDangerUnitCompensateLloss(this);">
								</td>
								<td class="input" style="width: 4%">
									<div align="center">
										<input class="readonlyNo" readonly name="prpLlossDtoSerialNo"
											description="<s:text name='regist.prpLregist.serialNo'/>"
											value="${prpLlossTemp.id.serialNo}">
										<%--序号--%>
										<!--是否超过保单中的限额标志域-->
										<!-- input type="hidden" name="prpLlossDtoOverAmount" -->
										<input type='hidden' name="prpLlossDtoLicenseNo"
											value="${prpLlossTemp.licenseNo}">
										<input type='hidden' name='prpLlossDtoFamilyNo'
											value="${prpLlossTemp.familyNo}">
										<input type='hidden' name="prpLlossDtoFeeTypeCode"
											value="${prpLlossTemp.feeTypeCode}">
										<input type='hidden' name="prpLlossDtoFeeTypeName"
											value="${prpLlossTemp.feeTypeName}">
										<input type='hidden' name='prpLlossDtoFamilyName'
											value="${prpLlossTemp.familyName}">
										<input type='hidden' name='prpLlossDtoItemAddress'
											value="${prpLlossTemp.itemAddress}">
										<input type='hidden' name='prpLlossDtoBuyDate'
											value="${prpLlossTemp.buyDate}">
										<input type='hidden' name='prpLlossDtoDepreRate'
											value="${prpLlossTemp.depreRate}">
										<input type='hidden' name='prpLlossDtoCurrency1'
											value="${prpLlossTemp.currency1}">
										<input type='hidden' name='prpLlossDtoCurrency2'
											value="${prpLlossTemp.currency2}">
										<input type='hidden' name='prpLlossDtoCurrency3'
											value="${prpLlossTemp.currency3}">
										<input type='hidden' name='prpLlossDtoFlag'
											value="${prpLlossTemp.flag}">
										<input type='hidden' name='prpLlossDtoUnit'
											value="${prpLlossTemp.unit}">
										<input type="hidden" name="prpLlossDtoLossQuantity"
											value="${prpLlossTemp.lossQuantity}">
										<input type="hidden" name="prpLlossDtoUnitPrice"
											value="${prpLlossTemp.unitPrice}">
										<input type="hidden" name="prpLlossDtoIndemnityDutyRate"
											value="${prpLlossTemp.indemnityDutyRate}">
									</div>
								</td>

								<td class="input" style="width: 15%">
									<input type="hidden" name=prpLlossDtoKindCode class="codecode"
										style="width: 40"
										ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onblur="checkRepeatItemCode(this);"
										onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									<input type="input" name=prpLlossDtoKindName class="codename"
										style="width: 110"
										ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onblur="checkRepeatItemCode(this);">
									<img src="/claim/images/bgMarkMustInput.jpg">
								</td>
								<td class="input" style="width: 10%">
									<input type="hidden" name="prpLlossDtoItemCode"
										class="codecode" style="width: 30%"
										value="${prpLlossTemp.itemCode}"
										ondblclick="code_CodeSelect(this, 'PolicyItemKindCode','0,1,2,4','Y','Y',fm.policyno.value);"
										onchange="code_CodeSelect(this, 'PolicyItemKindCode','0,1,2,4','Y','Y',fm.policyno.value);"
										onkeyup="code_CodeSelect(this, 'PolicyItemKindCode','0,1,2,4','Y','Y',fm.policyno.value);"
										onblur="checkRepeatItemCode(this);">
									<input name="prpLlossDtoLossName" class="codename"
										style="width: 98%" value="${prpLlossTemp.lossName}"
										ondblclick="code_CodeSelect(this, 'PolicyItemKindCode','-1,0,1,3','Y','N',fm.policyno.value);"
										onchange="code_CodeSelect(this, 'PolicyItemKindCode','-1,0,1,3','Y','N',fm.policyno.value);"
										onkeyup="code_CodeSelect(this, 'PolicyItemKindCode','-1,0,1,3','Y','N',fm.policyno.value);"
										onblur="checkRepeatItemCode(this);">
									<input type='hidden' name='prpLlossDtoItemKindNo'
										value="${prpLlossTemp.itemKindNo}">
								</td>
								<td class=input style="width: 7%">
									<input type="hidden" name="prpLlossDtoAmountDisplay"
										class='readonly' readonly
										value='<fmt:formatNumber value="${prpLlossTemp.amount}" pattern="#"/>'>
									<input type="text" name="prpLlossDtoAmount" class='readonly'
										readonly
										value='<fmt:formatNumber value="${prpLlossTemp.amount}" pattern="#"/>'>
								</td>
								<td class="input" style="width: 4%">
									<input name="prpLlossDtoCurrency4" class="readonly" readonly
										value="${prpLlossTemp.currency4}">
									<input name="prpLlossDtoCurrency4Name" type="hidden"
										value="${prpLcompensate.currency}">
								</td>
								<td class="input" style="width: 7%">
									<input name="prpLlossDtoItemValue" class="common"
										value='<fmt:formatNumber value="${prpLlossTemp.itemValue}" pattern="#"/>'
										onblur="checkLength(this);">
								</td>

								<td class="input" style="width: 7%">
									<input name="prpLlossDtoSumLoss" class="common"
										onblur="calcuateSumRealPay(this);checkBeyondQuota(this);"
										value='<fmt:formatNumber value="${prpLlossTemp.sumLoss}" pattern="#"/>'>
								</td>

								<td class="input" style="width: 7%">
									<input name="prpLlossDtoDeductible" class="common"
										value='<fmt:formatNumber value="${prpLlossTemp.deductible}" pattern="#"/>'
										onblur="calRealpay(this);calLoss();checkBeyondQuota(this);">
								</td>
								<td class="input" style="width: 8%">
									<input name="prpLlossDtoClaimRate" class="common"
										onblur="calcuateSumRealPay(this);checkBeyondQuota(this);"
										value='<fmt:formatNumber value="${prpLlossTemp.claimRate}" pattern="#"/>'
										style="width: 90%">
								</td>
								<td class="input" style="width: 5%">
									<input name="prpLlossDtoSumRest" class="common"
										onblur="calcuateSumRealPay(this);checkBeyondQuota(this);"
										value='<fmt:formatNumber value="${prpLlossTemp.sumRest}" pattern="#"/>'>
								</td>
								<td class="input" style="width: 7%">
									<input name="prpLlossDtoDeductibleRate" class="common"
										value='<fmt:formatNumber value="${prpLlossTemp.deductiblerate}" pattern="#"/>'
										onblur="calRealpay(this);calLoss();checkBeyondQuota(this);">
								</td>
								<td class="input" style="width: 8%">
									<input name="prpLlossDtoSumRealPay" readonly class="common"
										value='<fmt:formatNumber value="${prpLlossTemp.sumRealPay}" pattern="#"/>'
										onblur="calRealpay(this);calLoss();checkBeyondQuota(this);">
								</td>
								<%--賠付對象讯息 --%>
								<td class="input" style="width: 8%">
									<input name="prpLlossDtoPayObjectSerialNo" class="common"
										onclick="setPrpObjectinfoSerialNo(this);"
										value='${prpLlossTemp.payObjectSerialNo}' />
								</td>
								<td class="input" style='width: 4%'>
									<div align="center">
										<input type=button name="buttonlLossDelete"
											class="smallbutton"
											onclick="deleteRow(this,'lLoss','prpLlossDtoSerialNo')" value="-"
											style="cursor: hand" onblur="calRealpay(this);calLoss();">
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
<input type="hidden" name="prpLlossDtoAmountDisplay" value='<fmt:formatNumber value="${prpLcompensate.sumAmount}" pattern="#"/>'>
<div id="prpLPayObjectinfo"
	style='width: 300; display: none; position: absolute; background-color: FFFFFF;'
	class="common" align="left">
	<ul id="uLprpLPayObjectinfo"
		style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<s:if
			test="#attr.prpLpayObjectInfoList==null||#attr.prpLpayObjectInfoList.size()==0">
			<li><s:text name="title.compensateEdit.notPaymentObject"/></li><%-- 沒有賠款給付對象訊息，請錄入賠款給付對象。 --%>
		</s:if>
		<s:else>
			<c:forEach var="prpLpayObjectInfoTemp"
				items="${prpLpayObjectInfoList}">
				<%-- 賠付對象 --%><%-- 賠付金額 --%>
				<li><input type="checkbox" onclick="setPayObjectPayAmount();"
						name="payObjectSerialNo"
						value="${prpLpayObjectInfoTemp.id.serialNo}" /><s:text name="compensate.paymentObject"/>${prpLpayObjectInfoTemp.id.serialNo}
					<s:text name="db.prpLCMain.sumClaim"/>: <input type="text" name="payObjectPayAmount"
						onblur="setPayObjectPayAmount();" value="" class="common"
						style="width: 100px" /></li>
			</c:forEach>
		</s:else>
	</ul>
	<ul align="center"
		style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
				class="button" name="closePrpObjectinfoSerialNo"
				onclick="hideSubPage(this,'prpLPayObjectinfo')"
				value="<s:text name='button.close.value' />" /></li>
	</ul>
</div>