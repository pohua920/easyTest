/****************************************************************************
 * DESC       :Guarantee?????js
 * AUTHOR     :???
 * CREATEDATE :
 * MODIFYLIST :   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ************************************************************************************/
/**
@author ???
@description ??????
@param       ?
@return      boolean,????true,?????false
*/
function checkForm() {
	var editRadioList = document.getElementsByName("editRadio");
	var flag = false;
	if (editRadioList.length == 0) {
		flag = true;
	} else {
		for (var i = 0; i < editRadioList.length; i++) {
			if (editRadioList[i].checked == true) {
				flag = true;
			}
		}
	}
	if (flag) {} else {
		alert(i18n.guarantee.printFormat); //?????????!
		return;
	}
	if (fm.GuaranteeTypeCode.value == "" || fm.GuaranteeTypeName.value == "") {
		errorMessage("????????!");
		return false;
	}
	if (fm.applyPerson.value == "") {
		errorMessage("???????!");
		return false;
	}
	if (fm.Currency.value == "") {
		errorMessage("????????!");
		return false;
	}
	if (fm.SumGuarantee.value == "") {
		errorMessage("????????!");
		return false;
	}
	return true;
}

function submitForm() {
	if (!checkForm()) {
		return false;
	}
	fm.submit();
}