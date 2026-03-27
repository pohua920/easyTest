/*****************************************************************************
 * DESC       ：查勘登记的脚本函数页面
 * AUTHOR     ：中科软
 * MODIFYLIST ：Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查查勘登记
 *@param       无
 *@return      通过返回true,否则返回false
 */
function checkForm() {
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
	if (window.confirm(i18n.button.confirmClear)) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */
function saveForm(field, saveType) {
	//add by luochang at 2010-10-08 提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}

	var errorMessage = "";
	var prpLcheckDamageStartDate = fm.prpLcheckDamageStartDate.value;
	prpLcheckDamageStartDate = prpLcheckDamageStartDate.substring(0, 10);
	var prpLcheckCheckDate = fm.prpLcheckCheckDate.value;
	if (prpLcheckCheckDate < prpLcheckDamageStartDate) {
		alert(i18n.check.surveyDateAccident); //查勘日期不能在出险日期之前！！
		return false;
	}
	fm.buttonSaveType.value = saveType;
	//设值复选框的值	
	var context = fm.prpLregistTextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.check.notAllowedEmpty; //查勘报告不允许为空\n
	}
	//查勘人判断
	var Checker1 = fm.prpLcheckChecker1.value;
	var Checker2 = fm.prpLcheckChecker2.value;
	if (Checker1 == Checker2) {
		errorMessage = errorMessage + i18n.check.cannotSamePerson; //查勘人 1和查勘人 2不能为同一人;\n
	}
     //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	if (saveType == "4") {
	var prpLextAppliPhone =fm.prpLextAppliPhone.value;
	if (prpLextAppliPhone.length > 0) {
		 if (prpLextAppliPhone.substr(0, 2)=='09'){
		    	reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLextAppliPhone)){
		    		errorMessage =errorMessage +"要保人或代表名稱及聯繫電話/傳真有誤\n";
		    	}
		  } else {
		      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
		      if (!reg.test(prpLextAppliPhone)){
		    	errorMessage =errorMessage +"要保人或代表名稱及聯繫電話/傳真有誤\n";
		      }
		}
	  }
	  var  prpLextInsuredPhone  =fm.prpLextInsuredPhone.value;
	  if (prpLextInsuredPhone.length > 0) {
			 if (prpLextInsuredPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLextInsuredPhone)){
			    		errorMessage =errorMessage +"被保險人或代表名稱及聯繫電話/傳真有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLextInsuredPhone)){
			    	errorMessage =errorMessage +"被保險人或代表名稱及聯繫電話/傳真有誤\n";
			      }
			}
		  }
    } //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	if (!validateForm(fm)) {
		return false;
	}
	field.disabled = true;
	fm.submit();
}

/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.prpLcheckRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {
	var policyNo = fm.prpLcheckPolicyNo.value;
	var registNo = fm.prpLcheckRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");

}

/**
 *@description 设置画面的初始值
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet1() {
	return true;
}

//按钮单击事件，用於相同保单号码多报案的显示
function buttonOnClick(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage1(strSubPageCode);

}

//显示输入框
//leftMove 默认值0，坐标左移leftMove
function showSubPage1(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;
	ey = ey + 10;
	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}

/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */
function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}

//add by qinyongli 查看出险时保单信息,在业务系统中进行保单还原
/**function backWardPolicy(){
     var SHOWTYPE  ="SHOW";
     var BizNo     =fm.prpLcheckPolicyNo.value;
     var RiskCode  =fm.prpLcheckRiskCode.value;
     var damageDate=fm.damageDate.value;
     var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;
     window.open(vURL,'详细信息','width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}*/

function heresyCheck() {
	if (fm.unitType.value == "0") {
		if (fm.prpLcheckHandleUnitCode.value != "" && fm.prpLcheckHandleUnitName.value != "") {
			window.open("/claim/ClaimPrint.do?printType=PropLocaleHeresy&RegistNo=" + fm.registno.value + "&HandleUnitName=" + fm.prpLcheckHandleUnitName.value, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=750,Height=800");
		} else {
			alert(i18n.check.operationAgain); //请选择代查勘处理机构，再进行委托书操作！
			return false;
		}
	} else {
		alert(i18n.check.surveyProcess); //只有当【查勘处理单位】为“系统外”时才可以进行委托书操作！
		return false;
	}
}

function countryFlag_change(countryFlag) {
	if (countryFlag == "0") {
		fm.foreignCountryName.style.display = "none";
	} else {
		fm.foreignCountryName.style.display = "";
	}

	fm.portCode.value = "";
	fm.portCName.value = "";
	fm.prpLcheckDamageAddress.value = "";
}

function showPort(field) {
	var port = "^(" + fm.portCName.value + ")"
	port = new RegExp(port);
	if (field.value.search(port) != -1 && fm.portCName.value != "") {

	} else {
		field.value = "";
		if (fm.countryFlag.value == "0") {
			field.value = fm.portCName.value;
		} else {
			if (fm.language.value == "E") {
				field.value = fm.portCName.value + " " + fm.foreignCountryName.value;
			} else {
				field.value = fm.foreignCountryName.value + " " + fm.portCName.value;
			}
		}
	}
}

function clearPortCode() {
	fm.portCode.value = "";
	fm.portCName.value = "";
	fm.prpLcheckDamageAddress.value = "";
}