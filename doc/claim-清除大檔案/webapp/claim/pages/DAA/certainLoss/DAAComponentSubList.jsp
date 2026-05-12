<%--
****************************************************************************
* DESC       ：换件信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
 /*  function checkVerpCompPrice(field,serialNo){
     var standerPrice  = fm.prpLcomponentSysMarketPrice.value;
    var compVerpPrice = fm.prpLcomponentVerpCompPrice[serialNo].value;
   
     if ( parseFloat(standerPrice) < parseFloat(compVerpPrice) ){
          alert (" 核定价格不能高於系统市场价格");
          field.focus();
          return false;
      }
       
   }
  */
  
  function setVerpoFlag(field, serialNo) {
  	if (field.checked) {

  		fm.verpoFlag[serialNo].value = "1";
  	} else {
  		fm.verpoFlag[serialNo].value = "0";
  	}
  }
  </script>
<c:set var="valueUpper" value="0.00" scope="page" />
<c:if test="${param.nodeType=='verpo'}">
	<c:set var="valueUpper" value="${requestScope.valueUpper}" />
</c:if>
<c:set var="color" value="" />
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanComponent" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="Component">
					<thead>
						<tr>
							<td class="subformtitle" colspan=17>
								<s:text name="certainLoss.ProjectCosts" />
								<!--零部件更换项目费用清单-->
							</td>
						</tr>
						<tr>
							<!--td style="display:none" class="centertitle" style="width:0%"
                                rowspan="2">
                                	险别代码
                            </td-->
							<td class="centertitle" style="width: 15%" rowspan="2">
								<s:text name="db.prpLendor.kindName" />
								<!--险别名称-->
							</td>
							<td class="centertitle" style="width: 23%" rowspan="2">
								<s:text name="certainLoss.partName" />
								<!--部件名称-->
							</td>
							<td class="centertitle" style="width: 10%" rowspan="2">
								<s:text name="certainLoss.originalEncoding" />
								<!--原厂编码-->
							</td>
							<!--td colspan="3" class="centertitle" style="width:5%">
                                		中心报价
                            </td-->
							<td colspan="3" class="centertitle" style="width: 15%">
								<s:text name="certainLoss.localQuotes" />
								<!--本地报价-->
							</td>
							<!---<td class="centertitle" style="width:9%" rowspan="2">原上报价</td>--->
							<td class="centertitle" style="width: 5%; display: none" rowspan="2">
								<s:text name="certainLoss.repairQuotes" />
								<!--修理厂报价-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.lossPrice" />
								<!--定损单价-->
							</td>
							<td class="centertitle" style="width: 5%; display: none" rowspan="2">
								<s:text name="certainLoss.priceType" />
								<!--价格类型-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.numberReplacement" />
								<!--更换数量-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.whetherRecycling" />
								<!--是否回收-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="db.prpLpersonloss.sumRest" />
								<!--残值-->
							</td>
							<td rowspan="2" class="centertitle" style="width: 5%">
								<s:text name="certainLoss.subtotal" />
								<!--小计-->
							</td>
							<c:if test="${param.nodeType =='verpo' || param.nodeType =='verip'}">
								<td class="centertitle" style="width: 5%" rowspan="2">
									<s:text name="certainLoss.whetherQuotation" />
									<!--是否询价-->
								</td>
							</c:if>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="db.prpLcomponent.remark" />
								<!--备注-->
							</td>
							<td class="centertitle" style="width: 7%;" colspan=3 rowspan="2">&nbsp; &nbsp;</td>
						</tr>
						<tr>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.price" />
								<!--专修价-->
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.marketPrice" />
								<!--市场价-->
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.factoryPrice" />
								<!--副厂价-->
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<c:if test="${param.nodeType !='verif' && param.editType!='SHOW'}">
								<!-- <td colspan="15" align="center">
                                <input type="button" name=buttonGetFittings class=bigbutton
                                    value="从零配件定损系统导入"
                                    onclick="insertThreeRowTableComponent('Component','Component_Data',this);setFlag0('Component',this);"
                                    onclick="getFittingsInfo('Compent');" style="cursor: hand">
                                &nbsp;&nbsp; </td> -->
								<td colspan=17>
									<table class="common" cellspacing="1" cellpadding="0">
										<tbody>
											<tr>
												<td colspan="15" align="left">
													<s:text name="prompt.certainLoss.addRemoveChange" />
												</td>
												<!--(按"+"号键增加换件信息，按"-"号键删除信息)-->
												<td class="title" align="right" style="width: 4%">
													<div align="center">
														<input type="button" value="+" class=smallbutton onclick="insertThreeRowTableComponent('Component','Component_Data',this);setFlag0('Component',this);" name="buttonGetFittings"
															style="cursor: hand">
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</c:if>
						</tr>
						<tr>
							<td colspan="15">
								<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" width="100%">
									<tr style="display: none">
										<td class='title' style="width: 2%"></td>
										<td class='title' style="width: 8%" align="right">
											<s:text name="certainLoss.freight" />:
											<!--运费-->
										</td>
										<td class='input' style="width: 8%">
											<input name="prpLcarLossSumTransFee" class="input" style='width: 80px' value="<fmt:formatNumber value='${pageScope.prpLcarLoss.sumTransFee}' pattern='#'/>" onBlur="sumComponentFee();">
										</td>
										<td class='title' style="width: 5%" align="right">
											<!-- 税金: -->
										</td>
										<td class='input' style="width: 12%">
											<input name="prpLcarLossSumTax" class="readonly" readonly style='width: 80px' value="${pageScope.prpLcarLoss.sumTax}" onBlur="sumComponentFee();" style="display:none">
										</td>
										<td class='title' style="width: 12%" align="right">
											<s:text name="certainLoss.managementFee" />:
											<!--管理费-->
										</td>
										<td class='input' style="width: 12%">
											<input name="prpLcarLossSumManager" class="input" style='width: 80px' value="${pageScope.prpLcarLoss.sumManager}" onBlur="sumComponentFee();">
											%
										</td>
										<td class='title' style="width: 24%" align="right"></td>
										<td class='input' style="width: 17%" colspan=8>
											<input type="hidden" name="prpLcarLossSumVeriManager" class="input" style='width: 80px' value="${pageScope.prpLcarLoss.sumVeriManager}">
										</td>
									</tr>
									<tr>
										<td class='title' style="width: 2%"></td>
										<td class='title' style="width: 12%" align="right">
											<s:text name="certainLoss.TotalResiduals" />:
											<!--残值合计-->
										</td>
										<input type="hidden" name="selectCarFittings">
										<td class='input' style="width: 12%">
											<input name="prpLcarLossSumRest" class="readonly" readonly style='width: 80px' value="<fmt:formatNumber value='${pageScope.prpLcarLoss.sumRest}' pattern='#'/>" onBlur="sumComponentFee1(this);">
										</td>
										<td class='title' style="width: 5%" align="right"></td>
										<td class='title' style="width: 8%" align="right"></td>
										<td class='title' style="width: 16%" align="right">
											<s:text name="certainLoss.changeTotals" />:
											<!--换件费合计-->
										</td>
										<td class='input' style="width: 12%">
											<input name="SumDefLoss2" class="readonly" readonly style='width: 80px'>
										</td>
										<!---<td class='title'  style="width:17%" align="right">核定换件费合计:</td>--->
										<td class='title' style="width: 17%" align="right"></td>
										<td class='input' style="width: 17%" colspan=8>
											<input name="SumVerifyLoss2" type="hidden" class="readonly" readonly style='width: 80px' value="${pageScope.prpLcarLoss.sumVerifyLoss }">
										</td>
										<input type=hidden class='readonly' class="input" readonly="true" style='width: 80px' name='SumManHourFee2'>
										<input type=hidden class='readonly' readonly="true" class="input" style='width: 80px' name='SumMaterialFee2'>
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					<tbody id="componentTable">
						<c:set var="componentNo" value="0" scope="page" />
						<c:set var="compensatebackReadOnly1" value="" scope="page" />
						<c:set var="compensatebackDiasable1" value="" scope="page" />
						<c:set var="compensatebackStyle1" value="" scope="page" />
						<c:forEach items="${requestScope.prpLcomponent.componentList}" var="prpLcomponent">
							<c:if test="${prpLcomponent.id.lossItemCode==pageScope.prpLcarLoss.id.lossItemCode}">
								<c:set var="componentNo" value="${prpLcomponent.id.lossItemCode}" scope="page" />
								<c:set var="compensatebackReadOnly1" value="" scope="page" />
								<c:set var="compensatebackDiasable1" value="" scope="page" />
								<c:set var="compensatebackStyle1" value="" scope="page" />
								<c:if test="${prpLcomponent.compensateBackFlag == '1'}">
									<%--//如果是由理算退回的，那么这行记录就应该显示得是只读的 --%>
									<c:set var="compensatebackReadOnly1" value="readOnly" scope="page" />
									<c:set var="compensatebackDiasable1" value="disabled" scope="page" />
									<c:set var="compensatebackStyle1" value="" scope="page" />
								</c:if>
								<tr>
									<td class="input" style="display: none">
										<input type="hidden" name="carLossComponentLossItemCode" style="width: 20px" value="${prpLcomponent.id.lossItemCode-1 }">
										<input type="hidden" name="prpLcomponentVeriSumDefLoss" value="<fmt:formatNumber value='${prpLcomponent.sumVeriLoss }' pattern='#'/>">
										<input type="hidden" name="prpLcomponentVeriVerpCompPrice" value="${prpLcomponent.sumVeriLoss }">
										<input type="text" name="prpLcomponentKindCode" class="codecode" style="width: 100%; ${pageScope.compensatebackStyle1}" <c:out value="${pageScope.compensatebackReadOnly1}"/>
											value="${prpLcomponent.kindCode}"
											<c:if test="${pageScope.compensatebackReadOnly1==''}">
                                           ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                           onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                        </c:if>>
									</td>
									<td class="input" style="width: 10%;">
										<input type="text" name="prpLcomponentKindName" class="codecode" style="width: 100%; ${pageScope.compensatebackStyle1}" <c:out value="${pageScope.compensatebackReadOnly1}"/>
											value="${prpLcomponent.kindName}"
											<c:if test="${empty pageScope.compensatebackReadOnly1}">
                                           <c:choose>
                                              <c:when test="${pageScope.prpLcarLoss.id.lossItemCode =='1'}">
                                                ondblclick="code_CodeSelect(this,'PolicyKindCodeForMainCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                              onkeyup="code_CodeSelect(this,'PolicyKindCodeForMainCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                              </c:when>
                                              <c:otherwise>
                                               ondblclick = "code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                               onkeyup = "code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"                            
                                              </c:otherwise>
                                           </c:choose>
                                    </c:if>>
									</td>
									<td class="input" style="width: 23%;">
										<input name="prpLcomponentCompCode" type='hidden' value="${prpLcomponent.compCode}">
										<%--//自定义配件红色字体表示--%>
										<c:choose>
											<c:when test="${fn:length(fn:trim(prpLcomponent.flag))>0 && fn:startsWith(fn:trim(prpLcomponent.flag), '1')}">
												<input name="prpLcomponentCompName" class="common" maxlength="100" style="width: 100%; color: '#FF0000' ${pageScope.compensatebackStyle1}"
													<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.compName}">
											</c:when>
											<c:otherwise>
												<input name="prpLcomponentCompName" class="common" maxlength="100" style='width: 100%; ${pageScope.compensatebackStyle1}' <c:out value="${pageScope.compensatebackReadOnly1}"/>
													value="${prpLcomponent.compName}">
											</c:otherwise>
										</c:choose>
										<%--//大於核价人的  权限的标记除特殊颜色--%>
										<c:choose>
											<c:when test="${param.nodeType=='verpo' && (prpLcomponent.verpCompPrice/prpLcomponent.quantity) > pageScope.valueUpper}">
												<c:set var="color" value=" ;color:'#008000' " />
											</c:when>
											<c:otherwise>
												<c:set var="color" value=" ;" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="input" style="width: 10%;">
										<input type="text" name="prpLcomponentOriginalId" class="common" style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.originalId}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentSys4SPrice" class="common" style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.sys4SPrice}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentSysMarketPrice" type="hidden" class="common" readonly style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.sysMarketPrice}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentSysMatchPrice" type="hidden" class="common" readonly style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.sysMatchPrice}">
									</td>
									<td class="input" style="width: 4%">
										<input name="prpLcomponentNative4SPrice" class="common" style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="<fmt:formatNumber value='${prpLcomponent.native4SPrice}' pattern='#'/>">
									</td>
									<td class="input" style="width: 3%">
										<input name="prpLcomponentNativeMarketPrice" class="common" style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="<fmt:formatNumber value='${prpLcomponent.nativeMarketPrice}' pattern='#'/>">
									</td>
									<td class="input" style="width: 3%">
										<input name="prpLcomponentNativeMatchPrice" class="common" style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="<fmt:formatNumber value='${prpLcomponent.nativeMatchPrice}' pattern='#'/>">
									</td>
									<input name="prpLcomponentFirstMaterialFee" type="hidden" class=readonly readonly style='width: 100%;'>
									<td class="input" style="display: none">
										<input name="prpLcomponentRepairFactoryFee" class=common style='width: 100%;' maxlength=10 value="<fmt:formatNumber value='${prpLcomponent.repairFactoryFee}' pattern='#'/>">
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentMaterialFee" class=common maxlength=10 style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="<fmt:formatNumber value='${prpLcomponent.materialFee}' pattern='#'/>" onBlur="getSumDefLoss(this,2);setFirstMaterialFee(this);">
									</td>
									<td class="input" style="display: none">
										<select name="prpLcomponentPriceType" class="three" onchange="changePriceType(this);" style="width: 60px">
											<option value="S" <c:if test="${fn:trim(prpLcomponent.priceType)=='S'}"><c:out value="selected"/></c:if>>
												<s:text name="certainLoss.price" />
												<!--专修价-->
											</option>
											<option value="M" <c:if test="${fn:trim(prpLcomponent.priceType)=='M'}"><c:out value="selected"/></c:if>>
												<s:text name="certainLoss.marketPrice" />
												<!--市场价-->
											</option>
											<option value="O" <c:if test="${fn:trim(prpLcomponent.priceType)=='O'}"><c:out value="selected"/></c:if>>
												<s:text name="certainLoss.factoryPrice" />
												<!--副厂价-->
											</option>
										</select>
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentQuantity" class="common" style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.quantity}" onBlur="return getSumDefLoss(this,2);">
									</td>
									<td class="input" align="center" style="width: 5%;">
										<c:set var="tempSelectedValue" value="${prpLcomponent.ifRemain}" />
										<s:select name="prpLcomponentIfRemain" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.ifRemainList" style='width: 100%' onChange="return sumComponentFee();" />
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentRestFee" class="common" style='width: 100%;' value="<fmt:formatNumber value='${prpLcomponent.restFee}' pattern='#'/>" onBlur="return sumComponentFee();">
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentSumDefLoss" class=common style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="<fmt:formatNumber value='${prpLcomponent.sumDefLoss}' pattern='#'/>" onchange="checkSumDefLoss(this);"
											onBlur="getSumDefLoss(this,2);setFirstMaterialFee(this);">
										<input name="prpLcomponentVerpCompPrice" type="hidden" class="common" <c:out value='${pageScope.color}'/>
											<c:out value='${pageScope.compensatebackStyle1}'/>"
                                        onBlur="return getSumDefLossVerify(this,2);"
											onchange=" return checkVerpCompPrice(${prpLcomponent.id.serialNo});" value="${prpLcomponent.verpCompPrice}">
										<input name="prpLcomponentVerpCompPriceLast" type="hidden" value="${prpLcomponent.verpCompPrice}">
									</td>
									<c:if test="${param.nodeType =='verpo' || param.nodeType =='verip'}">
										<td class="input">
											<input name="verpoFlagSelect" type=checkbox <c:if test="${prpLcomponent.verpoFlag =='1'}"><c:out value="checked"/></c:if>
												<c:if test="${param.nodeType =='verpo'}"><c:out value="disabled"/></c:if> onClick="return setVerpoFlag(this,'${prpLcomponent.id.serialNo}');">
										</td>
									</c:if>
									<td class="input" style="width: 5%;">
										<input name="verpoFlag" type="hidden" value="${prpLcomponent.verpoFlag}">
										<input name="prpLcomponentRemark" maxlength=60 class=common style="width: 100%; <c:out value='${pageScope.color}'/><c:out value='${pageScope.compensatebackStyle1}'/>"
											<c:out value="${pageScope.compensatebackReadOnly1}"/> value="${prpLcomponent.remark}">
										<input name="prpLcomponentFlag" type=hidden value="${prpLcomponent.flag}">
										<input name="prpLcomponentIndId" type=hidden value="${prpLcomponent.indId}">
									</td>
									<input type="hidden" name="prpLcomponentSerialNo" value="${prpLcomponent.id.serialNo}">
									<input type="hidden" name="prpLcomponentItemKindNo" value="${prpLcomponent.itemKindNo}">
									<input type="hidden" name="prpLcomponentLossItemCode" value="${prpLcomponent.id.lossItemCode}">
									<input type="hidden" name="prpLcomponentLicenseNo" value="${prpLcomponent.licenseNo}">
									<input type="hidden" name="prpLcomponentLicenseColorCode" value="${prpLcomponent.licenseColorCode}">
									<input type="hidden" name="prpLcomponentCarKindCode" value="${prpLcomponent.carKindCode}">
									<input type="hidden" name="prpLcomponentMakeYear" value="${prpLcomponent.makeYear}">
									<input type="hidden" name="prpLcomponentGearboxType" value="${prpLcomponent.gearboxType}">
									<input type="hidden" name="prpLcomponentQuoteCompanyGrade" value="${prpLcomponent.quoteCompanyGrade}">
									<input type="hidden" name="prpLcomponentManageFeeRate" value="${prpLcomponent.manageFeeRate}">
									<input type="hidden" name="prpLcomponentRepairFactoryCode" value="${prpLcomponent.repairFactoryCode}">
									<input type="hidden" name="prpLcomponentRepairFactoryName" value="${prpLcomponent.repairFactoryName}">
									<input type="hidden" name="prpLcomponentHandlerCode" value="${prpLcomponent.handlerCode}">
									<input type="hidden" name="prpLcomponentRepairStartDate" value="${prpLcomponent.repairStartDate}">
									<input type="hidden" name="prpLcomponentRepairEndDate" value="${prpLcomponent.repairEndDate}">
									<input type="hidden" name="prpLcomponentSanctioner" value="${prpLcomponent.sanctioner}">
									<input type="hidden" name="prpLcomponentApproverCode" value="${prpLcomponent.approverCode}">
									<input type="hidden" name="prpLcomponentOperatorCode" value="${prpLcomponent.operatorCode}">
									<input type="hidden" name="prpLcomponentQueryPrice" value="${prpLcomponent.queryPrice}">
									<input type="hidden" name="prpLcomponentQuotedPrice" value="${prpLcomponent.quotedPrice}">
									<input type="hidden" name="prpLcomponentPartCode" value="${prpLcomponent.partCode}">
									<input type="hidden" name="prpLcomponentPartName" value="${prpLcomponent.partName}">
									<input type="hidden" name="prpLcomponentManHourFee" value="${prpLcomponent.manHourFee}">
									<input type="hidden" name="prpLcomponentBackCheckRemark" value="${prpLcomponent.backCheckRemark}">
									<input type="hidden" name="prpLcomponentLossRate" value="${prpLcomponent.lossRate}">
									<input type="hidden" name="prpLcomponentCurrency" value="${prpLcomponent.currency}">
									<input type="hidden" name="prpLcomponentVeriRemark" value="${prpLcomponent.remark}">
									<input type="hidden" name="prpLcomponentVeriQuantity" value="${prpLcomponent.veriQuantity}">
									<input type="hidden" name="prpLcomponentVeriMaterFee" value="<fmt:formatNumber value='${prpLcomponent.veriMaterFee}' pattern='#'/>">
									<input type="hidden" name="prpLcomponentVeriManHourFee" value="${prpLcomponent.veriManHourFee}">
									<input type="hidden" name="prpLcomponentVeriLossRate" value="${prpLcomponent.veriLossRate}">
									<input type="hidden" name="prpLcomponentVeriRestFee" value="<fmt:formatNumber value='${prpLcomponent.veriRestFee}' pattern='#'/>">
									<input type="hidden" name="prpLcomponentCompensateBackFlag" value="${prpLcomponent.compensateBackFlag}">
									<c:choose>
										<c:when test="${!(param.status == '3')}">
											<td class="input" style='width: 7%;' align="center">
												<div>
													<input type=button name="buttonComponentDelete" class=smallbutton onclick="deleteRow(this,'Component');sumComponentFee();" value="-" style="cursor: hand">
												</div>
											</td>
								</tr>
								</c:when>
								<c:otherwise>
									<td class="input" style='width: 7%; display: none' align="center">
										<div>
											<input type="button" name="buttonComponentDelete" class="smallbutton" <c:out value="${pageScope.compensatebackDiasable1}"/>
												onclick="deleteRowTableComponent(this,'Component',1,2);sumComponentFee();" value="-" style="cursor: hand">
										</div>
									</td>
									</tr>
									<tr>
										<td class="input" colspan="3" style="width: 48%">
											<s:text name="certainLoss.nuclearDamage" />:
											<!--核损意见-->
										</td>
										<c:set var="strVeriMindColor" value="black" scope="page" />
										<c:if test="${prpLcomponent.veriMaterFee != prpLcomponent.materialFee}">
											<c:set var="strVeriMind" value="價格異議" scope="page" />
											<c:set var="strVeriMindColor" value="red" scope="page" />
										</c:if>
										<td class="input" style="width: 4%">
											<font color="${pageScope.strVeriMindColor}"><c:out value="${pageScope.strVeriMind}" /></font>
										</td>
										<td class="input" style="width: 3%"></td>
										<td class="input" style="width: 3%"></td>
										<td class="input" style="width: 5%">
											<input name="prpLcomponentVeriMaterFee" class="readonly" readonly" style='width: 100%' value="<fmt:formatNumber value='${prpLcomponent.veriMaterFee}' pattern='#'/>" onBlur="">
										</td>
										<td class="input" style="width: 5%">
											<input name="prpLcomponentVeriQuantity" class="readonly" readonly style='width: 100%' value="${prpLcomponent.veriQuantity}">
										</td>
										<td class="input" colspan="1" style="width: 5%">&nbsp;</td>
										<td class="input" style="width: 5%">
											<input name="prpLcomponentVeriRestFee" class="readonly" readonly class="input" style='width: 100%' value="<fmt:formatNumber value='${prpLcomponent.veriRestFee}' pattern='#'/>" onBlur="">
										</td>
										<td class="input" style="width: 5%">
											<input name="prpLcomponentVeriSumDefLoss" class="readonly" readonly" style='width: 100%' value="<fmt:formatNumber value='${prpLcomponent.sumVeriLoss}' pattern='#'/>" onBlur="">
											<input type="hidden" name="prpLcomponentVeriVerpCompPrice" class="readonly" readonly style='width: 100%' value="${prpLcomponent.sumVeriLoss}">
										</td>
										<td class="input" colspan="2" style="width: 12%">
											<input name="prpLcomponentVeriRemark" class="readonly" readonly style='width: 100%' value="${prpLcomponent.veriRemark}">
										</td>
										<td class="input" style='width: 7%; display: none' align="center">
											<div>
												<input type="button" name="buttonComponentDelete" class="smallbutton" <c:out value="${pageScope.compensatebackDiasable1}"/>
													onclick="deleteRowTableComponent(this,'Component',1,2);sumComponentFee();" value="-" style="cursor: hand">
											</div>
										</td>
									</tr>
								</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
<c:choose>
	<c:when test="${param.editType=='EDIT'}">
		<input type="hidden" name="clickComponentFlag" value="true">
	</c:when>
	<c:otherwise>
		<input type="hidden" name="clickComponentFlag" value="false">
	</c:otherwise>
</c:choose>