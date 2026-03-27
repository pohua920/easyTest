/***************************************************************************

* Description: 处理批量核保任务

* Author     : luyang

* CreateDate:  2004-12-29 14:28

* UpdateLog：  Name       Date            Reason/Contents

*

****************************************************************************/



//提交批量任务

function submitBatchTaskBefore()

{
//modify by zhangfan 当审批意见为空时，对其中是否有回车的判断
var HandleTextTrim=replace(fm.HandleText.value," ","");
var len=HandleTextTrim.length;
for(a="\r\n",i=0;i<len;i++)
  {
  	if(trim(HandleTextTrim)==a)
	  {
		alert(i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice);
    fm.HandleText.focus();
    return false;
		}
  a=a+"\r\n";	
  }
  //END
  var intCount = fm.operateFlag.length;

  var i = 0;

  var blnCheck = false;

  if(isEmptyField(fm.HandleText))

  {

	  alert(i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice);

    fm.HandleText.focus();

    return false;

  }

  for(i=0;i<intCount;i++)

  {

    if(fm.Delete[i].checked == true)

    {

      blnCheck = true;

      break;

    }

  }



  if(blnCheck)

  {

    fm.action="/undwrt/submitOrGiveUpTask/batchTaskSubmit.do?dealType=submitBefore";

    fm.method="post";

    fm.submit();

  }

  else

  {

    errorMessage(i18n.messages.chooseSubmitTask);

  }

}



//保存审批意见

function saveNotion()

{
//modify by zhangfan 当审批意见为空时，对其中是否有回车的判断
var HandleTextTrim=replace(fm.HandleText.value," ","");
var len=HandleTextTrim.length;
for(a="\r\n",i=0;i<len;i++)
  {
  	if(trim(HandleTextTrim)==a)
	  {
  		alert(i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice);
    fm.HandleText.focus();
    return false;
		}
  a=a+"\r\n";	
  }
  //END
  if(isEmptyField(fm.HandleText))

  {

	  alert(i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice);

    fm.HandleText.focus();

    return false;

  }

  fm.action="/undwrt/submitOrGiveUpTask/batchTaskSubmit.do?dealType=saveNotion";

  fm.method="post";

  fm.submit();

}



//提交批量任务

function submitBatchTask()

{

  var intCount = fm.radSelectNode.length;

  var FlowID,ModelNo,NodeNo,BusinessNo,FlowStatus,HandlerCode,Flag,CertiType;

  var strURL;

  var singleSubmit,multiSubmit;

  var i = 0;

  fm.action = "";

  intCount = intCount - 1;



  for(i=0;i<intCount;i++)

  {

    if(fm.radSelectNode[i].checked == true)

    {

      fm.selectNodeNo.value = fm.NodeNo[i].value;

      fm.selectNodeName.value = fm.NodeName[i].value;



      fm.action="/undwrt/submitOrGiveUpTask/batchTaskSubmit.do?dealType=submit";

      fm.method="post";

      fm.submit();

      return;

    }

  }

  errorMessage(i18n.messages.chooseSubmitCode);

}



/**设置选中标志**/

function updateStatus(index)

{

  if(fm.Delete[index].checked == true)

  {

    fm.operateFlag[index].value = "Y";

  }

  else if(fm.Delete[index].checked == false)

  {

    fm.operateFlag[index].value = "N" ;

  }

}



//全部选中

function selectBatchTaskAll()

{

	var flag=fm.selectedAll.checked;

	if(flag==true)

	{

		for(var i=0;i<fm.contractNo.length;i++)

		{

			fm.Delete[i].checked=true;

			fm.operateFlag[i].value="Y";

		}

	}

	else if(flag==false)

	{

		for(var i=0;i<fm.contractNo.length;i++)

		{

			fm.Delete[i].checked=false;

			fm.operateFlag[i].value="N";

		}

	}

}



function checkNotion()

{

  if(fm.HandleText.value="")

  {

	  alert(i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice);

  }

}

function cancelBatchTask()
{
  fm.butCancelForm.disabled = true;
  fm.action="/undwrt/submitOrGiveUpTask/batchTaskSubmit.do?dealType=cancel";

 fm.method="post";

  fm.submit();
	
}

function submitCommonBatchTask()
{
	//modify by zhangfan 当审批意见为空时，对其中是否有回车的判断
	var HandleTextTrim=replace(fm.HandleText.value," ","");
	var len=HandleTextTrim.length;
	for(a="\r\n",i=0;i<len;i++)
	  {
	  	if(trim(HandleTextTrim)==a)
		{ 
	  		alert("系統訊息：請填寫審批意見！");
//	  		alert("i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice");
	        fm.HandleText.focus();
	        return false;
		}
	    a=a+"\r\n";	
	  }
	  //END
	  var intCount = fm.operateFlag.length;	
	  var i = 0;	
	  if(isEmptyField(fm.HandleText))	
	  {	
	    alert("系統訊息：請填寫審批意見！");	//i18n.messages.systemInformation+"\n\n"+i18n.messages.inputApproveAdvice
	    fm.HandleText.focus();	
	    return false;	
	  }
      fm.action = "";
	  var intIndex = fm.selectNodeNo.selectedIndex;
	  //add by xuhuiling 按鈕點擊一次后失效  20160823 begin 
	  fm.butSubmitForm.disabled = true;
	  //add by xuhuiling 按鈕點擊一次后失效 20160823 end 
      fm.selectNodeNo.value = fm.selectNodeNo.value;      
      fm.selectNodeName.value = fm.selectNodeNo[intIndex].text;
      fm.action="/undwrt/submitOrGiveUpTask/batchTaskSubmit.do?dealType=submit";
      fm.method="post";
      fm.submit();
      return;
}



