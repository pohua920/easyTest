<%@ page import="com.sinosoft.claim.common.ConstantCodes" %>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
   <!--建立显示的录入条，可以收缩显示的-->

<script language='javascript'>
function viewDangerUnitCompensateLoss(field) {
	for (var i = 1; i < fm.prpLlossDtoSerialNo.length; i++) {
		if (fm.prpLlossDtoDangerNo[i] == field) {
			var count = i;
			var policyNo = fm.policyno.value;
			var damageDate = fm.damageStartDate.value;
			var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateLloss";
			window.open(submitStr, '查看危险单位信息', 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		}
	}
}

function justdoIt(field) {
	var findex;
	for (var i = 1; i < fm.all(field.name).length; i++) {
		if (fm.all(field.name)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fm.prpLlossDtoKindCode[findex].value == 'C5') {
		var url = "/claim/DAA/compensate/DAACompensateChild.jsp";
		var mxh1 = new Array("总住宿费用", "住宿天数");
		var handle = window.showModalDialog(url, mxh1, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:230px");
		var handletmp = parseFloat(handle[0]);
		for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
			if (fm.prpLlossDtoKindCodeShow[index].value == 'C5') {
				if (handle[0] > fm.kindAmount[index].value)
					handle[0] = parseFloat(fm.kindAmount[index].value);
				var unitFee = parseFloat(handle[0] / handle[1]);
				if (unitFee > 200) {
					unitFee = 200;
					handle[0] = unitFee * handle[1];
				}
			}
		}
		fm.prpLlossDtoSumLoss[findex].value = point(round(handletmp, 0), 0);
		fm.prpLlossDtoSumDefPay[findex].value = point(round(handle[0], 0), 0);
		fm.prpLlossDtoSumRealPay[findex].value = point(round(handle[0], 0), 0);
		initEvryTypeRealPay();
		calFund();
	} else if (fm.prpLlossDtoKindCode[findex].value == 'T') {
		var unitValue;
		var amount;
		var days;
		var url = "/claim/DAA/compensate/DAACompensateChild.jsp";
		var mxh1 = new Array("0", "修理天数");
		var handle = window.showModalDialog(url, mxh1, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:230px");
		for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
			if (fm.prpLlossDtoKindCodeShow[index].value == 'T') {
				unitValue = parseFloat(fm.unitAmount[index].value);
				amount = parseFloat(fm.kindAmount[index].value);
			}
		}
		if (parseInt(handle[0]) == 1) {
			fm.prpLlossDtoIsLossAll.value = 'Y';
			fm.prpLlossDtoSumLoss[findex].value = point(round(amount, 0), 0);
			fm.prpLlossDtoSumDefPay[findex].value = point(round(amount, 0), 0);
			fm.prpLlossDtoSumRealPay[findex].value = point(round(amount, 0), 0);
		} else {
			fm.prpLlossDtoIsLossAll.value = 'N';
			if (unitValue > 300)
				unitValue = 300;
			days = parseFloat(handle[1]);
			fm.days.value = parseFloat(handle[1]);
			if (days > 60)
				days = 60;
			fm.prpLlossDtoSumLoss[findex].value = point(round(unitValue * days, 0), 0);
			fm.prpLlossDtoSumDefPay[findex].value = point(round(unitValue * days, 0), 0);
			fm.prpLlossDtoSumRealPay[findex].value = point(round(unitValue * days, 0), 0);
		}
		initEvryTypeRealPay();
		calFund();
	}
}
</script>
<%
	ArrayList prpCitemKindDtoListInit = (ArrayList) request.getAttribute("prpCitemKindDtoListInit");
	double itemValue = 0;
	double purchasePrice = 0;
	double finalValue = 0;
	double factValue = 0;
	if (request.getAttribute("purchasePrice") != null && request.getAttribute("finalValue") != null && request.getAttribute("factValue") != null) {
		purchasePrice = ((Double) request.getAttribute("purchasePrice")).doubleValue();
		finalValue = ((Double) request.getAttribute("finalValue")).doubleValue();
		//System.out.println("finalValue="+finalValue);
		factValue = ((Double) request.getAttribute("factValue")).doubleValue();
	}
	if (prpCitemKindDtoListInit != null) {
%>
<input type="hidden" name="size" value="<%=prpCitemKindDtoListInit.size()%>">
<%
	for (int i = 0; i < prpCitemKindDtoListInit.size(); i++) {
			PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) prpCitemKindDtoListInit.get(i);
			if (ConstantCodes.KINDCODE_D_A.equals(prpCitemKindDto.getKindCode()))
				itemValue = prpCitemKindDto.getAmount();
%>
<input type="hidden" name=prpLlossDtoKindCodeShow style="width: 25px" value="<%=prpCitemKindDto.getKindCode()%>">
<input type="hidden" name=prpLlossDtoKindNameShow style="width: 55px" value="<%=prpCitemKindDto.getKindName()%>">
<input type="hidden" name=DutyDeductibleRate value="<%=prpCitemKindDto.getDutyDeductibleRate()%>">
<input type="hidden" name=DeductibleRate value="<%=prpCitemKindDto.getDeductibleRate()%>">
<input type="hidden" name="kindAmount" value="<%=prpCitemKindDto.getAmount()%>">
<input type="hidden" name="unitAmount" value="<%=prpCitemKindDto.getUnitAmount()%>">
<input type="hidden" name="value" value="<%=prpCitemKindDto.getValue()%>">
<%
	if (prpCitemKindDto.getFlag().length() >= 4) {
%>
<input type="hidden" name="flag" value="<%=prpCitemKindDto.getFlag().substring(4, 5)%>">
<%
	} else {
%>
<input type="hidden" name="flag" value="0">
<%
	}
%>
<input type="hidden" name="prpLsumDefPayAllShow" value="0">
<input type="hidden" name="prpLsumDefPayAllShowMiddle" value="0">
<input type="hidden" name="prpLsumRealPayAllShow" value="0">
<input type="hidden" name="prpLchargeSumRealPayAllShow" value="0">
<input type="hidden" name="prpLchargeAmountShow" value="0">
<%
	}
	}
%>


<%
	Map limitMap = new HashMap();
	limitMap = (Map) request.getAttribute("limitMap");
	if (limitMap.size() > 0) {
		String limitType = "";
		String limitFee = "";
		Set limitMapKey = limitMap.keySet();
		if (limitMapKey != null && limitMapKey.size() > 0) {
			for (Iterator limit = limitMapKey.iterator(); limit.hasNext();) {
				limitType = (String) limit.next();
				limitFee = (String) limitMap.get(limitType);
%>    	    
     <input type="hidden" name="limitType" value="<%=limitType%>">   	 
     <input type="hidden" name="limitFee" value="<%=limitFee%>">   	 
 <%
   	  	}
   	  		}
   	  	}
   	  %>
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<input type="hidden" name="prpLcompensateSumLoss" value="<bean:write name='prpLcompensateDto' property='sumLoss' />">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="lLossImg" onclick="showPage(this,CompensateLoss);changeCompensateFlag('1');">
			<s:text name="compensate.compCheSunLossInfo" />
			<!-- 赔付车损/物损信息 -->
			<input type="button" class="button" name="button_Loss_Collect" value="<s:text name="claim.total"/>汇总" onclick="return showLossCollect();">
			&nbsp&nbsp&nbsp
			<s:text name="compensate.carPurchaseValue" />
			:
			<!-- 标的车新车购置价 -->
			<input type="text" name="purchasePrice" class="common" style="width: 80px" value="<%=purchasePrice%>" onChange="calRealValuen();relateChange2(this);">
			<input type="hidden" name="finalValue" value="<%=finalValue%>">
			&nbsp&nbsp&nbsp
			<s:text name="quickCase.markCarRealValue" />
			<!-- 标的车实际价值： -->
			<input name="factValue" class="readonly" style="width: 80px" readonly value="<%=factValue%>">
			<br> <span style="display: none">
				<table class="common" style="display: none" id="CompensateLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<%--         	 	<td class="input" style="width:6%">--%>
							<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="1" onClick="viewDangerUnitCompensateLoss(this);">
							<%--                </td>--%>
							<td class="input" style="width: 15%" style="align:center">
								<input type="hidden" name="lossDtoSerialNo" style="width: 10px">
								<input type="hidden" name="prpLlossDtoSerialNo" style="width: 10px">
								<input type=text name="prpLlossDtoKindCode" class="codecode" style="width: 20%" title="險別"
									ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onChange="code_CodeChange(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onblur="inputControl(this);checkExcept4();insertRow2(this);inputControl2(this);calRealpay(this);clearPrpLctext();">
								<input type=text name="prpLlossDtoKindName" class="codecode" style="width: 70%" title="險別"
									ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onChange="code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value +'|'+fm.prpLRegistRPolicyNo.value);"
									onblur="inputControl(this);checkExcept4();insertRow2(this);inputControl2(this);calRealpay(this);clearPrpLctext();">
							</td>
							<input name="prpLlossDtoItemKindNo" type="hidden">
							<td class="inputsubsub" style="width: 10%">
								<input name="licenseNo" class="input" style="width: 98%" onchange="inputControl(this);checkBeyondQuotaLienceNo(this);">
							</td>
							<td class="inputsubsub" style="width: 10%">
								<input name="prpLlossDtoLossName" maxlength=40 class="common" style="width: 98%">
							</td>
							<%--               <td class="inputsubsub" style="width:7%">--%>
							<input type="hidden" name="prpLlossDtoFeeTypeCode" class='codecode' style="width: 30%" value="01" ondblclick="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
								onchange="code_CodeChange(this, 'PropertyFeeType','0,1','Y');" onkeyup="code_CodeSelect(this,'PropertyFeeType','0,1','Y');" onblur="code_CodeChange(this,'PropertyFeeType',1);getValue(this);">
							<input type="hidden" name="prpLlossDtoFeeTypeName" class='codename' style="width: 98%" value="<s:text name="claim.total"/>修理费"
								ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');" onchange="code_CodeChange(this, 'PropertyFeeType','-1,0','Y','N');"
								onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');" onblur="getValue(this);">
							<%--              </td>--%>
							<td class="inputsubsub" style="width: 10%" align="right">
								<input name="prpLlossDtoSumLoss" class="common" style="width: 98%" value='0' onchange="calLoss();" ondblclick="justdoIt(this);">
							</td>
							<td class="inputsubsub" style="width: 10%" align="right">
								<input name="prpLlossDtoSumDefPay" class="common" style="width: 98%" onBlur="checkBeyondQuota(this);calRealpay(this);"
									onchange="inputControl2(this);checkBeyondQuota(this);calRealpay(this);clearPrpLctext();">
							</td>
							<td class="inputsubsub" style="width: 7%">
								<%--                <input name="prpLlossDtoSumRest" class="common" style="width:98%"  onfocus="checkInputPower(this);" onchange="calRealpay(this);">--%>
								<input name="prpLlossDtoSumRest" class="common" style="width: 98%" value="0" onchange="calRealpay(this);clearPrpLctext();">
							</td>
							<td class="inputsubsub" style="width: 8%" align="right">
								<input name="prpLlossDtoCompelPay" class="common" style="width: 98%" value="0" onchange="calRealpay(this);clearPrpLctext();">
							</td>
							<%--              <td class="inputsubsub" style="width:7%">--%>
							<input name="prpLlossDtoClaimRate" type="hidden" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();">
							<%--              </td>--%>
							<%--               <td class="inputsubsub" style="width:7%">--%>
							<input name="prpLlossDtoIndemnityDutyRate" type="hidden" class="common" style="width: 98%" onchange="getIndemnityDutyRate(this);">
							<%--              </td>--%>
							<%--              <td class="inputsubsub" style="width:7%">--%>
							<input name="prpLlossDtoArrangeRate" class="common" type="hidden" style="width: 98%" onchange="getArrangeRate(this);">
							<%--              </td>--%>
							<td class="inputsubsub" style="width: 7%">
								<input name="prpLlossDtoDeductible" type="hidden">
								<!-- 免赔率选择-20060418--start--------------------->
								<input name="prpLlossDtoDutyDeductibleRate" class='common' style="width: 98%" onchange="calRealpay(this);clearPrpLctext(); " onblur="return checkLossDeductibleRate(this);">
								<!-- 免赔率选择-20060418--end--------------------->
							</td>
							<td class="inputsubsub" style="width: 7%">
								<input name="prpLlossDtoDeductibleRate" class='common' style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" onblur="return checkLossDeductibleRate(this);">
								<input type="hidden" name="PrpLlossDtoMainKindDuctibleRate">
								<input type="hidden" name="prpLlossDtoDriverDeductibleRate">
							</td>
							<td class="inputsubsub" style="width: 7%" align="right">
								<input name="prpLlossDtoSumRealPay" class='readonly' style="width: 98%" readonly value="0">
								<input name="compensateAdd" type="hidden">
								<input name="prpLlossDtoExceptDeductiblePay" value="0" type="hidden">
								<input name="prpLlossDtoExceptDeductibleRate" value="0" type="hidden">
								<input name="prpLlossDtoFlag" type="hidden">
								<input type="hidden" name="prpLlossDtoFamilyNo">
								<input type="hidden" name="prpLlossDtoItemKindNo">
								<input type='hidden' name='prpLlossDtoFamilyName'>
								<input type='hidden' name='prpLlossDtoItemCode'>
								<input type='hidden' name='prpLlossDtoItemAddress'>
								<input type="hidden" name="prpLlossDtoCurrency2" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								<input type="hidden" name="prpLlossDtoCurrency2Name2" value="<s:text name="claim.total"/><%=ConstantCodes.LOCAL_CURRENCYNAME%>">
								<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
								<input type='hidden' name='prpLlossDtoDepreRate'>
								<input type='hidden' name='prpLlossDtoCurrency' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								<input type='hidden' name='prpLlossDtoCurrency1' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								<input type='hidden' name='prpLlossDtoCurrency3' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								<input type='hidden' name='prpLlossDtoCurrency4' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								<input type='hidden' name='prpLlossDtoUnit'>
								<input type="hidden" name="prpLlossDtoAmount">
								<input type="hidden" name="prpLlossDtoItemValue" value="<%=itemValue%>">
								<input type="hidden" name="prpLlossDtoUnitPrice" value="0">
								<input type="hidden" name="prpLlossDtoLossQuantity">
								<input type="hidden" name="button_Loss_Refresh">
								<%--                <input type="hidden" name="prpLlossDtoIsLossAll">--%>
							</td>
							<td class="inputsubsub" style='width: 3%'>
								<div align="center">
									<input type=button name="buttonCompensateLossDelete" class="smallbutton" onclick="deleteRow2(this,'CompensateLoss');deleteRow(this,'CompensateLoss');initExceptDeductible();calFund();"
										value="-" readonly style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanlLoss" style="display:">
				<table id="CompensateLoss" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<%--         		 <td class="centertitle" style="width:6%">危险单位号</td>--%>
							<td class="centertitle" style="width: 15%" align="center">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
							</td>
							<!-- 险别 -->
							<td class="centertitle" style="width: 10%" align="center">
								<s:text name="compensate.plate" />
							</td>
							<!-- 号牌 -->
							<td class="centertitle" style="width: 10%" align="center">
								<s:text name="compensate.propertyName" />
							</td>
							<!-- 财物名称 -->
							<%--                 <td class="centertitle" style="width:7%" align="center">损失明细</td>--%>
							<td class="centertitle" style="width: 10%" align="center">
								<s:text name="compensate.approvedLoss" />
							</td>
							<!-- 核定损失 -->
							<td class="centertitle" style="width: 10%" align="center">
								<s:text name="compensate.approvedCompen" />
							</td>
							<!-- 核定赔偿 -->
							<td class="centertitle" style="width: 7%" align="center">
								<s:text name="compensate.excludAmountSalvage" />
							</td>
							<!-- 剔除金额/残值 -->
							<td class="centertitle" style="width: 8%" align="center">
								<s:text name="compensate.insuranceInde" />
							</td>
							<!-- 交强险赔款 -->
							<%--                 <td class="centertitle" style="width:7%" align="center">承保比例%</td>--%>
							<%--                 <td class="centertitle" style="width:7%" align="center">责任比例%</td>--%>
							<%--                 <td class="centertitle" style="width:7%" align="center">协商比例%</td>--%>
							<td class="centertitle" style="width: 7%" align="center">
								<s:text name="compensate.responsibilityFran" />
								%
							</td>
							<!-- 责任免赔率 -->
							<td class="centertitle" style="width: 7%" align="center">
								<s:text name="compensate.absoluteFranchise" />
								%
							</td>
							<!-- 绝对免赔率之和% -->
							<td class="centertitle" style="width: 7%" align="center">
								<s:text name="claim.compenPay" />
							</td>
							<!-- 赔偿金额 -->
							<td class="centertitle" style="width: 3%" align="center">
								<s:text name="certify.operate" />
							</td>
							<!-- 操作 -->
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=13>
								<s:text name="prompt.compensate.addRemove01" />
							</td>
							<!-- (按"+"号键增加赔付标的信息，按"-"号键删除信息) -->
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertRow('CompensateLoss');getObjectMessage();" name="buttonInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<logic:present name="prpLlossDto">
							<logic:notEmpty name="prpLlossDto" property="prpLlossList">
								<logic:iterate id="prplLoss" name="prpLlossDto" property="prpLlossList">
									<tr>
										<%--          	 	 <td class="input" style="width:6%">--%>
										<logic:notEqual name="prplLoss" property="dangerNo" value="0">
											<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="<bean:write name='prplLoss' property='dangerNo'/>" onClick="viewDangerUnitCompensateLoss(this);">
											<%--                 </td>--%>
										</logic:notEqual>
										<logic:equal name="prplLoss" property="dangerNo" value="0">
											<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="1" onClick="viewDangerUnitCompensateLoss(this);">
											<%--                 </td>--%>
										</logic:equal>
										<td class="input" style="width: 15%" style="align:center">
											<input type="hidden" name="lossDtoSerialNo" style="width: 10px">
											<input type="hidden" name="prpLlossDtoSerialNo" style="width: 10px">
											<input type=text name="prpLlossDtoKindCode" class="codecode" style="width: 20%" title="<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType"/>"
												ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onChange="code_CodeChange(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onblur="inputControl(this);calRealpay(this);clearPrpLctext();checkExcept4();insertRow2(this);" value="<bean:write name='prplLoss' property='kindCode' />">
											<!-- 险别 -->
											<input type=text name="prpLlossDtoKindName" class="codecode" style="width: 70%" title="<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType"/>"
												ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onChange="code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onblur="inputControl(this);calRealpay(this);clearPrpLctext();checkExcept4();insertRow2(this);" value="<bean:write name='prplLoss' property='kindName' />">
											<!-- 险别 -->
										</td>
										<input name="prpLlossDtoItemKindNo" type="hidden" value="<bean:write name='prplLoss' property="itemKindNo" />">
										<td class="inputsubsub" style="width: 10%">
											<input name="licenseNo" class="input" style="width: 98%" value="<bean:write name='prplLoss' property='licenseNo' />" onchange="inputControl(this);checkBeyondQuotaLienceNo(this);">
										</td>
										<td class="inputsubsub" style="width: 10%">
											<input name="prpLlossDtoLossName" maxlength=40 class="common" style="width: 98%" value="<bean:write name='prplLoss' property='lossName' />">
										</td>
										<%--                <td class="inputsubsub" style="width:7%">--%>
										<input type="hidden" name="prpLlossDtoFeeTypeCode" class='codecode' style="width: 30%" ondblclick="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
											onchange="code_CodeChange(this, 'PropertyFeeType','0,1','Y');" onkeyup="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
											onblur="code_CodeChange(this,'PropertyFeeType',1);getValue(this);" value="<bean:write name='prplLoss' property='feeTypeCode' />">
										<input type="hidden" name="prpLlossDtoFeeTypeName" class='codename' style="width: 98%" ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');"
											onchange="code_CodeChange(this, 'PropertyFeeType','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');" onblur="getValue(this);"
											value="<bean:write name='prplLoss' property='feeTypeName' />">
										<%--              </td>--%>
										<td class="inputsubsub" style="width: 10%" align="right">
											<input name="prpLlossDtoSumLoss" class="common" style="width: 98%" onchange="calLoss();" ondblclick="justdoIt(this);" value="<bean:write name='prplLoss' property='sumLoss' />">
										</td>
										<td class="inputsubsub" style="width: 10%" align="right">
											<input name="prpLlossDtoSumDefPay" class="common" style="width: 98%" onchange="inputControl2(this);calRealpay(this);clearPrpLctext();" onBlur="checkBeyondQuota(this);calRealpay(this);"
												value="<bean:write name='prplLoss' property='sumDefPay' />">
										</td>
										<td class="inputsubsub" style="width: 7%">
											<input name="prpLlossDtoSumRest" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" value="<bean:write name='prplLoss' property='sumRest'/>">
										</td>
										<td class="inputsubsub" style="width: 8%" align="right">
											<input name="prpLlossDtoCompelPay" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" value="<bean:write name='prplLoss' property='compelPay'/>">
										</td>
										<%--              <td class="inputsubsub" style="width:7%">--%>
										<input name="prpLlossDtoClaimRate" type="hidden" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" value="<bean:write name='prplLoss' property='claimRate' />">
										<%--              </td>--%>
										<%--              <td class="inputsubsub" style="width:7%">--%>
										<input name="prpLlossDtoIndemnityDutyRate" type="hidden" class="common" style="width: 98%" onchange="getIndemnityDutyRate(this);"
											value="<bean:write name='prplLoss' property='indemnityDutyRate'/>">
										<%--              </td>--%>
										<%--                <td class="inputsubsub" style="width:7%">--%>
										<input name="prpLlossDtoArrangeRate" type="hidden" class="common" style="width: 98%" onchange="getArrangeRate(this);" value="<bean:write name='prplLoss' property='arrangeRate' />">
										<%--              </td>--%>
										<td class="inputsubsub" style="width: 7%">
											<input name="prpLlossDtoDeductible" type="hidden" value="<bean:write name='prplLoss' property='deductible'/>">
											<!-- 免赔率选择-20060418--start--------------------->
											<input name="prpLlossDtoDutyDeductibleRate" class='common' style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" onblur="return checkLossDeductibleRate(this);"
												value="<bean:write name='prplLoss' property='dutyDeductibleRate' />">
											<!-- 免赔率选择-20060418--end--------------------->
										</td>
										<td class="inputsubsub" style="width: 7%">
											<input name="prpLlossDtoDeductibleRate" class='common' style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" onblur="return checkLossDeductibleRate(this);"
												value="<bean:write name='prplLoss' property='deductibleRate' />">
											<input type="hidden" name="PrpLlossDtoMainKindDuctibleRate">
											<input type="hidden" name="prpLlossDtoDriverDeductibleRate">
										</td>
										<td class="inputsubsub" style="width: 7%" align="right">
											<input name="prpLlossDtoSumRealPay" class='readonly' style="width: 98%" readonly onchange="calFund();" value="<bean:write name='prplLoss' property='sumRealPay' />">
											<input name="prpLlossDtoExceptDeductiblePay" type="hidden" value="<bean:write name='prplLoss' property='exceptDeductiblePay' />">
											<input name="prpLlossDtoExceptDeductibleRate" type="hidden" value="<bean:write name='prplLoss' property='exceptDeductibleRate' />">
											<input name="compensateAdd" type="hidden" value="N">
											<input name="prpLlossDtoFlag" type="hidden" value="<bean:write name='prplLoss' property='flag' />">
											<input type="hidden" name="prpLlossDtoFamilyNo" value="<bean:write name='prplLoss' property='familyNo' />">
											<input type="hidden" name="prpLlossDtoItemKindNo" value="<bean:write name='prplLoss' property='itemKindNo' />">
											<input type='hidden' name='prpLlossDtoFamilyName' value="<bean:write name='prplLoss' property='familyName' />">
											<input type='hidden' name='prpLlossDtoItemCode' value="<bean:write name='prplLoss' property='itemCode' />">
											<input type='hidden' name='prpLlossDtoItemAddress' value="<bean:write name='prplLoss' property='itemAddress' />">
											<input type="hidden" name="prpLlossDtoCurrency2" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type="hidden" name="prpLlossDtoCurrency2Name2" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
											<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
											<input type='hidden' name='prpLlossDtoDepreRate'>
											<input type='hidden' name='prpLlossDtoCurrency' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoCurrency1' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoCurrency3' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoCurrency4' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoUnit' value="<bean:write name='prplLoss' property='unit' />">
											<input type="hidden" name="prpLlossDtoAmount" value="<bean:write name='prplLoss' property='amount' />">
											<input type="hidden" name="prpLlossDtoItemValue" value="<bean:write name='prplLoss' property='itemValue' />">
											<input type="hidden" name="prpLlossDtoUnitPrice" value="<bean:write name='prplLoss' property='unitPrice' />">
											<input type="hidden" name="prpLlossDtoLossQuantity" value="<bean:write name='prplLoss' property='lossQuantity' />">
											<input type="hidden" name="button_Loss_Refresh">
											<%--                <input type="hidden" name="prpLlossDtoIsLossAll" value="<bean:write name='prplLoss' property='isLossAll' />">--%>
											<!-- 不计免赔率之和 -->
											<input type="hidden" name="prpLlossExceptDeductibleRate" value="<bean:write name='prplLoss' property='exceptDeductibleRate' />">
											<!-- 不计免赔率金额 -->
											<input type="hidden" name="prpLlossExceptDeductiblePay" value="<bean:write name='prplLoss' property='exceptDeductiblePay' />">
											<input type="hidden" name="prpLlossFlag" value="<bean:write name='prplLoss' property='flag' />">
										</td>
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonCompensateLossDelete" class=smallbutton onclick="deleteRow2(this,'CompensateLoss');deleteRow(this,'CompensateLoss'),initExceptDeductible();calFund();"
													value="-" style="cursor: hand">
											</div>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						<logic:present name="compelPrpLlossDto">
							<logic:notEmpty name="compelPrpLlossDto" property="prpLlossList">
								<logic:iterate id="compelPrplLoss" name="compelPrpLlossDto" property="prpLlossList">
									<tr>
										<%--          	 	 <td class="input" style="width:6%">--%>
										<logic:notEqual name="compelPrplLoss" property="dangerNo" value="0">
											<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="<bean:write name='compelPrplLoss' property='dangerNo'/>" onClick="viewDangerUnitCompensateLoss(this);">
											<%--                 </td>--%>
										</logic:notEqual>
										<logic:equal name="compelPrplLoss" property="dangerNo" value="0">
											<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="1" onClick="viewDangerUnitCompensateLoss(this);">
											<%--                 </td>--%>
										</logic:equal>
										<td class="input" style="width: 15%" style="align:center">
											<input type="hidden" name="lossDtoSerialNo" style="width: 10px">
											<input type="hidden" name="prpLlossDtoSerialNo" style="width: 10px">
											<input type=text name="prpLlossDtoKindCode" class="codecode" style="width: 20%" title="<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType"/>"
												ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onChange="code_CodeChange(this,'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onblur="inputControl(this);calRealpay(this);clearPrpLctext();checkExcept4();insertRow2(this);" value="<bean:write name='compelPrplLoss' property='kindCode' />">
											<!-- 险别 -->
											<input type=text name="prpLlossDtoKindName" class="codecode" style="width: 70%" title="<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType"/>"
												ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onChange="code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onblur="inputControl(this);calRealpay(this);clearPrpLctext();checkExcept4();insertRow2(this);" value="<bean:write name='compelPrplLoss' property='kindName' />">
											<!-- 险别 -->
										</td>
										<input name="prpLlossDtoItemKindNo" type="hidden" value="<bean:write name='compelPrplLoss' property="itemKindNo" />">
										<td class="inputsubsub" style="width: 10%">
											<input name="licenseNo" class="input" style="width: 98%" value="<bean:write name='compelPrplLoss' property='licenseNo' />" onchange="inputControl(this);checkBeyondQuotaLienceNo(this);">
										</td>
										<td class="inputsubsub" style="width: 10%">
											<input name="prpLlossDtoLossName" maxlength=40 class="common" style="width: 98%" value="<bean:write name='compelPrplLoss' property='lossName' />">
										</td>
										<%--                <td class="inputsubsub" style="width:7%">--%>
										<input type="hidden" name="prpLlossDtoFeeTypeCode" class='codecode' style="width: 30%" ondblclick="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
											onchange="code_CodeChange(this, 'PropertyFeeType','0,1','Y');" onkeyup="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
											onblur="code_CodeChange(this,'PropertyFeeType',1);getValue(this);" value="<bean:write name='compelPrplLoss' property='feeTypeCode' />">
										<input type="hidden" name="prpLlossDtoFeeTypeName" class='codename' style="width: 98%" ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');"
											onchange="code_CodeChange(this, 'PropertyFeeType','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');" onblur="getValue(this);"
											value="<bean:write name='compelPrplLoss' property='feeTypeName' />">
										<%--              </td>--%>
										<td class="inputsubsub" style="width: 10%" align="right">
											<input name="prpLlossDtoSumLoss" class="common" style="width: 98%" onchange="calLoss();" ondblclick="justdoIt(this);" value="<bean:write name='compelPrplLoss' property='sumLoss' />">
										</td>
										<td class="inputsubsub" style="width: 10%" align="right">
											<input name="prpLlossDtoSumDefPay" class="common" style="width: 98%" onchange="inputControl2(this);checkBeyondQuota(this);calRealpay(this);clearPrpLctext();"
												onBlur="checkBeyondQuota(this);calRealpay(this);" value="<bean:write name='compelPrplLoss' property='sumDefPay' />">
										</td>
										<td class="inputsubsub" style="width: 7%">
											<input name="prpLlossDtoSumRest" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" value="<bean:write name='compelPrplLoss' property='sumRest'/>">
										</td>
										<td class="inputsubsub" style="width: 8%" align="right">
											<input name="prpLlossDtoCompelPay" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" value="<bean:write name='compelPrplLoss' property='compelPay'/>">
										</td>
										<%--              <td class="inputsubsub" style="width:7%">--%>
										<input name="prpLlossDtoClaimRate" type="hidden" class="common" style="width: 98%" onchange="calRealpay(this);clearPrpLctext();"
											value="<bean:write name='compelPrplLoss' property='claimRate' />">
										<%--              </td>--%>
										<%--              <td class="inputsubsub" style="width:7%">--%>
										<input name="prpLlossDtoIndemnityDutyRate" type="hidden" class="common" style="width: 98%" onchange="getIndemnityDutyRate(this);"
											value="<bean:write name='compelPrplLoss' property='indemnityDutyRate'/>">
										<%--              </td>--%>
										<%--                <td class="inputsubsub" style="width:7%">--%>
										<input name="prpLlossDtoArrangeRate" type="hidden" class="common" style="width: 98%" onchange="getArrangeRate(this);" value="<bean:write name='compelPrplLoss' property='arrangeRate' />">
										<%--              </td>--%>
										<td class="inputsubsub" style="width: 7%">
											<input name="prpLlossDtoDeductible" type="hidden" value="<bean:write name='compelPrplLoss' property='deductible'/>">
											<!-- 免赔率选择-20060418--start--------------------->
											<input name="prpLlossDtoDutyDeductibleRate" class='common' style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" onblur="return checkLossDeductibleRate(this);"
												value="<bean:write name='compelPrplLoss' property='dutyDeductibleRate' />">
											<!-- 免赔率选择-20060418--end--------------------->
										</td>
										<td class="inputsubsub" style="width: 7%">
											<input name="prpLlossDtoDeductibleRate" class='common' style="width: 98%" onchange="calRealpay(this);clearPrpLctext();" onblur="return checkLossDeductibleRate(this);"
												value="<bean:write name='compelPrplLoss' property='deductibleRate' />">
											<input type="hidden" name="PrpLlossDtoMainKindDuctibleRate">
											<input type="hidden" name="prpLlossDtoDriverDeductibleRate">
										</td>
										<td class="inputsubsub" style="width: 7%" align="right">
											<input name="prpLlossDtoSumRealPay" class='readonly' style="width: 98%" readonly onchange="calFund();" value="<bean:write name='compelPrplLoss' property='sumRealPay' />">
											<input name="prpLlossDtoExceptDeductiblePay" type="hidden" value="<bean:write name='compelPrplLoss' property='exceptDeductiblePay' />">
											<input name="prpLlossDtoExceptDeductibleRate" type="hidden" value="<bean:write name='compelPrplLoss' property='exceptDeductibleRate' />">
											<input name="compensateAdd" type="hidden" value="N">
											<input name="prpLlossDtoFlag" type="hidden" value="<bean:write name='compelPrplLoss' property='flag' />">
											<input type="hidden" name="prpLlossDtoFamilyNo" value="<bean:write name='compelPrplLoss' property='familyNo' />">
											<input type="hidden" name="prpLlossDtoItemKindNo" value="<bean:write name='compelPrplLoss' property='itemKindNo' />">
											<input type='hidden' name='prpLlossDtoFamilyName' value="<bean:write name='compelPrplLoss' property='familyName' />">
											<input type='hidden' name='prpLlossDtoItemCode' value="<bean:write name='compelPrplLoss' property='itemCode' />">
											<input type='hidden' name='prpLlossDtoItemAddress' value="<bean:write name='compelPrplLoss' property='itemAddress' />">
											<input type="hidden" name="prpLlossDtoCurrency2" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type="hidden" name="prpLlossDtoCurrency2Name2" value="<s:text name="claim.total"/><%=ConstantCodes.LOCAL_CURRENCYNAME%>">
											<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
											<input type='hidden' name='prpLlossDtoDepreRate'>
											<input type='hidden' name='prpLlossDtoCurrency' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoCurrency1' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoCurrency3' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoCurrency4' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
											<input type='hidden' name='prpLlossDtoUnit' value="<bean:write name='compelPrplLoss' property='unit' />">
											<input type="hidden" name="prpLlossDtoAmount" value="<bean:write name='compelPrplLoss' property='amount' />">
											<input type="hidden" name="prpLlossDtoItemValue" value="<bean:write name='compelPrplLoss' property='itemValue' />">
											<input type="hidden" name="prpLlossDtoUnitPrice" value="<bean:write name='compelPrplLoss' property='unitPrice' />">
											<input type="hidden" name="prpLlossDtoLossQuantity" value="<bean:write name='compelPrplLoss' property='lossQuantity' />">
											<input type="hidden" name="button_Loss_Refresh">
											<%--                <input type="hidden" name="prpLlossDtoIsLossAll" value="<bean:write name='prplLoss' property='isLossAll' />">--%>
											<!-- 不计免赔率之和 -->
											<input type="hidden" name="prpLlossExceptDeductibleRate" value="<bean:write name='compelPrplLoss' property='exceptDeductibleRate' />">
											<!-- 不计免赔率金额 -->
											<input type="hidden" name="prpLlossExceptDeductiblePay" value="<bean:write name='compelPrplLoss' property='exceptDeductiblePay' />">
											<input type="hidden" name="prpLlossFlag" value="<bean:write name='compelPrplLoss' property='flag' />">
										</td>
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonCompensateLossDelete" class=smallbutton onclick="deleteRow2(this,'CompensateLoss');deleteRow(this,'CompensateLoss'),initExceptDeductible();calFund();"
													value="-" style="cursor: hand">
											</div>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
	<logic:present name="prpLdeductibleDto">
		<tr>
			<td class="title" style="width: 12%">
				<b><s:text name="compensate.ptionalFranchise" />:</b>
			</td>
			<!-- 可选免赔额信息 -->
			<td class="title" style="width: 25%">
				<s:text name="compensate.optionalLossDanger" />
				<!-- 车辆损失险(A)可选免赔额 -->
				<input class="readonly" style="width: 20%" name="prpLDeductible" value="<bean:write name='prpLdeductibleDto' property='deductible' />">
			</td>
			<td class="title" style="width: 63%">
				<!-- 车损险最终赔款:-->
				<input type="hidden" style="width: 60%" class="readonly" name='lastRealPay' value="">
			</td>
		</tr>
	</logic:present>
	<logic:notPresent name="prpLdeductibleDto">
		<input type="hidden" style="width: 20%" class="common" name="prpLDeductible" value="0">
		<input type='hidden' style="width: 60%" class="readonly" name='lastRealPay' value="">
	</logic:notPresent>
	<input type='hidden' name='compensateMessage1' value="">
	<input type='hidden' name='days' value="">
</table>
