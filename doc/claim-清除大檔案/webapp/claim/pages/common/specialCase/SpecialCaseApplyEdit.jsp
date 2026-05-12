<!--
****************************************************************************
* DESC       ：特殊赔案申请信息处理页面
* CREATEDATE ：2005-04-17
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<!--特殊赔案申请信息处理入口-->
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<!-- 标签页样式 -->
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script language=javascript>
function saveForm() {
	var context = fm.Context.value;
	var boxCheckFlag = '0';
	if (typeof document.all["haveClaimNo"] == "undefined") {
		errorMessage("特殊赔案申请之前必须立案!");
		fm.Context.focus();
		return false;
	} else {
		var claimNoBox = document.getElementsByName("claimNoBox");
		for (var i = 1; i < claimNoBox.length; i++) {
			if (fm.claimNoBox[i].checked) {
				boxCheckFlag = '1';
				break;
			}
		}
		if (boxCheckFlag == '0') {
			errorMessage("请选择赔案号!");
			return false;
		}
	}
	if (!checkList()) {
		fm.Context.focus();
		return false;
	}
	context1 = rightTrim(context);
	context1 = leftTrim(context1);
	if (context.length < 1) {
		errorMessage("特殊赔案申请原因不能为空!");
		fm.Context.focus();
		return false;
	}
	if (context.length > 500) {
		errorMessage("特殊赔案申请原因不能超过500个字符/汉字!");
		fm.Context.focus();
		return false;
	}
	fm.buttonSave.disabled = true;
	fm.submit();

}
/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("${ctx }/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName",
		"resizable=0,scrollbars=1,width=800,height=600");
}

function checkPrepay(obj) {
	if (typeof document.all["claimNoBox"] == "undefined") {
		errorMessage("特殊赔案申请之前必须立案!");
		return false;
	} else {
		var configCode = document.getElementsByName("configCode");
		var claimNoFlag = document.getElementsByName("claimNoFlag");
		for (var i = 1; i < configCode.length; i++) {
			if (i == parseInt(obj.value, 10)) {
				fm.claimNoFlag[i].value = '1';
			} else {
				fm.claimNoFlag[i].value = '0';
			}
			if (fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].value != "5") {
				if (i == parseInt(obj.value, 10) && configCode[i].value != 'RISKCODE_DAZ' && obj.checked) {
					errorMessage("商业保险不可进行" + fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].text + "申请!");
					return false;
				}
			}
			if (configCode[i].value == 'RISKCODE_DAZ' && obj.checked && fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].value == "5") {
				errorMessage("强制保险不可进行" + fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].text + "的申请!");
				return false;
			}
		}
	}
}

function checkList() {
	if (typeof document.all["claimNoBox"] != "undefined") {
		var configCode = document.getElementsByName("configCode");
		var claimNoBox = document.getElementsByName("claimNoBox");
		for (var i = 1; i < configCode.length; i++) {
			if (configCode[i].value != 'RISKCODE_DAZ' && fm.claimNoBox[i].checked && fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].value != "5") {
				errorMessage("商业保险不可进行" + fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].text + "申请!");
				return false;
			}
			if (configCode[i].value == 'RISKCODE_DAZ' && fm.claimNoBox[i].checked && fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].value == "5") {
				errorMessage("强制保险不可进行" + fm.specialCaseCaseType.options[fm.specialCaseCaseType.selectedIndex].text + "申请!");
				return false;
			}
		}
		return true;
	}
}

function getClaimNo() {
	var configCode = document.getElementsByName("configCode");
	var claimNoBox = document.getElementsByName("claimNoBox");
	for (var i = 1; i < configCode.length; i++) {
		if (fm.claimNoBox[i].checked) {
			fm.prpLclaimNo.value = fm.ClaimNo[i].value;
		}
	}
}
</script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
</head>
<body onload="initPage();oMPC.style.visibility='visible';" style="scroll: no; overflow: hidden;">
	<iframe name=CodeFrame src='${ctx}/common/pub/QueryCodeInputOverviewNone.jsp' style='DISPLAY: none; Z-INDEX: 100; POSITION: absolute' marginwidth='0' marginheight='0' hspace='0' vspace='0'
		frameborder='0' scrolling='no'></iframe>
	<form name=fm action="${ctx }/specailCaseSave.do" method="post" onsubmit="return validateForm(this);" onload="getClaimNo()">
		<s:token></s:token>
		<DIV id="mainLayer" class="mainLayer">
			<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
				<TR>
					<td align="left">
						<%--赔案处理记录  --%>
						<input type="button" name="prpLmessageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>"
							onclick="openWinSave(fm.RegistNo.value,fm.policyNo.value,fm.riskCode.value,'certi',fm.ClaimNo.value);">
						<%--电子单证  --%>
						<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.electronicDocuments.value'/>"
							onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo',fm.RegistNo.value,'certi');">
						<%--索赔清单  --%>
						<input type="button" name="buttonCertifyDirect" class="bigbutton" value="<s:text name='button.stateClaim.value'/>" onClick="doCertifyDirect(fm.RegistNo.value,'certi')">
					</td>
				</TR>
			</TABLE>
			<mpc:container ID="oMPC">
				<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<!-- 1.1.1.特殊赔案主信息 -->
							<%@include file="/pages/common/specialCase/SpecialCaseMainEdit.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
				<mpc:page ID="tabMain" TABTITLE="已有特殊賠案訊息" TABTEXT="已有特殊賠案訊息">
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%@include file="/pages/common/specialCase/SpecialCaseOldView.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
			<TABLE id="btnCommon" class="common">
				<TR>
					<TD align="center">
						<!-- 保存通用按钮 -->
						<%@include file="/pages/common/specialCase/SpecialCaseSave.jsp"%>
					</TD>
				</TR>
			</TABLE>
		</DIV>
	</form>
</body>
</html>
