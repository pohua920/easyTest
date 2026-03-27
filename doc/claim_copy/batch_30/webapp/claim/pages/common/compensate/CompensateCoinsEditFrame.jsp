<%@	page contentType="text/html; charset=GBK" language="java"%>
<%@include file="/common/taglibs.jsp"%>
<c:choose>
	<c:when test="${coinsFlag==1}">
		<c:set var="coinsFlagShow" value="主辦業務"/>
	</c:when>
	<c:when test="${coinsFlag==2}">
		<c:set var="coinsFlagShow" value="非主辦業務"/>
	</c:when>
	<c:when test="${coinsFlag==3}">
		<c:set var="coinsFlagShow" value="分進業務"/>
	</c:when>
</c:choose>
<script language='javascript'>
	function resetChangelossCharge() {
		//目前只为了联共保判断而增加的,表示变化已经操作过，可以清除了
		if (fm.all("lossOrChargeHaveChanged") != null) {
			fm.lossOrChargeHaveChanged.value = '0';
		}
	}
	/****
	 * 联共保分摊ajax请求
	 * @param prpLcfeecoinsList
	 * @returns {Boolean}
	 */
	function setPrpLcfeecoins(prpLcfeecoinsList){
		if(prpLcfeecoinsList==null || prpLcfeecoinsList == undefined){
			return false;
		}
		var $isPayForOther = $(":input[name='isPayForOther']");
		if($isPayForOther.length>0&&!$isPayForOther.first().is(":checked") && !$isPayForOther.last().is(":checked")){
			alert("請先選擇是否代付賠款,系統默認成了不代付賠款！如代付,請重新進行選擇！");
			$isPayForOther.last().attr("checked",true);
		}
		var isPayForOther = $isPayForOther.val()=="1";//是否代付賠款
		var $data_to = $("#Coins").children("tbody");
		var $data = $("#Coins_Data").children("tbody").children();
		var sumCoinUs = 0;//我方赔款金额
		var sumCoinUsFee = 0;//我方费用金额
		var sumCoinForOther = 0;//代付赔款金额
		var sumCoinForOtherFee = 0;//代付费用金额
		var coinUsCoinsRate = $(":input[name='coinUsCoinsRate']");
		$.each(prpLcfeecoinsList, function(i,prpLcfeecoins){
			var $obj = $data.clone(true);
			$obj.find(":input[name='prpLcoinsSerialNo']").val(prpLcfeecoins.id.serialNo);
			$obj.find(":input[name='prpLcoinsChargeCode']").val(prpLcfeecoins.chargeCode);
			$obj.find(":input[name='prpLcoinsChargeName']").val(prpLcfeecoins.chargeName);
			$obj.find(":input[name='prpLcoinsChiefFlag']").val(prpLcfeecoins.chiefFlag);
			$obj.find(":input[name='prpLcoinsCoinsCode']").val(prpLcfeecoins.coinsCode);
			if(prpLcfeecoins.coinsCode=="18"){
				//台寿保比例
				coinUsCoinsRate.val(Math.round(prpLcfeecoins.coinsRate)/100);
			}
			$obj.find(":input[name='prpLcoinsCoinsName']").val(prpLcfeecoins.coinsName);
			$obj.find(":input[name='prpLcoinsCurrency']").val(prpLcfeecoins.currency);
			$obj.find(":input[name='prpLcoinsCoinsRate']").val(Math.round(prpLcfeecoins.coinsRate));
			$obj.find(":input[name='prpLcoinsCoinsSumpaid']").val(Math.round(prpLcfeecoins.coinsSumPaid));
			$obj.find(":input[name='prpLcoinsCoinsType']").val(prpLcfeecoins.coinsType);
			$obj.find(":input[name='prpLcoinsLossFeeType']").val(prpLcfeecoins.lossFeeType);
			$obj.find(":input[name='prpLcoinsSumpaid']").val(prpLcfeecoins.sumPaid);
			var $prpLcoinsTypeForShow = $obj.find(":input[name='prpLcoinsTypeForShow']");
			if(prpLcfeecoins.lossFeeType == "0"){
				$prpLcoinsTypeForShow.val("<s:text name='db.prpGradeExt.sumPaid'/>");//赔款
			} else if(prpLcfeecoins.lossFeeType == "1"){
				$prpLcoinsTypeForShow.val("<s:text name='claim.cost'/>");//费用
			}
			var $prpLcoinsChiefFlagShow = $obj.find(":input[name='prpLcoinsChiefFlagShow']");
			if(prpLcfeecoins.chiefFlag == "2"){
				$prpLcoinsChiefFlagShow.val("<s:text name='regist.prpLregist.yes'/>");
			} else if(prpLcfeecoins.chiefFlag == "1"){
				$prpLcoinsChiefFlagShow.val("<s:text name='regist.prpLregist.no'/>");
			}
			var $prpLcoinsCoinsTypeShow = $obj.find(":input[name='prpLcoinsCoinsTypeShow']");
			if(prpLcfeecoins.coinsType == "1"){
				$prpLcoinsCoinsTypeShow.val("<s:text name='compensate.weAre'/>");//主承保人
			} else if(prpLcfeecoins.coinsType == "2"){
				$prpLcoinsCoinsTypeShow.val("<s:text name='compensate.otherSystem'/>");//共保人
			} else if(prpLcfeecoins.coinsType == "3"){
				$prpLcoinsCoinsTypeShow.val("<s:text name='compensate.outsideSystem'/>");//
			}
			$obj.appendTo($data_to);
			if(prpLcfeecoins.lossFeeType == "0"){
				if(prpLcfeecoins.coinsType == "1"){
					sumCoinUs += prpLcfeecoins.coinsSumPaid;
				} else {
					sumCoinForOther += prpLcfeecoins.coinsSumPaid;
				}
			} else if(prpLcfeecoins.lossFeeType == "1"){
				if(prpLcfeecoins.coinsType == "1"){
					sumCoinUsFee += prpLcfeecoins.coinsSumPaid;
				} else {
					sumCoinForOtherFee += prpLcfeecoins.coinsSumPaid;
				}
			}
		});
		var $sumCoinUs = $(":input[name='prpLcompensateSumCoinUs']");
		var $sumCoinUsFee = $(":input[name='prpLcompensateSumCoinUsFee']");
		var $sumCoinForOther = $(":input[name='prpLcompensateSumCoinForOther']");
		var $sumCoinForOtherFee = $(":input[name='prpLcompensateSumCoinForOtherFee']");
		var $sumCoinForOtherBak = $(":input[name='prpLcompensateSumCoinForOtherBak']");
		var $sumCoinForOtherFeeBak = $(":input[name='prpLcompensateSumCoinForOtherFeeBak']");
		$sumCoinUs.val(Math.round(sumCoinUs));
		$sumCoinUsFee.val(Math.round(sumCoinUsFee));
		if(isPayForOther){
			$sumCoinForOther.val(Math.round(sumCoinForOther));
			$sumCoinForOtherBak.val(Math.round(sumCoinForOther));
			$sumCoinForOtherFee.val(Math.round(sumCoinForOtherFee));
			$sumCoinForOtherFeeBak.val(Math.round(sumCoinForOtherFee));
		}else{
			$sumCoinForOther.val(0);
			$sumCoinForOtherBak.val(Math.round(sumCoinForOther));
			$sumCoinForOtherFee.val(0);
			$sumCoinForOtherFeeBak.val(Math.round(sumCoinForOtherFee));
		}
		return true;
	}
	function setPrpLcfeecoinsSumDutyPaid(){
		var $isPayForOther = $(":input[name='isPayForOther']");
		if($isPayForOther.length>0&&!$isPayForOther.first().is(":checked") && !$isPayForOther.last().is(":checked")){
			alert("請先選擇是否代付賠款,系統默認成了不代付賠款！如代付,請重新進行選擇！");
			$isPayForOther.last().attr("checked",true);
		}
		var isPayForOther = $isPayForOther.val()=="1";//是否代付賠款
		var $data_to = $("#Coins").children("tbody");
		//var $data = $("#Coins_Data").children("tbody").children();
		var sumDutyPaid = $(":input[name='prpLcompensateSumDutyPaid']").val();
		var sumCoinUs = 0;//我方赔款金额
		var sumCoinUsFee = 0;//我方费用金额
		var sumCoinForOther = 0;//代付赔款金额
		var sumCoinForOtherFee = 0;//代付费用金额
		var coinUsCoinsRate = $(":input[name='coinUsCoinsRate']");
		$("#Coins").children("tbody").children("tr").each(function(){
			var $obj = $(this);
			var coinsRate = $obj.find(":input[name='prpLcoinsCoinsRate']").val();
			var coinsSumpaid = Math.round(sumDutyPaid*coinsRate/100);
			var coinsCode = $obj.find(":input[name='prpLcoinsCoinsCode']").val();
			if(coinsCode=="18"){
				//台寿保比例
				coinUsCoinsRate.val(Math.round(coinsRate)/100);
			}
			if(!$.isNumeric(coinsSumpaid)){
				coinsSumpaid = 0;
			}
			$obj.find(":input[name='prpLcoinsSumpaid']").val(sumDutyPaid);
			$obj.find(":input[name='prpLcoinsCoinsSumpaid']").val(coinsSumpaid);
			var lossFeeType = $obj.find(":input[name='prpLcoinsCoinsType']").val();
			var chiefFlag = $obj.find(":input[name='prpLcoinsChiefFlag']").val();
			if(lossFeeType == "0"){
				if(chiefFlag == "1"){
					sumCoinUs += coinsSumpaid;
				} else {
					sumCoinForOther += coinsSumpaid;
				}
			} else if(lossFeeType == "1"){
				if(chiefFlag == "1"){
					sumCoinUsFee += coinsSumpaid;
				} else {
					sumCoinForOtherFee += coinsSumpaid;
				}
			}
		})
		
		var $sumCoinUs = $(":input[name='prpLcompensateSumCoinUs']");
		var $sumCoinUsFee = $(":input[name='prpLcompensateSumCoinUsFee']");
		var $sumCoinForOther = $(":input[name='prpLcompensateSumCoinForOther']");
		var $sumCoinForOtherFee = $(":input[name='prpLcompensateSumCoinForOtherFee']");
		var $sumCoinForOtherBak = $(":input[name='prpLcompensateSumCoinForOtherBak']");
		var $sumCoinForOtherFeeBak = $(":input[name='prpLcompensateSumCoinForOtherFeeBak']");
		$sumCoinUs.val(Math.round(sumCoinUs));
		$sumCoinUsFee.val(Math.round(sumCoinUsFee));
		if(isPayForOther){
			$sumCoinForOther.val(Math.round(sumCoinForOther));
			$sumCoinForOtherBak.val(Math.round(sumCoinForOther));
			$sumCoinForOtherFee.val(Math.round(sumCoinForOtherFee));
			$sumCoinForOtherFeeBak.val(Math.round(sumCoinForOtherFee));
		}else{
			$sumCoinForOther.val(0);
			$sumCoinForOtherBak.val(Math.round(sumCoinForOther));
			$sumCoinForOtherFee.val(0);
			$sumCoinForOtherFeeBak.val(Math.round(sumCoinForOtherFee));
		}
		return true;
	}
 	function creatCoins() {
 	 	if($("#Coins").children("tbody").children("tr").length>0){
 	 		setPrpLcfeecoinsSumDutyPaid();
			return true;
 	 	 }
		$.ajax({
			type : "POST",
			url: "/claim/compensateCoins.do?jflag="+true,
			data :$("form").serializeArray(),
			dataType : "json",
			beforeSend :function(){
				$("#Coins").children("tbody").empty();//清空原数据
			},
			success : function(data){
				setPrpLcfeecoins(data.prpLcfeecoinsList);
			}
		});
		return true;
	}
</script>
<table class="common" align="center">
	<tr style="display: none">
		<td colspan="4">
		<input type="hidden" name="lossOrChargeHaveChanged" value="">
		<input type="hidden" name="coinUsCoinsRate" value="1">
		<!-- 表示有赔款或者费用变化了 -->
		<input type="hidden" name="buttonCoins" class='bigbutton' value="<s:text name='compensate.generateShareInformation'/>" onclick="creatCoins();creatCoinsFlag('1');resetChangelossCharge();">
		<font color='red'><s:text name="compensate.afterModifyAmountAttentionInformation" />!!!</font>
		<!-- 修改金额後注意重新生成联共保信息 -->
		<!-- 生成联共保分摊信息 -->
		</td>
	</tr>
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="CoinsImg" onclick="showPage(this,spanCoins)">
			<s:text name="compensate.insuranceShareInformation" />
			<br>
			<!-- 联共保分摊信息 -->
			<span style="display: none">
				<table class="common" style="display: none" id="Coins_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="input" style="width: 5%">
								<input type='text' class="readonlyNo" readonly name="prpLcoinsSerialNo" style="width: 90%;" description="序号">
								<input type='hidden' name="prpLcoinsLossFeeType">
								<input type='hidden' name="prpLcoinsCurrency">
								<input type='hidden' name="prpLcoinsCoinsType">
								<input type='hidden' name="prpLcoinsChiefFlag">
							</td>
							<td class="input" style="width: 10%">
								<input name="coinsFlag" class="readonly" readonly value="${coinsFlagShow }">
							</td>
							<td class="input" style="width: 10%">
								<input type='text' name="prpLcoinsTypeForShow" class="readonly" readonly>
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLcoinsCoinsCode" class="readonly" readonly>
							</td>
							<td class="input" style="width: 25%">
								<input type='text' name="prpLcoinsCoinsName" class="readonly" readonly>
							</td>
							<td class="input" style="width: 10%">
								<input type='text' name="prpLcoinsCoinsTypeShow" class="readonly" readonly>
							</td>
							<td class="input" style="width: 10%">
								<input type='text' name="prpLcoinsChiefFlagShow" class="readonly" readonly>
							</td>
							<td class="input" style="width: 10%">
								<input type='text' name="prpLcoinsCoinsRate" class="readonly" readonly style="width: 50%;">%
								<div style="display: none">
									<input type='text' name="prpLcoinsChargeName" class="readonly" readonly>
									<input type='text' name="prpLcoinsChargeCode" class="readonly" readonly>
									<input type="hidden" name="prpLcoinsCoinsSumpaid" class="common" >
									<input type='hidden' name="prpLcoinsSumpaid">
									<input type=button name="buttonCoinsDelete" class=smallbutton onclick="deleteRow(this,'Coins')" value="-" style="display: none">
									<input type="hidden" name="prpLcoinsFlag">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<input type="hidden" name="countFlag" value="">
			<span id="spanCoins" style="display:">
				<table class="common" style="width: 100%" id="Coins" cellpadding="5" cellspacing="1">
					<thead>
						<tr class="common" >
							<td class="centertitle" style="width: 5%">
								<s:text name="db.prpDrate.serialNo" />
							</td>
							<!-- 序号 -->
							<td class="centertitle" style="width: 10%">
								共保狀態
							</td>
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.compensationCategories" />
							</td>
							<!-- 赔付类别 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.insuranceCode" />
							</td>
							<!-- 联共保人代码 -->
							<td class="centertitle" style="width: 25%">
								<s:text name="compensate.insuranceName" />
							</td>
							<!-- 联共保人名称 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.insuranceIdentity" />
							</td>
							<!-- 联共保身份 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.whetherChief" />
							</td>
							<!-- 是否首席 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.insuranceProportion" />
							</td>
							<!-- 联共保比例 -->
							<%-- <s:text name="compensate.shareAmount" /> --%>
							<!-- 分摊金额 -->
						</tr>
					</thead>
					<tfoot>
						<tr style="display: none">
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('Coins')" class=smallbutton name="buttonCoinsInsert" style="display: none">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${prpLcfeecoins.prpLcfeecoinsList}" var="prpLcfeecoinsTemp">
							<tr>
								<td class="input" style="width: 5%">
									<input type="text" class="readonlyNo" readonly name="prpLcoinsSerialNo" value="${prpLcfeecoinsTemp.id.serialNo}" style="width: 90%;" description="序号">
									<input type='hidden' name="prpLcoinsLossFeeType" value="<c:out value='${prpLcfeecoinsTemp.lossFeeType}'/>">
									<input type='hidden' name="prpLcoinsCurrency" value="<c:out value='${prpLcfeecoinsTemp.currency}'/>">
									<input type='hidden' name="prpLcoinsCoinsType" value="<c:out value='${prpLcfeecoinsTemp.coinsType}'/>">
									<input type='hidden' name="prpLcoinsChiefFlag" value="<c:out value='${prpLcfeecoinsTemp.chiefFlag}'/>">
								</td>
								<td class="input" style="width: 10%">
									<input name="coinsFlag" class="readonly" readonly value="${coinsFlagShow }">
								</td>
								<td class="input" style="width: 10%">
									<c:choose>
										<c:when test="${prpLcfeecoinsTemp.lossFeeType=='0'}">
											<input  name="prpLcoinsTypeForShow" type="text"  class="readonly" readonly value="<s:text name='db.prpGradeExt.sumPaid'/>">
										</c:when>
										<c:otherwise>
											<input name="prpLcoinsTypeForShow" type="text"  class="readonly" readonly value="<s:text name='claim.cost'/>">
										</c:otherwise>
									</c:choose>
								</td>
								<td class="input" style="width: 10%">
									<input name="prpLcoinsCoinsCode" type="text" class="readonly" readonly value="<c:out value='${prpLcfeecoinsTemp.coinsCode}'/>">
								</td>
								<td class="input" style="width: 25%">
									<input name="prpLcoinsCoinsName" type="text" class="readonly" readonly  value="${prpLcfeecoinsTemp.coinsName}">
								</td>
								<td class="input" style="width: 10%">
									<c:choose>
										<c:when test="${prpLcfeecoinsTemp.coinsType=='1'}">
											<input name="prpLcoinsCoinsTypeShow" type="text" class="readonly" readonly value="<s:text name='compensate.weAre'/>">
											<%-- 我方 --%>
										</c:when>
										<c:when test="${prpLcfeecoinsTemp.coinsType=='2'}">
											<input name="prpLcoinsCoinsTypeShow" type="text" class="readonly" readonly value="<s:text name='compensate.otherSystem'/>">
											<%-- 系统内他方 --%>
										</c:when>
										<c:when test="${prpLcfeecoinsTemp.coinsType=='3'}">
											<input name="prpLcoinsCoinsTypeShow" type="text" class="readonly" readonly value="<s:text name='compensate.outsideSystem'/>">
											<%-- 系统外他方 --%>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
								<td class="input" style="width: 10%">
									<c:choose>
										<c:when test="${prpLcfeecoinsTemp.chiefFlag=='2'}">
											<input name="prpLcoinsChiefFlagShow" type="text" class="readonly" readonly value="<s:text name='regist.prpLregist.yes'/>">
											<%-- 是 --%>
										</c:when>
										<c:when test="${prpLcfeecoinsTemp.coinsType=='1'}">
											<input name="prpLcoinsChiefFlagShow" type="text" class="readonly" readonly value="<s:text name='regist.prpLregist.no'/>">
											<%-- 否 --%>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
								<td class="input" style="width: 10%">
									<input name="prpLcoinsCoinsRate" type="text" class="readonly" readonly value="${prpLcfeecoinsTemp.coinsRate}" style="width: 50%;">%
									<div style="display: none">
										<input name="prpLcoinsCoinsSumpaid" class="common" value="<fmt:formatNumber value='${prpLcfeecoinsTemp.coinsSumPaid}' pattern='#'/>" >
										<input type='hidden' name="prpLcoinsSumpaid" value="<fmt:formatNumber value='${prpLcfeecoinsTemp.sumPaid}' pattern='#'/>">
										<input name="prpLcoinsChargeCode" type="text" style="width: 50" class="readonly" readonly value="${prpLcfeecoinsTemp.chargeCode}">
										<input name="prpLcoinsChargeName" type="text" class="readonly" readonly value="${prpLcfeecoinsTemp.chargeName}">
										<input type=button name="buttonCoinsDelete" class="smallbutton" onclick="deleteRow(this,'Coins')" value="-" style="display: none">
										<input type="hidden" name="prpLcoinsFlag">
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