<%@ include file="/common/taglibs.jsp"%>
<span style="display: none;">
	<table class=common cellpadding="1" cellspacing="1" id="Remnant_Data">
	  <tbody>
	  	<tr name="trRemnant">
	  		<td class="right" style="width: 5%;">
	  			<input type="text" name="prpLremnantSerialNo" value="0" class="readonly" readonly="readonly"/>
	  		</td>
	  		<td class="subformtitle" style="width: 90%;">
	  			<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<s:text name='print.dangerPlan' />：
						</td>
						<%-- 出險險種 --%>
						<td class="input" style="width: 15%" style="align:center">
							<input type=text name="prpLremnantKindCode" style="width: 20%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRaskType'/>" maxlength="23" class="codecode"
								ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
								onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
								onchange="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="" value="">
							<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
							<input type=text name="prpLremnantKindName" title="<s:text name='db.prpDrate.kindName'/>" maxlength="100" class="codecode" style="width: 66%"
								ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
								onkeyup="code_CodeSelect(this,'PolicyKindCode', '-1,0', 'Y', 'N', fm.prpLcompensatePolicyNo.value+ '|' + fm.damageStartDate.value + '|' + fm.damageStartHour.value);"
								onchange="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="" value="">
							<%-- <img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='prpLremnant.address' />：
						</td>
						<%-- 放置地點 --%>
						<td class="right">
							<input class="common" type=text name="prpLremnantAddress" value="">
							<%--<img src="/claim/images/bgMarkMustInput.jpg"> --%>
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name='prpLremnant.generateDate' />：
						</td>
						<%-- 產生日期 --%>
						<td class="right">
							<rc:rcDate name="prpLremnantGenerateDate" style="width:90%;" />
							<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='db.prpLregist.estimateLoss' />：
						</td>
						<%-- 預估金額 --%>
						<td class="right">
							<input type=text name="prpLremnantEstimateAmount" class="common" onblur="checkNumber(this)" value="0">
							<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='prpLremnant.auctionDate' />：
						</td>
						<%-- 拍賣日期 --%>
						<td class="right">
							<rc:rcDate name="prpLremnantAuctionDate" style="width:90%;" />
							<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name='prpLremnant.auctionAmount' />：
						</td>
						<%-- 拍賣金額 --%>
						<td class="right">
							<input type=text name="prpLremnantAuctionAmount" title="拍賣金額" onblur="checkNumber(this)" maxlength="23" class="common" value="0">
							<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='prpLremnant.backAmount' />：<%-- 失竊車返還額--%>
						</td>
						<td class="right">
							<input type=text name="prpLremnantBackAmount" class="common" onblur="checkNumber(this)" value="0">
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='prpLremnant.realPay' />：
						</td>
						<%-- 實繳金額 --%>
						<td class="right">
							<input type=text name="prpLremnantRealPay" class="common" value="0" onblur="checkNumber(this)">
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
					</tr>
					<tr>
						<td class="left">
							幣別：
						</td>
						<td class="right">
							<s:select name="prpLremnantCurrency" list="#request.prpLpayObjectInfoCurrencyList" onchange="setExchRate(this);" listKey="key" listValue="value"></s:select>
						</td>
						<td class="left">
							匯率：
						</td>
						<td class="right">
							<input type=text name="prpLremnantExchRate" class="common" onblur="checkNumber(this)" value="1">
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							實際金額(NTD)：
						</td>
						<td class="right">
							<input type=text name="prpLremnantCurrencyPay" class="readonly" readonly="readonly" value="0"  >
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name='prpLremnant.shareDate' />：
						</td>
						<%-- 攤回日期 --%>
						<td class="right">
							<rc:rcDate name="prpLremnantShareDate" style="width:90%;" value="" />
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='prpLremnant.handleCost' />：
						</td>
						<%-- 處理費用 --%>
						<td class="right">
							<input class="readonly" type=text name="prpLremnantHandleCost" readonly="readonly" value="0" onblur="checkNumber(this)">
						<%-- <img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							收取對象訊息
						</td>
						<td class="right">
							<input name="prpLremnantPayObjectSerialNo" class="common" onclick="setPrpObjectinfoSerialNo(this);" value="${prpLremnant.payobjectserialno}" />
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name='prpLremnant.confirmorName' />：
						</td>
						<%-- 確認人 --%>
						<td class="right">
							<input class="common" type=text name="prpLremnantConfirmorName" value="">
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='db.prpLsalvation.verifyDate' />：
						</td>
						<%-- 確認日期 --%>
						<td class="right">
							<rc:rcDate name="prpLremnantConfirmDate" style="width:90%;" value="" />
							<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
						<td class="left">
							<s:text name='prpLremnant.remnants' />：
						</td>
						<%-- 殘餘物任務是否結束 --%>
						<td class="right">
							<select name="prpLremnantRemnants">
								<option value="1" >
									<s:text name='regist.prpLregist.yes' />
								</option>
								<option value="0" >
									<s:text name='regist.prpLregist.no' />
								</option>
							</select>
							<%--			 <img src="/claim/images/bgMarkMustInput.jpg">--%>
						</td>
					</tr>
				</table>
	  		</td>
	  		<td class="right" style="width: 5%;">
	  			<input type="button" value="-" class=smallbutton onclick="deleteRow(this,'Remnant','prpLremnantSerialNo');" name="deleteRemnantLoss" style="cursor: hand">
	  		</td>
	  	</tr>
	  </tbody>
	</table>
</span>
<table class=common cellpadding="0" cellspacing="1">
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="ChargeImg" onclick="showPage(this,spanRemnant);">
			<b>殘餘物訊息</b>
		</td>
	</tr>
</table>
<span id="spanRemnant">
	<table class=common cellpadding="1" cellspacing="1" id="Remnant">
		<thead>
			<tr>
				<td class="centertitle"	style="width: 5%;">序號</td>
				<td class="centertitle"	style="width: 90%;">殘餘物訊息</td>
				<td class="centertitle"	style="width: 5%;">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${remnantDto.prpLremnantList}" var="prpLremnant">
			  	<tr name="trRemnant">
			  		<td class="right" style="width: 5%;">
			  			<input type="text" name="prpLremnantSerialNo" value="${prpLremnant.id.serialNo}" class="readonly" readonly="readonly"/>
			  		</td>
			  		<td class="subformtitle" style="width: 90%;">
			  			<table class=common cellpadding="1" cellspacing="1">
							<tr>
								<td class="left">
									<s:text name='print.dangerPlan' />：
								</td>
								<%-- 出險險種 --%>
								<td class="input" style="width: 15%" style="align:center">
									<input type=text name="prpLremnantKindCode" style="width: 20%" title="<s:text name='certainLoss.thirdCarLoss.prpLcheckRaskType'/>" maxlength="23" class="codecode"
										ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="" value="${prpLremnant.kindCode}">
									<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
									<input type=text name="prpLremnantKindName" title="<s:text name='db.prpDrate.kindName'/>" maxlength="100" class="codecode" style="width: 66%"
										ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup="code_CodeSelect(this,'PolicyKindCode', '-1,0', 'Y', 'N', fm.prpLcompensatePolicyNo.value+ '|' + fm.damageStartDate.value + '|' + fm.damageStartHour.value);"
										onchange="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="" value="${prpLremnant.kindName}">
									<%-- <img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='prpLremnant.address' />：
								</td>
								<%-- 放置地點 --%>
								<td class="right">
									<input class="common" type=text name="prpLremnantAddress" value="${prpLremnant.address}">
									<%--<img src="/claim/images/bgMarkMustInput.jpg"> --%>
								</td>
								<td class="left"></td>
								<td class="right"></td>
							</tr>
							<tr>
								<td class="left">
									<s:text name='prpLremnant.generateDate' />：
								</td>
								<%-- 產生日期 --%>
								<td class="right">
									<rc:rcDate name="prpLremnantGenerateDate" style="width:90%;" value="${prpLremnant.generateDate}" />
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='db.prpLregist.estimateLoss' />：
								</td>
								<%-- 預估金額 --%>
								<td class="right">
									<input type=text name="prpLremnantEstimateAmount" class="common" onblur="checkNumber(this)" value="<fmt:formatNumber value='${prpLremnant.estimateAmount}' pattern='#'/>">
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='prpLremnant.auctionDate' />：
								</td>
								<%-- 拍賣日期 --%>
								<td class="right">
									<rc:rcDate name="prpLremnantAuctionDate" style="width:90%;" value="${prpLremnant.auctionDate}" />
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left">
									<s:text name='prpLremnant.auctionAmount' />：
								</td>
								<%-- 拍賣金額 --%>
								<td class="right">
									<input type=text name="prpLremnantAuctionAmount" title="拍賣金額" onblur="checkNumber(this)" maxlength="23" class="common" value="<fmt:formatNumber value='${prpLremnant.auctionAmount}' pattern='#'/>">
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='prpLremnant.backAmount' />：<%-- 失竊車返還額--%>
								</td>
								<td class="right">
									<input type=text name="prpLremnantBackAmount" class="common" onblur="checkNumber(this)" value="<fmt:formatNumber value='${prpLremnant.backAmount}' pattern='#'/>">
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='prpLremnant.realPay' />：
								</td>
								<%-- 實繳金額 --%>
								<td class="right">
									<input type=text name="prpLremnantRealPay" class="common" value="<fmt:formatNumber value='${prpLremnant.realPay}' pattern='#'/>" onblur="checkNumber(this)">
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left">
									幣別：
								</td>
								<td class="right">
									<s:select name="prpLremnantCurrency" value="#attr.prpLremnant.currency" list="#request.prpLpayObjectInfoCurrencyList" onchange="setExchRate(this);" listKey="key" listValue="value"></s:select>
								</td>
								<td class="left">
									匯率：
								</td>
								<td class="right">
									<input type=text name="prpLremnantExchRate" class="common" onblur="checkNumber(this)" value="${prpLremnant.exchRate }">
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									實際金額(NTD)：
								</td>
								<td class="right">
									<input type=text name="prpLremnantCurrencyPay" class="readonly" readonly="readonly" value="<fmt:formatNumber value='${prpLremnant.realPay*prpLremnant.exchRate}' pattern='#'/>"  >
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left">
									<s:text name='prpLremnant.shareDate' />：
								</td>
								<%-- 攤回日期 --%>
								<td class="right">
									<rc:rcDate name="prpLremnantShareDate" style="width:90%;" value="${prpLremnant.shareDate}" />
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='prpLremnant.handleCost' />：
								</td>
								<%-- 處理費用 --%>
								<td class="right">
									<input class="readonly" type=text name="prpLremnantHandleCost" value="<fmt:formatNumber value='${prpLremnant.handleCost}' pattern='#'/>" readonly="readonly" onblur="checkNumber(this)">
								<%-- <img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									收取對象訊息
								</td>
								<td class="right">
									<input name="prpLremnantPayObjectSerialNo" class="common" onclick="setPrpObjectinfoSerialNo(this);" value="${prpLremnant.payObjectSerialNo}" />
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left">
									<s:text name='prpLremnant.confirmorName' />：
								</td>
								<%-- 確認人 --%>
								<td class="right">
									<input class="common" type=text name="prpLremnantConfirmorName" value="${prpLremnant.confirmorName}">
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='db.prpLsalvation.verifyDate' />：
								</td>
								<%-- 確認日期 --%>
								<td class="right">
									<rc:rcDate name="prpLremnantConfirmDate" style="width:90%;" value="${prpLremnant.confirmDate}" />
									<%--			<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left">
									<s:text name='prpLremnant.remnants' />：
								</td>
								<%-- 殘餘物任務是否結束 --%>
								<td class="right">
									<select name="prpLremnantRemnants">
										<option value="1" <c:if test="${prpLremnant.remnants == '1'}">selected="selected"</c:if>>
											<s:text name='regist.prpLregist.yes' />
										</option>
										<option value="0" <c:if test="${prpLremnant.remnants == '0'}">selected="selected"</c:if>>
											<s:text name='regist.prpLregist.no' />
										</option>
									</select>
									<%--			 <img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
						</table>
					</td>
					<td class="right" style="width: 5%;">
						<input type="button" value="-" class=smallbutton onclick="deleteRow(this,'Remnant','prpLremnantSerialNo');" name="deleteRemnantLoss" style="cursor: hand">
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td class="left" style="width: 95%;" colspan="2"></td>
				<td class="right">
					<input type="button" value="+" class=smallbutton onclick="insertRow('Remnant',this,'prpLremnantSerialNo');" name="addRemnantLoss" style="cursor: hand">
				</td>
			</tr>
		</tfoot>
	</table>
</span>
<div id="prpLPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
	<ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
	</ul>
	<ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'prpLPayObjectinfo')"
			value="<s:text name='button.close.value' />" /></li>
	</ul>
</div>