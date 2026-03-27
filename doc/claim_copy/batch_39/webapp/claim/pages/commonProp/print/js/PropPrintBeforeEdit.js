/*****************************************************************************
 * DESC       ：火險列印腳本
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2013-10-15
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

/**
 * 调用列印的action
 */
function submitForm(){
	var strURL = "";
	var strWindowName = "";
	if(fm.printType.value == "PropReplevyReport"){
		 strURL = contextRootPath+"/JRPropPropReplevyReport.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "火險追償計算書";
	}else if(fm.printType.value == "PropPaymentAcceptance"){
		 strURL = contextRootPath+"/JRPropPropPaymentAcceptance.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "火災保險賠款接受書";
	}else if(fm.printType.value == "PropRegistReport"){
		 strURL = contextRootPath+"/JRPropPropRegistReport.do?"+"registNo="+fm.registNo.value;
		 strWindowName = "火險出險報告";
	}else if(fm.printType.value == "PropGeneralClaim"){
		 strURL = contextRootPath+"/JRPropPropGeneralClaim.do?"+"registNo="+fm.registNo.value;
		 strWindowName = "非水代查勘委託書";
	}else if(fm.printType.value == "PropRemittanceForm"){
		 strURL = contextRootPath+"/JRPropPropRemittanceForm.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "匯款同意書1111賠款同意書代位求償承諾書1111";//为什么把1111 换成 （ 号 就报错？
	}else if(fm.printType.value == "PropClaimApplicationForm"){
		 strURL = contextRootPath+"/JRPropPropClaimApplicationForm.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "理賠處理報告";
	}else if(fm.printType.value == "PropClaimDisposeReport"){
		 strURL = contextRootPath+"/JRPropPropClaimDisposeReport.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "理賠申請書";
	}else if(fm.printType.value == "PropCoinsCompensate"){
		 strURL = contextRootPath+"/JRPropPropCoinsCompensate.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "聯共保計算書";
	}else if(fm.printType.value == "PropClaimCompensateReport"){
		 strURL = contextRootPath+"/JRPropPropClaimCompensateReport.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "火險賠款計算書";
	}else if(fm.printType.value == "PropLossList"){
		 strURL = contextRootPath+"/JRPropPropLossList.do?"+"registNo="+fm.registNo.value+"&policyNo="+fm.policyNo.value;
		 strWindowName = "火險損失清單";
	}else if(fm.printType.value == "PropBankAgreement"){
		 strURL = contextRootPath+"/JRPropPropBankAgreement.do";
		 strWindowName = "銀行同意書";
	}else if(fm.printType.value == "PropPrpinsClaimInformation"){
		 strURL = contextRootPath+"/JRPropPropPrpinsClaimInformation.do?" +"registNo="+fm.registNo.value;
		 strWindowName = "火險承保理賠信息";
	}else if(fm.printType.value == "PropRemnantReport"){
		 strURL = contextRootPath+"/JRPropPropRemnant.do?" + "compensateNo="+fm.compensateNo.value;
		 strWindowName = "火險殘餘物訊息";
	}
	if(strURL == ""){
		alert("列印功能配置不正確，請聯繫管理員！");
		return false;
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
					printWindow(contextRootPath+"/JRTimesPropReplevyReport.do?"+"bizNo="+data,"火險追償計算書");
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
