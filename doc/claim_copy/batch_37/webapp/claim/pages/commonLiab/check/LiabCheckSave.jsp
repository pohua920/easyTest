<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>  
      //显示列印窗口
      function printWindow(registNo,strWindowName)
      {
    	//add print 
          //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
          return false;
        strUrl = "/claim/ClaimPrint.do?printType=CopyPrint&RegistNo=" + registNo;       
        var pageWidth=screen.availWidth-10;
        var pageHeight=screen.availHeight-30;
        
        if (pageWidth<100 )
          pageWidth = 100;
      
        if (pageHeight<100 )
          pageHeight = 100;
      
        var newWindow = window.open(strUrl,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
        newWindow.focus();
        return newWindow;
      }       
</script>
<%-- 保存通用按钮 --%>        
          <table id="buttonArea" cellpadding="0" cellspacing="0" width="80%" style="display:">
            <!--在不同状态下，按钮的数量是不同的，-->
            <%-- (1)立案登记 " 保存" " 取消" "已完成","已完成並提交"
                 (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
                 (3)已完成   "更新" "取消" "提交"
                 (4)已提交   "返回"
                 (5)撤消     "返回"
            --%>
            <tr>
            <%-- 隐藏所按的保存按钮是哪个的标志--%>
            <td>
            <input  type="hidden" name=buttonSaveType value="1">
            <%-- 代查勘 --%>
            <input type="hidden" name="checkGuideUser" value="${checkGuideUser }">
			<input type="hidden" name="checkGuideMessages" value="${checkGuideMessages }">
            </td>
            </tr>
            <tr>
              <td>
              	<input type="hidden" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value'/>" onclick="printWindow('${prpLcheck.id.registNo}','列印1');">&nbsp;&nbsp;<%--列印承保理赔信息--%>
              </td>
              <c:if test="${prpLcheck.status!='4'}">
              	<td class=button style="width:20%" align="center">
                <!--保存按钮-->
                 <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="saveForm(this,'2');">
                 
              </td>
              
              <td class=button style="width:70%" align="center">
               <!--提交的对象和内容-->
               	<c:if test="${!checkGuideUser }">
                	<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="saveForm(this,'4');">
              	</c:if>
              </td>
              <td class=button style="width:20%" align="center">
                <!--取消按钮-->
                <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();" >
              </td>
            
             <!--放弃任务(只对待处理状态)-->
             <c:if test="${not empty requestScope.status && requestScope.status=='0'&& !checkGuideUser }">
             	<td class=button  align="center">
              <!--放弃按钮style="width:33%"-->
             <input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskCheckGiveup();" >
             </td>
             </c:if>
           <!--放弃任务-->
           <%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
           </c:if>
            <c:if test="${prpLcheck.status=='4'}">
            	<td class=button style="width:33%" align="center">
              <!--取消按钮-->
              
              <input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();" >
            </td>
            </c:if>
        </tr>
      </table>