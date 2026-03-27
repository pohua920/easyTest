<%--
****************************************************************************
* DESC       ：添加费用赔款信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@include file="/common/taglibs.jsp"%>
<script language='javascript'>
    function viewDangerUnitCompensateCharge(field){
        for ( var i = 1; i < fm.prpLchargeSerialNo.length; i++) {
            if (fm.prpLchargeDangerNo[i] == field) {
                var count = i;
                var policyNo = fm.policyno.value;
                var damageDate = fm.damageStartDate.value;
                var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateCharge";
                //查看危險單位信息
                window.open(submitStr, i18n.title.compensateEidt.dangerUnitInformation, 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
            }
        }
    }
   /**
    * 支付方式切换
    */
    function chargeOwnerShipChange(field) {
        var $chargeObject = findPageCodeObject($(field).closest("table").get(0),"Charge");
        if (field.value == "B") { //汇款
            $chargeObject.find("span[name='spanCutBack']").hide(); //隐藏禁背
            $chargeObject.find("tr[name='bankInfo']").show(); //显示支付账户信息
            $chargeObject.find("tr[name='areaInfo']").show(); 
        } else if (field.value == "Q") { //支票
            $chargeObject.find("span[name='spanCutBack']").show();
            $chargeObject.find("tr[name='bankInfo']").hide();
            $chargeObject.find("tr[name='areaInfo']").show(); 
        } else if (field.value == "C") { //现金
            alert("費用支付方式未開放現金支付方式。");
            $(field).val("B");
            chargeOwnerShipChange(field);
            //$chargeObject.find("span[name='spanCutBack']").hide();
            //$chargeObject.find("tr[name='bankInfo']").hide();
            //$chargeObject.find("tr[name='areaInfo']").hide(); 
        }
    }
   
    function afterDeleteCharge(Charge,btnField,pageCode,csFieldName,psFieldName){
        calFund();
    }
    /***
     * 計算并設置費用的費用金額（NTD）
     */
    function setChargeAmountNTD(field){
        var $chargeObject = findPageCodeObject(field,"Charge");
        var $chargeAmountNTD = $chargeObject.find(":input[name='prpLchargeChargeAmountNTD']");//費用金額（NTD）
        var chargeAmount = initValue($chargeObject.find(":input[name='prpLchargeChargeAmount']"),0);//支付費用金額
        var exchRate = parseFloat($chargeObject.find(":input[name='prpLchargeExchRate']").val());
        $chargeAmountNTD.val(Math.round(chargeAmount*exchRate));
        calFund();
    }
    /***
     * 代扣費用序號處理
     */
    function checkFeeSerialNo(field){
        var $chargeObject = findPageCodeObject(field,"Charge");
        var message = "";
        if($.trim(field.value).length > 0){
            var serialNo = $chargeObject.find(":input[name='prpLchargeSerialNo']").val();
            if(field.value == serialNo){
                message = "代扣費用序號不能為本記錄自身的序號！";
            }else{
                var $t = $("#Charge").find(":input[name='prpLchargeSerialNo'][value='"+field.value+"']");
                if($t.length == 0){
                    message = "沒有序號為‘"+field.value+"’的記錄！";
                }else{
                    var $o = findPageCodeObject($t[0],"Charge");
                    var chargeReport = initValue($o.find(":input[name='prpLchargeChargeReport']"),0);
                    var chargeAmount = initValue($o.find(":input[name='prpLchargeChargeAmount']"),0);
                    var fee = chargeReport - chargeAmount;//可以代扣的费用额度
                    if(fee <= 0){
                        message = "序號‘"+field.value+"’的記錄可代扣的費用金額為0";
                    }else{
                        var currency = $o.find(":input[name='prpLchargeCurrency']").val();
                        $chargeObject.find(":input[name='prpLchargeCurrency']").val(currency);
                        $chargeObject.find(":input[name='prpLchargeChargeReport']").val(getFormatValueByCurrency(fee,currency));
                        $chargeObject.find(":input[name='prpLchargeChargeAmount']").val(getFormatValueByCurrency(fee,currency));
                        $chargeObject.find(":input[name='prpLchargeCurrency']").val(currency);
                        var rate = $o.find(":input[name='prpLchargeExchRate']").val();
                        $chargeObject.find(":input[name='prpLchargeExchRate']").val(rate);
                        $chargeObject.find(":input[name='prpLchargeChargeAmountNTD']").val(Math.round(fee*rate));
                        calFund();
                    }
                }
            }
            if(message.length > 0){
                alert(message);
                field.value = "";
                try{
                    $(field).focus();
                  }catch(e){
                      
                  }
            }
        }
    }
    /***
     *当前记录发生改变时，代扣本条费用的记录序号重新填写
     * 清空代扣費用序號處理
     */
    function calChargeFee(field){
        var $chargeObject = findPageCodeObject(field,"Charge");
        var currency = $chargeObject.find(":input[name='prpLchargeCurrency']").val();//当前币别
        var $chargeReport = $chargeObject.find(":input[name='prpLchargeChargeReport']");
        var $chargeAmount = $chargeObject.find(":input[name='prpLchargeChargeAmount']");
        var chargeReport = initValue($chargeReport,0,currency);
        var chargeAmount = initValue($chargeAmount,0,currency);
        if(chargeReport < chargeAmount){
            if(field.name=="prpLchargeChargeReport"){
                alert("費用金額不得低於實際費用金額！");
                $chargeReport.val($chargeAmount.val());
            }else if(field.name=="prpLchargeChargeAmount"){
                alert("實際費用金額不得超過費用金額！");
                $chargeAmount.val($chargeReport.val());
                setChargeAmountNTD(field);
            }
        }
        var currSerialNo = $chargeObject.find(":input[name='prpLchargeSerialNo']").val();
        var $feeSerialNo = $("#Charge").find(":input[name='prpLchargeFeeSerialNo'][value='"+currSerialNo+"']");
        if($feeSerialNo.length > 0){
            if(parseFloat($chargeReport.val()) > parseFloat($chargeAmount.val())){
                checkFeeSerialNo($feeSerialNo[0]);
            }else{
                $feeSerialNo.val("");
            }
        }
    }
</script>
<table class="common" style="display: none" id="Charge_Data" cellspacing="1" cellpadding="5">
    <tbody>
        <tr>
            <td class="input" style="width: 3%;">
                <input type="text" name="prpLchargeSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>" readonly="readonly" class="readonly">
            </td>
            <td class="input" style="width: 3%;">
                <input type="text" name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);" onchange="viewDangerUnitCompensateCharge(this);">
            </td>
            <td class="input" style="width: 5%;">
                <input type="text" name="prpLchargeKindCode" class="codecode" ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                    onchange="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
                    onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
            </td>
            <td class="input" style="width: 11%;">
                <input type="text" name="prpLchargeKindName" class="codename" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                    onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
                    onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="setChargeInput(this);">
                 <input type="hidden" name="prpLchargeItemKindNo" value="0">
            </td>
            <td class="input" align="center" style="width: 4%;">
                <input type="text" name="prpLchargeChargeCode" class="codecode" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                    onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                    onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this,'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setChargeInput(this);">
            </td>
            <td class="input" align="center" style="width: 10%;">
                <input type="text" name="prpLchargeChargeName" class="codename" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
                    onkeyup="clearPayObject(this);clearPayment(this);setChargeInput(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
                    onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setChargeInput(this);">
            </td>
            <td class="input" style="width: 5%;">
                <s:select name="prpLchargePayObjectType" value="B" cssClass="common" listKey="key" listValue="value" list="#request.payObjectTypeList" />
            </td>
            <td class="input" style="width: 6%;">
                <input type="text" name="prpLchargePayObjectCode" class="readonly" readonly value="">
            </td>
            <td class="input" style="width: 10%;">
                <input type="text" name="prpLchargePayObjectName" class="codename" value="" ondblclick="clearPayment(this);getPayObject(this);" onchange="clearPayment(this);getPayObject(this);" onkeyup="clearPayment(this);getPayObject(this);"
                    onblur="">
            </td>
            <td class="input" style="width: 8%;">
                <input type="text" name="prpLchargeChargeReport" value="0" class="input" onchange="calChargeFee(this);">
            </td>
            <td class="input" align="center" style="width: 8%;">
                <input type="text" name="prpLchargeChargeAmount" value="0" class="input" onchange="setChargeAmountNTD(this);calChargeFee(this);">
            </td>
            <td class="input" align="center" style="width: 5%;">
                <select name="prpLchargeCurrency" class="input" onchange="getPrpLchargeExchRate(this);calChargeFee(this);">
                    <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                        <option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                    </c:forEach>
                </select>
            </td>
            <td class="input" align="center" style="width: 8%;">
                <input type="text" name="prpLchargeExchRate" value="1" class="input" onchange="setChargeAmountNTD(this);calChargeFee(this);">
            </td>
            <td class="input" align="center" style="width: 8%;">
                <input type="text" name="prpLchargeChargeAmountNTD" class="common" readonly="readonly" value="0">
            </td>
            <td class="input" align="center" style="width: 3%;">
                <input type="text" name="prpLchargeFeeSerialNo" class="input" maxlength="2" onchange="checkFeeSerialNo(this);">
            </td>
            <td class="input" align="center" style="width: 3%;">
                <input type="hidden" name="prpLchargeSumRealPay" value="0">
                <input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo')" value="-" style="cursor: hand;width: 25px">
                <input type="hidden" name="prpLchargeFlag">
            </td>
        </tr>
        <tr>
            <td colspan="16" >
                <table class="common" style="width: 100%">
                    <tr>
                        <td class="title" style="width: 15%">費用支付方式：</td>
                        <td class="input" style="width: 18%">
                            <select name="prpLchargeOwnerShip" onchange="chargeOwnerShipChange(this);">
                                <option value="B" selected >
                                    <s:text name="compensate.remittance" /><%-- 汇款--%>
                                </option>
                                <option value="Q" >
                                    <s:text name="compensate.agentInfo.cheque" /><%-- 支票--%>
                                </option>
                                <option value="C" >
                                    <s:text name="compensate.agentInfo.cash" /><%-- 现金--%>
                                </option>
                            </select>
                            <input name="prpLchargeCurrencyForPayObject" type="hidden" value="${LOCAL_CURRENCY }">
                            <input name="prpLchargeAccountCurrency" type="hidden" value="${LOCAL_CURRENCY }">
                        </td>
                        <td class="title" style="width: 15%" ></td>
                        <td class="input" style="width: 18%" ></td>
                        <td class="title" style="width: 15%" ></td>
                        <td class="input" style="width: 18%" ></td>
                    </tr>
                    <tr>
                        <td class="title" style="width: 15%">賠付對象：</td>
                        <td class="input" style="width: 18%">
                            <input type="text" class="input" name="prpLchargeOwnerName" maxlength="50" value="">
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                        <td class="title" style="width: 15%">賠付類型：</td>
                        <td class="input" style="width: 18%">
                            <s:select name="prpLchargePaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" headerKey="" headerValue=""></s:select>
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                        <td class="title" style="width: 15%" ><s:text name="common.compensate.payeePhone"/>：</td>
                        <td class="input" style="width: 18%" ><input name="prpLchargeBeneficiaryPhone" class="input" value="" style="width: 120px"/></td>
                    </tr>
                    <tr>
                        <td class="title" style="width: 15%">證件類型：</td>
                        <td class="input" style="width: 18%">
                            <c:set var="tempCertificateCode" value="01" />
                            <s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
                        </td>
                        <td class="title" style="width: 15%">證件號碼：</td>
                        <td class="input" style="width: 18%">
                            <input type="text" class="input" name="prpLchargeUniformNo" value="">
                        </td>
                        <td class="title" style="width: 15%">
                            <span name="spanCutBack" style="display:none">禁背：</span>
                        </td>
                        <td class="input" style="width: 18%">
                            <span name="spanCutBack" style="display:none"> 
                              <select name="prpLchargeCutBack">
                                    <option value="0" >
                                        <s:text name="否" />
                                    </option>
                                    <option value="1" selected>
                                        <s:text name="是" />
                                    </option>
                              </select>
                            </span>
                        </td>
                    </tr>
                    <tr name="bankInfo">
                        <td class="title" style="width: 15%">總行代號：</td>
                        <td class="input" style="width: 18%">
                            <input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="">
                        </td>
                        <td class="title" style="width: 15%">总行名稱：</td>
                        <td class="input" style="width: 18%">
                            <input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="">
                        </td>
                        <td class="title" style="width: 15%">匯款帳號：</td>
                        <td class="input" style="width: 18%">
                            <input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="">
                        </td>
                    </tr>
                    <tr name="bankInfo">
                        <td class="title" style="width: 15%">分行代號：</td>
                        <td class="input" style="width: 18%">
                            <input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="">
                        </td>
                        <td class="title" style="width: 15%">分行名稱：</td>
                        <td class="input" style="width: 18%">
                            <input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly" value="">
                        </td>
                        <td class="title" style="width: 34%" colspan="2" align="center">
                            <input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUser(this);" style="width: 180px;">
                            <%--輸入费用支付帳户信息 --%>
                        </td>
                    </tr>
                    <tr name="areaInfo">
                    <!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                    <!--  \claim\webapp\claim\pages\commonLiab\compensate\LiabCompensateChargeEdit.jsp -->
                        <td class="title" style="width: 15%">郵遞區號：</td>
                        <td class="input" style="width: 18%">
                        	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                            <input type="text" class="input" name="prpLchargeAreaCode" maxlength="40" value="" maxlength="3">
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                        <td class="title" style="width: 15%">郵遞地址：</td>
                        <td class="input" style="width: 52%" colspan="3">
                            <input type="text" class="input" name="prpLchargeCourierAddress" value="">
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr height="2" bgcolor="block">
            <td colspan="16"></td>
        </tr>
    </tbody>
</table>
<table class="common" align="center">
    <tr>
        <td class="common" colspan="4" style="text-align: left">
            <img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge)">
            <b><s:text name="compensate.feePaymentInfo" /></b>
            <br>
            <span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0">
                <table class="common" style="width: 100%" id="Charge">
                    <thead>
                        <tr>
                            <td class="centertitle" width="3%">序號</td>
                            <td class="centertitle" width="3%"><s:text name="claim.dangeSerialNum" /><%-- 危险单位序号 --%></td>
                            <td class="centertitle" width="5%">險種代碼</td>
                            <td class="centertitle" width="10%"><s:text name="certainLoss.prpLacciCheck.riskCName" /><%-- 险种名称 --%></td>
                            <td class="centertitle" width="4%"><s:text name="compensate.costCode" /><%--费用代码  --%></td>
                            <td class="centertitle" width="10%"><s:text name="compensate.costName" /><%--费用名称  --%></td>
                            <td class="centertitle" width="5%"><s:text name="compensate.paymentType" /><%-- 支付类别 --%></td>
                            <td class="centertitle" width="6%"><s:text name="quickCase.payObjectCode" /><%-- 支付对象编码 --%></td>
                            <td class="centertitle" width="10%"><s:text name="compensate.payNameObject" /><%-- 支付对象名称 --%></td>
                            <td class="centertitle" width="8%">費用金額</td>
                            <td class="centertitle" width="8%">實際費用</td>
                            <td class="centertitle" width="5%">賠付幣別</td>
                            <td class="centertitle" width="8%">匯率</td>
                            <td class="centertitle" width="8%">費用金額（NTD）</td>
                            <td class="centertitle" width="3%">代扣費用序號</td>
                            <td class="centertitle" width="3%">&nbsp;</td>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <td class="title" colspan="15" style="width: 96%">
                                <s:text name="prompt.schedule.addRename11" /><%-- (按"+"号键增加费用信息，按"-"号键删除信息)--%>
                            </td>
                            <td class="title" align="right" style="width: 4%">
                                <div align="center">
                                    <input type="button" value="+" onclick="insertRow('Charge',this,'prpLchargeSerialNo')" class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach var="chargedtox" items="${requestScope.prpLcharge.prpLchargeList}" varStatus="status">
                            <tr>
                                <td class="input" style="width: 3%;">
                                    <input type="text" name="prpLchargeSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>" value="${chargedtox.id.serialNo}" readonly="readonly" class="readonly">
                                </td>
                                <td class="input" style="width: 3%;">
                                    <input type="text" name="prpLchargeDangerNo" class="codecode" onClick="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);" onchange="viewDangerUnitCompensateCharge(this);" value="${chargedtox.dangerNo}" >
                                </td>
                                <td class="input" style="width: 5%;">
                                    <input type="text" name="prpLchargeKindCode" class="codecode" ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                        onchange="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
                                        onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" value="${chargedtox.kindCode}">
                                </td>
                                <td class="input" style="width: 11%;">
                                    <input type="text" name="prpLchargeKindName" class="codename" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                        onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
                                        onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="setChargeInput(this);" value="${chargedtox.kindName}">
                                    <input type="hidden" name="prpLchargeItemKindNo" value="${chargedtox.itemKindNo}">
                                </td>
                                <td class="input" align="center" style="width: 4%;">
                                    <input type="text" name="prpLchargeChargeCode" class="codecode" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);" 
                                        onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                                        onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this,'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);" value="${chargedtox.chargeCode}">
                                </td>
                                <td class="input" align="center" style="width: 10%;">
                                    <input type="text" name="prpLchargeChargeName" class="codename" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" 
                                        onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
                                        onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" value="${chargedtox.chargeName}">
                                </td>
                                <td class="input" style="width: 5%;">
                                    <c:set var="tempSelectedValue" value="${chargedtox.payObjectType}" />
                                    <s:select name="prpLchargePayObjectType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.payObjectTypeList" />
                                </td>
                                <td class="input" style="width: 6%;">
                                    <input type="text" name="prpLchargePayObjectCode" class="readonly" readonly value="${chargedtox.payObjectCode}" >
                                </td>
                                <td class="input" style="width: 10%;">
                                    <input type="text" name="prpLchargePayObjectName" class="codename" value="${chargedtox.payObjectName}" ondblclick="clearPayment(this);getPayObject(this);" onchange="clearPayment(this);getPayObject(this);" onkeyup="clearPayment(this);getPayObject(this);" >
                                </td>
                                <td class="input" style="width: 8%;">
                                    <input type="text" name="prpLchargeChargeReport" value="<fmt:formatNumber value='${chargedtox.chargeReport}' pattern='#0.##'/>" class="input" onchange="calChargeFee(this);">
                                </td>
                                <td class="input" align="center" style="width: 8%;">
                                    <input type="text" name="prpLchargeChargeAmount" value="<fmt:formatNumber value='${chargedtox.chargeAmount}' pattern='#0.##'/>" class="input" onchange="setChargeAmountNTD(this);calChargeFee(this);">
                                </td>
                                <td class="input" align="center" style="width: 5%;">
                                    <select name="prpLchargeCurrency" class="common" onchange="getPrpLchargeExchRate(this);calChargeFee(this);">
                                        <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                            <option value="${tempMap.key}" <c:if test="${tempMap.key==chargedtox.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="input" align="center" style="width: 8%">
                                    <input type="text" name="prpLchargeExchRate" class="input" value="${chargedtox.exchRate}" onchange="setChargeAmountNTD(this);calChargeFee(this);">
                                </td>
                                <td class="input" align="center" style="width: 8%">
                                    <input type="text" name="prpLchargeChargeAmountNTD" class="common" readonly="readonly" value="0" >
                                </td>
                                <td class="input" align="center" style="width: 3%">
                                    <input type="text" name="prpLchargeFeeSerialNo" class="common" value="${chargedtox.feeSerialNo}" maxlength="2" onchange="checkFeeSerialNo(this);">
                                </td>
                                <td class="input" align="center" style="width: 3%">
                                    <div>
                                        <input type="hidden" name="prpLchargeSumRealPay" value="0">
                                        <input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo')" value="-" style="cursor: hand;width: 25px">
                                        <input type="hidden" name="prpLchargeFlag">
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="16">
                                    <table class="common" style="width: 100%">
                                        <tr>
                                            <td class="title" style="width: 15%">費用支付方式：</td>
                                            <td class="input" style="width: 18%">
                                                <select name="prpLchargeOwnerShip" onchange="chargeOwnerShipChange(this);">
                                                    <option value="B" <c:if test="${pageScope.chargedtox.ownerShip=='B'}"><c:out value="selected"/></c:if>>
                                                        <s:text name="compensate.remittance" /><%-- 汇款 --%>
                                                    </option>
                                                    <option value="Q" <c:if test="${pageScope.chargedtox.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
                                                        <s:text name="compensate.agentInfo.cheque" /><%-- 支票 --%>
                                                    </option>
                                                    <option value="C" <c:if test="${pageScope.chargedtox.ownerShip=='C'}"><c:out value="selected"/></c:if>>
                                                        <s:text name="compensate.agentInfo.cash" /><%-- 现金--%>
                                                    </option>
                                                </select>
                                                <input name="prpLchargeCurrencyForPayObject" type="hidden" value="${chargedtox.prpLpayObjectInfo.currency}">
                                                <input name="prpLchargeAccountCurrency" type="hidden" value="${chargedtox.prpLpayObjectInfo.accountCurrency}">
                                            </td>
                                            <td class="title" style="width: 15%" ></td>
                                            <td class="input" style="width: 18%" ></td>
                                            <td class="title" style="width: 15%" ></td>
                                            <td class="input" style="width: 18%" ></td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 15%">賠付對象：</td>
                                            <td class="input" style="width: 18%">
                                                <input type="text" class='input' name="prpLchargeOwnerName" maxlength="50" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>">
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                            <td class="title" style="width: 15%">賠付類型：</td>
                                            <td class="input" style="width: 18%">
                                                <c:set var="tempSelectedValue" value='${chargedtox.prpLpayObjectInfo.paymentKind}' />
                                                <s:select name="prpLchargePaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" value="#attr.tempSelectedValue"></s:select>
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                            <td class="title" style="width: 15%" ><s:text name="common.compensate.payeePhone"/>：</td>
                                            <td class="input" style="width: 18%" ><input name="prpLchargeBeneficiaryPhone" class="input" value="${prpLpayObject.beneficiaryPhone}" style="width: 120px"/></td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 15%">證件類型：</td>
                                            <td class="input" style="width: 18%">
                                                <c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
                                                <s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
                                            </td>
                                            <td class="title" style="width: 15%">證件號碼：</td>
                                            <td class="input" style="width: 18%">
                                                <input type="text" class='input' name="prpLchargeUniformNo" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if>>禁背：</span>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if>> 
                                                  <select name="prpLchargeCutBack">
                                                        <option value="0" <c:if test="${chargedtox.prpLpayObjectInfo.cutBack == '0'}">selected</c:if>>
                                                            <s:text name="否" />
                                                        </option>
                                                        <option value="1" <c:if test="${chargedtox.prpLpayObjectInfo.cutBack == '1'}">selected</c:if>>
                                                            <s:text name="是" />
                                                        </option>
                                                  </select>
                                                </span>
                                            </td>
                                        </tr>
                                        <tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
                                            <td class="title" style="width: 15%">總行代號：</td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankCode}'/>">
                                            </td>
                                            <td class="title" style="width: 15%">总行名稱：</td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankName}'/>">
                                            </td>
                                            <td class="title" style="width: 15%">匯款帳號：</td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.accountCode}'/>">
                                            </td>
                                        </tr>
                                        <tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
                                            <td class="title" style="width: 15%">分行代號：</td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankCode}'/>">
                                            </td>
                                            <td class="title" style="width: 15%">分行名稱：</td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankName}'/>">
                                            </td>
                                            <td class="title" style="width: 34%" colspan="2" align="center">
                                                <input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUser(this);" style="width: 180px;">
                                                <%--輸入费用支付帳户信息 --%>
                                            </td>
                                        </tr>
                                        <tr name="areaInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
                                          <!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                          <!--  \claim\webapp\claim\pages\commonLiab\compensate\LiabCompensateChargeEdit.jsp 2-->  
                                            <td class="title" style="width: 15%">郵遞區號：</td>
                                            <td class="input" style="width: 18%">
                                            	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                                <input type="text" class='input' name="prpLchargeAreaCode" maxlength="40" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>" maxlength="3">
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                            <td class="title" style="width: 15%">郵遞地址：</td>
                                            <td class="input" style="width: 52%" colspan="3">
                                                <input type="text" class='input' name="prpLchargeCourierAddress" value="<c:out value='${chargedtox.prpLpayObjectInfo.courierAddress}'/>">
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr height="2" bgcolor="block">
                                <td colspan="16"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </span>
        </td>
    </tr>
</table>
