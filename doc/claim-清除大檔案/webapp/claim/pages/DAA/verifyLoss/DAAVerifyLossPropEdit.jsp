<%--
****************************************************************************
* DESC       ：财产损失清单页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<span id="SpanProp" style="display: " cellspacing="1" cellpadding="0">
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class="centertitle" colspan="4">
				<s:text name="verifyLoss.insuranceInfo" />
			</td>
			<%--保险信息 --%>
		</tr>
		<tr>
			<td class="title" style="width: 15%">
				<s:text name="verifyLoss.insuredName" />:
			</td>
			<%--被保险人姓名 --%>
			<td class="input" style="width: 35%">
				<input type="text" name="prpLverifyLossInsuredNameShow" class="readonly" readonly="true" value="${prpLverifyLoss.insuredName}">
			</td>
			<td class="title" style="width: 15%">
				<s:text name="db.prpLlawsuit.licenseNo" />:
			</td>
			<%--号牌号码 --%>
			<td class="input" style="width: 35%">
				<input type=text name="prpLverifyLossLicenseNoShow" class="readonly" readonly="true" style="width: 140px" value="${prpLverifyLoss.licenseNo}">
			</td>
		</tr>
		<tr>
			<td class="title" style="width: 15%">
				<s:text name="db.prpLcomponent.currency" />:
			</td>
			<td class="input" style="width: 35%">
				<input type="text" name="prpLverifyLossCurrencyNameShow" class="readonly" readonly="true" style="width: 140px" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
				<input type="hidden" name="prpLverifyLossCurrencyShow" value="${prpLverifyLoss.currency}">
				<%--号牌底色 <s:text name="db.prpLlawsuit.licenseColorCode" />:
				<input type="text" name="prpLverifyLossLicenseColorShow" class="readonly" readonly="true" value="${prpLverifyLoss.licenseColor}">
				--%>
			</td>
			<td class="title" style="width: 15%">
				<s:text name="certainLoss.thirdCarLoss.carKind" />:
			</td>
			<%--车辆种类: --%>
			<td class="input" style="width: 35%">
				<input type=text name="prpLverifyLossCarKindShow" class="readonly" readonly="true" style="width: 140px" value="${prpLverifyLoss.carKind}">
			</td>
		</tr>
		<%--条款类别 <s:text name="db.prpLCItemCar.clauseType" />:
			<input type="text" name="prpLverifyLossClauseNameShow" class="readonly" readonly="true" value="${prpLverifyLoss.clauseName}">
		--%>
	</table>
	<span style="display: none">
		<table class="common" style="display: none" id="prop_Data" cellspacing="1" cellpadding="0">
			<tbody>
				<tr name="trPersonFeeLoss">
					<td class="input" style="width: 7%;">
						<input type="hidden" name="prpLpropSerialNo" description="序号">
						<input type="input" name="prpLpropKindCode" class="codecode" style='width: 100%;'
							ondblclick="code_CodeSelect(this,'PolicyKindCode');"
							onchange="code_CodeChange(this, 'PolicyKindCode');"
							onkeyup="code_CodeSelect(this,'PolicyKindCode');">
					</td>
					<td class="input" style="width: 23%;">
						<input type="input" name="prpLpropKindName" class="codename" style='width: 100%;'
							ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');"
							onchange="code_CodeChange(this, 'PolicyKindCode','-1','name','none','post');"
							onkeyup="code_CodeSelect(this, 'PolicyKindCode', '-1', 'always', 'none', 'post');">
					</td>
					<td class="input" style="width: 20%;">
						<input name="prpLpropLossItemName" class=common style='width: 100%;'>
					</td>
					<td class="input" style="width: 10%;">
						<s:select list="#request.FeeTypeCodeList" name="feeTypeCode" listKey="key" listValue="value" value="#request.prpLprop.feeTypeCode" style="width: 100%;"></s:select>
					</td>
					<td class="input" style="width: 7%;">
						<input name="prpLpropSumLoss" class=common style='width: 100%;'>
					</td>
					<td class="input" style="width: 7%;">
						<input name="prpLpropSumReject" class=common style='width: 100%;'>
					</td>
					<td class="input" style="width: 7%;">
						<input name="prpLpropSumDefLoss" class=common style='width: 100%;'>
					</td>
					<td class="input">
						<input name="prpLpropRemark" class=common style='width: 100%;'>
					</td>
					<input type="hidden" name="prpLpropItemKindNo">
					<input type="hidden" name="prpLpropFamilyNo">
					<input type="hidden" name="prpLpropFamilyName">
					<input type="hidden" name="prpLpropItemCode">
					<input type="hidden" name="prpLpropLossItemCode">
					<input type="hidden" name="prpLpropFeeTypeCode">
					<input type="hidden" name="prpLpropFeeTypeName">
					<input type="hidden" name="prpLpropCurrency" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
					<input type="hidden" name="prpLpropUnitPrice">
					<input type="hidden" name="prpLpropLossQuantity">
					<input type="hidden" name="prpLpropUnit">
					<input type="hidden" name="prpLpropBuyDate">
					<input type="hidden" name="prpLpropDepreRate">
					<input type="hidden" name="prpLpropRejectReason">
					<input type="hidden" name="prpLpropLossRate">
					<input type="hidden" name="prpLpropVeriUnitPrice">
					<input type="hidden" name="prpLpropVeriLossQuantity">
					<input type="hidden" name="prpLpropVeriUnit">
					<input type="hidden" name="prpLpropVeriDepreRate">
					<input type="hidden" name="prpLpropVeriRejectReason">
					<input type="hidden" name="prpLpropVeriLossRate">
					<input type="hidden" name="prpLpropFlag">
					<input type="hidden" name="prpLpropCompensateBackFlag">
				</tr>
				<tr name="trPersonFeeLoss">
					<td class="input" colspan="4" style="width: 60%">
						<s:text name="certainLoss.nuclearDamage" />：
						<%--核损意见 --%>
					</td>
					<td class="input" style="width: 7%">
						<input name="prpLpropVeriSumLoss" class=common  value="" style='width: 100%;'>
					</td>
					<td class="input" style="width: 7%">
						<input name="prpLpropVeriSumReject" class=common  value="" style='width: 100%;'>
					</td>
					<td class="input" style="width: 7%">
						<input name="prpLpropVeriSumDefLoss" class=common  value="" style='width: 100%;'>
					</td>
					<td class="input" >
						<input name="prpLpropVeriRemark" class=common  value="" style='width: 100%;'>
					</td>
				</tr>
			</tbody>
		</table>
	</span>
	<span id="spanProp" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
		<table class=common cellpadding="5" cellspacing="1" id="tableProp">
			<thead>
				<tr>
					<td class="centertitle" colspan=8>
						<s:text name="certainLoss.propertyLoss" />
						<%--财产核定损清单 --%>
					</td>
				</tr>
				<tr>
					<td class="centertitle" style="width: 7%">
						<s:text name="regist.prpLregist.kindCode" />
					</td>
					<%--险别代码 --%>
					<td class="centertitle" style="width: 20%">
						<s:text name="regist.prpLregist.kindName" />
					</td>
					<%--险别名称 --%>
					<td class="centertitle" style="width: 20%">
						<s:text name="certainLoss.lostProperty" />
					</td>
					<%--损失财产名称 --%>
					<td class="centertitle" style="width: 10%">
						<s:text name="db.prpLprop.feeTypeName" />
					</td>
					<%--费用名称 --%>
					<td class="centertitle" style="width: 7%">
						<s:text name="db.prpLpersonloss.sumLoss" />
					</td>
					<%--受损金额 --%>
					<td class="centertitle" style="width: 7%">
						<s:text name="db.prpLmedicine.sumReject" />
					</td>
					<%--剔除金额 --%>
					<td class="centertitle" style="width: 7%">
						<s:text name="compensate.amountNucDamage" />
					</td>
					<%--核损金额 --%>
					<td class="centertitle" >
						<s:text name="db.prpLcomponent.remark" />
					</td>
					<%--备注 --%>
				</tr>
			</thead>
			<tfoot>
			</tfoot>
			<tbody>
				<c:if test="${prpLprop.propList!=null}">
					<c:forEach items="${requestScope.prpLprop.propList}" var="prpLpropTemp">
						<%--增加理算退回的判断--%>
						<c:set var="compensatebackReadOnly" value="" scope="page" />
						<c:set var="compensatebackDiasable" value="" scope="page" />
						<c:set var="compensatebackStyle" value="" />
						<c:if test="${prpLpropTemp.compensateBackFlag =='1'}">
							<c:set var="compensatebackReadOnly" value="readOnly" />
							<c:set var="compensatebackDiasable" value="disabled" />
							<c:set var="compensatebackStyle" value="" />
						</c:if>
						<tr name="trPersonFeeLoss">
							<td class="input" style="width: 7%">
								<input type="hidden" name="prpLpropSerialNo" description="序号" value="${prpLpropTemp.id.serialNo}">
								<input type="input" name="prpLpropKindCode"  value="${prpLpropTemp.kindCode}" class="readonly" readonly="true" style="width: 100%;">
							</td>
							<td class="input" style="width: 23%">
								<input type="input" name="prpLpropKindName"  value="${prpLpropTemp.kindName}" class="readonly" readonly="true" style="width: 100%;">
							</td>
							<td class="input" style="width: 20%">
								<input name="prpLpropLossItemName" class="readonly" readonly="true"  value="${prpLpropTemp.lossItemName}" style="width: 100%;">
							</td>
							<td class="input" style="width: 10%">
								<input name="feeTypeCode" type="hidden"  value="${prpLpropTemp.feeTypeCode}">
								<input type="text" name="prpLpropFeeTypeName"  class="readonly" readonly="true" value="${prpLpropTemp.feeTypeName}">
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLpropSumLoss" class="readonly" readonly="true"  value="<fmt:formatNumber value='${prpLpropTemp.sumLoss}' pattern='#'/>" style="width: 100%;">
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLpropSumReject" class="readonly" readonly="true"  value="<fmt:formatNumber value='${prpLpropTemp.sumReject}' pattern='#'/>" style="width: 100%;">
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLpropSumDefLoss" class="readonly" readonly="true"  value="<fmt:formatNumber value='${prpLpropTemp.sumDefLoss}' pattern='#'/>" style="width: 100%;">
							</td>
							<td class="input">
								<input name="prpLpropRemark" class="readonly" readonly="true" value="${prpLpropTemp.remark}" style="width: 100%;">
							</td>
							<input type="hidden" name="prpLpropItemKindNo" value="${prpLpropTemp.itemKindNo}">
							<input type="hidden" name="prpLpropFamilyNo" value="${prpLpropTemp.familyNo}">
							<input type="hidden" name="prpLpropFamilyName" value="${prpLpropTemp.familyName}">
							<input type="hidden" name="prpLpropItemCode" value="${prpLpropTemp.itemCode}">
							<input type="hidden" name="prpLpropLossItemCode" value="${prpLpropTemp.lossItemCode}">
							<input type="hidden" name="prpLpropFeeTypeCode" value="${prpLpropTemp.feeTypeCode}">
							<input type="hidden" name="prpLpropCurrency" value="${prpLpropTemp.currency}">
							<input type="hidden" name="prpLpropUnitPrice" value="${prpLpropTemp.unitPrice}">
							<input type="hidden" name="prpLpropLossQuantity" value="${prpLpropTemp.lossQuantity}">
							<input type="hidden" name="prpLpropUnit" value="${prpLpropTemp.unit}">
							<input type="hidden" name="prpLpropBuyDate" value="${prpLpropTemp.buyDate}">
							<input type="hidden" name="prpLpropDepreRate" value="${prpLpropTemp.depreRate}">
							<input type="hidden" name="prpLpropRejectReason" value="${prpLpropTemp.rejectReason}">
							<input type="hidden" name="prpLpropLossRate" value="${prpLpropTemp.lossRate}">
							<input type="hidden" name="prpLpropVeriUnitPrice" value="${prpLpropTemp.veriUnitPrice}">
							<input type="hidden" name="prpLpropVeriLossQuantity" value="${prpLpropTemp.veriLossQuantity}">
							<input type="hidden" name="prpLpropVeriUnit" value="${prpLpropTemp.veriUnit}">
							<input type="hidden" name="prpLpropVeriDepreRate" value="${prpLpropTemp.veriDepreRate}">
							<input type="hidden" name="prpLpropVeriRejectReason" value="${prpLpropTemp.veriRejectReason}">
							<input type="hidden" name="prpLpropVeriLossRate" value="${prpLpropTemp.veriLossRate}">
							<input type="hidden" name="prpLpropFlag" value="${prpLpropTemp.flag}">
							<input type="hidden" name="prpLpropCompensateBackFlag" value="${prpLpropTemp.compensateBackFlag}">
						</tr>
						<tr name="trPersonFeeLoss">
							<td class="input" colspan="4" style="width: 60%;">
								<s:text name="certainLoss.nuclearDamage" />：
								<%--核损意见 --%>
							</td>
							<td class="input" style="width: 7%;">
								<input name="prpLpropVeriSumLoss" class=common  value="<fmt:formatNumber value="${prpLpropTemp.veriSumLoss}" pattern="#"/>" onblur="calSumPropVeriDefLoss(this);" ${compensatebackReadOnly } style="width: 100%;">
							</td>
							<td class="input" style="width: 7%;">
								<input name="prpLpropVeriSumReject" class=common  value="<fmt:formatNumber value="${prpLpropTemp.veriSumReject}" pattern="#"/>" onblur="calSumPropVeriDefLoss(this);" ${compensatebackReadOnly } style="width: 100%;">
							</td>
							<td class="input" style="width: 7%;">
								<input name="prpLpropVeriSumDefLoss" class=common  value="<fmt:formatNumber value="${prpLpropTemp.veriSumDefLoss}" pattern="#"/>" onblur="calSumPropVeriDefLoss(this);" ${compensatebackReadOnly } style="width: 100%;">
							</td>
							<td class="input">
								<input name="prpLpropVeriRemark" class=common  value="${prpLpropTemp.veriRemark}" ${compensatebackReadOnly } style="width: 100%;">
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</span>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class='title' width="33%">
				<s:text name="certainLoss.damageTotals" />:
				<%--受损金额合计 --%>
				<input class='readonly' readonly="true" style='width: 80px' name='prpLpropSumSumLoss'>
			</td>
			<td class='title' width="33%">
				<s:text name="certainLoss.removeTotals" />:
				<%--剔除金额合计 --%>
				<input class='readonly' readonly="true" style='width: 80px' name='prpLpropSumSumReject'>
			</td>
			<td class='title' width="33%">
				<s:text name="certainLoss.lossTotal" />:
				<%--定损金额合计 --%>
				<input class='readonly' readonly="true" style='width: 80px' name='prpLpropSumSumDefLoss'>
			</td>
		</tr>
	</table>
</span>
