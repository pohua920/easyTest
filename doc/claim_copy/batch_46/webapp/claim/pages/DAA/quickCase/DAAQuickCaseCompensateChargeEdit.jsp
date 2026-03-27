<%--
****************************************************************************
* DESC       ：添加费用赔款信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
   <!--建立显示的輸入条，可以收缩显示的-->
<%@ page import="com.sinosoft.claim.common.ConstantCodes" %>
    <script language='javascript'>
    function viewDangerUnitCompensateCharge(field) {
    	for (var i = 1; i < fm.prpLchargeSerialNo.length; i++) {
    		if (fm.prpLchargeDangerNo[i] == field) {
    			var count = i;
    			var policyNo = fm.policyNo.value;
    			var damageDate = fm.prpLcheckDamageStartDate.value;
    			var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateCharge";
    			window.open(submitStr, '查看危险单位信息', 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
    		}
    	}
    }

    function checkChargeCode(field) {
    	var fieldname = field.name;
    	var findex = 0;
    	for (var i = 1; i < fm.all(fieldname).length; i++) {
    		if (fm.all(fieldname)[i] == field) {

    			findex = i;
    			break;
    		}
    	}
    	//modify by wangliguang 20080625 begin
    	//reason:费用类型不允许手工輸入
    	var kindCodeNum = fm.all("prpLlossDtoKindCode").length;
    	if (fm.prpLchargeChargeCode[findex].value != '' || fm.prpLchargeChargeName[findex].value != '') {
    		if (!(
    			(fm.prpLchargeChargeCode[findex].value == '03' && fm.prpLchargeChargeName[findex].value == '施救费') || (fm.prpLchargeChargeCode[findex].value == '04' && fm.prpLchargeChargeName[findex].value == '查勘费') || (fm.prpLchargeChargeCode[findex].value == '05' && fm.prpLchargeChargeName[findex].value == '诉讼费') || (fm.prpLchargeChargeCode[findex].value == '06' && fm.prpLchargeChargeName[findex].value == '代查勘') || (fm.prpLchargeChargeCode[findex].value == '08' && fm.prpLchargeChargeName[findex].value == '奖励费') || (fm.prpLchargeChargeCode[findex].value == '13' && fm.prpLchargeChargeName[findex].value == '公估费') || (fm.prpLchargeChargeCode[findex].value == '99' && fm.prpLchargeChargeName[findex].value == '其他')
    		)) {
    			alert("费用类型不允许手工輸入");
    			fm.prpLchargeChargeCode[findex].value = "";
    			fm.prpLchargeChargeName[findex].value = "";
    			return false;
    		}
    	}
    	//modify by wangliguang 20080625 end
    	var flag = 0;
    	var kindCode;
    	if (kindCodeNum != 'undefined' && kindCodeNum > 1) {
    		for (var index = 1; index < kindCodeNum; index++) {
    			kindCode = fm.all("prpLlossDtoKindCode")[index].value;
    			if (RISKINFO.KINDCODE_D_A == kindCode || "AB" == kindCode || 'Z' == kindCode || 'X1' == kindCode || 'K1' == kindCode || 'K2' == kindCode || 'Y' == kindCode || 'S' == kindCode || ConstantCodes.KINDCODE_D_BZ == kindCode) {
    				flag = 1;
    			}

    		}
    		if (flag == 0 && (field.value == '施救费' || field.value == '03')) {
    			alert(" 没有輸入车损信息，不能輸入施救费用");
    			fm.prpLchargeChargeCode[findex].value = "";
    			fm.prpLchargeChargeName[findex].value = "";
    			field.focus();
    			return false;
    		}
    	} else {
    		if (field.value == '施救费' || field.value == '03') {
    			alert(" 没有輸入车损信息，不能輸入施救费用");
    			fm.prpLchargeChargeCode[findex].value = "";
    			fm.prpLchargeChargeName[findex].value = "";
    			field.focus();
    			return false;
    		}
    	}

    }


    //在下面加入本页自定义的JavaScript方法

    /*
            插入一条新的之後的处理（可选方法）
          */

    function afterInsertCharge() {
    	setPrpLchargeSerialNo();
    }

    /*
            删除本条WarnRegion之後的处理（可选方法）
          */

    function afterDeleteCharge(field) {
    	setPrpLchargeSerialNo();
    	initExceptDeductible();
    	initEvryTypeRealPay();
    	calFund();
    }

    /**
     * 设置setPrpLchargeSerialNo
     */

    function setPrpLchargeSerialNo() {
    	var count = getElementCount("prpLchargeSerialNo");
    	for (var i = 0; i < count; i++) {
    		//alert("看看什么时候运行?count="+count+"  i="+i);
    		if (count != 1) {
    			fm.prpLchargeSerialNo[i].value = i;
    		}
    	}
    }
    </script>
<table class="common" align="center" style="width: 100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge);changeCompensateFlag('1');">
			<s:text name="compensate.feePaymentInfo" />
			<br>
			<!-- 费用赔款信息 -->
			<span style="display: none">
				<table class="common" style="display: none" id="Charge_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<%--<td class="input" style="width:7%">
                   <input type=text name="prpLchargeDangerNo" class="codecode" value = "1" onClick= "viewDangerUnitCompensateCharge(this);">
                </td>
                --%>
							<td class="input" style="width: 8%">
								<input type="hidden" type=text name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);">
								<input type="hidden" name="prpLchargeFlag">
								<input type="hidden" name="prpLchargeSerialNo" description="序号">
								<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3"
									ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onchange="code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onblur="checkExcept4();insertRow2(this,'1');checkBeyondQuota(this);checkChargeAmount(this);calChargeAmount(this);clearPrpLctext();">
							</td>
							<td class="input" style="width: 15%">
								<input name="prpLchargeKindName" class="codename" style="width: 100%" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onchange="code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
									onblur="checkExcept4();insertRow2(this,'2');checkBeyondQuota(this);calChargeAmount(this);checkChargeAmount(this);clearPrpLctext();">
							</td>
							<%-- modify by wangliguang 20080625 begin --%>
							<td class="input" style="width: 6%">
								<input name="prpLchargeChargeCode" class="codename" style="width: 100%" ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y');" onchange="code_CodeChange(this, 'ChargeCode','0,1','Y'); "
									onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y'); " onblur="checkChargeCode(this);calChargeAmount(this);checkChargeAmount(this);clearPrpLctext();">
							</td>
							<td class="input" style="width: 15%">
								<input name="prpLchargeChargeName" class="codename" style="width: 100%" ondblclick="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');"
									onchange="code_CodeChange(this, 'ChargeCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');"
									onblur=" checkChargeCode(this);calChargeAmount(this);checkChargeAmount(this);clearPrpLctext();">
							</td>
							<%-- modify by wangliguang 20080625 end --%>
							<td class="input" style="width: 5%">
								<select name="prpLchargePayObjectType" class='common' style="width: 50px">
									<option value="B" selected>
										<s:text name="compensate.external" />
									</option>
									<!-- 外部 -->
									<option value="A">
										<s:text name="compensate.internal" />
									</option>
									<!-- 内部 -->
								</select>
							</td>
							<td class="input" style="width: 8%">
								<input name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="" ondblclick="getPayObject(this);" onchange="getPayObject(this);" onkeyup="getPayObject(this);"
									onblur="clearPrpLctext();">
							</td>
							<td class="input" style="width: 6%">
								<input name="prpLchargeCurrency" class="readonly" style="width: 100%" value="<%=ConstantCodes.LOCAL_CURRENCY %>">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLchargeChargeReport" class="input" style="width: 100%" onchange="checkBeyondQuota(this);calChargeAmount(this);setRealPay();clearPrpLctext();" onblur=" checkChargeAmount(this);">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLchargeChargeAmount" class="input" style="width: 100%" onchange="calChargeAmount(this);setRealPay();clearPrpLctext();" onblur="checkChargeAmount(this);">
								<input name="prpLchargeSumRealPay" type="hidden" style="width: 100%" class='readonly' readonly>
								<input type='hidden' name="prpLchargeAmount">
								<input type='hidden' name="prpLchargeExceptDeductiblePay" value="0">
								<input type='hidden' name="prpLchargeExceptDeductibleRate" value="0">
								<input name="prpLchargeFlag" type="hidden">
							</td>
							<!-- 
                <td class="input" style="width:16%">
                </td>
                -->
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow2(this,'Charge');directDeleteRow(this,'Charge',1,4);" value="-" style="cursor: hand">
									<input type="hidden" name="prpLchargeFlag">
								</div>
							</td>
						</tr>
						<%--添加支付方式 2010-05-26--%>
						<%@include file="/common/compensate/ChargeOwnerShipAdd.jsp"%>
						<tr>
							<td colspan="12">
								<div id="bank" style="display: none">
									<table class="common" style="width: 100%">
										<tr>
											<td class="input" style="width: 10%">
												<s:text name="compensate.bankAccount" />
												<!-- 银行帳号 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeAccountCode" readOnly="readonly" class="input">
											</td>
											<td class="input" style="width: 8%">
												<s:text name="compensate.headquarterName" />
												<!-- 总行名称 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeBankName" readOnly="readonly" class="input">
												<input type="hidden" name="prpLchargeBankCode" class="input">
											</td>
											<td class="input" style="width: 12%">
												<s:text name="compensate.bankNames" />
												<!-- 开户银行名称c -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeCustomBankName" readOnly="readonly" class="input">
											</td>
										</tr>
										<tr>
											<td class="input" style="width: 10%">
												<s:text name="compensate.ownerCertiNum" />
												<!-- 归属人证件号码 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeCertifiCateCode" readOnly="readonly" class="input">
											</td>
											<td class="input" style="width: 8%">
												<s:text name="compensate.belongPeopleName" />
												<!-- 归属人名称 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeOwnerName" readOnly="readonly" class="input">
											</td>
											<td class="input" style="width: 12%">
												<s:text name="compensate.belongPeopleTel" />
												<!-- 归属人电话 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargePhoneNo" readOnly="readonly" class="input">
											</td>
										</tr>
										<tr>
											<td class="input" style="width: 10%">
												<s:text name="compensate.accountCurrency" />
												<!-- 帳户币别 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeAccountCurrency" readOnly="readonly" class="input">
											</td>
											<td class="input" style="width: 8%">
												<s:text name="compensate.accountCurrencyType" />
												<!-- 帳户类型 -->
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeAccountTypeShow" readOnly="readonly" class="input">
												<input type="hidden" name="prpLchargeAccountType" readOnly="readonly" class="input">
											</td>
											<td class="input" style="width: 12%" style="display:none">
												<s:text name="compensate.businessRelatAccount" />
												<!-- 业务与帳户关系 -->
											</td>
											<td class="input" style="width: 18%" style="display:none">
												<input name="prpLchargeOwnerShipOld" readOnly="readonly" class="input">
											</td>
											<td class="input" style="width: 12%"></td>
											<td class="input" style="width: 18%">
												<input class='bigbutton' type='button' name='buttonAddAcc' value='輸入费用支付帳户信息' onclick="queryUser(this);">
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr height="2" bgcolor="block">
							<td colspan="12"></td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="Charge">
					<thead>
						<tr>
							<td class="centertitle" style="width: 9%">
								<s:text name="db.prpDkind.kindCode" />
							</td>
							<!-- 险别代码 -->
							<td class="centertitle" style="width: 16%">
								<s:text name="db.prpDrate.kindName" />
							</td>
							<!-- 险别名称 -->
							<td class="centertitle" style="width: 7%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostCode" />
							</td>
							<!-- 费用代码 -->
							<td class="centertitle" style="width: 16%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostName" />
							</td>
							<!-- 费用名称 -->
							<td class="centertitle" style="width: 6%">
								<s:text name="compensate.paymentType" />
							</td>
							<!-- 支付类别 -->
							<td class="centertitle" style="width: 9%">
								<s:text name="quickCase.payObjectCode" />
							</td>
							<!-- 支付对象编码 -->
							<td class="centertitle" style="width: 11%">
								<s:text name="compensate.payNameObject" />
							</td>
							<!-- 支付对象名称 -->
							<td class="centertitle" style="width: 6%">
								<s:text name="db.prpDrate.currency" />
							</td>
							<!-- 币别 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLcharge.chargeAmount" />
							</td>
							<!-- 费用金额 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.actualCost" />
							</td>
							<!--Add 20060512 -->
							<!-- 实际费用 -->
							<!--
                <td class="centertitle"  style="width:16%">计入赔款金额</td>
                -->
							<td class="centertitle" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=10>
								<s:text name="prompt.compensate.addRemove" />
							</td>
							<!-- (按"+"号键增加费用赔款信息，按"-"号键删除信息) -->
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertRow('Charge')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<% int indexCharge=0;%>
						<logic:present name="prpLchargeDto">
							<logic:notEmpty name="prpLchargeDto" property="prpLchargeList">
								<logic:iterate id="chargedtox" name="prpLchargeDto" property="prpLchargeList">
									<tr>
										<%--<td class="input" style="width:7%">
                   <input name="prpLchargeDangerNo" class="codecode" value = "<bean:write name='chargedtox' property='dangerNo'/>" onClick= "viewDangerUnitCompensateCharge(this);">
                </td>
                --%>
										<td class="input" style="width: 8%">
											<input type="hidden" name="prpLchargeFlag" value="<bean:write name='chargedtox' property='flag'/>">
											<input type="hidden" name="prpLchargeDangerNo" class="codecode" value="<bean:write name='chargedtox' property='dangerNo'/>" onClick="viewDangerUnitCompensateCharge(this);">
											<input type="hidden" name="prpLchargeSerialNo" description="序号" value="<bean:write name='chargedtox' property='serialNo'/>">
											<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3" value="<bean:write name='chargedtox' property='kindCode'/>"
												ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onchange="code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onblur="checkExcept4();insertRow2(this,'1');checkBeyondQuota(this);calChargeAmount(this);checkChargeAmount(this);clearPrpLctext();">
										</td>
										<td class="input" style="width: 15%">
											<input name="prpLchargeKindName" class="codename" value="<bean:write name='chargedtox' property='kindName'/>"
												ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onchange="code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
												onblur="checkExcept4();insertRow2(this,'2');checkBeyondQuota(this);calChargeAmount(this);checkChargeAmount(this);clearPrpLctext();">
										</td>
										<%-- modify by wangliguang 20080625 begin --%>
										<td class="input" style="width: 6%">
											<input name="prpLchargeChargeCode" class="readonly" style="width: 100%" value="<bean:write name='chargedtox' property='chargeCode'/>"
												ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y');" onchange="code_CodeChange(this, 'ChargeCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y');"
												onblur="checkChargeCode(this);checkChargeAmount(this);calChargeAmount(this);clearPrpLctext();">
										</td>
										<td class="input" style="width: 15%">
											<input name="prpLchargeChargeName" class="codename" style="width: 100%" value="<bean:write name='chargedtox' property='chargeName'/>"
												ondblclick="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'ChargeCode','-1,0','Y','N');"
												onkeyup="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');" onblur=" checkChargeCode(this);checkChargeAmount(this);calChargeAmount(this);clearPrpLctext();">
										</td>
										<%-- modify by wangliguang 20080625 end --%>
										<td class="input" style="width: 5%">
											<select name="prpLchargePayObjectType" class='common' style="width: 50px">
												<option value="B" <logic:equal name='chargedtox' property='payObjectType' value="B">selected</logic:equal>>
													<s:text name="title.quickCase.makeAdjustmentReport" />
													外部
												</option>
												<option value="A" <logic:equal name='chargedtox' property='payObjectType' value="A">selected</logic:equal>>
													<s:text name="title.quickCase.makeAdjustmentReport" />
													内部
												</option>
											</select>
										</td>
										<td class="input" style="width: 8%">
											<input name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="<bean:write name='chargedtox' property='payObjectCode'/>">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="<bean:write name='chargedtox' property='payObjectName'/>" ondblclick="getPayObject(this);"
												onchange="getPayObject(this);" onkeyup="getPayObject(this);" onblur="clearPrpLctext();">
										</td>
										<td class="input" style="width: 6%">
											<input name="prpLchargeCurrency" class="readonly" style="width: 100%" value="<bean:write name='chargedtox' property='currency'/>">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargeChargeReport" class="input" style="width: 100%" value="<bean:write name='chargedtox' property='chargeReport'/>"
												onchange="checkBeyondQuota(this);calChargeAmount(this);setRealPay();clearPrpLctext();" onblur="checkChargeAmount(this);">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargeChargeAmount" class="input" style="width: 100%" value="<bean:write name='chargedtox' property='chargeAmount'/>"
												onchange="calChargeAmount(this);setRealPay();clearPrpLctext();" onblur="checkChargeAmount(this);">
											<input name="prpLchargeSumRealPay" type="hidden" class='readonly' readonly style="width: 100%" value="<bean:write name='chargedtox' property='sumRealPay'/>">
											<input type='hidden' name="prpLchargeExceptDeductiblePay" value="<bean:write name='chargedtox' property='exceptDeductiblePay'/>">
											<input type='hidden' name="prpLchargeExceptDeductibleRate" value="<bean:write name='chargedtox' property='exceptDeductibleRate'/>">
											<input type='hidden' name="prpLchargeAmount">
											<input name="prpLchargeFlag" type="hidden" value="<bean:write name='chargedtox' property='flag'/>">
										</td>
										<!-- 
                <td class="input" style="width:16%"></td>
                -->
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonChargeDelete" class=smallbutton onclick="deleteRow2(this,'Charge');directDeleteRow(this,'Charge',1,4);" value="-" style="cursor: hand">
												<input type="hidden" name="prpLchargeFlag">
											</div>
										</td>
									</tr>
									<%--添加支付方式 2010-05-26--%>
									<%@include file="/common/compensate/ChargeOwnerShipEdit.jsp"%>
									<tr>
										<td colspan="12">
											<logic:equal name='chargedtox' property='ownership' value="B">
												<div id="bank">
											</logic:equal>
											<logic:notEqual name='chargedtox' property='ownership' value="B">
												<div id="bank" style="display: none">
											</logic:notEqual>
											<table class="common" style="width: 100%">
												<tr>
													<td class="input" style="width: 10%">
														<s:text name="compensate.bankAccount" />
														<!-- 银行帳号 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeAccountCode" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='accountCode'/>">
													</td>
													<td class="input" style="width: 8%">
														<s:text name="compensate.headquarterName" />
														<!-- 总行名称 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeBankName" readOnly="readonly" class="input"
															<logic:equal name="chargedtox" property="bankCode" value="102">value="<s:text name="compensate.bankName1"/>"</logic:equal>
														<!-- 中国工商银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="103">value="<s:text name="compensate.bankName2" />"</logic:equal>
														<!-- 中国农业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="104">value="<s:text name="compensate.bankName3" />"</logic:equal>
														<!-- 中国银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="105">value="<s:text name="compensate.bankName4" />"</logic:equal>
														<!-- 中国建设银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="106">value="<s:text name="compensate.bankName5" />"</logic:equal>
														<!-- 民生银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="107">value="<s:text name="compensate.bankName6" />"</logic:equal>
														<!-- 农村信用社 -->
														<logic:equal name="chargedtox" property="bankCode" value="108">value="<s:text name="compensate.bankName7" />"</logic:equal>
														<!-- 兴业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="109">value="<s:text name="compensate.bankName8" />"</logic:equal>
														<!-- 中信实业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="110">value="<s:text name="compensate.bankName9" />"</logic:equal>
														<!-- 国家开发银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="111">value="<s:text name="compensate.bankName10" />"</logic:equal>
														<!-- 国家进出口银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="112">value="<s:text name="compensate.bankName11" />"</logic:equal>
														<!-- 农业发展银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="113">value="<s:text name="compensate.bankName12" />"</logic:equal>
														<!-- 恒丰银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="114">value="<s:text name="compensate.bankName13" />"</logic:equal>
														<!--住房公积金管理中心  -->
														<logic:equal name="chargedtox" property="bankCode" value="1200">value="<s:text name="compensate.bankName14" />"</logic:equal>
														<!--邮政储汇  -->
														<logic:equal name="chargedtox" property="bankCode" value="1701">value="<s:text name="compensate.bankName15" />"</logic:equal>
														<!--香港上海汇丰银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1702">value="<s:text name="compensate.bankName16" />"</logic:equal>
														<!-- 东亚银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1703">value="<s:text name="compensate.bankName17" />"</logic:equal>
														<!--标准渣打银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1704">value="<s:text name="compensate.bankName18" />"</logic:equal>
														<!-- 荷兰商业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1705">value="<s:text name="compensate.bankName19" />"</logic:equal>
														<!-- 恒生银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1706">value="<s:text name="compensate.bankName20" />"</logic:equal>
														<!-- 大华银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1707">value="<s:text name="compensate.bankName21" />"</logic:equal>
														<!--法国里昂信贷银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1708">value="<s:text name="compensate.bankName22" />"</logic:equal>
														<!-- 法国巴黎银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1709">value="<s:text name="compensate.bankName23" />"</logic:equal>
														<!-- 美国花旗银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1710">value="<s:text name="compensate.bankName24" />"</logic:equal>
														<!-- 美国摩根大通银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1711">value="<s:text name="compensate.bankName25" />"</logic:equal>
														<!--美国银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1712">value="<s:text name="compensate.bankName26" />"</logic:equal>
														<!-- 美国运通银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1713">value="<s:text name="compensate.bankName27" />"</logic:equal>
														<!-- 德国商业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1714">value="<s:text name="compensate.bankName28" />"</logic:equal>
														<!-- 德意志银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1715">value="<s:text name="compensate.bankName29" />"</logic:equal>
														<!-- 日本三井住友银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1716">value="<s:text name="compensate.bankName30" />"</logic:equal>
														<!-- 日本东京三菱银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1717">value="<s:text name="compensate.bankName31" />"</logic:equal>
														<!--日本横滨银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1718">value="<s:text name="compensate.bankName32" />"</logic:equal>
														<!-- 日本日联银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1719">value="<s:text name="compensate.bankName33" />"</logic:equal>
														<!-- 瑞士信贷第一波士顿银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1720">value="<s:text name="compensate.bankName34" />"</logic:equal>
														<!--瑞士信贷银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1721">value="<s:text name="compensate.bankName35" />"</logic:equal>
														<!-- 瑞士银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1722">value="<s:text name="compensate.bankName36" />"</logic:equal>
														<!-- 古巴国民银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1723">value="<s:text name="compensate.bankName37" />"</logic:equal>
														<!-- 韩国产业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1724">value="<s:text name="compensate.bankName38" />"</logic:equal>
														<!--韩亚银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="1725">value="<s:text name="compensate.bankName39" />"</logic:equal>
														<!-- 加拿大皇家银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1726">value="<s:text name="compensate.bankName40" />"</logic:equal>
														<!-- 马来西亚马来亚银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="1727">value="<s:text name="compensate.bankName41" />"</logic:equal>
														<!-- 泰国盘谷银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="301">value="<s:text name="compensate.bankName42" />"</logic:equal>
														<!-- 交通银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="302">value="<s:text name="compensate.bankName43" />"</logic:equal>
														<!-- 中信实业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="303">value="<s:text name="compensate.bankName44" />"</logic:equal>
														<!-- 中国光大银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="304">value="<s:text name="compensate.bankName45" />"</logic:equal>
														<!-- 华夏银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="305">value="<s:text name="compensate.bankName46" />"</logic:equal>
														<!-- 中国民生银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="307">value="<s:text name="compensate.bankName47" />"</logic:equal>
														<!-- 深圳发展银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="308">value="<s:text name="compensate.bankName48" />"</logic:equal>
														<!-- 招商银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="309">value="<s:text name="compensate.bankName49" />"</logic:equal>
														<!--福建兴业银行  -->
														<logic:equal name="chargedtox" property="bankCode" value="310">value="<s:text name="compensate.bankName50" />"</logic:equal>
														<!-- 上海浦东发展银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="313">value="<s:text name="compensate.bankName51" />"</logic:equal>
														<!-- 城市商业银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="314">value="<s:text name="compensate.bankName52" />"</logic:equal>
														<!-- 厦门银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="401">value="<s:text name="compensate.bankName53" />"</logic:equal>
														<!-- 城市信用合作社 -->
														<logic:equal name="chargedtox" property="bankCode" value="402">value="<s:text name="compensate.bankName54" />"</logic:equal>
														<!-- 农村信用社（含北京农村商业银行） -->
														<logic:equal name="chargedtox" property="bankCode" value="403">value="<s:text name="compensate.bankName55" />"</logic:equal>
														<!-- 中国邮政储蓄银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="501">value="<s:text name="compensate.bankName56" />"</logic:equal>
														<!-- 广东发展银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="783">value="<s:text name="compensate.bankName57" />"</logic:equal>
														<!-- 平安银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="781">value="<s:text name="compensate.bankName58" />"</logic:equal>
														<!-- 厦门国际银行 -->
														<logic:equal name="chargedtox" property="bankCode" value="701">value="<s:text name="compensate.bankName59" />"</logic:equal>
														>
														<!-- 上海农村商业银 -->
														<input type="hidden" name="prpLchargeBankCode" class="input" value="<bean:write name='chargedtox' property='bankCode'/>">
													</td>
													<td class="input" style="width: 12%">
														<s:text name="compensate.bankNames" />
														<!-- 开户银行名称 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeCustomBankName" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='customBankName'/>">
													</td>
												</tr>
												<tr>
													<td class="input" style="width: 10%">
														<s:text name="compensate.ownerCertiNum" />
														<!-- 归属人证件号码 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeCertifiCateCode" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='certifiCateCode'/>">
													</td>
													<td class="input" style="width: 8%">
														<s:text name="compensate.belongPeopleName" />
														<!-- 归属人名称 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeOwnerName" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='ownerName'/>">
													</td>
													<td class="input" style="width: 12%">
														<s:text name="compensate.belongPeopleTel" />
														<!-- 归属人电话 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargePhoneNo" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='ownerPhoneNo'/>">
													</td>
												</tr>
												<tr>
													<td class="input" style="width: 10%">
														<s:text name="compensate.accountCurrency" />
														<!-- 帳户币别 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeAccountCurrency" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='accountCurrency'/>">
													</td>
													<td class="input" style="width: 8%">
														<s:text name="compensate.accountCurrencyType" />
														<!-- 帳户类型 -->
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeAccountTypeShow" readOnly="readonly" class="input"
															<logic:equal name="chargedtox" property="accountType" value="1"> value="<s:text name="compensate.passbook"/>"</logic:equal>
														<!-- 存折 -->
														<logic:equal name="chargedtox" property="accountType" value="2"> value="<s:text name="compensate.creditCard" />"</logic:equal>
														<!-- 信用卡 -->
														<logic:equal name="chargedtox" property="accountType" value="3"> value="<s:text name="compensate.CARDS" />"</logic:equal>
														<!-- 储值卡 -->
														<logic:equal name="chargedtox" property="accountType" value="4"> value="<s:text name="regist.prpLregist.other" />"</logic:equal>
														>
														<!-- 其他 -->
														<input type="hidden" name="prpLchargeAccountType" readOnly="readonly" class="input" value="<bean:write name='chargedtox' property='accountType'/>">
													</td>
													<td class="input" style="width: 12%; display: none">
														<s:text name="compensate.businessRelatAccount" />
														<!-- 业务与帳户关系 -->
													</td>
													<td class="input" style="width: 18%; display: none">
														<input name="prpLchargeOwnerShipOld" readOnly="readonly" class="input">
													</td>
													<td class="input" style="width: 12%"></td>
													<td class="input" style="width: 18%">
														<input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name="button.entryPaymentInfo.value"/>' onclick="queryUser(this);">
														<!-- 輸入费用支付帳户信息 -->
													</td>
												</tr>
											</table>
											</div>
										</td>
									</tr>
									<tr height="2" bgcolor="block">
										<td colspan="12"></td>
									</tr>
									<%    indexCharge++;%>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						<logic:present name="compelPrpLchargeDto">
							<logic:notEmpty name="compelPrpLchargeDto" property="prpLchargeList">
								<logic:iterate id="chargedtox" name="compelPrpLchargeDto" property="prpLchargeList">
									<tr>
										<%--<td class="input" style="width:7%">
                   <input name="prpLchargeDangerNo" class="codecode" value = "<bean:write name='chargedtox' property='dangerNo'/>" onClick= "viewDangerUnitCompensateCharge(this);">
                </td>
                --%>
										<td class="input" style="width: 8%">
											<input type="hidden" name="prpLchargeFlag" value="<bean:write name='chargedtox' property='flag'/>">
											<input type="hidden" name="prpLchargeDangerNo" class="codecode" value="<bean:write name='chargedtox' property='dangerNo'/>" onClick="viewDangerUnitCompensateCharge(this);">
											<input type="hidden" name="prpLchargeSerialNo" description="序号" value="<bean:write name='chargedtox' property='serialNo'/>">
											<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3" value="<bean:write name='chargedtox' property='kindCode'/>"
												ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value||fm.prpLRegistRPolicyNo.value);"
												onchange="code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value||fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyNo.value||fm.prpLRegistRPolicyNo.value);" onblur="checkExcept4();insertRow2(this,'1');clearPrpLctext();">
										</td>
										<td class="input" style="width: 15%">
											<input name="prpLchargeKindName" class="codename" value="<bean:write name='chargedtox' property='kindName'/>"
												ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value||fm.prpLRegistRPolicyNo.value);"
												onchange="code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value||fm.prpLRegistRPolicyNo.value);"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyNo.value||fm.prpLRegistRPolicyNo.value);" onblur="checkExcept4();insertRow2(this,'2');clearPrpLctext();">
										</td>
										<%-- modify by wangliguang 20080625 begin --%>
										<td class="input" style="width: 6%">
											<input name="prpLchargeChargeCode" class="readonly" style="width: 100%" value="<bean:write name='chargedtox' property='chargeCode'/>"
												ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y');" onchange="code_CodeChange(this, 'ChargeCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y');"
												onblur="checkChargeCode(this);checkChargeAmount(this);calChargeAmount(this);clearPrpLctext();">
											">
										</td>
										<td class="input" style="width: 15%">
											<input name="prpLchargeChargeName" class="codename" style="width: 100%" value="<bean:write name='chargedtox' property='chargeName'/>"
												ondblclick="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'ChargeCode','-1,0','Y','N');"
												onkeyup="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');" onblur=" checkChargeCode(this);checkChargeAmount(this);calChargeAmount(this);clearPrpLctext();">
											">
										</td>
										<%-- modify by wangliguang 20080625 end --%>
										<td class="input" style="width: 5%">
											<select name="prpLchargePayObjectType" class='common' style="width: 50px">
												<option value="B" <logic:equal name='chargedtox' property='payObjectType' value="B">selected</logic:equal>>
													<s:text name="compensate.external" />
												</option>
												<!-- 外部 -->
												<option value="A" <logic:equal name='chargedtox' property='payObjectType' value="A">selected</logic:equal>>
													<s:text name="compensate.internal" />
												</option>
												<!-- 内部 -->
											</select>
										</td>
										<td class="input" style="width: 8%">
											<input name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="<bean:write name='chargedtox' property='payObjectCode'/>">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="<bean:write name='chargedtox' property='payObjectName'/>"
												ondblclick="code_CodeSelect(this, 'payObject','-1,0','Y','N');" onchange="code_CodeChange(this, 'payObject','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'payObject','-1,0','Y','N');"
												onblur="clearPrpLctext();">
										</td>
										<td class="input" style="width: 6%">
											<input name="prpLchargeCurrency" class="readonly" style="width: 100%" value="<bean:write name='chargedtox' property='currency'/>">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargeChargeReport" class="input" style="width: 100%" value="<bean:write name='chargedtox' property='chargeReport'/>"
												onchange="calChargeAmount(this);setRealPay();clearPrpLctext();" onblur="checkChargeAmount(this);">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargeChargeAmount" class="input" style="width: 100%" value="<bean:write name='chargedtox' property='chargeAmount'/>"
												onchange="calChargeAmount(this);setRealPay();clearPrpLctext();" onblur="checkChargeAmount(this);">
											<input name="prpLchargeSumRealPay" type="hidden" class='readonly' readonly style="width: 100%" value="<bean:write name='chargedtox' property='sumRealPay'/>">
											<input type='hidden' name="prpLchargeExceptDeductiblePay" value="<bean:write name='chargedtox' property='exceptDeductiblePay'/>">
											<input type='hidden' name="prpLchargeExceptDeductibleRate" value="<bean:write name='chargedtox' property='exceptDeductibleRate'/>">
											<input type='hidden' name="prpLchargeAmount">
											<input name="prpLchargeFlag" type="hidden" value="<bean:write name='chargedtox' property='flag'/>">
										</td>
										<!-- 
                <td class="input" style="width:16%"></td>
                -->
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonChargeDelete" class=smallbutton onclick="deleteRow2(this,'Charge');deleteRow(this,'Charge');" value="-" style="cursor: hand">
												<input type="hidden" name="prpLchargeFlag">
											</div>
										</td>
									</tr>
									<%    indexCharge++;%>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
