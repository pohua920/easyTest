<%--
****************************************************************************
* DESC       ：显示(非车险)立案登记的险别估损金额页面
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<c:set var="riskCode" value="${requestScope.prpLclaim.riskCode}" />
<script language="javascript">
	//显示危险单位划分信息
	function viewDangerUnit(field) {
		for ( var i = 1; i < fm.prpLclaimLossSerialNo.length; i++) {
			if (fm.prpLclaimLossDangerNo[i] == field) {
				var count = i;
				var policyNo = fm.policyno.value;
				var damageDate = fm.prpLclaimDamageStartDate.value;
				field.value = "";
				var submitStr = "getDangerUnit.do?policyNo=" + policyNo
						+ "&damageDate=" + damageDate + "&openerIndex=" + count
						+ "&PageType=ClaimLoss";
				window.open(submitStr,'查看危險單位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
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
		sumAmount();
	}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLclaimLossKindName'],:input[name='prpLclaimLossItemDetailName']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
			<s:text name="claim.amountInsurLossInfo" />
			<font color="#FF0000">*</font><br>
			<%--险别估损金额信息  --%>
			<span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 5%">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onclick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
								<input name="prpLclaimLossSerialNo" type="hidden">
							</td>
							<td class="input" style="width: 20%" style="align:center">
								<input type=input name="prpLclaimLossKindCode" class="codecode" style="width: 32%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>" value="${defaultKindCode}"
									ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
								<%--险别--%>
								<input type=input name="prpLclaimLossKindName" class="codecode" style="width: 50%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>"
									ondblclick="claimLossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="claimLossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
								<%--险别--%>
								<font color="red">*</font>
							</td>
							<td class="input" style="width: 14%" style="align:center">
								<input type='input' name='prpLclaimLossItemCode' class="codecode" style="width: 25%" title="<s:text name='db.prpDlimit.itemCode'/>" 
									ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,3,-2,-1','Y','Y',fm.policyno.value);"
									onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,3,-2,-1','Y','Y',fm.policyno.value);" 
									onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,3,-2,-1','Y','Y',fm.policyno.value);">
								<%--标的代码--%>
								<input type='input' name="prpLclaimLossItemDetailName" class="codename" style="width: 65%" title="<s:text name='regist.prpLregist.itemName'/>"
									ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,2,-3,-2','Y','N',fm.policyno.value);" 
									onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,2,-3,-2','Y','N',fm.policyno.value);"
									onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,2,-3,-2','Y','N',fm.policyno.value);">
								<%--标的名称--%>
								<input name="prpLclaimLossItemKindNo" type="hidden">
							</td>
							<td class="input" style="width: 7%" align="center"><%--保险金额 --%>
								<input type=text name="prpLclaimLossAmount" class="readonly" readonly style="" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
							</td>
							<input type="hidden" name="prpLclaimLossItemName" value="">
							<input name="prpLclaimLossFlag" type="hidden" value="">
							<input name="prpLclaimLossFeeCategory" type="hidden" value="">
							<td class="input" style="width: 8%">
								<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" />
							</td>
							<td class="input" style="width: 10%" align="center">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="<s:text name='db.prpDrate.currency'/>" value="${prpLclaim.estiCurrency}">
								<%--币别--%>
								<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="<s:text name='db.prpDrate.currency'/>" value="${strCurrencyName}">
								<%--币别--%>
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLclaimLossSumClaim" class=common style="text-align: right" onchange="calculateSumClaim(this);">
							</td>
							<td class="input" style="width: 8%">
								<input name="prpLclaimLossKindRest" class=common style="width: 90px">
							</td>
							<td class="input" style="width: 10%" style="display:none">
								<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10"
									value="<%=new com.sinosoft.sysframework.common.datatype.DateTime(com.sinosoft.sysframework.common.datatype.DateTime.current().toString(), com.sinosoft.sysframework.common.datatype.DateTime.YEAR_TO_DAY)%>">
							</td>
							<td class="input" style="width: 15%">
								<input name="prpLclaimLossRemarkFlag" style="width: 200px" maxlength="100" class="common">
							</td>
							<td class="input" style='width: 2%' colspan="2" align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimLoss();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table> </span>
			<span id="spanClaimLoss" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ClaimLoss">
					<thead>
						<tr>
							<td class="centertitle" style="width: 5%">
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%-- 危险单位序号 --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="undwrt.Risks" />
							</td>
							<%--险别  --%>
							<td class="centertitle" style="width: 14%">
								<s:text name="compensate.dubang.project" />
							</td>
							<%-- 项目 --%>
							<td class="centertitle" style="width:7%">保險金額</td><%-- 保險金額 --%>
							<td class="centertitle" style="width: 8%">
								<s:text name="claim.costType" />
							</td>
							<%-- 费用类型 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="regist.prpLregist.currency" />
							</td>
							<%--币别  --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="claim.amountInsurLoss" />
							</td>
							<%-- 险别估损金额 --%>
							<td class="centertitle" style="width: 8%">
								<s:text name="claim.salvage" />
							</td>
							<%--  残值--%>
							<td class="centertitle" style="width: 10%" style="display:none">
								<s:text name="modifySumClaim.inputDate" />
							</td>
							<%-- 输入日期 --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="claim.adjustReason" />
							</td>
							<%-- 调整原因 --%>
							<td class="centertitle" style="width: 4%">
								<input onclick="collectCurrency();" style="width: 35px" type="button" class="button" value="<s:text name='button.summary.value'/>">
								<%-- 汇总 --%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="12">
								<table id="ClaimLoss_button" style="width: 100%">
									<tr>
										<td class="title" colspan="12" style="width: 96%">
											<s:text name="prompt.certify.addRemove" />
										</td>
										<%--(按"+"号键增加信息，按"-"号键删除信息) --%>
										<td class="title" align="right" style="width: 4%">
											<div align="center">
												<input type="button" value="+" onclick="insertRow('ClaimLoss')" class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<c:if test="${strBaseCurrency != null && ('' != strBaseCurrency) && strBaseCurrency!= 'NTD'}">
							<tr>
								<td colspan="12">
								<table>
								<tr>
									<td class="title" style="color: red">
										<s:text name="claim.signCurrencyCase" />:
									</td>
									<%-- 此案件签单币别为 --%>
									<td>
										<input type=text name="BaseCurrency2" class="readonly" readonly style="color: red" value="${strBaseCurrency }">
									</td>
								</tr>
								<tr>
									<td class="title" style="color: red">
										<s:text name="compensate.currentExchangeRate" />:
									</td>
									<%-- 当前兑换率为 --%>
									<td>
										<input type=text name="ExchRate2" class="readonly" readonly style="color: red" value="${strExchRate}">
									</td>
								</tr>
								</table>
								</td>
							</tr>
						</c:if>
					</tfoot>
					<tbody>
						<c:set var="indexClaimLoss" value="0" />
						<c:set var="displayType" value="readonly" />
						<c:set var="buttonType" value="disabled" />
						<c:choose>
							<c:when test="${editType=='ADD' || editType=='EDIT'}">
								<c:set var="displayType" value="" />
								<c:set var="buttonType" value="" />
							</c:when>
							<c:otherwise>
								<c:set var="displayType" value="" />
								<c:set var="buttonType" value="disabled" />
							</c:otherwise>
						</c:choose>
						<c:forEach var="prpLclaimLossForProp" items="${prpLclaimLoss.claimLossList}">
							<tr>
								<td class="input" style="width: 5%" style="align:center">
									<input type=text name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLossForProp.dangerNo}" onclick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
									<input name="prpLclaimLossSerialNo" type="hidden" value="${prpLclaimLossForProp.id.serialNo }">
								</td>
								<td class="input" style="width: 20%" style="align:center">
									<input type=input name="prpLclaimLossKindCode" class="codecode" style="width: 32%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>" value="${prpLclaimLossForProp.kindCode}"
										ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
										onkeyup="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"><%--险别--%>
									<input type=input name="prpLclaimLossKindName" class="codecode" style="width: 50%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>" value="${prpLclaimLossForProp.kindName}"
										ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="claimLossKindCodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"><%--险别--%>
									<font color="red">*</font>
								</td>
								<td class="input" style="width: 14%" style="align:center">
									<input type='input' name='prpLclaimLossItemCode' class="codecode" style="width: 25%" title="<s:text name='db.prpDlimit.itemCode'/>" value="${prpLclaimLossForProp.itemCode}"
										ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,3,-2,-1','Y','Y',fm.policyno.value);"
										onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,3,-2,-1','Y','Y',fm.policyno.value);"
										onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,3,-2,-1','Y','Y',fm.policyno.value);"><%--标的代码--%>
									<input type='input' name="prpLclaimLossItemDetailName" class="codename" style="width: 65%" title="<s:text name='regist.prpLregist.itemName'/>" value="${prpLclaimLossForProp.itemDetailName}"
										ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,2,-3,-2','Y','N',fm.policyno.value);"
										onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,2,-3,-2','Y','N',fm.policyno.value);"
										onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,2,-3,-2','Y','N',fm.policyno.value);"><%--标的名称--%>
									<input name="prpLclaimLossItemKindNo" type="hidden" value="${prpLclaimLossForProp.itemKindNo}">
								</td>
								<td class="input" style="width: 7%" align="center"><%--保险金额 --%>
									<input type=text name="prpLclaimLossAmount" class="readonly" readonly style="" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaimLossForProp.amount}' pattern='#'/>">
								</td>
								<input type="hidden" name="prpLclaimLossItemName" value="">
								<td class="input" style="width: 8%">
									<c:set var="tempSelectedValue" value="${prpLclaimLossForProp.lossFeeType}" />
									<s:select name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" />
								</td>
								<td class="input" style="width: 10%" align="center">
									<input type="text" name="prpLclaimLossCurrency" value="${prpLclaimLossForProp.currency }" class="readonly" readonly style="width: 30%" title="<s:text name='db.prpDrate.currency'/>" ><%--币别--%>
									<input type=text name="prpLclaimLossCurrencyName" value="${prpLclaimLossForProp.currencyName }" class="readonly" readonly style="width: 60%" title="<s:text name='db.prpDrate.currency'/>" ><%--币别--%>
								</td>
								<td class="input" style="width: 10%">
									<input name="prpLclaimLossSumClaim" class=common style="text-align: right" value="<fmt:formatNumber value='${prpLclaimLossForProp.sumClaim}' pattern="#"/>" onchange="calculateSumClaim(this);">
								</td>
								<td class="input" style="width: 8%">
									<input name="prpLclaimLossKindRest" class=common style="width: 90px" value="<fmt:formatNumber value="${prpLclaimLossForProp.kindRest}" pattern="#"/>">
								</td>
								<td class="input" style="width: 10%" style="display:none">
									<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10" value="${prpLclaimLossForProp.inputDate}">
								</td>
								<td class="input" style="width: 15%">
									<input name="prpLclaimLossRemarkFlag" style="width: 200px" maxlength="100" class="common" value="${prpLclaimLossForProp.remarkFlag}">
									<input name="prpLclaimLossFlag" type="hidden" value="${prpLclaimLossForProp.flag}">
									<input name="prpLclaimLossFeeCategory" type="hidden" value="${prpLclaimLossForProp.feeCategory}">
								</td>
								<td class="input" style='width: 4%' colspan="2" align="center">
									<div>
										<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimLoss();" <c:out value="${buttonType}"/> value="-" style="cursor: hand">
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