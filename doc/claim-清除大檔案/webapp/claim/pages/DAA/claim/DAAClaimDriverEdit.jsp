<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	/**
	 //在下面加入本页自定义的JavaScript方法
	 插入一条新的driver之後的处理（可选方法）
	 */
	function afterInsertDriver() {
		setButtonDriverInsertStatus();
	}
	/*
	  删除本条Driver之後的处理（可选方法）
	 */
	function afterDeleteDriver(field) {
		setButtonDriverInsertStatus();
	}
	/**
	 * 只允许有一个驾驶员
	 */
	function setButtonDriverInsertStatus() {
		fm.buttonDriverInsert.disabled = false;
	}
	function checkLicenseNo(field) {
		
		var prpLdriverIdentifyNumber = $(field).parents("table").find(":input[name='prpLdriverIdentifyNumber']");
		var identifyNumber = prpLdriverIdentifyNumber.val();
		var driverSex = $(field).parents("table").find(":input[name='driverSex']");
		var driverIdentity = $(field).parents("table").find(":input[name='prpLdriverDriverIdentity']");
		if(identifyNumber != "" ){
			if (driverIdentity.val()=="1"&&!checkIdentifyNumber(identifyNumber, driverSex[0].value)) {
				alert("請輸入正確的身份證號");
				return false;
			}else if (driverIdentity.val()=="3"&&!checkUniformNo(identifyNumber)) {
				alert("請輸入正確的統一編號");
				return false;
			}else{//校验正确则给驾驶证号赋上一样的值
				changeIdentifyNumber(prpLdriverIdentifyNumber[0]);
			}
		}
	}
	function checktype(field) {
		var prpLdriverIdentifyNumber = $(field).parents("table").find(
				"input[name='prpLdriverIdentifyNumber']");
		checkLicenseNo(prpLdriverIdentifyNumber[0]);
	}
	function changeIdentifyNumber(field){
		var prpLdriverIdentifyNumber = $(field).parents("table").find(":input[name='prpLdriverIdentifyNumber']");//身份证号
		var prpLdriverDrivingLicenseNo = $(field).parents("table").find(":input[name='prpLdriverDrivingLicenseNo']");
		//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
		if (null!=prpLdriverDrivingLicenseNo && undefined!=prpLdriverDrivingLicenseNo[0]){
			if(trim(prpLdriverDrivingLicenseNo[0].value).length == 0) { //驾照号码
				prpLdriverDrivingLicenseNo[0].value = prpLdriverIdentifyNumber[0].value;
			}
		}
		//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
	}
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td>
			<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="PersonTraceImg" onclick="showPage(this,spanDriver)">
			<%-- 驾驶人信息 --%>
			<s:text name="info.driver" />
			<table cellpadding="5" cellspacing="1" class="common" id="Driver_Data" style="display: none">
				<tbody>
					<tr class=oddrow >
						<td class="input" style="width: 2%">
							<div align="center">
								<input name="prpLdriverSerialNo" class="readonly" style="width: 70%" readonly="readonly" value="">
							</div>
						</td>
						<td class="common" style="width: 96%">
							<table cellpadding="5" cellspacing="1" class="common" style="width: 100%">
								<tr>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.driverName" />：
									</td>
									<%-- 姓名 --%>
									<td class="input" style="width: 20%">
										<input name="prpLdriverDriverName" class="common" style="width: 85%" maxlength=20 value="" title="駕駛員姓名">
										<img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.driverSex" />：
									</td>
									<%-- 性别 --%>
									<td class="input" style="width: 20%">
										<select name="driverSex" style="width: 20%" onchange="checktype(this)">
											<c:forEach items="${requestScope.driverSexs}" var="driverSex">
												<option value="${driverSex.id.codeCode}">
													<c:out value="${driverSex.codeCName}"></c:out>
												</option>
											</c:forEach>
										</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 13%"><s:text name="db.prpLdriver.marriage" />：</td><%-- 婚姻别 --%>
									<td class="input" style="width: 20%">
										<select name="prpLdriverIsMarried" style="width: 30%">
											<option value="0" selected="selected"><s:text name="db.prpLdriver.Unmarried" /></option><%-- 未婚 --%>
											<option value="1"><s:text name="db.prpLdriver.Married" /></option><%-- 已婚 --%>
										</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.birthday" />：
									</td>
									<%-- 出生年份 --%>
									<%-- 
										mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- start
	       								處理過程：將原本的民國年tag<re:reDate>改為新增的西元年tag<ad:date>
	       								 20190318 修正新增欄位時生日欄輸入的日期會是民國年的問題
									--%>
									<td class="input" style='width: 20%'>
										<ad:date class='common' style="width:85%" name="prpLdriverBirthday" title="出生年份" wdatePicker="true" />
										<img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<%-- 
										mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- end
									 --%>
									 
									<td class="title" style="width: 13%">
										<s:text name="db.prpCCarDriver.identifynumber" />：
									</td>
									<%-- 身份证号码 --%>
									<td class="input" style='width: 20%'>
										<input name="prpLdriverIdentifyNumber" class="common" style="width: 85%" maxlength=20 title="身份證號碼" onblur="checkLicenseNo(this)">
										<c:if test="${prpLnodeType == 'claim'}">
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</c:if>
									</td>
									<td class="title" style="width: 13%"></td>
									<td class="title" style="width: 20%"></td>
								</tr>
								<tr>
									<td class="title" style="width: 13%">
										<s:text name="check.foundFreeTel" />：
									</td>
									<%-- 駕駛人市話 --%>
									<td class="input" style='width: 20%'>
										<input name="prpLdriverDriverPhone" class="common" style="width: 85%" maxlength=20 value="" title="市話號碼">
									</td>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.drivingLicenseNo" />：
									</td>
									<%-- 驾照号码 --%>
									<td class="input" style='width: 20%'>
										<input name="prpLdriverDrivingLicenseNo" class="common" style="width: 85%" maxlength=20 value="${driver.drivingLicenseNo}" title="駕照號碼">
										<c:if test="${prpLnodeType == 'claim'}">
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</c:if>
									</td>
									<td class="title" style="width: 13%">
										<div style="display: none">
											<s:text name="db.prpCinsured.identifytype" />：
										</div>
									</td>
									<%-- 证件类型 --%>
									<td class="input" style="width: 20%">
										<div style="display: none">
											<s:select name="drivingCarType" listKey="key" listValue="value" list="#request.drivingCarTypeList" onblur="checktype(this)" />
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</div>
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.driverCellPhone" />：
									</td>
									<%-- 駕駛人手機 --%>
									<td class="input" style="width: 20%">
										<input name="prpLdriverMobilePhone" class="common" style="width: 85%" maxlength=20 value="${driver.mobilePhone}" title="手機號碼">
									</td>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.Identity" />：
									</td>
									<%-- 駕駛人身份--%>
									<td class="input" style='width: 30%' colspan="2">
										<c:set var="tempDriverIdentity" value="${driver.driverIdentity}" />
										<s:select name="prpLdriverDriverIdentity" value="#attr.tempDriverIdentity" listKey="key" listValue="value" list="#request.identityList" onchange="checkLicenseNo(this);"/>
										<c:if test="${RISKCODE == RISKCODE_DAZ}">
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</c:if>
									</td>
									<td class="title" style="width: 20%"></td>
								</tr>
								<tr>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLthirdparty.licenseNo" />：
									</td>
									<%-- 车牌号 --%>
									<td class="input" style='width: 20%'>
										<input name="prpLdriverLicenseNo" class="common" style="width: 85%" maxlength=20 description="車牌號" value="">
										<img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 13%">
										<s:text name="db.prpLdriver.difference" />：
									</td>
									<%-- 駕駛人區別--%>
									<td class="input" style='width: 20%'>
										<s:select name="prpLdriverDriverDistrict" listKey="key" listValue="value" list="#request.driverDistrictList" style="width:85%" />
										<img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 13%">
										<s:text name="claim.driverComCode" />：
									</td>
									<%-- 驾驶员属地 --%>
									<td class="input" style="width: 20%">
										<input type=text class="codecode" name="prpLdriverApanageCode" style="width: 25%" title="駕駛員屬地代碼" description="駕駛員屬地代碼" value="" ondblclick="code_CodeSelect(this,'DriverApanage','0,1','Y');"
											onchange="code_CodeChange(this,'DriverApanage','0,1','Y');" onkeyup="code_CodeSelect(this,'DriverApanage','0,1','Y');">
										<input type=text class="codecode" name="prpLdriverApanage" title="駕駛員屬地" description="駕駛員屬地" style="width: 65%" value="" ondblclick="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');"
											onchange="code_CodeChange(this,'DriverApanage','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');">
									</td>
								</tr>
								<tr>
									<td style="width: 13%"></td>
									<td style="width: 20%"></td>
									<td style="width: 13%"></td>
									<td style="width: 20%"></td>
								</tr>
							</table>
						</td>
						<td class="title" style="width: 2%" align="right">
							<input type=button name="buttonDriverDelete" class=smallbutton onclick="deleteRow(this,'Driver','prpLdriverSerialNo');" value="-" style="cursor: hand">
							<input name="prpLdriverLicenseColorCode" type="hidden" value="">
							<input name="prpLdriverDriverOccupation" type="hidden" value="">
							<input name="prpLdriverDriverGrade" type="hidden" value="">
							<input name="prpLdriverDriverSeaRoute" type="hidden" value="">
							<input name="prpLdriverDrivingYear" type="hidden" value="">
							<input name="prpLdriverSpecialCertificate" type="hidden" value="">
							<input name="prpLdriverFlag" type="hidden" value="">
						</td>
					</tr>
					<tr height="2" bgcolor="block">
						<td colspan="3"></td>
					</tr>
				</tbody>
			</table>
			<span id="spanDriver" style="display:"> <%-- 多行输入展现域 --%>
				<table class=common id="Driver" cellpadding="5" cellspacing="1">
					<thead>
						<tr class=listtitle>
							<td style="width: 5%">
								<s:text name="db.prpCinsuredExt.serialNo" />
							</td>
							<%-- 序号 --%>
							<td style="width: 90%">
								<s:text name="db.prpLregistText.context" />
							</td>
							<%-- 内容 --%>
							<td style="width: 5%"><s:text name="certify.operate" /></td>
							<%-- 操作 --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="driver" items="${prpLdriver.driverList}" varStatus="driver_status">
							<tr class=oddrow>
								<td class="input" style="width: 2%">
									<div align="center">
										<input name="prpLdriverSerialNo" class="readonly" readonly="readonly" value="${driver.id.serialNo}">
									</div>
								</td>
								<td class="common" style="width: 96%">
									<table cellpadding="5" cellspacing="1" class="common" style="width: 100%">
										<tr>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLdriver.driverName" />：
											</td>
											<%-- 姓名 --%>
											<td class="input" style="width: 20%">
												<input name="prpLdriverDriverName" class="common" style="width: 85%" maxlength=20 value="${driver.driverName}" title="駕駛員姓名">
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLdriver.driverSex" />：
											</td>
											<%-- 性别 --%>
											<td class="input" style="width: 20%">
												<select name="driverSex" style="width: 20%" onchange="checktype(this)">
													<c:forEach items="${requestScope.driverSexs}" var="driverSex">
														<option value="${driverSex.id.codeCode}" <c:if test="${driverSex.id.codeCode==driver.driverSex}">selected="selected"</c:if>>
															<c:out value="${driverSex.codeCName}"></c:out>
														</option>
													</c:forEach>
												</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 13%"><s:text name="db.prpLdriver.marriage" />：</td><%-- 婚姻别--%>
											<td class="input" style='width: 20%'>
												<select name="prpLdriverIsMarried" style="width: 30%">
													<option value="0" <c:if test="${driver.isMarried=='0' }">selected</c:if>><s:text name="db.prpLdriver.Unmarried" /></option><%--未婚--%>
													<option value="1" <c:if test="${driver.isMarried=='1' }">selected</c:if>><s:text name="db.prpLdriver.Married" /></option><%--已婚--%>
												</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLdriver.birthday" />：
											</td>
											<%-- 出生年份 --%>
											<%-- 
												mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- start
		        								處理過程：將原本的民國年tag<re:reDate>改為新增的西元年tag<ad:date>
		        								 20190318 需求再次變更，生日依然需必填
											 --%>
											<td class="input" style='width: 20%'>
												<ad:date class='common' style="width:85%" name="prpLdriverBirthday" title="出生年份" wdatePicker="true" value="${driver.birthday}" />
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<%-- 
												mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- end
											 --%>
											<td class="title" style="width: 13%">
												<s:text name="db.prpCCarDriver.identifynumber" />：
											</td>
											<%-- 身份证号码 --%>
											<td class="input" style='width: 20%'>
												<input name="prpLdriverIdentifyNumber" class="common" style="width: 85%" maxlength=20 value="${driver.identifyNumber}" title="身份证号码" onblur="checkLicenseNo(this)">
											<c:if test="${prpLnodeType == 'claim'}">
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</c:if>
											</td>
											<td class="title" style="width: 13%"></td>
											<td class="title" style="width: 20%"></td>
										</tr>
										<tr>
											<td class="title" style="width: 13%"><s:text name="check.foundFreeTel" />：</td>
											<%-- 驾驶人电话 --%>
											<td class="input" style='width: 20%'>
												<input name="prpLdriverDriverPhone" class="common" style="width: 85%" maxlength=20 value="${driver.driverSeaRoute}" title="駕駛人電話">
											</td>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLdriver.drivingLicenseNo" />：
											</td>
											<%-- 驾照号码 --%>
											<td class="input" style='width: 20%'>
												<input name="prpLdriverDrivingLicenseNo" class="common" style="width: 85%" maxlength=20 value="${driver.drivingLicenseNo}" title="駕照號碼">
												<c:if test="${prpLnodeType == 'claim'}">
													<img src="${ctx }/images/bgMarkMustInput.jpg">
												</c:if>
											</td>
											<td class="title" style="width: 13%">
												<div style="display: none">
													<s:text name="db.prpCinsured.identifytype" />：
												</div>
											</td>
											<%-- 证件类型 --%>
											<td class="input" style="width: 20%">
												<div style="display: none">
													<c:set var="tempSelectedValue" value="${driver.drivingCarType}" />
													<s:select name="drivingCarType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.drivingCarTypeList" onblur="checktype(this)" />
													<img src="${ctx }/images/bgMarkMustInput.jpg">
												</div>
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 13%"><s:text name="db.prpLdriver.driverCellPhone" />：</td><%-- 驾驶人手机 --%>
											<td class="input" style="width: 20%">
												<input name="prpLdriverMobilePhone" class="common" style="width: 85%" maxlength=20 value="${driver.mobilePhone}" title="駕駛人手機">
											</td>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLdriver.Identity" />：
											</td>
											<%-- 駕駛人身份--%>
											<td class="input" style='width: 30%' colspan="2">
												<c:set var="tempDriverIdentity" value="${driver.driverIdentity}" />
												<s:select name="prpLdriverDriverIdentity" value="#attr.tempDriverIdentity" listKey="key" listValue="value" list="#request.identityList" onchange="checkLicenseNo(this);"/>
												<c:if test="${RISKCODE == RISKCODE_DAZ}">
													<img src="${ctx }/images/bgMarkMustInput.jpg">
												</c:if>
											</td>
											<td class="title" style="width: 20%"></td>
										</tr>
										<tr>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLthirdparty.licenseNo" />：
											</td>
											<%-- 车牌号 --%>
											<td class="input" style='width: 20%'>
												<input name="prpLdriverLicenseNo" class="common" style="width: 85%" maxlength=20 description="车牌号" value="${driver.licenseNo}">
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 13%">
												<s:text name="db.prpLdriver.difference" />：
											</td>
											<%-- 駕駛人區別--%>
											<td class="input" style='width: 20%'>
												<c:set var="tempDriverDistrict" value="${driver.driverDistrict}" />
												<s:select name="prpLdriverDriverDistrict" value="#attr.tempDriverDistrict" listKey="key" listValue="value" list="#request.driverDistrictList" style="width:85%" />
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 13%">
												<s:text name="claim.driverComCode" />：
											</td>
											<%-- 驾驶员属地 --%>
											<td class="input" style="width: 20%">
												<input type=text class="codecode" name="prpLdriverApanageCode" style="width: 25%" title="駕駛員屬地代碼" description="駕駛員屬地代碼" value="${driver.driverApanageCode}"
													ondblclick="code_CodeSelect(this,'DriverApanage','0,1','Y');" onchange="code_CodeChange(this,'DriverApanage','0,1','Y');" onkeyup="code_CodeSelect(this,'DriverApanage','0,1','Y');">
												<input type=text class="codecode" name="prpLdriverApanage" title="駕駛員屬地" description="駕駛員屬地" style="width: 65%" value="${driver.driverApanage}"
													ondblclick="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');" onchange="code_CodeChange(this,'DriverApanage','-1,0','Y','N');"
													onkeyup="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');">
											</td>
										</tr>
										<tr>
											<td style="width: 13%"></td>
											<td style="width: 20%"></td>
											<td style="width: 13%"></td>
											<td style="width: 20%"></td>
										</tr>
									</table>
								</td>
								<td class="title" style="width: 2%" align="right">
									<input type=button name="buttonDriverDelete" class=smallbutton onclick="deleteRow(this,'Driver','prpLdriverSerialNo');" value="-" style="cursor: hand">
									<input name="prpLdriverLicenseColorCode" type="hidden" value="${driver.licenseColorCode}">
									<input name="prpLdriverDriverOccupation" type="hidden" value="${driver.driverOccupation}">
									<input name="prpLdriverDriverGrade" type="hidden" value="${driver.driverGrade}">
									<input name="prpLdriverDriverSeaRoute" type="hidden" value="${driver.driverSeaRoute}">
									<input name="prpLdriverDrivingYear" type="hidden" value="${driver.drivingYear}">
									<input name="prpLdriverSpecialCertificate" type="hidden" value="${driver.specialCertificate}">
									<input name="prpLdriverFlag" type="hidden" value="${driver.flag}">
								</td>
							</tr>
							<tr height="2" bgcolor="block">
								<td colspan="3"></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td class="title" colspan="2">
								<s:text name="prompt.certainLoss.addDelete" />
								<%--(按"+"号键增加驾驶员讯息，按"-"号键删除讯息) --%>
							</td>
							<td class="title" align="right">
								<input type="button" value="+" class=smallbutton onclick="insertRow('Driver',this,'prpLdriverSerialNo')" name="buttonDriverInsert" style="cursor: hand">
							</td>
						</tr>
					</tfoot>
				</table>
			</span>
		</td>
	</tr>
</table>