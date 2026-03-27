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
		<td style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
			<%--  调查描述--%>
			<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDetail" />
			<br>
			<table class="common" align="center" id="RegistText" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML">${prpLacciCheckTextDto.context }</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
