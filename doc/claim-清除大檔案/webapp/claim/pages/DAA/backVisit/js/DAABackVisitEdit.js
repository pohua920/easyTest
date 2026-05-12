/**
 *@description 检查页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {
	return validateForm(window.fm);
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
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {

	if (checkForm() == false) {
		return false;
	}

	var errorMessage = "";

	fm.buttonSaveType.value = saveType;

	var context = fm.prpLbackVisitTextContent.value;
	if (context == null || context.length < 1) {
		errorMessage = errorMessage + i18n.backVisit.pleaseEnterComment; //请输入客户意见
	}
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
    if (saveType == "4") {
		var prpLbackVisitPhone =fm.prpLbackVisitPhone.value;
		if (prpLbackVisitPhone.length > 0) {
			 if (prpLbackVisitPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLbackVisitPhone)){
			    		errorMessage =errorMessage +"聯繫電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLbackVisitPhone)){
			    	errorMessage =errorMessage +"聯繫電話有誤\n";
			      }
			}
		  }
	}////mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end 
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}

	if (saveType == "4") {
		ableAllInput();
	}

	field.disabled = true;

	fm.submit();
	return true;
}