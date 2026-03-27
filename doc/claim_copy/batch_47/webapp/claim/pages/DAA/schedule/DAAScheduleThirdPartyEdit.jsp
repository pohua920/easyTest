<%@ page contentType="text/html; charset=GBK"%>
<%@page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%--
****************************************************************************
* DESC       ：添加第三者车辆信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-03-02
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
************************ ****************************************************
--%>
<%-- 多行输入展现域的模板 --%>
<%--多行输入自定义JavaScript方法域--%>
<script language='javascript'>
//在下面加入本页自定义的JavaScript方法

/*
插入一条新的ThirdParty之後的处理（可选方法）
 */
function afterInsertThirdParty() {
	setPrpLthirdPartySerialNo();
}

/* 
  删除本条WarnRegion之後的处理（可选方法）
 */
function afterDeleteThirdParty(field) {

	setPrpLthirdPartySerialNo();
}

/**
 * 设置setPrpLthirdPartySerialNo
 */
function setPrpLthirdPartySerialNo() {
	var count = getElementCount("prpLthirdPartySerialNo");
	for ( var i = 0; i < count; i++) {
		// alert("看看什么时候运行?count="+count+"  i="+i);
		if (count != 1) {
			fm.prpLthirdPartySerialNo[i].value = i;
			fm.prpLthirdPartyNewAddFlag[i].value = "new"; //add by liyanjie 2005-12-17
			//是否新增的车辆标志=new,因为已经控制了不能删除原来的.
		}
	}
}

/**
 * 判断对本涉案车责任比例不能是大於100，小於0的数
 */
function isRightDutyPercent() {
	var lPercent = 0;
	var strmsg = "";
	var i = 0;
	if (isNaN(fm.prpLthirdPartySerialNo.length)) {
		return true;
	}
	//只有一条不校验
	for (i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
		lPercent = parseInt(fm.prpLthirdPartyDutyPercent[i].value);
		if ((lPercent > 100) || (lPercent < 0)) {
			strmsg = "序號為" + fm.prpLthirdPartySerialNo[i].value
					+ "的涉案車輛的責任比例不能是大於100或者小於0!";
			alert(strmsg);
			//fm.prpLthirdPartySerialNo[i].onfocus();
			return false;
		}

	}

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
		strmsg = "涉案車輛中，必須有1輛車輛為本保單車輛！";
		alert(strmsg);
		return false;
	}

	if (flagCount > 1) {
		strmsg = "涉案車輛中，只能有1輛車輛為本保單車輛！";
		alert(strmsg);
		return false;
	}

	return true;
}
</script>
<span style="display: none">
	<table class="common" style="display: none" id="ThirdCarLoss_Data" cellspacing="1" cellpadding="0">
		<tbody>
			<tr>
				<td class="input" style="width: 10%">
					<input type="hidden" name="prpLthirdCarLossFlag">
					<input type="hidden" name="prpLthirdCarLossSerialNo" description="序号">
					<input type="hidden" name="RelateSerialNo" description="序号">
					<input type="hidden" name="prpLthirdCarLossLossGrade" description="损失程度级别">
					<input name="prpLthirdCarLossItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="1">
				</td>
				<td class="input" style="width: 15%">
					<input name="prpLthirdCarLossLicenseNo" class="common" style="width: 90%">
				</td>
				<td class="input" style="width: 15%">
					<s:select name="partCode" Style="width:120px" list="#request.partCodeList" listKey="key" listValue="value" value="prpLthirdCarLoss.partCode" onchange="getPartName(this);"></s:select>
					<input type="hidden" name="partName" value="${prpLthirdCarLoss.partName}">
				</td>
				<td class="input" style="width: 15%">
					<input name="compName" class="codename" style="width: 90%" ondblclick="return openCompCodeWin(ThirdCarLoss_Data,this);">
					<input type="hidden" name="compCode">
				</td>
				<td class="input" style="width: 26%">
					<input name="prpLthirdCarLossLossDesc" class="input" style="width: 90%">
				</td>
				<td class="input" style='width: 4%' align="center">
					<div>
						<input type=button name="buttonThirdCarLossDelete" onclick="deleteRowTable(this,'ThirdCarLoss',1,1)" value="-" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr class="mline">
		<td class="subformtitle" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ThirdPartyImg" onclick="showPage(this,spanThirdPartyAll)"> 涉案車輛<br> <span style="display: none"> <!--点击後显示内容-->
				<table class="common" style="display: none" id="ThirdParty_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="title" style="width: 4%">
								<div align="center">
									<input class="readonlyno" readonly name="prpLthirdPartySerialNo">
									<input type="hidden" class="readonlyno" name="prpLthirdPartyNewAddFlag">
								</div>
							</td>
							<td class="subformtitle" style="width: 92%">
								<table class="common" cellspacing="1" cellpadding="0">
									<tr>
										<td class="common" style="TEXT-ALIGN: center" colspan=2 style="width:30%">
											<font color=red><s:text name="certainLoss.thirdCarLoss.prpLcheckThirdCar" /></font>
										</td>
										<td class="title" style="width: 10%">號牌號碼：</td>
										<td class="input" style="width: 20%" style="valign:bottom">
											<input name="prpLthirdPartyLicenseNo" class="input" style="width: 75%" maxlength=20 description="号牌号码">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style="width: 10%; valign: bottom">車架號：</td>
										<td class="input" style="width: 20%; valign: bottom">
											<input type="text" name="prpLthirdPartyFrameNo" class="input" maxlength=20 description="车架号">
										</td>
										<input type="hidden" name="prpLthirdPartySelectSend" value="0">
										<input type="hidden" name="insuredFlag" value="1">
									</tr>
									<tr>
										<td class="title" style="width: 10%">車輛種類：</td>
										<td class="input" style="width: 20%">
											<select name="carKindCode">
												<c:forEach items="${carKindCodes}" var="prpDcode">
													<option value="${prpDcode.id.codeCode}" <c:if test="${thirdParty.carKindCode ==prpDcode.id.codeCode}">
	                             <c:out value="selected"/></c:if>>
														<c:out value="${prpDcode.codeCName}" />
													</option>
												</c:forEach>
											</select>
										</td>
										<td class="title" style="width: 10%">發動機號：</td>
										<td class="input" style="width: 20%">
											<input type="text" name="prpLthirdPartyEngineNo" class="input" maxlength=20 description="发动机号">
										</td>
										<td class="title" style="width: 10%">號牌底色：</td>
										<td class="input" style="width: 20%">
											<select name="licenseColorCode">
												<c:forEach items="${licenseColorCodes}" var="prpDcode">
													<option value="${prpDcode.id.codeCode}" <c:if test="${thirdParty.licenseColorCode ==prpDcode.id.codeCode}">
	                             <c:out value="selected"/></c:if>>
														<c:out value="${prpDcode.codeCName}" />
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 10%">廠牌型號：</td>
										<td class="input" style="width: 20%">
											<input type="text" name="prpLthirdPartyBrandName" class="input" maxlength=30 description="厂牌型号">
										</td>
										<td class="title" style="width: 10%">承保公司：</td>
										<td id="ThirdPartyInsureComCodeInput" class="input" style="width: 20%">
											<input name="prpLthirdPartyInsureComCode" class="codecode" description="承保公司代码" style="width: 30%" ondblclick="code_CodeSelect(this,'CompanyCode','0,1','Y');"
												onkeyup="code_CodeSelect(this,'CompanyCode','0,1','Y');">
											<input type="text" name="prpLthirdPartyInsureComName" class="codename" maxlength=50 description="承保公司名称" style="width: 60%" ondblclick="code_CodeSelect(this,'CompanyCode','-1,0','Y','N');"
												onkeyup="code_CodeSelect(this,'CompanyCode','-1,0','Y','N');">
										</td>
										<td class="title" style="width: 10%">車輛使用年限：</td>
										<td class="input" style="width: 20%">
											<input type="input" name="prpLthirdPartyUseYears" class="input" maxlength=5 description="车辆使用年限">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 10%">
<!-- 										VIN： -->
										</td>
										<td class="input" style="width: 20%">
											<input type="hidden" name="prpLthirdPartyVINNo" class="common" style="width: 90%">
										</td>
										<td class="title" style="width: 10%">行駛公里數：</td>
										<td class="input" style="width: 20%">
											<input type="text" name="prpLthirdPartyRunDistance" class="input" description="车辆已行驶公里数" maxlength=15>
										</td>
										<td class="title" style="width: 16%; display: none">是否為本保單車輛</td>
										<td class="input" style="width: 6%; TEXT-ALIGN: center; display: none">
											<input type="hidden" name="insureCarFlag" value="0">
											三者車
										</td>
										<!--在报案时不显示责任比例-->
										<td class="title" id="tdDutyPercentTitle" style="width: 10%;">對本涉案車責任比例：</td>
										<td class="input" id="tdDutyPercentInput" style="width: 20%;">
											<input type="text" name="prpLthirdPartyDutyPercent" class="input" maxlength=6 description="保险车辆对本车责任" style="width: 90%"">
											% <img src="/claim/images/bgMarkMustInput.jpg">
										</td>
									</tr>
									<tr>
										<td colspan="8" class="subformtitle" style="width: 92%">
											<table class="common" id="ThirdCarLoss" cellspacing="1" cellpadding="0">
												<thead>
													<tr>
														<td class="centertitle" style="width: 10%">損失項目序號</td>
														<td class="centertitle" style="width: 15%">車牌號</td>
														<td class="centertitle" style="width: 15%">損失部位</td>
														<td class="centertitle" style="width: 15%">零件(項目)名稱</td>
														<td class="centertitle" style="width: 26%">損失程度描述</td>
														<td class="title" style="width: 4%">&nbsp;</td>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<td class="title" colspan=5 style="width: 96%">(按"+"號鍵增加損失部位信息，按"-"號鍵刪除信息)</td>
														<td class="title" align="right" style="width: 4%">
															<div align="center">
																<input type="button" value="+" onclick="insertRowTable('ThirdCarLoss','ThirdCarLoss_Data',this)" name="buttonDriverInsert" style="cursor: hand">
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
									<input type=button name="buttonThirdPartyDelete" onclick="deleteRow(this,'ThirdParty')" value="-">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanThirdPartyAll" style="display:"> <%-- 多行输入展现域 --%>
				<table id="ThirdParty" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="title" style="width: 5%">序號</td>
							<td class="title" style="width: 95%" colspan="2">内容</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">(按"+"號鍵增加涉案車輛，按"-"號鍵刪除信息)</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('ThirdParty')" align="right" name="buttonThirdPartyInsert">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="readonlyThirdPartyTable">
						<c:set var="index" value="0"></c:set>
						<c:if test="${prpLthirdParty.thirdPartyList!=null}">
							<c:forEach var="thirdParty" items="${prpLthirdParty.thirdPartyList}">
								<c:set var="prpLthirdPartyDto2" value="${prpLthirdParty.thirdPartyList[index]}"></c:set>
								<c:set var="intSerialNo" value="${prpLthirdPartyDto2.id.serialNo}"></c:set>
								<!-- 插入涉案车辆内容-->
								<c:set var="butdisabled" value=""></c:set>
								<c:if test="${prpLthirdParty.nodeType=='check'}">
									<c:set var="butdisabled" value="'disabled'"></c:set>
								</c:if>
								<tr>
									<td class="title" style="width: 4%">
										<div align="center">
											<input class="readonlyno" readonly name="prpLthirdPartySerialNo" value="${thirdParty.id.serialNo}">
											<input type="hidden" class="readonlyno" name="prpLthirdPartyNewAddFlag" value="old">
											<!--是否是新增的车辆标志 -->
										</div>
									</td>
									<td class="subformtitle" style="width: 92%">
										<table class="common" cellspacing="1" cellpadding="0">
											<tr>
												<td class="common" style="TEXT-ALIGN: center" colspan=2 style="width:30%">
													<c:if test="${thirdParty.insureCarFlag=='1'}">
														<font color=red><s:text name="certainLoss.thirdCarLoss.car" /></font>
													</c:if>
													<c:if test="${thirdParty.insureCarFlag!='1'}">
														<font color=red><s:text name="certainLoss.thirdCarLoss.prpLcheckThirdCar" /></font>
													</c:if>
												</td>
												<td class="title" style="width: 10%">號牌號碼：</td>
												<td class="input" style="width: 20%; valign: bottom">
													<input name="prpLthirdPartyLicenseNo" class="input" style="width: 75%" maxlength=20 description="号牌号码" value="${thirdParty.licenseNo}">
													<img src="/claim/images/bgMarkMustInput.jpg">
												</td>
												<td class="title" style="width: 10%">車架號：</td>
												<td class="input" style="width: 20%">
													<input type="text" name="prpLthirdPartyFrameNo" class="input" maxlength=20 description="车架号" value="${thirdParty.frameNo}">
												</td>
												<input type="hidden" name="prpLthirdPartySelectSend" value="${thirdParty.selectSend}">
												<input type="hidden" name="insuredFlag" value="1">
											</tr>
											<tr>
												<td class="title" style="width: 10%">車輛種類：</td>
												<td class="input" style="width: 20%">
													<select name="carKindCode">
														<c:forEach items="${carKindCodes}" var="prpDcode">
															<option value="${prpDcode.id.codeCode}" <c:if test="${thirdParty.carKindCode ==prpDcode.id.codeCode}">
	                             <c:out value="selected"/></c:if>>
																<c:out value="${prpDcode.codeCName}" />
															</option>
														</c:forEach>
													</select>
												</td>
												<td class="title" style="width: 10%">發動機號：</td>
												<td class="input" style="width: 20%">
													<input type="text" name="prpLthirdPartyEngineNo" value="${thirdParty.engineNo}" class="input" maxlength=20 description="发动机号">
												</td>
												<td class="title" style="width: 10%">號牌底色：</td>
												<td class="input" style="width: 20%">
													<select name="licenseColorCode">
														<c:forEach items="${licenseColorCodes}" var="prpDcode">
															<option value="${prpDcode.id.codeCode}" <c:if test="${thirdParty.licenseColorCode ==prpDcode.id.codeCode}">
	                             <c:out value="selected"/></c:if>>
																<c:out value="${prpDcode.codeCName}" />
															</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td class="title" style="width: 10%">廠牌型號：</td>
												<td class="input" style="width: 20%">
													<input type="text" name="prpLthirdPartyBrandName" class="input" value="${thirdParty.brandName}" style="width: 100%" maxlength=30 description="厂牌型号">
												</td>
												<td class="title" style="width: 10%">承保公司：</td>
												<td id="ThirdPartyInsureComCodeInput" class="input" style="width: 20%">
													<input name="prpLthirdPartyInsureComCode" class="codecode" description="承保公司代码" style="width: 30%" value="${thirdParty.insureComCode}">
													<input type="text" name="prpLthirdPartyInsureComName" class="codename" maxlength=50 description="承保公司名称" style="width: 60%" value="${thirdParty.insureComName}">
												</td>
												<td class="title" style="width: 10%">車輛使用年限：</td>
												<td class="input" style="width: 20%">
													<input type="input" name="prpLthirdPartyUseYears" class="common" maxlength=5 description="车辆使用年限" value="${thirdParty.useYears}">
												</td>
											</tr>
											<tr>
												<td class="title" style="width: 10%">
<!-- 												VIN： -->
												</td>
												<td class="input" style="width: 20%">
													<input type="hidden" name="prpLthirdPartyVINNo" class="common" style="width: 90%" value="${thirdParty.VINNo}">
												</td>
												<td class="title" style="width: 10%">行駛公里數：</td>
												<td class="input" style="width: 20%">
													<input type="text" name="prpLthirdPartyRunDistance" value="${thirdParty.runDistance}" class="common" description="车辆已行驶公里数" maxlength=15>
												</td>
												<td class="title" style="width: 16%; display: none">是否為本保單車輛</td>
												<td class="input" style="width: 6%; display: none; TEXT-ALIGN: center">
													<input type="hidden" name="insureCarFlag" value="${thirdParty.insureCarFlag}">
													<c:if test="${thirdParty.insureCarFlag=='1'}">標的車</c:if>
													<c:if test="${thirdParty.insureCarFlag!='1'}">三者車</c:if>
												</td>
												<td class="title" id="tdDutyPercentTitle" style="width: 10%;">對本涉案車責任比例：</td>
												<td class="input" id="tdDutyPercentInput" style="width: 20%;">
													<input type="text" name="prpLthirdPartyDutyPercent" class="common" maxlength=6 description="保险车辆对本车责任" style="width: 90%" value="${thirdParty.dutyPercent}">
													% <img src="/claim/images/bgMarkMustInput.jpg">
												</td>
											</tr>
											<tr>
												<td colspan="8" class="subformtitle" style="width: 92%">
													<table class="common" id="ThirdCarLoss" cellspacing="1" cellpadding="0">
														<thead>
															<tr>
																<td class="centertitle" style="width: 10%">損失項目序號</td>
																<td class="centertitle" style="width: 15%">車牌號</td>
																<td class="centertitle" style="width: 15%">損失部位</td>
																<td class="centertitle" style="width: 15%">零件(項目)名稱</td>
																<td class="centertitle" style="width: 26%">損失程度描述</td>
																<td class="title" style="width: 4%">&nbsp;</td>
															</tr>
														</thead>
														<tfoot>
															<tr>
																<td class="title" colspan=5 style="width: 96%">(按"+"號鍵增加損失部位信息，按"-"號鍵刪除信息)</td>
																<td class="title" align="right" style="width: 4%">
																	<div align="center">
																		<input type="button" value="+" onclick="insertRowTable('ThirdCarLoss','ThirdCarLoss_Data',this)" name="buttonDriverInsert" ${disabled } style="cursor: hand">
																	</div>
																</td>
															</tr>
														</tfoot>
														<tbody>
															<c:if test="${prpLthirdCarLoss.thirdCarLossList!=null}">
																<c:forEach var="thirdCarLossdtox" items="${prpLthirdCarLoss.thirdCarLossList}">
																	<input type="hidden" name="test" value="${thirdParty.id.serialNo}">
																	<input type="hidden" name="test2" value="${thirdCarLossdtox.id.serialNo}">
																	<c:if test="${thirdCarLossdtox.id.serialNo==intSerialNo}">
																		<tr>
																			<td class="input" style="width: 10%">
																				<input type="hidden" name="prpLthirdCarLossFlag" value="${thirdCarLossdtox.flag}">
																				<input type="hidden" name="prpLthirdCarLossSerialNo" description="序号" value="${thirdCarLossdtox.id.serialNo}">
																				<input type="hidden" name="RelateSerialNo" description="序号" value="${thirdCarLossdtox.id.serialNo}">
																				<input type="hidden" name="prpLthirdCarLossLossGrade" value="${thirdCarLossdtox.lossGrade}">
																				<input name="prpLthirdCarLossItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="${thirdCarLossdtox.id.itemNo}">
																			</td>
																			<td class="input" style="width: 15%">
																				<input name="prpLthirdCarLossLicenseNo" class="readonly" readonly style="width: 90%" value="${thirdCarLossdtox.licenseNo}">
																			</td>
																			<td class="input" style="width: 15%">
																				<select name="partCode">
																					<c:forEach items="${partCodeList}" var="lable">
																						<option value="${lable.key}" <c:if test="${thirdCarLossdtox.partCode==lable.key}"> selected="selected" </c:if>>
																							<c:out value="${lable.value}"></c:out>
																						</option>
																					</c:forEach>
																				</select>
																				<input type="hidden" name="partName" value="${thirdCarLossdtox.partName}">
																			</td>
																			<td class="input" style="width: 15%">
																				<input name="compName" class="codename" style="width: 90%" value="${thirdCarLossdtox.compName}">
																				<input type="hidden" name="compCode" value="${thirdCarLossdtox.compCode}">
																			</td>
																			<td class="input" style="width: 26%">
																				<input name="prpLthirdCarLossLossDesc" class="input" style="width: 90%" value="${thirdCarLossdtox.lossDesc}">
																			</td>
																			<td class="input" style='width: 4%' align="center">
																				<div>
																					<input type=button name="buttonThirdCarLossDelete" onclick="deleteRowTable(this,'ThirdCarLoss',1,1)" value="-" ${disabled } style="cursor: hand">
																				</div>
																			</td>
																		</tr>
																	</c:if>
																</c:forEach>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td class="title" style="width: 4%">
										<div align="center">
											<input type=button name="buttonThirdPartyDelete" onclick="deleteRow(this,'ThirdParty')" value="-" disabled="disabled">
										</div>
									</td>
								</tr>
								<c:set var="index" value="${index + 1}"></c:set>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>