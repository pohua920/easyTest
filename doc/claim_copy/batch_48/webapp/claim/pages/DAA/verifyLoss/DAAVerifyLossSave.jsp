<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2013-02-28
* MODIFYLIST ：   Name       Date            Reason/Contents
* 增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%-- 保存通用按钮 --%>
<table cellpadding="0" cellspacing="0" style="display:" id="buttonArea">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成并提交"
               (2)正处理   "更新" "取消","已完成","已完成并提交","撤消"
               (3)已完成   "更新" "取消" "提交"
               (4)已提交   "返回"
               (5)撤消     "返回"                  
          --%>
	<input type="hidden" name=buttonSaveType value="1">
	<tr>
		<c:choose>
			<c:when test="${param.editType =='SHOW'}">
				<!--取消按钮-->
				<input type=button name=buttonBack class='button' value="<s:text name="prompt.back" />" onclick="javascript:history.go(-1);">
				<%--返回--%>
			&nbsp;&nbsp;
	    </c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${param.dfFlag =='Y'}">
						<td>
							<!--返回按钮-->
							<input type=button name=buttonCancel class='button' value="<s:text name="prompt.back" />" onclick="javascript:history.go(-1);">
							&nbsp;&nbsp;
							<%--返回--%>
						</td>
					</c:when>
					<c:otherwise>
						<c:if test="${requestScope.prpLverifyLoss.status !='4'}">
							<td>
								<!--保存按钮-->
								<input type="button" name=buttonSave class='button' value="<s:text name="compensate.staging" />" onclick="return saveForm(this,'2');">
								&nbsp;&nbsp;
								<%--基本信息--%>
							</td>
							<td>
								<s:select list="#request.pathList" name="nextNodeNo" value="#request.swfPath.nextNodeNo" listKey="endNodeNo" listValue="endNodeName" style="width:80px;display:none"></s:select>
								<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
								&nbsp;&nbsp;
							</td>
							<td>
								<!--取消按钮-->
								<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
								&nbsp;&nbsp;
							</td>
						</c:if>
						<c:if test="${requestScope.prpLverifyLoss.status =='4'}">
							<c:choose>
								<c:when test="${param.ifclose =='true'}">
									<td>
										<input type="button" name=buttonSave class='button' value="<s:text name="button.close.value" />" onclick="window.close();">
										&nbsp;&nbsp;
										<%--关 闭--%>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<!--取消按钮-->
										<input type=button name=buttonCancel class='button' value="<s:text name="prompt.back" />" onclick="javascript:history.go(-1);">
										&nbsp;&nbsp;
										<%--返 回--%>
									</td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<td>
							<!--取消按钮-->
							<input type=button name=buttonReject class='button' value="<s:text name="button.back.value" />" onclick="return saveForm(this,'5');">
							<%--退 回--%>
							&nbsp;&nbsp;
						</td>
						<!--放弃任务(只对待处理状态)-->
						<c:if test="${param.status =='0'}">
							<td>
								<!--放弃按钮style="width:33%"-->
								<input type=button name=buttonGiveup class='button' value="<s:text name="button.giveUpTask.value" />" <%--放弃任务--%>
								onclick="taskGiveup();">
								&nbsp;&nbsp;
							</td>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</tr>
</table>