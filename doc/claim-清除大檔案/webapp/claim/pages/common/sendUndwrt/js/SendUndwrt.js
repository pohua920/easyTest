function SubmitDisplay() {
	fm.buttonSave.style.display = "none";
	fm.buttonSaveFinishSubmit.style.display = "none";
	if (fm.buttonCancel != undefined) {
		fm.buttonCancel.style.display = "none";
	}

	var buttonGiveup = fm.buttonGiveup;
	if (buttonGiveup != null) {
		fm.buttonGiveup.style.display = "none";
	}
	readonlyAllInput();
	fm.undwrtPhrase.disabled = false;
	fm.undwrtTextContextInnerHTML.readOnly = false;
	fm.undwrtTextContextInnerHTML.style.color = "#000000";
	fm.undwrtTextContextInnerHTML.style.backgroundColor = "#F4F9FF";
}

function checkUndwrt() {
	if (fm.undwrtFlag.value == "3") {
		alert(i18n.sendUndwrt.passageLaw); // 您上一次送审没有通过，需再次送审並通过後才能进行【提交】操作！
		return false;
	} else if (fm.needUndwrtFlag.value == "Y" && fm.undwrtFlag.value == "0") {
		if (fm.nodeType.value == "claim") {
			var BaseCurrency2 = fm.BaseCurrency2;
			if (BaseCurrency2 != null) {
				if (parseFloat(fm.prpLclaimSumClaim.value) * parseFloat(fm.ExchRate2.value) > parseFloat(fm.undwrtSumPaid.value)) {
					alert(i18n.sendUndwrt.amountSent + fm.undwrtSumPaid.value + i18n.sendUndwrt.informationReview); //估损金额乘以兑换率大於送审金额   //，请暂存立案信息後送审，审核通过後才能进行【提交】操作！

					return false;
				} else {
					return true;
				}
			} else {
				if (parseFloat(fm.prpLclaimSumClaim.value) > parseFloat(fm.undwrtSumPaid.value)) {
					alert(i18n.sendUndwrt.greaterOriginated + fm.undwrtSumPaid.value + i18n.sendUndwrt.informationReview); //估损金额大於送审金额    //，请暂存立案信息後送审，审核通过後才能进行【提交】操作！
					return false;
				} else {
					return true;
				}
			}
		} else {
			return true;
		}
	} else {
		return true;
	}
}

function SendUndwrt() {
	var vaild = confirm("【送审】操作不会儲存您此次輸入的相关理赔信息。\n请确认您輸入的理赔信息已经暂存？");
	if (vaild) {
		var url  = "/claim/sendUndwrt/sendUndwrt.do?actionType=Send&businessNo=";
		if (fm.nodeType.value == "claim" || fm.nodeType.value == "check") {
			url += fm.registno.value;
		} else if (fm.nodeType.value == "compe") {
			url += fm.prpLcompensateClaimNo.value;
		} else if (fm.nodeType.value == "compp") {
			url += fm.prpLcompensateCompensateNo.value;
		}
		fm.action = url;
		fm.submit();
	} else {
		return false;
	}
}

function Undwrt(method) {
	var undwrtText = fm.undwrtTextContextInnerHTML.value;
	if (trim(undwrtText).length > 50) {
		alert(i18n.sendUndwrt.reviewComments); //审核意见不能大於50个字符！
		return false;
	}
	var BaseCurrency2 = fm.BaseCurrency2;
	if (BaseCurrency2 != null) {
		if (fm.nodeType.value == "claim" && method == "Pass" && parseFloat(fm.prpLclaimSumClaim.value) * parseFloat(fm.ExchRate2.value) > parseFloat(fm.undwrtSumPaid.value)) {
			alert(i18n.sendUndwrt.estimateAmount + fm.undwrtSumPaid.value + i18n.sendUndwrt.submitHigher); //估损金额乘以兑换率大於审核权限    //，请进行【提交上级】操作！
			return false;
		}
		if (fm.nodeType.value == "compp" && method == "Pass" && parseFloat(fm.prpLcompensateSumPaid.value) * parseFloat(fm.ExchRate2.value) > parseFloat(fm.undwrtSumPaid.value)) {
			alert(i18n.sendUndwrt.caseAudit + fm.undwrtSumPaid.value + i18n.sendUndwrt.submitHigher); //本案合计金额乘以兑换率大於审核权限   //，请进行【提交上级】操作！
			return false;
		}
	} else {
		if (fm.nodeType.value == "claim" && method == "Pass" && parseFloat(fm.prpLclaimSumClaim.value) > parseFloat(fm.undwrtSumPaid.value)) {
			alert(i18n.sendUndwrt.auditPermissions + fm.undwrtSumPaid.value + i18n.sendUndwrt.submitHigher); //估损金额大於审核权限    //，请进行【提交上级】操作！
			return false;
		}
		if (fm.nodeType.value == "compp" && method == "Pass" && parseFloat(fm.prpLcompensateSumPaid.value) > parseFloat(fm.undwrtSumPaid.value)) {
			alert(i18n.sendUndwrt.presentCase + fm.undwrtSumPaid.value + i18n.sendUndwrt.submitHigher); //本案合计金额大於审核权限  //，请进行【提交上级】操作！
			return false;
		}
	}
	fm.action = "/claim/sendUndwrt/sendUndwrt.do?actionType=Undwrt&businessNo=";
	if (fm.nodeType.value == "claim" || fm.nodeType.value == "check") {
		fm.action += fm.registno.value;
	} else if (fm.nodeType.value == "compe") {
		fm.action += fm.prpLcompensateClaimNo.value;
	} else if (fm.nodeType.value == "compp") {
		fm.action += fm.prpLcompensateCompensateNo.value;
	}

	fm.action += "&method=" + method;
	fm.submit();
}