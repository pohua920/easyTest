/*****************************************************************************
 * DESC       ：单证登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-07-05
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查单证登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {
	var content = $(":input[name='prpLcertifyCollectContent']").val();
	if(content.length>170){
		alert("案件處理意見字數("+content.length+")，請控制字數在170個以內。");
		return false;
	}
	return true;
}


/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitForm() {
	if (checkForm() == false) {
		return false;
	}
	fm.buttonSave.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 清除
 *@param       无
 *@return      通过返回true,否则返回false
 */

function resetForm() {
	if (window.confirm("確定要清除嗎？")) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}


/**
 *@description 查看制定的文件(下载功能)
 *@param       fileName，businessNo，uploadFileName
 */

function viewFile(fileName, businessNo) {

	var editType = fm.editType.value;

	window.open("/claim/common/certify/CertifyViewFile.jsp?fileName=" + fileName + "&businessNo=" + businessNo + "&editType=" + editType, businessNo, "resizable=0,scrollbars=1,width=800,height=600");
}

/**
 *@description 查看全部文件
 *@param       businessNo
 */

function viewFileAll(businessNo) {
	var editType = fm.editType.value;
	window.open("/claim/common/certify/CertifyViewFile.jsp?businessNo=" + businessNo + "&editType=" + editType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}

/**
 *@description 查看全部文件
 *@param       businessNo
 */

function doViewFile(directType) {
	var editType = fm.editType.value;
	var businessNo = fm.prpLcertifyCollectBusinessNo.value;
	var nodeTypeUpload = fm.nodeTypeUpload.value;
	window.open("/claim/certify/certifyViewFile.do?directType=" + directType + "&nodeTypeUpload=" + nodeTypeUpload + "&businessNo=" + businessNo + "&editType=" + editType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}

/**
 *@description 上传文件
 *@param       businessNo
 */

function doUploadFile(directType, lossItemCode, lossItemName, titleName, imageTypeList) {
	if (imageTypeList == "") {
		alert(i18n.certify.pleaseChooseUploadData); //请先选择您要上传的资料.
		return false;
	}
	var businessNo = fm.prpLcertifyCollectBusinessNo.value;
	var uploadYear = fm.prpLcertifyCollectUploadYear.value;
	var riskCode = fm.prpLcertifyCollectRiskCode.value;
	var nodeType = fm.nodeTypeUpload.value;

	var oldAction = fm.action;
	var oldTarget = fm.target;
	var userCode = fm.prpLcertifyCollectOperatorCode.value;
	if (confirm("上傳的圖片原始文件名稱中，不能包含 ! @ # $ % ^ & * < > 等特殊字符，否則可能會導致圖片上傳失敗！")) {
		fm.action = "/claim/pages/applet/ViewApplet.jsp?directType=" + directType + "&businessNo=" + businessNo + "&lossItemCode=" + lossItemCode + "&nodeType=" + nodeType + "&lossItemName=" + lossItemName + "&uploadYear=" + uploadYear + "&riskCode=" + riskCode + "&titleName=" + titleName + "&imageTypeList=" + imageTypeList + "&operatorCode=" + userCode;
		fm.target = "fraSubmit";
		fm.submit();

		fm.action = oldAction;
		fm.target = oldTarget;
	}
}


/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relatePolicy(policyNo) {
	var riskCode = fm.prpLcertifyCollectRiskCode.value;

	var vURL = '/prpall/' + riskCode + '/tbcbpg/UIPrPoEn' + riskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + policyNo + '&RiskCode=' + riskCode;

	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relateRegist() {
	var registNo = fm.RegistNo.value;
	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW&riskCode=" + fm.riskCode.value;
	//当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 start
	if (opener == undefined) {
		linkURL = linkURL + "&ifclose=true";
	}
	//当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 end
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
/**
 *@description 根据按钮状态保存单证数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	
	fm.buttonSaveType.value = saveType;
	var riskCode = fm.prpLcertifyCollectRiskCode.value;
	var riskType = $(":input[name='riskType']").val();
	if (checkForm() == false) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	var checkReturn = true;
	//意键险不用立案就可以单证收集
	if (!(riskType == "E") && saveType == "4") {
		checkReturn = checkSubmitButton();
	}
	if (!validateForm(fm)) {
		return false;
	}
	if (checkReturn == false) {
		return false;
	}
	field.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 设置收集齐全的标志
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function setCollectFlag(field) {
	var fieldName = field.name;
	var tempElements = null;
	//案件的收集标志
	if (fieldName == "collectFlag") {
		//当案件的收集标志为齐全的时候，所有的子收集标志自动齐全    
		if (field.value == 1) {
			for (i = 0; i < document.all.length; i++) {
				tempElements = document.all(i);
				if (tempElements.type == "radio" && tempElements.value == "1") {
					tempElements.checked = true;
				}
			}
		}
		//子的收集标志  
	} else {
		//任何一个子的收集标志为不齐全，案件的收集标志为不齐全
		if (field.value == 0) {
			fm.collectFlag[0].checked = true;
		}
	}
	return true;
}


/**
 *@description 检查是不是可以按提交按钮
 *@return      通过返回true,否则返回false
 */

function checkSubmitButton() {
	var msg = fm.prpLcertifyCollectNoSubmitMsg.value;
	if (msg == "") {
		return true;
	}
	alert(msg);
	return false;
}


/**
 *@description 设置索赔清单的隐含域
 *@param       无
 *@return      通过返回true,否则返回false
 */

function directCodeChange(field) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var manHourFee;
	var materialFee;
	var sumDefLoss;
	var ManHour;
	var Quantity;

	var fieldCount = getElementCount(field.name);
	if (fieldCount == 1) {
		//更改隐含项的值 
		if (field.checked == true) {
			fm.all("prpLcertifyDirectCode").value = fm.all("code").value;
		} else {
			fm.all("prpLcertifyDirectCode").value = "0";
		}
	} else {
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == field) {
				findex = i;
				break;
			}
		}
		//更改隐含项的值 
		if (field.checked == true) {
			fm.all("prpLcertifyDirectCode")[findex].value = fm.all("code")[findex].value;
		} else {
			fm.all("prpLcertifyDirectCode")[findex].value = "0";
		}
	}
	return true;
}


/**
 *@description 索赔资料清单保存
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveCertifyDirect(field, saveType) {
	fm.submit();
	fm.target = "_self";
	return true;
}

/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "Print", "resizable=0,scrollbars=1,width=800,height=600");
}


/**
 *@description 打印索赔资料清单
 *@param       businessNo
 */

function certifyDirectPrint(businessNo, nodeType) {
	//add print liudaoping 2013-04-15
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirectPrint&nodeType=" + nodeType, "Print", "resizable=0,scrollbars=1,width=800,height=600");
}


//reason:打印索赔须知清单

function certifyDirectList(businessNo, nodeType) {
	//add print liudaoping 2013-04-15
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirectPrint&nodeType=" + nodeType, "Print", "resizable=0,scrollbars=1,width=800,height=600");
}
/**
 *@description 打印索赔须知清单
 *@param       businessNo
 */

function certifyDirectAdd(registNo, nodeType, count) {
	//add print liudaoping 2013-04-15
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + registNo + "&editType=CertifyDirectPrintAdd&index=" + count + "&nodeType=" + nodeType, "Print", "resizable=0,scrollbars=1,width=800,height=600");
}