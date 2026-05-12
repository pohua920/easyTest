<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法
	/*
	插入一条新的ThirdParty之後的处理（可选方法）
	 */
/* 	function afterInsertThirdParty() {
		var prpLthirdPartyInsuredIdentity = document.getElementsByName("prpLthirdPartyInsuredIdentity");
		if (prpLthirdPartyInsuredIdentity.length > 1) {
			prpLthirdPartyInsuredIdentity[prpLthirdPartyInsuredIdentity.length - 1].selectedIndex = 1;
		}
	}
	/*
	  删除本条WarnRegion之後的处理（可选方法）
	 */
/* 	function afterDeleteThirdParty(field) {
		setPrpLthirdPartySerialNo();
	} */
	/**
	 * 设置setPrpLthirdPartySerialNo
	 */
/* 	function setPrpLthirdPartySerialNo() {
		var count = getElementCount("prpLthirdPartySerialNo");
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i);
			if (count != 1) {
				fm.prpLthirdPartySerialNo[i].value = i;
				fm.prpLthirdPartyNewAddFlag[i].value = "new"; //add by liyanjie 2005-12-18
				//是否新增的车辆标志=new,因为已经控制了不能删除原来的.
			}
		}
	} */
	function afterInsertThirdParty(ThirdPartyObject){
		$(ThirdPartyObject).find(":input[name='prpLthirdPartyNewAddFlag']").val("new");
		$(ThirdPartyObject).find(":input[name='prpLthirdPartyInsuredIdentity']").val("1");
	}
	/**
	 *删除判断涉案车前的判断
	 */
	function beforeDeleteThirdParty(field,pageCode,csFieldName,psFieldName){
		//涉案车流入定损不许删除的判断
		var buttonFlagValue = $(field).siblings(":input[name='buttonFlag']").val();
		if(buttonFlagValue == "disabled"){
			alert("本車爲標的車或者已經流入定損不允許刪除!");
			return false;
		}
		return true;
	}
	//add by wangliguang 20080708 begin
	function beforeDelectRow(field) {
		var count = getElementCount("buttonThirdPartyDelete");
		if (count >= 3) {
			var row = "";
			for ( var i = 0; i < count; i++) {
				if (fm.all("buttonThirdPartyDelete")[i] == field) {
					row = i - 1;
					break;
				}
			}
			if (fm.all("buttonFlag")[row].value == "disabled") {
				alert("本車爲標的車或者已經流入定損不允許刪除!");
			} else {
				deleteRow(field, 'ThirdParty');
			}
		} else {
			if (fm.all("buttonFlag").value == "disabled") {
				alert("本車爲標的車或者已經流入定損不允許刪除!");
			} else {
				deleteRow(field, 'ThirdParty');
			}
		}
	}
	//add by wangliguang 20080708 end 
	/**
	 * 判断对本涉案车责任比例不能是大於100，小於0的数
	 */
	function isRightDutyPercent(field) {
		var lPercent = 0;
		var strmsg = "";
		var i = 0;
		lPercent = parseFloat(field.value);
		if ((lPercent > 100) || (lPercent < 0)) {
			strmsg = "涉案車輛的責任比例不能大於100或者小於0!";
			alert(strmsg);
			field.select();
			field.focus();
			return false;
		}
		if (isNaN(fm.prpLthirdPartySerialNo.length)) {
			return true;
		}
		//只有一条不校验
		return true;
	}
	/**
	 * 並且只应该/必须有一辆为保单车辆
	 */
	function checkInsureCarFlag() {
		var insureCarFlag = ""; //是否本保单车辆
		var i = 0; //计数
		var flagCount = 0; //
		var strmsg = ""; //提示消息
		for (i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
			insureCarFlag = fm.insureCarFlag[i].value;
			if (insureCarFlag = "1") {
				flagCount++;
			}
		}
		if (flagCount < 1) {
			strmsg = "涉案車輛中，必須有1輛車輛爲本保單車輛！";
			alert(strmsg);
			return false;
		}
		if (flagCount > 1) {
			strmsg = "涉案車輛中，必須有1輛車輛爲本保單車輛！";
			alert(strmsg);
			return false;
		}
		return true;
	}
	//改變車輛種類
	function changeCar(field){
		var index = getElementOrder(field) - 1;
		var prpLthirdPartyCarryingUnit = $(document.getElementsByName("prpLthirdPartyCarryingUnit")[index]);
		if($.inArray(field.value, LOADKIND_P) != -1){//LOADKIND_P 人數
			prpLthirdPartyCarryingUnit.attr("value","P");
		}else if($.inArray(field.value, LOADKIND_T) != -1){//LOADKIND_T 噸數
			prpLthirdPartyCarryingUnit.attr("value","T");
		}
		var carryingNumber = $(":input[name='prpLthirdPartyCarryingNumber']")[index];
		changeCarryingNumber(carryingNumber);
	}
	//改變承載單位
	function changeLoadKind(field){
		var index = getElementOrder(field) - 1;
		var carKindCode = document.getElementsByName("carKindCode")[index];
		if($.inArray(carKindCode.value, LOADKIND_P) != -1){
			if(field.value != 'P'){
				alert("請注意：車載類別和車種不匹配！");
				field.value = 'P';
				return false;
			}
		}else if($.inArray(carKindCode.value, LOADKIND_T) != -1){
			if(field.value != 'T'){
				alert("請注意：車載類別和車種不匹配！");
				field.value = 'T';
				return false;
			}
		}
		var carryingNumber = $(":input[name='prpLthirdPartyCarryingNumber']")[index];
		changeCarryingNumber(carryingNumber);
	}
	//改變承載數量
	function changeCarryingNumber(field){
		var index = getElementOrder(field) - 1;
		var carryingUnit = $($(":input[name='prpLthirdPartyCarryingUnit']")[index]).val();
		var carKindCode = $($(":input[name='carKindCode']")[index]).val();
		var numInfo = "";//例如"10,-1"，表示不能大於10
		var num = "";
		var info = "";
		if(carryingUnit == "P" && !!QUANTITY_P[carKindCode]){
			numInfo = QUANTITY_P[carKindCode];
			num = numInfo.split(',')[0];
			info = numInfo.split(',')[1];
			if("-1" == info && parseFloat($(field).val()) > num){
				alert("請注意：此車輛種類乘載人數不能大於 " + num +"！");
				$(field).attr("value","");
				return false;
			}else if("0" == info && parseFloat($(field).val()) != num){
				alert("請注意：此車輛種類乘載人數必須等於 " + num +"！");
				$(field).attr("value",num);
				return false;
			}else if("1" == info && parseFloat($(field).val()) < num){
				alert("請注意：此車輛種類乘載人數不能小於 " + num +"！");
				$(field).attr("value","");
				return false;
			}
		}else if(carryingUnit == "T" && !!QUANTITY_T[carKindCode]){
			numInfo = QUANTITY_T[carKindCode];
			num = numInfo.split(',')[0];
			info = numInfo.split(',')[1];
			if("-1" == info && parseFloat($(field).val()) > num){
				alert("請注意：此車輛種類乘載噸位不能大於 " + num +"！");
				$(field).attr("value","");
				return false;
			}else if("0" == info && parseFloat($(field).val()) != num){
				alert("請注意：此車輛種類乘載噸位必須等於 " + num +"！");
				$(field).attr("value",num);
				return false;
			}else if("1" == info && parseFloat($(field).val()) < num){
				alert("請注意：此車輛種類乘載噸位不能小於 " + num +"！");
				$(field).attr("value","");
				return false;
			}
		}
	}
	
	$(document).ready(function(){
		$(document.getElementsByName("carKindCode")).each(function(index, element){
			if(index > 1){
				if($.inArray($(element).val(), LOADKIND_P) != -1){//LOADKIND_P 人數
					$(document.getElementsByName("prpLthirdPartyCarryingUnit")[index]).attr("value","P");
				}else if($.inArray($(element).val(), LOADKIND_T) != -1){//LOADKIND_T 噸數
					$(document.getElementsByName("prpLthirdPartyCarryingUnit")[index]).attr("value","T");
				}
			}
		});
	});
	<!-- mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整  START-->
	<!-- mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正  START-->
	function updateInsuranceNo() {
		var companyCodeList = document.getElementsByName("prpLthirdPartyInsureComCode");
		var companyCodeCount = companyCodeList.length;
		for (var i = 2; i < companyCodeCount; i++) {
	    	var companyCode = document.getElementsByName("prpLthirdPartyInsureComCode")[i].value;
	    	var InsuranceNumber = document.getElementsByName("prpLthirdPartyInsuranceNo")[i].value;
	    	if (companyCode != '32' && companyCode!='99' && InsuranceNumber.length <=2){
	    	document.getElementsByName("prpLthirdPartyInsuranceNo")[i].value= companyCode;}
			}
		}
	<!-- mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正  END-->	
	<!-- mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 END -->
</script>
<span style="display: none">
	<table class="common" style="display: none" id="ThirdCarLoss_Data"
		cellspacing="1" cellpadding="5">
		<tbody>
			<tr>
				<td class="common"><input type="hidden"
					name="prpLthirdCarLossFlag"> <input type="hidden"
					name="prpLthirdCarLossSerialNo"> <input type="hidden" 
					name="RelateSerialNo"> <input type="hidden"
					name="prpLthirdCarLossLossGrade"> <input
					name="prpLthirdCarLossItemNo" class="readonly" readonly="readonly"
					style="width: 75%" maxlength=3 value="1"></td>
				<td class="common"><input name="prpLthirdCarLossLicenseNo"
					class="readonly" style="width: 90%"></td>
				<c:if test="${prpLnodeType=='check'}">
					<td class="common"><s:select
							list="#request.prpLcheckItemKindList" listKey="kindCode"
							listValue="kindName" name="kindCode" headerKey="" headerValue=""></s:select>
					</td>
				</c:if>
				<td class="common"><s:select list="#request.partCodeList"
						name="partCode" id="partCode" listKey="key" listValue="value"
						onchange="getPartName(this);"></s:select> <input type="hidden"
					name="partName" value="${prpLthirdCarLoss.partName}"></td>
				<td class="common"><input name="compName" class="codename"
					style="width: 90%"
					ondblclick="return openCompCodeWin(ThirdCarLoss_Data,this);">
					<input type="hidden" name="compCode"></td>
				<td class="common"><input name="prpLthirdCarLossLossDesc"
					class="input" style="width: 90%"></td>
				<td class="common" style='width: 4%' align="center">
					<div>
						<input type=button name="buttonThirdCarLossDelete"
							class=smallbutton
							onclick="deleteRow(this,'ThirdCarLoss','prpLthirdCarLossItemNo')" value="-"
							style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" cellpadding="5" cellspacing="1">
	<tr>
		<td>
			<table class="common" style="display: none" id="ThirdParty_Data"
				cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="title" style="width: 4%">
							<div align="center">
								<input class="readonly" readonly="readonly" style="width: 20%"
									name="prpLthirdPartySerialNo"> <input type="hidden"
									class="readonlyno" name="prpLthirdPartyNewAddFlag">
							</div>
						</td>
						<td class="common" style="width: 92%">
							<table class="common" cellspacing="1" cellpadding="1">
								<tr>
									<td class="common" style="TEXT-ALIGN: center" colspan=2
										style="width:30%"><input type="hidden"
										name="insureCarFlag" value="0"> <font color=red>
											<%-- 三者车 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLcheckThirdCar" />
									</font></td>
									<td class="common" style="width: 10%">
										<%-- 牌照號碼--%> <s:text
											name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
									</td>
									<td class="common" style="valign: bottom" colspan=3
										style="width:25%"><input type="text"
										name="prpLthirdPartyLicenseNo" class="input" maxlength=20
										onchange="getCarLossLicenseNo(this);" description="牌照號碼" /> <img
										src="${ctx }/images/bgMarkMustInput.jpg"></td>
									<td class="common" style="valign: bottom" style="width:10%">
										<%-- 车架号 --%> <s:text
											name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
									</td>
									<td class="common" style="valign: bottom" style="width:25%">
										<input type="text" name="prpLthirdPartyFrameNo" class="input"
										maxlength=20 description="车架号">
									</td>
									<input type="hidden" name="prpLthirdPartySelectSend" value="0">
									<input type="hidden" name="insuredFlag" value="1">
								</tr>
								<tr>
									<td class="common" style="width: 10%">
										<%-- 车辆种类 --%> <s:text name="certainLoss.thirdCarLoss.carKind" />：
									</td>
									<td class="common" style="width: 20%"><s:select
											name="carKindCode" list="#request.carKindCodes"
											listKey="id.codeCode" listValue="codeCName" style="width:90%"
											onchange="changeCar(this);"></s:select> <img
										src="${ctx }/images/bgMarkMustInput.jpg"></td>
									<td class="common" style="width: 10%">
										<%-- 发动机号 --%> <s:text
											name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
									</td>
									<td class="common" colspan=3 style="width: 25%"><input
										type="text" name="prpLthirdPartyEngineNo" class="input"
										maxlength=20 description="发动机号"></td>
									<td class="common" style="width: 10%">
										<%-- 号牌底色 --%> <s:text
											name="certainLoss.thirdCarLoss.licenseColor" />
									</td>
									<td class="common" style="width: 25%"><s:select
											name="licenseColorCode" list="#request.licenseColorCodes"
											listKey="id.codeCode" listValue="codeCName"
											value="prpLthirdParty.licenseColorCode"></s:select></td>
								</tr>
								<tr>
									<td class="title" style="width: 10%">
										<%-- 厂牌型号 --%> <s:text
											name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
									</td>
									<td id="prpLthirdPartyBrandName" class="input"
										style="width: 20%"><input type="hidden"
										name="prpLthirdPartyModelCode" class="codecode"
										description="厂牌型号"
										ondblclick="code_CodeSelect(this,'modelCode','0,1','Y');"
										onchange="code_CodeChange(this,'modelCode','0,1','Y');"
										onkeyup="code_CodeSelect(this,'modelCode','0,1','Y');">
										<input type="text" name="prpLthirdPartyBrandName"
										class="codename" maxlength=50 description="厂牌型号名称"
										style="width: 90%"
										ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');"
										onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');"
										onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
									</td>
									<td class="title" style="width: 10%">
										<%-- 承保公司 --%> <s:text
											name="certainLoss.thirdCarLoss.ThirdPartyInsureCom" />
									</td>
									<!-- mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 START -->
									<td id="ThirdPartyInsureComCodeInput" class="input" colspan=3
										style="width: 25%"><input
										name="prpLthirdPartyInsureComCode" class="codecode"
										description="承保公司代码" style="width: 30%"
										ondblclick="code_CodeSelect(this,'CompanyCode','0,1','Y');updateInsuranceNo();"
										onchange="code_CodeChange(this,'CompanyCode','0,1','Y');updateInsuranceNo();"
										onkeyup="code_CodeSelect(this,'CompanyCode','0,1','Y');updateInsuranceNo();">
										<input type="text" name="prpLthirdPartyInsureComName"
										class="codename" style="width: 55%" maxlength=50
										description="承保公司名称"
										ondblclick="code_CodeSelect(this,'CompanyCode','-1,0','Y','N');updateInsuranceNo();"
										onchange="code_CodeChange(this,'CompanyCode','-1,0','Y','N');updateInsuranceNo();"
										onkeyup="code_CodeSelect(this,'CompanyCode','-1,0','Y','N');updateInsuranceNo();">
										<img src="${ctx }/images/bgMarkMustInput.jpg"></td>
									<!-- mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 END -->
									<td class="title" style="width: 12%">
										<%-- 车辆使用年限 --%> <s:text
											name="certainLoss.thirdCarLoss.prpLthirdPartyUseYears" />
									</td>
									<td class="input" style="width: 23%"><input type="text"
										name="prpLthirdPartyUseYears" class="input" maxlength=5
										description="车辆使用年限"></td>
								</tr>
								<tr>
									<td class="title" style="width: 10%">
										<%-- VIN --%> <!-- 										VIN: -->
									</td>
									<td class="input" style="width: 20%"><input type="hidden"
										name="prpLthirdPartyVINNo" value="${thirdParty.VINNo}"
										class="common"></td>
									<td class="title" style="width: 10%">
										<%-- 行驶公里数 --%> <s:text
											name="certainLoss.thirdCarLoss.prpLthirdPartyRunDistance" />
									</td>
									<td class="input" style="width: 25%" colspan=3><input
										type="text" name="prpLthirdPartyRunDistance" class="input"
										description="车辆已行驶公里数" maxlength=15></td>
									<td class="title" id="tdDutyPercentTitle" style="width: 10%;">
										<%-- 责任比例 --%> <s:text
											name="db.prpLpersonloss.indemnityDutyRate" />:
									</td>
									<!--增加对责任比例的校验 -->
									<td class="input" id="tdDutyPercentInput" style="width: 25%;">
										<input type="text" name="prpLthirdPartyDutyPercent"
										class="input" value="0.0" maxlength=6 description="保险车辆对本车责任"
										style="width: 85%"
										onchange="checkPrpLthirdPartyDutyPercent();"> % <img
										src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
								</tr>
								<!-- 差异化    add by liuwei-->
								<input type="hidden" name="prpLthirdPartyRelationship" value="" />
								<tr>
									<td class="common" style="width: 10%">
										<%-- 修車廠負責人姓名 --%> 修車廠負責人姓名：
									</td>
									<td class="common" width=20%><input type="text"
										name="prpLthirdPartyGarageHeadName" class="common" value="">
									</td>
									<td class="common" style="width: 10%">
										<%--承載單位 --%> 承載單位：
									</td>
									<td class="common" style="width: 25%" colspan=3><s:select
											style="width:100px" name="prpLthirdPartyCarryingUnit"
											description="承載單位" class="common"
											list="#request.partyCarryingUnitList" listKey="key"
											listValue="value" onchange="changeLoadKind(this);"></s:select>
										<img src="${ctx }/images/bgMarkMustInput.jpg"></td>
									<td class="common" style="width: 10%">
										<%--強制保險證編號 --%> 強制保險證編號：
									</td>
									<!--mantis： CLM0120 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0120.新核心-強制證號長度管控 -->
									<!-- mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整  -->
									<td class="common" style="width: 25%" colspan=3><input
										type="text" name="prpLthirdPartyInsuranceNo" value=""
										class="common" description="強制保險證編號" maxlength="18"  onchange="checkCINo(2);" > <img
										src="${ctx }/images/bgMarkMustInput.jpg"></td>
								</tr>
								<tr>
									<td class="common" style="width: 10%">
										<%-- 是否有保強制險 --%> 是否有保強制險：
									</td>
									<td class="common" width=20%><select
										name="prpLthirdPartyIsInsurance" style="width: 50%"
										onchange="setInsuredIdentity(this);">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
									</select><img src="${ctx }/images/bgMarkMustInput.jpg"></td>
									<td class="common" style="width: 10%">
										<%--承載數量 --%> 承載數量：
									</td>
									<td class="common" style="width: 25%" colspan=3><input
										type="text" name="prpLthirdPartyCarryingNumber" value=""
										class="common" description="承載數量" maxlength=15 ${readOnly} onchange="changeCarryingNumber(this);">
										<img src="${ctx }/images/bgMarkMustInput.jpg"></td>
									<td class="common" style="width: 10%">
										<%--被保險人身份--%> 被保險人身份：
									</td>
									<td class="common" style="width: 25%" colspan=3><s:select
											name="prpLthirdPartyInsuredIdentity" listKey="key"
											listValue="value" list="#request.identityList" headerKey="0"
											headerValue="0-未投保車輛" style="width:90%" /> <img
										src="${ctx }/images/bgMarkMustInput.jpg"></td>
								</tr>
								<tr>
									<td class="common" style="width: 10%">
										<%--財車車主 --%> 財車車主：
									</td>
									<td class="input" style="width: 15%"><input type="text"
										name="prpLthirdPartyCarsOwners" value="" class="common"
										description="財車車主" maxlength=15></td>
									<td class="title" style="width: 10%">
										<%--財車駕駛地址--%> 財車駕駛地址：
									</td>
									<td class="input" style="width: 30%" colspan="3"><input
										type="text" name="prpLthirdPartyDrivingAddress" value=""
										class="common" description="財車駕駛地址"></td>
									<td class="title" colspan="3"></td>
								</tr>
								<c:if test="${prpLnodeType=='check'}">
									<tr>
										<td class="title" style="width: 10%">
											<%-- 损失金额 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyLossFee" />
										</td>
										<td class="input" style="width: 20%"><input type="text"
											name="prpLthirdPartyLossFee" class="common"></td>
										<input type="hidden" name="prpLthirdPartyLossFlag" value="1">
										<td class="input" colspan="6" />
									</tr>
								</c:if>
								<tr>
									<td colspan="8" class="common" style="width: 92%">
										<table class="common" id="ThirdCarLoss" cellspacing="1"
											cellpadding="5">
											<thead>
												<tr>
													<td class="centertitle" style="width: 15%">
														<%-- 损失项目序号 --%> <s:text
															name="certainLoss.thirdCarLoss.prpLcheckDamageItemNo" />
													</td>
													<td class="centertitle" style="width: 15%">
														<%-- 车牌号 --%> <s:text
															name="certainLoss.thirdCarLoss.prpLthirdCarLicenseNo" />
													</td>
													<c:if test="${prpLnodeType=='check'}">
														<td class="centertitle" style="width: 20%">
															<%-- 险别 --%> <s:text
																name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
														</td>
													</c:if>
													<td class="centertitle" style="width: 15%">
														<%-- 损失部位 --%> <s:text
															name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
													</td>
													<td class="centertitle" style="width: 15%">
														<%-- 零件(项目)名称 --%> <s:text
															name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
													</td>
													<td class="centertitle" style="width: 36%">
														<%-- 损失程度描述 --%> <s:text
															name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
													</td>
													<td class="centertitle" style="width: 4%">&nbsp;</td>
												</tr>
											</thead>
											<tfoot>
												<tr class=common>
													<s:if test="#request.prpLnodeType=='check'">
														<td class="title" colspan=6 align="left"><s:property
																value="prpLnodeType" /> <s:text
																name="certainLoss.thirdCarLoss.promptLoss" /></td>
													</s:if>
													<s:else>
														<td class="title" colspan=5 align="left"><s:text
																name="certainLoss.thirdCarLoss.promptLoss" /></td>
													</s:else>
													<td class="title" align="right" style="width: 4%">
														<div align="center">
															<input type="button" value="+" class=smallbutton
																onclick="insertRowTableNew('ThirdCarLoss','ThirdCarLoss_Data',this,'prpLthirdCarLossItemNo','RelateSerialNo')"
																name="buttonThirdCarLossInsert" style="cursor: hand">
														</div>
													</td>
												</tr>
											</tfoot>
											<tbody>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td class="title" style="width: 4%">
							<div align="center">
								<input type=button name="buttonThirdPartyDelete"
									class=smallbutton onclick="deleteRow(this,'ThirdParty','prpLthirdPartySerialNo','RelateSerialNo');"
									value="-">
							</div>
						</td>
					</tr>
				</tbody>
			</table> <%-- 多行输入展现域 --%>
			<table id="ThirdParty" class="common" align="center" cellspacing="1"
				cellpadding="0">
				<thead>
					<tr class=listtitle>
						<td style="width: 5%">
							<%-- 序号 --%> <s:text name="certainLoss.thirdCarLoss.prpLcheckNo" />
						</td>
						<td style="width: 90%">
							<%-- 内容 --%> <s:text
								name="certainLoss.thirdCarLoss.prpLcheckContent" /> <%--反正只两种调度，所以先用两个隐藏的输入框就够了--%>
							<input type=hidden name="nextScheduleTypeCheck" value="1">
							<input type=hidden name="nextScheduleTypeLoss" value="0">
						</td>
						<td style="width: 5%">操作</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td class="title" colspan=3 align="right" style="width: 4%">
							<input type="button" class=smallbutton value="+"
							onclick="insertRow('ThirdParty',this,'prpLthirdPartySerialNo')" name="buttonThirdPartyInsert">
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:set var="readOnly" value=""></c:set>
					<c:set var="butdisabled" value=""></c:set>
					<c:forEach var="thirdParty" items="${prpLthirdParty.thirdPartyList}" varStatus="thirdParty_status">
					<c:set var="readOnly" value=""></c:set>
					<c:set var="butdisabled" value=""></c:set>
						<!-- 插入涉案车辆内容-->
						<c:forEach var="IsDisabled" items="${delete}"
							varStatus="IsDisabled_status">
							<c:if test="${IsDisabled_status.count==thirdParty_status.count}">
								<c:set var="butdisabled" value="${IsDisabled}"></c:set>
							</c:if>
						</c:forEach>
						<c:if test="${thirdParty.insureCarFlag=='1'}">
							<s:set name="readOnly" value=" readOnly"></s:set>
							<c:set var="butdisabled" value="disabled"></c:set>
						</c:if>
						<c:if test="${prpLnodeType=='check'}">
							<c:set var="prpLcheckLossLossFee" value="0" />
							<c:forEach var="checkLoss"
								items="${prpLcheckLoss.prpLcheckLossList}"
								varStatus="checkLoss_status">
								<c:if test="${checkLoss_status.count==thirdParty_status.count}">
									<c:set var="checkLoss" value="${checkLoss}"></c:set>
									<c:set var="prpLcheckLossLossFee" value="${checkLoss.lossFee}" />
								</c:if>
							</c:forEach>
						</c:if>

						<tr>
							<td class="title" style="width: 4%">
								<div align="center">
									<input class="readonly" readonly="readonly" style="width: 15%"
										"
										name="prpLthirdPartySerialNo"
										value="${thirdParty.id.serialNo}"> <input
										type="hidden" class="readonlyno"
										name="prpLthirdPartyNewAddFlag" value="old">
									<!--是否是新增的车辆标志 -->
								</div>
							</td>
							<td class="common" style="width: 92%">
								<table class="common" cellspacing="1" cellpadding="1">
									<tr>
										<td class="common" style="TEXT-ALIGN: center" colspan=2
											style="width:30%"><input type="hidden"
											name="insureCarFlag" value="${thirdParty.insureCarFlag}">
											<c:if test="${thirdParty.insureCarFlag=='1'}">
												<font color=red><s:text
														name="certainLoss.thirdCarLoss.car" /></font>
											</c:if> <c:if test="${thirdParty.insureCarFlag!='1'}">
												<font color=red><s:text
														name="certainLoss.thirdCarLoss.prpLcheckThirdCar" /></font>
											</c:if></td>
										<td class="common" style="width: 10%">
											<%-- 车牌号码 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
										</td>
										<td class="common" colspan=3 style="width: 25%"><input
											name="prpLthirdPartyLicenseNo" class="input" maxlength=20
											onchange="getCarLossLicenseNo(this);" description="号牌号码"
											value="${thirdParty.licenseNo}" > <img
											src="${ctx }/images/bgMarkMustInput.jpg"></td>
										<td class="common" style="width: 12%">
											<%-- 车架号 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
										</td>
										<td class="common" style="width: 23%"><input type="text"
											name="prpLthirdPartyFrameNo" class="input"
											style="width: 100%" maxlength=20 description="车架号"
											value="${thirdParty.frameNo}" ${readOnly}></td>
										<input type="hidden" name="selectSend" value="1">
										<input type="hidden" name="prpLthirdPartySelectSend"
											value="${thirdParty.selectSend}">
										<input type="hidden" name="insuredFlag" value="1">
									</tr>
									<tr>
										<td class="common" style="width: 10%">
											<%-- 车辆种类 --%> <s:text
												name="certainLoss.thirdCarLoss.carKind" />：
										</td>
										<td class="common" style="width: 20%"><select
											name="carKindCode" style="width: 90%"
											onchange="changeCar(this);">
												<c:forEach items="${requestScope.carKindCodes}"
													var="prpDcode">
													<option value="${prpDcode.id.codeCode}"
														<c:if test="${prpDcode.id.codeCode==thirdParty.carKindCode}"> selected="selected"</c:if>>${prpDcode.codeCName}</option>
												</c:forEach>
										</select> <c:if test="${thirdParty.insureCarFlag!='1'}">
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</c:if></td>
										<td class="common" style="width: 10%">
											<%-- 发动机号 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
										</td>
										<td class="common" colspan=3 style="width: 25%"><input
											type="text" name="prpLthirdPartyEngineNo"
											value="${thirdParty.engineNo}" class="input" maxlength=20
											description="发动机号" ${readOnly}></td>
										<td class="title" style="width: 12%">
											<%-- 号牌底色 --%> <s:text
												name="certainLoss.thirdCarLoss.licenseColor" />
										</td>
										<td class="input" style="width: 23%"><select
											name="licenseColorCode">
												<c:forEach items="${requestScope.licenseColorCodes}"
													var="prpDcode">
													<option value="${prpDcode.id.codeCode}"
														<c:if test="${prpDcode.id.codeCode==thirdParty.licenseColorCode}"> selected="selected"</c:if>>${prpDcode.codeCName}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td class="title" style="width: 10%">
											<%--  厂牌型号 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
										</td>
										<td id="prpLthirdPartyBrandName" class="common"
											style="width: 20%"><input type="hidden" ${readOnly}
											name="prpLthirdPartyModelCode" class="codecode"
											description="厂牌型号" value="${thirdParty.modelCode}"
											ondblclick="code_CodeSelect(this,'ModelCode','0,1','Y');"
											onchange="code_CodeChange(this,'ModelCode','0,1','Y');"
											onkeyup="code_CodeSelect(this,'ModelCode','0,1','Y');">
											<input ${readOnly} type="text" name="prpLthirdPartyBrandName"
											class="codename" maxlength=50 description="厂牌型号名称"
											style="width: 90%" value="${thirdParty.brandName}"
											ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');"
											onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');"
											onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
										</td>
										<!--	<td class="common" style="width:10%">
													厂牌型号
												</td>
												<td class="common" style="width:20%">
													<input type="text" name="prpLthirdPartyBrandName" class="input" value="${thirdParty.brandName}"  maxlength=30 description="厂牌型号" ${readOnly}>
												</td>-->
										<!-- modify by liuwei at 2011-1-19 将获取承保机构信息改成获取承保公司信息 begin -->
										<td class="title" style="width: 10%"><c:if
												test="${thirdParty.insureCarFlag=='1'}">
												<s:text name="certainLoss.thirdCarLoss.insureComName" />
											</c:if> <c:if test="${thirdParty.insureCarFlag!='1'}">
												<%-- 承保公司 --%>
												<s:text name="certainLoss.thirdCarLoss.ThirdPartyInsureCom" />
											</c:if></td>
										<td id="ThirdPartyInsureComCodeInput" class="input" colspan=3
											style="width: 25%"><c:if
												test="${thirdParty.insureCarFlag == '1'}">
												<input name="prpLthirdPartyInsureComCode" class="codecode"
													description="承保机构代码" style="width: 30%"
													value="${thirdParty.insureComCode}"
													ondblclick="code_CodeSelect(this,'insureComCode','0,1','Y');"
													onkeyup="code_CodeSelect(this,'insureComCode','0,1','Y');"
													${readOnly}>
												<input type="text" name="prpLthirdPartyInsureComName"
													class="codecode" maxlength=50 description="承保机构名称"
													style="width: 55%" value="${thirdParty.insureComName}"
													ondblclick="code_CodeSelect(this,'insureComCode','-1,0','Y','N');"
													onkeyup="code_CodeSelect(this,'insureComCode','-1,0','Y','N');"
													${readOnly}>
											</c:if> <c:if test="${thirdParty.insureCarFlag != '1' }">
												<input name="prpLthirdPartyInsureComCode" class="codecode"
													description="承保公司代码" style="width: 30%"
													value="${thirdParty.insureComCode}"
													ondblclick="code_CodeSelect(this,'CompanyCode','0,1','Y');"
													onkeyup="code_CodeSelect(this,'CompanyCode','0,1','Y');"
													${readOnly}>
												<input type="text" name="prpLthirdPartyInsureComName"
													class="codecode" maxlength=50 description="承保公司名称"
													style="width: 55%" value="${thirdParty.insureComName}"
													ondblclick="code_CodeSelect(this,'CompanyCode','-1,0','Y','N');"
													onkeyup="code_CodeSelect(this,'CompanyCode','-1,0','Y','N');"
													${readOnly}>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</c:if></td>
										<!-- modify by liuwei at 2011-1-19 将获取承保机构信息改成获取承保公司信息 end -->
										<td class="common" style="width: 12%">
											<%-- 车辆使用年限 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyUseYears" />
										</td>
										<td class="common" style="width: 23%"><input type="input"
											name="prpLthirdPartyUseYears" class="common" maxlength=5
											description="车辆使用年限" value="${thirdParty.useYears}"
											${readOnly}></td>
									</tr>
									<tr>
										<td class="common" width=10%>
											<%-- VIN --%> <!-- 											VIN: -->
										</td>
										<td class="common" width=20%><input type="hidden"
											name="prpLthirdPartyVINNo" value="${thirdParty.VINNo}"
											class="common" ${readOnly}></td>
										<td class="common" style="width: 10%">
											<%-- 行驶公里数 --%> <s:text
												name="certainLoss.thirdCarLoss.prpLthirdPartyRunDistance" />
										</td>
										<td class="common" style="width: 25%" colspan=3><input
											type="text" name="prpLthirdPartyRunDistance"
											value="${thirdParty.runDistance}" class="common"
											description="车辆已行驶公里数" maxlength=15 ${readOnly}></td>
										<td class="common" id="tdDutyPercentTitle" style="width: 12%;">
											<%-- 责任比例 --%> <s:text
												name="db.prpLpersonloss.indemnityDutyRate" />:
										</td>
										<!--增加对责任比例的校验-->
										<td class="common" id="tdDutyPercentInput" style="width: 23%;">
											<input type="text" name="prpLthirdPartyDutyPercent"
											class="common" maxlength=6 description="保险车辆对本车责任"
											value="${thirdParty.dutyPercent}" style="width: 85%"
											onchange="checkPrpLthirdPartyDutyPercent();"> % <img
											src="${ctx }/images/bgMarkMustInput.jpg">
										</td>
									</tr>
									<!-- 差异化 add by  liuwei 2013-5-11 -->
									<c:if test="${thirdParty.insureCarFlag=='1'}">
										<input type="hidden" name="prpLthirdPartyCarryingUnit"
											value="" />
										<input type="hidden" name="prpLthirdPartyInsuranceNo" value="" />
										<input type="checkbox" name="prpLthirdPartyIsInsurance"
											value="0" checked="checked" style="display: none" />
										<input type="hidden" name="prpLthirdPartyCarryingNumber"
											value="" />
										<input type="hidden" name="prpLthirdPartyInsuredIdentity"
											value="" />
										<input type="hidden" name="prpLthirdPartyCarsOwners" value="" />
										<tr>
											<td class="common" style="width: 10%">
												<%-- 修車廠負責人姓名 --%> 修車廠負責人姓名：
											</td>
											<td class="common" width=20%><input type="text"
												name="prpLthirdPartyGarageHeadName" class="common"
												value="${thirdParty.garageHeadName}" ${readOnly}></td>
											<td class="common" style="width: 10%">
												<%-- 保車駕駛地址 --%> 保車駕駛地址：
											</td>
											<td class="common" style="width: 25%" colspan=3><input
												type="text" name="prpLthirdPartyDrivingAddress"
												value="${thirdParty.drivingAddress}" class="common"
												description="保車駕駛地址"></td>
											<td class="common" style="width: 10%">
												<%-- 本車駕駛人與被保險人關係 --%> 本車駕駛人與被保險人關係：
											</td>
											<td class="common" style="width: 25%" colspan=3><c:set
													var="tempRelationship" value="${thirdParty.relationship}" />
												<s:select name="prpLthirdPartyRelationship"
													value="#attr.tempRelationship" listKey="key"
													listValue="value"
													list="#request.thirdPartyRelationshipList"
													style="width: 100%" /></td>
										</tr>
									</c:if>
									<c:if test="${thirdParty.insureCarFlag!='1'}">
										<input type="hidden" name="prpLthirdPartyRelationship"
											value="" />
										<tr>
											<td class="common" style="width: 10%">
												<%-- 修車廠負責人姓名 --%> 修車廠負責人姓名：
											</td>
											<td class="common" width=20%><input type="text"
												name="prpLthirdPartyGarageHeadName" class="common"
												value="${thirdParty.garageHeadName}" ${readOnly}></td>
											<td class="common" style="width: 10%">
												<%--承載單位 --%> 承載單位：
											</td>
											<td class="common" style="width: 25%" colspan=3><select
												style="width: 100px" name="prpLthirdPartyCarryingUnit"
												class="common" description="承載單位" ${readOnly}
												list="#request.carKindCodes"
												onchange="changeLoadKind(this);">
													<c:forEach items="${partyCarryingUnitList}" var="temp">
														<option value="${temp.key}"
															<c:if test="${temp.key==thirdParty.carryingUnit }">selected</c:if>>${temp.value}</option>
													</c:forEach>
											</select><img src="${ctx }/images/bgMarkMustInput.jpg"></td>
											<td class="common" style="width: 10%">
												<%--強制保險證編號 --%> 強制保險證編號：
											</td>
											<!--mantis： CLM0120 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0120.新核心-強制證號長度管控 -->
											<!-- mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 -->
											<td class="common" style="width: 25%" colspan=3><input
												type="text" name="prpLthirdPartyInsuranceNo" maxlength="18"
												value="${thirdParty.insureComCode}${thirdParty.insuranceNo}" class="common"
												description="強制保險證編號" onchange="checkCINo(2);" ${readOnly}> <img
												src="${ctx }/images/bgMarkMustInput.jpg"></td>
										</tr>
										<tr>
											<td class="common" style="width: 10%">
												<%-- 是否有保強制險 --%> 是否有保強制險：
											</td>
											<td class="common" width=20%><select
												name="prpLthirdPartyIsInsurance"
												onchange="setInsuredIdentity(this);">
													<option value="0"
														<c:if test="${thirdParty.isInsurance=='0'}">selected</c:if>>否</option>
													<option value="1"
														<c:if test="${thirdParty.isInsurance=='1'}">selected</c:if>>是</option>
											</select><img src="${ctx }/images/bgMarkMustInput.jpg"></td>
											<td class="common" style="width: 10%">
												<%--承載數量 --%> 承載數量：
											</td>
											<td class="common" style="width: 25%" colspan=3><input
												type="text" name="prpLthirdPartyCarryingNumber"
												value="${thirdParty.carryingNumber }" class="common"
												onchange="changeCarryingNumber(this);"
												description="承載數量" ${readOnly}> <img
												src="${ctx }/images/bgMarkMustInput.jpg"></td>
											<td class="common" style="width: 10%">
												<%--被保險人身分 --%> 被保險人身份：
											</td>
											<td class="common" style="width: 25%" colspan=3><c:set
													var="tempInsuredIdentity"
													value="${thirdParty.insuredIdentity}" /> <s:select
													name="prpLthirdPartyInsuredIdentity"
													value="#attr.tempInsuredIdentity" listKey="key"
													listValue="value" list="#request.identityList"
													headerKey="0" headerValue="0-未投保車輛" style="width:90%" /> <img
												src="${ctx }/images/bgMarkMustInput.jpg"></td>
										</tr>
										<tr>
											<td class="common" style="width: 10%">
												<%--財車車主 --%> 財車車主：
											</td>
											<td class="input" style="width: 15%"><input type="text"
												name="prpLthirdPartyCarsOwners"
												value="${thirdParty.carsOwners }" class="common"
												description="財車車主" maxlength=15 ${readOnly}></td>
											<td class="title" style="width: 10%">
												<%--財車駕駛地址--%> 財車駕駛地址：
											</td>
											<td class="common" style="width: 30%" colspan="3"><input
												type="text" name="prpLthirdPartyDrivingAddress"
												value="${thirdParty.drivingAddress }" class="common"
												description="財車駕駛地址" maxlength=15 ${readOnly}></td>
											<td class="title" colspan="3"></td>
										</tr>
									</c:if>
									<c:if test="${prpLnodeType=='check'}">
										<tr>
											<td class="title" style="width: 10%">
												<%-- 损失金额 --%> <s:text
													name="certainLoss.thirdCarLoss.prpLthirdPartyLossFee" />
											</td>
											<td class="input" style="width: 20%"><input type="text"
												name="prpLthirdPartyLossFee" class="common"
												value="<fmt:formatNumber value='${prpLcheckLossLossFee}' pattern='#'/>">
											</td>
											<c:choose>
												<c:when
													test="${thirdParty!=null && thirdParty.insureCarFlag=='1'}">
													<c:choose>
														<c:when test="${kindAFlag!=null && kindAFlag=='1'}">
															<c:set var="lossFlag" value="0"></c:set>
															<c:if test="${lossFlag!='0'}">
																<c:set var="lossFlag" value="1"></c:set>
															</c:if>
															<td class="title">
																<%-- 本车是否受损： --%> <s:text
																	name="certainLoss.thirdCarLoss.prpLcheckIsDamage" />
															</td>
															<td class="input" style="width: 10%"><c:set
																	var="tempSelectedValue" value="${lossFlag}" /> <s:select
																	name="prpLthirdPartyLossFlag"
																	value="#attr.thirdParty.lossFlag" listKey="key"
																	listValue="value" list="#request.lossFlagList" /></td>
														</c:when>
														<c:otherwise>
															<input type="hidden" name="prpLthirdPartyLossFlag"
																value="0">
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<input type="hidden" name="prpLthirdPartyLossFlag"
														value="1">
												</c:otherwise>
											</c:choose>
											<td class="input" colspan="5" />
										</tr>
									</c:if>
									<tr class=common>
										<td colspan="8" class="common" style="width: 92%">
											<table class="common" id="ThirdCarLoss" cellspacing="1"
												cellpadding="5">
												<thead>
													<tr>
														<td class="centertitle" style="width: 15%">
															<%-- 损失项目序号 --%> <s:text
																name="certainLoss.thirdCarLoss.prpLcheckDamageItemNo" />
														</td>
														<td class="centertitle" style="width: 15%">
															<%-- 车牌号 --%> <s:text
																name="certainLoss.thirdCarLoss.prpLthirdCarLicenseNo" />
														</td>
														<c:if test="${prpLnodeType=='check'}">
															<td class="centertitle" style="width: 20%">
																<%-- 险别 --%> <s:text
																	name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
															</td>
														</c:if>
														<td class="centertitle" style="width: 15%">
															<%-- 损失部位 --%> <s:text
																name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
														</td>
														<td class="centertitle" style="width: 15%">
															<%-- 零件(项目)名称 --%> <s:text
																name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
														</td>
														<td class="centertitle" style="width: 36%">
															<%-- 损失程度描述 --%> <s:text
																name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
														</td>
														<td class="centertitle" style="width: 4%">&nbsp;</td>
													</tr>
												</thead>
												<tfoot>
													<tr class=common>
														<c:choose>
															<c:when test="${prpLnodeType=='check'}">
																<td class="title" colspan=6 align="left"><s:text
																		name="certainLoss.thirdCarLoss.promptLoss" /></td>
															</c:when>
															<c:otherwise>
																<td class="title" colspan=5 align="left"><s:text
																		name="certainLoss.thirdCarLoss.promptLoss" /></td>
															</c:otherwise>
														</c:choose>
														<td align="right">
															<div align="center">
																<input type="button" class=smallbutton value="+"
																	onclick="insertRowTableNew('ThirdCarLoss','ThirdCarLoss_Data',this,'prpLthirdCarLossItemNo','RelateSerialNo')"
																	name="buttonThirdCarLossInsert" style="cursor: hand">
															</div>
														</td>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="thirdCarLossdtox"
														items="${prpLthirdCarLoss.thirdCarLossList}">
														<input type="hidden" name="test"
															value="${thirdParty.id.serialNo}">
														<input type="hidden" name="test2"
															value="${thirdCarLossdtox.id.serialNo}">
														<c:if
															test="${thirdCarLossdtox.id.serialNo==thirdParty.id.serialNo}">
															<tr class=common>
																<td><input type="hidden"
																	name="prpLthirdCarLossFlag"
																	value="${thirdCarLossdtox.flag}"> <input
																	type="hidden" name="prpLthirdCarLossSerialNo"
																	description="序号"
																	value="${thirdCarLossdtox.id.serialNo}"> <input type="hidden" name="RelateSerialNo" description="序号"
																	value="${thirdCarLossdtox.id.serialNo}"> <input
																	type="hidden" name="prpLthirdCarLossLossGrade"
																	value="${thirdCarLossdtox.lossGrade}"> <input
																	name="prpLthirdCarLossItemNo" class="readonly"
																	readonly="readonly" style="width: 75%" maxlength=3
																	value="${thirdCarLossdtox.id.itemNo}"></td>
																<td><input name="prpLthirdCarLossLicenseNo"
																	class="readonly" readonly="readonly" style="width: 90%"
																	value="${thirdCarLossdtox.licenseNo}"></td>
																<c:if test="${prpLnodeType=='check'}">
																	<td><c:set var="tempSelectedValue"
																			value="${thirdCarLossdtox.kindCode}" scope="request" />
																		<s:select name="kindCode"
																			value="#attr.tempSelectedValue"
																			list="#request.prpLcheckItemKindList"
																			listKey="kindCode" listValue="kindName" headerKey=""
																			headerValue="" /></td>
																</c:if>
																<td><select name="partCode" Class="three"
																	style="width: 90%" onchange="getPartName(this);">
																		<c:forEach var="partCode" items="${partCodeList}">
																			<option value="${partCode.key}"
																				<c:if test="${partCode.key==thirdCarLossdtox.partCode}">selected="selected"</c:if>>${partCode.value}</option>
																		</c:forEach>
																</select> <input type="hidden" name="partName"
																	value="${thirdCarLossdtox.partName}"></td>
																<td><input name="compName" class="codename"
																	style="width: 90%" value="${thirdCarLossdtox.compName}"
																	ondblclick="return openCompCodeWin(ThirdCarLoss,this);">
																	<input type="hidden" name="compCode"
																	value="${thirdCarLossdtox.compCode}"></td>
																<td><input name="prpLthirdCarLossLossDesc"
																	class="input" style="width: 90%"
																	value="${thirdCarLossdtox.lossDesc}"></td>
																<td align="center">
																	<div>
																		<input type="button" name="buttonThirdCarLossDelete"
																			class="smallbutton"
																			onclick="deleteRow(this,'ThirdCarLoss','prpLthirdCarLossItemNo')"
																			value="-" style="cursor: hand">
																	</div>
																</td>
															</tr>
														</c:if>
													</c:forEach>
												</tbody>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td class="title" style="width: 4%">
								<div align="center">
									<input type="hidden" name="buttonFlag" value='${butdisabled}'>
									<input type="button" name="buttonThirdPartyDelete" <c:if test="${thirdParty.insureCarFlag=='1'}">disabled="disabled"</c:if> 
										class=smallbutton onclick="deleteRow(this,'ThirdParty','prpLthirdPartySerialNo','RelateSerialNo')" value="-">
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</td>
	</tr>
</table>