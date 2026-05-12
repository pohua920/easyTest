<%--
****************************************************************************
* DESC       ： 报案修改信息页面
* AUTHOR     ： 曹志刚 
* CREATEDATE ： 2009-11-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
//按钮单击事件，显示轨迹
function showLocus(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y
	ex = ex - 1100;
	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}

function hideLocus(spanID) {
	var span = eval(spanID);
	span.style.display = 'none';
}
</SCRIPT>
<input type="hidden" name="modifyFlag" value="${editType}">
<input type="hidden" name="editType" value="${editType}">
<table class="prompt">
	<tr class=listtitle>
		<td colspan="4">
			<s:text name="regist.actionModifyInformationInsert" />
			<%--报案修改人信息录入 --%>
	</tr>
	<tr>
		<td class="title">
			<s:text name="regist.modifyTime" />
			<%--修改时间 --%>
		</td>
		<td class="input">
			<%-- <input class="input" type=text name="alterTime" title="修改時間"
				value="${alterTime }">
				--%>
			<rc:rcDate name="alterTime" title="<s:text name='regist.modifyTime'/>" class="readonly" readonly="true" wdatePicker="false" style="width:120px" value="${alterTime}" /><%--修改時間--%>
		</td>
		<td class="title">
			<s:text name="regist.actionModifyName" />
			<%--报案修改人姓名 --%>
		</td>
		<td class="input">
			<input class="input" type=text name="alterName" title="<s:text name="regist.actionModifyName"/>"><%--備案修改人姓名--%>
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.phoneNumber" />
			<%--联系电话 --%>
		</td>
		<td class="input">
			<input class="input" type=text name="alterPhoneNumber" title="<s:text name="regist.actionModifyPhoneNumber"/>">
		</td>
		<td class="title">
			<s:text name="regist.relationshipInsured" />
			<%--与被保险人关系 --%>
		</td>
		<td class="input">
			<select name="alterRelationType" class='input' title="<s:text name="regist.relationshipInsured"/>">
				<option value="1">
					<s:text name="regist.prpLregist.self" />
					<%--本人 --%>
				</option>
				<option value="2">
					<s:text name="regist.prpLregist.agentName" />
					<%--代理人 --%>
				</option>
				<option value="3">
					<s:text name="certainLoss.thirdCarLoss.duty9" />
					<%--其他 --%>
				</option>
			</select>
		</td>
	</tr>
</table>
<table class="prompt">
	<tr class=listtitle>
		<td colspan="6">
			<s:text name="regist.historyActionModifyInformation" />
			<%--历史报案修改信息 --%>
	</tr>
	<tr class="prompt">
		<td class="prompttitle" width="5%">
			<s:text name="db.prpDrate.serialNo" />
			<%--序号 --%>
		</td>
		<td class="prompttitle" width="20%">
			<s:text name="regist.modifyTime" />
			<%--修改时间 --%>
		</td>
		<td class="prompttitle" width="20%">
			<s:text name="regist.modifyName" />
			<%--修改人姓名 --%>
		</td>
		<td class="prompttitle" width="15%">
			<s:text name="db.prpLregist.phoneNumber" />
			<%--联系电话 --%>
		</td>
		<td class="prompttitle" width="20%">
			<s:text name="regist.relationshipInsured" />
			<%--与被保险人关系 --%>
		</td>
		<td class="prompttitle" width="20%">
			<s:text name="regist.reportModifyAction" />
			<%--报案修改轨迹 --%>
		</td>
	</tr>
	<c:if test="${modifyInfoList!=null}">
		<c:forEach items="${modifyInfoList}" var="prpLregistDto2" varStatus="prpLregistDto2_status">
			<tr>
				<td class="prompt">${prpLregistDto2_status.count }</td>
				<td class="prompt">
					<%-- ${prpLregistDto2.alterTime}--%>
					<rc:rcDate name="alterTime" class="readonly" readonly="true" wdatePicker="false" value="${prpLregistDto2.alterTime}" />
				</td>
				<td class="prompt">${prpLregistDto2.alterName}</td>
				<td class="prompt">${prpLregistDto2.alterPhoneNumber}</td>
				<td class="prompt">
					<c:if test="${prpLregistDto2.alterRelationType=='1'}">
						<s:text name="regist.prpLregist.self" />
						<%--本人--%>
					</c:if>
					<c:if test="${prpLregistDto2.alterRelationType=='2'}">
						<s:text name="regist.prpLregist.agentName" />
						<%--代理人--%>
					</c:if>
					<c:if test="${prpLregistDto2.alterRelationType=='3'}">
						<s:text name="regist.prpLregist.other" />
					</c:if>
					<%--其他 --%>
				</td>
				<td class="prompt">
					<input type="button" name="showLocusButton" class="button" value="<s:text name="button.modifyAction.value" />" onClick="showLocus('LocusText${prpLregistDto2_status.index }',0)">
					<%--修改轨迹 --%>
					<span id="LocusText${prpLregistDto2_status.index }" style='width: 800; display: none; position: absolute; background-color: FFFFFF;'>
						<table class=common cellpadding="5" cellspacing="1" style="position: absolute;">
							<tr>
								<td>
									<textarea style="wrap: hard; overflow: auto" rows="20" type="text" readonly name="modifyLocusText">${prpLregistDto2.alterLocus}</textarea>
								</td>
							</tr>
							<tr>
								<td colspan=14 class="common">
									<input type="button" name='hideLocusButton' value='<s:text name="db.prpDrate.serialNo" /><s:text name="button.useClose.value" />' class="button" ACCESSKEY="O" onclick="hideLocus('LocusText${prpLregistDto2_status.index }')">
								</td>
							</tr>
						</table>
					</span>
				</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
