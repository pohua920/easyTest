<%--
****************************************************************************
* DESC       ：录入/显示事故经过及其事故者现状 1事故经过及其事故者现状 2为调查描叙
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
	<tr class=mline>
		<td class="subformtitle" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
			<c:if test="${prpLregistText.id.textType=='1'}">
				<s:text name="commonAcci.regist.accidentProcessAccident" />
				<%--事故经过及其事故者现状--%>
			</c:if>
			<c:if test="${prpLregistText.id.textType=='2'}">
				<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDetail" />
				<%--调查描述--%>
			</c:if>
			<c:if test="${prpLregistText.id.textType=='3'}">
				<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDetail" />
				<%--调查描述--%>
			</c:if>
			<br>
			<table class="common" align="center" id="RegistText" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;" colspan="0">
							<c:if test="${editType=='ADD'}">
								<%--事故经过--%>
								<%--事故结果--%>
								<%--事故者現狀 --%>
								<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML">
<s:text name="print.accidentAfter" />：
<s:text name="commonAcci.regist.accidentResult" />：
<s:text name="commonAcci.regist.accidentPresentSituation" />：</textarea>
								<%-- 不能移动过来对齐，移动过来後，textarea里面会有空格 --%>
							</c:if>
							<c:if test="${editType!='ADD'}">
								<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML">${prpLregistText.context}</textarea>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<c:if test="${not empty strContext}">
	<table class="common" align="center" width="100%">
		<tr class=mline>
			<td class="subformtitle" style="text-align: left;">
				<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText3)">
				<s:text name="db.regist.registText.textType2" />
				<br>
				<table class="common" align="center" id="RegistText3" style="display: none" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="title" style="text-align: center;">
								<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML2">${strContext }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</c:if>
