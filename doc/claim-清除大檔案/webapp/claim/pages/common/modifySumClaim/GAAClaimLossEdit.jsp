<%--
****************************************************************************
* DESC       ：显示火險調整估損金額 - 險別估損訊息訊息
* AUTHOR     ：中科软
* CREATEDATE ：2015-08-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page import="com.sinosoft.function.insutil.dto.domain.PrpDexchDto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%
	//兑换率信息写到画面上
	PrpDexchDto prpDexchDto;
	List<PrpDexchDto> prpdexchDtoList = (List<PrpDexchDto>) request.getAttribute("prpDexchList");
	if (prpdexchDtoList != null) {
		Iterator<PrpDexchDto> itprpdexch = prpdexchDtoList.iterator();
		while (itprpdexch.hasNext()) {
			prpDexchDto = (PrpDexchDto) itprpdexch.next();
			out.println("  <input type=\"hidden\" name=\"baseCurrency\"" + " value=\"" + prpDexchDto.getBaseCurrency() + "\"" + "\">");
			out.println("  <input type=\"hidden\" name=\"exchCurrency\"" + " value=\"" + prpDexchDto.getExchCurrency() + "\"" + "\">");
			out.println("  <input type=\"hidden\" name=\"exchRate\"" + " value=\"" + prpDexchDto.getExchRate() + "\"" + "\">");
		}
	} else {
		out.println("  <input type=\"hidden\" name=\"baseCurrency\"" + " value=\"1\" >");
	}
%>
<script language="javascript">
	/***
	 *包裝下險別選擇的函數，選擇險別的時候清空下標的的訊息
	 */
	function claimLossKindCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
		var $tr = $(field).closest("tr");
		$tr.find(":input[name='prpLclaimLossItemCode']").val("");
		$tr.find(":input[name='prpLclaimLossItemName']").val("");
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
			}else if("prpLclaimLossItemName" == field.name){
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
		$(":input[name='prpLclaimLossKindName'],:input[name='prpLclaimLossItemName']").bind("propertychange",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<%-- 增加多危险单位--%>
							<td class="input" style="width: 5%">
								<input type="text" name="prpLclaimLossDangerNo" class="codecode" value="1" onclick="viewDangerUnit(this);">
								<%/** 估損調整增加的估損訊息 來源為 2 */%>
								<input type="hidden" name="prpLclaimLossDatafrom" value="2">
							</td>
							<td class="input" style="width: 16%" >
								<input type=input name="prpLclaimLossKindCode" class="codecode" style="width: 50px" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>" value="${defaultKindCode}"
									ondblclick="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="claimLossKindCodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
								<input type=input name="prpLclaimLossKindName" class="codecode" style="width: 130px" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRiskType'/>"
									ondblclick="claimLossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="claimLossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="claimLossKindCodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="input" style="width: 16%" style="align:center">
								<input type='input' name='prpLclaimLossItemCode' class="codecode" style="width: 30px" ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);"
									onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);" onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','0,1,2,4,-2,-1','Y','Y',fm.policyno.value);">
								<%--标的代码--%>
								<input type='input' name="prpLclaimLossItemName" class="codename" style="width: 150px" 
									ondblclick="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);" onkeyup="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);"
									onchange="claimLossItemCodeSelect(this,'policyItemKindCodeNoRisk','-1,0,1,3,-3,-2','Y','N',fm.policyno.value);">
								<%--标的名称--%>
								<input name="prpLclaimLossItemKindNo" type="hidden" value="">
							</td>
							<td class="input" align="center" style="width: 5%">
								<s:select name="prpLclaimLossFeeCategory" listKey="key" listValue="value" list="#request.lossFeeCategoryList" />
							</td>
							<td class="input" style="width: 8%" align="center"><%--保险金额 --%>
								<input type="text" name="prpLclaimLossAmount" class="readonly" readonly style="text-align: right" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
							</td>
							<td class="input" style="width: 6%" align="center">
								<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList"/>
							</td>
							<td class="input" style="width: 8%" align="center">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="幣別" value="${prpLclaim.currency}">
								<input type="text" name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="幣別" value="${prpLclaim.currencyName}">
							</td>
							<td class="input" style="width: 8%" align="center">
								<input name="prpLclaimLossSumClaim" value="0" class="input" style="text-align: right" onchange="calculateSumClaim(this);">
								<input type="hidden" class="input" name="prpLclaimLossKindRest" value="0">
							</td>
							<%-- 获得当前时间 --%>
							<td class="input" style="width: 8%" align="center">
								<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" defaultValue="0" />
							</td>
							<td class="input" style="width: 10%" align="center">
								<input type="text" name="prpLclaimLossHandlerCode" value="${user.userCode}" class="readonly" readonly="readonly" style="width: 40%;">
								<input type="text" name="prpLclaimLossHandlerName" value="${user.userName}" class="readonly" readonly="readonly" style="width: 50%;">
							</td>
							<td class="centertitle" style="width: 7%" align="center">
								<input type="button" ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
								<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
									<table class="common">
										<tr>
											<td class="prompttitle" colspan="6">
												<s:text name="claim.adjustReason" />
												<%-- 调整原因 --%>
											</td>
										</tr>
										<tr>
											<td class="prompt" colspan="6">
												<input name="prpLclaimLossRemarkFlag" class="input" type="text" maxlength="100" value="${prpLclaimLoss.remarkFlag}">
											</td>
										</tr>
										<tr>
											<td colspan=6 class="common">
												<input type="button" class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' <%--关闭--%>
														ACCESSKEY="O"
													onclick="hideSubPage(this,'span_Engage_Context00')">
											</td>
										</tr>
									</table>
								</span>
							</td>
							<td class="input" style='width: 3%' align="center">
								<div>
									<input type="button" name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimFee(this);" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanClaimLoss" style="display:" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ClaimLoss">
					<thead>
						<tr>
							<td class="formtitle" colspan="12"><s:text name="claim.amountInsurLossInfo" /></td><%-- 险别估损金额信息--%>
						</tr>
						<tr>
							<td class="centertitle" style="width: 5%"><s:text name="claim.dangeSerialNum" /></td><%-- 危险单位序号--%>
							<td class="centertitle" style="width: 16%"><s:text name="undwrt.Risks" /><%--险别  --%></td>
							<td class="centertitle" style="width: 16%"><s:text name="compensate.dubang.project" /><%-- 標的物 --%></td>
							<td class="centertitle" style="width: 5%"><s:text name="claim.scope" /><%-- 范围 --%></td>
							<td class="centertitle" style="width: 8%">保險金額</td>
							<td class="centertitle" style="width: 6%"><s:text name="claim.cost" /></td><%-- 费用类别--%>
							<td class="centertitle" style="width: 8%"><s:text name="db.prpLpersonloss.currency" /></td><%-- 币别--%>
							<td class="centertitle" style="width: 8%"><s:text name="claim.amountInsurLoss" /></td><%-- 险别估损金额--%>
							<td class="centertitle" style="width: 8%">修改日期</td><%-- 输入日期--%>
							<td class="centertitle" style="width: 10%">修改人員</td>
							<td class="centertitle" style="width: 7%"><s:text name="claim.adjustReason" /></td><%-- 调整原因--%>
							<td class="formtitle" style="width: 3%">
								<input onclick="collectCurrency();" style="width: 35px" type="button" class="button" value="匯總">
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan="11"><s:text name="prompt.schedule.addRename10" /></td><%--  (按"+"号键增加估损金额信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 3%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('ClaimLoss')" class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="prpLclaimLoss" items="${claimDto.prpLclaimLossList}">
							<tr>
								<%-- 增加多危险单位--%>
								<td class="input" style="width: 5%">
									<input readonly type="text" name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLoss.dangerNo}" >
									<input type="hidden" name="prpLclaimLossDatafrom" value="">
								</td>
								<td class="input" style="width: 16%" >
									<input readonly type="text" name="prpLclaimLossKindCode" class="codecode" style="width: 50px" value="${prpLclaimLoss.kindCode}" > 
									<input readonly type="text" name="prpLclaimLossKindName" class="codename" style="width: 130px" value="${prpLclaimLoss.kindName}" >
								</td>
								<td class="input" style="width: 16%" >
									<input readonly type="text" name="prpLclaimLossItemCode" class="codecode" style="width: 30px" value="${prpLclaimLoss.itemCode}">
									<input readonly type="text" name="prpLclaimLossItemName" class="codename" style="width: 150px" value="${prpLclaimLoss.itemDetailName}">
									<input name="prpLclaimLossItemKindNo" type="hidden" value="${prpLclaimLoss.itemKindNo}">
								</td>
								<td class="input" align="center" style="width: 5%">
									<c:set var="tempSelectedValue" value="${prpLclaimLoss.feeCategory}" /> 
									<s:select name="prpLclaimLossFeeCategory" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossFeeCategoryList" disabled="true" />
								</td>
								<td class="input" style="width: 8%" align="center"><%--保险金额 --%>
									<input type="text" name="prpLclaimLossAmount" class="readonly" readonly style="text-align: right" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaimLoss.amount}' pattern='#'/>">
								</td>
								<td class="input" style="width: 6%" align="center">
									<c:set var="tempSelectedValue" value="${prpLclaimLoss.lossFeeType}" /> 
									<s:select name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" disabled="true" />
								</td>
								<td class="input" style="width: 8%" align="center">
									<input readonly type="text" name="prpLclaimLossCurrency" value="${prpLclaimLoss.currency}" class="readonly" style="width: 30%" title="幣別">
									<input readonly type="text" name="prpLclaimLossCurrencyName" value="${prpLclaimLoss.currencyName}" class="readonly" style="width: 60%" title="幣別">
								</td>
								<td class="input" style="width: 8%" align="center">
									<%-- 解除科学计数法的显示--%>
									<input readonly name="prpLclaimLossSumClaim" class="common" style="text-align: right" value="<fmt:formatNumber value='${prpLclaimLoss.sumClaim}' pattern='#'/>">
									<input readonly type="hidden" class="common" name="prpLclaimLossKindRest" value="<fmt:formatNumber value='${prpLclaimLoss.kindRest}' pattern='#'/>">
								</td>
								<td class="input" style="width: 8%" align="center">
									<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaimLoss.inputDate}" />
								</td>
								<td class="input" style="width: 10%" align="center">
									<input type="text" name="prpLclaimLossHandlerCode" value="${prpLclaimLoss.handlerCode}" class="readonly" readonly="readonly" style="width: 40%;">
									<input type="text" name="prpLclaimLossHandlerName" value="${prpLclaimLoss.handlerName}" class="readonly" readonly="readonly" style="width: 50%;">
								</td>
								<td class="centertitle" style="width: 7%">
									<input type="button" ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
									<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
										<table class="common">
											<tr>
												<td class="prompttitle" colspan="6">
													<s:text name="claim.adjustReason" />
													<%-- 调整原因 --%>
												</td>
											</tr>
											<tr>
												<td class="prompt" colspan="6">
													<input name="prpLclaimLossRemarkFlag" class="readonly" readonly maxlength="100" value="${prpLclaimLoss.remarkFlag}">
												</td>
											</tr>
											<tr>
												<td colspan=6 class="common">
													<input type="button" class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' <%--关闭--%>
															ACCESSKEY="O"
														onclick="hideSubPage(this,'span_Engage_Context00')">
												</td>
											</tr>
										</table>
									</span>
								</td>
								<td class="input" style='width: 3%' align="center">
									<div>
										<input disabled type="button" class="smallbutton" name="buttonClaimLossDelete" onclick="deleteRow(this,'ClaimLoss')" value="-" style="cursor: hand">
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
<script language="javascript">
//显示危险单位划分信息
	function viewDangerUnit(field){
		for (var i=1;i<fm.prpLclaimLossDangerNo.length;i++){
	 		if(fm.prpLclaimLossDangerNo[i]==field){
				var count      = i;
				var policyNo   = fm.policyno.value;
				var damageDate = fm.prpLclaimDamageStartDate.value;
				var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&openerIndex=" + count+"&PageType=ClaimLoss" ;  
				window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}	
//按钮单击事件，用於条款的显示
</script>