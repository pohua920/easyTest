<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.common.util.CommonUtils"%>
<%@ page import="com.sinosoft.claim.schema.model.PrpCitemCar"%>
<%@ page import="com.sinosoft.claim.schema.model.PrpLcompensate"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="ins.framework.utils.DataUtils"%>
<%
    PrpCitemCar prpCitemCar = (PrpCitemCar) request.getAttribute("prpCitemCar");
    PrpLcompensate prpLcompensate = (PrpLcompensate) request.getAttribute("prpLcompensate");
    //增被保险人联系电话  end
    int intstartHour = 0;
    int intendHour = 0;
    String startHour = "";
    String endHour = "";
    if (prpLcompensate != null) {
        intstartHour = prpLcompensate.getStartHour();
        intendHour = prpLcompensate.getEndHour();
    }
    if (intstartHour == 0) {
        startHour = "零時起至";
    } else if (intstartHour == 12) {
        startHour = "十二時起至";
    } else if (intstartHour == 24) {
        startHour = "二十四時起";
    }
    if (intendHour == 12) {
        endHour = "十二時止";
    } else if (intendHour == 24) {
        endHour = "二十四時止";
    } else if (intendHour == 0) {
        endHour = "零時止";
    }
	String compensateNo = prpLcompensate.getCompensateNo();
	String editType = request.getParameter("editType");
	pageContext.setAttribute("registerFlag", "addQuery".equals(editType));//是否登錄、登錄修改 
	pageContext.setAttribute("negotiationFlag", false);//是否追偿协商
	if("editQuery".equals(editType) || 
			(DataUtils.emptyToNull(compensateNo)!=null && compensateNo.endsWith("00") 
				&& ("UNDWRT".equals(editType) || "EDIT".equals(editType) || "SHOW".equals(editType)))){
		pageContext.setAttribute("negotiationFlag", true);
	}
%>
<script type="text/javascript">
    var $baseToExch = $("body");<%-- 汇率  本位币转换其他币别  --%>
    var $exchToBase = $("body");<%-- 汇率  其他币别转换本位币 --%>
</script>
<c:forEach items="${requestScope.baseToExch}" var="temp">
    <script type="text/javascript">jQuery.data($baseToExch,'${temp.id.exchCurrency}','${temp.exchRate}');</script>
</c:forEach>
<c:forEach items="${requestScope.exchToBase}" var="temp">
    <script type="text/javascript">jQuery.data($exchToBase,'${temp.id.baseCurrency}','${temp.exchRate}');</script>
</c:forEach>
<script type="text/javascript">
    $(function(){
        if(${pageScope.registerFlag}){//追偿登录
            $("#tableCharge").hide();//隐藏费用讯息
        }
        if(${pageScope.negotiationFlag}){//追偿协商
            $("#tableCharge").hide();//隐藏费用讯息
            $("#tablePrpLpayObject").hide();//隐藏追偿对象讯息
        }
    })
</script>
<input type="hidden" name="riskType" value="${requestScope.riskType}">
<input type="hidden" name="configCode" value="${requestScope.configCode}">
<input type="hidden" name="InputDate" value="${strToday}">
<input type="hidden" name="StatisticsYM" value="${strToday}">
<input type="hidden" name="caseNo" value="${prpLcompensate.caseNo}">
<input type="hidden" name="riskCode" value="${prpLcompensate.riskCode}">
<input type="hidden" name="outerCode" value="${outerCode}">
<input type="hidden" name="damageStartDate" value="${prpLcompensate.damageStartDate}">
<input type="hidden" name="damageStartHour" value="<c:out value='${requestScope.prpLcompensate.damageStartHour}'/>">
<input type="hidden" name="policyno" value="${prpLcompensate.policyNo}">
<input type="hidden" name="prpLregistExtRegistNo" value="${prpLcompensate.registNo}">
<input type="hidden" name="prpLcompensatePolicyNo" value="${prpLcompensate.policyNo}">
<input type="hidden" name="isPayForOtherFlag" value="${prpLcompensate.isPayForOther}">
<input type="hidden" name="coinsFlag" value="${coinsFlag}">
<input type="hidden" name="prpLcompensateClaimNo" value="${prpLcompensate.claimNo}">
<input type="hidden" name="prpLcompensateTimes" value="${prpLcompensate.times}">
<input type="hidden" name="prpLcompensateSumLoss" value="0.00">
<input type="hidden" name="prpLcompensateSumRest" value="0.00">
<input type="hidden" name="prpLcompensateSumDutyPaid" value="0.00">
<input type="hidden" name="prpLcompensateSumPaid" value="0.00">
<input type="hidden" name="prpLcompensateSumNoDutyFee" value="0.00">
<input type="hidden" name="prpLcompensateSumPrePaid" value="0.00">
<input type="hidden" name="prpLcompensateSumThisPaid" value="0.00">
<!-- //mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start -->
<c:if test="${'A01' != prpLcompensate.riskCode && 'B01' != prpLcompensate.riskCode}">
<input type="hidden" name="prpLcompensateIndemnityDutyRate" value="0.00">
</c:if>
<!-- //mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end -->
<input type="hidden" name="prpLcompensateSumAmount" value="0.00">
<input type="hidden" name="prpLcompensateCompensateNo" value="${prpLcompensate.compensateNo}">
<input type="hidden" name="prpLcompensateCurrency" value="${prpLcompensate.currency}">
<input type="hidden" name="prpLcompensateFinallyFlag" value="0">
<input type="hidden" name="underWriteFlag" value="${prpLcompensate.underWriteFlag}">
<input type="hidden" name="swfLogFlowID" value="<c:out value='${param.swfLogFlowID}'/>">
<input type="hidden" name="swfLogLogNo" value="<c:out value='${param.swfLogLogNo}'/>">
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td class="formtitle" >
            <c:choose>
                <c:when test="${param.editType=='addQuery'}">${RiskCodeName}追償登錄</c:when>
                <c:when test="${param.editType=='editQuery'}">${RiskCodeName}追償協商</c:when>
                <c:when test="${param.editType=='ADD'}">${RiskCodeName}追償處理</c:when>
                <c:when test="${param.editType=='EDIT'}">${RiskCodeName}追償駁回修改</c:when>
                <c:when test="${param.editType=='UNDWRT'}">${RiskCodeName}追償審核</c:when>
                <c:otherwise> </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
<!---联共保操作选择信息 --->
<c:if test="${coinsFlag=='1'}">
    <table class="common" cellpadding="1" cellspacing="1" style="display: none">
        <tr>
            <td class="left" colspan="6" align="center">
                <font color="red"><s:text name="replevy.notUseThisFunction3" /></font>
            </td>
            <%--本案涉及共保，且我方为主共方，请录入本案总的追偿收入和费用，系统会按比例自动计算出各方数额 --%>
        </tr>
        <tr>
            <td class="left">
                <s:text name="replevy.notUseThisFunction2" />
            </td>
            <%-- 请选择从共方数据是否送收付--%>
            <td class="right" colspan='5'>
                <input type="radio" disabled name="isPayForOther" onclick="changePayForOtherFlag(1);" <c:if test="${prpLcompensate.isPayForOther=='1' }">checked</c:if> value="1">是
                <input type="radio" disabled name="isPayForOther" onclick="changePayForOtherFlag(0);" <c:if test="${prpLcompensate.isPayForOther=='0' }">checked</c:if> value="0">否 
                <font color="#FF0000">* &nbsp;&nbsp;&nbsp;<s:text name="replevy.notUseThisFunction1" /></font>
                <%--此功能暂不使用 --%>
            </td>
        </tr>
    </table>
</c:if>
<table class="subtable" cellpadding="0" cellspacing="1">
    <tr>
        <td>
            <table class="common" cellpadding="1" cellspacing="1">
            <c:if test="${'UNDWRT' == param.editType}">
                <tr>
                    <td class="left">審核類型：</td>
                    <td class="right">
                        <c:choose>
                            <c:when test="${pageScope.negotiationFlag}"><input class="readonly" readonly value="追償協商"></c:when>
                            <c:otherwise><input class="readonly" readonly value="一般追償"></c:otherwise>
                        </c:choose>
                    </td>
                    <td class="left"></td>
                    <td class="right"></td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
            </c:if>
                <tr>
                    <td class="left">立案號碼：</td>
                    <td class="right">
                        <input class="readonly" readonly name="prpLreplevyClaimNo" value="${prpLcompensate.claimNo}">
                    </td>
                    <td class="left">追償理算次數：</td>
                    <td class="right">
                         <input class="readonly" readonly name="prpLreplevyTimes" value="${prpLcompensate.times}" style="width: 50px">
                    </td>
                    <td class="left">追償代號：</td>
                    <td class="right">
                         <select name="prpLcompensatePayCodeType" style="width: 100px">
                            <option value="1" <c:if test="${prpLcompensate.payCodeType=='1'}">selected="selected"</c:if>>1-一般賠案</option>
                            <option value="2" <c:if test="${prpLcompensate.payCodeType=='2'}">selected="selected"</c:if>>2-同業</option>
                            <option value="3" <c:if test="${prpLcompensate.payCodeType=='3'}">selected="selected"</c:if>>3-健保局</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="left">保單號碼：</td>
                    <td class="right">
                        <input name="prpLreplevyPolicyNo" class="readonly" readonly value="${prpLcompensate.policyNo}">
                    </td>
                    <td class="left">要保人：</td>
                    <td class="right">
                        <input class="readonly" readonly name="AppliName" value="${prpLcompensate.appliName}">
                    </td>
                    <td class="left">被保險人：</td>
                    <td class="right">
                        <input class="readonly" readonly name="InsuredName" value="${prpLcompensate.insuredName}">
                    </td>
                </tr>
                <tr <c:if test="${requestScope.riskType != 'Y'}">style="display:none"</c:if> >
                    <td class="left">船名：</td>
                    <td class="right">
                        <input type="text" name="prpLreplevyShipCName" class="readonly" readonly value="${prpLcompensate.shipCName}">
                    </td>
                    <td class="left" >航程：</td>
                    <td class="right" colspan="3" >
                        <input type="text" name="prpLreplevyStartSitePort" class="readonly" readonly="readonly" value="${prpLcompensate.startSitePort}" style="width: 120px">
                        <input type="text" name="prpLreplevyStartSiteCountry" class="readonly" readonly="readonly" value="${prpLcompensate.startSiteCountry}" style="width: 120px">
                        &nbsp;到&nbsp;
                        <input type="text" name="prpLreplevyEndSitePort" class="readonly" readonly="readonly" value="${prpLcompensate.endSitePort}" style="width: 120px">
                        <input type="text" name="prpLreplevyEndSiteCountry" class="readonly" readonly="readonly" value="${prpLcompensate.endSiteCountry}" style="width: 120px">
                    </td>
                </tr>
                <tr <c:if test="${requestScope.riskType != 'Y'}">style="display:none"</c:if> >
                    <td class="left">追償機構：</td>
                    <td class="right">
                        <input name="prpLreplevyComName" type="text" class="readonly" readonly value="${prpLcompensate.comName}">
                        <input name="prpLreplevyComCode" type="hidden" value="${prpLcompensate.comCode}">
                    </td>
                    <td class="left">保單年度：</td>
                    <td class="right">
                        <input name="prpLreplevyPolicyYear" type="text" class="readonly" readonly value="${prpLcompensate.policyYear}">
                    </td>
                    <td class="left">運輸方式：</td>
                    <td class="right">
                        <select name="transportType" disabled="disabled">
                            <option value="1"><s:text name="claim.transportType.sea" /><%-- 海運 --%></option>
                            <option value="2"><s:text name="claim.transportType.air" /><%-- 空運 --%></option>
                            <option value="3"><s:text name="claim.transportType.land" /><%-- 陸運 --%></option>
                            <option value="4"><s:text name="claim.transportType.post" /><%-- 郵寄--%></option>
                        </select>
                        <script type="text/javascript">$("select[name='transportType']").val('${prpLclaim.transportType}');</script>
                    </td>
                </tr>
                <tr <c:if test="${requestScope.riskType != 'Y'}">style="display:none"</c:if> >
                    <td class="left">開航日期：</td>
                    <td class="right">
                        <rc:rcDate name="prpLreplevySailStartDate" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${requestScope.prpLcompensate.sailStartDate}" />
                    </td>
                    <td class="left"></td>
                    <td class="right"></td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
                <tr>
                    <td class="left">保險期間：</td>
                    <td class="right" colspan="3">
                        <rc:rcDate name="prpLcompensateStartDate" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${requestScope.prpLcompensate.startDate}" />
                        <%=startHour%><rc:rcDate name="endDate" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${requestScope.prpLcompensate.endDate}" />
                        <%=endHour%>
                    </td>
                    <td class="left">出險日期：</td>
                    <td class="right">
                        <rc:rcDate name="prpLcompensateDamageStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:75px" value="${requestScope.prpLcompensate.damageStartDate}" />日<c:out value='${requestScope.prpLcompensate.damageStartHour}' />時<c:out value='${requestScope.prpLcompensate.damageStartMinute}' />分
                    </td>
                </tr>
                <tr>
                    <td class="left">車牌號碼：</td>
                    <td class="right"><input class="readonly" readonly name="prpLcompensateLicenseNo" value="${prpLcompensate.licenseNo}"></td>
                    <td class="left">車輛種類：</td>
                    <td class="right">
                        <input class="readonly" readonly name="prpLcompensateCarKind" value="${prpLcompensate.carKind}">
                    </td>
                    <td class="left">引擎號碼：</td>
                    <td class="right">
                        <input class="readonly" readonly name="prpLcompensateEngineNo" value="${prpLcompensate.engineNo}">
                    </td>
                </tr>
                <tr>
                    <td class="left">原發照年月：</td>
                    <td class="right"><%=CommonUtils.getMGDateStr(prpCitemCar.getEnrollDate(), new SimpleDateFormat("yyyy年MM月"))%></td>
                    <td class="left">製造年月：</td>
                    <td class="right"><%=prpCitemCar.getMakeDate()==null ? "" : new SimpleDateFormat("yyyy年MM月").format(prpCitemCar.getMakeDate())%></td>
                    <td class="left">廠牌型號：</td>
                    <td class="right">
                        <input class="readonly" readonly name="prpLcompensateBrandName" value="${prpLcompensate.brandName}">
                    </td>
                </tr>
                <tr>
                    <td class="left">排氣量：</td>
                    <td class="right"><fmt:formatNumber value='${prpCitemCar.exhaustScale}' pattern='#'/></td>
                    <!-- //mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start -->
                    <c:if test="${'A01' != prpLcompensate.riskCode && 'B01' != prpLcompensate.riskCode}">
                    <td class="left">肇事責任比例：</td>
                    <td class="right">
                        <input class="readonly" style="width: 30px" readonly name="prpLcompensateIndemnityDutyRate" value="<fmt:formatNumber value='${prpLcompensate.indemnityDutyRate}' pattern='#'/>">%
                    </td>
                    </c:if>
                    <c:if test="${'A01' == prpLcompensate.riskCode || 'B01' == prpLcompensate.riskCode}">
                    <td class="left"></td>
                    <td class="right"></td>
                    </c:if>
                    <!-- //mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end -->
                    <td class="left">憲警單位：</td>
                    <td class="right"><input class="readonly" readonly style="width: 80%" name="prpLcheckPoliceUnit" value="${prpLcheck.policeUnit}" ></td>
                </tr>
                <tr <c:if test="${prpLcompensate.riskCode!=outerCode}">style="display:none"</c:if>>
                    <td class="left"></td>
                    <td class="right"></td>
                    <td class="left"></td>
                    <td class="right"></td>
                    <td class="left">賠付類別：</td>
                    <td class="right">
                       <c:set var="tempCompelPayType" value='${prpLcompensate.compelPayType}' />
                       <s:select name="prpLcompensateCompelPayType" value="#attr.tempCompelPayType" listKey="key" listValue="value" list="#request.compelPayTypeList" /><img src="${ctx}/images/bgMarkMustInput.jpg">
                    </td>
                </tr>
                <tr>
                    <td class="left">被追償人名稱：</td>
                    <td class="right">
                        <input class="input" style="width: 150px" name="prpLreplevyRepleviedName" value="${prpLcompensate.counterClaimerName}" onblur="checkLength(this)" description="被追偿人名称">
                        <img src="${ctx}/images/bgMarkMustInput.jpg">
                    </td>
                    <td class="left">幣別：</td>
                    <td class="right">
                        <input type="text" class="readonly" readonly name="Currency" style="width: 40px" value="${prpLcompensate.currency}">
                        <c:out value="${prpLcompensate.currencyName}"></c:out>
                    </td>
                    <td class="left">法務預估總金額：</td>
                    <td class="right">
                        <input class="readonly" readonly name="SumLoss" value="<fmt:formatNumber value='${prpLcompensate.sumLoss}' pattern='#'/>">
                    </td>
                </tr>
                <tr>
                    <td class="left">追償原因：</td>
                    <td class="right" colspan='3'>
                        <input class="input" name="prpLreplevyReplevyReason" style="width: 80%" value="${prpLcompensate.dutyDescription}" onblur="checkLength(this)" style='width:80%' description="追償原因">
                    </td>
                    <td class="left">本案總賠付金額：</td>
                    <td class="right">
                        <input class="readonly" readonly name="prpLclaimSumPaid" value="<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>">
                    </td>
                </tr>
                <tr>
                    <td class="left">追償時效：</td>
                    <td class="right">
                        <rc:rcDate name="ReplevyLimitDate" readonly="true" style="width:95px" value="${prpLcompensate.preserveDate}" class="readonly"/>
                    </td>
                    <td class="left"><c:if test="${prpLcompensate.riskCode==outerCode}">賠付日期：</c:if></td><%/** 強制險才有 **/%>
                    <td class="right"><c:if test="${prpLcompensate.riskCode==outerCode}"><rc:rcDate name="prpLcompensatePayDate" class="readonly" readonly="true" wdatePicker="false" style="width:75px" value="${prpLcompensate.payDate}"/></c:if></td>
                    <td class="left"><c:if test="${!(pageScope.registerFlag||pageScope.negotiationFlag)}">本次追回日期：</c:if></td>
                    <td class="right">
                        <c:if test="${!(pageScope.registerFlag||pageScope.negotiationFlag)}">
                            <rc:rcDate name="prpLreplevyValidDate" class="readonly" readonly="true" style="width:95px" value="${prpLcompensate.statisticsYM}" />
                        </c:if>
                    </td>
                </tr>
                <tr <c:if test="${pageScope.registerFlag||pageScope.negotiationFlag}">style="display: none"</c:if> >
                    <td class="left">本次追償收入：</td>
                    <td class="right">
                        <input class="readonly" readonly name="SumThisPaid" value="<fmt:formatNumber value='${prpLcompensate.sumThisPaid}' pattern='#'/>">
                        <input type="hidden" name="prpLreplevySumDutyPaid" value="0">
                    </td>
                    <td class="left">本次追償費用：</td>
                    <td class="right">
                        <input class="readonly" readonly name="SumThisCharge" value="<fmt:formatNumber value='${prpLcompensate.sumNoDutyFee}' pattern='#'/>">
                    </td>
                    <td class="left">總追償收入：</td>
                    <td class="right">
                    <c:choose>
                        <c:when test="${pageScope.registerFlag||pageScope.negotiationFlag}">
                            <input class="readonly" readonly name="SumPaidAll" value="<fmt:formatNumber value='${prpLcompensate.sumPaidAll}' pattern='#'/>">
                            <input type="hidden" name="OldSumPaidAll" value="${prpLcompensate.sumPaidAll}">
                        </c:when>
                        <c:otherwise>
                            <c:set var="sumPaidAll" value="0" scope="page" />
                            <c:if test="${prpLcompensate!=null}">
                                <c:set var="sumPaidAll" value="${prpLcompensate.sumPaidAll+prpLcompensate.sumThisPaid}" scope="page" />
                            </c:if>
                            <input class="readonly" readonly name="SumPaidAll" value="<fmt:formatNumber value='${sumPaidAll}' pattern='#'/>">
                            <input type="hidden" name="OldSumPaidAll" value="${prpLcompensate.sumPaidAll}">
                        </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
                <tr <c:if test="${pageScope.registerFlag||pageScope.negotiationFlag}">style="display: none"</c:if> >
                    <td class="left">總追償費用：</td>
                    <td class="right">
                    <c:choose>
                        <c:when test="${pageScope.registerFlag||pageScope.negotiationFlag}">
                            <input name="SumFeeAll" class="readonly" readonly value="<fmt:formatNumber value='${prpLcompensate.sumDutyPaid1}' pattern='#'/>">
                            <input type="hidden" name="OldSumFeeAll" value="${prpLcompensate.sumDutyPaid1}">
                        </c:when>
                        <c:otherwise>
                            <c:set var="sumFeeAll" value="0.00" scope="page" />
                            <c:if test="${prpLcompensate!=null}">
                                <c:set var="sumFeeAll" value="${prpLcompensate.sumDutyPaid1+prpLcompensate.sumNoDutyFee}" scope="page" />
                            </c:if>
                            <input name="SumFeeAll" class="readonly" readonly value="<fmt:formatNumber value='${sumFeeAll}' pattern='#'/>">
                            <input type="hidden" name="OldSumFeeAll" value="${prpLcompensate.sumDutyPaid1}">
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td class="left">給付追償情況：</td>
                    <td class="right">
                        <c:set var="tempPaySituation" value="${prpLcompensate.paySituation}" />
                        <s:select name="paySituation" value="#attr.tempPaySituation" listKey="key" listValue="value" list="#request.compelPaySituationList" onchange="changePaySituation(this);"/>
                        <img src="${ctx}/images/bgMarkMustInput.jpg">
                    </td>
                    <td class="left"><span <c:if test="${pageScope.registerFlag||pageScope.negotiationFlag}">style="display: none"</c:if>>追償類型：</span></td>
                    <td class="right">
                        <select class=query name="ReplevyTypeCode" style="width: 50%;<c:if test="${pageScope.registerFlag||pageScope.negotiationFlag}">display: none</c:if>">
                            <option value="1" <c:if test="${prpLcompensate.indemnityDuty=='1' }">selected</c:if> >自追償</option>
                            <option value="2" <c:if test="${prpLcompensate.indemnityDuty=='2' }">selected</c:if> >代追償</option>
                            <option value="3" <c:if test="${prpLcompensate.indemnityDuty=='3' }">selected</c:if> >理賠</option>
                        </select>
                    </td>
                </tr>
                <!-- //mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start -->
                <c:if test="${'A01' == prpLcompensate.riskCode || 'B01' == prpLcompensate.riskCode}">
                <tr>
                    <td class="left">賠付代號：</td>
                    <td class="right">
                        <s:select name="prpLcompensatePayCode" id="prpLcompensatePayCode" cssStyle="width:250px" value="#request.prpLcompensate.payCode" list="#request.payCodeList" listKey="key" listValue="value" />
                        <img src="/claim/images/bgMarkMustInput.jpg">
                        <script type="text/javascript">
                            $(function(){
                                $("#prpLcompensatePayCode").bind("mouseover",function(){
                                    $(this).prop("title",$(this).children(":selected").text());
                                });
                            })
                        </script>
                    </td>
                    <td class="left"></td>
                    <td class="right"></td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
                <tr>
                    <td class="left">本車肇責百分比：</td>
                    <td class="right">
                        <s:select name="prpLcompensateIndemnityDutyRate" value="#request.prpLcompensate.indemnityDutyRate" listKey="key" listValue="value" list="#request.indemnityDutyList"
                            onchange="checkIndemnityDuty(this);" />
                        <img src="/claim/images/bgMarkMustInput.jpg">
                    </td>
                    <td class="left">對方車肇責百分比：</td>
                    <td class="right">
                        <s:select name="prpLcompensateOppositeIndemnityDuty" value="#request.prpLcompensate.oppositeIndemnityDuty" listKey="key" listValue="value" list="#request.indemnityDutyList"
                            onchange="checkIndemnityDuty(this);" />
                        <img src="/claim/images/bgMarkMustInput.jpg">
                    </td>
                    <td class="left">其他肇責百分比：</td>
                    <td class="right">
                        <s:select name="prpLcompensateOtherIndemnityDuty" value="#request.prpLcompensate.otherIndemnityDuty" listKey="key" listValue="value" list="#request.indemnityDutyList"
                            onchange="checkIndemnityDuty(this);" />
                        <img src="/claim/images/bgMarkMustInput.jpg">
                    </td>
                </tr>
                </c:if>
                <!-- //mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end -->
                <tr name="trPaySituationTimes" <c:if test="${pageScope.registerFlag || pageScope.negotiationFlag || prpLcompensate.paySituation!='4'}">style="display: none"</c:if> >
                    <td class="left"></td>
                    <td class="right"></td>
                    <td class="left">總期數：</td>
                    <td class="right"><input type="text" name="prpLreplevyTotalTimes" value="${prpLcompensate.totalTimes}" class="input" style="width: 50px"></td>
                    <td class="left">已追償期數：</td>
                    <td class="right"><input type="text" name="prpLreplevyReplevyTimes" value="${prpLcompensate.replevyTimes}" class="readonly" readonly style="width: 50px"></td>
                </tr>
                <tr <c:if test="${pageScope.registerFlag || pageScope.negotiationFlag || (requestScope.riskType != 'Z' && requestScope.riskType != 'G' )}">style="display:none"</c:if> >
                    <td class="left">身份證字號：</td>
                    <td class="right">
                        <input type="text" name="prpLreplevyIdNumber" value="${prpLcompensate.idNumber}" class="input" style="width: 100px;ime-mode:disabled;" maxlength="10" >
                    </td>
                    <td class="left">聯絡電話：</td>
                    <td class="right">
                        <input type="text" name="prpLreplevyContactTelephone" value="${prpLcompensate.contactTelephone}" class="input" style="width: 120px;ime-mode:disabled;" maxlength="10" >
                    </td>
                    <td class="left"></td>
                    <td class="right"></td>
                </tr>
                <tr <c:if test="${pageScope.registerFlag || pageScope.negotiationFlag || (requestScope.riskType != 'Z' && requestScope.riskType != 'G' )}">style="display:none"</c:if> >
                    <td class="left">聯絡地址：</td>
                    <td class="right" colspan="5">
                        <input type="text" name="prpLreplevyContactAddress" value="${prpLcompensate.contactAddress}" class="input" style="width: 80%;" maxlength="10" >
                    </td>
                </tr>
                <tr>
                    <td class="left">處理單位：</td>
                    <td class="right">
                        <input class="readonly" readonly name="MakeCom" description="處理單位" value="${makeComName }">
                        <input type="hidden" name="MakeCode" value="${prpLcompensate.makeCom}">
                    </td>
                    <td class="left">操作員：</td>
                    <td class="right">
                        <input name="OperatorName" class="readonly" readonly value="${prpLcompensate.handlerName}">
                        <input name="OperatorCode" type="hidden" name="" value="${prpLcompensate.handlerCode}">
                    </td>
                    <td class="left">對方賠案號碼：</td>
                    <td class="right">
                        <input class="input" name="prpLcompensateOppositeClaimNo" maxlength="20" value="${prpLcompensate.oppositeClaimNo}">
                    </td>
                </tr>
                <tr>
                    <td class="left">備註：</td>
                    <td class="right" colspan='3'>
                        <input class="input" name="prpLreplevyNote" style="width: 80%" value="${prpLcompensate.remark}" onblur="checkLength(this)" description="备注">
                    </td>
                    <td class="left">對方理賠員：</td>
                    <td class="right">
                        <input class="input" name="prpLcompensateOppositeClaimOfficer" value="${prpLcompensate.oppositeClaimOfficer}">
                    </td>
                </tr>
                <tr style="display: none">
                    <td class="left">
                        <s:text name="db.prpLregist.comCode" />
                    </td>
                    <%--业务归属机构 --%>
                    <td class="right">
                        <input name="ComName" class="readonly" readonly description="业务归属机构" value="${prpLcompensate.comName}">
                        <input name="ComCode" type="hidden" description="业务归属机构" value="${prpLcompensate.comCode}">
                    </td>
                    <td class="left">
                        <s:text name="replevy.belongPerson" />
                    </td>
                    <%--归属经办人 --%>
                    <td class="right">
                        <input class="readonly" readonly name="Handler1Name" description="归属经办人" value="${prpLcompensate.handler1Name}">
                        <input type="hidden" name="Handler1Code" value="${prpLcompensate.handler1Code}">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>