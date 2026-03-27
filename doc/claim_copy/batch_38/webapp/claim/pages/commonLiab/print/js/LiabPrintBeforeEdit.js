/*****************************************************************************
 * DESC       ：責任險列印腳本
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2014-6-12
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

/**
 * 调用列印的action
 */
function submitForm(){
	var strURL = "";
	var strWindowName = "";
	if(fm.printType.value == "LiabNotification"){
		 strURL = contextRootPath+"/JRLiabLiabNotification.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "補件通知函";
	}else if(fm.printType.value == "LiabRemnant"){
		 strURL = contextRootPath+"/JRLiabLiabRemnant.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "殘餘物理算書";
	}else if(fm.printType.value == "LiabInvestigative"){
		 strURL = contextRootPath+"/JRLiabLiabInvestigative.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "查案單";
	}else if(fm.printType.value == "LiabRevocation"){
		 strURL = contextRootPath+"/JRLiabLiabRevocation.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "撤銷申請理賠同意書";
	}else if(fm.printType.value == "LiabReconciliation"){
		 strURL = contextRootPath+"/JRLiabLiabReconciliation.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "和解書";
	}else if(fm.printType.value == "LiabRemittance"){
		 strURL = contextRootPath+"/JRLiabLiabRemittance.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "匯款同意書";
	}else if(fm.printType.value == "LiabCompensate"){
		 strURL = contextRootPath+"/JRLiabLiabCompensate.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "理賠計算書";
	}else if(fm.printType.value == "LiabReceipt"){
		 strURL = contextRootPath+"/JRLiabLiabReceipt.do?"+"compensateNo="+fm.compensateNo.value;
		 strWindowName = "賠款同意書暨領款收據";
	}else if(fm.printType.value == "LiabCommissioned"){
		 strURL = contextRootPath+"/JRLiabLiabCommissioned.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "委託公證申請單";
	}else if(fm.printType.value == "LiabClaimApplication"){
		 strURL = contextRootPath+"/JRLiabLiabClaimApplication.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "責任險理賠申請書";
	}else if(fm.printType.value == "LiabContract"){
		 strURL = contextRootPath+"/JRLiabLiabContract.do?"+"claimNo="+fm.claimNo.value;
		 strWindowName = "債權讓與契約暨通知書";
	}else if(fm.printType.value == "LiabSingleNote"){
		 strURL = contextRootPath+"/JRLiabLiabSingleNote.do?" +"claimNo="+fm.claimNo.value;
		 strWindowName = "旅行業責任保險理賠照會單";
	}else if(fm.printType.value == "LiabCard"){
		 strURL = contextRootPath+"/JRLiabLiabCard.do?" +"claimNo="+fm.claimNo.value;
		 strWindowName = "信用卡不便險理賠申請書";
	}else if(fm.printType.value == "LiabCardAppend"){
		 strURL = contextRootPath+"/JRLiabLiabCardAppend.do?" +"claimNo="+fm.claimNo.value;
		 strWindowName = "信用卡附加旅平險理賠申請書";
	}else if(fm.printType.value == "LiabCardComplex"){
		 strURL = contextRootPath+"/JRLiabLiabCardComplex.do?" +"claimNo="+fm.claimNo.value;
		 strWindowName = "信用卡綜合保險全球購物理賠申請書";
	}else if(fm.printType.value == "LiabDocument"){
		 strURL = contextRootPath+"/JRLiabLiabDocument.do";
		 strWindowName = "應備文件";
	}else if(fm.printType.value == "LiabCardDocument"){
		 strURL = contextRootPath+"/JRLiabLiabCardDocument.do";
		 strWindowName = "信用卡綜合保險應備文件";
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
					printWindow(contextRootPath+"/JRTimesPropReplevyReport.do?"+"bizNo="+data,"責任險追償計算書");
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
