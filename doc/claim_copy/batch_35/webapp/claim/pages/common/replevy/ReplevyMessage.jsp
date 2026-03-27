<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/replevy/js/replevyPrpLlossEdit.js"></script>
<script type="text/javascript">
    /**
     * 1，删除后，重新计算总追偿
     * 2，被删除那条记录涉及的赔付对象的金额需要重新计算
     */
    function afterDeleteReplevy(deletObject){
        //被删除讯息涉及的收取對象訊息
        calPayAmount($(deletObject));
        calSumPaidAll();
        calSumLoss();
    }
    
    function calSumLoss(){
        var sumLoss = 0;
        $("#spanReplevy").find(":input[name='prpLlossSumLoss']").each(function(){
            sumLoss += parseFloat(this.value);
        });
        $(":input[name='SumLoss']").val(Math.round(sumLoss));
    }
   
</script>
<div style="display: none" id="limitList">
  <c:forEach items="${requestScope.limitList}" var="obj">
      <div name="limitObject_${obj.kindCode}">
           <input type="hidden" name="limitKindCode" value="${obj.kindCode}"><%/**險別*/%>
           <input type="hidden" name="limitSumLoss" value="${obj.sumLoss}"><%/**賠付金額*/%>
           <input type="hidden" name="limitSumRealPay" value="${obj.sumRealPay}"><%/**已追償金額*/%>
      </div>
  </c:forEach>
</div>
<input type="hidden" name="familyNo" value="${requestScope.familyNo}" >
<table class="common" align="center" style="width: 100%">
    <tr>
        <td class="common" colspan="5">
            <img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanReplevy)">
            <b>預估追償金額</b><img src="${ctx}/images/bgMarkMustInput.jpg"><font color="red" id="updateInfo" style="display: none">紅色字體標註為調整前法務預估</font>
            <font color="red">
                <c:if test="${'UNDWRT' == param.editType && prpLcompensate.paySituation != '7'}"><%/** 追償審核，且非費用審核  */ %>
                    <c:choose>
                        <c:when test="${pageScope.negotiationFlag}">
                            <c:choose>
                                <c:when test="${prpLcompensate.sumLoss == 0}">
                                    &nbsp;簽結不予追償，本案賠付總金額：<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>
                                </c:when>
                                <c:otherwise>
                                    &nbsp;協商金額折讓比例 = (本案賠付總金額-法務預估總金額)/本案賠付總金額 =(<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>-<fmt:formatNumber value='${prpLcompensate.sumLoss}' pattern='#'/>)/<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/> = <fmt:formatNumber value='${(prpLclaim.sumPaid-prpLcompensate.sumLoss)/prpLclaim.sumPaid}' type="percent" maxFractionDigits="2"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${prpLcompensate.sumPaidAll+prpLcompensate.sumThisPaid == 0}">
                                    &nbsp;簽結不予追償，法務預估總金額：<fmt:formatNumber value='${prpLcompensate.sumLoss}' pattern='#'/>
                                </c:when>
                                <c:otherwise>
                                    &nbsp;追償金額折讓比例 = (法務預估總金額-已追償總金額)/法務預估總金額 =(<fmt:formatNumber value='${prpLcompensate.sumLoss}' pattern='#'/>-<fmt:formatNumber value='${prpLcompensate.sumPaidAll+prpLcompensate.sumThisPaid}' pattern='#'/>)/<fmt:formatNumber value='${prpLcompensate.sumLoss}' pattern='#'/> = <fmt:formatNumber value='${(prpLcompensate.sumLoss-(prpLcompensate.sumPaidAll+prpLcompensate.sumThisPaid))/prpLcompensate.sumLoss}' type="percent" maxFractionDigits="2"/>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </font>
            <span style="display: none">
                <table class="common" style="display: none" id="Replevy_Data">
                    <tbody>
                        <tr name="prpLlossObject">
                            <td class="input" style="width: 8%" align="center" >
                                <input type="hidden" name="prpLlossSerialNo" description="序号">
                                <input name="prpLlossKindCode" class="codecode" maxlength="3" style="width: 80%" 
                                    ondblclick="code_CodeSelect(this,'PolicyKindCodeForReplevy','0,1,2','Y','Y',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onkeyup="code_CodeSelect(this,'PolicyKindCodeForReplevy','0,1,2','Y','Y',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onchange="code_CodeChange(this,'PolicyKindCodeForReplevy','0,1,2','Y','Y',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onblur="controlPrpLloss(this);clearPrpLloss(this);" >
                            </td>
                            <td class="input" style="width: 20%" align="center" >
                                <input name="prpLlossKindName" class="codename" style="width: 401px;" ondblclick="code_CodeSelect(this,'PolicyKindCodeForReplevy','-1,0,1','Y','N',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onkeyup="code_CodeSelect(this,'PolicyKindCodeForReplevy','-1,0,1','Y','N',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onchange="code_CodeChange(this,'PolicyKindCodeForReplevy','-1,0,1','Y','N',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);" 
                                    onblur="controlPrpLloss(this);clearPrpLloss(this);">
                                <input type="hidden" name="prpLlossItemKindNo" value="0">
                            </td>
                            <td class="input" style="width: 8%" align="center" name="tdSumDefPay">
                                <input class="readonly" readonly="readonly" type="text" name="prpLlossSumDefPay" title="賠款金額" style="width: 75px" value="0">
                            </td>
                            <td class="input" style="width: 8%" align="center">
                                <input class="input" type="text" name="prpLlossSumLoss" title="法務預估金額" style="width: 75px" value="0" onfocus="cacheData(this);" onchange="controlPrpLlossOther(this);calSumLoss();">
                                <input class="input" type="hidden" name="prpLlossPreSumloss"  title="調整前法務預估金額" style="width: 75px" value="0" >
                            </td>
                            <td class="input" style="width: 8%" name="tdSumRealPay" align="center">
                                <input class="input" type="text" name="prpLlossSumRealPay" title="實際追償金額" style="width: 75px" value="0" onblur="controlPrpLlossOther(this);">
                            </td>
                            <td class="input" style="width: 6%" align="center">
                                <c:choose>
                                    <c:when test="${pageScope.registerFlag||pageScope.negotiationFlag}">
                                        <input class="readonly" type="text" style="width: 70px" name="prpLlossCurrency" value="${requestScope.LOCAL_CURRENCY}">
                                    </c:when>
                                    <c:otherwise>
                                        <select name="prpLlossCurrency" class="input" style="width: 50px" onchange="getPrpLlossDtoExchRate(this);">
                                            <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                                <option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                                            </c:forEach>
                                        </select>
                                    </c:otherwise>
                                </c:choose>
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency1" value="${requestScope.LOCAL_CURRENCY}">
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency2" value="${requestScope.LOCAL_CURRENCY}">
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency3" value="${requestScope.LOCAL_CURRENCY}">
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency4" value="${requestScope.LOCAL_CURRENCY}">
                            </td>
                            <td class="input" style="width: 8%" align="center" name="tdExchRate">
                                <input type="text" name="prpLlossDtoExchRate" value="1" class="input" readonly="readonly" style="width: 70px" onchange="setRealPayNTD(this);" value="1">
                            </td>
                            <td class="input" style="width: 8%" align="center" name="tdSumRealPayNTD">
                                <input type="text" name="prpLlossDtoSumRealPayNTD" class="common" value="0" style="width: 75px" readonly="readonly">
                            </td>
                            <td class="input" style="width: 16%" align="center" name="tdRemark">
                                <input type="text" class='input' name="prpLlossRemark">
                            </td>
                            <td class="input" style="width: 13%" align="center" name="tdPayObjectSerialNo">
                                <input type="text" class='common' readonly="readonly" name="prpLlossPayObjectSerialNo" value="" onclick="setPrpObjectinfoSerialNo(this);" />
                            </td>
                            <td class="input" style='width: 5%' align="center">
                                <div style="width: 30px">
                                    <input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'Replevy','prpLlossSerialNo');" value="-" style="cursor: hand">
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </span> 
            <span id="spanReplevy" style="display:">
                <table class="common" align="center" style="width: 100%" id="Replevy">
                    <thead>
                        <tr>
                            <td class="centertitle" style="width: 8%">險別代碼</td>
                            <td class="centertitle" style="width: 20%">險別名稱</td>
                            <td class="centertitle" style="width: 8%" name="tdSumDefPay">賠款金額</td>
                            <td class="centertitle" style="width: 8%">法務預估金額</td>
                            <td class="centertitle" style="width: 8%" name="tdSumRealPay">實際追償金額</td>
                            <td class="centertitle" style="width: 6%">幣別</td>
                            <td class="centertitle" style="width: 8%" name="tdExchRate">匯率</td>
                            <td class="centertitle" style="width: 8%" name="tdSumRealPayNTD">台幣金額</td>
                            <td class="centertitle" style="width: 16%" name="tdRemark">備註</td>
                            <td class="centertitle" style="width: 13%" name="tdPayObjectSerialNo">追償對象訊息</td>
                            <td class="centertitle" style="width: 5%">操作</td>
                        </tr>
                    </thead>
<!--                     mantis：CLM0029 ，處理人員：DP0713，需求單編號：CLM0029 追償處理險種增刪控制 -->
<%--                 <c:if test="${pageScope.registerFlag || pageScope.negotiationFlag}"><%/** 追償登錄可以顯示 */%> --%>
                    <tfoot>
                        <tr>
                            <td class="title"  <c:if test="${pageScope.registerFlag||pageScope.negotiationFlag}">colspan="5"</c:if> <c:if test="${!(pageScope.registerFlag||pageScope.negotiationFlag)}">colspan="9"</c:if>
                                 style="width: 97%">(按"+"號鍵增加預估追償金額訊息，按"-"號鍵刪除訊息)</td>
                            <td class="title" align="right" style="width: 5%">
                                <div align="center">
                                    <input type="button" value="+" class=smallbutton onclick="insertRow('Replevy',this,'prpLlossSerialNo');" name="buttonDriverInsert1" style="cursor: hand">
                                </div>
                            </td>
                        </tr>
                    </tfoot>
<!--                     mantis：CLM0029 ，處理人員：DP0713，需求單編號：CLM0029 追償處理險種增刪控制 -->
<%--                 </c:if> --%>
                    <tbody id="PrpLloss">
                    <c:forEach var="tempPrpLloss" items="${prpLloss.prpLlossList}">
                        <tr name="prpLlossObject">
                            <td class="input" style="width: 8%" align="center" >
                                <input type="hidden" name="prpLlossSerialNo" value="${tempPrpLloss.id.serialNo}">
                                <input name="prpLlossKindCode" class="codecode" style="width: 80%" maxlength="3" value="${tempPrpLloss.kindCode}"
                                    ondblclick="code_CodeSelect(this,'PolicyKindCodeForReplevy','0,1,2','Y','Y',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onkeyup="code_CodeSelect(this,'PolicyKindCodeForReplevy','0,1,2','Y','Y',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onchange="code_CodeChange(this,'PolicyKindCodeForReplevy','0,1,2','Y','Y',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onblur="controlPrpLloss(this);clearPrpLloss(this);" >
                            </td>
                            <td class="input" style="width: 20%" align="center" >
                                <input name="prpLlossKindName" class="codename" style="width: 401px;" value="${tempPrpLloss.kindName}"
                                    ondblclick="code_CodeSelect(this,'PolicyKindCodeForReplevy','-1,0,1','Y','N',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onchange="code_CodeChange(this,'PolicyKindCodeForReplevy','-1,0,1','Y','N',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onkeyup="code_CodeSelect(this,'PolicyKindCodeForReplevy','-1,0,1','Y','N',fm.policyno.value+'|'+fm.prpLcompensateClaimNo.value+'|'+fm.familyNo.value);"
                                    onblur="controlPrpLloss(this);clearPrpLloss(this);">
                                <input type="hidden" name="prpLlossItemKindNo" value="${tempPrpLloss.itemKindNo}">
                            </td>
                            <td class="input" style="width: 8%" align="center" name="tdSumDefPay">
                                <input class="readonly" readonly="readonly" type="text" name="prpLlossSumDefPay" title="賠款金額" style="width: 75px" value="<fmt:formatNumber value='${tempPrpLloss.sumDefPay}' pattern='#'/>">
                            </td>
                            <td class="input" style="width: 8%" align="center">
                                <input class="input" type="text" name="prpLlossSumLoss"  title="法務預估金額" style="width: 75px" value="<fmt:formatNumber value='${tempPrpLloss.sumLoss}' pattern='#0.##'/>" onfocus="cacheData(this);" onchange="controlPrpLlossOther(this);calSumLoss();" >
                                <input class="input" type="hidden" name="prpLlossPreSumloss"  title="調整前法務預估金額" style="width: 75px" value="<fmt:formatNumber value='${tempPrpLloss.sumLoss}' pattern='#0.##'/>" >
                            </td>
                            <td class="input" style="width: 8%" name="tdSumRealPay" align="center">
                                <input class="input" type="text" name="prpLlossSumRealPay" title="實際追償金額" style="width: 75px" value="<fmt:formatNumber value='${tempPrpLloss.sumRealPay}' pattern='#0.##'/>" onfocus="cacheData(this);" onchange="controlPrpLlossOther(this);">
                            </td>
                            <td class="input" style="width: 6%" align="center">
                                <c:choose>
                                    <c:when test="${pageScope.registerFlag||pageScope.negotiationFlag}">
                                        <input class="readonly" type="text" style="width: 70px" name="prpLlossCurrency" value="${tempPrpLloss.currency}">
                                    </c:when>
                                    <c:otherwise>
                                        <select name="prpLlossCurrency" class="input" style="width: 50px" onchange="getPrpLlossDtoExchRate(this);">
                                            <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                                <option value="${tempMap.key}" <c:if test="${tempMap.key==tempPrpLloss.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                                            </c:forEach>
                                        </select>
                                    </c:otherwise>
                                </c:choose>
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency1" value="${tempPrpLloss.currency1}">
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency2" value="${tempPrpLloss.currency2}">
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency3" value="${tempPrpLloss.currency3}">
                                <input class="readonly" type="hidden" style="width: 70px" name="prpLlossCurrency4" value="${tempPrpLloss.currency4}">
                            </td>
                            <td class="input" align="center" style="width: 8%" name="tdExchRate">
                                <input type="text" name="prpLlossDtoExchRate" class="input" style="width: 70px" onchange="setRealPayNTD(this);" value="${tempPrpLloss.exchRate}" readonly="readonly">
                            </td>
                            <td class="input" style="width: 8%" name="tdSumRealPayNTD">
                                <input type="text" name="prpLlossDtoSumRealPayNTD" class="common" value="" style="width: 75px" readonly="readonly">
                            </td>
                            <td class="input" style="width: 16%" align="center" name="tdRemark">
                                <input class="input" type="text" name="prpLlossRemark" value="${tempPrpLloss.remark}">
                            </td>
                            <td class="input" style="width: 13%;" align="center" name="tdPayObjectSerialNo">
                                <input class="common" type="text" readonly="readonly" name="prpLlossPayObjectSerialNo" value="${tempPrpLloss.payObjectSerialNo}" onclick="setPrpObjectinfoSerialNo(this);">
                            </td>
                            <td class="input" style='width: 5%' align="center">
                                <div style="width: 30px">
                                   <input type=button name="buttonClaimLossDelete" class=smallbutton value="-"  onclick="deleteRow(this,'Replevy','prpLlossSerialNo');" style="cursor: hand" >
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
<script type="text/javascript">
    //追償登錄、追償協商
    if(${pageScope.registerFlag||pageScope.negotiationFlag}){
        $("td[name='tdSumRealPay']").hide();
        $("td[name='tdExchRate']").hide();
        $("td[name='tdSumRealPayNTD']").hide();
        $("td[name='tdRemark']").hide();
        $("td[name='tdPayObjectSerialNo']").hide();
    }else{
        $("td[name='tdSumDefPay']").hide();
        //mantis：CLM0029 ，處理人員：DP0713，需求單編號：CLM0029 追償處理險種增刪控制
        //$(":input[name='prpLlossSumLoss']").attr("readonly",true);
    }
</script>
<%/** 追償協商顯示調整原因 */%>
<c:if test="${pageScope.negotiationFlag}">
    <table class="common" cellpadding="5" cellspacing="1" id="Lltext" style="display: block">
        <tr>
            <td class="common" style="text-align: left;">
                &nbsp;&nbsp;調整原因
                <br>
                <table class="common" align="center" >
                    <tbody>
                        <tr>
                            <td class="input" style="text-align: center;" colspan="0">
                                <textarea style="wrap: hard;text-align: left;" rows="8" cols="50" name="prpLctextContextInnerHTML">${prpLctextAdjReason.context}</textarea>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
</c:if>