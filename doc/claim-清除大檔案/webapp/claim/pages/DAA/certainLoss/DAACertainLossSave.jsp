<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2013-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%-- 保存通用按钮 --%>
<table cellpadding="0" cellspacing="0" align="center" id="buttonArea">
	<tr>
		<!-- 一个table两个id del: id="tablebutton" -->
		<!--在不同状态下，按钮的数量是不同的，-->
		<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
  (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
  (3)已完成   "更新" "取消" "提交"
  (4)已提交   "返回"
  (5)撤消     "返回"                  
  --%>
		<%-- 只显示返回按钮--%>
		<c:choose>
			<c:when test="${param.editType =='SHOW'}">
				<td>
					<!--返回按钮-->
					<input type="button" name=buttonBack class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);">
					&nbsp;&nbsp;
				</td>
				<td>
					<input type="hidden" name=buttonSaveType value="1">
				</td>
			</c:when>
			<c:otherwise>
				<%-- 隐藏所按的保存按钮是哪个的标志--%>
				<td>
					<input type="hidden" name=buttonSaveType value="2">
					<%--
						//简易赔案增加录入按钮的判断
					--%>
					<input type="hidden" name="status" value="INPUT">
					<input type="hidden" name="nodeType" value="${param.nodeType }">
				</td>
				<%--
					//检查是不是从查勘直接进入的定损，如果是的话，那么只保存，没有工作流的事情。
				--%>
				<c:choose>
					<c:when test="${param.dfFlag =='Y'}">
						<td>
							<!--返回按钮-->
							<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);">
							&nbsp;&nbsp;
						</td>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${param.checkInput =='true'}">
								<td>
									<!--暂存按钮-->
									<input type="hidden" name=checkInput value="true">
									<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'CheckInput');">
									&nbsp;&nbsp;
								</td>
								<td>
									<!--返回按钮-->
									<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);">
									&nbsp;&nbsp;
								</td>
							</c:when>
							<c:otherwise>
								<c:if test="${requestScope.prpLverifyLossDto.status !='4'}">
									<td>
										<!--暂存按钮-->
										<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
										&nbsp;&nbsp;
									</td>
									<td>
										<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
										&nbsp;&nbsp;
									</td>
									<td>
										<!--取消按钮-->
										<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
										&nbsp;&nbsp;
									</td>
								</c:if>
							</c:otherwise>
						</c:choose>
						<c:if test="${requestScope.prpLverifyLossDto.status =='4'}">
							<c:choose>
								<c:when test="${param.ifclose =='true'}">
									<td>
										<input type="button" name=buttonSave class='button' value="<s:text name='button.close.value'/>" onclick="window.close();">
										&nbsp;&nbsp;
										<!--关 闭-->
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<!--返回按钮-->
										<input type="button" class="button" value="<s:text name='button.return.value'/>" src="/claim/images/bgClaimBackButtonSmall.gif" align="middle" width="68" height="21" border-style="none"
											LANGUAGE=JAVASCRIPT onmousedown="history.back();return false;">
										&nbsp;&nbsp;
									</td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${requestScope.prpLverifyLossDto.status =='2'}">
							<td>
								<input type="button" name=giveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="giveupTemporarySave('certa');">
								&nbsp;&nbsp;
								<!--放弃任务-->
							</td>
							<td>
								<c:if test="${sessionScope.user.quickCaseWritePower && param.nodeType =='certa'}">
									<!--转为简易赔案按钮-->
									<input type="button" name=buttonQuickCase class='button' value="<s:text name='button.notifySchedul.value'/>" style="display: none" onclick="return changeToQuickCase();">&nbsp;&nbsp;
					             </c:if>
							</td>
						</c:if>
						<!--放弃任务(只对待处理状态)-->
						<c:if test="${param.status =='0'}">
							<td>
								<!--放弃按钮style="width:30%"-->
								<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="taskGiveup();">
								&nbsp;&nbsp;
							</td>
							<td>
								<c:if test="${sessionScope.user.quickCaseWritePower && param.nodeType =='certa'}">
									<!--转为简易赔案按钮-->
									<input type="button" name=buttonQuickCase class='button' value="<s:text name='button.notifySchedul.value'/>" style="display: none" onclick="return changeToQuickCase();">&nbsp;&nbsp;
					             </c:if>
							</td>
						</c:if>
					</c:otherwise>
				</c:choose>
	</tr>
	</c:otherwise>
	</c:choose>
</table>
