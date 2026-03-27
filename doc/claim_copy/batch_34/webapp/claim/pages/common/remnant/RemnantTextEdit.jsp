<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)"> <b><s:text name="compensate.adjustReport" /></b>
			<%-- 理算报告 --%>
			<br>
			<table class="common" align="center" id="RegistText" >
				<tbody>
					<tr>
						<td class="input" style="text-align: center;">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLctextContextInnerHTML"><c:out value="${remnantDto.prpLctext.context}" /></textarea>
							<br>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
