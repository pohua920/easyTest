
<%@page import="com.sinosoft.claim.schema.model.PrpLacciCheck"%><%--
****************************************************************************
* DESC       :添加主信息子块界面页面
* AUTHOR     : 理赔组
* CREATEDATE : 2004-06-03 
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<input type="hidden" name="referKind">
				<input type="hidden" name="prpLcheckRiskCode" value="${ prpLcheck.riskCode}" />
				<input type="hidden" name="prpLcheckFlag" value="${ prpLcheck.flag}" />
				<input type="hidden" name="prpLcheckReferSerialNo" value="${ prpLcheck.id.referSerialNo}" />
				<input type="hidden" name="prpLcheckInsureCarFlag" value="${ prpLcheck.insureCarFlag}" />
				<input type="hidden" name="prpLacciCheckTimes" value="${ prpLacciCheck.times}" />
				<input type="hidden" name="prpLacciCheckCertiNo" value="${ prpLacciCheck.certiNo}" />
				<input type="hidden" name="prpLacciCheckRiskCode" value="${ prpLacciCheck.riskCode}" />
				<input type="hidden" name="riskCode" value="${ prpLacciCheck.riskCode}" />
				<input type="hidden" name="prpLacciCheckCertiType" value="${ prpLacciCheck.certiType}" />
				<input type="hidden" name="prpLacciCheckCheckNo" value="${ prpLacciCheck.checkNo}" />
				<input type="hidden" name="prpLacciCheckTimes" value="${ prpLacciCheck.times}" />
				<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID }">
				<input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo }">
				<input type="hidden" name="type" value="#parameters.type[0]">
				<input type="hidden" name="policyno" value="${ prpLacciCheck.policyNo}" />
				<input type="hidden" name="prpLcheckPolicyNo" value="${ prpLacciCheck.policyNo}" />
				<input type="hidden" name="registno" value="${ prpLacciCheck.registNo}" />
				<input type="hidden" name="prpLcheckRegistNo" value="${ prpLacciCheck.registNo}" />
				<input type=hidden name="prpLregistComCode" title="歸屬機構" class="ReadOnly" ReadOnly style="width: 120px" value="${ prpLregist.comCode}" />
				<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
				<td class="left">
					<s:text name="certainLoss.prpLacciCheck.riskCName" />:
				</td>
				<%--险种名称 --%>
				<td class="right">${riskCName}</td>
				<td class="left"></td>
				<td class="right"></td>
				<td class="left"></td>
				<td class="right"></td>
				</tr>
				<tr>
					<td class="left" colspan="4">
						<c:if test="${prpLacciCheck.certiType=='01'}">
							<input type="text" class="readonly" readonly="true" name="" color="red" value="此調查由 備案 環節發起">
						</c:if>
						<c:if test="${prpLacciCheck.certiType=='03'}">
							<input type="text" class="readonly" readonly="true" name="" color="red" value="此調查由 立案 環節發起">
						</c:if>
						<c:if test="${prpLacciCheck.certiType=='05'}">
							<input type="text" class="readonly" readonly="true" name="" color="red" value="此調查由 審核 環節發起">
						</c:if>
						<c:if test="${prpLacciCheck.certiType=='07'}">
							<input type="text" class="readonly" readonly="true" name="" color="red" value="此調查由 計算書 環節發起">
						</c:if>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckRegistNo" />
					</td>
					<%--报案号 --%>
					<td class="right">
						<input type=text name="prpLacciCheckRegistNo" class="readonly" readonly="true" value="${ prpLacciCheck.registNo}" />
					</td>
					<td class="left">發起環節業務號碼：</td>
					<%--赔案号--%>
					<td class="right">
						<input type="text" name="prpLcheckClaimNo" class="readonly" readonly="true" value="${prpLacciCheck.certiNo}" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckPolicyNo" />
					</td>
					<%--保单号--%>
					<td class="right" colspan="3">
						<input type="text" name="prpLacciCheckPolicyNo" class="readonly" readonly="true" value="${ prpLacciCheck.policyNo}" style="width: 30%;"/>
						<input type="hidden" name="policyNo" class="readonly" readonly="true" value="${ prpLacciCheck.policyNo}" />
						<input type="button" name="btRelate" class='button' value="<s:text name="check.relate" />" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLacciCheckPolicyNo.value);return false;">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<%--事故时间--%>
						<s:text name="certainLoss.prpLacciCheck.prpLcheckDamageStartDate" />
					</td>
					<td class="right">
						<rc:rcDate name="prpLcheckDamageStartDate" class="readonly" readonly="true" style="width:80px" value="${ prpLacciCheck.damageStartDate}"/> 日${ prpLacciCheck.damageStartHour }  时${ prpLacciCheck.damageStartMinute}分
					</td>
					<td class="left">
						<%--事故地点--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageAddress" />
					</td>
					<td class="right">
						<input type="text" name="prpLacciCheckDamageAddress" class="readonly" readonly="true" value="${ prpLacciCheck.damageAddress}" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<%--<td class="title" style="width:15%">调查类型:</td>
        <td class="input" style="width:35%" >
          <html:select name='prpLcheckDto.checkType' >
            <html:option value="L">调查</html:option>
            <html:option value="D">代调查</html:option>
           </html:select>
        </td>--%>
					<td class="left">
						<%--调查起止日期--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckTime" />
					</td>
					<td class="right" colspan=5>
						<rc:rcDate name="prpLacciCheckCheckDate" class="input" style="width:10%" value="${ prpLacciCheck.checkDate}"/>
						<input type=text name="prpLacciCheckCheckHour" class="input" style="width=10%" maxlength="2" value="${ prpLacciCheck.checkHour}">
						<s:text name="regist.prpLregist.hour" />
						<%--时--%>
						<input type=text name="prpLaccecheckCheckMinute" class="input" style="width=10%" maxlength="2" value="${ prpLacciCheck.damageStartMinute2}">
						<s:text name="regist.prpLregist.minute" />
						<%--分--%>
						<img src="/claim/images/bgMarkMustInput.jpg">
						<s:text name="prompt.to" />
						<%--至--%>
						<rc:rcDate name="prpLacciCheckCheckEndDate" class="input" style="width:10%" value="${ prpLacciCheck.checkEndDate}"/>
						<input type=text name="prpLacciCheckCheckEndHour" class="input" style="width=10%" maxlength="2" value="${ prpLacciCheck.checkEndHour}">
						<s:text name="regist.prpLregist.hour" />
						<%--时--%>
						<input type=text name="prpLacciCheckCheckEndMinute" class="input" style="width=10%" maxlength="2" value="${ prpLacciCheck.damageStartMinute3}">
						<s:text name="regist.prpLregist.minute" />
						<%--分--%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<%--调查方式--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckMethod" />:
					</td>
					<td class="right">
					<c:set var="checkNaturevalue" value="${prpLacciCheck.checkNature}"/>
					<s:select name="checkNature" value="#attr.checkNaturevalue" listKey ="key" listValue="value" list="#request.checkNatureList"/>
					</td>
					<td class="left">
						<%--已出事故次数--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageTimes" />
					</td>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/common/regist/ExistRegist.jsp"%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<%--调查对象--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckObject" />:
					</td>
					<td class="right">
						<input type="text" name="prpLacciCheckCheckObject" style="" class="input" value="${prpLacciCheck.checkObject}" />
					</td>
					<td class="left">
						<%--调查地点--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckSite" />
					</td>
					<td class="right">
						<input type="text" name="prpLacciCheckCheckSite" class="input" style="" value="${ prpLacciCheck.checkSite}" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<%--调查员 1--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckMan" />
					</td>
					<td class="right">
						<input name='prpLacciCheckCode' class='input' maxlength=20 style="" description="查勘人1" value="${ prpLacciCheck.checkerCode}" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<%--事故原因--%>
						<s:text name="db.prpLregist.damageCode" />
					</td>
					<td class="right">
						<input name="prpLacciCheckDamageCode" type="text" class="codecode" maxlength=4 description="出险原因" value="${ prpLacciCheck.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);" style="width:15%;"/>
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input name="prpLacciCheckDamageName" type=text class="codecode" maxlength="100" description="出险原因" value="${ prpLacciCheck.damageName}" ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);" onkeyup= "code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);" style="width:70%;">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<%--事故类型--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckType" />
					</td>
					<td class="right" colspan="3">
						<input type=text name="prpLacciCheckDamageTypeCode" class="codecode" title="事故原因" value="${ prpLacciCheck.damageTypeCode}" ondblclick="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');" style="width: 10%;">
						<input type=text name="prpLacciCheckDamageTypeName" class="codecode" title="事故原因" value="${ prpLacciCheck.damageTypeName}" ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');" style="width: 30%;"  >
						<img src="/claim/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle"> <img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				</tr>
				<tr>
					<td class="left">
						<%--调查内容简要描述--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckContext" />
					</td>
					<td class="right">
						<input type="text" name="prpLacciCheckCheckContext" class="readonly" readonly="true" value="${ prpLacciCheck.checkContext}">
					</td>
					<td class="left">
						<%--调查币别费用--%>
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCost" />
					</td>
					<td class="right">
						<input type="text" readonly="readonly"  name="prpLacciCheckCurrencyCode" value="${ prpLacciCheck.currency}" class="input" title="幣別" style="width: 15%;"/>
						<input type=text readonly="readonly" name="prpLacciCheckCurrencyName" class="input" title="幣別" value="${ prpLacciCheck.currencyName}"  style="width: 25%;"/>
						<input type="text" readonly="readonly" name="prpLacciCheckCheckFee" class='input' maxlength=20 value="<fmt:formatNumber value='${ prpLacciCheck.checkFee}' pattern='#'/>" style="width: 25%;">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
