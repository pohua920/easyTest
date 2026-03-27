<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function ownerShip_object(field) {
	var i = getElementOrder(field) - 1;
	if (field.value == "B") {
		document.getElementsByName("cutBack1")[i].style.display = "none";
		document.getElementsByName("cutBack2")[i].style.display = "none";
		//document.getElementsByName("prpLpayObjectInfobank1")[i].style.display = "";
		//document.getElementsByName("prpLpayObjectInfobank2")[i].style.display = "";
	} else if (field.value == "Q") {
		document.getElementsByName("cutBack1")[i].style.display = "";
		document.getElementsByName("cutBack2")[i].style.display = "";
		//document.getElementsByName("prpLpayObjectInfobank1")[i].style.display = "none";
		//document.getElementsByName("prpLpayObjectInfobank2")[i].style.display = "none";
	}
}
//删除一条数据后执行的操作
function afterDeletePayObjectInfo(deletObject,btnField,pageCode,csFieldName,psFieldName){
	var index = $(deletObject).find(":input[name='prpLpayObjectInfoSerialNo']").val();
	index = parseInt(index);
	$.each($.find("input[name='prpLremnantPayObjectSerialNo']"), function (i, n) {
 		if (i > 0 && n.value != "") {
 			var payObjectValue = n.value.split(";");
 			var payObjectValueTemp = "";
 			for (var i = 0; i < payObjectValue.length; i++) {
 				var payObjectTemp = payObjectValue[i].split(":");
 				if (index < parseInt(payObjectTemp[0])) {
 					payObjectValueTemp += (parseInt(payObjectTemp[0]) - 1) + ":" + payObjectTemp[1] + ";";
 				} else if (index > parseInt(payObjectTemp[0])) {
 					payObjectValueTemp += payObjectValue[i] + ";";
 				}
 			}
 			if (payObjectValueTemp != "") {
 				payObjectValueTemp = payObjectValueTemp.substring(0, payObjectValueTemp.length - 1);
 			}
 			n.value = payObjectValueTemp;
 		}
 	});
	uLprpLPayObjectinfo();
}
</script>
<%--支付对象讯息 --%>
<span style="display: none">
	<table class="common" style="width: 100%" id="PayObjectInfo_Data">
		<tbody>
			<tr name="trPrpLpayObjectInfo">
				<td class="subformtitle" style="width: 96%">
					<table class="common" style="width: 100%">
						<tr>
							<td class="title" colspan="6">
								<b>收取對象&nbsp;<input name="prpLpayObjectInfoSerialNo" value="" readOnly class="readonly"></b>
							</td>
						</tr>
						<tr>
							<td class="title" style="width: 12%">
								<s:text name='prpLremnant.payAmount' />：
							</td>
							<td class="input" style="width: 20%">
								<input type="text" name="prpLpayObjectInfoPayAmount" class="readonly" readonly="readonly" value="0">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="title" style="width: 12%">
								<s:text name='prpLremnant.remnantCostList' />：
							</td>
							<td class="input" style="width: 20%">
								<s:select list="#request.remnantCostList" name="prpLpayObjectInfoPaymentKind" listKey="key" listValue="value" value="" />
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="title" style="width: 12%"></td>
							<td class="input" style="width: 20%"></td>
						</tr>
						<tr>
							<td class="title" style="width: 12%">
								<s:text name='prpLpayObjectInfo.ownerShip' />：
							</td>
							<td class="input" style="width: 20%">
								<select name="prpLpayObjectInfoOwnerShip" style="width: 50%" onchange="ownerShip_object(this)">
									<option value="B" selected="selected"><s:text name="compensate.remittance" /><!-- 汇款 --></option>
									<option value="C"><s:text name="compensate.agentInfo.cash" /><!-- 现金 --></option>
									<option value="Q"><s:text name="compensate.agentInfo.cheque" /><!-- 支票 --></option>
								</select>
							</td>
							<td class="title" style="width: 12%">證件類型：</td>
							<td class="input" style="width: 20%">
								<s:select name="prpLpayObjectInfoCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
							</td>
							<td class="title" style="width: 12%">受款人電話：</td>
							<td class="input" style="width: 20%">
								<input type="text" name="prpLpayObjectInfoBeneficiaryPhone" class="input" >
							</td>
						</tr>
						<tr>
							<td class="title" style="width: 12%"><s:text name="replevy.haveObjectName" />：</td>
							<%-- 支付對象 --%>
							<td class="input" style="width: 20%">
								<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
								<input name="prpLpayObjectInfoOwnerName" maxlength="100" class="input">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="title" style="width: 12%">
								<s:text name='prpLbuyer.uniformNo' />：
							</td>
							<%-- 統一編號 --%>
							<td class="input" style="width: 20%">
								<input name="prpLpayObjectInfoUniformNo" class="input">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="title" style="width: 12%">
								<span id="cutBack1" style="display: none"> <s:text name='prpLpayObjectInfo.cutBack' />：
								</span>
							</td>
							<%-- 禁背 --%>
							<td class="input" style="width: 20%">
								<span id="cutBack2" style="display: none"> <select name="prpLpayObjectInfoCutBack" class='input'>
										<option value="1" selected>
											<s:text name="regist.prpLregist.yes" />
											<%-- 是 --%>
										</option>
										<option value="0">
											<s:text name="regist.prpLregist.no" />
											<%-- 否 --%>
										</option>
								</select>
								</span>
							</td>
						</tr>
						<tr style = "display: none" >
							<td class="title" style="width: 12%">
								<s:text name='prpLcharge.bankCode' />：
							</td>
							<%-- 总行代号 --%>
							<td class="input" style="width: 20%">
								<input name="prpLpayObjectInfoBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="title" style="width: 12%">
								<s:text name="compensate.headquarterName" />：
							</td>
							<%-- 总行名称 --%>
							<td class="input" style="width: 20%">
								<input name="prpLpayObjectInfoBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="title" style="width: 12%">
								<s:text name='prpLcharge.accountCode' />：
							</td>
							<%-- 匯款帳號 --%>
							<td class="input" style="width: 20%">
								<input name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly">
							</td>
						</tr>
						<tr style = "display: none" >
							<td class="title" style="width: 12%">
								<s:text name='prpLcharge.customBankCode' />：
							</td>
							<%-- 分行代號 --%>
							<td class="input" style="width: 20%">
								<input name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="title" style="width: 12%">
								<s:text name='compensate.bankNames' />：
							</td>
							<%-- 分行名称 --%>
							<td class="input" style="width: 20%">
								<input name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="input"  colspan="2">
								<input class='bigbutton' type='button' name='buttonAddPrpLpayObjectInfo' style="width: 50%" value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUserNew(this);">
								<%--录入费用支付帳户信息 --%>
							</td>
						</tr>
						<tr>
							<td class="title" style="width: 12%">
								<s:text name='regist.prpLregist.areaCode' />：
							</td>
							<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
							<!-- \webapp\claim\pages\common\remnant\RemnantPayObjectInfo.jsp -->
							<%-- 邮递区号 --%>
							<td class="input" style="width: 20%">
								<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
								<input name="prpLpayObjectInfoAreaCode" class="input" maxlength="3">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="title" style="width: 12%">
								<s:text name='prpLcharge.courierAddress' />：
							</td>
							<%-- 邮递地址 --%>
							<td class="input" style="width: 20%" colspan="3">
								<input name="prpLpayObjectInfoCourierAddress" class="input">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
						</tr>
					</table>
				</td>
				<td class="input" style='width: 4%' align="right">
					<div>
						<input type=button name="buttonPayObjectInfoDelete" class="smallbutton" onclick="deleteRow(this,'PayObjectInfo','prpLpayObjectInfoSerialNo');" value="-" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="ChargeImg" onclick="showPage(this,spanPayObjectInfo);">
			<b>收取對象訊息</b>
		</td>
	</tr>
</table>
<span id="spanPayObjectInfo" style="display:">
	<table class="common" style="width: 100%" id="PayObjectInfo">
		<thead>
			<tr>
				<td class="centertitle" colspan=2>收取對象訊息</td><%-- 支付对象讯息--%>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="prpLpayObjectInfo" items="${remnantDto.prpLpayObjectInfoList}">
				<tr name="trPrpLpayObjectInfo">
					<td class="subformtitle" style="width: 96%">
						<table class="common" style="width: 100%">
							<tr>
								<td class="title" colspan="6">
									<b>收取對象&nbsp;<input name="prpLpayObjectInfoSerialNo" value="${prpLpayObjectInfo.id.serialNo}" class="readonly" readOnly></b>
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 12%">
									<s:text name="prpLremnant.payAmount" />：
								</td><%-- 支付金额--%>
								<td class="input" style="width: 20%">
									<input name="prpLpayObjectInfoPayAmount" type="text" class="readonly" readonly="readonly" value="<fmt:formatNumber value='${prpLpayObjectInfo.payAmount}' pattern='#'/>">
									<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
								</td>
								<td class="title" style="width: 12%">
									<s:text name="prpLremnant.remnantCostList" />：
								</td><%-- 殘餘物費用代碼--%>
								<td class="input" style="width: 20%">
									<c:set var="paymentKind" value="${prpLpayObjectInfo.paymentKind}" />
									<s:select list="#request.remnantCostList" name="prpLpayObjectInfoPaymentKind" listKey="key" listValue="value" value="#attr.paymentKind" />
									<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
								</td>
								<td class="title" style="width: 12%"></td>
								<td class="input" style="width: 20%"></td>
							</tr>
							<tr>
								<td class="title" style="width: 12%">
									<s:text name='prpLpayObjectInfo.ownerShip' />：
								</td><%-- 支付方式--%>
								<!-- 费用支付方式 -->
								<td class="input" style="width: 20%">
									<select name="prpLpayObjectInfoOwnerShip" onchange="compensateChargeownerShip_object(this)">
										<option value="B" <c:if test="${prpLpayObjectInfo.ownerShip=='B'}"><c:out value="selected"/></c:if> ><s:text name="compensate.remittance" /><!-- 汇款 --></option>
										<option value="C" <c:if test="${prpLpayObjectInfo.ownerShip=='C'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cash" /><!-- 现金 --></option>
										<option value="Q" <c:if test="${prpLpayObjectInfo.ownerShip=='Q'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cheque" /><!-- 支票 --></option>
									</select>
								</td>
								<td class="title" style="width: 12%">
									證件類型：
								</td>
								<td class="input" style="width: 20%">
									<s:select name="prpLpayObjectInfoCertificateCode" listKey="key" listValue="value" value="#attr.prpLpayObjectInfo.certificateCode" list="#request.prpdpaymentaccountCertificateTypeList" />
								</td>
								<td class="title" style="width: 12%">
									受款人電話：
								</td>
								<td class="input" style="width: 20%">
									<input type="text" name="prpLpayObjectInfoBeneficiaryPhone" class="input" value="${prpLpayObjectInfo.beneficiaryPhone}">
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 12%" colspan="1">
									<s:text name="replevy.haveObjectName" />：
									<%-- 賠付對象 --%>
								</td>
								<td class="input" style="width: 20%" colspan="1">
									<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
									<input name="prpLpayObjectInfoOwnerName" class="input" style="width: 90%" maxlength="100" value="${prpLpayObjectInfo.ownerName}">
									<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete">
								</td>
								<td class="title" style="width: 12%" colspan="1">
									<%--統一編號 --%>
									<s:text name="prpLbuyer.uniformNo" />：
								</td>
								<td class="input" style="width: 20%" colspan="1">
									<input name="prpLpayObjectInfoUniformNo" class="input" style="width: 90%" maxlength="20" value="${prpLpayObjectInfo.uniformNo}">
									<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
								</td>
								<td class="title" style="width: 12%">
									<span id="cutBack1" <c:if test="${prpLpayObjectInfo.ownerShip!='Q'}">style="display:none" </c:if>> 
									<s:text name='prpLpayObjectInfo.cutBack'/>：											
									</span>
								</td>
								<%-- 禁背 --%>
								<td class="input" style="width: 20%">
								<span id="cutBack2" <c:if test="${prpLpayObjectInfo.ownerShip!='Q'}">style="display:none" </c:if>> 
										<select name="prpLpayObjectInfoCutBack" class='common' style="width: 50%">
											<option value="1" <c:if test="${prpLpayObjectInfo.cutBack=='1'}"><c:out value="selected"/></c:if>>
												<s:text name="regist.prpLregist.yes" />
												<%-- 是 --%>
											</option>
											<option value="0" <c:if test="${prpLpayObjectInfo.cutBack=='0'}"><c:out value="selected"/></c:if>>
												<s:text name="regist.prpLregist.no" />
												<%-- 否 --%>
											</option>
										</select>
								</span>
								</td>
							</tr>
							<tr style = "display: none" >
								<td class="title" style="width: 12%"><s:text name="prpLcharge.bankCode" />：</td>
								<%-- 总行代号 --%>
								<td class="input" style="width: 20%">
									<input name="prpLpayObjectInfoBankCode" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.bankCode}">
								</td>
								<td class="title" style="width: 12%">
									<s:text name="compensate.headquarterName" />：
								</td>
								<%-- 总行名称 --%>
								<td class="input" style="width: 20%">
									<input name="prpLpayObjectInfoBankName" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.bankName}">
								</td>
								<td class="title" style="width: 12%"><s:text name="prpLcharge.accountCode" />：</td>
								<%-- 匯款帳號 --%>
								<td class="input" style="width: 20%">
									<input name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.accountCode}">
								</td>
							</tr>
							<tr style = "display: none" >
								<td class="title" style="width: 12%"><s:text name="prpLcharge.customBankCode" />：</td>
								<%-- 分行代號 --%>
								<td class="input" style="width: 20%">
									<input name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.customBankCode}">
								</td>
								<td class="title" style="width: 12%">
									<s:text name='compensate.bankNames' />：
								</td>
								<%-- 分行名称 --%>
								<td class="input" style="width: 20%">
									<input name="prpLpayObjectInfoCustomBankName" style="width: 33%" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.customBankName}">
								</td>
								<td class="input" colspan="2">
									<input class='bigbutton' type='button' name='buttonAddPrpLpayObjectInfo' style="width: 50%" value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUserNew(this);">
									<%--录入费用支付帳户信息 --%>
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 12%" colspan="1">
									<s:text name='regist.prpLregist.areaCode' />：
								</td>
								<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
								<!--  \webapp\claim\pages\common\remnant\RemnantPayObjectInfo.jsp 2-->
								<%-- 邮递区号 --%>
								<td class="input" style="width: 20%" colspan="1">
									<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
									<input name="prpLpayObjectInfoAreaCode" style="width: 90%" class="input" value="${prpLpayObjectInfo.areaCode}" maxlength="3">
									<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
								</td>
								<td class="title" style="width: 12%" colspan="1"><s:text name="prpLcharge.courierAddress" />：</td>
								<%-- 邮递地址 --%>
								<td class="input" style="width: 20%" colspan="3">
									<input name="prpLpayObjectInfoCourierAddress" style="width: 90%" class="input" value="${prpLpayObjectInfo.courierAddress}">
									<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
								</td>
							</tr>
						</table>
					</td>
					<td class="input" style='width: 4%' align="right">
						<input type=button name="buttonPayObjectInfoDelete" class="smallbutton" onclick="deleteRow(this,'PayObjectInfo','prpLpayObjectInfoSerialNo');" value="-" style="cursor: hand">
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td class="title" style="width: 96%">
					<s:text name="prompt.remnant.addRemove" />
				</td>
				<td class="title" align="right" style="width: 4%">
					<div align="right">
						<input type="button" value="+" class=smallbutton onclick="insertRow('PayObjectInfo',this,'prpLpayObjectInfoSerialNo');" name="buttonPayObjectInfoInsert" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</span>
<div id="prpLPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
	<ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList==null||#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()==0">
			<li><s:text name="title.compensateEdit.notPaymentObject" />。</li>
			<%-- 沒有賠款給付對象訊息，請錄入賠款給付對象--%>
		</s:if>
		<s:else>
			<c:forEach var="prpLpayObjectInfoTemp" items="${prpLpayObjectInfo.prpLpayObjectInfoList}">
				<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="${prpLpayObjectInfoTemp.id.serialNo}" /> <s:text name="compensate.paymentObject" />${prpLpayObjectInfoTemp.id.serialNo} <s:text
						name="db.prpLcfee.sumPaid" />: <input type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width: 100px" /></li>
				<%--赔付对象--%>
				<%--赔付金额--%>
			</c:forEach>
		</s:else>
	</ul>
	<ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'prpLPayObjectinfo')" value="<s:text name='button.close.value' />" /></li>
	</ul>
</div>