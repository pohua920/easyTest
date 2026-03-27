<!--建立显示的录入条，可以收缩显示的-->
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	function viewDangerUnitCompensateLoss(field) {
		for (var i = 1; i < fm.prpLlossDtoSerialNo.length; i++) {
			if (fm.prpLlossDtoDangerNo[i] == field) {
				var count = i;
				var policyNo = fm.policyno.value;
				var damageDate = fm.damageStartDate.value;
				var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateLloss";
   			    window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLlossDtoKindName'],:input[name='prpLlossPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<input type="hidden" name="size" value="${fn:length(requestScope.prpCitemKindListInit)}">
<input type="hidden" name="prpLlossDtoIsLossAll" value="<c:out value='${requestScope.isLossAll}'/>">

<c:set var="itemValue" value="0" />
<%/** 带出的承保险别 及其 险别信息，用於计算 */%>
<c:forEach items="${requestScope.prpCitemKindListInit}" var="prpCitemKind">
  <div name="divPrpCitemKind" style="display: none">
	<input type="hidden" name=prpLlossDtoKindCodeShow  style="width:25px"  value="<c:out value='${prpCitemKind.kindCode}' />">
	<input type="hidden" name=prpLlossDtoKindNameShow  style="width:55px"  value="<c:out value='${prpCitemKind.kindName}' />">
	<input type="hidden" name=DutyDeductibleRate  value="<c:out value='${prpCitemKind.dutyDeductibleRate}' />"><%/*事故责任免赔率*/%>
	<input type="hidden" name=DeductibleRate      value="<c:out value='${prpCitemKind.deductibleRate}' />"><%/*免赔率*/%>
	<input type="hidden" name=Deductible      value="<c:out value='${prpCitemKind.deductible}' />"><%/*事故责任免赔额*/%>
	<input type="hidden" name="kindAmount" value="<c:out value='${prpCitemKind.amount}' />"><%/**险别赔偿限额*/%>
	<input type="hidden" name="unitAmount" value="<c:out value='${prpCitemKind.unitAmount}' />">
	<input type="hidden" name="value" value="<c:out value='${prpCitemKind.value}' />">
	<c:choose>
	  <c:when test="${fn:length(prpCitemKind.flag)>=4}">
	     <input type="hidden" name="flag" value="${fn:substring(prpCitemKind.flag,4,5)}">
	  </c:when>
	  <c:otherwise>
	     <input type="hidden" name="flag" value="0">
	  </c:otherwise>
	</c:choose>
	<input type="hidden" name="prpLsumDefPayAllShow" value="0">
	<input type="hidden" name="prpLsumDefPayAllShowMiddle" value="0">
	<input type="hidden" name="prpLsumRealPayAllShow" value="0">
	<input type="hidden" name="prpLchargeSumRealPayAllShow" value="0">
	<input type="hidden" name="prpLchargeAmountShow" value="0">
  </div>
</c:forEach>
<%/** 各险别赔款限额 **/%>
<div style="display: none" id="limitList">
  <c:forEach items="${requestScope.limitList}" var="mapObject">
     <div name="limitObject">
        <input type="hidden" name="limitKindCode" value="${mapObject['limitKindCode']}"><%/**受限险别代码*/%>
        <input type="hidden" name="limitKindName" value="${mapObject['limitKindName']}"><%/**受限险别名称*/%>
        <input type="hidden" name="limitAmount" value="${mapObject['limitAmount']}"><%/**每事故限额*/%>
        <input type="hidden" name="limitPastPay" value="${mapObject['limitPastPay']}"><%/**本案险别已赔付*/%>
        <!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 -->
        <input type="hidden" name="limitPastPayE" value="${mapObject['limitPastPayE']}"><%/**本案超額险别已赔付*/%>
        <input type="hidden" name="limitPersonPastPay" value="${mapObject['limitPersonPastPay']}"><%/**本案险别人伤已赔付，limitType为2时会有值*/%>
        <input type="hidden" name="limitFlag" value="${mapObject['limitFlag']}"><%/**状态 0：接受限额控制；1：不受限*/%>
        <input type="hidden" name="limitMeter" value="${mapObject['limitMeter']}"><%/**计次状态：0赔付次数达限*/%>
        <input type="hidden" name="limitMaxNum" value="${mapObject['limitMaxNum']}"><%/**可赔付次数：limitMeter为0时会有值*/%>
        <input type="hidden" name="limitType" value="${mapObject['limitType']}"><%/**限制类型：0每次事故,1每次事故每人,2每次事故每人财产单独*/%>
        <input type="hidden" name="limitPropAmount" value="${mapObject['limitPropAmount']}"><%/**财产限额 limitType为2时会有值，代表车物损赔付部分的限额*/%>
        <input type="hidden" name="limitPersonAmount" value="${mapObject['limitPersonAmount']}"><%/**每人限额 limitType为1\2时会有值，*/%>
        <input type="hidden" name="limitResidue" value="${mapObject['limitResidue']}"><%/**累计型的：剩余赔付；-1时代表非累计型*/%>
        <input type="hidden" name="limitTotalPay" value="${mapObject['limitTotalPay']}"><%/**累计型的：历史已赔付；limitResidue非-1时有值*/%>
        <input type="hidden" name="limitDeductible" value="${mapObject['limitDeductible']}"><%/**自负额*/%>
        <input type="hidden" name="limitDeductibleRate" value="${mapObject['limitDeductibleRate']}"><%/**自负额比例*/%>
        <!-- mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START-->
        <input type="hidden" name="limitDeductibleTypeConfirm" value="${mapObject['limitDeductibleTypeConfirm']}"><%/**自负额型態確認抬頭*/%>
        <input type="hidden" name="limitDeductibleCount" value="${mapObject['limitDeductibleCount']}"><%/**有效保期內已使用次數*/%>
        <!-- mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END-->
        
     </div>
  </c:forEach>
  <c:forEach items="${requestScope.pastPersonPayList}" var="map">
      <input type="hidden" name="${map.key}" value="${map.value}">
  </c:forEach>
</div>
<c:set var="purchasePrice" value="0" />
<c:set var="finalValue" value="0" />
<c:set var="factValue" value="0" />
<c:if test="${not empty requestScope.purchasePrice && not empty requestScope.finalValue && not empty requestScope.factValue}">
	<c:set var="purchasePrice" value="${requestScope.purchasePrice}" />
	<c:set var="finalValue" value="${requestScope.finalValue}" />
	<c:set var="factValue" value="${requestScope.factValue}" />
</c:if>
<table class="common" align="center" style="width: 100%">
	<tr>
		 <td class="common" colspan="4" style="text-align: left">
		 		<img style="cursor:hand;" src="/claim/images/butExpandBlue.gif" name="lLossImg" onclick="showPage(this,CompensateLoss);changeCompensateFlag('1');"><b><s:text name="compensate.compCheSunLossInfo" /></b><%-- 赔付车损/物损信息 --%>
		 		<input type="hidden" class="button" name="button_Loss_Collect" value="<s:text name='button.summary.value' />" onclick="return showLossCollect();">&nbsp;&nbsp;&nbsp;<s:text name="compensate.carPurchaseValue" />：<%-- 标的车新车购置价 --%>
		 		<input type="text" name="purchasePrice" class="readonly" readonly style="width:80px" value="<fmt:formatNumber value='${pageScope.purchasePrice}' pattern='#'/>" onChange="calRealValuen();relateChange2(this);calRealpayWithFinalValue()">
		 		<input type="hidden" name="finalValue" value="${pageScope.finalValue}">&nbsp;&nbsp;&nbsp;<s:text name="quickCase.markCarRealValue" /><%--标的车实际价值 --%>
		 		<input name="factValue" class="readonly" style="width: 80px" readonly value="<fmt:formatNumber value='${pageScope.factValue}' pattern='#'/>">
		 		<br>
         <span style="display:none">
				<table class="common" style="display: none" id="prpLloss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr name="prpLlossObject">
							<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="1" onClick="viewDangerUnitCompensateLoss(this);">
	         		 		<td class="input" style="width:15%" style="align:center"><%-- 险别 --%>
								<input type="hidden" name="lossDtoSerialNo" style="width: 10px">
								<input type="hidden" name="prpLlossDtoSerialNo" style="width: 10px">
								<input type=text name="prpLlossDtoKindCode" class="codecode" style="width: 20%" title="險別" readonly="readonly"
									ondblclick="clearPrpLloss(this);code_CodeSelect(this,'PolicyKindCodeForCarAndProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="clearPrpLloss(this);code_CodeSelect(this,'PolicyKindCodeForCarAndProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onChange="clearPrpLloss(this);code_CodeChange(this,'PolicyKindCodeForCarAndProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onfocus="cacheData(this);" onblur="inputKindControl(this,'prpLloss');getPrpLlossPrpItemKind(this);setAccidentType();">
								<input type=text name="prpLlossDtoKindName" class="codecode" style="width: 70%" title="險別" readonly="readonly"
									ondblclick="clearPrpLloss(this);code_CodeSelect(this, 'PolicyKindCodeForCarAndProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="clearPrpLloss(this);code_CodeSelect(this, 'PolicyKindCodeForCarAndProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onChange="clearPrpLloss(this);code_CodeChange(this, 'PolicyKindCodeForCarAndProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onfocus="cacheData(this);" onblur="inputKindControl(this,'prpLloss');getPrpLlossPrpItemKind(this);setAccidentType();">
								<input name="prpLlossDtoItemKindNo" type="hidden">
							</td>
	                		<td class="inputsubsub" style="width:7%"><%-- 牌照號碼 --%>
								<input name="prpLlossDtoLicenseNo" class="input" style="width: 98%" onchange="inputControl(this);" title="物損賠付記錄，牌照號碼請填“無”">
							</td>
							<td class="inputsubsub" style="width: 10%">
								<%-- 财物名称 --%>
								<input name="prpLlossDtoLossName" maxlength=40 class="common" style="width: 98%" title="財物名稱">
							</td>
							<td class="inputsubsub" style="display: none" align="right">
								<%-- 核定损失 --%>
								<input name="prpLlossDtoSumLoss" class="common" style="width: 98%" value='0'>
							</td>
							<td class="inputsubsub" style="width: 8%" align="right">
								<%-- 核定赔偿 --%>
								<%-- mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 --%>
								<input name="prpLlossDtoSumDefPay" class="common" style="width: 98%" value='0' onfocus="cacheData(this);" onchange="validateMoney(this);calRealpayNew(this);calculateFinishAndDayCount();" title="核定賠償">
							</td>
							<td class="inputsubsub" style="width: 7%">
								<%-- 剔除金额/残值 --%>
								<input name="prpLlossDtoSumRest" class="common" style="width: 98%" value="0" onfocus="cacheData(this);" onchange="validateMoney(this);calRealpayNew(this);" title="剔除金額/殘值">
							</td>
							<td class="inputsubsub" style="width: 5%">
								<%-- 折旧率 --%>
								<input name="prpLlossDtoDepreRate" class="common" style="width: 98%" value="0" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpayNew(this);" title="折舊率">
							</td>
							<td class="inputsubsub" style="width: 5%">
								<%-- 自負額 --%>
								<input name="prpLlossDtoDeductible" type="text" class='common' value='0' onfocus="cacheData(this);" onchange="validateMoney(this);checkLossDeductibleRateNew(this);calRealpayNew(this);"
									title="自負額">
							</td>
							<td class="inputsubsub" style="width: 5%">
								<%-- 自负额比例/ 取的是事故责任免赔率 --%>
								<input name="prpLlossDtoDutyDeductibleRate" class='common' value='0' style="width: 98%;" onfocus="cacheData(this);"
									onchange="validatePercent(this,0,100);checkLossDeductibleRateNew(this);calRealpayNew(this);" title="自負額比率">
								<input name="prpLlossDtoDeductibleRate" class='common' value='0' style="width: 98%; display: none" onfocus="cacheData(this);"
									onchange="validatePercent(this,0,100);checkLossDeductibleRateNew(this);calRealpayNew(this);" title="自負額比率">
								<input type="hidden" name="PrpLlossDtoMainKindDuctibleRate">
								<input type="hidden" name="prpLlossDtoDriverDeductibleRate">
							</td>
							<td class="inputsubsub" style="width: 5%">
								<%-- 肇事責任比率  --%>
								<input name="prpLlossDtoIndemnityDutyRate" class='common' type="text" value='0' onfocus="cacheData(this);"
									onchange="validatePercent(this,0,100);validatePercent(this,0,100);calRealpayNew(this);" title="肇事責任比率">
							</td>
							<td class="inputsubsub" style="width: 7%" align="right">
								<input name="prpLlossDtoSumRealPay" class='readonly' style="width: 98%" readonly value="0">
								<input name="prpLlossDtoExceptDeductiblePay" value="0" type="hidden">
								<input name="prpLlossDtoExceptDeductibleRate" value="0" type="hidden">
								<input name="prpLlossDtoFlag" type="hidden">
								<input type="hidden" name="prpLlossDtoFamilyNo">
								<input type='hidden' name='prpLlossDtoFamilyName'>
								<input type='hidden' name='prpLlossDtoItemCode'>
								<input type='hidden' name='prpLlossDtoItemAddress'>
								<input type="hidden" name="prpLlossDtoCurrency2" value="${LOCAL_CURRENCY}">
								<input type="hidden" name="prpLlossDtoCurrency2Name2" value="<%=com.sinosoft.claim.common.ConstantCodes.LOCAL_CURRENCYNAME%>">
								<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
								<input type='hidden' name='prpLlossDtoCurrency' value="${LOCAL_CURRENCY}">
								<input type='hidden' name='prpLlossDtoCurrency1' value="${LOCAL_CURRENCY}">
								<input type='hidden' name='prpLlossDtoCurrency3' value="${LOCAL_CURRENCY}">
								<input type='hidden' name='prpLlossDtoCurrency4' value="${LOCAL_CURRENCY}">
								<input type='hidden' name='prpLlossDtoUnit'>
								<input type="hidden" name="prpLlossDtoAmount" value="0">
								<input type="hidden" name="prpLlossDtoItemValue" value="0">
								<input type="hidden" name="prpLlossDtoUnitPrice" value="0">
								<input type="hidden" name="prpLlossDtoLossQuantity">
								<input type="hidden" name="button_Loss_Refresh">
								<%-- 強制險給付金額 --%>
								<input type="hidden" name="prpLlossDtoCompelPay" class="common" style="width: 98%" value="0" onfocus="cacheData(this);" onchange="validateMoney(this);calRealpayNew(this);" title="強制險給付金額">
								<input name="prpLlossDtoClaimRate" type="hidden" class="common" value="100.000" style="width: 98%">
								<input name="prpLlossDtoArrangeRate" class="common" type="hidden" style="width: 98%">
								<input type="hidden" name="prpLlossDtoFeeTypeCode" class='codecode' style="width: 30%" value="01" ondblclick="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
									onchange="code_CodeChange(this, 'PropertyFeeType','0,1','Y');" onkeyup="code_CodeSelect(this,'PropertyFeeType','0,1','Y');" onblur="code_CodeChange(this,'PropertyFeeType',1);">
								<input type="hidden" name="prpLlossDtoFeeTypeName" class='codename' style="width: 98%" value="修理费" ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');"
									onchange="code_CodeChange(this, 'PropertyFeeType','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');">
							</td>
							<td class="inputsubsub" style="width: 9%" title="请单击选择賠付對象讯息">
								<%-- 賠付對象序号  --%>
								<input name="prpLlossPayObjectSerialNo" class='common' type="text" onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly">
								<%--onblur="checkPayObjectSerialNo(this);" --%>
							</td>
							<td class="inputsubsub" style="width: 4%" >
								<s:select name="prpLlossReservedEstimate" list="#attr.reservedEstimateList" onchange="setAccidentType()"></s:select>
							</td>
							<!-- delete by chenjie 20150601 需求變更-095 
							<td class="inputsubsub" style="width: 10%" align="right">
							</td>
							-->
							<td class="inputsubsub" style='width: 3%'>
								<div align="center">
									<input type=button name="buttonCompensateLossDelete" class="smallbutton" onclick="deletePrpLlossObject(this);" value="-" readonly style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanlLoss" style="display:">
				<table id="CompensateLoss" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="centertitle" style="width: 15%" align="center">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
							</td>
							<%-- 险别 --%>
							<td class="centertitle" style="width: 7%" align="center">牌照號碼</td>
							<%-- 牌照號碼 --%>
							<td class="centertitle" style="width: 10%" align="center">
								<s:text name="compensate.propertyName" />
							</td>
							<%-- 财物名称 --%>
							<td class="centertitle" style="display: none" align="center">
								<s:text name="compensate.approvedLoss" />
							</td>
							<%-- 核定损失 --%>
							<td class="centertitle" style="width: 8%" align="center">
								<s:text name="compensate.approvedCompen" />
							</td>
							<%-- 核定赔偿 --%>
							<td class="centertitle" style="width: 7%" align="center">
								<s:text name="compensate.excludAmountSalvage" />
							</td>
							<%-- 剔除金额/残值 --%>
							<td class="centertitle" style="width: 5%" align="center">折舊率</td>
							<%-- 折舊率 --%>
							<td class="centertitle" style="width: 5%" align="center">自負額</td>
							<%-- 自負額 --%>
							<td class="centertitle" style="width: 5%" align="center">自負額比率</td>
							<%-- 自負額比率 --%>
							<td class="centertitle" style="width: 5%" align="center">肇事責任比率</td>
							<%-- 肇事責任比率 --%>
							<td class="centertitle" style="width: 7%" align="center">
								<s:text name="claim.compenPay" />
							</td>
							<%-- 赔偿金额 --%>
							<td class="centertitle" style="width: 9%" align="center" title="请单击选择賠付對象讯息">賠付對象讯息</td>
							<td class="centertitle" style="width: 4%" align="center" >保留預估</td>
							<!-- delete by chenjie 20150601 需求變更-095 
							<td class="centertitle" style="width: 10%" align="center">肇責類型</td>
							-->
							<td class="centertitle" style="width: 3%" align="center">
								<s:text name="certify.operate" />
							</td>
							<%-- 操作 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan="12">
								<s:text name="prompt.compensate.addRemove01" />
							</td>
							<%-- (按"+"号键增加赔付标的信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 3%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertPrpLlossObject(this);" name="buttonInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="PrpLloss">
						<c:if test="${not empty requestScope.prpLloss.prpLlossList}">
							<c:forEach items="${requestScope.prpLloss.prpLlossList}" var="prpLlossTemp">
								<tr name="prpLlossObject">
									<c:choose>
										<c:when test="${prpLlossTemp.dangerNo!=0}">
											<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="${prpLlossTemp.dangerNo}" onClick="viewDangerUnitCompensateLoss(this);">
										</c:when>
										<c:otherwise>
											<input name="prpLlossDtoDangerNo" type="hidden" class="codecode" value="1" onClick="viewDangerUnitCompensateLoss(this);">
										</c:otherwise>
									</c:choose>
									<td class="input" style="width: 15%" style="align:center">
										<%-- 险别 --%>
										<input type="hidden" name="lossDtoSerialNo" style="width: 10px">
										<input type="hidden" name="prpLlossDtoSerialNo" style="width: 10px">
										<input type=text name="prpLlossDtoKindCode" class="codecode" style="width: 20%" title="險別" readonly="readonly"
											ondblclick="clearPrpLloss(this);code_CodeSelect(this,'PolicyKindCodeForCarAndProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onkeyup="clearPrpLloss(this);code_CodeSelect(this,'PolicyKindCodeForCarAndProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onChange="clearPrpLloss(this);code_CodeChange(this,'PolicyKindCodeForCarAndProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onblur="inputKindControl(this,'prpLloss');getPrpLlossPrpItemKind(this);setAccidentType();" value="${prpLlossTemp.kindCode}">
										<input type=text name="prpLlossDtoKindName" class="codecode" style="width: 70%" title="險別" readonly="readonly"
											ondblclick="clearPrpLloss(this);code_CodeSelect(this, 'PolicyKindCodeForCarAndProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onkeyup="clearPrpLloss(this);code_CodeSelect(this, 'PolicyKindCodeForCarAndProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onChange="clearPrpLloss(this);code_CodeChange(this, 'PolicyKindCodeForCarAndProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onblur="inputKindControl(this,'prpLloss');getPrpLlossPrpItemKind(this);setAccidentType();" value="${prpLlossTemp.kindName}">
										<input name="prpLlossDtoItemKindNo" type="hidden" value="${prpLlossTemp.itemKindNo}">
									</td>
									<td class="inputsubsub" style="width: 7%">
										<%-- 号牌 --%>
										<input name="prpLlossDtoLicenseNo" class="input" style="width: 98%" value="<c:out value='${prpLlossTemp.licenseNo}'/>" onchange="inputControl(this);" title="物損賠付記錄，牌照號碼請填“無”">
									</td>
									<td class="inputsubsub" style="width: 10%">
										<%-- 财物名称 --%>
										<input name="prpLlossDtoLossName" maxlength=40 class="common" style="width: 98%" value="${prpLlossTemp.lossName}" title="財物名稱">
									</td>
									<td class="inputsubsub" style="display: none" align="right">
										<%-- 核定损失 --%>
										<input name="prpLlossDtoSumLoss" class="common" style="width: 98%" value="${prpLlossTemp.sumLoss}">
									</td>
									<td class="inputsubsub" style="width: 8%" align="right">
										<%-- 核定赔偿 --%>
										<%-- mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 --%>
										<input name="prpLlossDtoSumDefPay" class="common" style="width: 98%" value="<fmt:formatNumber value='${prpLlossTemp.sumDefPay}' pattern='#'/>" onfocus="cacheData(this);"
											onchange="validateMoney(this);calRealpayNew(this);calculateFinishAndDayCount();" title="核定賠償">
									</td>
									<td class="inputsubsub" style="width: 7%">
										<%-- 剔除金额/残值 --%>
										<input name="prpLlossDtoSumRest" class="common" style="width: 98%" value="<fmt:formatNumber value='${prpLlossTemp.sumRest}' pattern='#'/>" onfocus="cacheData(this);"
											onchange="validateMoney(this);calRealpayNew(this);" title="剔除金額/殘值">
									</td>
									<td class="inputsubsub" style="width: 5%">
										<%-- 折旧率 --%>
										<input name="prpLlossDtoDepreRate" class="common" style="width: 98%" value="<fmt:formatNumber value='${prpLlossTemp.depreRate}'  maxFractionDigits='2'/>" onfocus="cacheData(this);"
											onchange="validatePercent(this,0,100);calRealpayNew(this);" title="折舊率">
									</td>
									<td class="inputsubsub" style="width: 5%">
										<%-- 自負額 --%>
										<input name="prpLlossDtoDeductible" class='common' value="<fmt:formatNumber value='${prpLlossTemp.deductible}' pattern='#'/>" onfocus="cacheData(this);"
											onchange="validateMoney(this);checkLossDeductibleRateNew(this);calRealpayNew(this);" title="自負額">
										<!-- 免赔率选择--------------------->
									</td>
									<td class="inputsubsub" style="width: 5%">
										<%-- 自负额比例/ 原存的是 --%>
										<input name="prpLlossDtoDutyDeductibleRate" class='common' style="width: 98%" value="<fmt:formatNumber value='${prpLlossTemp.dutyDeductibleRate}'  maxFractionDigits='2'/>"
											onfocus="cacheData(this);" onchange="validatePercent(this,0,100);checkLossDeductibleRateNew(this);calRealpayNew(this);" title="自負額比率">
										<input name="prpLlossDtoDeductibleRate" class='common' style="width: 98%; display: none" value="${prpLlossTemp.deductiblerate}">
										<input type="hidden" name="PrpLlossDtoMainKindDuctibleRate">
										<input type="hidden" name="prpLlossDtoDriverDeductibleRate">
									</td>
									<td class="inputsubsub" style="width: 5%">
										<%-- 肇事責任比率  --%>
										<input name="prpLlossDtoIndemnityDutyRate" class='common' value="<fmt:formatNumber value='${prpLlossTemp.indemnityDutyRate}'  maxFractionDigits='2'/>" onfocus="cacheData(this);"
											onchange="validatePercent(this,0,100);calRealpayNew(this);">
									</td>
									<td class="inputsubsub" style="width: 7%" align="right">
										<%-- 赔偿金额 --%>
										<input name="prpLlossDtoSumRealPay" class='readonly' style="width: 98%" readonly value="<fmt:formatNumber value='${prpLlossTemp.sumRealPay}' pattern='#'/>">
										<input name="prpLlossDtoExceptDeductiblePay" type="hidden" value="${prpLlossTemp.exceptDeductiblePay}">
										<input name="prpLlossDtoExceptDeductibleRate" type="hidden" value="${prpLlossTemp.exceptDeductibleRate}">
										<input name="prpLlossDtoFlag" type="hidden" value="${prpLlossTemp.flag}">
										<input type="hidden" name="prpLlossDtoFamilyNo" value="${prpLlossTemp.familyNo}">
										<input type='hidden' name='prpLlossDtoFamilyName' value="${prpLlossTemp.familyName}">
										<input type='hidden' name='prpLlossDtoItemCode' value="${prpLlossTemp.itemCode}">
										<input type='hidden' name='prpLlossDtoItemAddress' value="${prpLlossTemp.itemAddress}">
										<input type="hidden" name="prpLlossDtoCurrency2" value="${LOCAL_CURRENCY}">
										<input type="hidden" name="prpLlossDtoCurrency2Name2" value="<%=com.sinosoft.claim.common.ConstantCodes.LOCAL_CURRENCYNAME%>">
										<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
										<input type='hidden' name='prpLlossDtoCurrency' value="${LOCAL_CURRENCY}">
										<input type='hidden' name='prpLlossDtoCurrency1' value="${LOCAL_CURRENCY}">
										<input type='hidden' name='prpLlossDtoCurrency3' value="${LOCAL_CURRENCY}">
										<input type='hidden' name='prpLlossDtoCurrency4' value="${LOCAL_CURRENCY}">
										<input type='hidden' name='prpLlossDtoUnit' value="${prpLlossTemp.unit}">
										<input type="hidden" name="prpLlossDtoAmount" value="${prpLlossTemp.amount}">
										<input type="hidden" name="prpLlossDtoItemValue" value="${prpLlossTemp.itemValue}">
										<input type="hidden" name="prpLlossDtoUnitPrice" value="${prpLlossTemp.unitPrice}">
										<input type="hidden" name="prpLlossDtoLossQuantity" value="${prpLlossTemp.lossQuantity}">
										<input type="hidden" name="button_Loss_Refresh">
										<!-- 不计免赔率之和 -->
										<input type="hidden" name="prpLlossExceptDeductibleRate" value="${prpLlossTemp.exceptDeductibleRate}">
										<!-- 不计免赔率金额 -->
										<input type="hidden" name="prpLlossExceptDeductiblePay" value="${prpLlossTemp.exceptDeductiblePay}">
										<input type="hidden" name="prpLlossFlag" value="${prpLlossTemp.flag}">
										<%-- 強制險給付金額 --%>
										<input type="hidden" name="prpLlossDtoCompelPay" class="common" style="width: 98%" value="<fmt:formatNumber value='${prpLlossTemp.compelPay}' pattern='#'/>" onfocus="cacheData(this);"
											onchange="validateMoney(this);calRealpayNew(this);" title="強制險給付金額">
										<input name="prpLlossDtoClaimRate" type="hidden" class="common" style="width: 98%" value="${prpLlossTemp.claimRate}">
										<input name="prpLlossDtoArrangeRate" type="hidden" class="common" style="width: 98%" onchange="getArrangeRate(this);" value="${prpLlossTemp.arrangeRate}">
										<input type="hidden" name="prpLlossDtoFeeTypeCode" class='codecode' style="width: 30%" ondblclick="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
											onchange="code_CodeChange(this, 'PropertyFeeType','0,1','Y');" onkeyup="code_CodeSelect(this,'PropertyFeeType','0,1','Y');" onblur="code_CodeChange(this,'PropertyFeeType',1);"
											value="${prpLlossTemp.feeTypeCode}">
										<input type="hidden" name="prpLlossDtoFeeTypeName" class='codename' style="width: 98%" ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');"
											onchange="code_CodeChange(this, 'PropertyFeeType','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');" value="${prpLlossTemp.feeTypeName}">
									</td>
									<td class="inputsubsub" style="width: 9%" title="请单击选择賠付對象讯息">
										<%-- 賠付對象序号  --%>
										<input name="prpLlossPayObjectSerialNo" class='common' type="text" value="${prpLlossTemp.payObjectSerialNo}" onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly">
										<%-- onblur="checkPayObjectSerialNo(this);" --%>
									</td>
									<td class="inputsubsub" style="width: 4%" >
										<s:select name="prpLlossReservedEstimate" list="#attr.reservedEstimateList" value="#attr.prpLlossTemp.reservedEstimate" onchange="setAccidentType()"></s:select>
									</td>
									<!-- delete by chenjie 20150601 需求變更-095 
									<td class="inputsubsub" style="width: 10%" align="right">
									</td>
									-->
									<td class="input" style='width: 3%' align="center">
										<div>
											<input type=button name="buttonCompensateLossDelete" class=smallbutton onclick="deletePrpLlossObject(this);" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
	<c:choose>
		<c:when test="${requestScope.prpLdeductible!=null}">
			<td class="title" style="width: 12%">
				<b><s:text name="compensate.ptionalFranchise" />：</b>
			</td>
			<%-- 可选免赔额信息 --%>
			<td class="title" style="width: 25%">
				<s:text name="compensate.optionalLossDanger" />
				<%-- 车辆损失险(A)可选免赔额 --%>
				<input class="readonly" style="width: 20%" name="prpLDeductible" value="${requestScope.prpLdeductible.deductible}">
			</td>
			<td class="title" style="width: 63%">
				<input type="hidden" style="width: 60%" class="readonly" name='lastRealPay' value="">
			</td>
		</c:when>
		<c:otherwise>
			<input type="hidden" style="width: 20%" class="common" name="prpLDeductible" value="0">
			<input type='hidden' style="width: 60%" class="readonly" name='lastRealPay' value="">
		</c:otherwise>
	</c:choose>
	<input type='hidden' name='compensateMessage1' value="">
	<input type='hidden' name='days' value="">
</table>
<div >
	<div id="divCarAccidentType" style="float:left;padding-left: 10px;margin: 10px 0px;display: none">
		<label style="vertical-align: middle;"><s:text name="claim.carAccidentType" />：<%-- 車體險肇責類型 --%></label>
		<s:select name="selectAccidentType" id="prpLcompensateAccidentType" disabled="true" list="#attr.accidentTypeList" listKey="key" listValue="value" value="#request.prpLcompensate.accidentType" style="width:140px;"></s:select>
		<input type="hidden" name="prpLcompensateAccidentType" value="<s:property value="#request.prpLcompensate.accidentType"/>">
	</div>
	<div id="divPropAccidentType" style="float:left;padding-left: 10px;margin: 10px 0px;display: none">
		<c:choose>
			<c:when test="${requestScope.prpLclaim.riskCode==riskCodeBZ}">
				<label style="vertical-align: middle;"><s:text name="claim.accidentType" />：<%-- 肇責類型 --%></label>
			</c:when>
			<c:otherwise>
				<label style="vertical-align: middle;"><s:text name="claim.propAccidentType" />：<%-- 責任險肇責類型 --%></label>
			</c:otherwise>
		</c:choose>
		<s:select name="selectPropAccidentType" id="prpLcompensatePropAccidentType" disabled="true" list="#attr.accidentTypeList" listKey="key" listValue="value" value="#request.prpLcompensate.propAccidentType" style="width:140px;"></s:select>
		<input type="hidden" name="prpLcompensatePropAccidentType" value="<s:property value="#request.prpLcompensate.propAccidentType"/>">
	</div>
	<c:if test="${editType != 'ADD' && editType != 'EDIT'}">
		<c:if test="${not empty prpLcompensate.accidentType}">
			<script type="text/javascript">$("#divCarAccidentType").show();</script>
		</c:if>
		<c:if test="${not empty prpLcompensate.propAccidentType}">
			<script type="text/javascript">$("#divPropAccidentType").show();</script>
		</c:if>
	</c:if>
</div>