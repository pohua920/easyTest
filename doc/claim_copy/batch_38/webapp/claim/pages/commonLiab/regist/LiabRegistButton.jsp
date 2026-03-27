        <%-- 保存通用按钮 --%>
        <%@ include file="/common/taglibs.jsp"%>
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
              <input type="hidden" name=buttonSaveType value="1">
            </td>
            </tr>
            <tr>
            <c:if test="${ prpLregist.status=='1'}">
            	<td class=button style="width:25%" align="center">
                <!--保存按钮-->
                <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
              </td>
              
              <td class=button style="width:25%" align="center">
                <!--已完成按钮-->
                <input type="button" name=buttonSaveFinish class='button' value="<s:text name='button.finish.value'/>"  onclick="return saveForm(this,'3');" >
              </td>
         
              <td class=button style="width:25%" align="center">
                <!--已完成並提交按钮-->
                <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.finishsubmit.value'/>" onclick="return saveForm(this,'4');" >
              </td>
         
             <td class=button style="width:25%" align="center">
                <!--取消按钮-->
                <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();" >
             </td>
            </c:if>
            <c:if test="${ prpLregist.status=='2'}">
            	<td class=button style="width:33%" align="center">
                <!--保存按钮-->
                 <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
              </td>
              <td class=button style="width:25%" align="center">
                <!--已完成按钮-->
                <input type="button" name=buttonSaveFinish class='button' value="<s:text name='button.finish.value'/>"  onclick="return saveForm(this,'3');" >
              </td>
              <td class=button style="width:33%" align="center">
                <!--提交按钮-->
                <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');" >
              </td>
         
              <td class=button style="width:33%" align="center">
                <!--取消按钮-->
                <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();" >
              </td>
            </c:if>
            <c:if test="${ prpLregist.status=='3'}">
            <td class=button style="width:33%" align="center">
                <!--保存按钮-->
                <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'3');">
              </td>
              <td class=button style="width:33%" align="center">
                <!--回退按钮-->
                <input type="button" name=buttonSave class='button' value="<s:text name='button.return.value'/>" onclick="return saveForm(this,'2');">
              </td>          
             <td class=button style="width:33%" align="center">
                <!--提交按钮-->
                <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');" >
             </td>
            <td class=button style="width:33%" align="center">
              <!--取消按钮-->
              <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();" >
            </td>
            </c:if>
            <c:if test="${ prpLregist.status=='4'}">
            <td class=button style="width:33%" align="center">
              <!--返回按钮-->
              <input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="history.back();"  >
            </td>
            </c:if>
        </tr>
      </table>