<!--
****************************************************************************
* DESC       ：冲减保额主画面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-26
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title>'<s:text name="title.endcaseBeforeEdit.reduceInsured" /></title>
<!-- 冲减保额 -->
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/endcase/js/DAAEndcaseEdit.js"></script>
<%@include file="/common/meta_js.jsp"%>
<script language='javascript'>
	/**
	 @author 中科软
	 @description 校验窗体方法
	 @param       无
	 @return      boolean,合法返回true,不合法返回false
	 */
	function checkForm() {
		if (isEmpty(fm.ClaimNo)) {
			fm.ClaimNo.focus();
			errorMessage("赔案号不能为空!");
			return false;
		} else if (trim(fm.ClaimNo.value).length != 22) {
			fm.ClaimNo.focus();
			errorMessage("赔案号应为22位长!");
			return false;
		}

		if (!isEmpty(fm.CompensateNo)) {
			if (trim(fm.CompensateNo.value).length != 22) {
				fm.CompensateNo.focus();
				errorMessage("赔款计算书号应为22位长!");
				return false;
			}
		}

		return true;
	}

	/**
	 @author 中科软
	 @description 提交窗体方法
	 @param       无
	 @return      无
	 @see         checkForm
	 */
	function submitForm() {
		/*
		  if(checkForm()==true)
		  	{
		    fm.submit();
		  }
		 */
		fm.submit();
	}

	function resetForm() {
		fm.reset();
	}
</script>
</head>
<body class="interface" onload="initPage();">
	<!-- 调用loadForm 初始化页面 -->
<body class="interface" onload="initPage();">
	<form name=fm action="${ctx }/endor.do" method="post" onsubmit="return validateForm(this);">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					'
					<s:text name="endcase.insuredRegistered" />
					<!-- 冲减保额登记 -->
				</td>
			</tr>
			<tr>
				<td class="title">
					'
					<s:text name="check.claimNum" />
					：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<input type='hidden' name='RiskCode' value="${prpLloss.riskCode}">
					<input name="ClaimNo" class="readonly" readonly value="${claimNo }">
				</td>
				<td class="title">
					'
					<s:text name="prompt.queRegist.PolicyNo" />
					：
				</td>
				<!-- 保单号 -->
				<td class="input">
					<input name="PolicyNo" class="readonly" readonly value="${prpLloss.policyNo}">
				</td>
			</tr>
		</table>
		<table class="common" align=center>
			<!--冲减保额登记清单-->
			<tr class=mline>
				<td class="common" colspan="4" style="text-align: left">
					<table class="common" cellpadding="5" cellspacing="1">
						<tr>
							<td class="subformtitle">
								'
								<s:text name="endcase.reduceInformation" />
								<!-- 冲减保额信息 -->
							</td>
						</tr>
					</table>
					<span id="spanEndorAll">
						<table class=common cellpadding="5" cellspacing="1" id="Endor">
							<thead>
								<tr>
									<td class="centertitle" style="width: 20%">
										'
										<s:text name="db.prpLcfee.compensateNo" />
									</td>
									<!-- 赔款计算书号 -->
									<td class="centertitle" style="width: 10%">
										'
										<s:text name="db.prpLendor.itemCode" />
									</td>
									<!-- 标的项目类别 -->
									<td class="centertitle" style="width: 20%">
										'
										<s:text name="db.prpLendor.itemName" />
									</td>
									<!-- 标的项目名称 -->
									<td class="centertitle" style="width: 20%">
										'
										<s:text name="db.prpLendor.kindName" />
									</td>
									<!-- 险别名称 -->
									<td class="centertitle" style="width: 15%">
										'
										<s:text name="db.prpLendor.currency" />
									</td>
									<!-- 币别 -->
									<td class="centertitle" style="width: 15%">
										'
										<s:text name="menu.finishCase.amountTask" />
									</td>
									<!-- 冲减保额 -->
								</tr>
							</thead>
							<tfoot>
							</tfoot>
							<tbody>
								<c:if test="${prpLloss.prpLlossList!=null}">
									<c:forEach var="prpLlossTemp" items="${prpLloss.prpLlossList}">
										<tr class=common>
											<td>
												<input name=CompensateNo class="readonly" readonly style='width: 170px' value="${prpLlossTemp.id.compensateNo}">
											</td>
											<td>
												<input name="ItemCode" class="readonly" readonly style="width: 40px" value="${prpLlossTemp.itemCode}">
											</td>
											<td>
												<input name="ItemName" class="readonly" readonly style="width: 110px" value="${prpLlossTemp.lossName}">
											</td>
											<td>
												<input type='hidden' name="ItemKindNo" value="${prpLlossTemp.itemKindNo}">
												<input type='hidden' name="KindCode" value="${prpLlossTemp.kindCode}">
												<input type="input" name=KindName class="readonly" readonly style='width: 105px' value="${prpLlossTemp.kindName}">
											</td>
											<td>
												<input type='hidden' name="Currency" value="${prpLlossTemp.currency}">
												<input name="CurrencyName" class="readonly" readonly style="width: 65px" value="${prpLlossTemp.currencyName}">
											</td>
											<td>
												<input name="EndorAmount" class="readonly" readonly style="width: 90px" value="-${prpLlossTemp.sumRealPay}">
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</span>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SAVE">
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value=" <s:text name="button.next.value" /> " class="button" onclick="submitForm();">
					<!-- 下一步 -->
				</td>
				<td class="button">
					<input type="button" name="buttonCancel" value=" <s:text name="button.cancel.value" />" class="button" onclick="resetForm();">
					<!-- 取 消  -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
