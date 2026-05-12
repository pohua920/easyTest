<%--
****************************************************************************
* DESC	   ：添加人员赔款费用信息页面
* AUTHOR	 ：理赔组 陈杰
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name	   Date			Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@page import="com.sinosoft.claim.schema.model.PrpCitemKind"%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	function viewDangerUnitPersonLoss(field){
	  for (var i=1;i<fm.prpLpersonCommerceSerialNo.length;i++){
		 if(fm.prpLpersonLossDangerNo[i]==field){
			 var count	  = i;
			 var policyNo   = fm.policyno.value;
			 var damageDate = fm.damageStartDate.value;
			 var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&PageType=PersonLoss&openerIndex=" + count;  
			 window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		 }
	  }
	}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLpersonMedicalDetailName'],:input[name='prpLdisabilityLimitRatingCode'],:input[name='prpLpersonLossPayObjectSerialNo']").live("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
		$(":input[name='prpLpersonCommerceProsecutorsOffice'],:input[name='prpLpersonCommerceMedicalCode'],:input[name='prpLpersonCommerceIdentityOfInjuredPerson']").live("mouseover",function(){
			$(this).prop("title",$(this).children(":selected").text());
		});
		$(":input[name='prpLpersonCommerceMedicalCode']").bind("change",function(){
			var $prpLpersonLossObject = $(this).closest("tr[name='prpLpersonLossObject']");
			//個別受害人醫療給付是否結案且待健保追償（返還）
			var $endCaseAndRecoverFlag = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceEndCaseAndRecoverFlag']");
			//健保局追償狀況
			var $chasingLossesStatus = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceChasingLossesStatus']");
			var $healthPoints = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthPoints']");
			var $healthAmount = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthAmount']");
			var A00 = $prpLpersonLossObject.find(":input[name='prpLPersonLossA00']").val();
			if(this.value=='N'){//未以健保就醫身份就醫
				//“個別受害人醫療給付是否結案且待健保追償（返還）”預設為“不需待健保追償”，readOnly不可改
				//“健保局追償狀況”預設為“無健保追償” ，readOnly不可改
				$endCaseAndRecoverFlag.val("0");
				$chasingLossesStatus.val("1");
				$healthPoints.val("0");
				$healthAmount.val("0");
				//readOnly
				$endCaseAndRecoverFlag.add($chasingLossesStatus).prop("disabled",true);
				$healthPoints.add($healthAmount).prop("readonly",true).removeClass("common").addClass("readonly");
			} else {
				$endCaseAndRecoverFlag.add($chasingLossesStatus).prop("disabled",false);
				if(parseFloat(A00 , 10) > 0 ){
					$healthPoints.add($healthAmount).prop("readonly",false).removeClass("readonly").addClass("common");
				} else {
					$healthPoints.add($healthAmount).prop("readonly",true).removeClass("common").addClass("readonly");
				}
			}
		});
		$(":input[name='prpLPersonLossA00']").bind("input propertychange",function(event){
			var e = event.originalEvent || event;
			if(e.propertyName == 'value'){
				var $prpLpersonLossObject = $(this).closest("tr[name='prpLpersonLossObject']");
				if( this.value == "0" ){
					$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthPoints']").val("0");
					$prpLpersonLossObject.find(":input[name='prpLpersonCommerceHealthAmount']").val("0");
				}
				var $medicalCode = $prpLpersonLossObject.find(":input[name='prpLpersonCommerceMedicalCode']");
				$medicalCode.triggerHandler("change");
			}
		});
		$(":input[name='prpLpersonCommerceMedicalCode']").triggerHandler("change");
	})
</script>
<div id="limitMap" style="display: none">
	<c:forEach items="${requestScope.limitInfoMap}" var="map">
		<input name="${map.key}" value="${map.value}" />
	</c:forEach>
</div>
<!--建立显示的录入条，可以收缩显示的-->
<span style="display: none">
	<table class="common" style="display: none" id="PersonFeeMedical_Data" cellspacing="1" cellpadding="0">
		<tbody>
			<tr name="prpLpersonFeeLossObject">
				<select name="claimfeeType" style="display: none">
					<option value="2">醫療費用</option>
					<option value="1">死亡傷殘</option>
					<option value="4">其它</option>
					<option value="5">無責死亡傷殘</option>
					<option value="6">無責醫療費用</option>
					<option value="8">無責其它</option>
				</select>
				<td class="inputsubsub">
				
					<input type="hidden" name="personMedicalSerialNo" style="width: 20px">
					<input name="prpLpersonMedicalDetailCode" class="codecode" style="width: 40px" title="費用代碼" ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
						onchange="code_CodeChange(this, 'PersonFeeTypeFlag', '0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" onblur="clearPrpLpersonFeeLoss(this);">
					<input name="prpLpersonMedicalDetailName" class="codename" title="費用名稱" style="width: 200px" ondblclick="code_CodeSelect(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
						onchange="code_CodeChange(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" onkeyup="code_CodeSelect(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
						onblur="clearPrpLpersonFeeLoss(this);">
					<input name="medicDeathFlag" type="hidden" title="人傷費用類型">
				</td>
				<td class="inputsubsub">
					<s:select name="prpLdisabilityLimitRatingCode" disabled="true" listKey="key" listValue="value" list="#request.injuryGradeList" onchange="getCrippledPay(this);" />
					<input name="prpLpersonMedicalInjuryGrade" type="hidden" value="">
				</td>
				<td class="inputsubsub">
					<input name="prpLpersonMedicalSumLoss" class="common" onfocus="cacheData(this);" value="0" onchange="validateMoney(this);calCompelSumLoss(this);" title="核定损失">
				</td>
				<td class="inputsubsub">
					<input type="hidden" class="flag" style="width: 20px" value="0">
					<input type="hidden" name="prpLpersonMedicalRejectSum" value="0">
					<input name="prpLpersonMedicalSumDefPay" class="common" onfocus="cacheData(this);" value="0" onchange="validateMoney(this);calCompelSumDefPay(this);" title="核定賠償">
					<input type="hidden" name="prpLpersonLossCurrency" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossCurrency1" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossCurrency2" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossDeductible" value="0">
					<input type="hidden" name="prpLpersonLossCurrency3" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossCurrency4" value="${LOCAL_CURRENCY}">
					<s:select name="prpLpersonLossReservedEstimate" cssStyle="display:none" list="#attr.reservedEstimateList" value="N"></s:select>
				</td>
				<!-- delete by sinosoft 20150617 需求變更095
				<td class='inputsubsub'>
				</td>
				 -->
				<td class="inputsubsub">
					<div align="center">
						<input type=button name="buttonPersonFeeMedicalDelete" class="smallbutton" onclick="deletePrpLpersonFeeLossObject(this);" value="-" readonly style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="PersonImg" onclick="showPage(this,spanPersonCommerce);"> <b>強制險受害人訊息</b><br>
			<span style="display: none">
				<table class="common" style="display: none" id="PersonCommerce_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr name="prpLpersonLossObject">
							<td class="input" style="width: 5%">
								<div align="center">
									<input type="text" class="readonly" readonly="readonly" name="personNum">
									<input type="hidden" name="prpLpersonLossItemKindNo" value="1">
									<input type="hidden" name="prpLpersonCommerceSerialNo">
								</div>
							</td>
							<td class="subformtitle" style="width: 91%">
								<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
									<tbody>
										<tr>
											<input type=hidden name="prpLpersonLossDangerNo" class="codecode" value="1" style="width: 45%" onClick="viewDangerUnitPersonLoss(this);">
											<input type="hidden" name="prpLpersonCommercePersonNo">
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.personName" />
												:
											</td>
											<%-- 人员姓名 --%>
											<td class="input" style="width: 18%">
												<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
												<input class='common' style="width: 110" name="prpLpersonCommercePersonName" maxlength="100" title="人員姓名">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 10%">
												<s:text name="db.prpLperson.personSex" />
												：
											</td>
											<%-- 性别 --%>
											<td class="input" style="width: 18%">
												<select name="prpLpersonCommerceSex" class='common' style="width: 110">
													<option value="1"><s:text name="certainLoss.male" /></option>
													<%-- 男 --%>
													<option value="2"><s:text name="certainLoss.female" /></option>
													<%-- 女 --%>
												</select>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class='title' style="width: 18%">
												<s:text name="db.prpLlawsuit.licenseNo" />
												：
											</td>
											<%-- 号牌号码 --%>
											<td class="input" style="width: 20%">
												<s:select name="prpLpersonCommerceFamilyName" listKey="licenseNo" listValue="licenseNo" list="#request.licenseNoList" headerKey="" headerValue="" style="width: 110" />
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">出生年份：</td>
											<%-- 出生年份 --%>
											<td class="input" style="width: 18%">
												<rc:rcDate class='common' style="width: 110" name="prpLpersonCommerceBirthday" onchange="updatePersonCommerceAge(this);" title="出生年份" wdatePicker="true" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 10%">
												<s:text name="db.prpLpersonloss.age" />
												：
											</td>
											<%-- 年龄 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonCommerceAge" style="width: 110" maxlength="3" title="年齡" onchange="checkInteger(this,1,120)">
											</td>
											<td class="title" style="width: 18%">受害人身份：</td>
											<%-- 受害人身份 --%>
											<td class="input" style="width: 20%">
												<s:select name="prpLpersonCommerceIdentityOfInjuredPerson" listKey="key" listValue="value" list="#request.identityOfInjuredPersonList" style="width: 120px" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">出事當時乘坐狀況：</td>
											<%-- 出事當時乘坐狀況 --%>
											<td class="input" style="width: 18%">
												<s:select name="prpLpersonCommerceRideSituation" listKey="key" listValue="value" list="#request.rideSituationList" onchange="countPersonLoss();" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 10%">身份證號：</td>
											<%-- 身份證號 --%>
											<td class="input" style="width: 18%">
												<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 -->
												<input class='common' name="prpLpersonCommerceIdentifyNumber" style="width: 110" title="身份證號" onchange="checkPerson(this);changeDef();">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 18%">受害人健保就醫代號：</td>
											<%-- 受害人健保就醫代號 --%>
											<td class="input" style="width: 20%">
												<s:select name="prpLpersonCommerceMedicalCode" listKey="key" listValue="value" list="#request.medicalCodeList" style="width: 120px" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 -->
											<td class="title" colspan="2">
												個別受害人醫療給付是否結案且待健保追償（返還）：
												<%-- 個別受害人醫療給付是否結案且待健保追償（返還） --%>
												<s:set name="flagYes" value="%{getText('regist.prpLregist.yes')}"></s:set>
												<s:set name="flagNo" value="%{getText('regist.prpLregist.no')}"></s:set>
												<s:select name="prpLpersonCommerceEndCaseAndRecoverFlag" list="#{'0':#flagNo,'1':#flagYes}" listKey="key" listValue="value" value="1" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START -->
											<td class="title" style="width: 15%">
												受害人身分證號類別：
											</td>
											<%-- 受害人身分證號類別 --%>
											<td class="input" style="width: 20%">
												<select name="prpLpersonLossIdNumberType" Style="width: 40%">
													<option value="ID_NUMBER">身分證字號</option>
													<option value="ARC_NUMBER">居留證號</option>
													<option value="PASSPORT_NUM">護照號碼</option>
												</select>
											</td>
											<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END -->
											<td class="title" style="width: 18%">婚姻别：</td>
											<%-- 婚姻别 --%>
											<td class="input" style="width: 20%">
												<select name="prpLpersonCommerceIsMarried" style="width: 120px">
													<option value="1" selected="selected"><s:text name="db.prpLdriver.Married" /></option>
													<%-- 已婚 --%>
													<option value="2"><s:text name="db.prpLdriver.Unmarried" /></option>
													<%-- 未婚 --%>
												</select>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">受害人電話：</td>
											<%-- 受害人市話 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonCommerceTelephoneNo" style="width: 110" title="受害人市話">
											</td>
											<td class="title" style="width: 10%">地檢署：</td>
											<%-- 地檢署 --%>
											<td class="input" style="width: 18%">
												<s:select name="prpLpersonCommerceProsecutorsOffice" listKey="key" listValue="value" list="#request.prosecutorsOfficeList" style="width: 100%" />
											</td>
											<td class="title" style="width: 18%">法醫師/檢驗員姓名：</td>
											<%-- 法醫師/檢驗員姓名 --%>
											<td class="input" style="width: 20%">
												<input class='common' name="prpLpersonCommerceCourtDoctor" style="width: 110" title="法醫師/檢驗員姓名">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">受害人手機：</td>
											<%-- 受害人手機 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonCommerceMobilePhone" style="width: 110" title="受害人手機">
											</td>
											<td class="title" style="width: 10%">檢察官姓名：</td>
											<%-- 檢察官姓名 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonCommerceProsecutor" style="width: 110" title="檢察官姓名">
											</td>
											<td class="title" style="width: 18%">修車廠負責人姓名：</td>
											<%-- 修車廠負責人姓名 --%>
											<td class="input" style="width: 20%">
												<input class='common' name="prpLpersonCommerceGarageHeadName" style="width: 110" title="修車廠負責人姓名">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">醫院名稱：</td>
											<%-- 醫院名稱 --%>
											<td class="input" colspan="3">
												<input name="prpLpersonCommerceHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" class='common' style="width: 110" title="醫院代碼">
												<input name="prpLpersonCommerceHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" class='common' style="width: 280" title="醫院名稱">
											</td>
											<td class="title" style="width: 18%">醫師姓名：</td>
											<%-- 醫師姓名 --%>
											<td class="input" style="width: 20%">
												<input class='common' name="prpLpersonCommerceDoctor" style="width: 110" title="醫師姓名">
											</td>
										</tr>
										<tr>
											<td class='title' style="width: 15%">賠付對象讯息：</td>
											<%-- 賠付對象序号  onblur="checkPayObjectSerialNo(this);"--%>
											<td class='input' style="width: 18%" title="请单击选择賠付對象讯息">
												<input class='common' type="text" readonly="readonly" name="prpLpersonLossPayObjectSerialNo" onclick="setPrpObjectinfoSerialNo(this);" style="width: 110" value="" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 15%">健保局追償狀況：</td>
											<%-- 醫院名稱 --%>
											<td class="input" colspan="3">
												<s:select name="prpLpersonCommerceChasingLossesStatus" value="#request.prpLcompensate.chasingLossesStatus" listKey="key" listValue="value" list="#request.chasingLossesStatusList" />
												<img src="/claim/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<input type="hidden" class='common' name="prpLpersonCommerceIndemnityDutyRate" class='common' style="width: 110px" onChange="calRealpay2ForSunny(this);clearPrpLctext();">
											<input type="hidden" class='common' name="prpLpersonCommerceArrangeRate" style="width: 110px" onChange="calRealpay2ForSunny(this);clearPrpLctext();" value="100">
											<td class="title" style="width: 15%">傷亡情形：</td>
											<%-- 傷亡情形 --%>
											<td class="input" style="width: 18%" colspan="3">
												<span name="CommerceCasualtiesSpan">
													<input type="checkbox" name="CommerceCasualties" value="1" onclick="setPersonLossCommerce(this);">
													1.醫療
													<input type="checkbox" name="CommerceCasualties" value="2" onclick="setPersonLossCommerce(this);">
													2.失能
													<input type="checkbox" name="CommerceCasualties" value="3" onclick="setPersonLossCommerce(this);">
													3.死亡 <img src="${ctx}/images/bgMarkMustInput.jpg">
													<%--/** 隐藏需要传入后台的内容，具体值由span下3个checkbox决定 */--%>
													<input type="hidden" name="prpLpersonCommerceCasualties" value="">
												</span>
											</td>
											<td class='title' style="width: 18%">
												<s:text name="compensate.payTotal" />
												：
												<%-- 赔付合计 --%>
											</td>
											<td class='input' style="width: 20%">
												<input class='readonly' style='width: 110px' readonly name="prpLpersonCommerceSumRealPay1" value="0">
												<input type='hidden' name="prpLpersonCommerceSumDefPay1" value="0">
											</td>
										</tr>
										<tr>
											<td colspan="6">
												<table name="PersonFeeMedical" class="common" align="center" cellspacing="1" cellpadding="0">
													<thead>
														<tr>
															<td class="subformtitle" colspan="5">費用訊息</td>
														</tr>
														<tr>
															<td class="centertitle" style="width: 35%">費用類別</td>
															<td class="centertitle" style="width: 20%">失能等級</td>
															<td class="centertitle" style="width: 20%">核定損失</td>
															<td class="centertitle" style="width: 20%">核定賠償</td>
															<!-- <td class="centertitle" style="width: 15%">保留預估</td> -->
															<!-- delete by sinosoft 20150617 需求變更095
															<td class="centertitle" style="width: 15%">肇責類型</td>
															 -->
															<td class="centertitle" style="width: 5%">操作</td>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<td class="titlesubsub" colspan="4" style="width: 95%">
																<div align="left">
																	<input type="button" value="醫療給付費用明細" class="bigbutton" onclick="insertMedicalDetail(this);" name="buttonInsertMedicalDetail" readonly style="cursor: hand">
																</div>
															</td>
															<td class="title" align="right" style="width: 5%">
																<div align="center">
																	<input type="button" value="+" class="smallbutton" onclick="insertPrpLpersonFeeLossObject(this);" name="buttonPersonFeeMedicalInsert" readonly style="cursor: hand">
																</div>
															</td>
														</tr>
													</tfoot>
													<tbody>
													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan="6">
												<table name="PersonFeeMedicalCount" class="common" align="center" cellspacing="1" cellpadding="0">
													<tbody>
														<tr>
															<td class='centertitle' style="width: 20%">
																<span style="float: left">A00 醫療費用給付加總:</span>
															</td>
															<td class='centertitle' style="width: 15%">
																<input class="readonly" readonly name="prpLPersonLossA00" value="0" />
															</td>
															<td class='centertitle' style="width: 35%">
																<span style="float: left">（為A01,A021~A029,A03各項之和)存於資料庫：報送用</span>
															</td>
															<td class='centertitle' style="width: 15%">
																<span style="float: center">健保點數</span>
															</td>
															<td class='centertitle' style="width: 15%">
																<span style="float: center">健保金額</span>
															</td>
														</tr>
														<tr>
															<td class='centertitle' style="width: 20%">
																<span style="float: left">C00 失能給付加總</span>
															</td>
															<td class='centertitle' style="width: 15%">
																<input class="readonly" readonly name="prpLPersonLossC00" value="0" />
															</td>
															<td class='centertitle' style="width: 35%">
																<span style="float: left"></span>
															</td>
															<td class='centertitle' style="width: 15%" rowspan="2">
																<input class="common" type="input" name="prpLpersonCommerceHealthPoints" value="0" />
															</td>
															<td class='centertitle' style="width: 15%" rowspan="2">
																<input class="common" type="input" name="prpLpersonCommerceHealthAmount" value="0" />
															</td>
														</tr>
														<tr>
															<td class='centertitle' style="width: 20%">
																<span style="float: left">B00 死亡給付</span>
															</td>
															<td class='centertitle' style="width: 15%">
																<input class="readonly" readonly name="prpLPersonLossB00" value="0" />
															</td>
															<td class='centertitle' style="width: 35%">
																<span style="float: left"></span>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input type=button name="buttonPersonCommerceDelete" class="smallbutton" onclick="deletePrpLpersonLossObject(this);" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<span id="spanPersonCommerce" style="display: none">
				<%-- 多行输入展现域 --%>
				<table id="PersonCommerce" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="centertitle" style="width: 4%">名称</td>
							<td class="centertitle" style="width: 96%" colspan=2>内容</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%" align="left">
								(按"+"號鍵增加強制保險人傷信息，按"-"號鍵刪除強制保險人傷信息)
								<input type="button" class="bigbutton" value="歷史賠付受害人訊息" name="buttonPersonHistory" style="cursor: hand; display:;" onclick="showPersonHistory();" />
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class="smallbutton" onclick="insertPrpLpersonLossObject();" name="buttonPersonInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="PrpLpersonLoss">
						<script language="javascript">
		var damageKind = new Array();
		<c:forEach var="itemKindTemp" items="${damageKindList }" varStatus="itemKindStatus">
		  	damageKind[${itemKindStatus.index }]="${itemKindTemp.kindCode }";
		 </c:forEach>
		</script>
						<c:set var="personMedicalDeformityNo" value="0" />
						<c:set var="personMedicalDeformitySerialNo" value="1" />
						<c:set var="personDeformityNo" value="0" />
						<c:set var="personDeformitySerialNo" value="1" />
						<c:set var="kindCode" value="" />
						<c:set var="jindex" value="0" />
						<c:forEach items="${requestScope.prpLpersonLoss.prpLpersonLossList}" var="prpLpersonLoss1" varStatus="personLoss_status">
							<c:if test="${prpLpersonLoss1.personNo!=personMedicalDeformityNo && prpLpersonLoss1.personNo >jindex}">
								<c:if test="${prpLpersonLoss1.kindCode==KINDCODE_D_BZ}">
									<c:set var="jindex" value="${jindex+1}" />
									<tr name="prpLpersonLossObject">
										<td class="input" style="width: 5%">
											<div align="center">
												<%-- 人伤损失--%>
												<input type="text" class="readonly" readonly="readonly" name="personNum" value="${personMedicalDeformitySerialNo}">
												<input type="hidden" name="prpLpersonCommerceSerialNo" value="<c:out value='${personMedicalDeformitySerialNo}'/>">
												<input type="hidden" name="prpLpersonLossItemKindNo" value="${prpLpersonLoss1.itemKindNo }">
											</div>
										</td>
										<td class="subformtitle" style="width: 91%">
											<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
												<tbody>
													<input type=hidden name="prpLpersonLossDangerNo" class="codecode" style="width: 45%" onClick="viewDangerUnitPersonLoss(this);" value="<c:out value='${prpLpersonLoss1.dangerNo}'/>">
													<tr>
														<input type="hidden" name="prpLpersonCommercePersonNo" value="<c:out value='${prpLpersonLoss1.personNo}'/>">
														<td class="title" style="width: 15%">
															<s:text name="db.prpLperson.personName" />
															:
														</td>
														<%-- 人员姓名 --%>
														<td class="input" style="width: 18%">
															<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
															<input class='common' style="width: 110" name="prpLpersonCommercePersonName" maxlength="100" title="人員姓名" value="<c:out value='${prpLpersonLoss1.personName}'/>">
															<img src="${ctx}/images/bgMarkMustInput.jpg">
														</td>
														<td class="title" style="width: 10%">
															<s:text name="db.prpLperson.personSex" />
															：
														</td>
														<%-- 性别 --%>
														<td class="input" style="width: 18%">
															<select name="prpLpersonCommerceSex" class='common' style="width: 110">
																<option value="1" <c:if test="${fn:trim(prpLpersonLoss1.sex)=='1'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.male" /></option>
																<%-- 男 --%>
																<option value="2" <c:if test="${fn:trim(prpLpersonLoss1.sex)=='2'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.female" /></option>
																<%-- 女 --%>
															</select>
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
														<td class='title' style="width: 18%">
															<s:text name="db.prpLlawsuit.licenseNo" />
															：
														</td>
														<%-- 号牌号码 --%>
														<td class="input" style="width: 20%">
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.familyName}" />
															<s:select name="prpLpersonCommerceFamilyName" value="#attr.tempSelectedValue" listKey="licenseNo" listValue="licenseNo" list="#request.licenseNoList" headerKey="" headerValue="" style="width: 110" />
														</td>
													</tr>
													<tr>
														<td class="title" style="width: 15%">出生年份：</td>
														<%-- 出生年份 --%>
														<td class="input" style="width: 18%">
															<rc:rcDate class='common' style="width: 110" name="prpLpersonCommerceBirthday" title="出生年份" onchange="updatePersonCommerceAge(this);" wdatePicker="true" value='${prpLpersonLoss1.birthday}' />
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
														<td class="title" style="width: 10%">
															<s:text name="db.prpLpersonloss.age" />
															：
														</td>
														<%-- 年龄 --%>
														<td class="input" style="width: 18%">
															<input class='common' name="prpLpersonCommerceAge" style="width: 110" maxlength="3" value="<c:out value='${prpLpersonLoss1.age}'/>" title="年齡" onchange="checkInteger(this,1,120)">
														</td>
														<td class="title" style="width: 18%">受害人身份：</td>
														<%-- 受害人身份 --%>
														<td class="input" style="width: 20%">
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.identityOfInjuredPerson}" />
															<s:select name="prpLpersonCommerceIdentityOfInjuredPerson" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.identityOfInjuredPersonList" style="width: 120px" />
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
													</tr>
													<tr>
														<td class="title" style="width: 15%">出事當時乘坐狀況：</td>
														<%-- 出事當時乘坐狀況 --%>
														<td class="input" style="width: 18%">
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.rideSituation}" />
															<s:select name="prpLpersonCommerceRideSituation" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.rideSituationList" onchange="countPersonLoss();" />
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
														<td class="title" style="width: 10%">身份證號：</td>
														<%-- 身份證號 --%>
														<td class="input" style="width: 18%">
															<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 -->
															<input class='common' name="prpLpersonCommerceIdentifyNumber" style="width: 110" title="身份證號" value="${prpLpersonLoss1.identifyNumber}" onchange="checkPerson(this);changeDef();">
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
														<td class="title" style="width: 18%">受害人健保就醫代號：</td>
														<%-- 受害人健保就醫代號 --%>
														<td class="input" style="width: 20%">
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.medicalCode}" />
															<s:select name="prpLpersonCommerceMedicalCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.medicalCodeList" style="width: 120px" />
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
													</tr>
													<tr><!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 -->
														<td class="title" colspan="2">
															個別受害人醫療給付是否結案且待健保追償（返還）：
															<%-- 個別受害人醫療給付是否結案且待健保追償（返還） --%>
															<s:set name="flagYes" value="%{getText('regist.prpLregist.yes')}"></s:set>
															<s:set name="flagNo" value="%{getText('regist.prpLregist.no')}"></s:set>
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.endCaseAndRecoverFlag}" />
															<s:select name="prpLpersonCommerceEndCaseAndRecoverFlag" value="#attr.tempSelectedValue" list="#{'0':#flagNo,'1':#flagYes}" listKey="key" listValue="value" />
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
														<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START -->
														<td class="title" style="width: 15%">
															受害人身分證號類別：
														</td>
														<%-- 受害人身分證號類別 --%>
														<td class="input" style="width: 20%">
															<select name="prpLpersonLossIdNumberType" Style="width: 40%" value="${personTrace.idNumberType}">
																<option value="ID_NUMBER" <c:if test="${personTrace.idNumberType=='ID_NUMBER'}"> selected="selected"</c:if>>身分證字號</option>
																<option value="ARC_NUMBER" <c:if test="${personTrace.idNumberType=='ARC_NUMBER'}"> selected="selected"</c:if>>居留證號</option>
																<option value="PASSPORT_NUM" <c:if test="${personTrace.idNumberType=='PASSPORT_NUM'}"> selected="selected"</c:if>>護照號碼</option>
															</select>
														</td>
														<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END -->
														<td class="title" style="width: 18%">婚姻别：</td>
														<%-- 婚姻别 --%>
														<td class="input" style="width: 20%">
															<select name="prpLpersonCommerceIsMarried" style="width: 120px">
																<option value="1" <c:if test="${prpLpersonLoss1.isMarried=='1' }">selected</c:if>><s:text name="db.prpLdriver.Married" /></option>
																<%--已婚--%>
																<option value="2" <c:if test="${prpLpersonLoss1.isMarried=='2' }">selected</c:if>><s:text name="db.prpLdriver.Unmarried" /></option>
																<%--未婚--%>
															</select>
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
													</tr>
													<tr>
														<td class="title" style="width: 15%">受害人電話：</td>
														<%-- 受害人市話 --%>
														<td class="input" style="width: 18%">
															<input class='common' name="prpLpersonCommerceTelephoneNo" value="${prpLpersonLoss1.telephoneNo}" style="width: 110" title="受害人市話">
														</td>
														<td class="title" style="width: 10%">地檢署：</td>
														<%-- 地檢署 --%>
														<td class="input" style="width: 18%">
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.prosecutorsOffice}" />
															<s:select name="prpLpersonCommerceProsecutorsOffice" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.prosecutorsOfficeList" style="width: 100%" />
														</td>
														<td class="title" style="width: 18%">法醫師/檢驗員姓名：</td>
														<%-- 法醫師/檢驗員姓名 --%>
														<td class="input" style="width: 20%">
															<input class='common' name="prpLpersonCommerceCourtDoctor" value="${prpLpersonLoss1.courtDoctor}" style="width: 110" title="法醫師/檢驗員姓名">
														</td>
													</tr>
													<tr>
														<td class="title" style="width: 15%">受害人手機：</td>
														<%-- 受害人手機 --%>
														<td class="input" style="width: 18%">
															<input class='common' name="prpLpersonCommerceMobilePhone" value="${prpLpersonLoss1.mobilePhone}" style="width: 110" title="受害人手機">
														</td>
														<td class="title" style="width: 10%">檢察官姓名：</td>
														<%-- 檢察官姓名 --%>
														<td class="input" style="width: 18%">
															<input class='common' name="prpLpersonCommerceProsecutor" value="${prpLpersonLoss1.prosecutor}" style="width: 110" title="檢察官姓名">
														</td>
														<td class="title" style="width: 18%">修車廠負責人姓名：</td>
														<%-- 修車廠負責人姓名 --%>
														<td class="input" style="width: 20%">
															<input class='common' name="prpLpersonCommerceGarageHeadName" value="${prpLpersonLoss1.garageHeadName}" style="width: 110" title="修車廠負責人姓名">
														</td>
													</tr>
													<tr>
														<td class="title" style="width: 15%">醫院名稱：</td>
														<%-- 醫院名稱 --%>
														<td class="input" colspan="3">
															<input name="prpLpersonCommerceHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" value="${prpLpersonLoss1.hospitalCode}" class='common' style="width: 110" title="醫院代碼">
															<input name="prpLpersonCommerceHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" value="${prpLpersonLoss1.hospitalName}" class='common' style="width: 280" title="醫院名稱">
														</td>
														<td class="title" style="width: 18%">醫師姓名：</td>
														<%-- 醫師姓名 --%>
														<td class="input" style="width: 20%">
															<input class='common' name="prpLpersonCommerceDoctor" value="${prpLpersonLoss1.doctor}" style="width: 110" title="醫師姓名">
														</td>
													</tr>
													<tr>
														<td class='title' style="width: 15%">賠付對象讯息：</td>
														<%-- 賠付對象序号 --%>
														<td class='input' style="width: 18%" title="请单击选择賠付對象讯息">
															<input class='common' type="text" name="prpLpersonLossPayObjectSerialNo" onclick="setPrpObjectinfoSerialNo(this);" style="width: 110" value="${prpLpersonLoss1.payObjectSerialNo}">
															<img src="${ctx}/images/bgMarkMustInput.jpg">
														</td>
														<td class="title" style="width: 15%">健保局追償狀況：</td>
														<%-- 醫院名稱 --%>
														<td class="input" colspan="3">
															<c:set var="tempSelectedValue" value="${prpLpersonLoss1.chasingLossesStatus}" />
															<s:select name="prpLpersonCommerceChasingLossesStatus" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.chasingLossesStatusList" />
															<img src="/claim/images/bgMarkMustInput.jpg">
														</td>
													</tr>
													<tr>
														<input type="hidden" class='common' name="prpLpersonCommerceIndemnityDutyRate" class='common' style="width: 110px" onChange="calRealpay2ForSunny(this);clearPrpLctext();">
														<input type="hidden" class='common' name="prpLpersonCommerceArrangeRate" style="width: 110px" onChange="calRealpay2ForSunny(this);clearPrpLctext();" value="100">
														<td class="title" style="width: 15%">
															傷亡情形：<img src="${ctx}/images/bgMarkMustInput.jpg">
															<%-- 傷亡情形 --%>
														<td class='input' style="width: 18%" colspan="3">
															<span name="CommerceCasualtiesSpan">
																<input type="checkbox" name="CommerceCasualties" value="1" onclick="setPersonLossCommerce(this);">
																1.醫療
																<input type="checkbox" name="CommerceCasualties" value="2" onclick="setPersonLossCommerce(this);">
																2.失能
																<input type="checkbox" name="CommerceCasualties" value="3" onclick="setPersonLossCommerce(this);">
																3.死亡
																<%--/** 隐藏需要传入后台的内容，具体值由span下3个checkbox决定 */--%>
																<input type="hidden" name="prpLpersonCommerceCasualties" value="<c:out value='${prpLpersonLoss1.casualties}' />">
															</span>
															<img src="${ctx }/images/bgMarkMustInput.jpg">
														</td>
														<td class='title' style="width: 18%">
															<s:text name="compensate.payTotal" />
															：
														</td>
														<%-- 赔付合计 --%>
														<td class='input' style="width: 20%">
															<input class='readonly' style='width: 110px' readonly name="prpLpersonCommerceSumRealPay1" value="0">
															<input type='hidden' name="prpLpersonCommerceSumDefPay1" value="0">
														</td>
													</tr>
													<tr>
														<td colspan="6">
															<table name="PersonFeeMedical" class="common" align="center" cellspacing="1" cellpadding="0">
																<thead>
																	<tr>
																		<td class="subformtitle" colspan="5">費用訊息</td>
																	</tr>
																	<tr>
																		<td class="centertitle" style="width: 35%">費用類別</td>
																		<td class="centertitle" style="width: 20%">失能等級</td>
																		<td class="centertitle" style="width: 20%">核定损失</td>
																		<td class="centertitle" style="width: 20%">核定賠償</td>
																		<!-- <td class="centertitle" style="width:15%">保留預估</td> -->
																		<!--  delete by sinosoft 20150617 需求變更095 
																		<td class="centertitle" style="width: 15%">肇責類型</td> -->
																		<td class="centertitle" style="width: 5%">操作</td>
																	</tr>
																</thead>
																<tfoot>
																	<tr>
																		<td class="titlesubsub" colspan="4" style="width: 95%">
																			<div align="left">
																				<input type="button" value="醫療給付費用明細" class="bigbutton" onclick="insertMedicalDetail(this);" name="buttonInsertMedicalDetail" readonly style="cursor: hand">
																			</div>
																		</td>
																		<td class="title" align="right" style="width: 5%">
																			<div align="center">
																				<input type="button" value="+" class="smallbutton" onclick="insertPrpLpersonFeeLossObject(this);" name="buttonPersonFeeMedicalInsert" readonly style="cursor: hand">
																			</div>
																		</td>
																	</tr>
																</tfoot>
																<tbody>
																	<c:forEach items="${requestScope.prpLpersonLoss.prpLpersonLossList}" var="prpLpersonLoss2">
																		<c:if test="${prpLpersonLoss2.personNo==prpLpersonLoss1.personNo && prpLpersonLoss2.kindCode==prpLpersonLoss1.kindCode}">
																			<tr name="prpLpersonFeeLossObject">
																				<select name="claimfeeType" style="display: none">
																					<option value="2" <c:if test="${prpLpersonLoss2.claimfeeType=='2'}"> selected="selected" </c:if>>醫療費用</option>
																					<option value="1" <c:if test="${prpLpersonLoss2.claimfeeType=='1'}"> selected="selected" </c:if>>死亡傷殘</option>
																					<option value="4" <c:if test="${prpLpersonLoss2.claimfeeType=='4'}"> selected="selected" </c:if>>其它</option>
																					<option value="5" <c:if test="${prpLpersonLoss2.claimfeeType=='5'}"> selected="selected" </c:if>>無責死亡傷殘</option>
																					<option value="6" <c:if test="${prpLpersonLoss2.claimfeeType=='6'}"> selected="selected" </c:if>>無責醫療費用</option>
																					<option value="8" <c:if test="${prpLpersonLoss2.claimfeeType=='8'}"> selected="selected" </c:if>>無責其它</option>
																				</select>
																				<td class="inputsubsub">
							
																					<input type="hidden" name="personMedicalSerialNo" style="width: 20px" value="<c:out value='${personMedicalDeformitySerialNo}'/>">
																					<input name="prpLpersonMedicalDetailCode" class="codecode" style="width: 40px" title="費用代碼" value="<c:out value='${prpLpersonLoss2.liabDetailCode}'/>"
																						ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" onchange="code_CodeChange(this, 'PersonFeeTypeFlag', '0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
																						onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" onblur="clearPrpLpersonFeeLoss(this);">
																					<input name="prpLpersonMedicalDetailName" class="codename" style="width: 200px" title="費用名稱" value="<c:out value='${prpLpersonLoss2.liabDetailName}'/>"
																						ondblclick="code_CodeSelect(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" onchange="code_CodeChange(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
																						onkeyup="code_CodeSelect(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" onblur="clearPrpLpersonFeeLoss(this);">
																					<input name="medicDeathFlag" type="hidden" title="人傷費用類型" value="<c:out value='${prpLpersonLoss2.feeCategory}'/>">
																				</td>
																				<td class="inputsubsub">
																					<c:set var="tempSelectedValue" value="${prpLpersonLoss2.injuryGrade}" />
																					<c:if test="${prpLpersonLoss2.liabDetailCode=='C00'}">
																						<s:select name="prpLdisabilityLimitRatingCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.injuryGradeList" onchange="getCrippledPay(this);" />
																					</c:if>
																					<c:if test="${prpLpersonLoss2.liabDetailCode!='C00'}">
																						<s:select name="prpLdisabilityLimitRatingCode" disabled="true" listKey="key" listValue="value" list="#request.injuryGradeList" onchange="getCrippledPay(this);" />
																					</c:if>
																					<input type="hidden" name="prpLpersonMedicalInjuryGrade" value="${prpLpersonLoss2.injuryGrade}" />
																					<input type="hidden" name="prpLpersonMedicalRejectSum" class="common" value="<c:out value='${prpLpersonLoss2.sumRest}'/>">
																				</td>
																				<td class="inputsubsub">
																					<input name="prpLpersonMedicalSumLoss" class="common" value="<fmt:formatNumber value="${prpLpersonLoss2.sumLoss}" pattern="#"/>" onfocus="cacheData(this);" onchange="validateMoney(this);calCompelSumLoss(this);" title="核定损失">
																				</td>
																				<td class="inputsubsub">
																					<input type="hidden" class="flag" style="width: 20px" value="0">
																					<input name="prpLpersonMedicalSumDefPay" class="common" value="<fmt:formatNumber value="${prpLpersonLoss2.sumDefPay}" pattern="#"/>" onfocus="cacheData(this);" onchange="validateMoney(this);calCompelSumDefPay(this);" title="核定賠償">
																					<input type="hidden" name="prpLpersonLossCurrency" value="${LOCAL_CURRENCY}">
																					<input type="hidden" name="prpLpersonLossAmount" value="<c:out value='${prpLpersonLoss2.amount}' />">
																					<input type="hidden" name="prpLpersonLossCurrency1" value="${LOCAL_CURRENCY}">
																					<input type="hidden" name="prpLpersonLossCurrency2" value="${LOCAL_CURRENCY}">
																					<input type="hidden" name="prpLpersonLossCurrency3" value="${LOCAL_CURRENCY}">
																					<input type="hidden" name="prpLpersonLossCurrency4" value="${LOCAL_CURRENCY}">
																					<s:select name="prpLpersonLossReservedEstimate" cssStyle="display:none" list="#attr.reservedEstimateList" value="#attr.prpLpersonLoss2.reservedEstimate"></s:select>
																				</td>
																				<!-- delete by sinosoft 20150617 需求變更095 
													<td class='inputsubsub'>
													</td>
													 -->
																				<td class="inputsubsub">
																					<div align="center">
																						<input type=button name="buttonPersonFeeMedicalDelete" class="smallbutton" onclick="deletePrpLpersonFeeLossObject(this);" value="-" readonly style="cursor: hand">
																					</div>
																				</td>
																				<td class="inputsubsub"></td>
																			</tr>
																		</c:if>
																	</c:forEach>
																</tbody>
															</table>
														</td>
													</tr>
													<tr>
														<td colspan="6">
															<table name="PersonFeeMedicalCount" class="common" align="center" cellspacing="1" cellpadding="0">
																<tbody>
																	<tr>
																		<td class='centertitle' style="width: 20%">
																			<span style="float: left">A00 醫療費用給付加總:</span>
																		</td>
																		<td class='centertitle' style="width: 15%">
																			<input class="readonly" readonly name="prpLPersonLossA00" value="0" />
																		</td>
																		<td class='centertitle' style="width: 35%">
																			<span style="float: left">（為A01,A021~A029,A03各項之和)存於資料庫：報送用</span>
																		</td>
																		<td class='centertitle' style="width: 15%">
																			<span style="float: center">健保點數</span>
																		</td>
																		<td class='centertitle' style="width: 15%">
																			<span style="float: center">健保金額</span>
																		</td>
																	</tr>
																	<tr>
																		<td class='centertitle' style="width: 20%">
																			<span style="float: left">C00 失能給付加總</span>
																		</td>
																		<td class='centertitle' style="width: 15%">
																			<input class="readonly" readonly name="prpLPersonLossC00" value="0" />
																		</td>
																		<td class='centertitle' style="width: 35%">
																			<span style="float: left"></span>
																		</td>
																		<td class='centertitle' style="width: 15%" rowspan="2">
																			<input class="common" readonly name="prpLpersonCommerceHealthPoints" value="<fmt:formatNumber value="${prpLpersonLoss1.healthPoints}" pattern="#"/>" />
																		</td>
																		<td class='centertitle' style="width: 15%" rowspan="2">
																			<input class="common" readonly name="prpLpersonCommerceHealthAmount" value="<fmt:formatNumber value="${prpLpersonLoss1.healthAmount}" pattern="#"/>" />
																		</td>
																	</tr>
																	<tr>
																		<td class='centertitle' style="width: 20%">
																			<span style="float: left">B00 死亡給付</span>
																		</td>
																		<td class='centertitle' style="width: 15%">
																			<input class="readonly" readonly name="prpLPersonLossB00" value="0" />
																		</td>
																		<td class='centertitle' style="width: 35%">
																			<span style="float: left"></span>
																		</td>
																	</tr>
																</tbody>
															</table>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td class="title" style="width: 4%">
											<div align="center">
												<input type=button name="buttonPersonCommerceDelete" class="smallbutton" onclick="deletePrpLpersonLossObject(this);" value="-" style="cursor: hand">
											</div>
										</td>
									</tr>
									<c:set var="personMedicalDeformityNo" value="${prpLpersonLoss1.personNo}" />
									<c:set var="personMedicalDeformitySerialNo" value="${personMedicalDeformitySerialNo+1}" />
									<c:set var="personDeformityNo" value="${prpLpersonLoss1.personNo}" />
									<c:set var="personDeformitySerialNo" value="${personDeformitySerialNo+1}" />
									<c:set var="kindCode" value="${prpLpersonLoss1.kindCode}" />
								</c:if>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
<%--** 醫院名稱下拉显示的隐藏域 *--%>
<div id="hospitalList" style="background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 400px;" align="left"></div>
<div id="prpLPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
	<ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0'>
		<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList==null||#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()==0">
			<li>沒有賠款給付對象訊息，請錄入賠款給付對象。</li>
		</s:if>
		<s:else>
			<c:forEach var="prpLpayObjectInfoTemp" items="${prpLpayObjectInfo.prpLpayObjectInfoList}">
				<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="${prpLpayObjectInfoTemp.id.serialNo}" />赔付对象${prpLpayObjectInfoTemp.id.serialNo} 赔付金额: <input type="text" name="payObjectPayAmount"
						onblur="setPayObjectPayAmount();" value="" class="common" style="width: 100px" /></li>
			</c:forEach>
		</s:else>
	</ul>
	<ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'prpLPayObjectinfo')" value="<s:text name='button.close.value' />" /></li>
	</ul>
</div>