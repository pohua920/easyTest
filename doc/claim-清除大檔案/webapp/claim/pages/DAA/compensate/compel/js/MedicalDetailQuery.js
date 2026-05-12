/*********************************************************************************************************************************************************************************************************************************************************
 * 強制險醫療給付費用處理 查詢
 */
function medicalDetailQuery(){
	$("#queryType").remove();
	$(":input[name='pageNo']").val(1);
	$(":button").prop("disabled",true);
	$("#fm").submit();
}
/**
 * 編輯醫療費用收據明細
 */
function editMedicalDetail(field , claimNo,compensateNo,personNo,identifyNumber,personName){
	var height = 600;
	var width = 1260;
	var url = contextRootPath + "/compensate/beforeInsertMedicalDetail.do?actionType=AMEND&claimNo=" + claimNo + "&compensateNo=" + compensateNo + "&personNo=" + personNo + "&identifyNumber=" + identifyNumber+"&personName="+personName;
	var returnObj = window.showModalDialog(url, window, "dialogHeight:" + height + "px;dialogWidth:" + width + "px;help:no;resizable:yes;status:no;scroll:yes;");
	if(returnObj){
		var status = "";
		if(returnObj == "0" ){
			status = "待補錄";
		} else if(returnObj == "2" ){
			status = "暫存";
		} else if(returnObj == "4" ){
			status = "已校核";
		} 
		$(field).closest("td").prevAll("td[name='tdstatus']").html(status);
	}
}

function exportMedicalDetail(){
	var $cbx = $("#tbresult").find(":checked[name='cbx']");
	if($cbx.length == 0){
		alert("請選擇要導出的受害人！");
		return;
	}
	$("#export").empty().append($cbx.clone(false)).submit();
}