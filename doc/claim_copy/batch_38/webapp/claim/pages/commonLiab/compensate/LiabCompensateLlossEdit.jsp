<%--
****************************************************************************
* DESC       ：赔付标的信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page import="com.sinosoft.claim.schema.model.PrpCitemKind"%>
<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
    function viewDangerUnitCompensateLloss(field){
        for ( var i = 1; i < fm.prpLlossDtoSerialNo.length; i++) {
            if (fm.prpLlossDtoDangerNo[i] == field) {
                var count = i;
                var policyNo = fm.policyno.value;
                var damageDate = fm.damageStartDate.value;
                var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateLloss";
                //field.value = "";
                //查看危险单位讯息 
                window.open(submitStr, i18n.title.compensateEidt.dangerUnitInformation, 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
            }
        }
    }
    var damageKind = new Array();
    var damageItemKind = new Array();
    var damageItemAmount = new Array();
    var damageItemAmountDisplay = new Array();
    var i = 0;
</script>
<c:forEach var="prpCitemKindTemp" items="${prpCitemKindList}" varStatus="prpCitemKind_status">
<script language="javascript">
    i = parseInt('${prpCitemKind_status.index}');
    damageKind[i] = '${prpCitemKindTemp.kindCode }';
    damageItemKind[i] = '${prpCitemKindTemp.itemCode }';
    damageItemAmount[i] = '<fmt:formatNumber value="${prpCitemKindTemp.amount }" pattern="#"/>';
    damageItemAmountDisplay[i] = '<fmt:formatNumber value="${prpCitemKindTemp.amount }" pattern="#"/>';
</script>
</c:forEach>
<script language="javascript">
    function calClaimRate(field){
        var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
        var amount = parseFloat($prpLlossObject.find(":input[name='prpLlossDtoAmount']").val());
        var itemValue = initValue($prpLlossObject.find(":input[name='prpLlossDtoItemValue']"),0,'<%=ConstantCodes.LOCAL_CURRENCY%>');
        if(amount !=0 && itemValue !=0){
            var $claimRate = $prpLlossObject.find(":input[name='prpLlossDtoClaimRate']");
            $claimRate.val(Math.round((itemValue/amount)*100));
        }
    }
	/***
	 *包裝下險別選擇的函數，選擇險別的時候清空下標的的訊息
	 */
	function prpLlossKindCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
		var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
		$prpLlossObject.find(":input[name='prpLlossDtoItemCode']").val("");
		$prpLlossObject.find(":input[name='prpLlossDtoLossName']").val("");
		$prpLlossObject.find(":input[name='prpLlossDtoItemKindNo']").val("");
		$prpLlossObject.find(":input[name='prpLlossDtoAmount']").val(0);
		$prpLlossObject.find(":input[name='prpLlossDtoItemValue']").val(0);
		code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition);
	}
	/***
	 *包裝下標的選擇的函數，選擇標的時的時候，若有險別，則加上險別的條件
	 */
	function prpLlossItemCodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition){
		var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
		var kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val();
		if($.trim(kindCode).length!=0){
			otherCondition += "|"+kindCode;
			if("prpLlossDtoItemCode" == field.name){
				codeRelation = codeRelation.substr(0,9);
			}else if("prpLlossDtoLossName" == field.name){
				codeRelation = codeRelation.substr(0,10);//codecode 是-1，多出一位
			}
		}else{
			otherCondition += "|";
		}
		otherCondition += '|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value;
		code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition);
	}
</script>
<script language="javascript">
    /**
     *删除本条标的赔付之後的处理
     */
    function afterDeletelLoss(deletObject,btnField,pageCode,csFieldName){
        var serialNoStr = $(deletObject).find(":input[name$='PayObjectSerialNo']").val();
        calPayAmount(serialNoStr);//处理赔付对象序号对应的赔付对象讯息
        calFund();
    }
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLlossDtoKindName'],:input[name='prpLlossDtoLossName'],:input[name='prpLlossDtoPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<c:if test="${coinsFlag=='1'||coinsFlag=='2'||coinsFlag=='3'}">
    <table class="common" align="center" style="display: none;">
        <tr>
            <td class="title" colspan=14 style="color: red">
                <s:text name="compensate.allLine" /><%-- 共保业务，录入损失时请录入总损失；录入费用时请录入我司分摊的费用  --%>
            </td>
        </tr>
    </table>
</c:if>
<table class="common" style="display: none" id="lLoss_Data" cellspacing="1" cellpadding="0" >
    <tbody>
        <tr name="prpLlossObject">
            <td class="input" style="width: 5%">
                <div align="center">
                    <input class="readonlyNo" readonly name="prpLlossDtoSerialNo" style="width: 20px" description="<s:text name='regist.prpLregist.serialNo'/>">
                    <input type="hidden" name="prpLlossDtoSumRest" value="0">
                    <input type='hidden' name="prpLlossDtoLicenseNo">
                    <input type='hidden' name='prpLlossDtoFamilyNo'>
                    <input type='hidden' name="prpLlossDtoFeeTypeCode">
                    <input type='hidden' name="prpLlossDtoFeeTypeName">
                    <input type='hidden' name='prpLlossDtoFamilyName'>
                    <input type='hidden' name='prpLlossDtoItemAddress'>
                    <input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
                    <input type='hidden' name='prpLlossDtoDepreRate'>
                    <input type='hidden' name='prpLlossDtoCurrency1' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type='hidden' name='prpLlossDtoCurrency2' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type='hidden' name='prpLlossDtoCurrency3' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type='hidden' name='prpLlossDtoCurrency4' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type='hidden' name='prpLlossDtoFlag'>
                    <input type='hidden' name='prpLlossDtoUnit'>
                    <input type="hidden" name="prpLlossDtoLossQuantity">
                    <input type="hidden" name="prpLlossDtoUnitPrice">
                    <input type="hidden" name="prpLlossDtoIndemnityDutyRate">
                </div>
            </td>
            <td class="subformtitle" style="width: 90%">
                <table class="common" cellpadding="0" cellspacing="1" style="width: 100%">
                    <tbody>
                        <tr>
                            <td class="title"><s:text name="claim.dangeSerialNum" />：<%-- 危险单位序号 --%></td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoDangerNo" class="codecode" style="width: 60px" value="1" onClick="viewDangerUnitCompensateLloss(this);" onkeyup="viewDangerUnitCompensateLloss(this);"
                                    onchange="viewDangerUnitCompensateLloss(this);">
                            </td>
                            <td class="title"><s:text name="undwrt.Risks" />：<%-- 险别 --%></td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoKindCode" class="codecode" style="width: 50px" onblur="clearPrpLloss(this);" 
                                    ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                    onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                    onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"/>
                                <input type="text" name=prpLlossDtoKindName class="codename" style="width: 150px" onblur="clearPrpLloss(this);" 
                                    ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                    onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                    onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
                                <input type='hidden' name='prpLlossItemKindNo'>
                                <img src="/claim/images/bgMarkMustInput.jpg">
                            </td>
                        </tr>
                        <tr>
                            <td class="title"><s:text name="compensate.lossSubject" />：<%-- 损失标的 --%></td>
                            <td class="input" colspan="3">
                                <input type="text" name="prpLlossDtoItemCode" class="codecode" style="width: 50px" 
                                	ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);"
                                    onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);" >
                                <input type="text" name="prpLlossDtoLossName" class="codename" style="width: 300px" 
                                	ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);"
                                    onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);" >
                                <input type='hidden' name='prpLlossDtoItemKindNo'>
                            </td>
                        </tr>
                        <tr>
                            <td class="title"><s:text name="db.prpLloss.amount" />：<%--保险金额  --%></td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoAmount" value="0" class="readonly" class="readonly" readonly style="width: 120px">
                            </td>
                            <td class="title">保險價值：</td>
                            <td class="input">
                                <input name="prpLlossDtoItemValue" type="text" class="input" style="width: 120px" value="0" onchange="calClaimRate(this);">
                            </td>
                        </tr>
                        <tr>
                            <td class="title">核定幣別：</td>
                            <td class="input">
                                <input type="text" name="Currency" class="common" readonly="readonly" value="<%=ConstantCodes.LOCAL_CURRENCY%>" style="width: 40px">
                                <input type="text" name="CurrencyName" class="readonly" readonly value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>" style="width: 80px">
                            </td>
                            <td class="title">累計賠付金額：</td>
                            <td class="input">
                                <input type="text" name="SumPay" class="common" readonly="readonly" value="0" style="width: 120px">
                            </td>
                        </tr>
                        <tr>
                            <td class="title">核定損失：</td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoSumLoss" class="input" value="0" onchange="calRealpay(this);"  style="width: 120px">
                            </td>
                            <td class="title"><s:text name="db.prpLloss.deductible" />：<%-- 自負額 --%></td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoDeductible" class="input" value="0" onchange="calRealpay(this);"  style="width: 120px">
                            </td>
                        </tr>
                        <tr>
                            <td class="title"><s:text name="db.prpLloss.claimRate" />（%）：<%-- 賠付比例 --%></td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoClaimRate" class="input" value="100" title="賠付比例" style="width: 120px" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">
                            </td>
                            <td class="title">自負額比率（%）：<%-- 自負額比率--%></td>
                            <td class="input">
                                <input type="text" name="prpLlossDtoDeductibleRate" class="input" title="自負額比率" value="0" style="width: 120px" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">
                            </td>
                        </tr>
                        <tr>
                            <td class="title">賠付幣別：</td>
                            <td class="input">
                                <select name="prpLlossDtoCurrency" class="input" style="width: 180px" onchange="getPrpLlossDtoExchRate(this);">
                                    <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                        <option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}"/> - <c:out value="${tempMap.value}"/></option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td class="title">匯率：</td>
                            <td class="input"><input type="text" name="prpLlossDtoExchRate" value="1" onchange="calRealpay(this);"  class="input" style="width: 120px" readonly="readonly"></td>
                        </tr>
                        <tr>
                            <td class="title">賠償金額（NTD）：</td>
                            <td class="input"><input type="text" name="prpLlossDtoSumRealPayNTD" class="input" value="0" style="width: 120px"</td>
                            <td class="title">賠償金額：</td>
                            <td class="input"><input type="text" name="prpLlossDtoSumRealPay" readonly="readonly" value="0" class="common" style="width: 120px"></td>
                        </tr>
                        <tr>
                            <td class="title">賠付對象讯息：</td>
                            <td class="input"><input type="text" name="prpLlossDtoPayObjectSerialNo" class="common" readonly="readonly" onclick="setPayObjectSerialNo(this);" /></td>
                            <td class="title"></td>
                            <td class="input"></td>
                        </tr>
                        <tr>
                            <td class="title" >保留預估：</td>
                            <td class="input" >
                                <s:select name="prpLlossDtoReservedEstimate" list="#attr.reservedEstimateList" ></s:select>
                            </td>
                            <td class="title" ></td>
                            <td class="input" >
                            </td>
                        </tr>
                    </tbody>
                </table>
            </td>
            <td class="input" style="width: 5%">
                <div align="center">
                    <input type=button name="buttonlLossDelete" class="smallbutton" onclick="deleteRow(this,'lLoss','prpLlossDtoSerialNo')" value="-" style="cursor: hand" >
                </div>
            </td>
        </tr>
    </tbody>
</table>
<table class="common" align="center">
    <tr>
        <td class="common" style="text-align: left">
            <img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="lLossImg" onclick="showPage(this,spanlLoss)">
            <b><s:text name="財產賠付訊息" /></b><br>
            <span id="spanlLoss" style="display: none">
                <table id="lLoss" class="common" align="center" cellspacing="1" cellpadding="3">
                    <thead>
                        <tr>
                            <td class="centertitle" style="width: 5%" align="center">序號</td>
                            <td class="centertitle" style="width: 90%" align="center">內容</td>
                            <td class="centertitle" style="width: 5%" align="center">操作</td>
                            
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <td class="common" colspan="2" style="width: 95%">（按“+”號鍵增加賠付標的訊息，按“-”號刪除訊息）</td>
                            <td class="common" align="right" style="width: 5%">
                                <div align="center">
                                    <input type="button" value="+" class="smallbutton" onclick="insertRow('lLoss',this,'prpLlossDtoSerialNo')" name="buttonlLossInsert" style="cursor: hand">
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach var="tempPrpLloss" items="${prpLloss.prpLlossList}" >
                            <tr name="prpLlossObject">
                                <td class="input" style="width: 5%">
                                    <div align="center">
                                        <input class="readonlyNo" readonly name="prpLlossDtoSerialNo" style="width: 20px" description="<s:text name='regist.prpLregist.serialNo'/>" value="${tempPrpLloss.id.serialNo}">
                                        <input type="hidden" name="prpLlossDtoSumRest" value="${tempPrpLloss.sumRest}">
                                        <input type='hidden' name="prpLlossDtoLicenseNo" value="${tempPrpLloss.licenseNo}">
                                        <input type='hidden' name='prpLlossDtoFamilyNo' value="${tempPrpLloss.familyNo}">
                                        <input type='hidden' name="prpLlossDtoFeeTypeCode" value="${tempPrpLloss.feeTypeCode}">
                                        <input type='hidden' name="prpLlossDtoFeeTypeName" value="${tempPrpLloss.feeTypeName}">
                                        <input type='hidden' name='prpLlossDtoFamilyName' value="${tempPrpLloss.familyName}">
                                        <input type='hidden' name='prpLlossDtoItemAddress' value="${tempPrpLloss.itemAddress}">
                                        <input type='hidden' name='prpLlossDtoBuyDate' value="${tempPrpLloss.buyDate}">
                                        <input type='hidden' name='prpLlossDtoDepreRate' value="${tempPrpLloss.depreRate}">
                                        <input type='hidden' name='prpLlossDtoCurrency1' value="${tempPrpLloss.currency1}">
                                        <input type='hidden' name='prpLlossDtoCurrency2' value="${tempPrpLloss.currency2}">
                                        <input type='hidden' name='prpLlossDtoCurrency3' value="${tempPrpLloss.currency3}">
                                        <input type='hidden' name='prpLlossDtoCurrency4' value="${tempPrpLloss.currency4}">
                                        <input type='hidden' name='prpLlossDtoFlag' value="${tempPrpLloss.flag}">
                                        <input type='hidden' name='prpLlossDtoUnit' value="${tempPrpLloss.unit}">
                                        <input type="hidden" name="prpLlossDtoLossQuantity" value="${tempPrpLloss.lossQuantity}">
                                        <input type="hidden" name="prpLlossDtoUnitPrice" value="${tempPrpLloss.unitPrice}">
                                        <input type="hidden" name="prpLlossDtoIndemnityDutyRate" value="${tempPrpLloss.indemnityDutyRate}">
                                    </div>
                                </td>
                                <td class="subformtitle" style="width: 90%">
                                    <table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
                                        <tbody>
                                            <tr>
                                                <td class="title"><s:text name="claim.dangeSerialNum" />：<%-- 危险单位序号 --%></td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoDangerNo" class="codecode" style="width: 60px" value="${tempPrpLloss.dangerNo}" onClick="viewDangerUnitCompensateLloss(this);" onkeyup="viewDangerUnitCompensateLloss(this);"
                                                        onchange="viewDangerUnitCompensateLloss(this);">
                                                </td>
                                                <td class="title"><s:text name="undwrt.Risks" />：<%-- 险别 --%></td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoKindCode" class="codecode" value="${tempPrpLloss.kindCode}" style="width: 50px" onblur="clearPrpLloss(this);" 
                                                        ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                                        onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                                        onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"/>
                                                    <input type="text" name=prpLlossDtoKindName class="codename" value="${tempPrpLloss.kindName}" style="width: 150px" onblur="clearPrpLloss(this);" 
                                                        ondblclick="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                                        onkeyup="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                                        onchange="prpLlossKindCodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" >
                                                    <input type='hidden' name='prpLlossItemKindNo' value="${tempPrpLloss.itemKindNo}">
                                                    <img src="/claim/images/bgMarkMustInput.jpg">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="title"><s:text name="compensate.lossSubject" />：<%-- 损失标的 --%></td>
                                                <td class="input" colspan="3">
                                                    <input type="text" name="prpLlossDtoItemCode" class="codecode" style="width: 50px" value="${tempPrpLloss.itemCode}"
                                                    	ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);"
                                                        onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','0,1,2,3,4,-3,-2','Y','Y',fm.policyno.value);">
                                                    <input type="text" name="prpLlossDtoLossName" class="codename" style="width: 300px" value="${tempPrpLloss.lossName}"
                                                    	ondblclick="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);"
                                                        onkeyup="prpLlossItemCodeSelect(this, 'PolicyItemKindCode','-1,0,1,2,3,-4,-3','Y','N',fm.policyno.value);">
                                                    <input type='hidden' name='prpLlossDtoItemKindNo' value="${tempPrpLloss.itemKindNo}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="title"><s:text name="db.prpLloss.amount" />：<%--保险金额  --%></td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoAmount" value='<fmt:formatNumber value="${tempPrpLloss.amount}" pattern="#"/>' class="readonly" readonly style="width: 120px">
                                                </td>
                                                <td class="title">保險價值：</td>
                                                <td class="input">
                                                    <input name="prpLlossDtoItemValue" type="text" class="input" value="<fmt:formatNumber value='${tempPrpLloss.itemValue}' pattern='#'/>" style="width: 120px" onchange="calClaimRate(this);">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="title">核定幣別：</td>
                                                <td class="input">
                                                    <input type="text" name="Currency" class="common" value="<%=ConstantCodes.LOCAL_CURRENCY%>" style="width: 40px">
                                                    <input type="text" name="CurrencyName" class="readonly" readonly value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>" style="width: 80px">
                                                </td>
                                                <td class="title">累計賠付金額：</td>
                                                <td class="input">
                                                    <input type="text" name="SumPay" class="common" readonly="readonly" value="0" style="width: 120px">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="title">核定損失：</td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoSumLoss" class="input" value="<fmt:formatNumber value='${tempPrpLloss.sumLoss}' pattern='#0.##'/>" onchange="calRealpay(this);" style="width: 120px">
                                                </td>
                                                <td class="title"><s:text name="db.prpLloss.deductible" />：<%-- 自負額 --%></td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoDeductible" class="input" value="<fmt:formatNumber value='${tempPrpLloss.deductible}' pattern='#0.##'/>" onchange="calRealpay(this);" style="width: 120px">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="title"><s:text name="db.prpLloss.claimRate" />（%）：<%-- 賠付比例 --%></td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoClaimRate" class="input" title="賠付比例" value="<fmt:formatNumber value='${tempPrpLloss.claimRate}' pattern='#'/>" style="width: 120px" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">
                                                </td>
                                                <td class="title">自負額比率（%）：<%-- 自負額比率--%></td>
                                                <td class="input">
                                                    <input type="text" name="prpLlossDtoDeductibleRate" class="input" title="自負額比率" value="<fmt:formatNumber value='${tempPrpLloss.deductiblerate}' pattern='#'/>" style="width: 120px" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);calRealpay(this);">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="title">賠付幣別：</td>
                                                <td class="input">
                                                    <select name="prpLlossDtoCurrency" class="input" style="width: 180px" onchange="getPrpLlossDtoExchRate(this);">
                                                        <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                                            <option value="${tempMap.key}" <c:if test="${tempMap.key==tempPrpLloss.currency}">selected="selected"</c:if> ><c:out value="${tempMap.key}"/> - <c:out value="${tempMap.value}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td class="title">匯率：</td>
                                                <td class="input"><input type="text" name="prpLlossDtoExchRate" value="${tempPrpLloss.exchRate}" style="width: 120px" class="input" onchange="calRealpay(this);" readonly="readonly"></td>
                                            </tr>
                                            <tr>
                                                <td class="title">賠償金額（NTD）：</td>
                                                <td class="input"><input type="text" name="prpLlossDtoSumRealPayNTD" class="common" value="<fmt:formatNumber value='${tempPrpLloss.sumRealPay}' pattern='#'/>" style="width: 120px" style="width: 120px" readonly="readonly"></td>
                                                <td class="title">賠償金額：</td>
                                                <td class="input"><input type="text" name="prpLlossDtoSumRealPay" value="<fmt:formatNumber value='${tempPrpLloss.sumRealPay}' pattern='#0.##'/>" class="common" onblur="calRealpay(this);" style="width: 120px" readonly="readonly"></td>
                                            </tr>
                                            <tr>
                                                <td class="title">賠付對象讯息：</td>
                                                <td class="input"><input type="text" name="prpLlossDtoPayObjectSerialNo" class="common" onclick="setPayObjectSerialNo(this);" value="${tempPrpLloss.payObjectSerialNo}" style="width: 120px" readonly="readonly"/></td>
                                                <td class="title"></td>
                                                <td class="input"></td>
                                            </tr>
                                            <tr>
                                                <td class="title" >保留預估：</td>
                                                <td class="input" >
                                                    <s:select name="prpLlossDtoReservedEstimate" list="#attr.reservedEstimateList" value="#attr.tempPrpLloss.reservedEstimate"></s:select>
                                                </td>
                                                <td class="title" ></td>
                                                <td class="input" >
                                                </td>
                                            </tr>
                                        <tbody>
                                    </table>
                                </td>
                                <td class="input" style="width: 5%">
                                    <div align="center">
                                        <input type=button name="buttonlLossDelete" class="smallbutton" onclick="deleteRow(this,'lLoss','prpLlossDtoSerialNo')" value="-" style="cursor: hand">
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