YAHOO.namespace("YAHOO.saaing");  
 /*
 function appendOpinoin(select){
	var handletext=document.getElementById("handletext");
	if(select.selectedIndex!=0){
		var text=select.options[select.selectedIndex].innerHTML;
		handletext.value=text;submitUnderwriting
	}		
}*/

function enableButton(){
	var btn = YAHOO.util.Dom.get("moreBtn");
	btn.disabled = false;
}
function moreInsuredInfo(field,bizNo,bizType){
	if(YAHOO.saaing.moreInsuredInfo != undefined){
		YAHOO.saaing.moreInsuredInfo.show();
	}else{
		YAHOO.saaing.moreInsuredInfo = 
			new YAHOO.widget.Panel("moreInsuredInfo_panel",
			{	iframe:true, 
				visible:true, 
				x:10,
				y:10,
				width:600, 
				height:250, 
				constraintoviewport:true, 
//				fixedcenter:true, 
				modal:false});
		YAHOO.saaing.moreInsuredInfo.setHeader("被保险人信息");
		YAHOO.saaing.moreInsuredInfo.setBody("<iframe name='moreInsuredInfo_Frame' src='/undwrt/underwriting/moreInsuredInfo.do?taskType="+bizType+"&businessNo="+bizNo+"'  frameborder='0' style='margin:0; padding:0; width:100%; height: 95%'></iframe>");
		YAHOO.saaing.moreInsuredInfo.render(document.body);
		YAHOO.saaing.moreInsuredInfo.show();
		YAHOO.util.Event.addListener(YAHOO.saaing.moreInsuredInfo.close, "click", enableButton, this);
	}
		field.disabled = true;
}



var submitDlg;
function submitUnderwriting(editType){
	if (checkForm()) {
		if (editType=="save") {
			fm.action="${ctx}/underwriting/saveUnderwriting.do?editType=save";
			fm.submit();
		} else if (editType=="submit"){
			submitDlg = new YAHOO.widget.Panel("submitDlg",{iframe:true, visible:true, width:650, height:400, underlay:"shadow", constraintoviewport:true, fixedcenter:true, modal:true});
			submitDlg.setHeader("核保任务提交!");
			submitDlg.setBody("<iframe name='submitFrame' src='javascript:false;'  frameborder='0' style='margin:0; padding:0; width:100%; height: 95%'></iframe>");
			submitDlg.render(document.body);
			submitDlg.show();
			YAHOO.util.Event.addListener(submitDlg.close, "click", refreshPage, this);
			fm.action="/undwrt/underwriting/saveUnderwriting.do?editType=submit";
			fm.target="submitFrame";
			fm.submit();
		}else{
			
		}
		
	} else {
		
	}
}
var riskPanel ;
/**
 * closeThisPanel
 * @param {type} riskPanel 
 */
 function closeThisPanel() {
 	riskPanel.destroy();
 }
/**
 * showRiskPanel
 */
 function showRiskPanel(field) {
 			riskPanel = new YAHOO.widget.Panel("riskPanel",{iframe:true, visible:true, width:650, height:400, underlay:"shadow", constraintoviewport:true, fixedcenter:true, modal:true});
			riskPanel.setHeader("产品构造模拟器!");
			riskPanel.setBody("<iframe name='submitFrame' src='/saa/common/getRiskTree.do'  frameborder='0' style='margin:0; padding:0; width:100%; height: 95%'></iframe>");
			riskPanel.render(document.body);
			riskPanel.show();
//			YAHOO.util.Event.addListener(riskPanel.close, "click", showSelectValue, this);
//			fm.action="${ctx}/underwriting/saveUnderwriting.do?editType=submit";
//			fm.target="submitFrame";
//			fm.submit();
 	
 }


/**
 * checkForm
 * @param {type}  
 */
 function checkForm() {
 	if (fm.handleText.value=="") {
 		alert("请输入核保意见！");
 		return false;
 	}
 	return true;	
 }
 
 
 
/**
 * checkBeforeQuery
 * @param {type}  
 */
 function checkBeforeQuery() {
	

 }


/**
 * refreshPage()
 * @param {type} param 
 */
 function refreshPage(){
	window.location.reload();

}
var giveUpDlg;
/**
 * giveUpTask()
 * @param {type}  
 */
 function giveUpTask() {
 			giveUpDlg = new YAHOO.widget.Panel("giveUpDlg",{iframe:true, visible:true, width:650, height:400, underlay:"shadow", constraintoviewport:true, close:false, fixedcenter:true, modal:true});
			giveUpDlg.setHeader("成功放弃任务!");
			giveUpDlg.setBody("<iframe name='giveUpFrame'   frameborder='0' style='margin:0; padding:0; width:100%; height: 95%'></iframe>");
			giveUpDlg.render(document.body);
			giveUpDlg.show();
			fm.action="/undwrt/underwriting/giveUpTask.do";
			fm.target="giveUpFrame";
			fm.submit();
 	
 }
 
 var moveTaskDlg;
 /**
  * moveTask
  * @param {type}  
  */
  function moveTask() {
  			moveTaskDlg = new YAHOO.widget.Panel("moveTaskDlg",{iframe:true, visible:true, width:650, height:400, underlay:"shadow", constraintoviewport:true, close:false, fixedcenter:true, modal:true});
			moveTaskDlg.setHeader("成功放弃任务!");
			moveTaskDlg.setBody("<iframe name='giveUpFrame'   frameborder='0' style='margin:0; padding:0; width:100%; height: 95%'></iframe>");
			moveTaskDlg.render(document.body);
			moveTaskDlg.show();
			fm.action="/undwrt/bpm/moveTask.do";
			fm.target="giveUpFrame";
			fm.submit();
  }

/**
 * checkBizType
 * @param {type} field 
 */
 function checkBizType(field) {
 	var bizType = field.value;
 	var stateValue = fm.state;
 	if(bizType == '1'){//审核通过
		stateValue[0].checked = ""; 		
 	}else if(bizType == '9'){//待核保
 		stateValue[1].checked = ""; 	
 	}else if(bizType == '4'){//已经拒保
 		stateValue[0].checked = ""; 	
 	}
 	
 }

