<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>           
<%  
  String editTypeTemp=request.getParameter("editType");
  if (editTypeTemp.equals("SHOW")||editTypeTemp.equals("DELETE"))
  {
%>

<%-- 保存通用按钮 --%>
<table cellpadding="0" cellspacing="0" width="80%" class="common">

  <tr>
    <%-- 隐藏所按的保存按钮是哪个的标志--%>
    <td>
      <input  type="hidden" name=buttonSaveType value="1"> 
    </td>
  </tr>  
  <tr>  
     <td class=button style="width:33%" align="center">
     <!--返回按钮-->
       <input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);" >
     </td> 
 </tr>
</table>  
<table cellpadding="0" cellspacing="0" width="0"  height="0"id="buttonArea">

</table>  
<%
  }else{
%>
  <%-- 保存通用按钮 --%>
    
    <table id="buttonArea" cellpadding="0" cellspacing="0" width="80%" style="display:">
      <!--在不同状态下，按钮的数量是不同的，-->
      <%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
           (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
           (4)已提交   "返回"
           (5)撤消     "返回"
           因为是自动的，所以先注释掉相应的人员提交 
      --%>
      <tr>
      <%-- 隐藏所按的保存按钮是哪个的标志--%>
      <td>
        <input  type="hidden" name=buttonSaveType value="1">
      </td>
      </tr>
      <tr>
                  <logic:notEqual name="prpLcompensateDto" property="status" value="4">
              <td class=button style="width:20%" align="center">
                <!--暂存按钮-->
                 <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
              </td>
              <td class=button style="width:70%" align="center">
               <!--提交的对象和内容-->
             <%/* 
             
               <select name="nextNodeNo" >
                    <option value="1">报案</option>
                    <option value="2">	调度</option>	
                    <option value="3">	查勘</option>	
                    <option value="4">	定损</option>	
                    <option value="5">	核损</option>	
                    <option value="10">	单证</option>	
                    <option value="11">	预赔</option>	
                    <option value="12">	实赔</option>	
                    <option value="13">	结案</option>	
                    <option value="14">立案	</option>
                  </select>
                <input type="text" name=nextHandlerCode class='common' style="width:90px" maxlength="10" value="" >
                <input type="text" name=nextHandlerName class='common' style="width:50px" readonly value="" >
             */%>
                <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');" >
              </td>
              <td class=button style="width:20%" align="center">
                <!--取消按钮-->
                <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();" >
              </td>
             <%String statusgiv= request.getParameter("status");
             if(statusgiv.equals("0")){
             %>
             <td class=button  align="center">
              <!--放弃任务按钮style="width:33%"-->
             <input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="taskGiveup();" >
             </td>
             <%
             }
              %>       
           <%@include file="/common/sendUndwrt/SendUndwrtButton.jsp"%>
            </logic:notEqual>
              
     <logic:equal name="prpLcompensateDto" property="status" value="4">
       <td class=button style="width:33%" align="center">
        <!--返回按钮-->
        <input type=button name=buttonBack class='button' value="<s:text name='button.return.value'/>" onclick="return history.back();" >
      </td>
    </logic:equal>          
  </tr> 
</table>
<%
  }
%>
