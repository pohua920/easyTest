<%--
****************************************************************************
* DESC       ¡êo??¨º??¨¢¡ã???¡Á?¨°3??
* AUTHOR     : ¨¤¨ª?a¡Á¨¦
* CREATEDATE ¡êo 2013-02-03
* MODIFYLIST ¡êo   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table id="buttonArea" cellpadding="0" cellspacing="0" id='tablebutton' align="center">
	<table class="common" align="center" style="width: 100%">
		<tr>
			<td class="common" style="text-align: left;">
				<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="EndcaseTextImg" onclick="showPage(this,EndcaseText)">
				<s:text name="db.prpLltext.text2" />
				<!--?¨¢¡ã?¡À¡§??-->
				<br>
				<table class="common" cellpadding="5" cellspacing="1" id="EndcaseText" style="display: none">
					<tbody>
						<tr>
							<td class="input" style="text-align: center;" colspan="0">
								<textarea style="wrap: hard" rows="15" cols="80" name="prpLltextContextInnerHTML">${prpLltext.context}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</table>