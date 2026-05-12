<!--
****************************************************************************
* DESC       ：显示预赔文字页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-05-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
	<tr>
		<td class="common" style="text-align: left;"><img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)"> <s:text
				name="prepay.compensationReport" />： <br> <!--预赔报告-->
			<table class="common" cellpadding="5" cellspacing="1" id="RegistText" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;"><textarea style="wrap: hard" rows="15" cols="80" name="prpLptextContextInnerHTML">${prpLptext.context}</textarea></td>
					</tr>
				</tbody>
			</table></td>
	</tr>
</table>
