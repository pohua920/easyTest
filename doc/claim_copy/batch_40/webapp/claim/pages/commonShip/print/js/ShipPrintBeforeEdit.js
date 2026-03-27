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
	if(fm.printType.value == "CargoClaimApplication"){
		 strURL = contextRootPath+"/JRShipCargoClaimApplication.do?"+"policyNo="+fm.policyNo.value;
		 strWindowName = "貨物運輸險索賠函";
	}else if(fm.printType.value == "CargoCommissioned"){
		 strURL = contextRootPath+"/JRShipCargoCommissioned.do?"+"registNo="+fm.registNo.value;
		 strWindowName = "貨物運輸險委託公證申請單";
	}else if(fm.printType.value == "CargoSubrogation"){
		 strURL = contextRootPath+"/JRShipCargoSubrogation.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "貨物運輸險代位追償權利書";
	}else if(fm.printType.value == "CargoTransfer"){
		 strURL = contextRootPath+"/JRShipCargoTransfer.do?"+"policyNo="+fm.policyNo.value;
		 strWindowName = "貨物運輸險權利轉讓書";
	}else if(fm.printType.value == "CargoCompensate"){
		 strURL = contextRootPath+"/JRShipCargoCompensate.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "貨物運輸險賠款理算書";
	}else if(fm.printType.value == "CargoRecovery"){
		 strURL = contextRootPath+"/JRShipCargoRecovery.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "貨物運輸險追償理算書";
	}else if(fm.printType.value == "CargoRemnant"){
		 strURL = contextRootPath+"/JRShipCargoRemnant.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "貨物運輸險殘餘物理算書";
	}else if(fm.printType.value == "ShipClaimApplication"){
		 strURL = contextRootPath+"/JRShipShipClaimApplication.do?"+"registNo="+fm.registNo.value;
		 strWindowName = "理賠申請書";
	}else if(fm.printType.value == "ShipRemittance"){
		 strURL = contextRootPath+"/JRShipShipRemittance.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "匯款同意書";
	}else if(fm.printType.value == "ShipReceipt"){
		 strURL = contextRootPath+"/JRShipShipReceipt.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "賠款同意書暨領款收據";
	}else if(fm.printType.value == "ShipCommissioned"){
		 strURL = contextRootPath+"/JRShipShipCommissioned.do?"+"registNo="+fm.registNo.value;
		 strWindowName = "委託公證申請單";
	}else if(fm.printType.value == "ShipContract"){
		 strURL = contextRootPath+"/JRShipShipContract.do?"+"policyNo="+fm.policyNo.value;
		 strWindowName = "債權讓與契約暨通知書";
	}else if(fm.printType.value == "ShipRevocation"){
		 strURL = contextRootPath+"/JRShipShipRevocation.do?"+"policyNo="+fm.policyNo.value;
		 strWindowName = "撤銷申請理賠同意書";
	}else if(fm.printType.value == "ShipCompensate"){
		 strURL = contextRootPath+"/JRShipShipCompensate.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "賠款理算書";
	}else if(fm.printType.value == "ShipReconciliation"){
		 strURL = contextRootPath+"/JRShipShipReconciliation.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "和解書";
	}else if(fm.printType.value == "ShipRecovery"){
		 strURL = contextRootPath+"/JRShipShipRecovery.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "追償理算書";
	}else if(fm.printType.value == "ShipRemnant"){
		var compensateNo = fm.compensateNo.value;
		if(compensateNo.indexOf("MC")>-1||compensateNo.indexOf("OP")>-1||compensateNo.indexOf("TB")>-1){
			//货运险的url
			strURL = contextRootPath+"/JRShipCargoRemnant.do?"+"compensateNo="+compensateNo;
		}else{
			strURL = contextRootPath+"/JRShipShipRemnant.do?"+"compensateNo="+compensateNo;
		}
		strWindowName = "殘餘物理算書";
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
