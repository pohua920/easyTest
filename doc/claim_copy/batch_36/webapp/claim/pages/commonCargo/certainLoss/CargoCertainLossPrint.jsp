<%--
****************************************************************************
* DESC       ：添加定损列印页面
* AUTHOR     ： wangli
* CREATEDATE ： 2005-03-29   
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>


<script language="javascript">

/**
 * printType 调用列印表类型
 */
 
  //列印提交按钮
 function submitForm1(printType){
  var claimNo = fm.prpLverifyLossClaimNo.value;
  //alert(printType);
  if(claimNo==''){
   alert("无赔案号，无法列印");
   return false;
   }
  var  strUrl = "/claim/ClaimPrint.do?printType="+printType+"&ClaimNo=" + claimNo;
  //alert("赔案号为："+claimNo);
  printWindow(strUrl,"列印1");

  } 
  //显示列印窗口
      function printWindow(strURL,strWindowName)
      {
    	//add print liudaoping 2013-04-15
          //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
          return false;
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
</script>

<table border="0" align="center" cellpadding="4" cellspacing="1"  class="title" >
      <tr>
         <td class="common" align="left" style="width:20%"  >
            <input type=button value="<s:text name='button.projectsList.value'/>" class='bigbutton' onclick="submitForm1('ComponentList');"><%-- 零部件更换项目清单 --%>
        </td>
         <td class="common" align="center" style="width:20%"  >
            <input type=button value="<s:text name='button.repairProjectsList.value'/>" class='bigbutton' onclick="submitForm1('RepairList');"><%-- 修理项目清单列印 --%>
        </td>
        <td class="common" align="center" style="width:20%"  >
            <input type=button value="<s:text name='button.projectsListTable.value'/>" class='bigbutton' onclick="submitForm1('RepairAdd');"> <%-- 修理项目清单附表 --%>
        </td>
        <td class="common" align="center" style="width:20%"  >
            <input type=button value="<s:text name='button.damagePrint.value'/>" class='bigbutton' onclick="submitForm1('Loss');"><%-- 损失情况确认书列印 --%>
        </td>
       <td class="common" align="right" style="width:20%"  >
            <input type=button value="<s:text name='button.testReport.value'/>" class='bigbutton' onclick="submitForm1('CheckCertainLoss');"><%-- 检验定损报告列印 --%>
        </td>
      </tr>
 </table>

