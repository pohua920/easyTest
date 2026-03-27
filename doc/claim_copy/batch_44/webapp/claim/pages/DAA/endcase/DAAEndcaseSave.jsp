<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table id="buttonArea" cellpadding="0" cellspacing="0" id='tablebutton' align="center">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
                 (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
                 (3)已完成   "更新" "取消" "提交"
                 (4)已提交   "返回"
                 (5)撤消     "返回"
            --%>
	<%-- 隐藏所按的保存按钮是哪个的标志--%>
	<input type="hidden" name=buttonSaveType value="1">
	&nbsp;&nbsp;
	<tr>
		<td width="5px">
			<c:if test="${param.editType!='SHOW'}">
				<td>
					<!--保存按钮-->
					<input type="button" name=buttonSave class='button' value="<s:text name='form.save'/>" onclick="return saveForm(this,'4');">
					&nbsp;&nbsp;
					<%--保存 --%>
					<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="taskGiveup();">
					&nbsp;&nbsp;
					<%--放弃任务 --%>
				</td>
			</c:if>
		<td>
			<!--返回按钮-->
			<input type=button name=buttonBack class='button' value="<s:text name='button.return.value'/>" onclick="history.back();">
			&nbsp;&nbsp;
			<%--返回 --%>
		</td>
	</tr>
</table>