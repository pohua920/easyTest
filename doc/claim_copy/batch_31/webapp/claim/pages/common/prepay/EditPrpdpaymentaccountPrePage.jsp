<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<c:if test="${prpLprepay!=null}">
<script>
function compensateOwnerShip_change(id) {
	if (id != "B") {
		document.all["compensatebank"].style.display = "none";
		document.all["compensateAccountCQ"].style.display = "block";
	} else {
		document.all["compensatebank"].style.display = "block";
		document.all["compensateAccountCQ"].style.display = "none";
	}
}

function checkName(field) {
	var classCode = fm.prpLprepayClassCode.value;
	if ("D" == getClassCodeType(classCode)) { //车险
		var insuredName = trim(fm.prpLprepayInsuredName.value); //被保险人名称
		var payName = trim(field.value); //支付对象名称
		if (payName != "") {
			if (insuredName != payName) {
				var payName2 = trim(fm.ifOwnerName.value);
				if (payName != payName2) { //此处判断是为了消除刚进入理算页面有时会触发onpropertychange事件而弹出警告窗口
					alert("收款人非被保险人，请说明原因。");
					fm.ifInsuredName.value = "1";
					compensateExceptions.style.display = "";
				}
			} else {
				fm.ifInsuredName.value = "0";
				compensateExceptions.style.display = "none";
			}
		}
	}
}

function changeExceptions() {
	if (fm.exceptions.value == 9) {
		fm.button_Engage_Open_Context00.style.display = "";
	} else {
		fm.reason.value = "";
		fm.button_Engage_Open_Context00.style.display = "none";
	}
}
</script>
	<tr>
		<td colspan="12">
			<table class=subtable style="width: 100%" cellpadding="0" cellspacing="1">
				<tr>
					<td class="input" style="width: 18%">
						<%--<s:text name="prepay.indemnityPaymentAdvance" />：预付赔款支付方式 --%>
						標的損失賠款支付方式:
					</td>
					<td class="input" style="width: 20%">
						<!-- 支付对象是否是被保险人标志 0是/1否 -->
						<c:if test="${prpLprepay.exceptions==null||prpLprepay.exceptions==''}">
							<input type="hidden" name="ifInsuredName" value="0">
						</c:if>
						<c:if test="${prpLprepay.exceptions!=null&&prpLprepay.exceptions!=''}">
							<input type="hidden" name="ifInsuredName" value="1">
						</c:if>
						<input type="hidden" name="ifOwnerName" value="${prpLprepay.ownerName}">
						<select name="prpLCompensateOwnership" onchange="compensateOwnerShip_change(this.options[this.selectedIndex].value)">
							<option value="C" <c:if test="${prpLprepay.ownership=='C'}">selected</c:if>>
								<s:text name="compensate.agentInfo.cash" />
								<%--现金 --%>
							</option>
							<option value="B" <c:if test="${prpLprepay.ownership=='B'}">selected</c:if>>
								<s:text name="compensate.remittance" />
								<%--汇款 --%>
							</option>
							<option value="Q" <c:if test="${prpLprepay.ownership=='Q'}">selected</c:if>>
								<s:text name="compensate.agentInfo.cheque" />
								<%--支票 --%>
							</option>
						</select>
					</td>
					<td class="input" style="width: 60%"></td>
				</tr>
			</table>
		</td>
	</tr>
	<table class=subtable cellpadding="0" cellspacing="1">
		<tr>
			<td>
				<c:if test="${prpLprepay.ownership=='B'}">
					<div id="compensateAccountCQ" style="display: none">
				</c:if>
				<c:if test="${prpLprepay.ownership!='B'}">
					<div id="compensateAccountCQ">
				</c:if>
				<table class="common" style="width: 100%">
					<tr>
						<td class="input" style="width: 18%">
							<s:text name="compensate.papObjectName" />
							：
							<%--支付对象姓名 --%>
						</td>
						<td class="input" style="width: 20%">
							<%
								request.setAttribute("CLASSCODE_D_A", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_A);
									request.setAttribute("CLASSCODE_D_B", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_B);
							%>
							<c:if test="${prpLprepay.classCode==CLASSCODE_D_A||prpLprepay.classCode==CLASSCODE_D_B}">
								<c:if test="${prpLprepay.ownerName!=null&&prpLprepay.ownerName!=''}">
									<input name="prpLCompensateOwnerNameCQ" class="input" maxlength="120" value="${prpLprepay.ownerName}" onblur="checkName(this)">
								</c:if>
								<c:if test="${prpLprepay.ownerName==null||prpLprepay.ownerName==''}">
									<input name="prpLCompensateOwnerNameCQ" class="input" maxlength="120" value="${insuredName }" onblur="checkName(this)">
								</c:if>
							</c:if>
							<c:if test="${prpLprepay.classCode!=CLASSCODE_D_A&&prpLprepay.classCode!=CLASSCODE_D_B}">
								<c:if test="${prpLprepay.ownership!='B'}">
									<input name="prpLCompensateOwnerNameCQ" class="input" maxlength="120" value="${prpLprepay.ownerName }" onblur="checkName(this)">
								</c:if>
								<c:if test="${prpLprepay.ownership=='B'}">
									<input name="prpLCompensateOwnerNameCQ" class="input" maxlength="120" value="" onblur="checkName(this)">
								</c:if>
							</c:if>
							<img src="${ctx }/images/bgMarkMustInput.jpg" complete="complete" />
						</td>
						<td class="input" style="width: 18%">
							<s:text name="db.prpLdriver.identifyNumber" />
							：
							<%--证件号码 --%>
						</td>
						<td class="input" style="width: 44%">
							<c:if test="${prpLprepay.ownership!='B'}">
								<input name="prpLCompensateCertificateCodeCQ" class="input" style="width: 160px" maxlength="20" value="${prpLprepay.certifiCateCode}">
							</c:if>
							<c:if test="${prpLprepay.ownership=='B'}">
								<input name="prpLCompensateCertificateCodeCQ" class="input" style="width: 160px" maxlength="20" value="">
							</c:if>
						</td>
					</tr>
				</table>
				</div>
				<c:if test="${prpLprepay.ownership=='B'}">
					<div id="compensatebank">
				</c:if>
				<c:if test="${prpLprepay.ownership!='B'}">
					<div id="compensatebank" style="display: none">
				</c:if>
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<input class='bigbutton' type='button' name='buttonAccCompensate' value='<s:text name="button.inputPaymentInformation.value" />' onclick="oldQueryUserCompensate();">
							<%--录入赔款支付帳户信息 --%>
							<input class="readonly" type="hidden" name="prpLCompensateSumSelfValue" readonly="true" value="">
						</td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="compensate.bankAccount" />
							<%--银行帳号 --%>
						</td>
						<td class="right">
							<input type=text name="prpLCompensateAccountCode" class="readonly" readonly="true" value="${prpLprepay.accountCode}">
						</td>
						<td class="left">
							<s:text name="compensate.headBank" />
							<%--银行总行 --%>
						</td>
						<td class="right">
							<input class="readonly" type="text" name="prpLCompensateBankName" readonly="true"<c:if test="${prpLprepay.bankCode=='102'}">value=" <s:text name="compensate.bankName1"/>"</c:if> <%-- 中国工商银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='103'}">value=" <s:text name="compensate.bankName2"/>"</c:if> <%-- 中国农业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='104'}">value=" <s:text name="compensate.bankName3"/>"</c:if> <%-- 中国银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='105'}">value=" <s:text name="compensate.bankName4"/>"</c:if> <%-- 中国建设银行 --%>                        
								<c:if test="${prpLprepay.bankCode=='106'}">value=" <s:text name="compensate.bankName5"/>"</c:if> <%-- 民生银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='107'}">value=" <s:text name="compensate.bankName6"/>"</c:if> <%-- 农村信用社 --%>                           
								<c:if test="${prpLprepay.bankCode=='108'}">value=" <s:text name="compensate.bankName7"/>"</c:if> <%-- 兴业银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='109'}">value=" <s:text name="compensate.bankName8"/>"</c:if> <%-- 中信实业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='110'}">value=" <s:text name="compensate.bankName9"/>"<</c:if> <%-- 国家开发银行 --%>                        
								<c:if test="${prpLprepay.bankCode=='111'}">value=" <s:text name="compensate.bankName10"/>"</c:if><%-- 国家进出口银行 --%>                       
								<c:if test="${prpLprepay.bankCode=='112'}">value=" <s:text name="compensate.bankName11"/>"</c:if><%-- 农业发展银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='113'}">value=" <s:text name="compensate.bankName12"/>"</c:if><%-- 恒丰银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='114'}">value=" <s:text name="compensate.bankName13"/>"</c:if><%--住房公积金管理中心  --%>                   
								<c:if test="${prpLprepay.bankCode=='1200'}">value="<s:text name="compensate.bankName14"/>"</c:if><%--邮政储汇  --%>                             
								<c:if test="${prpLprepay.bankCode=='1701'}">value="<s:text name="compensate.bankName15"/>"</c:if><%--香港上海汇丰银行  --%>                     
								<c:if test="${prpLprepay.bankCode=='1702'}">value="<s:text name="compensate.bankName16"/>"</c:if><%-- 东亚银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='1703'}">value="<s:text name="compensate.bankName17"/>"</c:if><%--标准渣打银行  --%>                         
								<c:if test="${prpLprepay.bankCode=='1704'}">value="<s:text name="compensate.bankName18"/>"</c:if><%-- 荷兰商业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1705'}">value="<s:text name="compensate.bankName19"/>"</c:if><%-- 恒生银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='1706'}">value="<s:text name="compensate.bankName20"/>"</c:if><%-- 大华银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='1707'}">value="<s:text name="compensate.bankName21"/>"</c:if><%--法国里昂信贷银行  --%>                     
								<c:if test="${prpLprepay.bankCode=='1708'}">value="<s:text name="compensate.bankName22"/>"</c:if><%-- 法国巴黎银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1709'}">value="<s:text name="compensate.bankName23"/>"</c:if><%-- 美国花旗银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1710'}">value="<s:text name="compensate.bankName24"/>"</c:if><%-- 美国摩根大通银行 --%>                     
								<c:if test="${prpLprepay.bankCode=='1711'}">value="<s:text name="compensate.bankName25"/>"</c:if><%--美国银行  --%>                             
								<c:if test="${prpLprepay.bankCode=='1712'}">value="<s:text name="compensate.bankName26"/>"</c:if><%-- 美国运通银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1713'}">value="<s:text name="compensate.bankName27"/>"</c:if><%-- 德国商业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1714'}">value="<s:text name="compensate.bankName28"/>"</c:if><%-- 德意志银行 --%>                           
								<c:if test="${prpLprepay.bankCode=='1715'}">value="<s:text name="compensate.bankName29"/>"</c:if><%-- 日本三井住友银行 --%>                     
								<c:if test="${prpLprepay.bankCode=='1716'}">value="<s:text name="compensate.bankName30"/>"</c:if><%-- 日本东京三菱银行 --%>                     
								<c:if test="${prpLprepay.bankCode=='1717'}">value="<s:text name="compensate.bankName31"/>"</c:if><%--日本横滨银行  --%>                         
								<c:if test="${prpLprepay.bankCode=='1718'}">value="<s:text name="compensate.bankName32"/>"</c:if><%-- 日本日联银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1719'}">value="<s:text name="compensate.bankName33"/>"</c:if><%-- 瑞士信贷第一波士顿银行 --%>               
								<c:if test="${prpLprepay.bankCode=='1720'}">value="<s:text name="compensate.bankName34"/>"</c:if><%--瑞士信贷银行  --%>                         
								<c:if test="${prpLprepay.bankCode=='1721'}">value="<s:text name="compensate.bankName35"/>"</c:if><%-- 瑞士银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='1722'}">value="<s:text name="compensate.bankName36"/>"</c:if><%-- 古巴国民银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1723'}">value="<s:text name="compensate.bankName37"/>"</c:if><%-- 韩国产业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='1724'}">value="<s:text name="compensate.bankName38"/>"</c:if><%--韩亚银行  --%>                             
								<c:if test="${prpLprepay.bankCode=='1725'}">value="<s:text name="compensate.bankName39"/>"</c:if><%-- 加拿大皇家银行 --%>                       
								<c:if test="${prpLprepay.bankCode=='1726'}">value="<s:text name="compensate.bankName40"/>"</c:if><%-- 马来西亚马来亚银行 --%>                   
								<c:if test="${prpLprepay.bankCode=='1727'}">value="<s:text name="compensate.bankName41"/>"</c:if><%-- 泰国盘谷银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='301'}">value=" <s:text name="compensate.bankName42"/>"</c:if><%-- 交通银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='302'}">value=" <s:text name="compensate.bankName43"/>"</c:if><%-- 中信实业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='303'}">value=" <s:text name="compensate.bankName44"/>"</c:if><%-- 中国光大银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='304'}">value=" <s:text name="compensate.bankName45"/>"</c:if><%-- 华夏银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='305'}">value=" <s:text name="compensate.bankName46"/>"</c:if><%-- 中国民生银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='307'}">value=" <s:text name="compensate.bankName47"/>"</c:if><%-- 深圳发展银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='308'}">value=" <s:text name="compensate.bankName48"/>"</c:if><%-- 招商银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='309'}">value=" <s:text name="compensate.bankName49"/>"</c:if><%--福建兴业银行  --%>                         
								<c:if test="${prpLprepay.bankCode=='310'}">value=" <s:text name="compensate.bankName50"/>"</c:if><%-- 上海浦东发展银行 --%>                     
								<c:if test="${prpLprepay.bankCode=='313'}">value=" <s:text name="compensate.bankName51"/>"</c:if><%-- 城市商业银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='314'}">value=" <s:text name="compensate.bankName52"/>"</c:if><%-- 厦门银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='401'}">value=" <s:text name="compensate.bankName53"/>"</c:if><%-- 城市信用合作社 --%>                       
								<c:if test="${prpLprepay.bankCode=='402'}">value=" <s:text name="compensate.bankName54"/>"</c:if><%-- 农村信用社（含北京农村商业银行） --%>     
								<c:if test="${prpLprepay.bankCode=='403'}">value=" <s:text name="compensate.bankName55"/>"</c:if><%-- 中国邮政储蓄银行 --%>                     
								<c:if test="${prpLprepay.bankCode=='501'}">value=" <s:text name="compensate.bankName56"/>"</c:if><%-- 广东发展银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='783'}">value=" <s:text name="compensate.bankName57"/>"</c:if><%-- 平安银行 --%>                             
								<c:if test="${prpLprepay.bankCode=='781'}">value=" <s:text name="compensate.bankName58"/>"</c:if><%-- 厦门国际银行 --%>                         
								<c:if test="${prpLprepay.bankCode=='701'}">value=" <s:text name="compensate.bankName59"/>"</c:if><%-- 上海农村商业银 --%>                       
								
								>
							<input type=hidden name="prpLCompensateBankCode" value="${prpLprepay.bankCode}">
						</td>
						<td class="left">
							<s:text name="db.prpDcustomer_Unit.bank" />
							<%--开户银行 --%>
						</td>
						<td class="right">
							<input class="readonly" type="text" name="prpLCompensateCustomBankName" readonly="true" value="${prpLprepay.customBankName}">
							<input type=hidden name="prpLCompensateCustomBankCode" value="">
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="compensate.accountBelongCode" />
							<%--帳户归属人证件代码 --%>
						</td>
						<td class="right">
							<input type="text" name="prpLCompensateCertificateCode" class="readonly" readonly="true" <c:if test="${prpLprepay.ownership!='B'}">value=""</c:if>
								<c:if test="${prpLprepay.ownership=='B'}">value="${prpLprepay.certifiCateCode}"</c:if>>
						</td>
						<td class="left">
							<s:text name="compensate.accountBelongName" />
							<%--帳户归属人名称 --%>
						</td>
						<td class="right">
							<c:if test="${prpLprepay.classCode==CLASSCODE_D_A||prpLprepay.classCode==CLASSCODE_D_B}">
								<c:if test="${prpLprepay.ownerName!=null&&prpLprepay.ownerName!=''}">
									<input class="readonly" type=text name="prpLCompensateOwnerName" readonly="true" value="${prpLprepay.ownerName}" onpropertychange="checkName(this)">
								</c:if>
								<c:if test="${prpLprepay.ownerName==null||prpLprepay.ownerName==''}">
									<input class="readonly" type=text name="prpLCompensateOwnerName" readonly="true" value="${insuredName }" onpropertychange="checkName(this)">
								</c:if>
							</c:if>
							<c:if test="${prpLprepay.classCode!=CLASSCODE_D_A&&prpLprepay.classCode!=CLASSCODE_D_B}">
								<input class="readonly" type="text" name="prpLCompensateOwnerName" readonly="true" <c:if test="${prpLprepay.ownership!='B'}">value=""</c:if>
									<c:if test="${prpLprepay.ownership=='B'}">value="${prpLprepay.ownerName }"</c:if> onpropertychange="checkName(this)">
							</c:if>
						</td>
						<td class="left">
							<s:text name="compensate.accountBelongPhone" />
							<%--帳户归属人联系电话 --%>
						</td>
						<td class="right">
							<input class="readonly" type="text" name="prpLCompensateOwnerPhoneNo" readonly="true" value="${prpLprepay.ownerPhoneNo}">
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="compensate.accountCurrencyType" />
							<%--帳户类型 --%>
						</td>
						<td class="right">
							<input type="text" name="prpLCompensateAccountTypeShow" class="readonly" readonly="true"
								<c:if test="${prpLprepay.accountType=='1'}">value="<s:text name="compensate.passbook" />"</c:if> <%--存折 --%>
								<c:if test="${prpLprepay.accountType=='2'}">value="<s:text name="compensate.creditCard" />"</c:if><%--信用卡 --%>
								<c:if test="${prpLprepay.accountType=='3'}">value="<s:text name="compensate.CARDS" />"</c:if> <%-- 储值卡 --%>
								<c:if test="${prpLprepay.accountType=='4'}">value="<s:text name="regist.prpLregist.other" />"</c:if>>
							<%--其他 --%>
							<input type=hidden name="prpLCompensateAccountType" value="${prpLprepay.accountType}">
						</td>
						<td class="left">
							<s:text name="compensate.accountCurrency" />
							<%--帳户币别 --%>
						</td>
						<td class="right">
							<input class="readonly" type=text name="prpLCompensateAccountCurrency" readonly="true" value="${prpLprepay.accountCurrency}">
						</td>
						<td class="left">
							<!-- 业务与帳户关系 -->
						</td>
						<td class="right">
							<input class="readonly" type="text" name="prpLCompensateOwnershipOld" readonly="true" value="">
						</td>
					</tr>
				</table>
				</div>
			</td>
		</tr>
	</table>
	<br>
	<c:if test="${prpLprepay.exceptions==null||prpLprepay.exceptions==''}">
		<div id="compensateExceptions" style="display: none;">
	</c:if>
	<c:if test="${prpLprepay.exceptions!=null&&prpLprepay.exceptions!=''}">
		<div id="compensateExceptions" style="display: block;">
	</c:if>
	<table class=subtable style="width: 100%" cellpadding="0" cellspacing="1">
		<tr>
			<td class="input" style="width: 18%">
				<s:text name="compensate.exceptionEventsCause" />
				<%--例外事项原因 --%>
			</td>
			<td class="input" style="width: 30%">
				<select name="exceptions" onchange="changeExceptions()">
					<option value="1" <c:if test="${prpLprepay.exceptions=='1'}">selected</c:if>>
						<s:text name="compensate.liabilityInsuranceThird" />
						<%--责任保险第三者 --%>
					</option>
					<option value="2" <c:if test="${prpLprepay.exceptions=='2'}">selected</c:if>>
						<s:text name="compensate.effectJudgmentArbitrationAward" />
						<%--生效的法院判决或仲裁裁决 --%>
					</option>
					<option value="3" <c:if test="${prpLprepay.exceptions=='3'}">selected</c:if>>
						<s:text name="compensate.shareBusiness" />
						<%--共保业务 --%>
					</option>
					<option value="4" <c:if test="${prpLprepay.exceptions=='4'}">selected</c:if>>
						<s:text name="compensate.insurancePayment" />
						<%--交强险垫付/支付 --%>
					</option>
					<option value="5" <c:if test="${prpLprepay.exceptions=='5'}">selected</c:if>>
						<s:text name="compensate.payBailoutFund" />
						<%--支付救助基金 --%>
					</option>
					<option value="6" <c:if test="${prpLprepay.exceptions=='6'}">selected</c:if>>
						<s:text name="compensate.outing" />
						<%--车辆过户 --%>
					</option>
					<option value="7" <c:if test="${prpLprepay.exceptions=='7'}">selected</c:if>>
						<s:text name="compensate.accordingPolicyAgreed" />
						<%--根据保单约定，需向第三方支付 --%>
					</option>
					<option value="8" <c:if test="${prpLprepay.exceptions=='8'}">selected</c:if>>
						<s:text name="compensate.shortCashPayment" />
						<%--1000元以下现金支付 --%>
					</option>
					<option value="9" <c:if test="${prpLprepay.exceptions=='9'}">selected</c:if>>
						<s:text name="check.other" />
						<%--其它 --%>
					</option>
				</select> <img src="${ctx }/images/bgMarkMustInput.jpg" complete="complete" />
			</td>
			<td class="input" style="width: 52%">
				<c:if test="${prpLprepay.exceptions=='9'}">
					<input type=button ACCESSKEY="." num=1 value='<s:text name="button.exceptionCauseDescription.value" />' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);" style="display: block;"
						title="點選輸入例外事項原因描述">
					<%--例外事项原因描述 --%>
				</c:if>
				<c:if test="${prpLprepay.exceptions!='9'}">
					<input type=button ACCESSKEY="." num=1 value='<s:text name="button.exceptionCauseDescription.value" />' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);" style="display: none;"
						title="點選輸入例外事項原因描述">
					<%--例外事项原因描述 --%>
				</c:if>
				<span id="span_Engage_Context00" style='width: 700; display: none; position: absolute; background-color: FFFFFF;'>
					<table class="common">
						<tr>
							<td class="prompttitle" colspan="3">
								<s:text name="button.exceptionCauseDescription.value" />
								<%--例外事项原因描述 --%>
							</td>
						</tr>
						<tr>
							<td class="prompt" colspan="3">
								<input name="reason" class="input" maxlength="250" value="${prpLprepay.reason}">
							</td>
						</tr>
						<tr>
							<td colspan=3 class="common">
								<input type=button class=button name='button_Engage_Close_Context00' value='<s:text name="button.close.value" />' ACCESSKEY="O" onclick="hideSubPage(this,'span_Engage_Context00')">
								<%--关闭 --%>
							</td>
						</tr>
					</table>
				</span>
			</td>
		</tr>
	</table>
	</div>
	<br>
	<table class=subtable cellpadding="0" cellspacing="1">
		<tr>
			<td></td>
		</tr>
	</table>
</c:if>