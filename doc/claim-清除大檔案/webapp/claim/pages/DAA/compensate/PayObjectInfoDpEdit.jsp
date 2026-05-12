<%--
**************************************************************************
mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
**************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.schema.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ page import="ins.framework.utils.DataUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START -->
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoBank.js"></script>
<script src="${ctx}/claim/common/js/jquery-1.7.2.min.js"></script>
<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END -->
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAAPersonLossEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAAlLossEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEditDwr.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEditNew.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateCertainLoss.js"></script>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script language="JavaScript">



  </SCRIPT>
<script type="text/javascript">
		//mpc调整
		$(function(){
		     initWindow();
	         $(window).resize(function(){
				initWindow();
	         });
		})
   </script>
	<script type="text/javascript">
		$(document).ready(function(){
			var inputStatus = '${inputStatus}';
			if(inputStatus=='1'){//審核狀態
				var modifyColumn = (fm.modifyColumn.value+"").split(",");
				$('input[name="changeCheckBox"]').each(function(){
					if($(this).is(':checked')){
						var parentTr = $(this).closest('tr[name="PrpLpayObjectInfo"]');
						if(fm.modifyColumn.value.indexOf('OWNERNAME')!=-1){
							var ownerNameInput = parentTr.find('input[name="prpLpayObjectInfoOwnerName"]');
							ownerNameInput.prop('disabled', false);
							ownerNameInput.attr('style', 'color: red; font-weight: bold;');
						}
						if(fm.modifyColumn.value.indexOf('UNIFORMNO')!=-1){
							var uniformInput = parentTr.find('input[name="prpLpayObjectInfoUniformNo"]');
							uniformInput.prop('disabled', false);
							uniformInput.attr('style', 'color: red; font-weight: bold;');
						}
						if(fm.modifyColumn.value.indexOf('ACCOUNTCODE')!=-1){
							var accountInput = parentTr.find('input[name="prpLpayObjectInfoAccountCode"]');
							accountInput.prop('disabled', false);
							accountInput.attr('style', 'color: red; font-weight: bold;');
						}
						//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
						var buttonAddAccButton = parentTr.find('input[name="buttonAddAcc"]');
						if(fm.modifyColumn.value.indexOf('BANKCODE')!=-1){
							var bankCodeInput = parentTr.find('input[name="prpLpayObjectInfoBankCode"]');
							bankCodeInput.prop('disabled', false);
							bankCodeInput.attr('style', 'color: red; font-weight: bold;');
							buttonAddAccButton.prop('disabled', false);
						}

						if(fm.modifyColumn.value.indexOf('BANKNAME')!=-1){
							var bankNameInput = parentTr.find('input[name="prpLpayObjectInfoBankName"]');
							bankNameInput.prop('disabled', false);
							bankNameInput.attr('style', 'color: red; font-weight: bold;');
							buttonAddAccButton.prop('disabled', false);
						}
						if(fm.modifyColumn.value.indexOf('CUSTOMBANKCODE')!=-1){
							var castomBankCodeInput = parentTr.find('input[name="prpLpayObjectInfoCustomBankCode"]');
							castomBankCodeInput.prop('disabled', false);
							castomBankCodeInput.attr('style', 'color: red; font-weight: bold;');
							buttonAddAccButton.prop('disabled', false);
						}

						if(fm.modifyColumn.value.indexOf('CUSTOMBANKNAME')!=-1){
							var castomBankNameInput = parentTr.find('input[name="prpLpayObjectInfoCustomBankName"]');
							castomBankNameInput.prop('disabled', false);
							castomBankNameInput.attr('style', 'color: red; font-weight: bold;');
							buttonAddAccButton.prop('disabled', false);
						}
						//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
					}
				});
			}
		});

		function changeCheckBox(index){
			$('input[name="changeCheckBox"]').each(function(){
				$('input[name="changeCheckBox"]:checkbox').removeAttr("checked");
			});
			$("#changeCheckBox"+index).prop("checked", "checked");
		}
		
		function submitCheck(){
			fm.action ="/claim/payObjectInfoCheck.do";
			
			var inputStatus = '${inputStatus}';
			var changeCount = -1;
			$('input[name="changeCheckBox"]').each(function(){
				if($(this).is(':checked')){
					changeCount+=1;
					var parentTr = $(this).closest('tr[name="PrpLpayObjectInfo"]');
					var ownerNameInput = parentTr.find('input[name="prpLpayObjectInfoOwnerName"]');
					if(ownerNameInput.val()!=ownerNameInput.attr('title')){
						changeCount+=1;
					}
					var uniformNoInput = parentTr.find('input[name="prpLpayObjectInfoUniformNo"]');
					if(uniformNoInput.val()!=uniformNoInput.attr('title')){
						changeCount+=1;
					}
					var accountCodeInput = parentTr.find('input[name="prpLpayObjectInfoAccountCode"]');
					if(accountCodeInput.val()!=accountCodeInput.attr('title')){
						changeCount+=1;
					}
					
					//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
					var bankCodeInput = parentTr.find('input[name="prpLpayObjectInfoBankCode"]');
					if(bankCodeInput.val()!=bankCodeInput.attr('title')){
						changeCount+=1;
					}
					var bankCodeInput = parentTr.find('input[name="prpLpayObjectInfoBankName"]');
					if(bankCodeInput.val()!=bankCodeInput.attr('title')){
						changeCount+=1;
					}
					var customBankCodeInput = parentTr.find('input[name="prpLpayObjectInfoCustomBankCode"]');
					if(customBankCodeInput.val()!=customBankCodeInput.attr('title')){
						changeCount+=1;
					}
					var customBankNameInput = parentTr.find('input[name="prpLpayObjectInfoCustomBankName"]');
					if(customBankNameInput.val()!=customBankNameInput.attr('title')){
						changeCount+=1;
					}
					//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
				}
			});
			if(changeCount == -1){
				alert("請勾選要修改的資料!");
				return false;
			}else{
				if(changeCount == 0){
					alert("勾選的項目無任何修改的資料!");
					return false;
				}
			}
			fm.submit();
			return true;
			
		}
		function submitPass(){
			fm.action ="/claim/payObjectInfoPass.do";
			
			fm.submit();
		}
		function submitReject(){
			fm.action ="/claim/payObjectInfoReject.do";
			fm.submit();
		}
		function submitCheck2(index){
			changeCheckBox(index);//不知道為何  直接呼叫失效 改用跳一層呼叫
		}

	</script>
</head>
<%
	//防止重复提交
	session.setAttribute("oldCompensateLastAccessedTime", "");
	String editType = request.getParameter("editType");
	UserDto user = (UserDto) session.getAttribute("user");
%>
<c:choose>
	<c:when test="${param.editType=='SHOW'}">
		<body class="interface" onload="initPage();initSet();initSet1();readonlyAllInput();oMPC.style.visibility='visible';">
	</c:when>
	<c:when test="${param.editType=='EDIT'}">
		<body class="interface" onload="initPage();initSet();initSet1();oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body class="interface" onload="initPage();initSet();initSet1();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<form name=fm action="/claim/payObjectInfoReject.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
<DIV id="mainLayer" class="mainLayer">
	<input type="hidden" name="inputStatus" value="${inputStatus}">
	<input type="hidden" name="totalCount" value="${fn:length(requestScope.prpLpayObjectInfo.prpLpayObjectInfoList)}" />
	<input type="hidden" name="modifyColumn" value="${modifyColumn}">
	<input type="hidden" name="logId" value="${logId}">
	<input type="hidden" name="inputLvMax" value="${inputLvMax}">
	<input type="hidden" name="reviewLvMax" value="${reviewLvMax}">
	<input type="hidden" name="reviewOfPower" value="${reviewOfPower}">
	<input type="hidden" name="nodeType" value="compe">
	<input type="hidden" name="editType" value="${editType}">
	<input type="hidden" name="compensateNo" value="${compensateNo}">
	<input type="hidden" name="riskCode" value="${riskCode}">
	
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
		
			<%--赔付信息--%>
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%-- 賠付對象信息
					<%@include file="/pages/DAA/compensate/DAACompensatePayObject.jsp"%> --%>
					
					<!-- ADD START from :\claim\webapp\claim\pages\DAA\compensate\DAACompensatePayObject.jsp-->
						
						<table class="common" align="center">
							<tr>
								<td class="common">
									<b>賠款給付對象訊息</b>&nbsp;(${compensateNo})&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<s:set var="prpLpayObjectInfoPaycodeType" value="" scope="page" />
									<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList!=null&&#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()>0">
										<s:set var="prpLpayObjectInfoPaycodeType" value="#attr.prpLpayObjectInfo.prpLpayObjectInfoList.get(0).paycodeType" scope="page" />
									</s:if>
									
									<span id="spanPayAccountInfo" style="display: ">
										<table class="common" align="center" cellspacing="1" cellpadding="0">
											<thead>
												<tr>
													<td class="centertitle" colspan=2>賠付對象訊息</td>
												</tr>
											</thead>
											<tfoot>
												<tr>
												</tr>
											</tfoot>
											<tbody id="PayAccountInfo">
												<c:forEach items="${requestScope.prpLpayObjectInfo.prpLpayObjectInfoList}" var="prpLpayObject" varStatus="stat">
													<input type="hidden" name="pai_index" value="${stat.index}">
													<input type="hidden" name="ownerNameOrg" value="${prpLpayObject.ownerName}"><!-- 賠付對象 -->
													<input type="hidden" name="uniformNoOrg" value="${prpLpayObject.uniformNo}"><!-- 統一編號/身份證號 -->
													<input type="hidden" name="accountCodeOrg" value="${prpLpayObject.accountCode}"><!-- 匯款帳號 -->
													<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START -->
													<input type="hidden" name="bankCodeOrg" value="${prpLpayObject.bankCode}"><!-- 總行代號 -->
													<input type="hidden" name="bankNameOrg" value="${prpLpayObject.bankName}"><!-- 總行名稱 -->
													<input type="hidden" name="customBankCodeOrg" value="${prpLpayObject.customBankCode}"><!-- 分行代號 -->
													<input type="hidden" name="customBankNameOrg" value="${prpLpayObject.customBankName}"><!-- 分行名稱 -->
													<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END -->
													<tr name="PrpLpayObjectInfo">
														<td class="input" style="width: 4%;">
															<input type="checkbox" id="changeCheckBox${prpLpayObject.id.serialNo}" name="changeCheckBox" ${prpLpayObject.id.serialNo == changeCheckBoxNumber?'checked':''} onclick="submitCheck2(${prpLpayObject.id.serialNo});" value="${stat.index}">
														</td>
														<td class="subformtitle" style="width: 96%">
															<table class="common" style="width: 100%">
																<tr>
																	<td class="input" colspan="6">
																		<b>賠付對象&nbsp;<span name="payObjectIndex"><c:out value="${prpLpayObject.id.serialNo}" /></span></b>
																		<input type="hidden" name="prpLpayObjectInfoSerialNo" value="<c:out value="${prpLpayObject.id.serialNo}"/>">
																		<input type="hidden" name="prpLpayObjectInfoCertiType" value="<c:out value="${prpLpayObject.id.certiType}"/>">
																		<c:if test="${stat.index==0}">
																			<div name="payObject">
																				<font color="red">請注意: 若賠付對象為法人者, 請在“統一編號” 欄位輸入該公司之八碼統一編號, 若賠付對象為個人者, 請在“統一編號” 欄位輸入該人員之十碼個人身份證字號</font>
																			</div>
																		</c:if>
																	</td>
																</tr>
																<tr>
																	<td class="input" style="width: 15%">賠款支付方式：</td>
																	<td class="input" style="width: 18%" >
																		<select name="prpLpayObjectInfoOwnerShip" onchange="payObjectInfoOwnerShip(this)" disabled="disabled">
																			<option value="B" <c:if test="${pageScope.prpLpayObject.ownerShip=='B'}"><c:out value="selected"/></c:if>>
																				<s:text name="compensate.remittance" />
																			</option>
																			<!-- 汇款 -->
																			<option value="Q" <c:if test="${pageScope.prpLpayObject.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
																				<s:text name="compensate.agentInfo.cheque" />
																			</option>
																			<!-- 支票 -->
																			<!--<option value="C" <c:if test="${pageScope.chargedtox.ownerShip=='C'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cash"/></option> -->
																			<!-- 现金 -->
																		</select>
																	</td>
																	<td class="input" style="width: 15%">理賠金額：</td>
																	<!-- 费用支付方式 -->
																	<td class="input" style="width: 18%">
																		<input name="prpLpayObjectInfoPayAmount" type="text" readonly class="readonly" disabled="disabled" maxlength="8" style="width: 80px" value="<fmt:formatNumber value="${pageScope.prpLpayObject.payAmount}" pattern="#"/>"
																			onfocus="cacheData(this);" onblur="validateMoney(this);" title="理賠金額">
																		<img src="${ctx}/images/bgMarkMustInput.jpg">
																	</td>
																	<td class="input" style="width: 15%">洗錢狀態回覆：</td>
																	<td class="input" style="width: 18%">
																		<input name="prpLpayObjectInfoAMLFlag" readonly type="text" class="readonly" disabled="disabled" maxlength="8" style="width: 80px" value="<c:out value="${prpLpayObject.amlFlag}"/>">
																	</td>
																</tr>
																<tr>
																	<td class="input" style="width: 15%">賠付對象：</td>
																	<td class="input" style="width: 18%">
																		<input name="prpLpayObjectInfoOwnerName" title="${prpLpayObject.ownerName}" class="${inputStatus == 1?'readonly':'input'}" ${inputStatus == 1?'disabled="disabled"':''} maxlength="50" value="<c:out value="${prpLpayObject.ownerName}"/>">
																		<img src="${ctx}/images/bgMarkMustInput.jpg">
																	</td>
																	<td class="input" style="width: 15%">費用類型：</td>
																	<td class="input" style="width: 18%">
																		<c:set var="tempSelectedValue" value='${prpLpayObject.paymentKind}' />
																		<s:select name="prpLpayObjectInfoPaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" value="#attr.tempSelectedValue"  disabled="true"></s:select>
																		<img src="${ctx}/images/bgMarkMustInput.jpg">
																	</td>
																	<td class="input" style="width: 15%">證件類型：</td>
																	<td class="input" style="width: 18%">
																		<c:set var="tempCertificateCode" value='${prpLpayObject.certificateCode}' />
																		<s:select name="prpLpayObjectInfoCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" disabled="true"/>
																	</td>
																</tr>
																<tr>
																	<td class="input" style="width: 15%">統一編號/身份證號：</td>
																	<td class="input" style="width: 18%">
																		<input name="prpLpayObjectInfoUniformNo" title="${prpLpayObject.uniformNo}" class="${inputStatus == 1?'readonly':'input'}" ${inputStatus == 1?'disabled="disabled"':''} maxlength="25" value="<c:out value="${prpLpayObject.uniformNo}"/>">
																		<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																	</td>
																	<td class="input" style="width: 15%">受款人電話：</td>
																	<td class="input" style="width: 18%">
																		<c:out value="${prpLpayObject.beneficiaryPhone}"/>
																		<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																	</td>
																	<td class="input" style="width: 15%">
																		<span name="spanCutBack" <c:if test="${pageScope.prpLpayObject.ownerShip!='Q'}"> style="display: none;" </c:if>>禁背：</span>
																	</td>
																	<td class="input" style="width: 18%">
																		<span name="spanCutBack" <c:if test="${pageScope.prpLpayObject.ownerShip!='Q'}"> style="display: none;" </c:if>> <c:set var="tempSelectedValue" value='${prpLpayObject.cutBack}' />
																			<s:select name="prpLpayObjectInfoCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.tempSelectedValue" disabled="true" /></span>
																	</td>
																</tr>
																<tr name="bankInfo" <c:if test="${pageScope.prpLpayObject.ownerShip!='B'}">style="display: none;"</c:if>>
																	<td class="input" style="width: 15%">總行代號：</td>
																	<%-- 總行代號 --%>
																	<td class="input" style="width: 18%">
																		<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 -->
																		<input name="prpLpayObjectInfoBankCode" title="${prpLpayObject.bankCode}" value="<c:out value="${prpLpayObject.bankCode}"/>" class="${inputStatus == 1?'readonly':'input'}" readonly>
																	</td>
																	<td class="input" style="width: 15%">總行名稱：</td>
																	<%-- 總行名稱 --%>
																	<td class="input" style="width: 18%">
																		<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 -->
																		<input name="prpLpayObjectInfoBankName" title="${prpLpayObject.bankName}" value="<c:out value="${prpLpayObject.bankName}"/>" class="${inputStatus == 1?'readonly':'input'}" readonly>
																	</td>
																	<td class="input" style="width: 15%">匯款帳號：</td>
																	<%-- 银行帳号 --%>
																	<td class="input" style="width: 18%">
																		<input name="prpLpayObjectInfoAccountCode" title="${prpLpayObject.accountCode}" class="${inputStatus == 1?'readonly':'input'}" ${inputStatus==1?'disabled="disabled"':''} value="<c:out value="${prpLpayObject.accountCode}"/>">
																	</td>
																</tr>
																<tr name="bankInfo" <c:if test="${pageScope.prpLpayObject.ownerShip!='B'}">style="display: none;"</c:if>>
																	<td class="input" style="width: 15%">分行代號：</td>
																	<%-- 分行代號 --%>
																	<td class="input" style="width: 18%">
																		<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 -->
																		<input name="prpLpayObjectInfoCustomBankCode" title="${prpLpayObject.customBankCode}" value="<c:out value="${prpLpayObject.customBankCode}"/>" class="${inputStatus == 1?'readonly':'input'}" readonly>
																	</td>
																	<td class="input" style="width: 15%">分行名稱：</td>
																	<%-- 分行名稱 --%>
																	<td class="input" style="width: 18%">
																		<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 -->
																		<input name="prpLpayObjectInfoCustomBankName" title="${prpLpayObject.customBankName}" value="<c:out value="${prpLpayObject.customBankName}"/>" class="${inputStatus == 1?'readonly':'input'}" readonly>
																	</td>
																	<td class="input" style="width: 33%" colspan="2">
																		<!-- mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 -->
																		<input class='bigbutton' type='button' name='buttonAddAcc' value='總行分行代號查詢' onclick="BankEdit(this);" style="width: 180px;" 
																		<c:if test="${inputStatus == 1}"> style="display: none;" </c:if> >
																	</td>
																	<%-- 录入费用支付帳户信息 --%>
																</tr>
																<tr>
																	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
																	<!-- \webapp\claim\pages\DAA\compensate\DAACompensatePayObject.jsp -->
																	<td class="input" style="width: 15%">郵遞區號：</td>
																	<td class="input" style="width: 18%">
																		<c:out value="${prpLpayObject.areaCode}"/>
																		<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																	</td>
																	<td class="input" style="width: 15%">郵遞地址：</td>
																	<td class="input" style="width: 51%" colspan="3">
																		<c:out value="${prpLpayObject.courierAddress}"/>
																		<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																	</td>
																</tr>
															</table>
															
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
															
															<table cellpadding="0" cellspacing="0" id="buttonArea">
															<tr>
															<c:choose>
																<c:when test="${inputStatus==1 && reviewOfPower}">
																	<td>
																		<input type="hidden" name=buttonSaveType value="1">
																		<input type=button name=button1 class='button' value="審核通過" onclick="submitPass();">
																	</td>
																	<td>
																		<input type="hidden" name=buttonSaveType value="1">
																		<input type=button name=button2 class='button' value="下發修改" onclick="submitReject();">
																	</td>
																	
																</c:when>
																<c:when test="${inputStatus==1 && !reviewOfPower}">
																	<td>
																		<font color="red">等待主管審核中</font>
																	</td>
																</c:when>
																<c:otherwise>
																	<td>
																		<input type="hidden" name=buttonSaveType value="1">
																		<input type=button name=button3 class='button' value="提交高階" onclick="submitCheck();">
																	</td>
																	<td>
																		<!--返回按钮-->
																		<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="history.go(-1);">
																		&nbsp;&nbsp;
																	</td>
																</c:otherwise>
															</c:choose>
																</tr>
															</table>
									</span>
								</td>
							</tr>
						</table>
					
					
					
					<!-- ADD END -->
					
					
				</DIV>
			</CENTER>
	<%-- 保存通用按钮页面 --%>
	<TABLE id="btnCommon" class="common">
		<TR>
			<table cellpadding="0" cellspacing="0" id="buttonArea">
			<tr>
			<c:choose>
				<c:when test="${inputStatus==1 && reviewOfPower}">
					<td>
						<input type="hidden" name=buttonSaveType value="1">
						<input type=button name=button1 class='button' value="審核通過" onclick="submitPass();">
					</td>
					<td>
						<input type="hidden" name=buttonSaveType value="1">
						<input type=button name=button2 class='button' value="下發修改" onclick="submitReject();">
					</td>
					
				</c:when>
				<c:when test="${inputStatus==1 && !reviewOfPower}">
					<td>
						<font color="red">等待主管審核中</font>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						<input type="hidden" name=buttonSaveType value="1">
						<input type=button name=button3 class='button' value="提交高階" onclick="submitCheck();">
					</td>
					<td>
						<!--返回按钮-->
						<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="history.go(-1);">
						&nbsp;&nbsp;
					</td>
				</c:otherwise>
			</c:choose>
			</tr>
		</table>
		</TR>
	</TABLE>
	</form>
</DIV>
</form>
</body>
</html>
