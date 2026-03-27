<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]（非车险）
* AUTHOR     ：  理赔组
* CREATEDATE ： 2014-04-16
* MODIFYLIST ：   Name       Date			  Reason/Contents
*			------------------------------------------------------
****************************************************************************
--%>
<!--modify by liuyanmei add 20051114 start-->
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
 <%@ page import="com.sinosoft.function.insutil.dto.domain.PrpDexchDto" %>
<s:if test="#request.prpLclaim.startHour == 0">
	<s:set var="startHour" value="%{getText('claim.prpLclaim.startHour.zero')}"></s:set>
</s:if>
<s:elseif test="#request.prpLclaim.startHour == 12">
	<s:set var="startHour" value="%{getText('claim.prpLclaim.startHour.twelve')}"></s:set>
</s:elseif>
<s:elseif test="#request.prpLclaim.startHour == 24">
	<s:set var="startHour" value="%{getText('claim.prpLclaim.startHour.twentyFour')}"></s:set>
</s:elseif>
<s:if test="#request.prpLclaim.endHour == 0">
	<s:set var="endHour" value="%{getText('regist.until')}"></s:set>
</s:if>
<s:elseif test="#request.prpLclaim.endHour == 12">
	<s:set var="endHour" value="%{getText('regist.end')}"></s:set>
</s:elseif>
<s:elseif test="#request.prpLclaim.endHour == 24">
	<s:set var="endHour" value="%{getText('modifySumClaim.hourEnd')}"></s:set>
</s:elseif> 
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
		<tr> 
			<td>
				<c:if test="${prpLclaim.caseType == '1'}">
					<s:text name="commonAcci.claim.rejectClaim" /><%--（已拒赔）--%>
				</c:if>
				<c:if test="${prpLclaim.caseType == '0'}">
					<s:text name="commonAcci.claim.cancelled" /><%--（已注销）--%>
				</c:if>
			</td>
		</tr>
	</table>
	<table  class=subtable cellpadding="0" cellspacing="1">
		<tr>
			<td>
				<table  class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<input type="hidden" name="prpLclaimRiskCode" value="${prpLclaim.riskCode}">
							<input type="hidden" name="prpLclaimOperatorCode" value="${prpLclaim.operatorCode}">
							<input type="hidden" name="prpLclaimMakeCom" value="${prpLclaim.makeCom}">		 		
							<input type="hidden" name="prpLclaimEngineNo" >
							<input type="hidden" name="prpLclaimFrameNo">
							<input type="hidden" name="prpLclaimRunDistance">
							<input type="hidden" name="prpLclaimSumDefLoss" value="${prpLclaim.sumDefLoss}">		
							<input type="hidden" name="prpLclaimTypeForDriver" value="claim">					
							<input type="hidden" name="coreURL" value="${coreURL}%>">
							<input type="hidden" name="prpLclaimPolicyType" value="${prpLclaim.policyType}"> 
							<input type="hidden" name="prpLclaimEscapeFlag" value="${prpLclaim.escapeFlag}">         
							<input type="hidden" name="prpLclaimClassCode" value="${prpLclaim.classCode}">
							<input type="hidden" name="prpLclaimInputDate" value="${prpLclaim.inputDate}">
							<input type="hidden" name="prpLclaimDamageEndDate" value="${prpLclaim.damageEndDate}">
							<input type="hidden" name="prpLclaimDamageEndHour" value="${prpLclaim.damageEndHour}">
							<input type="hidden" name="prpLclaimDamageEndMinute" value="${prpLclaim.damageEndMinute}">
							<input type="hidden" name="prpLclaimClauseType" readonly="true"  style="width:30px" value="${prpLclaim.clauseType}">
							<input type="hidden" name="prpLclaimClauseName" readonly="true"  style="width:300px"value="${prpLclaim.clauseName}">							
							<%-- <input type="hidden" name="prpLclaimAddressCode"  title="<s:text name="db.prpLregist.addressCode"/>" class="input"  style="width:80px" value="${prpLclaim.addressCode}"> --%>
							<input type="hidden" name="prpLclaimDamageAddressType" title="<s:text name="db.prpLregist.damageAddressType"/>" class="codecode" style="width:90px" value="${prpLclaim.damageAddressType}">
							<input type="hidden" name="prpLclaimDamageTypeCode" class="codecode"  style="width:15%" title="<s:text name="db.prpLregist.damageTypeCode"/>" value="${prpLclaim.damageTypeCode}">
							<input type="hidden" name="prpLclaimDamageAreaCode" class="codecode" style="width:15%" title="<s:text name="regist.prpLregist.damageAreaCode"/>" value="${prpLclaim.damageAreaCode}">
							<input type="hidden" name="riskcode" value="${prpLclaim.riskCode}"> 
							<input type="hidden" name="policyno" value="${prpLclaim.policyNo}">    
							<input type="hidden" name="registno" value="${prpLclaim.registNo}">    
							<input type="hidden" name='payFee' value="${payFlag}">
							<input type="hidden" name='BaseCurrency1' value="${prpDexch.baseCurrency}">
							<input type="hidden" name='ExchRate1' value="${prpDexch.exchRate}">
							<input type="hidden" name="prpLclaimLanguage" title="<s:text name="db.prpLregist.language"/>" class="readonly" readonly="true" style="width:140px" value="${prpLclaim.language}">
							<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
							<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
							<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
							<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
							<input type="hidden" name="prpLclaimOthFlag" value="${prpLclaim.othFlag}">
							<input type="hidden" name="underWriteEndDate" value="${prpLclaim.underWriteEndDate}">
							<input type="hidden" name="damageStartDate" value="${prpLclaim.damageStartDate}">
							<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
							<input type="hidden" name="reportTimeMessage" value="${reportTimeMessage }">
							<c:if test="${not empty claim_days}">
							<input type="hidden" name='claim_days' value="${claim_days}">
							</c:if>
							<c:if test="${empty claim_days}">
							<input type="hidden" name='claim_days' value="1">
							</c:if>
							<c:if test="${not empty standardDays}">
							<input type="hidden" name='standardDays' value="${standardDays}">
							</c:if>
							<c:if test="${empty standardDays}">
							<input type="hidden" name='standardDays' value="100">
							</c:if>
							<!--modify by qinyongli 2005-07-22 start 联共保和股东信息 临分 追溯期 报案日期-->
							<c:if test="${not empty coinsFlag}">
							<input type="hidden" name="coinsFlag" value="${coinsFlag}">
							</c:if>
							<c:if test="${empty coinsFlag}">
							<input type="hidden" name="coinsFlag" value="0">
							</c:if>
							<c:if test="${not empty shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
							</c:if>
							<c:if test="${empty shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="0">
							</c:if>
							<c:if test="${not empty tempReinsFlag}">
							<input type="hidden" name="tempReinsFlag" value="${tempReinsFlag}">
							</c:if>
							<c:if test="${empty tempReinsFlag}">
							<input type="hidden" name="tempReinsFlag" value="0">
							</c:if>
							<c:if test="${not empty liabStartDate}">
							<input type="hidden" name="liabStartDate" value="${liabStartDate}">
							</c:if>
							<c:if test="${empty liabStartDate}">
							<input type="hidden" name="liabStartDate" value="0">
							</c:if>
							<c:if test="${not empty registDate}">
							<input type="hidden" name="prpLclaimReportDate1" value="${registDate}">
							</c:if>
							<c:if test="${empty registDate}">
							<input type="hidden" name="prpLclaimReportDate1" value="0">
							</c:if>
							<s:text name="query.xianzhongName" />
						</td><%--险种名称--%>
						<td class="right"><c:out value="${requestScope.riskCName}"/> </td>
						<td class="left"><s:text name="db.prpLclaim.claimNo" /></td>
						<td class="right">
							<input type=text name="prpLclaimClaimNo" title="<s:text name="db.prpLperson.claimNo"/>" maxlength="22" class="readonly" readonly="true"  value="${prpLclaim.claimNo}">
						</td><!-- update by wangxing 2008-8-4 赔案号改为立案号 -->
						<td class="left"><s:text name="db.prpLcompensate.caseNo" /></td><%--结案号--%>
						<td class="right">
							<input type=text name="prpLclaimCaseNo" title="<s:text name="db.prpLcompensate.caseNo"/>" class="readonly" readonly="true"  maxlength="22" style="width:140px" value="${prpLclaim.caseNo}">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLclaim.registNo" /></td> <%--备案号码--%>
						<td class="right">
							<input type=text name="prpLclaimRegistNo" title="<s:text name="db.prpLcomponent.registNo"/>" class="readonly" readonly="true"  value="${prpLclaim.registNo}">
						</td>
						<td class="left" colspan="2">
						<input type="hidden" name="damageDate" value="${prpLclaim.damageStartDate}">
						<input type=button class="bigbutton"  name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />" onclick="backWardPolicy(fm.coreURL.value,fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,fm.damageDate.value,fm.prpLclaimComCode.value);"><%--出险时保单信息--%>
						</td>
						<td class="left"><div style="display:none"><s:text name="db.prpLclaim.lflag" /></div></td>
						<td class="right">
						<div style="display:none">
						<s:select name="lflag" listKey="key" listValue="value" list="#request.claimFlagList"  />
						</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<c:if test="${prpLclaim.riskCode=='CC'}">
		<br>
		<%-- add by zhuyongwei reason:增加 银行卡相关信息  欄位 begin --%>
		<table  class=subtable cellpadding="0" cellspacing="1" >
			<tr>
				<td>
					<table  class=common cellpadding="1" cellspacing="1">
						<tr>
							<td class="left"><s:text name="db.prpLclaim_credit.issuingBank" />:</td><%-- 發卡銀行 --%>
							<td class="right">
								<input type="hidden" name="prpLclaimCreditNodeType" value="claim">
								<input type="hidden" name="prpLclaimCreditPolicyNo" value="${prpLclaim.policyNo }">
								<input type="hidden" name="prpLclaimCreditRiskCode" value="${prpLclaim.riskCode }">
								<c:set var="prpLclaimCredit" value="${claimDto.prpLclaimCredit}" scope="page"/>
								<select name="prpLclaimCreditBankCode" id="prpLclaimCreditBankCode">
									<option value=""></option>
									<c:forEach items="${requestScope.creditBankList}" var="prpDcode">
										<option value="${prpDcode.id.codeCode}">${prpDcode.id.codeCode}.${prpDcode.codeCName} </option>
									</c:forEach>
								</select>
								<script type="text/javascript">
									$("#prpLclaimCreditBankCode").val("${prpLclaimCredit.bankCode}");
								</script>
							</td>
							<td class="left"><s:text name="db.prpLclaim_credit.creditCardType" />:</td><%--卡別--%>
							<td class="right" >
								<select name="prpLclaimCreditCardCode" id="prpLclaimCreditCardCode" style="width: 250px">
									<option value=""></option>
									<c:forEach items="${requestScope.creditTypeList}" var="prpDcode">
										<option value="${prpDcode.id.codeCode}">${prpDcode.id.codeCode}-${prpDcode.codeCName}</option>
									</c:forEach>
								</select>
								<script type="text/javascript">
									$("#prpLclaimCreditCardCode").val("${prpLclaimCredit.cardCode}");
								</script>
								<!-- 
								<input type="radio" name="prpLclaimCreditCardType" value="1" <c:if test="${prpLclaimCredit.cardType==null||prpLclaimCredit.cardType==''||prpLclaimCredit.cardType=='1' }"> checked="checked" </c:if> onclick="changeOtherCardTypeValue(this);"/> <s:text name="db.prpLclaim_credit.whiteGoldCard" /><%-- 白金卡 --%>
								<input type="radio" name="prpLclaimCreditCardType" value="2" <c:if test="${prpLclaimCredit.cardType=='2' }"> checked="checked" </c:if> onclick="changeOtherCardTypeValue(this);"/> <s:text name="db.prpLclaim_credit.goldCard" /><%-- 金卡 --%>
								<input type="radio" name="prpLclaimCreditCardType" value="3" <c:if test="${prpLclaimCredit.cardType=='3' }"> checked="checked" </c:if> onclick="changeOtherCardTypeValue(this);"/> <s:text name="db.prpLclaim_credit.classicCard" /><%-- 普卡 --%>
								<input type="radio" name="prpLclaimCreditCardType" value="4" <c:if test="${prpLclaimCredit.cardType=='4' }"> checked="checked" </c:if> onclick="changeOtherCardTypeValue(this);"/> <s:text name="db.prpLclaim_credit.otherCard" /><%-- 其他--%>
								 -->
							</td>
							<td class="left">
								<!-- 
								<input type="text" name="prpLclaimCreditCardOtherType" <c:if test="${prpLclaimCredit.cardType!='4' }">class="readOnly"  readonly </c:if> <c:if test="${prpLclaimCredit.cardType=='4' }"> class="input" </c:if>  value="${prpLclaimCredit.cardOtherType }"> 
								 -->
							</td>
						</tr>
						<tr>
							<td class="left"><s:text name="db.prpLclaim_credit.creditCardNo" />:</td><%--信用卡號碼--%>
							<td class="right">
								<input type=text name="prpLclaimCreditCardNo" class="input" value="${prpLclaimCredit.cardNo}" maxlength="16">
							</td>
							<td class="left">到期年月：</td>
							<td class="right">
								<c:if test="${not empty prpLclaimCredit.validDateYear}">
									<c:set var="CreditValidDate" value="${prpLclaimCredit.validDateYear}年${prpLclaimCredit.validDateMonth}月"></c:set>
								</c:if>
								<input type="text" name="prpLclaimCreditValidDate" title="到期年月" style="width:120px" value="${pageScope.CreditValidDate}" onclick="WdatePicker({dateFmt:'yyyy年MM月',yearOffset:0});" class="Wdate input" />
							</td>
							<td class="left">
							</td>
						</tr>
						<tr>
							<td class="left"><s:text name="db.prpLclaim_credit.cardholderName" />:</td><%--持卡人姓名--%>
							<td class="right" >
								<input type=text name="prpLclaimCreditHolderName" class="input"value="${prpLclaimCredit.holderName}">
							</td>
							<td class="left"><s:text name="db.prpLclaim_credit.cardholderIDNo"/>:</td><%--持卡人身份證字號--%>
							<td class="right">
								<input type=text name="prpLclaimCreditHolderIdentifyNumber" class="input" value="${prpLclaimCredit.holderIdentifyNumber}">
							</td>
							<td class="left">
							</td>
						</tr>
						<tr>
							<td class="left"><s:text name="db.prpLclaim_credit.cardholderTel" />:</td><%--持卡人電話--%>
							<td class="right">
								<input type=text name="prpLclaimCreditHolderTel" class="input" value="${prpLclaimCredit.holderTel}">
							</td>
							<td class="left"><s:text name="db.prpLclaim_credit.cardholderPhone" />:</td><%--持卡人手機--%>
							<td class="right">
								<input type=text name="prpLclaimCreditHolderPhone" class="input"  value="${prpLclaimCredit.holderPhone}">
							</td>
							<td class="left">
							</td>
						</tr>
						<tr>
							<td class="left"><s:text name="db.prpLclaim_credit.relationship" />:</td><%-- 與被保險人關係 --%>
							<td class="right">
								<select name="prpLclaimCreditHolderRelationShip" style="width: 200px" id="prpLclaimCreditHolderRelationShip">
									<option value="1" > 1.本人 </option>
									<option value="2" > 2.本人及配偶 </option>
									<option value="3" > 3.本人及配偶及子女 </option>
									<option value="4" > 4.配偶 </option>
									<option value="5" > 5.配偶及子女 </option>
								</select>
								<script type="text/javascript">
									$("#prpLclaimCreditHolderRelationShip").val("${prpLclaimCredit.holderRelationShip}");
								</script>
							</td>
							<td class="left"><s:text name="db.prpLclaim_credit.cardholderAddress" />:</td><%--持卡人居住地址--%>
							<td class="right" >
								<input type=text name="prpLclaimCreditHolderAddress" class="input" value="${prpLclaimCredit.holderAddress}">
							</td>
							<td class="left">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			$(function(){
				$("#prpLclaimCreditBankCode").change(function(){
					var $cardCode = $(":input[name='prpLclaimCreditCardCode']");
					$cardCode.empty();
					if(this.value != ""){
						$.ajax({
							type:"POST",
							url:"/claim/getPrpDcode.do",
							dataType: "html",
							data: "codeType=CreditType&codeLevel=2&upperCode=" + this.value,
							cache : true,
							success:function(data){
								$cardCode.append(data);
							}
						})
					}
				});
			});
		</script>
	</c:if> 
	<br>
	<table  class=subtable cellpadding="0" cellspacing="1">
		<tr>
			<td>
				<table  class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left"><s:text name="db.prpLclaim.policyNo" /></td>
						<td class="right">
						<input type=text name="prpLclaimPolicyNo" class="readonly" readonly="true" value="${prpLclaim.policyNo}">
						</td>
						<td class="left">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif"  onclick="relate(fm.prpLclaimPolicyNo.value);return false;">
						</td>
						<td class="right"></td>
						<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" /></td><%--已出险次数--%>
						<td class="right">
						<%@include file="/pages/commonLiab/regist/LiabExistRegist.jsp"%>
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLCMain.businessNature" /></td><%--业务来源--%>
						<td class="right">
						<input type="hidden" name="prpLclaimBusinessNature" value="${prpLclaim.businessNature}">
						<input type=text name="prpLclaimBusinessNatureName" title="<s:text name="db.prpLCMain.businessNature " />" class="readonly" readonly="true" value="${prpLclaim.businessNatureName}">
						</td>
						<td class="left"><s:text name="db.prpLclaim.language" /></td><%--语种--%>
						<td class="right">
						<c:if test="${prpLclaim.language =='C' }">
						<s:text name="commonAcci.claim.chinese" /><%--中文--%>
						</c:if>
						</td>
						<td class="left"><s:text name="db.prpLclaim.insuredName" /></td>
						<td class="right">
						<input type=hidden name="prpLregistInsuredCode" title="<s:text name="db.prpLclaim.insuredCode" />" class="readonly" readonly="true" value="${prpLclaim.insuredCode}">  
						<input type=hidden name="prpLclaimInsuredName" title="<s:text name="db.prpLclaim.insuredName" />" class="readonly" readonly="true" value="${prpLclaim.insuredName}">
						${prpLclaim.insuredName}
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="regist.prpLregist.insuranceTime" /></td><%--保险期间--%>
						<td class="right" colspan="2">
						<rc:rcDate name="prpLclaimStartDate" title="<s:text name='db.prpCmain.startDate'/>" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.startDate}" /><%-- 起保日期 --%>
						<c:out value="${startHour}" />
						<rc:rcDate name="prpLclaimEndDate" title="<s:text name='db.prpCmain.endDate'/>" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.endDate}" /><%-- 终保日期 --%>
						<c:out value="${endHour}" />
						</td>
						<%-- 增加“追溯日” --%>
						<td class="left"><s:text name="db.prpCmain.liabStartDate"/></td>
						<td class="right">
							<rc:rcDate name="prpLclaimBKWardStartDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLclaim.bkWardStartDate}" format="yyyy-MM-dd"/>
						</td>
						<td class="right">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="db.prpLperson.currency" /></td><%--币别--%>
						<td class="right">
						<input class="readonly" name="claimCurrency" value="${prpLclaim.estiCurrency}-${strCurrencyName}">
						<input class="readonly" type=hidden name="prpLclaimCurrency" value="${prpLclaim.estiCurrency}">
						<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">	
						</td>
						<td class="left"><s:text name="regist.prpLregist.sumAmount" /></td><%--保险金额--%>
						<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumAmount}'/>">
						<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumPremium}'/>">
						</td>
						<td class="left"></td>
						<td class="right">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<table  class=subtable cellpadding="0" cellspacing="1">
		<tr>
			<td>
				<table  class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" /></td><%--出险时间--%>
						<td class="right">
						<rc:rcDate name="prpLclaimDamageStartDate" title="<s:text name='regist.prpLregist.damageTime'/>" style="width:120px" value="${prpLclaim.damageStartDate}"/>日
						<input  name="prpLclaimDamageStartHour" title="<s:text name="db.prpLregist.damageHour" />" class="readonly"  readonly="true" maxlength="2" style="width:20px" value="${prpLclaim.damageStartHour}">時
						<input  name="prpLclaimDamageStartMinute" title="<s:text name="db.prpLregist.damageMinute" />" class="readonly"  readonly="true"  maxlength="2" style="width:20px" value="${prpLclaim.damageStartMinute}">分
						<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="left"><s:text name="certify.dateReceipt"/>:</td><%-- 收件日期 --%>
						<td class="right">
						<rc:rcDate name="prpLclaimReceiptDate" title="收件日期" style="width:187px" value="${prpLclaim.receiptDate}" format="yyyy-MM-dd HH:mm" /><%-- 收件日期 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="left"><s:text name="db.prpLclaim.damageName" /></td>
						<td class="right">
						<!--reason:选择险别出错 -->
						<input type=text class="codecode" name="prpLclaimDamageCode"  style="width:34%" title="出险原因" value="${prpLclaim.damageCode}"
						ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
						onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
						onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" >
						<input type=text class="codecode" name="prpLclaimDamageName"  title="<s:text name="db.prpLregist.damageCode" />" style="width:46%" value="${prpLclaim.damageName}"
						ondblclick="code_CodeSelect(this, 'DamageCode','0,-1','Y','Y',fm.riskcode.value);"
							onchange="code_CodeChange(this, 'DamageCode','0,-1','Y','Y',fm.riskcode.value);"
						onkeyup="code_CodeSelect(this, 'DamageCode','0,-1','Y','Y',fm.riskcode.value);" >
						<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" /></td><%--出险地点--%>
						<td class="right" colspan="5">
						<select name="countryFlag" style="width:100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
						<option value="0"><s:text name="commonAcci.claim.domestic" /></option><%--国内--%>
						<option value="1"><s:text name="commonAcci.claim.abroad" /></option><%--国外--%>
						</select>
						<input type=text class="codecode" name="countryCode" style="display:none"/>
						<input type=text class="codecode" name="countryCName" style="display:none" title="<s:text name='common.select.country'/>" style="width:120px"
						ondblclick = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
							onkeyup = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
								onchange = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"/>
						<input type=text class="codecode" name="prpLclaimAddressCode" style="width: 40px;" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLclaim.addressCode}"
							ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"
							onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" > 
						<input type=text class="codecode" name="prpLclaimAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLclaim.addressName}"
							ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"
							onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"> 
						<%-- <input type=text class="codecode" name="provinceCode" style="display:none"/>
						<input type=text class="codecode" name="provinceName" title="<s:text name='common.select.province'/>" style="width:120px"
						ondblclick = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
							onkeyup = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
								onchange = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"/>
						<input type=text class="codecode" name="cityCode" style="display:none"/>
						<input type=text class="codecode" name="cityName" title="<s:text name='common.select.city'/>" style="width:120px"
						ondblclick = "code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);"
							onkeyup = "code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);"
								onchange = "code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);"/> --%>
						<input type=text name="prpLclaimDamageAddress" title="<s:text name='db.prpLregist.damageAddress'/>"  value="${prpLclaim.damageAddress}"
						onclick = "showProvinceCity(this,'countryCName','prpLclaimAddressName');" style="width:350px" class="input" selectValue="${prpLclaim.addressName}"><img src="/claim/images/bgMarkMustInput.jpg">
						</td>
					</tr>
					<tr>
						<td class="left"><s:text name="claim.otherClaimsInterm" /></td><%--是否有其他理赔中介机构--%>
						<td class="right">
						<select name="thirdComFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
						</td>
						<td class="left"><s:text name="claim.possibleRec" /></td><%--是否可能有追偿--%>
						<td class="right">
						<select name="replevyFlag" <c:if test="${prpLclaim.replevyFlag=='0'}">selected="selected"</c:if>>
							<option value="0" >
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1" <c:if test="${prpLclaim.replevyFlag=='1'}">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
						</td>
						<td class="left"><s:text name="claim.recoverAge" /></td><%--追偿时效--%>
						<td class="right">
						<rc:rcDate name="ReplevyLimitDate" title="<s:text name='common.check.endDate'/>"  style="width:90px"   value="${prpLclaim.replevyLimitDate}" onkeypress="return pressFullDate(event);" />
						<%-- <input type=text name="ReplevyLimitDate" class="query" value="${prpLclaim.replevyLimitDate}" description ="<s:text name='common.check.endDate'/>"  onkeypress="return pressFullDate(event);" > --%>
						<c:if test="#request.editType =='ADD' || #request.editType =='EDIT'">
						<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.ReplevyLimitDate', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()-15 %>', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()+2 %>')">
						</c:if>
						</td>
					</tr>
					<tr>
						<td class="left">
							追償說明：
							<%-- 是否可能有追偿 --%>
						</td>
						<td class="right" colspan="3">
							<input name="prpLclaimReplevyRemark" class="common" value="${prpLclaim.replevyRemark }">
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left"><s:text name="commonAcci.claim.involvedLitigat" /></td><%--是否涉及诉讼--%>
						<td class="right">
						<select name="referLawFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
						</td>
						<td class="left"></td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<br>