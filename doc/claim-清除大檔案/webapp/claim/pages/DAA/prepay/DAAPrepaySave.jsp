<!--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<s:if test="#attr.editType=='SHOW'||#attr.editType=='DELETE'">
	<%-- 保存通用按钮 --%>
	<table cellpadding="0" cellspacing="0" class="common">
		<tr>
			<%-- 隐藏所按的保存按钮是哪个的标志--%>
			<td><input type="hidden" name=buttonSaveType value="1"></td>
		</tr>
		<tr>
			<td>
				<!--取消按钮--> <input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);">
			</td>
		</tr>
	</table>
</s:if>
<s:else>
	<%-- 保存通用按钮 --%>
	<table id="buttonArea" cellpadding="0" cellspacing="0" class="common">
		<!--在不同状态下，按钮的数量是不同的，-->
		<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
       (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
       (3)已完成   "更新" "取消" "提交"
       (4)已提交   "返回"
       (5)撤消     "返回"
  --%>
		<tr>
			<%-- 隐藏所按的保存按钮是哪个的标志--%>
			<td><input type="hidden" name=buttonSaveType value="1"></td>
		</tr>
		<tr>
			<c:if test="${prpLprepay.status!='4'}">
				<td>
					<!--保存按钮--> <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
				</td>
				<td><input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');"></td>
				<td>
					<!--取消按钮--> <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
				</td>
			</c:if>
			<c:if test="${prpLprepay.status=='4'}">
				<c:if test="${param.ifclose=='true'}">
					<td><input type="button" name=buttonSave class='button' value="<s:text name='button.close.value'/>" onclick="window.close();"></td>
				</c:if>
				<c:if test="${param.ifclose!='true'}">
					<td>
						<!--取消按钮--> <input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="return resetForm();">
					</td>
				</c:if>
			</c:if>
		</tr>
	</table>
</s:else>
