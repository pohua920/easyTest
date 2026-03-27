<%--
****************************************************************************
* DESC       ：财产损失清单页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-03-13 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<!--建立显示的录入条，可以收缩显示的-->
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法
	/*
	  插入一条新的之後的处理（可选方法）
	 */
	function afterInsertProp() {
		setPrpLpropSerialNo();
	}

	/* 
	  删除本条WarnRegion之後的处理（可选方法）
	 */
	function afterDeleteProp(field) {
		setPrpLpropSerialNo();
	}

	/**
	 * 设置setPrpLpropSerialNo
	 */
	function setPrpLpropSerialNo() {
		var count = getElementCount("prpLpropSerialNo");
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i); 
			if (count != 1) {
				fm.prpLpropSerialNo[i].value = i;
			}
		}
	}
</script>
<span id="SpanProp" style="display: " cellspacing="1" cellpadding="0">
	<span style="display: none">
		<table class="common" style="display: none" id="Prop_Data" ellpadding="5" cellspacing="1">
			<tbody>
				<tr>
					<c:choose>
						<c:when test="${requestScope.prpLregistRPolicyNo!=null}">
							<td class="common">
								<input type="hidden" name="prpLpropSerialNo" description="序号">
								<input type="input" name="prpLpropKindCode" class="codecode" style="width: 100%"
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="code_CodeChange(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="common">
								<input type="input" name="prpLpropKindName" class="codename" style="width: 100%"
									ondblclick="code_CodeSelect(this, 'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="code_CodeChange(this, 'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
						</c:when>
						<c:otherwise>
							<td class="common">
								<input type="hidden" name="prpLpropSerialNo" description="序号">
								<input type="input" name="prpLpropKindCode" class="codecode" style="width: 100%"
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="code_CodeChange(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="common">
								<input type="input" name="prpLpropKindName" class="codename" style="width: 100%"
									ondblclick="code_CodeSelect(this, 'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="code_CodeChange(this, 'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
						</c:otherwise>
					</c:choose>
					<td class="common">
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input name="prpLpropLossItemName" class=common style="width: 100%" maxlength="100">
					</td>
					<td class="common">
						<s:select name="feeTypeCode" style="width: 100%" list="#request.FeeTypeCodeList" listKey="key" listValue="value"></s:select>
					</td>
					<td class="common">
						<input name="prpLpropSumLoss" class=common style="width: 100%" maxlength=40 onBlur="getSumPropDefLoss(this);getNewSum();">
					</td>
					<td class="common">
						<input name="prpLpropSumReject" class=common style="width: 100%" maxlength=40 onBlur="getSumPropDefLoss(this);getNewSum();">
					</td>
					<td class="common">
						<input name="prpLpropSumDefLoss" class=readonly readonly style="width: 100%" maxlength=40 onBlur="getNewSum();">
					</td>
					<td class="common">
						<input name="prpLpropRemark" class=common style="width: 100%" maxlength=40>
					</td>
					<td class="input" style='width: 4%' align="center">
						<div>
							<input type=button name="buttonPropDelete" class="smallbutton" onclick="directDeleteRow(this,'Prop',1,2);getNewSum()" value="-" style="cursor: hand">
						</div>
						<input type="hidden" name="prpLpropItemKindNo">
						<input type="hidden" name="prpLpropFamilyNo">
						<input type="hidden" name="prpLpropFamilyName">
						<input type="hidden" name="prpLpropItemCode">
						<input type="hidden" name="prpLpropLossItemCode">
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
					</td>
				</tr>
				<tr style="display: none;">
					<td class="input" colspan="4">
						<s:text name="certainLoss.nuclearDamage" />：
						<%--核损意见 --%>
					</td>
					<td class="common">
						<input name="prpLpropVeriSumLoss" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumLoss}" pattern="#"/>">
					</td>
					<td class="common">
						<input name="prpLpropVeriSumReject" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumReject}" pattern="#"/>">
					</td>
					<td class="common">
						<input name="prpLpropVeriSumDefLoss" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumDefLoss}" pattern="#"/>">
					</td>
					<td class="common">
						<input name="prpLpropVeriRemark" class="readonly" readonly style="width: 100%" maxlength=40 value="<c:out value='${prpLprop1.veriRemark}'/>">
					</td>
					<td class="input" style='width: 4%' align="center">
						&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
	</span>
	<span id="spanProp" cellspacing="1" cellspacing="0" width="100%">
		<%-- 多行输入展现域 --%>
		<table class="common" id="Prop" cellpadding="5" cellspacing="1">
			<thead>
				<tr>
					<td class="subformtitle" colspan=9 width="100%">
						<s:text name="certainLoss.propertyLoss" />
						<!--财产核定损清单-->
					</td>
				</tr>
				<tr>
					<td class="centertitle" style="width: 8%">
						<s:text name="db.prpLcomponent.kindCode" />
						<!--险别代码-->
					</td>
					<td class="centertitle" style="width: 24%">
						<s:text name="db.prpLCitemKind.kindName" />
						<!--险别名称-->
					</td>
					<td class="centertitle" style="width: 12%">
						<s:text name="certainLoss.lostProperty" />
						<!--损失财产名称-->
					</td>
					<td class="centertitle" style="width: 8%">
						<s:text name="db.prpLprop.feeTypeName" />
						<!--费用名称-->
					</td>
					<td class="centertitle" style="width: 10%">
						<s:text name="db.prpLloss.sumLoss" />
						<!--受损金额-->
					</td>
					<td class="centertitle" style="width: 10%">
						<s:text name="db.prpLpersonloss.sumRest" />
						<!--剔除金额-->
					</td>
					<td class="centertitle" style="width: 10%">
						<s:text name="certainLoss.lossAmount" />
						<!--定损金额-->
					</td>
					<td class="centertitle" style="width: 14%">
						<s:text name="db.prpLcomponent.remark" />
						<!--备注-->
					</td>
					<td class="title" style="width: 4%"></td>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td class="title" colspan=8 style="width: 96%">
						<s:text name="prompt.certainLoss.addRemoveProperty" />
						<!--(按"+"号键增加财产核定损信息，按"-"号键删除信息)-->
					</td>
					<td class="title" align="right" style="width: 4%">
						<div align="center">
							<input type="button" value="+" class=smallbutton onclick="insertRow('Prop')" name="buttonDriverInsert" style="cursor: hand">
						</div>
					</td>
				</tr>
			</tfoot>
			<tbody>
				<%--
					如果是由理算退回的，那么这行记录就应该显示得是只读的
				--%>
				<c:if test="${not empty requestScope.prpLprop.propList}">
					<c:forEach items="${requestScope.prpLprop.propList}" var="prpLprop1">
						<c:set var="compensatebackReadOnly" value="" scope="page" />
						<c:set var="compensatebackDiasable" value="" scope="page" />
						<c:set var="compensatebackStyle" value="" scope="page" />
						<%--退回财产定损 --%>
						<c:if test="${param.status=='3'}">
							<c:set var="compensatebackDiasable" value="disabled" scope="page" />
						</c:if>
						<c:if test="${prpLprop1.compensateBackFlag=='1'}">
							<c:set var="compensatebackReadOnly" value="readOnly" scope="page" />
							<c:set var="compensatebackDiasable" value="disabled" scope="page" />
							<c:set var="compensatebackStyle" value="" scope="page" />
						</c:if>
						<c:choose>
							<c:when test="${empty param.flag}">
								<tr>
									<input type="hidden" name="prpLpropSerialNo" description="序号" value="<c:out value='${prpLprop1.id.serialNo}'/>">
									<c:choose>
										<c:when test="${requestScope.prpLregistRPolicyNo !=null}">
											<td class="common" style="width: 8%">
												<input type="input" name="prpLpropKindCode" class="codecode" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindCode}'/>"
												<c:if test="${compensatebackReadOnly=='' }">
													ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onchange="code_CodeChange(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
												</c:if>>
											</td>
											<td class="common" style="width: 24%">
												<input type="input" name="prpLpropKindName" class="codename" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindName}'/>"
												<c:if test="${compensatebackReadOnly=='' }">
													ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onchange="code_CodeChange(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												</c:if>>
											</td>
										</c:when>
										<c:otherwise>
											<td class="common" style="width: 8%">
												<input type="common" name="prpLpropKindCode" class="codecode"
													style='width:100%${compensatebackStyle }'
													${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindCode}'/>"
													<c:if test="${compensatebackReadOnly=='' }">
														ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="code_CodeChange(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													</c:if>>
											</td>
											<td class="common" style="width: 24%">
												<input type="input" name="prpLpropKindName" class="codename" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindName}'/>"
													<c:if test="${compensatebackReadOnly=='' }">
														ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="code_CodeChange(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
													</c:if>>
											</td>
										</c:otherwise>
									</c:choose>
									<td class="input" style="width: 12%">
										<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
										<input name="prpLpropLossItemName" class=common style='width:100%${compensatebackStyle }' maxlength="100" ${compensatebackReadOnly } value="<c:out value='${prpLprop1.lossItemName}'/>">
									</td>
									<td class="input" style="width: 8%">
									<select style="width: 100%" name="feeTypeCode">
										<c:forEach items="${requestScope.FeeTypeCodeList}" var="prpDcode">
											<option value="${prpDcode.key}"
												<c:if test="${prpDcode.key==prpLprop1.feeTypeCode}"> selected="selected"</c:if>>
												<c:out value="${prpDcode.value}" />
											</option>
										</c:forEach>
									</select>
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLpropSumLoss" class=common style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly }
											value="<fmt:formatNumber value='${prpLprop1.sumLoss}' pattern='#'/>" onBlur="getSumPropDefLoss(this);getNewSum();">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLpropSumReject" class=common style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly }
											value="<fmt:formatNumber value='${prpLprop1.sumReject}' pattern='#'/>" onBlur="getSumPropDefLoss(this);getNewSum();">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLpropSumDefLoss" class=readonly readonly style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } maxlength=40
											value="<fmt:formatNumber value='${prpLprop1.sumDefLoss}' pattern='#'/>" onBlur="getNewSum();">
									</td>
									<td class="input" style="width: 14%">
										<input name="prpLpropRemark" class=common style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly } value="<c:out value='${prpLprop1.remark}'/>">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonPropDelete" class=smallbutton onclick="directDeleteRow(this,'Prop',1,2);getNewSum()" ${compensatebackDiasable } value="-" style="cursor: hand">
										</div>
										<input type="hidden" name="prpLpropItemKindNo" value="<c:out value='${prpLprop1.itemKindNo}'/>">
										<input type="hidden" name="prpLpropFamilyNo" value="<c:out value='${prpLprop1.familyNo}'/>">
										<input type="hidden" name="prpLpropFamilyName" value="<c:out value='${prpLprop1.familyName}'/>">
										<input type="hidden" name="prpLpropItemCode" value="<c:out value='${prpLprop1.itemCode}'/>">
										<input type="hidden" name="prpLpropLossItemCode" value="<c:out value='${prpLprop1.lossItemCode}'/>">
										<input type="hidden" name="prpLpropCurrency" value="<c:out value='${prpLprop1.currency}'/>">
										<input type="hidden" name="prpLpropUnitPrice" value="<c:out value='${prpLprop1.unitPrice}'/>">
										<input type="hidden" name="prpLpropLossQuantity" value="<c:out value='${prpLprop1.lossQuantity}'/>">
										<input type="hidden" name="prpLpropUnit" value="<c:out value='${prpLprop1.unit}'/>">
										<input type="hidden" name="prpLpropBuyDate" value="<c:out value='${prpLprop1.buyDate}'/>">
										<input type="hidden" name="prpLpropDepreRate" value="<c:out value='${prpLprop1.depreRate}'/>">
										<input type="hidden" name="prpLpropRejectReason" value="<c:out value='${prpLprop1.rejectReason}'/>">
										<input type="hidden" name="prpLpropLossRate" value="<c:out value='${prpLprop1.lossRate}'/>">
										<input type="hidden" name="prpLpropVeriUnitPrice" value="<c:out value='${prpLprop1.veriUnitPrice}'/>">
										<input type="hidden" name="prpLpropVeriLossQuantity" value="<c:out value='${prpLprop1.veriLossQuantity}'/>">
										<input type="hidden" name="prpLpropVeriUnit" value="<c:out value='${prpLprop1.veriUnit}'/>">
										<input type="hidden" name="prpLpropVeriDepreRate" value="<c:out value='${prpLprop1.veriDepreRate}'/>">
										<input type="hidden" name="prpLpropVeriRejectReason" value="<c:out value='${prpLprop1.veriRejectReason}'/>">
										<input type="hidden" name="prpLpropVeriLossRate" value="<c:out value='${prpLprop1.veriLossRate}'/>">
										<input type="hidden" name="prpLpropFlag" value="<c:out value='${prpLprop1.flag}'/>">
										<input type="hidden" name="prpLpropCompensateBackFlag" value="<c:out value='${prpLprop1.compensateBackFlag}'/>">
									</td>
								</tr>
								<tr <c:if test="${param.status !='3'}">style="display: none;"</c:if>>
									<td class="input" colspan="4">
										<s:text name="certainLoss.nuclearDamage" />：
										<%--核损意见 --%>
									</td>
									<td class="common">
										<input name="prpLpropVeriSumLoss" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumLoss}" pattern="#"/>">
									</td>
									<td class="common">
										<input name="prpLpropVeriSumReject" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumReject}" pattern="#"/>">
									</td>
									<td class="common">
										<input name="prpLpropVeriSumDefLoss" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumDefLoss}" pattern="#"/>">
									</td>
									<td class="common">
										<input name="prpLpropVeriRemark" class="readonly" readonly style="width: 100%" maxlength=40 value="<c:out value='${prpLprop1.veriRemark}'/>">
									</td>
									<td class="input" style='width: 4%' align="center">
										&nbsp;
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<input type="hidden" name="prpLpropSerialNo" description="序号" value="<c:out value='${prpLprop1.id.serialNo}'/>">
									<c:choose>
										<c:when test="${requestScope.prpLregistRPolicyNo!=null}">
											<td class="input" style="width: 8%">
												<input type="input" name="prpLpropKindCode" class="codecode" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindCode}'/>"
													<c:if test="${compensatebackReadOnly=='' }">
														ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','0，1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="code_CodeChange(this,'PolicyKindCodeForProp','0，1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','0，1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
													</c:if>>
											</td>
											<td class="input" style="width: 24%">
												<input type="input" name="prpLpropKindName" class="codename" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindName}'/>"
													<c:if test="${compensatebackReadOnly=='' }">
														ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="code_CodeChange(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"	
													</c:if>>
											</td>
										</c:when>
										<c:otherwise>
											<td class="input" style="width: 8%">
												<input type="input" name="prpLpropKindCode" class="codecode" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindCode}'/>"
												<c:if test="${compensatebackReadOnly=='' }">
													ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','0，1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onchange="code_CodeChange(this,'PolicyKindCodeForProp','0，1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
													onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','0，1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												</c:if>>
											</td>
											<td class="input" style="width: 24%">
												<input type="input" name="prpLpropKindName" class="codename" style='width:100%${compensatebackStyle }' ${compensatebackReadOnly } value="<c:out value='${prpLprop1.kindName}'/>"
													<c:if test="${compensatebackReadOnly=='' }">
														ondblclick="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="code_CodeChange(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="code_CodeSelect(this,'PolicyKindCodeForProp','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
													</c:if>>
											</td>
										</c:otherwise>
									</c:choose>
									<td class="input" style="width: 12%">
										<input name="prpLpropLossItemName" class=common
											style='width:100%${compensatebackStyle }' maxlength=40
											${compensatebackReadOnly } value="<c:out value='${prpLprop1.lossItemName}'/>">
									</td>
									<td class="input" style="width: 8%">
										<select style="width: 100%" name="feeTypeCode">
											<c:forEach items="${requestScope.FeeTypeCodeList}" var="prpDcode">
												<option value="${prpDcode.key}"
													<c:if test="${prpDcode.key==prpLprop1.feeTypeCode}"> selected="selected"</c:if>>
													<c:out value="${prpDcode.value}" />
												</option>
											</c:forEach>
										</select>
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLpropSumLoss" class=common style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly }
											value="<fmt:formatNumber value='${prpLprop1.sumLoss}' pattern='#'/>" onBlur="getSumPropDefLoss(this);getNewSum();">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLpropSumReject" class=common style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly }
											value="<fmt:formatNumber value='${prpLprop1.sumReject}' pattern='#'/>" onBlur="getSumPropDefLoss(this);getNewSum();">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLpropSumDefLoss" class=readonly readonly style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly }
											value="<fmt:formatNumber value='${prpLprop1.sumDefLoss}' pattern='#'/>" onBlur="getNewSum();">
									</td>
									<td class="input" style="width: 14%">
										<input name="prpLpropRemark" class=common style='width:100%${compensatebackStyle }' maxlength=40 ${compensatebackReadOnly } value="<c:out value='${prpLprop1.remark}'/>">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonPropDelete" class=smallbutton ${compensatebackDiasable } onclick="directDeleteRow(this,'Prop',1,2);;getNewSum()" value="-" style="cursor: hand">
										</div>
										<input type="hidden" name="prpLpropItemKindNo" value="<c:out value='${prpLprop1.itemKindNo}'/>">
										<input type="hidden" name="prpLpropFamilyNo" value="<c:out value='${prpLprop1.familyNo}'/>">
										<input type="hidden" name="prpLpropFamilyName" value="<c:out value='${prpLprop1.familyName}'/>">
										<input type="hidden" name="prpLpropItemCode" value="<c:out value='${prpLprop1.itemCode}'/>">
										<input type="hidden" name="prpLpropLossItemCode" value="<c:out value='${prpLprop1.lossItemCode}'/>">
										<input type="hidden" name="prpLpropCurrency" value="<c:out value='${prpLprop1.currency}'/>">
										<input type="hidden" name="prpLpropUnitPrice" value="<c:out value='${prpLprop1.unitPrice}'/>">
										<input type="hidden" name="prpLpropLossQuantity" value="<c:out value='${prpLprop1.lossQuantity}'/>">
										<input type="hidden" name="prpLpropUnit" value="<c:out value='${prpLprop1.unit}'/>">
										<input type="hidden" name="prpLpropBuyDate" value="<c:out value='${prpLprop1.buyDate}'/>">
										<input type="hidden" name="prpLpropDepreRate" value="<c:out value='${prpLprop1.depreRate}'/>">
										<input type="hidden" name="prpLpropRejectReason" value="<c:out value='${prpLprop1.rejectReason}'/>">
										<input type="hidden" name="prpLpropLossRate" value="<c:out value='${prpLprop1.lossRate}'/>">
										<input type="hidden" name="prpLpropVeriUnitPrice" value="<c:out value='${prpLprop1.veriUnitPrice}'/>">
										<input type="hidden" name="prpLpropVeriLossQuantity" value="<c:out value='${prpLprop1.veriLossQuantity}'/>">
										<input type="hidden" name="prpLpropVeriUnit" value="<c:out value='${prpLprop1.veriUnit}'/>">
										<input type="hidden" name="prpLpropVeriDepreRate" value="<c:out value='${prpLprop1.veriDepreRate}'/>">
										<input type="hidden" name="prpLpropVeriSumLoss" value="<c:out value='${prpLprop1.veriSumLoss}'/>">
										<input type="hidden" name="prpLpropVeriSumReject" value="<c:out value='${prpLprop1.veriSumReject}'/>">
										<input type="hidden" name="prpLpropVeriRejectReason" value="<c:out value='${prpLprop1.veriRejectReason}'/>">
										<input type="hidden" name="prpLpropVeriLossRate" value="<c:out value='${prpLprop1.veriLossRate}'/>">
										<input type="hidden" name="prpLpropVeriSumDefLoss" value="<c:out value='${prpLprop1.veriSumDefLoss}'/>">
										<input type="hidden" name="prpLpropFlag" value="<c:out value='${prpLprop1.flag}'/>">
										<input type="hidden" name="prpLpropCompensateBackFlag" value="<c:out value='${prpLprop1.compensateBackFlag}'/>">
									</td>
								</tr>
								<tr>
									<td class="input" colspan="4">
										<s:text name="certainLoss.nuclearDamage" />：
										<%--核损意见 --%>
									</td>
									<td class="common">
										<input name="prpLpropVeriSumLoss" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumLoss}" pattern="#"/>">
									</td>
									<td class="common">
										<input name="prpLpropVeriSumReject" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumReject}" pattern="#"/>">
									</td>
									<td class="common">
										<input name="prpLpropVeriSumDefLoss" class="readonly" readonly style="width: 100%" maxlength=40 value="<fmt:formatNumber value="${prpLprop1.veriSumDefLoss}" pattern="#"/>">
									</td>
									<td class="common">
										<input name="prpLpropVeriRemark" class="readonly" readonly style="width: 100%" maxlength=40 value="<c:out value='${prpLprop1.veriRemark}'/>">
									</td>
									<td class="input" style='width: 4%' align="center">
										&nbsp;
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</span>
	<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" width="100%">
		<tr>
			<td class='title' width="33%">
				<s:text name="certainLoss.damageTotals" />:
				<!--受损金额合计-->
				<input class='readonly' readonly="true" style="width: 20%" name='prpLpropSumSumLoss'>
			</td>
			<td class='title' width="33%">
				<s:text name="certainLoss.removeTotals" />:
				<!--剔除金额合计-->
				<input class='readonly' readonly="true" style="width: 20%" name='prpLpropSumSumReject'>
			</td>
			<td class='title' width="33%">
				<s:text name="certainLoss.lossTotal" />:
				<!--定损金额合计-->
				<input class='readonly' readonly="true" style="width: 20%" name='prpLpropSumSumDefLoss'>
			</td>
		</tr>
	</table>
</span>