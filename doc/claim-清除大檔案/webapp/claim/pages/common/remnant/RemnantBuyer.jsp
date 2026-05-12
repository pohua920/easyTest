<%@ include file="/common/taglibs.jsp"%>
<span style="display: none;">
	<table class=common cellpadding="1" cellspacing="1" id="Buyer_Data">
		<tbody>
			<tr name="trBuyer">
				<td class="right" style="width: 5%;">
					<input type="text" name="prpLbuyerSerialNo" value="0" class="readonly" readonly="readonly"/>
				</td>
				<td class="subformtitle" style="width: 90%;">
					<table class=common cellpadding="1" cellspacing="1">
						<tr>
							<td class="left" style="width: 12%;">
								<s:text name='prpLbuyer.buyerName' />：
							</td>
							<%-- 買受人--%>
							<td class="right" style="width: 21%;">
								<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
								<input type=text name="prplbuyerBuyerName" title="<s:text name='prpLbuyer.buyerName'/>" maxlength="100" class="common" value="">
								<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
							</td>
							<td class="left" rowspan="4" style="width: 12%;">
								<s:text name='certify.instructe' />：
							</td>
							<%-- 說明 --%>
							<td class="right" rowspan="4" style="width: 54%;">
								<textarea name="prplbuyerExplanation" style="wrap: hard;" rows="6" cols="80"></textarea>
								<%--	<img src="/claim/images/bgMarkMustInput.jpg"> --%>
							</td>
						</tr>
						<tr>
							<td class="left" style="width: 12%;">
								<s:text name='prpLbuyer.uniformNo' />：
							</td>
							<%-- 統一編號 --%>
							<td class="right" style="width: 21%;">
								<input class="common" type=text name="prplbuyerUniformNo" value="">
								<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
							</td>
						</tr>
						<tr>
							<td class="left" style="width: 12%;">
								<s:text name='prpLbuyer.address' />：
							</td>
							<%-- 住址--%>
							<td class="right" style="width: 21%;">
								<input type=text name="prplbuyerAddress" title="<s:text name='prpLbuyer.address'/>"  class="common" value="">
								<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
							</td>
						</tr>
						<tr>
							<td class="left" style="width: 12%;">
								<s:text name='db.prpLregist.phoneNumber' />：
							</td>
							<%-- 聯繫電話 --%>
							<td class="right" style="width: 21%;">
								<input type=text name="prplbuyerLinkPhone" class="common" value="">
								<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
							</td>
						</tr>
					</table>
				</td>
				<td class="right" style="width: 5%;">
					<input type="button" value="-" class=smallbutton onclick="deleteRow(this,'Buyer','prpLbuyerSerialNo');" name="deleteBuyer" style="cursor: hand">
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class=common cellpadding="0" cellspacing="1" >
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="ChargeImg" onclick="showPage(this,spanBuyer);">
			<b><s:text name='prplbuyer.info' /></b>
			<%--買受人訊息 --%>
		</td>
	</tr>
	<tr>
</table>
<span id="spanBuyer" >
	<table class=common cellpadding="1" cellspacing="1" id="Buyer">
		<thead>
			<tr>
				<td class="centertitle"  style="width: 5%;">序號</td>
				<td class="centertitle"  style="width: 90%;"><s:text name='prplbuyer.info' /><%--買受人訊息 --%></td>
				<td class="centertitle"  style="width: 5%;">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${remnantDto.prpLbuyerList}" var="prpLbuyer">
				<tr name="trBuyer">
					<td class="right" style="width: 5%;">
						<input type="text" name="prpLbuyerSerialNo" value="${prpLbuyer.id.serialNo}" class="readonly" readonly="readonly"/>
					</td>
					<td class="subformtitle" style="width: 90%;">
						<table class=common cellpadding="1" cellspacing="1">
							<tr>
								<td class="left" style="width: 12%;">
									<s:text name='prpLbuyer.buyerName' />：
								</td>
								<%-- 買受人--%>
								<td class="right" style="width: 21%;">
									<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
									<input type=text name="prplbuyerBuyerName" title="<s:text name='prpLbuyer.buyerName'/>" maxlength="100" class="common" value="${prpLbuyer.buyerName}">
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
								<td class="left" rowspan="4" style="width: 12%;">
									<s:text name='certify.instructe' />：
								</td>
								<%-- 說明 --%>
								<td class="right" rowspan="4" style="width: 54%;">
									<textarea name="prplbuyerExplanation" style="wrap: hard;" rows="6" cols="80">${prpLbuyer.explanation}</textarea>
									<%--	<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left" style="width: 12%;">
									<s:text name='prpLbuyer.uniformNo' />：
								</td>
								<%-- 統一編號 --%>
								<td class="right" style="width: 21%;">
									<input class="common" type=text name="prplbuyerUniformNo" value="${prpLbuyer.uniformNo}">
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left" style="width: 12%;">
									<s:text name='prpLbuyer.address' />：
								</td>
								<%-- 住址--%>
								<td class="right" style="width: 21%;">
									<input type=text name="prplbuyerAddress" title="<s:text name='prpLbuyer.address'/>" maxlength="23" class="common" value="${prpLbuyer.address}">
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
							<tr>
								<td class="left" style="width: 12%;">
									<s:text name='db.prpLregist.phoneNumber' />：
								</td>
								<%-- 聯繫電話 --%>
								<td class="right" style="width: 21%;">
									<input type=text name="prplbuyerLinkPhone" class="common" value="${prpLbuyer.linkPhone}">
									<%--<img src="/claim/images/bgMarkMustInput.jpg">--%>
								</td>
							</tr>
						</table>
					</td>
					<td class="left" style="width: 5%;">
						<input type="button" value="-" class=smallbutton onclick="deleteRow(this,'Buyer','prpLbuyerSerialNo');" name="deleteBuyer" style="cursor: hand">
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<td class="left" style="width: 95%;" colspan="2"></td>
			<td class="right">
				<input type="button" value="+" class=smallbutton onclick="insertRow('Buyer',this,'prpLbuyerSerialNo');" name="addBuyer" style="cursor: hand">
			</td>
		</tfoot>
	</table>
</span>