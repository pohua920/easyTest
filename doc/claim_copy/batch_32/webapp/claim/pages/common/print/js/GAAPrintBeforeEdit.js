/*****************************************************************************
 * DESC       ：工程险列印腳本
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2014-06-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

/**
 * 调用列印的action
 */
function submitForm(field){
	var strURL = "";
	var strWindowName = "";
	if(fm.printType.value == "GAAClaimApplication"){
		strURL = contextRootPath+"/JRGAAGAAClaimApplication.do?claimNo="+fm.claimNo.value;
		strWindowName = "理賠申請書";
	}else if(fm.printType.value == "GAARemittance"){
		strURL = contextRootPath+"/JRGAAGAARemittance.do?compensateNo="+fm.compensateNo.value;
		strWindowName = "匯款同意書";
	}else if(fm.printType.value == "GAAReceipt"){
		strURL = contextRootPath+"/JRGAAGAAReceipt.do?claimNo="+fm.claimNo.value;
		strWindowName = "賠款同意書暨領款收據";
	}else if(fm.printType.value == "GAACommissioned"){
		strURL = contextRootPath+"/JRGAAGAACommissioned.do?claimNo="+fm.claimNo.value;
		strWindowName = "委託公證申請單";
	}else if(fm.printType.value == "GAAContract"){
		strURL = contextRootPath+"/JRGAAGAAContract.do?claimNo="+fm.claimNo.value;
		strWindowName = "債權讓與契約暨通知書";
	}else if(fm.printType.value == "GAARevocation"){
		strURL = contextRootPath+"/JRGAAGAARevocation.do?claimNo="+fm.claimNo.value;
		strWindowName = "撤銷申請理賠同意書";
	}else if(fm.printType.value == "GAANotification"){
		strURL = contextRootPath+"/JRGAAGAANotification.do?claimNo="+fm.claimNo.value;
		strWindowName = "補件通知函";
	}else if(fm.printType.value == "GAAInvestigative"){
		strURL = contextRootPath+"/JRGAAGAAInvestigative.do?claimNo="+fm.claimNo.value;
		strWindowName = "查案單";
	}else if(fm.printType.value == "GAAReconciliation"){
		strURL = contextRootPath+"/JRGAAGAAReconciliation.do?claimNo="+fm.claimNo.value;
		strWindowName = "和解書";
	}else if(fm.printType.value == "GAARemnant"){
		strURL = contextRootPath+"/JRGAAGAARemnant.do?compensateNo="+fm.compensateNo.value;
		strWindowName = "残余物理算书";
	}else if(fm.printType.value == "GAACompensate"){
		strURL = contextRootPath+"/JRGAAGAACompensate.do?compensateNo="+fm.compensateNo.value;
		strWindowName = "理賠計算書";
		//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書-start
	}else if(fm.printType.value == "GAAReplevyReport"){
		 strURL = contextRootPath+"/JRGAAGAAReplevyReport.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "追償計算書";
		//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 -end
	}
	if(strURL == ""){
		alert("列印功能配置不正確，請聯繫管理員！");
		return false;
	}else{
		field.disabled = true;
	}
	printWindow(strURL,strWindowName);
}

/**
 * 檢查輸入的業務號是否正確 (此方法暂不使用)
 */
function checkBizNo(printType, bizNoType, bizNo){
	var data = "printType="+printType+"&bizNoType="+bizNoType+"&bizNo="+bizNo;
    $.ajax({
    	type:"get",
		url:contextRootPath+"/checkBizNo.do",
		cache:false,
		dataType:"text",
		async:false,
		data:data,
		success:function(data){
			if (data.length>0){
				// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
				field.disabled = true;
				if(printType == "PropReplevyReport" && bizNoType=="1"){
					printWindow(contextRootPath+"/JRTimesPropReplevyReport.do?"+"bizNo="+data,"工程險追償計算書");
				}
			}else{
				if(printType == "propReplevyReport" && bizNoType=="1"){
					alert("計算書號碼不存在！");
				}
				return false;
			}
		}
	});
}

/**
 * 显示列印窗口
 */
function printWindow(strURL,strWindowName){
  var pageWidth=screen.availWidth-10;
  var pageHeight=screen.availHeight-30;
  if (pageWidth<100 )
    pageWidth = 100;
  if (pageHeight<100 )
    pageHeight = 100;
  var newWindow = window.open(strURL,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
  newWindow.focus();
  return newWindow;
}
