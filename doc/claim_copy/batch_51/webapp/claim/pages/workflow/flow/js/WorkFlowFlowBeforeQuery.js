/*****************************************************************************
 * DESC       ：工作流流程查询JS
 * AUTHOR     ：weishixin
 * CREATEDATE ： 2004-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
//---------------------------------------------------------------
//声明全局变量
//---------------------------------------------------------------
/**
 *@description 当险种为DAA时显示车牌号
 *@param       field
 *@return      通过返回true,否则返回false
 */
function showLicenseNo(field) {
	var riskCode = trim(field.value);

	if (riskCode == "DAA") {

		fm.prpLregistLicenseNo.value = "";
		fm.prpLregistLicenseNo.style.borderTop = "#4196BF 1px solid";
		fm.prpLregistLicenseNo.style.borderBottom = "#4196BF 1px solid";
		fm.prpLregistLicenseNo.style.borderRight = "#4196BF 1px solid";
		fm.prpLregistLicenseNo.style.borderLeft = "#4196BF 1px solid";
		fm.prpLregistLicenseNo.style.width = "80%";
		fm.prpLregistLicenseNo.style.color = "#ffffff";
		fm.prpLregistLicenseNo.style.backgroundColor = "#ffffff";
		fm.prpLregistLicenseNo.readOnly = false;
	} else {
		fm.prpLregistLicenseNo.style.fontSize = "11pt";
		fm.prpLregistLicenseNo.style.borderTop = "none";
		fm.prpLregistLicenseNo.style.borderBottom = "none";
		fm.prpLregistLicenseNo.style.borderRight = "none";
		fm.prpLregistLicenseNo.style.borderLeft = "none";
		fm.prpLregistLicenseNo.style.width = "80%";
		fm.prpLregistLicenseNo.style.color = "#000000";
		fm.prpLregistLicenseNo.style.backgroundColor = "#F4F9FF";
		//fm.prpLregistLicenseNo.style= "FONT-SIZE: 11pt; BORDER-TOP: none; BORDER-BOTTOM: none;BORDER-RIGHT-STYLE: none;BORDER-LEFT-STYLE: none;WIDTH: 100%;COLOR: #000000;BACKGROUND-COLOR: #F4F9FF";
		fm.prpLregistLicenseNo.value = "";
		fm.prpLregistLicenseNo.readOnly = true;
	}
	return true;
}


function submitForm() {
	/*
      var ref="";
      for(i=0;i<fm.status.length;i++){
        if(fm.status[i].checked==true){
           ref = ref+fm.status[i].value+",";
        }
      }
      fm.caseFlag.value = ref;
*/
	if (trim(fm.prpLregistRegistNo.value) == '' && trim(fm.prpLregistPolicyNo.value) == '' && trim(fm.prpLregistLicenseNo.value) == '' && trim(fm.prpLregistInsuredName.value) == '' && trim(fm.prpLregistClaimNo.value) == '') {
		alert(i18n.regist.queryConditionCannotEmpty); //查询条件不允许为空！
		return false;
	}

	if (trim(fm.prpLregistInsuredName.value) != '' && trim(fm.InsuredNameSign.value) == '=*' && trim(fm.prpLregistInsuredName.value).length < 2) {
		alert(i18n.regist.insurantFuzzyQueryNeedTwoInputName); //被保险人模糊查询时最少需要输入姓名前两位！
		return false;
	}
	fm.submit(); //提交
}