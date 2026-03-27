/**对输入域是否是整数的校验**/
function isInteger(strValue)
{
  var NUM="0123456789";
  var i;
  if(strValue==null || strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false;

  }
  return true;
}

//核保查询时，查询状态必须至少一个被选上
function validateForm()
{
	var nodeStatusObj = document.fm.nodeStatus;
	var checkedFlag = false;
	if(fm.editType.value == "deal"||fm.editType.value == "queryStats")
	{
		for(i=0;i<nodeStatusObj.length;i++){
		    if(nodeStatusObj.item(i).checked==true)
		    {
		        checkedFlag = true;
		    }
		}
		
		if(!checkedFlag){
		    alert(i18n.messages.mustChooseStatue);
			return false;
		}
	}
	
	fm.buttonSubmit.disabled = true;
	fm.submit();
}
function boundCheckBox(controlField, checkBoxField){
    var count=0;
    try{
        count = checkBoxField.length;
    }catch(E){
    }
    if(isNaN(count)){
        checkBoxField.checked=controlField.checked;
    }else{
        for(var i=0;i<count;i++){
            checkBoxField[i].checked=controlField.checked;
        }
    }
}
function validateFlowInTime()
{
	var start = fm.flowInTime1.value;
	var end = fm.flowInTime2.value;
	if(start != "" && !regExpTest(start, /[\d]{4}-[\d]{1,2}-[\d]{1,2}/))
	{
		errorMessage(i18n.messages.inputValidStartSubmitTime);
		fm.flowInTime1.focus();
		fm.flowInTime1.select();
		return false;
	}
	if(end != "" && !regExpTest(end, /[\d]{4}-[\d]{1,2}-[\d]{1,2}/))
	{
		errorMessage(i18n.messages.inputValidEndSubmitTime);
		fm.flowInTime2.focus();
		fm.flowInTime2.select();
		return false;
	}
}
function checkNodeStatus(nodeStatusValue)
{
	var nodeStatusObj = document.fm.nodeStatus;
	if(nodeStatusValue == "1" || nodeStatusValue == "2" || nodeStatusValue == "3" || nodeStatusValue == "5")
	{
		//nodeStatusObj.item(3).checked = false;
		//nodeStatusObj.item(4).checked = false;
		nodeStatusObj.item(2).checked = false;
		nodeStatusObj.item(3).checked = false;
	}
	else if(nodeStatusValue == "4")
	{
		//nodeStatusObj.item(0).checked = false;
		//nodeStatusObj.item(1).checked = false;
		//nodeStatusObj.item(2).checked = false;
		//nodeStatusObj.item(4).checked = false;
		nodeStatusObj.item(0).checked = false;
		nodeStatusObj.item(1).checked = false;
		nodeStatusObj.item(3).checked = false;
		
	}
	else if(nodeStatusValue == "0")
	{
		//nodeStatusObj.item(0).checked = false;
		//nodeStatusObj.item(1).checked = false;
		//nodeStatusObj.item(2).checked = false;
		//nodeStatusObj.item(3).checked = false;
		nodeStatusObj.item(0).checked = false;
		nodeStatusObj.item(1).checked = false;
		nodeStatusObj.item(2).checked = false;
	}
}
function changeField(categoryValue, handType)
{
	if(handType == "11")
	{
		if(categoryValue == "D")
		{
			licenseNoId.style.display = "";
			identifyId.style.display = "none";
			contractId.style.display = "none";
		}
		else if(categoryValue == "4")
		{
			licenseNoId.style.display = "none";
			identifyId.style.display = "";
			contractId.style.display = "none";
		}
		else if(categoryValue == "2")
		{
			licenseNoId.style.display = "none";
			identifyId.style.display = "none";
			contractId.style.display = "";
		}
		else
		{
			licenseNoId.style.display = "none";
			identifyId.style.display = "none";
			contractId.style.display = "none";
		}
	}
}

function buildRiskCodeSelect(riskCategoryField, riskCodeField)
{
	var categoryValue = riskCategoryField.value;
	riskCodeField.length = 0;
	riskCodeField.options[0] = new Option("", "");
	for(var i=0; i<riskCodeCount; i++)
	{
		if(riskCodes[i][0] == categoryValue)
		{
			riskCodeField.options[riskCodeField.length] = new Option(riskCodes[i][2], riskCodes[i][1]);
		}
	}
	riskCodeField.remove(0);
	for(var i=0; i<riskCodeField.length; i++)
	{
		riskCodeField.item(i).selected = true;
	}
}

function gotoPage(strMethod)
{
	if(strMethod=="First")
	{
		  fm.pageNo.value = 1;
	}
	else if(strMethod=="Previous")
	{
		  fm.pageNo.value = parseInt(fm.pageNo.value) - 1;
	}
	else if(strMethod=="Next")
	{
		  fm.pageNo.value = parseInt(fm.pageNo.value) + 1;
	}
	else if(strMethod=="Final")
	{
		  fm.pageNo.value = fm.PageCount.value;
	}
	else if(strMethod=="Personal")
	{
		  if(parseInt(fm.Personal.value)<1||parseInt(fm.Personal.value)>parseInt(fm.PageCount.value))
		  {
			alert(i18n.messages.haveNoThisPage);
			fm.Personal.focus();  
			return false;
		  }
		  else
		  {
			fm.pageNo.value = fm.Personal.value;
		  }
	}
	fm.action="/undwrt/newWfLogQuery.do?actionType=queryContinue";
	fm.submit();
}
/**
 * 导出指定结果列表对象到EXCEL(只保留数字)
 * @table 结果表的名称
 * @since 2005-12-31
 */
  function exportResultDataToExcel(table){
  alert(i18n.messages.undwrtEXCEL);
  var oXL;
  try{
   oXL = GetObject("","Excel.Application");
  }catch(E){
    try{
      oXL = new ActiveXObject("Excel.Application");
    }catch(E2){
      alert(i18n.messages.pleaseConfirm+"\n"+i18n.messages.installExcel+"\n"+i18n.messages.InternetSafe+i18n.messages.ActiveX);
      //showMessage("请确认:\n1.本机安装了Excel软件\n2.Internet选项中的安全设置\"对没有标记为安全的ActiveX进行初始化和脚本运行\"设置为启用");
      return;
    }
  }

  var oWB = oXL.Workbooks.Add();
  var oSheet = oWB.ActiveSheet;
  var displayArray = new Array();//add by yuyiqiang 20110518 存放被隐藏字段的列序号，从0开始
  //var Lenr = 1;
  var Lenr=table.tHead.rows.length;
  var HardLenr=Lenr+1;
  var maxColumn = 0; //最大Column号，从0开始
  var displayFlag = 0;
  for (var i=0;i<Lenr;i++){
    var Lenc = table.tHead.rows(i).cells.length;
    //add by yuyiqiang 20110518 begin 存放显示字段列序号

    for (j=0;j<Lenc;j++) {    
      var displayvalue = table.tHead.rows(i).cells(j).style.display;
      if(displayvalue=="none") { 
         
      }else {         
         displayArray[maxColumn] = j;     //将显示字段的列序号存入数组
         maxColumn = maxColumn + 1;
      }
    }
    //add by yuyiqiang 20110518 end 存放显示字段列序号  
    
    outerloop://命名外圈语句
    oSheet.Columns(1).EntireColumn.NumberFormatLocal = "@";
    oSheet.Cells(i+1,1).value = i18n.messages.serialNo;
    for(t=0;t<maxColumn;t++) { 
        var p = t+1;
	    for (j=0;j<Lenc;j++) {                      
	      innerloop://命名内圈语句
            if(j == displayArray[t]) {          //显示字段的列序号

	           oSheet.Columns(p+1).EntireColumn.NumberFormatLocal = "@";
		       oSheet.Cells(i+1,p+1).value = table.tHead.rows(i).cells(j).innerText;	           
	           break innerloop;//跳出内圈
            }else {
               continue;
            }           
        }
    }
  }
  
  Lenr = table.tBodies(0).rows.length;

  for (var i=0;i<Lenr;i++){
    var Lenc = table.tBodies(0).rows(i).cells.length;
    var j=0;
    var value = table.tBodies(0).rows(i).cells(j).innerText;
  
    var pos = value.indexOf(" ");
    if(pos>-1){
      value = trim(value.substring(pos));
    }
    oSheet.Cells(i+HardLenr,j+1).value = i+1;
    //初始化导出XLS第一列
    //oSheet.Cells(i+HardLenr,1).value = ; 
    for (t=0;t<=maxColumn;t++) {    //实现 t(显示字段序列) 和 j(原始字段序列)的一一对应
       var q = t + 1;
       for (j=0;j<Lenc;j++) {                     
            if(j == displayArray[t]) {
                oSheet.Cells(i+HardLenr,q+1).value = table.tBodies(0).rows(i).cells(j).innerText; 
                break;//跳出内圈
            }else {
                continue;
            } 
       }
    }
  }
  for (var i=0;i<maxColumn;i++){           
      oSheet.Columns(i+1).EntireColumn.AutoFit; 
  }  
  oXL.Visible = true;
}

//批量核保处理
function prepareBatchSubmit(handType, actionType)
{
	var n = 0;
	if(fm.checkboxSelect != null)
	{
		for(var i=1;i<fm.checkboxSelect.length;i++)
		{
			if(fm.checkboxSelect[i].checked==true)
			{
				n = n + 1;
			}
		}
	}
	if(n==0)
	{
		alert(i18n.messages.chooseRecord);
	}
	else
	{
		fm.action = "/undwrt/commonBatchTaskDeal.do?actionType=" + actionType;
		fm.submit();
	}
}
function batchSubmit(actionType, message)
{
	if(trim(fm.HandleText.value) == "")
	{
		alert(i18n.messages.approvelOpinion);
		return;
	}
	if(confirm(i18n.messages.reallyOrNot + message + i18n.messages.what))
	{
		fm.action = "/undwrt/commonBatchTaskDeal.do?actionType=" + actionType;
		fm.submit();
	}
}

function showPolicyInfo()
{
	var vBizType = "POLICY";
	var vRiskCode = fm.riskCode.value;
	var vPolicyNo = fm.PolicyNo.value;

    if(vRiskCode=="OAZ" || vRiskCode=="BAZ" || vRiskCode=="JPB")
    {
      vRiskCode = "OTH";
    }
    var vURL = '/prpall/'+ vRiskCode +'/tbcbpg/UIPrPoEn'+ vRiskCode +'Show.jsp?BIZTYPE='+ vBizType +
			   '&SHOWTYPE=SHOW&BizNo='+ vPolicyNo+'&RiskCode='+ vRiskCode;
    window.open(vURL,i18n.messages.detailedMessage,'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}

function showHistoryEndorses()
{
	var index = 0;
	for(var i=0; i<fm.radio.length; i++)
	{
		if(fm.radio.item(i).checked)
		{
			index = i;
			break;
		}
	}
	var vPolicyNo = fm.policyNo[i].value;
	var vRiskCode = fm.riskCode[i].value;

	if(vPolicyNo == "")
	{
		alert(i18n.messages.noProvideValidWarranty);
		return;
	}
	var vURL = "/undwrt/taskDealHistoryProposal.do?actionType=showHistoryEndorses&policyNo=" + vPolicyNo;
    window.open(vURL,i18n.messages.detailedMessage,'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}

