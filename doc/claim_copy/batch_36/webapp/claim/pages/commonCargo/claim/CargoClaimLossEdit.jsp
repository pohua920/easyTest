<%--
****************************************************************************
* DESC	   ：显示立案登记的险别估损金额页面
* AUTHOR	 ：理赔组
* CREATEDATE ：2014-04-17
* MODIFYLIST ：   Name	   Date			Reason/Contents
*		  ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var damageKind = new Array();

<c:forEach var="damageKindList" items="${damageKindList}" varStatus="damageKindListIndex">
damageKind[${damageKindListIndex.index}]   ="${prpCitemKind.kindCode}";
</c:forEach>

	function viewDangerUnit(field){
		for (var i=1;i<fm.prpLclaimLossSerialNo.length;i++){
			if(fm.prpLclaimLossDangerNo[i]==field){
			var count	  = i;
			var policyNo   = fm.policyno.value;
			var damageDate = fm.prpLclaimDamageStartDate.value;
			field.value="";
			var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&openerIndex=" + count+"&PageType=ClaimLoss" ;  
			<%-- 查看危险单位信息 --%>
			window.open(submitStr,i18n.title.dangerUnitBeforeEdit.RiskUnitInformation,'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
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
	var strValue = fm.prpLclaimLossKindCode[findex].value;
	//判断选择的险别是否为出险日期当时生效的险别
	for (var j=0;j<damageKind.length;j++)
	{
		if(damageKind[j]==strValue)
		{
			findFlag = 1;
			break;
		}
	}
	if(findFlag==0)
	{
		<%-- 您选择的险别不是出险日期时的险别,请重新进行选择 --%>
		alert(i18n.claim.selecNotDateDangerAgain);
		fm.prpLclaimLossKindCode[findex].value = "";
		return false;
	} 
}
</script>
<script language="javascript">
  /*
    删除一条之後的处理（可选方法）
  */
  function afterDeleteClaimLoss(field) {
	  collectClaimLoss(field);
  }

</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
			<s:text name="claim.amountInsurLossInfo" />
			<font color="#FF0000">*</font><br>
			<%-- 险别估损金额信息 --%>
			<span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 5%">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onClick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
							</td>
							<td class="input" style="width: 20%" style="align:center">
								<input type="text" name="prpLclaimLossKindCode" class="codecode" style="width: 30%" title="<s:text name="db.prpCitemKind.kindCode" />" value="${defaultKindCode}"
									<s:if test="#request.editType =='ADD' || #request.editType =='EDIT'">
					ondblclick= "code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
					onkeyup= "code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
					onchange= "code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
					onchange="judgeKindCode(this);"
					</s:if>> <%-- 险别代码 --%>
								<input type="text" name="prpLclaimLossKindName" class="codecode" style="width: 65%" title="<s:text name="db.prpCname.kindName" />"
									<s:if test="#request.editType =='ADD' || #request.editType =='EDIT'">
					ondblclick= "code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
					onkeyup= "code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
					onchange= "code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
					onchange="judgeKindCode(this);"
					</s:if>> <%-- 险别名称 --%>
							</td>
							<td class="input" style="width: 14%" style="align:center">
								<input type="text" name="prpLclaimLossItemCode" class="codecode" style="width: 25%" title="<s:text name="db.prpDlimit.itemCode" />" ondblclick="code_CodeSelect(this,'policyItemKindCodeNoRisk','0,1','Y','Y',fm.policyno.value);"
									onkeyup="code_CodeSelect(this,'policyItemKindCodeNoRisk','0,1','Y','Y',fm.policyno.value);" onchange="code_CodeSelect(this,'policyItemKindCodeNoRisk','0,1','Y','Y',fm.policyno.value);"
									onchange="judgeKindCode(this);"> <%-- 标的代码 --%>
								<input type="text" name="prpLclaimLossItemDetailName" class="codename" style="width: 65%" title="<s:text name="regist.prpLregist.itemName" />"
									ondblclick="code_CodeSelect(this, 'policyItemKindCodeNoRisk','-1,0','Y','N',fm.policyno.value);" onchange="code_CodeSelect(this, 'policyItemKindCodeNoRisk','-1,0','Y','N',fm.policyno.value);"
									onkeyup="code_CodeSelect(this, 'policyItemKindCodeNoRisk','-1,0','Y','N',fm.policyno.value);">
								<input name="prpLclaimLossItemKindNo" type="hidden"> <%-- 标的名称 --%>
								<input name="prpLclaimLossSerialNo" type="hidden">
							</td>
							<td class="input" style="width: 10%" align="center">
								<!-- 
				<input name="prpLclaimLossItemCode" type="hidden">
				-->
								<input type="text" name="prpLclaimLossCurrency" value="${prpLclaim.estiCurrency}" class="readonly" readonly style="width: 30%" title="<s:text name="replevy.currency" />" onblur="collectClaimLoss(this);"><%-- 币别 --%>
								<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="<s:text name="replevy.currency" />" value="${prpLclaim.currencyName}" onblur="collectClaimLoss(this);"><%-- 币别 --%>
							</td>
							<td class="input" style="width: 8%">
								<select name="prpLclaimLossLossFeeType">
									<option value="P">
										<s:text name="db.prpGradeExt.sumPaid" />
									</option>
									<%-- 赔款 --%>
									<option value="Z">
										<s:text name="claim.cost" />
									</option>
									<%-- 费用 --%>
								</select>
								<input type="hidden" name="prpLclaimLossFlag" class="input" readonly="true" style="width: 60%" value="1">
								<input name="prpLclaimLossFeeCategory" type="hidden" value="">
							</td>
							<td class="input" style="width: 9%">
								<input name="prpLclaimLossSumClaim" class=common style="text-align: right" onblur=" collectClaimLoss(this);" onchange="return checkBeyondSumAmount(this)">
							</td>
							<td class="input" style="width: 8%">
								<input name="prpLclaimLossKindRest" class=common style="width: 50px">
							</td>
							<td class="input" style="width: 10%" style="display:none">
								<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10"
									value="<%=new com.sinosoft.sysframework.common.datatype.DateTime(com.sinosoft.sysframework.common.datatype.DateTime.current().toString(), com.sinosoft.sysframework.common.datatype.DateTime.YEAR_TO_DAY)%>">
								<!--
				 <input name="prpLclaimLossInputDate" class="readonly" readonly style="width:85px" maxlength="10" >
				   -->
							</td>
							<td class="input" style="width: 15%">
								<input name="prpLclaimLossRemarkFlag" style="width: 110px" class="input" maxlength="100">
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input name="buttonClaimLossDelete" type="button" class="smallbutton" onclick="deleteRow(this,'ClaimLoss','prpLclaimLossSerialNo');" value="-" style="cursor: hand">
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
							<%--危险单位序号  --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="undwrt.Risks" />
							</td>
							<%-- 险别 --%>
							<td class="centertitle" style="width: 14%">
								<s:text name="compensate.dubang.project" />
							</td>
							<%--项目  --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLlawsuit.currency" />
							</td>
							<%-- 币别 --%>
							<td class="centertitle" style="width: 8%">
								<s:text name="modifySumClaim.costType" />
							</td>
							<%-- 费用类别 --%>
							<td class="centertitle" style="width: 9%">
								<s:text name="claim.amountInsurLoss" />
							</td>
							<%-- 险别估损金额 --%>
							<td class="centertitle" style="width: 8%">
								<s:text name="claim.salvage" />
							</td>
							<%-- 残值 --%>
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
						<!-- 查看页面，按钮要灰掉（如果这里要修改，请注意对应的ClaimEdit需要去掉disabledAllButton(ClaimLoss_button)方法）-->
						<tr>
							<td colspan="10">
								<table id="ClaimLoss_button" style="width: 100%">
									<tr>
										<td class="title" colspan=8 style="width: 91%">
											<s:text name="prompt.certify.addRemove" />
										</td>
										<%-- (按"+"号键增加信息，按"-"号键删除信息) --%>
										<td class="title" align="middle" style="width: 9%">
											<div align="center">
												<input type="button" value="+" class="smallbutton" onclick="insertRow('ClaimLoss',this,'prpLclaimLossSerialNo')" name="buttonDriverInsert" style="cursor: hand">
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<s:if test="#request.prpDexch.baseCurrency != null && #request.prpDexch.baseCurrency != '' && #request.prpDexch.baseCurrency != 'CNY' ">
							<tr>
								<td colspan="10">
									<table>
										<tr>
											<td class="title" style="color: red">
												<s:text name="claim.signCurrencyCase" />
												:
											</td>
											<%-- 此案件签单币别为 --%>
											<td>
												<input type=text name="BaseCurrency2" class="readonly" readonly style="color: red" value="${prpDexch.baseCurrency}">
											</td>
										</tr>
										<tr>
											<td class="title" style="color: red">
												<s:text name="compensate.currentExchangeRate" />
												:
											</td>
											<%-- 当前兑换率为 --%>
											<td>
												<input type=text name="ExchRate2" class="readonly" readonly style="color: red" value="${prpDexch.strExchRate}">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</s:if>
					</tfoot>
					<tbody>
						<s:iterator id="claimLoss" value="claimLossList" status="status1">
							<tr>
								<s:if test="%{#status1.count % 2} == 0">
								out.print("<tr class=oddrow>");
								</s:if>
								<s:else>
								out.print("<tr class=oddrow>");
								</s:else>
								<s:if test="#request.editType !='ADD' && #request.editType !='EDIT'">
									<s:set var="inputType" value="readonly" scope="page" />
									<s:set var="inputDisable" value="disabled" scope="page" />
								</s:if>
								<td class="input" style="width: 5%">
									<input type=text name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLoss.dangerNo }" onClick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);"
										onchange="viewDangerUnit(this);">
								</td>
								<td class="input" style="width: 20%" style="align:center">
									<input type="text" name="prpLclaimLossKindCode" class="codecode" <s:property value="#inputType"/> style="width: 30%" title="<s:text name="db.prpCitemKind.kindCode" />"
										<s:if test="#request.editType =='SHOW' || #request.editType =='EDIT'">
										ondblclick= "code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value);"
										onkeyup= "code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value);"
										onchange= "code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value);"
										onchange="judgeKindCode(this);"
										</s:if>
										value="${prpLclaimLoss.kindCode}"> <%-- 险别代码 --%>
									<input type="text" name="prpLclaimLossKindName" class="codecode" <s:property value="#inputType"/> style="width: 65%" title="<s:text name="db.prpCname.kindName" />"
										<s:if test="#request.editType =='SHOW' || #request.editType =='EDIT'">
										ondblclick= "code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value);"
										onkeyup= "code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value);"
										onchange= "code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyno.value);"
										onchange="judgeKindCode(this);"
										</s:if>
										value="${prpLclaimLoss.kindName}"> <%-- 险别名称 --%>
								</td>
								<td class="input" style="width: 14%" style="align:center">
									<input type='text' name='prpLclaimLossItemCode' class="codecode" style="width: 25%" title="<s:text name="db.prpDlimit.itemCode" />" value="${prpLclaimLoss.itemCode}"
										ondblclick="code_CodeSelect(this,'policyItemKindCodeNoRisk','0,1','Y','Y',fm.policyno.value);" onkeyup="code_CodeSelect(this,'policyItemKindCodeNoRisk','0,1','Y','Y',fm.policyno.value);"
										onchange="code_CodeSelect(this,'policyItemKindCodeNoRisk','0,1','Y','Y',fm.policyno.value);"><%-- 标的代码 --%>
									<input type='text' name="prpLclaimLossItemDetailName" class="codename" style="width: 65%" title="<s:text name="regist.prpLregist.itemName" />" value="${prpLclaimLoss.itemDetailName}"
										ondblclick="code_CodeSelect(this, 'policyItemKindCodeNoRisk','-1,0','Y','N',fm.policyno.value);" onchange="code_CodeSelect(this, 'policyItemKindCodeNoRisk','-1,0','Y','N',fm.policyno.value);"
										onkeyup="code_CodeSelect(this, 'policyItemKindCodeNoRisk','-1,0','Y','N',fm.policyno.value);"><%-- 标的名称 --%>
									<input type="hidden" name="prpLclaimLossItemKindNo" value="${prpLclaimLoss.itemKindNo}">
									<input name="prpLclaimLossSerialNo" type="hidden" value="${prpLclaimLoss.id.serialNo}">
								</td>
								<td class="input" style="width: 10%" align="center">
									<input type=text name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="<s:text name="replevy.currency" />" value="${prpLclaimLoss.currency}" onblur="collectClaimLoss(this);"><%-- 币别 --%>
									<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="<s:text name="replevy.currency" />" value="${prpLclaimLoss.currencyName}" onblur="collectClaimLoss(this);"><%-- 币别 --%>
								</td>
								<td class="input" style="width: 8%">
									<select name="prpLclaimLossLossFeeType">
										<option value="P" <c:if test ="${prpLclaimLoss.lossFeeType=='P'}">selected</c:if>>
											<s:text name="db.prpGradeExt.sumPaid" />
										</option>
										<%-- 赔款 --%>
										<option value="Z" <c:if test ="${prpLclaimLoss.lossFeeType=='Z'}">selected</c:if>>
											<s:text name="claim.cost" />
										</option>
										<%--费用  --%>
									</select>
									<input type="hidden" name="prpLclaimLossFlag" class="input" readonly="true" style="width: 60%" value="1">
									<input name="prpLclaimLossFeeCategory" type="hidden" value="<fmt:formatNumber pattern='#' value='${prpLclaimLoss.feeCategory}'/>">
								</td>
								<td class="input" style="width: 9%">
									<input name="prpLclaimLossSumClaim" class=common <s:property value="#inputType"/> style="text-align: right" value="<fmt:formatNumber pattern='#' value='${prpLclaimLoss.sumClaim}'/>"
										onblur=" collectClaimLoss(this);" onchange="return checkBeyondSumAmount(this)">
								</td>
								<td class="input" style="width: 8%">
									<input name="prpLclaimLossKindRest" class=common style="width: 50px" value="${prpLclaimLoss.kindRest}">
								</td>
								<td class="input" style="width: 10%" style="display:none">
									<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10" value="${prpLclaimLoss.inputDate}">
								</td>
								<td class="input" style="width: 15%">
									<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100" style="width: 110px" value="${prpLclaimLoss.remarkFlag}">
								</td>
								<td class="input" style='width: 4%' align="center">
									<div>
										<input name="buttonClaimLossDelete" <s:property value="#inputDisable"/> type="button" class="smallbutton" onclick="deleteRow(this,'ClaimLoss','prpLclaimLossSerialNo');"
											<s:if test="#request.editType !='ADD' && #request.editType !='EDIT'">disabled</s:if> value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>