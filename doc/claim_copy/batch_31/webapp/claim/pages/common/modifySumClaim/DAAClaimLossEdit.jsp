<%--
****************************************************************************
* DESC       ：显示(车险)立案登记的险别估损金额页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes" %>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
//显示危险单位划分信息
function viewDangerUnit(field) {
	for ( var i = 1; i < fm.prpLclaimLossSerialNo.length; i++) {
		if (fm.prpLclaimLossDangerNo[i] == field) {
			var count = i;
			var policyNo = fm.policyno.value;
			var damageDate = fm.prpLclaimDamageStartDate.value;
			var submitStr = "getDangerUnit.do?policyNo=" + policyNo
					+ "&damageDate=" + damageDate + "&openerIndex=" + count
					+ "&PageType=ClaimLoss";
			window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		}
	}
}
//按钮单击事件，用於条款的显示
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLclaimLossKindName']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
		/** 險種範圍選擇31、3A、3D、3F、3H時，範圍可選擇體傷、殘廢、死亡 */
		//mantis：CLM0225，處理人員：DP0713，需求單編號：新核心-任意險電動車新商品責任險估損預設範圍調整 C1選項調整
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 START
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		var pskindArray = new Array("31","3A","3D","3F","3H","C1","C3","G1","G4");
		var pskindArray2 = new Array("C4");
		var pskindArray3 = new Array("G2");
		//mantis：CLM0297 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品H1~H6調整理賠系統 只留車損
		var pskindArray4 = new Array("G3","H1","H2","H3","H4","H5","H6");
		var opts = $(":input[name='prpLclaimLossFeeCategory']").eq(0).html();
		$(":input[name='prpLclaimLossKindCode']").bind("input propertychange",function(event){
			var event = event.originalEvent || event;
			if(event.propertyName == 'value' && $.trim(this.value).length != 0 ){
				var $tr = $(this).closest("tr");
				var $feeCategory = $tr.find(":input[name='prpLclaimLossFeeCategory']");
				var v = $feeCategory.val();
				$feeCategory.empty();
				var $opts = $(opts);
				if($.inArray(this.value,pskindArray) >= 0){
					$feeCategory.append($opts.filter("option[value='M'],option[value='H'],option[value='D']"));
				} else if($.inArray(this.value,pskindArray2) >= 0){
					$feeCategory.append($opts.filter("option[value='C'],option[value='G'],option[value='O']"));
				}else if($.inArray(this.value,pskindArray3) >= 0){
					$feeCategory.append($opts.filter("option[value='G']"));
				}else if($.inArray(this.value,pskindArray4) >= 0){
					$feeCategory.append($opts.filter("option[value='C']"));
				} else {
					$feeCategory.append($opts);
				}
				$feeCategory.val(v);
			}
		});
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 END
	})
</script>
<!-- mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常  START-->
<!-- 來源:\claim\webapp\claim\pages\DAA\claim\DAAClaimLossEdit.jsp -->
<%-- ** 各险别赔款限额 ** --%>
<div style="display: none" id="limitList">
  <c:forEach items="${requestScope.limitList}" var="mapObject">
     <div name="limitObject">
        <input type="hidden" name="limitKindCode" value="${mapObject['limitKindCode']}"><%--/**受限险别代码*/--%>
        <input type="hidden" name="limitKindName" value="${mapObject['limitKindName']}"><%--/**受限险别名称*/--%>
        <input type="hidden" name="limitAmount" value="${mapObject['limitAmount']}"><%--/**每事故限额*/--%>
        <input type="hidden" name="limitPastPay" value="${mapObject['limitPastPay']}"><%--/**本案险别已赔付*/--%>
        <input type="hidden" name="limitPastPayE" value="${mapObject['limitPastPayE']}"><%--/**本案超額险别已赔付*/--%>
        <input type="hidden" name="limitPersonPastPay" value="${mapObject['limitPersonPastPay']}"><%--/**本案险别人伤已赔付，limitType为2时会有值*/--%>
        <input type="hidden" name="limitFlag" value="${mapObject['limitFlag']}"><%--/**状态 0：接受限额控制；1：不受限*/--%>
        <input type="hidden" name="limitMeter" value="${mapObject['limitMeter']}"><%--/**计次状态：0赔付次数达限*/--%>
        <input type="hidden" name="limitMaxNum" value="${mapObject['limitMaxNum']}"><%--/**可赔付次数：limitMeter为0时会有值*/--%>
        <input type="hidden" name="limitType" value="${mapObject['limitType']}"><%--/**限制类型：0每次事故,1每次事故每人,2每次事故每人财产单独*/--%>
        <input type="hidden" name="limitPropAmount" value="${mapObject['limitPropAmount']}"><%--/**财产限额 limitType为2时会有值，代表车物损赔付部分的限额*/--%>
        <input type="hidden" name="limitPersonAmount" value="${mapObject['limitPersonAmount']}"><%--/**每人限额 limitType为1\2时会有值，*/--%>
        <input type="hidden" name="limitResidue" value="${mapObject['limitResidue']}"><%--/**累计型的：剩余赔付；-1时代表非累计型*/--%>
        <input type="hidden" name="limitTotalPay" value="${mapObject['limitTotalPay']}"><%--/**累计型的：历史已赔付；limitResidue非-1时有值*/--%>
        <input type="hidden" name="limitDeductible" value="${mapObject['limitDeductible']}"><%--/**自负额*/--%>
        <input type="hidden" name="limitDeductibleRate" value="${mapObject['limitDeductibleRate']}"><%--/**自负额比例*/--%>
        <input type="hidden" name="limitDeductibleTypeConfirm" value="${mapObject['limitDeductibleTypeConfirm']}"><%--<%/**自负额型態確認抬頭*/%>--%>
        <input type="hidden" name="limitDeductibleCount" value="${mapObject['limitDeductibleCount']}"><%--<%/**有效保期內已使用次數*/%>--%>
     </div>
  </c:forEach>
  <c:forEach items="${requestScope.pastPersonPayList}" var="map">
      <input type="hidden" name="${map.key}" value="${map.value}">
  </c:forEach>
</div>
<!-- mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常  END-->
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" cellpadding="5" cellspacing="1">
	<input type="hidden" name="configCode" value="<%=(String) request.getAttribute("configCode")%>">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
			<%--reason:增加对估损金额公式的提示 --%>
			<s:text name="claim.amountInsurLossInfo" />
			&nbsp;
			<%--险别估损金额信息 --%>
			<c:choose>
				<c:when test="${prpLclaim.riskCode==RISKCODE_DAZ}">
					<font color="#FF0000"><s:text name="prompt.claim.lossAmountCalFormula2" /></font>
					<%--估损金额计算公式：险别估损金额=上报估损金额 --%>
				</c:when>
				<c:otherwise>
					<font color="#FF0000"><s:text name="prompt.claim.lossAmountCalFormula" /></font>
					<%--估损金额计算公式：险别估损金额=上报估损金额*事故责任比例*（1-事故责任免赔率） --%>
				</c:otherwise>
			</c:choose>
			<br> <span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr name="prpLclaimLossObject">
							<%--增加多危险单位--%>
							<td class="input" style="width: 5%">
								<%/** 估損調整增加的估損訊息 來源為 2 */%>
								<input type="hidden" name="prpLclaimLossDatafrom" value="2">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onclick="viewDangerUnit(this);">
							</td>
							<%--估损金额调整 --%>
							<td class="input" style="width: 6%">
								<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" onchange="calculateSumClaimNew(this);" />
							</td>
							<td class="input" style="width: 15%" style="align:center">
								<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 20%" title="險別"
									ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onchange="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);">
								<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 70%" title="險別"
									ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onchange="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);">
								<input name="prpLclaimLossItemKindNo" type="hidden">
								<input name="prpLclaimLossSerialNo" type="hidden">
								<input type="hidden" name="prpLclaimLossKindCode1">
							</td>
							<td class="input" style="width: 6%" align="center">
								<s:select name="prpLclaimLossFeeCategory" listKey="key" listValue="value" list="#request.lossFeeCategoryList" onchange="calculateSumClaimNew(this);" />
							</td>
							<td class="input" style="width: 9%" align="center">
								<input name="prpLclaimLossItemCode" type="hidden">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" style="width: 30%" title="幣別" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								<input type="text" name="prpLclaimLossCurrencyName" class="readonly" readonly="true" style="width: 60%" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
								<%--人民币 --%>
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLclaimLossKindLoss" description="上报估损金额" maxlength="14" class=common style="text-align: right" onchange="calculateSumClaimNew(this);" value="0">
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLclaimLossSumClaim" description="险别估损金额" class="readonly" readonly style="text-align: right" value="0">
								<input type="hidden" name="prpLclaimLossAcciDeductiblePay" description="责任免赔额" value="0">
								<input type="hidden" name="prpLclaimLossAcciDeductibleRate" description="责任免赔率" value="0">
							</td>
							<td class="input" style="width: 6%">
								<input name="prpLclaimLossKindRest" class=common style="width: 50px" maxlength="14" description="残值" value="0">
							</td>
							<!-- delete 20150618 需求變更095
							<td class="input" style="width: 10%">
								<s:select name="prpLclaimLossAccidentType" list="#attr.accidentTypeList" listKey="key" listValue="value"  style="width:95%;"></s:select>
							</td>
							 -->
							<td class="input" style="width: 8%">
								<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" defaultValue="0" />
							</td>
							<td class="input" style="width: 9%">
								<input type="hidden" name="prpLclaimLossHandlerCode" value="${user.userCode}" class="readonly" readonly="readonly" style="width: 40%;">
								<input type="hidden" name="prpLclaimLossHandlerName" value="${user.userName}" class="readonly" readonly="readonly" style="width: 50%;">
								${user.userCode}${user.userName}
							</td>
							<td class="centertitle" style="width: 6%">
								<input type=button ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
								<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
									<table class="common">
										<tr>
											<td class="prompttitle" colspan="6">
												<s:text name="claim.adjustReason" />
												<%-- 调整原因 --%>
											</td>
										</tr>
										<tr>
											<td class="prompt" colspan="6">
												<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100">
											</td>
										</tr>
										<tr>
											<td colspan=6 class="common">
												<input type=button class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' <%--关闭--%>
														ACCESSKEY="O"
													onclick="hideSubPage(this,'span_Engage_Context00')">
											</td>
										</tr>
									</table>
								</span>
							</td>
							<td class="input" style='width: 6%'  align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deletePrpLclaimLossObject(this);collectClaimLossNew(this);" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanClaimLoss" style="display:"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="ClaimLoss">
					<thead>
						<tr>
							<td class="centertitle" style="width: 5%">
								<s:text name="claim.dangeSerialNum" />
								<%-- 危险单位序号 --%>
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="claim.damageCategory" />
								<%-- 损伤类别 --%>
							</td>
							<td class="centertitle" style="width: 15%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
								<%-- 险别 --%>
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="claim.scope" />
								<%-- 范围 --%>
							</td>
							<td class="centertitle" style="width: 9%">
								<s:text name="regist.prpLregist.currency" />
								<%-- 币别 --%>
							</td>
							<td class="centertitle" style="width: 7%">
								<s:text name="claim.reportEstimLoss" />
								<%-- 上报估损金额 --%>
							</td>
							<td class="centertitle" style="width: 7%">
								<s:text name="claim.amountInsurLoss" />
								<%-- 险别估损金额 --%>
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="claim.salvage" />
								<%-- 残值 --%>
							</td>
							<!-- delete 20150618 需求變更095
							<td class="centertitle" style="width: 10%">
								肇責類型
							</td>
							-->
							<td class="centertitle" style="width: 8%">
								修改日期
								<%-- 输入日期 --%>
							</td>
							<td class="centertitle" style="width: 9%">
								修改人員
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="claim.adjustReason" />
								<%-- 调整原因 --%>
							</td>
							<td class="centertitle" style="width: 6%">
								<input onclick="collectCurrency();" type="button" class="button" value="<s:text name="modifySumClaim.damageSum" />">
								<%--估损合计 --%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=11>
								<s:text name="prompt.claim.addRemove" />
								<%--(按"+"号键增加立案估损信息，按"-"号键删除信息) --%>
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertPrpLclaimLossObject();" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="prpLclaimLoss" items="${claimDto.prpLclaimLossList}">
							<c:if test="${prpLclaimLoss.kindCode!='M'}">
								<tr name="prpLclaimLossObject">
									<td class="input" style="width: 5%">
										<%/** 历史记录本次不计 */%>
										<input type="hidden" name="prpLclaimLossDatafrom" value="">
										<c:if test="${prpLclaimLoss.dangerNo != '0'}">
											<input readonly type=text name="prpLclaimLossDangerNo" class="codecode" value="${prpLclaimLoss.dangerNo}">
										</c:if>
										<c:if test="${prpLclaimLoss.dangerNo == '0'}">
											<input readonly type=text name="prpLclaimLossDangerNo" class="codecode" value="1">
										</c:if>
									</td>
									<td class="input" style="width: 6%">
										<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> <c:set var="tempSelectedValue" value="${prpLclaimLoss.lossFeeType}" /> <s:select
												name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" onchange="calculateSumClaimNew(this);" />
										</span>
									</td>
									<td class="input" style="width: 15%" style="align:center">
										<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 20%" title="險別" readonly="readonly" value="${prpLclaimLoss.kindCode}">
										<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 70%" title="險別" readonly="readonly" value="${prpLclaimLoss.kindName}">
										<input type="hidden" name="prpLclaimLossItemKindNo" value="${prpLclaimLoss.itemKindNo}">
										<input name="prpLclaimLossSerialNo" type="hidden" value="${prpLclaimLoss.id.serialNo}">
										<input type="hidden" name="prpLclaimLossKindCode1" value="${prpLclaimLoss.kindCode}">
									</td>
									<td class="input" style="width: 6%" align="center">
										<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
										<c:set var="tempSelectedValue" value="${prpLclaimLoss.feeCategory}" />
										<s:select name="prpLclaimLossFeeCategory" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossFeeCategoryList" disabled="disabled" />
										</span>
									</td>
									<td class="input" style="width: 9%" align="center">
										<input type=text name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="幣別" value="${prpLclaimLoss.currency}">
										<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="幣別" value="${prpLclaimLoss.currencyName}">
										<input type="hidden" name="prpLclaimLossItemCode" value="${prpLclaimLoss.itemCode}">
									</td>
									<td class="input" style="width: 7%">
										<input name="prpLclaimLossKindLoss" class=common style="text-align: right" description="上报估损金额" value="<fmt:formatNumber pattern='#' value='${prpLclaimLoss.kindLoss}'/>" readonly="readonly">
									</td>
									<td class="input" style="width: 7%">
										<input name="prpLclaimLossSumClaim" class="readonly" readonly style="text-align: right" description="险别估损金额" value="<fmt:formatNumber pattern='#' value='${prpLclaimLoss.sumClaim}'/>">
										<input type="hidden" name="prpLclaimLossAcciDeductiblePay" description="责任免赔额" value="${prpLclaimLoss.acciDeductiblePay}">
										<input type="hidden" name="prpLclaimLossAcciDeductibleRate" description="责任免赔率" value="${prpLclaimLoss.acciDeductibleRate}">
									</td>
									<td class="input" style="width: 6%">
										<input name="prpLclaimLossKindRest" class=common style="width: 50px" value="<fmt:formatNumber pattern='#' value='${prpLclaimLoss.kindRest}'/>" description="残值" readonly="readonly" >
									</td>
									<!-- delete 20150618 需求變更095 
									<td class="input" style="width: 10%">
										<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
										<s:select  name="prpLclaimLossAccidentType" list="#attr.accidentTypeList" listKey="key" listValue="value" value="#attr.prpLclaimLoss.accidentType" style="width:95%;" ></s:select>
										</span>
									</td>
									 -->
									<td class="input" style="width: 8%">
										<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaimLoss.inputDate}" />
									</td>
									<td class="input" style="width: 9%">
										<input type="hidden" name="prpLclaimLossHandlerCode" value="${prpLclaimLoss.handlerCode}" class="readonly" readonly="readonly" style="width: 40%;">
										<input type="hidden" name="prpLclaimLossHandlerName" value="${prpLclaimLoss.handlerName}" class="readonly" readonly="readonly" style="width: 50%;">
										${prpLclaimLoss.handlerCode}${prpLclaimLoss.handlerName}
									</td>
									<td class="centertitle" style="width: 6%">
										<input type=button ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
										<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
											<table class="common">
												<tr>
													<td class="prompttitle" colspan="6">
														<s:text name="claim.adjustReason" />
														<%-- 调整原因 --%>
													</td>
												</tr>
												<tr>
													<td class="prompt" colspan="6">
														<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100" value="${prpLclaimLoss.remarkFlag}" >
													</td>
												</tr>
												<tr>
													<td colspan=6 class="common">
														<input type=button class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' ACCESSKEY="O" onclick="hideSubPage(this,'span_Engage_Context00')">
													</td>
												</tr>
											</table>
										</span>
									</td>
									<td class="input" style='width: 6%' align="center">
										<div>
											<input type=button name="buttonClaimLossDelete" class=smallbutton onclick="deletePrpLclaimLossObject(this);collectClaimLossNew(this);" disabled="disabled" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
<script type="text/javascript">
//jquery ajax 请求
$(document).ready(
	function () {
		$("input:enabled").filter(":button,:submit,:reset").ajaxStart(
			function () {
				$(this).attr("disabled", true); //请求开始禁用按钮
			}).ajaxComplete(function () {
			$(this).attr("disabled", false); //请求完成恢复按钮
		});
	});
</script>
