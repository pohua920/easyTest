<%--
****************************************************************************
* DESC       ：显示立案登记的险别估损金额页面
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
var damageKind = new Array();
<c:forEach var="damageKindList" items="${damageKindList}" varStatus="damageKindListIndex">
damageKind[${damageKindListIndex.index}]   ="${prpCitemKind.kindCode}";
</c:forEach>

function viewDangerUnit(field){
	for (var i=1;i<fm.prpLclaimLossSerialNo.length;i++){
		if(fm.prpLclaimLossDangerNo[i]==field){
			var count      = i;
			var policyNo   = fm.policyno.value;
			var damageDate = fm.prpLclaimDamageStartDate.value;
			field.value="";
			var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&openerIndex=" + count+"&PageType=ClaimLoss" ;  
			window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
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
		alert("您选择的险别不是出险日期时的险别,请重新进行选择");
		fm.prpLclaimLossKindCode[findex].value = "";
		return false;
	}
}
</script>
<script type="text/javascript">
$(function(){
	$(":input[name='prpLclaimLossKindName']").bind("mouseover",function(){
		$(this).prop("title",$(this).val());
	});
})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%" >
	<!--表示显示多行的-->
	<tr >
		<td colspan="4" style="text-align:left">
		<img style="cursor:hand;" src="/claim/images/butExpandBlue.gif"
		name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
		<s:text name="claim.amountInsurLossInfo" /><font color="#FF0000">*</font><br><%--险别估损金额信息--%>
			<span style="display:none">
				<table class="common" style="display:none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
				<tbody>
					<tr>
						<td class="input" style="width:5%" style="align:center">
							<input type=text name="prpLclaimLossDangerNo" class="codecode" value = "1"  onClick= "viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
							<input name="prpLclaimLossSerialNo" type="hidden" value="0">
						</td>
						<td class="input" style="width:24%" style="align:center">
							<input type="hidden" name="prpLclaimLossItemCode" class="codecode" style="width:30%" title="险别代码" value="">
							<input type="hidden" name="prpLclaimLossItemDetailName" class="codecode" style="width:65%" title="险别名称">
							<input type="text" name="prpLclaimLossKindCode" class="codecode" style="width:25%" title="标的代码"
							ondblclick= "code_CodeSelect(this,'PolicyKindCode','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"
							onkeyup= "code_CodeSelect(this,'PolicyKindCode','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"
							onchange= "code_CodeSelect(this,'PolicyKindCode','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"> 
							<input type="text" name="prpLclaimLossKindName" class="codename"  style="width:200px" title="标的名称"
							ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1,2','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"
							onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1,2','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"
							onkeyup= "code_CodeSelect(this, 'PolicyKindCode','-1,0,1,2','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);" >
							<input name="prpLclaimLossItemKindNo" type="hidden">
						</td>
						<td class="input" style="width: 7%" align="center"><%--保险金额 --%>
							<input type=text name="prpLclaimLossAmount" class="readonly" readonly style="" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
						</td>
						<td class="input" style="width:10%" align="center">
							<input type="text" name="prpLclaimLossCurrency" value="${prpLclaim.estiCurrency}" class="readonly" readonly style="width:30%" title="币别"
							onchange="calculateSumClaim(this);">
							<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width:60%" title="币别"  value="${strCurrencyName}"
							onchange="calculateSumClaim(this);">
						</td>
						<td class="input" style="width:9%" >
							<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" />
							<input type="hidden" name="prpLclaimLossFlag" class="input" readonly="true" style="width:60%" value="1">
							<input name="prpLclaimLossFeeCategory" type="hidden" value="">
						</td>
						<td class="input" style="width:10%">
							<input name="prpLclaimLossSumClaim" class=common style="text-align:right" onchange="calculateSumClaim(this);" >
						</td>
						<td class="input" style="width:8%">
							<input name="prpLclaimLossKindRest" class=common style="width:50px">
						</td>
						<td class="input" style="width:10%" style="display:none" >
							<input name="prpLclaimLossInputDate" class="readonly" readonly  maxlength="10"
							value="<%= new com.sinosoft.sysframework.common.datatype.DateTime(com.sinosoft.sysframework.common.datatype.DateTime.current().toString(), com.sinosoft.sysframework.common.datatype.DateTime.YEAR_TO_DAY) %>">
						</td>
						<td class="input" style="width:20%">
							<input name="prpLclaimLossRemarkFlag" class="input" style="width:150px" maxlength="100">
						</td>
						<td class="input" style='width:4%' colspan="1" align="center">
							<div>
								<input  name="buttonClaimLossDelete"  type="button" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimFee(this)" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
		<span  id="spanClaimLoss" style="display:" cellspacing="1" cellpadding="0">
		<%-- 多行输入展现域 --%>
			<table class="common" style="width:100%" id="ClaimLoss" >
				<thead>
				<tr>
					<td class="centertitle" style="width:5%"><s:text name="claim.dangeSerialNum" /></td><%--危险单位序号--%>
					<td class="centertitle" style="width:24%"><s:text name="compensate.dubang.project" /></td>   <%--项目--%>
					<td class="centertitle" style="width:7%">保險金額</td><%-- 保險金額 --%>
					<td class="centertitle" style="width:10%"><s:text name="db.prpLperson.currency" /></td><%--币别--%>
					<td class="centertitle" style="width:9%"><s:text name="modifySumClaim.costType" /></td> <%--费用类别--%>
					<td class="centertitle" style="width:10%"><s:text name="claim.amountInsurLoss" /></td>  <%--险别估损金额--%>
					<td class="centertitle" style="width:8%"><s:text name="claim.salvage" /></td><%--残值--%>
					<td class="centertitle" style="width:10%" style="display:none" ><s:text name="modifySumClaim.inputDate" /></td><%--输入日期--%>
					<td class="centertitle" style="width:20%"><s:text name="claim.adjustReason" /></td><%--调整原因--%>
					<td  class="centertitle"  style="width:4%">
						<input onclick="collectCurrency();" style="width:35px"  type="button" class="button" value="<s:text name='button.summary.value' />">  <%--汇总--%>
					</td>
				</tr>
			</thead>
			<tfoot>
			<!--查看页面，按钮要灰掉（如果这里要修改，请注意对应的ClaimEdit需要去掉disabledAllButton(ClaimLoss_button)方法）-->
				<tr>
					<td colspan="9">
						<table id="ClaimLoss_button" style="width:100%">
							<tr>
								<td class="title" colspan=8 style="width:96%"><s:text name="prompt.certify.addRemove" /></td><%--(按"+"号键增加信息，按"-"号键删除信息)--%>
								<td class="title" align="middle" style="width:4%" align="center">
									<div >
										<input type="button" value="+" class="smallbutton" onclick="insertRow('ClaimLoss')" name="buttonDriverInsert" style="cursor: hand">
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<s:if test="#request.prpDexch.baseCurrency != null && #request.prpDexch.baseCurrency != '' && #request.prpDexch.baseCurrency != #request.LOCAL_CURRENCY ">
					<tr>
						<td colspan="9">
							<table style="width:100%">
								<tr>
									<td class="title" style="color:red; width:20%;" ><s:text name="claim.signCurrencyCase" />:</td><%--此案件签单币别为--%>
									<td class="title" style="width:80%;" >
										<input type=text name="BaseCurrency2" class="readonly" readonly  style="color:red" value="${strBaseCurrency}">
									</td>
								</tr>
								<tr>
									<td class="title" style="color:red"><s:text name="claim.currentExchangeRate" />:</td><%--当前兑换率为--%>
									<td class="title">
										<input type=text name="ExchRate2" class="readonly" readonly  style="color:red" value="${strExchRate}">
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:if>
			</tfoot>
					<tbody>
						<s:iterator id="claimLoss" value="#request.prpLclaimLoss.claimLossList" status="status1">
							<s:if test="#request.editType !='ADD' && #request.editType !='EDIT'">
								<s:set var="inputType" value="readonly" scope="page" />
								<s:set var="inputDisable" value="disabled" scope="page" />
							</s:if>
							<tr>
								<td class="input" style="width: 5%">
									<input type=text name="prpLclaimLossDangerNo" class="codecode" value="${claimLoss.dangerNo}" onClick="viewDangerUnit(this);" onkeyup="viewDangerUnit(this);" onchange="viewDangerUnit(this);">
									<input name="prpLclaimLossSerialNo" type="hidden" value="${claimLoss.id.serialNo}">
								</td>
								<td class="input" style="width: 24%" style="align:center">
									<input type="hidden" name="prpLclaimLossItemCode" class="codecode" <c:out value="${displayType}"/> style="width: 20%" title="险别代码" value="${claimLoss.itemCode}">
									<input type="hidden" name="prpLclaimLossItemDetailName" class="codecode" <c:out value="${displayType}"/> style="width: 70%" title="险别名称" value="${claimLoss.itemDetailName}">
									<input type='input' name='prpLclaimLossKindCode' class="codecode" style="width: 25%" title="标的代码" value="${claimLoss.kindCode}" ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"
										onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);" onchange="code_CodeSelect(this,'PolicyKindCode','0,1,2,3','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);">
									<input type='input' name="prpLclaimLossKindName" class="codename" style="width: 200px" title="标的名称" value="${claimLoss.kindName}" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1,2','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);"
										onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1,2','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);" onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1,2','Y','N',fm.policyno.value+'|'+fms.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+fm.prpLclaimEndorseNo.value);">
									<input type="hidden" name="prpLclaimLossItemKindNo" value="${claimLoss.itemKindNo}">
								</td>
								<td class="input" style="width: 7%" align="center"><%--保险金额 --%>
									<input type=text name="prpLclaimLossAmount" class="readonly" readonly style="" title="<s:text name="db.prpLpersonloss.amount" />" value="<fmt:formatNumber value='${claimLoss.amount}' pattern='#'/>">
								</td>
								<td class="input" style="width: 10%" align="center">
									<input type=text name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="币别" value="${claimLoss.currency}" onchange="calculateSumClaim(this);">
									<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="币别" value="${claimLoss.currencyName}" onchange="calculateSumClaim(this);">
								</td>
								<td class="input" style="width: 9%">
									<c:set var="tempSelectedValue" value="${claimLoss.lossFeeType}" />
									<s:select name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" />
									<input type="hidden" name="prpLclaimLossFlag" class="input" readonly="true" style="width: 60%" value="1">
									<input name="prpLclaimLossFeeCategory" type="hidden" value="${claimLoss.feeCategory}">
								</td>
								<td class="input" style="width: 10%">
									<input name="prpLclaimLossSumClaim" class=common <c:out value="${displayType}"/> style="text-align: right" value="<fmt:formatNumber pattern='#' value='${claimLoss.sumClaim}'/>" onchange="calculateSumClaim(this);">
								</td>
								<td class="input" style="width: 8%">
									<input name="prpLclaimLossKindRest" class=common style="width: 50px" value="<fmt:formatNumber pattern='#' value='${claimLoss.kindRest}'/>">
								</td>
								<td class="input" style="width: 10%" style="display:none">
									<input name="prpLclaimLossInputDate" class="readonly" readonly maxlength="10" value="${claimLoss.inputDate}">
								</td>
								<td class="input" style="width: 20%">
									<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100" style="width: 150px" value="${claimLoss.remarkFlag}">
								</td>
								<td class="input" style='width: 4%' align="center">
									<div>
										<input name="buttonClaimLossDelete" <c:out value="${buttonType}"/> type="button" class="smallbutton" onclick="deleteRow(this,'ClaimLoss');collectClaimFee(this);" <c:out value="${buttonType}"/> value="-" style="cursor: hand">
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