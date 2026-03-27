<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     ：sunchenggang
* CREATEDATE ：2006-03-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*            
****************************************************************************
--%>
<%-- 保存通用按钮 --%>
<table cellpadding="5" cellspacing="1" class=common style="display:" id="buttonArea">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
               (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
               (3)已完成   "更新" "取消" "提交"
               (4)已提交   "返回"
               (5)撤消     "返回"                  
          --%>
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td colspan="5">
			<input type="hidden" name=buttonSaveType value="1">
		</td>
	</tr>
	<tr>
		<%
			String dfFlag = request.getParameter("dfFlag");

			if ("Y".equals(dfFlag)) {
		%>
		<td class=button style="width: 33%" align="center">
			<!--取消按钮-->
			<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
		</td>
		<%
			} else {
		%>
		<logic:notEqual name="prpLverifyLossDto" property="status" value="4">
			<td class=button style="width: 20%" align="center">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
			</td>
			<td class=button style="width: 20%" align="center">
				<!--提交的对象和内容
               
                <html:select name="swfPathDto" property="nextNodeNo" style="width:80px">
                    <html:options collection="pathList" property="endNodeNo" labelProperty="endNodeName"/>          
                </html:select> 
                -->
				<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
			</td>
			<td class=button style="width: 20%" align="center">
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
			</td>
		</logic:notEqual>
		<logic:equal name="prpLverifyLossDto" property="status" value="4">
			<%
				String ifclose = request.getParameter("ifclose");
						if ("true".equals(ifclose)) {
			%>
			<td class=button style="width: 33%" align="center">
				<input type="button" name=buttonSave class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
			</td>
			<%
				} else {
			%>
			<td class=button style="width: 20%" align="center">
				<!--取消按钮-->
				<input id="buttonreturn" type=button name="buttonCancel" class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.go(-1);">
			</td>
			<%
				}
			%>
		</logic:equal>
		<%
			//对外核价时不显示退回按钮
				if (!nodeType.equals("verpo")) {
		%>
		<td class=button style="width: 20%" align="center">
			<!--退回按钮-->
			<input type=button name=buttonReject class='button' value="<s:text name='button.return.value' />" onclick="return saveForm(this,'5');">
		</td>
		<%
			}
		%>
		<!--放弃任务(只对待处理状态)-->
		<%
			String statusgiv = request.getParameter("status");
				//System.out.println("satatus为0吗？"+statusgiv);
				if (statusgiv != null && statusgiv.equals("0")) {
		%>
		<td class=button align="center">
			<!--放弃按钮style="width:30%"-->
			<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
		</td>
		<%
			}
			}
		%>
		<!--放弃任务-->
	</tr>
</table>