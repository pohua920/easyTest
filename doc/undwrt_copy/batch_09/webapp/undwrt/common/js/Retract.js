//提交任务撤回js
 function submitRetractForm()
 {
 	if(fm.businessNo.value==""){
 		alert(i18n.messages.businessNo);
 		return false;
 	}
 	if(confirm(i18n.messages.withdrawSubmitedTask))
 {

 fm.action = "/undwrt/retractTask/commonRetractTask.do";
 fm.submit();
 }
 }