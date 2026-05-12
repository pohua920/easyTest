<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.*"%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=GBK"%>
<%--
****************************************************************************
* DESC       ：显示立案登记的险别估损金额页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<script language="javascript">
//显示危险单位划分信息
	function viewDangerUnit(field) {
		for (var i = 1; i < fm.prpLclaimLossSerialNo.length; i++) {
			if (fm.prpLclaimLossDangerNo[i] == field) {
				var count = i;
				var policyNo = fm.policyno.value;
				var damageDate = fm.prpLclaimDamageStartDate.value;
				var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=ClaimLoss";
				window.open(submitStr, '查看危险单位信息', 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
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
		/** 險種範圍選擇31、3A、3D、3F、3H時，範圍可選擇體傷、失能、死亡 */
		//mantis：CLM0225，處理人員：DP0713，需求單編號：新核心-任意險電動車新商品責任險估損預設範圍調整 C1選項調整
		var pskindArray = new Array("31","3A","3D","3F","3H","C1");
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
				} else {
					$feeCategory.append($opts);
				}
				$feeCategory.val(v);
			}
		});
	})
</script>
<%-- ** 各险别赔款限额 ** --%>
<div style="display: none" id="limitList">
  <c:forEach items="${requestScope.limitList}" var="mapObject">
     <div name="limitObject">
        <input type="hidden" name="limitKindCode" value="${mapObject['limitKindCode']}"><%--/**受限险别代码*/--%>
        <input type="hidden" name="limitKindName" value="${mapObject['limitKindName']}"><%--/**受限险别名称*/--%>
        <input type="hidden" name="limitAmount" value="${mapObject['limitAmount']}"><%--/**每事故限额*/--%>
        <input type="hidden" name="limitPastPay" value="${mapObject['limitPastPay']}"><%--/**本案险别已赔付*/--%>
        <!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 -->
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
        <!-- mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START-->
        <input type="hidden" name="limitDeductibleTypeConfirm" value="${mapObject['limitDeductibleTypeConfirm']}"><%--<%/**自负额型態確認抬頭*/%>--%>
        <input type="hidden" name="limitDeductibleCount" value="${mapObject['limitDeductibleCount']}"><%--<%/**有效保期內已使用次數*/%>--%>
        <!-- mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END-->
     </div>
  </c:forEach>
  <c:forEach items="${requestScope.pastPersonPayList}" var="map">
      <input type="hidden" name="${map.key}" value="${map.value}">
  </c:forEach>
</div>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" cellpadding="5" cellspacing="1">
	<input type="hidden" name="configCode" value="${configCode}">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<!-- mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題  -->
			<input type="hidden" name="gradeLevel" value="${gradeLevel}"/>
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
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
					<font color="#FF0000"><s:text name="prompt.claim.lossAmountCalFormula2" /></font>
					<%--估损金额计算公式：险别估损金额=上报估损金额 --%>
				</c:otherwise>
			</c:choose>
			<br> <span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr name="prpLclaimLossObject">
							<%--增加多危险单位--%>
							<td class="input" style="width: 9%">
								<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onclick="viewDangerUnit(this);">
							</td>
							<%--估损金额调整 --%>
							<td class="input" style="width: 7%">
								<s:select name="prpLclaimLossLossFeeType" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" onchange="calculateSumClaimNew(this);" />
							</td>
							<td class="input" style="width: 32%" style="align:center">
								<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 20%" title="險別"
									ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onchange="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);" onblur="">
								<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 70%" title="險別"
									ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
									onchange="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);" onblur="">
								<input name="prpLclaimLossItemKindNo" type="hidden">
								<input name="prpLclaimLossSerialNo" type="hidden">
								<input type="hidden" name="prpLclaimLossKindCode1">
							</td>
							<td class="input" align="center" style="width: 7%">
								<s:select name="prpLclaimLossFeeCategory" listKey="key" listValue="value" list="#request.lossFeeCategoryList" onchange="calculateSumClaimNew(this);" />
							</td>
							<td class="input" style="width: 9%" align="center">
								<input name="prpLclaimLossItemCode" type="hidden">
								<input type="text" name="prpLclaimLossCurrency" class="readonly" style="width: 30%" title="幣別" value="${LOCAL_CURRENCY }">
								<input type="text" name="prpLclaimLossCurrencyName" class="readonly" readonly="true" style="width: 60%" value="${LOCAL_CURRENCYNAME }">
								<%--人民币 --%>
							</td>
							<td class="input" style="width: 9%">
								<input name="prpLclaimLossKindLoss" description="上报估损金额" maxlength="14" class=common style="text-align: right" onfocus="cacheData(this);" onchange="calculateSumClaimNew(this);" value="0">
							</td>
							<td class="input" style="width: 9%">
								<input name="prpLclaimLossSumClaim" description="险别估损金额" class="readonly" readonly style="text-align: right;" value="0">
								<input type="hidden" name="prpLclaimLossAcciDeductiblePay" description="责任免赔额" value="0">
								<input type="hidden" name="prpLclaimLossAcciDeductibleRate" description="责任免赔率" value="0">
							</td>
							<td class="input" style="width: 7%">
								<input name="prpLclaimLossKindRest" class=common style="width: 50px" maxlength="14" description="残值" value="0">
								<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="readonly" style="width: 85px;display: none;" wdatePicker="false" defaultValue="0"/>
							</td>
							<!-- delete by chenjie 20150601 需求變更-095 
							<td class="input" style="width: 14%;">
								<s:select name="prpLclaimLossAccidentType" list="#attr.accidentTypeList" listKey="key" listValue="value" style="width:95%;"></s:select>
							</td>
							-->
							<td class="input" style="width: 7%;">
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
												<input type=button class=button name='button_Engage_Close_Context00' value='<s:text name='button.close.value' />' ACCESSKEY="O" onclick="hideSubPage(this,'span_Engage_Context00')">
											</td>
										</tr>
									</table>
								</span>
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deletePrpLclaimLossObject(this);collectClaimLossNew(this);checkAccidentType();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanClaimLoss" style="display:"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="ClaimLoss">
					<thead>
						<c:if test="${deductible>0 }">
							<tr>
								<td colspan=2 style="width: 18%">
									<s:text name="claim.optionalFran" />：
									<%-- 可选免赔额 --%>
									<input name="prpLclaimLossAmount" style="width: 30%" readonly="true" class="readonly" value="${deductible}">
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="centertitle" style="width: 9%">
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%-- 危险单位序号 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="claim.damageCategory" />
							</td>
							<%-- 损伤类别 --%>
							<td class="centertitle" style="width: 32%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
							</td>
							<%-- 险别 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="claim.scope" />
							</td>
							<%-- 范围 --%>
							<td class="centertitle" style="width: 9%">
								<s:text name="regist.prpLregist.currency" />
							</td>
							<%-- 币别 --%>
							<td class="centertitle" style="width: 9%">
								<s:text name="claim.reportEstimLoss" />
							</td>
							<%-- 上报估损金额 --%>
							<td class="centertitle" style="width: 9%">
								<s:text name="claim.amountInsurLoss" />
							</td>
							<%-- 险别估损金额 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="claim.salvage" />
							</td>
							<%-- 残值 --%>
							<!-- delete by 20150601 需求變更-095 
							<td class="centertitle" style="width: 14%">
								肇責類型
							</td>
							 -->
							<td class="centertitle" style="width: 7%">
								<s:text name="claim.adjustReason" />
							</td>
							<%-- 调整原因 --%>
							<td class="centertitle" style="width: 4%">
								<input onclick="collectCurrency();" type="button" class="button" value="<s:text name="modifySumClaim.damageSum" />">
							</td>
							<%--估损合计 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan="9">
								<s:text name="prompt.claim.addRemove" />
							</td>
							<td class="title" align="center" style="width: 4%">
								<div>
									<input type="button" value="+" class=smallbutton onclick="insertPrpLclaimLossObject();" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<script language="javascript">
						var damageKind = new Array();
						<c:forEach items="${damageKindList }" var="prpCitemKindDto" varStatus="kindCodeStatus">
							damageKind[${kindCodeStatus.index}]   = "${prpCitemKindDto.kindCode }";
						</c:forEach>
					</script>
					<tbody>
						<c:set var ="displayType" value="readonly"/>
						<c:set var ="buttonType" value="disabled"/>
						<c:if test="${param.editType=='ADD'}">
							<c:set var ="displayType" value=""/>
							<c:set var ="buttonType" value=""/>
						</c:if>
						<c:if test="${param.editType=='EDIT'}">
			    			<c:set var ="displayType" value=""/>
							<c:set var ="buttonType" value="disabled"/>
						</c:if>
						<c:forEach items="${requestScope.prpLclaimLoss.claimLossList}" var="claimLoss" varStatus="stat">
							<c:if test="${claimLoss.kindCode!='M'}">
								<tr class=oddrow name="prpLclaimLossObject">
									<td class="input" style="width: 9%">
										<c:choose>
											<c:when test="${not empty claimLoss.dangerNo && claimLoss.dangerNo!=0}">
												<input type=text name="prpLclaimLossDangerNo" class="codecode" value="<c:out value='${claimLoss.dangerNo }'/>" onclick="viewDangerUnit(this);">
											</c:when>
											<c:otherwise>
												<input type=text name="prpLclaimLossDangerNo" class="codecode" value="1" onclick="viewDangerUnit(this);">
											</c:otherwise>
										</c:choose>
									</td>
									<td class="input" style="width: 7%">
										<c:set var="tempSelectedValue" value="${claimLoss.lossFeeType}" />
										<s:select name="prpLclaimLossLossFeeType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossLossFeeTypeList" onchange="calculateSumClaimNew(this);" />
									</td>
									<td class="input" style="width: 32%" style="align:center">
										<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 20%" title="險別" value="<c:out value='${claimLoss.kindCode }'/>"
											ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
											onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
											onchange="code_CodeChange(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);" onblur="">
										<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 70%" title="險別" value="<c:out value='${claimLoss.kindName }'/>"
											ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
											onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);"
											onchange="code_CodeChange(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);calculateSumClaimNew(this);" onblur="">
										<input type="hidden" name="prpLclaimLossItemKindNo" value="<c:out value='${claimLoss.itemKindNo }'/>">
										<input type="hidden" name="prpLclaimLossSerialNo" value="<c:out value='${claimLoss.id.serialNo }'/>">
										<input type="hidden" name="prpLclaimLossKindCode1" value="<c:out value='${claimLoss.kindCode }'/>">
									</td>
									<td class="input" style="width: 7%" align="center">
										<c:set var="tempSelectedValue" value="${claimLoss.feeCategory}" />
										<s:select name="prpLclaimLossFeeCategory" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.lossFeeCategoryList" onchange="calculateSumClaimNew(this);" />
									</td>
									<td class="input" style="width: 9%" align="center">
										<input type=text name="prpLclaimLossCurrency" class="readonly" readonly style="width: 30%" title="幣別" value="<c:out value='${claimLoss.currency}'/>">
										<input type=text name="prpLclaimLossCurrencyName" class="readonly" readonly style="width: 60%" title="幣別" value="<c:out value='${claimLoss.currencyName}'/>">
										<input type="hidden" name="prpLclaimLossItemCode" value="<c:out value='${claimLoss.itemCode }'/>">
									</td>
									<td class="input" style="width: 9%">
										<input name="prpLclaimLossKindLoss" class=common style="text-align: right" description="上报估损金额" value="<fmt:formatNumber value="${claimLoss.kindLoss}" pattern="#"/>" onfocus="cacheData(this);" onchange="calculateSumClaimNew(this);">
									</td>
									<td class="input" style="width: 9%">
										<input name="prpLclaimLossSumClaim" class="readonly" readonly style="text-align: right" value="<fmt:formatNumber value="${claimLoss.sumClaim}" pattern="#"/>">
										<input type="hidden" name="prpLclaimLossAcciDeductiblePay" description="责任免赔额" value="<fmt:formatNumber value="${claimLoss.acciDeductiblePay}" pattern="#"/>">
										<input type="hidden" name="prpLclaimLossAcciDeductibleRate" description="责任免赔率" value="<fmt:formatNumber value="${claimLoss.acciDeductibleRate}" pattern="#"/>">
									</td>
									<td class="input" style="width: 7%">
										<input name="prpLclaimLossKindRest" class=common style="width: 50px" value="<fmt:formatNumber value="${claimLoss.kindRest }" pattern="#"/>" description="残值">
										<rc:rcDate name="prpLclaimLossInputDate" class="readonly" readonly="true" style="width: 85px;display: none" value="${claimLoss.inputDate }" defaultValue="0"/>
									</td>
									<!-- delete by chenjie 20150601 需求變更-095 
									<td class="input" style="width: 14%;">
										<s:select name="prpLclaimLossAccidentType" list="#attr.accidentTypeList" listKey="key" listValue="value" value="#attr.claimLoss.accidentType" style="width:95%;"></s:select>
									</td>
									-->
									<td class="input" style="width: 7%">
										<input type=button ACCESSKEY="." num=1 value='...' name='button_Engage_Open_Context00' onclick="buttonOnClick3(this);">
										<span id="span_Engage_Context00" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
											<table class="common">
												<tr>
													<td class="prompttitle" colspan="6">
														<s:text name="claim.adjustReason" />
													</td>
													<%-- 调整原因 --%>
												<tr>
													<td class="prompt" colspan="6">
														<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100" value="<c:out value='${claimLoss.remarkFlag }'/>">
													</td>
												</tr>
												<tr>
													<td colspan=6 class="common">
														<input type=button class=button name='button_Engage_Close_Context00' value="<s:text name='button.close.value' />" ACCESSKEY="O" onclick="hideSubPage(this,'span_Engage_Context00')">
													</td>
												</tr>
											</table>
										</span>
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonClaimLossDelete" class=smallbutton onclick="deletePrpLclaimLossObject(this);collectClaimLossNew(this);checkAccidentType();" ${buttonType } value="-" style="cursor: hand">
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
<div style="margin-top: 10px;">
	<c:if test="${ editType == 'ADD' || editType == 'EDIT' || (editType == 'SHOW' && not empty prpLclaim.carAccidentType)}">
		<div id="divCarAccidentType" style="float:left;padding-left: 10px;">
			<label style="vertical-align: middle;"><s:text name="claim.carAccidentType" />：<%-- 車體險肇責類型 --%></label>
			<s:select name="prpLclaimCarAccidentType" id="prpLclaimCarAccidentType" list="#attr.accidentTypeList" listKey="key" listValue="value" value="#request.prpLclaim.carAccidentType" style="width:140px;"></s:select>
		</div>
	</c:if>
	<c:if test="${ editType == 'ADD' || editType == 'EDIT' || (editType == 'SHOW' && not empty prpLclaim.propAccidentType)}">
		<div id="divPropAccidentType" style="float:left;padding-left: 10px;">
			<c:choose>
				<c:when test="${requestScope.prpLclaim.riskCode==riskCodeBZ}">
					<label style="vertical-align: middle;"><s:text name="claim.accidentType" />：<%-- 肇責類型 --%></label>
				</c:when>
				<c:otherwise>
					<label style="vertical-align: middle;"><s:text name="claim.propAccidentType" />：<%-- 責任險肇責類型 --%></label>
				</c:otherwise>
			</c:choose>
			<s:select name="prpLclaimPropAccidentType" id="prpLclaimPropAccidentType" list="#attr.accidentTypeList" listKey="key" listValue="value" value="#request.prpLclaim.propAccidentType" style="width:140px;"></s:select>
		</div>
	</c:if>
</div>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLclaimLossKindCode']").bind("propertychange",function(event){
			if(event.originalEvent && event.originalEvent.propertyName == "value"){
				checkAccidentType();
			}
		});
		// CLM0042 ，處理人員：BK007 蘇哲，需求單編號：CLM0042「任意車險查詢平台」調整理賠資料傳輸-肇責未釐清，不計次
		checkAccidentType();
	})
	var CarKindCode = "${requestScope.CarKindCode}";
	var CarKindCodeArray = CarKindCode.split(",");
	function checkAccidentType(){
		var carKindCodes = new Array();
		var propKindCodes = new Array();
		$("#spanClaimLoss").find(":input[name='prpLclaimLossKindCode']").each(function(){
			var kindCode = $.trim(this.value);
			if(kindCode.length != 0){
				if($.inArray(kindCode,CarKindCodeArray) > -1){
					carKindCodes.push(kindCode);
				}else{
					propKindCodes.push(kindCode);
				}
			}
		});
		if(carKindCodes.length == 0){
			$("#divCarAccidentType").hide();
			$("#prpLclaimCarAccidentType").prop("disabled",true);
		}else{
			$("#divCarAccidentType").show();
			$("#prpLclaimCarAccidentType").prop("disabled",false);
		}
		if(propKindCodes.length == 0){
			$("#divPropAccidentType").hide();
			$("#prpLclaimPropAccidentType").prop("disabled",true);
		}else{
			$("#divPropAccidentType").show();
			$("#prpLclaimPropAccidentType").prop("disabled",false);
		}
	}
</script>
<script type="text/javascript">
	//jquery ajax 请求
	$(document).ready(function() {
		$(":button,:submit,:reset").filter(":enabled").ajaxStart(function() {
			$(this).attr("disabled", true);//请求开始禁用按钮
		}).ajaxComplete(function() {
			$(this).attr("disabled", false);//请求完成恢复按钮
		});
	});
</script>
