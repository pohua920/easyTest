/*****************************************************************************
 * DESC       ：车险列印的脚本函数页面
 * AUTHOR     ：中科软
 * CREATEDATE ： 2004-11-12
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function claimPrint() {
	var strPrintType = fm.printType.value;
	var strBusinessNo = fm.BusinessNo.value;
	var win;
	var printDetailDo = "";
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	if (strPrintType == "Regist") {

		if (trim(strBusinessNo).length < 1) {
			alert(i18n.print.pleaseEnterBusinessNo); //请输入业务号码!
			return false;
		}
		printDetailDo = "/claim/ClaimPrint.do?printType=" + strPrintType + "&registNo=" + strBusinessNo;

		printWindow(printDetailDo, "列印");
	}
	return true;
}

//显示列印窗口

function printWindow(strURL, strWindowName) {
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;
	if (pageWidth < 100)
		pageWidth = 100;

	if (pageHeight < 100)
		pageHeight = 100;

	var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
	newWindow.focus();
	return newWindow;
}