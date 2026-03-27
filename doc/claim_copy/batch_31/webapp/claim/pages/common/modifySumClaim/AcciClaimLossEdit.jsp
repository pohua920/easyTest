<%--
****************************************************************************
* DESC       ：显示(非车险)立案登记的险别估损金额页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-11
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
		out.println("  <input type=\"hidden\" name=\"baseCurrency\"" + " value=1 " + "\">");
	}
%>
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
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onclick="viewDangerUnit(this);">
								<%/** 估損調整增加的估損訊息 來源為 2 */%>
								<input type="hidden" name="prpLclaimLossDatafrom" value="2">
							</td>
							<td class="input" style="width: 25%" style="align:center">
								<input type=text name="prpLclaimLossKindCode" value="" class="codecode" style="width: 50px" title="險別" ondblclick="code_CodeSelect(this,'policyKindCodeOfPerson','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);"
									onchange="code_CodeChange(this,'policyKindCodeOfPerson','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" onkeyup="code_CodeSelect(this,'policyKindCodeOfPerson','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);">
								<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 250px" title="險別" ondblclick="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);"
									onchange="code_CodeChange(this,'policyKindCodeOfPerson','-1,0,1,2','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);" onkeyup="code_CodeSelect(this,'policyKindCodeOfPerson','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.familyno.value);">
								<input name="prpLclaimLossItemKindNo" type="hidden" >
								<input type="hidden" name="prpLclaimLossAmount" value="0">
								<input type="hidden" name="prpLclaimLossItemCode" value="">
								<input type="hidden" name="prpLclaimLossItemName" value="">
								<input type="hidden" class="readonly" name="prpLclaimLossKindRest" value="0">
							</td>
							<td class="input" style="width: 10%" align="center">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="幣別" value="${prpLclaim.currency}">
								<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="幣別" value="${prpLclaim.currencyName}">
							</td>
							<td class="input" style="width: 10%" align="center">
								<input name="prpLclaimLossSumClaim" value="0" class=common style="text-align: right" onblur="collectCurrency1();" >
								<input readonly name="prpLclaimLossFeeCategory" type="hidden" value="G">
								<%-- <s:select name="prpLclaimLossFeeCategory" listKey="key" listValue="value" list="#request.lossFeeCategoryList"/> --%>
							</td>
							<td class="input" style="width: 10%" align="center">
								<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList"/>
							</td>
							<%-- 获得当前时间 --%>
							<td class="input" style="width: 15%" align="center">
								<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" defaultValue="0" />
							</td>
							<td class="input" style="width: 15%" align="center">
								<input type="text" name="prpLclaimLossHandlerCode" value="${user.userCode}" class="readonly" readonly="readonly" style="width: 40%;">
								<input type="text" name="prpLclaimLossHandlerName" value="${user.userName}" class="readonly" readonly="readonly" style="width: 50%;">
							</td>
							<td class="centertitle" style="width: 7%" align="center">
								<input type=button ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
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
												<input type=button class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' <%--关闭--%>
														ACCESSKEY="O"
													onclick="hideSubPage(this,'span_Engage_Context00')">
											</td>
										</tr>
									</table>
								</span>
							</td>
							<td class="input" style='width: 3%' align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanClaimLoss" style="display:" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ClaimLoss">
					<thead>
						<tr>
							<td class="formtitle" colspan="9"><s:text name="claim.amountInsurLossInfo" /></td><%-- 险别估损金额信息--%>
						</tr>
						<tr>
							<td class="centertitle" style="width: 5%"><s:text name="claim.dangeSerialNum" /></td><%-- 危险单位序号--%>
							<td class="centertitle" style="width: 25%"><s:text name="certainLoss.thirdCarLoss.prpLcheckRiskType" /></td><%-- 险别--%>
							<td class="centertitle" style="width: 10%"><s:text name="db.prpLpersonloss.currency" /></td><%-- 币别--%>
							<td class="centertitle" style="width: 10%"><s:text name="claim.amountInsurLoss" /></td><%-- 险别估损金额--%>
							<td class="centertitle" style="width: 10%"><s:text name="claim.cost" /></td><%-- 费用类别--%>
							<td class="centertitle" style="width: 15%">修改日期</td><%-- 输入日期--%>
							<td class="centertitle" style="width: 15%">修改人員</td>
							<td class="centertitle" style="width: 7%"><s:text name="claim.adjustReason" /></td><%-- 调整原因--%>
							<td class="formtitle" style="width: 3%">
								<input onclick="collectCurrency();" style="width: 35px" type="button" class="button" value="匯總">
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan="8"><s:text name="prompt.schedule.addRename10" /></td><%--  (按"+"号键增加估损金额信息，按"-"号键删除信息) --%>
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
									<input readonly type=text name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLoss.dangerNo}" >
									<input type="hidden" name="prpLclaimLossDatafrom" value="">
								</td>
								<td class="input" style="width: 25%" style="align:center">
									<input readonly type=text name="prpLclaimLossKindCode" class="codecode" style="width: 50px" title="險別" value="${prpLclaimLoss.kindCode}">
									<input readonly type=text name="prpLclaimLossKindName" class="codecode" style="width: 250px" title="險別" value="${prpLclaimLoss.kindName}">
									<input name="prpLclaimLossItemKindNo" type="hidden" value="${prpLclaimLoss.itemKindNo}">
									<input type="hidden" name="prpLclaimLossAmount" value="<fmt:formatNumber value='${prpLclaimLoss.amount}' pattern='#'/>">
									<input type="hidden" class="readonly" name="prpLclaimLossItemCode" value="${prpLclaimLoss.itemCode}">
									<input type="hidden" class="readonly" name="prpLclaimLossItemName" value="${prpLclaimLoss.itemDetailName}">
									<input type="hidden" class="readonly" name="prpLclaimLossKindRest" value="0">
								</td>
								<td class="input" style="width: 10%" align="center">
									<input readonly type="text" name="prpLclaimLossCurrency" value="${prpLclaimLoss.currency}" class="readonly" style="width: 30%" title="幣別">
									<input readonly type="text" name="prpLclaimLossCurrencyName" value="${prpLclaimLoss.currencyName}" class="readonly" style="width: 60%" title="幣別">
								</td>
								<td class="input" style="width: 10%" align="center">
									<%-- 解除科学计数法的显示--%>
									<input readonly name="prpLclaimLossSumClaim" class=common style="text-align: right" onblur=" collectCurrency1();" value="<fmt:formatNumber value='${prpLclaimLoss.sumClaim}' pattern='#'/>">
									<input readonly name="prpLclaimLossFeeCategory" type="hidden" value="${prpLclaimLoss.feeCategory}">
								</td>
								<td class="input" style="width: 10%" align="center">
									<c:set var="tempSelectedValue" value="${prpLclaimLoss.lossFeeType}" /> 
									<s:select name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" disabled="true" />
								</td>
								<td class="input" style="width: 15%" align="center">
									<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaimLoss.inputDate}" />
								</td>
								<td class="input" style="width: 15%" align="center">
									<input type="text" name="prpLclaimLossHandlerCode" value="${prpLclaimLoss.handlerCode}" class="readonly" readonly="readonly" style="width: 40%;">
									<input type="text" name="prpLclaimLossHandlerName" value="${prpLclaimLoss.handlerName}" class="readonly" readonly="readonly" style="width: 50%;">
								</td>
								<td class="centertitle" style="width: 7%">
									<input type=button ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
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
													<input type=button class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' <%--关闭--%>
															ACCESSKEY="O"
														onclick="hideSubPage(this,'span_Engage_Context00')">
												</td>
											</tr>
										</table>
									</span>
								</td>
								<td class="input" style='width: 3%' align="center">
									<div>
										<input disabled type=button class="smallbutton" name="buttonClaimLossDelete" onclick="deleteRow(this,'ClaimLoss')" value="-" style="cursor: hand">
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