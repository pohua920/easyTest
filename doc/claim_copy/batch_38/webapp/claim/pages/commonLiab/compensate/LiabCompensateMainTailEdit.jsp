<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面Tail[ 实赔 ]
* AUTHOR     : 中科软
* MODIFYLIST : Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String sosMedicFee = (String) request.getAttribute("sosMedicFee");
%>
<table class="subtable" cellpadding="0" cellspacing="1">
    <tr>
        <td>
            <table class="common" cellpadding="1" cellspacing="1">
                <tr>
                    <td class="left">
                        <s:text name="commonLiab.compensate.paymentCurrency" />：<%--赔款币种--%>
                    </td>
                    <td class="right">
                        <input class="readonly" readonly name="MergeCurrency" style="width: 40px" value="<%=ConstantCodes.LOCAL_CURRENCY%>">&nbsp;<%=ConstantCodes.LOCAL_CURRENCYNAME%>
                    </td>
                    <td class="left">
                        <input type="hidden" name="btnCurrencyTotle" class="bigbutton" value="<s:text name='common.modifySumClaim.summary'/>" onclick="alert('<s:text name='common.modifySumClaim.summary'/>');"><%--分币别汇总--%>
                    </td>
                    <td class="right"></td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
                <tr>
                    <td class="left">
                        <input type="text" class='readonly' readonly value='<s:text name="compensate.compel.paymentAmount" />' title='賠款金額之和，不包括費用！' style="width: 65px">：<%--赔款金额 --%>
                    </td>
                    <td class="right">
                        <input class="readonly" type="text" name="prpLcompensateSumDutyPaid" readonly value="<fmt:formatNumber value='${prpLcompensate.sumDutyPaid}' pattern='#'/>" title='賠款金額之和，不包括費用！'>
                    </td>
                    <td class="left">
                        <input class='readonly' readonly type='text' value='<s:text name="undwrt.ChargeAmount" />' title='費用金額之和，不包括賠款金額！' style="width: 65px">：<%--费用金额--%>
                    </td>
                    <td class="right">
                        <input type="text" name="prpLcompensateSumNoDutyFee" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${prpLcompensate.sumNoDutyFee}' pattern='#'/>" title='費用金額之和，不包括賠款金額！'>
                    </td>
                    <td class="left">
                        <input class='readonly' readonly type='text' value='<s:text name="undwrt.CaseTotal" />' title='賠款合計與費用之和！' style="width: 65px">：<%--本案合计--%>
                    </td>
                    <td class="right">
                        <input class="readonly" type="text" name="prpLcompensateSumPaid" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumPaid}' pattern='#'/>" title='賠款合計與費用之和！'>
                    </td>
                </tr>
                <tr>
                    <td class="left">
                        <input class='readonly' readonly type='text' value='<s:text name="undwrt.PaymentAmount" />' title='預付賠款金額之和！' style="width: 110px">：<%--已预付赔款--%>
                    </td>
                    <td class="right">
                        <input type="text" name="prpLcompensateSumPrePaid" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${requestScope.sosMedicFee}' pattern='#'/>" title='預付賠款金額之和！'>
                    </td>
                    <td class="left">
                        <input class='readonly' readonly type='text' value='<s:text name="db.prpLcompensate.sumThisPaid" />' title='賠款合計減去已預付賠款！' style="width: 100px">：<%--本次标的损失赔款--%>
                    </td>
                    <td class="right">
                        <input class="readonly" type="text" name="prpLcompensateSumThisPaid" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumThisPaid}' pattern='#'/>" title='賠款合計減去已預付賠款！'><%--标的损失赔款减去已预付赔款！--%>
                    </td>
                    <td class="left">
                        <s:text name="claim.salvage" />：<%--残值--%>
                    </td>
                    <td class="right">
                        <input class="readonly" type="text" readonly="true" name="prpLcompensateSumRest" value="<fmt:formatNumber value='${prpLcompensate.sumRest}' pattern='#'/>" >
                    </td>
                </tr>
            <c:if test="${requestScope.coinsFlag == '1' || requestScope.coinsFlag == '2' || requestScope.coinsFlag == '3'}">
                <tr style="display: none;">
                    <td class="left">
                        <s:text name="compensate.ourAmount"/>：<%--我方赔款金额--%>
                    </td>
                    <td class="right" colspan="2">
                        <input type="text" name="prpLcompensateSumCoinUs" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${prpLcompensate.sumCoinUs}' pattern='#'/>">
                    </td>
                    <td class="left">
                        <s:text name="compensate.weCharge"/>：<%--我方费用金额--%>
                    </td>
                    <td class="right" colspan="2">
                        <input class="readonly" type="text" name="prpLcompensateSumCoinUsFee" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumCoinUsFee}' pattern='#'/>">
                    </td>
                </tr>
                <tr style="display: none;">
                    <td class="left">
                        <s:text name="compensate.amountPaid"/>：<%--代付赔款金额--%>
                    </td>
                    <td class="right" colspan="2">
                        <input type="text" name="prpLcompensateSumCoinForOther" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOther}' pattern='#'/>">
                        <input type="hidden" name="prpLcompensateSumCoinForOtherBak" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOther}' pattern='#'/>">
                    </td>
                    <td class="left">
                        <s:text name="compensate.payFee"/>：<%--代付费用金额--%>
                    </td>
                    <td class="right" colspan="2">
                        <input class="readonly" type="text" name="prpLcompensateSumCoinForOtherFee" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOtherFee}' pattern='#'/>">
                        <input class="readonly" type="hidden" name="prpLcompensateSumCoinForOtherFeeBak" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOtherFee}' pattern='#'/>">
                    </td>
                </tr>
            </c:if>
                <tr>
                    <td class="left">業務經辦人：</td>
                    <td class="right">
                        <input name="prpLcompensateHandlerCode" class="codecode" style="width: 60px" value="${prpLcompensate.handlerCode}" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y');">
                        <input name="prpLcompensateHandlerName" class="codename" style="width: 120px" title="<s:text name='db.prpLarrearageNew.handlerCode'/>" value="${prpLcompensate.handlerName}" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
                            onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
                    </td>
                    <td class="left">
                        <s:text name="workflow.countDate" />： <%--统计日期--%>
                    </td>
                    <td class="right">
                        <rc:rcDate name="prpLcompensateStatisticsYM" class="input" style="width: 120px" title="<s:text name='db.prpLclaim.statisticsYM'/>" readonly="true" value="${prpLcompensate.statisticsYM}"/>
                    </td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
                <tr>
                    <td class="left">
                        <s:text name="db.prpLcomponent.remark" />：<%--备注--%>
                    </td>
                    <td class="right" colspan="5">
                        <input type="text" name='prpLcompensateRemark' title="<s:text name='db.prpDshipclass.remark'/>" value="${prpLcompensate.remark}" width="80%"  class="input">
                    </td>
                </tr>
                <input type="hidden" name="prpLcompensateChecker1" value="${prpLcompensate.checker1}">
            </table>
        </td>
    </tr>
</table>