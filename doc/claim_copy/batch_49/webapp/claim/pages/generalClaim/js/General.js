/**
 *@description 委托提交
 *@param       无
 *@return      
 */
function giveInsert() {
	var registNo = trim(fm.registNo.value);
	var permitFlag = fm.permitFlag.value;
	var receiveComcode = ""
	if (permitFlag == "NO") {
		alert(i18n.generalClaim.notConformCompensateUnableCompensate); //不符合通赔条件，不能进行通赔！
		return false;
	}
	var $GeneralClaim = $("#GeneralClaim");
	var message = "";
	$GeneralClaim.find(":input[name='prpLgeneralClaimTaskLogReceiveComCode']").each(function(){
		if(this.value==""){
			message += "“處理機構代碼”不能為空，請錄入！\n";
			return false;
		}
	});
	$GeneralClaim.find(":input[name='prpLgeneralClaimTaskLogReceiveComName']").each(function(){
		if(this.value==""){
			message += "“處理機構名稱”不能為空，請錄入！\n";
			return false;
		}
	});
	var actionType = $(":input[name='actionType']" ).val();
	if(actionType=="Guide"){
		var handlerCode = $("#GeneralClaim_Data").find(":input[name='handlerCode']").val();
		$GeneralClaim.find(":input[name='prpLgeneralClaimTaskLogReceiveOperatorCode']").each(function(){
			if(this.value==""){
				message += "“處理人員代碼”不能為空，請錄入！\n";
				return false;
			}else if(handlerCode==this.value){
				message += "“處理人員代碼”不能是查勘處理人員 ！\n";
				return false;
			}
		});
		$GeneralClaim.find(":input[name='prpLgeneralClaimTaskLogReceiveOperatorName']").each(function(){
			if(this.value==""){
				message += "“處理人員名稱 ”不能為空，請錄入！\n";
				flag = false;
				return false;
			}
		});
	}
	if($(":input[name='prpLgeneralClaimTaskLogExtendString1']" ).val()=="") {
		message += "“更改原因 ”不能為空，請錄入！\n";
	}
	if($(":input[name='prpLgeneralClaimTaskLogExtendString1']" ).val().length>200) {
		message += "“更改原因 ”不能超过200个字符 \n";
	}
	if(message.length>0){
		alert(message);
		return false;
	}
	fm.action = "/claim/generalClaimEditPost.do";
	fm.method = "post";
	fm.submit();
}
function selectReceiveOperator(field,codeRelation,isQueryCode) {
	var $tr = $(field).parents("tr[name='trPrpLgeneralClaimTaskLog']");
	var comCode = $tr.find(":input[name='prpLgeneralClaimTaskLogReceiveComCode']").val();
	var nodeType = $tr.find(":input[name='nodeType']").val();
	if (comCode=="") {
		alert(i18n.general.checkComCode);//请先选择处理机构！
		return false;
	}else if(nodeType==""){
		alert( "沒有可以修改的節點！");//请先选择处理机构！
		return false;
	} else {
		code_CodeSelect(field, 'queryUserHaveRights', codeRelation, 'Y',isQueryCode, comCode + "," + nodeType);
	}
	return true;
}
function showExtendString(field){
	var $tr = $(field).parents("tr[name='trPrpLgeneralClaimTaskLog']");
	var extendString1 = $tr.find(":input[name='extendString1']").val();
	$("#divExtendString").find(":input[name='textExtendString1']").val(extendString1);
	$("#divExtendString").css("left",findPosX(field)-600).css("top",findPosY(field)+20).show();
}

function closeExtendString(field){
	$("#divExtendString").hide();
}
/**定位div的内容*/
function findPosX(obj){
    var curLeft = 0;
    if(obj.offsetParent){
      do{
        curLeft += obj.offsetLeft;
      }while(obj = obj.offsetParent);
    }else if(obj.x){
      curLeft += obj.x;
    }
    return curLeft;
  }
/**定位div的内容*/
function findPosY(obj){
   var curTop = 0;
   if (obj.offsetParent){
    do{
      curTop += obj.offsetTop;
    }while(obj = obj.offsetParent);
  }else if(obj.y){
    curTop += obj.y;
  }
  return curTop;
}