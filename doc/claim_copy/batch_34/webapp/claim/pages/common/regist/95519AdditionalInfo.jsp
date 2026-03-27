<%--
****************************************************************************
* DESC       ：95519补充信息展示
* AUTHOR     ：曹志刚
* CREATEDATE ：2009-08-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<input type="hidden" name="callCenterFlag" value="1">
<c:if test="${prpLregistText.id.textType=='1' }">
	<table class="common">
		<tr class=mline>
			<td class="subformtitle" style="text-align: left" colspan=4>
				<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="callCenterInfoTextImg" onclick="showPage(this,callCenterInfoText)">
				<s:text name="regist.callCenter" />
				<%--callCenter系统补充信息 --%>
				<br>
				<table class="common" align="center" id="callCenterInfoText" style="display: none">
					<tbody>
						<tr>
							<td class="title" style="text-align: center;">
								<textarea style="width: 700px" rows="15" cols="80" name="callCenterInfo">${callCenterInfo }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</c:if>
