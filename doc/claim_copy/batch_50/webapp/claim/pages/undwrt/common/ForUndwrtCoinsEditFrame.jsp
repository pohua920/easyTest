<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
function resetChangelossCharge() {
    //目前只为了联共保判断而增加的,表示变化已经操作过，可以清除了
    if (fm.all("lossOrChargeHaveChanged") != null) {
        fm.lossOrChargeHaveChanged.value = '0';
    }
}
</script>
<table class="common" align="center">
    <tr>
        <td class="title4" colspan="4" style="text-align: left">
            <img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="CoinsImg" onclick="showPage(this,spanCoins)">
            <s:text name="compensate.insuranceShareInformation" />
            <br>
            <%-- 联共保分摊信息 --%>
            <span style="display: none">
                <table class="common" style="display: none" id="Coins_Data" cellspacing="1" cellpadding="5">
                    <tbody>
                        <tr>
                            <td  style="width: 4%;" class="title4">
                                <input readonly name="prpLcoinsSerialNo" class="readonly" description="序号">
                                <input type='hidden' name="prpLcoinsLossFeeType">
                                <input type='hidden' name="prpLcoinsCurrency">
                                <input type='hidden' name="prpLcoinsCoinsType">
                                <input type='hidden' name="prpLcoinsChiefFlag">
                            </td>
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsTypeForShow" class="readonly" readonly>
                            </td>
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsChargeCode" class="readonly" readonly>
                            </td>
                            <td  style="width: 15%;" class="title4">
                                <input name="prpLcoinsChargeName" class="readonly" readonly>
                            </td>
                            <td  style="width: 15%;" class="title4">
                                <input name="prpLcoinsCoinsCode" class="readonly" readonly>
                            </td>
                            <td  style="width: 15%;" class="title4">
                                <input name="prpLcoinsCoinsName" class="readonly" readonly>
                            </td>
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsCoinsTypeShow" class="readonly" readonly>
                            </td>
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsChiefFlagShow" class="readonly" readonly>
                            </td>
                            <td  style="width: 5%;" class="title4">
                                <input name="prpLcoinsCoinsRate" class="readonly" readonly>%
                            </td>
                            <td  class="title4">
                                <input name="prpLcoinsCoinsSumpaid" class="readonly" readonly>
                                <input type='hidden' name="prpLcoinsSumpaid">
                            </td>
                            <div>
                                <input type=button name="buttonCoinsDelete" class=smallbutton onclick="deleteRow(this,'Coins')" value="-" style="display: none">
                                <input type="hidden" name="prpLcoinsFlag">
                            </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </span>
            <input type="hidden" name="countFlag" value="">
            <span id="spanCoins" style="display:">
                <table class="common" style="width: 100%" id="Coins" align="center" cellpadding="5" cellspacing="1">
                    <thead>
                        <tr class="common">
                            <td  style="width: 5%;" class="title4">
                                <s:text name="regist.prpLregist.serialNo" />
                            </td>
                            <%-- 序号 --%>
                            <td style="width: 10%;" class="title4">
                                <s:text name="compensate.compensationCategories" />
                            </td>
                            <%-- 赔付类别 --%>
                            <td  style="width: 10%;" class="title4">
                                <s:text name="compensate.insuranceCode" />
                            </td>
                            <%-- 联共保人代码 --%>
                            <td  style="width: 25%;" class="title4">
                                <s:text name="compensate.insuranceName" />
                            </td>
                            <%--  联共保人名称--%>
                            <td  style="width: 10%;" class="title4">
                                <s:text name="compensate.insuranceIdentity" />
                            </td>
                            <%-- 联共保身份 --%>
                            <td  style="width: 10%;" class="title4">
                                <s:text name="compensate.whetherChief" />
                            </td>
                            <%-- 是否首席 --%>
                            <td  style="width: 10%;" class="title4">
                                <s:text name="compensate.insuranceProportion" />
                            </td>
                            <%-- 联共保比例 --%>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr style="display: none">
                            <td class="title" align="right" style="width: 4%">
                                <div align="center">
                                    <input type="button" value="+" onclick="insertRow('Coins')" class=smallbutton name="buttonCoinsInsert" style="display: none">
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach var="tempPrpLcfeecoins" items="${requestScope.prpLcfeecoins.prpLcfeecoinsList}">
                        <tr>
                            <td style="width: 5%;" class="title4">
                                <input readonly name="prpLcoinsSerialNo" description="序号" value="${tempPrpLcfeecoins.id.serialNo }" class="readonly" style="width: 95%;">
                                <input type='hidden' name="prpLcoinsLossFeeType" value="${tempPrpLcfeecoins.lossFeeType}">
                                <input type='hidden' name="prpLcoinsCurrency" value="${tempPrpLcfeecoins.currency}">
                                <input type='hidden' name="prpLcoinsCoinsType" value="${tempPrpLcfeecoins.coinsType}">
                                <input type='hidden' name="prpLcoinsChiefFlag" value="${tempPrpLcfeecoins.chiefFlag}">
                            </td>
                        <c:if test="${tempPrpLcfeecoins.lossFeeType=='0'}">
                            <td style="width: 10%;" class="title4">
                                <input name="prpLcoinsTypeForShow" type="text"  class="readonly" readonly value="赔款" style="width: 95%;">
                            </td>
                        </c:if>
                        <c:if test="${tempPrpLcfeecoins.lossFeeType!='0'}">
                            <td style="width: 10%;" class="title4">
                                <input name="prpLcoinsTypeForShow" type="text"  class="readonly" readonly value="费用" style="width: 95%;">
                            </td>
                        </c:if>
                            <td style="width: 10%;" class="title4">
                                <input name="prpLcoinsCoinsCode" type="text" class="readonly" readonly value="${tempPrpLcfeecoins.coinsCode}" style="width: 95%;">
                            </td>
                            <td  style="width: 25%;" class="title4">
                                <input name="prpLcoinsCoinsName" type="text" class="readonly" readonly value="${tempPrpLcfeecoins.coinsName}" style="width: 95%;">
                            </td>
                        <c:if test="${tempPrpLcfeecoins.coinsType=='1'}">
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsCoinsTypeShow" type="text" class="readonly" readonly value="<s:text name='compensate.weAre'/>" style="width: 95%;">
                                <%-- 我方 --%>
                            </td>
                        </c:if>
                        <c:if test="${tempPrpLcfeecoins.coinsType=='2'}">
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsCoinsTypeShow" type="text" class="readonly" readonly value="<s:text name='compensate.otherSystem'/>" style="width: 95%;">
                                <%-- 系统内他方 --%>
                            </td>
                        </c:if>
                        <c:if test="${tempPrpLcfeecoins.coinsType=='3'}">
                            <td  style="width: 10%;" class="title4">
                                <input name="prpLcoinsCoinsTypeShow" type="text" class="readonly" readonly value="<s:text name='compensate.outsideSystem'/>" style="width: 95%;">
                                <%-- 系统外他方 --%>
                            </td>
                        </c:if>
                            <td  style="width: 10%;" class="title4">
                                <c:if test="${tempPrpLcfeecoins.chiefFlag=='2'}">
                                    <input name="prpLcoinsChiefFlagShow" type="text" class="readonly" readonly value="<s:text name='certainLoss.thirdCarLoss.yes'/>" style="width: 95%;">
                                    <%-- 是 --%>
                                </c:if>
                                <c:if test="${tempPrpLcfeecoins.chiefFlag=='1'}">
                                    <input name="prpLcoinsChiefFlagShow" type="text" class="readonly" readonly value="<s:text name='certainLoss.thirdCarLoss.no'/>" style="width: 95%;">
                                    <%-- 否 --%>
                                </c:if>
                            </td>
                            <td  style="width:10%;" class="title4">
                                <input name="prpLcoinsCoinsRate" type="text" class="readonly" readonly value="${tempPrpLcfeecoins.coinsRate }" style="width: 80%;">%
                                <div style="display: none">
	                                <input name="prpLcoinsCoinsSumpaid" class="readonly" readonly value="<fmt:formatNumber value="${tempPrpLcfeecoins.coinsSumPaid}" pattern="#" />" style="width: 95%;">
	                                <input type='hidden' name="prpLcoinsSumpaid" value="${tempPrpLcfeecoins.coinsSumPaid}">
                                	<input name="prpLcoinsChargeCode" type="text"  class="readonly" readonly value="${tempPrpLcfeecoins.chargeCode}" style="width: 95%;">
                               		<input name="prpLcoinsChargeName" type="text" class="readonly" readonly value="${tempPrpLcfeecoins.chargeName}" style="width: 95%;">
                                    <input type=button name="buttonCoinsDelete" class="smallbutton" onclick="deleteRow(this,'Coins')" value="-" style="display: none">
                                    <input type="hidden" name="prpLcoinsFlag">
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